获取标的的期权链到期日期权标的列表
=================

该接口用于获取标的的期权链到期日期权标的列表。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # AAPL 2026-04-17 到期的行权价列表
    longbridge option-chain AAPL.US --date 2026-04-17
    # TSLA 2026-04-17 到期的行权价列表
    longbridge option-chain TSLA.US --date 2026-04-17

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.option\_chain\_info\_by\_date](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.option_chain_info_by_date) |
| Rust | [longbridge::quote::QuoteContext#option\_chain\_info\_by\_date](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.option_chain_info_by_date) |
| Go  | [QuoteContext.OptionChainInfoByDate](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.OptionChainInfoByDate) |
| Node.js | [QuoteContext#optionChainInfoByDate](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#optionchaininfobydate) |
| Java | [QuoteContext.getOptionChainInfoByDate](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getOptionChainInfoByDate(java.lang.String%2Cjava.time.LocalDate)) |
| C++ | [longbridge::quote::QuoteContext::option\_chain\_info\_by\_date](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a9d8df470c1324ce2407265cdc99df2a5) |

Info

[业务指令](./socket-biz-command.md)
：`21`

Request [​](./quote-pull-optionchain-date-strike.md#request)

-----------------------------------------------------------------------------------------------

### Parameters [​](./quote-pull-optionchain-date-strike.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | 是   | 标的代码，使用 `ticker.region` 格式，例如：`700.HK` |
| expiry\_date | string | 是   | 期权到期日，使用 `YYMMDD` 格式，例如：`20220429`，通过 [期权到期日](https://open.longbridge.com/zh-CN/docs/quote/pull/optionchain_date)<br> 接口获取 |

### Protobuf [​](./quote-pull-optionchain-date-strike.md#protobuf)

protobuf

    message OptionChainDateStrikeInfoRequest {
      string symbol = 1;
      string expiry_date = 2;
    }

### Request Example [​](./quote-pull-optionchain-date-strike.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from datetime import date
    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.option_chain_info_by_date("AAPL.US", date(2023, 1, 20))
    print(resp)

python

    import asyncio
    from datetime import date
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.option_chain_info_by_date("AAPL.US", date(2023, 1, 20))
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, NaiveDate } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => {
        console.log("Open this URL to authorize: " + url)
      })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.optionChainInfoByDate("AAPL.US", new NaiveDate(2023, 1, 20))
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
                StrikePriceInfo[] resp = ctx.getOptionChainInfoByDate("AAPL.US", LocalDate.of(2023, 1, 20)).get();
                for (StrikePriceInfo o : resp) System.out.println(o);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config};
    use time::macros::date;
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id")
            .build(|url| println!("Open this URL to authorize: {url}"))
            .await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.option_chain_info_by_date("AAPL.US", date!(2023 - 01 - 20)).await?;
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
    
        ctx.option_chain_info_by_date("AAPL.US", Date{2023, 1, 20}, [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& o : *res) std::cout << o.price << std::endl;
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
    	expiry := time.Date(2023, 1, 20, 0, 0, 0, 0, time.UTC)
    	list, err := qctx.OptionChainInfoByDate(context.Background(), "AAPL.US", &expiry)
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, o := range list {
    		fmt.Println(o.Price)
    	}
    }

Response [​](./quote-pull-optionchain-date-strike.md#response)

-------------------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-optionchain-date-strike.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| strike\_price\_info | object\[\] | 到期日期权标的列表 |
| ∟ price | string | 行权价 |
| ∟ call\_symbol | string | CALL 期权标的代码 |
| ∟ put\_symbol | string | PUT 期权标的代码 |
| ∟ standard | bool | 是否标准期权 |

### Protobuf [​](./quote-pull-optionchain-date-strike.md#protobuf-1)

protobuf

    message OptionChainDateStrikeInfoResponse {
      repeated StrikePriceInfo strike_price_info = 1;
    }
    
    message StrikePriceInfo {
      string price = 1;
      string call_symbol = 2;
      string put_symbol = 3;
      bool  standard = 4;
    }

### Response JSON Example [​](./quote-pull-optionchain-date-strike.md#response-json-example)

json

    {
      "strike_price_info": [\
        {\
          "price": "100",\
          "call_symbol": "AAPL220429C100000.US",\
          "put_symbol": "AAPL220429P100000.US",\
          "standard": true\
        },\
        {\
          "price": "105",\
          "call_symbol": "AAPL220429C105000.US",\
          "put_symbol": "AAPL220429P105000.US",\
          "standard": true\
        },\
        {\
          "price": "110",\
          "call_symbol": "AAPL220429C110000.US",\
          "put_symbol": "AAPL220429P110000.US",\
          "standard": true\
        },\
        {\
          "price": "115",\
          "call_symbol": "AAPL220429C115000.US",\
          "put_symbol": "AAPL220429P115000.US",\
          "standard": true\
        }\
      ]
    }

错误码 [​](./quote-pull-optionchain-date-strike.md#%E9%94%99%E8%AF%AF%E7%A0%81)

---------------------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求数据非法 | 检查请求的 `symbol`，`expiry_date` 数据格式 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/optionchain-date-strike.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/optionchain-date-strike.md)

最后更新于:

Pager

[上一页获取标的的期权链到期日列表](./quote-pull-optionchain-date.md)

[下一页获取轮证发行商 ID](./quote-pull-issuer.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
