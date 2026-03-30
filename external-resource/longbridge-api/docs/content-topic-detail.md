获取讨论详情
======

根据讨论 ID 获取完整详情，包含正文（Markdown）、作者信息、关联标的与标签、互动数据及详情页链接。尝试一下

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge topic-detail 6993508780031016960

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.ContentContext.topic\_detail](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.ContentContext.topic_detail) |
| Rust | [longbridge::content::ContentContext#topic\_detail](https://longbridge.github.io/openapi/rust/longbridge/content/struct.ContentContext.html#method.topic_detail) |
| Go  | [ContentContext.TopicDetail](https://pkg.go.dev/github.com/longbridge/openapi-go/content#ContentContext.TopicDetail) |
| Node.js | [ContentContext#topicDetail](https://longbridge.github.io/openapi/nodejs/classes/ContentContext.html#topicdetail) |
| Java | [ContentContext.getTopicDetail](https://longbridge.github.io/openapi/java/com/longbridge/content/ContentContext.html#getTopicDetail) |
| C++ | [longbridge::content::ContentContext::topic\_detail](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1content_1_1_content_context.html) |

Request [​](./content-topic-detail.md#request)

---------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/content/topics/:id |

### Path Parameters [​](./content-topic-detail.md#path-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| id  | string | YES | 讨论 ID，如 `6993508780031016960` |

### Request Example [​](./content-topic-detail.md#request-example)

CLIPythonPython (async)GoRust

bash

    longbridge topic-detail 6993508780031016960

python

    from longbridge.openapi import ContentContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = ContentContext(config)
    
    topic = ctx.topic_detail("6993508780031016960")
    print(topic)

python

    import asyncio
    from longbridge.openapi import AsyncContentContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncContentContext.create(config)
    
        topic = await ctx.topic_detail("6993508780031016960")
        print(topic)
    
    if __name__ == "__main__":
        asyncio.run(main())

go

    package main
    
    import (
    	"context"
    	"fmt"
    	"log"
    
    	"github.com/longportapp/openapi-go/config"
    	"github.com/longportapp/openapi-go/content"
    )
    
    func main() {
    	conf, err := config.NewFromEnv()
    	if err != nil {
    		log.Fatal(err)
    	}
    	ctx, err := content.NewFromCfg(conf)
    	if err != nil {
    		log.Fatal(err)
    	}
    	topic, err := ctx.TopicDetail(context.Background(), "6993508780031016960")
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Printf("title: %s\nauthor: %s\nlikes: %d\n", topic.Title, topic.Author.Name, topic.LikesCount)
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, content::ContentContext, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let ctx = ContentContext::new(config);
        let topic = ctx.topic_detail("6993508780031016960").await?;
        println!("{:?}", topic);
        Ok(())
    }

Response [​](./content-topic-detail.md#response)

-----------------------------------------------------------------------------------

### Response Headers [​](./content-topic-detail.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./content-topic-detail.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "item": {
          "id": "6993508780031016960",
          "title": "我对苹果的分析",
          "description": "文章摘要...",
          "body": "**看多** AAPL，因为...",
          "topic_type": "article",
          "tickers": ["AAPL.US"],
          "hashtags": ["earnings"],
          "images": [\
            {\
              "url": "https://cdn.longbridge.com/img/abc.jpg",\
              "sm": "https://cdn.longbridge.com/img/abc_sm.jpg",\
              "lg": "https://cdn.longbridge.com/img/abc_lg.jpg"\
            }\
          ],
          "likes_count": 42,
          "comments_count": 7,
          "views_count": 1500,
          "shares_count": 3,
          "detail_url": "https://longbridge.com/topics/6993508780031016960",
          "author": {
            "member_id": "10086",
            "name": "张三",
            "avatar": "https://example.com/avatar.jpg"
          },
          "created_at": "1742000000",
          "updated_at": "1742001000"
        }
      }
    }

### Response Status [​](./content-topic-detail.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [topic\_detail\_response](./content-topic-detail.md#schematopic-detail-response) |
| 500 | 内部错误 | None |

Schemas [​](./content-topic-detail.md#schemas)

---------------------------------------------------------------------------------

### topic\_detail\_response [​](./content-topic-detail.md#topic-detail-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| item | object | true | 讨论详情 |
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
| ∟ comments\_count | int32 | false | 回复数 |
| ∟ views\_count | int32 | false | 浏览数 |
| ∟ shares\_count | int32 | false | 分享数 |
| ∟ detail\_url | string | false | 讨论详情页链接 |
| ∟ author | object | false | 作者信息 |
| ∟∟ member\_id | string | false | 作者 member ID |
| ∟∟ name | string | false | 作者昵称 |
| ∟∟ avatar | string | false | 作者头像 URL |
| ∟ created\_at | string | true | 创建时间，Unix 时间戳（秒） |
| ∟ updated\_at | string | false | 最后更新时间，Unix 时间戳（秒） |

[LLMs Text](https://open.longbridge.com/docs/content/topic-detail.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/content/topic_detail.md)

最后更新于:

Pager

[上一页创建讨论](./content-create-topic.md)

[下一页获取讨论回复列表](./content-topic-replies.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
