创建讨论回复
======

在指定讨论下发布回复，支持嵌套回复已有回复。尝试一下

仅限 **Longbridge 开户且持有资产** 的用户才允许通过 Longbridge Developers 的 API 或 CLI 发布社区讨论和回复。否则返回 `403`。

**正文格式：** 仅支持纯文本，不支持 HTML 或 Markdown。

正文中提到的标的代码（如 \`700.HK\`、\`TSLA.US\`）会被平台自动识别并关联为相关标的。

⚠️ 请勿滥用此功能关联与内容无关的标的，否则后台内容运营可能会限制发布，甚至有可能禁言。

**频率限制：** 同一用户在同一讨论下，前 3 条无间隔限制；此后每条须与上一条保持递增间隔（3 s → 5 s → 8 s → 13 s → 21 s → 34 s → 55 s 封顶），超出限制返回 `429`。

> ⚠️ 以上频率限制规则仅供参考，平台可能随时进行内部调整。

CLI[安装 CLI](./cli.md)

--------------------------------------------------------

bash

    longbridge create-topic-reply 6993508780031016960 --body "分析得很好！"

SDK Links
---------

|     |     |
| --- | --- |
| Python | [longbridge.openapi.ContentContext.create\_topic\_reply](https://longbridge.github.io/openapi/python/reference_all/#longbridge.openapi.ContentContext.create_topic_reply) |
| Rust | [longbridge::content::ContentContext#create\_topic\_reply](https://longbridge.github.io/openapi/rust/longbridge/content/struct.ContentContext.html#method.create_topic_reply) |
| Go  | [ContentContext.CreateTopicReply](https://pkg.go.dev/github.com/longbridge/openapi-go/content#ContentContext.CreateTopicReply) |
| Node.js | [ContentContext#createTopicReply](https://longbridge.github.io/openapi/nodejs/classes/ContentContext.html#createtopicreply) |
| Java | [ContentContext.getCreateTopicReply](https://longbridge.github.io/openapi/java/com/longbridge/content/ContentContext.html#getCreateTopicReply) |
| C++ | [longbridge::content::ContentContext::create\_topic\_reply](https://longbridge.github.io/openapi/cpp/classlongbridge_1_1content_1_1_content_context.html) |

Request [​](./content-create-topic-reply.md#request)

---------------------------------------------------------------------------------------

|     |     |
| --- | --- |
| HTTP Method | POST |
| HTTP URL | /v1/content/topics/:topic\_id/comments |

### Path Parameters [​](./content-create-topic-reply.md#path-parameters)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| topic\_id | string | YES | 讨论 ID，如 `6993508780031016960` |

### Request Body [​](./content-create-topic-reply.md#request-body)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| body | string | YES | 回复正文，仅支持纯文本。正文中提到的标的代码会被平台自动识别并关联。 |
| reply\_to\_id | string | NO  | 被回复的回复 ID；不填或填 `"0"` 表示发顶层回复，填入有效 ID 则嵌套在该回复下。 |

### Request Example [​](./content-create-topic-reply.md#request-example)

CLIPythonPython (async)GoRust

bash

    # 顶层回复
    longbridge create-topic-reply 6993508780031016960 --body "分析得很好！"
    
    # 嵌套回复
    longbridge create-topic-reply 6993508780031016960 --body "同意你的观点。" --reply-to 7001234567890123456

python

    from longbridge.openapi import ContentContext, Config, OAuthBuilder
    
    oauth = OAuthBuilder("your-client-id").build(lambda url: print("Visit:", url))
    config = Config.from_oauth(oauth)
    ctx = ContentContext(config)
    
    # 顶层回复
    reply = ctx.create_topic_reply("6993508780031016960", body="分析得很好！")
    print(reply.id)
    
    # 嵌套回复
    nested = ctx.create_topic_reply(
        "6993508780031016960",
        body="同意你的观点。",
        reply_to_id="7001234567890123456",
    )
    print(nested.id)

python

    import asyncio
    from longbridge.openapi import AsyncContentContext, Config, OAuthBuilder
    
    async def main() -> None:
        oauth = await OAuthBuilder("your-client-id").build_async(lambda url: print("Visit:", url))
        config = Config.from_oauth(oauth)
        ctx = AsyncContentContext.create(config)
    
        reply = await ctx.create_topic_reply("6993508780031016960", body="分析得很好！")
        print(reply.id)
    
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
    	// 顶层回复
    	reply, err := ctx.CreateTopicReply(context.Background(), "6993508780031016960",
    		&content.CreateReplyOptions{Body: "分析得很好！"},
    	)
    	if err != nil {
    		log.Fatal(err)
    	}
    	fmt.Println("reply id:", reply.ID)
    }

rust

    use std::sync::Arc;
    use longbridge::{oauth::OAuthBuilder, content::{ContentContext, CreateReplyOptions}, Config};
    
    #[tokio::main]
    async fn main() -> Result<(), Box<dyn std::error::Error>> {
        let oauth = OAuthBuilder::new("your-client-id").build(|url| println!("Open: {url}")).await?;
        let config = Arc::new(Config::from_oauth(oauth));
        let ctx = ContentContext::new(config);
    
        // 顶层回复
        let reply = ctx.create_topic_reply(
            "6993508780031016960",
            CreateReplyOptions { body: "分析得很好！".to_string(), reply_to_id: None },
        ).await?;
        println!("reply id: {}", reply.id);
        Ok(())
    }

Response [​](./content-create-topic-reply.md#response)

-----------------------------------------------------------------------------------------

### Response Headers [​](./content-create-topic-reply.md#response-headers)

*   Content-Type: application/json

### Response Example [​](./content-create-topic-reply.md#response-example)

json

    {
      "code": 0,
      "message": "success",
      "data": {
        "item": {
          "id": "7001234567890123460",
          "topic_id": "6993508780031016960",
          "body": "分析得很好！",
          "reply_to_id": "0",
          "author": {
            "member_id": "10086",
            "name": "张三",
            "avatar": "https://example.com/avatar.jpg"
          },
          "images": [],
          "likes_count": 0,
          "comments_count": 0,
          "created_at": "1742002000"
        }
      }
    }

### Response Status [​](./content-create-topic-reply.md#response-status)

| Status | Description | Schema |
| --- | --- | --- |
| 200 | 返回成功 | [create\_reply\_response](./content-create-topic-reply.md#schemacreate-reply-response) |
| 403 | 权限不足 | 用户未开户或无资产 |
| 429 | 频率超限 | 超过同讨论下的发帖频率限制，请等待后重试 |
| 500 | 内部错误 | None |

Schemas [​](./content-create-topic-reply.md#schemas)

---------------------------------------------------------------------------------------

### create\_reply\_response [​](./content-create-topic-reply.md#create-reply-response)

| Name | Type | Required | Description |
| --- | --- | --- | --- |
| item | object | true | 新建回复详情 |
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

[LLMs Text](https://open.longbridge.com/docs/content/create-topic-reply.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/content/create_topic_reply.md)

最后更新于:

Pager

[上一页获取讨论回复列表](./content-topic-replies.md)

[下一页通用问题](./qa-general.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
