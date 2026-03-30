获取标的当日资金分布
==========

该接口用于获取标的当日的资金分布。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Tesla 资金分布快照（大/中/小单）
    longbridge capital-dist TSLA.US
    # Apple 资金分布快照
    longbridge capital-dist AAPL.US
    # NVDA 资金分布快照
    longbridge capital-dist NVDA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.capital\_distribution](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.capital_distribution) |
| Rust | [longbridge::quote::QuoteContext#capital\_distribution](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.capital_distribution) |
| Go  | [QuoteContext.CapitalDistribution](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.CapitalDistribution) |
| Node.js | [QuoteContext#capitalDistribution](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#capitaldistribution) |
| Java | [QuoteContext.getCapitalDistribution](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getCapitalDistribution(java.lang.String)) |
| C++ | [longbridge::quote::QuoteContext::capital\_distribution](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a6a8ba241ac3c8249a2a5d0c58c10a786) |

Info

[业务指令](./socket-biz-command.md)
：`25`

Request [​](./quote-pull-capital-distribution.md#request)

--------------------------------------------------------------------------------------------

### Parameters [​](./quote-pull-capital-distribution.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | 是   | 标的代码，使用 `ticker.region` 格式，例如： `700.HK` |

### Protobuf [​](./quote-pull-capital-distribution.md#protobuf)

protobuf

    message SecurityRequest {
      string symbol = 1;
    }

### Request Example [​](./quote-pull-capital-distribution.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.capital_distribution("700.HK")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.capital_distribution("700.HK")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.capitalDistribution("700.HK")
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
                CapitalDistributionResponse resp = ctx.getCapitalDistribution("700.HK").get();
                System.out.println(resp);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.capital_distribution("700.HK").await?;
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
    
        ctx.capital_distribution("700.HK", [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "capital_distribution: " << res->symbol << std::endl;
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
    	dist, err := qctx.CapitalDistribution(context.Background(), "700.HK")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", dist)
    }

Response [​](./quote-pull-capital-distribution.md#response)

----------------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-capital-distribution.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码 |
| timestamp | int64 | 数据更新时间戳 |
| capital\_in | object\[\] | 流入资金 |
| ∟ large | string | 大单  |
| ∟ medium | string | 中单  |
| ∟ small | string | 小单  |
| capital\_out | object\[\] | 流出资金 |
| ∟ large | string | 大单  |
| ∟ medium | string | 中单  |
| ∟ small | string | 小单  |

### Protobuf [​](./quote-pull-capital-distribution.md#protobuf-1)

protobuf

    message CapitalDistributionResponse {
      message CapitalDistribution {
        string large = 1;
        string medium = 2;
        string small = 3;
      }
      string symbol = 1;
      int64 timestamp = 2;
      CapitalDistribution capital_in = 3;
      CapitalDistribution capital_out = 4;
    }

### Response JSON Example [​](./quote-pull-capital-distribution.md#response-json-example)

json

    {
      "symbol": "700.HK",
      "timestamp": "1655107800",
      "capital_in": {
        "large": "935389700.000",
        "medium": "2056032380.000",
        "small": "828715920.000"
      },
      "capital_out": {
        "large": "1175331560.000",
        "medium": "2271829740.000",
        "small": "751648940.000"
      }
    }

错误码 [​](./quote-pull-capital-distribution.md#%E9%94%99%E8%AF%AF%E7%A0%81)

------------------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求标的不存在 | 检查请求的 `symbol` 是否正确 |
| 7   | 301603 | 标的无行情 | 标的没有请求的行情数据 |
| 7   | 301604 | 无权限 | 没有获取标的行情的权限 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/capital-distribution.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/capital-distribution.md)

最后更新于:

Pager

[上一页获取标的当日资金流向](./quote-pull-capital-flow-intraday.md)

[下一页获取标的计算指标](./quote-pull-calc-index.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
