 [![Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/img/logo.png) Futu API 文档 v10.2](https://openapi.futunn.com/futu-api-doc/)

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/overview.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/overview.html)
    

下载

*   [PDF](https://openapi.futunn.com/pdfs/Futu-API-Doc-zh-Python.pdf)
    
*   [Markdown](https://openapi.futunn.com/mds/Futu-API-Doc-zh-Python.md)
    
*   [Skills](https://openapi.futunn.com/skills/opend-skills.zip)
    

编程语言

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   proto

简体中文

*   [简体中文](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
    
*   [English](https://openapi.futunn.com/futu-api-doc/en/trade/overview.html)
    
*   [繁體中文](https://openapi.futunn.com/futu-api-doc/hk/trade/overview.html)
    

*   介绍
    
*   快速上手
    
*   OpenD
    
*   行情接口
    
*   交易接口
    
    *   [交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)
        
    *   [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
        
    *   账户
        
    *   资产持仓
        
    *   订单
        
    *   成交
        
    *   [交易定义](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
        
    
*   基础接口
    
*   Q&A
    

[#](https://openapi.futunn.com/futu-api-doc/trade/overview.html#548)
 交易接口总览
============================================================================

*   Python
*   C#
*   Java
*   C++
*   JavaScript
*   裸协议

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [Get Account List](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html) | 获取交易业务账户列表 |
| [Unlock Trading](https://openapi.futunn.com/futu-api-doc/trade/unlock.html) | 解锁交易 |
| 资产持仓 | [Get Account Financial Information](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html) | 获取账户资金数据 |
| [Get Maximum Tradable Quantity](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html) | 查询账户最大可买卖数量 |
| [Get Positions List](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html) | 获取持仓列表 |
| [Get Margin Trading Data](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html) | 获取融资融券数据 |
| [Get Cash Flow Summary](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [Place Order](https://openapi.futunn.com/futu-api-doc/trade/place-order.html) | 下单  |
| [Modify or Cancel Order](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html) | 改单撤单 |
| [Get Order list](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html) | 查询未完成订单 |
| [Get Order Fees](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html) | 查询订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [Get Historical Order List](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html) | 查询历史订单 |
| [Order Callback](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) | 订单回调 |
| [Trade Data Callback](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html) | 订阅交易推送 |
| 成交  | [Get Today's Executed Trades](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html) | 查询当日成交 |
| [Get Historical Executed Trades](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html) | 查询历史成交 |
| [Trade Execution Callback](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html) | 成交回调 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [GetAccList](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html) | 获取交易业务账户列表 |
| [UnlockTrade](https://openapi.futunn.com/futu-api-doc/trade/unlock.html) | 解锁交易 |
| 资产持仓 | [GetFunds](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html) | 获取账户资金数据 |
| [GetMaxTrdQtys](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html) | 查询账户下最大可买卖数量 |
| [GetPositionList](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html) | 获取账户持仓列表 |
| [GetMarginRatio](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html) | 获取融资融券数据 |
| [FlowSummary](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [PlaceOrder](https://openapi.futunn.com/futu-api-doc/trade/place-order.html) | 下单  |
| [ModifyOrder](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html) | 修改订单 |
| [GetOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html) | 获取订单列表 |
| [GetOrderFee](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [GetHistoryOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html) | 获取历史订单列表 |
| [UpdateOrder](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) | 订单更新 |
| [SubAccPush](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html) | 订阅交易推送 |
| 成交  | [GetOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html) | 获取成交列表 |
| [GetHistoryOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html) | 获取历史成交列表 |
| [OnPush\_UpdateOrderFill](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html) | 成交更新 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [getAccList](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html) | 获取交易业务账户列表 |
| [unlockTrade](https://openapi.futunn.com/futu-api-doc/trade/unlock.html) | 解锁交易 |
| 资产持仓 | [getFunds](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html) | 获取账户资金数据 |
| [getMaxTrdQtys](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html) | 查询账户下最大可买卖数量 |
| [getPositionList](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html) | 获取账户持仓列表 |
| [getMarginRatio](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html) | 获取融资融券数据 |
| [FlowSummary](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [placeOrder](https://openapi.futunn.com/futu-api-doc/trade/place-order.html) | 下单  |
| [modifyOrder](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html) | 修改订单 |
| [getOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html) | 获取订单列表 |
| [getOrderFee](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [getHistoryOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html) | 获取历史订单列表 |
| [updateOrder](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) | 订单更新 |
| [subAccPush](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html) | 订阅交易推送 |
| 成交  | [getOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html) | 获取成交列表 |
| [getHistoryOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html) | 获取历史成交列表 |
| [onPush\_UpdateOrderFill](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html) | 成交更新 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [GetAccList](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html) | 获取交易业务账户列表 |
| [UnlockTrade](https://openapi.futunn.com/futu-api-doc/trade/unlock.html) | 解锁交易 |
| 资产持仓 | [GetFunds](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html) | 获取账户资金数据 |
| [GetMaxTrdQtys](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html) | 查询账户下最大可买卖数量 |
| [GetPositionList](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html) | 获取账户持仓列表 |
| [GetMarginRatio](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html) | 获取融资融券数据 |
| [FlowSummary](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [PlaceOrder](https://openapi.futunn.com/futu-api-doc/trade/place-order.html) | 下单  |
| [ModifyOrder](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html) | 修改订单 |
| [GetOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html) | 获取订单列表 |
| [GetOrderFee](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [GetHistoryOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html) | 获取历史订单列表 |
| [UpdateOrder](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) | 订单更新 |
| [SubAccPush](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html) | 订阅交易推送 |
| 成交  | [GetOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html) | 获取成交列表 |
| [GetHistoryOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html) | 获取历史成交列表 |
| [OnPush\_UpdateOrderFill](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html) | 成交更新 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [GetAccList](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html) | 获取交易业务账户列表 |
| [UnlockTrade](https://openapi.futunn.com/futu-api-doc/trade/unlock.html) | 解锁交易 |
| 资产持仓 | [GetFunds](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html) | 获取账户资金数据 |
| [GetMaxTrdQtys](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html) | 查询账户下最大可买卖数量 |
| [GetPositionList](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html) | 获取账户持仓列表 |
| [GetMarginRatio](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html) | 获取融资融券数据 |
| [FlowSummary](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [PlaceOrder](https://openapi.futunn.com/futu-api-doc/trade/place-order.html) | 下单  |
| [ModifyOrder](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html) | 修改订单 |
| [GetOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html) | 获取订单列表 |
| [GetOrderFee](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [GetHistoryOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html) | 获取历史订单列表 |
| [UpdateOrder](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) | 订单更新 |
| [SubAccPush](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html) | 订阅交易推送 |
| 成交  | [GetOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html) | 获取成交列表 |
| [GetHistoryOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html) | 获取历史成交列表 |
| [OnPush\_UpdateOrderFill](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html) | 成交更新 |

| 模块  | 协议 ID | Protobuf 文件 | 说明  |
| --- | --- | --- | --- |
| 账户  | 2001 | [Trd\_GetAccList](https://openapi.futunn.com/futu-api-doc/trade/get-acc-list.html) | 获取交易业务账户列表 |
| 2005 | [Trd\_UnlockTrade](https://openapi.futunn.com/futu-api-doc/trade/unlock.html) | 解锁或锁定交易 |
| 资产持仓 | 2101 | [Trd\_GetFunds](https://openapi.futunn.com/futu-api-doc/trade/get-funds.html) | 获取账户资金 |
| 2111 | [Trd\_GetMaxTrdQtys](https://openapi.futunn.com/futu-api-doc/trade/get-max-trd-qtys.html) | 获取最大交易数量 |
| 2102 | [Trd\_GetPositionList](https://openapi.futunn.com/futu-api-doc/trade/get-position-list.html) | 获取账户持仓 |
| 2223 | [Trd\_GetMarginRatio](https://openapi.futunn.com/futu-api-doc/trade/get-margin-ratio.html) | 获取融资融券数据 |
| 2226 | [Trd\_FlowSummary](https://openapi.futunn.com/futu-api-doc/trade/get-acc-cash-flow.html) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | 2202 | [Trd\_PlaceOrder](https://openapi.futunn.com/futu-api-doc/trade/place-order.html) | 下单  |
| 2205 | [Trd\_ModifyOrder](https://openapi.futunn.com/futu-api-doc/trade/modify-order.html) | 修改订单 |
| 2201 | [Trd\_GetOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-order-list.html) | 获取订单列表 |
| 2225 | [Trd\_GetOrderFee](https://openapi.futunn.com/futu-api-doc/trade/order-fee-query.html) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| 2221 | [Trd\_GetHistoryOrderList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-list.html) | 获取历史订单列表 |
| 2208 | [Trd\_UpdateOrder](https://openapi.futunn.com/futu-api-doc/trade/update-order.html) | 推送订单状态变动通知 |
| 2008 | [Trd\_SubAccPush](https://openapi.futunn.com/futu-api-doc/trade/sub-acc-push.html) | 订阅业务账户的交易推送数据 |
| 成交  | 2211 | [Trd\_GetOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-order-fill-list.html) | 获取成交列表 |
| 2222 | [Trd\_GetHistoryOrderFillList](https://openapi.futunn.com/futu-api-doc/trade/get-history-order-fill-list.html) | 获取历史成交列表 |
| 2218 | [Trd\_UpdateOrderFill](https://openapi.futunn.com/futu-api-doc/trade/update-order-fill.html) | 推送成交通知 |

← [行情定义](https://openapi.futunn.com/futu-api-doc/quote/quote.html) [交易对象](https://openapi.futunn.com/futu-api-doc/trade/base.html)
 →

[交易接口总览](https://openapi.futunn.com/futu-api-doc/trade/overview.html)