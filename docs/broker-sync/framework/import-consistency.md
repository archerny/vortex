# 券商同步 — 数据一致性与中断恢复设计文档

> **创建日期**：2026-04-16  
> **最后更新**：2026-04-18  
> **状态**：✅ 已实现（逐条独立事务 + 幂等去重 + 两级状态模型 + SyncBatchRecoveryRunner + Resume 端点 + 前端恢复按钮）  
> **关联**：[data-persistence.md](./data-persistence.md) | [overall-design.md](../overall-design.md) | [broker-registration.md](./broker-registration.md)  
> **前置**：Phase 2 数据库变更已完成（V19-V24 + Entity + Repository）

---

## 一、背景与目标

[data-persistence.md](./data-persistence.md) 定义了「暂存 → 导入」两阶段的数据持久化方案。本文档聚焦于 **导入过程中的数据一致性保证**，解决以下核心问题：

1. **事务策略**：staging 和 import 阶段如何保证数据不会处于不一致的中间状态？
2. **失败分类**：不同类型的失败如何处理？哪些可重试，哪些不可？
3. **中断恢复**：应用在 staging 或 import 过程中崩溃重启后，如何安全恢复？
4. **状态机设计**：batch 和 staged order 的状态如何精确反映执行进度？

**核心原则**：这是一个金融记账应用，对数据完整性不能妥协。不允许静默丢数据，不允许残留不可恢复的中间状态。

---

## 二、关键设计决策

| # | 决策项 | 结论 | 理由 |
|---|--------|------|------|
| 1 | 事务策略 | **逐条独立事务 + 幂等**，不用一个大事务包裹整个流程 | 数据量可能上百条，大事务 holding 连接时间太长；一条出错不应阻塞其他；逐条幂等天然支持断点续传 |
| 2 | Batch 状态模型 | **`status` + `phase` 两级状态** | `status` 是主状态（前端展示），`phase` 是子阶段（进度展示 + 诊断）；拆分后语义更清晰 |
| 3 | Batch 最终状态 | **三态：`COMPLETED` / `PARTIAL` / `FAILED`** | 区分全部成功、部分成功、整体失败三种结果，用户一看就知道后续操作 |
| 4 | 中断恢复策略 | **方案 A：完整重跑（fetch → parse → staging → import）** | 不用简化版（只 import 已有 staged records），因为 staging 中断时你无法确认数据是否完整，静默丢数据不可接受 |
| 5 | 计数统计方式 | **从 DB 统计，不依赖内存累加器** | 中断恢复后也能拿到准确数字 |

---

## 三、Batch 状态机

### 3.1 两级状态模型

| 字段 | 可选值 | 说明 |
|------|--------|------|
| `status` | `PENDING` / `PROCESSING` / `COMPLETED` / `PARTIAL` / `FAILED` / `INTERRUPTED` | 主状态，前端主要展示 |
| `phase` | `null` / `FETCHING` / `STAGING` / `IMPORTING` | 子阶段，仅 `status=PROCESSING` 时有意义；`status=INTERRUPTED` 时保留中断时的值用于诊断 |

> **命名变更**：之前的 `IMPORTING` 主状态改为 `PROCESSING`，因为它实际覆盖了 fetch / stage / import 三个子阶段，`IMPORTING` 容易产生歧义。

### 3.2 状态流转图

```
PENDING (phase=null)
   │
   ▼
PROCESSING (phase=FETCHING)   ── 从券商 API 拉取数据
   │
   ▼
PROCESSING (phase=STAGING)    ── 逐条写入 staged 表
   │
   ▼
PROCESSING (phase=IMPORTING)  ── 逐条从 staged 表导入 trade_records
   │
   ├── 全部成功 ──→ COMPLETED  (phase=null)   failedCount == 0
   │
   ├── 部分成功 ──→ PARTIAL    (phase=null)   importedCount > 0 && failedCount > 0
   │
   ├── 全部失败 ──→ FAILED     (phase=null)   请求/解析阶段失败 或 全部记录 failed
   │
   └── 应用崩溃 ──→ INTERRUPTED (phase=保留)   应用重启时检测到卡在 PROCESSING
                    │
                    └── 用户触发 resume ──→ PROCESSING (phase=FETCHING) → ...正常流转
```

### 3.3 最终状态判定规则

| 最终 status | 判定条件 | 用户含义 |
|------------|---------|---------|
| `COMPLETED` | `failedCount == 0` | 全部成功导入，无需操作 |
| `PARTIAL` | `importedCount > 0 && failedCount > 0` | 部分成功，需关注失败记录 |
| `FAILED` | 请求/解析阶段整体失败；或所有记录都 failed | 整体失败，查看 error_message |

### 3.4 数据库变更

`broker_sync_batches` 表新增 `phase` 字段：

```sql
ALTER TABLE broker_sync_batches ADD COLUMN phase VARCHAR(32);
```

batch 状态注释更新（原有 4 种 → 6 种）：

| 旧状态 | 新状态 | 说明 |
|--------|--------|------|
| `PENDING` | `PENDING` | 不变 |
| `IMPORTING` | `PROCESSING` | 改名，含义扩展为覆盖 fetch/stage/import 全流程 |
| `COMPLETED` | `COMPLETED` | 不变 |
| _(无)_ | `PARTIAL` | 新增，部分成功 |
| `FAILED` | `FAILED` | 不变 |
| _(无)_ | `INTERRUPTED` | 新增，应用中断后恢复标记 |

---

## 四、事务与幂等性设计

### 4.1 Staging 阶段

每条 staged order 独立事务写入：

```
stageOrders(batchId, orders):
  for each order:
    @Transactional(REQUIRES_NEW):
      if stagedOrderRepo.existsByOrderId(order.getOrderId()):
        skip  // 幂等：已存在则跳过
      else:
        save(mapToEntity(batchId, order))
```

**幂等保证**：`order_id` UNIQUE 约束。重复写入时：
- 优先通过 `existsByOrderId()` 检查跳过（避免依赖异常控制流）
- UNIQUE 约束作为兜底防线

**中断后恢复**：对同一 batch 重新调用 staging，已有的跳过，未写入的补上。

### 4.2 Import 阶段

每条 staged order 独立事务导入：

```
importStagedOrders(batchId):
  pendingOrders = stagedOrderRepo.findByBatchIdAndStatus(batchId, "PENDING")
  for each staged in pendingOrders:
    @Transactional(REQUIRES_NEW):
      try:
        // 去重检查
        if tradeRecordRepo.existsByExternalBrokerAndExternalId("ibkr", staged.orderId):
          staged.status = "SKIPPED"
          staged.errorMessage = "Duplicate: already exists in trade_records"
        else:
          // 映射 + 保存
          tradeRecord = mapToTradeRecord(staged)
          saved = tradeRecordRepo.save(tradeRecord)
          staged.status = "IMPORTED"
          staged.importedTradeId = saved.id
      catch Exception:
        staged.status = "FAILED"
        staged.errorMessage = e.message
      stagedOrderRepo.save(staged)
```

**幂等保证**（双重）：
1. 只查询 `status = PENDING` 的记录 → 已处理的不会再出现
2. `(external_broker, external_id)` 唯一索引 → 即使状态判断出问题，也不会重复插入

**中断后恢复**：重新查询 `WHERE batch_id = ? AND status = 'PENDING'`，已处理的不会再出现。

### 4.3 为什么不用一个大事务？

| 考虑因素 | 大事务 | 逐条独立事务 |
|---------|--------|------------|
| 连接占用 | 长时间 hold 连接 | 每条快进快出 |
| 一条失败的影响 | 全部回滚 | 仅该条标记 FAILED，其他继续 |
| 断点续传 | 不支持，必须从头来 | 天然支持（PENDING 过滤） |
| 中断恢复代价 | 前功尽弃 | 只需处理剩余 PENDING |
| 进度可见性 | 全部完成前看不到进度 | 实时可见每条处理结果 |

### 4.4 计数统计

Import 阶段完成后，从 staged 表统计实际结果（不依赖内存累加器）：

```java
int importedCount = stagedOrderRepo.countByBatchIdAndStatus(batchId, "IMPORTED");
int skippedCount  = stagedOrderRepo.countByBatchIdAndStatus(batchId, "SKIPPED");
int failedCount   = stagedOrderRepo.countByBatchIdAndStatus(batchId, "FAILED");
int totalCount    = importedCount + skippedCount + failedCount;
```

**好处**：即使中断恢复后重新统计，也能拿到准确数字。

---

## 五、失败分类与处理策略

### 5.1 失败类型全览

| # | 阶段 | 失败原因 | 已写入的数据 | batch 最终状态 | 重试价值 |
|---|------|---------|-------------|--------------|---------|
| A | Fetch | HTTP 超时 / 认证失败 / 网络错误 | 无 | `FAILED` | 网络问题有价值；认证问题无价值 |
| B | Parse | XML 格式异常 / 解析器 bug | 无 | `FAILED` | 一般无价值（数据/代码有问题）|
| C | Staging 中断 | 应用崩溃 / OOM / DB 连接断 | 部分 staged records | → `INTERRUPTED` (phase=STAGING) | **有价值（resume 完整重跑）** |
| D | Import 中断 | 同上 | 部分 staged→IMPORTED，部分 PENDING | → `INTERRUPTED` (phase=IMPORTING) | **有价值（resume 完整重跑）** |
| E | 单条映射失败 | 字段值不合法 / 类型转换出错 | 该条→FAILED，其他正常 | `PARTIAL` | 无价值（数据本身有问题）|
| F | 全部映射失败 | 所有记录都转换出错 | 全部→FAILED | `FAILED` | 一般无价值（代码有问题）|
| G | 全部成功 | — | 全部→IMPORTED/SKIPPED | `COMPLETED` | 不需要 |

### 5.2 各类型详细分析

#### 类型 A/B：请求/解析阶段失败

- 还没有写入任何 staged 数据
- batch 直接标记为 `FAILED`，`errorMessage` 记录原因
- 跟之前 Phase 1 的失败处理完全一致，不涉及 staging/import 逻辑
- 用户可以重新触发一次 sync（创建新 batch）

#### 类型 C：Staging 中断

- **核心问题**：staged 表里只有写进去的部分，缺失的部分无从得知。原始数据在内存中，应用崩溃后丢失
- **不能只 import 已有 staged records**：这会导致静默丢数据，对金融应用不可接受
- **处理方式**：resume 时完整重跑（fetch → parse → staging → import），staging 阶段的幂等性保证已有记录不会重复

#### 类型 D：Import 中断

- staged 表部分记录已 IMPORTED，部分还是 PENDING
- **同样走完整重跑**：因为无法确认 staging 是否完整（万一 staging 看起来完了但最后几条没写进去？）
- 安全起见统一走 fetch → staging → import，代价只是多一次 API 调用和一些 UNIQUE 冲突跳过

#### 类型 E：单条映射失败

- 该条被标记为 FAILED + errorMessage，其他条正常继续
- batch 最终标记为 `PARTIAL`（如果有成功的）或 `FAILED`（如果全部失败）
- 重试一般无意义——数据本身有问题，重试 100 次结果一样
- 需要人工查看 error_message 处理

---

## 六、中断检测与恢复机制

### 6.1 应用启动扫描

应用启动时，扫描所有卡在 `PROCESSING` 状态的 batch，标记为 `INTERRUPTED`：

```java
@Component
public class SyncBatchRecoveryRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        List<BrokerSyncBatch> stuckBatches =
            batchRepo.findByStatus("PROCESSING");

        for (BrokerSyncBatch batch : stuckBatches) {
            batch.setStatus("INTERRUPTED");
            batch.setErrorMessage("Interrupted: application restarted during sync");
            // phase 保留原值，用于诊断中断发生在哪个阶段
            batchRepo.save(batch);
            log.warn("Batch {} marked as INTERRUPTED (was PROCESSING, phase={})",
                     batch.getId(), batch.getPhase());
        }
    }
}
```

> **为什么不自动恢复？** 应用重启时自动触发 API 调用不太合适——可能是计划内的重启，也可能是配置变更后的重启。让用户主动决定是否 resume 更安全。

### 6.2 Resume API

```
POST /api/broker-sync/batches/{id}/resume
```

#### 前置校验

- batch `status` 必须是 `INTERRUPTED`，其他状态一律拒绝
- batch 的 `brokerCode` 对应的适配器必须存在且已配置

#### 执行流程

```
resumeSync(batchId):
  1. 验证 batch.status == INTERRUPTED
  2. 从 batch 记录恢复请求参数：
     - brokerCode  → batch.brokerCode
     - startTime   → batch.syncDateFrom
     - endTime     → batch.syncDateTo
  3. markAsProcessing(batchId, phase=FETCHING)
  4. 完整执行：fetch → parse → staging(幂等补全) → import(从 PENDING 续传)
  5. 统计 counts，更新 batch 最终状态
```

#### 为什么是完整重跑？

| 阶段 | 已有数据的处理 | 幂等保证 |
|------|-------------|---------|
| Fetch + Parse | 重新从 API 拿完整数据 | IBKR Flex Query 查历史成交记录，结果稳定（至少是超集） |
| Staging | 已有的跳过，缺失的补上 | `order_id` UNIQUE 约束 |
| Import | 已 IMPORTED 的不会被选中 | `status = PENDING` 过滤 + `(external_broker, external_id)` 唯一索引 |

完整重跑的代价：多一次 API 调用 + 一些 UNIQUE 冲突跳过。不会产生错误数据。

#### Resume 和重新 Sync 的区别

| | Resume | 重新 Sync |
|--|--------|----------|
| Batch | 复用原 batch（INTERRUPTED → PROCESSING） | 创建新 batch |
| 请求参数 | 从 batch 记录恢复，**用户无需重新填** | 用户重新填参数 |
| Staged 数据 | 复用 + 补全（同一 batch_id） | 全新写入（新 batch_id） |
| 旧残留数据 | 被整合进同一 batch | 旧 batch 的 staged 数据成孤儿 |
| 历史追溯 | 干净：一个 batch = 一次完整 sync | 两个 batch 记录，旧的是残缺的 |

### 6.3 隐含假设

**IBKR API 的幂等性**：对同样的日期范围重新 fetch，IBKR 返回的数据应该是一致的（至少是超集）。

这个假设对 IBKR Flex Query 成立——它查的是历史成交记录，不会因为多查一次就变了。最多是两次查询之间有新的成交产生（超集），不会丢失已有记录。

---

## 七、完整执行流程

### 7.1 正常流程

```
triggerSync(request):
  → createBatch(PENDING, phase=null)
  → asyncExecutor.execute(batchId, request):
      → markAsProcessing(batchId, phase=FETCHING)
      → adapter.fetch(request)           // 从券商 API 拉数据
      → adapter.parse(response)          // 解析为内存模型

      → updatePhase(batchId, STAGING)
      → stagingService.stage(batchId, orders)   // 逐条写入 staged 表

      → updatePhase(batchId, IMPORTING)
      → importService.import(batchId)           // 逐条 PENDING→IMPORTED/SKIPPED/FAILED

      → 从 DB 统计 counts
      → markAsCompleted / markAsPartial / markAsFailed(batchId, counts)
```

### 7.2 恢复流程

```
resumeSync(batchId):
  → 验证 status == INTERRUPTED
  → 从 batch 恢复 SyncRequest(brokerCode, syncDateFrom, syncDateTo)
  → asyncExecutor.executeResume(batchId, request):
      → markAsProcessing(batchId, phase=FETCHING)
      → 同正常流程（fetch → parse → staging → import → 统计 → 更新状态）
```

---

## 八、失败场景全覆盖表

| # | 场景 | batch 最终状态 | staged 状态 | Resume 支持 | 用户操作 |
|---|------|--------------|------------|-------------|---------|
| 1 | HTTP 请求失败 | `FAILED` | 无数据 | ❌ 不支持（重新 sync） | 检查网络/配置，重新触发 sync |
| 2 | XML 解析失败 | `FAILED` | 无数据 | ❌ 不支持 | 排查数据/代码问题 |
| 3 | Staging 中断 | → `INTERRUPTED` (phase=STAGING) | 部分 PENDING | ✅ 完整重跑 | 点击 Resume |
| 4 | Import 中断 | → `INTERRUPTED` (phase=IMPORTING) | 部分 IMPORTED/PENDING | ✅ 完整重跑 | 点击 Resume |
| 5 | 单条映射失败 | `PARTIAL` | 该条 FAILED，其他 IMPORTED | ❌（数据问题，重试无意义） | 查看 error_message，手动处理 |
| 6 | 全部映射失败 | `FAILED` | 全部 FAILED | ❌（代码/数据问题） | 代码修复后重新 sync |
| 7 | 全部成功 | `COMPLETED` | 全部 IMPORTED/SKIPPED | 不需要 | 无需操作 |

---

## 九、需要变更的地方汇总

### 9.1 数据库变更

| 变更 | 说明 | Flyway 脚本 |
|------|------|------------|
| `broker_sync_batches` 新增 `phase` 列 | `VARCHAR(32)`，可空 | V24 |
| `broker_sync_batches.status` 枚举扩展 | 新增 `PROCESSING`、`PARTIAL`、`INTERRUPTED`；`IMPORTING` 改为 `PROCESSING` | 注释更新，无 DDL 变更（VARCHAR 字段） |

> **注意**：如果已有数据中存在 `status = 'IMPORTING'` 的记录，需要在 Flyway 脚本中做数据迁移：`UPDATE broker_sync_batches SET status = 'PROCESSING' WHERE status = 'IMPORTING'`

### 9.2 后端代码变更

| 组件 | 变更类型 | 说明 |
|------|---------|------|
| `BrokerSyncBatch` entity | 修改 | 新增 `phase` 字段；状态注释更新 |
| `BrokerSyncBatchRepository` | 修改 | 新增 `findByStatus(String status)` 查询 |
| `BrokerSyncBatchService` | 修改 | 新增 `markAsProcessing(id, phase)`、`markAsPartial(id, result)`、`markAsInterrupted(id, message)` 方法；`markAsImporting` 改为 `markAsProcessing` |
| `SyncBatchRecoveryRunner` | **新建** | `ApplicationRunner`，启动时扫描 PROCESSING → INTERRUPTED |
| `IbkrStagingService` | **新建** | 逐条幂等写入 staged 表 |
| `IbkrImportService` | **新建** | 逐条从 staged 表导入 trade_records |
| `BrokerSyncController` | 修改 | 新增 `POST /batches/{id}/resume` 端点 |
| `BrokerSyncAsyncExecutor` | 修改 | 适配新的 status/phase 流转；支持 resume 模式 |

### 9.3 前端变更（后续任务）

| 组件 | 说明 |
|------|------|
| Batch 列表状态展示 | 支持 `PROCESSING`（含 phase 展示）、`PARTIAL`、`INTERRUPTED` 状态 |
| Resume 按钮 | `INTERRUPTED` 状态的 batch 显示「恢复」按钮 |


