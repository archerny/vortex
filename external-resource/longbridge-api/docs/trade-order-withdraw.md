撤销订单
====

该接口用于订单撤销。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 将下方订单 ID 替换为实际的订单 ID
    longbridge cancel 693664675163312128

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.cancel\_order](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.cancel_order) |
| Rust | [longbridge::trade::TradeContext#cancel\_order](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.cancel_order) |
| Go  | [TradeContext.CancelOrder](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.CancelOrder) |
| Node.js | [TradeContext#cancelOrder](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#cancelorder) |
| Java | [TradeContext.cancelOrder](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#cancelOrder(java.lang.String)) |
| C++ | [longbridge::trade::TradeContext::cancel\_order](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#ae8474241533b66f192e8353520cbd7d4) |

Request [​](./trade-order-withdraw.md#request)

---------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | DELETE |
| HTTP URL | /v1/trade/order |

### Parameters [​](./trade-order-withdraw.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| order\_id | string | YES | 订单 ID |

### Request Example [​](./trade-order-withdraw.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    
    ctx.cancel_order("709043056541253632")

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
    
        ctx.cancel_order("709043056541253632")
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      await ctx.cancelOrder("701276261045858304")
      console.log("cancelled")
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
                ctx.cancelOrder("701276261045858304").get();
                System.out.println("cancelled");
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
        ctx.cancel_order("701276261045858304").await?;
        println!("cancelled");
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
    
        ctx.cancel_order("701276261045858304", [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "cancelled" << std::endl;
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
    	err = tctx.WithdrawOrder(context.Background(), "701276261045858304")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("cancelled")
    }

Response [​](./trade-order-withdraw.md#response)

-----------------------------------------------------------------------------------

### Response Headers [​](./trade-order-withdraw.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-order-withdraw.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {}
    }

### Response Status [​](./trade-order-withdraw.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 提交成功，订单已委托。 | None |
| 400 | 撤单被拒绝，请求参数错误。 | None |

[LLMs Text](https://open.longbridge.com/docs/trade/order/withdraw.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/order/withdraw.md)

最后更新于:

Pager

[上一页获取当日订单](./trade-order-today_orders.md)

[下一页交易推送](./trade-trade-push.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
