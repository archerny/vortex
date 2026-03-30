行情接口概览 [​](./quote-overview.md#%E8%A1%8C%E6%83%85%E6%8E%A5%E5%8F%A3%E6%A6%82%E8%A7%88)

=========================================================================================================================

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
| [获取轮证发行商 id](./quote-pull-issuer.md) |
| [获取轮证筛选列表](./quote-pull-warrant-filter.md) |
| [获取各市场当日交易时段](./quote-pull-trade-session.md) |
| [获取市场交易日](./quote-pull-trade-day.md) |
| [获取标的当日资金流向](./quote-pull-capital-flow-intraday.md) |
| [获取标的当日资金分布](./quote-pull-capital-distribution.md) |
| [获取标的计算指标](./quote-pull-calc-index.md) |
| [获取标的历史 k 线](./quote-pull-history-candlestick.md) |
| 订阅  | [获取已订阅标的行情](./quote-subscribe-subscription.md) |
| [订阅行情数据](./quote-subscribe-subscribe.md) |
| [取消订阅行情数据](./quote-subscribe-unsubscribe.md) |
| 推送  | [实时价格推送](./quote-push-quote.md) |
| [实时盘口推送](./quote-push-depth.md) |
| [实时经纪队列推送](./quote-push-broker.md) |
| [实时成交明细推送](./quote-push-trade.md) |
| 个性化 | [创建自选股分组](./quote-individual-watchlist_create_group.md) |
| [删除自选股分组](./quote-individual-watchlist_delete_group.md) |
| [获取自选股分组](./quote-individual-watchlist_groups.md) |
| [更新自选股分组](./quote-individual-watchlist_update_group.md) |
| 标的  | [获取标的列表](./quote-security-security_list.md) |

标的代码说明 [​](./quote-overview.md#%E6%A0%87%E7%9A%84%E4%BB%A3%E7%A0%81%E8%AF%B4%E6%98%8E)

-------------------------------------------------------------------------------------------------------------------------

标的代码使用 `ticker.region` 格式，`ticker` 表示标的代码，各个市场的标的代码示例：

*   美股市场：`region` 为 `US`，例如：`AAPL.US`
*   港股市场：`region` 为 `HK`，例如：`700.HK`
*   A 股市场：`region` 上交所为 `SH`，深交所为 `SZ`，例如：`399001.SZ`，`600519.SH`
*   新加坡市场：`region` 为 `SG`，例如：`D05.SG`

接入方式 [​](./quote-overview.md#%E6%8E%A5%E5%85%A5%E6%96%B9%E5%BC%8F)

-----------------------------------------------------------------------------------------------------

1.  使用私有协议，长连接方式进行接入，接入方法请参考 [二进制通信协议](./socket-protocol-overview.md)
    。
2.  使用 SDK 进行接入，[SDK 介绍及下载地址](https://open.longbridge.com/sdk)
    。

业务数据序列化方式 [​](./quote-overview.md#%E4%B8%9A%E5%8A%A1%E6%95%B0%E6%8D%AE%E5%BA%8F%E5%88%97%E5%8C%96%E6%96%B9%E5%BC%8F)

-------------------------------------------------------------------------------------------------------------------------------------------------------

行情的请求、响应、推送数据作为业务数据，存放在私有协议的数据包 body 部分。 我们使用 [Protobuf](https://developers.google.cn/protocol-buffers)
 协议对业务数据进行序列化，相较于常见的文本协议（如 JSON, XML 等），Protobuf 协议具有如下优点：

*   序列化时间快
*   数据包体积小
*   较强的版本前向后向兼容性

行情 Protobuf 协议文档[下载地址](https://github.com/longbridge/openapi-protobufs/blob/main/quote/api.proto)
。

[LLMs Text](https://open.longbridge.com/docs/quote/overview.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/overview.md)

最后更新于:

Pager

[上一页LLM](./llm.md)

[下一页命名词典](./quote-objects.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
