获取当日订单
======

该接口用于获取当日订单和订单查询。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge orders

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.today\_orders](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.today_orders) |
| Rust | [longbridge::trade::TradeContext#today\_orders](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.today_orders) |
| Go  | [TradeContext.TodayOrders](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.TodayOrders) |
| Node.js | [TradeContext#todayOrders](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#todayorders) |
| Java | [TradeContext.getTodayOrders](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getTodayOrders(com.longbridge.trade.GetTodayOrdersOptions)) |
| C++ | [longbridge::trade::TradeContext::today\_orders](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#aa591e194baa2934634b4a753dc95e0f7) |

Request [​](./trade-order-today_orders.md#request)

-------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/trade/order/today |

### Parameters [​](./trade-order-today_orders.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | NO  | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| status | string\[\] | NO  | [订单状态](./trade-trade-definition.md#orderstatus)<br>  <br>  <br>例如：`status=FilledStatus&status=NewStatus` |
| side | string | NO  | 买卖方向  <br>  <br>**可选值：**  <br>`Buy` - 买入  <br>`Sell` - 卖出 |
| market | string | NO  | 市场  <br>  <br>**可选值：**  <br>`US` - 美股  <br>`HK` - 港股 |
| order\_id | string | NO  | 订单 ID，用于指定订单 ID 查询，例如：`701276261045858304` |

### Request Example [​](./trade-order-today_orders.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OrderStatus, OrderSide, Market, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    
    resp = ctx.today_orders(
        symbol = "700.HK",
        status = [OrderStatus.Filled, OrderStatus.New],
        side = OrderSide.Buy,
        market = Market.HK,
    )
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OrderStatus, OrderSide, Market, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
    
        resp = await ctx.today_orders(
            symbol = "700.HK",
            status = [OrderStatus.Filled, OrderStatus.New],
            side = OrderSide.Buy,
            market = Market.HK,
        )
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      const resp = await ctx.todayOrders({})
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
                Order[] resp = ctx.getTodayOrders(null).get();
                for (Order o : resp) System.out.println(o);
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
        let resp = ctx.today_orders(None).await?;
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
    
        ctx.today_orders(std::nullopt, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            for (const auto& o : *res) std::cout << o.order_id << std::endl;
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
    	orders, err := tctx.TodayOrders(context.Background(), &trade.GetTodayOrders{})
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, o := range orders {
    		fmt.Println(o.OrderId)
    	}
    }

Response [​](./trade-order-today_orders.md#response)

---------------------------------------------------------------------------------------

### Response Headers [​](./trade-order-today_orders.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-order-today_orders.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "orders": [\
          {\
            "currency": "HKD",\
            "executed_price": "0.000",\
            "executed_quantity": "0",\
            "expire_date": "",\
            "last_done": "",\
            "limit_offset": "",\
            "msg": "",\
            "order_id": "706388312699592704",\
            "order_type": "ELO",\
            "outside_rth": "UnknownOutsideRth",\
            "price": "11.900",\
            "quantity": "200",\
            "side": "Buy",\
            "status": "RejectedStatus",\
            "stock_name": "东亚银行",\
            "submitted_at": "1651644897",\
            "symbol": "23.HK",\
            "tag": "Normal",\
            "time_in_force": "Day",\
            "trailing_amount": "",\
            "trailing_percent": "",\
            "trigger_at": "0",\
            "trigger_price": "",\
            "trigger_status": "NOT_USED",\
            "updated_at": "1651644898",\
            "remark": "",\
            "limit_depth_level": 0,\
            "monitor_price": "",\
            "trigger_count": 1\
          }\
        ]
      }
    }

### Response Status [​](./trade-order-today_orders.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 当日订单查询成功 | [today\_orders\_rsp](./trade-order-today_orders.md#schematoday-orders-rsp) |
| 400 | 查询失败，请求参数错误。 | None |

Schemas [​](./trade-order-today_orders.md#schemas)

-------------------------------------------------------------------------------------

### today\_orders\_rsp [​](./trade-order-today_orders.md#today-orders-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| orders | object\[\] | false | 订单信息 |
| ∟ order\_id | string | true | 订单 ID |
| ∟ status | string | true | [订单状态](./trade-trade-definition.md#orderstatus) |
| ∟ stock\_name | string | true | 股票名称 |
| ∟ quantity | string | true | 下单数量 |
| ∟ executed\_quantity | string | true | 成交数量。  <br>  <br>当订单未成交时为 0 |
| ∟ price | string | true | 下单价格。  <br>  <br>当市价条件单未触发时为空字符串 |
| ∟ executed\_price | string | true | 成交价。  <br>  <br>当订单未成交时为 0 |
| ∟ submitted\_at | string | true | 下单时间 |
| ∟ side | string | true | 买卖方向  <br>  <br>**可选值：**  <br>`Buy` - 买入  <br>`Sell` - 卖出 |
| ∟ symbol | string | true | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| ∟ order\_type | string | true | [订单类型](./trade-trade-definition.md#ordertype) |
| ∟ last\_done | string | true | 最近成交价格。  <br>  <br>当订单未成交时为空字符串 |
| ∟ trigger\_price | string | true | `LIT` / `MIT` 订单触发价格。  <br>  <br>当订单不是 `LIT` / `MIT` 订单为空字符串 |
| ∟ msg | string | true | 拒绝信息或备注，默认为空字符串。 |
| ∟ tag | string | true | 订单标记  <br>  <br>**可选值：**  <br>`Normal` - 普通订单  <br>`GTC` - 长期单  <br>`Grey` - 暗盘单 |
| ∟ time\_in\_force | string | true | 订单有效期类型  <br>  <br>**可选值：**  <br>`Day` - 当日有效  <br>`GTC` - 撤单前有效  <br>`GTD` - 到期前有效 |
| ∟ expire\_date | string | true | 长期单过期时间，格式为 `YYYY-MM-DD`, 例如：`2022-12-05。<br/><br/>不是长期单时，默认为空字符串。` |
| ∟ updated\_at | string | true | 最近更新时间，格式为时间戳 (秒)，默认为 0。 |
| ∟ trigger\_at | string | true | 条件单触发时间，格式为时间戳 (秒)，默认为 0。 |
| ∟ trailing\_amount | string | true | `TSLPAMT` 订单跟踪金额。  <br>  <br>当订单不是 `TSLPAMT` 订单时为空字符串。 |
| ∟ trailing\_percent | string | true | `TSLPPCT` 订单跟踪涨跌幅。  <br>  <br>当订单不是 `TSLPPCT` 订单时为空字符串。 |
| ∟ limit\_offset | string | true | `TSLPAMT` / `TSLPPCT` 订单指定价差。  <br>  <br>当订单不是 `TSLPAMT` / `TSLPPCT` 订单时为空字符串。 |
| ∟ trigger\_status | string | true | 条件单触发状态  <br>当订单不是条件单或条件单未触发时，触发状态为 NOT\_USED  <br>  <br>**可选值：**  <br>`NOT_USED` - 未激活 `DEACTIVE` - 已失效 `ACTIVE` - 已激活 `RELEASED` - 已触发 |
| ∟ currency | string | true | 结算货币 |
| ∟ outside\_rth | string | true | 是否允许盘前盘后  <br>当订单不是美股时，默认为 UnknownOutsideRth  <br>  <br>**可选值：**  <br>`RTH_ONLY` - 不允许盘前盘后  <br>`ANY_TIME` - 允许盘前盘后  <br>`OVERNIGHT` - 夜盘" |
| ∟ remark | string | true | 备注  |
| ∟ limit\_depth\_level | int32 | true | 指定买卖档位 |
| ∟ trigger\_count | int32 | true | 触发次数 |
| ∟ monitor\_price | string | true | 监控价格 |

[LLMs Text](https://open.longbridge.com/docs/trade/order/today_orders.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/order/today_orders.md)

最后更新于:

Pager

[上一页委托下单](./trade-order-submit.md)

[下一页撤销订单](./trade-order-withdraw.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
