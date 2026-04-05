# IBKR Flex Web Service 同步方案（讨论记录）

> **状态**: 方案讨论完成，方案已收敛为 Flex-only，待实现  
> **日期**: 2026-03-31  
> **关联**: [overall-design.md](./overall-design.md)  
> **总体规划对应**: 本文档的 Phase 1 对应总体规划（README.md）中的 **Phase 2**（多券商适配阶段）

---

## 1. 方案选型

IBKR 提供多种 API（Client Portal API / TWS API / Flex Web Service），经调研选择 **Flex Web Service** 作为同步方案，原因：

- **无需本地中间件**：不需要运行 Client Portal Gateway 或 TWS，直接通过互联网 HTTPS 调用
- **无外部 SDK 依赖**：纯 Java HTTP + XML 解析即可，用 `java.net.http.HttpClient` + `javax.xml.parsers.DocumentBuilder`
- **可查询任意历史数据**：不受 Client Portal API 的时间范围限制
- **配置简单**：只需 Token + Query ID 两个参数

---

## 2. 整体流程

### 阶段一：用户在 IBKR Client Portal 手动配置（一次性）

1. **登录** [Client Portal](https://portal.interactivebrokers.com)
2. 进入 **Reporting → Flex Queries → Flex Web Service Configuration**
3. **启用 Flex Web Service**，勾选状态 → Save → 获得 **Access Token**（24 位数字，有效期可设 6 小时到 1 年）
4. 创建一个 **Trade Confirmation Flex Query** 模板，配置需要的字段（symbol、side、quantity、price、commission、tradeDate 等）
5. 点击 Flex Query 旁的 **Info** 图标 → 获得 **Query ID**

最终用户手上有两个值：
- `Token`：如 `528191644107458877539776`
- `Query ID`：如 `800969`

### 阶段二：代码调用 Flex Web Service（两步 HTTP 请求）

```
Step 1: GET /SendRequest?t={token}&q={queryId}&v=3
    ↓ 返回 XML，包含 ReferenceCode
    ↓ 等待约 5-20 秒
Step 2: GET /GetStatement?t={token}&q={referenceCode}&v=3
    ↓ 返回报告内容（XML 或 CSV，取决于 Flex Query 配置）
```

**Base URL**: `https://ndcdyn.interactivebrokers.com/AccountManagement/FlexWebService`

### 阶段三：解析报告数据

拿到 Trade Confirmation 报告（XML/CSV），解析出每笔交易记录，日志输出。

---

## 3. 与 Tiger 适配器的对比

| 对比项 | Tiger | IBKR Flex |
|--------|-------|-----------|
| 认证方式 | API Key + Private Key + Account | Token + Query ID |
| 请求方式 | Tiger SDK（封装了 HTTP） | 原生 HTTP GET（两步） |
| 数据范围 | 最多 90 天/窗口，可分页 | 取决于 Flex Query 模板配置（可以是任意历史） |
| 数据格式 | SDK 对象 → Java POJO | XML/CSV → 自行解析 |
| 外部依赖 | Tiger Open API SDK | 无（仅 Java 标准 HTTP + XML 解析） |
| 中间件需求 | 无 | 无 |
| 速率限制 | 未明确 | **每秒 1 次，每分钟 10 次** (per token) |

---

## 4. 实现计划

### 4.1 同步策略决策（2026-04-05 更新）

#### 最终策略

IBKR 同步方案最终收敛为 **Flex-only**：

- **首次同步**：使用 Flex Query 做历史全量回补
- **后续同步**：仍然使用 Flex Query，不再混用 Client Portal Web API
- **同步频率**：按当前设想，后续以**每周一次**的批量同步为主

#### 选择该策略的原因

- **与同步频率匹配**：当前目标不是实时成交展示，而是首次补历史 + 周期性对账，Flex Query 更匹配这种批处理场景
- **避免双通道复杂度**：若混用 Web API，需要额外处理 session / brokerage session、认证链路、字段口径差异和跨来源去重
- **降低漏数风险**：Web API 近期成交接口窗口较短，而每周同步存在任务延迟、失败补跑等情况；统一使用 Flex Query 更稳妥
- **便于后续幂等处理**：只保留一个数据源，有利于后续设计去重键、补跑机制与同步日志

#### 实施建议

- 首次历史同步如数据范围较大，可按**年 / 季度**拆分执行，避免单次报表过大
- 每周同步建议使用**带重叠时间窗**的策略，例如每次回拉最近 14 天或 30 天，再由系统做幂等去重
- 最终以 Flex Query 返回的数据作为权威来源，不引入"临时数据 / 最终数据"双状态模型

### 4.2 日期范围策略（2026-04-06 更新）

#### API 支持的日期模式

Flex Web Service v3 的 `SendRequest` 只支持三种日期模式：

| 模式 | 参数 | 说明 |
|------|------|------|
| 标准模式 | 无日期参数 | 使用 Portal 上 Query 配置的 Period |
| 日期覆盖 | `fd=yyyymmdd&td=yyyymmdd` | 手动指定起止日期，最多 365 天 |
| 周期覆盖 | `p=N` | 最近 N 天，最多 365 天 |

API **没有** `mtd`（Month to Date）、`ytd`（Year to Date）等快捷参数。

Portal 创建 Query 时可以选择 Period 预设（Last Business Day / Month to Date / Year to Date 等），但这些只在"标准模式"下生效。

#### 决策：统一使用一个 Flex Query 模板 + 代码动态计算日期

出于方案统一的角度，**不创建多个 Query 模板**（如一个 MTD、一个 YTD），而是：

- Portal 上只维护 **一个 Flex Query 模板**（字段选择、格式、账户等只管一处）
- 无论历史回填还是增量同步，**统一通过 `fd` + `td` 参数覆盖日期范围**
- Portal 上配置的 Period 值无所谓（会被 `fd`/`td` 覆盖）

#### 代码侧日期计算场景

| 场景 | `fd` | `td` |
|------|------|------|
| 每日/每周增量同步 | 上次同步日期（或带重叠的窗口起点） | 今天 |
| 本月至今 | 本月 1 号 | 今天 |
| 年初至今 | 本年 1 月 1 日 | 今天 |
| 历史回填（指定范围） | 用户指定起始日 | 用户指定结束日 |
| 历史回填（>365 天） | 每段起始日 | 每段结束日（间隔 ≤365 天，需分段请求） |

#### 与 `SyncRequest` 的日期格式对接

`SyncRequest` 统一使用 `yyyy-MM-dd` 格式（如 `2025-01-01`），这是面向调用者的标准格式，Tiger 适配器也用此格式。IBKR Flex API 要求的 `fd`/`td` 格式是 `yyyyMMdd`（如 `20250101`），**格式转换在 `IbkrSyncAdapter` 内部完成**（`LocalDate.format(DateTimeFormatter.BASIC_ISO_DATE)`），`SyncRequest` 层面保持 `yyyy-MM-dd` 不变。

#### 注意事项

- **IBKR API 日期格式**：固定 `yyyyMMdd`（如 `20260406`），由适配器内部从 `SyncRequest` 的 `yyyy-MM-dd` 格式转换
- **最多 365 天**：`fd` 和 `td` 之间不能超过 365 天，超过需分段请求
- **非交易日**：传入非交易日不会报错，只是返回空数据，代码侧无需特殊处理交易日历

### 4.3 Phase 1 实现计划（与 Tiger 对齐：API 调通 → 日志输出）

#### 4.3.1 配置属性类 `IbkrFlexApiProperties`

- `ibkr.flex.token` — Flex Web Service Token
- `ibkr.flex.query-id` — Trade Confirmation Query ID
- `ibkr.flex.base-url` — Base URL（默认 `https://ndcdyn.interactivebrokers.com/AccountManagement/FlexWebService`）

#### 4.3.2 Flex Web Service HTTP 客户端 `IbkrFlexClient`

- `sendRequest(token, queryId)` → 解析 XML → 返回 ReferenceCode
- `getStatement(token, referenceCode)` → 返回原始报告内容
- 内置重试逻辑（处理 1019 "generation in progress" 错误）
- 必须设置 `User-Agent` header

#### 4.3.3 交易记录模型 `IbkrTradeRecord`

- 映射 Flex Query Trade Confirmation 字段（symbol、side、quantity、price、commission、tradeDate 等）

#### 4.3.4 同步适配器 `IbkrSyncAdapter`

- 实现 `BrokerSyncAdapter` 接口
- `getBrokerName()` → `"ibkr"`
- `sync(SyncRequest)` → 调用 FlexClient → 解析 XML 报告 → 日志输出
- **分段请求**：当 `SyncRequest` 的日期范围超过 365 天时，自动按 ≤365 天的窗口拆分为多次请求，逐段调用 FlexClient 并汇总结果（与 Tiger 适配器中 `fetchOrdersInWindows()` 的 90 天窗口拆分逻辑类似）
- **速率限制**：内置 rate limiter，确保请求频率不超过每秒 1 次、每分钟 10 次（per token）。分段请求和两步请求（SendRequest + GetStatement）都会消耗配额，rate limiter 需统一管控。遇到 1006/1018 错误码时自动等待后重试

#### 4.3.5 配置更新

- `application.properties` 添加占位配置
- `application-local.properties.template` 添加模板

---

## 5. 关键技术细节

### 5.1 两步请求间隔策略

SendRequest 后轮询 GetStatement：
- 初始等待 5 秒
- 每次重试间隔 3 秒
- 最多重试 10 次
- 处理 1019 错误码（报告生成中）

### 5.2 XML 响应格式

**SendRequest 响应**：
```xml
<FlexStatementResponse timestamp='...' >
    <Status>Success</Status>
    <ReferenceCode>1234567890</ReferenceCode>
    <Url>https://ndcdyn.interactivebrokers.com/AccountManagement/FlexWebService/GetStatement</Url>
</FlexStatementResponse>
```

**GetStatement 响应（Trade Confirmation 报告）**：
```xml
<FlexQueryResponse queryName="..." type="TCF">
    <FlexStatements count="1">
        <FlexStatement accountId="..." fromDate="..." toDate="...">
            <TradeConfirms>
                <TradeConfirm
                    symbol="AAPL"
                    buySell="BUY"
                    quantity="100"
                    price="150.00"
                    commission="-1.00"
                    tradeDate="20260301"
                    currency="USD"
                    ... />
            </TradeConfirms>
        </FlexStatement>
    </FlexStatements>
</FlexQueryResponse>
```

### 5.3 错误码

| 错误码 | 含义 | 处理方式 |
|--------|------|---------|
| 1003 | Statement generation in progress | 重试 GetStatement |
| 1004 | Statement could not be generated | 终止并报错 |
| 1005 | Invalid token | 检查 Token 配置 |
| 1006 | Too many requests | 降低频率后重试 |
| 1012 | Query not found | 检查 Query ID |
| 1018 | Too many requests (minute limit) | 等待后重试 |
| 1019 | Statement generation in progress (retry later) | 等待后重试 |

### 5.4 速率限制

- 每个 Token 每秒最多 1 次请求
- 每个 Token 每分钟最多 10 次请求
- 代码中需内置 rate limiter

---

## 6. 待确认事项

- [ ] Flex Query 模板具体需要哪些字段（需要对照 `SyncResult` 的数据模型）
- [ ] XML 解析方案选型：DOM 解析 vs SAX 解析 vs JAXB
- [ ] Token 存储方式：当前 properties 文件 vs 后续数据库存储
- [ ] 是否需要支持 Activity Flex Query（除 Trade Confirmation 外的其他报告类型）
- [x] ~~同步的日期范围如何与 `SyncRequest` 对接~~ → **统一使用一个 Query 模板，代码通过 `fd`/`td` 参数动态指定日期范围**（见 4.1 节）

---

## 7. 参考资料

- IBKR Flex Web Service 官方文档：项目 `external-resource/ibkr-api/` 目录下的相关文件
- Tiger 适配器实现：`backend/src/main/java/com/localledger/sync/adapter/tiger/TigerSyncAdapter.java`
- 同步核心接口：`backend/src/main/java/com/localledger/sync/core/BrokerSyncAdapter.java`
