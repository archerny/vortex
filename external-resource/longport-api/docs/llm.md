LLM 组件 [​](./llm.md#llm-%E7%BB%84%E4%BB%B6)

==============================================================================

我们提供了一些用于 LLM（大型语言模型）的组件，您可以轻松访问和分析金融数据、实时市场数据，甚至可以让 AI 提交订单。

是的，您可以通过 Longbridge Developers 使用我们的 LLM 组件，今天就开始吧！

LLMs 文本 [​](./llm.md#llms-%E6%96%87%E6%9C%AC)

--------------------------------------------------------------------------------

OpenAPI 文档遵循 [LLMs 文本](https://llmstxt.org/)
 提供 [llms.txt](https://open.longbridge.com/llms.txt)
 以及每个文档的 Markdown 文件，基于这个 LLMs 文本，你可以为 AI 提供 Longbridge Developers 完整的文档字典作为 AI 辅助生成开发的参考信息，这样 AI 能生成出来的代码可以更准确。

*   [https://open.longbridge.com/llms.txt](https://open.longbridge.com/llms.txt)
     - 大约 2104 个 token。

我们的每个文档也都提供 Markdown 格式，当您访问它们时，只需在 URL 后添加 `.md` 后缀。

例如：

*   [https://open.longbridge.com/docs/getting-started.md](https://open.longbridge.com/docs/getting-started.md)
    
*   [https://open.longbridge.com/docs/quote/pull/static.md](https://open.longbridge.com/docs/quote/pull/static.md)
    

longbridge.com 页面 Markdown 获取（AI 友好） [​](./llm.md#longbridge-com-%E9%A1%B5%E9%9D%A2-markdown-%E8%8E%B7%E5%8F%96-ai-%E5%8F%8B%E5%A5%BD)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

除了 OpenAPI 文档， [https://longbridge.com](https://longbridge.com/)
 的页面也支持面向 AI 的 Markdown 获取。

你可以通过两种方式获取页面 Markdown：

1.  在页面 URL 后追加 `.md`
2.  请求时带上 Header：`Accept: text/markdown`

示例：

*   `https://longbridge.com/en/pricing.md`
*   `curl -H "Accept: text/markdown" https://longbridge.com/quote/TSLA.US`

这个能力适合 LLM 抓取、RAG 建索引、以及工具化读取页面内容，并且能保持页面结构清晰。

### 演示 [​](./llm.md#%E6%BC%94%E7%A4%BA)

### Cursor 内使用 [​](./llm.md#cursor-%E5%86%85%E4%BD%BF%E7%94%A8)

打开 Cursor，打开命令面板（`Command + Shift + P`）搜索并选择 **Add New Custom Docs**，并在出来的对话框中输入 Longbridge Developers 的 LLMs Text 地址：

    https://open.longbridge.com/llms.txt

添加成功后，Cursor Settings 里面会是这样：

![](https://assets.lbkrs.com/uploads/5d5d037f-d8fb-42ed-aa5e-6c59bd65d066/scr-20250423-qrgl.png)

接下来你可以在 AI 的会话中，**@Add Context** 的 `docs` 菜单下选择刚才添加的 Docs，这样接下来与 AI 的会话中，AI 将会使用这些文档作为上下文。

![](https://assets.lbkrs.com/uploads/4c3c37d5-ead7-4854-8c8d-e8e77cdcd967/scr-20250423-qoxl.png)

[LLMs Text](https://open.longbridge.com/docs/llm.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/llm.md)

最后更新于:

Pager

[上一页CLI](./cli.md)

[下一页概览](./quote-overview.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
