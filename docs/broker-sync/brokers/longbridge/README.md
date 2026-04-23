# 长桥证券（Longbridge）同步

> **状态**：📋 设计稿 v0.1（未实现，待 review 与决策确认后落地）
> **适配器（规划）**：`LongbridgeSyncAdapter`
> **API 类型**：Longbridge OpenAPI（HTTPS/JSON，官方 Java SDK，Rust JNI 底层）
> **Broker Code**：`longbridge`
> **账户前提**：单账户（一个 `APP_KEY / APP_SECRET / ACCESS_TOKEN` 对应一个长桥账户）

---

## 文档定位

这是一份**设计阶段**文档，尚未编码。目的：

1. 对齐与 IBKR / Tiger / Futu 的架构一致性
2. 把长桥的**独特差异**（Rust JNI SDK、1000 条单次上限 + `has_more` 翻页、成交字段极简、仅 US/HK 市场）显式记录下来
3. 列出 **Open questions**，在编码前敲定

所有引用的长桥协议字段都来自仓库内 `external-resource/longbridge-api/docs/` 下的官方文档快照。

---

## 1. 长桥与 IBKR / Tiger / Futu 的关键差异

| 维度 | IBKR | Tiger | Futu | **长桥** |
|---|---|---|---|---|
| 接入方式 | 云端 Flex Web Service | 云端 OpenAPI (REST+RSA) | 本地 OpenD 网关 | **云端 HTTPS/JSON + 官方 Java SDK（Rust JNI 底层）** |
| 传输协议 | HTTPS/XML | HTTPS/JSON | TCP/Protobuf | HTTPS/JSON（SDK 封装） |
| SDK 调用模型 | 同步 REST | 同步 REST | 回调式 | **同步（`CompletableFuture.get()`）** |
| Java SDK | 无 / 自研 | 老虎 Java SDK | futu-api Java SDK | **`io.github.longbridge:openapi-sdk:4.0.5`**，Rust JNI |
| 认证 | Flex Token | TigerId + RSA 私钥 | OpenD 解锁密码 MD5 | **OAuth 2.0** 或 **API Key（`APP_KEY / APP_SECRET / ACCESS_TOKEN`）** |
| 历史成交接口 | Flex Statement | `get_filled_orders` | `Trd_GetHistoryOrderFillList` | **`TradeContext.getHistoryExecutions`** |
| 时间窗口限制 | 365 天 | 90 天 | 90 天 | **90 天（默认）** |
| 单次返回上限 | 无 | 无 | 无 | **1000 条，`has_more=true` 表示被截断** |
| 支持市场 | 全球 | 全球 | 多市场 | **仅 US + HK**（`Market` 枚举） |
| 支持标的 | 股票/期权/期货/外汇… | 股票/期权… | 股票/期货… | **股票 + 窝轮 + 期权**（来自 `trade-order-submit.md`） |

### 1.1 长桥的独特变量

1. **Rust JNI Java SDK**
   - 官方 Java SDK 基于 Rust 底层，通过 JNI 调用
   - 打包/部署需确认目标平台的 native 库（`.so` / `.dylib` / `.dll`）可用
   - 好处：API 是**同步式 `CompletableFuture`**，不需要像 Futu 那样做回调适配

2. **成交字段极简 + 必须联合订单表**
   - `getHistoryExecutions` **只返回** `order_id / trade_id / symbol / trade_done_at / quantity / price`
   - **没有 `side`（买/卖）、没有 `currency`、没有 `market`**
   - 必须配合 `getHistoryOrders` 按 `order_id` 关联，补齐 `side` / `currency` / `stock_name` 等
   - 这是和 Futu 的共性（Futu 也需要联合订单接口），但**长桥成交表比 Futu 还瘦**

3. **1000 条单次上限 + `has_more` 截断**
   - 单次返回最多 1000 条成交，超过则 `has_more=true`
   - 长桥**没有游标/分页 token**，翻页只能通过**收紧时间窗**实现
   - 结果：需要在 90 天窗口内再做**嵌套翻页**（IBKR / Tiger / Futu 都没有这个复杂度）

4. **仅 US + HK，但含期权**
   - `Market` 枚举只有 `US` / `HK`（文档 `getting-started.md`）
   - 但 `submit_order` 接口明确支持「港美股，窝轮，**期权**」
   - 期权成交会通过同一个 `getHistoryExecutions` 接口返回，**混在普通股票成交里**
   - 期权 symbol 预计使用 OCC-style 格式（如 `AAPL230317P00160000.US`），但**历史成交响应中的确切格式官方文档未直接给出示例**，需接入时实测

---

## 2. 接入架构

### 2.1 模块划分（沿用 IBKR / Tiger / Futu 骨架）

```
backend/src/main/java/com/vortex/sync/adapter/longbridge/
├── LongbridgeSyncAdapter.java          # BrokerSyncAdapter 实现
├── LongbridgeClient.java               # TradeContext 生命周期 + 限频 + 重试封装
├── LongbridgeCredentials.java          # appKey / appSecret / accessToken / region
├── LongbridgeExecutionRecord.java      # 历史成交原始模型（1:1 对应 Execution）
├── LongbridgeOrderRecord.java          # 历史订单原始模型（1:1 对应 Order，补字段用）
├── LongbridgeRawPayloadAssembler.java  # Execution + Order 合并成 staged 行
├── LongbridgeStagedExecutionRepository.java
├── LongbridgeTradeRecordMapper.java    # staged → trade_records 映射（含期权 symbol 识别）
└── LongbridgeSymbolClassifier.java     # 识别 stock / warrant / option
```

### 2.2 `LongbridgeClient` 设计

```java
class LongbridgeClient implements AutoCloseable {
    // 通过 Config.fromApikey(appKey, appSecret, accessToken) 创建
    // 单次 sync 任务内持有一个 TradeContext，任务结束 close()
    List<Execution> historyExecutions(String symbol, Instant start, Instant end);
    List<Order>     historyOrders(Instant start, Instant end, List<OrderStatus> status);
    void close();
}
```

调用模型：同步式 `future.get()`，不需要像 Futu 一样做回调 → CompletableFuture 适配。

---

## 3. 数据源选型

### 3.1 核心接口 —— 两个接口联合

| 接口 | SDK 方法 | 角色 |
|---|---|---|
| **`GET /v1/trade/execution/history`** | `TradeContext#getHistoryExecutions` | **主源** —— 产出 `trade_records` 行 |
| **`GET /v1/trade/order/history`** | `TradeContext#getHistoryOrders` | **字段补充** —— 用 `order_id` 关联，取 `side` / `currency` / `stock_name` |

### 3.2 为什么必须联合订单接口

成交接口**没有买卖方向**。不拉订单表，无法填 `trade_records.direction` 字段——这是硬要求，不是优化。

### 3.3 不采用的接口

| 接口 | 不采用理由 |
|---|---|
| `today_executions` / `today_orders` | 本功能只做历史同步，不做实时 |
| `order_detail`（含 `charge_detail`） | Phase 1 不导入手续费（与 IBKR / Tiger 对齐），且逐单查会触发大量请求 |
| WebSocket 交易推送 | 推送式不符合批次同步 |
| `cashflow` / `stock positions` / `fund positions` | 超出本功能范围 |

---

## 4. 数据模型

### 4.1 Staged 表：`longbridge_staged_executions`

```sql
CREATE TABLE longbridge_staged_executions (
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    batch_id            VARCHAR(64)    NOT NULL,
    -- Raw fields (1:1 from Execution)
    trade_id            VARCHAR(64)    NOT NULL,           -- 成交 ID（dedup key）
    order_id            VARCHAR(64)    NOT NULL,
    symbol              VARCHAR(64)    NOT NULL,           -- "AAPL.US" / "700.HK" / "AAPL230317P00160000.US"
    trade_done_at       DATETIME       NOT NULL,           -- UTC
    quantity            DECIMAL(20, 8) NOT NULL,
    price               DECIMAL(20, 8) NOT NULL,
    -- Enriched from Order (join by order_id)
    side                VARCHAR(8),                         -- "Buy" / "Sell"
    currency            VARCHAR(8),                         -- "USD" / "HKD"
    order_status        VARCHAR(32),                        -- "FilledStatus" etc.
    stock_name          VARCHAR(128),
    outside_rth         VARCHAR(16),                        -- 盘前盘后（保留用，不入 trade_records）
    -- Classification (由 SymbolClassifier 填）
    security_type       VARCHAR(16),                        -- "STOCK" / "WARRANT" / "OPTION"
    -- Sync metadata
    raw_payload         JSON,                               -- Execution + Order 的合并 JSON
    created_at          DATETIME       NOT NULL,
    UNIQUE KEY uk_batch_trade (batch_id, trade_id),
    KEY idx_batch (batch_id),
    KEY idx_trade_id (trade_id)
) ENGINE=InnoDB;
```

### 4.2 字段映射 → `trade_records`

| `trade_records` 字段 | 来源 | 规则 |
|---|---|---|
| `broker_id` | 配置 | 长桥的 broker 记录 ID |
| `broker_ref` | `trade_id` | 长桥自身全局唯一 |
| `trade_time` | `trade_done_at` | UTC → 本地时区显示 |
| `symbol` | `symbol` 的 ticker 部分 | `AAPL.US → AAPL`；`700.HK → 0700`（复用 Tiger 的 `SymbolNormalizer`）；**期权 symbol 走独立分支**（见 § 4.4） |
| `market` | `symbol` 的 region 后缀 | `.US → US`，`.HK → HK` |
| `direction` | `side`（来自订单表） | `Buy → BUY`，`Sell → SELL` |
| `quantity` | `quantity` | 直接用 |
| `price` | `price` | 直接用 |
| `currency` | `currency`（来自订单表） | 直接用 |
| `fee` | 不填 | 见 § 3.3 |
| `source_batch_id` | `batch_id` | |

### 4.3 期权 / 窝轮事件处理

长桥 OpenAPI **支持股票 + 窝轮 + 期权交易**（见 `trade-order-submit.md`）。期权成交会通过**同一个** `getHistoryExecutions` 接口返回，混在普通成交列表里。

**Phase 1 推荐策略（对应 Open question D7）**：
- **方案 A（推荐）**：只导入股票 + 窝轮成交，识别出期权 symbol 就**跳过 + 记日志 + 在 staged 表留底**。`security_type` 字段用于标记。和 IBKR 早期策略一致。
- **方案 B**：Phase 1 就把期权导入 `trade_records`，参考 IBKR `BookTrade` 事件模型。复杂度高，推迟到 P3.x。

### 4.4 Symbol 分类（`LongbridgeSymbolClassifier`）

```
AAPL.US                   → STOCK
700.HK                    → STOCK
NVDA250620C00120000.US    → OPTION（OCC-style：ticker + YYMMDD + C/P + 8 位行权价）
SEHK-xxxxxx               → WARRANT（港股窝轮 symbol 约定，接入时实测确认）
```

识别规则在接入时根据真实响应 symbol 格式最终确定，先用**保守规则**：
- 含字母 + 符合 OCC 正则（`^[A-Z]+\d{6}[CP]\d{8}\.US$`）→ OPTION
- `.HK` 结尾 + 长度 ≥ 6 位数字 → 可能是窝轮（保留原 symbol，走 WARRANT 分支）
- 其余默认 STOCK

---

## 5. 同步流程（`LongbridgeSyncAdapter#sync`）

```
1. 加载 LongbridgeCredentials
2. 通过 Config.fromApikey(...) 创建 TradeContext（try-with-resources）
3. 计算待同步时间窗（from cursor → now），按 90 天拆分
4. 对每个 90 天窗口：
   a. 循环调用 historyExecutions(start, end)，处理 `has_more`：
      - 收集 executions
      - 若 executions.size() == 1000 →
           end = min(当前批次所有 trade_done_at)
           继续拉（不减 1 秒，允许重叠）
      - 直到 executions.size() < 1000 或达到翻页硬上限（D6）
   b. 从 executions 提取 unique orderIds
   c. 调用 historyOrders(start, end, status=[Filled, PartialFilled]) 一次性拉回
   d. 按 orderId join → 每笔 execution 补 side / currency / stock_name
   e. 由 SymbolClassifier 打 security_type
   f. 写入 longbridge_staged_executions
5. Stage → trade_records 应用（复用 v2 状态模型 + 期权跳过策略）
6. 推进 cursor，关闭 TradeContext
```

### 5.1 翻页策略

长桥没有 page_token，只有时间窗过滤。当 `has_more=true`：

- 在内存拿到当前批次**最早** `trade_done_at`
- 下一轮用 `end_at = 最早.trade_done_at`（允许秒级重叠）
- 重叠部分由 `trade_id` UNIQUE KEY 自动 dedup
- 当一个 90 天窗口内嵌套翻页超过硬上限（D6 建议 20 轮 = 20000 条）→ fail-fast，记日志报警

### 5.2 订单联合失败的处理（对应 Open question D4）

Execution 已到手但对应 Order 没查到（`side` / `currency` 缺失）：

- **方案 A（推荐）**：fail 整个 staged 批次 —— 不允许 `direction=NULL` 的脏数据进入 `trade_records`
- **方案 B**：对单条执行降级为跳过 + 日志

推荐方案 A（与项目现有「关键字段缺失 → fail-fast」基调一致）。

---

## 6. 凭证与配置

### 6.1 `LongbridgeCredentials` 字段

```java
record LongbridgeCredentials(
    // 方式 1：API Key（Phase 1 仅实现此方式）
    String appKey,
    String appSecret,
    String accessToken,
    // Region（可选）
    String region          // "cn" / "hk" / null（SDK 自动选）
) {}
```

### 6.2 为什么 Phase 1 只做 API Key

OAuth 流程需要**浏览器回跳 + 本地 callback 端口**（默认 `localhost:60355`），不适合无头服务端。API Key 模式与 IBKR Flex Token / Tiger TigerId 的服务端场景一致。

### 6.3 加密存储

沿用 `BrokerCredentialsService`；`appSecret` 与 `accessToken` 入库前对称加密。

### 6.4 Token 过期

`ACCESS_TOKEN` 有有效期（API Key 场景下无 refresh，需用户手动更新）。错误时 fail-fast 并在日志明确提示 token 失效，提示用户在长桥后台重生成。

---

## 7. 分期计划

| Phase | 范围 | 产出 |
|---|---|---|
| **P1 (MVP)** | API Key 认证 / US + HK / `getHistoryExecutions` + `getHistoryOrders` / 手动触发 / 期权跳过 / 无手续费 | 可导入账户最近 90 天的美股 + 港股**股票/窝轮**成交到 `trade_records`，期权落 staged 不入正表 |
| **P2** | 90 天窗口拆分 + `has_more` 翻页 / 增量 cursor / v2 状态模型对齐 / 错误分类 | 生产可用 |
| **P3.x** | 凭证加密完善 / `charge_detail` 手续费导入（若 IBKR/Tiger 也做了）/ 期权事件入 `trade_records`（参考 IBKR BookTrade）/ OAuth 支持（如需要） | 对齐全家桶成熟度 |

---

## 8. 风险与已知约束

1. **Rust native 库依赖**：Java SDK 基于 Rust JNI，部署时需确认目标平台 native 库可用。参见 D2。
2. **成交表无 `side` 是硬依赖**：任何 execution → order 联合缺失都会导致 staged 行 `side=NULL`，按 § 5.2 方案 A fail 整批。
3. **1000 条上限 + `has_more`**：长桥独有复杂度，需在 90 天窗口内嵌套翻页，并设硬上限防失控。
4. **Market 只有 US + HK**：夜盘（`outside_rth`）标记保留在 staged，但不进 `trade_records`。
5. **Token 过期**：fail-fast，日志明确提示用户手动更新 `ACCESS_TOKEN`。
6. **期权 symbol 格式未直接示例**：历史成交响应中的确切期权 symbol 格式需接入时实测（D8）。

---

## 9. Open questions（编码前需敲定）

| # | 问题 | 推荐 | 状态 |
|---|---|---|---|
| **D1** | 认证方式：Phase 1 只做 API Key，OAuth 延后？ | 是 —— API Key 服务端更简单 | ❓ |
| **D2** | Rust native 库：是否需要在 `deploy/` 加平台检测脚本？ | 需要 —— 打包/启动时验证 `.so/.dylib/.dll` 存在并可加载 | ❓ |
| **D3** | 是否同步 `charge_detail` 手续费？ | Phase 1 不同步，与 IBKR/Tiger 对齐 | ❓ |
| **D4** | 同批订单缺字段（`side`/`currency` 查不到）时的行为？ | fail 整个 staged 批次 —— 不允许脏数据 | ❓ |
| **D5** | Phase 1 市场覆盖？ | US + HK（官方 SDK 也只支持这两个） | ❓ |
| **D6** | `has_more` 翻页硬上限（防死循环）？ | 每 90 天窗口最多 20 轮（即 ~20000 条） | ❓ |
| **D7** | 期权成交 Phase 1 策略？ | 方案 A：跳过并落 staged 留底（`security_type=OPTION`），P3.x 再接入 `BookTrade` 式处理 | ❓ |
| **D8** | 期权 symbol 在 `historyExecutions` 响应中的确切格式？ | 文档未直接示例 → 接入时实测，补 `LongbridgeSymbolClassifier` 规则 | ❓ |

---

## 10. 参考资料

### 长桥官方文档（仓库快照）

| 文档 | 用途 |
|---|---|
| `external-resource/longbridge-api/docs/getting-started.md` | SDK 安装 / OAuth + API Key 认证 / 环境变量 / `Market` 枚举（US/HK） |
| `external-resource/longbridge-api/docs/trade-trade-overview.md` | 交易接口清单 |
| `external-resource/longbridge-api/docs/trade-execution-history_executions.md` | **核心接口** — 字段 / Java 示例 / 1000 条上限 / 90 天默认窗口 |
| `external-resource/longbridge-api/docs/trade-order-history_orders.md` | 历史订单（补 `side` / `currency` / `stock_name`） |
| `external-resource/longbridge-api/docs/trade-order-submit.md` | 下单接口 —— 证明长桥支持「港美股，窝轮，期权」 |
| `external-resource/longbridge-api/docs/trade-order-order_detail.md` | `charge_detail` 费用明细（P3.x 用） |
| `external-resource/longbridge-api/docs/trade-trade-definition.md` | 订单状态 / 订单类型等枚举 |
| `external-resource/longbridge-api/docs/qa-trade.md` | 模拟环境限制、权限开通说明（模拟不支持期权） |

### 项目内部参考

| 文档 | 用途 |
|---|---|
| `docs/broker-sync/architecture.md` | 整体架构 |
| `docs/broker-sync/framework/data-persistence.md` | 两阶段导入 / staged 表设计 |
| `docs/broker-sync/framework/import-consistency.md` | v2 状态模型 / fail-fast cleanup |
| `docs/broker-sync/brokers/ibkr/README.md` | IBKR 骨架参考 + BookTrade 期权事件设计 |
| `docs/broker-sync/brokers/tiger/README.md` | Tiger 骨架参考 + 90 天窗口经验 + SymbolNormalizer |
| `docs/broker-sync/brokers/futu/README.md` | Futu 骨架参考（成交+订单两表联合的对照版） |

---

**设计要点 TL;DR**

1. **成交字段极简**：长桥的 `getHistoryExecutions` 比 Futu 还瘦，必须联合 `getHistoryOrders` 才能取到 `side` / `currency` —— 这是硬依赖
2. **1000 条 + `has_more`**：长桥独有的嵌套翻页复杂度，需在 90 天窗口内再翻页
3. **Rust JNI SDK**：调用模型简单（无需回调适配），但部署时要验 native 库
4. **支持期权但 Phase 1 跳过**：期权成交会混在普通成交里，先识别 + 跳过 + 落 staged 留底，P3.x 再接入

请 review 本文档，重点关注 § 9 的 D1–D8。确认后我会修订为 v0.2 并进入编码。
