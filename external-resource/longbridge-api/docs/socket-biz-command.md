业务指令
====

长连接目前支持行情和交易的推送，两个业务有不同的接入地址，具体[查看](./socket-hosts.md)

行情 [​](./socket-biz-command.md#%E8%A1%8C%E6%83%85)

-------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| 类型  | 功能简介 |
| 拉取  | [获取标的基础信息](./quote-pull-static.md) |
| [获取标的实时行情](./quote-pull-quote.md) |
| [获取期权实时行情](./quote-pull-option-quote.md) |
| [获取轮证实时行情](./quote-pull-warrant-quote.md) |
| [获取标的盘口](./quote-pull-depth.md) |
| [获取标的经纪队列](./quote-pull-brokers.md) |
| [获取券商席位 id](./quote-pull-broker-ids.md) |
| [获取标的成交明细](./quote-pull-trade.md) |
| [获取标的分时](./quote-pull-intraday.md) |
| [获取标的 K 线](./quote-pull-candlestick.md) |
| [获取标的的期权链到期日列表](./quote-pull-optionchain-date.md) |
| [获取标的的期权链到期日期权标的列表](./quote-pull-optionchain-date-strike.md) |
| [获取轮证发行商 ID](./quote-pull-issuer.md) |
| [获取轮证筛选列表](./quote-pull-warrant-filter.md) |
| [获取各市场当日交易时段](./quote-pull-trade-session.md) |
| [获取市场交易日](./quote-pull-trade-day.md) |
| [获取标的当日资金流向](./quote-pull-capital-flow-intraday.md) |
| [获取标的当日资金分布](./quote-pull-capital-distribution.md) |
| [获取标的计算指标](./quote-pull-calc-index.md) |
| [获取标的历史 K 线](./quote-pull-history-candlestick.md) |
| 订阅  | [获取已订阅标的行情](./quote-subscribe-subscription.md) |
| [订阅行情数据](./quote-subscribe-subscribe.md) |
| [取消订阅行情数据](./quote-subscribe-unsubscribe.md) |
| 推送  | [实时价格推送](./quote-push-quote.md) |
| [实时盘口推送](./quote-push-depth.md) |
| [实时经纪队列推送](./quote-push-broker.md) |
| [实时成交明细推送](./quote-push-trade.md) |

更多细节查看[行情接口概览](./quote-overview.md#%E8%A1%8C%E6%83%85%E6%8E%A5%E5%8F%A3%E6%A6%82%E8%A7%88)

交易 [​](./socket-biz-command.md#%E4%BA%A4%E6%98%93)

-------------------------------------------------------------------------------------

| 类型  | 功能  |
| --- | --- |
| 订阅  | [订阅推送](./trade-trade-push.md#%E8%AE%A2%E9%98%85)<br>  <br>  <br>[取消订阅](./trade-trade-push.md#%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85) |
| 通知  | [通知推送](./trade-trade-push.md#%E9%80%9A%E7%9F%A5%E6%8E%A8%E9%80%81) |

更多细节查看[交易推送](./trade-trade-push.md)

[LLMs Text](https://open.longbridge.com/docs/socket/biz-command.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/socket/biz-command.md)

最后更新于:

Pager

[上一页控制指令](./socket-control-command.md)

[下一页业务地址](./socket-hosts.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
