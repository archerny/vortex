# 对象列表

Client 对象

[](./appendix-object-index.md#client-%E5%AF%B9%E8%B1%A1)

================================================================================================

| 对象名 | 描述  |
| --- | --- |
| PushClient | 订阅类。websocket长链接推送使用该类处理, 如实时行情推送、资产/订单/持仓变动推送 |
| QuoteClient | 行情类。行情有关接口使用该类处理, 如请求k线、实时价格 |
| TradeClient | 交易类。交易有关接口使用该类处理, 如下单、改单 |

账户类

[](./appendix-object-index.md#%E8%B4%A6%E6%88%B7%E7%B1%BB)

============================================================================================

**综合/模拟账户**

| 对象名 | 引用路径 | 描述  |
| --- | --- | --- |
| [PortfolioAccount](./appendix-object-detail.md#portfolioaccount) | tigeropen.trade.domain.prime\_account.PortfolioAccount | 账户资产信息 |
| [Segment](./appendix-object-detail.md#segment) | tigeropen.trade.domain.prime\_account.Segment | 按交易品种划分的资产(期货/股票) |
| [CurrencyAsset](./appendix-object-detail.md#currencyasset) | tigeropen.trade.domain.prime\_account.CurrencyAsset | 现金资产信息 |

**环球账户**

| 对象名 | 引用路径 | 描述  |
| --- | --- | --- |
| [PortfolioAccount](./appendix-object-detail.md#portfolioaccountglobal) | tigeropen.trade.domain.account.PortfolioAccount | 账户资产信息 |
| [Account](./appendix-object-detail.md#accountglobal) | tigeropen.trade.domain.account.Account | 汇总的账户信息 |
| [CommoditySegment](./appendix-object-detail.md#commoditysegmentglobal) | tigeropen.trade.domain.account.CommoditySegment | 期货资产信息 |
| [SecuritySegment](./appendix-object-detail.md#securitysegmentglobal) | tigeropen.trade.domain.account.SecuritySegment | 股票资产信息 |
| [MarketValue](./appendix-object-detail.md#marketvalueglobal) | tigeropen.trade.domain.account.MarketValue | 市值对象 |

交易类

[](./appendix-object-index.md#%E4%BA%A4%E6%98%93%E7%B1%BB)

============================================================================================

| 对象名 | 引用路径 | 描述  |
| --- | --- | --- |
| [Position](./appendix-object-detail.md#position) | tigeropen.trade.domain.position.Position | 持仓对象 |
| [Order](./appendix-object-detail.md#order) | tigeropen.trade.domain.order.Order | 订单对象 |
| [OrderLeg](./appendix-object-detail.md#orderleg) | tigeropen.trade.domain.order.OrderLeg | 附加订单对象 |
| [AlgoParams](./appendix-object-detail.md#algoparams) | tigeropen.trade.domain.order.AlgoParams | 算法订单(VWAP/TWAP)参数 |
| [Contract](./appendix-object-detail.md#contract) | tigeropen.trade.domain.contract.Contract | 合约对象 |

行情类

[](./appendix-object-index.md#%E8%A1%8C%E6%83%85%E7%B1%BB)

============================================================================================

| 对象名 | 引用路径 | 描述  |
| --- | --- | --- |
| [MarketStatus](./appendix-object-detail.md#marketstatus) | tigeropen.quote.domain.market\_status.MarketStatus | 市场状态对象 |
| [OptionFilter](./appendix-object-detail.md#optionfilter) | tigeropen.quote.domain.filter.OptionFilter | 期权过滤Filter |
