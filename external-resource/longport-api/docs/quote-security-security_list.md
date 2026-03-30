获取标的列表
======

获取标的列表尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 美股夜盘可交易标的列表
    longbridge security-list

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.security\_list](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.security_list) |
| Rust | [longbridge::quote::QuoteContext#security\_list](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.security_list) |
| Go  | [QuoteContext.SecurityList](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.SecurityList) |
| Node.js | [QuoteContext#securityList](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#securitylist) |
| Java | [QuoteContext.getSecurityList](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getSecurityList(com.longbridge.Market%2Ccom.longbridge.quote.SecurityListCategory)) |
| C++ | [longbridge::quote::QuoteContext::security\_list](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#aa1fe96cbcadcb09a7b3cf08c0324a7b4) |

Request [​](./quote-security-security_list.md#request)

-----------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/quote/get\_security\_list |

### Parameters [​](./quote-security-security_list.md#parameters)

> Content-Type: application/json; charset=utf-8

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| market | string | YES | 市场，目前只支持 US |
| category | string | YES | 市场下分类，目前只支持 Overnight |

### Request Example [​](./quote-security-security_list.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, Market, SecurityListCategory, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    resp = ctx.security_list(Market.US, SecurityListCategory.Overnight)
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, Market, SecurityListCategory, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
        resp = await ctx.security_list(Market.US, SecurityListCategory.Overnight)
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, QuoteContext, OAuth, SecurityListCategory } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = QuoteContext.new(config)
      const resp = await ctx.securityList(SecurityListCategory.Overnight)
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
                Security[] resp = ctx.getSecurityList(SecurityListCategory.Overnight).get();
                for (Security s : resp) System.out.println(s);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, quote::QuoteContext, Config, quote::SecurityListCategory};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let (ctx, _) = QuoteContext::new(config);
        let resp = ctx.security_list(SecurityListCategory::Overnight).await?;
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
    
        ctx.security_list(SecurityListCategory::Overnight, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "securities: " << res->size() << std::endl;
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
    	list, err := qctx.SecurityList(context.Background(), quote.MarketUS, quote.Overnight)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("securities:", len(list))
    }

Response [​](./quote-security-security_list.md#response)

-------------------------------------------------------------------------------------------

### Response Headers [​](./quote-security-security_list.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./quote-security-security_list.md#response-example)

json

    {
      "code": 0,
      "data": {
        "list": [\
          {\
            "symbol": "BAC.US",\
            "name_cn": "美国银行",\
            "name_hk": "美國銀行",\
            "name_en": "Bank of America"\
          },\
          {\
            "symbol": "RDDT.US",\
            "name_cn": "REDDIT INC",\
            "name_hk": "REDDIT INC",\
            "name_en": "REDDIT INC"\
          },\
          {\
            "symbol": "GOOGL.US",\
            "name_cn": "谷歌-A",\
            "name_hk": "谷歌-A",\
            "name_en": "Alphabet"\
          }\
        ]
      }
    }

#### Response Status [​](./quote-security-security_list.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [security\_response](./quote-security-security_list.md#get-security-list-rsp) |
| 400 | 参数错误 | None |

Schemas [​](./quote-security-security_list.md#schemas)

-----------------------------------------------------------------------------------------

### security\_response [​](./quote-security-security_list.md#security-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| list | object\[\] | false | 列表  |
| ∟ symbol | integer | true | 标的代码 |
| ∟ name\_cn | string | true | 中文名称 |
| ∟ name\_hk | string | true | 繁体名称 |
| ∟ name\_en | string | true | 英文名称 |

错误码 [​](./quote-security-security_list.md#%E9%94%99%E8%AF%AF%E7%A0%81)

---------------------------------------------------------------------------------------------------------

| 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- |
| 310010 | 无效的请求 | 请求参数有误 |
| 310011 | 服务端内部错误 | 请重试或联系技术人员处理 |

[LLMs Text](https://open.longbridge.com/docs/quote/security/security_list.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/security/security.md)

最后更新于:

Pager

[上一页更新自选股分组](./quote-individual-watchlist_update_group.md)

[下一页概览](./trade-trade-overview.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
