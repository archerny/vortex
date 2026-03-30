获取自选股分组
=======

获取自选股分组尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 查看所有自选股分组及标的
    longbridge watchlist

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.watchlist](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.watchlist) |
| Rust | [longbridge::quote::QuoteContext#watchlist](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.watchlist) |
| Go  | [QuoteContext.Watchlist](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.Watchlist) |
| Node.js | [QuoteContext#watchlist](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#watchlist) |
| Java | [QuoteContext.getWatchlist](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getWatchlist()) |
| C++ | [longbridge::quote::QuoteContext::watchlist](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a6e12e64f96ac4ab514df3127fe404d41) |

Request [​](./quote-individual-watchlist_groups.md#request)

----------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/watchlist/groups |

### Request Example [​](./quote-individual-watchlist_groups.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    resp = ctx.watchlist()
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        resp = await ctx.watchlist()
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.watchlist()
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
                WatchlistGroup[] resp = ctx.getWatchlist().get();
                for (WatchlistGroup g : resp) System.out.println(g);
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
        let resp = ctx.watchlist().await?;
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
    
        ctx.watchlist([](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            for (const auto& g : *res) std::cout << g.name << std::endl;
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
    	groups, err := qctx.WatchedGroups(context.Background())
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, g := range groups {
    		fmt.Println(g.Name)
    	}
    }

Response [​](./quote-individual-watchlist_groups.md#response)

------------------------------------------------------------------------------------------------

### Response Headers [​](./quote-individual-watchlist_groups.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-individual-watchlist_groups.md#response-example)

json

    {
      "code": 0,
      "data": {
        "groups": [\
          {\
            "id": 28020,\
            "name": "all",\
            "securities": [\
              {\
                "symbol": "700.HK",\
                "market": "HK",\
                "name": "腾讯控股",\
                "watched_price": "364.4",\
                "watched_at": 1652855022\
              }\
            ]\
          }\
        ]
      }
    }

### Response Status [​](./quote-individual-watchlist_groups.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [groups\_response](./quote-individual-watchlist_groups.md#schemagroups-response) |
| 500 | 内部错误 | None |

Schemas [​](./quote-individual-watchlist_groups.md#schemas)

----------------------------------------------------------------------------------------------

### groups\_response [​](./quote-individual-watchlist_groups.md#groups-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| groups | object\[\] | false | 分组  |
| ∟ id | integer | true | 分组 ID |
| ∟ name | string | true | 名称  |
| ∟ securities | object\[\] | true | 股票  |
| ∟∟ symbol | string | true | 代码  |
| ∟∟ market | string | true | 市场  |
| ∟∟ name | string | true | 名称  |
| ∟∟ watched\_price | string | true | 关注时的价格 |
| ∟∟ watched\_at | integer | true | 关注时间 |

[LLMs Text](https://open.longbridge.com/docs/quote/individual/watchlist_groups.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/individual/watchlist_groups.md)

最后更新于:

Pager

[上一页删除自选股分组](./quote-individual-watchlist_delete_group.md)

[下一页更新自选股分组](./quote-individual-watchlist_update_group.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
