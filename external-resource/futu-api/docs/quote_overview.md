[#](./quote_overview.md#426)
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
| 实时行情 | 订阅  | [subscribe](./quote_sub.md#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [unsubscribe](./quote_sub.md#4908) | 取消订阅 |
| [unsubscribe\_all](./quote_sub.md#2489) | 取消所有订阅 |
| [query\_subscription](./quote_query-subscription.md) | 查询订阅信息 |
| 推送回调 | [StockQuoteHandlerBase](./quote_update-stock-quote.md) | 报价推送 |
| [OrderBookHandlerBase](./quote_update-order-book.md) | 摆盘推送 |
| [CurKlineHandlerBase](./quote_update-kl.md) | K 线推送 |
| [TickerHandlerBase](./quote_update-ticker.md) | 逐笔推送 |
| [RTDataHandlerBase](./quote_update-rt.md) | 分时推送 |
| [BrokerHandlerBase](./quote_update-broker.md) | 经纪队列推送 |
| 拉取  | [get\_market\_snapshot](./quote_get-market-snapshot.md) | 获取市场快照 |
| [get\_stock\_quote](./quote_get-stock-quote.md) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [get\_order\_book](./quote_get-order-book.md) | 获取实时摆盘数据 |
| [get\_cur\_kline](./quote_get-kl.md) | 实时获取指定股票最近 num 个 K 线数据 |
| [get\_rt\_data](./quote_get-rt.md) | 获取指定股票的分时数据 |
| [get\_rt\_ticker](./quote_get-ticker.md) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [get\_broker\_queue](./quote_get-broker.md) | 获取股票的经纪队列 |
| 基本数据 |     | [get\_market\_state](./quote_get-market-state.md) | 获取股票对应市场的市场状态 |
| [get\_capital\_flow](./quote_get-capital-flow.md) | 获取个股资金流向 |
| [get\_capital\_distribution](./quote_get-capital-distribution.md) | 获取个股资金分布 |
| [get\_owner\_plate](./quote_get-owner-plate.md) | 获取单支或多支股票的所属板块信息列表 |
| [request\_history\_kline](./quote_request-history-kline.md) | 获取 K 线，不需要事先下载 K 线数据 |
| [get\_rehab](./quote_get-rehab.md) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [get\_option\_expiration\_date](./quote_get-option-expiration-date.md) | 通过标的股票，查询期权链的所有到期日 |
| [get\_option\_chain](./quote_get-option-chain.md) | 通过标的股查询期权 |
| [get\_warrant](./quote_get-warrant.md) | 拉取窝轮和相关衍生品数据接口 |
| [get\_referencestock\_list](./quote_get-referencestock-list.md) | 获取证券的关联数据 |
| [get\_future\_info](./quote_get-future-info.md) | 获取期货合约资料 |
| 全市场筛选 |     | [get\_stock\_filter](./quote_get-stock-filter.md) | 获取条件选股 |
| [get\_plate\_stock](./quote_get-plate-stock.md) | 获取特定板块下的股票列表 |
| [get\_plate\_list](./quote_get-plate-list.md) | 获取板块集合下的子板块列表 |
| [get\_stock\_basicinfo](./quote_get-static-info.md) | 获取指定市场中特定类型或特定股票的基本信息 |
| [get\_ipo\_list](./quote_get-ipo-list.md) | 获取指定市场的 ipo 列表 |
| [get\_global\_state](./quote_get-global-state.md) | 获取全局市场状态 |
| [request\_trading\_days](./quote_request-trading-days.md) | 获取交易日历 |
| 个性化 |     | [get\_history\_kl\_quota](./quote_get-history-kl-quota.md) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [set\_price\_reminder](./quote_set-price-reminder.md) | 设置到价提醒 |
| [get\_price\_reminder](./quote_get-price-reminder.md) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [get\_user\_security\_group](./quote_get-user-security-group.md) | 获取自选股分组列表 |
| [get\_user\_security](./quote_get-user-security.md) | 获取指定分组的自选股列表 |
| [modify\_user\_security](./quote_modify-user-security.md) | 修改指定分组的自选股列表 |
| [PriceReminderHandlerBase](./quote_update-price-reminder.md) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [Sub](./quote_sub.md#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [Sub](./quote_sub.md#4908) | 取消订阅 |
| [Sub](./quote_sub.md#2489) | 取消所有订阅 |
| [GetSubInfo](./quote_query-subscription.md) | 查询订阅信息 |
| 推送回调 | [UpdateBasicQot](./quote_update-stock-quote.md) | 报价推送 |
| [UpdateOrderBook](./quote_update-order-book.md) | 摆盘推送 |
| [UpdateKL](./quote_update-kl.md) | K 线推送 |
| [UpdateTicker](./quote_update-ticker.md) | 逐笔推送 |
| [UpdateRT](./quote_update-rt.md) | 分时推送 |
| [UpdateBroker](./quote_update-broker.md) | 经纪队列推送 |
| 拉取  | [GetSecuritySnapshot](./quote_get-market-snapshot.md) | 获取市场快照 |
| [GetBasicQot](./quote_get-stock-quote.md) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [GetOrderBook](./quote_get-order-book.md) | 获取实时摆盘数据 |
| [GetKL](./quote_get-kl.md) | 实时获取指定股票最近 num 个 K 线数据 |
| [GetRT](./quote_get-rt.md) | 获取指定股票的分时数据 |
| [GetTicker](./quote_get-ticker.md) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [GetBroker](./quote_get-broker.md) | 获取股票的经纪队列 |
| 基本数据 |     | [GetMarketState](./quote_get-market-state.md) | 获取股票对应市场的市场状态 |
| [GetCapitalFlow](./quote_get-capital-flow.md) | 获取个股资金流向 |
| [GetCapitalDistribution](./quote_get-capital-distribution.md) | 获取个股资金分布 |
| [GetOwnerPlate](./quote_get-owner-plate.md) | 获取单支或多支股票的所属板块信息列表 |
| [RequestHistoryKL](./quote_request-history-kline.md) | 获取 K 线，不需要事先下载 K 线数据 |
| [RequestRehab](./quote_get-rehab.md) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [GetOptionExpirationDate](./quote_get-option-expiration-date.md) | 通过标的股票，查询期权链的所有到期日 |
| [GetOptionChain](./quote_get-option-chain.md) | 通过标的股查询期权 |
| [GetWarrant](./quote_get-warrant.md) | 拉取窝轮和相关衍生品数据接口 |
| [GetReference](./quote_get-referencestock-list.md) | 获取证券的关联数据 |
| [GetFutureInfo](./quote_get-future-info.md) | 获取期货合约资料 |
| 全市场筛选 |     | [StockFilter](./quote_get-stock-filter.md) | 获取条件选股 |
| [GetPlateSecurity](./quote_get-plate-stock.md) | 获取特定板块下的股票列表 |
| [GetPlateSet](./quote_get-plate-list.md) | 获取板块集合下的子板块列表 |
| [GetStaticInfo](./quote_get-static-info.md) | 获取指定市场中特定类型或特定股票的基本信息 |
| [GetIpoList](./quote_get-ipo-list.md) | 获取指定市场的 ipo 列表 |
| [GetGlobalState](./quote_get-global-state.md) | 获取全局市场状态 |
| [RequestTradeDate](./quote_request-trading-days.md) | 在线请求交易日 |
| 个性化 |     | [RequestHistoryKLQuota](./quote_get-history-kl-quota.md) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [SetPriceReminder](./quote_set-price-reminder.md) | 设置到价提醒 |
| [GetPriceReminder](./quote_get-price-reminder.md) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [GetUserSecurityGroup](./quote_get-user-security-group.md) | 获取自选股分组列表 |
| [GetUserSecurity](./quote_get-user-security.md) | 获取指定分组的自选股列表 |
| [ModifyUserSecurity](./quote_modify-user-security.md) | 修改指定分组的自选股列表 |
| [UpdatePriceReminder](./quote_update-price-reminder.md) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [sub](./quote_sub.md#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [sub](./quote_sub.md#4908) | 取消订阅 |
| [sub](./quote_sub.md#2489) | 取消所有订阅 |
| [getSubInfo](./quote_query-subscription.md) | 查询订阅信息 |
| 推送回调 | [updateBasicQot](./quote_update-stock-quote.md) | 报价推送 |
| [updateOrderBook](./quote_update-order-book.md) | 摆盘推送 |
| [updateKL](./quote_update-kl.md) | K 线推送 |
| [updateTicker](./quote_update-ticker.md) | 逐笔推送 |
| [updateRT](./quote_update-rt.md) | 分时推送 |
| [updateBroker](./quote_update-broker.md) | 经纪队列推送 |
| 拉取  | [getSecuritySnapshot](./quote_get-market-snapshot.md) | 获取市场快照 |
| [getBasicQot](./quote_get-stock-quote.md) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [getOrderBook](./quote_get-order-book.md) | 获取实时摆盘数据 |
| [getKL](./quote_get-kl.md) | 实时获取指定股票最近 num 个 K 线数据 |
| [getRT](./quote_get-rt.md) | 获取指定股票的分时数据 |
| [getTicker](./quote_get-ticker.md) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [getBroker](./quote_get-broker.md) | 获取股票的经纪队列 |
| 基本数据 |     | [getMarketState](./quote_get-market-state.md) | 获取股票对应市场的市场状态 |
| [getCapitalFlow](./quote_get-capital-flow.md) | 获取个股资金流向 |
| [getCapitalDistribution](./quote_get-capital-distribution.md) | 获取个股资金分布 |
| [getOwnerPlate](./quote_get-owner-plate.md) | 获取单支或多支股票的所属板块信息列表 |
| [requestHistoryKL](./quote_request-history-kline.md) | 获取 K 线，不需要事先下载 K 线数据 |
| [requestRehab](./quote_get-rehab.md) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [getOptionExpirationDate](./quote_get-option-expiration-date.md) | 通过标的股票，查询期权链的所有到期日 |
| [getOptionChain](./quote_get-option-chain.md) | 通过标的股查询期权 |
| [getWarrant](./quote_get-warrant.md) | 拉取窝轮和相关衍生品数据接口 |
| [getReference](./quote_get-referencestock-list.md) | 获取证券的关联数据 |
| [getFutureInfo](./quote_get-future-info.md) | 获取期货合约资料 |
| 全市场筛选 |     | [stockFilter](./quote_get-stock-filter.md) | 获取条件选股 |
| [getPlateSecurity](./quote_get-plate-stock.md) | 获取特定板块下的股票列表 |
| [getPlateSet](./quote_get-plate-list.md) | 获取板块集合下的子板块列表 |
| [getStaticInfo](./quote_get-static-info.md) | 获取指定市场中特定类型或特定股票的基本信息 |
| [getIpoList](./quote_get-ipo-list.md) | 获取指定市场的 ipo 列表 |
| [getGlobalState](./quote_get-global-state.md) | 获取全局市场状态 |
| [requestTradeDate](./quote_request-trading-days.md) | 在线请求交易日 |
| 个性化 |     | [requestHistoryKLQuota](./quote_get-history-kl-quota.md) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [setPriceReminder](./quote_set-price-reminder.md) | 设置到价提醒 |
| [getPriceReminder](./quote_get-price-reminder.md) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [getUserSecurityGroup](./quote_get-user-security-group.md) | 获取自选股分组列表 |
| [getUserSecurity](./quote_get-user-security.md) | 获取指定分组的自选股列表 |
| [modifyUserSecurity](./quote_modify-user-security.md) | 修改指定分组的自选股列表 |
| [updatePriceReminder](./quote_update-price-reminder.md) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [Sub](./quote_sub.md#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [Sub](./quote_sub.md#4908) | 取消订阅 |
| [Sub](./quote_sub.md#2489) | 取消所有订阅 |
| [GetSubInfo](./quote_query-subscription.md) | 查询订阅信息 |
| 推送回调 | [UpdateBasicQot](./quote_update-stock-quote.md) | 报价推送 |
| [UpdateOrderBook](./quote_update-order-book.md) | 摆盘推送 |
| [UpdateKL](./quote_update-kl.md) | K 线推送 |
| [UpdateTicker](./quote_update-ticker.md) | 逐笔推送 |
| [UpdateRT](./quote_update-rt.md) | 分时推送 |
| [UpdateBroker](./quote_update-broker.md) | 经纪队列推送 |
| 拉取  | [GetSecuritySnapshot](./quote_get-market-snapshot.md) | 获取市场快照 |
| [GetBasicQot](./quote_get-stock-quote.md) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [GetOrderBook](./quote_get-order-book.md) | 获取实时摆盘数据 |
| [GetKL](./quote_get-kl.md) | 实时获取指定股票最近 num 个 K 线数据 |
| [GetRT](./quote_get-rt.md) | 获取指定股票的分时数据 |
| [GetTicker](./quote_get-ticker.md) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [GetBroker](./quote_get-broker.md) | 获取股票的经纪队列 |
| 基本数据 |     | [GetMarketState](./quote_get-market-state.md) | 获取股票对应市场的市场状态 |
| [GetCapitalFlow](./quote_get-capital-flow.md) | 获取个股资金流向 |
| [GetCapitalDistribution](./quote_get-capital-distribution.md) | 获取个股资金分布 |
| [GetOwnerPlate](./quote_get-owner-plate.md) | 获取单支或多支股票的所属板块信息列表 |
| [RequestHistoryKL](./quote_request-history-kline.md) | 获取 K 线，不需要事先下载 K 线数据 |
| [RequestRehab](./quote_get-rehab.md) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [GetOptionExpirationDate](./quote_get-option-expiration-date.md) | 通过标的股票，查询期权链的所有到期日 |
| [GetOptionChain](./quote_get-option-chain.md) | 通过标的股查询期权 |
| [GetWarrant](./quote_get-warrant.md) | 拉取窝轮和相关衍生品数据接口 |
| [GetReference](./quote_get-referencestock-list.md) | 获取证券的关联数据 |
| [GetFutureInfo](./quote_get-future-info.md) | 获取期货合约资料 |
| 全市场筛选 |     | [StockFilter](./quote_get-stock-filter.md) | 获取条件选股 |
| [GetPlateSecurity](./quote_get-plate-stock.md) | 获取特定板块下的股票列表 |
| [GetPlateSet](./quote_get-plate-list.md) | 获取板块集合下的子板块列表 |
| [GetStaticInfo](./quote_get-static-info.md) | 获取指定市场中特定类型或特定股票的基本信息 |
| [GetIpoList](./quote_get-ipo-list.md) | 获取指定市场的 ipo 列表 |
| [GetGlobalState](./quote_get-global-state.md) | 获取全局市场状态 |
| [RequestTradeDate](./quote_request-trading-days.md) | 在线请求交易日 |
| 个性化 |     | [RequestHistoryKLQuota](./quote_get-history-kl-quota.md) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [SetPriceReminder](./quote_set-price-reminder.md) | 设置到价提醒 |
| [GetPriceReminder](./quote_get-price-reminder.md) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [GetUserSecurityGroup](./quote_get-user-security-group.md) | 获取自选股分组列表 |
| [GetUserSecurity](./quote_get-user-security.md) | 获取指定分组的自选股列表 |
| [ModifyUserSecurity](./quote_modify-user-security.md) | 修改指定分组的自选股列表 |
| [UpdatePriceReminder](./quote_update-price-reminder.md) | 到价提醒推送 |

| 模块  |     | 接口名 | 功能简介 |
| --- | --- | --- | --- |
| 实时行情 | 订阅  | [Sub](./quote_sub.md#2263) | 订阅实时数据，指定股票代码和订阅的数据类型即可 |
| [Sub](./quote_sub.md#4908) | 取消订阅 |
| [Sub](./quote_sub.md#2489) | 取消所有订阅 |
| [GetSubInfo](./quote_query-subscription.md) | 查询订阅信息 |
| 推送回调 | [UpdateBasicQot](./quote_update-stock-quote.md) | 报价推送 |
| [UpdateOrderBook](./quote_update-order-book.md) | 摆盘推送 |
| [UpdateKL](./quote_update-kl.md) | K 线推送 |
| [UpdateTicker](./quote_update-ticker.md) | 逐笔推送 |
| [UpdateRT](./quote_update-rt.md) | 分时推送 |
| [UpdateBroker](./quote_update-broker.md) | 经纪队列推送 |
| 拉取  | [GetSecuritySnapshot](./quote_get-market-snapshot.md) | 获取市场快照 |
| [GetBasicQot](./quote_get-stock-quote.md) | 获取订阅股票报价的实时数据，有订阅要求限制 |
| [GetOrderBook](./quote_get-order-book.md) | 获取实时摆盘数据 |
| [GetKL](./quote_get-kl.md) | 实时获取指定股票最近 num 个 K 线数据 |
| [GetRT](./quote_get-rt.md) | 获取指定股票的分时数据 |
| [GetTicker](./quote_get-ticker.md) | 获取指定股票的实时逐笔。取最近 num 个逐笔 |
| [GetBroker](./quote_get-broker.md) | 获取股票的经纪队列 |
| 基本数据 |     | [GetMarketState](./quote_get-market-state.md) | 获取股票对应市场的市场状态 |
| [GetCapitalFlow](./quote_get-capital-flow.md) | 获取个股资金流向 |
| [GetCapitalDistribution](./quote_get-capital-distribution.md) | 获取个股资金分布 |
| [GetOwnerPlate](./quote_get-owner-plate.md) | 获取单支或多支股票的所属板块信息列表 |
| [RequestHistoryKL](./quote_request-history-kline.md) | 获取 K 线，不需要事先下载 K 线数据 |
| [RequestRehab](./quote_get-rehab.md) | 获取给定股票的复权因子 |
| 相关衍生品 |     | [GetOptionExpirationDate](./quote_get-option-expiration-date.md) | 通过标的股票，查询期权链的所有到期日 |
| [GetOptionChain](./quote_get-option-chain.md) | 通过标的股查询期权 |
| [GetWarrant](./quote_get-warrant.md) | 拉取窝轮和相关衍生品数据接口 |
| [GetReference](./quote_get-referencestock-list.md) | 获取证券的关联数据 |
| [GetFutureInfo](./quote_get-future-info.md) | 获取期货合约资料 |
| 全市场筛选 |     | [StockFilter](./quote_get-stock-filter.md) | 获取条件选股 |
| [GetPlateSecurity](./quote_get-plate-stock.md) | 获取特定板块下的股票列表 |
| [GetPlateSet](./quote_get-plate-list.md) | 获取板块集合下的子板块列表 |
| [GetStaticInfo](./quote_get-static-info.md) | 获取指定市场中特定类型或特定股票的基本信息 |
| [GetIpoList](./quote_get-ipo-list.md) | 获取指定市场的 ipo 列表 |
| [GetGlobalState](./quote_get-global-state.md) | 获取全局市场状态 |
| [RequestTradeDate](./quote_request-trading-days.md) | 在线请求交易日 |
| 个性化 |     | [RequestHistoryKLQuota](./quote_get-history-kl-quota.md) | 获取已使用过的额度，即当前周期内已经下载过多少只股票 |
| [SetPriceReminder](./quote_set-price-reminder.md) | 设置到价提醒 |
| [GetPriceReminder](./quote_get-price-reminder.md) | 获取对某只股票(某个市场)设置的到价提醒列表 |
| [GetUserSecurityGroup](./quote_get-user-security-group.md) | 获取自选股分组列表 |
| [GetUserSecurity](./quote_get-user-security.md) | 获取指定分组的自选股列表 |
| [ModifyUserSecurity](./quote_modify-user-security.md) | 修改指定分组的自选股列表 |
| [UpdatePriceReminder](./quote_update-price-reminder.md) | 到价提醒推送 |

| 模块  |     | 协议 ID | Protobuf 定义 | 说明  |
| --- | --- | --- | --- | --- |
| 实时行情 | 订阅  | 3001 | [Qot\_Sub](./quote_sub.md) | 订阅或者反订阅 |
| 3003 | [Qot\_GetSubInfo](./quote_query-subscription.md) | 获取订阅信息 |
| 推送回调 | 3005 | [Qot\_UpdateBasicQot](./quote_update-stock-quote.md) | 推送股票基本报价 |
| 3013 | [Qot\_UpdateOrderBook](./quote_update-order-book.md) | 推送买卖盘 |
| 3007 | [Qot\_UpdateKL](./quote_update-kl.md) | 推送 K 线 |
| 3009 | [Qot\_UpdateRT](./quote_update-rt.md) | 推送分时 |
| 3011 | [Qot\_UpdateTicker](./quote_update-ticker.md) | 推送逐笔 |
| 3015 | [Qot\_UpdateBroker](./quote_update-broker.md) | 推送经纪队列 |
| 拉取  | 3203 | [Qot\_GetSecuritySnapshot](./quote_get-market-snapshot.md) | 获取股票快照 |
| 3004 | [Qot\_GetBasicQot](./quote_get-stock-quote.md) | 获取股票基本报价 |
| 3012 | [Qot\_GetOrderBook](./quote_get-order-book.md) | 获取买卖盘 |
| 3006 | [Qot\_GetKL](./quote_get-kl.md) | 获取 K 线 |
| 3008 | [Qot\_GetRT](./quote_get-rt.md) | 获取分时 |
| 3010 | [Qot\_GetTicker](./quote_get-ticker.md) | 获取逐笔 |
| 3014 | [Qot\_GetBroker](./quote_get-broker.md) | 获取经纪队列 |
| 基本数据 |     | 3223 | [Qot\_GetMarketState](./quote_get-market-state.md) | 获取指定品种的市场状态 |
| 3211 | [Qot\_GetCapitalFlow](./quote_get-capital-flow.md) | 获取资金流向 |
| 3212 | [Qot\_GetCapitalDistribution](./quote_get-capital-distribution.md) | 获取资金分布 |
| 3207 | [Qot\_GetOwnerPlate](./quote_get-owner-plate.md) | 获取股票所属板块 |
| 3103 | [Qot\_RequestHistoryKL](./quote_request-history-kline.md) | 在线获取单只股票一段历史 K 线 |
| 3105 | [Qot\_RequestRehab](./quote_get-rehab.md) | 在线获取单只股票复权信息 |
| 相关衍生品 |     | 3224 | [Qot\_GetOptionExpirationDate](./quote_get-option-expiration-date.md) | 获取期权到期日 |
| 3209 | [Qot\_GetOptionChain](./quote_get-option-chain.md) | 获取期权链 |
| 3210 | [Qot\_GetWarrant](./quote_get-warrant.md) | 获取窝轮 |
| 3206 | [Qot\_GetReference](./quote_get-referencestock-list.md) | 获取正股相关股票 |
| 3218 | [Qot\_GetFutureInfo](./quote_get-future-info.md) | 获取期货合约资料 |
| 全市场筛选 |     | 3215 | [Qot\_StockFilter](./quote_get-stock-filter.md) | 获取条件选股 |
| 3205 | [Qot\_GetPlateSecurity](./quote_get-plate-stock.md) | 获取板块下的股票 |
| 3204 | [Qot\_GetPlateSet](./quote_get-plate-list.md) | 获取板块集合下的板块 |
| 3202 | [Qot\_GetStaticInfo](./quote_get-static-info.md) | 获取股票静态信息 |
| 3217 | [Qot\_GetIpoList](./quote_get-ipo-list.md) | 获取新股 |
| 1002 | [GetGlobalState](./quote_get-global-state.md) | 获取全局市场状态 |
| 3219 | [Qot\_RequestTradeDate](./quote_request-trading-days.md) | 获取市场交易日，在线拉取不在本地计算 |
| 个性化 |     | 3104 | [Qot\_RequestHistoryKLQuota](./quote_get-history-kl-quota.md) | 获取历史 K 线额度 |
| 3220 | [Qot\_SetPriceReminder](./quote_set-price-reminder.md) | 设置到价提醒 |
| 3221 | [Qot\_GetPriceReminder](./quote_get-price-reminder.md) | 获取到价提醒 |
| 3213 | [Qot\_GetUserSecurity](./quote_get-user-security.md) | 获取自选股分组下的股票 |
| 3222 | [Qot\_GetUserSecurityGroup](./quote_get-user-security-group.md) | 获取自选股分组列表 |
| 3214 | [Qot\_ModifyUserSecurity](./quote_modify-user-security.md) | 修改自选股分组下的股票 |
| 3019 | [Qot\_UpdatePriceReminder](./quote_update-price-reminder.md) | 到价提醒通知 |

← [运维命令](./opend_opend-operate.md) [行情对象](./quote_base.md)
 →

[行情接口总览](./quote_overview.md)