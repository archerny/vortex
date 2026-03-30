订阅交易推送
======

客户端可以通过 WebSocket 或者 TCP 和交易推送网关建立长连接，当订单状态更新时，客户端可以实时的接收通知。

Info

WebSocket Endpoint: `wss://openapi-trade.longbridge.com`

TCP Endpoint: `openapi-trade.longbridge.com`

流程如下：

ServerClientServerClientpar\[订阅\]握手1链接建立2登录鉴权3返回登录鉴权结果4订阅行情请求，req\_id: 10, cmd: 165返回订阅行情响应，req\_id: 10, cmd: 166实时订单变更推送，cmd: 187实时订单变更推送，cmd: 188 zf5rms

订阅 [​](./socket-subscribe_trade.md#%E8%AE%A2%E9%98%85)

-----------------------------------------------------------------------------------------

订阅的 Protobuf 定义可以[查看](./trade-trade-push.md)

Example:

json

    {
      "topics": ["private"]
    }

> 这里方便展示使用 `JSON`，实际上需要通过 protobuf 序列化请求到服务端

推送例子 [​](./socket-subscribe_trade.md#%E6%8E%A8%E9%80%81%E4%BE%8B%E5%AD%90)

-------------------------------------------------------------------------------------------------------------

json

    {
      "topic": "private",
      "content_type": 2,
      "dispatch_type": 1,
      "data": "eyJldmVudCI6Im9yZGVyX2NoYW5nZWRfbGIiLCJkYXRhIjp7InNpZGUiOiJCdXkiLCJzdG9ja19uYW1lIjoi6IW+6K6v5o6n6IKhIiwicXVhbnRpdHkiOiIxMDAwIiwic3ltYm9sIjoiNzAwLkhLIiwib3JkZXJfdHlwZSI6IkxPIiwicHJpY2UiOiIyMTMuMiIsImV4ZWN1dGVkX3F1YW50aXR5IjoiMTAwMCIsImV4ZWN1dGVkX3ByaWNlIjoiMjEzLjIiLCJvcmRlcl9pZCI6IjI3IiwiY3VycmVuY3kiOiJIS0QiLCJzdGF0dXMiOiJOZXdTdGF0dXMiLCJzdWJtaXR0ZWRfYXQiOiIxNTYyNzYxODkzIiwidXBkYXRlZF9hdCI6IjE1NjI3NjE4OTMiLCJ0cmlnZ2VyX3ByaWNlIjoiMjEzLjAiLCJtc2ciOiJJbnN1ZmZpY2llbnQgUXR5IC0gMTAwMCIsInRhZyI6IkdUQyIsInRyaWdnZXJfc3RhdHVzIjoiQUNUSVZFIiwidHJpZ2dlcl9hdCI6IjE1NjI3NjE4OTMiLCJ0YWlsaW5nX2Ftb3VudCI6IjUiLCJ0YWlsaW5nX3BlcmNlbnQiOiIxIiwibGltaXRfb2Zmc2V0IjoiMC4wMSIsImFjY291bnRfbm8iOiJISzEyMzQ0NSJ9fQ=="
    }

Info

`data` 是 `JSON` 字符串的二进制内容 (Base64)

`data` 的实际 `JSON` 内容如下

json

    {
      "event": "order_changed_lb",
      "data": {
        "side": "Buy",
        "stock_name": "腾讯控股",
        "quantity": "1000",
        "symbol": "700.HK",
        "order_type": "LO",
        "price": "213.2",
        "executed_quantity": "1000",
        "executed_price": "213.2",
        "order_id": "27",
        "currency": "HKD",
        "status": "NewStatus",
        "submitted_at": "1562761893",
        "updated_at": "1562761893",
        "trigger_price": "213.0",
        "msg": "Insufficient Qty - 1000",
        "tag": "GTC",
        "trigger_status": "ACTIVE",
        "trigger_at": "1562761893",
        "trailing_amount": "5",
        "trailing_percent": "1",
        "limit_offset": "0.01",
        "account_no": "HK123445",
        "last_share": "100",
        "last_price": "234",
        "remark": "abc"
      }
    }

字段解释可以查看[交易命名词典-WebSocket 推送通知](./trade-trade-definition.md#websocket-%E6%8E%A8%E9%80%81%E9%80%9A%E7%9F%A5)

协议 [​](./socket-subscribe_trade.md#%E5%8D%8F%E8%AE%AE)

-----------------------------------------------------------------------------------------

我们使用的长连接[协议](./socket-protocol-overview.md)

[LLMs Text](https://open.longbridge.com/docs/socket/subscribe_trade.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/how_to_subscribe_trade.md)

最后更新于:

Pager

[上一页订阅行情推送](./socket-subscribe_quote.md)

[下一页获取长连接 OTP](./socket-token-api.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
