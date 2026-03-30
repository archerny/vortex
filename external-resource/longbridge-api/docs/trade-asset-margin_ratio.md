获取保证金比例
=======

该接口用于获取股票初始保证金比例、维持保证金比例、强平保证金比例。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge margin-ratio TSLA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.margin\_ratio](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.margin_ratio) |
| Rust | [longbridge::trade::TradeContext#margin\_ratio](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.margin_ratio) |
| Go  | [TradeContext.MarginRatio](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.MarginRatio) |
| Node.js | [TradeContext#marginRatio](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#marginratio) |
| Java | [TradeContext.getMarginRatio](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getMarginRatio(java.lang.String)) |
| C++ | [longbridge::trade::TradeContext::margin\_ratio](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a63733868dd08101f12e379ac7c63501e) |

Request [​](./trade-asset-margin_ratio.md#request)

-------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/risk/margin-ratio |

### Parameters [​](./trade-asset-margin_ratio.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | YES | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |

### Request Example [​](./trade-asset-margin_ratio.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from datetime import datetime
    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    resp = ctx.margin_ratio("700.HK")
    print(resp)

python

    import asyncio
    from datetime import datetime
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
        resp = await ctx.margin_ratio("700.HK")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      const resp = await ctx.marginRatio("700.HK")
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.trade.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 TradeContext ctx = TradeContext.create(config)) {
                MarginRatio resp = ctx.getMarginRatio("700.HK").get();
                System.out.println(resp);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, trade::TradeContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = TradeContext::new(config);
        let resp = ctx.margin_ratio("700.HK").await?;
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
    using namespace longbridge::trade;
    
    static void
    run(const OAuth& oauth)
    {
        Config config = Config::from_oauth(oauth);
        TradeContext ctx = TradeContext::create(config);
    
        ctx.margin_ratio("700.HK", [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "margin_ratio" << std::endl;
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
    	"github.com/longbridge/openapi-go/trade"
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
    	tctx, err := trade.NewFromCfg(conf)
    	if err != nil {
    		log.Fatal(err)
    	}
    	defer tctx.Close()
    	mr, err := tctx.MarginRatio(context.Background(), "700.HK")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", mr)
    }

Response [​](./trade-asset-margin_ratio.md#response)

---------------------------------------------------------------------------------------

### Response Headers [​](./trade-asset-margin_ratio.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-asset-margin_ratio.md#response-example)

json

    {
      "code": 0,
      "data": {
        "im_factor": "0.1",
        "mm_factor": "0.1",
        "fm_factor": "0.1"
      }
    }

### Response Status [​](./trade-asset-margin_ratio.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [margin\_ratio\_rsp](./trade-asset-margin_ratio.md#schemamargin-ratio-rsp) |
| 400 | 内部错误 | None |

Schemas [​](./trade-asset-margin_ratio.md#schemas)

-------------------------------------------------------------------------------------

### margin\_ratio\_rsp [​](./trade-asset-margin_ratio.md#margin-ratio-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| im\_factor | string | true | 初始保证金比例 |
| mm\_factor | string | true | 维持保证金比例 |
| fm\_factor | string | true | 强平保证金比例 |

[LLMs Text](https://open.longbridge.com/docs/trade/asset/margin_ratio.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/asset/margin_ratio.md)

最后更新于:

Pager

[上一页获取基金持仓](./trade-asset-fund.md)

[下一页获取股票持仓](./trade-asset-stock.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
