获取讨论回复列表
========

获取指定讨论下的回复列表，支持分页。尝试一下

每条回复包含作者信息、正文（纯文本）、互动数据及 `reply_to_id` 字段：`"0"` 表示顶层回复，其他值表示对指定回复的嵌套回复。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge topic-replies 6993508780031016960
    longbridge topic-replies 6993508780031016960 --page 2 --size 20

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.ContentContext.list\_topic\_replies](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.ContentContext.list_topic_replies) |
| Rust | [longbridge::content::ContentContext#list\_topic\_replies](https://longbridge.github.io/openapi/rust/longbridge/content/struct.ContentContext.html#method.list_topic_replies) |
| Go  | [ContentContext.ListTopicReplies](https://pkg.go.dev/github.com/longbridge/openapi-go/content#ContentContext.ListTopicReplies) |
| Node.js | [ContentContext#listTopicReplies](https://longbridge.github.io/openapi/nodejs/classes/ContentContext.html#listtopicreplies) |
| Java | [ContentContext.getListTopicReplies](https://longbridge.github.io/openapi/java/com/longbridge/content/ContentContext.html#getListTopicReplies) |
| C++ | [longbridge::content::ContentContext::list\_topic\_replies](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1content_1_1_content_context.html) |

Request [​](./content-topic-replies.md#request)

----------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | GET |
| HTTP URL | /v1/content/topics/:topic\_id/comments |

### Path Parameters [​](./content-topic-replies.md#path-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| topic\_id | string | YES | 讨论 ID，如 `6993508780031016960` |

### Query Parameters [​](./content-topic-replies.md#query-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| page | int32 | NO  | 页码，默认 1 |
| size | int32 | NO  | 每页数量，范围 1~50，默认 20 |

### Request Example [​](./content-topic-replies.md#request-example)

CLIPythonPython (async)GoRust

bash

    longbridge topic-replies 6993508780031016960
    longbridge topic-replies 6993508780031016960 --page 2 --size 20

python

    from longbridge.openapi import ContentContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = ContentContext(config)
    
    replies = ctx.list_topic_replies("6993508780031016960", page=1, size=20)
    for r in replies:
        print(r.author.name, r.body)

python

    import asyncio
    from longbridge.openapi import AsyncContentContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncContentContext.create(config)
    
        replies = await ctx.list_topic_replies("6993508780031016960", page=1, size=20)
        for r in replies:
            print(r.author.name, r.body)
    
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
    	replies, err := ctx.ListTopicReplies(context.Background(), "6993508780031016960",
    		&content.ListTopicRepliesOptions{Page: 1, Size: 20},
    	)
    	if err != nil {
    		log.Fatal(err)
    	}
    	for _, r := range replies {
    		fmt.Printf("[%s] %s: %s\n", r.ID, r.Author.Name, r.Body)
    	}
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, content::{ContentContext, ListTopicRepliesOptions}, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let ctx = ContentContext::new(config);
        let replies = ctx.list_topic_replies(
            "6993508780031016960",
            ListTopicRepliesOptions { page: Some(1), size: Some(20) },
        ).await?;
        for r in &replies {
            println!("{}: {}", r.author.name, r.body);
        }
        Ok(())
    }

Response [​](./content-topic-replies.md#response)

------------------------------------------------------------------------------------

### Response Headers [​](./content-topic-replies.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./content-topic-replies.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "items": [\
          {\
            "id": "7001234567890123456",\
            "topic_id": "6993508780031016960",\
            "body": "分析得很到位！",\
            "reply_to_id": "0",\
            "author": {\
              "member_id": "10087",\
              "name": "李四",\
              "avatar": "https://example.com/avatar2.jpg"\
            },\
            "images": [],\
            "likes_count": 5,\
            "comments_count": 2,\
            "created_at": "1742001500"\
          },\
          {\
            "id": "7001234567890123457",\
            "topic_id": "6993508780031016960",\
            "body": "估值部分我有不同看法。",\
            "reply_to_id": "7001234567890123456",\
            "author": {\
              "member_id": "10088",\
              "name": "王五",\
              "avatar": "https://example.com/avatar3.jpg"\
            },\
            "images": [],\
            "likes_count": 1,\
            "comments_count": 0,\
            "created_at": "1742001800"\
          }\
        ]
      }
    }

### Response Status [​](./content-topic-replies.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [topic\_replies\_response](./content-topic-replies.md#schematopic-replies-response) |
| 500 | 内部错误 | None |

Schemas [​](./content-topic-replies.md#schemas)

----------------------------------------------------------------------------------

### topic\_replies\_response [​](./content-topic-replies.md#topic-replies-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| items | object\[\] | true | 回复列表 |
| ∟ id | string | true | 回复 ID |
| ∟ topic\_id | string | true | 所属讨论 ID |
| ∟ body | string | false | 回复正文（纯文本） |
| ∟ reply\_to\_id | string | false | 父回复 ID，`"0"` 表示顶层回复 |
| ∟ author | object | false | 作者信息 |
| ∟∟ member\_id | string | false | 作者 member ID |
| ∟∟ name | string | false | 作者昵称 |
| ∟∟ avatar | string | false | 作者头像 URL |
| ∟ images | object\[\] | false | 附图列表 |
| ∟∟ url | string | false | 原始图片 URL |
| ∟∟ sm | string | false | 小缩略图 URL |
| ∟∟ lg | string | false | 大缩略图 URL |
| ∟ likes\_count | int32 | false | 点赞数 |
| ∟ comments\_count | int32 | false | 嵌套回复数 |
| ∟ created\_at | string | true | 创建时间，Unix 时间戳（秒） |

[LLMs Text](https://open.longbridge.com/docs/content/topic-replies.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/content/topic_replies.md)

最后更新于:

Pager

[上一页获取讨论详情](./content-topic-detail.md)

[下一页创建讨论回复](./content-create-topic-reply.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
