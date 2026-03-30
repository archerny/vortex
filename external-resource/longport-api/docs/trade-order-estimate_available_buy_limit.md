预估最大购买数量
========

该接口用于港美股，窝轮，期权的预估最大购买数量。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge max-qty TSLA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.estimate\_max\_purchase\_quantity](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.estimate_max_purchase_quantity) |
| Rust | [longbridge::trade::TradeContext#estimate\_max\_purchase\_quantity](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.estimate_max_purchase_quantity) |
| Go  | [TradeContext.EstimateMaxPurchaseQuantity](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.EstimateMaxPurchaseQuantity) |
| Node.js | [TradeContext#estimateMaxPurchaseQuantity](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#estimatemaxpurchasequantity) |
| Java | [TradeContext.getEstimateMaxPurchaseQuantity](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getEstimateMaxPurchaseQuantity(com.longbridge.trade.EstimateMaxPurchaseQuantityOptions)) |
| C++ | [longbridge::trade::TradeContext::estimate\_max\_purchase\_quantity](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a08212a313a00792c42d2e47956ef3070) |

Request [​](./trade-order-estimate_available_buy_limit.md#request)

-----------------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/trade/estimate/buy\_limit |

### Parameters [​](./trade-order-estimate_available_buy_limit.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | YES | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| order\_type | string | YES | [订单类型](./trade-trade-definition.md#ordertype) |
| price | string | NO  | 预估下单价格，例如：`388.5` |
| side | string | YES | 买卖方向  <br>  <br>**可选值：**  <br>`Buy` - 买入  <br>`Sell` - 卖出 卖出只支持美股卖空查询 |
| currency | string | NO  | 结算货币 |
| order\_id | string | NO  | 订单 ID，获取改单预估最大购买数量时必填 |

### Request Example [​](./trade-order-estimate_available_buy_limit.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OrderType, OrderSide, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    
    resp = ctx.estimate_max_purchase_quantity(
        symbol = "700.HK",
        order_type = OrderType.LO,
        side = OrderSide.Buy,
    )
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OrderType, OrderSide, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
    
        resp = await ctx.estimate_max_purchase_quantity(
            symbol = "700.HK",
            order_type = OrderType.LO,
            side = OrderSide.Buy,
        )
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth, OrderType, OrderSide, Decimal } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      const resp = await ctx.estimateMaxPurchaseQuantity({ symbol: "700.HK", orderType: OrderType.LO, side: OrderSide.Buy, price: new Decimal("400"), fractionalShares: false })
      console.log(resp)
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
                EstimateMaxPurchaseQuantityResponse resp = ctx.getEstimateMaxPurchaseQuantity(new EstimateMaxPurchaseQuantityOptions("700.HK", OrderType.LO, OrderSide.Buy).setPrice(new BigDecimal("400"))).get();
                System.out.println(resp);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, trade::{TradeContext, EstimateMaxPurchaseQuantityOptions, OrderType, OrderSide}, Config};
    use rust_decimal::Decimal;
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = TradeContext::new(config);
        let resp = ctx.estimate_max_purchase_quantity(
            EstimateMaxPurchaseQuantityOptions::new("700.HK", OrderType::LO, OrderSide::Buy)
                .price(Decimal::from(400))
        ).await?;
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
    
        EstimateMaxPurchaseQuantityOptions opts{"700.HK", OrderType::LO, OrderSide::Buy, Decimal(400.0), 100};
        ctx.estimate_max_purchase_quantity(opts, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "max_cash_buy: " << res->max_cash_buy << std::endl;
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
    	resp, err := tctx.EstimateMaxPurchaseQuantity(context.Background(), &trade.GetEstimateMaxPurchaseQuantity{
    		Symbol:    "AAPL.US",
    		OrderType: trade.OrderTypeLO,
    		Price:     decimal.NewFromFloat(175.62),
    		Currency:  "USD",
    		Side:      trade.OrderSideBuy,
    	})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("max_cash_buy:", resp.MaxCashBuy)
    }

Response [​](./trade-order-estimate_available_buy_limit.md#response)

-------------------------------------------------------------------------------------------------------

### Response Headers [​](./trade-order-estimate_available_buy_limit.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-order-estimate_available_buy_limit.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "cash_max_qty": "100",
        "margin_max_qty": "100"
      }
    }

### Response Status [​](./trade-order-estimate_available_buy_limit.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 获取预估最大购买数量 | [estimate\_available\_buy\_limit\_rsp](./trade-order-estimate_available_buy_limit.md#schemaestimate-available-buy-limit-rsp) |
| 400 | 查询失败，请求参数错误。 | None |

Schemas [​](./trade-order-estimate_available_buy_limit.md#schemas)

-----------------------------------------------------------------------------------------------------

### estimate\_available\_buy\_limit\_rsp [​](./trade-order-estimate_available_buy_limit.md#estimate-available-buy-limit-rsp)

预估最大购买数量

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| cash\_max\_qty | string | true | 现金可买数量，默认为空字符串 |
| margin\_max\_qty | string | true | 融资可买数量，默认为空字符串 |

[LLMs Text](https://open.longbridge.com/docs/trade/order/estimate_available_buy_limit.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/order/estimate_available_buy_limit.md)

最后更新于:

Pager

[上一页获取当日成交明细](./trade-execution-today_executions.md)

[下一页获取历史订单](./trade-order-history_orders.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
