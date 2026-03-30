获取长连接 OTP(One Time Password) [​](./socket-token-api.md#%E8%8E%B7%E5%8F%96%E9%95%BF%E8%BF%9E%E6%8E%A5-otp-one-time-password)

==============================================================================================================================================================

获取长连接使用的 `Token`(One time password)，长连接的 `Token` 可以用来连接行情和交易的长连接网关，是一次性的，使用过后就会作废。尝试一下

> 最后更新于 2022-04-28

请求 [​](./socket-token-api.md#%E8%AF%B7%E6%B1%82)

-----------------------------------------------------------------------------------

| 基本信息 |     |
| --- | --- |
| HTTP URL | /v1/socket/token |
| HTTP Method | GET |

### 请求头 [​](./socket-token-api.md#%E8%AF%B7%E6%B1%82%E5%A4%B4)

| 名称  | 类型  | 必须  | 描述  |
| --- | --- | --- | --- |
| Authorization | string | 是   |     |
| Content-Type | string | 是   | **固定值**："application/json; charset=utf-8" |

### 请求参数 [​](./socket-token-api.md#%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0)

响应 [​](./socket-token-api.md#%E5%93%8D%E5%BA%94)

-----------------------------------------------------------------------------------

### 响应体 [​](./socket-token-api.md#%E5%93%8D%E5%BA%94%E4%BD%93)

| 名称  | 类型  | 描述  |
| --- | --- | --- |
| code | int | 错误码，非 0 表示失败 |
| msg | string | 错误描述 |
| data | object |     |
| ∟otp | string | 获取到的 token |
| ∟limit | int | 连接限制总数 |
| ∟online | int | 当前在线连接数 |

### 响应体示例 [​](./socket-token-api.md#%E5%93%8D%E5%BA%94%E4%BD%93%E7%A4%BA%E4%BE%8B)

json

    {
      "code": 0,
      "message": "",
      "data": {
        "otp": "xxxxxxxx",
        "limit": 10,
        "online": 3
      }
    }

[LLMs Text](https://open.longbridge.com/docs/socket-token-api.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/socket-otp-api.md)

最后更新于:

Pager

[上一页订阅交易推送](./socket-subscribe_trade.md)

[下一页协议概览](./socket-protocol-overview.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
