获取基金持仓
======

该接口用于获取包括账户、基金代码、持有份额、成本净值、当前净值、币种在内的基金持仓信息。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge fund-positions

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.fund\_positions](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.fund_positions) |
| Rust | [longbridge::trade::TradeContext#fund\_positions](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.fund_positions) |
| Go  | [TradeContext.FundPositions](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.FundPositions) |
| Node.js | [TradeContext#fundPositions](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#fundpositions) |
| Java | [TradeContext.getFundPositions](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getFundPositions(com.longbridge.trade.GetFundPositionsOptions)) |
| C++ | [longbridge::trade::TradeContext::fund\_positions](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#ac2135e51ac5d72f3b302e41b6f41d116) |

Request [​](./trade-asset-fund.md#request)

-----------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/asset/fund |

### Parameters [​](./trade-asset-fund.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string\[\] | NO  | 基金代码，使用 `ISIN` 格式，例如：`HK0000676327` [ISIN 解释](https://en.wikipedia.org/wiki/International_Securities_Identification_Number) |

### Request Example [​](./trade-asset-fund.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    resp = ctx.fund_positions()
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
        resp = await ctx.fund_positions()
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      const resp = await ctx.fundPositions()
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
                FundPositionsResponse resp = ctx.getFundPositions(null).get();
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
        let resp = ctx.fund_positions(None).await?;
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
    
        ctx.fund_positions(std::nullopt, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "fund positions" << std::endl;
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
    	positions, err := tctx.FundPositions(context.Background(), []string{"AAPL.US", "700.HK"})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", positions)
    }

Response [​](./trade-asset-fund.md#response)

-------------------------------------------------------------------------------

### Response Headers [​](./trade-asset-fund.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-asset-fund.md#response-example)

json

    {
      "code": 0,
      "data": {
        "list": [\
          {\
            "account_channel": "lb",\
            "fund_info": [\
              {\
                "symbol": "HK0000447943",\
                "symbol_name": "高腾亚洲收益基金",\
                "currency": "USD",\
                "holding_units": "5.000",\
                "current_net_asset_value": "0",\
                "cost_net_asset_value": "0.00",\
                "net_asset_value_day": "1649865600"\
              }\
            ]\
          }\
        ]
      }
    }

### Response Status [​](./trade-asset-fund.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [fund\_rsp](./trade-asset-fund.md#schemafund-rsp) |
| 400 | 内部错误 | None |

Schemas [​](./trade-asset-fund.md#schemas)

-----------------------------------------------------------------------------

### fund\_rsp [​](./trade-asset-fund.md#fund-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| list | object\[\] | false | 股票持仓信息 |
| ∟ account\_channel | string | true | 账户类型 |
| ∟ fund\_info | object\[\] | false | 基金详情 |
| ∟∟ symbol | string | true | 基金 ISIN 代码 |
| ∟∟ current\_net\_asset\_value | string | true | 当前净值 |
| ∟∟ net\_asset\_value\_day | string | true | 当前净值时间 |
| ∟∟ symbol\_name | string | true | 基金名称 |
| ∟∟ currency | string | true | 币种  |
| ∟∟ cost\_net\_asset\_value | string | true | 成本净值 |

[LLMs Text](https://open.longbridge.com/docs/trade/asset/fund.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/asset/fund.md)

最后更新于:

Pager

[上一页获取资金流水](./trade-asset-cashflow.md)

[下一页获取保证金比例](./trade-asset-margin_ratio.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
