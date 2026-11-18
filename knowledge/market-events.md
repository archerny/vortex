# 市场事件

## 录入与处理状态

市场事件包含拆股、证券代码变更和实物分红。前端三个标签页提供列表、新增并处理、受限撤销。后端还支持按 ID、证券代码或日期查询；仅未处理事件允许修改，已处理事件须先撤销再重建。事件日期使用 `LocalDate`，数据库存储为 `DATE`。事件被处理后记录 `processed` 和 `processedAt`。无符合条件的持仓或应生成的数量为零时，事件仍可标记为已处理。 [市场事件 API](../backend/src/main/java/com/vortex/controller/MarketEventController.java) · [市场事件实体](../backend/src/main/java/com/vortex/entity/BaseMarketEvent.java) · [市场事件页面](../frontend/src/pages/market-events/MarketEvents.jsx) · [V13 处理状态](../backend/src/main/resources/db/migration/V13__add_market_event_processing_fields.sql)

录入或修改时，服务端会查找相关证券最近一条未软删除的交易，缺少币种时据此补齐。拆股和实物分红查原证券，代码变更查旧证券代码。没有交易记录时拒绝保存。实物分红另须提供分红证券的 `dividendCurrency`，它可与原证券币种不同。 [拆股服务](../backend/src/main/java/com/vortex/service/StockSplitEventService.java) · [代码变更服务](../backend/src/main/java/com/vortex/service/SymbolChangeEventService.java) · [实物分红服务](../backend/src/main/java/com/vortex/service/DividendInKindEventService.java)

## 生成系统交易

三类事件均读取事件前一日的持仓，并处理满足条件的正持仓。生成的交易日期为事件当天，费用为 0，`trade_trigger` 为 `MARKET_EVENT`，关联类型和 ID 指向对应事件。持仓计算通过普通的 `BUY` 或 `SELL` 聚合这些交易。 [市场事件处理服务](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [持仓服务](../backend/src/main/java/com/vortex/service/PositionService.java)

| 事件 | 系统交易与数量规则 |
| --- | --- |
| 拆股 | 新数量按 `Math.round(持仓数量 × ratioTo / ratioFrom)` 计算。增量为正时生成一笔 `BUY`，价格和金额均为 0。数量不增加时不会生成记录。 |
| 代码变更 | 对持有旧代码的非期权持仓生成等量的旧代码 `SELL` 和新代码 `BUY`。两笔交易都以旧证券截至前一日的移动加权平均成本计价。 |
| 实物分红 | 分红数量为 `floor(持仓数量 × ratioTo / ratioFrom)`。数量为正时生成分红证券的股票 `BUY`，币种取 `dividendCurrency`。价格取 `fairValuePerShare`，缺省时按 0，金额为价格 × 分红数量。 |

代码变更的平均成本按原证券和券商回放历史交易计算。买入金额增加成本池；当前正持仓恰好全部卖出时，直接结清成本池并将数量减为零，后续建仓不再携带旧的舍入尾差。部分卖出仍按 6 位小数、`HALF_UP` 的均价减记，最终均价仍以 4 位小数、`HALF_UP` 舍入，两笔转移交易的金额仍为该均价乘持仓数量。此处成本池仅为回放中的局部变量，计算用于代码变更交易定价，不是日常持仓查询维护的成本账户。 [平均成本实现](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [0028 清仓成本池方案](../design/0028-2026-11-16-clear-cost-pool-on-liquidation.md) · [交易与持仓](trades-and-positions.md)

[市场事件回归测试](../backend/src/test/java/com/vortex/service/MarketEventProcessingServiceTest.java) 使用真实事件处理与持仓计算、Mockito 仓储及合成交易，从证券代码变更入口验证清仓、重新建仓和生成交易。原清仓修复的内存运行记录见 [归档](../docs/TODO-ARCHIVE.md)，真实事务的后续验证入口见 [e2e](../e2e/README.md#ordered-market-event-check)。卖超、负持仓、同日成交排序及更广的计价精度规则仍保持原有边界；清仓修复不代表这些口径均已解决。

## 顺序处理与关联证券历史锁

事件 D 只生成自身的交易，不再自动级联重算。处理前要求关联证券不存在 D 当天或之后的有效普通交易，也不存在同日或更晚的已处理关联事件。同日无关事件允许；同日有关事件拒绝，不推断日内先后。公开处理入口持锁后按类型和 ID 重读，已处理则幂等返回，已撤销或不存在则拒绝。 [单事件处理](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java)

| 事件 | 关联证券，覆盖所有券商 |
| --- | --- |
| 拆股 | 原证券 |
| 代码变更 | 旧代码和新代码 |
| 实物分红 | 原证券和分红证券 |

处理成功后，关联证券在 D 之前的正式交易不可新增、修改或删除；D 当天的普通交易可随后录入。无关证券仍可补录，按实际 symbol 匹配，不将 underlyingSymbol 相同的期权合约视为同一证券。修改交易同时检查旧、新范围，不能通过移动日期、证券或券商绕过；其他字段编辑也无隐式例外。MARKET_EVENT 行只能随所属事件维护，普通 CRUD 不可单独创建、修改、删除或改写来源。前端详情也不提供其编辑／删除按钮。 [统一边界](../backend/src/main/java/com/vortex/service/MarketEventHistoryService.java) · [交易入口](../backend/src/main/java/com/vortex/service/TradeRecordService.java)

历史锁直接从已处理且未撤销的三类事件推导，没有独立锁定日期表。零持仓、零输出的已处理事件同样生效；多个约束叠加。撤销只允许关联范围的尾部事件：没有同日或更晚的已处理关联事件，也没有 D 当天或之后的有效普通交易。撤销在同一事务删除本事件的 MARKET_EVENT 派生行、软删除事件并清除处理状态，其他事件及其约束保留。用户先回退后续相关数据，再撤销、修正历史并按日期重新创建；不会自动级联撤销。

## 并发、同步与旧状态

账本短写事务共用 PostgreSQL 事务级 advisory mutex，等待后再查询边界；这只协调并发写入，历史限制仍按关联证券判断。锁与写入使用同一事务，随提交或回滚释放，实际隔离级别须为 READ COMMITTED。手工 CRUD、三类事件、正式导入、IBKR 关联回填、批次创建和清理均接入；外层不得持锁调用另一个需要该锁的 REQUIRES_NEW Worker。 [写入互斥](../backend/src/main/java/com/vortex/service/LedgerWriteLock.java)

存在 PENDING／PROCESSING／CLEANUP_FAILED 批次时，事件新增处理、修改和撤销均拒绝。IBKR 同内容重复仍跳过，新交易命中历史锁则整批失败并清理；Tiger 使用相同边界，原时间格式缺陷仍单独待办。清理先核对批次归属、状态和本批有效交易的历史限制，再执行任何删除；COMPLETED 不允许清理，旧 FAILED 残留若被事件锁定也拒绝删除。 [同步生命周期](broker-sync/lifecycle.md) · [恢复说明](../docs/broker-sync/recovery.md)

交易／事件 HTTP 写入冲突返回 409，保留 status/message，并提供 code、blockingEventId、blockingEventType、eventDate、symbols；页面显示原因及可用的阻挡事件信息。同步保留既有 UNRECOGNIZED 分类。 [响应契约](../backend/src/main/java/com/vortex/controller/MarketEventConflictAdvice.java) · [操作与错误码](../docs/market-events.md)

不在启动时重算、删除或补造历史结果；processed=true 是保守锁定依据，不证明旧结果正确。未处理事件已有派生行时拒绝修改、处理与撤销；创建请求不能指定 ID、审计字段、已处理／删除状态或显式空状态。存量检查与异常处置见 [操作指南](../docs/market-events.md#存量切换)。既有验收使用合成数据，没有检查或修复实际用户账本。

规则来源：[0035](../design/0035-2026-11-17-ordered-market-events-and-history-locks.md)、[0036](../design/0036-2026-11-17-market-event-write-coordination.md)、[0037](../design/0037-2026-11-17-ordered-event-state-validation.md)、[0038](../design/0038-2026-11-17-sync-cleanup-history-guard.md)。单测及独立临时数据库的真实事务、并发、失败回滚验收见 [验证指南](../e2e/README.md#ordered-market-event-check)。

## 数据演进与历史决策

- V8 建立三张事件表，V13 补充处理状态和实物分红公允价。 [V8 市场事件表](../backend/src/main/resources/db/migration/V8__create_market_event_tables.sql) · [V13 处理字段](../backend/src/main/resources/db/migration/V13__add_market_event_processing_fields.sql)
- 实物分红现用整数比例。V16 替换旧的每股分红数量字段，并将迁移时既有的非空比例置为 1:1。V17 添加分红币种，V18 增加非空约束。 [V16 比例迁移](../backend/src/main/resources/db/migration/V16__refactor_dividend_in_kind_ratio_fields.sql) · [V17 分红币种](../backend/src/main/resources/db/migration/V17__add_dividend_currency_to_dividend_in_kind.sql) · [V18 非空约束](../backend/src/main/resources/db/migration/V18__make_dividend_currency_not_null.sql)
- V25 删除市场事件与交易中的证券名称列。当前录入和展示使用证券代码。 [V25 删除名称字段](../backend/src/main/resources/db/migration/V25__drop_symbol_name_fields.sql)
- 设计沿革见 [0002 市场事件处理](../design/0002-2026-03-08-market-event-processing.md)、[0005 实物分红 Tab 重构](../design/0005-2026-03-14-dividend-in-kind-tab-refactor.md) 和 [0014 删除证券名称字段](../design/0014-2026-04-21-drop-symbol-name-fields.md)。
