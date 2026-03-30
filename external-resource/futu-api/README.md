# Futu API 文档 - 下载与维护指南

## 概述

本目录存放从 [Futu OpenAPI 官方文档](https://openapi.futunn.com/futu-api-doc/) 下载的离线 Markdown 文档，供项目内部引用。

- **文档来源**: `https://openapi.futunn.com/futu-api-doc/`
- **文档版本**: v10.2
- **文件数量**: 82 个 .md 文件（以实际下载结果为准）
- **存放路径**: `docs/`

### 站点技术特征（影响下载策略）

| 特征 | 说明 |
|------|------|
| **框架** | VuePress（Vue.js SPA 单页应用） |
| **侧边栏** | JS 动态渲染，折叠分组不在初始 DOM 中 |
| **sitemap** | `/futu-api-doc/sitemap.xml` 返回 404；根域名 sitemap 仅含 2 个 URL，无文档子页面 |
| **robots.txt** | 不存在（返回 404） |
| **路由配置** | 完整的页面 URL 列表编码在 VuePress 打包的 JS 文件中（`app.*.js`，约 32MB） |
| **首页** | `https://openapi.futunn.com/futu-api-doc/` 会 302 重定向到 `/intro/intro.html` |

> ⚠️ 以上特征直接导致 Firecrawl 的 `map` 命令**完全无法发现页面 URL**（返回空数组）。详见 [踩坑 4.4](#44-firecrawl-map-命令对该站点完全失效)。

---

## 1. 文件命名规则

URL 路径与本地文件名的映射关系：

```
https://openapi.futunn.com/futu-api-doc/{category}/{page}.html
→ docs/{category}_{page}.md
```

示例：

| URL 路径 | 本地文件名 |
|----------|-----------|
| `/trade/trade.html` | `trade_trade.md` |
| `/quote/get-kl.html` | `quote_get-kl.md` |
| `/intro/ai.html` | `intro_ai.md` |
| `/ftapi/common.html` | `ftapi_common.md` |

特殊情况：
- 站点首页 `/futu-api-doc/` → `index.md`
- 注意 `base/*` 路径在服务器上实际对应 `ftapi/*`，`qa/qa` 实际对应 `qa/opend`、`qa/quote` 等

---

## 2. 下载最佳实践

### 2.1 推荐方式：发现 URL + 批量 scrape（分步下载）

**不要使用 `download` 一键下载**。`download` 命令内部批量处理时，部分页面会出现抓取降级，导致质量不一致（详见[踩坑记录](#4-踩坑记录)）。

**推荐流程**：

#### 第一步：发现所有 URL

> ⚠️ **Firecrawl 的 `map` 命令对本站点无效**（返回空数组），因为该站点是 VuePress SPA，无有效 sitemap，侧边栏链接由 JS 动态渲染。详见 [踩坑 4.4](#44-firecrawl-map-命令对该站点完全失效)。

**正确做法：从 VuePress JS 路由配置中提取 URL 列表**：

```bash
# 1. 先找到当前版本的 JS 入口文件名（app.*.js）
curl -sL "https://openapi.futunn.com/futu-api-doc/intro/intro.html" \
  | grep -o '/futu-api-doc/assets/js/app\.[a-f0-9]*\.js'

# 2. 下载 JS 并提取所有中文版文档路径
curl -sL "https://openapi.futunn.com/futu-api-doc/assets/js/app.<hash>.js" \
  | python3 -c "
import sys, re
js = sys.stdin.read()
paths = re.findall(r'path:\"(/(?:trade|quote|intro|quick|ftapi|qa)/[^\"]+\.html)\"', js)
for p in sorted(set(paths)):
    print(f'https://openapi.futunn.com/futu-api-doc{p}')
" > .firecrawl/futu-urls.txt

# 3. 检查发现的 URL 数量
wc -l .firecrawl/futu-urls.txt
```

> 📌 上面的正则 `path:"(/(?:trade|quote|intro|quick|ftapi|qa)/..."` 匹配 VuePress 路由配置中不含 `/en/`、`/hk/` 的中文版路径。如果站点新增了顶级分类目录，需要在正则中补充。
>
> 📌 另外还有 `index.md`（首页）和 `opend/` 目录下的文件不在路由 path 配置中，需要根据实际情况手工补充。

> 💡 如果未来站点添加了完善的 sitemap 或改用了服务端渲染，可以再尝试 `firecrawl map` 命令。但在当前 VuePress SPA 架构下，map 完全无效（详见 [踩坑 4.4](#44-firecrawl-map-命令对该站点完全失效)）。

#### 第二步：逐个 scrape（不加 --only-main-content）

```bash
# 单个页面
node scripts/index.js scrape \
  "https://openapi.futunn.com/futu-api-doc/trade/trade.html" \
  $AUTH \
  -o .firecrawl/trade_trade.md

# 批量并行（推荐）
FIRECRAWL_DATA_DIR=<dataDir> node scripts/index.js scrape $AUTH \
  https://openapi.futunn.com/futu-api-doc/trade/trade.html \
  https://openapi.futunn.com/futu-api-doc/quote/get-kl.html \
  https://openapi.futunn.com/futu-api-doc/intro/ai.html
```

> **关键**：不要加 `--only-main-content` 参数。该参数依赖 Firecrawl 自动判断"主内容"区域，判断失败会丢失内容。宁可拿到完整内容后手动清理噪音，也不要冒丢失内容的风险。

#### 第三步：后处理（统一格式）

下载完成后，对每个文件执行以下后处理：

1. **清理导航栏噪音**：去除 logo、语言切换菜单等非正文内容
2. **替换提示图标**：将 `tip.png` 图标替换为文本格式
3. **替换超链接**：将指向已下载文档的链接替换为本地相对路径
4. **中文文件名翻译为英文**：如果下载的文档文件名包含中文，将文件名翻译为对应的英文（如 `期权工具.md` → `option-tools.md`），同时更新所有引用了该文件名的本地链接。中文文件名不利于统一管理和跨平台兼容。

### 2.2 各模式对比

| 模式 | 内容完整性 | 输出干净度 | 一致性 | 适用场景 |
|------|-----------|-----------|--------|---------|
| `scrape`（默认） | ✅ 完整 | ⚠️ 可能含导航栏噪音 | ✅ 稳定 | **推荐，逐页下载** |
| `scrape --only-main-content` | ❌ 可能丢内容 | ✅ 干净 | ❌ 不稳定 | 不推荐 |
| `download` | ⚠️ 部分降级 | ⚠️ 不一致 | ❌ 不一致 | 不推荐 |
| `crawl` | ✅ 完整 | ✅ 较好 | ✅ 稳定 | 输出为 JSON，需自行拆分 |

---

## 3. 文档格式规范

### 3.1 提示图标：使用 (ℹ️ ...) 格式

原始文档中的提示图标 `tip.png` 应替换为纯文本格式：

**替换前**（原始）：
```markdown
| 股票、ETFs<br><br>![](https://openapi.futunn.com/futu-api-doc/img/tip.png)<br><br>含纽交所上市的股票 |
```

**替换后**（规范）：
```markdown
| 股票、ETFs<br>(ℹ️ 含纽交所上市的股票) |
```

规则：
- ℹ️ 图标放在括号内，和补充文字一起被包裹
- 使用英文括号 `()`
- 开始 `(ℹ️` 和结束 `)` 一目了然
- 表格内使用 `<br>` 换行，独立行直接另起一行

### 3.2 超链接：已下载文档使用本地相对路径

文档内部的交叉引用链接，如果目标页面已下载，应替换为本地相对路径：

**替换前**：
```markdown
[交易](https://openapi.futunn.com/futu-api-doc/trade/trade.html)
```

**替换后**：
```markdown
[交易](./trade_trade.md)
```

**不替换的链接类型**（保留原始 URL）：
- 英文/繁体版链接（`/en/`、`/hk/` 路径）
- 图片资源（`/assets/img/`、`/img/`）
- 软件下载链接（`futunn.com/download`、`moomoo.com/download`）
- 行情卡购买链接（`qtcard.moomoo.com`）
- 法律/协议/风险披露页面
- PDF/文档导出链接
- 其他外部业务页面

---

## 4. 踩坑记录

### 4.1 download 命令导致文件格式不一致

**问题**：使用 `firecrawl download` 一次性下载 82 个页面，结果产生了两种不同格式的文件：

| 格式 | 文件数 | 头部特征 |
|------|--------|---------|
| 格式 A（`--only-main-content` 生效） | 32 个 | `# 标题`，内容干净 |
| 格式 B（降级为全量转换） | 43 个 | 以 logo 图片开头，含导航栏菜单 |
| BROKEN（失败） | 4 个 | `Cannot GET /path` |

**原因**：`download` 命令内部批量调用 `scrape`，默认带有 `--only-main-content`。部分页面的主内容提取成功，输出干净；提取失败的页面则降级为全量 HTML 转 Markdown，混入导航栏等噪音。

**教训**：批量下载后必须验证每个文件的格式一致性。推荐使用"发现 URL + 逐个 `scrape`"的分步方式（见 [§2.1](#21-推荐方式发现-url--批量-scrape分步下载)），确保每个页面独立抓取、质量可控。

### 4.2 URL 路径错误导致下载失败

**问题**：以下 URL 在服务器上不存在：

| 错误 URL | 正确 URL |
|----------|----------|
| `/base/base.html` | `/ftapi/common.html` |
| `/base/connection.html` | `/ftapi/init.html` |
| `/base/intro.html` | `/ftapi/protocol.html` |
| `/qa/qa.html` | `/qa/opend.html`、`/qa/quote.html`、`/qa/trade.html`、`/qa/other.html` |

**教训**：发现的 URL 可能包含重定向或过时路径。下载后应检查文件内容是否为有效文档（如检查是否包含 `Cannot GET`）。

### 4.3 tip.png 提示图标的处理

**问题**：原始文档中使用 `![](tip.png)` 图片作为提示图标，下载为 Markdown 后图片链接失效，且在纯文本环境下无法显示。

**解决**：批量替换为 `(ℹ️ 补充文字)` 格式。共处理 67 个文件、1552 处替换。

处理了两种出现形式：
- **表格内**（绝大多数）：`<br><br>![](tip.png)<br><br>补充文字` → `<br>(ℹ️ 补充文字)`
- **独立行**（42 处）：多行 tip 图标块 → 单行 `(ℹ️ 补充文字)`

### 4.4 Firecrawl map 命令对该站点完全失效

**问题**：Firecrawl 的 `map` 命令无论使用何种参数组合，均返回**空数组**（0 个 URL）。

**测试记录**（2026-03-30）：

| 参数 | 结果 |
|------|------|
| `map URL --limit 500` | `{"links": []}` |
| `map URL --sitemap only` | 空 |
| `map URL --sitemap skip` | `{"links": []}` |
| `map URL --search "API"` | 空 |
| `map URL --include-subdomains` | 空 |

**根因分析**：

Firecrawl 的 `map` 命令依赖两种方式发现 URL：

1. **sitemap 解析** — 该站点 `/futu-api-doc/sitemap.xml` 返回 404；根域名 `sitemap.xml` 存在但只含 2 个入口 URL（首页和中文首页），没有文档子页面。
2. **页面链接分析** — 该站点是 VuePress SPA，侧边栏是折叠式组件，未展开分组的链接不在初始 HTML DOM 中。首页 HTML 中仅能发现 6 个同站链接（都是当前展开分组 `intro/` 下的页面）。

而完整的 78 个文档 URL 实际编码在 VuePress 打包的 JS 路由文件 `app.*.js`（约 32MB）中，格式为：

```javascript
{name:"v-xxx",path:"/trade/base.html",component:ia,beforeEnter:(n,e,t)=>{...}}
```

Firecrawl 的 `map` 不解析 JS 文件中的路由配置，因此无法发现这些 URL。

**对比**：

| URL 发现方式 | 发现数量 |
|-------------|---------|
| Firecrawl `map` | **0** ❌ |
| 页面 HTML 中的 `<a href>` | **6**（仅当前展开的侧边栏分组） |
| VuePress JS 路由配置解析 | **78** ✅ |
| 本地已有文件 | **82**（含 4 个不在 JS 路由中的页面） |

**教训**：对于 SPA 类文档站点（VuePress、Docusaurus、GitBook 等），不能依赖 `map` 命令来发现 URL。正确做法是：
1. **首选**：解析框架的 JS 路由配置或数据文件
2. **备选**：使用 `crawl` 命令递归爬取（但也取决于 JS 渲染效果）
3. `map` 只适合有完善 sitemap 或纯静态 HTML 的站点

---

## 5. 维护 Checklist

当需要更新或重新下载文档时，按以下步骤执行：

- [ ] 发现所有 URL：**解析 VuePress JS 路由配置**提取完整 URL 列表（见 [§2.1](#21-推荐方式发现-url--批量-scrape分步下载)）
- [ ] 将发现的 URL 列表与现有文件对比，确认新增/删除页面
- [ ] 使用 `scrape`（不加 `--only-main-content`）逐个下载
- [ ] 检查所有文件是否下载成功（排除 `Cannot GET` 等错误内容）
- [ ] 清理导航栏噪音（logo、语言切换菜单等）
- [ ] 替换 `tip.png` 提示图标为 `(ℹ️ ...)` 格式
- [ ] 将已下载文档的交叉引用链接替换为本地相对路径
- [ ] 验证替换结果：`grep -r 'tip\.png' docs/` 应返回 0 结果
- [ ] 检查中文文件名：如有中文命名的文件，翻译为英文并更新所有引用链接
- [ ] 记录本次使用的 JS 文件名（如 `app.2b5b9942.js`），方便下次对比版本变化
