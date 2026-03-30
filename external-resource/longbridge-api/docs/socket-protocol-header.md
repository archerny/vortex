解析数据包头
======

我们的协议包的大小时可变长的，这里会用一个数据包头来描述数据包携带的数据类型和数据大小。

Info

数据包头的大小是固定的 `1` 个字节

结构 [​](./socket-protocol-header.md#%E7%BB%93%E6%9E%84)

-----------------------------------------------------------------------------------------

     0 1 2 3 4 5 6 7
    +-+-+-+-+-+-+-+-+
    |  type |v|g|re.|
    +-+-+-+-+-+-+-+-+

| 字段  | 长度 (bit) | 说明  |
| --- | --- | --- |
| type | 4   | `1` - request  <br>`2` - resopnse  <br>`3` - push |
| verify | 1   | 数据是否加签标志  <br>  <br>`0` - 不加签  <br>`1` - 加签 |
| gzip | 1   | 数据是否使用 `gzip` 压缩：  <br>  <br>`1` - 压缩  <br>`0` - 不压缩 |
| reserve | 2   | 预留  |

例子 [​](./socket-protocol-header.md#%E4%BE%8B%E5%AD%90)

-----------------------------------------------------------------------------------------

    // reserve - 0, gzip - 0, verify - 0,  type - 1
    0b 0000 0001
    
    // reserve - 0, gzip - 1, verify - 0, type - 2
    0b 0011 0010
    
    // reserve - 0, gzip - 1, verify - 1, type - 3
    0b 0001 0011
    
    
    //  reserve - 3, gzip - 1, verify - 0, type - 3
    0b 1110 0011

[LLMs Text](https://open.longbridge.com/docs/socket/protocol/header.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/protocol/how-to-parse-header.md)

最后更新于:

Pager

[上一页解析握手包](./socket-protocol-handshake.md)

[下一页解析请求包](./socket-protocol-request.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
