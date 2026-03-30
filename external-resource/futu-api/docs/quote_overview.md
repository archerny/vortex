# 行情接口总览 | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/quote/overview.html

[#](https://openapi.futunn.com/futu-api-doc/quote/overview.html#426)
 行情接口总览
============================================================================

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [subscribe](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [unsubscribe](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4908) | 取消订阅 |
| [unsubscribe\_all](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2489) | 取消所有订阅 |
| [query\_subscription](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html) | 查询订阅信息 |
| 推送回调 | [StockQuoteHandlerBase](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html) | 报价推送 |
| [OrderBookHandlerBase](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html) | 摆盘推送 |
| [CurKlineHandlerBase](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html) | K 线推送 |
| [TickerHandlerBase](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html) | 逐笔推送 |
| [RTDataHandlerBase](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html) | 分时推送 |
| [BrokerHandlerBase](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html) | 经纪队列推送 |
| 拉取  | [get\_market\_snapshot](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html) | 获取市场快照 |
| [get\_stock\_quote](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [get\_order\_book](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html) | 获取实时摆盘数据 |
| [get\_cur\_kline](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html) | 实时获取指定股票最近 num 个 K 线数据 |
| [get\_rt\_data](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html) | 获取指定股票的分时数据 |
| [get\_rt\_ticker](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [get\_broker\_queue](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html) | 获取股票的经纪队列 |
| 基本数据 |     | [get\_market\_state](https://openapi.futunn.com/futu-api-doc/quote/get-market-state.html) | 获取股票对应市场的市场状态 |
| [get\_capital\_flow](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html) | 获取个股资金流向 |
| [get\_capital\_distribution](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html) | 获取个股资金分布 |
| [get\_owner\_plate](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html) | 获取单支或多支股票的所属板块信息列表 |
| [request\_history\_kline](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html) | 获取 K 线，不需要事先下载 K 线数据 |
| [get\_rehab](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [get\_option\_expiration\_date](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html) | 通过标的股票，查询期权链的所有到期日 |
| [get\_option\_chain](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html) | 通过标的股查询期权 |
| [get\_warrant](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html) | 拉取窝轮和相关衍生品数据接口 |
| [get\_referencestock\_list](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html) | 获取证券的关联数据 |
| [get\_future\_info](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html) | 获取期货合约资料 |
| 全市场筛选 |     | [get\_stock\_filter](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html) | 获取条件选股 |
| [get\_plate\_stock](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html) | 获取特定板块下的股票列表 |
| [get\_plate\_list](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html) | 获取板块集合下的子板块列表 |
| [get\_stock\_basicinfo](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html) | 获取指定市场中特定类型或特定股票的基本信息 |
| [get\_ipo\_list](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html) | 获取指定市场的 ipo 列表 |
| [get\_global\_state](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html) | 获取全局市场状态 |
| [request\_trading\_days](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html) | 获取交易日历 |
| 个性化 |     | [get\_history\_kl\_quota](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [set\_price\_reminder](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html) | 设置到价提醒 |
| [get\_price\_reminder](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [get\_user\_security\_group](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html) | 获取自选股分组列表 |
| [get\_user\_security](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html) | 获取指定分组的自选股列表 |
| [modify\_user\_security](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html) | 修改指定分组的自选股列表 |
| [PriceReminderHandlerBase](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4908) | 取消订阅 |
| [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2489) | 取消所有订阅 |
| [GetSubInfo](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html) | 查询订阅信息 |
| 推送回调 | [UpdateBasicQot](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html) | 报价推送 |
| [UpdateOrderBook](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html) | 摆盘推送 |
| [UpdateKL](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html) | K 线推送 |
| [UpdateTicker](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html) | 逐笔推送 |
| [UpdateRT](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html) | 分时推送 |
| [UpdateBroker](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html) | 经纪队列推送 |
| 拉取  | [GetSecuritySnapshot](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html) | 获取市场快照 |
| [GetBasicQot](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [GetOrderBook](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html) | 获取实时摆盘数据 |
| [GetKL](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html) | 实时获取指定股票最近 num 个 K 线数据 |
| [GetRT](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html) | 获取指定股票的分时数据 |
| [GetTicker](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [GetBroker](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html) | 获取股票的经纪队列 |
| 基本数据 |     | [GetMarketState](https://openapi.futunn.com/futu-api-doc/quote/get-market-state.html) | 获取股票对应市场的市场状态 |
| [GetCapitalFlow](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html) | 获取个股资金流向 |
| [GetCapitalDistribution](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html) | 获取个股资金分布 |
| [GetOwnerPlate](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html) | 获取单支或多支股票的所属板块信息列表 |
| [RequestHistoryKL](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html) | 获取 K 线，不需要事先下载 K 线数据 |
| [RequestRehab](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [GetOptionExpirationDate](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html) | 通过标的股票，查询期权链的所有到期日 |
| [GetOptionChain](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html) | 通过标的股查询期权 |
| [GetWarrant](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html) | 拉取窝轮和相关衍生品数据接口 |
| [GetReference](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html) | 获取证券的关联数据 |
| [GetFutureInfo](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html) | 获取期货合约资料 |
| 全市场筛选 |     | [StockFilter](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html) | 获取条件选股 |
| [GetPlateSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html) | 获取特定板块下的股票列表 |
| [GetPlateSet](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html) | 获取板块集合下的子板块列表 |
| [GetStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html) | 获取指定市场中特定类型或特定股票的基本信息 |
| [GetIpoList](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html) | 获取指定市场的 ipo 列表 |
| [GetGlobalState](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html) | 获取全局市场状态 |
| [RequestTradeDate](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html) | 在线请求交易日 |
| 个性化 |     | [RequestHistoryKLQuota](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [SetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html) | 设置到价提醒 |
| [GetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [GetUserSecurityGroup](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html) | 获取自选股分组列表 |
| [GetUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html) | 获取指定分组的自选股列表 |
| [ModifyUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html) | 修改指定分组的自选股列表 |
| [UpdatePriceReminder](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4908) | 取消订阅 |
| [sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2489) | 取消所有订阅 |
| [getSubInfo](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html) | 查询订阅信息 |
| 推送回调 | [updateBasicQot](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html) | 报价推送 |
| [updateOrderBook](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html) | 摆盘推送 |
| [updateKL](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html) | K 线推送 |
| [updateTicker](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html) | 逐笔推送 |
| [updateRT](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html) | 分时推送 |
| [updateBroker](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html) | 经纪队列推送 |
| 拉取  | [getSecuritySnapshot](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html) | 获取市场快照 |
| [getBasicQot](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [getOrderBook](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html) | 获取实时摆盘数据 |
| [getKL](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html) | 实时获取指定股票最近 num 个 K 线数据 |
| [getRT](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html) | 获取指定股票的分时数据 |
| [getTicker](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [getBroker](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html) | 获取股票的经纪队列 |
| 基本数据 |     | [getMarketState](https://openapi.futunn.com/futu-api-doc/quote/get-market-state.html) | 获取股票对应市场的市场状态 |
| [getCapitalFlow](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html) | 获取个股资金流向 |
| [getCapitalDistribution](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html) | 获取个股资金分布 |
| [getOwnerPlate](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html) | 获取单支或多支股票的所属板块信息列表 |
| [requestHistoryKL](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html) | 获取 K 线，不需要事先下载 K 线数据 |
| [requestRehab](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [getOptionExpirationDate](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html) | 通过标的股票，查询期权链的所有到期日 |
| [getOptionChain](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html) | 通过标的股查询期权 |
| [getWarrant](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html) | 拉取窝轮和相关衍生品数据接口 |
| [getReference](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html) | 获取证券的关联数据 |
| [getFutureInfo](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html) | 获取期货合约资料 |
| 全市场筛选 |     | [stockFilter](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html) | 获取条件选股 |
| [getPlateSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html) | 获取特定板块下的股票列表 |
| [getPlateSet](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html) | 获取板块集合下的子板块列表 |
| [getStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html) | 获取指定市场中特定类型或特定股票的基本信息 |
| [getIpoList](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html) | 获取指定市场的 ipo 列表 |
| [getGlobalState](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html) | 获取全局市场状态 |
| [requestTradeDate](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html) | 在线请求交易日 |
| 个性化 |     | [requestHistoryKLQuota](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [setPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html) | 设置到价提醒 |
| [getPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [getUserSecurityGroup](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html) | 获取自选股分组列表 |
| [getUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html) | 获取指定分组的自选股列表 |
| [modifyUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html) | 修改指定分组的自选股列表 |
| [updatePriceReminder](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4908) | 取消订阅 |
| [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2489) | 取消所有订阅 |
| [GetSubInfo](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html) | 查询订阅信息 |
| 推送回调 | [UpdateBasicQot](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html) | 报价推送 |
| [UpdateOrderBook](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html) | 摆盘推送 |
| [UpdateKL](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html) | K 线推送 |
| [UpdateTicker](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html) | 逐笔推送 |
| [UpdateRT](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html) | 分时推送 |
| [UpdateBroker](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html) | 经纪队列推送 |
| 拉取  | [GetSecuritySnapshot](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html) | 获取市场快照 |
| [GetBasicQot](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [GetOrderBook](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html) | 获取实时摆盘数据 |
| [GetKL](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html) | 实时获取指定股票最近 num 个 K 线数据 |
| [GetRT](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html) | 获取指定股票的分时数据 |
| [GetTicker](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [GetBroker](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html) | 获取股票的经纪队列 |
| 基本数据 |     | [GetMarketState](https://openapi.futunn.com/futu-api-doc/quote/get-market-state.html) | 获取股票对应市场的市场状态 |
| [GetCapitalFlow](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html) | 获取个股资金流向 |
| [GetCapitalDistribution](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html) | 获取个股资金分布 |
| [GetOwnerPlate](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html) | 获取单支或多支股票的所属板块信息列表 |
| [RequestHistoryKL](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html) | 获取 K 线，不需要事先下载 K 线数据 |
| [RequestRehab](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [GetOptionExpirationDate](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html) | 通过标的股票，查询期权链的所有到期日 |
| [GetOptionChain](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html) | 通过标的股查询期权 |
| [GetWarrant](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html) | 拉取窝轮和相关衍生品数据接口 |
| [GetReference](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html) | 获取证券的关联数据 |
| [GetFutureInfo](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html) | 获取期货合约资料 |
| 全市场筛选 |     | [StockFilter](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html) | 获取条件选股 |
| [GetPlateSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html) | 获取特定板块下的股票列表 |
| [GetPlateSet](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html) | 获取板块集合下的子板块列表 |
| [GetStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html) | 获取指定市场中特定类型或特定股票的基本信息 |
| [GetIpoList](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html) | 获取指定市场的 ipo 列表 |
| [GetGlobalState](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html) | 获取全局市场状态 |
| [RequestTradeDate](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html) | 在线请求交易日 |
| 个性化 |     | [RequestHistoryKLQuota](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [SetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html) | 设置到价提醒 |
| [GetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [GetUserSecurityGroup](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html) | 获取自选股分组列表 |
| [GetUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html) | 获取指定分组的自选股列表 |
| [ModifyUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html) | 修改指定分组的自选股列表 |
| [UpdatePriceReminder](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#4908) | 取消订阅 |
| [Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html#2489) | 取消所有订阅 |
| [GetSubInfo](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html) | 查询订阅信息 |
| 推送回调 | [UpdateBasicQot](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html) | 报价推送 |
| [UpdateOrderBook](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html) | 摆盘推送 |
| [UpdateKL](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html) | K 线推送 |
| [UpdateTicker](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html) | 逐笔推送 |
| [UpdateRT](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html) | 分时推送 |
| [UpdateBroker](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html) | 经纪队列推送 |
| 拉取  | [GetSecuritySnapshot](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html) | 获取市场快照 |
| [GetBasicQot](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [GetOrderBook](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html) | 获取实时摆盘数据 |
| [GetKL](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html) | 实时获取指定股票最近 num 个 K 线数据 |
| [GetRT](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html) | 获取指定股票的分时数据 |
| [GetTicker](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [GetBroker](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html) | 获取股票的经纪队列 |
| 基本数据 |     | [GetMarketState](https://openapi.futunn.com/futu-api-doc/quote/get-market-state.html) | 获取股票对应市场的市场状态 |
| [GetCapitalFlow](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html) | 获取个股资金流向 |
| [GetCapitalDistribution](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html) | 获取个股资金分布 |
| [GetOwnerPlate](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html) | 获取单支或多支股票的所属板块信息列表 |
| [RequestHistoryKL](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html) | 获取 K 线，不需要事先下载 K 线数据 |
| [RequestRehab](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [GetOptionExpirationDate](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html) | 通过标的股票，查询期权链的所有到期日 |
| [GetOptionChain](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html) | 通过标的股查询期权 |
| [GetWarrant](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html) | 拉取窝轮和相关衍生品数据接口 |
| [GetReference](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html) | 获取证券的关联数据 |
| [GetFutureInfo](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html) | 获取期货合约资料 |
| 全市场筛选 |     | [StockFilter](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html) | 获取条件选股 |
| [GetPlateSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html) | 获取特定板块下的股票列表 |
| [GetPlateSet](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html) | 获取板块集合下的子板块列表 |
| [GetStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html) | 获取指定市场中特定类型或特定股票的基本信息 |
| [GetIpoList](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html) | 获取指定市场的 ipo 列表 |
| [GetGlobalState](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html) | 获取全局市场状态 |
| [RequestTradeDate](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html) | 在线请求交易日 |
| 个性化 |     | [RequestHistoryKLQuota](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [SetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html) | 设置到价提醒 |
| [GetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [GetUserSecurityGroup](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html) | 获取自选股分组列表 |
| [GetUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html) | 获取指定分组的自选股列表 |
| [ModifyUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html) | 修改指定分组的自选股列表 |
| [UpdatePriceReminder](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html) | 到价提醒推送 |

| 模块  |     | 协议 ID | Protobuf 定义 | 说明  |
| --- | --- | --- | --- | --- |
| 实时行情 | 订阅  | 3001 | [Qot\_Sub](https://openapi.futunn.com/futu-api-doc/quote/sub.html) | 订阅或者反订阅 |
| 3003 | [Qot\_GetSubInfo](https://openapi.futunn.com/futu-api-doc/quote/query-subscription.html) | 获取订阅信息 |
| 推送回调 | 3005 | [Qot\_UpdateBasicQot](https://openapi.futunn.com/futu-api-doc/quote/update-stock-quote.html) | 推送股票基本报价 |
| 3013 | [Qot\_UpdateOrderBook](https://openapi.futunn.com/futu-api-doc/quote/update-order-book.html) | 推送买卖盘 |
| 3007 | [Qot\_UpdateKL](https://openapi.futunn.com/futu-api-doc/quote/update-kl.html) | 推送 K 线 |
| 3009 | [Qot\_UpdateRT](https://openapi.futunn.com/futu-api-doc/quote/update-rt.html) | 推送分时 |
| 3011 | [Qot\_UpdateTicker](https://openapi.futunn.com/futu-api-doc/quote/update-ticker.html) | 推送逐笔 |
| 3015 | [Qot\_UpdateBroker](https://openapi.futunn.com/futu-api-doc/quote/update-broker.html) | 推送经纪队列 |
| 拉取  | 3203 | [Qot\_GetSecuritySnapshot](https://openapi.futunn.com/futu-api-doc/quote/get-market-snapshot.html) | 获取股票快照 |
| 3004 | [Qot\_GetBasicQot](https://openapi.futunn.com/futu-api-doc/quote/get-stock-quote.html) | 获取股票基本报价 |
| 3012 | [Qot\_GetOrderBook](https://openapi.futunn.com/futu-api-doc/quote/get-order-book.html) | 获取买卖盘 |
| 3006 | [Qot\_GetKL](https://openapi.futunn.com/futu-api-doc/quote/get-kl.html) | 获取 K 线 |
| 3008 | [Qot\_GetRT](https://openapi.futunn.com/futu-api-doc/quote/get-rt.html) | 获取分时 |
| 3010 | [Qot\_GetTicker](https://openapi.futunn.com/futu-api-doc/quote/get-ticker.html) | 获取逐笔 |
| 3014 | [Qot\_GetBroker](https://openapi.futunn.com/futu-api-doc/quote/get-broker.html) | 获取经纪队列 |
| 基本数据 |     | 3223 | [Qot\_GetMarketState](https://openapi.futunn.com/futu-api-doc/quote/get-market-state.html) | 获取指定品种的市场状态 |
| 3211 | [Qot\_GetCapitalFlow](https://openapi.futunn.com/futu-api-doc/quote/get-capital-flow.html) | 获取资金流向 |
| 3212 | [Qot\_GetCapitalDistribution](https://openapi.futunn.com/futu-api-doc/quote/get-capital-distribution.html) | 获取资金分布 |
| 3207 | [Qot\_GetOwnerPlate](https://openapi.futunn.com/futu-api-doc/quote/get-owner-plate.html) | 获取股票所属板块 |
| 3103 | [Qot\_RequestHistoryKL](https://openapi.futunn.com/futu-api-doc/quote/request-history-kline.html) | 在线获取单只股票一段历史 K 线 |
| 3105 | [Qot\_RequestRehab](https://openapi.futunn.com/futu-api-doc/quote/get-rehab.html) | 在线获取单只股票复权信息 |
| 相关衍生品 |     | 3224 | [Qot\_GetOptionExpirationDate](https://openapi.futunn.com/futu-api-doc/quote/get-option-expiration-date.html) | 获取期权到期日 |
| 3209 | [Qot\_GetOptionChain](https://openapi.futunn.com/futu-api-doc/quote/get-option-chain.html) | 获取期权链 |
| 3210 | [Qot\_GetWarrant](https://openapi.futunn.com/futu-api-doc/quote/get-warrant.html) | 获取窝轮 |
| 3206 | [Qot\_GetReference](https://openapi.futunn.com/futu-api-doc/quote/get-referencestock-list.html) | 获取正股相关股票 |
| 3218 | [Qot\_GetFutureInfo](https://openapi.futunn.com/futu-api-doc/quote/get-future-info.html) | 获取期货合约资料 |
| 全市场筛选 |     | 3215 | [Qot\_StockFilter](https://openapi.futunn.com/futu-api-doc/quote/get-stock-filter.html) | 获取条件选股 |
| 3205 | [Qot\_GetPlateSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-plate-stock.html) | 获取板块下的股票 |
| 3204 | [Qot\_GetPlateSet](https://openapi.futunn.com/futu-api-doc/quote/get-plate-list.html) | 获取板块集合下的板块 |
| 3202 | [Qot\_GetStaticInfo](https://openapi.futunn.com/futu-api-doc/quote/get-static-info.html) | 获取股票静态信息 |
| 3217 | [Qot\_GetIpoList](https://openapi.futunn.com/futu-api-doc/quote/get-ipo-list.html) | 获取新股 |
| 1002 | [GetGlobalState](https://openapi.futunn.com/futu-api-doc/quote/get-global-state.html) | 获取全局市场状态 |
| 3219 | [Qot\_RequestTradeDate](https://openapi.futunn.com/futu-api-doc/quote/request-trading-days.html) | 获取市场交易日，在线拉取不在本地计算 |
| 个性化 |     | 3104 | [Qot\_RequestHistoryKLQuota](https://openapi.futunn.com/futu-api-doc/quote/get-history-kl-quota.html) | 获取历史 K 线额度 |
| 3220 | [Qot\_SetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/set-price-reminder.html) | 设置到价提醒 |
| 3221 | [Qot\_GetPriceReminder](https://openapi.futunn.com/futu-api-doc/quote/get-price-reminder.html) | 获取到价提醒 |
| 3213 | [Qot\_GetUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/get-user-security.html) | 获取自选股分组下的股票 |
| 3222 | [Qot\_GetUserSecurityGroup](https://openapi.futunn.com/futu-api-doc/quote/get-user-security-group.html) | 获取自选股分组列表 |
| 3214 | [Qot\_ModifyUserSecurity](https://openapi.futunn.com/futu-api-doc/quote/modify-user-security.html) | 修改自选股分组下的股票 |
| 3019 | [Qot\_UpdatePriceReminder](https://openapi.futunn.com/futu-api-doc/quote/update-price-reminder.html) | 到价提醒通知 |

← [运维命令](https://openapi.futunn.com/futu-api-doc/opend/opend-operate.html) [行情对象](https://openapi.futunn.com/futu-api-doc/quote/base.html)
 →