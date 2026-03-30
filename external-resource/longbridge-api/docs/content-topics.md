获取标的社区讨论
========

获取指定股票的讨论列表。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    # Tesla 社区讨论帖子
    longbridge topics TSLA.US
    # Apple 社区讨论帖子
    longbridge topics AAPL.US
    # NVDA 社区讨论帖子
    longbridge topics NVDA.US

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.ContentContext.topics](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.ContentContext.topics) |
| Rust | [longbridge::content::ContentContext#topics](https://longbridge.github.io/openapi/rust/longbridge/content/struct.ContentContext.html#method.topics) |
| Go  | [ContentContext.Topics](https://pkg.go.dev/github.com/longbridge/openapi-go/content#ContentContext.Topics) |
| Node.js | [ContentContext#topics](https://longbridge.github.io/openapi/nodejs/classes/ContentContext.html#topics) |
| Java | [ContentContext.getTopics](https://longbridge.github.io/openapi/java/com/longbridge/content/ContentContext.html#getTopics) |
| C++ | [longbridge::content::ContentContext::topics](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1content_1_1_content_context.html) |

Request [​](./content-topics.md#request)

---------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/content/{symbol}/topics |

### Path Parameters [​](./content-topics.md#path-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| symbol | string | YES | 股票代码，使用 `ticker.region` 格式，例如：`AAPL.US` |

### Request Example [​](./content-topics.md#request-example)

PythonPython (async)Node.jsJavaRustC++Go

python

    from longbridge.openapi import ContentContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = ContentContext(config)
    
    resp = ctx.topics("AAPL.US")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncContentContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncContentContext.create(config)
    
        resp = await ctx.topics("AAPL.US")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, ContentContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = ContentContext.new(config)
      const resp = await ctx.topics("AAPL.US")
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
                TopicItem[] resp = ctx.getTopics("AAPL.US").get();
                for (TopicItem item : resp) System.out.println(item);
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
        let resp = ctx.topics("AAPL.US").await?;
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
    
        ctx.topics("AAPL.US", [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "topics: " << res->size() << std::endl;
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
    	items, err := ctx.Topics(context.Background(), "AAPL.US")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("topics:", len(items))
    }

Response [​](./content-topics.md#response)

-----------------------------------------------------------------------------

### Response Headers [​](./content-topics.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./content-topics.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "items": [\
          {\
            "id": "39304657",\
            "title": "英伟达 GTC 备受关注；阿里 "Token 战略" 再加码｜今日重要消息回顾",\
            "description": "0317 ｜海豚君重点关注：🐬 个股 1、[st]ST/US/NVDA#英伟达.US[/st] 英伟达 GTC 2026 大会正式开幕，英伟达创始人兼 CEO 黄仁勋发表了主题演讲。宣布，其下一代 Vera Rubin 架构将推出专为空间轨道数据中心设计的 Vera Rubin Space Module，性能比 H100 提升 25 倍。同时宣布与 Groq 合作开发新型 LPU 芯片...",\
            "url": "https://longbridge.com/topics/39304657",\
            "published_at": "1773736144",\
            "comments_count": 1,\
            "likes_count": 7,\
            "shares_count": 4\
          }\
        ]
      }
    }

### Response Status [​](./content-topics.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [topics\_response](./content-topics.md#schematopics-response) |
| 500 | 内部错误 | None |

Schemas [​](./content-topics.md#schemas)

---------------------------------------------------------------------------

### topics\_response [​](./content-topics.md#topics-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| items | object\[\] | true | 讨论列表 |
| ∟ id | string | true | 讨论 ID |
| ∟ title | string | true | 标题  |
| ∟ description | string | true | 摘要/描述 |
| ∟ url | string | true | 讨论详情链接 |
| ∟ published\_at | string | true | 发布时间，Unix 时间戳（秒） |
| ∟ comments\_count | int32 | true | 评论数 |
| ∟ likes\_count | int32 | true | 点赞数 |
| ∟ shares\_count | int32 | true | 分享数 |

[LLMs Text](https://open.longbridge.com/docs/content/topics.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/content/security_topics.md)

最后更新于:

Pager

[上一页获取标的资讯](./content-news.md)

[下一页获取我发布的讨论](./content-my-topics.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
