# Getting Started — 入门指南

## Schwab API 访问前提

使用 Schwab Trader API 之前，需要完成以下步骤：

1. 创建开发者账户
2. 创建应用（Application）
3. 获取 App Key 和 App Secret

## 创建开发者账户

访问 [Schwab Developer Portal](https://developer.schwab.com/) 创建开发者账户。

> **前提条件**：需要拥有一个活跃的 Charles Schwab 经纪账户。

## 创建应用

登录开发者门户后，创建一个新的应用。需要填写以下信息：

### API Product

选择要订阅的 API 产品。对于个人交易者，推荐选择 **"Accounts and Trading Production"**，它会授予所有交易和市场数据 API 的访问权限。

### Order Limit

每分钟允许的订单相关请求数量上限。推荐设置为 **120**（最大值）。

### App Name and Description

应用名称和描述。Schwab 审核团队会查看这些信息。

### Callback URL（回调 URL）

这是 OAuth 认证流程中最重要的配置项。

推荐使用：`https://127.0.0.1:8182`（注意不要有尾部斜杠）

> ⚠️ **重要**：回调 URL 必须在后续的 API 调用中**完全匹配**，包括协议（https）、端口号、是否有尾部斜杠等。任何偏差都会导致难以调试的问题。

### 等待审批

应用创建后，初始状态为 `Approved - Pending`（虽然包含 "Approved" 但实际上还未批准）。需要等待 Schwab 人工审核，审核通过后状态会变为 `Ready For Use`。审核时间通常在几天以内。

> ⚠️ 在 `Ready For Use` 状态之前，所有 API 调用都会失败（返回 `401 Unauthorized`、`Access Denied` 等错误）。

### 获取密钥

应用审核通过后，可以在应用详情页面获取：

- **App Key**（Client ID）
- **App Secret**（Client Secret）

> ⚠️ 这两个值都是敏感信息，不要分享给任何人。

## Token 生命周期

| Token 类型 | 有效期 | 说明 |
|-----------|--------|------|
| **Access Token** | 30 分钟 | 附带在每个 API 请求的 `Authorization: Bearer {token}` 头中 |
| **Refresh Token** | 7 天 | 用于在 Access Token 过期后自动获取新的 Access Token |

- Access Token 过期后，客户端库会自动使用 Refresh Token 获取新的 Access Token（此过程对用户透明）
- Refresh Token 过期后（7天），需要重新走完整的 OAuth 登录流程
- 建议在周末（非交易时间）主动刷新 Token

## 联系方式

- **Schwab API 团队邮箱**: [traderapi@schwab.com](mailto:traderapi@schwab.com)
- **社区 Discord**: [schwab-py Discord](https://discord.gg/mm44rstRCg)

## 相关文档

- [OAuth 认证详解](./authentication.md)
- [API 端点参考](../trader-api/api-reference.md)
