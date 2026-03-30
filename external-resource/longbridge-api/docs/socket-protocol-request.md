解析请求包
=====

当客户端想要请求服务端获取数据时必须通过请求包发送相应的请求。

Info

当包头中的 `type` 值为 `1` 时，数据包为请求包

结构如下：

      0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | type=1|v|g|re.|    cmd_code   |           request_id          |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                               |            timeout            |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                    body_len                   |               |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+               +
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

包体字段说明如下：

| 字段  | 长度 (bit) | 长度（字节） | 说明  |
| --- | --- | --- | --- |
| cmd\_code | 8   | 1   | 指令 cmd 值 |
| request\_id | 32(uint32) | 4   | 请求 id，同一个连接的 id 需要唯一，从 1 开始，到达 4294967295 后从新开始。 |
| timeout | 16(uint16) | 2   | `timeout` 单位毫秒，最大 60000（60s） |
| body\_len | 24(uint32) | 3   | `body` 长度，单位：字节，最大 16 MB 数据；如果 gzip 为 1，该值为 `body` 压缩后的长度 |
| body | 可变长度，由 body\_len 决定 | 可变长度 | `body`，最大 16 MB |
| nonce | 64  | 8   | 仅当包头中的 `verify` 为 1 时存在 |
| signature | 128 | 16  | 仅当包头中的 verify 为 1 时存在 |

Info

获取到数据包后，通过反序列化 `body` 得到具体的业务数据

[LLMs Text](https://open.longbridge.com/docs/socket/protocol/request.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/protocol/how-to-parse-request.md)

最后更新于:

Pager

[上一页解析数据包头](./socket-protocol-header.md)

[下一页解析响应包](./socket-protocol-response.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
