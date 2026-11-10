# Tiger 同步

## 拉取与暂存

`TigerSyncAdapter` 通过 POM 中声明的 Tiger OpenAPI Java SDK 查询 `FILLED_ORDERS`。窗口末日最多取起始日加 90 天，下一窗从上一窗末日的下一天开始。适配器使用 `broker.tiger.tiger-id`、`broker.tiger.private-key` 和 `broker.tiger.account`，配置步骤见 [Tiger 接入指南](../../../docs/broker-sync/brokers/tiger/README.md)。

订单暂存到 `tiger_staged_orders`。`tiger_id` 来自 SDK 的 `TradeOrder.getId()`，用于暂存去重，导入后也作为 `trade_records.external_id`。V27 尝试给已有 Tiger 券商行填入技术标识 `tiger`。前端同步批次页可触发 Tiger，同步原始数据页目前只提供 IBKR 面板。见 [Tiger 适配器](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerSyncAdapter.java)和 [V27](../../../backend/src/main/resources/db/migration/V27__seed_tiger_broker_code.sql)。

## 导入映射

- 已导入的外部订单和无正数成交量的订单记为 `SKIPPED`。未知证券类型或方向、`quantity_scale > 0`、非空的 `attr_desc`，以及无法识别的期权方向会让该订单失败，最终触发整批清理。
- `STK` 映射到股票，`OPT` 按 `CALL`、`PUT` 映射到期权。期权 symbol 使用标的、到期日、方向和去掉尾零的行权价。`CNH`、`CNY` 都映射为 `CNY`，另支持 `USD` 和 `HKD`。
- 成交数量取 `filled_quantity` 的绝对整数，价格取成交均价，成交金额按价格乘数量计算，期权再乘合约乘数。空的期权乘数使用 100。费用为佣金和 GST 的绝对值之和。当前通过过滤的订单使用 `MANUAL/NONE/0`。Tiger 的期权事件订单尚无相应映射。

过滤和映射见 [交易映射器](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java)和 [导入 Worker](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerImportWorker.java)。

## 时间格式限制

当前源码存在两阶段时间格式不一致。`TigerStagingWorker` 将 SDK 的毫秒时间戳以 `Asia/Shanghai` 转成 `yyyy-MM-dd'T'HH:mm:ss` 后写入 `trade_time`，`TigerTradeRecordMapper` 却将同一字段按 epoch 毫秒整数解析。按该代码路径，新暂存订单在导入时会因时间格式失败并触发批次清理。本次文档迁移通过静态核对发现此问题，未修复业务代码或连接真实账户验证。

[暂存服务测试](../../../backend/src/test/java/com/vortex/sync/adapter/tiger/TigerStagingServiceTest.java)模拟暂存 Worker，[映射测试](../../../backend/src/test/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapperTest.java)直接使用构造的毫秒字符串。这两组测试没有覆盖暂存到导入的时间格式衔接。对应实现见 [暂存 Worker](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerStagingWorker.java)和 [交易映射器](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java)。

历史方案见 [Tiger Open API 接入](../../../design/0006-2026-03-14-tiger-open-api.md)、[暂存设计](../../../design/0016-2026-04-21-tiger-staging-schema.md)和 [Phase 3 实施计划](../../../design/0017-2026-04-21-tiger-phase3-plan.md)。
