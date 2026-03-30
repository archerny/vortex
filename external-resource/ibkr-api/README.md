# IBKR (Interactive Brokers) API 文档 - 下载与维护指南

## 概述

本目录存放从 [IBKR API 官方文档](https://www.interactivebrokers.com/campus/ibkr-api-page/ibkr-api-home/) 下载的离线 Markdown 文档，供项目内部引用。

- **文档来源**: `https://www.interactivebrokers.com/campus/ibkr-api-page/`
- **OpenAPI 规范来源**: `https://api.ibkr.com/gw/api/v3/api-docs`
- **存放路径**: `docs/`（按 API 类型分子目录）

### 目录结构

```
scripts/
├── convert_twsapi_html.py          # TWS API 专属：HTML→Markdown 转换器（BeautifulSoup）
docs/
├── index.md                        # 总目录导航（所有 API 类型的入口）
├── general/                        # 通用文档（所有 API 类型共享）
│   ├── getting-started.md
│   ├── contracts.md
│   ├── market-data-subscriptions.md
│   └── order-types.md
├── web-api/                        # Web API（RESTful）
│   ├── web-api.md
│   ├── web-api-trading.md
│   ├── web-api-account-management.md
│   ├── webapi-doc.md
│   ├── webapi-ref.md
│   ├── cpapi-v1.md
│   └── openapi-spec.json
├── tws-api/                        # TWS API（Socket 协议）
│   ├── twsapi-doc.md
│   ├── twsapi-ref.md
│   └── protobuf-reference.md
└── third-party/                    # 第三方集成
    ├── third-party-connections.md
    ├── prospective-integrations.md
    └── tradingview.md
```

### IBKR API 文档体系

IBKR 提供多种 API 接入方式：

| API 类型 | 说明 | 目录 | 是否已下载 |
|---------|------|------|-----------|
| **General** | 通用文档（合约、订单类型、市场数据订阅等，所有 API 共享） | `general/` | ✅ |
| **Web API** | 现代 RESTful API，功能最全（交易、账户管理、市场数据） | `web-api/` | ✅ |
| **TWS API** | 面向 Trader Workstation 的 Socket API（Python/Java/C++/C#/VB） | `tws-api/` | ✅ |
| **Third-Party** | 第三方平台集成（TradingView 等） | `third-party/` | ✅ |
| **FIX API** | 机构级 FIX 协议接口 | — | ❌ |
| **Excel API** | Excel 集成（ActiveX/DDE/RTD） | — | ❌ |

### 站点技术特征（影响下载策略）

| 特征 | 说明 |
|------|------|
| **框架** | WordPress（IBKR Campus 教育平台） |
| **页面类型** | 服务端渲染的长页面，部分内容由 JS 动态加载（如 Swagger 参考） |
| **sitemap** | 未测试，但 Firecrawl `map` 命令返回空数组 |
| **导航栏** | 包含完整的 IBKR Campus 侧边栏菜单（Traders' Academy、Webinars 等大量非 API 导航） |
| **页脚** | 包含 Cookie Policy、隐私声明、法律条款、全球子公司信息等大段噪音 |
| **特殊元素** | `Copy Location` 锚点按钮（带尾部反斜杠）、`SHARE Facebook/LinkedIn/X/Email` 分享栏、语言选择器 Tab、社交分享链接头部 |

> ⚠️ Firecrawl `map` 命令对本站点无效（返回空数组），需要通过首页链接提取或手动整理 URL 列表。

---

## 1. 文件命名规则

URL 路径与本地文件名的映射关系：

```
https://www.interactivebrokers.com/campus/ibkr-api-page/{page-slug}/
→ docs/{category}/{page-slug}.md
```

其中 `{category}` 根据页面所属 API 类型决定：`general`、`web-api`、`tws-api`、`third-party`。

示例：

| URL 路径 | 本地路径 | 说明 |
|----------|---------|------|
| `/ibkr-api-page/ibkr-api-home/` | `index.md` | API 文档首页（目录导航） |
| **General** | | |
| `/ibkr-api-page/getting-started/` | `general/getting-started.md` | 入门指南 |
| `/ibkr-api-page/contracts/` | `general/contracts.md` | 合约定义 |
| `/ibkr-api-page/market-data-subscriptions/` | `general/market-data-subscriptions.md` | 市场数据订阅 |
| `/ibkr-api-page/order-types/` | `general/order-types.md` | 订单类型 |
| **Web API** | | |
| `/ibkr-api-page/web-api/` | `web-api/web-api.md` | Web API 概览 |
| `/ibkr-api-page/web-api-trading/` | `web-api/web-api-trading.md` | Web API 交易（核心文档） |
| `/ibkr-api-page/web-api-account-management/` | `web-api/web-api-account-management.md` | Web API 账户管理 |
| `/ibkr-api-page/webapi-doc/` | `web-api/webapi-doc.md` | Web API 长篇文档 |
| `/ibkr-api-page/webapi-ref/` | `web-api/webapi-ref.md` | Web API 参考（从 OpenAPI 自动生成） |
| `/ibkr-api-page/cpapi-v1/` | `web-api/cpapi-v1.md` | Client Portal API v1.0 |
| — | `web-api/openapi-spec.json` | OpenAPI 3.0 规范 |
| **TWS API** | | |
| `/ibkr-api-page/twsapi-doc/` | `tws-api/twsapi-doc.md` | TWS API 开发文档 |
| `/ibkr-api-page/twsapi-ref/` | `tws-api/twsapi-ref.md` | TWS API 类参考 |
| `/ibkr-api-page/protobuf-reference/` | `tws-api/protobuf-reference.md` | ProtoBuf 协议参考 |
| **Third-Party** | | |
| `/ibkr-api-page/third-party-connections/` | `third-party/third-party-connections.md` | 已有第三方集成列表 |
| `/ibkr-api-page/prospective-integrations/` | `third-party/prospective-integrations.md` | 潜在集成 |
| `/ibkr-api-page/tradingview/` | `third-party/tradingview.md` | TradingView 集成 |

特殊情况：
- `ibkr-api-home` → 命名为 `index.md`（首页，放在 `docs/` 根目录）
- `webapi-ref.md` 原始 Swagger 页面无法抓取，改为从 OpenAPI JSON 自动生成 API 端点概览表
- `openapi-spec.json` 直接从 `https://api.ibkr.com/gw/api/v3/api-docs` 下载

---

## 2. 下载最佳实践

### 2.1 推荐方式：手动整理 URL + 批量 scrape

**不要使用 `download` 一键下载**。IBKR Campus 站点包含大量非 API 页面（教育、新闻、播客等），`download` 会抓取大量无关内容。

**推荐流程**：

#### 第一步：整理 URL 列表

由于 Firecrawl `map` 命令对本站点无效，需要通过以下方式获取 URL 列表：

1. **从首页链接提取**：使用 `scrape --format links` 抓取首页所有链接，筛选 `ibkr-api-page` 路径

```bash
node scripts/index.js scrape \
  "https://www.interactivebrokers.com/campus/ibkr-api-page/ibkr-api-home/" \
  $AUTH --format links -o .firecrawl/ibkr-home-links.json

# 筛选 API 页面链接
python3 -c "
import json
with open('.firecrawl/ibkr-home-links.json') as f:
    data = json.load(f)
links = sorted(set(l for l in data['links'] if 'ibkr-api-page' in l and '#' not in l))
for l in links:
    print(l)
" > .firecrawl/ibkr-urls.txt
```

2. **手动补充遗漏页面**：首页链接可能不包含所有页面（如 `web-api-trading`、`web-api-account-management`），需要根据导航结构手动添加。

#### 第二步：批量 scrape

```bash
# 按 API 类型分批下载（推荐）
FIRECRAWL_DATA_DIR=.firecrawl/ibkr-docs node scripts/index.js scrape $AUTH \
  "https://www.interactivebrokers.com/campus/ibkr-api-page/twsapi-doc/" \
  "https://www.interactivebrokers.com/campus/ibkr-api-page/twsapi-ref/" \
  "https://www.interactivebrokers.com/campus/ibkr-api-page/protobuf-reference/"
```

> **注意**：不要加 `--only-main-content` 参数。IBKR 页面的主内容判断可能不准确，宁可拿到完整内容后手动清理噪音。

##### ⚠️ TWS API 文档的特殊处理方式（直接从 HTML 转换）

TWS API 文档（`twsapi-doc.md`）使用 EnlighterJS 代码高亮插件，Firecrawl 转 Markdown 会产生严重质量问题（代码三重复、语言标签丢失、Tab 结构损坏）。**因此 TWS API 文档不使用 Firecrawl 的 Markdown 输出，而是直接从原始 HTML 用 BeautifulSoup 转换**。

**流程**：

1. **通过 Firecrawl 获取原始 HTML**（请求 `rawHtml` 格式）：

   ```bash
   curl -X POST https://api.firecrawl.dev/v2/scrape \
     -H 'Content-Type: application/json' \
     -H 'Authorization: Bearer fc-YOUR-API-KEY' \
     -d '{
       "url": "https://www.interactivebrokers.com/campus/ibkr-api-page/twsapi-doc/",
       "formats": ["rawHtml"]
     }'
   ```

2. **使用专用脚本转换**（需要 `pip install beautifulsoup4`）：
   ```bash
   python3 scripts/convert_twsapi_html.py .firecrawl/twsapi-raw.html docs/tws-api/twsapi-doc.md
   ```

3. **转换完成后无需其他后处理**，脚本一步到位输出：TOC 导航目录、带语言标签的代码块、Tab 分组、图片（含 lazy-loading `data-src`）、零噪音。

#### 第三步：复制到目标目录

将 `.firecrawl/ibkr-docs/` 下的文件复制到对应的子目录并重命名：

```bash
cp .firecrawl/ibkr-docs/interactivebrokers.com-campus-ibkr-api-page-twsapi-doc.md \
   external-resource/ibkr-api/docs/tws-api/twsapi-doc.md
# ... 其余类似
```

#### 第四步：下载 OpenAPI 规范

IBKR 的 Swagger 参考页面由 JS 动态渲染，无法通过 Firecrawl 抓取。但可以直接下载底层的 OpenAPI 3.0 规范：

```bash
curl -s "https://api.ibkr.com/gw/api/v3/api-docs" -o docs/web-api/openapi-spec.json
```

然后从 JSON 规范自动生成可读的 API 端点概览 Markdown（参见 [第三步后处理脚本](#23-openapi-规范处理脚本)）。

#### 第五步：后处理（统一格式）

下载完成后，对每个文件执行以下后处理。

> ⚠️ **TWS API 文档（`twsapi-doc.md`）不走此通用后处理流程**，而是在第二步中使用 `scripts/convert_twsapi_html.py` 直接从原始 HTML 生成最终 Markdown，无需额外清理。以下步骤仅适用于其他文档。

1. **清理头部导航噪音**（⚠️ 这是最复杂的部分）
   - 标准情况：查找 `SHARE Facebook` 行，删除该行及其上方的所有导航内容
   - **特殊情况**：部分页面（如首页 `ibkr-api-home`）没有 `SHARE` 标记行，导致头部清理跳过。**必须单独处理此类页面**，手动提取正文内容
   - 要清理的内容包括：`[**NEW** Interactive Options Course]`、`IBKR Home`、`Why IB`、`FREE TRIAL`、`Log In`、`Open Account`、`Open Navigation`、`Close Navigation`、完整的 IBKR Campus 侧边栏菜单

2. **清理头部社交分享链接**：部分页面在 `SHARE` 行清除后，头部残留 `[LinkedIn](...)` `[X](...)` `[Email](...)` 等社交分享链接行，需要额外清理

3. **清理 UI 元素**（⚠️ 注意格式变体）
   - `###### Copy Location` — 注意有三种变体：
     - `###### Copy Location\n`（无尾部反斜杠）
     - `###### Copy Location\`（带尾部反斜杠）
     - `###### Copy Location`（行首无前导换行，出现在某些文件中）
   - `Full Search` / `Search` / `SearchSearch` 搜索框
   - `Filter` 独立行（出现在部分文档开头）
   - `Loading ...` 行（JS 渲染失败残留）

4. **清理页脚噪音**：去除 `IBKR Campus Newsletters` 订阅区域、Cookie Policy、隐私偏好设置、法律声明、全球子公司信息、`reCAPTCHA` 声明、`Take me to IBKR.com` 弹窗、`Interactive Brokers® and IB SM are` 声明

6. **清理尾部推广内容**：部分页面尾部有 `Latest IBKR Quant Articles`、`IBKR API Training Courses` 等推广区块（带 Base64 嵌入图片），需一并清除

7. **替换超链接**：将指向已下载文档的链接替换为本地相对路径。**注意**：由于目录结构分层，链接路径需要根据当前文件所在子目录计算相对路径（同目录用 `./`，跨目录用 `../`）

8. **中文文件名翻译为英文**：如果下载的文档文件名包含中文，将文件名翻译为对应的英文

9. **收缩连续空行**：将 3 行以上的连续空行压缩为 2 行

### 2.2 后处理脚本参考

以下 Python 脚本可用于批量后处理（**完整版，支持多级子目录**）：

```python
import os, re, glob

docs_root = "external-resource/ibkr-api/docs"

# 递归查找所有 .md 文件
for filepath in sorted(glob.glob(os.path.join(docs_root, "**", "*.md"), recursive=True)):
    with open(filepath, 'r') as f:
        content = f.read()
    lines = content.split('\n')
    fname = os.path.relpath(filepath, docs_root)  # e.g. "tws-api/twsapi-doc.md"
    dir_name = os.path.dirname(fname)  # e.g. "tws-api"
    
    # === STEP 1: 清理头部导航 ===
    share_idx = None
    for i, line in enumerate(lines):
        if line.startswith('SHARE') and ('Facebook' in line or 'LinkedIn' in line):
            share_idx = i
            break
    if share_idx is not None:
        lines = lines[share_idx + 1:]
    
    # === STEP 1b: 清理头部社交分享链接 ===
    content_start = 0
    for i, line in enumerate(lines):
        stripped = line.strip()
        if stripped == '':
            continue
        if re.match(r'^\[?(LinkedIn|Facebook|X|Email)\]?\(', stripped):
            content_start = i + 1
            continue
        break
    if content_start > 0:
        while content_start < len(lines) and lines[content_start].strip() == '':
            content_start += 1
        lines = lines[content_start:]
    
    # === STEP 2: 清理 UI 噪音 ===
    content = '\n'.join(lines)
    content = re.sub(r'\n###### Copy Location\\?\s*\n', '\n', content)
    content = re.sub(r'\n###### Copy Location\\?\\\s*\n', '\n', content)
    content = re.sub(r'^###### Copy Location\s*$', '', content, flags=re.MULTILINE)
    content = re.sub(r'\n(Python(?:Java|cURL|C\+\+|C#|VB|JavaScript|Node\.js)+)\n', '\n', content)
    content = re.sub(r'\nFilter\n', '\n', content)
    content = re.sub(r'\nLoading \.\.\.\n', '\n', content)
    content = re.sub(r'\n\[Log In\]\([^)]*\)\n', '\n', content)
    content = re.sub(r'\n\[Open Account\]\([^)]*\)\n', '\n', content)
    content = re.sub(r'\n(?:Open|Close) Navigation\n', '\n', content)
    content = re.sub(r'\nFull Search\n\n Search\n', '\n', content)
    content = re.sub(r'\n Search\n', '\n', content)
    content = re.sub(r'\nSearchSearch\n', '\n', content)
    
    # === STEP 3: 清理页脚 ===
    lines = content.split('\n')
    footer_markers = [
        'IBKR Campus Newsletters',
        'This site is protected by reCAPTCHA',
        'IBKR CAMPUS LOG IN',
        '##### Take me to IBKR.com',
        'Interactive Brokers® and IB SM are',
    ]
    footer_start = None
    for i, line in enumerate(lines):
        for marker in footer_markers:
            if marker in line:
                j = i
                while j > 0 and lines[j-1].strip() == '':
                    j -= 1
                footer_start = j
                break
        if footer_start is not None:
            break
    if footer_start is not None:
        lines = lines[:footer_start]
    
    # === STEP 4: 清理尾部推广 ===
    content = '\n'.join(lines)
    for promo in ['### Latest IBKR Quant Articles', '### IBKR API Training Courses']:
        idx = content.find(promo)
        if idx > 0:
            content = content[:idx]
    
    # === STEP 5: 替换链接（支持跨目录相对路径） ===
    url_to_local = {
        # (url, local_filename, target_dir)
        "https://www.interactivebrokers.com/campus/ibkr-api-page/ibkr-api-home/": ("index.md", ""),
        "https://www.interactivebrokers.com/campus/ibkr-api-page/getting-started/": ("getting-started.md", "general"),
        "https://www.interactivebrokers.com/campus/ibkr-api-page/contracts/": ("contracts.md", "general"),
        # ... 完整映射表，包含所有已下载页面 ...
    }
    for url, (local_file, target_dir) in url_to_local.items():
        if url in content:
            if dir_name == target_dir:
                rel_path = f"./{local_file}"
            elif dir_name == "":
                rel_path = f"./{target_dir}/{local_file}" if target_dir else f"./{local_file}"
            elif target_dir == "":
                rel_path = f"../{local_file}"
            else:
                rel_path = f"../{target_dir}/{local_file}"
            content = content.replace(url, rel_path)
    
    # === STEP 6: 收缩空行、去首尾 ===
    content = re.sub(r'\n{4,}', '\n\n\n', content)
    content = content.strip() + '\n'
    
    with open(filepath, 'w') as f:
        f.write(content)
```

### 2.3 OpenAPI 规范处理脚本

从 OpenAPI JSON 自动生成 `web-api/webapi-ref.md`（API 端点概览表）：

```python
import json

with open("docs/web-api/openapi-spec.json") as f:
    spec = json.load(f)

# 按 tag 分组所有端点
tag_paths = {}
for path, methods in sorted(spec["paths"].items()):
    for method, detail in methods.items():
        if method in ("get", "post", "put", "delete", "patch"):
            for tag in detail.get("tags", ["Untagged"]):
                tag_paths.setdefault(tag, []).append({
                    "method": method.upper(),
                    "path": path,
                    "summary": detail.get("summary", ""),
                })

# 输出为 Markdown 表格
for tag_name, endpoints in tag_paths.items():
    print(f"#### {tag_name}\n")
    print("| Method | Path | Summary |")
    print("|--------|------|---------|")
    for ep in endpoints:
        print(f"| `{ep['method']}` | `{ep['path']}` | {ep['summary']} |")
    print()
```

### 2.4 各模式对比

| 模式 | 内容完整性 | 输出干净度 | 适用场景 |
|------|-----------|-----------|---------|
| `scrape`（默认） | ✅ 完整 | ⚠️ 含大量导航噪音 | **推荐，逐页下载** |
| `scrape --only-main-content` | ⚠️ 可能丢内容 | ✅ 较干净 | 不推荐（主内容提取不稳定） |
| `download` | ✅ 完整 | ⚠️ 会下载大量非 API 页面 | 不推荐 |

---

## 3. 文档格式规范

### 3.1 超链接：已下载文档使用本地相对路径

文档内部的交叉引用链接，如果目标页面已下载，应替换为本地相对路径。**注意跨目录时的相对路径计算**：

**同目录引用**（如 `tws-api/twsapi-doc.md` 引用 `tws-api/twsapi-ref.md`）：
```markdown
[TWS API Reference](./twsapi-ref.md)
```

**跨目录引用**（如 `tws-api/twsapi-doc.md` 引用 `general/contracts.md`）：
```markdown
[Contracts](../general/contracts.md)
```

**从根目录引用**（如 `index.md` 引用 `web-api/web-api.md`）：
```markdown
[Web API](./web-api/web-api.md)
```

**不替换的链接类型**（保留原始 URL）：
- 指向未下载页面的链接（Excel、FIX 等）
- `ibkrcampus.com` 域名的链接（某些文档交叉引用使用此短域名）
- `ibkrguides.com` 域名的链接（用户指南）
- 图片资源链接
- 外部业务页面（开户、登录等）

### 3.2 已知的内容限制

| 文件 | 限制说明 |
|------|---------|
| `web-api/webapi-ref.md` | 原始 Swagger 页面无法抓取；已改为从 OpenAPI JSON 自动生成 API 端点概览 |
| `web-api/openapi-spec.json` | 直接从 `api.ibkr.com` 下载的 OpenAPI 3.0 规范（756KB, 152 端点, 426 模型），可导入 Postman/Swagger UI |
| `web-api/cpapi-v1.md` | Client Portal API v1.0 文档，内容非常长（约 29000 行），包含完整的端点定义 |
| `general/order-types.md` | 订单类型文档，内容非常长（约 18000 行），包含所有订单类型的详细说明 |
| `tws-api/twsapi-doc.md` | TWS API 完整开发文档（约 14000 行），涵盖安装、配置、架构、Python 同步 API 等。由 `scripts/convert_twsapi_html.py` 直接从原始 HTML 转换生成（836 个代码块、180 个 Tab 分组、37 张图片、360 条 TOC 导航） |

---

## 4. 踩坑记录

### 4.1 Firecrawl map 命令返回空数组

**问题**：Firecrawl `map` 命令对 IBKR Campus 站点返回空数组（0 个 URL）。

**原因分析**：IBKR Campus 是 WordPress 站点，侧边栏导航可能由 JS 动态渲染。`map` 命令依赖 sitemap 或静态 HTML 链接发现 URL，对本站点不适用。

**解决方案**：使用 `scrape --format links` 从首页提取链接，再手动补充遗漏页面。

### 4.2 Swagger 参考页面无法抓取（已解决 ✅）

**问题**：`webapi-ref`（Web API Reference）页面的实际内容是嵌入式 Swagger UI，由 JS 动态渲染。Firecrawl 抓取后仅得到 `Loading ...`。

**解决方案**：
1. 发现 IBKR 的 OpenAPI 3.0 规范可以从 `https://api.ibkr.com/gw/api/v3/api-docs` 直接下载
2. 下载完整的 OpenAPI JSON 规范保存为 `web-api/openapi-spec.json`
3. 从 JSON 自动生成可读的 API 端点概览 Markdown 作为 `web-api/webapi-ref.md`

> 💡 **经验**：Swagger UI 页面无法爬取时，尝试直接访问底层的 OpenAPI 规范端点。常见路径：`/v3/api-docs`、`/swagger.json`、`/openapi.json`

### 4.3 首页（index 页面）没有 SHARE 标记行

**问题**：后处理脚本通过 `SHARE Facebook` 行判断正文起始位置。但首页没有 `SHARE` 行，导致头部噪音未被清理。

**解决方案**：对没有 `SHARE` 标记的页面单独手动处理，或增加备用标记（如第一个 `#` 标题行）。

### 4.4 Copy Location 有多种格式变体

**问题**：`###### Copy Location` 在不同文件中至少有三种格式：无尾部反斜杠、带尾部反斜杠、行首出现（无前导换行）。

**解决方案**：同时使用 `\n` 前缀匹配和 `re.MULTILINE` 行首匹配，确保所有变体被覆盖。

### 4.5 头部社交分享链接残留

**问题**：`SHARE` 行被清除后，紧接着的 `[LinkedIn](...)` `[X](...)` `[Email](...)` 社交分享链接行仍然残留在文件头部。

**解决方案**：在清除 `SHARE` 行后，额外检查头部是否有社交分享链接行（匹配 `[LinkedIn]`、`[X]`、`[Email]`、`[Facebook]` 开头的行），一并清除。

### 4.6 页面导航噪音极重

**问题**：每个页面都包含完整的站点导航菜单（约 500 行）+ 页脚（约 250 行），噪音占比 30%-50%。

**解决**：关键标记：
- 头部结束标记：`SHARE Facebook`
- 尾部开始标记：`IBKR Campus Newsletters`、`This site is protected by reCAPTCHA`、`Interactive Brokers® and IB SM are`

### 4.7 TWS API 文档使用 HTML 直转方案

**经验**：当 Firecrawl 对特定页面（如使用 EnlighterJS 代码高亮插件的页面）转换质量差时，**直接从原始 HTML 用 BeautifulSoup 转换比试图修补 Firecrawl 的输出更高效可靠**。

TWS API 文档的 EnlighterJS 代码高亮导致 Firecrawl 输出存在代码三重复、语言标签丢失、Tab 结构损坏等问题。最终方案是 `scripts/convert_twsapi_html.py` 直接解析 HTML，从 `<pre class="EnlighterJSRAW">` 提取代码和 `data-enlighter-language` 属性获取语言标签，一步到位。

**EnlighterJS HTML 结构参考**（脚本的解析依据）：

```html
<pre class="EnlighterJSRAW" data-enlighter-language="python" data-enlighter-group="group-xxx">
  self.reqAccountUpdates(True, self.account)
</pre>
```

关键属性：`data-enlighter-language`（语言）、`data-enlighter-group`（语言组 ID）、`data-enlighter-title`（显示名）。

页面结构：358 个 `<section class="api-block-N">`、180 个 `tab-block` 多语言代码 Tab、图片 lazy-loading 使用 `data-src`。

### 4.8 跨目录链接替换需计算相对路径（Firecrawl 通用）

**问题**：重构为多级子目录后，链接替换不能再使用简单的 `./filename.md`，需要根据当前文件和目标文件的目录关系计算相对路径。

**解决方案**：链接替换逻辑中引入 `dir_name`（当前文件目录）和 `target_dir`（目标文件目录），动态计算 `./`、`../` 等相对路径前缀。

---

## 5. 文档内容概览

### General（通用，`general/`）

| 文件 | 说明 |
|------|------|
| `getting-started.md` | 入门指南：账户要求、访问权限 |
| `contracts.md` | 合约定义、合约搜索、Contract Database |
| `market-data-subscriptions.md` | 市场数据订阅说明 |
| `order-types.md` | 所有订单类型详细说明（限价单、止损单、算法订单等） |

### Web API（`web-api/`）

| 文件 | 说明 |
|------|------|
| `web-api.md` | Web API 概览（架构、认证、组件介绍） |
| `web-api-trading.md` | **核心文档** - 交易功能（市场数据、订单管理、投资组合、通知） |
| `web-api-account-management.md` | 账户管理（注册、维护、认证、资金、报告） |
| `webapi-doc.md` | Web API 长篇文档（最佳实践、流程图、会话管理） |
| `webapi-ref.md` | Web API 端点概览（从 OpenAPI 规范自动生成，152 个端点） |
| `openapi-spec.json` | **完整的 OpenAPI 3.0 规范**（756KB, 152 端点, 426 数据模型） |
| `cpapi-v1.md` | Client Portal API v1.0 完整端点文档 |

### TWS API（`tws-api/`）

| 文件 | 说明 |
|------|------|
| `twsapi-doc.md` | **核心文档** - TWS API 完整开发文档（安装、配置、Socket 协议、EWrapper/EClient 架构、Python 同步 API、账户数据、市场数据、订单管理） |
| `twsapi-ref.md` | TWS API 类参考（AccountSummaryTags、Contract、Order、EClient、EWrapper 等核心类定义）⚠️ Beta 阶段 |
| `protobuf-reference.md` | ProtoBuf 协议参考 |

### Third-Party Integrations（`third-party/`）

| 文件 | 说明 |
|------|------|
| `third-party-connections.md` | 已有第三方集成列表（含各平台支持的功能矩阵） |
| `prospective-integrations.md` | 潜在集成说明 |
| `tradingview.md` | TradingView 集成详细文档 |

### 服务器环境（Web API）

| 环境 | 地址 |
|------|------|
| **Production** | `https://api.ibkr.com` |
| **Sandbox** | `https://qa.interactivebrokers.com` |

---

## 6. 维护 Checklist

当需要更新或重新下载文档时，按以下步骤执行：

### 文档页面更新
- [ ] 使用 `scrape --format links` 从首页获取最新的页面链接列表
- [ ] 手动补充首页链接中缺失的页面
- [ ] 将发现的 URL 列表与现有文件对比，确认新增/删除页面
- [ ] 使用 `scrape`（不加 `--only-main-content`）逐个下载
- [ ] 将下载的文件复制到对应的子目录（`general/`、`web-api/`、`tws-api/`、`third-party/`）
- [ ] 检查所有文件是否下载成功（排除空内容或仅含 `Loading...` 的文件）

### 后处理（⚠️ 按顺序执行，注意特殊情况）
- [ ] **⚠️ TWS API 文档**：使用 `scripts/convert_twsapi_html.py` 从原始 HTML 直接转换（无需通用后处理）
  ```bash
  python3 scripts/convert_twsapi_html.py .firecrawl/twsapi-raw.html docs/tws-api/twsapi-doc.md
  ```
- [ ] **其他文档**：清理头部导航噪音（IBKR Campus 完整侧边栏菜单）
- [ ] **⚠️ 检查是否有页面缺少 `SHARE` 标记行**（如首页），对这些页面单独处理
- [ ] **清理头部社交分享链接**（`[LinkedIn]`、`[X]`、`[Email]` 等）
- [ ] 清理页脚噪音（Cookie Policy、法律声明、全球子公司信息）
- [ ] 清理 UI 元素：`Copy Location`（**三种变体**）、`SHARE`、`Full Search`
- [ ] 清理独立 `Filter` 行和 `Loading ...` 行
- [ ] 清理尾部推广内容（`Latest IBKR Quant Articles`、`Training Courses` 等）
- [ ] 将已下载文档的交叉引用链接替换为本地相对路径（**注意跨目录相对路径计算**）
- [ ] 验证替换结果：`grep -rc 'ibkr-api-page' docs/` 中，剩余的应该都是指向未下载页面的链接
- [ ] 压缩连续空行（3+ 行 → 2 行）

### OpenAPI 规范更新
- [ ] 从 `https://api.ibkr.com/gw/api/v3/api-docs` 下载最新的 OpenAPI JSON
- [ ] 比对版本号（当前: 2.28.0），确认是否有新增端点
- [ ] 重新生成 `web-api/webapi-ref.md` 的 API 端点概览表

### 最终验证
- [ ] `grep -rc "Copy Location" docs/` 应全部为 0
- [ ] `grep -rc "PythonJava\|PythoncURL" docs/` 应全部为 0
- [ ] `grep -rc "SHARE Facebook" docs/` 应全部为 0
- [ ] `grep -rc "EnlighterJS 3 Syntax Highlighter" docs/` 应全部为 0
- [ ] TWS API 文档验证：`grep -c "^<!-- tabs:start -->" docs/tws-api/twsapi-doc.md` 应为 180（Tab 分组数）
- [ ] TWS API 文档验证：`grep -c "^\`\`\`\w" docs/tws-api/twsapi-doc.md` 应为 836（带语言标签的代码块数）
- [ ] `head -5` 检查 `twsapi-doc.md` 第一行为 `# TWS API Documentation`，第二行后为 TOC 导航
- [ ] `head -2` 检查其他每个文件第一行都是有意义的标题
- [ ] 如需扩展下载范围（FIX、Excel 等），创建对应子目录按相同流程处理
