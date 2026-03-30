当前市场温度
======

获取当前市场温度尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 港股市场温度
    longbridge market-temp HK
    # 美股市场温度
    longbridge market-temp US
    # A 股市场温度
    longbridge market-temp CN

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.market\_temperature](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.market_temperature) |
| Rust | [longbridge::quote::QuoteContext#market\_temperature](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.market_temperature) |
| Go  | [QuoteContext.MarketTemperature](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.MarketTemperature) |
| Node.js | [QuoteContext#marketTemperature](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#markettemperature) |
| Java | [QuoteContext.getMarketTemperature](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getMarketTemperature(com.longbridge.Market)) |
| C++ | [longbridge::quote::QuoteContext::market\_temperature](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a24a820b09f5fd60b76016d299136748d) |

Request [​](./quote-pull-market_temperature.md#request)

------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/quote/market\_temperature |

### Parameters [​](./quote-pull-market_temperature.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| market | string | YES | 市场，目前支持 US、HK、SG、CN |

### Request Example [​](./quote-pull-market_temperature.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, Market, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    resp = ctx.market_temperature(Market.US)
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, Market, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        resp = await ctx.market_temperature(Market.US)
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, Market } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.marketTemperature(Market.US)
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                MarketTemperature resp = ctx.getMarketTemperature(Market.US).get();
                System.out.println(resp);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config, Market};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.market_temperature(Market::US).await?;
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
    
        ctx.market_temperature(Market::US, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "temperature: " << res->temperature << std::endl;
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
    	// Market temperature API: use HTTP client to call GET /v1/quote/market-temperature
    	_ = qctx
    	fmt.Println("See openapi-go for HTTP quote APIs")
    }

Response [​](./quote-pull-market_temperature.md#response)

--------------------------------------------------------------------------------------------

### Response Headers [​](./quote-pull-market_temperature.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-pull-market_temperature.md#response-example)

json

    {
      "code": 0,
      "data": {
        "temperature": 50,
        "description": "温度适宜，保持平稳",
        "valuation": 23,
        "sentiment": 78,
        "updated_at": 1744616612
      }
    }

#### Response Status [​](./quote-pull-market_temperature.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [MarketTemperatureResponse](./quote-pull-market_temperature.md#market-temperature-response) |
| 400 | 参数错误 | None |

Schemas [​](./quote-pull-market_temperature.md#schemas)

------------------------------------------------------------------------------------------

### MarketTemperatureResponse [​](./quote-pull-market_temperature.md#markettemperatureresponse)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| temperature | integer | true | 温度值 |
| description | string | true | 温度描述 |
| valuation | integer | true | 市场估值 |
| sentiment | integer | true | 市场情绪 |
| updated\_at | integer | true | 更新时间 |

错误码 [​](./quote-pull-market_temperature.md#%E9%94%99%E8%AF%AF%E7%A0%81)

----------------------------------------------------------------------------------------------------------

| 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- |
| 2601500 | 服务端内部错误 | 请重试或联系技术人员处理 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/market_temperature.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/market-temp.md)

最后更新于:

Pager

[上一页获取标的公告](./quote-pull-filings.md)

[下一页历史市场温度](./quote-pull-history_market_temperature.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
