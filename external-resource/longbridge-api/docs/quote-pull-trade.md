获取标的成交明细
========

该接口用于获取标的的成交明细数据。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Tesla 最新逐笔成交
    longbridge trades TSLA.US
    # Apple 最新逐笔成交
    longbridge trades AAPL.US
    # NVDA 最新逐笔成交
    longbridge trades NVDA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.trades](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.trades) |
| Rust | [longbridge::quote::QuoteContext#trades](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.trades) |
| Go  | [QuoteContext.Trades](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.Trades) |
| Node.js | [QuoteContext#trades](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#trades) |
| Java | [QuoteContext.getTrades](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getTrades(java.lang.String%2Cint)) |
| C++ | [longbridge::quote::QuoteContext::trades](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a5329988f37c0b1a5ef4145dc8b80cdb2) |

Info

[业务指令](./socket-biz-command.md)
：`17`

Request [​](./quote-pull-trade.md#request)

-----------------------------------------------------------------------------

### Parameters [​](./quote-pull-trade.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | 是   | 标的代码，使用 `ticker.region` 格式，例如：`700.HK` |
| count | int32 | 是   | 请求的逐笔明细数量  <br>  <br>**校验规则：**  <br>请求数量最大为 `1000` |

### Protobuf [​](./quote-pull-trade.md#protobuf)

protobuf

    message SecurityTradeRequest {
      string symbol = 1;
      int32 count = 2;
    }

### Request Example [​](./quote-pull-trade.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.trades("700.HK", 10)
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.trades("700.HK", 10)
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => {
        console.log("Open this URL to authorize: " + url)
      })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.trades("700.HK", 10)
      for (const obj of resp) {
        console.log(obj.toString())
      }
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id")
                    .build(url -> System.out.println("Open to authorize: " + url))
                    .get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                Trade[] resp = ctx.getTrades("700.HK", 10).get();
                for (Trade obj : resp) {
                    System.out.println(obj);
                }
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id")
            .build(|url| println!("Open this URL to authorize: {url}"))
            .await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.trades("700.HK", 10).await?;
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
    using namespace longbridge::quote;
    
    static void
    run(const OAuth& oauth)
    {
        Config config = Config::from_oauth(oauth);
        QuoteContext ctx = QuoteContext::create(config);
    
        ctx.trades("700.HK", 10, [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& t : *res) {
                std::cout << t.price << " " << t.quantity << std::endl;
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
    	"github.com/longbridge/openapi-go/quote"
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
    	qctx, err := quote.NewFromCfg(conf)
    	if err != nil {
    		log.Fatal(err)
    	}
    	defer qctx.Close()
    	trades, err := qctx.Trades(context.Background(), "700.HK", 10)
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, t := range trades {
    		fmt.Println(t.Price, t.Volume)
    	}
    }

Response [​](./quote-pull-trade.md#response)

-------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-trade.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| symbol | string | 标的代码 |
| trades | object\[\] | 逐笔明细数据 |
| ∟ price | string | 价格  |
| ∟ volume | int64 | 成交量 |
| ∟ timestamp | int64 | 成交时间 |
| ∟ trade\_type | string | [交易类型说明](./quote-pull-trade.md#%E4%BA%A4%E6%98%93%E7%B1%BB%E5%9E%8B) |
| ∟ direction | int32 | 交易方向  <br>  <br>**可选值：**  <br>`0` - neutral  <br>`1` - down  <br>`2` - up |
| ∟ trade\_session | int32 | 交易时段，详见 [TradeSession](./quote-objects.md#tradesession-%E4%BA%A4%E6%98%93%E6%97%B6%E6%AE%B5) |

#### 交易类型 [​](./quote-pull-trade.md#%E4%BA%A4%E6%98%93%E7%B1%BB%E5%9E%8B)

港股

*   `*` - 场外交易
*   `D` - 碎股交易
*   `M` - 非自动对盘
*   `P` - 开市前成交盘
*   `U` - 竞价交易
*   `X` - 同一券商非自动对盘
*   `Y` - 同一券商自动对盘
*   \- 自动对盘

美股

*   \- 自动对盘
*   `A` - 收购
*   `B` - 批量交易
*   `D` - 分配
*   `F` - 跨市扫盘单
*   `G` - 批量卖出
*   `H` - 离价交易
*   `I` - 碎股交易
*   `K` - 第 155 条交易（纽交所规则）
*   `M` - 交易所收盘价
*   `P` - 前参考价
*   `Q` - 交易所开盘价
*   `S` - 拆单交易
*   `V` - 附属交易
*   `W` - 平均价成交
*   `X` - 跨市场交易
*   `1` - 停售股票（常规交易）

### Protobuf [​](./quote-pull-trade.md#protobuf-1)

protobuf

    message SecurityTradeResponse {
      string symbol = 1;
      repeated Trade trades = 2;
    }
    
    message Trade {
      string price = 1;
      int64 volume = 2;
      int64 timestamp = 3;
      string trade_type = 4;
      int32 direction = 5;
      TradeSession trade_session = 6;
    }

### Response JSON Example [​](./quote-pull-trade.md#response-json-example)

json

    {
      "symbol": "AAPL.US",
      "trades": [\
        {\
          "price": "158.760",\
          "volume": 1,\
          "timestamp": 1651103979,\
          "trade_type": "I",\
          "direction": 0,\
          "trade_session": 2\
        },\
        {\
          "price": "158.745",\
          "volume": 1,\
          "timestamp": 1651103985,\
          "trade_type": "I",\
          "direction": 0,\
          "trade_session": 2\
        },\
        {\
          "price": "158.800",\
          "volume": 1,\
          "timestamp": 1651103995,\
          "trade_type": "I",\
          "direction": 0,\
          "trade_session": 2\
        }\
      ]
    }

错误码 [​](./quote-pull-trade.md#%E9%94%99%E8%AF%AF%E7%A0%81)

---------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求标的不存在 | 检查请求的 `symbol` 是否正确 |
| 7   | 301603 | 标的无行情 | 标的没有请求的行情数据 |
| 7   | 301604 | 无权限 | 没有获取标的行情的权限 |
| 7   | 301607 | 接口限制 | 请求的数据数量超限，减少数据数量 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/trade.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/trade.md)

最后更新于:

Pager

[上一页获取券商席位 ID](./quote-pull-broker-ids.md)

[下一页获取标的当日分时](./quote-pull-intraday.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
