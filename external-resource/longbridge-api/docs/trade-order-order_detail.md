订单详情
====

该接口用于订单详情查询。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 将下方订单 ID 替换为实际的订单 ID
    longbridge order 693664675163312128

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.order\_detail](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.order_detail) |
| Rust | [longbridge::trade::TradeContext#order\_detail](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.order_detail) |
| Go  | [TradeContext.OrderDetail](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.OrderDetail) |
| Node.js | [TradeContext#orderDetail](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#orderdetail) |
| Java | [TradeContext.getOrderDetail](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getOrderDetail(java.lang.String)) |
| C++ | [longbridge::trade::TradeContext::order\_detail](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#aedb3d207a04d2a4bcde41e351b5c4f60) |

Request [​](./trade-order-order_detail.md#request)

-------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/trade/order |

### Parameters [​](./trade-order-order_detail.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| order\_id | string | YES | 订单 ID，用于指定订单 ID 查询，例如：`701276261045858304` |

### Request Example [​](./trade-order-order_detail.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    
    resp = ctx.order_detail(
        order_id = "701276261045858304",
    )
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
    
        resp = await ctx.order_detail(
            order_id = "701276261045858304",
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
      const resp = await ctx.orderDetail("701276261045858304")
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
                OrderDetail resp = ctx.getOrderDetail("701276261045858304").get();
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
        let resp = ctx.order_detail("701276261045858304").await?;
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
    
        ctx.order_detail("701276261045858304", [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << res->order_id << std::endl;
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
    	detail, err := tctx.OrderDetail(context.Background(), "701276261045858304")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", detail)
    }

Response [​](./trade-order-order_detail.md#response)

---------------------------------------------------------------------------------------

### Response Headers [​](./trade-order-order_detail.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-order-order_detail.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "order_id": "828940451093708800",
        "status": "FilledStatus",
        "stock_name": "苹果",
        "quantity": "10",
        "executed_quantity": "10",
        "price": "200.000",
        "executed_price": "164.660",
        "submitted_at": "1680863604",
        "side": "Buy",
        "symbol": "AAPL.US",
        "order_type": "LO",
        "last_done": "164.660",
        "trigger_price": "0.0000",
        "msg": "",
        "tag": "Normal",
        "time_in_force": "Day",
        "expire_date": "2023-04-10",
        "updated_at": "1681113000",
        "trigger_at": "0",
        "trailing_amount": "",
        "trailing_percent": "",
        "limit_offset": "",
        "limit_depth_level": 0,
        "monitor_price": "",
        "trigger_count": 1,
        "trigger_status": "NOT_USED",
        "outside_rth": "ANY_TIME",
        "currency": "USD",
        "remark": "1680863603.927165",
        "free_status": "None",
        "free_amount": "",
        "free_currency": "",
        "deductions_status": "NONE",
        "deductions_amount": "",
        "deductions_currency": "",
        "platform_deducted_status": "NONE",
        "platform_deducted_amount": "",
        "platform_deducted_currency": "",
        "history": [\
          {\
            "price": "164.6600",\
            "quantity": "10",\
            "status": "FilledStatus",\
            "msg": "Execution of 10",\
            "time": "1681113000"\
          },\
          {\
            "price": "200.0000",\
            "quantity": "10",\
            "status": "NewStatus",\
            "msg": "",\
            "time": "1681113000"\
          }\
        ],
        "charge_detail": {
          "items": [\
            {\
              "code": "BROKER_FEES",\
              "name": "收费明细",\
              "fees": []\
            },\
            {\
              "code": "THIRD_FEES",\
              "name": "第三方收费明细",\
              "fees": []\
            }\
          ],
          "total_amount": "0",
          "currency": "USD"
        }
      }
    }

### Response Status [​](./trade-order-order_detail.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 订单详情查询成功 | [order\_detail\_rsp](./trade-order-order_detail.md#schemaorder-detail-rsp) |
| 400 | 查询失败，请求参数错误。 | None |

Schemas [​](./trade-order-order_detail.md#schemas)

-------------------------------------------------------------------------------------

### order\_detail\_rsp [​](./trade-order-order_detail.md#order-detail-rsp)

订单信息

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| order\_id | string | true | 订单 ID |
| status | string | true | [订单状态](./trade-trade-definition.md#orderstatus) |
| stock\_name | string | true | 股票名称 |
| quantity | string | true | 下单数量 |
| executed\_quantity | string | true | 成交数量。  <br>  <br>当订单未成交时为 0 |
| price | string | true | 下单价格。  <br>  <br>当市价条件单未触发时为空字符串 |
| executed\_price | string | true | 成交价。  <br>  <br>当订单未成交时为 0 |
| submitted\_at | string | true | 下单时间 |
| side | string | true | 买卖方向  <br>  <br>**可选值：**  <br>`Buy` - 买入  <br>`Sell` - 卖出 |
| symbol | string | true | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| order\_type | string | true | [订单类型](./trade-trade-definition.md#ordertype) |
| last\_done | string | true | 最近成交价格。  <br>  <br>当订单未成交时为空字符串 |
| trigger\_price | string | true | `LIT` / `MIT` 订单触发价格。  <br>  <br>当订单不是 `LIT` / `MIT` 订单为空字符串 |
| msg | string | true | 拒绝信息或备注，默认为空字符串。 |
| tag | string | true | 订单标记  <br>  <br>**可选值：**  <br>`Normal` - 普通订单  <br>`GTC` - 长期单  <br>`Grey` - 暗盘单 |
| time\_in\_force | string | true | 订单有效期类型  <br>  <br>**可选值：**  <br>`Day` - 当日有效  <br>`GTC` - 撤单前有效  <br>`GTD` - 到期前有效 |
| expire\_date | string | true | 长期单过期时间，格式为 `YYYY-MM-DD`, 例如：\`2022-12-05。  <br>  <br>不是长期单时，默认为空字符串。 |
| updated\_at | string | true | 最近更新时间，格式为时间戳 (秒)，默认为 0。 |
| trigger\_at | string | true | 条件单触发时间，格式为时间戳 (秒)，默认为 0。 |
| trailing\_amount | string | true | `TSLPAMT` 订单跟踪金额。  <br>  <br>当订单不是 `TSLPAMT` 订单时为空字符串。 |
| trailing\_percent | string | true | `TSLPPCT` 订单跟踪涨跌幅。  <br>  <br>当订单不是 `TSLPPCT` 订单时为空字符串。 |
| limit\_offset | string | true | `TSLPAMT` / `TSLPPCT` 订单指定价差。  <br>  <br>当订单不是 `TSLPAMT` / `TSLPPCT` 订单时为空字符串。 |
| trigger\_status | string | true | 条件单触发状态  <br>当订单不是条件单或条件单未触发时，触发状态为 NOT\_USED  <br>  <br>**可选值：**  <br>`NOT_USED` - 未激活  <br>`DEACTIVE` - 已失效  <br>`ACTIVE` - 已激活  <br>`RELEASED` - 已触发 |
| currency | string | true | 结算货币 |
| outside\_rth | string | true | 是否允许盘前盘后  <br>当订单不是美股时，默认为 UnknownOutsideRth  <br>  <br>**可选值：**  <br>`RTH_ONLY` - 不允许盘前盘后  <br>`ANY_TIME` - 允许盘前盘后  <br>`OVERNIGHT` - 夜盘" |
| remark | string | true | 备注  |
| free\_status | string | true | 免佣状态，默认为 None  <br>  <br>**可选值：**  <br>`None` - 无  <br>`Calculated` - 免佣额待计算  <br>`Pending` - 待免佣  <br>`Ready` - 已免佣 |
| free\_amount | string | true | 免佣金额，默认为空字符串 |
| free\_currency | string | true | 免佣货币，默认为空字符串 |
| deductions\_status | string | true | 抵扣状态/返现状态，默认为 NONE  <br>  <br>**可选值：**  <br>`NONE` - 待结算  <br>`NO_DATA` - 已结算无数据  <br>`PENDING` - 已结算待发放  <br>`DONE` - 已结算已发放 |
| deductions\_amount | string | true | 抵扣金额，默认为空字符串 |
| deductions\_currency | string | true | 抵扣货币，默认为空字符串 |
| platform\_deducted\_status | string | true | 平台费抵扣状态/返现状态，默认为 NONE  <br>  <br>**可选值：**  <br>`NONE` - 待结算  <br>`NO_DATA` - 已结算无数据  <br>`PENDING` - 已结算待发放  <br>`DONE` - 已结算已发放 |
| platform\_deducted\_amount | string | true | 平台费抵扣金额，默认为空字符串 |
| platform\_deducted\_currency | string | true | 平台费抵扣货币，默认为空字符串 |
| history | object\[\] | true | 订单历史明细 |
| ∟ price | string | true | 成交展示成交价格，过期、撤单、拒绝等状态展示提交价格 |
| ∟ quantity | string | true | 成交展示成交数量，过期、撤单、拒绝等状态展示剩余数量 |
| ∟ status | string | true | 订单状态 |
| ∟ msg | string | true | 成交或错误信息 |
| ∟ time | string | true | 发生时间 |
| charge\_detail | object | true | 订单费用 |
| ∟ total\_amount | string | true | 全部费用 |
| ∟ currency | string | true | 结算货币 |
| ∟ items | object\[\] | true | 订单费用明细 |
| ∟∟ code | string | true | 收费类别代码  <br>  <br>**可选值：**  <br>`UNKNOWN`  <br>`BROKER_FEES`  <br>`THIRD_FEES` |
| ∟∟ name | string | true | 收费类别名称 |
| ∟∟ fees | object\[\] | true | 收费明细 |
| ∟∟∟ code | string | true | 收费代码 |
| ∟∟∟ name | string | true | 收费名称 |
| ∟∟∟ amount | string | true | 单项收费金额 |
| ∟∟∟ currency | string | true | 收费货币 |
| ∟∟∟ limit\_depth\_level | int32 | true | 指定买卖档位 |
| ∟∟∟ monitor\_price | string | true | 监控价格 |
| ∟∟∟ trigger\_count | int32 | true | 触发次数 |

[LLMs Text](https://open.longbridge.com/docs/trade/order/order_detail.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/order/order_detail.md)

最后更新于:

Pager

[上一页获取历史订单](./trade-order-history_orders.md)

[下一页修改订单](./trade-order-replace.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
