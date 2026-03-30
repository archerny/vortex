获取标的资讯
======

获取指定股票的资讯列表。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Tesla 最新资讯
    longbridge news TSLA.US
    # Apple 最新资讯
    longbridge news AAPL.US
    # NVDA 最新资讯
    longbridge news NVDA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.ContentContext.news](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.ContentContext.news) |
| Rust | [longbridge::content::ContentContext#news](https://longbridge.github.io/openapi/rust/longbridge/content/struct.ContentContext.html#method.news) |
| Go  | [ContentContext.News](https://pkg.go.dev/github.com/longbridge/openapi-go/content#ContentContext.News) |
| Node.js | [ContentContext#news](https://longbridge.github.io/openapi/nodejs/classes/ContentContext.html#news) |
| Java | [ContentContext.getNews](https://longbridge.github.io/openapi/java/com/longbridge/content/ContentContext.html#getNews) |
| C++ | [longbridge::content::ContentContext::news](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1content_1_1_content_context.html) |

Request [​](./content-news.md#request)

-------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/content/{symbol}/news |

### Path Parameters [​](./content-news.md#path-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | YES | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |

### Request Example [​](./content-news.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import ContentContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = ContentContext(config)
    
    resp = ctx.news("AAPL.US")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncContentContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncContentContext.create(config)
    
        resp = await ctx.news("AAPL.US")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, ContentContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = ContentContext.new(config)
      const resp = await ctx.news("AAPL.US")
      console.log(resp)
    }
    main().catch(console.error)

java

    import com.longbridge.*;
    import com.longbridge.content.*;
    
    class Main {
        public static void main(String[] args) throws Exception {
            try (OAuth oauth = new OAuthBuilder("your-client-id").build(url -> System.out.println("Open to authorize: " + url)).get();
                 Config config = Config.fromOAuth(oauth);
                 ContentContext ctx = ContentContext.create(config)) {
                NewsItem[] resp = ctx.getNews("AAPL.US").get();
                for (NewsItem item : resp) System.out.println(item);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, content::ContentContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let ctx = ContentContext::new(config);
        let resp = ctx.news("AAPL.US").await?;
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
    using namespace longbridge::content;
    
    static void
    run(const OAuth& oauth)
    {
        Config config = Config::from_oauth(oauth);
        ContentContext ctx = ContentContext::create(config);
    
        ctx.news("AAPL.US", [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "news: " << res->size() << std::endl;
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
    	"github.com/longbridge/openapi-go/content"
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
    	ctx, err := content.NewFromCfg(conf)
    	if err != nil {
    		log.Fatal(err)
    	}
    	items, err := ctx.News(context.Background(), "AAPL.US")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("news:", len(items))
    }

Response [​](./content-news.md#response)

---------------------------------------------------------------------------

### Response Headers [​](./content-news.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./content-news.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "items": [\
          {\
            "id": "279528757",\
            "title": "Beats 跨界联动耐克破圈！苹果欲再掀可穿戴消费热潮 耐克押注 "运动科技" 叙事",\
            "description": "苹果公司旗下的 Beats 与耐克合作推出限量版 Powerbeats Pro 2 耳机，耳机上印有耐克的 Swoosh 标志。该耳机将于 3 月 20 日在线及部分 Apple Store 发售，售价为 250 美元。这是 Beats 首次与外部运动品牌合作，标志着两家公司在品牌和产品生态上的进一步协同。耳机具备实时心率追踪功能，续航时间最长可达 45 小时。",\
            "url": "https://longbridge.com/news/279528757",\
            "published_at": "1773805586",\
            "comments_count": 0,\
            "likes_count": 0,\
            "shares_count": 0\
          }\
        ]
      }
    }

### Response Status [​](./content-news.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [news\_response](./content-news.md#schemanews-response) |
| 500 | 内部错误 | None |

Schemas [​](./content-news.md#schemas)

-------------------------------------------------------------------------

### news\_response [​](./content-news.md#news-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| items | object\[\] | true | 资讯列表 |
| ∟ id | string | true | 资讯 ID |
| ∟ title | string | true | 标题  |
| ∟ description | string | true | 摘要/描述 |
| ∟ url | string | true | 资讯详情链接 |
| ∟ published\_at | string | true | 发布时间，Unix 时间戳（秒） |
| ∟ comments\_count | int32 | true | 评论数 |
| ∟ likes\_count | int32 | true | 点赞数 |
| ∟ shares\_count | int32 | true | 分享数 |

[LLMs Text](https://open.longbridge.com/docs/content/news.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/content/security_news.md)

最后更新于:

Pager

[上一页获取股票持仓](./trade-asset-stock.md)

[下一页获取标的社区讨论](./content-topics.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
