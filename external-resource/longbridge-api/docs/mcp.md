Longbridge MCP 服务 [​](./mcp.md#longbridge-mcp-%E6%9C%8D%E5%8A%A1)

====================================================================================================

Longbridge 提供托管的 MCP（Model Context Protocol）服务，让你在 AI 编程助手或对话工具中直接使用 Longbridge 的行情与账户能力，无需手动管理 API 密钥。

**MCP 服务地址：** `https://openapi.longbridge.com/mcp`

前置条件 [​](./mcp.md#%E5%89%8D%E7%BD%AE%E6%9D%A1%E4%BB%B6)

------------------------------------------------------------------------------------------

*   已拥有 Longbridge 账户并完成开户
*   使用支持 MCP OAuth 2.1 的 AI 客户端（见下方兼容性说明）

可用能力 [​](./mcp.md#%E5%8F%AF%E7%94%A8%E8%83%BD%E5%8A%9B)

------------------------------------------------------------------------------------------

接入后，MCP 客户端可调用以下能力：

| 能力类别 | 说明  |
| --- | --- |
| 行情数据 | 实时快照、K 线、历史行情查询 |
| 账户信息 | 账户概览、资产、持仓查询 |
| 交易操作 | 下单、改单、撤单（受账户权限与地区限制） |

实际可用能力因地区、账户等级和授权范围而有所不同。

客户端接入 [​](./mcp.md#%E5%AE%A2%E6%88%B7%E7%AB%AF%E6%8E%A5%E5%85%A5)

----------------------------------------------------------------------------------------------------

> 各客户端的 MCP 配置格式可能随版本变更，以客户端官方文档为准。以下提供核心配置参数。

在支持 MCP 的客户端中，以 Remote MCP Server 方式添加如下配置：

json

    {
      "mcpServers": {
        "longbridge": {
          "url": "https://openapi.longbridge.com/mcp"
        }
      }
    }

各主流客户端的配置入口：

*   **Cursor**：Settings → MCP Servers → 添加 Remote MCP Server
*   **Claude Code**：MCP 配置文件或 `claude mcp add` 命令
*   **ChatGPT**：Settings → Connectors（或工作区 MCP 配置入口）
*   **Zed**：`settings.json` 中的 `context_servers` 字段（key 名称可自定义）
*   **Cherry Studio**：设置 → MCP 服务器 → 添加

配置完成后，客户端会自动引导你完成 OAuth 授权流程。

OAuth 授权流程 [​](./mcp.md#oauth-%E6%8E%88%E6%9D%83%E6%B5%81%E7%A8%8B)

------------------------------------------------------------------------------------------------------

Longbridge MCP 使用标准 OAuth 2.1 授权，你无需向客户端提供 API 密钥或 Token。

    AI 客户端                  浏览器                    Longbridge
        |                        |                           |
        |--- 发起 MCP 连接 ------>|                           |
        |                        |-- 跳转授权页 ------------>|
        |                        |<- 展示登录 & 权限确认 ----|
        |                        |-- 登录并同意 ------------>|
        |<-- 返回授权凭证 --------|                           |
        |--- 携带凭证访问工具 ----------------------------------->|

**步骤说明：**

1.  **发起连接** — 在客户端添加 Longbridge MCP 配置后，首次调用会触发授权
2.  **浏览器跳转** — 客户端自动打开浏览器，进入 Longbridge 登录与权限确认页
3.  **登录并授权** — 使用 Longbridge 账户登录，查看并同意所请求的权限范围（scope）
4.  **建立会话** — 授权完成后，客户端获得凭证，MCP 工具即可使用
5.  **凭证维护** — 凭证按 OAuth 策略自动刷新；如需撤销，前往 Longbridge 账户安全设置

客户端兼容性 [​](./mcp.md#%E5%AE%A2%E6%88%B7%E7%AB%AF%E5%85%BC%E5%AE%B9%E6%80%A7)

--------------------------------------------------------------------------------------------------------------

Longbridge MCP 依赖 **MCP OAuth 2.1** 标准。若客户端未完整实现该协议，将无法完成授权。

已知问题：Cherry Studio 早期版本不支持完整 OAuth 流程，请升级至最新版本。

如遇其他客户端连接失败，请确认客户端版本并查阅其 MCP 支持文档。

安全建议 [​](./mcp.md#%E5%AE%89%E5%85%A8%E5%BB%BA%E8%AE%AE)

------------------------------------------------------------------------------------------

*   **最小权限**：授权时仅同意当前任务所需的 scope，避免过度授权
*   **交易确认**：涉及下单等交易操作时，在 AI 提示词中明确要求执行前人工确认
*   **凭证安全**：OAuth 凭证由客户端管理，避免将其复制到不受信任的环境
*   **定期审查**：定期在 Longbridge 账户安全设置中检查并撤销不再使用的授权

推荐使用方式 [​](./mcp.md#%E6%8E%A8%E8%8D%90%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F)

--------------------------------------------------------------------------------------------------------------

1.  **从只读能力开始**：优先使用行情查询、持仓查看等低风险功能，熟悉工具行为
2.  **逐步开放交易能力**：确认权限范围和风控逻辑后，再使用下单相关工具
3.  **在提示词中加入约束**：例如"每笔交易金额不超过 X"、"执行前向我确认"等明确限制

常见问题 [​](./mcp.md#%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)

------------------------------------------------------------------------------------------

### OAuth 登录失败 [​](./mcp.md#oauth-%E7%99%BB%E5%BD%95%E5%A4%B1%E8%B4%A5)

*   确认 Longbridge 账户状态正常，已完成必要的身份验证
*   在客户端删除现有配置后重新添加并发起授权
*   检查当前账户是否支持所请求的 scope

### 已连接但部分工具不可用 [​](./mcp.md#%E5%B7%B2%E8%BF%9E%E6%8E%A5%E4%BD%86%E9%83%A8%E5%88%86%E5%B7%A5%E5%85%B7%E4%B8%8D%E5%8F%AF%E7%94%A8)

*   账户或地区限制：特定市场或功能可能受账户等级或地区限制
*   scope 变更：如工具能力有更新，可能需要重新授权以获得新 scope

### 交易操作提示权限不足 [​](./mcp.md#%E4%BA%A4%E6%98%93%E6%93%8D%E4%BD%9C%E6%8F%90%E7%A4%BA%E6%9D%83%E9%99%90%E4%B8%8D%E8%B6%B3)

*   检查账户的交易权限和市场可交易资格
*   确认当前 MCP 会话的 OAuth scope 包含交易相关权限

[LLMs Text](https://open.longbridge.com/docs/mcp.md)

[Edit this page](https://github.com/longbridge/developers/edit/main/docs/zh-CN/docs/mcp.md)

最后更新于:

Pager

[上一页快速开始](./getting-started.md)

[下一页CLI](./cli.md)

[Longbridge](https://longbridge.com/)
[Download](https://longbridge.com/download)
[服务条款](https://support.longbridgewhale.com/topics/misc.disable/lp-user-agreement?locale=zh-CN)
[隐私政策](https://support.longbridgewhale.com/topics/misc/privacy-policy?locale=zh-CN)

[SDK](https://open.longbridge.com/sdk)
[MCP](https://open.longbridge.com/docs/mcp)
[CLI](https://open.longbridge.com/docs/cli)
[LLM](https://open.longbridge.com/docs/llm)
[](https://github.com/longbridge)
