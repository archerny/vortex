创建自选股分组
=======

创建自选股分组尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 创建新的自选股分组
    longbridge watchlist create "My Portfolio"
    # 创建另一个分组
    longbridge watchlist create "Tech Stocks"

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.create\_watchlist\_group](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.create_watchlist_group) |
| Rust | [longbridge::quote::QuoteContext#create\_watchlist\_group](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.create_watchlist_group) |
| Go  | [QuoteContext.CreateWatchlistGroup](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.CreateWatchlistGroup) |
| Node.js | [QuoteContext#createWatchlistGroup](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#createwatchlistgroup) |
| Java | [QuoteContext.createWatchlistGroup](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#createWatchlistGroup(com.longbridge.quote.CreateWatchlistGroup)) |
| C++ | [longbridge::quote::QuoteContext::create\_watchlist\_group](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#afbcabe6c545c05d1d4fcefe0b9a66aea) |

Request [​](./quote-individual-watchlist_create_group.md#request)

----------------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | POST |
| HTTP URL | /v1/watchlist/groups |

### Parameters [​](./quote-individual-watchlist_create_group.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| name | string | YES | 分组名称，例如 `信息产业组` |
| securities | string\[\] | NO  | 股票列表，例如 `["BABA.US","AAPL.US"]`  <br>分组下股票的展示顺序，与此列表的顺序一致  <br>如果不传此参数，则创建一个空的分组 |

### Request Example [​](./quote-individual-watchlist_create_group.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    group_id = ctx.create_watchlist_group(name = "Watchlist1", securities = ["700.HK", "AAPL.US"])
    print(group_id)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        group_id = ctx.create_watchlist_group(name = "Watchlist1", securities = ["700.HK", "AAPL.US"])
        print(group_id)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.createWatchlistGroup("My Group", ["700.HK", "AAPL.US"])
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
                WatchlistGroup resp = ctx.createWatchlistGroup("My Group", new String[] { "700.HK", "AAPL.US" }).get();
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
        let resp = ctx.create_watchlist_group("My Group", vec!["700.HK".to_string(), "AAPL.US".to_string()]).await?;
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
    
        ctx.create_watchlist_group("My Group", symbols, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "created: " << res->name << std::endl;
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
    	gid, err := qctx.CreateWatchlistGroup(context.Background(), "My Group", []string{"700.HK", "AAPL.US"})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("created:", gid)
    }

Response [​](./quote-individual-watchlist_create_group.md#response)

------------------------------------------------------------------------------------------------------

### Response Headers [​](./quote-individual-watchlist_create_group.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-individual-watchlist_create_group.md#response-example)

json

    {
      "code": 0,
      "data": {
        "id": 10086
      }
    }

### Response Status [​](./quote-individual-watchlist_create_group.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [create\_group\_response](./quote-individual-watchlist_create_group.md#schemacreate-group-response) |
| 500 | 内部错误 | None |

Schemas [​](./quote-individual-watchlist_create_group.md#schemas)

----------------------------------------------------------------------------------------------------

### create\_group\_response [​](./quote-individual-watchlist_create_group.md#create-group-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| id  | integer | false | 分组 ID |

[LLMs Text](https://open.longbridge.com/docs/quote/individual/watchlist_create_group.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/individual/watchlist_create_group.md)

最后更新于:

Pager

[上一页实时成交明细推送](./quote-push-trade.md)

[下一页删除自选股分组](./quote-individual-watchlist_delete_group.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
