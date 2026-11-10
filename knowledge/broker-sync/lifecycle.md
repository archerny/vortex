# 券商同步生命周期

## 入口

| 接口 | 当前行为 |
| --- | --- |
| `POST /api/broker-sync/trigger` | 校验 `brokerCode`，创建 `PENDING` 批次并提交异步任务。返回 HTTP 200 和批次信息 |
| `GET /api/broker-sync/brokers` | 返回已注册且在 `brokers` 表中有对应记录的适配器 |
| `GET /api/broker-sync/batches` | 按可选的 `brokerCode`、`status` 查询批次，按 `startedAt` 倒序 |
| `GET /api/broker-sync/batches/{id}` | 按 ID 查询单个批次，找不到时返回 HTTP 404 |

新建同步表单要求选择日期范围，并提交 `yyyy-MM-dd` 格式的 `startTime` 和 `endTime`。API 允许省略日期。省略时，批次表中缺失的日期各自记录为当天，适配器仍根据原请求计算抓取范围，默认结束为当天、起始为结束前 90 天。前端提交成功后刷新一次批次列表，后续由用户手动刷新。见 [控制器](../../backend/src/main/java/com/vortex/controller/BrokerSyncController.java)、[批次服务](../../backend/src/main/java/com/vortex/service/BrokerSyncBatchService.java)和 [批次页](../../frontend/src/pages/sync/batches/SyncBatchesTab.jsx)。

## 异步执行

`BrokerSyncAsyncExecutor` 在 `syncTaskExecutor` 线程池执行。它将批次置为 `PROCESSING`、阶段置为 `FETCHING`，将批次 ID 写入 `SyncRequest`，再由 `BrokerSyncService` 根据 `brokerCode` 路由。适配器在开始暂存和导入前分别切换到 `STAGING` 和 `IMPORTING`。适配器返回成功时，执行器写入结果计数。适配器返回失败或执行中抛出异常时，执行器调用统一失败处理器。

线程池配置为核心线程 2、最大线程 4、队列容量 10，提交被拒绝时记录错误并抛出异常。控制器在提交任务前已经创建批次，提交失败可能遗留 `PENDING`，由启动恢复处理。见 [异步执行器](../../backend/src/main/java/com/vortex/sync/core/BrokerSyncAsyncExecutor.java)和 [线程池配置](../../backend/src/main/java/com/vortex/config/AsyncConfig.java)。

## 状态与恢复

| 批次状态 | 含义 |
| --- | --- |
| `PENDING` | 已创建，等待执行 |
| `PROCESSING` | 正在拉取、暂存或导入 |
| `COMPLETED` | 批次成功，记录导入和跳过计数 |
| `FAILED` | 失败处理已完成清理，并成功写入失败状态 |
| `CLEANUP_FAILED` | 清理未完成，保留失败时的阶段和诊断信息，等待人工处理 |

`PENDING`、`PROCESSING`、`CLEANUP_FAILED` 共用 V28 的部分唯一索引 `uk_only_one_active`。应用层也在创建前检查这三种状态。只要存在一个占用该索引的批次，就不能再创建另一批，哪怕来自不同券商。控制器返回 HTTP 409，可带冲突批次 ID 和状态。

失败处理在一个事务中先清理当前券商本批次的暂存行，再清理 `trade_records.sync_batch_id` 对应的记录。清理最多尝试三次，尝试间隔两秒。等待被中断时，停止重试并尝试写入 `CLEANUP_FAILED`。清理事务与最终状态更新分开。如果状态更新本身失败，处理器记录错误，批次可能留在原状态。

启动恢复扫描 `PENDING` 和 `PROCESSING`，并调用同一失败处理器。`CLEANUP_FAILED` 继续占用活动索引，由人工确认残留后处理。当前流程无 Resume 接口。历史 `PARTIAL` 和 `INTERRUPTED` 仍有前端展示标签，当前应用不生成这两种状态。见 [V28](../../backend/src/main/resources/db/migration/V28__simplify_sync_batch_state_model.sql)、[失败处理器](../../backend/src/main/java/com/vortex/sync/core/SyncBatchFailureHandler.java)和 [启动恢复](../../backend/src/main/java/com/vortex/sync/core/SyncBatchRecoveryRunner.java)。

相关测试源码覆盖 [异步执行](../../backend/src/test/java/com/vortex/sync/core/BrokerSyncAsyncExecutorTest.java)、[重试和中断](../../backend/src/test/java/com/vortex/sync/core/SyncBatchFailureHandlerTest.java)、[启动恢复](../../backend/src/test/java/com/vortex/sync/core/SyncBatchRecoveryRunnerTest.java)和 [HTTP 409](../../backend/src/test/java/com/vortex/controller/BrokerSyncControllerTest.java)。历史决策见 [导入一致性 v2](../../design/0018-2026-04-22-broker-import-consistency-v2.md)。
