# Authentication — OAuth 2.0 认证

## 概述

Schwab API 使用 **OAuth 2.0 authorization_code** 授权类型进行认证。所有 API 请求通过 HTTPS 发送，不直接使用用户名和密码。

## 认证流程

### 1. 获取授权码（Authorization Code）

将用户重定向到 Schwab 登录页面：

```
https://api.schwabapi.com/v1/oauth/authorize?client_id={APP_KEY}&redirect_uri={CALLBACK_URL}
```

用户登录并授权后，Schwab 会重定向到回调 URL，并在 URL 查询参数中包含授权码：

```
{CALLBACK_URL}?code={AUTHORIZATION_CODE}&session={SESSION_ID}
```

### 2. 交换 Token

使用授权码换取 Access Token 和 Refresh Token：

```bash
curl -X POST https://api.schwabapi.com/v1/oauth/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -H "Authorization: Basic {BASE64_ENCODED_APP_KEY:APP_SECRET}" \
  -d "grant_type=authorization_code&code={AUTH_CODE}&redirect_uri={CALLBACK_URL}"
```

响应示例：

```json
{
  "expires_in": 1800,
  "token_type": "Bearer",
  "scope": "api",
  "refresh_token": "{REFRESH_TOKEN}",
  "access_token": "{ACCESS_TOKEN}",
  "id_token": "{ID_TOKEN}"
}
```

### 3. 使用 Access Token 发起 API 请求

```bash
curl -X GET https://api.schwabapi.com/trader/v1/accounts \
  -H "Authorization: Bearer {ACCESS_TOKEN}"
```

### 4. 刷新 Token

Access Token 过期后（30分钟），使用 Refresh Token 获取新的 Access Token：

```bash
curl -X POST https://api.schwabapi.com/v1/oauth/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -H "Authorization: Basic {BASE64_ENCODED_APP_KEY:APP_SECRET}" \
  -d "grant_type=refresh_token&refresh_token={REFRESH_TOKEN}"
```

## Token 管理

### Token 生命周期

| Token | 有效期 | 用途 |
|-------|--------|------|
| Access Token | 30 分钟 | API 请求认证 |
| Refresh Token | 7 天 | 刷新 Access Token |

### 技术细节

- **Access Token** 是随机生成的字符串，附加到每个 API 请求中验证身份
- **Refresh Token** 在 Access Token 即将过期时自动用于获取新的 Access Token
- 7 天后 Refresh Token 过期，会收到 `invalid_client: refresh token invalid` 错误
- 此时必须删除旧 Token 文件并重新走完整登录流程

### 权限管理

Schwab API 通过**产品订阅**（Product Subscription）而非传统的 OAuth scope 来管理权限。零售用户通常在 "Myself" 或 "Retail Clients" 产品下进行操作。

## 常见错误排查

### `401 Unauthorized`

- 应用状态可能还在 `Approved - Pending`，等待变为 `Ready for Use`
- Access Token 可能已过期
- App Key 或 App Secret 不正确

### `Access Denied`

- 应用尚未通过审核
- 尝试联系 Schwab API 团队：[traderapi@schwab.com](mailto:traderapi@schwab.com)

### `invalid_client: refresh token invalid`

- Refresh Token 已超过 7 天有效期
- 需要删除旧 Token 并重新登录

### SSL 证书警告

在本地开发环境使用 `https://127.0.0.1` 作为回调 URL 时，浏览器会显示 SSL 证书警告（因为证书是自签名的）。可以安全地忽略此警告，但应始终验证地址栏中的 URL 与回调 URL 匹配。

## 相关文档

- [入门指南](./getting-started.md)
- [API 端点参考](../trader-api/api-reference.md)
