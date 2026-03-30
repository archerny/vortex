# Charles Schwab API 文档 - 下载与维护指南

## 概述

本目录存放从 [Charles Schwab Developer Portal](https://developer.schwab.com/) 及相关来源整理的离线 Markdown 文档，供项目内部引用。

- **官方文档来源**: `https://developer.schwab.com/`
- **API 规范来源**: 从 [schwab-py](https://github.com/alexgolec/schwab-py) 源码和 [Grokipedia](https://grokipedia.com/page/Schwab_Trader_API) 提取
- **存放路径**: `docs/`（按 API 类型分子目录）

### 目录结构

```
docs/
├── index.md                        # 总目录导航（所有 API 类型的入口）
├── user-guides/                    # 用户指南
│   ├── getting-started.md          # 入门指南（注册、创建应用、获取密钥）
│   └── authentication.md           # OAuth 2.0 认证详解
├── trader-api/                     # Trader API（核心交易 API）
│   ├── api-reference.md            # REST API 端点完整参考
│   ├── streaming.md                # WebSocket 实时流式数据
│   ├── order-templates.md          # 订单模板（股票、期权、复合订单）
│   └── order-builder.md            # 订单构建器高级参考
└── market-data/                    # 市场数据
    └── overview.md                 # 市场数据 API 概览
```

### Schwab API 产品体系

Schwab 开发者门户提供多种 API 产品：

| API 产品 | 说明 | 目录 | 是否已下载 |
|---------|------|------|-----------|
| **Trader API (Individual)** | 个人交易者 API — 账户、交易、市场数据（核心） | `trader-api/`, `market-data/` | ✅ |
| **Trader API (Commercial)** | 商业版 — 面向第三方集成商 | — | ❌（需商业合作） |
| **Account and Client Data** | 账户和客户数据 | — | ❌ |
| **Advisor Services** | 投资顾问服务 | — | ❌ |
| **Data Aggregation** | 数据聚合 | — | ❌ |
| **Tax Data** | 税务数据 | — | ❌ |

### 站点技术特征（影响下载策略）

| 特征 | 说明 |
|------|------|
| **框架** | Angular SPA（`<app-root>` 根组件，JS 打包文件 `main.*.js`） |
| **CDN/安全** | **Akamai**（`edgesuite.net`）反爬保护，连 `robots.txt` 和 `sitemap.xml` 都返回 403 |
| **API 规范** | OpenAPI/Swagger 页面由 JS 动态渲染，直接 URL 返回 403 |
| **Firecrawl map** | ✅ **可用** — 返回 32 个 URL（map 命令使用了搜索引擎索引而非直接爬取） |
| **Firecrawl scrape** | ❌ **被反爬拦截** — 所有 scrape 请求均返回 "anti-bot" 错误 |
| **Firecrawl crawl** | ❌ **被反爬拦截** — crawl 返回 0 结果 |
| **curl 直接请求** | HTML 返回空壳 `<app-root></app-root>`（SPA，需 JS 渲染） |
| **web_fetch** | ❌ 返回 403 Forbidden |

> ⚠️ **关键结论**: `developer.schwab.com` 是当前所有已下载券商中反爬保护最强的站点。Firecrawl 的 `scrape`、`crawl`、`download` 命令**全部失效**。只有 `map` 命令可以返回 URL 列表（32 个），但无法获取任何页面内容。

---

## 1. 文件命名规则

由于无法直接从官方站点抓取，本文档采用基于内容主题的命名方式：

```
docs/{category}/{topic}.md
```

示例：

| 主题 | 本地路径 | 说明 |
|------|---------|------|
| 总目录 | `index.md` | 所有 API 类型的入口 |
| 入门指南 | `user-guides/getting-started.md` | 开发者注册、应用创建 |
| OAuth 认证 | `user-guides/authentication.md` | 认证流程详解 |
| API 端点参考 | `trader-api/api-reference.md` | **核心** — 所有 REST 端点 |
| 流式数据 | `trader-api/streaming.md` | WebSocket 实时数据 |
| 订单模板 | `trader-api/order-templates.md` | 常用订单快速构建 |
| 订单构建器 | `trader-api/order-builder.md` | 高级订单规范 |
| 市场数据概览 | `market-data/overview.md` | 市场数据端点概览 |

---

## 2. 下载最佳实践

### 2.1 关于 developer.schwab.com 的反爬保护

**❌ 以下方法均失败**：

| 方法 | 结果 |
|------|------|
| `firecrawl scrape` | "Document scrape was prevented by anti-bot" |
| `firecrawl scrape --wait-for 8000 --country US` | 同上 |
| `firecrawl crawl --limit 1 --max-depth 0` | 返回 0 结果 |
| `firecrawl download` | 内部调用 scrape，同样被拦截 |
| `curl` + User-Agent 伪装 | 返回空壳 Angular HTML |
| `web_fetch`（IDE 内置工具） | 403 Forbidden |
| Google Cache | Google 异常流量拦截页面 |

**✅ 唯一有效的 Firecrawl 命令**: `firecrawl map` — 返回 32 个 URL（通过搜索引擎索引发现，不需要实际访问站点）

### 2.2 推荐方式：多源综合

由于官方站点无法爬取，推荐从以下替代来源获取 Schwab API 文档信息：

#### 来源 1: schwab-py GitHub 源码（✅ 主要来源）

[alexgolec/schwab-py](https://github.com/alexgolec/schwab-py) 是最完善的第三方 Schwab API 封装库，其源码和文档包含了：

- **`docs/*.rst`**：完整的用户文档（RST 格式），包括认证、HTTP Client、Streaming、订单构建
- **`schwab/client/base.py`**：所有 REST API 端点的定义（完整的 URL 路径、参数、枚举值）
- **`schwab/streaming.py`**：所有 WebSocket 流类型的定义

**下载方式**：

```bash
# 批量下载文档源文件
for f in index.rst client.rst auth.rst getting-started.rst streaming.rst \
         order-builder.rst order-templates.rst util.rst help.rst tda-transition.rst; do
  curl -sL "https://raw.githubusercontent.com/alexgolec/schwab-py/main/docs/$f" \
    -o ".firecrawl/schwab-py-$f"
done

# 下载核心源码（包含 API 端点定义）
curl -sL "https://raw.githubusercontent.com/alexgolec/schwab-py/main/schwab/client/base.py" \
  -o ".firecrawl/schwab-client-base.py"
curl -sL "https://raw.githubusercontent.com/alexgolec/schwab-py/main/schwab/streaming.py" \
  -o ".firecrawl/schwab-streaming.py"
```

> **优势**：GitHub raw 文件不受任何反爬限制，100% 可靠
> **注意**：RST 格式需要手动转换为 Markdown

#### 来源 2: Grokipedia（✅ 概览信息）

[Grokipedia Schwab Trader API 页面](https://grokipedia.com/page/Schwab_Trader_API) 提供了结构化的 API 概览，包括：

- API 组件和端点列表
- 认证流程
- 速率限制
- 已知限制
- 代码示例

**下载方式**：使用 `web_fetch` 工具（Grokipedia 无反爬保护）。

#### 来源 3: Firecrawl map（✅ URL 发现）

虽然无法 scrape，但 `map` 命令可以发现站点的完整 URL 结构：

```bash
node scripts/index.js map "https://developer.schwab.com/" --limit 500 --json \
  -o .firecrawl/schwab-map.json
```

返回的 32 个 URL 作为文档结构参考。

#### 来源 4: 官方开发者门户（⚠️ 需要浏览器手动访问）

如需获取最新、最权威的信息（特别是 API 变更），只能通过浏览器手动访问 `developer.schwab.com` 并复制内容。Schwab 的 Akamai 反爬保护目前无法通过自动化工具绕过。

### 2.3 developer.schwab.com 的完整 URL 列表

以下是 Firecrawl `map` 命令发现的所有页面（2026-03-30）：

**用户指南（User Guides）**:

| URL | 说明 |
|-----|------|
| `/user-guides` | 用户指南首页 |
| `/user-guides/get-started/introduction` | 介绍 |
| `/user-guides/get-started/user-registration` | 用户注册 |
| `/user-guides/get-started/how-api-products-are-organized` | API 产品组织方式 |
| `/user-guides/get-started/requesting-product-access` | 请求产品访问 |
| `/user-guides/get-started/authenticate-with-oauth` | OAuth 认证 |
| `/user-guides/third-party-company/company-roles` | 第三方公司角色 |
| `/user-guides/third-party-company/create-company-profile` | 创建公司档案 |
| `/user-guides/third-party-company/edit-company-profile` | 编辑公司档案 |
| `/user-guides/third-party-company/invite-developers-to-company` | 邀请开发者 |
| `/user-guides/individual-developer/about-individual-developer-role` | 个人开发者角色 |
| `/user-guides/individual-developer/become-individual-developer` | 成为个人开发者 |
| `/user-guides/apis-and-apps/create-an-app` | 创建应用 |
| `/user-guides/apis-and-apps/modify-an-app` | 修改应用 |
| `/user-guides/apis-and-apps/test-in-sandbox` | 沙箱测试 |
| `/user-guides/apis-and-apps/promoting-apps-to-production` | 上线到生产环境 |
| `/user-guides/apis-and-apps/oauth-restart-vs-refresh-token` | OAuth 重启 vs 刷新令牌 |
| `/user-guides/apis-and-apps/app-callback-url-requirements` | 回调 URL 要求 |

**API 产品页面（Products）**:

| URL | 说明 |
|-----|------|
| `/products` | 产品列表 |
| `/products/account-and-client-data` | 账户和客户数据 |
| `/products/advisor-services` | 投资顾问服务 |
| `/products/data-aggregation` | 数据聚合 |
| `/products/tax-data` | 税务数据 |
| `/products/trader-api--commercial` | Trader API（商业版） |
| `/products/trader-api--individual` | Trader API（个人版） |

**其他**:

| URL | 说明 |
|-----|------|
| `/` | 首页 |
| `/login` | 登录 |
| `/register` | 注册 |
| `/terms-and-conditions` | 使用条款 |
| `/privacy` | 隐私政策 |

### 2.4 后处理

由于文档来源是 RST 格式和第三方信息，后处理主要包括：

1. **RST → Markdown 转换**：将 `schwab-py` 的 RST 文档转为 Markdown 格式
2. **API 端点提取**：从 `schwab/client/base.py` 提取完整的 REST API 路径和参数
3. **信息整合**：将多个来源的信息合并为统一的文档结构
4. **交叉引用**：建立文档间的相对路径链接

### 2.5 各模式对比（针对 developer.schwab.com）

| 模式 | 结果 | 原因 |
|------|------|------|
| `map` | ✅ 返回 32 个 URL | 使用搜索引擎索引，不需要直接访问站点 |
| `scrape` | ❌ anti-bot | Akamai CDN 反爬保护 |
| `crawl` | ❌ 0 结果 | 同上 |
| `download` | ❌ anti-bot | 内部调用 scrape |
| `search` | ✅ 部分可用 | 搜索引擎结果不受站点反爬影响 |

---

## 3. 文档格式规范

### 3.1 超链接：已下载文档使用本地相对路径

文档内部的交叉引用链接使用本地相对路径。由于目录分层，需根据文件位置计算：

**同目录引用**（如 `trader-api/api-reference.md` 引用 `trader-api/streaming.md`）：
```markdown
[WebSocket 流式数据](./streaming.md)
```

**跨目录引用**（如 `trader-api/api-reference.md` 引用 `user-guides/authentication.md`）：
```markdown
[OAuth 认证](../user-guides/authentication.md)
```

**从根目录引用**（如 `index.md` 引用 `trader-api/api-reference.md`）：
```markdown
[API 端点参考](./trader-api/api-reference.md)
```

**不替换的链接类型**（保留原始 URL）：
- 指向 `developer.schwab.com` 的链接（官方门户，需浏览器访问）
- GitHub 仓库链接
- 第三方文档链接（如 schwab-py readthedocs）
- SDK 和工具下载链接

---

## 4. 踩坑记录

### 4.1 developer.schwab.com 使用 Akamai 反爬保护（最严格）

**问题**：`developer.schwab.com` 是所有已下载券商中反爬保护最强的站点。不仅 Firecrawl 的所有 scrape/crawl 方法都失败，连 `curl` 和 `web_fetch` 也被 403 拦截。

**根因分析**：

1. **Angular SPA**：站点使用 Angular 框架，HTML 仅包含空壳 `<app-root></app-root>`，所有内容由 JS 动态渲染
2. **Akamai CDN 保护**：错误页面包含 `edgesuite.net` 引用，表明使用了 Akamai 的 Bot Manager 产品
3. **无 sitemap/robots.txt**：即使 `sitemap.xml` 和 `robots.txt` 也返回 403（Akamai 在 CDN 层面拦截）
4. **API 规范受保护**：尝试直接访问 OpenAPI 规范 URL（如 `/openapi/producerapi/spec/trader-api--individual`）也返回 403

**解决方案**：放弃直接爬取，改用多源综合方案（见 [§2.2](#22-推荐方式多源综合)）。

**与其他券商站点对比**：

| 券商 | 框架 | 反爬 | scrape | map | 文档可获取性 |
|------|------|------|--------|-----|------------|
| Futu OpenAPI | VuePress SPA | 无 | ✅ | ❌（SPA 路由） | ✅ 从 JS 路由提取 |
| IBKR Campus | WordPress | 轻度 | ✅ | ❌ | ✅ 从首页链接提取 |
| **Schwab** | **Angular SPA** | **Akamai（重度）** | **❌** | **✅（32 URL）** | **❌ 需替代来源** |

### 4.2 Firecrawl map 命令对 Schwab 站点有效

**发现**：虽然 scrape/crawl 全部被拦截，但 `map` 命令成功返回了 32 个 URL。

**原因分析**：Firecrawl 的 `map` 命令在 scrape 和 sitemap 都失败时，会 fallback 到搜索引擎索引来发现 URL。搜索引擎（Google、Bing）已经索引了 Schwab 开发者门户的页面，这些索引信息不受站点反爬限制。

**教训**：对于反爬保护强的站点，`map` 命令仍然可以作为 URL 发现工具使用，只是无法获取页面内容。

### 4.3 schwab-py GitHub 源码是最可靠的 API 文档来源

**发现**：`schwab/client/base.py` 包含了所有 REST API 端点的完整定义：

- 25+ 个 API 方法
- 完整的 URL 路径（如 `/trader/v1/accounts/{accountHash}/orders`）
- 所有参数名和类型
- 所有枚举值（订单状态、交易类型、周期类型等）
- 详细的 docstring 文档

而 `docs/*.rst` 包含了结构化的用户文档，虽然是 RST 格式，但转换为 Markdown 很简单。

**教训**：当官方文档站点被反爬保护时，**优先查找社区维护的 SDK 源码**。成熟的 SDK 通常包含完整的 API 定义，且 GitHub raw 文件永远不会被反爬拦截。

### 4.4 Google Cache 也被反爬拦截

**问题**：尝试通过 `webcache.googleusercontent.com` 获取 Schwab 页面的缓存版本，被 Google 的异常流量检测拦截。

**教训**：Google Cache 不适合作为自动化文档获取的可靠来源。

### 4.5 readthedocs.io 被 Cloudflare 保护

**问题**：`schwab-py.readthedocs.io` 使用 Cloudflare 防护，所有自动化访问（包括 Firecrawl scrape 和 web_fetch）都被 "Just a moment..." 验证页面拦截。

**解决方案**：直接从 GitHub 仓库的 `docs/` 目录获取 RST 源文件（raw.githubusercontent.com 不受限制）。

**教训**：readthedocs.io 的 Cloudflare 保护使其不适合自动化爬取。始终优先使用 GitHub raw 文件。

---

## 5. 待探索方案：真实浏览器模拟爬取

> **状态**：🔬 方案已设计，待验证（2026-03-30）

由于 `developer.schwab.com` 的 Akamai 反爬保护极强，所有服务端爬取工具（Firecrawl、curl、web_fetch）全部被拦截。下一步计划使用 **真实浏览器自动化**（Playwright）来绕过反爬保护，直接获取官方文档内容。

### 5.1 为什么需要真实浏览器？

Akamai Bot Manager 的检测维度包括：

| 检测项 | 服务端爬取（Firecrawl/curl） | 真实浏览器（Playwright） |
|--------|---------------------------|------------------------|
| TLS 指纹 | ❌ 与浏览器不同 | ✅ 真实 Chromium 指纹 |
| HTTP/2 特征 | ❌ 不完整 | ✅ 完整 |
| JavaScript 执行 | ❌ 无法执行 / 有限 | ✅ 完整 V8 引擎 |
| Angular SPA 渲染 | ❌ 仅得到空壳 HTML | ✅ 完整渲染 |
| 浏览器指纹（Canvas/WebGL） | ❌ 无 | ✅ 真实指纹 |
| `navigator.webdriver` | N/A | ⚠️ 需 stealth 插件隐藏 |

### 5.2 推荐方案：Playwright + Stealth 模式

#### 核心思路

```python
from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    # 1. 启动真实 Chromium（非 headless 更难被检测）
    browser = p.chromium.launch(headless=False)
    context = browser.new_context()
    page = context.new_page()

    # 2. 导航到目标页面
    page.goto("https://developer.schwab.com/products/trader-api--individual")

    # 3. 等待 Angular SPA 完全渲染
    page.wait_for_load_state("networkidle")

    # 4. 提取渲染后的内容
    content = page.inner_text("body")  # 纯文本
    html = page.content()              # 完整 DOM
```

#### Stealth 配置要点

- 使用 `playwright-stealth` 插件隐藏自动化特征
- 设置真实的 User-Agent、视口大小、语言等
- 模拟真实用户行为（随机延迟、滚动、鼠标移动）
- 优先使用非 headless 模式

### 5.3 登录问题处理

部分 Schwab 开发者门户页面可能需要登录。根据分析：

| 页面类型 | 示例 | 是否需要登录 |
|----------|------|-------------|
| 产品介绍 (`/products/*`) | API 功能描述 | ❌ 大概率不需要 |
| 用户指南 (`/user-guides/*`) | 入门文档 | ❌ 大概率不需要 |
| API 规范 (`/apis/*`) | Swagger/OpenAPI | ⚠️ 可能需要 |
| 控制台 (`/dashboard`, `/apps/*`) | 应用管理 | ✅ 需要（但不需要爬取） |

#### 方案 A：半自动登录 + Cookie 复用（推荐）

```python
# 第一次运行：手动登录并保存状态
context = browser.new_context()
page = context.new_page()
page.goto("https://developer.schwab.com/login")

# ⏸️ 暂停，等待用户在浏览器窗口中手动登录（支持 2FA）
input("请在浏览器中完成登录，然后按回车继续...")

# 保存登录状态（Cookie + localStorage + sessionStorage）
context.storage_state(path="schwab-auth.json")

# 后续运行：自动加载登录状态，无需再次登录
context = browser.new_context(storage_state="schwab-auth.json")
```

**优点**：
- 密码不经过脚本，安全性高
- 支持任何 2FA 方式（短信、Authenticator 等）
- Cookie 过期前都不需要重新登录

#### 方案 B：持久化浏览器 Profile

```python
# 使用持久化 Profile，登录状态跨次运行保留
context = playwright.chromium.launch_persistent_context(
    user_data_dir="./schwab-browser-profile",
    headless=False
)
# 首次手动登录后，Profile 中的 Cookie 会一直保留
```

### 5.4 完整执行计划

```
Step 1: 验证可行性（先不处理登录）
  → 用 Playwright stealth 模式打开一个公开页面（如 /products/trader-api--individual）
  → 确认能否绕过 Akamai 反爬，获取渲染后的页面内容
  → 如果成功，进入 Step 2

Step 2: 批量爬取公开文档页面
  → 使用 map 发现的 32 个 URL 作为目标列表
  → 逐页爬取，控制请求间隔（建议 5-10 秒），避免触发速率限制
  → 将渲染后的 HTML 转换为 Markdown

Step 3: 处理需要登录的页面（如有）
  → 切换到方案 A（半自动登录 + Cookie 复用）
  → 用户手动登录一次，脚本保存状态后自动完成剩余工作

Step 4: 替换现有文档
  → 用官方内容替换当前从 schwab-py 提取的文档
  → 保留 schwab-py 源码作为 API 端点定义的交叉验证来源
```

### 5.5 风险与注意事项

| 风险 | 说明 | 缓解措施 |
|------|------|---------|
| Akamai 高级检测 | 可能识别 Playwright 的自动化特征 | 使用 stealth 插件 + 非 headless 模式 |
| 速率限制/IP 封禁 | 频繁访问可能触发封禁 | 控制间隔 5-10s，使用随机延迟 |
| 登录态过期 | Cookie/Token 有时效 | 检测 401 响应，自动提示重新登录 |
| 页面结构变化 | Angular 组件更新导致选择器失效 | 使用通用文本提取，避免依赖具体 CSS 选择器 |
| Schwab ToS 合规 | 自动化爬取可能违反使用条款 | 仅用于个人离线参考，不公开分发 |

### 5.6 所需工具

- **Playwright**：`npm install playwright` 或 `pip install playwright`
- **playwright-stealth**（可选）：隐藏自动化特征
- **Turndown / html-to-markdown**：HTML → Markdown 转换

---

## 7. 文档内容概览

### User Guides（用户指南，`user-guides/`）

| 文件 | 说明 |
|------|------|
| `getting-started.md` | 入门指南：开发者注册、应用创建、Callback URL、密钥获取、审批等待 |
| `authentication.md` | OAuth 2.0 认证：授权码获取、Token 交换、Token 刷新、生命周期、故障排查 |

### Trader API（`trader-api/`）

| 文件 | 说明 |
|------|------|
| `api-reference.md` | **核心文档** — 所有 REST API 端点（账户 3 个、订单 7 个、交易 2 个、报价 2 个、K线 1 个、期权链 2 个、工具搜索 3 个、其他 3 个），共计 23 个端点 |
| `streaming.md` | WebSocket 流式数据：K 线图表、Level 1/2 报价、筛选器、账户活动，支持股票/期权/期货/外汇 |
| `order-templates.md` | 订单模板：股票买卖、做空、期权单腿、垂直价差、复合策略（OCO、Trigger） |
| `order-builder.md` | 订单构建器：完整的订单规范字段参考（订单类型、时段、价格、止损等） |

### Market Data（`market-data/`）

| 文件 | 说明 |
|------|------|
| `overview.md` | 市场数据 API 概览：报价、K 线、期权链、市场时间、工具搜索 |

### API 基础信息

| 项目 | 值 |
|------|------|
| **基础 URL** | `https://api.schwabapi.com` |
| **认证** | OAuth 2.0 Bearer Token |
| **数据格式** | JSON |
| **Trader API 前缀** | `/trader/v1/` |
| **Market Data 前缀** | `/marketdata/v1/` |
| **WebSocket** | `wss://`（URL 通过 REST API 获取） |
| **速率限制** | 市场数据 ~120/min，交易 2-4/sec |
| **Token 有效期** | Access: 30min, Refresh: 7 days |

---

## 8. 维护 Checklist

当需要更新文档时，按以下步骤执行：

### URL 发现（使用 map）
- [ ] 运行 `firecrawl map "https://developer.schwab.com/" --limit 500` 获取最新页面列表
- [ ] 与现有文件对比，确认新增/删除的页面
- [ ] 检查是否有新的 API 产品或用户指南页面

### 内容更新（多源综合）
- [ ] 从 GitHub 拉取 `schwab-py` 最新源码，检查 `schwab/client/base.py` 是否有新增端点
- [ ] 对比 `docs/*.rst` 文件变化，更新对应的 Markdown 文档
- [ ] 如有新的 API 端点，更新 `trader-api/api-reference.md` 的端点汇总表
- [ ] 检查 schwab-py 的 CHANGELOG 或 Release Notes 了解 API 变更

### 尝试直接爬取（定期检查）
- [ ] 尝试 `firecrawl scrape "https://developer.schwab.com/products/trader-api--individual"` — 检查 Akamai 保护是否有变化
- [ ] 如果 scrape 成功，切换到直接爬取方式获取最新官方文档

### 最终验证
- [ ] 所有文档文件的第一行为有意义的标题
- [ ] 所有交叉引用链接有效（相对路径正确）
- [ ] API 端点列表与 `schwab-py` 源码中的端点一致
- [ ] 检查 `schwab-py` 版本号（当前参考: v1.5.0）
