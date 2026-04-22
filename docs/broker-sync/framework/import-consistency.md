# 券商同步 — 数据一致性与失败清理设计文档

> **创建日期**：2026-04-16（v1）
> **最后更新**：2026-04-22（v2 — 全面重构：移除 INTERRUPTED / PARTIAL / Resume，改为"失败即清理"模型）
> **状态**：📋 设计中（等待实施）
> **关联**：[architecture.md](../architecture.md) | [data-persistence.md](./data-persistence.md) | [broker-registration.md](./broker-registration.md)
> **取代**：本文档是 v1（2026-04-16）的完全重写。v1 设计的 `INTERRUPTED` / `PARTIAL` / Resume / 幂等续跑机制被整体废弃，历史版本可在 git log 中追溯。

---

## 一、背景与目标

### 1.1 v1 设计的问题

v1（"两级状态 + 幂等 + Resume"）虽然理论上优雅，但在实际使用中暴露出**复杂度过高、收益有限**的问题：

1. **状态机过于复杂**：6 种 status × 4 种 phase 的组合难以穷举测试，前端展示逻辑、过滤器、按钮可用性判断充满边界条件
2. **`PARTIAL` 语义模糊**：用户看到 "部分成功" 的第一反应是"所以呢？我该怎么办？"—— 实际上单条映射失败几乎都是代码 bug 或脏数据，"重试"并无价值
3. **Resume 的价值有限**：
   - 对 IBKR：Flex Query 重新拉取代价低，用户直接点"同步"按钮和点"恢复"按钮效果一致
   - 对 Tiger：两阶段分页拉取的"第一阶段 staged 数据"复用价值更低，实操中几乎总是重新拉
4. **残留数据成孤儿**：失败/中断批次的 staged 记录长期留在表里占用空间，且对后续新 batch 不产生任何价值
5. **INTERRUPTED → Resume 流程对用户不友好**：用户必须记住"INTERRUPTED 要点恢复，不能点触发"，增加心智负担

### 1.2 v2 的核心思路

**"失败即清理" —— 任何没有完整成功的 sync，都回滚到"从未发生过"的状态**。

核心原则：

1. **结果二元化**：sync 要么 `COMPLETED`（全部成功），要么 `FAILED`（失败 + 已清理）。没有中间态
2. **失败即清理**：失败的 batch 自动删除其写入的所有数据（staged 表 + `trade_records`），释放资源
3. **不保留复用价值**：没有 resume，没有"幂等续跑"。重试 = 用户重新触发一次 sync
4. **金融数据不妥协**：清理必须原子（事务保证）。清理本身失败时，系统进入**保护性阻塞状态**，拒绝后续 sync 直到人工介入
5. **同时只跑一个 sync**：DB 级唯一约束保证不会有并发 sync 任务，避免复杂的并发一致性问题

---

## 二、关键设计决策

| # | 决策项 | 结论 | 理由 |
|---|--------|------|------|
| 1 | 最终状态 | **三态：`COMPLETED` / `FAILED` / `CLEANUP_FAILED`**（活跃态另有 `PENDING` / `PROCESSING`） | 取代 v1 的六态，语义简化 |
| 2 | 失败处理 | **失败 → 自动清理 → 标记 FAILED**（不保留残留） | 用户任何时候看到 FAILED 都表示"这次没留下任何痕迹" |
| 3 | 清理原子性 | 用**单个事务包裹所有 DELETE**，任何一张表删不掉就整体回滚 | 不能留半清理状态 |
| 4 | 清理失败重试 | **重试 3 次**（含原始尝试 = 最多 3 次事务执行），仍失败则进入 `CLEANUP_FAILED` | 容忍偶发 DB 抖动，又不无限阻塞 |
| 5 | CLEANUP_FAILED 恢复 | **人工介入**：DBA 手动清理残留后，手动更新 status 为 FAILED | 小项目不做自动恢复机制 |
| 6 | 并发控制 | **DB 级唯一约束**：同时只能有 1 个活跃 batch（PENDING / PROCESSING / CLEANUP_FAILED）| 应用层 check-then-insert 有 TOCTOU race；DB 约束绝对安全 |
| 7 | 启动检测 | 扫 PENDING / PROCESSING → 走**完整清理流程** → 标记 FAILED / CLEANUP_FAILED | 和运行时失败处理统一 |
| 8 | Resume | **完全删除** | 价值不足以抵消复杂度 |
| 9 | PARTIAL | **完全删除** | 一刀切失败，让人工查原因 |
| 10 | INTERRUPTED | **完全删除** | 被"启动检测 → 清理"取代 |
| 11 | `phase` 字段 | **保留** | 仍用于 PROCESSING 时的进度展示（FETCHING/STAGING/IMPORTING），以及 CLEANUP_FAILED 时的诊断 |
| 12 | `failedCount` 字段 | **删除** | 新模型下没有部分失败概念 |
| 13 | 历史数据迁移 | **不迁移**（目前无生产数据） | 清库重建 Flyway 即可 |

---

## 三、状态机

### 3.1 状态定义

| status | 含义 | phase 是否有意义 | 用户可见操作 |
|--------|------|-----------------|-------------|
| `PENDING` | 已创建，等待 async 线程拾取 | 否（null） | 仅展示，等待自动流转 |
| `PROCESSING` | 执行中 | **是**（FETCHING / STAGING / IMPORTING） | 仅展示进度，禁止其他操作 |
| `COMPLETED` | 全部成功 | 否（null） | 查看详情 |
| `FAILED` | 失败且清理成功（数据已回滚） | 否（null） | 查看错误原因 `error_message`，可重新触发一次 sync |
| `CLEANUP_FAILED` | 失败且清理本身也失败（**数据可能有残留**） | 保留清理发起时的 phase 用于诊断 | 阻塞所有新 sync，需人工介入 |

`CLEANUP_FAILED` 是本次设计新增的保护性状态。它的存在目的就是让系统"**宁可阻塞也不允许错误累积**"。

### 3.2 活跃态定义（并发控制用）

| 是否"活跃" | 状态 |
|----------|------|
| ✅ 活跃（阻塞新 sync） | `PENDING`, `PROCESSING`, `CLEANUP_FAILED` |
| ❌ 非活跃（不阻塞） | `COMPLETED`, `FAILED` |

`CLEANUP_FAILED` 列为活跃态，目的是通过"阻塞新 sync"倒逼人工介入。

### 3.3 状态流转图

```
             ┌─────────────────────────────────────────────────┐
             │                                                 │
             │                                                 ▼
        ┌─────────┐     ┌────────────┐     ┌─────────────────────────┐
        │ PENDING ├────▶│ PROCESSING │────▶│ 成功                     │
        └─────────┘     │            │     │ → COMPLETED (phase=null)│
             ▲          │ FETCHING   │     └─────────────────────────┘
             │          │ STAGING    │
             │          │ IMPORTING  │     ┌─────────────────────────┐
     (create │          │            ├────▶│ 失败 → 清理                │
      batch) │          │            │     │ ├─ 清理 OK               │
             │          └────────────┘     │ │   → FAILED            │
             │                             │ └─ 清理 3 次仍失败         │
        ┌────┴────┐                        │     → CLEANUP_FAILED    │
        │ 用户触发  │                        │       (phase=保留)       │
        │ 或启动   │                        └─────────────────────────┘
        │ 检测    │                                     │
        └─────────┘                                     │
                                         ┌──────────────┘
                                         ▼
                                   ❗ DBA 人工介入
                                   手动清理残留 + 手动 UPDATE status=FAILED
```

### 3.4 应用启动时的状态处理

`SyncBatchRecoveryRunner` 在应用启动时执行：

```
for batch in findByStatusIn([PENDING, PROCESSING]):
  // 说明 JVM 上次异常退出前这些 batch 未完成
  cleanupAndMarkFailed(batch.id,
      "Interrupted: application restarted during " + batch.phase)
```

**注意**：启动时遇到 `CLEANUP_FAILED` 的 batch **不做任何处理**，保持其现状 —— 它本来就需要人工介入。

---

## 四、并发控制：DB 级唯一约束

### 4.1 问题

我们希望"同时只能有一个活跃 sync batch"。纯应用层的 `SELECT COUNT → INSERT` 方案有 TOCTOU race（两个请求同时通过校验，各自 INSERT 一条）。必须用 DB 层保证。

### 4.2 方案：虚拟列 + 唯一索引

```sql
ALTER TABLE broker_sync_batches
  ADD COLUMN active_flag TINYINT
  GENERATED ALWAYS AS (
    CASE WHEN status IN ('PENDING', 'PROCESSING', 'CLEANUP_FAILED') THEN 1
         ELSE NULL
    END
  ) VIRTUAL;

ALTER TABLE broker_sync_batches
  ADD UNIQUE KEY uk_only_one_active (active_flag);
```

原理：
- `active_flag` 是 `VIRTUAL` 列（不占存储，读时计算），值取决于 `status`
- MySQL 的唯一索引**不对 NULL 去重**：无数条 NULL 可以共存
- 活跃状态的记录 `active_flag = 1`，非活跃记录为 NULL
- 因此：**最多存在 1 条记录 active_flag = 1**，天然保证"至多一个活跃 batch"

### 4.3 应用层配合

```java
// BrokerSyncBatchService.createBatch()
@Transactional
public BrokerSyncBatch createBatch(String brokerCode, LocalDate from, LocalDate to) {
    BrokerSyncBatch batch = buildNewBatch(brokerCode, from, to);  // status=PENDING
    try {
        return batchRepository.save(batch);
    } catch (DataIntegrityViolationException e) {
        if (isActiveBatchConstraintViolation(e)) {
            throw new SyncConflictException(
                "Another sync task is running or blocked. Please wait for it to "
                + "complete, or resolve the CLEANUP_FAILED batch manually.");
        }
        throw e;
    }
}
```

Controller 捕获 `SyncConflictException` → 返回 **HTTP 409 Conflict**。

### 4.4 保护性阻塞的含义

当存在 `CLEANUP_FAILED` batch 时：
- 新的 `triggerSync` 请求会被 DB 唯一约束拒绝 → 409
- 前端展示错误信息："上一次同步清理失败，请人工处理 batch #X 后再试"
- 只有 DBA 人工把 `CLEANUP_FAILED` batch 的 status 改为 `FAILED`（或删除该 batch 记录），系统才能接受新 sync

这是**故意为之**的保护机制：CLEANUP_FAILED 意味着 staged 表或 trade_records 可能有孤儿数据，继续跑新 sync 可能放大问题。

---

## 五、清理机制

### 5.1 清理范围

清理必须覆盖**本次 sync 写入的所有表**。以 `batch_id` / `sync_batch_id` 为锚点定位：

| 表名 | 锚点字段 | 所有 broker 通用？ |
|------|---------|------------------|
| `ibkr_staged_orders` | `batch_id` | 仅 IBKR 写入 |
| `ibkr_staged_trade_confirms` | `batch_id` | 仅 IBKR 写入 |
| `tiger_staged_orders` | `batch_id` | 仅 Tiger 写入 |
| `trade_records` | `sync_batch_id` | ✅ 所有 broker 共享 |

**未来新 broker 加入时的约束**：任何引入的新 staged 表必须带 `batch_id` 字段并在 `SyncBatchCleanupService` 中注册，否则清理会漏数据。

### 5.2 不清理的表

以下表**不在清理范围**（它们不是 sync 直接写入的）：

- `dividend_records` —— sync 流程不写
- 持仓快照 / 成本重算缓存 —— 由 `trade_records` 派生，删 `trade_records` 时这些会被下次重算自动校正
- `market_event_*` —— 独立业务表，不在 sync 范围

### 5.3 清理事务设计

**单个 `@Transactional` 包所有 DELETE**：

```java
@Service
public class SyncBatchCleanupService {

    @Transactional
    public void cleanupBatchData(Long batchId, String brokerCode) {
        // Broker-specific staged tables
        switch (brokerCode) {
            case "ibkr":
                ibkrStagedOrderRepo.deleteByBatchId(batchId);
                ibkrStagedTradeConfirmRepo.deleteByBatchId(batchId);
                break;
            case "tiger":
                tigerStagedOrderRepo.deleteByBatchId(batchId);
                break;
            default:
                throw new IllegalStateException("Unknown broker: " + brokerCode);
        }
        // Common table (all brokers)
        tradeRecordRepo.deleteBySyncBatchId(batchId);
    }
}
```

事务内任意一个 DELETE 失败 → Spring 自动回滚整个事务 → 保证"要么全删，要么全不删"。

**注意**：清理事务**不包含** batch 本身的 status 更新。status 更新在独立的下一步做（见 5.4）。

### 5.4 带重试的清理入口

```java
@Service
public class SyncBatchFailureHandler {

    private static final int MAX_CLEANUP_ATTEMPTS = 3;

    /**
     * 失败处理统一入口：清理数据 + 标记 FAILED（或 CLEANUP_FAILED）
     * 清理成功 → status = FAILED + errorMessage = 原因
     * 清理失败 3 次 → status = CLEANUP_FAILED + errorMessage = 清理失败原因 + 原始失败原因 (保留 phase)
     */
    public void handleFailure(Long batchId, String brokerCode, String originalError) {
        for (int attempt = 1; attempt <= MAX_CLEANUP_ATTEMPTS; attempt++) {
            try {
                cleanupService.cleanupBatchData(batchId, brokerCode);
                batchService.markAsFailed(batchId, originalError);  // clears phase
                log.info("Batch {} cleaned up and marked FAILED (attempt {})", batchId, attempt);
                return;
            } catch (Exception e) {
                log.error("Cleanup attempt {}/{} failed for batch {}: {}",
                        attempt, MAX_CLEANUP_ATTEMPTS, batchId, e.getMessage(), e);
                if (attempt == MAX_CLEANUP_ATTEMPTS) {
                    // 最后一次仍失败 → CLEANUP_FAILED
                    batchService.markAsCleanupFailed(batchId,
                            String.format("Cleanup failed after %d attempts: %s. Original error: %s",
                                    MAX_CLEANUP_ATTEMPTS, e.getMessage(), originalError));
                    return;
                }
                // otherwise retry after small backoff (optional)
            }
        }
    }
}
```

`markAsCleanupFailed` **保留** `phase` 值用于诊断（和 v1 的 INTERRUPTED 处理一样）；`markAsFailed` 清空 `phase`。

### 5.5 清理触发时机

所有以下场景都调用 `SyncBatchFailureHandler.handleFailure`：

1. **Fetch / Parse 阶段异常**：`BrokerSyncAsyncExecutor` catch 后调用
2. **Staging / Import 阶段异常**：同上
3. **单条映射失败导致整批失败**：v2 新模型下，单条失败不再变成 PARTIAL，而是**整批判定为失败**触发清理（见 5.6）
4. **应用重启后的启动检测**：`SyncBatchRecoveryRunner` 扫到 PENDING/PROCESSING 时调用

### 5.6 "单条失败整批失败" 的具体判定

v1 中 import 阶段每条独立事务，失败时只标记该条 FAILED，其他继续。v2 保留这个事务模型（性能必需），但最终判定时：

```
import 阶段结束后：
  importedCount = countByStatus(IMPORTED)
  skippedCount  = countByStatus(SKIPPED)
  failedCount   = countByStatus(FAILED)
  totalCount    = imported + skipped + failed

  if failedCount == 0:
      → markAsCompleted
  else:
      → handleFailure("N records failed to import, see staged records for details")
      // 触发完整清理，包括那些已成功 IMPORTED 的 trade_records
```

**代价**：已成功 import 的记录会被清理（重拉时重新 import）。但这是"全有或全无"原则的必然结果，用户会从 UI 上得到清晰的信号（FAILED 就是整体失败）。

**诊断仍然可用**：staged 表在清理**之前**已经包含每条的 status 和 errorMessage；但清理会把 staged 记录一并删掉 —— 为了让用户能查原因，**error_message 字段要包含具体失败记录的汇总信息**（例如前 3 条的错误摘要）。清理完成后用户仍能从 batch 的 `error_message` 看到失败概况。

---

## 六、数据库变更（Flyway V28）

### 6.1 变更清单

单个 Flyway 脚本 `V28__simplify_sync_batch_state_model.sql`：

```sql
-- 1. 删除 failedCount 列（v2 新模型不再有部分失败概念）
ALTER TABLE broker_sync_batches
  DROP COLUMN failed_count;

-- 2. 更新 status 注释（语义变化）
ALTER TABLE broker_sync_batches
  MODIFY COLUMN status VARCHAR(32) NOT NULL
  COMMENT '批次主状态: PENDING, PROCESSING, COMPLETED, FAILED, CLEANUP_FAILED';

-- 3. 更新 phase 注释（语义微调）
ALTER TABLE broker_sync_batches
  MODIFY COLUMN phase VARCHAR(32)
  COMMENT '子阶段: FETCHING, STAGING, IMPORTING. PROCESSING 时表示当前进度; CLEANUP_FAILED 时保留发起清理时的阶段用于诊断; 其他状态为 NULL.';

-- 4. 新增 active_flag 虚拟列（并发控制）
ALTER TABLE broker_sync_batches
  ADD COLUMN active_flag TINYINT
  GENERATED ALWAYS AS (
    CASE WHEN status IN ('PENDING', 'PROCESSING', 'CLEANUP_FAILED') THEN 1
         ELSE NULL
    END
  ) VIRTUAL
  COMMENT 'Virtual: 1 when batch is active (blocks new sync), NULL otherwise. Used by uk_only_one_active.';

-- 5. 新增唯一索引（保证至多一个活跃 batch）
ALTER TABLE broker_sync_batches
  ADD UNIQUE KEY uk_only_one_active (active_flag);
```

### 6.2 数据迁移说明

**不迁移**。目前无生产数据，Flyway 执行前库里不会有 INTERRUPTED / PARTIAL 记录。如果未来发现有历史数据，可以在脚本首部加：

```sql
-- (not needed now, placeholder for future)
-- DELETE FROM broker_sync_batches WHERE status IN ('INTERRUPTED', 'PARTIAL');
```

开发环境已有测试数据的话，推荐清库重来（`ibkr_staged_*`, `tiger_staged_*`, `trade_records`, `broker_sync_batches` 全清），避免遗留 `failed_count` 非零、`PARTIAL` / `INTERRUPTED` 老状态干扰。

---

## 七、后端代码变更

### 7.1 新增

| 组件 | 说明 |
|------|------|
| `SyncBatchCleanupService` | 单个 `@Transactional` 方法，按 brokerCode dispatch 清理 staged + trade_records |
| `SyncBatchFailureHandler` | 清理重试 3 次 + 状态标记统一入口 |
| `SyncConflictException` | RuntimeException，表示活跃 batch 约束冲突，Controller 捕获返回 409 |
| `V28__simplify_sync_batch_state_model.sql` | 见第六章 |

### 7.2 修改

| 组件 | 变更 |
|------|------|
| `BrokerSyncBatch` entity | 删除 `failedCount` 字段；更新 `status` / `phase` javadoc |
| `BrokerSyncBatchService.createBatch` | catch `DataIntegrityViolationException` → 转换为 `SyncConflictException` |
| `BrokerSyncBatchService` | **新增** `markAsCleanupFailed(batchId, message)`（保留 phase）|
| `BrokerSyncBatchService` | **删除** `markAsPartial`、`markAsInterrupted` 方法 |
| `BrokerSyncBatchService.markAsCompleted` | 去掉 `setFailedCount` 调用 |
| `BrokerSyncBatchService.markAsFailed` | 保持签名，但现在只代表"清理成功后的最终 FAILED" |
| `SyncResult` | 删除 `failedCount` 字段及 setter/getter；`success()` 重载去掉 `failedCount` 参数；toString 同步更新 |
| `BrokerSyncAsyncExecutor.execute` | 删除 `markAsPartial` 分支；失败分支（包括 result.failedCount>0 和 catch）统一调 `SyncBatchFailureHandler.handleFailure` |
| `BrokerSyncController` | 删除 `resumeSync` 端点和 `RESUMABLE_STATUSES` 常量 |
| `BrokerSyncController.triggerSync` | catch `SyncConflictException` → 返回 409 |
| `SyncBatchRecoveryRunner` | 行为改为：扫 PENDING/PROCESSING → 调 `SyncBatchFailureHandler.handleFailure`；CLEANUP_FAILED 的 batch 跳过（不做任何处理） |
| `BrokerSyncBatchRepository` | 删除与 INTERRUPTED/PARTIAL 相关的查询（如有）|
| `IbkrStagedOrderRepository` / `IbkrStagedTradeConfirmRepository` / `TigerStagedOrderRepository` | **新增** `deleteByBatchId(Long)` 方法（如尚未存在）|
| `TradeRecordRepository` | **新增** `deleteBySyncBatchId(Long)` 方法 |
| 所有 Import/Staging Service 里的 import 阶段统计代码 | 保持逐条事务模型，但最终 `result.getFailedCount() > 0` 触发 failure handler，不再走 PARTIAL |

### 7.3 删除

| 组件 | 处理 |
|------|------|
| `BrokerSyncController.resumeSync` 方法 | 删除 |
| `BrokerSyncController.RESUMABLE_STATUSES` | 删除 |
| `BrokerSyncBatchService.markAsPartial` / `markAsInterrupted` | 删除 |
| `SyncResult` 的 `failedCount` 字段 | 删除（影响 6 处代码：field/getter/setter/两个 success 重载/toString）|
| 任何对 `INTERRUPTED` / `PARTIAL` 状态字符串的引用 | 全库 grep 清除 |

---

## 八、前端代码变更

### 8.1 删除

- "恢复" 按钮（`RedoOutlined`）及其 onClick 处理
- "按 INTERRUPTED 过滤" / "按 PARTIAL 过滤" 的状态过滤器选项
- `resumableStatuses` 相关判断逻辑
- 调 `/api/broker-sync/batches/{id}/resume` 的 API 函数（如 `resumeBatch`）

### 8.2 修改

- 状态徽章（Badge/Tag）的 color mapping：
  - `PENDING` → default
  - `PROCESSING` → processing（蓝色，spinning）
  - `COMPLETED` → success（绿色）
  - `FAILED` → error（红色）
  - `CLEANUP_FAILED` → **error 红色 + 特殊图标**，tooltip 说明"本次同步清理失败，请人工处理该 batch 后再触发新同步"
- 状态过滤器选项同步更新为上述 5 种
- 列表不再显示 `failedCount` 列（如果之前有）
- `triggerSync` 的错误处理：识别 HTTP 409 → 展示友好提示"已有同步任务在运行或需要人工处理，请稍后再试"

### 8.3 保留

- 进度展示（`phase` 字段在 PROCESSING 时显示 "获取中 / 暂存中 / 导入中"）

---

## 九、失败场景全覆盖表（v2）

| # | 场景 | 期间写入数据 | 清理是否成功 | batch 最终状态 | 用户操作 |
|---|------|-------------|------------|--------------|---------|
| 1 | Fetch 阶段异常（HTTP 超时/认证失败） | 无 | 无需清理（立即走 FAILED） | `FAILED` | 查 error，修复后重新触发 sync |
| 2 | Parse 阶段异常 | 无 | 无需清理 | `FAILED` | 查 error 排查数据/代码 |
| 3 | Staging 中途应用重启 | 部分 staged 记录 | 启动检测 → 清理成功 | `FAILED` | 重新触发 sync |
| 4 | Staging 中途应用重启 | 部分 staged 记录 | 启动检测 → 清理失败 | `CLEANUP_FAILED` | DBA 人工清理 |
| 5 | Import 中途应用重启 | 部分 staged + 部分 trade_records | 启动检测 → 清理成功 | `FAILED` | 重新触发 sync |
| 6 | Import 中途应用重启 | 部分 staged + 部分 trade_records | 启动检测 → 清理失败 | `CLEANUP_FAILED` | DBA 人工清理 |
| 7 | 单条映射失败（其他条正常） | 部分 staged + 部分 trade_records | 运行时清理成功 | `FAILED` | 查 error_message 中的失败摘要，修复数据/代码后重试 |
| 8 | 全部映射失败 | staged 全 FAILED | 运行时清理成功 | `FAILED` | 查原因，代码/数据修复后重试 |
| 9 | 运行时任何清理失败 | 数据可能部分残留 | 重试 3 次仍失败 | `CLEANUP_FAILED` | DBA 人工清理 |
| 10 | 全部成功 | 全部 IMPORTED/SKIPPED | 无需清理 | `COMPLETED` | 无需操作 |
| 11 | 用户在有活跃 batch 时触发新 sync | — | — | — | 收到 409，前端提示"请等待当前任务完成" |

---

## 十、CLEANUP_FAILED 的人工处理 SOP

当看到 CLEANUP_FAILED 状态时，DBA 需要：

1. **看 batch 的 `error_message`**：了解清理失败原因（通常是 DB 连接问题 / 锁等待 / 外键约束等）
2. **看 batch 的 `phase`**：判断清理发起时 batch 处于哪个阶段，据此推断可能有残留数据的表
3. **手动执行清理 SQL**（按 brokerCode 选择）：
   ```sql
   -- 假设 batchId = 123, brokerCode = 'ibkr'
   DELETE FROM trade_records WHERE sync_batch_id = 123;
   DELETE FROM ibkr_staged_trade_confirms WHERE batch_id = 123;
   DELETE FROM ibkr_staged_orders WHERE batch_id = 123;
   ```
4. **更新 batch 状态**：
   ```sql
   UPDATE broker_sync_batches
   SET status = 'FAILED', phase = NULL,
       error_message = CONCAT(error_message, ' | Manually cleaned by DBA at ', NOW())
   WHERE id = 123;
   ```
5. **验证 active_flag 已变 NULL**：`uk_only_one_active` 解锁，新 sync 可以触发

此 SOP 应该写进运维手册（本项目目前没有，可放到 `docs/operations/` 新建）。

---

## 十一、和 v1 的对照（给 reviewer 的快速指南）

| 方面 | v1 (2026-04-16) | v2 (2026-04-22) |
|------|----------------|----------------|
| 状态数量 | 6 种 | 5 种（少了 INTERRUPTED/PARTIAL，多了 CLEANUP_FAILED） |
| 失败批次数据 | 保留，等 resume 续跑 | 立即清理，无残留（CLEANUP_FAILED 除外） |
| Resume 机制 | 完整支持（幂等续跑） | 完全删除 |
| 部分成功 | `PARTIAL` 状态 | 整体判失败，触发清理 |
| 并发控制 | 未处理（隐含假设） | DB 虚拟列 + 唯一索引 |
| `failedCount` | 保留展示 | 完全删除 |
| 运维心智 | 复杂（3 种失败状态需要理解） | 简单（FAILED = 没留下任何东西） |
| JVM 崩溃恢复 | 标记 INTERRUPTED + 用户点 Resume | 启动自动清理 + 标记 FAILED |

**核心权衡**：用"多一次 API 调用（重拉）"换取"零运维心智 + 零残留数据 + 零并发隐患"。对小项目性价比极高。

---

## 十二、待实施 Todos（执行前会用 todo 工具重新梳理）

1. 写 `V28` Flyway 脚本
2. 改 entity / repository（删 failedCount，加 delete 方法）
3. 新增 `SyncBatchCleanupService` + `SyncBatchFailureHandler`
4. 改 `BrokerSyncBatchService`（删 markAsPartial/markAsInterrupted，加 markAsCleanupFailed，处理 conflict）
5. 改 `BrokerSyncAsyncExecutor`（接入 failure handler，删 PARTIAL 分支）
6. 改 `BrokerSyncController`（删 resume，处理 409）
7. 改 `SyncBatchRecoveryRunner`（改为清理逻辑）
8. 删除 `SyncResult.failedCount`
9. 改前端：删恢复按钮、删 INTERRUPTED/PARTIAL 过滤、加 CLEANUP_FAILED 展示、处理 409
10. 测试：新增 `SyncBatchCleanupServiceTest`、`SyncBatchFailureHandlerTest`；修改 `BrokerSyncBatchServiceTest`（去掉 PARTIAL/INTERRUPTED 用例）、`BrokerSyncControllerTest`（去掉 resume 用例，加 409 用例）、`SyncBatchRecoveryRunnerTest`（改为清理流程）
11. 更新关联文档：`data-persistence.md`、`architecture.md`、`broker-registration.md` 中任何提到 INTERRUPTED/PARTIAL/Resume 的地方 —— 按"零文档债"规则

---

## 十三、开放问题 / Review 待确认

✅ 所有 A-H 问题已在讨论中确认。
✅ 并发方案（虚拟列 + 唯一索引）已确认采用。
✅ 文档重写策略（直接 v2 覆盖，v1 从 git 追溯）已确认。

剩余小决策由 AI 在实施时自行决定（第五章 5.6 的 error_message 汇总格式、前端 409 错误文案具体措辞等），有歧义会在 PR review 时再讨论。
