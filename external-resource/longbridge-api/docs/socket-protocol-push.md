解析推送包
=====

推送用于一端向另一端发送数据，接收方不需要进行回复

Info

当包头中的 `type` 值为 `3` 时，数据包为推送包

     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | type=3|v|g|re.|    cmd_code   |            body_len           |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |  body_len     |               body(by body_len)               |
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
| body\_len | 24(uint32) | 3   | `body` 长度，单位：字节，最大 16 MB 数据；如果 gzip 为 1，该值为 `body` 压缩后的长度 |
| body | 可变长度，由 body\_len 决定 | 可变长度 | `body`，最大 16 MB |
| nonce | 64  | 8   | 仅当包头中的 `verify` 为 1 时存在 |
| signature | 128 | 16  | 仅当包头中的 `verify` 为 1 时存在 |

[LLMs Text](https://open.longbridge.com/docs/socket/protocol/push.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/protocol/how-to-parse-push.md)

最后更新于:

Pager

[上一页解析响应包](./socket-protocol-response.md)

[下一页控制指令](./socket-control-command.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
