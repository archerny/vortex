# IBKR 同步

## 拉取与暂存

`IbkrSyncAdapter` 使用 Trade Confirmation Flex Query。`IbkrFlexClient` 先调用 `SendRequest`，再轮询 `GetStatement` 获取 XML。适配器按日期范围分窗，窗口末日最多取起始日加 365 天，下一窗从上一窗末日的下一天开始。请求日期使用 `yyyyMMdd`。适配器使用 `broker.ibkr.flex-token` 和 `broker.ibkr.trade-confirm-query-id`，配置步骤见 [IBKR 接入指南](../../../docs/broker-sync/brokers/ibkr/README.md)。

`FlexQueryParser` 读取第一个 `FlexStatement` 下的 `TradeConfirms`。`Order` 写入 `ibkr_staged_orders`，`TradeConfirm` 写入 `ibkr_staged_trade_confirms`，`SymbolSummary` 不导入。暂存 Worker 按 `order_id` 和 `trade_id` 分别去重。前端可按批次查看两表，查询接口为 `GET /api/sync/ibkr/orders` 和 `GET /api/sync/ibkr/trade-confirms`。见 [Flex 客户端](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrFlexClient.java)、[XML 解析器](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java)和 [原始数据控制器](../../../backend/src/main/java/com/vortex/controller/IbkrStagedDataController.java)。

## 导入映射

- 正式交易以订单为粒度。`STK` 映射到股票，`OPT` 按 `putCall` 的 `C` 或 `P` 映射到期权。期权 symbol 使用 `description` 的首个词作为标的，再拼到期日、方向和去掉尾零的行权价。
- `tradeDate` 使用 `yyyyMMdd`。`BUY`、`SELL` 分别映射为买卖方向。`CNH` 与 `CNY` 都映射为 `CNY`，`USD` 和 `HKD` 保持原值。金额取原始 `amount` 的绝对值，费用为佣金和附加费的绝对值之和。数量按当前实现从十进制转为整数再取绝对值。
- `order_time` 和 `order_type` 同时为空时，订单进入 BookTrade 分支，交易触发来源为 `OPTION`。其他订单使用 `MANUAL/NONE`。BookTrade 按 `order_id` 读取第一条成交确认的 `code`，按分号拆分后精确匹配。`Ex`、`MEx`、`AEx` 的行权判定优先于 `Ep` 的到期判定，后者优先于 `A`、`GEA` 的指派判定。确认记录缺失、code 为空或无法识别时，该订单失败并最终触发整批清理。
- 期权事件初始 `trigger_ref_id` 为 0。导入后，对本批股票侧行权或指派记录回填关联的期权交易 ID。期权候选按事件类型、标的和交易日查询，可能来自其他批次。多候选时优先匹配行权价，其次匹配数量，仍无法区分时取首条。找不到候选则保留 0 并记录警告。

映射和回填见 [导入 Worker](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java)、[导入服务](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportService.java)及 [交易仓储查询](../../../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java)。

## 验证边界

[适配器测试](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapterTest.java)和 [导入 Worker 测试](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrImportWorkerTest.java)覆盖分窗、失败残留、映射与 BookTrade 边界。解析器测试中部分用例依赖被 Git 忽略的本地 XML。本次文档迁移仅核对源码，未运行这些用例或连通真实账户。

历史方案见 [Flex Web Service](../../../design/0009-2026-04-14-ibkr-flex-web-service.md)、[BookTrade](../../../design/0012-2026-04-18-ibkr-booktrade-mapping.md)、[IBKR 暂存](../../../design/0013-2026-04-21-ibkr-staging-schema.md)和 [原始数据查看](../../../design/0015-2026-04-21-sync-staged-data-viewer.md)。
