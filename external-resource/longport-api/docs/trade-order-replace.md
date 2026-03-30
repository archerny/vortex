修改订单
====

该接口用于修改订单的价格，数量。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 将下方订单 ID 替换为实际的订单 ID
    longbridge replace 693664675163312128 --qty 200 --price 255.00

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.replace\_order](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.replace_order) |
| Rust | [longbridge::trade::TradeContext#replace\_order](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.replace_order) |
| Go  | [TradeContext.ReplaceOrder](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.ReplaceOrder) |
| Node.js | [TradeContext#replaceOrder](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#replaceorder) |
| Java | [TradeContext.replaceOrder](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#replaceOrder(com.longbridge.trade.ReplaceOrderOptions)) |
| C++ | [longbridge::trade::TradeContext::replace\_order](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#af4fd45eccae1108d356308dbc8a4fd24) |

Request [​](./trade-order-replace.md#request)

--------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | PUT |
| HTTP URL | /v1/trade/order |

### Parameters [​](./trade-order-replace.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| order\_id | string | YES | 订单 ID |
| quantity | string | YES | 改单数量，例如：`200` |
| price | string | NO  | 改单价格，例如：`388.5`  <br>  <br>`LO` / `ELO` / `ALO` / `ODD` / `LIT` 订单必填 |
| trigger\_price | string | NO  | 触发价格，例如：`388.5`  <br>  <br>`LIT` / `MIT` 订单必填 |
| limit\_offset | string | NO  | 指定价差  <br>  <br>`TSLPAMT` / `TSLPPCT` 订单在 `limit_depth_level` 为 0 时必填 |
| trailing\_amount | string | NO  | 跟踪金额  <br>  <br>`TSLPAMT` 订单必填 |
| trailing\_percent | string | NO  | 跟踪涨跌幅  <br>  <br>`TSLPPCT` 订单必填 |
| remark | string | NO  | 备注 (最大 64 字符) |
| limit\_depth\_level | int32 | NO  | 指定买卖档位，`TSLPAMT` / `TSLPPCT` 订单必填 |
| monitor\_price | string | NO  | 监控价格，`TSLPAMT` / `TSLPPCT` 订单必填 |
| trigger\_count | int32 | NO  | 触发次数，`LIT` / `MIT` / `TSLPAMT` / `TSLPPCT` 订单必填 |

### Request Example [​](./trade-order-replace.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from decimal import Decimal
    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    
    ctx.replace_order(
        order_id = "709043056541253632",
        quantity = Decimal(100),
        price = Decimal(50),
    )

python

    import asyncio
    from decimal import Decimal
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
    
        ctx.replace_order(
            order_id = "709043056541253632",
            quantity = Decimal(100),
            price = Decimal(50),
        )
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth, Decimal } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      await ctx.replaceOrder({ orderId: "701276261045858304", quantity: new Decimal(400), price: new Decimal(60) })
      console.log("replaced")
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.trade.*;
    import java.math.BigDecimal;
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 TradeContext ctx = TradeContext.create(config)) {
                ctx.replaceOrder(new ReplaceOrderOptions("701276261045858304", new BigDecimal("400")).setPrice(new BigDecimal("60"))).get();
                System.out.println("replaced");
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, trade::{TradeContext, ReplaceOrderOptions}, Config};
    use rust_decimal::Decimal;
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = TradeContext::new(config);
        ctx.replace_order(
            ReplaceOrderOptions::new("701276261045858304", Decimal::from(400))
                .price(Decimal::from(60))
        ).await?;
        println!("replaced");
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
    
        ReplaceOrderOptions opts{"701276261045858304", 400, Decimal(60.0)};
        ctx.replace_order(opts, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "replaced" << std::endl;
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
    	"github.com/shopspring/decimal"
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
    	err = tctx.ReplaceOrder(context.Background(), &trade.ReplaceOrder{
    		OrderId:  "701276261045858304",
    		Quantity: 400,
    		Price:    decimal.NewFromFloat(60),
    	})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("replaced")
    }

Response [​](./trade-order-replace.md#response)

----------------------------------------------------------------------------------

### Response Headers [​](./trade-order-replace.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-order-replace.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {}
    }

### Response Status [​](./trade-order-replace.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 提交成功，订单已委托。 | None |
| 400 | 下单被拒绝，请求参数错误。 | None |

[LLMs Text](https://open.longbridge.com/docs/trade/order/replace.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/order/replace.md)

最后更新于:

Pager

[上一页订单详情](./trade-order-order_detail.md)

[下一页委托下单](./trade-order-submit.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
