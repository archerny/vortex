# Charles Schwab Trader API 文档

## 概述

Charles Schwab Trader API 是 Charles Schwab & Co., Inc. 开发的一套安全 API，旨在为散户交易者和开发者提供与 Schwab 经纪交易平台集成的自定义应用程序能力。该 API 是在 Charles Schwab 于 2020 年收购 TD Ameritrade 后推出的，用于取代旧版 TD Ameritrade Trader API（已于 2024 年中期逐步淘汰）。

- **官方开发者门户**: [developer.schwab.com](https://developer.schwab.com/)
- **API 基础 URL**: `https://api.schwabapi.com`
- **认证协议**: OAuth 2.0（authorization_code 授权类型）
- **数据格式**: JSON

## API 产品

Schwab 开发者门户提供以下 API 产品：

| 产品 | 说明 | 目录 | 是否已下载 |
|------|------|------|-----------|
| **Trader API (Individual)** | 个人交易者 API — 账户管理、交易、市场数据 | `trader-api/` | ✅ |
| **Trader API (Commercial)** | 商业交易者 API — 面向第三方集成商 | — | ❌（需商业合作） |
| **Account and Client Data** | 账户和客户数据 API | — | ❌ |
| **Advisor Services** | 投资顾问服务 API | — | ❌ |
| **Data Aggregation** | 数据聚合 API | — | ❌ |
| **Tax Data** | 税务数据 API | — | ❌ |

## 文档目录

### 用户指南（User Guides）

| 文件 | 说明 |
|------|------|
| [入门指南](./user-guides/getting-started.md) | 开发者账户注册、应用创建、OAuth 认证 |
| [OAuth 认证](./user-guides/authentication.md) | OAuth 2.0 认证流程详解、Token 管理 |

### Trader API（核心文档）

| 文件 | 说明 |
|------|------|
| [API 端点参考](./trader-api/api-reference.md) | **核心文档** — 所有 REST API 端点的完整参考（账户、订单、市场数据） |
| [WebSocket 流式数据](./trader-api/streaming.md) | 实时市场数据流（Level 1/2 报价、K线、期权、期货） |
| [订单模板](./trader-api/order-templates.md) | 常用订单类型的构建模板（股票、期权、复合订单） |
| [订单构建器参考](./trader-api/order-builder.md) | 完整的订单规范参考（高级用法） |

### 市场数据（Market Data）

| 文件 | 说明 |
|------|------|
| [市场数据概览](./market-data/overview.md) | 市场数据 API 端点概览（报价、K线、期权链、市场时间等） |

## 服务器环境

| 环境 | 地址 |
|------|------|
| **Production** | `https://api.schwabapi.com` |
| **Sandbox** | 通过开发者门户配置 |

## 速率限制

| 请求类型 | 限制 |
|---------|------|
| 市场数据请求 | 约 120 次/分钟 |
| 交易请求 | 2-4 次/秒 |
| 超限响应 | HTTP 429 |

## 第三方 SDK

| 语言 | 库名 | 链接 |
|------|------|------|
| Python | `schwab-py` | [GitHub](https://github.com/alexgolec/schwab-py) / [文档](https://schwab-py.readthedocs.io/) |
| Node.js | `schwab-client-js` | [npm](https://www.npmjs.com/package/schwab-client-js) |
| Node.js | `@sudowealth/schwab-api` | [npm](https://www.npmjs.com/package/@sudowealth/schwab-api) |
