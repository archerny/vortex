订阅行情数据
======

该接口用于订阅标的行情数据。

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.subscribe](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.subscribe) |
| Rust | [longbridge::quote::QuoteContext#subscribe](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.subscribe) |
| Go  | [QuoteContext.Subscribe](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.Subscribe) |
| Node.js | [QuoteContext#subscribe](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#subscribe) |
| Java | [QuoteContext.subscribe](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#subscribe(java.lang.String%5B%5D%2Cint)) |
| C++ | [longbridge::quote::QuoteContext::subscribe](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a321f37f6d6e05c00062cd5a15b2928a7) |

Info

[业务指令](./socket-biz-command.md)
：`6`

Request [​](./quote-subscribe-subscribe.md#request)

--------------------------------------------------------------------------------------

### Parameters [​](./quote-subscribe-subscribe.md#parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string\[\] | 是   | 订阅的标的代码，例如：`[700.HK]`  <br>  <br>**校验规则：**  <br>每次请求支持传入的标的数量上限是 `500` 个  <br>每个用户同时订阅标的数量最多为 `500` |
| sub\_type | int32\[\] | 是   | 订阅的数据类型，例如：`[1,2]`，详见 [SubType](./quote-objects.md#subtype-%E8%AE%A2%E9%98%85%E6%95%B0%E6%8D%AE%E7%9A%84%E7%B1%BB%E5%9E%8B) |
| is\_first\_push | bool | 是   | 订阅后是否立刻进行一次数据推送。( trade 不支持) |

### Protobuf [​](./quote-subscribe-subscribe.md#protobuf)

protobuf

    message SubscribeRequest {
      repeated string symbol = 1;
      repeated SubType sub_type = 2;
      bool is_first_push = 3;
    }

### Request Example [​](./quote-subscribe-subscribe.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from time import sleep
    from longbridge.openapi import QuoteContext, Config, SubType, PushQuote, OAuthBuilder
    
    def on_quote(symbol: str, event: PushQuote):
        print(symbol, event)
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    ctx.set_on_quote(on_quote)
    
    ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Quote])
    sleep(30)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, SubType, PushQuote, OAuthBuilder
    
    async def main() -> None:
        async def on_quote(symbol: str, event: PushQuote) -> None:
            print(symbol, event)
    
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        ctx.set_on_quote(on_quote)
    
        await ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Quote])
        await asyncio.sleep(30)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, SubType } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      ctx.setOnQuote((event) => console.log(event))
      await ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Quote], true)
      await new Promise(r => setTimeout(r, 30000))
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.quote.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 QuoteContext ctx = QuoteContext.create(config)) {
                ctx.setOnQuote(event -> System.out.println(event));
                ctx.subscribe(new String[] { "700.HK", "AAPL.US" }, new SubType[] { SubType.Quote }, true).get();
                Thread.sleep(30000);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config, quote::SubFlags};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        ctx.subscribe(vec!["700.HK".to_string(), "AAPL.US".to_string()], SubFlags::quote(), true).await?;
        tokio::time::sleep(std::time::Duration::from_secs(30)).await;
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
    
        ctx.set_on_quote([](auto e) { std::cout << e->symbol << std::endl; });
        ctx.subscribe(symbols, SubFlags::QUOTE(), true, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
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
    	qctx.OnQuote(func(e *quote.PushQuote) { fmt.Println(e.Symbol) })
    	err = qctx.Subscribe(context.Background(), []string{"700.HK", "AAPL.US"}, []quote.SubType{quote.SubTypeQuote}, true)
    	if err != nil {
    		log.Fatal(err)
    	}
    	// keep running to receive push
    	select {}
    }

Response [​](./quote-subscribe-subscribe.md#response)

----------------------------------------------------------------------------------------

### Response Properties [​](./quote-subscribe-subscribe.md#response-properties)

返回本次请求订阅成功的标的和类型。

| Name | Type | Description |
| --- | --- | --- |
| sub\_list | object\[\] | 订阅的数据 |
| ∟ symbol | string | 标的代码 |
| ∟ sub\_type | int32\[\] | 订阅的数据类型，详见：[SubType](./quote-objects.md#subtype-%E8%AE%A2%E9%98%85%E6%95%B0%E6%8D%AE%E7%9A%84%E7%B1%BB%E5%9E%8B) |

### Protobuf [​](./quote-subscribe-subscribe.md#protobuf-1)

protobuf

    message SubscriptionResponse {
      repeated SubTypeList sub_list = 1;
    }
    
    message SubTypeList {
      string symbol = 1;
      repeated SubType sub_type = 2;
    }

### Response JSON Example [​](./quote-subscribe-subscribe.md#response-json-example)

json

    {
      "sub_list": [\
        {\
          "symbol": "700.HK",\
          "sub_type": [1, 2, 3]\
        },\
        {\
          "symbol": "AAPL.US",\
          "sub_type": [2]\
        }\
      ]
    }

接口限制 [​](./quote-subscribe-subscribe.md#%E6%8E%A5%E5%8F%A3%E9%99%90%E5%88%B6)

----------------------------------------------------------------------------------------------------------------

Caution

*   港股 BMP 行情不支持行情数据推送。

错误码 [​](./quote-subscribe-subscribe.md#%E9%94%99%E8%AF%AF%E7%A0%81)

------------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |
| 7   | 301605 | 订阅数量超出限制 | 取消部分订阅 |
| 7   | 301600 | 请求参数有误 | 检查请求的 `sub_type` |

[LLMs Text](https://open.longbridge.com/docs/quote/subscribe/subscribe.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/subscribe/subscribe.md)

最后更新于:

Pager

[上一页历史市场温度](./quote-pull-history_market_temperature.md)

[下一页取消订阅行情数据](./quote-subscribe-unsubscribe.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
