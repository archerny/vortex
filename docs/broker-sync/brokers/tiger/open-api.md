# 老虎证券同步方案（设计与实现记录）

> **状态**: Phase 1 已实现（API 调通 → 日志输出）  
> **日期**: 2026-03-14（方案讨论） → 2026-03-31（实现完成）  
> **关联**: [../../architecture.md](../../architecture.md) | [../../README.md](../../README.md)

---

## 1. 方案概述

通过 **Tiger Open API**（REST 方式）获取已成交订单数据，使用官方 Java SDK 对接，Phase 1 仅做日志输出供核对，暂不入库。

### 核心决策

| 决策项 | 决策 | 理由 |
|--------|------|------|
| API 接入方式 | Tiger Open API（REST） | 官方 SDK 成熟，文档完善 |
| SDK 版本 | `io.github.tigerbrokers:openapi-java-sdk:2.4.7` | 最新稳定版 |
| 认证方式 | Tiger ID + RSA Private Key + Account | 标准 OpenAPI 认证 |
| 凭证管理 | `application-local.properties`（gitignore） | 与数据库密码保持一致的管理方式 |
| 数据范围 | 已成交订单（`get_filled_orders`） | Phase 1 仅同步交易记录 |
| 输出方式 | 日志逐条打印 | Phase 1 不入库，先核对原始数据 |

---

## 2. 架构设计

### 2.1 整体架构

```
                  ┌──────────────────────┐
                  │  BrokerSyncController │  POST /api/broker-sync/trigger
                  │  (REST API 入口)       │  GET  /api/broker-sync/brokers
                  └──────────┬───────────┘
                             │ SyncRequest
                             ▼
                  ┌──────────────────────┐
                  │  BrokerSyncService   │  按 brokerName 路由到适配器
                  │  (编排服务)           │  自动发现所有 BrokerSyncAdapter
                  └──────────┬───────────┘
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
     ┌─────────────┐  ┌───────────┐  ┌───────────┐
     │TigerSync    │  │ (ibkr)    │  │ (futu)    │  ← 未来扩展
     │Adapter      │  │ 预留      │  │ 预留      │
     └──────┬──────┘  └───────────┘  └───────────┘
            │
            ▼
     ┌─────────────────────────────────┐
     │ Tiger Open API (SDK v2.4.7)     │
     │ MethodName.FILLED_ORDERS        │
     │ 90天窗口自动拆分                  │
     └─────────────────────────────────┘
            │
            ▼
     ┌─────────────────────────────────┐
     │ TradeOrder → TigerOrderRecord   │  数据转换
     │ (保留原始语义，日志逐条输出)       │  Phase 1: 仅日志
     └─────────────────────────────────┘
```

### 2.2 数据流

```
券商 API 响应 (JSON)
    ↓ Tiger SDK 反序列化
TradeOrder (Tiger SDK 对象)
    ↓ convertToRecord()
TigerOrderRecord (券商专属模型)     ← 保留原始字段，日志在此层打印
    ↓ 📋 日志打印（Phase 1 在此输出，核对原始数据）
    ↓ （后续阶段再实现）
BrokerTradeRecord (系统统一模型)    ← sync 模块的统一输出模型
    ↓ （后续阶段再实现）
入库 / 其他处理
```

### 2.3 设计亮点

1. **开闭原则**：新增券商只需添加 `BrokerSyncAdapter` 实现类 + `@Component`，`BrokerSyncService` 和 `BrokerSyncController` 零修改
2. **90 天窗口自动拆分**：应对 Tiger API 的时间跨度限制
3. **配置分离**：敏感凭证通过 `application-local.properties`（gitignore）管理
4. **Phase 1 渐进式设计**：当前只做 API 调用 + 日志输出，预留统一模型转换和入库的扩展点
5. **碎股支持**：通过 `quantityScale` 字段支持碎股场景的精确数量计算

---

## 3. 包结构

```
com.vortex
├── controller/
│   └── BrokerSyncController.java          ← REST API 入口
└── sync/
    ├── core/                               ← 核心抽象层
    │   ├── BrokerSyncAdapter.java          ← 适配器接口
    │   ├── BrokerSyncService.java          ← 编排服务
    │   ├── SyncRequest.java                ← 请求模型
    │   └── SyncResult.java                 ← 结果模型
    └── adapter/
        └── tiger/                          ← Tiger 适配器实现
            ├── TigerApiProperties.java     ← 配置属性绑定
            ├── TigerOrderRecord.java       ← 原始订单数据模型
            └── TigerSyncAdapter.java       ← 核心同步逻辑
```

---

## 4. 各组件详细说明

### 4.1 核心抽象层（`sync/core/`）

#### `BrokerSyncAdapter.java` — 适配器接口

券商同步适配器的统一接口，采用**策略模式 + 适配器模式**组合设计。

```java
public interface BrokerSyncAdapter {
    String getBrokerName();        // 返回券商标识（如 "tiger"）
    SyncResult sync(SyncRequest request);  // 执行同步
}
```

#### `BrokerSyncService.java` — 编排服务

- 构造方法通过 Spring IoC 自动注入所有 `BrokerSyncAdapter` 实现，构建 `Map<String, BrokerSyncAdapter>` 索引
- `sync(SyncRequest)` 根据 `brokerName` 路由到对应适配器
- `getSupportedBrokers()` 返回已注册券商列表

#### `SyncRequest.java` — 请求模型

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `brokerName` | String | ✅ | 券商标识，用于匹配适配器 |
| `startTime` | String | ❌ | 同步起始时间，格式 `yyyy-MM-dd`，默认由适配器决定 |
| `endTime` | String | ❌ | 同步截止时间，格式 `yyyy-MM-dd`，默认由适配器决定 |

#### `SyncResult.java` — 结果模型

通过静态工厂方法创建：
- `SyncResult.success(brokerName, totalRecords, durationMs)`
- `SyncResult.failure(brokerName, errorMessage, durationMs)`

字段：`success`、`brokerName`、`totalRecords`、`message`、`durationMs`

### 4.2 Tiger 适配器（`sync/adapter/tiger/`）

#### `TigerApiProperties.java` — 配置属性绑定

使用 `@ConfigurationProperties(prefix = "broker.tiger")` 绑定配置：

| 配置项 | 属性 | 说明 |
|--------|------|------|
| `broker.tiger.tiger-id` | `tigerId` | 老虎开放平台 Tiger ID |
| `broker.tiger.private-key` | `privateKey` | RSA 私钥（PKCS#8 格式） |
| `broker.tiger.account` | `account` | 资金账号 |

提供 `isConfigured()` 方法检查三个凭证是否都已配置。

#### `TigerOrderRecord.java` — 原始订单数据模型

**定位**：内存中间数据载体，不持久化到数据库。字段直接对应 Tiger API 返回的 Order 对象属性，保留原始语义。

**字段分组**：

| 分组 | 字段 | 说明 |
|------|------|------|
| 订单基本信息 | `account`, `orderId`, `orderTime`, `tradeTime`, `action`, `status` | BUY/SELL, FILLED 等 |
| 数量与价格 | `quantity`, `quantityScale`, `filledQuantity`, `avgFillPrice` | 碎股场景用 quantityScale |
| 费用 | `commission`, `gst`, `realizedPnl` | 佣金含印花税/证监会费等 |
| 合约信息 | `symbol`, `contractName`, `secType`, `currency`, `exchange`, `market`, `identifier`, `multiplier`, `etf` | secType: STK/OPT/WAR/IOPT/FUT/FUND |
| 期权专有 | `expiry`, `strike`, `putCall` | CALL/PUT |
| 订单类型 | `orderType`, `limitPrice` | MKT/LMT/STP 等 |

**关键业务方法**：

| 方法 | 说明 |
|------|------|
| `getRealQuantity()` | 考虑 `quantityScale` 计算真实数量（碎股：qty=111, scale=2 → 1.11） |
| `getRealFilledQuantity()` | 同上，针对成交数量 |
| `getTradeDate()` | 毫秒时间戳 → `LocalDate`（东八区 Asia/Shanghai） |
| `getTotalFee()` | 佣金 + GST |
| `getFilledAmount()` | 成交金额，期权自动乘以 multiplier |

#### `TigerSyncAdapter.java` — 核心同步逻辑

**`sync()` 执行流程**：

```
1. 检查配置 → isConfigured()? 否则返回失败
2. 初始化客户端 → createClient() → TigerHttpClient 单例
3. 时间范围解析 → resolveStartDate() / resolveEndDate()
   - 有参数：解析 yyyy-MM-dd
   - 无参数：endDate=今天, startDate=endDate-90天
4. 分段查询 → fetchOrdersInWindows()
   - 按 90 天窗口拆分
   - 逐段调用 fetchFilledOrders()
5. 日志输出 → logRecords() 逐条打印
6. 返回 SyncResult
```

**API 调用细节**：

```java
// 使用 MethodName.FILLED_ORDERS 获取已成交订单
QueryOrderRequest request = new QueryOrderRequest(MethodName.FILLED_ORDERS);
String bizContent = AccountParamBuilder.instance()
        .account(account)
        .startDate("2025-01-01")
        .endDate("2025-03-31")
        .buildJson();
request.setBizContent(bizContent);
BatchOrderResponse response = client.execute(request);
```

**数据转换** (`convertToRecord()`)：

将 Tiger SDK 的 `TradeOrder` 逐字段映射为 `TigerOrderRecord`，包含：
- 空值安全处理（所有 nullable 字段检查）
- 类型转换（Long→int、Double→BigDecimal、String→BigDecimal）
- 期权 strike 值的 NumberFormatException 容错

**90 天窗口拆分**：

Tiger API 限制 `start_time` 和 `end_time` 之间的间隔不能超过 90 天。`fetchOrdersInWindows()` 自动将大范围拆成多个 ≤90 天的子查询：

```
假设请求范围: 2025-01-01 ~ 2025-06-30 (181天)
  → 窗口 1: 2025-01-01 ~ 2025-04-01
  → 窗口 2: 2025-04-01 ~ 2025-06-30
```

### 4.3 REST API 控制器

`BrokerSyncController.java`，放在 `controller/` 包统一管理（不在 `sync` 包内）。

| 方法 | 路径 | 功能 |
|------|------|------|
| `POST` | `/api/broker-sync/trigger` | 触发同步 |
| `GET` | `/api/broker-sync/brokers` | 查询已注册券商列表 |

**请求示例**：

```bash
# 最简请求（默认查最近 90 天）
curl -X POST http://localhost:8080/api/broker-sync/trigger \
  -H "Content-Type: application/json" \
  -d '{"brokerName":"tiger"}'

# 指定时间范围
curl -X POST http://localhost:8080/api/broker-sync/trigger \
  -H "Content-Type: application/json" \
  -d '{"brokerName":"tiger","startTime":"2025-01-01","endTime":"2025-03-31"}'

# 查询支持的券商
curl http://localhost:8080/api/broker-sync/brokers
```

**响应格式**：

```json
// 成功
{"status":"SUCCESS", "message":"同步完成", "data": {"success":true, "brokerName":"tiger", "totalRecords":42, "durationMs":1234, "message":"..."}}

// 失败
{"status":"ERROR", "message":"同步失败 [tiger]：API 凭证未配置（耗时 0 ms）"}
```

---

## 5. 配置文件

### 5.1 `application.properties` — 主配置（空白占位）

```properties
# ========== 券商 API 凭证 ==========
# 老虎证券 Tiger Open API
# 注意：实际的凭证请在 application-local.properties 中设置
broker.tiger.tiger-id=
broker.tiger.private-key=
broker.tiger.account=
```

### 5.2 `application-local.properties.template` — 凭证模板

```properties
# 券商 API 凭证 - 老虎证券
# 从老虎开放平台获取：https://developer.itigerup.com/profile
broker.tiger.tiger-id=your_tiger_id_here
broker.tiger.private-key=your_pkcs8_private_key_here
broker.tiger.account=your_account_here
```

### 5.3 `application-local.properties` — 实际凭证（gitignore）

用户需手动添加 `broker.tiger.*` 配置项到此文件，格式参考 template。

---

## 6. Maven 依赖

```xml
<!-- Tiger Open API Java SDK -->
<dependency>
    <groupId>io.github.tigerbrokers</groupId>
    <artifactId>openapi-java-sdk</artifactId>
    <version>2.4.7</version>
</dependency>
```

---

## 7. Tiger API 技术要点

### 7.1 认证流程

1. 在 [老虎开放平台](https://developer.itigerup.com/profile) 注册开发者账号
2. 获取 Tiger ID
3. 生成 RSA 密钥对，上传公钥到平台，保留 PKCS#8 私钥
4. 获取资金账号

### 7.2 API 限制

| 限制项 | 值 |
|--------|-----|
| 单次查询时间跨度 | ≤ 90 天 |
| 证券类型支持 | STK（股票）、OPT（期权）、WAR（窝轮）、IOPT（牛熊证）、FUT（期货）、FUND（基金） |

### 7.3 已知注意事项

- `TigerHttpClient.getInstance()` 返回单例，需确保 `ClientConfig` 在首次使用前正确设置
- `TradeOrder.getTotalQuantity()` / `getFilledQuantity()` 返回 `Long` 类型，需转 `int`
- 期权 `strike` 以 `String` 类型返回，需手动解析为 `BigDecimal`
- `commission` 字段包含佣金、印花税、证监会费等多项费用的合计值
- `getLatestTime()` 代表订单最后更新时间，用作成交时间

---

## 8. 后续待办

- [ ] 统一模型转换：实现 `TigerOrderRecord` → `BrokerTradeRecord` 映射
- [ ] 入库逻辑：数据核对无误后接入入库
- [ ] 去重机制：基于 `orderId` 进行幂等性控制
- [ ] 单元测试：当前尚无 Tiger 同步相关测试用例
- [ ] 错误重试：API 调用失败时的重试策略
- [ ] `application-local.properties` 中补充 Tiger 凭证配置

---

## 9. 参考资料

- Tiger Open API 文档：`external-resource/` 目录下相关文件
- Tiger 开发者平台：https://developer.itigerup.com/profile
- Tiger Open API Java SDK：https://github.com/tigerfintech/openapi-java-sdk
