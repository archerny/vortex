# Tiger 暂存表结构与字段映射

> **创建日期**：2026-04-21
> **最后更新**：2026-04-22
> **状态**：🔧 Phase 3 实施中（Stage 1/2/3 已完成）
> **关联**：[framework/data-persistence.md](../../framework/data-persistence.md) | [open-api.md](./open-api.md) | [phase3-plan.md](./phase3-plan.md)

本文档定义 Tiger 专属的暂存表结构（`tiger_staged_orders`），以及从暂存表映射到 `trade_records` 的字段规范。框架层通用内容（`broker_sync_batches`、`trade_records` 扩展字段、两阶段导入原则）参见 [framework/data-persistence.md](../../framework/data-persistence.md)。

---

## 一、账户前提与适用范围

- **账户类型**：老虎国际 = **环球账户（GLOBAL，账号以 `U` 开头）**
- **SDK**：`io.github.tigerbrokers:openapi-java-sdk:2.4.7`
- **数据源**：`MethodName.FILLED_ORDERS`（已成交订单）
- **本期支持的资产类型**：`STK`（股票）、`OPT`（期权）
- **本期不支持的资产类型**（暂存但导入 `FAILED`）：`WAR` / `IOPT` / `FUT` / `FUND` / `CASH` / `CC`
- **本期不支持的订单特性**（暂存但导入 `FAILED`）：碎股（`quantityScale > 0`）

---

## 二、暂存粒度决策

**暂存粒度为 Order（订单级），不设执行明细附表。**

Tiger Open API 的 `FILLED_ORDERS` 接口只返回 `TradeOrder`（订单级）数据，不像 IBKR 的 Flex Web Service 那样同时提供 `Order` 与 `TradeConfirm` 两层。Tiger 的 `TradeOrder` 已包含完整的成交数量、加权均价、佣金等业务字段，足以直接映射到 `trade_records`，因此本期**不设 `tiger_staged_trade_confirms` 附表**。

### Tiger 的三个 ID 对比（容易踩坑）

| 字段 | 类型 | 唯一性 | 是否用于去重 |
|------|------|--------|-------------|
| **`id`** | `long` | **全局唯一** | ✅ **作为 `external_id` 与暂存表去重键** |
| `orderId` | `long` | 仅本地自增 | ❌ 不唯一，**不可用于去重** |
| `externalId` | `String` | 通常等于 `orderId` | ❌ 不唯一 |

> ⚠️ `TigerOrderRecord.orderId` 字段目前存的是 `TradeOrder.getId()`（即全局唯一 `id`），命名有歧义，在 Phase 3 保留现有字段命名但要在注释中明确说明；或借机重命名为 `tigerId`（具体见 [phase3-plan.md](./phase3-plan.md) 的实施步骤）。

---

## 三、`tiger_staged_orders` — Tiger 核心暂存表（Order 粒度）

字段 1:1 对应 `TigerOrderRecord.java`，业务字段全部使用 `VARCHAR(255)` 存储（数据无损原则）。另加暂存管理字段和审计字段。

### 3.1 暂存管理字段

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | BIGSERIAL | PK | 主键 |
| `batch_id` | BIGINT | NOT NULL, FK→`broker_sync_batches.id` | 所属同步批次 |
| `status` | VARCHAR(32) | NOT NULL DEFAULT 'PENDING' | 记录状态（见下方状态枚举） |
| `imported_trade_id` | BIGINT | | 导入成功后关联的 `trade_records.id`，用于反向追溯 |
| `error_message` | TEXT | | 记录级错误信息（转换失败、冲突等的详细说明） |

### 3.2 Tiger Order 数据字段（全部 VARCHAR(255)）

| # | 列名 | 对应 `TigerOrderRecord` 字段 | 说明 |
|---|------|------------------------------|------|
| 1 | `tiger_id` | `orderId`（实际存 `TradeOrder.getId()`） | **Tiger 全局唯一订单 ID，去重键** |
| 2 | `account` | `account` | 资金账号（U 开头） |
| 3 | `action` | `action` | `BUY` / `SELL` |
| 4 | `status_raw` | `status` | 订单状态：`FILLED` / `PARTIALLY_FILLED` 等 |
| 5 | `order_time` | `orderTime` | 下单时间（毫秒时间戳，字符串存储；期权事件可能为空或系统时间） |
| 6 | `trade_time` | `tradeTime` | 成交/最后更新时间（毫秒时间戳） |
| 7 | `quantity` | `quantity` | 下单数量（原始值，碎股时需配合 `quantity_scale`） |
| 8 | `quantity_scale` | `quantityScale` | 碎股偏移量，`真实数量 = quantity * 10^-quantityScale` |
| 9 | `filled_quantity` | `filledQuantity` | 已成交数量（原始值） |
| 10 | `avg_fill_price` | `avgFillPrice` | 平均成交价 |
| 11 | `commission` | `commission` | 佣金合计（含印花税、证监会费等） |
| 12 | `gst` | `gst` | 消费税（TBSG 牌照才有，环球账户通常为空） |
| 13 | `realized_pnl` | `realizedPnl` | 已实现盈亏 |
| 14 | `symbol` | `symbol` | 证券代码（股票：`AAPL` / `00700`；期权：正股代码） |
| 15 | `contract_name` | `contractName` | 合约名称 |
| 16 | `sec_type` | `secType` | `STK` / `OPT` / `WAR` / `IOPT` / `FUT` / `FUND` |
| 17 | `currency` | `currency` | `USD` / `HKD` / `CNH` |
| 18 | `exchange` | `exchange` | 交易所 |
| 19 | `market` | `market` | `US` / `HK` / `CN` |
| 20 | `identifier` | `identifier` | 期权 21 位标识（OPRA 格式） |
| 21 | `multiplier` | `multiplier` | 合约乘数（期权通常为 100） |
| 22 | `expiry` | `expiry` | 期权到期日（`yyyyMMdd` 格式） |
| 23 | `strike` | `strike` | 期权行权价 |
| 24 | `put_call` | `putCall` | `CALL` / `PUT` |
| 25 | `order_type` | `orderType` | `MKT` / `LMT` / `STP` / ... |
| 26 | `limit_price` | `limitPrice` | 限价（LMT 单） |
| 27 | `attr_desc` | `attrDesc`（**Phase 3 新增**） | 订单描述（期权事件识别字段，如 `Exercise` / `Assignment` / `Expired`） |

### 3.3 审计字段

| 列名 | 类型 | 约束 |
|------|------|------|
| `created_at` | TIMESTAMP | NOT NULL DEFAULT NOW() |
| `updated_at` | TIMESTAMP | NOT NULL DEFAULT NOW() |

### 3.4 暂存记录状态枚举

与 IBKR 完全一致（框架层复用）：

| 状态 | 含义 |
|------|------|
| `PENDING` | 待处理，等待导入到 `trade_records` |
| `IMPORTED` | 已成功导入到 `trade_records`，`imported_trade_id` 已填充 |
| `SKIPPED` | 跳过（重复记录，`trade_records` 中已存在对应 `external_id`） |
| `CONFLICT` | 与已有记录字段不一致（预留，当前不触发） |
| `FAILED` | 转换或导入失败，详见 `error_message` |

### 3.5 状态流转

```
PENDING → IMPORTED   （成功导入到 trade_records）
PENDING → SKIPPED    （重复记录，已存在）
PENDING → FAILED     （转换失败、资产类型不支持、碎股等）
PENDING → CONFLICT   （当前不触发，预留）
```

### 3.6 索引与约束

| 类型 | 列 | 说明 |
|------|-----|------|
| UNIQUE | `tiger_id` | 全局唯一订单 ID，防止同一订单重复写入暂存表 |
| INDEX | `batch_id` | 按批次查询暂存记录 |
| INDEX | `status` | 按状态筛选 |
| FK | `batch_id` → `broker_sync_batches.id` | 外键关联批次 |

---

## 四、ER 关系图

```
broker_sync_batches (1) ──── (N) tiger_staged_orders    ← 导入 trade_records 的数据源
        │                          │
        │ (1)                      │
        │                          │
        └──── (N) trade_records (通过 sync_batch_id 关联)

tiger_staged_orders.imported_trade_id ──── trade_records.id (可选反向关联)

trade_records.(external_broker + external_id) ←→ tiger_staged_orders.tiger_id
```

---

## 五、字段映射规范（`tiger_staged_orders` → `trade_records`）

> **适用范围**：`TigerImportService` / `TigerImportWorker` 的核心转换逻辑

### 5.1 前置过滤（不映射，直接 `FAILED` 或 `SKIPPED`）

按如下顺序判断，命中任一规则后**不进行后续字段映射**：

| # | 条件 | 暂存状态 | `error_message` |
|---|------|---------|-----------------|
| 1 | `trade_records` 中已存在 `external_broker='tiger' AND external_id=tigerId` | `SKIPPED` | `Already imported in trade #{id}` |
| 2 | `filled_quantity` 为空、0 或 `null` | `SKIPPED` | `Not actually filled (filled_quantity <= 0)` |
| 3 | `sec_type` 不在 `{STK, OPT}` | `FAILED` | `Unsupported secType: {value}. Only STK/OPT are supported.` |
| 4 | `quantity_scale` > 0（碎股订单） | `FAILED` | `Fractional share not supported. Raw qty={qty}, scale={scale}, realQty={real}` |
| 5 | `attr_desc` 非空（期权事件订单） | `FAILED` | `Option event attrDesc={value} — mapping TBD (will be supported after sample review)` |
| 6 | 资产类型为 `OPT` 但 `put_call` 为空或不在 `{CALL, PUT}` | `FAILED` | `Option missing or invalid putCall: {value}` |
| 7 | `action` 不在 `{BUY, SELL}` | `FAILED` | `Unsupported action: {value}` |

> 🔸 **规则 5（attrDesc 非空）是本期的临时策略**：由于目前缺乏 `attrDesc` 的真实样本（已确认"不确定，先留空"），所有 `attrDesc` 非空的订单统一标记 `FAILED` 并保留原值。待收集到足够样本后再补充期权事件映射表，将其从 `FAILED` 切换为 `OPTION` / 对应 `trigger_ref_type`。参见 [phase3-plan.md 阶段 6](./phase3-plan.md#阶段-6attrdesc-实证与期权事件映射补齐待真实数据到手后再做)。

### 5.2 直接映射（简单转换）

| # | `trade_records` 字段 | 类型 | ← 源 | 转换规则 |
|---|---------------------|------|-------|---------|
| 1 | `trade_date` | `LocalDate` | `trade_time` | `Instant.ofEpochMilli(parseLong(tradeTime)).atZone(Asia/Shanghai).toLocalDate()` |
| 2 | `broker_id` | `Long` | — | `brokers.findByBrokerCode("tiger").getId()`，导入批次级缓存一次 |
| 3 | `currency` | `Currency` (enum) | `currency` | `USD→USD`、`HKD→HKD`、`CNH→CNY`（老虎 CNH 映射到系统 CNY） |
| 4 | `trade_type` | `TradeType` (enum) | `action` | `BUY→BUY`，`SELL→SELL` |
| 5 | `quantity` | `Integer` | `filled_quantity` + `quantity_scale` | 经 5.1 规则 4 过滤后此处 `scale=0`，直接 `abs(parseInt(filledQuantity))` |
| 6 | `price` | `BigDecimal` | `avg_fill_price` | `new BigDecimal(avgFillPrice)` |
| 7 | `amount` | `BigDecimal` | 计算 | STK：`quantity × price`<br>OPT：`quantity × price × multiplier` |
| 8 | `fee` | `BigDecimal` | `commission` + `gst` | `abs(parseBigDecimal(commission)) + abs(parseBigDecimal(gst, default=0))` |
| 9 | `external_id` | `String` | `tiger_id` | 直接赋值 |
| 10 | `external_broker` | `String` | — | 固定 `"tiger"` |
| 11 | `sync_batch_id` | `Long` | — | 当前批次的 `broker_sync_batches.id` |
| 12 | `is_deleted` | `Boolean` | — | 固定 `false` |
| 13 | `strategy_id` | `Long` (nullable) | — | 固定 `null` |

### 5.3 需要逻辑判断的映射

| # | `trade_records` 字段 | 类型 | 转换规则 |
|---|---------------------|------|---------|
| 14 | `asset_type` | `AssetType` (enum) | `STK → STOCK`<br>`OPT + putCall=CALL → OPTION_CALL`<br>`OPT + putCall=PUT → OPTION_PUT` |
| 15 | `symbol` | `String` | **STK**：`symbol.trim()`（如 `AAPL`、`00700`、`600519`）<br>**OPT**：`{underlying}-{expiry}-{putCall[0]}{normalizedStrike}`（如 `AAPL-20260130-C265`） |
| 16 | `underlying_symbol` | `String` | STK：与 `symbol` 一致<br>OPT：直接取暂存的 `symbol` 字段（Tiger 的 OPT `symbol` 就是正股代码） |
| 17 | `trade_trigger` | `TradeTrigger` (enum) | 经 5.1 规则 5 过滤后，此处 `attrDesc` 必为空 → **固定 `MANUAL`**（Phase 3.x 扩展再覆盖期权事件） |
| 18 | `trigger_ref_type` | `TriggerRefType` (enum) | 固定 `NONE`（同上） |
| 19 | `trigger_ref_id` | `Long` | 固定 `0`（同上） |

> **注**：早期设计曾包含 `name` 字段映射（源自 `contract_name`），但 `TradeRecord.name` 列已在 V25 迁移脚本 `V25__drop_symbol_name_fields.sql` 中被移除，因此本期**不再映射** `contract_name` 到 `trade_records`，该字段仅保留在 `tiger_staged_orders` 中供审计/回溯。

### 5.4 期权 symbol 拼接细节

```java
// Tiger 的 putCall 取值为 "CALL" / "PUT"，系统格式要求单字符 C / P
String pc = "CALL".equalsIgnoreCase(putCall) ? "C" : "P";

// expiry 已是 yyyyMMdd 格式，无需转换
// strike 需去除尾部零
String normalizedStrike = new BigDecimal(strike).stripTrailingZeros().toPlainString();
// "265" → "265"、"265.00" → "265"、"17.50" → "17.5"

// 最终 symbol
String optionSymbol = symbol.trim() + "-" + expiry + "-" + pc + normalizedStrike;
// 例：AAPL-20260130-C265
```

### 5.5 不映射到 `trade_records` 的暂存字段

| `tiger_staged_orders` 字段 | 不映射原因 |
|--------------------------|-----------|
| `account` | 仅用于 Tiger 内部标识，系统通过 `broker_id` 表达 |
| `status_raw` | 已由前置过滤消化 |
| `order_time` | 仅供审计；成交日期以 `trade_time` 为准 |
| `quantity` / `quantity_scale` | 已在映射中消化为 `filled_quantity` 推导真实数量 |
| `realized_pnl` | 系统不记录券商返回的已实现盈亏，由仓位计算得出 |
| `exchange` / `market` | 仅供审计 |
| `contract_name` | `TradeRecord.name` 列已被 V25 移除，本期不再映射；仅保留在暂存表供审计 |
| `identifier` | 仅供审计（原设计用于 `name` 字段回落，现已废弃） |
| `multiplier` | 仅用于 `amount` 计算 |
| `strike` / `expiry` / `put_call` | 仅用于拼接 `symbol` 和判断 `asset_type` |
| `order_type` / `limit_price` | 订单类型对已成交订单无业务意义 |
| `attr_desc` | 本期未启用（参见 5.1 规则 5） |
| `gst` | 已在映射中合并到 `fee` |

---

## 六、多币种与多市场说明

### 6.1 币种映射

| Tiger 返回 | 系统 `Currency` 枚举 | 备注 |
|-----------|--------------------|------|
| `USD` | `USD` | 美股 |
| `HKD` | `HKD` | 港股 |
| `CNH` | `CNY` | A 股连通（离岸人民币 → 系统人民币，暂无差别） |
| 其他 | 抛异常 / `FAILED` | 记录 `Unsupported currency: {value}` |

### 6.2 市场特殊性

| 市场 | 符号特性 | 可能的额外问题 |
|------|---------|----------------|
| `US` | 字母代码（`AAPL`、`TSLA`） | 基本无特殊 |
| `HK` | 5 位数字补零（`00700`、`00005`） | Tiger 是否返回 5 位已补零需样本验证 |
| `CN` | 6 位数字（`600519`、`000001`） | — |

> 首次真实同步后需检查 HK 股代码是否需要补零，若不一致，在 `normalizeSymbol()` 内统一处理。

---

## 七、Flyway 迁移脚本

> 当前仓库最新脚本编号：`V25__drop_symbol_name_fields.sql`（见 `backend/src/main/resources/db/migration/`）

| 脚本 | 内容 | 状态 |
|------|------|------|
| `V26__create_tiger_staged_orders.sql` | 创建 `tiger_staged_orders` 表及索引、FK | 📋 待实现 |
| `V27__seed_tiger_broker_code.sql` | 幂等 UPDATE `brokers` 表设置 `broker_code='tiger'`（若已有记录） | 📋 待实现 |

> 注：若 `brokers` 表里 Tiger 记录已存在 `broker_code`，V27 采用 `UPDATE ... WHERE broker_code IS NULL OR broker_code <> 'tiger'` 的幂等语义，避免重复执行报错。

---

## 八、JPA Entity 与 Repository

| 类 | 说明 | 状态 |
|----|------|------|
| `TigerStagedOrder` (Entity) | 对应 `tiger_staged_orders` 表 | 📋 待实现 |
| `TigerStagedOrderRepository` | Spring Data JPA Repository | 📋 待实现 |

关键 Repository 方法（参考 `IbkrStagedOrderRepository`）：

- `Optional<TigerStagedOrder> findByTigerId(String tigerId)` — 幂等写入前查重
- `List<TigerStagedOrder> findByBatchIdAndStatus(Long batchId, String status)` — 批次 + 状态过滤
- `long countByBatchIdAndStatus(Long batchId, String status)` — 统计

---

## 九、开放问题与后续工作

| 代号 | 问题 | 处理策略 | 参考 |
|------|------|---------|------|
| T-1 | `attrDesc` 完整枚举值未知 | 本期所有 `attrDesc` 非空订单 → `FAILED` + 原值留痕，收集样本后再补充映射表 | [phase3-plan.md 阶段 6](./phase3-plan.md#阶段-6attrdesc-实证与期权事件映射补齐待真实数据到手后再做) |
| T-2 | 碎股不支持 | 本期 `FAILED`，待系统级 `quantity` 类型改造（独立立项） | [phase3-plan.md 已知限制](./phase3-plan.md#八已知限制清单) |
| T-3 | `WAR` / `IOPT` / `FUT` / `FUND` 不支持 | 本期 `FAILED`，个人账户罕见 | 同上 |
| T-4 | SDK 是否自动分页 | 实现前确认 `get_filled_orders` 是否需要 `pageToken` 循环 | [phase3-plan.md 阶段 5](./phase3-plan.md#阶段-5tigersyncadapter-流程改造两阶段接入) |
| T-5 | HK 股代码是否需补零 | 首次真实同步后验证 | 本文档 § 6.2 |
| T-6 | Tiger 期权行权 → 股票获得的记录结构 | 真实数据验证后决定是否需要 STK 侧 `trigger_ref_id` 回填 | 延后到 `attrDesc` 映射阶段 |
