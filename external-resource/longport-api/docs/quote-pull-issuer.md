获取轮证发行商 ID
==========

该接口用于获取轮证发行商 ID 数据 (可每天同步一次)。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # 港股权证发行商完整列表
    longbridge warrant-issuers

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.QuoteContext.warrant\_issuers](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.QuoteContext.warrant_issuers) |
| Rust | [longbridge::quote::QuoteContext#warrant\_issuers](https://longbridge.github.io/openapi/rust/longbridge/quote/struct.QuoteContext.html#method.warrant_issuers) |
| Go  | [QuoteContext.WarrantIssuers](https://pkg.go.dev/github.com/longbridge/openapi-go/quote#QuoteContext.WarrantIssuers) |
| Node.js | [QuoteContext#warrantIssuers](https://longbridge.github.io/openapi/nodejs/classes/QuoteContext.html#warrantissuers) |
| Java | [QuoteContext.getWarrantIssuers](https://longbridge.github.io/openapi/java/com/longbridge/quote/QuoteContext.html#getWarrantIssuers()) |
| C++ | [longbridge::quote::QuoteContext::warrant\_issuers](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1quote_1_1_quote_context.html#a90965318b95a15869f50760339b8a71c) |

Info

[业务指令](./socket-biz-command.md)
：`22`

Request [​](./quote-pull-issuer.md#request)

------------------------------------------------------------------------------

### Request Example [​](./quote-pull-issuer.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import QuoteContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = QuoteContext(config)
    
    resp = ctx.warrant_issuers()
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncQuoteContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncQuoteContext.create(config)
    
        resp = await ctx.warrant_issuers()
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
      const resp = await ctx.warrantIssuers()
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
                IssuerInfo[] resp = ctx.getWarrantIssuers().get();
                for (IssuerInfo obj : resp) {
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
        let resp = ctx.warrant_issuers().await?;
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
    
        ctx.warrant_issuers([](auto res) {
            if (!res) {
                std::cout << "failed: " << *res.status().message() << std::endl;
                return;
            }
            for (const auto& i : *res) {
                std::cout << i.id << " " << i.name_cn << " " << i.name_en << std::endl;
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
    	issuers, err := qctx.WarrantIssuers(context.Background())
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, i := range issuers {
    		fmt.Println(i.Id, i.NameCn, i.NameEn)
    	}
    }

Response [​](./quote-pull-issuer.md#response)

--------------------------------------------------------------------------------

### Parameters [​](./quote-pull-issuer.md#parameters)

| Name | Type | Description |
| --- | --- | --- |
| issuer\_info | object\[\] | 发行机构信息 |
| ∟ id | int32 | 机构 ID |
| ∟ name\_cn | string | 机构名称 (简) |
| ∟ name\_en | string | 机构名称 (英) |
| ∟ name\_hk | string | 机构名称 (繁) |

### Protobuf [​](./quote-pull-issuer.md#protobuf)

protobuf

    message IssuerInfoResponse {
      repeated IssuerInfo issuer_info = 1;
    }
    
    message IssuerInfo {
      int32 id = 1;
      string name_cn = 2;
      string name_en = 3;
      string name_hk = 4;
    }

### Response JSON Example [​](./quote-pull-issuer.md#response-json-example)

json

    {
      "issuer_info": [\
        {\
          "id": 15,\
          "name_cn": "瑞银",\
          "name_en": "UB",\
          "name_hk": "瑞銀"\
        },\
        {\
          "id": 14,\
          "name_cn": "汇丰",\
          "name_en": "HS",\
          "name_hk": "滙豐"\
        },\
        {\
          "id": 12,\
          "name_cn": "花旗",\
          "name_en": "CT",\
          "name_hk": "花旗"\
        }\
      ]
    }

错误码 [​](./quote-pull-issuer.md#%E9%94%99%E8%AF%AF%E7%A0%81)

----------------------------------------------------------------------------------------------

| 协议错误码 | 业务错误码 | 描述  | 排查建议 |
| --- | --- | --- | --- |
| 3   | 301600 | 无效的请求 | 请求参数有误或解包失败 |
| 3   | 301606 | 限流  | 降低请求频次 |
| 7   | 301602 | 服务端内部错误 | 请重试或联系技术人员处理 |

[LLMs Text](https://open.longbridge.com/docs/quote/pull/issuer.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/quote/pull/issuer.md)

最后更新于:

Pager

[上一页获取标的的期权链到期日期权标的列表](./quote-pull-optionchain-date-strike.md)

[下一页获取轮证筛选列表](./quote-pull-warrant-filter.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
