# 市场事件

## 录入与处理状态

市场事件包含拆股、证券代码变更和实物分红。前端三个标签页提供列表和新增。后端还支持按 ID、证券代码或日期查询，以及按 ID 修改和软删除。事件日期使用 `LocalDate`，数据库存储为 `DATE`。事件被处理后记录 `processed` 和 `processedAt`。无符合条件的持仓或应生成的数量为零时，事件仍可标记为已处理。 [市场事件 API](../backend/src/main/java/com/vortex/controller/MarketEventController.java) · [市场事件实体](../backend/src/main/java/com/vortex/entity/BaseMarketEvent.java) · [市场事件页面](../frontend/src/pages/market-events/MarketEvents.jsx) · [V13 处理状态](../backend/src/main/resources/db/migration/V13__add_market_event_processing_fields.sql)

录入或修改时，服务端会查找相关证券最近一条未软删除的交易，缺少币种时据此补齐。拆股和实物分红查原证券，代码变更查旧证券代码。没有交易记录时拒绝保存。实物分红另须提供分红证券的 `dividendCurrency`，它可与原证券币种不同。 [拆股服务](../backend/src/main/java/com/vortex/service/StockSplitEventService.java) · [代码变更服务](../backend/src/main/java/com/vortex/service/SymbolChangeEventService.java) · [实物分红服务](../backend/src/main/java/com/vortex/service/DividendInKindEventService.java)

## 生成系统交易

三类事件均读取事件前一日的持仓，并处理满足条件的正持仓。生成的交易日期为事件当天，费用为 0，`trade_trigger` 为 `MARKET_EVENT`，关联类型和 ID 指向对应事件。持仓计算通过普通的 `BUY` 或 `SELL` 聚合这些交易。 [市场事件处理服务](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [持仓服务](../backend/src/main/java/com/vortex/service/PositionService.java)

| 事件 | 系统交易与数量规则 |
| --- | --- |
| 拆股 | 新数量按 `Math.round(持仓数量 × ratioTo / ratioFrom)` 计算。增量为正时生成一笔 `BUY`，价格和金额均为 0。数量不增加时不会生成记录。 |
| 代码变更 | 对持有旧代码的非期权持仓生成等量的旧代码 `SELL` 和新代码 `BUY`。两笔交易都以旧证券截至前一日的移动加权平均成本计价。 |
| 实物分红 | 分红数量为 `floor(持仓数量 × ratioTo / ratioFrom)`。数量为正时生成分红证券的股票 `BUY`，币种取 `dividendCurrency`。价格取 `fairValuePerShare`，缺省时按 0，金额为价格 × 分红数量。 |

代码变更的平均成本按原证券和券商计算。买入金额增加成本池，卖出按当时平均成本减记。最终平均成本以 4 位小数、`HALF_UP` 舍入。该成本计算用于代码变更交易定价。 [平均成本实现](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [交易与持仓](trades-and-positions.md)

## 历史重算的范围

新增、修改或软删除事件会触发重算。新增从事件日期开始，修改从新旧日期中较早的一天开始并合并新旧标的。软删除时，服务先清理被删除事件关联的系统交易，再从该事件日期重算其余相关事件。事件变更和派生交易处理在同一事务中完成。 [事件处理与事务](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [系统交易删除查询](../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java)

重算先按传入的标的集合与起始日期查询未软删除的事件，按日期升序排列。同日优先级依次为代码变更、拆股、实物分红。服务按事件类型和 ID 物理删除旧的系统交易，重置处理状态，再逐个生成记录并标记已处理。查询以这次传入的标的集合为限，不沿后续代码变更或分红递归扩展。由于生成记录时仍取前一日的持仓，同日排序不会让同日拆股结果计入分红基数。交易记录服务中的普通增、改、软删不会自动启动这条重算流程。 [重算实现](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [交易写入](../backend/src/main/java/com/vortex/service/TradeRecordService.java)

## 数据演进与历史决策

- V8 建立三张事件表，V13 补充处理状态和实物分红公允价。 [V8 市场事件表](../backend/src/main/resources/db/migration/V8__create_market_event_tables.sql) · [V13 处理字段](../backend/src/main/resources/db/migration/V13__add_market_event_processing_fields.sql)
- 实物分红现用整数比例。V16 替换旧的每股分红数量字段，并将迁移时既有的非空比例置为 1:1。V17 添加分红币种，V18 增加非空约束。 [V16 比例迁移](../backend/src/main/resources/db/migration/V16__refactor_dividend_in_kind_ratio_fields.sql) · [V17 分红币种](../backend/src/main/resources/db/migration/V17__add_dividend_currency_to_dividend_in_kind.sql) · [V18 非空约束](../backend/src/main/resources/db/migration/V18__make_dividend_currency_not_null.sql)
- V25 删除市场事件与交易中的证券名称列。当前录入和展示使用证券代码。 [V25 删除名称字段](../backend/src/main/resources/db/migration/V25__drop_symbol_name_fields.sql)
- 设计沿革见 [0002 市场事件处理](../design/0002-2026-03-08-market-event-processing.md)、[0005 实物分红 Tab 重构](../design/0005-2026-03-14-dividend-in-kind-tab-refactor.md) 和 [0014 删除证券名称字段](../design/0014-2026-04-21-drop-symbol-name-fields.md)。
