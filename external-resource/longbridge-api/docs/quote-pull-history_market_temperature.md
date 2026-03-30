历史市场温度
======

该接口用于获取历史市场温度。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 港股 2025 年 Q1 历史温度
    longbridge market-temp HK --history --start 2025-01-01 --end 2025-03-31
    # 美股 2025 年 1 月历史温度
    longbridge market-temp US --history --start 2025-01-01 --end 2025-01-31
    # A 股 2025 年上半年历史温度
    longbridge market-temp CN --history --start 2025-01-01 --end 2025-06-30

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.history\_market\_temperature](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.history_market_temperature) |
| Rust | [longbridge::quote::QuoteContext#history\_market\_temperature](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.history_market_temperature) |
| Go  | [QuoteContext.HistoryMarketTemperature](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.HistoryMarketTemperature) |
| Node.js | [QuoteContext#historyMarketTemperature](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#historymarkettemperature) |
| Java | [QuoteContext.getHistoryMarketTemperature](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getHistoryMarketTemperature(com.longbridge.Market%2Cjava.time.LocalDate%2Cjava.time.LocalDate)) |
| C++ | [longbridge::quote::QuoteContext::history\_market\_temperature](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#aa766f5cdb11ac2ebec96e942a4bf97fd) |

Request [​](./quote-pull-history_market_temperature.md#request)

--------------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/quote/history\_market\_temperature |

### Parameters [​](./quote-pull-history_market_temperature.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| market | string | YES | 市场，目前支持 US、HK、SG、CN |
| start\_date | string | YES | 开始日期，最小到 2016 年，比如：20240101 |
| end\_date | string | YES | 结束日期，比如：20250101 |

### Request Example [​](./quote-pull-history_market_temperature.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    import datetime
    from longbridge.openapi import QuoteContext, Config, Market, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    resp = ctx.history_market_temperature(Market.US, datetime.date(2024, 1, 1), datetime.date(2025, 1, 1))
    print(resp)

python

    import asyncio
    import datetime
    from longbridge.openapi import AsyncQuoteContext, Config, Market, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        resp = await ctx.history_market_temperature(Market.US, datetime.date(2024, 1, 1), datetime.date(2025, 1, 1))
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, Market, NaiveDate } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.historyMarketTemperature(Market.US, new NaiveDate(2024, 1, 1), new NaiveDate(2024, 1, 31))
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    import java.time.LocalDate;
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                HistoryMarketTemperatureResponse resp = ctx.getHistoryMarketTemperature(Market.US, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)).get();
                System.out.println(resp);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config, Market};
    use time::macros::date;
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.history_market_temperature(Market::US, date!(2024 - 01 - 01), date!(2024 - 01 - 31)).await?;
        println!("{:?}", resp);
        Ok(())
    }

cpp

    #include <iostream>
    #include <longbridge.hpp>
    
    #ifdef WIN32
    #include <windows.h>
    #endif
    
    using namespace longbridge;
    using namespace longbridge::quote;
    
    static void
    run(const OAuth& oauth)
    {
        Config config = Config::from_oauth(oauth);
        QuoteContext ctx = QuoteContext::create(config);
    
        ctx.history_market_temperature(Market::US, Date{2024, 1, 1}, Date{2024, 1, 31}, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "records: " << res->records.size() << std::endl;
        });
    }
    
    int main(int argc, char const* argv[]) {
    #ifdef WIN32
        SetConsoleOutputCP(CP_UTF8);
    #endif
    
        const std::string client_id = "your-client-id";
        OAuthBuilder(client_id).build(
        [](const std::string& url) {
            std::cout << "Open this URL to authorize: " << url << std::endl;
        },
        [](auto res) {
            if (!res) {
                std::cout << "authorization failed: " << *res.status().message() << std::endl;
                return;
            }
            run(*res);
        });
    
        std::cin.get();
        return 0;
    }

go

    package main
    
    import (
    	"context"
    	"fmt"
    	"log"
    
    	"github.com/longbridge/openapi-go/config"
    	"github.com/longbridge/openapi-go/oauth"
    	"github.com/longbridge/openapi-go/quote"
    )
    
    func main() {
    	o := oauth.New("your-client-id").
    		OnOpenURL(func(url string) { fmt.Println("Open this URL to authorize:", url) })
    	if err := o.Build(context.Background()); err != nil {
    		log.Fatal(err)
    	}
    	conf, err := config.New(config.WithOAuthClient(o))
    	if err != nil {
    		log.Fatal(err)
    	}
    	qctx, err := quote.NewFromCfg(conf)
    	if err != nil {
    		log.Fatal(err)
    	}
    	defer qctx.Close()
    	// History market temperature: use HTTP client for GET /v1/quote/history-market-temperature
    	_ = qctx
    	fmt.Println("See openapi-go for HTTP quote APIs")
    }

Response [​](./quote-pull-history_market_temperature.md#response)

----------------------------------------------------------------------------------------------------

### Response Headers [​](./quote-pull-history_market_temperature.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-pull-history_market_temperature.md#response-example)

json

    {
      "code": 0,
      "data": {
        "type": "month",
        "list": [\
          {\
            "timestamp": 1580486400,\
            "temperature": 36,\
            "valuation": 12,\
            "sentiment": 46\
          },\
          {\
            "timestamp": 1582992000,\
            "temperature": 46,\
            "valuation": 12,\
            "sentiment": 46\
          }\
        ]
      }
    }

#### Response Status [​](./quote-pull-history_market_temperature.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [HistoryMarketTemperatureResponse](./quote-pull-history_market_temperature.md#history-market-temperature-response) |
| 400 | 参数错误 | None |

Schemas [​](./quote-pull-history_market_temperature.md#schemas)

--------------------------------------------------------------------------------------------------

### HistoryMarketTemperatureResponse [​](./quote-pull-history_market_temperature.md#historymarkettemperatureresponse)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| list | object\[\] | true | 历史温度列表 |
| ∟timestamp | integer | true | 时间戳 |
| ∟temperature | integer | true | 温度值 |
| ∟valuation | integer | true | 估值值 |
| ∟sentiment | integer | true | 情绪值 |
| type | string | true | 数据颗粒度  <br>day: 日;week: 周;month: 月 |

错误码 [​](./quote-pull-history_market_temperature.md#%E9%94%99%E8%AF%AF%E7%A0%81)

------------------------------------------------------------------------------------------------------------------

| 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- |
| 2601500 | 服务端内部错误 | 请重试或联系技术人员处理 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/history_market_temperature.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/history-market-temp.md)

最后更新于:

Pager

[上一页当前市场温度](./quote-pull-market_temperature.md)

[下一页订阅行情数据](./quote-subscribe-subscribe.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
