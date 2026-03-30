解析响应包
=====

当服务端收到客户端的请求包后必须响应一个响应包回来

Info

当包头中的 `type` 值为 `2` 时，数据包为请求包

结构 [​](./socket-protocol-response.md#%E7%BB%93%E6%9E%84)

-------------------------------------------------------------------------------------------

     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | type=2|v|g|re.|    cmd_code   |           request_id          |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                               |  status_code  |    body_len   |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |            body_len           |                               |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+                               +
    |                       body(by body_len)                       |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                                                               |
    +                        nonce(optional)                        +
    |                                                               |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                                                               |
    +                                                               +
    |                                                               |
    +                      signature(optional)                      +
    |                                                               |
    +                                                               +
    |                                                               |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

字段说明：

| 字段  | 长度 (bit) | 长度（字节） | 说明  |
| --- | --- | --- | --- |
| cmd\_code | 8   | 1   | 指令 cmd 值 |
| request\_id | 32(uint32) | 4   | 请求 id，同一个连接的 id 需要唯一，从 1 开始，到达 4294967295 后从新开始。 |
| status | 8(uint8) | 1   | 状态码 `0` - 成功；参考状态码表 |
| body\_len | 24(uint32) | 3   | `body` 长度，单位：字节，最大 16 MB 数据；如果 gzip 为 1，该值为 body 压缩后的长度 |
| body | 可变长度，由 body\_len 决定 | 可变长度 | `body`，最大 16 MB |
| nonce | 64  | 8   | 仅当包头中的 `verify` 为 1 时存在 |
| signature | 128 | 16  | 仅当包头中的 verify 为 1 时存在 |

响应包状态码 [​](./socket-protocol-response.md#%E5%93%8D%E5%BA%94%E5%8C%85%E7%8A%B6%E6%80%81%E7%A0%81)

-----------------------------------------------------------------------------------------------------------------------------------

响应包有状态说明：

| 值   | 标识  | 说明  |
| --- | --- | --- |
| 0   | SUCCESS | 成功，类似于 HTTP 200 |
| 1   | SERVER\_TIMEOUT | 服务端超时，类似于 HTTP 408 |
| 3   | BAD\_REQUEST | 请求错误，通常为参数错误，类似于 HTTP 400 |
| 5   | UNAUTHENTICATED | 鉴权失败，类似于 HTTP 401 |
| 7   | SERVER\_INTERNAL\_ERROR | 服务端内部错误，类似于 HTTP 500 |

[LLMs Text](https://open.longbridge.com/docs/socket/protocol/response.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/protocol/how-to-parse-response.md)

最后更新于:

Pager

[上一页解析请求包](./socket-protocol-request.md)

[下一页解析推送包](./socket-protocol-push.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
