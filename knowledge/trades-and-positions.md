# 交易与持仓

## 交易记录

`/api/trade-records` 支持新增、修改、软删除和查询。查询可按账户（`brokerAccountId`）、券商、证券类型、策略、证券代码或日期范围筛选。交易日期使用 `LocalDate`，数据库存储为 `DATE`。交易支持股票、ETF、看涨期权和看跌期权，币种为 `CNY`、`HKD` 或 `USD`。底层证券代码 `underlyingSymbol` 必填，交易记录不再保存证券名称。 [交易 API](../backend/src/main/java/com/vortex/controller/TradeRecordController.java) · [交易实体](../backend/src/main/java/com/vortex/entity/TradeRecord.java) · [V5 底层证券代码约束](../backend/src/main/resources/db/migration/V5__alter_underlying_symbol_not_null.sql) · [V25 删除名称列](../backend/src/main/resources/db/migration/V25__drop_symbol_name_fields.sql)

通过交易 API 新增或修改记录时，服务端按数量 × 单价计算成交金额。看涨和看跌期权再乘合约乘数 100，费用单列。交易数量为整数，数据库中的单价保留 4 位小数，成交金额保留 2 位。费用从 V29 起使用不固定小数位的 PostgreSQL `NUMERIC`，后端以 `BigDecimal` 传递及汇总，不截断或舍入，在数据库支持范围内保留来源数值精度。券商同步和市场事件有各自的记录生成入口。 [交易服务](../backend/src/main/java/com/vortex/service/TradeRecordService.java) · [交易表定义](../backend/src/main/resources/db/migration/V3__create_trade_records.sql) · [费用迁移](../backend/src/main/resources/db/migration/V29__preserve_trade_fee_precision.sql) · [市场事件](market-events.md)

交易响应保留原 JSON 数字字段 `fee`，另提供只读十进制字符串 `feeExact`；统计同样为各币种保留原数字字段并补充对应的 `totalFeeUSDExact`、`totalFeeCNYExact`、`totalFeeHKDExact`。前端列表、详情、统计和编辑统一使用原始精确费用文本，不固定小数位、不补齐两位；编辑以原字符串提交 `fee`，避免 JavaScript 浮点转换，旧数字请求仍可接收。费用列排序也按十进制精确值比较。V29 只扩展列类型，不修复、重新同步或重算历史交易，原两位费用数值保持不变。部署先应用迁移，再导入高精度费用；应用回退时保留扩展后的列，避免降精度。 [0029 费用精度方案](../design/0029-2026-11-17-preserve-trade-fee-precision.md) · [0030 完整精度展示](../design/0030-2026-11-17-display-exact-trade-fees.md) · [独立迁移验证](../e2e/README.md#fee-precision-migration-check)

统计接口 `GET /api/trade-records/statistics` 统计未软删除记录的总数、各证券类型的记录数、分币种的费用，以及底层证券去重数和按交易次数排名的前 10 个底层证券。 [统计查询](../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java) · [交易统计面板](../frontend/src/pages/trade/TradeStatisticsPanel.jsx)

## 交易方向、触发来源和关联

`trade_type` 只存 `BUY` 或 `SELL`。`trade_trigger` 区分 `MANUAL`、`OPTION` 和 `MARKET_EVENT`，`trigger_ref_type` 进一步标识期权或市场事件类型。`trigger_ref_id` 为 0 时表示无关联。通过交易 API 新增记录时，缺省的触发字段分别填充为 `MANUAL`、`NONE`、`0`。券商同步来源由 `external_broker`、`external_id` 和 `sync_batch_id` 记录。 [交易服务](../backend/src/main/java/com/vortex/service/TradeRecordService.java) · [V10 触发字段](../backend/src/main/resources/db/migration/V10__add_trade_trigger_fields.sql) · [V12 交易类型枚举](../backend/src/main/resources/db/migration/V12__remove_legacy_enum_values.sql) · [V22 同步来源字段](../backend/src/main/resources/db/migration/V22__add_external_fields_to_trade_records.sql)

保存时校验来源与关联类型的组合。手动交易使用 `NONE` 和 0。市场事件交易须有非零关联 ID 和对应的事件类型。期权触发交易使用 `OPTION_EXPIRE`、`OPTION_EXERCISE` 或 `OPTION_ASSIGNED`，到期交易的成交价须为 0。行权或指派关联通常由股票或 ETF 侧记录指向期权侧交易。期权触发记录指定非零关联 ID 时，保存校验来源记录存在、未删除且属于同一本地账户；被有效期权关联交易引用的来源记录，修改账户时也检查反向引用，不能留下跨账户关联；软删除的引用不阻止移动。尚不按该入口完整核对合约或交割业务条件。 [保存校验](../backend/src/main/java/com/vortex/service/TradeRecordService.java)

交易详情页先限定同一本地账户，再从未软删除的交易列表中按期权合约 `symbol` 精确匹配，并根据 `triggerRefId` 找出由该合约触发的股票和 ETF 记录。从股票或 ETF 详情进入时，页面先按关联 ID 请求触发源，确认同账户后再用同样的规则整理结果。若请求失败，仅尝试在已有列表中查找该 ID。页面按记录 ID 去重，当前记录在结果中时会高亮。该规则目前用于期权关联。 [详情页实现](../frontend/src/pages/trade/TradeRecordDetail.jsx)

## 账户归属

V33 新增本地 `broker_account_id`，交易和流水保留 `broker_id`，组合外键保证二者一致。创建／更新交易与创建流水时优先指定 `brokerAccountId`，服务端据此填充券商；只传旧 `brokerId` 时，必须恰好有一个账户，否则返回 400。响应包含本地账户主键及只读账户资料，修改账户后即返回新的账户关联。新建托管账户须有真实账户 ID，迁移的待补全账户保留旧账本并允许手工记账；同步须先补齐 ID。历史金额、数量、日期与事件关联不重算。详见 [账户指南](../docs/accounts.md) 和 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。

## 历史写入边界

已处理、未撤销的市场事件锁定其关联证券在事件日之前的交易，覆盖所有券商；无关证券仍可补录，事件日普通交易可在事件处理后录入。交易新增检查新记录，修改先检查原记录再检查目标证券和日期，软删除检查原记录。费用、策略等其他编辑同样受约束。MARKET_EVENT 记录不接受普通 CRUD 修改、删除、单独创建或改写来源，须通过撤销所属事件维护。冲突返回 HTTP 409 和原因，详情页隐藏系统交易的编辑／删除入口。 [边界服务](../backend/src/main/java/com/vortex/service/MarketEventHistoryService.java) · [完整事件规则](market-events.md#顺序处理与关联证券历史锁) · [0035](../design/0035-2026-11-17-ordered-market-events-and-history-locks.md)

## 持仓快照与数据核对

`GET /api/positions?date=YYYY-MM-DD` 按截止日期查询持仓，可另传 `brokerId` 或 `brokerAccountId`（两者同时传入时均须符合）。服务读取截止日期当天及之前未软删除的交易，按 `(symbol, brokerId, brokerAccountId)` 聚合，`BUY` 加数量，`SELL` 减数量，过滤净数量为 0 的结果。负数量会保留，前端标记为可能异常。已生成的市场事件交易同样计入持仓。持仓页提示文字仍称暂未考虑市场事件，服务端的聚合规则以代码为准。 [持仓 API](../backend/src/main/java/com/vortex/controller/PositionController.java) · [持仓服务](../backend/src/main/java/com/vortex/service/PositionService.java) · [持仓页面](../frontend/src/pages/analysis/PositionSnapshotTab.jsx)

`GET /api/trade-records/verify` 对未软删除的交易执行六类核对，覆盖期权代码、港币结算非期权代码、期权被动操作价格及费用、美元结算股票代码、证券代码与资产类型一致性，以及触发类型与关联类型一致性。`errorCount` 是异常明细数，同一笔交易可能命中多条规则。核对接口只报告异常，不修改记录。 [核对服务](../backend/src/main/java/com/vortex/service/TradeVerificationService.java) · [结果 DTO](../backend/src/main/java/com/vortex/dto/TradeVerificationResult.java)

## 当前边界与历史决策

- 详情页的编辑、删除按钮由本地界面设置控制。交易列表的新增按钮和服务端 API 不受该开关控制。 [编辑设置](../frontend/src/contexts/TradeEditableContext.jsx) · [交易列表](../frontend/src/pages/trade/TradeRecords.jsx) · [交易详情](../frontend/src/pages/trade/TradeRecordDetail.jsx)
- 个股、账户和策略盈亏目前为占位页面。移动加权平均成本用于市场事件中的证券代码变更，独立盈亏口径仍未落地。 [个股盈亏](../frontend/src/pages/profit/StockProfitAnalysis.jsx) · [账户盈亏](../frontend/src/pages/profit/AccountProfitAnalysis.jsx) · [策略盈亏](../frontend/src/pages/profit/StrategyProfitAnalysis.jsx) · [市场事件定价](market-events.md#生成系统交易)
- 单元测试源码覆盖交易金额、触发类型校验、软删除、持仓聚合和六类异常核对；[市场事件成本回归](../backend/src/test/java/com/vortex/service/MarketEventProcessingServiceTest.java) 另验证历史成本回放中完全清仓会结清成本池，随后重新建仓和证券代码变更不携带旧尾差。该修复只作用于代码变更的成本回放，不改变持仓数量聚合，设计见 [0028](../design/0028-2026-11-16-clear-cost-pool-on-liquidation.md)。 [交易服务测试](../backend/src/test/java/com/vortex/service/TradeRecordServiceTest.java) · [持仓服务测试](../backend/src/test/java/com/vortex/service/PositionServiceTest.java) · [交易核对测试](../backend/src/test/java/com/vortex/service/TradeVerificationServiceTest.java)
- 设计背景见 [0001 交易触发来源](../design/0001-2026-03-08-trade-trigger.md)、[0003 TradeType 精简](../design/0003-2026-03-09-trade-type-refactor.md)、[0004 相关交易展示](../design/0004-2026-03-12-related-trades-display.md) 和 [0014 删除证券名称字段](../design/0014-2026-04-21-drop-symbol-name-fields.md)。
