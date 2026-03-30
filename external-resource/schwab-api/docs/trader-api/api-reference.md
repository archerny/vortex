# Schwab Trader API — 端点参考

## 概述

Schwab Trader API 提供两组 REST API：

- **Trader API** (`/trader/v1/`): 账户管理、订单管理、交易历史
- **Market Data API** (`/marketdata/v1/`): 报价、K线、期权链、市场时间、工具搜索

基础 URL: `https://api.schwabapi.com`

所有请求需要在 Header 中携带 `Authorization: Bearer {ACCESS_TOKEN}`。

---

## 一、账户（Accounts）

### 获取账户哈希列表

```
GET /trader/v1/accounts/accountNumbers
```

返回当前 Token 关联的所有账户号和对应的账户哈希值。

> ⚠️ Schwab API 不直接使用原始账户号，而是使用 **Account Hash**（账户哈希）。所有需要指定账户的 API 调用都需要传入账户哈希而非原始账户号。

**响应示例**：

```json
[
  {
    "accountNumber": "123456789",
    "hashValue": "ABC123XYZ"
  }
]
```

### 获取单个账户详情

```
GET /trader/v1/accounts/{accountHash}
```

**可选参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `fields` | string | 额外字段：`positions`（包含持仓信息） |

### 获取所有关联账户

```
GET /trader/v1/accounts
```

返回所有关联账户的余额、持仓和订单信息。

**可选参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `fields` | string | 额外字段：`positions` |

> 注意：此方法不返回账户哈希，需要通过 `GET /trader/v1/accounts/accountNumbers` 获取。

---

## 二、订单（Orders）

### 下单

```
POST /trader/v1/accounts/{accountHash}/orders
```

**请求体**：JSON 格式的订单规范（Order Spec）。详见 [订单构建器参考](./order-builder.md)。

**简单限价买入示例**：

```json
{
  "session": "NORMAL",
  "duration": "DAY",
  "orderType": "LIMIT",
  "price": "190.90",
  "orderLegCollection": [
    {
      "instruction": "BUY",
      "instrument": {
        "assetType": "EQUITY",
        "symbol": "MSFT"
      },
      "quantity": 1
    }
  ],
  "orderStrategyType": "SINGLE"
}
```

**成功响应**：HTTP 201，响应头 `Location` 包含新订单的 URL（可从中提取订单 ID）。

### 预览订单

```
POST /trader/v1/accounts/{accountHash}/previewOrder
```

预览订单（测试是否会被 API 接受），请求体与下单相同。

### 获取特定订单

```
GET /trader/v1/accounts/{accountHash}/orders/{orderId}
```

### 获取账户订单列表

```
GET /trader/v1/accounts/{accountHash}/orders
```

**可选参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `maxResults` | integer | 最大返回数量 |
| `fromEnteredTime` | string | 起始时间（ISO 8601），距今不超过 60 天 |
| `toEnteredTime` | string | 结束时间（ISO 8601） |
| `status` | string | 按状态筛选 |

**订单状态枚举**：

| 状态 | 说明 |
|------|------|
| `AWAITING_PARENT_ORDER` | 等待父订单 |
| `AWAITING_CONDITION` | 等待条件触发 |
| `AWAITING_STOP_CONDITION` | 等待止损条件 |
| `AWAITING_MANUAL_REVIEW` | 等待人工审核 |
| `ACCEPTED` | 已接受 |
| `PENDING_ACTIVATION` | 待激活 |
| `QUEUED` | 排队中 |
| `WORKING` | 执行中 |
| `REJECTED` | 已拒绝 |
| `PENDING_CANCEL` | 待取消 |
| `CANCELED` | 已取消 |
| `PENDING_REPLACE` | 待替换 |
| `REPLACED` | 已替换 |
| `FILLED` | 已成交 |
| `EXPIRED` | 已过期 |
| `NEW` | 新订单 |

### 获取所有关联账户的订单

```
GET /trader/v1/orders
```

参数同上。

### 取消订单

```
DELETE /trader/v1/accounts/{accountHash}/orders/{orderId}
```

### 替换订单

```
PUT /trader/v1/accounts/{accountHash}/orders/{orderId}
```

取消现有订单并创建新订单。请求体为新的订单规范。

---

## 三、交易记录（Transactions）

### 获取交易记录列表

```
GET /trader/v1/accounts/{accountHash}/transactions
```

**参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `types` | string | 交易类型（逗号分隔），见下表 |
| `startDate` | string | 起始日期（ISO 8601），距今不超过 60 天 |
| `endDate` | string | 结束日期（ISO 8601） |
| `symbol` | string | 只返回指定标的的交易记录 |

**交易类型枚举**：

| 类型 | 说明 |
|------|------|
| `TRADE` | 交易 |
| `RECEIVE_AND_DELIVER` | 接收和交割 |
| `DIVIDEND_OR_INTEREST` | 股息或利息 |
| `ACH_RECEIPT` | ACH 收款 |
| `ACH_DISBURSEMENT` | ACH 付款 |
| `CASH_RECEIPT` | 现金收款 |
| `CASH_DISBURSEMENT` | 现金付款 |
| `ELECTRONIC_FUND` | 电子资金 |
| `WIRE_OUT` | 电汇出 |
| `WIRE_IN` | 电汇入 |
| `JOURNAL` | 日记账 |
| `MEMORANDUM` | 备忘录 |
| `MARGIN_CALL` | 保证金追缴 |
| `MONEY_MARKET` | 货币市场 |
| `SMA_ADJUSTMENT` | SMA 调整 |

### 获取单笔交易

```
GET /trader/v1/accounts/{accountHash}/transactions/{transactionId}
```

---

## 四、用户偏好设置

### 获取用户偏好

```
GET /trader/v1/userPreference
```

返回当前登录账户的偏好设置，包括所有关联账户的信息。

---

## 五、市场数据 — 报价（Quotes）

### 获取单个标的报价

```
GET /marketdata/v1/{symbol}/quotes
```

> ⚠️ 此端点不建议用于包含非字母数字字符的标的（如期货 `/ES`），请使用批量报价端点。

**可选参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `fields` | string | 返回字段：`quote`, `fundamental`, `extended`, `reference`, `regular` |

### 获取多个标的报价

```
GET /marketdata/v1/quotes
```

支持所有标的，包括期货等包含特殊字符的标的。

**参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `symbols` | string | 标的列表（逗号分隔） |
| `fields` | string | 返回字段 |
| `indicative` | boolean | 是否返回指示性报价 |

---

## 六、市场数据 — K线历史（Price History）

### 获取价格历史

```
GET /marketdata/v1/pricehistory
```

**参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `symbol` | string | 标的代码 |
| `periodType` | string | 周期类型：`day`, `month`, `year`, `ytd` |
| `period` | integer | 周期数量 |
| `frequencyType` | string | 频率类型：`minute`, `daily`, `weekly`, `monthly` |
| `frequency` | integer | 频率：1, 5, 10, 15, 30 |
| `startDate` | long | 起始时间（毫秒时间戳） |
| `endDate` | long | 结束时间（毫秒时间戳） |
| `needExtendedHoursData` | boolean | 是否包含盘前盘后数据（默认 true） |
| `needPreviousClose` | boolean | 是否返回前收盘价 |

### 常用组合

| 粒度 | periodType | frequency | 数据回溯范围 |
|------|-----------|-----------|-------------|
| 1 分钟 | `day` | 1（minute） | 约 48 天 |
| 5 分钟 | `day` | 5（minute） | 约 9 个月 |
| 10 分钟 | `day` | 10（minute） | 约 9 个月 |
| 15 分钟 | `day` | 15（minute） | 约 9 个月 |
| 30 分钟 | `day` | 30（minute） | 约 9 个月 |
| 日线 | `year` | 1（daily） | 数十年（AAPL 可追溯到 1985） |
| 周线 | `year` | 1（weekly） | 数十年 |

> ⚠️ **已知限制**：
> - 仅支持股票和 ETF 的价格历史
> - 不支持期权、期货或其他工具的价格历史
> - 期货不支持历史 K 线数据

---

## 七、市场数据 — 期权链（Option Chains）

### 获取期权链

```
GET /marketdata/v1/chains
```

**参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `symbol` | string | 标的代码 |
| `contractType` | string | 合约类型：`CALL`, `PUT`, `ALL` |
| `strikeCount` | integer | 在平值价格上下返回的行权价数量 |
| `includeUnderlyingQuote` | boolean | 是否包含标的报价 |
| `strategy` | string | 策略：`SINGLE`, `ANALYTICAL`, `COVERED`, `VERTICAL`, `CALENDAR`, `STRANGLE`, `STRADDLE`, `BUTTERFLY`, `CONDOR`, `DIAGONAL`, `COLLAR`, `ROLL` |
| `interval` | number | 价差策略的行权价间隔 |
| `strike` | number | 只返回指定行权价 |
| `range` | string | `ITM`（价内）, `NTM`（平值附近）, `OTM`（价外）, `SAK`, `SBK`, `SNK`, `ALL` |
| `fromDate` | string | 最早到期日（YYYY-MM-DD） |
| `toDate` | string | 最晚到期日（YYYY-MM-DD） |
| `volatility` | number | 用于分析策略计算的波动率 |
| `underlyingPrice` | number | 用于分析策略计算的标的价格 |
| `interestRate` | number | 用于分析策略计算的利率 |
| `daysToExpiration` | integer | 用于分析策略计算的到期天数 |
| `expMonth` | string | 到期月份：`JAN`-`DEC` 或 `ALL` |
| `optionType` | string | 期权类型：`S`（标准）, `NS`（非标准）, `ALL` |
| `entitlement` | string | 权限类型：`PP`（付费专业）, `NP`（非专业）, `PN`（非付费专业） |

### 获取期权到期日链

```
GET /marketdata/v1/expirationchain?symbol={symbol}
```

---

## 八、市场数据 — 其他端点

### 市场涨跌幅排行（Movers）

```
GET /marketdata/v1/movers/{index}
```

获取指定指数/交易所的涨跌幅前十。

**路径参数**：

| index 值 | 说明 |
|---------|------|
| `$DJI` | 道琼斯工业指数 |
| `$COMPX` | 纳斯达克综合指数 |
| `$SPX` | 标普 500 |
| `NYSE` | 纽约证券交易所 |
| `NASDAQ` | 纳斯达克 |
| `OTCBB` | 场外交易 |
| `INDEX_ALL` | 所有指数 |
| `EQUITY_ALL` | 所有股票 |
| `OPTION_ALL` | 所有期权 |
| `OPTION_PUT` | 看跌期权 |
| `OPTION_CALL` | 看涨期权 |

**可选参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `sort` | string | 排序方式：`VOLUME`, `TRADES`, `PERCENT_CHANGE_UP`, `PERCENT_CHANGE_DOWN` |
| `frequency` | integer | 频率：0（全天）, 1, 5, 10, 30, 60（分钟） |

### 市场交易时间

```
GET /marketdata/v1/markets
```

**参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `markets` | string | 市场列表（逗号分隔）：`equity`, `option`, `bond`, `future`, `forex` |
| `date` | string | 日期（YYYY-MM-DD），最多查询未来一年 |

### 工具搜索

```
GET /marketdata/v1/instruments
```

**参数**：

| 参数 | 类型 | 说明 |
|------|------|------|
| `symbol` | string | 搜索关键字或标的代码 |
| `projection` | string | 搜索模式，见下表 |

| projection 值 | 说明 |
|--------------|------|
| `symbol-search` | 按标的代码搜索 |
| `symbol-regex` | 按标的代码正则匹配 |
| `desc-search` | 按描述搜索 |
| `desc-regex` | 按描述正则匹配 |
| `search` | 通用搜索 |
| `fundamental` | 获取基本面数据 |

### 通过 CUSIP 获取工具信息

```
GET /marketdata/v1/instruments/{cusip}
```

---

## 端点汇总表

### Trader API (`/trader/v1/`)

| Method | Path | 说明 |
|--------|------|------|
| `GET` | `/accounts/accountNumbers` | 获取账户哈希列表 |
| `GET` | `/accounts/{hash}` | 获取单个账户详情 |
| `GET` | `/accounts` | 获取所有账户 |
| `GET` | `/accounts/{hash}/orders` | 获取账户订单 |
| `GET` | `/accounts/{hash}/orders/{id}` | 获取单个订单 |
| `POST` | `/accounts/{hash}/orders` | 下单 |
| `PUT` | `/accounts/{hash}/orders/{id}` | 替换订单 |
| `DELETE` | `/accounts/{hash}/orders/{id}` | 取消订单 |
| `POST` | `/accounts/{hash}/previewOrder` | 预览订单 |
| `GET` | `/orders` | 获取所有账户订单 |
| `GET` | `/accounts/{hash}/transactions` | 获取交易记录 |
| `GET` | `/accounts/{hash}/transactions/{id}` | 获取单笔交易 |
| `GET` | `/userPreference` | 获取用户偏好 |

### Market Data API (`/marketdata/v1/`)

| Method | Path | 说明 |
|--------|------|------|
| `GET` | `/{symbol}/quotes` | 获取单标的报价 |
| `GET` | `/quotes` | 获取多标的报价 |
| `GET` | `/chains` | 获取期权链 |
| `GET` | `/expirationchain` | 获取期权到期日 |
| `GET` | `/pricehistory` | 获取价格历史 |
| `GET` | `/movers/{index}` | 获取涨跌排行 |
| `GET` | `/markets` | 获取市场时间 |
| `GET` | `/instruments` | 搜索工具 |
| `GET` | `/instruments/{cusip}` | 通过 CUSIP 查询 |

## 相关文档

- [WebSocket 流式数据](./streaming.md)
- [订单模板](./order-templates.md)
- [订单构建器参考](./order-builder.md)
