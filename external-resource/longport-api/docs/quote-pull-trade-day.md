获取市场交易日
=======

该接口用于获取市场的交易日信息。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 港股未来交易日
    longbridge trading-days HK
    # 美股未来交易日
    longbridge trading-days US
    # A 股未来交易日
    longbridge trading-days CN

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.trading\_days](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.trading_days) |
| Rust | [longbridge::quote::QuoteContext#trading\_days](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.trading_days) |
| Go  | [QuoteContext.TradingDays](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.TradingDays) |
| Node.js | [QuoteContext#tradingDays](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#tradingdays) |
| Java | [QuoteContext.getTradingDays](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getTradingDays(com.longbridge.Market%2Cjava.time.LocalDate%2Cjava.time.LocalDate)) |
| C++ | [longbridge::quote::QuoteContext::trading\_days](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a880aaa31010b174bb42c81412c240559) |

Info

[业务指令](./socket-biz-command.md)
：`9`

Request [​](./quote-pull-trade-day.md#request)

---------------------------------------------------------------------------------

### Parameters [​](./quote-pull-trade-day.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| market | string | 是   | 市场  <br>  <br>**可选值：**  <br>`US` - 美股市场  <br>`HK` - 港股市场  <br>`CN` - A 股市场  <br>`SG` - 新加坡市场 |
| beg\_day | string | 是   | 开始时间，使用 `YYMMDD` 格式，例如：`20220401` |
| end\_day | string | 是   | 结束时间，使用 `YYMMDD` 格式，例如：`20220420`  <br>  <br>**校验规则：**  <br>`开始时间` 和 `结束时间`，间隔不能大于一个月  <br>仅支持查询最近一年的数据 |

### Protobuf [​](./quote-pull-trade-day.md#protobuf)

protobuf

    message MarketTradeDayRequest {
      string market = 1;
      string beg_day = 2;
      string end_day = 3;
    }

### Request Example [​](./quote-pull-trade-day.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from datetime import date
    from longbridge.openapi import QuoteContext, Config, Market, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.trading_days(Market.HK, date(2022, 1, 1), date(2022, 2, 1))
    print(resp)

python

    import asyncio
    from datetime import date
    from longbridge.openapi import AsyncQuoteContext, Config, Market, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.trading_days(Market.HK, date(2022, 1, 1), date(2022, 2, 1))
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, Market, NaiveDate } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => {
        console.log("Open this URL to authorize: " + url)
      })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.tradingDays(Market.HK, new NaiveDate(2022, 1, 1), new NaiveDate(2022, 2, 1))
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    import java.time.LocalDate;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id")
                    .build(url -> System.out.println("Open to authorize: " + url))
                    .get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                MarketTradingDays resp = ctx.getTradingDays(Market.HK, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 1)).get();
                System.out.println(resp);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config, Market};
    use time::macros::date;
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id")
            .build(|url| println!("Open this URL to authorize: {url}"))
            .await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.trading_days(Market::HK, date!(2022 - 01 - 01), date!(2022 - 02 - 01)).await?;
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
    
        ctx.trading_days(Market::HK, Date{2022, 1, 1}, Date{2022, 2, 1}, [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            std::cout << "trade_days: " << res->trade_day.size() << std::endl;
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
    	"github.com/longbridge/openapi-go/quote"
    	openapi "github.com/longbridge/openapi-go"
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
    	begin := time.Date(2022, 1, 1, 0, 0, 0, 0, time.UTC)
    	end := time.Date(2022, 2, 1, 0, 0, 0, 0, time.UTC)
    	days, err := qctx.TradingDays(context.Background(), openapi.MarketHK, &begin, &end)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("trade_days:", len(days.TradeDay))
    }

Response [​](./quote-pull-trade-day.md#response)

-----------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-trade-day.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| trade\_day | string\[\] | 交易日，使用 `YYMMDD` 格式 |
| half\_trade\_day | string\[\] | 半日市，使用 `YYMMDD` 格式 |

### Protobuf [​](./quote-pull-trade-day.md#protobuf-1)

protobuf

    message MarketTradeDayResponse {
      repeated string trade_day = 1;
      repeated string half_trade_day = 2;
    }

### Response JSON Example [​](./quote-pull-trade-day.md#response-json-example)

json

    {
      "trade_day": [\
        "20220120",\
        "20220121",\
        "20220124",\
        "20220125",\
        "20220126",\
        "20220127",\
        "20220128",\
        "20220204",\
        "20220207",\
        "20220208",\
        "20220209",\
        "20220210"\
      ],
      "half_trade_day": ["20220131"]
    }

错误码 [​](./quote-pull-trade-day.md#%E9%94%99%E8%AF%AF%E7%A0%81)

-------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求数据非法 | 检查请求的市场，日期是否在正确范围内 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/trade-day.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/trade-day.md)

最后更新于:

Pager

[上一页获取各市场当日交易时段](./quote-pull-trade-session.md)

[下一页获取标的当日资金流向](./quote-pull-capital-flow-intraday.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
