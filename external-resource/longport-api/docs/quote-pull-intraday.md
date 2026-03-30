获取标的当日分时
========

该接口用于获取标的的当日分时数据。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Tesla 今日分时数据
    longbridge intraday TSLA.US
    # Apple 今日分时数据
    longbridge intraday AAPL.US
    # 腾讯今日分时数据
    longbridge intraday 700.HK

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.intraday](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.intraday) |
| Rust | [longbridge::quote::QuoteContext#intraday](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.intraday) |
| Go  | [QuoteContext.Intraday](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.Intraday) |
| Node.js | [QuoteContext#intraday](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#intraday) |
| Java | [QuoteContext.getIntraday](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getIntraday(java.lang.String%2Ccom.longbridge.quote.TradeSessions)) |
| C++ | [longbridge::quote::QuoteContext::intraday](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#ad9d3cee180163c0a27564910c4d121e8) |

Info

[业务指令](./socket-biz-command.md)
：`18`

Request [​](./quote-pull-intraday.md#request)

--------------------------------------------------------------------------------

### Parameters [​](./quote-pull-intraday.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | 是   | 标的代码，使用 `ticker.region` 格式，例如：`700.HK` |

### Protobuf [​](./quote-pull-intraday.md#protobuf)

protobuf

    message SecurityIntradayRequest {
      string symbol = 1;
    }

### Request Example [​](./quote-pull-intraday.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.intraday("700.HK")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.intraday("700.HK")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, TradeSessions } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => {
        console.log("Open this URL to authorize: " + url)
      })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.intraday("700.HK", TradeSessions.Intraday)
      console.log(resp)
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
                IntradayLine[] resp = ctx.getIntraday("700.HK", TradeSessions.Intraday).get();
                for (IntradayLine line : resp) System.out.println(line);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::{QuoteContext, TradeSessions}, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id")
            .build(|url| println!("Open this URL to authorize: {url}"))
            .await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.intraday("700.HK", TradeSessions::Intraday).await?;
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
    
        ctx.intraday("700.HK", TradeSessions::Intraday, [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            std::cout << "intraday lines: " << res->size() << std::endl;
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
    	lines, err := qctx.Intraday(context.Background(), "700.HK")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("intraday lines:", len(lines))
    }

Response [​](./quote-pull-intraday.md#response)

----------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-intraday.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码，例如：`AAPL.US` |
| lines | object\[\] | 分时数据 |
| ∟ price | string | 当前分钟的收盘价格 |
| ∟ timestamp | int64 | 当前分钟的开始时间 |
| ∟ volume | int64 | 成交量 |
| ∟ turnover | string | 成交额 |
| ∟ avg\_price | string | 均价  |

### Protobuf [​](./quote-pull-intraday.md#protobuf-1)

protobuf

    message SecurityIntradayResponse{
      string symbol = 1;
      repeated Line lines = 2;
    }
    
    message Line {
      string price = 1;
      int64 timestamp = 2;
      int64 volume = 3;
      string turnover = 4;
      string avg_price = 5;
    }

### Response JSON Example [​](./quote-pull-intraday.md#response-json-example)

json

    {
      "symbol": "700.HK",
      "lines": [\
        {\
          "price": "330.400",\
          "timestamp": 1651023000,\
          "volume": 375870,\
          "turnover": "123949699.000",\
          "avg_price": "329.767470"\
        },\
        {\
          "price": "331.200",\
          "timestamp": 1651023060,\
          "volume": 233095,\
          "turnover": "77269032.800",\
          "avg_price": "330.427416"\
        },\
        {\
          "price": "330.400",\
          "timestamp": 1651023120,\
          "volume": 192565,\
          "turnover": "63711556.000",\
          "avg_price": "330.530719"\
        },\
        {\
          "price": "330.800",\
          "timestamp": 1651023180,\
          "volume": 143397,\
          "turnover": "47471072.400",\
          "avg_price": "330.608989"\
        },\
        {\
          "price": "330.800",\
          "timestamp": 1651023240,\
          "volume": 141834,\
          "turnover": "46890605.600",\
          "avg_price": "330.608078"\
        }\
      ]
    }

错误码 [​](./quote-pull-intraday.md#%E9%94%99%E8%AF%AF%E7%A0%81)

------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求标的不存在 | 检查请求的 `symbol` 是否正确 |
| 7   | 301603 | 标的无行情 | 标的没有请求的行情数据 |
| 7   | 301604 | 无权限 | 没有获取标的行情的权限 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/intraday.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/intraday.md)

最后更新于:

Pager

[上一页获取标的成交明细](./quote-pull-trade.md)

[下一页获取标的历史 K 线](./quote-pull-history-candlestick.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
