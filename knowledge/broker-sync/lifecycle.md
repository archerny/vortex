# 券商同步生命周期

## 入口

| 接口 | 当前行为 |
| --- | --- |
| `POST /api/broker-sync/trigger` | 校验 `brokerCode` 及所选 `brokerAccountId` 的归属、启停与真实 ID，创建 `PENDING` 批次并提交异步任务。返回 HTTP 200 和批次信息 |
| `GET /api/broker-sync/brokers` | 返回已注册且在 `brokers` 表中有对应券商资料的适配器，不检查凭据 |
| `GET /api/broker-sync/connections` | 列出全部托管账户、支持状态、非秘密配置与就绪状态 |
| `GET /api/broker-sync/connections/{brokerAccountId}` | 返回单账户连接元数据，不返回Token／私钥 |
| `PUT /api/broker-sync/connections/{brokerAccountId}` | 保存与账户券商类型一致的凭证；秘密字段首次必填，后续省略／留空保留；不修改账户资料 |
| `DELETE /api/broker-sync/connections/{brokerAccountId}` | 删除该账户连接，保留账户、其他账户连接和账本 |
| `/api/broker-sync/ibkr/settings`（GET／PUT／DELETE） | 保留旧IBKR接口响应结构，由统一连接服务读写；只在该券商恰好一个账户时使用，旧 `brokerId` 保持券商主键语义；多账户拒绝并提示使用账户连接 API |
| `GET /api/broker-sync/batches` | 按可选的 `brokerCode`、`status`、`brokerAccountId` 查询批次，按 `startedAt` 倒序 |
| `GET /api/broker-sync/batches/{id}` | 按 ID 查询单个批次，找不到时返回 HTTP 404 |

新建同步表单提交本地账户主键、券商代码和日期范围；不提交真实 ID，真实 ID 由服务端读取并存入批次快照。旧请求省略账户主键时只允许唯一账户，多账户返回 400。新建同步表单要求选择日期范围，并提交 `yyyy-MM-dd` 格式的 `startTime` 和 `endTime`。API 允许省略日期。省略时，批次表中缺失的日期各自记录为当天，适配器仍根据原请求计算抓取范围，默认结束为当天、起始为结束前 90 天。前端提交成功后刷新一次批次列表，后续由用户手动刷新。见 [控制器](../../backend/src/main/java/com/vortex/controller/BrokerSyncController.java)、[批次服务](../../backend/src/main/java/com/vortex/service/BrokerSyncBatchService.java)和 [批次页](../../frontend/src/pages/sync/batches/SyncBatchesTab.jsx)。

## 异步执行

保存／清空连接及账户变更可在活动批次期间执行。IBKR每份报表从数据库读取同一组已提交凭证，SendRequest和GetStatement使用该Token，后续报表可使用新配置。Tiger在批次开始捕获同一组Tiger ID／私钥／资金账号，客户端和所有日期窗口均使用该快照。导入从已持久化批次取得本地账户归属，即使拉取期间券商代码转移，也不重新按代码选择记账账户。IBKR 所有报表（含空报表）和 Tiger 返回订单须符合本次真实账户 ID 快照。路由在券商请求前校验账户启用、真实 ID 已补齐。账户资料及凭证编辑仍由本地用户安排时机，进一步保护设计记录在 TODO。 [0047](../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)

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

IBKR 股票侧期权事件回填也是同步成功的必要步骤：同一本地账户全部条件核对后，无匹配、多匹配或已占用均使整个批次失败，查询及保存异常向上传播；本次新导入期权交割记录的乘数不受支持也失败。历史正式记录直接信任，不回查旧来源或历史状态。失败原因保留到批次，沿用 FAILED／CLEANUP_FAILED 流程，不另设“未关联但完成”状态。 [关联规则](brokers/ibkr.md#导入映射) · [0042](../../design/0042-2026-11-18-trust-existing-option-event-records.md)

## 与市场事件的协调

批次创建与事件操作在同一账本短写互斥中检查最新状态；PENDING／PROCESSING／CLEANUP_FAILED 期间拒绝事件处理、修改和撤销。正式导入与 IBKR 关联回填也取得该事务互斥，新历史交易受关联证券锁限制，完全一致的重复仍可跳过。历史冲突沿用整批失败，不自动重算。 [市场事件](../market-events.md#并发同步与旧状态)

清理在任何删除前确认批次存在、券商相符、状态为 PENDING／PROCESSING／FAILED／CLEANUP_FAILED，并检查本批有效正式交易没有命中事件历史锁。COMPLETED 拒绝清理；旧 FAILED 残留若已参与后续事件，也拒绝删除，避免清理绕过历史锁。普通失败流程仍先清理再置 FAILED，清理失败保留 CLEANUP_FAILED。 [0038](../../design/0038-2026-11-17-sync-cleanup-history-guard.md) · [恢复说明](../../docs/broker-sync/recovery.md)

相关测试源码覆盖 [异步执行](../../backend/src/test/java/com/vortex/sync/core/BrokerSyncAsyncExecutorTest.java)、[重试和中断](../../backend/src/test/java/com/vortex/sync/core/SyncBatchFailureHandlerTest.java)、[启动恢复](../../backend/src/test/java/com/vortex/sync/core/SyncBatchRecoveryRunnerTest.java)和 [HTTP 409](../../backend/src/test/java/com/vortex/controller/BrokerSyncControllerTest.java)。历史决策见 [导入一致性 v2](../../design/0018-2026-04-22-broker-import-consistency-v2.md)。
