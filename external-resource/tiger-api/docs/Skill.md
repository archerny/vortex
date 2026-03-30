# AI Skill 技能插件

Tiger OpenAPI 提供了 AI Skill 技能插件，遵循 [Agent Skills](https://agentskills.io/)
 开放标准，支持 Claude Code、Cursor、Gemini CLI、GitHub Copilot、VS Code、OpenAI Codex 等 40+ AI 编码工具。

安装后，AI 工具可以直接通过自然语言帮你调用老虎 SDK 完成行情查询、下单交易、账户管理等操作，无需手动查阅 API 文档。

  

### 

安装

[](./Skill.md#%E5%AE%89%E8%A3%85)

推荐使用 `npx skills` 一键安装：

Bash

    npx skills add tigerfintech/tigeropen-skill

CLI 会自动检测你本机已安装的 AI 编码工具，并引导你选择安装范围（项目级 / 全局）。

更多安装选项：

Bash

    # 查看可用技能
    npx skills add tigerfintech/tigeropen-skill --list
    
    # 安装到指定工具
    npx skills add tigerfintech/tigeropen-skill -a claude-code
    
    # 全局安装（所有项目可用）
    npx skills add tigerfintech/tigeropen-skill -g
    
    # 安装到所有已检测到的工具
    npx skills add tigerfintech/tigeropen-skill --all

也可以通过 [ClawHub](https://clawhub.com/)
 安装：

Bash

    npx clawhub install tigerbrokers

其他安装方式

#### 

通过 --plugin-dir 本地加载（Claude Code）

[](./Skill.md#%E9%80%9A%E8%BF%87---plugin-dir-%E6%9C%AC%E5%9C%B0%E5%8A%A0%E8%BD%BDclaude-code)

Bash

    git clone https://github.com/tigerfintech/tigeropen-skill.git ~/tigeropen-skill
    claude --plugin-dir ~/tigeropen-skill

#### 

手动复制到 skills 目录

[](./Skill.md#%E6%89%8B%E5%8A%A8%E5%A4%8D%E5%88%B6%E5%88%B0-skills-%E7%9B%AE%E5%BD%95)

Bash

    # 全局生效
    git clone https://github.com/tigerfintech/tigeropen-skill.git /tmp/tigeropen-skill
    cp -r /tmp/tigeropen-skill/skills/tigeropen ~/.claude/skills/
    rm -rf /tmp/tigeropen-skill
    
    # 仅当前项目
    mkdir -p .claude/skills
    cp -r /tmp/tigeropen-skill/skills/tigeropen .claude/skills/

  

### 

前置条件

[](./Skill.md#%E5%89%8D%E7%BD%AE%E6%9D%A1%E4%BB%B6)

1.  安装 Python SDK：`pip install tigeropen`
2.  拥有老虎证券账户和 API 权限（[开发者页面](https://developer.itigerup.com/)
    ）
3.  准备好 `tiger_id`、私钥文件和 `account`

  

### 

技能模块

[](./Skill.md#%E6%8A%80%E8%83%BD%E6%A8%A1%E5%9D%97)

Skill 包含以下功能模块，AI 会根据你的需求自动加载对应的参考文档：

| 模块  | 内容  |
| --- | --- |
| Quickstart | SDK 安装、配置、客户端创建、枚举/对象参考、错误码、FAQ |
| Market Data | 股票/期权/期货/基金行情、K线、深度、选股器、基本面数据 |
| Trading | 下单（市价/限价/止损/算法单）、订单管理、账户资产、持仓、资金划转 |
| Options | 期权链、Greeks、单腿/多腿组合策略、期权计算工具 |
| Real-time Push | 实时推送（行情/深度/逐笔/K线/订单/持仓/资产变动） |
| CLI | CLI 命令行工具：配置管理、行情查询、交易操作、账户查看 |
| MCP Server | MCP Server 配置，集成 Cursor/Claude Code/Trae 等 AI 编辑器 |

  

### 

使用示例

[](./Skill.md#%E4%BD%BF%E7%94%A8%E7%A4%BA%E4%BE%8B)

安装后，直接在 AI 工具中用自然语言操作即可：

    > 帮我查询 AAPL 和 TSLA 的实时行情
    
    > 用限价单买入 100 股 AAPL，价格 150
    
    > 获取 AAPL 近 30 天的日K线数据并画图
    
    > 查询我的账户资产和当前持仓
    
    > 获取 AAPL 下个月到期的期权链，筛选 delta 在 0.3-0.7 的
    
    > 订阅 AAPL 和 TSLA 的实时行情推送

  

### 

工作原理

[](./Skill.md#%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86)

AI Skill 本质上是一组结构化的参考文档，告诉 AI 编码工具如何正确调用 Tiger OpenAPI。当你在 AI 工具中描述需求时：

1.  AI 识别你的意图（如"查行情"、"下单"等）
2.  自动加载对应的参考文档（如 quote.md、trade.md）
3.  根据文档中的 API 规范和代码示例，生成正确的代码
4.  执行代码并返回结果

这意味着你不需要记忆 API 细节，只需用自然语言描述你想做什么。

  

### 

支持的 AI 工具

[](./Skill.md#%E6%94%AF%E6%8C%81%E7%9A%84-ai-%E5%B7%A5%E5%85%B7)

Skill 遵循 Agent Skills 开放标准，支持以下 AI 编码工具（部分列表）：

*   **Claude Code** — Anthropic 官方 CLI
*   **Cursor** — AI 代码编辑器
*   **Gemini CLI** — Google Gemini 命令行工具
*   **GitHub Copilot** — GitHub AI 编程助手
*   **VS Code** — 通过扩展支持
*   **OpenAI Codex** — OpenAI 编码工具
*   **Kiro** — AWS AI IDE
*   **Trae** — 字节跳动 AI IDE

以及其他支持 Agent Skills 标准的 40+ 工具。

  

### 

仓库地址

[](./Skill.md#%E4%BB%93%E5%BA%93%E5%9C%B0%E5%9D%80)

GitHub：[tigerfintech/tigeropen-skill](https://github.com/tigerfintech/tigeropen-skill)
