# Tiger OpenAPI 文档 - 下载与维护指南

## 概述

本目录存放从 [老虎开放平台新文档站](https://docs.itigerup.com/docs) 下载的离线 Markdown 文档，供项目内部引用。

- **文档来源**: `https://docs.itigerup.com/docs`（新版文档站，推荐）
- **旧文档站**: `https://quant.itigerup.com/openapi/zh/python/` （VuePress，内容与新站一致）
- **文件数量**: 81 个 .md 文件
- **存放路径**: `docs/`
- **语言覆盖**: Python / Java / C++ 三种 SDK 文档 + 概述 / AI / 使用规范

### 站点技术特征（影响下载策略）

| 特征 | 说明 |
|------|------|
| **框架** | ReadMe.com（托管文档平台） |
| **域名** | `docs.itigerup.com`（新站），`quant.itigerup.com`（旧站） |
| **sitemap** | 不存在（map 命令无效） |
| **反爬保护** | 无（81 个页面全部一次性 scrape 成功，无 anti-bot 拦截） |
| **侧边栏** | 服务端渲染，所有文档链接可从首页 HTML 中提取 |
| **首页** | `https://docs.itigerup.com/docs/intro` 为简介页 |
| **多语言** | 中文文档在 `docs.itigerup.com`，英文在 `docs-en.itigerup.com` |

> ✅ 相比长桥和富途，老虎新文档站下载**非常顺畅**——无反爬、无 anti-bot、无需重试。

---

## 1. 文件命名规则

URL 路径与本地文件名的映射关系：

```
https://docs.itigerup.com/docs/{slug}
→ docs/{slug}.md
```

示例：

| URL 路径 | 本地文件名 |
|----------|-----------|
| `/docs/intro` | `intro.md` |
| `/docs/prepare` | `prepare.md` |
| `/docs/quote-stock` | `quote-stock.md` |
| `/docs/place-order-java` | `place-order-java.md` |
| `/docs/appendix-enum-cpp` | `appendix-enum-cpp.md` |
| `/docs/option-tools` | `option-tools.md` |

规则说明：
- slug 直接作为文件名，加 `.md` 后缀
- URL 编码的中文 slug 解码为中文文件名
- Python 文档不带后缀，Java 文档带 `-java` 后缀，C++ 文档带 `-cpp` 后缀

---

## 2. 下载最佳实践

### 2.1 推荐方式：从首页侧边栏提取 URL + 批量 scrape

#### 第一步：发现所有 URL

> ⚠️ **Firecrawl 的 `map` 命令对本站点无效**（返回 0 个 URL），因为无 sitemap。

**正确做法：scrape 首页/文档入口页面（docs.itigerup.com 根路径会重定向到 intro），然后从内容中提取侧边栏链接**：

```bash
# 1. 抓取文档入口页（任意文档页面都包含完整侧边栏）
node scripts/index.js scrape \
  "https://docs.itigerup.com/" \
  $AUTH \
  -o .firecrawl/tiger-newdocs-index.md

# 2. 从内容中提取所有文档 URL
grep -o 'https://docs\.itigerup\.com/docs/[^)"#]*' \
  .firecrawl/tiger-newdocs-index.md \
  | sed 's/#.*$//' | sed 's/\/$//' | sort -u > .firecrawl/tiger-urls.txt

# 3. 检查发现的 URL 数量
wc -l .firecrawl/tiger-urls.txt
```

> 📌 老虎新文档站使用 ReadMe.com 平台，侧边栏在服务端渲染，链接可直接从首页 HTML 中提取。每个文档页面都包含完整的侧边栏，所以抓任何一个页面都行。

#### 第二步：批量 scrape（不加 --only-main-content）

```bash
# 批量并行（推荐每批 20 个左右）
FIRECRAWL_DATA_DIR=.firecrawl/tiger-docs node scripts/index.js scrape $AUTH \
  https://docs.itigerup.com/docs/intro \
  https://docs.itigerup.com/docs/prepare \
  https://docs.itigerup.com/docs/quote-stock \
  ...
```

> **关键**：
> - 不要加 `--only-main-content` 参数
> - 每批 20 个 URL，分 4 批执行（共 81 个）
> - 老虎文档站无反爬保护，全部一次成功，无需重试

#### 第三步：重命名文件

Firecrawl 默认的文件名格式为 `{domain}-{path}.md`，需要去掉域名前缀：

```bash
# 从 docs.itigerup.com-docs-{slug}.md 重命名为 {slug}.md
# 同时 URL 解码中文文件名
for f in docs.itigerup.com-docs-*.md; do
  newname=$(echo "$f" | sed 's/^docs\.itigerup\.com-docs-//')
  newname=$(python3 -c "import urllib.parse,sys; print(urllib.parse.unquote(sys.argv[1]))" "$newname")
  mv "$f" "$newname"
done
```

#### 第四步：后处理（统一格式）

下载完成后，对每个文件执行以下后处理：

1. **清理导航栏噪音**：去除 `[Jump to Content]`、logo、语言切换、搜索框、完整侧边栏列表
2. **转换正文标题**：将 setext H1（`====` 下划线）转为 ATX H1（`# 标题`）
3. **清理页脚**：去除 `Updated X ago`、翻页链接（`What's Next` 或 prev/next）、`Did this page help you?`、`Ask AI`
4. **替换超链接**：将指向已下载文档的链接替换为本地相对路径
5. **中文文件名翻译为英文**：如果下载的文档文件名包含中文，将文件名翻译为对应的英文（如 `期权工具.md` → `option-tools.md`），同时更新所有引用了该文件名的本地链接。中文文件名不利于统一管理和跨平台兼容。

**导航栏清理策略**：

老虎新文档站的每个页面结构一致：

```
导航栏噪音区域：
- [Jump to Content] 链接
- logo 图片
- Docs / Changelog 链接
- English 语言切换
- 搜索框 (Search / Ask AI / Start typing to search…)
- 完整的侧边栏目录列表（概述/AI/Python/Java/Cpp/使用规范/联系我们）

正文起始标志（setext H1）：
标题文字
====

页脚噪音区域（两种格式）：
格式 A：Updated X ago → * * * → 翻页链接 → Did this page help you?
格式 B：Updated X ago → * * * → What's Next → 链接列表 → Did this page help you?
```

> ⚠️ **注意**：部分页面内容末尾在代码块内，`Updated X ago` 可能带有 `\` 行尾转义符，正则需兼容。

### 2.2 各模式对比

| 模式 | 内容完整性 | 反爬影响 | 适用场景 |
|------|-----------|---------|---------|
| `scrape`（默认） | ✅ 完整 | ✅ 无反爬 | **推荐，逐页下载** |
| `scrape --only-main-content` | ❌ 可能丢内容 | ✅ 无反爬 | 不推荐 |
| `map` | ❌ 返回 0 个 URL | - | 无效 |

---

## 3. 文档格式规范

### 3.1 超链接：已下载文档使用本地相对路径

文档内部的交叉引用链接，如果目标页面已下载，应替换为本地相对路径：

**替换前**：
```markdown
[证券行情](https://docs.itigerup.com/docs/quote-stock)
```

**替换后**：
```markdown
[证券行情](./quote-stock.md)
```

**不替换的链接类型**（保留原始 URL）：
- 英文版链接（`docs-en.itigerup.com`）
- 旧文档站链接（`quant.itigerup.com`）
- 开发者平台链接（`developer.itigerup.com`）
- GitHub 仓库链接
- 外部网站链接
- 锚点链接中的 URL 部分已替换为本地路径

### 3.2 setext 标题转换

原始页面使用 setext 风格 H1 标题（`====` 下划线），后处理统一转为 ATX 风格（`# 标题`）。

H2 标题中的锚点模式（`标题文字\n\n[](url#anchor)\n\n---`）也统一转为 `## 标题文字` 格式。

---

## 4. 文档分类结构

| 分类 | 文件数 | 说明 |
|------|--------|------|
| 概述 | 6 | 简介、账户类型、交易规则、费用、权限、限制 |
| AI | 3 | MCP Server、CLI、Skill 插件 |
| Python SDK | 22 | 快速入门、行情、交易、账户、推送、附录 |
| Java SDK | 21 | 同 Python，带 `-java` 后缀 |
| C++ SDK | 18 | 同 Python，带 `-cpp` 后缀 |
| 使用规范 | 1 | 错误代码 |
| 其他 | 10 | FAQ、联系我们、option-tools等 |

---

## 5. 踩坑记录

### 5.1 新旧文档站选择

**问题**：老虎证券有新旧两个文档站：
- 旧站 `quant.itigerup.com/openapi/zh/{lang}/`：VuePress 框架，按语言分目录
- 新站 `docs.itigerup.com/docs/`：ReadMe.com 平台，推荐使用

**决策**：选择新站，原因：
- 官方推荐（旧站首页有"🚀新文档入口（推荐使用）"提示）
- 新站结构更扁平，URL 更简洁（slug 而非深层路径）
- 新站有 AI 相关文档（MCP、CLI、Skill）
- 新站无反爬保护，下载更顺畅

### 5.2 Firecrawl map 命令无效

**问题**：对新文档站和旧文档站，`map` 命令均返回极少 URL（0-1 个）。

**根因**：站点无 sitemap，ReadMe.com 平台不生成标准 sitemap。

**替代方案**：从首页 scrape 结果中提取侧边栏链接。

### 5.3 页脚格式有两种变体

**问题**：页脚有两种格式，第一轮正则只匹配了其中一种：

格式 A（标准）：
```
Updated 2 months ago
* * *
[上一页](url)
[下一页](url)
```

格式 B（带 What's Next）：
```
Updated 20 days ago
* * *
What's Next
*   [下一页](url)
Did this page help you?
Yes
No
```

还有个别页面内容在代码块中，`Updated ago` 行带有 `\` 行尾转义。

**解决**：第二轮正则扩展，兼容 `What's Next`、`Did this page help you?` 和行尾转义符，最终 81/81 全部清理成功。

### 5.4 URL 编码的中文 slug

**问题**：有一个文档页面的 slug 是中文（`option-tools`），URL 为 `%E6%9C%9F%E6%9D%83%E5%B7%A5%E5%85%B7`。

**解决**：重命名时使用 `urllib.parse.unquote()` 将 URL 编码转换为中文文件名。

---

## 6. 维护 Checklist

当需要更新或重新下载文档时，按以下步骤执行：

- [ ] Scrape 首页/入口页面：获取最新的侧边栏链接列表
- [ ] 提取所有文档 URL，与现有文件对比，确认新增/删除页面
- [ ] 使用 `scrape`（不加 `--only-main-content`）分批下载
- [ ] 重命名文件（去掉域名前缀，URL 解码中文）
- [ ] 清理导航栏噪音（logo、侧边栏、搜索框等）
- [ ] 转换 setext 标题为 ATX 格式
- [ ] 清理页脚（Updated ago、翻页链接、Did this page help you?）
- [ ] 将已下载文档的交叉引用链接替换为本地相对路径
- [ ] 验证：`grep -rl 'Jump to Content' docs/` 应返回 0 结果
- [ ] 验证：`grep -rl 'Start typing to search' docs/` 应返回 0 结果
- [ ] 验证：`grep -rl 'Did this page help' docs/` 应返回 0 结果
- [ ] 检查中文文件名：如有中文命名的文件，翻译为英文并更新所有引用链接
