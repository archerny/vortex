# 券商同步 — 数据一致性与失败清理设计文档

> **创建日期**：2026-04-16（v1）
> **最后更新**：2026-04-23（v2.4.4 — cleanup retry 加入固定 2s backoff：`SyncBatchFailureHandler` 在两次 cleanup 尝试之间 `Thread.sleep(2000)`，让瞬时 DB 抖动（连接池耗尽 / 短时锁冲突 / serialization failure）有机会自愈；中断时立即退出 retry 升级 `CLEANUP_FAILED`，保持优雅关停语义。§5.4 代码示例与 "Cleanup retry policy" 小节已同步更新；原 v2.4.3 P0 修复内容保持不变。）
> **状态**：✅ v2 状态模型已完整落地 + 架构加固 + P0 数据丢失链已修复 + cleanup retry 加入 backoff
> **关联**：[architecture.md](../architecture.md) | [data-persistence.md](./data-persistence.md) | [broker-registration.md](./broker-registration.md) | [sync-lifecycle.md](./sync-lifecycle.md) | [../fix-p0-data-loss-chain.md](../fix-p0-data-loss-chain.md)
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
| 4 | 清理失败重试 | **重试 3 次**（含原始尝试 = 最多 3 次事务执行），**两次尝试之间固定间隔 2 秒**；若仍失败则进入 `CLEANUP_FAILED` | 容忍偶发 DB 抖动（瞬时连接池耗尽 / 短时锁冲突 / Postgres serialization failure），又不无限阻塞 |
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

### 4.2 方案：部分唯一索引（PostgreSQL）

本项目使用 **PostgreSQL**，采用 PG 的**部分唯一索引**（Partial Unique Index）即可满足"最多一条活跃 batch"的约束，**无需引入虚拟列**：

```sql
CREATE UNIQUE INDEX uk_only_one_active
  ON broker_sync_batches ((1))
  WHERE status IN ('PENDING', 'PROCESSING', 'CLEANUP_FAILED');
```

原理：
- 表达式 `(1)` 是**常量表达式索引**：所有进入该索引的行 key 都是 `1`
- `WHERE ...` 子句限定**只有活跃状态的行**才被索引（非活跃行完全不进索引）
- 因此：**最多存在 1 条活跃记录**（否则两条 key=1 的行会冲突），天然保证"至多一个活跃 batch"
- 活跃状态结束后（status 变为 COMPLETED / FAILED），行自动从索引中消失，不阻塞后续 sync

**为什么不用虚拟列方案？** MySQL 常见的写法是"`VIRTUAL` 生成列 + 普通唯一索引"（利用"唯一索引不去重 NULL"的特性）。在 PG 里这种写法会有两个代价：(a) PG 的生成列必须是 `STORED`（实际占用存储），(b) JPA 对"数据库生成的列"需要额外注解（`@Generated` + `insertable=false`）否则 INSERT 时会冲突。PG 原生的部分索引更干净，且语义完全等价。

> 📌 如果未来迁移到 MySQL，此方案不通用，需改为"虚拟列 + 普通唯一索引"。届时在此处注明。

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

**策略模式 + 单个 `@Transactional` 包所有 DELETE**：

```java
// Per-broker strategy — each BrokerSyncAdapter ships one of these.
public interface BrokerCleanupStrategy {
    String brokerCode();
    void deleteStagedRows(Long batchId);
}

@Component
public class TigerCleanupStrategy implements BrokerCleanupStrategy {
    public String brokerCode() { return "tiger"; }
    public void deleteStagedRows(Long batchId) {
        tigerStagedOrderRepo.deleteByBatchId(batchId);
    }
}

// Composer — unchanged for existing brokers, zero edits for new brokers.
@Service
public class SyncBatchCleanupService {
    private final Map<String, BrokerCleanupStrategy> byBroker;   // from Spring

    @PostConstruct
    void verifyAdapterCoverage() {
        // Fail-fast at startup if any adapter has no matching strategy.
    }

    @Transactional
    public void cleanupBatchData(Long batchId, String brokerCode) {
        byBroker.get(brokerCode).deleteStagedRows(batchId);     // broker-specific
        tradeRecordRepo.deleteBySyncBatchId(batchId);            // common
    }
}
```

`trade_record_tags` 表通过 `ON DELETE CASCADE` 外键随 `trade_records` 一起清理，无需手工操作。

事务内任意一个 DELETE 失败 → Spring 自动回滚整个事务 → 保证"要么全删，要么全不删"。

**注意**：清理事务**不包含** batch 本身的 status 更新。status 更新在独立的下一步做（见 5.4）。

### 5.4 带重试的清理入口

```java
@Service
public class SyncBatchFailureHandler {

    private static final int MAX_CLEANUP_ATTEMPTS = 3;
    private static final long CLEANUP_RETRY_BACKOFF_MS = 2000L;

    /**
     * 失败处理统一入口：清理数据 + 标记 FAILED（或 CLEANUP_FAILED）
     * 清理成功 → status = FAILED + errorMessage = 原因
     * 清理失败 3 次 → status = CLEANUP_FAILED + errorMessage = 清理失败原因 + 原始失败原因 (保留 phase)
     * 两次尝试之间 sleep 固定 2s；线程被中断 → 立即退出 retry 升级为 CLEANUP_FAILED
     */
    public void handleFailure(Long batchId, String brokerCode, String originalError) {
        Exception lastCleanupException = null;
        for (int attempt = 1; attempt <= MAX_CLEANUP_ATTEMPTS; attempt++) {
            try {
                cleanupService.cleanupBatchData(batchId, brokerCode);
                batchService.markAsFailed(batchId, originalError);  // clears phase
                log.info("Batch {} cleaned up and marked FAILED (attempt {})", batchId, attempt);
                return;
            } catch (Exception e) {
                lastCleanupException = e;
                log.error("Cleanup attempt {}/{} failed for batch {}: {}",
                        attempt, MAX_CLEANUP_ATTEMPTS, batchId, e.getMessage(), e);
            }
            if (attempt < MAX_CLEANUP_ATTEMPTS) {
                try {
                    Thread.sleep(CLEANUP_RETRY_BACKOFF_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;  // 中断 → 放弃剩余重试，走 CLEANUP_FAILED
                }
            }
        }
        // 全部失败 → CLEANUP_FAILED
        batchService.markAsCleanupFailed(batchId,
                String.format("Cleanup failed after %d attempts: %s. Original error: %s",
                        MAX_CLEANUP_ATTEMPTS,
                        lastCleanupException != null ? lastCleanupException.getMessage() : "unknown",
                        originalError));
    }
}
```

`markAsCleanupFailed` **保留** `phase` 值用于诊断（和 v1 的 INTERRUPTED 处理一样）；`markAsFailed` 清空 `phase`。

#### Cleanup retry policy（重试策略细化）

| 维度 | 选择 | 理由 |
|------|------|------|
| 重试次数 | **3**（含首次尝试） | 足以吸收典型的瞬时抖动；不至于拖长失败路径 |
| 退避策略 | **固定 2 秒**（不做指数退避 / 抖动） | cleanup 是本地 DB 的若干 DELETE，失败模式是瞬时连接池耗尽 / 短时锁冲突 / Postgres serialization failure，2s 足以让它们自行恢复；指数退避面向"远端级联雪崩"，与本场景不匹配；固定间隔在日志里更好读 |
| 最坏耗时 | **≤ 4 秒**（= 2 次 sleep × 2s） | 不会让前端 `/status` 轮询明显感知卡顿 |
| 中断处理 | `Thread.sleep` 抛 `InterruptedException` → 恢复中断标志、跳出循环、升级为 `CLEANUP_FAILED` | 应用关停（`SIGTERM`）时必须立刻放弃重试，否则违反优雅关停语义 |
| 兜底 | `CLEANUP_FAILED` 终态 + `uk_only_one_active` 唯一索引 | 即便重试全军覆没，也能通过"阻塞新 sync + 人工介入"保证不会错误累积 |

**为何不把 backoff 暴露为配置项**：这是一个内部实现细节，调整它需要同时考虑 retry 次数、前端轮询节奏、`CLEANUP_FAILED` 升级时机三者的配合，直接改代码更稳妥。如果未来真的需要在运维层面临时调整，可以把 `CLEANUP_RETRY_BACKOFF_MS` 改为 `@Value` 注入。

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

单个 Flyway 脚本 `V28__simplify_sync_batch_state_model.sql`（PostgreSQL 方言）：

```sql
-- 1. Drop failed_count column (v2 has no "partial success" concept anymore)
ALTER TABLE broker_sync_batches
  DROP COLUMN failed_count;

-- 2. Update status column comment (semantic change: 5 states, no PARTIAL / INTERRUPTED)
COMMENT ON COLUMN broker_sync_batches.status IS
  'Batch status: PENDING, PROCESSING, COMPLETED, FAILED, CLEANUP_FAILED';

-- 3. Update phase column comment (minor semantic tweak for CLEANUP_FAILED)
COMMENT ON COLUMN broker_sync_batches.phase IS
  'Sub-stage: FETCHING, STAGING, IMPORTING. Indicates current progress when status=PROCESSING; preserved for diagnostics when status=CLEANUP_FAILED; NULL otherwise.';

-- 4. Enforce "at most one active batch" via partial unique index.
--    Active = status IN (PENDING, PROCESSING, CLEANUP_FAILED). Non-active rows
--    are excluded from the index entirely, so they never collide. Active rows
--    all share the constant key (1), so a second active row violates uniqueness.
CREATE UNIQUE INDEX uk_only_one_active
  ON broker_sync_batches ((1))
  WHERE status IN ('PENDING', 'PROCESSING', 'CLEANUP_FAILED');

COMMENT ON INDEX uk_only_one_active IS
  'Partial unique index: blocks a second active sync batch at DB level. Released automatically when status becomes COMPLETED or FAILED.';
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
| `SyncBatchCleanupService` | 单个 `@Transactional` 方法，按 brokerCode 查 `BrokerCleanupStrategy` 清理 staged + trade_records；`@PostConstruct` 校验所有 adapter 都有匹配策略 |
| `BrokerCleanupStrategy`（接口，v2.4.2 引入）| 每个 broker adapter 提供一个策略 `@Component` 负责清理自己的 staged 表；替代原 switch/case |
| `IbkrCleanupStrategy` / `TigerCleanupStrategy`（v2.4.2 引入）| 两个 broker 的具体策略实现 |
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

> **实施状态**：✅ Phase 4 已完成（2026-04-23）。下列描述反映当前实际实现。

### 8.1 删除

- "恢复" 按钮（`RedoOutlined`）及其 `handleResume` 处理函数
- `resumableStatuses` 常量与按钮条件渲染
- `services/brokerSyncApi.js` 的 `resumeSync` export（后端端点已删）
- 状态过滤器下拉中的 `INTERRUPTED` / `PARTIAL` 选项
- 表格中的 `操作` 列（曾唯一用途是承载恢复按钮）

### 8.2 修改

- 状态徽章（Tag）color mapping：
  - `PENDING` → blue
  - `PROCESSING` → orange
  - `COMPLETED` → green
  - `FAILED` → red
  - `CLEANUP_FAILED` → **magenta（洋红）**，视觉上与 FAILED 明显区分
  - `PARTIAL` / `INTERRUPTED` → gold / volcano —— 仅保留以便正确渲染 v1 历史批次数据，过滤器不再暴露这两项
- `CLEANUP_FAILED` 的状态列 Tooltip 追加："自动清理失败，需人工确认残留数据后将状态改为 FAILED 才能重试"
- 状态过滤器选项更新为：`PENDING` / `PROCESSING` / `COMPLETED` / `FAILED` / `CLEANUP_FAILED`（5 种）
- `handleSyncSubmit` 的错误处理：HTTP 409 + `conflictingBatchId != null` → 用 `Modal.warning` 弹窗展示冲突批次 ID、状态 Tag、以及 `CLEANUP_FAILED` 场景的人工处理提示（改 status 为 FAILED 才能重试）；其它错误走原有 `message.error` 分支

### 8.3 保留

- 进度展示（`phase` 字段在 PROCESSING 时显示在状态 Tooltip 中）
- v1 历史批次的状态 label/color 映射（`PARTIAL` / `INTERRUPTED`）仅用于渲染存量数据，新 sync 不会再产生这两种状态

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

| 方面 | v1 (2026-04-16) | v2 (2026-04-22 起；当前 v2.4.4 / 2026-04-23) |
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

## 十二、实施 Todos（全部完成 ✅）

1. ✅ 写 `V28` Flyway 脚本（Phase 1a）
2. ✅ 改 entity / repository（删 failedCount，加 delete 方法）（Phase 1b / 2）
3. ✅ 新增 `SyncBatchCleanupService` + `SyncBatchFailureHandler`（Phase 2）
4. ✅ 改 `BrokerSyncBatchService`：加 `markAsCleanupFailed`；加 `createBatch` 409 guard（`SyncConflictException` + DB 唯一索引兜底）；删 `markAsPartial`/`markAsInterrupted`（Phase 3 Commit B 完成）
5. ✅ 改 `BrokerSyncAsyncExecutor`：接入 `SyncBatchFailureHandler`；成功 → COMPLETED；旧 PARTIAL 分支已完全移除
6. ✅ 改 `BrokerSyncController`：加 `@ExceptionHandler(SyncConflictException)` → 409；删 `/batches/{id}/resume` 端点与 `RESUMABLE_STATUSES`
7. ✅ 改 `SyncBatchRecoveryRunner`（启动扫 PENDING / PROCESSING 批次 → `SyncBatchFailureHandler.handleFailure`）
8. ✅ 删除 `SyncResult.failedCount`（Phase 1b）
9. ✅ 改前端：删恢复按钮与 `resumeSync` 客户端；状态过滤器去除 `PARTIAL` / `INTERRUPTED`，新增 `CLEANUP_FAILED`；状态列 Tooltip 追加 `CLEANUP_FAILED` 的人工处理提示；`POST /trigger` 409 响应改为 Modal 展示冲突批次 ID / 状态并给出处理指引（Phase 4 完成）
10. ✅ 测试：新增 `SyncBatchCleanupServiceTest`、`SyncBatchFailureHandlerTest`、`BrokerSyncControllerTest`（含 409 用例）；改 `BrokerSyncBatchServiceTest` 加 conflict 用例并清理 PARTIAL/INTERRUPTED 用例；改 `SyncBatchRecoveryRunnerTest` 改为 cleanup-flow；改 `BrokerSyncAsyncExecutorTest` 改为 failureHandler-mock；类级 `@SuppressWarnings("deprecation")` 已移除
11. ✅ 更新关联文档：`data-persistence.md` 同步到 v2.3；`brokers/tiger/phase3-plan.md` 将 v1 的 Resume/INTERRUPTED/PARTIAL 口径改写为 v2 现状口径（Phase 3 Commit C 完成）；`architecture.md` / `broker-registration.md` 扫描无残留命中
12. ✅ 端到端 audit 修复（v2.4.1）：
    - **数据一致性修复**：`TigerSyncAdapter.fetchFilledOrders` 改为抛出 API 异常（原先吞 `Exception` 只返回空 list，会把 API 错误静默当作"没有订单"，存在丢数据风险）；`!response.isSuccess()` 同样抛 `RuntimeException`。IBKR 的 `IbkrFlexClient.fetchReport` 行为未变（本就会抛异常），两边达到对等。
    - **启动恢复扫描范围扩展**：`SyncBatchRecoveryRunner` 由只扫 `PROCESSING` 扩展到 `PENDING ∪ PROCESSING`；新增 `BrokerSyncBatchRepository.findByStatusIn(Collection)`。原先 `createBatch` 与 `markAsProcessing` 之间的窗口若发生 JVM 崩溃会留下 PENDING 残留，被 `uk_only_one_active` 永久锁死新 sync；修复后启动时一并清理。
    - **adapter fail-fast 路径简化**：IBKR / Tiger `sync()` 在 `failedCount > 0` 时改为直接 `return SyncResult.failure(...)`（原先 throw 后被同方法 catch 再转 failure，路径绕且日志双重）。最终效果与之前一致：`BrokerSyncAsyncExecutor` → `SyncBatchFailureHandler.handleFailure` → FAILED / CLEANUP_FAILED。
    - 测试：`SyncBatchRecoveryRunnerTest` 改为 `findByStatusIn` 契约，新增"PENDING 残留也会被清理"用例，同时断言扫描集合与 v2 active 语义一致（不包含 CLEANUP_FAILED）。

标记含义：✅ 完成；🔧 进行中；⏳ 待开始。

---

## 十三、开放问题 / Review 待确认

✅ 所有 A-H 问题已在讨论中确认。
✅ 并发方案（虚拟列 + 唯一索引）已确认采用。
✅ 文档重写策略（直接 v2 覆盖，v1 从 git 追溯）已确认。

剩余小决策由 AI 在实施时自行决定（第五章 5.6 的 error_message 汇总格式、前端 409 错误文案具体措辞等），有歧义会在 PR review 时再讨论。

---

## 十四、P0 Data-Loss Chain — Fixed in 2026-04 (v2.4.3)

> **完整设计 / 决策记录**：[../fix-p0-data-loss-chain.md](../fix-p0-data-loss-chain.md)（Status: ✅ Implemented）

v2.4.2 架构加固之后，在进一步审计 import 链路时发现了一组**紧耦合的三个 P0 级数据丢失缺陷**——单独看每个都严重，组合在一起意味着失败的 staged 行可以**既不落到 `trade_records`、又不以 FAILED 呈现**，batch 最终仍报 `COMPLETED`。2026-04-23 完整修复。

### 14.1 三个 P0 缺陷回顾

| 编号 | 问题 | 结果 |
|------|------|------|
| **P0-1** | `importSingleOrder` / `importOne` 在 `REQUIRES_NEW` 事务内 catch 异常后，直接在**同一个已被标记 rollback-only 的事务**里 `save(staged=FAILED)` | 要么在 commit 时抛 `UnexpectedRollbackException`（错误上下文被掩盖），要么 Hibernate 层成功但随事务回滚而丢弃——无论哪种，staged 保持原 `PENDING` 状态 |
| **P0-2** | Adapter 的 failure 判定只看 `failedCount`；若 P0-1 让 FAILED 写入丢失，`failedCount=0` → batch 判 `COMPLETED` → 不触发 cleanup → 残留的 `PENDING` staged 行永久留在表中 | batch 看起来"成功"，但有行既没进正式表也没记为 FAILED |
| **P0-3** | `IbkrImportWorker.resolveBookTradeRefType` 在 TradeConfirm 缺失 / code 空 / 含未知 token 时采用 silent downgrade 为 `MANUAL`，把期权事件误分类为手动交易 | 财务语义污染，且配合 P0-1/P0-2 下游没有任何异常信号 |

### 14.2 修复思路

1. **P0-1 → package-private `ImportOneFailedException`**：
   - Worker 的 catch 块不再自己 `save(FAILED)`，而是 `throw new ImportOneFailedException(staged, cause)`。
   - Service 层 per-order 循环捕获该异常，通过 **AOP 代理**调本 service bean 的 `markFailed(staged.getId(), rootMessage(cause))`。
   - `markFailed` 本身 `@Transactional(REQUIRES_NEW)`，内部通过 `findById` 重新读取后再 `save` —— 与 worker 的事务完全隔离，不会被 rollback 牵连。
   - `markFailed` 自身抛异常时，service 以 `markFailedSafely` 吞异常 + WARN，避免"记 FAILED 失败 → 整批循环崩溃"的链式失败。

2. **P0-2 → residual（非终态）staged 计数**：
   - Repository 新增 `countByBatchIdAndStatusNotIn(batchId, TERMINAL_STATUSES)` 和 `findIdsByBatchIdAndStatusNotIn(... Pageable)`（`TERMINAL_STATUSES = {IMPORTED, SKIPPED, FAILED}`）。
   - Adapter 在 import 阶段完成后，`residualCount = count(status NOT IN terminal)`。
   - failure 条件改为 `failedCount > 0 || residualCount > 0`；reason 中附带 `residual_non_terminal=N`。
   - `residualCount > 0` 时 WARN 日志 dump 前 20 个残留 staged id（`RESIDUAL_ID_LOG_CAP`），便于运维人肉排查——这是兜底诊断工具，正常修复后不应被触发。

3. **P0-3 → fail-fast throw**：
   - `resolveBookTradeRefType` 的三个 silent-downgrade 分支一律改为 `throw new IllegalStateException(...)`。
   - 异常路径 = P0-1 路径：经 `importOne` 冒泡 → `ImportOneFailedException` → service `markFailed` 持久化 staged=FAILED → `failedCount > 0` → batch fail-fast cleanup。
   - 语义上把"未知 code 要不要悄悄归到 MANUAL"的决定权交还运维：看到 `unknown code tokens` 就是需要更新 [booktrade-mapping.md](../brokers/ibkr/booktrade-mapping.md) §2.2 优先级表。
   - 顺便正式并入 `GEA` token（与 `A` 同归 `OPTION_ASSIGNED`）。

### 14.3 测试覆盖

| 类 | 新增/改动用例 |
|----|-------------|
| `IbkrImportWorkerTest` | `ErrorHandlingTest`（mapping/save 异常均断言抛 `ImportOneFailedException` 且不再 save FAILED）、`MarkFailedTest`（REQUIRES_NEW 行为 + findById 路径）、`BookTradeDetectionTest` 4 个新分支（missing / blank / unknown / GEA） |
| `TigerImportWorkerTest` | 对称：`ExceptionPath` 重写 + `MarkFailedTest` + `PreFilterTerminalPersistsStaged`（名字改动反映 importOne 错误路径不再 save） |
| `IbkrImportServiceTest` / `TigerImportServiceTest` | `shouldInvokeMarkFailedOnImportFailure`、`shouldSwallowMarkFailedException`、`shouldKeepIteratingAfterFailure` |
| `IbkrSyncAdapterTest` | `ResidualNonTerminalTest`：residual-only / failed+residual / 无 residual 三分支 |

全量 `mvn test` 2026-04-23：**242 tests, 0 failures, 0 errors, BUILD SUCCESS**。

### 14.4 对 v2 状态模型的兼容性

本次修复**不改变**三终态（`COMPLETED` / `FAILED` / `CLEANUP_FAILED`）、不改变"失败即清理"原则、不改变 `uk_only_one_active` 并发约束，仅堵住了"失败信号丢失 → 批次被误判 COMPLETED"的缺口。v2 状态图、清理策略、前端交互全部保持不变。
