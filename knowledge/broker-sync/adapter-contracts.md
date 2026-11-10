# 券商同步适配器契约

## 注册和路由

`BrokerSyncAdapter#getBrokerCode()` 返回稳定的技术标识。Spring 收集适配器组件，`BrokerSyncService` 以该标识构建路由。数据库 `brokers.broker_code` 用于关联实际的券商记录。发现接口只返回可关联到记录的适配器，发现结果本身不检查 API 凭据。导入阶段从该记录取得 `broker_id`。见 [适配器接口](../../backend/src/main/java/com/vortex/sync/core/BrokerSyncAdapter.java)和 [路由服务](../../backend/src/main/java/com/vortex/sync/core/BrokerSyncService.java)。

每个适配器都须提供同一技术标识的 `BrokerCleanupStrategy`。启动时清理服务检查策略是否覆盖已注册的适配器，并拒绝同一标识的重复清理策略。新增暂存表时应持有 `batch_id` 并纳入该券商策略，正式交易由框架统一按 `sync_batch_id` 清理。见 [清理策略接口](../../backend/src/main/java/com/vortex/sync/core/BrokerCleanupStrategy.java)和 [清理服务](../../backend/src/main/java/com/vortex/sync/core/SyncBatchCleanupService.java)。

## 两阶段写入与失败处理

1. 使用执行器注入的 `batchId`。先拉取调用方指定范围内的数据，进入 `STAGING` 后写入券商暂存表，进入 `IMPORTING` 后处理暂存订单。
2. 适配器委托独立的 Worker Bean，以 `REQUIRES_NEW` 事务写入暂存记录或导入单条订单。发生导入异常时，在导入事务之外重新加载该暂存行，并在新的事务中写入 `FAILED` 和错误信息。正常过滤出的失败可以由尚可提交的单条事务写入。
3. 导入结束后从数据库统计 `FAILED` 和残留行。现有两种适配器将 `IMPORTED`、`SKIPPED`、`FAILED` 之外的状态视为残留。只要失败或残留计数大于零，适配器报告整批失败，执行器调用统一清理。无数据或所有新暂存行均按已定义的规则跳过时，可以成功结束。
4. 当前批次清理成功后，可以重新触发同一范围。暂存业务键和正式表外部订单键负责去重。当前没有持久化的增量游标。

这套契约的历史依据见 [导入一致性 v2](../../design/0018-2026-04-22-broker-import-consistency-v2.md)及 [导入数据丢失链修复](../../design/0019-2026-04-23-broker-import-data-loss-fix.md)。清理及恢复见 [生命周期](lifecycle.md#状态与恢复)。

## 分类与诊断

券商各自映射原始资产类别。内部 `AssetType` 包含 `STOCK`、`ETF`、`OPTION_CALL`、`OPTION_PUT`。当前 IBKR 和 Tiger 的常规同步映射覆盖股票和期权，ETF 没有单独的映射分支。

`FailureCategory` 定义 `AUTH`、`NETWORK`、`UNRECOGNIZED`、`INTERNAL`。适配器用 `CategorizedSyncException` 或其格式化方法生成带分类的原因。错误行的导入服务可补充 `ext_id`。批次失败消息还会由 `SyncResult.failure` 包装，清理失败时另有清理错误前缀。排查时不要假设 `broker_sync_batches.error_message` 以分类开头。见 [错误分类](../../backend/src/main/java/com/vortex/sync/core/FailureCategory.java)、[分类异常](../../backend/src/main/java/com/vortex/sync/core/CategorizedSyncException.java)和 [同步结果](../../backend/src/main/java/com/vortex/sync/core/SyncResult.java)。
