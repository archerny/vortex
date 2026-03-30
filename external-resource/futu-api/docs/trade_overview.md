[#](./trade_overview.md#548)
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
| 账户  | [Get Account List](./trade_get-acc-list.md) | 获取交易业务账户列表 |
| [Unlock Trading](./trade_unlock.md) | 解锁交易 |
| 资产持仓 | [Get Account Financial Information](./trade_get-funds.md) | 获取账户资金数据 |
| [Get Maximum Tradable Quantity](./trade_get-max-trd-qtys.md) | 查询账户最大可买卖数量 |
| [Get Positions List](./trade_get-position-list.md) | 获取持仓列表 |
| [Get Margin Trading Data](./trade_get-margin-ratio.md) | 获取融资融券数据 |
| [Get Cash Flow Summary](./trade_get-acc-cash-flow.md) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [Place Order](./trade_place-order.md) | 下单  |
| [Modify or Cancel Order](./trade_modify-order.md) | 改单撤单 |
| [Get Order list](./trade_get-order-list.md) | 查询未完成订单 |
| [Get Order Fees](./trade_order-fee-query.md) | 查询订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [Get Historical Order List](./trade_get-history-order-list.md) | 查询历史订单 |
| [Order Callback](./trade_update-order.md) | 订单回调 |
| [Trade Data Callback](./trade_sub-acc-push.md) | 订阅交易推送 |
| 成交  | [Get Today's Executed Trades](./trade_get-order-fill-list.md) | 查询当日成交 |
| [Get Historical Executed Trades](./trade_get-history-order-fill-list.md) | 查询历史成交 |
| [Trade Execution Callback](./trade_update-order-fill.md) | 成交回调 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [GetAccList](./trade_get-acc-list.md) | 获取交易业务账户列表 |
| [UnlockTrade](./trade_unlock.md) | 解锁交易 |
| 资产持仓 | [GetFunds](./trade_get-funds.md) | 获取账户资金数据 |
| [GetMaxTrdQtys](./trade_get-max-trd-qtys.md) | 查询账户下最大可买卖数量 |
| [GetPositionList](./trade_get-position-list.md) | 获取账户持仓列表 |
| [GetMarginRatio](./trade_get-margin-ratio.md) | 获取融资融券数据 |
| [FlowSummary](./trade_get-acc-cash-flow.md) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [PlaceOrder](./trade_place-order.md) | 下单  |
| [ModifyOrder](./trade_modify-order.md) | 修改订单 |
| [GetOrderList](./trade_get-order-list.md) | 获取订单列表 |
| [GetOrderFee](./trade_order-fee-query.md) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [GetHistoryOrderList](./trade_get-history-order-list.md) | 获取历史订单列表 |
| [UpdateOrder](./trade_update-order.md) | 订单更新 |
| [SubAccPush](./trade_sub-acc-push.md) | 订阅交易推送 |
| 成交  | [GetOrderFillList](./trade_get-order-fill-list.md) | 获取成交列表 |
| [GetHistoryOrderFillList](./trade_get-history-order-fill-list.md) | 获取历史成交列表 |
| [OnPush\_UpdateOrderFill](./trade_update-order-fill.md) | 成交更新 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [getAccList](./trade_get-acc-list.md) | 获取交易业务账户列表 |
| [unlockTrade](./trade_unlock.md) | 解锁交易 |
| 资产持仓 | [getFunds](./trade_get-funds.md) | 获取账户资金数据 |
| [getMaxTrdQtys](./trade_get-max-trd-qtys.md) | 查询账户下最大可买卖数量 |
| [getPositionList](./trade_get-position-list.md) | 获取账户持仓列表 |
| [getMarginRatio](./trade_get-margin-ratio.md) | 获取融资融券数据 |
| [FlowSummary](./trade_get-acc-cash-flow.md) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [placeOrder](./trade_place-order.md) | 下单  |
| [modifyOrder](./trade_modify-order.md) | 修改订单 |
| [getOrderList](./trade_get-order-list.md) | 获取订单列表 |
| [getOrderFee](./trade_order-fee-query.md) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [getHistoryOrderList](./trade_get-history-order-list.md) | 获取历史订单列表 |
| [updateOrder](./trade_update-order.md) | 订单更新 |
| [subAccPush](./trade_sub-acc-push.md) | 订阅交易推送 |
| 成交  | [getOrderFillList](./trade_get-order-fill-list.md) | 获取成交列表 |
| [getHistoryOrderFillList](./trade_get-history-order-fill-list.md) | 获取历史成交列表 |
| [onPush\_UpdateOrderFill](./trade_update-order-fill.md) | 成交更新 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [GetAccList](./trade_get-acc-list.md) | 获取交易业务账户列表 |
| [UnlockTrade](./trade_unlock.md) | 解锁交易 |
| 资产持仓 | [GetFunds](./trade_get-funds.md) | 获取账户资金数据 |
| [GetMaxTrdQtys](./trade_get-max-trd-qtys.md) | 查询账户下最大可买卖数量 |
| [GetPositionList](./trade_get-position-list.md) | 获取账户持仓列表 |
| [GetMarginRatio](./trade_get-margin-ratio.md) | 获取融资融券数据 |
| [FlowSummary](./trade_get-acc-cash-flow.md) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [PlaceOrder](./trade_place-order.md) | 下单  |
| [ModifyOrder](./trade_modify-order.md) | 修改订单 |
| [GetOrderList](./trade_get-order-list.md) | 获取订单列表 |
| [GetOrderFee](./trade_order-fee-query.md) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [GetHistoryOrderList](./trade_get-history-order-list.md) | 获取历史订单列表 |
| [UpdateOrder](./trade_update-order.md) | 订单更新 |
| [SubAccPush](./trade_sub-acc-push.md) | 订阅交易推送 |
| 成交  | [GetOrderFillList](./trade_get-order-fill-list.md) | 获取成交列表 |
| [GetHistoryOrderFillList](./trade_get-history-order-fill-list.md) | 获取历史成交列表 |
| [OnPush\_UpdateOrderFill](./trade_update-order-fill.md) | 成交更新 |

| 模块  | 接口名 | 功能简介 |
| --- | --- | --- |
| 账户  | [GetAccList](./trade_get-acc-list.md) | 获取交易业务账户列表 |
| [UnlockTrade](./trade_unlock.md) | 解锁交易 |
| 资产持仓 | [GetFunds](./trade_get-funds.md) | 获取账户资金数据 |
| [GetMaxTrdQtys](./trade_get-max-trd-qtys.md) | 查询账户下最大可买卖数量 |
| [GetPositionList](./trade_get-position-list.md) | 获取账户持仓列表 |
| [GetMarginRatio](./trade_get-margin-ratio.md) | 获取融资融券数据 |
| [FlowSummary](./trade_get-acc-cash-flow.md) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | [PlaceOrder](./trade_place-order.md) | 下单  |
| [ModifyOrder](./trade_modify-order.md) | 修改订单 |
| [GetOrderList](./trade_get-order-list.md) | 获取订单列表 |
| [GetOrderFee](./trade_order-fee-query.md) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| [GetHistoryOrderList](./trade_get-history-order-list.md) | 获取历史订单列表 |
| [UpdateOrder](./trade_update-order.md) | 订单更新 |
| [SubAccPush](./trade_sub-acc-push.md) | 订阅交易推送 |
| 成交  | [GetOrderFillList](./trade_get-order-fill-list.md) | 获取成交列表 |
| [GetHistoryOrderFillList](./trade_get-history-order-fill-list.md) | 获取历史成交列表 |
| [OnPush\_UpdateOrderFill](./trade_update-order-fill.md) | 成交更新 |

| 模块  | 协议 ID | Protobuf 文件 | 说明  |
| --- | --- | --- | --- |
| 账户  | 2001 | [Trd\_GetAccList](./trade_get-acc-list.md) | 获取交易业务账户列表 |
| 2005 | [Trd\_UnlockTrade](./trade_unlock.md) | 解锁或锁定交易 |
| 资产持仓 | 2101 | [Trd\_GetFunds](./trade_get-funds.md) | 获取账户资金 |
| 2111 | [Trd\_GetMaxTrdQtys](./trade_get-max-trd-qtys.md) | 获取最大交易数量 |
| 2102 | [Trd\_GetPositionList](./trade_get-position-list.md) | 获取账户持仓 |
| 2223 | [Trd\_GetMarginRatio](./trade_get-margin-ratio.md) | 获取融资融券数据 |
| 2226 | [Trd\_FlowSummary](./trade_get-acc-cash-flow.md) | 查询账户现金流水<br>(ℹ️ 最低版本要求：9.1.5108) |
| 订单  | 2202 | [Trd\_PlaceOrder](./trade_place-order.md) | 下单  |
| 2205 | [Trd\_ModifyOrder](./trade_modify-order.md) | 修改订单 |
| 2201 | [Trd\_GetOrderList](./trade_get-order-list.md) | 获取订单列表 |
| 2225 | [Trd\_GetOrderFee](./trade_order-fee-query.md) | 获取订单费用<br>(ℹ️ 最低版本要求：8.2.4218) |
| 2221 | [Trd\_GetHistoryOrderList](./trade_get-history-order-list.md) | 获取历史订单列表 |
| 2208 | [Trd\_UpdateOrder](./trade_update-order.md) | 推送订单状态变动通知 |
| 2008 | [Trd\_SubAccPush](./trade_sub-acc-push.md) | 订阅业务账户的交易推送数据 |
| 成交  | 2211 | [Trd\_GetOrderFillList](./trade_get-order-fill-list.md) | 获取成交列表 |
| 2222 | [Trd\_GetHistoryOrderFillList](./trade_get-history-order-fill-list.md) | 获取历史成交列表 |
| 2218 | [Trd\_UpdateOrderFill](./trade_update-order-fill.md) | 推送成交通知 |

← [行情定义](./quote_quote.md) [交易对象](./trade_base.md)
 →

[交易接口总览](./trade_overview.md)