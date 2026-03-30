更新日志
====

2025-06-17 [​](./changelog.md#_2025-06-17)

-----------------------------------------------------------------------------

*   更新获取账户资金接口
    *   `GET /v1/asset/account` 增加 (frozen\_transaction\_fees) 返回字段

2024-10-09 [​](./changelog.md#_2024-10-09)

-----------------------------------------------------------------------------

### SDK 2.0.0 [​](./changelog.md#sdk-2-0-0)

*   连接到服务器时打印已开通的行情包
*   交易 API 中的数量类型从 `int` 更改为 `Decimal`。

2024-09-11 [​](./changelog.md#_2024-09-11)

-----------------------------------------------------------------------------

*   更新获取标的列表接口
    *   `GET /v1/quote/get_security_list` 返回的多语言名称根据请求头 `accept-language` 返回对应字段，不再一次性返回三种语言名称

2024-08-28 [​](./changelog.md#_2024-08-28)

-----------------------------------------------------------------------------

*   SDK 中 `Depth.price` 字段从 `Decimal` 类型改为 `Optional[Decimal]` 类型

2024-05-17 [​](./changelog.md#_2024-05-17)

-----------------------------------------------------------------------------

*   下单及订单查询接口扩展 `outside_rth` 字段支持夜盘交易

2024-05-06 [​](./changelog.md#_2024-05-06)

-----------------------------------------------------------------------------

*   更新获取账户资金接口
    *   `GET /v1/asset/account` 增加 (buy\_power) 返回字段

2024-04-29 [​](./changelog.md#_2024-04-29)

-----------------------------------------------------------------------------

*   删除 `TSMPCT`, `TSMAMT` 订单类型

2024-04-15 [​](./changelog.md#_2024-04-15)

-----------------------------------------------------------------------------

*   [交易推送](https://open.longbridge.com/docs/trade/trade-definition#websocket-%E6%8E%A8%E9%80%81%E9%80%9A%E7%9F%A5)
    添加 `last_share`, `last_price`。

2024-04-13 [​](./changelog.md#_2024-04-13)

-----------------------------------------------------------------------------

*   [交易推送](https://open.longbridge.com/docs/trade/trade-definition#websocket-%E6%8E%A8%E9%80%81%E9%80%9A%E7%9F%A5)
    添加 `remark` 备注字段。

2023-11-03 [​](./changelog.md#_2023-11-03)

-----------------------------------------------------------------------------

*   新增行情历史 K 线接口
    *   长连接 `Business Command：27` 获取标的历史 K 线

2023-08-17 [​](./changelog.md#_2023-08-17)

-----------------------------------------------------------------------------

*   更新获取账户资金接口
    *   `GET /v1/asset/account` 增加入参 (currency) 字段

2023-04-12 [​](./changelog.md#_2023-04-12)

-----------------------------------------------------------------------------

*   更新获取股票持仓接口
    *   `GET /v1/asset/stock` 增加开盘前初始持仓 (init\_quantity) 字段

2023-04-11 [​](./changelog.md#_2023-04-11)

-----------------------------------------------------------------------------

*   新增订单详情查询接口
    *   `GET /v1/trade/order` 获取订单详情
*   新增预估最大购买数量接口
    *   `GET /v1/trade/estimate/buy_limit` 获取预估最大购买数量接口
*   美股期权添加市价单和条件单支持

2022-07-18 [​](./changelog.md#_2022-07-18)

-----------------------------------------------------------------------------

*   更新标的基础信息接口
    *   长连接 `Business Command: 10` 响应增加 `board` 字段

2022-07-14 [​](./changelog.md#_2022-07-14)

-----------------------------------------------------------------------------

*   新增获取保证金比例接口
    *   `GET /v1/risk/margin-ratio` 获取保证金比例

2022-06-30 [​](./changelog.md#_2022-06-30)

-----------------------------------------------------------------------------

*   新增获取关注分组接口
    *   `GET /v1/watchlist/groups` 获取关注分组

2022-06-20 [​](./changelog.md#_2022-06-20)

-----------------------------------------------------------------------------

*   更新账号资金接口
    *   `GET /v1/asset/account` 响应增加净资产 (net\_assets)、初始保证金 (init\_margin)、维持保证金 (maintenance\_margin) 字段
*   更新持仓接口
    *   `GET /v1/asset/stock` 支持用户获取期权持仓

2022-06-15 [​](./changelog.md#_2022-06-15)

-----------------------------------------------------------------------------

*   新增行情资金流接口
    *   长连接 `Business Command：24` 获取标的当日资金流向
    *   长连接 `Business Command：25` 获取标的当日资金分布

[LLMs Text](https://open.longbridge.com/docs/changelog.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/changelog.md)

最后更新于:

Pager

[上一页WebSocket 和 TCP 接入的不同点](./socket-diff_ws_tcp.md)

[下一页API Reference](./api.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
