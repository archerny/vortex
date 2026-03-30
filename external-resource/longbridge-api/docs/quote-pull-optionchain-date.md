获取标的的期权链到期日列表
=============

该接口用于获取标的的期权链到期日列表。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # AAPL 期权到期日列表
    longbridge option-chain AAPL.US
    # TSLA 期权到期日列表
    longbridge option-chain TSLA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.option\_chain\_expiry\_date\_list](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.option_chain_expiry_date_list) |
| Rust | [longbridge::quote::QuoteContext#option\_chain\_expiry\_date\_list](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.option_chain_expiry_date_list) |
| Go  | [QuoteContext.OptionChainExpiryDateList](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.OptionChainExpiryDateList) |
| Node.js | [QuoteContext#optionChainExpiryDateList](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#optionchainexpirydatelist) |
| Java | [QuoteContext.getOptionChainExpiryDateList](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getOptionChainExpiryDateList(java.lang.String)) |
| C++ | [longbridge::quote::QuoteContext::option\_chain\_expiry\_date\_list](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a39c967d93c9435deea550956bc668bdb) |

Info

[业务指令](./socket-biz-command.md)
：`20`

Request [​](./quote-pull-optionchain-date.md#request)

----------------------------------------------------------------------------------------

### Parameters [​](./quote-pull-optionchain-date.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | 是   | 标的代码，使用 `ticker.region` 格式，例如：`700.HK` |

### Protobuf [​](./quote-pull-optionchain-date.md#protobuf)

protobuf

    message SecurityRequest {
      string symbol = 1;
    }

### Request Example [​](./quote-pull-optionchain-date.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.option_chain_expiry_date_list("AAPL.US")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.option_chain_expiry_date_list("AAPL.US")
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
      const resp = await ctx.optionChainExpiryDateList("AAPL.US")
      console.log(resp)
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
                LocalDate[] resp = ctx.getOptionChainExpiryDateList("AAPL.US").get();
                for (LocalDate d : resp) System.out.println(d);
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
        let resp = ctx.option_chain_expiry_date_list("AAPL.US").await?;
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
    
        ctx.option_chain_expiry_date_list("AAPL.US", [](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& d : *res) std::cout << d.year << "-" << (int)d.month << "-" << (int)d.day << std::endl;
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
    	dates, err := qctx.OptionChainExpiryDateList(context.Background(), "AAPL.US")
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, d := range dates {
    		fmt.Println(d.Format("2006-01-02"))
    	}
    }

Response [​](./quote-pull-optionchain-date.md#response)

------------------------------------------------------------------------------------------

### Response Properties [​](./quote-pull-optionchain-date.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| expiry\_date | string\[\] | 标的对应的期权链到期日列表，使用 `YYMMDD` 格式 |

### Protobuf [​](./quote-pull-optionchain-date.md#protobuf-1)

protobuf

    message OptionChainDateListResponse {
      repeated string expiry_date = 1;
    }

### Response JSON Example [​](./quote-pull-optionchain-date.md#response-json-example)

json

    {
      "expiry_date": [\
        "20220422",\
        "20220429",\
        "20220506",\
        "20220513",\
        "20220520",\
        "20220527",\
        "20220603",\
        "20220617",\
        "20220715",\
        "20220819",\
        "20220916",\
        "20221021",\
        "20221118",\
        "20230120",\
        "20230317",\
        "20230616",\
        "20230915",\
        "20240119",\
        "20240621"\
      ]
    }

错误码 [​](./quote-pull-optionchain-date.md#%E9%94%99%E8%AF%AF%E7%A0%81)

--------------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301600 | 请求标的不存在 | 检查请求的 `symbol` 是否正确 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/optionchain-date.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/optionchain_date.md)

最后更新于:

Pager

[上一页获取标的历史 K 线](./quote-pull-history-candlestick.md)

[下一页获取标的的期权链到期日期权标的列表](./quote-pull-optionchain-date-strike.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
