# IBKR Flex Web Service 同步方案（讨论记录）

> **状态**: 方案讨论完成，待实现  
> **日期**: 2026-03-31  
> **关联**: [overall-design.md](./overall-design.md)

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

### Phase 1（与 Tiger 对齐：API 调通 → 日志输出）

#### 4.1 配置属性类 `IbkrFlexApiProperties`

- `ibkr.flex.token` — Flex Web Service Token
- `ibkr.flex.query-id` — Trade Confirmation Query ID
- `ibkr.flex.base-url` — Base URL（默认 `https://ndcdyn.interactivebrokers.com/AccountManagement/FlexWebService`）

#### 4.2 Flex Web Service HTTP 客户端 `IbkrFlexClient`

- `sendRequest(token, queryId)` → 解析 XML → 返回 ReferenceCode
- `getStatement(token, referenceCode)` → 返回原始报告内容
- 内置重试逻辑（处理 1019 "generation in progress" 错误）
- 必须设置 `User-Agent` header

#### 4.3 交易记录模型 `IbkrTradeRecord`

- 映射 Flex Query Trade Confirmation 字段（symbol、side、quantity、price、commission、tradeDate 等）

#### 4.4 同步适配器 `IbkrSyncAdapter`

- 实现 `BrokerSyncAdapter` 接口
- `getBrokerName()` → `"ibkr"`
- `sync(SyncRequest)` → 调用 FlexClient → 解析 XML 报告 → 日志输出

#### 4.5 配置更新

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
- [ ] 同步的日期范围如何与 `SyncRequest` 对接（Flex Query 模板中设置 vs 动态指定）

---

## 7. 参考资料

- IBKR Flex Web Service 官方文档：项目 `external-resource/ibkr-api/` 目录下的相关文件
- Tiger 适配器实现：`backend/src/main/java/com/localledger/sync/adapter/tiger/TigerSyncAdapter.java`
- 同步核心接口：`backend/src/main/java/com/localledger/sync/core/BrokerSyncAdapter.java`
