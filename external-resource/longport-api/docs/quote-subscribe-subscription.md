获取已订阅标的行情
=========

该接口用于获取当前连接已订阅的标的行情。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 查看当前 WebSocket 实时订阅状态
    longbridge subscriptions

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.subscriptions](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.subscriptions) |
| Rust | [longbridge::quote::QuoteContext#subscriptions](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.subscriptions) |
| Go  | [QuoteContext.Subscriptions](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.Subscriptions) |
| Node.js | [QuoteContext#subscriptions](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#subscriptions) |
| Java | [QuoteContext.getSubscrptions](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getSubscrptions()) |
| C++ | [longbridge::quote::QuoteContext::subscriptions](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#af2bf64d57c5dfc29d39cd63441ed5ec5) |

Info

[业务指令](./socket-biz-command.md)
：`5`

Request [​](./quote-subscribe-subscription.md#request)

-----------------------------------------------------------------------------------------

### Protobuf [​](./quote-subscribe-subscription.md#protobuf)

protobuf

    message SubscriptionRequest {
    }

### Request Example [​](./quote-subscribe-subscription.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, SubType, OAuthBuilder
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Quote])
    resp = ctx.subscriptions()
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, SubType, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        await ctx.subscribe(["700.HK", "AAPL.US"], [SubType.Quote])
        resp = await ctx.subscriptions()
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.subscriptions()
      console.log(resp)
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
                Subscription[] resp = ctx.getSubscriptions().get();
                for (Subscription s : resp) System.out.println(s);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.subscriptions().await?;
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
    
        ctx.subscriptions([](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            for (const auto& s : *res) std::cout << s.symbol << std::endl;
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
    	subs, err := qctx.Subscriptions(context.Background())
    	if err != nil {
    		log.Fatal(err)
    	}
    	for symbol := range subs {
    		fmt.Println(symbol)
    	}
    }

Response [​](./quote-subscribe-subscription.md#response)

-------------------------------------------------------------------------------------------

### Response Properties [​](./quote-subscribe-subscription.md#response-properties)

| Name | Type | Description |
| --- | --- | --- |
| sub\_list | object\[\] | 订阅的数据 |
| ∟ symbol | string | 标的代码 |
| ∟ sub\_type | \[\]int32 | 订阅的数据类型，详见 [SubType](./quote-objects.md#subtype-%E8%AE%A2%E9%98%85%E6%95%B0%E6%8D%AE%E7%9A%84%E7%B1%BB%E5%9E%8B) |

### Protobuf [​](./quote-subscribe-subscription.md#protobuf-1)

protobuf

    message SubscriptionResponse {
      repeated SubTypeList sub_list = 1;
    }
    
    message SubTypeList {
      string symbol = 1;
      repeated SubType sub_type = 2;
    }

### Response JSON Example [​](./quote-subscribe-subscription.md#response-json-example)

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

错误码 [​](./quote-subscribe-subscription.md#%E9%94%99%E8%AF%AF%E7%A0%81)

---------------------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |

[LLMs Text](https://open.longbridge.com/docs/quote/subscribe/subscription.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/subscribe/subsciption.md)

最后更新于:

Pager

[上一页取消订阅行情数据](./quote-subscribe-unsubscribe.md)

[下一页实时价格推送](./quote-push-quote.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
