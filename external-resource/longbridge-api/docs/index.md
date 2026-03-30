OpenAPI 介绍
==========

Longbridge Developers 为有研发能力的投资者提供程序化行情交易接口，助力投资者根据自身投资策略搭建交易或行情策略分析工具。覆盖以下类别功能：

*   交易类 - 创建、修改、撤销订单，当日/历史订单及成交记录的查询等
*   行情类 - 实时行情报价、历史行情数的获取等
*   资产类 - 实时账户资产、持仓、现金查询等
*   实时订阅 - 提供行情实时报价以及订单状态实时变更信息推送

接口类型 [​](./index.md#%E6%8E%A5%E5%8F%A3%E7%B1%BB%E5%9E%8B)

--------------------------------------------------------------------------------------

Longbridge 提供接入底层服务的 HTTP / WebSockets 接口以及封装在上层的 SDK（Python / C++ ...）等多种接入方式，灵活选择。

如何开通 [​](./index.md#%E5%A6%82%E4%BD%95%E5%BC%80%E9%80%9A)

--------------------------------------------------------------------------------------

1.  登录 [Longbridge App](https://longbridge.com/download)
     完成开户；
2.  登录 [longbridge.com](https://longbridge.com/)
     进入开发者平台，完成开发者认证即 OpenAPI 权限申请，获取令牌。

行情覆盖 [​](./index.md#%E8%A1%8C%E6%83%85%E8%A6%86%E7%9B%96)

--------------------------------------------------------------------------------------

| 市场  | 标的  |
| --- | --- |
| 港股  | 证券类产品（含股票、ETFs、窝轮、牛熊、界内证） |
| 恒生指数 |
| 美股  | 证券类产品（含纽交所、美交所、纳斯达克上市的股票、ETFs） |
| 纳斯达克指数 |
| OPRA 期权 |
| A 股 | 证券类产品（含股票、ETFs） |
| 指数  |

交易标的类别 [​](./index.md#%E4%BA%A4%E6%98%93%E6%A0%87%E7%9A%84%E7%B1%BB%E5%88%AB)

----------------------------------------------------------------------------------------------------------

目前 OpenAPI 支持交易一下标的类别：

| 市场  | 股票 ETF | 权证  | 期权  |
| --- | --- | --- | --- |
| 香港市场 | ✓   | ✓   |     |
| 美国市场 | ✓   | ✓   | ✓   |

频率限制 [​](./index.md#rate-limit)

------------------------------------------------------------

| 类别  | 限制规则 |
| --- | --- |
| 行情相关 API | *   一个账号同时只能建立一个长连接，最多同时订阅 500 个标的<br>*   1 秒内不超过 10 次调用，并发请求数不超过 5 |
| 交易相关 API | *   30 秒内累计不超过 30 次调用，且每两次调用之间间隔不小于 0.02 秒 |

Success

我们 [OpenAPI SDK](https://open.longbridge.com/sdk)
 内部已经做了有效的频率控制：

*   行情类：`QuoteContext` 下的接口，SDK 内部会按照服务端的频率限制来主动控制，当请求过快的时候，SDK 会自动延迟请求。因此你可以不需要额外实现频率控制细节。
*   交易类：`TradeContext` 下的接口，SDK 没有做限制，由于交易下单场景特殊性，将这个交由用户自行处理。

使用费用 [​](./index.md#pricing)

---------------------------------------------------------

Longbridge 不针对接口服务额外收取开通或使用费用，只需开通 Longbridge 账户及 OpenAPI 服务权限后即可免费使用。实际交易费率请咨询您开通证券账户的券商。

其他 [​](./index.md#%E5%85%B6%E4%BB%96)

------------------------------------------------------------------

OpenAPI 服务由 Longbridge 及其适用的关联公司提供（具体以协议为准）。

[LLMs Text](https://open.longbridge.com/docs.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/index.md)

最后更新于:

Pager

[下一页快速开始](./getting-started.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
