# Longbridge API 文档 - 下载与维护指南

## 概述

本目录存放从 [Longbridge OpenAPI 官方文档](https://open.longbridge.com/zh-CN/docs) 下载的离线 Markdown 文档，供项目内部引用。

- **文档来源**: `https://open.longbridge.com/zh-CN/docs`
- **文件数量**: 85 个 .md 文件（以实际下载结果为准）
- **存放路径**: `docs/`

### 站点技术特征（影响下载策略）

| 特征 | 说明 |
|------|------|
| **框架** | VitePress（Vue.js SSG/SPA） |
| **域名** | `open.longbridge.com`（另有 `open.longportapp.com` 域名，部分地区可能不可达） |
| **sitemap** | 不存在（`/sitemap.xml` 和 `/zh-CN/sitemap.xml` 均返回 404） |
| **robots.txt** | 不存在 |
| **反爬保护** | 有 anti-bot 机制，批量请求时随机触发，重试即可成功 |
| **侧边栏** | 服务端渲染，所有文档链接可从首页 HTML 中提取 |
| **首页** | `https://open.longbridge.com/zh-CN/docs` 为 OpenAPI 介绍页 |

> ⚠️ 站点有 anti-bot 保护，批量 scrape 时**个别页面会随机失败**（约 5%），重试即可。Firecrawl 的 `map` 命令对该站点**基本无效**（仅返回 1 个 URL）。

---

## 1. 文件命名规则

URL 路径与本地文件名的映射关系：

```
https://open.longbridge.com/zh-CN/docs/{path}
→ docs/{path 中的 / 替换为 -}.md
```

示例：

| URL 路径 | 本地文件名 |
|----------|-----------|
| `/docs` | `index.md` |
| `/docs/getting-started` | `getting-started.md` |
| `/docs/quote/pull/quote` | `quote-pull-quote.md` |
| `/docs/trade/order/submit` | `trade-order-submit.md` |
| `/docs/socket/protocol/overview` | `socket-protocol-overview.md` |

规则说明：
- 首页 `/docs` → `index.md`
- 其余页面：去掉 `/zh-CN/docs/` 前缀，将路径中的 `/` 替换为 `-`，加 `.md` 后缀
- 原始 URL 中的 `-` 和 `_` 保持不变

---

## 2. 下载最佳实践

### 2.1 推荐方式：从首页侧边栏提取 URL + 逐个 scrape

**推荐流程**：

#### 第一步：发现所有 URL

> ⚠️ **Firecrawl 的 `map` 命令对本站点基本无效**（仅返回 1 个 URL），因为无 sitemap 且首页内容被 anti-bot 保护。

**正确做法：先 scrape 首页（可能需要多次重试），然后从首页内容中提取侧边栏链接**：

```bash
# 1. 抓取首页内容（可能被 anti-bot 拦截，重试即可）
node scripts/index.js scrape \
  "https://open.longbridge.com/zh-CN/docs" \
  $AUTH \
  -o .firecrawl/longbridge-docs-index.md

# 2. 从首页内容中提取所有文档 URL
grep -o 'https://open\.longbridge\.com/zh-CN/docs[^)"#]*' \
  .firecrawl/longbridge-docs-index.md \
  | grep -v '#' | sort -u > .firecrawl/longbridge-urls.txt

# 3. 检查发现的 URL 数量
wc -l .firecrawl/longbridge-urls.txt
```

> 📌 长桥文档站使用 VitePress 框架，侧边栏链接在服务端渲染，所以可以直接从首页 HTML 中提取完整的链接列表。这比富途（VuePress SPA）的情况简单得多。
>
> 📌 如果首页被 anti-bot 拦截，多重试几次即可（通常 1-3 次内会成功）。

#### 第二步：批量 scrape（不加 --only-main-content）

```bash
# 批量并行（推荐）
FIRECRAWL_DATA_DIR=<dataDir> node scripts/index.js scrape $AUTH \
  https://open.longbridge.com/zh-CN/docs/getting-started \
  https://open.longbridge.com/zh-CN/docs/quote/overview \
  https://open.longbridge.com/zh-CN/docs/trade/trade-overview
```

> **关键**：
> - 不要加 `--only-main-content` 参数
> - 每批 15-20 个 URL，分批执行
> - 个别页面可能被 anti-bot 拦截（约 5%），记录失败的 URL 后单独重试

#### 第三步：重试失败的页面

```bash
# 对失败的页面逐个重试
node scripts/index.js scrape \
  "https://open.longbridge.com/zh-CN/docs/<failed-page>" \
  $AUTH \
  -o .firecrawl/longbridge-docs/<filename>.md
```

> 💡 anti-bot 拦截是随机的，同一个 URL 重试通常就能成功。

#### 第四步：后处理（统一格式）

下载完成后，对每个文件执行以下后处理：

1. **清理导航栏噪音**：去除 logo、搜索框、语言切换、Sidebar Navigation 列表、页面 TOC 目录等非正文内容
2. **清理页脚导航**：去除 "上一页"/"下一页" 翻页链接
3. **替换超链接**：将指向已下载文档的链接替换为本地相对路径
4. **中文文件名翻译为英文**：如果下载的文档文件名包含中文，将文件名翻译为对应的英文（如 `期权工具.md` → `option-tools.md`），同时更新所有引用了该文件名的本地链接。中文文件名不利于统一管理和跨平台兼容。

**导航栏清理策略**：

长桥文档的正文标题使用 setext 风格（标题文字后跟 `====` 下划线），正文前的所有内容（logo、导航栏、侧边栏、TOC）都应清除。具体规则：

```
导航栏噪音特征：
- "[跳转到内容]" 链接
- logo 图片（logo-without-title-lb.svg）
- "搜索文档" 文本
- "Sidebar Navigation" 标记
- 侧边栏链接列表
- 页面 TOC（指向本页锚点的链接列表）

正文起始标志（setext H1）：
标题文字
====

或：
标题文字 [​](url#anchor)

====
```

### 2.2 各模式对比

| 模式 | 内容完整性 | 反爬影响 | 适用场景 |
|------|-----------|---------|---------|
| `scrape`（默认） | ✅ 完整 | ⚠️ 个别失败，重试即可 | **推荐，逐页下载** |
| `scrape --only-main-content` | ❌ 可能丢内容 | ⚠️ 同上 | 不推荐 |
| `download` | ⚠️ 部分降级 | ⚠️ 失败率更高 | 不推荐 |
| `map` | ❌ 仅返回 1 个 URL | - | 无效 |

---

## 3. 文档格式规范

### 3.1 超链接：已下载文档使用本地相对路径

文档内部的交叉引用链接，如果目标页面已下载，应替换为本地相对路径：

**替换前**：
```markdown
[委托下单](https://open.longbridge.com/zh-CN/docs/trade/order/submit)
```

**替换后**：
```markdown
[委托下单](./trade-order-submit.md)
```

**不替换的链接类型**（保留原始 URL）：
- 英文版/繁体版链接（`/docs/` 不含 `/zh-CN/`、`/zh-HK/` 路径）
- GitHub 仓库链接（`github.com/longbridge/`）
- SDK 下载/文档链接
- 外部网站链接
- 锚点链接（保留，但 URL 部分替换为本地路径）

---

## 4. 踩坑记录

### 4.1 anti-bot 保护导致批量下载随机失败

**问题**：使用 Firecrawl `scrape` 批量下载时，每批约 5% 的页面被 anti-bot 机制拦截，返回错误 "Document scrape was prevented by anti-bot"。

**表现**：
- 失败页面是随机的，每次运行失败的不同
- 同一页面重试通常能成功
- 单独 scrape 单个 URL 时也可能触发

**解决**：
- 记录每批失败的 URL
- 对失败的 URL 单独重试（逐个或小批量）
- 通常 1-2 次重试即可全部成功

**教训**：长桥文档站有 anti-bot 保护，下载脚本必须有重试机制。建议每批处理后检查结果，自动重试失败的 URL。

### 4.2 Firecrawl map 命令对该站点基本无效

**问题**：Firecrawl 的 `map` 命令仅返回 1 个 URL（首页自身）。

**测试记录**（2026-03-30）：

| 参数 | 结果 |
|------|------|
| `map URL --limit 500` | 1 个 URL |
| `map URL --search "API"` | 1 个 URL |

**根因**：站点无 sitemap，且 `map` 的页面分析能力受限于 anti-bot 保护。

**替代方案**：从首页 scrape 结果中提取侧边栏链接（长桥使用 VitePress SSG，侧边栏在服务端渲染，链接可从 HTML 中直接提取）。

### 4.3 首页和 longportapp.com 域名不可达

**问题**：
- `open.longportapp.com` 域名通过 Firecrawl 完全无法访问
- `open.longbridge.com/zh-CN/docs` 首页偶尔被 anti-bot 拦截

**解决**：
- 使用 `open.longbridge.com` 域名
- 首页抓取失败时多重试几次

### 4.4 导航栏噪音清理需适配两种标题格式

**问题**：长桥文档的页面标题有两种 setext 格式：

格式 A（标准 setext）：
```markdown
快速开始
====
```

格式 B（带锚点 setext）：
```markdown
交易接口总览 [​](url#anchor)

===============================================================
```

**教训**：导航栏清理的正则需要同时匹配这两种格式。

---

## 5. 维护 Checklist

当需要更新或重新下载文档时，按以下步骤执行：

- [ ] Scrape 首页：获取最新的侧边栏链接列表（可能需要重试）
- [ ] 从首页内容中提取所有文档 URL，与现有文件对比，确认新增/删除页面
- [ ] 使用 `scrape`（不加 `--only-main-content`）分批下载
- [ ] 重试所有失败的页面（anti-bot 导致的随机失败）
- [ ] 检查所有文件是否下载成功
- [ ] 清理导航栏噪音（logo、侧边栏、TOC 等）
- [ ] 清理页脚翻页链接（上一页/下一页）
- [ ] 将已下载文档的交叉引用链接替换为本地相对路径
- [ ] 验证替换结果：`grep -rl 'Sidebar Navigation' docs/` 应返回 0 结果
- [ ] 验证替换结果：`grep -rl 'logo-without-title' docs/` 应返回 0 结果
- [ ] 检查中文文件名：如有中文命名的文件，翻译为英文并更新所有引用链接
