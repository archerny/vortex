获取标的公告
======

获取指定股票的公告列表。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Apple 监管文件和公告
    longbridge filings AAPL.US
    # Tesla 监管文件和公告
    longbridge filings TSLA.US
    # NVDA 监管文件和公告
    longbridge filings NVDA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.filings](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.filings) |
| Rust | [longbridge::quote::QuoteContext#filings](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.filings) |
| Go  | [QuoteContext.Filings](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.Filings) |
| Node.js | [QuoteContext#filings](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#filings) |
| Java | [QuoteContext.getFilings](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getFilings) |
| C++ | [longbridge::quote::QuoteContext::filings](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html) |

Request [​](./quote-pull-filings.md#request)

-------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/quote/filings |

### Query Parameters [​](./quote-pull-filings.md#query-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | YES | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |

### Request Example [​](./quote-pull-filings.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.filings("AAPL.US")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.filings("AAPL.US")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.filings("AAPL.US")
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
                FilingItem[] resp = ctx.getFilings("AAPL.US").get();
                for (FilingItem item : resp) System.out.println(item);
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
        let resp = ctx.filings("AAPL.US").await?;
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
    
        ctx.filings("AAPL.US", [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "filings: " << res->size() << std::endl;
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
    	items, err := qctx.Filings(context.Background(), "AAPL.US")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("filings:", len(items))
    }

Response [​](./quote-pull-filings.md#response)

---------------------------------------------------------------------------------

### Response Headers [​](./quote-pull-filings.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-pull-filings.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "items": [\
          {\
            "id": "627391979864985729",\
            "title": "苹果 | 4 - Apple Inc. (0000320193) (Issuer)",\
            "description": "",\
            "file_name": "4 - Apple Inc. (0000320193) (Issuer)",\
            "file_urls": [\
              "https://www.sec.gov/Archives/edgar/data/320193/000178052526000005/xslF345X05/wk-form4_1773786674.xml"\
            ],\
            "publish_at": "1773786677"\
          }\
        ]
      }
    }

### Response Status [​](./quote-pull-filings.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [filings\_response](./quote-pull-filings.md#schemafilings-response) |
| 500 | 内部错误 | None |

Schemas [​](./quote-pull-filings.md#schemas)

-------------------------------------------------------------------------------

### filings\_response [​](./quote-pull-filings.md#filings-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| items | object\[\] | true | 公告列表 |
| ∟ id | string | true | 公告 ID |
| ∟ title | string | true | 标题  |
| ∟ description | string | true | 摘要  |
| ∟ file\_name | string | true | 文件名 |
| ∟ file\_urls | string\[\] | true | 文件链接列表 |
| ∟ publish\_at | string | true | 发布时间，Unix 时间戳（秒） |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/filings.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/filings.md)

最后更新于:

Pager

[上一页获取标的 K 线](./quote-pull-candlestick.md)

[下一页当前市场温度](./quote-pull-market_temperature.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
