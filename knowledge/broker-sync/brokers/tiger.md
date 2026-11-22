# Tiger 同步

## 拉取与暂存

`TigerSyncAdapter` 通过 POM 中声明的 Tiger OpenAPI Java SDK 查询 `FILLED_ORDERS`。窗口末日最多取起始日加 90 天，下一窗从上一窗末日的下一天开始。托管账户选择 Tiger 券商并填写真实资金账号；同步配置的账户行将 Tiger ID 和 PKCS#8 私钥明文保存到 `broker_sync_connections`，资金账号来自所选账户资料。生产运行仅从数据库读取，不回退profile；每批捕获同一组凭证，用于新建SDK客户端和所有日期窗口。配置步骤见 [Tiger接入指南](../../../docs/broker-sync/brokers/tiger/README.md)和 [0047](../../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。

订单暂存到 `tiger_staged_orders`。`tiger_id` 来自 SDK 的 `TradeOrder.getId()`，与来源资金账号组成暂存去重键，导入后与本地账户主键及券商共同去重，也作为 `trade_records.external_id`。V27 尝试给已有 Tiger 券商行填入技术标识 `tiger`。前端同步批次页可触发 Tiger，同步原始数据页目前只提供 IBKR 面板。见 [Tiger 适配器](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerSyncAdapter.java)和 [V27](../../../backend/src/main/resources/db/migration/V27__seed_tiger_broker_code.sql)。

拉取订单的资金账号必须与本批真实账户 ID 快照一致，否则在暂存前失败。不同账户可导入相同外部 ID；导入从持久化批次读取本地账户，不能按当前券商代码重新选择归属。局部合成验收覆盖错账户拒绝、分账户凭证和去重；真实 ORM 导入使用 epoch 时间夹具，特意不经过下述有缺陷的正常暂存时间转换，不能据此认定该缺陷已修复。

## 导入映射

- 已导入的外部订单和无正数成交量的订单记为 `SKIPPED`。未知证券类型或方向、`quantity_scale > 0`、非空的 `attr_desc`，以及无法识别的期权方向会让该订单失败，最终触发整批清理。
- `STK` 映射到股票，`OPT` 按 `CALL`、`PUT` 映射到期权。期权 symbol 使用标的、到期日、方向和去掉尾零的行权价。`CNH`、`CNY` 都映射为 `CNY`，另支持 `USD` 和 `HKD`。
- 成交数量取 `filled_quantity` 的绝对整数，价格取成交均价，成交金额按价格乘数量计算，期权再乘合约乘数。空的期权乘数使用 100。费用为佣金和 GST 的绝对值之和。当前通过过滤的订单使用 `MANUAL/NONE/0`。Tiger 的期权事件订单尚无相应映射。

过滤和映射见 [交易映射器](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java)和 [导入 Worker](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerImportWorker.java)。

## 时间格式限制

当前源码存在两阶段时间格式不一致。`TigerStagingWorker` 将 SDK 的毫秒时间戳以 `Asia/Shanghai` 转成 `yyyy-MM-dd'T'HH:mm:ss` 后写入 `trade_time`，`TigerTradeRecordMapper` 却将同一字段按 epoch 毫秒整数解析。按该代码路径，通过预过滤的新暂存订单在导入时会因时间格式失败并触发批次清理。该缺陷仍待修复；证据为源码静态核对，未进行真实账户运行验证。 [活动待办](../../../docs/TODO.md)

[暂存服务测试](../../../backend/src/test/java/com/vortex/sync/adapter/tiger/TigerStagingServiceTest.java)模拟暂存 Worker，[映射测试](../../../backend/src/test/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapperTest.java)直接使用构造的毫秒字符串。这两组测试没有覆盖暂存到导入的时间格式衔接。对应实现见 [暂存 Worker](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerStagingWorker.java)和 [交易映射器](../../../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java)。

历史方案见 [Tiger Open API 接入](../../../design/0006-2026-03-14-tiger-open-api.md)、[暂存设计](../../../design/0016-2026-04-21-tiger-staging-schema.md)和 [Phase 3 实施计划](../../../design/0017-2026-04-21-tiger-phase3-plan.md)。
