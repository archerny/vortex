# 交易与持仓

## 交易记录

`/api/trade-records` 支持新增、修改、软删除和查询。查询可按券商、证券类型、策略、证券代码或日期范围筛选。交易日期使用 `LocalDate`，数据库存储为 `DATE`。交易支持股票、ETF、看涨期权和看跌期权，币种为 `CNY`、`HKD` 或 `USD`。底层证券代码 `underlyingSymbol` 必填，交易记录不再保存证券名称。 [交易 API](../backend/src/main/java/com/vortex/controller/TradeRecordController.java) · [交易实体](../backend/src/main/java/com/vortex/entity/TradeRecord.java) · [V5 底层证券代码约束](../backend/src/main/resources/db/migration/V5__alter_underlying_symbol_not_null.sql) · [V25 删除名称列](../backend/src/main/resources/db/migration/V25__drop_symbol_name_fields.sql)

通过交易 API 新增或修改记录时，服务端按数量 × 单价计算成交金额。看涨和看跌期权再乘合约乘数 100，费用单列。交易数量为整数，数据库中的单价保留 4 位小数，金额和费用各保留 2 位。券商同步和市场事件有各自的记录生成入口。 [交易服务](../backend/src/main/java/com/vortex/service/TradeRecordService.java) · [交易表定义](../backend/src/main/resources/db/migration/V3__create_trade_records.sql) · [市场事件](market-events.md)

统计接口 `GET /api/trade-records/statistics` 统计未软删除记录的总数、各证券类型的记录数、分币种的费用，以及底层证券去重数和按交易次数排名的前 10 个底层证券。 [统计查询](../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java) · [交易统计面板](../frontend/src/pages/trade/TradeStatisticsPanel.jsx)

## 交易方向、触发来源和关联

`trade_type` 只存 `BUY` 或 `SELL`。`trade_trigger` 区分 `MANUAL`、`OPTION` 和 `MARKET_EVENT`，`trigger_ref_type` 进一步标识期权或市场事件类型。`trigger_ref_id` 为 0 时表示无关联。通过交易 API 新增记录时，缺省的触发字段分别填充为 `MANUAL`、`NONE`、`0`。券商同步来源由 `external_broker`、`external_id` 和 `sync_batch_id` 记录。 [交易服务](../backend/src/main/java/com/vortex/service/TradeRecordService.java) · [V10 触发字段](../backend/src/main/resources/db/migration/V10__add_trade_trigger_fields.sql) · [V12 交易类型枚举](../backend/src/main/resources/db/migration/V12__remove_legacy_enum_values.sql) · [V22 同步来源字段](../backend/src/main/resources/db/migration/V22__add_external_fields_to_trade_records.sql)

保存时校验来源与关联类型的组合。手动交易使用 `NONE` 和 0。市场事件交易须有非零关联 ID 和对应的事件类型。期权触发交易使用 `OPTION_EXPIRE`、`OPTION_EXERCISE` 或 `OPTION_ASSIGNED`，到期交易的成交价须为 0。行权或指派关联通常由股票或 ETF 侧记录指向期权侧交易。当前保存逻辑未校验每笔期权触发记录的关联 ID 是否符合这套约定，也未查询关联记录是否存在。 [保存校验](../backend/src/main/java/com/vortex/service/TradeRecordService.java)

交易详情页从未软删除的交易列表中按期权合约 `symbol` 精确匹配，并根据 `triggerRefId` 找出由该合约触发的股票和 ETF 记录。从股票或 ETF 详情进入时，页面先按关联 ID 请求触发源，再用同样的规则整理结果。若请求失败，仅尝试在已有列表中查找该 ID。页面按记录 ID 去重，当前记录在结果中时会高亮。该规则目前用于期权关联。 [详情页实现](../frontend/src/pages/trade/TradeRecordDetail.jsx)

## 持仓快照与数据核对

`GET /api/positions?date=YYYY-MM-DD` 按截止日期查询持仓，可另传 `brokerId`。服务读取截止日期当天及之前未软删除的交易，按 `(symbol, brokerId)` 聚合，`BUY` 加数量，`SELL` 减数量，过滤净数量为 0 的结果。负数量会保留，前端标记为可能异常。已生成的市场事件交易同样计入持仓。持仓页提示文字仍称暂未考虑市场事件，服务端的聚合规则以代码为准。 [持仓 API](../backend/src/main/java/com/vortex/controller/PositionController.java) · [持仓服务](../backend/src/main/java/com/vortex/service/PositionService.java) · [持仓页面](../frontend/src/pages/analysis/PositionSnapshotTab.jsx)

`GET /api/trade-records/verify` 对未软删除的交易执行六类核对，覆盖期权代码、港币结算非期权代码、期权被动操作价格及费用、美元结算股票代码、证券代码与资产类型一致性，以及触发类型与关联类型一致性。`errorCount` 是异常明细数，同一笔交易可能命中多条规则。核对接口只报告异常，不修改记录。 [核对服务](../backend/src/main/java/com/vortex/service/TradeVerificationService.java) · [结果 DTO](../backend/src/main/java/com/vortex/dto/TradeVerificationResult.java)

## 当前边界与历史决策

- 详情页的编辑、删除按钮由本地界面设置控制。交易列表的新增按钮和服务端 API 不受该开关控制。 [编辑设置](../frontend/src/contexts/TradeEditableContext.jsx) · [交易列表](../frontend/src/pages/trade/TradeRecords.jsx) · [交易详情](../frontend/src/pages/trade/TradeRecordDetail.jsx)
- 个股、账户和策略盈亏目前为占位页面。移动加权平均成本用于市场事件中的证券代码变更，独立盈亏口径仍未落地。 [个股盈亏](../frontend/src/pages/profit/StockProfitAnalysis.jsx) · [账户盈亏](../frontend/src/pages/profit/AccountProfitAnalysis.jsx) · [策略盈亏](../frontend/src/pages/profit/StrategyProfitAnalysis.jsx) · [市场事件定价](market-events.md#生成系统交易)
- 单元测试源码覆盖交易金额、触发类型校验、软删除、持仓聚合和六类异常核对。 [交易服务测试](../backend/src/test/java/com/vortex/service/TradeRecordServiceTest.java) · [持仓服务测试](../backend/src/test/java/com/vortex/service/PositionServiceTest.java) · [交易核对测试](../backend/src/test/java/com/vortex/service/TradeVerificationServiceTest.java)
- 设计背景见 [0001 交易触发来源](../design/0001-2026-03-08-trade-trigger.md)、[0003 TradeType 精简](../design/0003-2026-03-09-trade-type-refactor.md)、[0004 相关交易展示](../design/0004-2026-03-12-related-trades-display.md) 和 [0014 删除证券名称字段](../design/0014-2026-04-21-drop-symbol-name-fields.md)。
