获取账户资金
======

该接口用于获取用户每个币种可用、可取、冻结、待结算金额、在途资金 (基金申购赎回) 信息。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge balance

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.account\_balance](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.account_balance) |
| Rust | [longbridge::trade::TradeContext#account\_balance](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.account_balance) |
| Go  | [TradeContext.AccountBalance](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.AccountBalance) |
| Node.js | [TradeContext#accountBalance](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#accountbalance) |
| Java | [TradeContext.getAccountBalance](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getAccountBalance()) |
| C++ | [longbridge::trade::TradeContext::account\_balance](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a4bb7495e51784df7535c8b08c9db05fd) |

Request [​](./trade-asset-account.md#request)

--------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/asset/account |

### Parameters [​](./trade-asset-account.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| currency | string | NO  | 币种（HKD、USD、CNH） |

### Request Example [​](./trade-asset-account.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    resp = ctx.account_balance()
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
        resp = await ctx.account_balance()
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => {
        console.log("Open this URL to authorize: " + url)
      })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      const resp = await ctx.accountBalance()
      for (const obj of resp) {
        console.log(obj.toString())
      }
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.trade.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id")
                    .build(url -> System.out.println("Open to authorize: " + url))
                    .get();
                 Config config = Config.fromOAuth(oauth);
                 TradeContext ctx = TradeContext.create(config)) {
                AccountBalance[] resp = ctx.getAccountBalance().get();
                for (AccountBalance obj : resp) {
                    System.out.println(obj);
                }
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, trade::TradeContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id")
            .build(|url| println!("Open this URL to authorize: {url}"))
            .await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = TradeContext::new(config);
        let resp = ctx.account_balance(None).await?;
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
    
        ctx.account_balance([](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& b : *res) {
                std::cout << b.currency << " " << (double)b.available << std::endl;
            }
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
    	ab, err := tctx.AccountBalance(context.Background(), &trade.GetAccountBalance{})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", ab[0])
    }

Response [​](./trade-asset-account.md#response)

----------------------------------------------------------------------------------

### Response Headers [​](./trade-asset-account.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-asset-account.md#response-example)

json

    {
      "code": 0,
      "data": {
        "list": [\
          {\
            "total_cash": "1759070010.72",\
            "max_finance_amount": "977582000",\
            "remaining_finance_amount": "0",\
            "risk_level": "1",\
            "margin_call": "2598051051.50",\
            "currency": "HKD",\
            "net_assets": "24145.90",\
            "init_margin": "1540.09",\
            "maintenance_margin": "1540.09",\
            "buy_power": "1759070.12",\
            "cash_infos": [\
              {\
                "withdraw_cash": "97592.30",\
                "available_cash": "195902464.37",\
                "frozen_cash": "11579339.13",\
                "settling_cash": "207288537.81",\
                "currency": "HKD"\
              },\
              {\
                "withdraw_cash": "199893416.74",\
                "available_cash": "199893416.74",\
                "frozen_cash": "28723.76",\
                "settling_cash": "-276806.51",\
                "currency": "USD"\
              }\
            ],\
            "frozen_transaction_fees": [\
              {\
                "currency": "USD",\
                "frozen_transaction_fee": "6.51"\
              }\
            ]\
          }\
        ]
      }
    }

### Response Status [​](./trade-asset-account.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [accountcash\_rsp](./trade-asset-account.md#schemaaccountcash-rsp) |
| 400 | 内部错误 | None |

Schemas [​](./trade-asset-account.md#schemas)

--------------------------------------------------------------------------------

### accountcash\_rsp [​](./trade-asset-account.md#accountcash-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| list | object\[\] | false | 账户资金信息 |
| ∟ total\_cash | string | true | 现金总额 |
| ∟ max\_finance\_amount | string | true | 最大融资金额 |
| ∟ remaining\_finance\_amount | string | true | 剩余融资金额 |
| ∟ risk\_level | string | true | 风控等级  <br>  <br>**可选值:**  <br>`0` - 安全  <br>`1` - 中风险  <br>`2` - 预警  <br>`3` - 危险 |
| ∟ margin\_call | string | true | 追缴保证金 |
| ∟ net\_assets | string | true | 净资产 |
| ∟ init\_margin | string | true | 初始保证金 |
| ∟ maintenance\_margin | string | true | 维持保证金 |
| ∟ currency | string | true | 币种  |
| ∟ market | string | false | 市场  |
| ∟ buy\_power | string | true | 购买力 |
| ∟ cash\_infos | object\[\] | false | 现金详情 |
| ∟∟ withdraw\_cash | string | true | 可提现金 |
| ∟∟ available\_cash | string | true | 可用现金 |
| ∟∟ frozen\_cash | string | true | 冻结现金 |
| ∟∟ settling\_cash | string | true | 待结算现金 |
| ∟∟ currency | string | true | 币种  |
| ∟ frozen\_transaction\_fees | object\[\] | false | 冻结费用 |
| ∟∟ currency | string | false | 币种  |
| ∟∟ frozen\_transaction\_fee | string | false | 费用金额 |

[LLMs Text](https://open.longbridge.com/docs/trade/asset/account.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/asset/account.md)

最后更新于:

Pager

[上一页交易推送](./trade-trade-push.md)

[下一页获取资金流水](./trade-asset-cashflow.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
