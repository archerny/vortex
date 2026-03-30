更新自选股分组
=======

更新自选股分组尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 向分组添加标的
    longbridge watchlist update <id> --add TSLA.US AAPL.US
    # 从分组移除标的
    longbridge watchlist update <id> --remove NVDA.US
    # 同时添加和移除
    longbridge watchlist update <id> --add TSLA.US --remove AAPL.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.update\_watchlist\_group](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.update_watchlist_group) |
| Rust | [longbridge::quote::QuoteContext#update\_watchlist\_group](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.update_watchlist_group) |
| Go  | [QuoteContext.UpdateWatchlistGroup](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.UpdateWatchlistGroup) |
| Node.js | [QuoteContext#updateWatchlistGroup](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#updatewatchlistgroup) |
| Java | [QuoteContext.updateWatchlistGroup](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#updateWatchlistGroup(com.longbridge.quote.UpdateWatchlistGroup)) |
| C++ | [longbridge::quote::QuoteContext::update\_watchlist\_group](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#ac44c1873a007d23d8040573ef9001aa6) |

Request [​](./quote-individual-watchlist_update_group.md#request)

----------------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | PUT |
| HTTP URL | /v1/watchlist/groups |

### Parameters [​](./quote-individual-watchlist_update_group.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| id  | integer | YES | 分组 ID，例如 `10086` |
| name | string | NO  | 分组名称，例如 `信息产业组`  <br>如果不传递此参数，则分组名称不会更新 |
| securities | string\[\] | NO  | 股票列表，例如 `["BABA.US","AAPL.US"]`  <br>配合下面的 `mode` 参数，可完成添加股票、移除股票、对关注列表进行排序等操作 |
| mode | string | NO  | 操作方法  <br>**可选值：**  <br>`add` - 添加  <br>`remove` - 移除  <br>`replace` - 替换  <br>  <br>选 `add` 时，将上面列表中的股票依序添加到此分组中  <br>  <br>选 `remove` 时，将上面列表中的股票从此分组中移除  <br>  <br>选 `replace` 时，将上面列表中的股票全量覆盖此分组下的股票  <br>假如原来分组中的股票为 `APPL.US, BABA.US, TSLA.US`，使用 `["BABA.US","AAPL.US","MSFT.US"]` 更新后变为 `["BABA.US","AAPL.US","MSFT.US"]`，对比之前，移除了 `TSLA.US`，添加了 `MSFT.US`，`BABA.US,AAPL.US` 调整了顺序 |

### Request Example [​](./quote-individual-watchlist_update_group.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, SecuritiesUpdateMode, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    ctx.update_watchlist_group(10086, name = "WatchList2", securities = ["700.HK", "AAPL.US"], SecuritiesUpdateMode.Replace)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, SecuritiesUpdateMode, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        ctx.update_watchlist_group(10086, name = "WatchList2", securities = ["700.HK", "AAPL.US"], SecuritiesUpdateMode.Replace)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      await ctx.updateWatchlistGroup(1, "New Name", ["700.HK"])
      console.log("updated")
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
                ctx.updateWatchlistGroup(1, "New Name", new String[] { "700.HK" }).get();
                System.out.println("updated");
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
        ctx.update_watchlist_group(1, "New Name", vec!["700.HK".to_string()]).await?;
        println!("updated");
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
    
        ctx.update_watchlist_group(1, "New Name", symbols, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "updated" << std::endl;
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
    	err = qctx.UpdateWatchlistGroup(context.Background(), 1, "New Name", []string{"700.HK"}, quote.AddWatchlist)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("updated")
    }

Response [​](./quote-individual-watchlist_update_group.md#response)

------------------------------------------------------------------------------------------------------

### Response Headers [​](./quote-individual-watchlist_update_group.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-individual-watchlist_update_group.md#response-example)

json

    {
      "code": 0
    }

### Response Status [​](./quote-individual-watchlist_update_group.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | None |
| 500 | 内部错误 | None |

[LLMs Text](https://open.longbridge.com/docs/quote/individual/watchlist_update_group.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/individual/watchlist_update_group.md)

最后更新于:

Pager

[上一页获取自选股分组](./quote-individual-watchlist_groups.md)

[下一页获取标的列表](./quote-security-security_list.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
