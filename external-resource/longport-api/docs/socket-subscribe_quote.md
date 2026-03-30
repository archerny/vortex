订阅行情推送
======

NOTE

我们的 OpenAPI SDK 已经完整实现了订阅行情的功能，你可以直接使用 SDK。

[https://open.longbridge.com/sdk](https://open.longbridge.com/sdk)

本章节文档提供给大家参考 API 细节。

客户端可以通过 WebSocket 或者 TCP 和行情网关建立长连接，客户端订阅股票行情，行情网关会实时推送客户端订阅的实时行情。

Info

WebSocket Endpoint: `wss://openapi-quote.longbridge.com`

TCP Endpoint: `openapi-quote.longbridge.com:2020`

流程如下：

ServerClientServerClientpar\[订阅行情\]握手1链接建立2登录鉴权3返回登录鉴权结果4订阅行情请求，req\_id: 10, cmd: 65返回订阅行情响应，req\_id: 10, cmd: 66实时价格推送，cmd: 1017实时盘口推送，cmd: 1028实时经纪队列推送，cmd: 1039实时成交明细推送，cmd: 10410 n1ltwf

订阅 [​](./socket-subscribe_quote.md#%E8%AE%A2%E9%98%85)

-----------------------------------------------------------------------------------------

客户端在和服务端建立连接后需要通过订阅行情的指令订阅不同类型的行情。

订阅的 Protobuf 定义可以[查看](./quote-subscribe-subscribe.md)

Example:

json

    {
      "symbol": ["700.HK", "AAPL.US"]
      "sub_type": [1, 2]
      "is_first_push": true
    }

> 这里方便展示使用 `JSON`，实际上需要通过 protobuf 序列化请求到服务端

客户端也可以通过获取已订阅接口查看自己已经订阅的标的行情，[Protobuf 定义](./quote-subscribe-subscription.md)

订阅成功后，服务端会推送相应的标的行情到客户端，具体的数据可以查看[行情概览](./quote-overview.md)

其他接口 [​](./socket-subscribe_quote.md#%E5%85%B6%E4%BB%96%E6%8E%A5%E5%8F%A3)

-------------------------------------------------------------------------------------------------------------

行情的数据拉去都是通过长连接网关的，具体可以查看[行情概览](./quote-overview.md)

协议 [​](./socket-subscribe_quote.md#%E5%8D%8F%E8%AE%AE)

-----------------------------------------------------------------------------------------

想要订阅行情，必须先了解我们的协议，我们使用的长连接[协议](./socket-protocol-overview.md)

[LLMs Text](https://open.longbridge.com/docs/socket/subscribe_quote.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/how_to_subscribe_quote.md)

最后更新于:

Pager

[上一页交易相关](./qa-trade.md)

[下一页订阅交易推送](./socket-subscribe_trade.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
