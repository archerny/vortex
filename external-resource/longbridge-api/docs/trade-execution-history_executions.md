获取历史成交明细
========

该接口用于获取历史订单的成交明细，包括买入和卖出的成交记录，不支持当日成交明细查询。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge executions --history

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.TradeContext.history\_executions](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.TradeContext.history_executions) |
| Rust | [longbridge::trade::TradeContext#history\_executions](https://longbridge.github.io/openapi/rust/longbridge/trade/struct.TradeContext.html#method.history_executions) |
| Go  | [TradeContext.HistoryExecutions](https://pkg.go.dev/github.com/longbridge/openapi-go/trade#TradeContext.HistoryExecutions) |
| Node.js | [TradeContext#historyExecutions](https://longbridge.github.io/openapi/nodejs/classes/TradeContext.html#historyexecutions) |
| Java | [TradeContext.getHistoryExecutions](https://longbridge.github.io/openapi/java/com/longbridge/trade/TradeContext.html#getHistoryExecutions(com.longbridge.trade.GetHistoryExecutionsOptions)) |
| C++ | [longbridge::trade::TradeContext::history\_executions](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1trade_1_1_trade_context.html#a323b4e7185bf067e622db373e96bcc75) |

Request [​](./trade-execution-history_executions.md#request)

-----------------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/trade/execution/history |

### Parameters [​](./trade-execution-history_executions.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | NO  | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| start\_at | string | NO  | 开始时间，格式为时间戳 (秒)，例如：`1650410999`。  <br>  <br>开始时间为空时，默认为结束时间或当前时间前九十天。 |
| end\_at | string | NO  | 结束时间，格式为时间戳 (秒)，例如：`1650410999`。  <br>  <br>结束时间为空时，默认为开始时间后九十天或当前时间。 |

### Request Example [​](./trade-execution-history_executions.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from datetime import datetime
    from longbridge.openapi import TradeContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = TradeContext(config)
    
    resp = ctx.history_executions(
        symbol = "700.HK",
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
    
        resp = await ctx.history_executions(
            symbol = "700.HK",
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
      const resp = await ctx.historyExecutions({})
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
                Execution[] resp = ctx.getHistoryExecutions(null).get();
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
        let resp = ctx.history_executions(None).await?;
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
    
        ctx.history_executions(std::nullopt, [](auto res) {
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
    	start := time.Date(2024, 5, 1, 0, 0, 0, 0, time.UTC)
    	end := time.Date(2024, 5, 10, 0, 0, 0, 0, time.UTC)
    	executions, err := tctx.HistoryExecutions(context.Background(), &trade.GetHistoryExecutions{
    		Symbol:  "AAPL.US",
    		StartAt: start,
    		EndAt:   end,
    	})
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, e := range executions {
    		fmt.Println(e.OrderId)
    	}
    }

Response [​](./trade-execution-history_executions.md#response)

-------------------------------------------------------------------------------------------------

### Response Headers [​](./trade-execution-history_executions.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./trade-execution-history_executions.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "has_more": false,
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

### Response Status [​](./trade-execution-history_executions.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 查询成功 | [history\_executions\_rsp](./trade-execution-history_executions.md#schemahistory-executions-rsp) |
| 400 | 查询失败，请求参数错误。 | None |

Schemas [​](./trade-execution-history_executions.md#schemas)

-----------------------------------------------------------------------------------------------

### history\_executions\_rsp [​](./trade-execution-history_executions.md#history-executions-rsp)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| has\_more | boolean | true | 是否还有更多数据。  <br>  <br>每次查询最大订单数量为 1000，如果查询结果数量超过 1000，那么 has\_more 就会为 true |
| trades | object\[\] | false | 成交明细信息 |
| ∟ order\_id | string | true | 订单 ID |
| ∟ trade\_id | string | true | 成交 ID |
| ∟ symbol | string | true | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |
| ∟ trade\_done\_at | string | true | 成交时间，格式为时间戳 (秒) |
| ∟ quantity | string | true | 成交数量 |
| ∟ price | string | true | 成交价格 |

[LLMs Text](https://open.longbridge.com/docs/trade/execution/history_executions.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/trade/execution/history_executions.md)

最后更新于:

Pager

[上一页交易命名词典](./trade-trade-definition.md)

[下一页获取当日成交明细](./trade-execution-today_executions.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
