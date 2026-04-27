# 长桥证券（Longbridge）同步

> **状态**：📋 设计稿 v0.2.2（D1–D9 决策已锁定，待进入编码）
> **适配器（规划）**：`LongbridgeSyncAdapter`
> **API 类型**：Longbridge OpenAPI（HTTPS/JSON，官方 Java SDK，Rust JNI 底层）
> **Broker Code**：`longbridge`
> **账户前提**：单账户（一个 `APP_KEY / APP_SECRET / ACCESS_TOKEN` 对应一个长桥账户）
> **最后更新**：2026-04-27（v0.2.1 → v0.2.2：D3 费用存储方式细化为"聚合列 `order_fee_total` + 专用 `charge_items` JSONB 列"；修正 review 暴露的一批文档瑕疵，见下表）

---

## 文档定位

这是一份**设计阶段**文档，尚未编码。目的：

1. 对齐与 IBKR / Tiger / Futu 的架构一致性
2. 把长桥的**独特差异**（Rust JNI SDK、1000 条单次上限 + `has_more` 翻页、成交字段极简、仅 US/HK 市场）显式记录下来
3. 记录编码前所有已敲定的设计决策（§ 9）

所有引用的长桥协议字段都来自仓库内 `external-resource/longbridge-api/docs/` 下的官方文档快照。

### v0.1 → v0.2 主要变化

| 项目 | v0.1 | v0.2（本次决策） |
|---|---|---|
| **D3 手续费** | Phase 1 不同步 | **改为 Phase 1 同步**（order 级 `charge_detail`，和 Tiger/IBKR 对齐） |
| **D7 期权策略** | 方案 A：识别 + 跳过 + 落 staged（软白名单） | **改为 UNRECOGNIZED fail-fast**（与 `framework/symbol-classification.md` 对齐，禁止软白名单） |
| **D6 翻页上限** | 每 90 天窗口最多 20 轮 | **改为 50 轮** |
| **D2 native 库检测** | Phase 1 必做检测脚本 | **降级为风险记录**，真实跨平台部署时再做 |
| **`historyExecutions` 签名** | `(symbol, start, end)`，按 symbol 循环 | **改为 `(start, end)`**，全账户拉（`symbol` 参数是 optional） |
| **D4 失败分类** | 未写分类 | **明确归类为 `INTERNAL`** |
| **D8 symbol 识别** | OCC 期权正则预设 | **去预设**，只识别股票（保守正则），其他一切 UNRECOGNIZED |
| **D9 首次同步窗口** | （未涉及） | **新增决策**：不做硬上限，接受首次同步可能较长的风险 |

### v0.2 → v0.2.1 修正（事实错误修正，决策未变）

| 位置 | v0.2（错误） | v0.2.1（修正） |
|---|---|---|
| § 5 / § 5.3 / § 8 / § 9（D9） | "按 cursor 从 `first_sync_start_date` 一路拉到 now / 和 Tiger/IBKR 的 cursor 行为一致" | **Tiger 和 IBKR 都没有数据库水位线 cursor**，窗口来自 `SyncRequest`（UI 提供 `startTime` / `endTime`），默认 `endDate=today` / `startDate=endDate-90d`；长桥 P1 与之完全对齐 |
| § 4.1 DDL 方言 | MySQL（`BIGINT AUTO_INCREMENT` / `DATETIME` / `JSON` / `ENGINE=InnoDB` / `KEY ...`） | **PostgreSQL**（`BIGSERIAL` / `TIMESTAMP` / `JSONB` / `CREATE INDEX` 外置） |
| § 4.1 staged 表字段 | 缺 `status` / `imported_trade_id` / `error_message` / `updated_at`；raw 字段用强类型（`DECIMAL` / `DATETIME`） | **补齐 v2 状态模型所需字段**；raw 字段全部改为 `VARCHAR(255)`（参照 Tiger/IBKR 的"无损 staging"原则，类型转换只在 import 阶段） |
| § 7 P2 描述 | "增量 cursor" | 删除（Tiger/IBKR 都没做，对齐即可；若真有需求放 P3.x） |

### v0.2.1 → v0.2.2 修正

| 位置 | v0.2.1 | v0.2.2（本次） |
|---|---|---|
| § 4.1 DDL / § 4.2 映射 / § 9 D3 | `raw_payload JSONB`（语义含糊，像是要存整包 payload） | **`order_fee_total VARCHAR(255)` 聚合列 + `charge_items JSONB` 专用明细列**（不存整包 payload，仅存 `charge_detail.items` 数组；导入路径只读聚合列，JSONB 只做 debug/审计） |
| § 2.2 / § 3.1 | `TradeContext#orderDetail` | **`TradeContext#getOrderDetail`**（核对官方 Java SDK 文档 `trade-order-order_detail.md` L24、L102） |
| § 3.3 / § 4.1 备注 | "`fee = Σ charge_detail.items[].amount`" | **以 `charge_detail.total_amount` 为聚合口径**（取绝对值、字符串化），`Σ items[].fees[].amount` 仅作校验；理由见 § 4.3 注释——长桥 `items` 是**分组层**（BROKER\_FEES / THIRD\_FEES / UNKNOWN），真正费目在 `items[].fees[]` |
| § 5 步骤 4.a | "若 executions.size() == 1000 →..." | **以 `has_more=true` 为翻页终止条件**（官方 Schema 定义，文档 `trade-execution-history_executions.md` L266）；`size==1000` 仅作兜底告警 |
| § 5 步骤 4.f | "对 unique orderIds 逐个调用 orderDetail" | 补充限频/失败处理：撞限频 → `CategorizedSyncException(NETWORK, ...)` 整批 fail-fast（与 § 3.3 对齐） |
| § 4.1 DDL 列注释 / § 4.2 字段映射 | "按成交数量占比分摊到 execution" 写在 `order_fee_total` 列注释和映射表来源列混在一起 | 列注释只说"order 级总手续费"；分摊规则只保留在 § 4.1 备注块；映射表 `fee` 行简化为"来源列 + 分摊规则见 § 4.1 备注" |
| § 7 P2 | "跨平台部署时的 Rust native 库检测" | 删除（与 D2 决策不符，归入 § 8 风险 #1） |
| § 8 风险 1 | 与 § 9 D2 措辞重复 | 简化为"详见 D2"，不重复论据 |
| § 4.3 | "与 framework 的一致性规则 trade off 后的必然选择" | **"与 framework 一致性规则权衡后的结果"**（中文语感修正） |
| § 8 风险（新增） | —— | **新增 JSONB 相关两条**：PostgreSQL 方言锁定加深（低影响）、`charge_detail.total_amount` 与 `Σ fees[].amount` 不一致时的 WARN 策略 |

---

## 1. 长桥与 IBKR / Tiger / Futu 的关键差异

| 维度 | IBKR | Tiger | Futu | **长桥** |
|---|---|---|---|---|
| 接入方式 | 云端 Flex Web Service | 云端 OpenAPI (REST+RSA) | 本地 OpenD 网关 | **云端 HTTPS/JSON + 官方 Java SDK（Rust JNI 底层）** |
| 传输协议 | HTTPS/XML | HTTPS/JSON | TCP/Protobuf | HTTPS/JSON（SDK 封装） |
| SDK 调用模型 | 同步 REST | 同步 REST | 回调式 | **同步（`CompletableFuture.get()`）** |
| Java SDK | 无 / 自研 | 老虎 Java SDK | futu-api Java SDK | **`io.github.longbridge:openapi-sdk:4.0.5`**，Rust JNI |
| 认证 | Flex Token | TigerId + RSA 私钥 | OpenD 解锁密码 MD5 | **API Key（`APP_KEY / APP_SECRET / ACCESS_TOKEN`）**（Phase 1 只做此种，OAuth 延后） |
| 历史成交接口 | Flex Statement | `get_filled_orders` | `Trd_GetHistoryOrderFillList` | **`TradeContext.getHistoryExecutions`** |
| 时间窗口限制 | 365 天 | 90 天 | 90 天 | **90 天（默认）** |
| 单次返回上限 | 无 | 无 | 无 | **1000 条，`has_more=true` 表示被截断** |
| 支持市场 | 全球 | 全球 | 多市场 | **仅 US + HK**（`Market` 枚举） |
| 支持标的 | 股票/期权/期货/外汇… | 股票/期权… | 股票/期货… | **Phase 1 仅股票**；期权 / 窝轮等 → UNRECOGNIZED fail-fast（D7） |

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

4. **仅 US + HK，期权/窝轮走 UNRECOGNIZED**
   - `Market` 枚举只有 `US` / `HK`（文档 `getting-started.md`）
   - 长桥 OpenAPI 下单接口支持「港美股，窝轮，期权」（`trade-order-submit.md`），所以期权/窝轮成交可能**混在** `getHistoryExecutions` 的响应里
   - **Phase 1 策略**（D7）：`LongbridgeSymbolClassifier` 只识别股票，任何非股票 symbol → `CategorizedSyncException(UNRECOGNIZED, ...)` → 整批 fail-fast + cleanup
   - 这与 `framework/symbol-classification.md § 3` 一致，禁止"识别了但跳过"的软白名单反模式
   - 期权/窝轮支持延后到 P3.x（参考 IBKR `BookTrade` 模型）

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
├── LongbridgeRawPayloadAssembler.java  # Execution + Order + charge_detail 合并成 staged 行
├── LongbridgeStagedExecutionRepository.java
├── LongbridgeTradeRecordMapper.java    # staged → trade_records 映射（含 fee 按 quantity 占比分摊）
└── LongbridgeSymbolClassifier.java     # Phase 1 仅识别 STOCK，其他一切 UNRECOGNIZED
```

### 2.2 `LongbridgeClient` 设计

```java
class LongbridgeClient implements AutoCloseable {
    // 通过 Config.fromApikey(appKey, appSecret, accessToken) 创建
    // 单次 sync 任务内持有一个 TradeContext，任务结束 close()

    // 成交：全账户拉（symbol 参数官方文档标为 optional）
    List<Execution> historyExecutions(Instant start, Instant end);

    // 订单：用于联合补 side / currency / stock_name
    List<Order>     historyOrders(Instant start, Instant end, List<OrderStatus> status);

    // 手续费：order 级明细（每个 order 调用一次），用于 D3 手续费同步
    // 官方 Java SDK 方法：TradeContext.getOrderDetail(orderId)
    OrderDetail     orderDetail(String orderId);

    void close();
}
```

调用模型：同步式 `future.get()`，不需要像 Futu 一样做回调 → CompletableFuture 适配。

> 📌 v0.2 修正：v0.1 的 `historyExecutions(String symbol, Instant start, Instant end)` 签名多余 —— 官方文档 `trade-execution-history_executions.md` 明确 `symbol` 是 **optional**。v0.2 改为全账户拉，翻页只按时间窗收紧，**不需要按 symbol 循环**。

---

## 3. 数据源选型

### 3.1 核心接口 —— 三个接口联合

| 接口 | SDK 方法 | 角色 |
|---|---|---|
| **`GET /v1/trade/execution/history`** | `TradeContext#getHistoryExecutions` | **主源** —— 产出 `trade_records` 行（全账户拉，不按 symbol） |
| **`GET /v1/trade/order/history`** | `TradeContext#getHistoryOrders` | **字段补充** —— 用 `order_id` 关联，取 `side` / `currency` / `stock_name` |
| **`GET /v1/trade/order`（`order_detail`）** | `TradeContext#getOrderDetail` | **手续费** —— 逐 order 查 `charge_detail`，取 `total_amount` 填 `trade_records.fee`（D3） |

### 3.2 为什么必须联合订单接口

成交接口**没有买卖方向**。不拉订单表，无法填 `trade_records.direction` 字段——这是硬要求，不是优化。

### 3.3 为什么 Phase 1 同步手续费（D3 修正）

v0.1 原本推荐"Phase 1 不同步手续费，与 IBKR / Tiger 对齐"。v0.2 修正这个判断 —— 实际上 **Tiger 和 IBKR 都已经落库手续费**：

- `TigerTradeRecordMapper#calculateFee` = `commission + gst`
- `IbkrImportWorker#calculateFee` = `commission + tradeCharge`

为保持**跨 broker 数据一致性**，长桥 Phase 1 也同步手续费：

- **数据源**：`order_detail.charge_detail`
- **调用粒度**：**per order**（不是 per execution）—— 一个 order 下 N 笔部分成交**只查 1 次**
- **聚合口径**：直接取 `charge_detail.total_amount`（官方给出的订单总费用），取绝对值后字符串化存入 `order_fee_total`；`Σ items[].fees[].amount` 仅作**校验**用（不相等时打 WARN 日志，以 `total_amount` 为准）
- **为什么不自己展开求和**：长桥 `charge_detail.items[]` 是**分组层**（`BROKER_FEES` / `THIRD_FEES` / `UNKNOWN` 三个枚举），真正的费目明细在 `items[].fees[]`。`total_amount` 是官方提供的顶层合计，直接用最简单；自己展开求和会多一层逻辑风险（如官方将来新增分组枚举会漏）
- **明细存档**：`charge_detail.items` 原数组（含 `fees[]` 子数组）落到 staged 表的 `charge_items JSONB` 列，仅用于 debug/审计，import 阶段不消费
- **性能评估**：单次 sync 的订单数远小于成交数；配合 § 5.1 的翻页硬上限 50 轮，实际 API 调用可控
- **限频失败**：撞上限频 → `CategorizedSyncException(NETWORK, "order_detail rate limited")` → fail-fast，用户稍后重试

### 3.4 不采用的接口

| 接口 | 不采用理由 |
|---|---|
| `today_executions` / `today_orders` | 本功能只做历史同步，不做实时 |
| WebSocket 交易推送 | 推送式不符合批次同步 |
| `cashflow` / `stock positions` / `fund positions` | 超出本功能范围 |

---

## 4. 数据模型

### 4.1 Staged 表：`longbridge_staged_executions`

**设计原则**（对齐 Tiger `tiger_staged_orders` / IBKR `ibkr_staged_trades`）：

1. **无损 staging**：所有来自上游 API 的 raw 字段统一存 `VARCHAR(255)`，类型转换（数字 / 时间戳 / 枚举）只在 import → `trade_records` 阶段做
2. **v2 状态模型**：`status` / `imported_trade_id` / `error_message` / `updated_at` 配合 `framework/import-consistency.md` 的状态机（`PENDING` → `IMPORTED` / `FAILED` / `CLEANED`）
3. **费用双列存储**（D3 细化）：
   - `order_fee_total VARCHAR(255)` —— 聚合列，取自 `charge_detail.total_amount`（绝对值），**import 阶段只读这一列**
   - `charge_items JSONB` —— 仅存 `charge_detail.items` 原数组（含分组 `code`/`name` 和 `fees[]` 子数组），仅用于 debug/审计，import 阶段不消费
   - **不存** `charge_detail` 的父级 `total_amount` / `currency`（已由 `order_fee_total` 表达）
   - **不存**整个 `order_detail` 返回体（避免把和费用无关的订单字段冗余落库）

```sql
CREATE TABLE longbridge_staged_executions (
    id                     BIGSERIAL       PRIMARY KEY,
    batch_id               BIGINT          NOT NULL,             -- 回指 broker_sync_batches.id
    -- Raw fields (1:1 from Execution; 全部 VARCHAR，无损 staging)
    trade_id               VARCHAR(255)    NOT NULL,             -- 成交 ID（dedup key）
    order_id               VARCHAR(255)    NOT NULL,
    symbol                 VARCHAR(255)    NOT NULL,             -- "AAPL.US" / "700.HK"
    trade_done_at          VARCHAR(255)    NOT NULL,             -- ISO-8601 字符串，UTC
    quantity               VARCHAR(255)    NOT NULL,
    price                  VARCHAR(255)    NOT NULL,
    -- Enriched from Order (join by order_id; 全部 VARCHAR，无损)
    side                   VARCHAR(255),                         -- "Buy" / "Sell"
    currency               VARCHAR(255),                         -- "USD" / "HKD"
    order_status           VARCHAR(255),                         -- "FilledStatus" 等
    stock_name             VARCHAR(255),
    outside_rth            VARCHAR(255),                         -- 盘前盘后（保留用，不入 trade_records）
    -- Fee: derived from order_detail.charge_detail (D3)
    order_fee_total        VARCHAR(255),                         -- order 级总手续费，来自 charge_detail.total_amount（绝对值）
    charge_items           JSONB,                                -- charge_detail.items 原数组（含 fees[] 子数组）；仅用于 debug/审计，import 阶段不消费
    -- Classification (由 SymbolClassifier 填，Phase 1 只有 STOCK 会落 staged)
    security_type          VARCHAR(255),                         -- "STOCK"
    -- v2 状态模型（对齐 tiger_staged_orders / ibkr_staged_trades）
    status                 VARCHAR(32)     NOT NULL DEFAULT 'PENDING',
    imported_trade_id      BIGINT,                               -- 回指 trade_records.id（import 成功后填）
    error_message          TEXT,                                 -- 失败原因（带 [CATEGORY] 前缀）
    created_at             TIMESTAMP       NOT NULL,
    updated_at             TIMESTAMP       NOT NULL,
    CONSTRAINT uk_lb_staged_batch_trade UNIQUE (batch_id, trade_id)
);
CREATE INDEX idx_lb_staged_batch    ON longbridge_staged_executions (batch_id);
CREATE INDEX idx_lb_staged_trade_id ON longbridge_staged_executions (trade_id);
CREATE INDEX idx_lb_staged_status   ON longbridge_staged_executions (status);
```

> **`charge_items` 内容约定**（仅用于 debug，import 阶段不消费）：
> ```json
> [
>   {
>     "code": "BROKER_FEES",
>     "name": "收费明细",
>     "fees": [
>       { "code": "COMMISSION", "name": "佣金",     "amount": "15.00", "currency": "HKD" },
>       { "code": "PLATFORM",   "name": "平台使用费", "amount": "15.00", "currency": "HKD" }
>     ]
>   },
>   {
>     "code": "THIRD_FEES",
>     "name": "第三方收费明细",
>     "fees": [
>       { "code": "STAMP_DUTY", "name": "印花税", "amount": "5.00", "currency": "HKD" }
>     ]
>   }
> ]
> ```
> - 存完整 `items[]` 数组（含分组层 `code`/`name` + 子数组 `fees[]`），不做摊平
> - "查过但 `items` 为空"时存 `[]`（区分"查过无费目" vs "没查过 NULL"）
> - `charge_detail.total_amount` / `charge_detail.currency` 父级字段**不**存（已由 `order_fee_total` 表达）
> - **校验**：若 `|Σ items[].fees[].amount| ≠ |total_amount|`，打 WARN 日志带 `order_id` + 两个值，**不阻断**导入，以 `total_amount` 为准

> **手续费分摊说明**（D3）：
> 长桥 `charge_detail` 是 order 级的，而 `trade_records.fee` 是 execution 级的。一个 order 如果有 N 笔部分成交，手续费按 **quantity 占比**分摊到每笔 execution：
> `execution.fee = order_fee_total × (execution.quantity / order.total_filled_quantity)`
> 分摊时对最后一笔用"余数兜底"以避免浮点累计误差。分摊动作发生在 **import 阶段**（staged → `trade_records`），staged 表本身的 `order_fee_total` 列保持 order 级聚合值不变。

### 4.2 字段映射 → `trade_records`

| `trade_records` 字段 | 来源 | 规则 |
|---|---|---|
| `broker_id` | 配置 | 长桥的 broker 记录 ID |
| `broker_ref` | `trade_id` | 长桥自身全局唯一 |
| `trade_time` | `trade_done_at` | UTC → 本地时区显示 |
| `symbol` | `symbol` 的 ticker 部分 | `AAPL.US → AAPL`；`700.HK → 0700`（复用 Tiger 的 `SymbolNormalizer`）。Phase 1 仅股票，期权/窝轮在 SymbolClassifier 阶段就已 UNRECOGNIZED |
| `market` | `symbol` 的 region 后缀 | `.US → US`，`.HK → HK` |
| `direction` | `side`（来自订单表） | `Buy → BUY`，`Sell → SELL` |
| `quantity` | `quantity` | 直接用 |
| `price` | `price` | 直接用 |
| `currency` | `currency`（来自订单表） | 直接用 |
| `fee` | `order_fee_total` | import 阶段按 quantity 占比分摊到每笔 execution（规则见 § 4.1 备注块）；与 Tiger/IBKR 的 `calculateFee` 语义对齐 |
| `source_batch_id` | `batch_id` | |

### 4.3 期权 / 窝轮事件处理（D7 修正）

长桥 OpenAPI **支持股票 + 窝轮 + 期权交易**（见 `trade-order-submit.md`）。期权/窝轮成交会通过**同一个** `getHistoryExecutions` 接口返回，混在普通成交列表里。

**Phase 1 策略：UNRECOGNIZED fail-fast（对齐 `framework/symbol-classification.md`）**

- `LongbridgeSymbolClassifier` 只识别股票（见 § 4.4 的保守正则）
- 任何非股票 symbol → 抛 `CategorizedSyncException(UNRECOGNIZED, message=...)`
- 整批 staged 行走 fail-fast cleanup 流程（与 Tiger / IBKR 完全一致）
- 错误消息模板：`"Unrecognized symbol '<symbol>' in Longbridge execution <trade_id>; only STOCK is supported in Phase 1"`

**用户影响**：如果账户里有期权/窝轮成交，整批 sync 会失败，直到 Phase 1 之后支持这些标的类型，或用户手动从时间窗中隔离出这些交易。这是**与 framework 一致性规则权衡后的结果** —— `framework/symbol-classification.md § 3 / § 4.2` 禁止"识别了但跳过"的软白名单。

### 4.4 Symbol 分类（`LongbridgeSymbolClassifier`）

Phase 1 使用**保守正则**识别股票，其他所有 symbol 形式一律 UNRECOGNIZED。期权/窝轮的真实 symbol 格式等编码 + debug 跑过真实账户数据后再补一份"symbol 格式观察笔记"，然后在 P3.x 决定如何接入期权（D8）。

```
规则（严格匹配）：
  ^[A-Z]+\.US$         → STOCK   （例：AAPL.US, TSLA.US）
  ^\d{1,5}\.HK$        → STOCK   （例：700.HK, 0700.HK, 00700.HK）
  其他一切             → UNRECOGNIZED（含小写 / 期权 OCC / SEHK 窝轮 / 非 US/HK / 带 . 分隔符的 ETF 变体等）
```

**边界说明**：

- 美股 ticker 有些带 `.` 分隔符（如 `BRK.B.US`），如命中 UNRECOGNIZED，需在 P3.x 扩展正则
- HK ticker 在长桥返回里是否始终已补前导 0，需编码时实测确认（不属于设计阻塞）

---

## 5. 同步流程（`LongbridgeSyncAdapter#sync`）

```
1. 加载 LongbridgeCredentials
2. 通过 Config.fromApikey(...) 创建 TradeContext（try-with-resources）
3. 从 SyncRequest 取同步窗口（默认值见 § 5.3），按 90 天拆分成 N 个子窗口
4. 对每个 90 天窗口：
   a. 循环调用 historyExecutions(start, end)，以 `has_more` 为翻页终止条件：
      - 收集 executions
      - 若响应 `has_more == true` →
           end = min(当前批次所有 trade_done_at)
           继续拉（允许秒级重叠，由 UNIQUE (batch_id, trade_id) dedup）
      - 若 `has_more == false` → 本窗口翻页完成
      - 达到翻页硬上限（D6 = 50 轮）时 fail-fast（见 § 5.1）
      - 兜底告警：若某轮响应 `has_more=false` 但 `trades.size() == 1000`（官方约定不会发生），打 WARN 日志
   b. 对 executions 中每个 symbol 先过 SymbolClassifier
      - 命中 UNRECOGNIZED → 抛 CategorizedSyncException(UNRECOGNIZED, ...) 整批 fail
   c. 从 executions 提取 unique orderIds
   d. 调用 historyOrders(start, end, status=[Filled, PartialFilled]) 一次性拉回
   e. 按 orderId join → 每笔 execution 补 side / currency / stock_name
      - 联合失败（orderId 缺失）→ 抛 CategorizedSyncException(INTERNAL, ...) 整批 fail
   f. 对 unique orderIds 逐个调用 getOrderDetail(orderId) 取 charge_detail（D3）：
      - 取 charge_detail.total_amount 的绝对值存入 order_fee_total
      - 取 charge_detail.items 原数组存入 charge_items JSONB
      - `|Σ items[].fees[].amount| ≠ |total_amount|` → WARN 日志，不阻断（以 total_amount 为准）
      - 撞限频 / 5xx / 超时 → 抛 CategorizedSyncException(NETWORK, "order_detail rate limited or transient failure for order=<orderId>") 整批 fail-fast
   g. 写入 longbridge_staged_executions（status='PENDING'）
5. Stage → trade_records 应用（复用 v2 状态模型 + fail-fast cleanup）
   - fee 按 quantity 占比分摊（见 § 4.1 备注）
6. 关闭 TradeContext
```

### 5.1 翻页策略

长桥没有 page_token，只有时间窗过滤。当响应的 `has_more=true`（官方 Schema `trade-execution-history_executions.md` L266：单次最多 1000 条，超过时 `has_more=true`）：

- 在内存拿到当前批次**最早** `trade_done_at`
- 下一轮用 `end_at = 最早.trade_done_at`（允许秒级重叠）
- 重叠部分由 `trade_id` UNIQUE KEY 自动 dedup
- 当一个 90 天窗口内嵌套翻页超过硬上限（**D6 = 50 轮，约 50000 条**）→ fail-fast，按 `CategorizedSyncException(INTERNAL, "Longbridge pagination exceeded 50 rounds in window [start, end]; shrink the sync window and retry")` 抛出
- **终止条件只看 `has_more` 字段本身**，不以 `trades.size() == 1000` 推断（size 判断是兜底告警，非终止信号）

### 5.2 订单联合失败的处理（D4 锁定）

Execution 已到手但对应 Order 没查到（`side` / `currency` 缺失）：

- **策略**：fail 整个 staged 批次（不允许 `direction=NULL` 的脏数据进入 `trade_records`）
- **失败分类**：`INTERNAL`（我们已识别证券类型，只是 order 表关联失败 —— 不属于 UNRECOGNIZED）
- **错误消息模板**：`"Longbridge execution <trade_id> references order <order_id> which is missing from history_orders response"`

### 5.3 同步窗口来源（D9 锁定）

- **策略**：同步窗口完全来自 `SyncRequest.startTime` / `SyncRequest.endTime`，**不引入数据库侧的水位线 cursor**
- **默认值**（UI 未填时，`SyncRequest` 层或 adapter 层补齐）：`endTime = now`；`startTime = endTime - 90 days` —— 与 `TigerSyncAdapter#resolveStartDate` / `IbkrSyncAdapter#resolveStartDate` 完全对齐
- **长窗口（例如 3 年历史）**：用户在 UI 上手动填 `startTime` 为 3 年前的日期，本 adapter 会自动按 90 天切成若干子窗口逐个拉（§ 5 步骤 3+4），**不存在"长桥 90 天 API 上限 → 只能同步 90 天"的退化**
- **已接受的风险**（D9）：长窗口 + 高频账户场景下，首次同步可能达到 10 分钟量级；若 P3.x 用户反馈问题，再新增"单次 sync 最大总窗口"或"每次 sync 最多 N 个 90 天窗口"的硬上限配置
- **翻页硬上限（D6 = 50 轮/窗口）仍生效**，作为防 API 异常（例如长桥始终返 `has_more=true`）的兜底

> 📌 与 v0.2 的差异：v0.2 误将本节描述为"按数据库 cursor 从 `first_sync_start_date` 拉到 now / 与 Tiger/IBKR 的 cursor 行为一致"。实际上 Tiger/IBKR 都**没有**数据库 cursor，窗口均来自 `SyncRequest`；长桥 P1 完全跟随此模式，v0.2.1 修正以消除混淆。

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

`ACCESS_TOKEN` 有有效期（API Key 场景下无 refresh，需用户手动更新）。过期时：

- **错误分类**：`CategorizedSyncException(AUTH, ...)`
- **错误消息模板**：`"Longbridge access_token expired or invalid; please regenerate it in the Longbridge developer console (https://open.longbridgeapp.com/) and update credentials"`
- 行为：fail-fast，与 IBKR Flex Token 过期处理一致

---

## 7. 分期计划

| Phase | 范围 | 产出 |
|---|---|---|
| **P1 (MVP)** | API Key 认证 / US + HK / `getHistoryExecutions` + `getHistoryOrders` + `getOrderDetail`（手续费）/ 手动触发 / 期权/窝轮 UNRECOGNIZED fail-fast / SymbolClassifier 保守正则 | 可导入账户最近 90 天的美股 + 港股**股票**成交到 `trade_records`（含手续费） |
| **P2** | 90 天窗口拆分 + `has_more` 翻页硬上限 50 / v2 状态模型对齐 / `INTERNAL` & `AUTH` & `NETWORK` & `UNRECOGNIZED` 四类错误分类 | 生产可用 |
| **P3.x** | 凭证加密完善 / 期权 symbol 格式观察 + 接入（参考 IBKR BookTrade 模型）/ 窝轮支持 / OAuth 支持（如需要）/ 首次同步窗口硬上限（若 D9 暴露问题）/ 数据库侧增量水位线（若 Tiger/IBKR 未来也要做，统一框架层实现） | 对齐全家桶成熟度 |

---

## 8. 风险与已知约束

1. **Rust native 库依赖**：详见 § 9 D2 决策（Phase 1 延后至真实跨平台部署时再做平台检测脚本；本地单机部署由 `TradeContext.create()` 自身 fail-fast 兜底）。
2. **成交表无 `side` 是硬依赖**：任何 execution → order 联合缺失都会导致 staged 行 `side=NULL`，按 § 5.2 整批 fail（`INTERNAL`）。
3. **1000 条上限 + `has_more`**：长桥独有复杂度，需在 90 天窗口内嵌套翻页，硬上限 50 轮/窗口。
4. **Market 只有 US + HK**：夜盘（`outside_rth`）标记保留在 staged，但不进 `trade_records`。
5. **Token 过期**：fail-fast，`AUTH` 分类，日志明确提示用户手动更新 `ACCESS_TOKEN`。
6. **期权 / 窝轮走 UNRECOGNIZED（D7 决策）**：Phase 1 账户若含期权/窝轮成交，整批 sync 失败，需用户在时间窗上隔离或等待 P3.x 接入。
7. **期权 symbol 格式未确认（D8）**：等编码 + 真实数据跑一遍后单独出"symbol 格式观察笔记"。
8. **同步窗口来自 SyncRequest，无数据库水位线（D9 决策）**：与 Tiger/IBKR 完全一致；长历史场景下最坏首次同步耗时 10 分钟量级，已接受；若 P3.x 暴露问题再加硬上限或水位线。
9. **JSONB 引入 PostgreSQL 方言锁定**（v0.2.2 新增）：`charge_items` 列是项目首个 JSONB 列。**缓解**：仅作 debug/审计字段，不参与业务查询路径；若未来需迁库，直接丢弃该列即可，不影响 `trade_records` 的导入逻辑（后者只读 `order_fee_total`）。
10. **`charge_detail.total_amount` 与 `Σ items[].fees[].amount` 不一致**（v0.2.2 新增）：可能由官方新增费目分组、或 `total_amount` 取整误差引起。**策略**：以 `total_amount` 为准（官方顶层合计），不相等时打 WARN 日志带 `order_id` + 两个值，**不阻断**导入。如高频出现需另行 review 口径。

---

## 9. 决策记录（v0.2 全部锁定 ✅）

所有条目在 2026-04-27 review 中敲定。本节只保留**最终决策 + 理由摘要**，详细讨论见设计 review 会话记录。

| # | 决策 | 最终方案 | 理由摘要 |
|---|---|---|---|
| **D1** | 认证方式 | ✅ **Phase 1 只做 API Key**，OAuth 延后 | OAuth 要浏览器回跳 + 本地 callback 端口，不适合无头服务端；API Key 与 IBKR Flex Token / Tiger RSA 的服务端凭证模式一致 |
| **D2** | Rust native 库检测 | ✅ **延后到真实跨平台部署时再做**，Phase 1 仅在 § 8 记录风险 | 本地单机部署下 `TradeContext.create()` 会自己 fail-fast；deploy/ 目前没有平台检测基础设施，单为长桥加不划算 |
| **D3** | 手续费同步 | ✅ **Phase 1 同步**（`getOrderDetail.charge_detail` per-order 调用；staged 表存**聚合列 `order_fee_total`**（取 `total_amount` 绝对值）+ **专用 `charge_items` JSONB 列**（仅 `items[]` 原数组，不存整包 payload）；import 阶段只读聚合列，按 quantity 占比分摊到 execution） | Tiger / IBKR 都已经同步手续费（`TigerTradeRecordMapper`、`IbkrImportWorker` 均用 `calculateFee`），长桥不跟上会破坏跨 broker 数据一致性；明细列仅作 debug/审计，不参与导入路径，保持方言锁定风险可控 |
| **D4** | Order 联合缺字段 | ✅ **fail 整批**，分类 `INTERNAL`，错误消息指出具体 `trade_id`/`order_id` | 不允许 `direction=NULL` 的脏数据入 `trade_records`；已识别证券类型所以不是 UNRECOGNIZED |
| **D5** | 市场覆盖 | ✅ **US + HK** | 官方 `Market` 枚举只有这两个 |
| **D6** | `has_more` 翻页硬上限 | ✅ **50 轮/90 天窗口** | 50000 条成交对个人账户已远超正常量，作为防失控兜底 |
| **D7** | 期权/窝轮 Phase 1 策略 | ✅ **UNRECOGNIZED fail-fast**（修正 v0.1 方案） | `framework/symbol-classification.md § 3 / § 4.2` 禁止"识别了但跳过"的软白名单；与 Tiger / IBKR UNRECOGNIZED 行为对齐 |
| **D8** | 期权 symbol 格式 | ✅ **Phase 1 不预设**，SymbolClassifier 只用保守正则识别股票（`^[A-Z]+\.US$` / `^\d{1,5}\.HK$`），其他全部 UNRECOGNIZED | 没真实跑过长桥账户，不提前猜期权格式；等 debug 出真实数据后单独出"symbol 格式观察笔记"再扩展 |
| **D9** | 同步窗口上限 | ✅ **不设硬上限**，窗口完全来自 `SyncRequest`（默认最近 90 天），接受最坏情况 10 分钟量级 | 与 Tiger/IBKR 完全一致：`SyncRequest.startTime/endTime` 由 UI 提供，无数据库水位线 cursor；翻页硬上限（D6）作为防失控兜底 |

### 编码前唯一剩下的前置动作

设计稿 v0.2.2 已完整，可直接进入**编码阶段**。编码启动前只需：

1. 在 `BrokerDefinition` 里注册 `longbridge` broker code
2. 按 § 2.1 的骨架创建 `backend/src/main/java/com/vortex/sync/adapter/longbridge/` 包

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
| `external-resource/longbridge-api/docs/trade-order-order_detail.md` | **`charge_detail` 费用明细 — Phase 1 用于同步 `trade_records.fee`**（D3） |
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
2. **1000 条 + `has_more`**：长桥独有的嵌套翻页复杂度，以 `has_more` 为终止条件在 90 天窗口内再翻页（硬上限 50 轮/窗口）
3. **Rust JNI SDK**：调用模型简单（无需回调适配），跨平台部署时再处理 native 库
4. **Phase 1 只收股票**：期权/窝轮 → UNRECOGNIZED fail-fast，与 `framework/symbol-classification.md` 对齐
5. **手续费同步**：`getOrderDetail.charge_detail.total_amount` per-order 取得，staged 落**聚合列 + `charge_items` JSONB 明细列**（仅 items 数组，非整包 payload），import 阶段按 quantity 占比分摊 —— 与 Tiger/IBKR 的 `calculateFee` 语义对齐

---

v0.2.2 已完成 D1–D9 全部决策锁定 + D3 细化（双列费用存储），可直接进入编码。
