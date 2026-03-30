WebSocket 和 TCP 接入的不同点
======================

Longbridge 行情长连接同时支持 `WebSocket` 和 `TCP` 的接入，不同点主要如下：

*   TCP 数据是流式的，客户端编写难度比 WebSocket 要大
*   `WebSocket` 握手包通过 [URL Query 发送](./socket-protocol-handshake.md#websocket-%E9%93%BE%E6%8E%A5%E5%A6%82%E4%BD%95%E6%8F%A1%E6%89%8B)
    
*   `WebSocket` 的 [心跳](./socket-control-command.md#%E5%BF%83%E8%B7%B3)
     通过 `WebSocket` 协议本身的心跳 `Ping-Pong` 进行
*   `WebSocket` 通信使用 `TLS` 进行加密，而 `TCP` 暂时没有

可以根据自己的需求选择，长连接协议是两者都适用的。

> 使用 `WebSocket` 接入较方便。如果对速度有更高的要求，行情可以接入 `TCP`。

[LLMs Text](https://open.longbridge.com/docs/socket/diff_ws_tcp.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/diff-ws-tcp.md)

最后更新于:

Pager

[上一页业务地址](./socket-hosts.md)

[下一页更新日志](./changelog.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
