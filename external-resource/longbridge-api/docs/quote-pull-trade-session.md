获取各市场当日交易时段
===========

该接口用于获取各市场当日交易时段。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 所有市场今日交易时段（美股、港股、A 股、新加坡）
    longbridge trading-session

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.trading\_session](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.trading_session) |
| Rust | [longbridge::quote::QuoteContext#trading\_session](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.trading_session) |
| Go  | [QuoteContext.TradingSession](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.TradingSession) |
| Node.js | [QuoteContext#tradingSession](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#tradingsession) |
| Java | [QuoteContext.getTradingSession](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getTradingSession()) |
| C++ | [longbridge::quote::QuoteContext::trading\_session](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#ab8a61cdb2a2b5b2d23f60a8dd0fdbb4f) |

Info

[业务指令](./socket-biz-command.md)
：`8`

Request [​](./quote-pull-trade-session.md#request)

-------------------------------------------------------------------------------------

### Request Example [​](./quote-pull-trade-session.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.trading_session()
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.trading_session()
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => {
        console.log("Open this URL to authorize: " + url)
      })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.tradingSession()
      for (const obj of resp) {
        console.log(obj.toString())
      }
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id")
                    .build(url -> System.out.println("Open to authorize: " + url))
                    .get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                MarketTradingSession[] resp = ctx.getTradingSession().get();
                for (MarketTradingSession obj : resp) {
                    System.out.println(obj);
                }
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id")
            .build(|url| println!("Open this URL to authorize: {url}"))
            .await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.trading_session().await?;
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
    
        ctx.trading_session([](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& s : *res) {
                std::cout << s.market << " " << s.trade_sessions.size() << std::endl;
            }
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
    	sessions, err := qctx.TradingSession(context.Background())
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, s := range sessions {
    		fmt.Println(s.Market, len(s.TradeSessions))
    	}
    }

Response [​](./quote-pull-trade-session.md#response)

---------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-trade-session.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| market\_trade\_session | object\[\] | 市场交易时段 |
| ∟ market | string | 市场  <br>  <br>`US` - 美股市场  <br>`HK` - 港股市场  <br>`CN` - A 股市场  <br>`SG` - 新加坡市场 |
| ∟ trade\_session | object\[\] | 交易时段 |
| ∟∟ beg\_time | int32 | 交易开始时间，格式：`hhmm` 例如：`900` |
| ∟∟ end\_time | int32 | 交易结束时间，格式：`hhmm` 例如：`1400` |
| ∟∟ trade\_session | int32 | 交易时段，详见 [TradeSession](./quote-objects.md#tradesession-%E4%BA%A4%E6%98%93%E6%97%B6%E6%AE%B5) |

### Protobuf [​](./quote-pull-trade-session.md#protobuf)

protobuf

    message MarketTradePeriodResponse {
      repeated MarketTradePeriod market_trade_session = 1;
    }
    
    message MarketTradePeriod {
      string market = 1;
      repeated TradePeriod trade_session = 2;
    }
    
    message TradePeriod {
      int32 beg_time = 1;
      int32 end_time = 2;
      TradeSession trade_session = 3;
    }

### Response JSON Example [​](./quote-pull-trade-session.md#response-json-example)

json

    {
      "market_trade_session": [\
        {\
          "market": "US",\
          "trade_session": [\
            {\
              "beg_time": 930,\
              "end_time": 1600\
            },\
            {\
              "beg_time": 400,\
              "end_time": 930,\
              "trade_session": 1\
            },\
            {\
              "beg_time": 1600,\
              "end_time": 2000,\
              "trade_session": 2\
            }\
          ]\
        },\
        {\
          "market": "HK",\
          "trade_session": [\
            {\
              "beg_time": 930,\
              "end_time": 1200\
            },\
            {\
              "beg_time": 1300,\
              "end_time": 1600\
            }\
          ]\
        },\
        {\
          "market": "CN",\
          "trade_session": [\
            {\
              "beg_time": 930,\
              "end_time": 1130\
            },\
            {\
              "beg_time": 1300,\
              "end_time": 1457\
            }\
          ]\
        },\
        {\
          "market": "SG",\
          "trade_session": [\
            {\
              "beg_time": 900,\
              "end_time": 1200\
            },\
            {\
              "beg_time": 1300,\
              "end_time": 1700\
            }\
          ]\
        }\
      ]
    }

错误码 [​](./quote-pull-trade-session.md#%E9%94%99%E8%AF%AF%E7%A0%81)

-----------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/trade-session.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/trade-session.md)

最后更新于:

Pager

[上一页获取轮证筛选列表](./quote-pull-warrant-filter.md)

[下一页获取市场交易日](./quote-pull-trade-day.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
