获取当日成交明细
========

该接口用于获取当日订单的成交明细。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge executions

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.today\_executions](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.today_executions) |
| Rust | [longbridge::trade::TradeContext#today\_executions](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.today_executions) |
| Go  | [TradeContext.TodayExecutions](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.TodayExecutions) |
| Node.js | [TradeContext#todayExecutions](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#todayexecutions) |
| Java | [TradeContext.getTodayExecutions](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getTodayExecutions(com.longbridge.trade.GetTodayExecutionsOptions)) |
| C++ | [longbridge::trade::TradeContext::today\_executions](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#addb207fb478708806f4bb414a48fd8ce) |

Request [​](./trade-execution-today_executions.md#request)

---------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/trade/execution/today |

### Parameters [​](./trade-execution-today_executions.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | NO  | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| order\_id | string | NO  | 订单 ID，用于指定订单 ID 查询，例如：`701276261045858304` |

### Request Example [​](./trade-execution-today_executions.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    
    resp = ctx.today_executions(symbol = "700.HK")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncTradeContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncTradeContext.create(config)
    
        resp = await ctx.today_executions(symbol = "700.HK")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, TradeContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = TradeContext.new(config)
      const resp = await ctx.todayExecutions({})
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
                Execution[] resp = ctx.getTodayExecutions(null).get();
                for (Execution e : resp) System.out.println(e);
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
        let resp = ctx.today_executions(None).await?;
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
    
        ctx.today_executions(std::nullopt, [](auto res) {
            if (!res) { std::cout << "failed" << std::endl; return; }
            for (const auto& e : *res) std::cout << e.order_id << std::endl;
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
    	executions, err := tctx.TodayExecutions(context.Background(), &trade.GetTodayExecutions{})
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, e := range executions {
    		fmt.Println(e.OrderId)
    	}
    }

Response [​](./trade-execution-today_executions.md#response)

-----------------------------------------------------------------------------------------------

### Response Headers [​](./trade-execution-today_executions.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-execution-today_executions.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "trades": [\
          {\
            "order_id": "693664675163312128",\
            "price": "388",\
            "quantity": "100",\
            "symbol": "700.HK",\
            "trade_done_at": "1648611351",\
            "trade_id": "693664675163312128-1648611351433741210"\
          }\
        ]
      }
    }

### Response Status [​](./trade-execution-today_executions.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 查询成功 | None |
| 400 | 查询失败，请求参数错误。 | None |

### Response Schema [​](./trade-execution-today_executions.md#response-schema)

Schemas [​](./trade-execution-today_executions.md#schemas)

---------------------------------------------------------------------------------------------

### today\_executions\_rsp [​](./trade-execution-today_executions.md#today-executions-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| trades | object\[\] | false | 成交明细信息 |
| ∟ order\_id | string | true | 订单 ID |
| ∟ trade\_id | string | true | 成交 ID |
| ∟ symbol | string | true | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| ∟ trade\_done\_at | string | true | 成交时间，格式为时间戳 (秒) |
| ∟ quantity | string | true | 成交数量 |
| ∟ price | string | true | 成交价格 |

[LLMs Text](https://open.longbridge.com/docs/trade/execution/today_executions.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/execution/today_executions.md)

最后更新于:

Pager

[上一页获取历史成交明细](./trade-execution-history_executions.md)

[下一页预估最大购买数量](./trade-order-estimate_available_buy_limit.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
