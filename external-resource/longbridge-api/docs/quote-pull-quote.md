获取标的实时行情
========

该接口用于获取标的的实时行情 (支持所有类型标的）。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Tesla 实时行情
    longbridge quote TSLA.US
    # 同时查询多只美股
    longbridge quote AAPL.US NVDA.US
    # 混合查询美港股
    longbridge quote TSLA.US 700.HK AAPL.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.quote](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.quote) |
| Rust | [longbridge::quote::QuoteContext#quote](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.quote) |
| Go  | [QuoteContext.Quote](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.Quote) |
| Node.js | [QuoteContext#quote](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#quote) |
| Java | [QuoteContext.getQuote](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getQuote(java.lang.String%5B%5D)) |
| C++ | [longbridge::quote::QuoteContext::quote](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#aa7a4595e9eb5830e935322a8ddc601f5) |

Info

[业务指令](./socket-biz-command.md)
：`11`

Request [​](./quote-pull-quote.md#request)

-----------------------------------------------------------------------------

### Parameters [​](./quote-pull-quote.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string\[\] | 是   | 标的代码列表，使用 `ticker.region` 格式，例如：`[700.HK]`  <br>  <br>**校验规则：**  <br>每次请求支持传入的标的数量上限是 `500` 个 |

### Protobuf [​](./quote-pull-quote.md#protobuf)

protobuf

    message MultiSecurityRequest {
      repeated string symbol = 1;
    }

### Request Example [​](./quote-pull-quote.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.quote(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"])
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.quote(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"])
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
      const resp = await ctx.quote(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"])
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
                SecurityQuote[] resp = ctx.getQuote(new String[] { "700.HK", "AAPL.US", "TSLA.US", "NFLX.US" }).get();
                for (SecurityQuote obj : resp) {
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
        let resp = ctx.quote(["700.HK", "AAPL.US", "TSLA.US", "NFLX.US"]).await?;
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
    
        std::vector<std::string> symbols = {"700.HK", "AAPL.US", "TSLA.US", "NFLX.US"};
        ctx.quote(symbols, [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& it : *res) {
                std::cout << it.symbol << " last_done=" << (double)it.last_done << std::endl;
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
    	quotes, err := qctx.Quote(context.Background(), []string{"700.HK", "AAPL.US", "TSLA.US", "NFLX.US"})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", quotes[0])
    }

Response [​](./quote-pull-quote.md#response)

-------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-quote.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| secu\_quote | object\[\] | 标的实时行情数据列表 |
| ∟ symbol | string | 标的代码 |
| ∟ last\_done | string | 最新价 |
| ∟ prev\_close | string | 昨收价 |
| ∟ open | string | 开盘价 |
| ∟ high | string | 最高价 |
| ∟ low | string | 最低价 |
| ∟ timestamp | int64 | 最新成交的时间戳 |
| ∟ volume | int64 | 成交量 |
| ∟ turnover | string | 成交额 |
| ∟ trade\_status | int32 | 标的交易状态，详见 [TradeStatus](./quote-objects.md#tradestatus-%E4%BA%A4%E6%98%93%E7%8A%B6%E6%80%81) |
| ∟ pre\_market\_quote | object | 美股盘前交易行情 |
| ∟∟ last\_done | string | 最新价 |
| ∟∟ timestamp | int64 | 最新成交的时间戳 |
| ∟∟ volume | int64 | 成交量 |
| ∟∟ turnover | string | 成交额 |
| ∟∟ high | string | 最高价 |
| ∟∟ low | string | 最低价 |
| ∟∟ prev\_close | string | 上一个交易阶段的收盘价 |
| ∟ post\_market\_quote | object | 美股盘后交易行情 |
| ∟∟ last\_done | string | 最新价 |
| ∟∟ timestamp | int64 | 最新成交的时间戳 |
| ∟∟ volume | int64 | 成交量 |
| ∟∟ turnover | string | 成交额 |
| ∟∟ high | string | 最高价 |
| ∟∟ low | string | 最低价 |
| ∟∟ prev\_close | string | 上一个交易阶段的收盘价 |
| ∟ overnight\_quote | object | 美股夜盘交易行情  <br>  <br>注意：需开启 `enable_overnight` 参数，否则会返回 null |
| ∟∟ last\_done | string | 最新价 |
| ∟∟ timestamp | int64 | 最新成交的时间戳 |
| ∟∟ volume | int64 | 成交量 |
| ∟∟ turnover | string | 成交额 |
| ∟∟ high | string | 最高价 |
| ∟∟ low | string | 最低价 |
| ∟∟ prev\_close | string | 上一个交易阶段的收盘价 |

### 注意 [​](./quote-pull-quote.md#%E6%B3%A8%E6%84%8F)

#### `overnight_quote` 参数细节 [​](./quote-pull-quote.md#overnight-quote-%E5%8F%82%E6%95%B0%E7%BB%86%E8%8A%82)

只有当我们在配置的时候开启了 `enable_overnight` 参数，才会返回 `overnight_quote` 字段。

py

    config = Config(
        app_key="your_app_key",
        app_secret="your_app_secret",
        access_token="your_access_token",
        enable_overnight=True)

或者设置环境变量 `LONGBRIDGE_ENABLE_OVERNIGHT` 为 `true`（兼容旧版 `LONGPORT_ENABLE_OVERNIGHT`）。

### Protobuf [​](./quote-pull-quote.md#protobuf-1)

protobuf

    message SecurityQuoteResponse {
      repeated SecurityQuote secu_quote = 1;
    }
    
    message SecurityQuote {
      string symbol = 1;
      string last_done = 2;
      string prev_close = 3;
      string open = 4;
      string high = 5;
      string low = 6;
      int64 timestamp = 7;
      int64 volume = 8;
      string turnover = 9;
      TradeStatus trade_status = 10;
      PrePostQuote pre_market_quote = 11;
      PrePostQuote post_market_quote = 12;
    }
    
    message PrePostQuote {
      string last_done = 1;
      int64 timestamp = 2;
      int64 volume = 3;
      string turnover = 4;
      string high = 5;
      string low = 6;
      string prev_close = 7;
    }

### Response JSON Example [​](./quote-pull-quote.md#response-json-example)

json

    {
      "secu_quote": [\
        {\
          "symbol": "700.HK",\
          "last_done": "338.000",\
          "prev_close": "334.800",\
          "open": "340.600",\
          "high": "340.600",\
          "low": "333.000",\
          "timestamp": 1651115955,\
          "volume": 7310881,\
          "turnover": "2461463161.000"\
        },\
        {\
          "symbol": "AAPL.US",\
          "last_done": "156.570",\
          "prev_close": "156.800",\
          "open": "155.910",\
          "high": "159.790",\
          "low": "155.380",\
          "timestamp": 1651089600,\
          "volume": 88063191,\
          "turnover": "13865092584.000",\
          "pre_market_quote": {\
            "last_done": "155.880",\
            "timestamp": 1651066201,\
            "volume": 1575504,\
            "turnover": "246653442.000",\
            "high": "158.400",\
            "low": "155.100",\
            "prev_close": "156.800"\
          },\
          "post_market_quote": {\
            "last_done": "158.770",\
            "timestamp": 1651103995,\
            "volume": 6188441,\
            "turnover": "970874184.759",\
            "high": "159.400",\
            "low": "156.400",\
            "prev_close": "156.570"\
          }\
        }\
      ]
    }

接口限制 [​](./quote-pull-quote.md#%E6%8E%A5%E5%8F%A3%E9%99%90%E5%88%B6)

-------------------------------------------------------------------------------------------------------

Caution

*   港股 BMP 行情，超过 20 支的港股标的将响应延迟行情。

错误码 [​](./quote-pull-quote.md#%E9%94%99%E8%AF%AF%E7%A0%81)

---------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301607 | 接口限制 | 请求的标的数量超限，请减少单次请求标的数量 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/quote.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/quote.md)

最后更新于:

Pager

[上一页获取标的基础信息](./quote-pull-static.md)

[下一页获取期权实时行情](./quote-pull-option-quote.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
