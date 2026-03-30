获取我发布的讨论
========

获取当前登录用户发布的讨论列表，支持分页与类型过滤。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge my-topics

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.ContentContext.topics\_mine](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.ContentContext.topics_mine) |
| Rust | [longbridge::content::ContentContext#topics\_mine](https://longbridge.github.io/openapi/rust/longbridge/content/struct.ContentContext.html#method.topics_mine) |
| Go  | [ContentContext.TopicsMine](https://pkg.go.dev/github.com/longbridge/openapi-go/content#ContentContext.TopicsMine) |
| Node.js | [ContentContext#topicsMine](https://longbridge.github.io/openapi/nodejs/classes/ContentContext.html#topicsmine) |
| Java | [ContentContext.getTopicsMine](https://longbridge.github.io/openapi/java/com/longbridge/content/ContentContext.html#getTopicsMine) |
| C++ | [longbridge::content::ContentContext::topics\_mine](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1content_1_1_content_context.html) |

Request [​](./content-my-topics.md#request)

------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/content/topics/mine |

### Query Parameters [​](./content-my-topics.md#query-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| page | int32 | NO  | 页码，默认 1 |
| size | int32 | NO  | 每页数量，范围 1~500，默认 50 |
| topic\_type | string | NO  | 类型过滤，可选 `article`（长文）、`post`（短帖），不传返回全部 |

### Request Example [​](./content-my-topics.md#request-example)

CLIPythonPython (async)Node.jsJavaRustC++Go

bash

    longbridge my-topics                           # 全部类型（默认每页 50 条）
    longbridge my-topics --type article            # 仅长文
    longbridge my-topics --type post --size 10     # 短帖，每页 10 条
    longbridge my-topics --page 2                  # 第二页
    longbridge my-topics --format json             # JSON 格式，适合脚本处理

python

    from longbridge.openapi import ContentContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = ContentContext(config)
    
    resp = ctx.topics_mine(page=1, size=50, topic_type="article")
    print(resp)

python

    import asyncio
    from longbridge.openapi import AsyncContentContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncContentContext.create(config)
    
        resp = await ctx.topics_mine(page=1, size=50, topic_type="article")
        print(resp)
    
    if __name__ == "__main__":
        asyncio.run(main())

javascript

    const { Config, ContentContext, OAuth } = require('longbridge')
    
    async function main() {
      const oauth = await OAuth.build("your-client-id", (_, url) => { console.log("Open this URL to authorize: " + url) })
      const config = Config.fromOAuth(oauth)
      const ctx = ContentContext.new(config)
      const resp = await ctx.topicsMine({ page: 1, size: 50, topicType: "article" })
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
                ListMyTopicsOptions opts = new ListMyTopicsOptions()
                    .setPage(1).setSize(50).setTopicType("article");
                OwnedTopic[] resp = ctx.getTopicsMine(opts).get();
                for (OwnedTopic item : resp) System.out.println(item);
            }
        }
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, content::{ContentContext, ListMyTopicsOptions}, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open this URL to authorize: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let ctx = ContentContext::new(config);
        let opts = ListMyTopicsOptions {
            page: Some(1),
            size: Some(50),
            topic_type: Some("article".to_string()),
        };
        let resp = ctx.topics_mine(opts).await?;
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
    
        ListMyTopicsOptions opts;
        opts.page = 1;
        opts.size = 50;
        opts.topic_type = "article";
    
        ctx.topics_mine(opts, [](auto res) {
            if (!res) { std::cout << "failed: " << *res.status().message() << std::endl; return; }
            std::cout << "my topics: " << res->size() << std::endl;
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
    	opts := content.ListMyTopicsOptions{Page: 1, Size: 50, TopicType: "article"}
    	items, err := ctx.TopicsMine(context.Background(), opts)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("my topics:", len(items))
    }

Response [​](./content-my-topics.md#response)

--------------------------------------------------------------------------------

### Response Headers [​](./content-my-topics.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./content-my-topics.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "items": [\
          {\
            "id": "39304657",\
            "title": "我对苹果的分析",\
            "description": "文章摘要...",\
            "body": "Markdown 正文内容...",\
            "topic_type": "article",\
            "tickers": ["AAPL.US"],\
            "hashtags": ["earnings"],\
            "images": [],\
            "likes_count": 12,\
            "comments_count": 3,\
            "views_count": 200,\
            "shares_count": 1,\
            "license": 1,\
            "detail_url": "https://longbridge.com/topics/39304657",\
            "author": {\
              "member_id": "10086",\
              "name": "张三",\
              "avatar": "https://example.com/avatar.jpg"\
            },\
            "created_at": "1742000000",\
            "updated_at": "1742000000"\
          }\
        ]
      }
    }

### Response Status [​](./content-my-topics.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [my\_topics\_response](./content-my-topics.md#schemamy-topics-response) |
| 500 | 内部错误 | None |

Schemas [​](./content-my-topics.md#schemas)

------------------------------------------------------------------------------

### my\_topics\_response [​](./content-my-topics.md#my-topics-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| items | object\[\] | true | 讨论列表 |
| ∟ id | string | true | 讨论 ID |
| ∟ title | string | false | 标题（短帖可能为空） |
| ∟ description | string | false | 纯文本摘要 |
| ∟ body | string | false | Markdown 格式正文 |
| ∟ topic\_type | string | true | 内容类型，`article`（长文）或 `post`（短帖） |
| ∟ tickers | string\[\] | false | 关联标的代码，如 `["AAPL.US", "700.HK"]` |
| ∟ hashtags | string\[\] | false | 讨论标签名称列表 |
| ∟ images | object\[\] | false | 附图列表 |
| ∟∟ url | string | false | 原始图片 URL |
| ∟∟ sm | string | false | 小缩略图 URL |
| ∟∟ lg | string | false | 大缩略图 URL |
| ∟ likes\_count | int32 | false | 点赞数 |
| ∟ comments\_count | int32 | false | 评论数 |
| ∟ views\_count | int32 | false | 浏览数 |
| ∟ shares\_count | int32 | false | 分享数 |
| ∟ license | int32 | false | 版权声明，`0`\=无声明，`1`\=原创，`2`\=非原创 |
| ∟ detail\_url | string | false | 讨论详情页链接 |
| ∟ author | object | false | 作者信息 |
| ∟∟ member\_id | string | false | 作者 member ID |
| ∟∟ name | string | false | 作者昵称 |
| ∟∟ avatar | string | false | 作者头像 URL |
| ∟ created\_at | string | true | 创建时间，Unix 时间戳（秒） |
| ∟ updated\_at | string | false | 最后更新时间，Unix 时间戳（秒） |

[LLMs Text](https://open.longbridge.com/docs/content/my-topics.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/content/my_topics.md)

最后更新于:

Pager

[上一页获取标的社区讨论](./content-topics.md)

[下一页创建讨论](./content-create-topic.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
