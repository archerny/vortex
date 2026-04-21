# IBKR 暂存表结构与字段映射

> **创建日期**：2026-04-21（从 `framework/data-persistence.md` 拆分）
> **最后更新**：2026-04-21
> **状态**：✅ 已实现（DB 迁移 V20-V22 + Entity + Repository + IbkrStagingService + IbkrImportService）
> **关联**：[framework/data-persistence.md](../../framework/data-persistence.md) | [flex-web-service.md](./flex-web-service.md) | [booktrade-mapping.md](./booktrade-mapping.md)

本文档定义 IBKR 专属的暂存表结构（`ibkr_staged_orders` + `ibkr_staged_trade_confirms`），以及从暂存表映射到 `trade_records` 的字段规范。框架层通用内容（`broker_sync_batches`、`trade_records` 扩展字段、两阶段导入原则）参见 [framework/data-persistence.md](../../framework/data-persistence.md)。

---

## 一、暂存粒度决策

**暂存粒度为 Order（订单级），非 TradeConfirm（执行明细）。**

`trade_records` 记录的是用户感知的"一次下单操作"，对应 IBKR 的 `<Order>` 节点。Order 节点已包含聚合后的成交数量、加权均价、总佣金等完整业务字段，足以直接映射到 `trade_records`。TradeConfirm（执行明细）作为附表留存，仅用于审计/对账。

### Order vs TradeConfirm 字段对比

基于实际 XML 数据（150 条 Order / 155 条 TradeConfirm）的逐字段验证：

| 字段类别 | Order 级别 | 说明 |
|---------|-----------|------|
| 日期字段（tradeDate, settleDate, dateTime） | ✅ 始终有值 | Order 级别从不出现 MULTI，MULTI 仅出现在 SymbolSummary 层 |
| 数量与价格（quantity, price） | ✅ 始终有值 | Order 已包含聚合后的总数量和加权均价 |
| 金额字段（amount, proceeds, netCash） | ✅ 始终有值 | 聚合值 |
| 佣金（commission, commissionCurrency, tradeCharge） | ✅ 始终有值 | 聚合后的总佣金 |
| 合约信息（symbol, conid, assetCategory 等） | ✅ 始终有值 | 与 TradeConfirm 一致 |
| 期权字段（strike, expiry, putCall） | ✅ 期权有值 | 与 TradeConfirm 一致 |
| 订单信息（orderID, buySell） | ✅ 始终有值 | — |
| orderTime, orderType | ⚠️ BookTrade 为空 | 正常行为：期权到期/行权等无下单动作 |
| isAPIOrder | ⚠️ 始终为空 | Order 级别不返回此值，非关键字段 |
| **exchange** | ❌ 始终为空 | 仅 TradeConfirm 有值（DARK, DRCTEDGE, MEMX 等），属于执行细节 |
| **code** | ❌ 始终为空 | 仅 TradeConfirm 有值（O, C, C;P, O;P, A;C 等），用于 BookTrade 判定 |

**结论**：`ibkr_staged_orders` 不包含 `exchange` 和 `code` 字段。

### TradeConfirm 独有字段（仅在附表中存在）

| 字段 | 说明 |
|------|------|
| `tradeID` | 成交确认 ID（全局唯一） |
| `execID` | 执行 ID（交易所分配） |
| `brokerageOrderID` | 券商内部订单 ID |
| `orderReference` | 订单引用 |
| `transactionType` | ExchTrade / BookTrade |
| `exchange` | 成交交易所 |
| `code` | 交易代码标记 |

---

## 二、`ibkr_staged_orders` — IBKR 核心暂存表（Order 粒度）

字段 1:1 对应 `IbkrOrderRecord.java`。全部使用 VARCHAR 存储（数据无损原则）。另加暂存管理字段和审计字段。

### 2.1 暂存管理字段

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | BIGSERIAL | PK | 主键 |
| `batch_id` | BIGINT | NOT NULL, FK→`broker_sync_batches.id` | 所属同步批次 |
| `status` | VARCHAR(32) | NOT NULL DEFAULT 'PENDING' | 记录状态（见下方状态枚举） |
| `imported_trade_id` | BIGINT | | 导入成功后关联的 `trade_records.id`，用于反向追溯 |
| `error_message` | TEXT | | 记录级错误信息（转换失败、冲突等的详细说明） |

### 2.2 IBKR Order 数据字段（28 个业务字段，全部 VARCHAR(255)）

| # | 列名 | 对应 `IbkrOrderRecord` 字段 | 说明 |
|---|------|------------------------------|------|
| 1 | `account_id` | `accountId` | IBKR 账户 ID |
| 2 | `acct_alias` | `acctAlias` | 账户别名 |
| 3 | `currency` | `currency` | 币种 |
| 4 | `asset_category` | `assetCategory` | 资产类别：STK / OPT / FUT / CASH / FUND |
| 5 | `symbol` | `symbol` | 证券代码 |
| 6 | `description` | `description` | 证券描述 |
| 7 | `conid` | `conid` | IBKR 合约 ID |
| 8 | `security_id` | `securityID` | 证券 ID（ISIN） |
| 9 | `security_id_type` | `securityIDType` | 证券 ID 类型 |
| 10 | `multiplier` | `multiplier` | 合约乘数 |
| 11 | `strike` | `strike` | 行权价（仅期权） |
| 12 | `expiry` | `expiry` | 到期日（仅期权） |
| 13 | `put_call` | `putCall` | 期权类型：C / P |
| 14 | `order_id` | `orderID` | 订单 ID（**IBKR 订单唯一标识，用作去重键**） |
| 15 | `order_time` | `orderTime` | 下单时间（BookTrade 为空） |
| 16 | `date_time` | `dateTime` | 成交时间（订单级汇总） |
| 17 | `settle_date` | `settleDate` | 交割日期 |
| 18 | `trade_date` | `tradeDate` | 交易日期 |
| 19 | `buy_sell` | `buySell` | 买卖方向：BUY / SELL |
| 20 | `order_type` | `orderType` | 订单类型（BookTrade 为空） |
| 21 | `is_api_order` | `isAPIOrder` | 是否通过 API 下单：Y / N |
| 22 | `quantity` | `quantity` | 成交数量（聚合值，卖出为负数） |
| 23 | `price` | `price` | 成交均价（加权平均） |
| 24 | `amount` | `amount` | 成交金额（聚合值） |
| 25 | `proceeds` | `proceeds` | 收入/支出（聚合值） |
| 26 | `net_cash` | `netCash` | 净现金流（聚合值） |
| 27 | `commission` | `commission` | 佣金（聚合值，通常为负数） |
| 28 | `commission_currency` | `commissionCurrency` | 佣金币种 |
| 29 | `trade_charge` | `tradeCharge` | 交易附加费（聚合值） |
| 30 | `trader_id` | `traderID` | 交易员 ID |

> **不包含的字段**：`exchange`、`code`（详见第一节的设计依据）。

### 2.3 审计字段

| 列名 | 类型 | 约束 |
|------|------|------|
| `created_at` | TIMESTAMP | NOT NULL DEFAULT NOW() |
| `updated_at` | TIMESTAMP | NOT NULL DEFAULT NOW() |

### 2.4 暂存记录状态枚举

| 状态 | 含义 |
|------|------|
| `PENDING` | 待处理，等待导入到 `trade_records` |
| `IMPORTED` | 已成功导入到 `trade_records`，`imported_trade_id` 已填充 |
| `SKIPPED` | 跳过（重复记录，`trade_records` 中已存在对应的 `external_id`） |
| `CONFLICT` | 与已有记录存在冲突（字段不一致），需人工介入（当前不会触发，预留） |
| `FAILED` | 转换或导入失败（如字段映射异常），详见 `error_message` |

### 2.5 状态流转

```
PENDING → IMPORTED    （成功导入到 trade_records）
PENDING → SKIPPED     （重复记录，已存在）
PENDING → CONFLICT    （与已有记录存在冲突，当前不触发）
PENDING → FAILED      （转换/导入失败）
```

### 2.6 索引与约束

| 类型 | 列 | 说明 |
|------|-----|------|
| UNIQUE | `order_id` | IBKR orderID 唯一，防止同一笔订单重复写入暂存表 |
| INDEX | `batch_id` | 按批次查询暂存记录 |
| INDEX | `status` | 按状态筛选暂存记录 |
| FK | `batch_id` → `broker_sync_batches.id` | 外键关联批次 |

---

## 三、`ibkr_staged_trade_confirms` — IBKR 执行明细附表

> **定位**：这是一张**审计附表**，用于存储 TradeConfirm 粒度的执行明细。主要用途是审计、对账、以及未来需要精确到每笔成交时的数据源。**不参与 `trade_records` 的导入流程**。

字段 1:1 对应 `IbkrTradeConfirm.java` 的 37 个字段，全部使用 VARCHAR 存储。

### 3.1 管理字段

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | BIGSERIAL | PK | 主键 |
| `batch_id` | BIGINT | NOT NULL, FK→`broker_sync_batches.id` | 所属同步批次 |

### 3.2 IBKR TradeConfirm 数据字段（37 个，全部 VARCHAR(255)）

| # | 列名 | 对应 `IbkrTradeConfirm` 字段 | 说明 |
|---|------|------------------------------|------|
| 1 | `account_id` | `accountId` | IBKR 账户 ID |
| 2 | `acct_alias` | `acctAlias` | 账户别名 |
| 3 | `currency` | `currency` | 币种 |
| 4 | `asset_category` | `assetCategory` | 资产类别 |
| 5 | `symbol` | `symbol` | 证券代码 |
| 6 | `description` | `description` | 证券描述 |
| 7 | `conid` | `conid` | IBKR 合约 ID |
| 8 | `security_id` | `securityID` | 证券 ID（ISIN） |
| 9 | `security_id_type` | `securityIDType` | 证券 ID 类型 |
| 10 | `multiplier` | `multiplier` | 合约乘数 |
| 11 | `strike` | `strike` | 行权价（仅期权） |
| 12 | `expiry` | `expiry` | 到期日（仅期权） |
| 13 | `put_call` | `putCall` | 期权类型：C / P |
| 14 | `transaction_type` | `transactionType` | 交易类型：ExchTrade / BookTrade |
| 15 | `trade_id` | `tradeID` | 成交确认 ID（**IBKR 全局唯一**） |
| 16 | `order_id` | `orderID` | 所属订单 ID（关联 `ibkr_staged_orders.order_id`） |
| 17 | `exec_id` | `execID` | 执行 ID（交易所分配） |
| 18 | `brokerage_order_id` | `brokerageOrderID` | 券商内部订单 ID |
| 19 | `order_reference` | `orderReference` | 订单引用 |
| 20 | `order_time` | `orderTime` | 下单时间 |
| 21 | `date_time` | `dateTime` | 成交时间 |
| 22 | `settle_date` | `settleDate` | 交割日期 |
| 23 | `trade_date` | `tradeDate` | 交易日期 |
| 24 | `exchange` | `exchange` | 成交交易所（如 DARK、DRCTEDGE、MEMX 等） |
| 25 | `buy_sell` | `buySell` | 买卖方向 |
| 26 | `quantity` | `quantity` | 成交数量（单笔执行） |
| 27 | `price` | `price` | 成交价格（单笔执行） |
| 28 | `amount` | `amount` | 成交金额（单笔执行） |
| 29 | `proceeds` | `proceeds` | 收入/支出 |
| 30 | `net_cash` | `netCash` | 净现金流 |
| 31 | `commission` | `commission` | 佣金（单笔执行） |
| 32 | `commission_currency` | `commissionCurrency` | 佣金币种 |
| 33 | `trade_charge` | `tradeCharge` | 交易附加费 |
| 34 | `code` | `code` | 交易代码标记（O/C/P/A/Ep，多值分号分隔） |
| 35 | `order_type` | `orderType` | 订单类型 |
| 36 | `trader_id` | `traderID` | 交易员 ID |
| 37 | `is_api_order` | `isAPIOrder` | 是否通过 API 下单 |

### 3.3 审计字段

| 列名 | 类型 | 约束 |
|------|------|------|
| `created_at` | TIMESTAMP | NOT NULL DEFAULT NOW() |
| `updated_at` | TIMESTAMP | NOT NULL DEFAULT NOW() |

### 3.4 索引与约束

| 类型 | 列 | 说明 |
|------|-----|------|
| UNIQUE | `trade_id` | IBKR tradeID 全局唯一 |
| INDEX | `batch_id` | 按批次查询 |
| INDEX | `order_id` | 按订单关联查询（关联 `ibkr_staged_orders`） |
| FK | `batch_id` → `broker_sync_batches.id` | 外键关联批次 |

---

## 四、ER 关系图

```
broker_sync_batches (1) ──── (N) ibkr_staged_orders         ← 核心：导入 trade_records 的数据源
        │                          │
        │                          │ order_id 关联（可选）
        │                          │
        │               (1) ──── (N) ibkr_staged_trade_confirms  ← 审计附表
        │
        │ (1)
        │
        └──── (N) trade_records (通过 sync_batch_id 关联)

ibkr_staged_orders.imported_trade_id ──── trade_records.id (可选反向关联)

trade_records.(external_broker + external_id) ←→ ibkr_staged_orders.order_id
```

---

## 五、字段映射规范（`ibkr_staged_orders` → `trade_records`）

> **确认日期**：2026-04-18
> **适用范围**：`IbkrImportService` 的核心转换逻辑

### 5.1 直接映射（简单转换）

| # | `trade_records` 字段 | 类型 | ← 源 | 转换规则 |
|---|---------------------|------|-------|---------|
| 1 | `trade_date` | `LocalDate` | `trade_date` | `LocalDate.parse(value, "yyyyMMdd")` |
| 2 | `broker_id` | `Long` | — | 通过 `brokers.broker_code = 'ibkr'` 查找，导入批次级别缓存一次 |
| 3 | `currency` | `Currency` (enum) | `currency` | `"USD"` → `USD`，`"HKD"` → `HKD` |
| 4 | `trade_type` | `TradeType` (enum) | `buy_sell` | `"BUY"` → `BUY`，`"SELL"` → `SELL` |
| 5 | `quantity` | `Integer` | `quantity` | `abs(Integer.parseInt(value))`（IBKR 卖出为负数，系统始终存正数） |
| 6 | `price` | `BigDecimal` | `price` | `new BigDecimal(value)` |
| 7 | `amount` | `BigDecimal` | `amount` | `new BigDecimal(value).abs()`（IBKR 带符号，系统存正数） |
| 8 | `fee` | `BigDecimal` | `commission` + `trade_charge` | `abs(commission) + abs(tradeCharge)`（两个都是负数或零，取绝对值后相加。经验证 IBKR 仅有此两项费用字段，无遗漏） |
| 9 | `external_id` | `String` | `order_id` | 直接赋值 |
| 10 | `external_broker` | `String` | — | 固定值 `"ibkr"` |
| 11 | `sync_batch_id` | `Long` | — | 当前批次的 `broker_sync_batches.id` |
| 12 | `is_deleted` | `Boolean` | — | 固定值 `false` |

### 5.2 需要逻辑判断的映射

| # | `trade_records` 字段 | 类型 | 转换规则 |
|---|---------------------|------|---------|
| 13 | `asset_type` | `AssetType` (enum) | `"STK"` → `STOCK`；`"OPT"` + `putCall="C"` → `OPTION_CALL`；`"OPT"` + `putCall="P"` → `OPTION_PUT` |
| 14 | `symbol` | `String` | **STK**：`symbol.trim()`（如 `AAPL`）<br>**OPT**：`{underlying}-{expiry}-{putCall}{normalizedStrike}`<br>示例：`AAPL-20260130-C265`，`TSM-20260320-P320` |
| 15 | `underlying_symbol` | `String` | **STK**：与 `symbol` 相同（`symbol.trim()`）<br>**OPT**：从 `description` 字段提取第一个空格前的 token（如 `"AAPL 30JAN26 265 C"` → `"AAPL"`） |
| 16 | `name` | `String` | 直接取 `description` 完整值（如 `"APPLE INC"`、`"TSM 20MAR26 320 P"`） |
| 17 | `trade_trigger` | `TradeTrigger` (enum) | 按 [booktrade-mapping.md](./booktrade-mapping.md) 判定：<br>`orderTime` 非空 → `MANUAL`<br>`orderTime` 为空（BookTrade）→ 查关联 TradeConfirm `code` → `OPTION` |
| 18 | `trigger_ref_type` | `TriggerRefType` (enum) | 随 `trade_trigger` 一起判定（MANUAL → `NONE`，BookTrade 按 code 映射到具体类型） |
| 19 | `trigger_ref_id` | `Long` | 初始为 `0`；STK 侧 BookTrade 在批次导入完成后回填对应期权 trade_records.id |
| 20 | `strategy_id` | `Long` (nullable) | 固定为 `null`（同步导入无法自动关联策略） |

### 5.3 期权 symbol 拼接细节

```java
// 1. 提取 underlying：从 description 的第一个空格前取得
String underlying = description.split("\\s+")[0]; // "AAPL 30JAN26 265 C" → "AAPL"

// 2. 标准化 strike：去除尾部零
String normalizedStrike = new BigDecimal(strike).stripTrailingZeros().toPlainString();
// "265" → "265", "265.00" → "265", "17.50" → "17.5"

// 3. 拼接系统格式 symbol
String optionSymbol = underlying + "-" + expiry + "-" + putCall + normalizedStrike;
// "AAPL" + "-" + "20260130" + "-" + "C" + "265" = "AAPL-20260130-C265"
```

### 5.4 不映射到 `trade_records` 的暂存字段

| `ibkr_staged_orders` 字段 | 不映射原因 |
|--------------------------|-----------|
| `account_id` | 仅用于 IBKR 内部标识，系统通过 `broker_id` 表达 |
| `acct_alias` | 仅用于 IBKR 内部标识 |
| `conid` | IBKR 合约唯一 ID，系统不需要 |
| `security_id` | ISIN 号，系统不需要 |
| `security_id_type` | 与 security_id 配对，系统不需要 |
| `multiplier` | 期权乘数，用于 BookTrade 回填阶段的数量比例匹配（不存入 trade_records） |
| `strike` | 仅用于拼接 `symbol`，不单独存储 |
| `expiry` | 仅用于拼接 `symbol`，不单独存储 |
| `put_call` | 仅用于判断 `asset_type` 和拼接 `symbol` |
| `order_time` | 仅用于 BookTrade 识别（不存入 trade_records） |
| `date_time` | 订单级汇总成交时间，系统以 `trade_date` 为准 |
| `settle_date` | 交割日期，系统当前不关注 |
| `order_type` | 仅用于 BookTrade 识别 |
| `is_api_order` | 下单渠道信息，对交易记录无意义 |
| `proceeds` | 与 amount 冗余（反向符号），不需要 |
| `net_cash` | = proceeds - commission，不需要 |
| `commission_currency` | 当前系统不支持多币种佣金，且 IBKR 佣金币种通常与交易币种一致 |
| `trader_id` | 交易员 ID，个人用户场景无意义 |

---

## 六、Flyway 迁移脚本

| 脚本 | 内容 | 状态 |
|------|------|------|
| `V20__create_ibkr_staged_orders.sql` | 创建 `ibkr_staged_orders` 表 | ✅ 已完成 |
| `V21__create_ibkr_staged_trade_confirms.sql` | 创建 `ibkr_staged_trade_confirms` 表 | ✅ 已完成 |

（`V19`、`V22`、`V23`、`V24` 属于框架层通用变更，见 [framework/data-persistence.md](../../framework/data-persistence.md#flyway-迁移脚本)）

## 七、JPA Entity 与 Repository

| 类 | 说明 | 状态 |
|----|------|------|
| `IbkrStagedOrder` (Entity) | 对应 `ibkr_staged_orders` 表 | ✅ 已完成 |
| `IbkrStagedOrderRepository` | IBKR 核心暂存表的 Repository | ✅ 已完成 |
| `IbkrStagedTradeConfirm` (Entity) | 对应 `ibkr_staged_trade_confirms` 表 | ✅ 已完成 |
| `IbkrStagedTradeConfirmRepository` | IBKR 明细附表的 Repository | ✅ 已完成 |
