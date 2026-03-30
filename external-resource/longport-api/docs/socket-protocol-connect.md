通信过程
====

客户端在和服务端交互时，会有三种数据包类型：

*   握手 - 建立连接
*   请求 - 客户端向服务端发起请求
*   响应 - 服务端向客户端响应请求
*   推送 - 服务端向客户端推送数据

握手 [​](./socket-protocol-connect.md#%E6%8F%A1%E6%89%8B)

------------------------------------------------------------------------------------------

serverclientserverclientalt\[handshake invalid\]\[is valid\]1\. handshake1.1 check handshake2\. disconnect2\. build connection b637u8

客户端向服务端发送握手包后，链接就建立了，服务端会判断握手包是否合法，不合法则发送一个错误包，并且断开底层连接。如果链接的是 TCP 服务端可以同时发送握手包和第一个数据包。

请求与响应 [​](./socket-protocol-connect.md#%E8%AF%B7%E6%B1%82%E4%B8%8E%E5%93%8D%E5%BA%94)

------------------------------------------------------------------------------------------------------------------------

协议支持，`请求 <--> 响应` 的通信方式，即客户端发送一个请求，服务端返回一个响应。

serverclientserverclientpar\[request 1\]par\[request 100\]par\[request n\]request, req\_id: 11response, req\_id: 12request, req\_id: 1003response: req\_id: 1004request, req\_id: n5response, req\_id: n6 ylgo1f

客户端和服务端握手成功后，双方就可以进行 `请求 <--> 响应` 的通信，请求和响应通过请求 `id` 进行关联。

推送 [​](./socket-protocol-connect.md#%E6%8E%A8%E9%80%81)

------------------------------------------------------------------------------------------

推送是一端向另一端直接推送数据而不需要另一端响应。

> 目前仅存在服务端向客户端推送数据的场景。

clientserverclientserverpush, data 1push, data 2 8qwl3h

[LLMs Text](https://open.longbridge.com/docs/socket/protocol/connect.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/protocol/connect.md)

最后更新于:

Pager

[上一页协议概览](./socket-protocol-overview.md)

[下一页解析握手包](./socket-protocol-handshake.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
