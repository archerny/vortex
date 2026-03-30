行情相关
====

Q1：订阅额度怎么算的，同一个标的订阅盘口，经济队列，是算 1 个还是多个？ [​](./qa-broker.md#q1-%E8%AE%A2%E9%98%85%E9%A2%9D%E5%BA%A6%E6%80%8E%E4%B9%88%E7%AE%97%E7%9A%84-%E5%90%8C%E4%B8%80%E4%B8%AA%E6%A0%87%E7%9A%84%E8%AE%A2%E9%98%85%E7%9B%98%E5%8F%A3-%E7%BB%8F%E6%B5%8E%E9%98%9F%E5%88%97-%E6%98%AF%E7%AE%97-1-%E4%B8%AA%E8%BF%98%E6%98%AF%E5%A4%9A%E4%B8%AA)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

A：仅按照标的维度计算订阅额度，同一个标的同时订阅多种行情，算同一个订阅额度。

Q2：请求限频的具体限制逻辑是怎样？ [​](./qa-broker.md#q2-%E8%AF%B7%E6%B1%82%E9%99%90%E9%A2%91%E7%9A%84%E5%85%B7%E4%BD%93%E9%99%90%E5%88%B6%E9%80%BB%E8%BE%91%E6%98%AF%E6%80%8E%E6%A0%B7)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

A：使用令牌桶进行限流，控制请求速率。1 秒内不超过 10 次调用，并发请求数不超过 5。

Q3：目前可以订阅的标的（包括指数）和对应的 symbol 格式？ [​](./qa-broker.md#q3-%E7%9B%AE%E5%89%8D%E5%8F%AF%E4%BB%A5%E8%AE%A2%E9%98%85%E7%9A%84%E6%A0%87%E7%9A%84-%E5%8C%85%E6%8B%AC%E6%8C%87%E6%95%B0-%E5%92%8C%E5%AF%B9%E5%BA%94%E7%9A%84-symbol-%E6%A0%BC%E5%BC%8F)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

A：标的代码使用 `ticker.region` 格式，`ticker` 表示标的代码。支持订阅的标的如下：

|     |     |     |     |
| --- | --- | --- | --- |
| 市场  | 标的  | Ticker | Region |
| 港股市场 | 证券类产品（含股票、ETFs、窝轮、牛熊、界内证） | 标的在交易所的官方代码 | HK  |
| 恒生指数 | HSI | HK  |
| 国企指数 | HSCEI | HK  |
| 恒生科技指数 | HSTECH | HK  |
| 美股市场 | 证券类产品（含纽交所、美交所、纳斯达克上市的股票、ETFs） | 标的在交易所的官方代码 | US  |
| 纳斯达克指数 | .IXIC | US  |
| 道琼斯指数 | .DJI | US  |
| A 股市场 | 证券类产品（含股票、ETFs） | 标的在交易所的官方代码 | SH 或 SZ |
| 指数  | 标的在交易所的官方代码 | SH 或 SZ |

可以使用 Longbridge App 查看标的的 symbol ![](https://pub.pbkrs.com/files/202206/7CSoiaDR4wGZPNCT/20220629-180013.jpeg)

Q4：OpenAPI 的行情权限是怎么样？如何购买行情卡？ [​](./qa-broker.md#q4-openapi-%E7%9A%84%E8%A1%8C%E6%83%85%E6%9D%83%E9%99%90%E6%98%AF%E6%80%8E%E4%B9%88%E6%A0%B7-%E5%A6%82%E4%BD%95%E8%B4%AD%E4%B9%B0%E8%A1%8C%E6%83%85%E5%8D%A1)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

A：

*   行情权限 应交易所规则，OpenAPI 的权限是独立的，和客户端（App、PC、Web）权限不共享。比如，你在客户端上拥有的港股 Level 2 权限并不能同样代入 OpenAPI 端使用。Longbridge 也给 OpenAPI 用户赠送了基础的行情权益，如你需要更高级别的行情，可以通过券商行情商店，或联系券商购买行情卡激活高级别行情权限。
*   如何购买行情卡  
    Longbridge 用户可以通过 Longbridge App 中的「行情商店」自行选择想要购买的行情卡。

Q5：各个市场的清盘时间 [​](./qa-broker.md#q5-%E5%90%84%E4%B8%AA%E5%B8%82%E5%9C%BA%E7%9A%84%E6%B8%85%E7%9B%98%E6%97%B6%E9%97%B4)

--------------------------------------------------------------------------------------------------------------------------------------------------------

A:

*   美股市场：09:20:00 EST
*   港股市场：08:50:00 CST
*   A 股市场：09:00:00 CST
*   新加坡市场：08:20:00 CST

Q6：如何获取夜盘行情 [​](./qa-broker.md#q6-%E5%A6%82%E4%BD%95%E8%8E%B7%E5%8F%96%E5%A4%9C%E7%9B%98%E8%A1%8C%E6%83%85)

----------------------------------------------------------------------------------------------------------------------------------------------

A:

*   夜盘行情需要主动开启，方式为在鉴权接口的 `metadata` 字段填充 key `need_over_night_quote`, value `true`。

protobuf

    message AuthRequest {
      string token = 1;
      map<string, string> metadata = 2;
    }
    
    message ReconnectRequest {
      string session_id = 1;
      map<string, string> metadata = 2;
    }

*   开启夜盘行情后，拉取和推送接口都将可以在夜盘交易时段，获取到夜盘盘情。

Q7：OpenApi SDK 中开启夜盘行情 [​](./qa-broker.md#q7-openapi-sdk-%E4%B8%AD%E5%BC%80%E5%90%AF%E5%A4%9C%E7%9B%98%E8%A1%8C%E6%83%85)

------------------------------------------------------------------------------------------------------------------------------------------------------------

A:

*   从环境变量创建 `Config` 对象

设置环境变量 `LONGBRIDGE_ENABLE_OVERNIGHT` 为 `true`（兼容旧版 `LONGPORT_ENABLE_OVERNIGHT`）

*   从构造函数创建 `Config` 对象

python

    config = Config(app_key="your_app_key", app_secret="your_app_secret", access_token="your_access_token", enable_overnight=True)

[LLMs Text](https://open.longbridge.com/docs/qa/broker.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/qa/quote.md)

最后更新于:

Pager

[上一页通用问题](./qa-general.md)

[下一页交易相关](./qa-trade.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
