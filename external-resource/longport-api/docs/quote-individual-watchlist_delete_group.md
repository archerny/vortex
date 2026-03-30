删除自选股分组
=======

删除自选股分组尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 删除指定分组（ID 通过 longbridge watchlist 查询）
    longbridge watchlist delete <id>

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.delete\_watchlist\_group](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.delete_watchlist_group) |
| Rust | [longbridge::quote::QuoteContext#delete\_watchlist\_group](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.delete_watchlist_group) |
| Go  | [QuoteContext.DeleteWatchlistGroup](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.DeleteWatchlistGroup) |
| Node.js | [QuoteContext#deleteWatchlistGroup](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#deletewatchlistgroup) |
| Java | [QuoteContext.deleteWatchlistGroup](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#deleteWatchlistGroup(com.longbridge.quote.DeleteWatchlistGroup)) |
| C++ | [longbridge::quote::QuoteContext::delete\_watchlist\_group](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a0255d0b3c8890d126b7178cf1412bf7d) |

Request [​](./quote-individual-watchlist_delete_group.md#request)

----------------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | DELETE |
| HTTP URL | /v1/watchlist/groups |

### Parameters [​](./quote-individual-watchlist_delete_group.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| id  | integer | YES | 分组 ID，例如 `10086` |
| purge | boolean | YES | 是否清除分组下的股票  <br>为 `true`，则此分组下的股票将被取消关注  <br>为 `false`，则此分组下的股票会保留在`全部`分组中 |

### Request Example [​](./quote-individual-watchlist_delete_group.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    ctx.delete_watchlist_group(10086)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        ctx.delete_watchlist_group(10086)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      await ctx.deleteWatchlistGroup(1)
      console.log("deleted")
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
                ctx.deleteWatchlistGroup(1).get();
                System.out.println("deleted");
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
        ctx.delete_watchlist_group(1).await?;
        println!("deleted");
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
    
        ctx.delete_watchlist_group(1, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "deleted" << std::endl;
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
    	err = qctx.DeleteWatchlistGroup(context.Background(), 1, false)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("deleted")
    }

Response [​](./quote-individual-watchlist_delete_group.md#response)

------------------------------------------------------------------------------------------------------

### Response Headers [​](./quote-individual-watchlist_delete_group.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-individual-watchlist_delete_group.md#response-example)

json

    {
      "code": 0
    }

### Response Status [​](./quote-individual-watchlist_delete_group.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | None |
| 500 | 内部错误 | None |

[LLMs Text](https://open.longbridge.com/docs/quote/individual/watchlist_delete_group.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/individual/watchlist_delete_group.md)

最后更新于:

Pager

[上一页创建自选股分组](./quote-individual-watchlist_create_group.md)

[下一页获取自选股分组](./quote-individual-watchlist_groups.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
