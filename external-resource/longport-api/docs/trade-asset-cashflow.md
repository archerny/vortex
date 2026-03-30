获取资金流水
======

该接口用于获取资金流入/流出方向、资金类别、资金金额、发生时间、关联股票代码和资金流水说明信息。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge cash-flow

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.cash\_flow](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.cash_flow) |
| Rust | [longbridge::trade::TradeContext#cash\_flow](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.cash_flow) |
| Go  | [TradeContext.CashFlow](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.CashFlow) |
| Node.js | [TradeContext#cashFlow](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#cashflow) |
| Java | [TradeContext.getCashFlow](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getCashFlow(com.longbridge.trade.GetCashFlowOptions)) |
| C++ | [longbridge::trade::TradeContext::cash\_flow](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a390e380ee98b35d7b05cd59d81016063) |

Request [​](./trade-asset-cashflow.md#request)

---------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/asset/cashflow |

### Parameters [​](./trade-asset-cashflow.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| start\_time | string | YES | 开始时间，时间戳，以 `秒` 为单位，例如：`1650037563` |
| end\_time | string | YES | 结束时间，时间戳，以 `秒` 为单位，例如：`1650747581` |
| business\_type | string | NO  | 资金类型  <br>  <br>**可选值:**  <br>`1` - 现金  <br>`2` - 股票  <br>`3` - 基金 |
| symbol | string | NO  | 标的代码，例如：`AAPL.US` |
| page | string | NO  | 起始页  <br>  <br>**默认值:** `1`  <br>**数据校验规则:**  <br>**取值范围:** `>=1` |
| size | string | NO  | 每页大小  <br>  <br>**默认值:** `50`  <br>**数据校验规则:** `1~10000` |

### Request Example [​](./trade-asset-cashflow.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from datetime import datetime
    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    resp = ctx.cash_flow(
        start_at = datetime(2022, 5, 9),
        end_at = datetime(2022, 5, 12),
    )
    print(resp)

python

    import asyncio
    from datetime import datetime
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
        resp = await ctx.cash_flow(
            start_at = datetime(2022, 5, 9),
            end_at = datetime(2022, 5, 12),
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
      const resp = await ctx.cashFlow({ startAt: new Date(2022, 4, 9), endAt: new Date(2022, 4, 12) })
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.trade.*;
    import java.time.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 TradeContext ctx = TradeContext.create(config)) {
                GetCashFlowOptions opts = new GetCashFlowOptions(
                    OffsetDateTime.of(2022, 5, 9, 0, 0, 0, 0, ZoneOffset.UTC),
                    OffsetDateTime.of(2022, 5, 12, 0, 0, 0, 0, ZoneOffset.UTC));
                CashFlow[] resp = ctx.getCashFlow(opts).get();
                for (CashFlow c : resp) System.out.println(c);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, trade::{TradeContext, GetCashFlowOptions}, Config};
    use time::macros::datetime;
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = TradeContext::new(config);
        let opts = GetCashFlowOptions::new(datetime!(2022-05-09 0:00 UTC), datetime!(2022-05-12 0:00 UTC));
        let resp = ctx.cash_flow(opts).await?;
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
    
        GetCashFlowOptions opts{}; ctx.account_balance(opts, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "cashflow" << std::endl;
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
    	"time"
    
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
    	start := time.Date(2024, 5, 1, 0, 0, 0, 0, time.UTC).Unix()
    	end := time.Date(2024, 6, 1, 0, 0, 0, 0, time.UTC).Unix()
    	flows, err := tctx.CashFlow(context.Background(), &trade.GetCashFlow{
    		StartAt:      start,
    		EndAt:        end,
    		BusinessType: trade.BalanceTypeCash,
    	})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", flows)
    }

Response [​](./trade-asset-cashflow.md#response)

-----------------------------------------------------------------------------------

### Response Headers [​](./trade-asset-cashflow.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-asset-cashflow.md#response-example)

json

    {
      "code": 0,
      "data": {
        "list": [\
          {\
            "transaction_flow_name": "股票买入成交",\
            "direction": 1,\
            "balance": "-248.60",\
            "currency": "USD",\
            "business_time": "1621507957",\
            "symbol": "AAPL.US",\
            "description": "AAPL"\
          },\
          {\
            "transaction_flow_name": "股票买入成交",\
            "direction": 1,\
            "balance": "-125.16",\
            "currency": "USD",\
            "business_time": "1621504824",\
            "symbol": "AAPL.US",\
            "description": "AAPL"\
          }\
        ]
      }
    }

### Response Status [​](./trade-asset-cashflow.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [cashflow\_rsp](./trade-asset-cashflow.md#schemacashflow-rsp) |
| 400 | 内部错误 | None |

Schemas [​](./trade-asset-cashflow.md#schemas)

---------------------------------------------------------------------------------

### cashflow\_rsp [​](./trade-asset-cashflow.md#cashflow-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| list | object\[\] | false | 流水信息 |
| ∟ transaction\_flow\_name | string | true | 流水名称 |
| ∟ direction | string | true | 流出方向  <br>  <br>**可选值:**  <br>`1` - 流出  <br>`2` - 流入 |
| ∟ business\_type | string | true | 资金类别  <br>  <br>**可选值:**  <br>`1` - 现金  <br>`2` - 股票  <br>`3` - 基金 |
| ∟ balance | string | true | 资金金额 |
| ∟ currency | string | true | 资金币种 |
| ∟ business\_time | string | true | 业务时间 |
| ∟ symbol | string | false | 关联股票代码信息 |
| ∟ description | string | false | 资金流水说明 |

[LLMs Text](https://open.longbridge.com/docs/trade/asset/cashflow.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/asset/cashflow.md)

最后更新于:

Pager

[上一页获取账户资金](./trade-asset-account.md)

[下一页获取基金持仓](./trade-asset-fund.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
