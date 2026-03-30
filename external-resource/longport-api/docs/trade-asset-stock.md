获取股票持仓
======

该接口用于获取包括账户、股票代码、持仓股数、可用股数、持仓均价（按账户设置计算均价方式）、币种在内的股票持仓信息。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge positions

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.stock\_positions](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.stock_positions) |
| Rust | [longbridge::trade::TradeContext#stock\_positions](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.stock_positions) |
| Go  | [TradeContext.StockPositions](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.StockPositions) |
| Node.js | [TradeContext#stockPositions](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#stockpositions) |
| Java | [TradeContext.getStockPositions](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getStockPositions(com.longbridge.trade.GetStockPositionsOptions)) |
| C++ | [longbridge::trade::TradeContext::stock\_positions](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a187b81fba74aeea06333e5460f6c79de) |

Request [​](./trade-asset-stock.md#request)

------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/asset/stock |

### Parameters [​](./trade-asset-stock.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string\[\] | NO  | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |

### Request Example [​](./trade-asset-stock.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    resp = ctx.stock_positions()
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
        resp = await ctx.stock_positions()
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      const resp = await ctx.stockPositions()
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
                StockPositionsResponse resp = ctx.getStockPositions(null).get();
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
        let resp = ctx.stock_positions(None).await?;
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
    
        ctx.stock_positions(std::nullopt, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            std::cout << "positions" << std::endl;
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
    	positions, err := tctx.StockPositions(context.Background(), []string{"AAPL.US", "700.HK"})
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("%+v\n", positions)
    }

Response [​](./trade-asset-stock.md#response)

--------------------------------------------------------------------------------

### Response Headers [​](./trade-asset-stock.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-asset-stock.md#response-example)

json

    {
      "code": 0,
      "data": {
        "list": [\
          {\
            "account_channel": "lb",\
            "stock_info": [\
              {\
                "symbol": "700.HK",\
                "symbol_name": "腾讯控股",\
                "currency": "HKD",\
                "quantity": "650",\
                "market": "HK",\
                "available_quantity": "-450",\
                "cost_price": "457.53",\
                "init_quantity": "214"\
              },\
              {\
                "symbol": "9991.HK",\
                "symbol_name": "宝尊电商-SW",\
                "currency": "HKD",\
                "market": "HK",\
                "quantity": "200",\
                "available_quantity": "0",\
                "cost_price": "32.25",\
                "init_quantity": "214"\
              },\
              {\
                "symbol": "TCEHY.US",\
                "symbol_name": "腾讯控股 (ADR)",\
                "currency": "USD",\
                "market": "US",\
                "quantity": "10",\
                "available_quantity": "10",\
                "init_quantity": "18"\
              },\
              {\
                "symbol": "2628.HK",\
                "symbol_name": "中国人寿",\
                "currency": "HKD",\
                "market": "HK",\
                "quantity": "9000",\
                "available_quantity": "0",\
                "init_quantity": "8000"\
              },\
              {\
                "symbol": "5.HK",\
                "symbol_name": "汇丰控股",\
                "currency": "HKD",\
                "market": "HK",\
                "quantity": "2400",\
                "available_quantity": "2000",\
                "init_quantity": "2000"\
              },\
              {\
                "symbol": "BABA.US",\
                "symbol_name": "阿里巴巴",\
                "currency": "USD",\
                "market": "US",\
                "quantity": "2000209",\
                "available_quantity": "2000209",\
                "init_quantity": "214"\
              },\
              {\
                "symbol": "2.HK",\
                "symbol_name": "中电控股",\
                "currency": "HKD",\
                "market": "HK",\
                "quantity": "2000",\
                "available_quantity": "2000",\
                "init_quantity": "2000"\
              },\
              {\
                "symbol": "NOK.US",\
                "symbol_name": "诺基亚",\
                "currency": "USD",\
                "market": "US",\
                "quantity": "1",\
                "available_quantity": "0",\
                "init_quantity": "1"\
              }\
            ]\
          }\
        ]
      }
    }

### Response Status [​](./trade-asset-stock.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [stock\_rsp](./trade-asset-stock.md#schemastock-rsp) |
| 400 | 内部错误 | None |

Schemas [​](./trade-asset-stock.md#schemas)

------------------------------------------------------------------------------

### stock\_rsp [​](./trade-asset-stock.md#stock-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| list | object\[\] | false | 股票持仓信息 |
| ∟ account\_channel | string | true | 账户类型 |
| ∟ stock\_info | object\[\] | false | 股票列表 |
| ∟∟ symbol | string | true | 股票代码 |
| ∟∟ symbol\_name | string | true | 股票名称 |
| ∟∟ quantity | string | true | 持仓股数 |
| ∟∟ available\_quantity | string | false | 可用股数 |
| ∟∟ currency | string | true | 币种  |
| ∟∟ market | string | true | 市场  |
| ∟∟ cost\_price | string | true | 成本价格 (具体根据客户端选择平均买入还是摊薄成本) |
| ∟∟ init\_quantity | string | false | 开盘前初始持仓 |

[LLMs Text](https://open.longbridge.com/docs/trade/asset/stock.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/asset/stock.md)

最后更新于:

Pager

[上一页获取保证金比例](./trade-asset-margin_ratio.md)

[下一页获取标的资讯](./content-news.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
