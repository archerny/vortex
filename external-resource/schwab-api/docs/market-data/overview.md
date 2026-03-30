# Market Data Overview — 市场数据概览

## 概述

Schwab Market Data API 提供股票、期权和其他证券的实时和历史市场信息。

**基础 URL**: `https://api.schwabapi.com/marketdata/v1`

## API 分类

### 报价（Quotes）

| 端点 | 说明 |
|------|------|
| `GET /{symbol}/quotes` | 单标的报价（不适用于含特殊字符的标的如 `/ES`） |
| `GET /quotes` | 多标的批量报价（支持所有标的类型） |

**可用字段**:
- `quote` — 当前报价
- `fundamental` — 基本面数据
- `extended` — 扩展数据
- `reference` — 参考数据
- `regular` — 常规交易数据

### 价格历史（Price History）

| 端点 | 说明 |
|------|------|
| `GET /pricehistory` | 历史 K 线数据 |

**支持的频率**:

| 频率 | 说明 | 回溯范围 |
|------|------|---------|
| 1 min | 1 分钟线 | 约 48 天 |
| 5 min | 5 分钟线 | 约 9 个月 |
| 10 min | 10 分钟线 | 约 9 个月 |
| 15 min | 15 分钟线 | 约 9 个月 |
| 30 min | 30 分钟线 | 约 9 个月 |
| daily | 日线 | 数十年 |
| weekly | 周线 | 数十年 |

> ⚠️ 仅支持股票和 ETF，不支持期权和期货的历史数据。

### 期权链（Option Chains）

| 端点 | 说明 |
|------|------|
| `GET /chains` | 完整期权链（含行权价、到期日、波动率等） |
| `GET /expirationchain` | 期权到期日列表 |

### 市场信息

| 端点 | 说明 |
|------|------|
| `GET /movers/{index}` | 涨跌幅排行 |
| `GET /markets` | 市场交易时间 |
| `GET /instruments` | 工具搜索 |
| `GET /instruments/{cusip}` | 通过 CUSIP 查询 |

## 速率限制

| 类型 | 限制 |
|------|------|
| 市场数据请求 | 约 120 次/分钟 |
| 超限响应 | HTTP 429 Too Many Requests |

## 实时数据

对于实时市场数据推送，请使用 [WebSocket Streaming API](../trader-api/streaming.md)。

REST API 返回的报价可能有延迟（取决于账户的市场数据订阅级别）。

## 相关文档

- [API 端点参考](../trader-api/api-reference.md)（包含所有端点的详细参数说明）
- [WebSocket 流式数据](../trader-api/streaming.md)
