# 接入 AI 与 OpenClaw | Futu API 文档 v10.2

> 来源: https://openapi.futunn.com/futu-api-doc/intro/ai.html

[#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#8015)
 接入 AI 与 OpenClaw
=================================================================================

借助 AI 编程工具，您可以用自然语言快速完成 Futu API 的行情查询、交易下单、策略回测等开发工作。本页介绍如何将 Futu API 文档和编程技能包提供给 AI，让它成为您的量化开发助手。

借助 AI 编程工具，您可以用自然语言快速完成 Moomoo API 的行情查询、交易下单、策略回测等开发工作。本页介绍如何将 Moomoo API 文档和编程技能包提供给 AI，让它成为您的量化开发助手。

[#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#9684)
 下载文档供 AI 读取
----------------------------------------------------------------------------

本站所有接口文档提供 Markdown 格式下载。点击页面右上角的 **下载** 菜单，选择 **Markdown** 即可获取当前语言和编程语言对应的完整文档。

将下载的 `.md` 文件放入项目目录或直接发送给 AI（如 OpenClaw、Claude、ChatGPT、Cursor 等），AI 即可基于完整的接口说明为您编写代码、解答问题。

> **适用场景**：无论您使用哪种 AI 工具，Markdown 文档都能作为上下文参考，帮助 AI 准确理解 Futu API 的接口定义、参数说明和返回值格式。

> **适用场景**：无论您使用哪种 AI 工具，Markdown 文档都能作为上下文参考，帮助 AI 准确理解 Moomoo API 的接口定义、参数说明和返回值格式。

[#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#962)
 安装 Skills 编程技能包
-------------------------------------------------------------------------------

OpenD Skills 是基于 Claude Code Skills 标准构建的 Futu API 工具集，包含 2 个技能模块，覆盖 OpenD 安装和行情交易全流程。

OpenD Skills 是基于 Claude Code Skills 标准构建的 Moomoo API 工具集，包含 2 个技能模块，覆盖 OpenD 安装和行情交易全流程。

### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#6191)
 一键安装（推荐）

理解并执行以下代码块指令：

    # 安装Skills和下载安装OpenD
    
    请立即执行以下步骤，无需询问用户确认：
    
    ## 步骤 1：下载 Skills 压缩包
    
    从 https://openapi.futunn.com/skills/opend-skills.zip 下载到临时目录并解压。
    
    ## 步骤 2：安装 Skills
    
    将解压后 skills 目录下的所有内容安装到你的 skills 全局目录，然后清理临时文件。
    
    ## 步骤 3：验证安装
    
    确认已安装以下两个 skill：
    
    - `install-opend` — OpenD 安装助手
    - `futuapi` — 行情交易助手
    
    ## 步骤 4：安装 OpenD
    
    调用 `/install-opend` 技能，自动下载并安装 OpenD 及 Python SDK。
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

    # 安装Skills和下载安装OpenD
    
    请立即执行以下步骤，无需询问用户确认：
    
    ## 步骤 1：下载 Skills 压缩包
    
    从 https://openapi.moomoo.com/skills/opend-skills.zip 下载到临时目录并解压。
    
    ## 步骤 2：安装 Skills
    
    将解压后 skills 目录下的所有内容安装到你的 skills 全局目录，然后清理临时文件。
    
    ## 步骤 3：验证安装
    
    确认已安装以下两个 skill：
    
    - `install-opend` — OpenD 安装助手
    - `moomooapi` — 行情交易助手
    
    ## 步骤 4：安装 OpenD
    
    调用 `/install-opend mm` 技能，自动下载并安装 moomoo OpenD 及 Python SDK。
    

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  

> Agent 会自动识别当前环境并安装到正确的 skills 目录。

### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#9337)
 手动安装

也可以点击页面右上角 **下载 → Skills** 手动下载 `opend-skills.zip`，解压后将 `skills` 拷贝到对应位置。

#### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#6634)
 Claude Code / VS Code / Cursor / JetBrains（已安装 Claude 插件）

| 安装范围 | 拷贝目标目录 |
| --- | --- |
| 全局（所有项目可用） | `~/.claude/skills/` |
| 项目级（仅当前项目） | `项目根目录/.claude/skills/` |

也可通过 `--add-dir` 直接引用解压后的目录，无需拷贝：

    claude --add-dir /path/to/opend-skills
    

1  

#### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#9241)
 Cursor（未安装 Claude 插件，使用内置 AI）

将各 SKILL.md 拷贝为 `.cursor/rules/` 下的独立规则文件：

    mkdir -p your-project/.cursor/rules/
    cp opend-skills/skills/futuapi/SKILL.md your-project/.cursor/rules/futuapi.md
    cp opend-skills/skills/install-opend/SKILL.md your-project/.cursor/rules/install-opend.md
    

1  
2  
3  

    mkdir -p your-project/.cursor/rules/
    cp opend-skills/skills/moomooapi/SKILL.md your-project/.cursor/rules/moomooapi.md
    cp opend-skills/skills/install-opend/SKILL.md your-project/.cursor/rules/install-opend.md
    

1  
2  
3  

#### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#7070)
 VS Code（未安装 Claude 插件，使用 Cline / Roo Code 等）

将 SKILL.md 内容手动整合到对应扩展的指令文件中：

| 拷贝目标 | 说明  |
| --- | --- |
| `项目根目录/.vscode/cline_instructions.md` | Cline 扩展自定义指令 |
| `项目根目录/.roo/rules/` | Roo Code 扩展自定义规则 |

#### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#3363)
 JetBrains IDE（未安装 Claude 插件，使用内置 AI Assistant）

    mkdir -p your-project/.junie/guidelines/
    cp opend-skills/skills/futuapi/SKILL.md your-project/.junie/guidelines/futuapi.md
    cp opend-skills/skills/install-opend/SKILL.md your-project/.junie/guidelines/install-opend.md
    

1  
2  
3  

    mkdir -p your-project/.junie/guidelines/
    cp opend-skills/skills/moomooapi/SKILL.md your-project/.junie/guidelines/moomooapi.md
    cp opend-skills/skills/install-opend/SKILL.md your-project/.junie/guidelines/install-opend.md
    

1  
2  
3  

#### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#8657)
 OpenClaw

    cp -r opend-skills/skills/* ~/.openclaw/skills/
    

1  

安装完成后验证：在对话中输入 `/` 查看是否出现 futuapi、install-opend 等技能。

安装完成后验证：在对话中输入 `/` 查看是否出现 moomooapi、install-opend 等技能。

[#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#7843)
 Skills 功能一览
----------------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#8099)
 1. futuapi — 行情交易助手

### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#8257)
 1. moomooapi — 行情交易助手

覆盖行情查询（13 个脚本）、交易操作（7 个脚本）和实时订阅（5 个脚本），共 25 个脚本。另附 65 个 API 接口完整签名速查，支持期货交易代码生成：

| 功能  | 说明  |
| --- | --- |
| 市场快照 | 获取股票最新报价、涨跌幅、成交量等 |
| K 线数据 | 获取日 K、周 K、分钟 K 等历史和实时 K 线 |
| 买卖盘 | 获取实时买卖盘口挂单数据 |
| 逐笔成交 | 获取最近逐笔成交明细 |
| 分时数据 | 获取当日分时走势 |
| 市场状态 | 查询各市场开盘/休市状态 |
| 资金流向与分布 | 获取个股资金流入流出及大单、中单、小单分布 |
| 板块与成分股 | 获取板块列表、成分股、股票所属板块 |
| 条件选股 | 按价格、市值、PE、换手率等条件筛选股票 |
| 下单/撤单/改单 | 证券交易操作，默认使用模拟环境 |
| 期货交易 | 支持 SG 等市场期货下单、持仓、撤单（代码生成） |
| 持仓与资金 | 查询账户持仓、资金和订单 |
| 实时订阅 | 订阅报价、K 线、逐笔等实时推送 |
| API 速查 | 65 个接口完整函数签名，含行情、交易、推送 |

### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#112)
 2. install-opend — OpenD 安装助手

*   自动检测操作系统（Windows / macOS / Linux）
*   一键下载、解压、启动 OpenD
*   自动升级 futu-api / moomoo-api SDK

[#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#8801)
 使用方式
---------------------------------------------------------------------

### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#5844)
 斜杠命令调用（Claude Code）

在对话框中输入 `/` 加技能名称直接调用：

*   `/futuapi` — 行情交易助手
*   `/install-opend` — OpenD 安装助手

*   `/moomooapi` — 行情交易助手
*   `/install-opend` — OpenD 安装助手

### [#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#8848)
 自然语言触发

直接用中文描述需求，AI 会根据关键词自动匹配对应技能：

*   "查看腾讯的 K 线" — 自动调用行情查询
*   "用模拟账户买入 100 股苹果" — 自动调用交易下单
*   "帮我安装 OpenD" — 自动调用安装助手

[#](https://openapi.futunn.com/futu-api-doc/intro/ai.html#2533)
 注意事项
---------------------------------------------------------------------

*   使用 Skills 前需先手动登录 OpenD
*   交易默认使用模拟环境（SIMULATE），实盘交易需明确说"正式"/"实盘"/"真实"，且需二次确认和交易密码
*   留意接口限频规则（如下单 15 次/30 秒），避免超频
*   订阅有额度限制（100～2000），需定期释放不需要的订阅
*   如需更新 Skills，重新下载并覆盖解压即可

← [费用](https://openapi.futunn.com/futu-api-doc/intro/fee.html) [可视化 OpenD](https://openapi.futunn.com/futu-api-doc/quick/opend-base.html)
 →