# 券商同步 — 数据持久化设计文档

> **创建日期**：2026-04-13  
> **最后更新**：2026-04-18  
> **状态**：✅ 已实现（DB 迁移 V19-V24 + Entity + Repository + IbkrStagingService + IbkrImportService + IbkrSyncAdapter 全链路）  
> **关联**：[overall-design.md](../overall-design.md) | [flex-web-service.md](../brokers/ibkr/flex-web-service.md) | [broker-registration.md](./broker-registration.md)  
> **前置**：Phase 1（API 获取 + 日志输出）已完成

---

## 一、背景与目标

Phase 1 已跑通「券商 API → 解析 → 日志输出」的基本流程，数据正确性已通过日志核对验证。本文档设计 **Phase 2 的数据持久化方案**：将从券商拉取的交易数据安全地存入数据库。

**核心原则**：

1. **不污染正式数据** — 同步数据先写入暂存表（staged table），确认后再导入 `trade_records`
2. **按券商独立暂存** — 各券商 API 返回结构差异大，每个券商一张暂存表，字段 1:1 对应各自专属内存模型
3. **暂存表存解析后的结构化数据** — 不存原始 raw_data，XML 解析失败则整个批次失败
4. **暂存表字段使用 VARCHAR** — 保持数据无损，不做类型转换
5. **暂存粒度为 Order（订单级）** — `trade_records` 记录的是用户感知的"一次下单"，对应 IBKR 的 `<Order>` 节点，而非 `<TradeConfirm>` 执行明细

---

## 二、关键设计决策

| # | 决策项 | 结论 | 理由 |
|---|--------|------|------|
| 1 | 导入策略 | **两阶段：暂存 → 导入** | 防止数据污染，便于管理中间状态、人工审核、冲突处理 |
| 2 | 暂存表设计 | **按券商独立一张表** | 各券商返回字段差异大，通用表会导致大量无意义的 NULL 列或 JSONB 混杂 |
| 3 | 暂存表字段类型 | **统一 VARCHAR** | 保持与券商原始数据一致，不做类型转换，数据无损 |
| 4 | 暂存表数据内容 | **解析后的结构化数据** | 不存原始 raw_data；解析失败则整个批次标记为 FAILED |
| 5 | `trade_trigger` 是否新增 `BROKER_SYNC` | **否，不新增** | `trade_trigger` 描述的是"交易为什么发生"（手动下单 / 期权行权 / 市场事件），与"记录来源"无关。同步导入的记录根据交易实际业务含义设置 `trade_trigger`（大部分为 `MANUAL`，期权行权记录为 `OPTION`） |
| 6 | 如何区分手动录入 vs 同步导入 | **通过 `external_id` 判断** | `external_id IS NULL` → 手动录入；`external_id IS NOT NULL` → 券商同步导入 |
| 7 | 是否冗余 `external_broker` 字段 | **是** | 光有 `external_id` 无法知道去哪张暂存表关联；`external_broker` + `external_id` 构成有意义的复合标识，避免 JOIN `broker_sync_batches` 才能拿到券商来源 |
| 8 | IBKR 暂存粒度 | **Order（订单级），非 TradeConfirm（执行明细）** | 系统核心关注的是用户感知层面——"我下了一笔单买了 100 股 AAPL"，而不是底层的部分成交明细。Order 节点已包含聚合后的数量、加权均价、总佣金等完整信息，足以直接导入 `trade_records` |
| 9 | Order 中不持久化 `exchange` 和 `code` | **是** | 经 155 条 TradeConfirm / 150 条 Order 实际数据验证，`exchange` 和 `code` 在 Order 级别始终为空（仅 TradeConfirm 有值）。这两个字段属于执行明细信息，对用户感知层面的交易记录无意义，暂存表和 `trade_records` 均不包含 |

---

## 三、数据库变更概览

本方案涉及 **4 项数据库变更**：

| 变更 | 类型 | 说明 |
|------|------|------|
| `broker_sync_batches` | 新建表 | 通用同步批次元信息表 |
| `ibkr_staged_orders` | 新建表 | IBKR 核心暂存表（Order 粒度，1:1 对应 `IbkrOrderRecord`） |
| `ibkr_staged_trade_confirms` | 新建表 | IBKR 执行明细附表（TradeConfirm 粒度，用于审计/对账） |
| `trade_records` 扩展 | 新增字段 | 新增 `external_id`、`external_broker`、`sync_batch_id` 三个字段 |

---

## 四、表结构详细设计

### 4.1 `broker_sync_batches` — 通用同步批次表

记录每次同步操作的元信息，所有券商共用。

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | BIGSERIAL | PK | 主键 |
| `broker_code` | VARCHAR(50) | NOT NULL | 券商技术标识（如 `ibkr`、`tiger`），详见 [broker-registration.md](./broker-registration.md) |
| `sync_date_from` | DATE | NOT NULL | 同步数据的起始日期 |
| `sync_date_to` | DATE | NOT NULL | 同步数据的结束日期 |
| `total_count` | INTEGER | NOT NULL DEFAULT 0 | 同步记录总数 |
| `imported_count` | INTEGER | NOT NULL DEFAULT 0 | 已导入正式表的数量 |
| `skipped_count` | INTEGER | NOT NULL DEFAULT 0 | 跳过的数量（重复记录等） |
| `failed_count` | INTEGER | NOT NULL DEFAULT 0 | 失败的数量 |
| `status` | VARCHAR(32) | NOT NULL | 批次状态（见下方状态枚举） |
| `error_message` | TEXT | | 批次级错误信息 |
| `started_at` | TIMESTAMP | | 同步开始时间 |
| `completed_at` | TIMESTAMP | | 同步完成时间 |
| `created_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 创建时间 |
| `updated_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 更新时间 |

#### 批次状态枚举

| 状态 | 含义 |
|------|------|
| `PENDING` | 数据已写入暂存表，等待导入 |
| `IMPORTING` | 正在导入到 `trade_records` |
| `COMPLETED` | 导入完成 |
| `FAILED` | 同步或导入失败 |

#### 索引

| 索引 | 列 | 说明 |
|------|-----|------|
| `idx_sync_batches_broker_code` | `broker_code` | 按券商筛选批次 |
| `idx_sync_batches_status` | `status` | 按状态筛选批次 |

---

### 4.2 `ibkr_staged_orders` — IBKR 核心暂存表（Order 粒度）

字段 1:1 对应 `IbkrOrderRecord.java`，**不包含 `exchange` 和 `code`**（Order 级别始终为空，无持久化价值）。全部使用 VARCHAR 类型存储。另加暂存管理字段和审计字段。

> **设计依据**：`trade_records` 记录的是用户感知的"一次下单操作"，对应 IBKR 的 `<Order>` 节点。Order 节点已包含聚合后的成交数量、加权均价、总佣金等完整业务字段，足以直接映射到 `trade_records`。

#### 暂存管理字段

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | BIGSERIAL | PK | 主键 |
| `batch_id` | BIGINT | NOT NULL, FK→`broker_sync_batches.id` | 所属同步批次 |
| `status` | VARCHAR(32) | NOT NULL DEFAULT 'PENDING' | 记录状态（见下方状态枚举） |
| `imported_trade_id` | BIGINT | | 导入成功后关联的 `trade_records.id`，用于反向追溯 |
| `error_message` | TEXT | | 记录级错误信息（转换失败、冲突等的详细说明） |

#### IBKR Order 数据字段（28 个，全部 VARCHAR(255)）

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

> **不包含的字段**：`exchange`（Order 级别始终为空）、`code`（Order 级别始终为空）。这两个字段仅在 TradeConfirm 执行明细中有值，属于底层成交细节，不在用户感知层面的订单记录中体现。

#### 审计字段

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `created_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 创建时间 |
| `updated_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 更新时间 |

#### 暂存记录状态枚举

| 状态 | 含义 |
|------|------|
| `PENDING` | 待处理，等待导入到 `trade_records` |
| `IMPORTED` | 已成功导入到 `trade_records`，`imported_trade_id` 已填充 |
| `SKIPPED` | 跳过（重复记录，`trade_records` 中已存在对应的 `external_id`） |
| `CONFLICT` | 与已有记录存在冲突（字段不一致），需人工介入 |
| `FAILED` | 转换或导入失败（如字段映射异常），详见 `error_message` |

#### 状态流转

```
PENDING → IMPORTED    （成功导入到 trade_records）
PENDING → SKIPPED     （重复记录，已存在）
PENDING → CONFLICT    （与已有记录存在冲突）
PENDING → FAILED      （转换/导入失败）
```

#### 索引与约束

| 类型 | 列 | 说明 |
|------|-----|------|
| UNIQUE | `order_id` | IBKR orderID 唯一，防止同一笔订单重复写入暂存表 |
| INDEX | `batch_id` | 按批次查询暂存记录 |
| INDEX | `status` | 按状态筛选暂存记录 |
| FK | `batch_id` → `broker_sync_batches.id` | 外键关联批次 |

---

### 4.3 `ibkr_staged_trade_confirms` — IBKR 执行明细附表

> **定位**：这是一张**审计附表**，用于存储 TradeConfirm 粒度的执行明细。主要用途是审计、对账、以及未来需要精确到每笔成交时的数据源。**不参与 `trade_records` 的导入流程**。

字段 1:1 对应 `IbkrTradeConfirm.java` 的 37 个字段，全部使用 VARCHAR 类型存储。

#### 管理字段

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | BIGSERIAL | PK | 主键 |
| `batch_id` | BIGINT | NOT NULL, FK→`broker_sync_batches.id` | 所属同步批次 |

#### IBKR TradeConfirm 数据字段（37 个，全部 VARCHAR(255)）

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

#### 审计字段

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `created_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 创建时间 |
| `updated_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 更新时间 |

#### 索引与约束

| 类型 | 列 | 说明 |
|------|-----|------|
| UNIQUE | `trade_id` | IBKR tradeID 全局唯一 |
| INDEX | `batch_id` | 按批次查询 |
| INDEX | `order_id` | 按订单关联查询（关联 `ibkr_staged_orders`） |
| FK | `batch_id` → `broker_sync_batches.id` | 外键关联批次 |

---

### 4.4 `trade_records` 扩展字段

在现有 `trade_records` 表上新增 3 个字段，用于关联同步来源信息。

| 新增列名 | 类型 | 约束 | 说明 |
|---------|------|------|------|
| `external_id` | VARCHAR(100) | | 券商原始订单 ID（IBKR 的 `orderID`、Tiger 的 `orderId` 等），`NULL` 表示手动录入 |
| `external_broker` | VARCHAR(50) | | 来源券商标识（如 `ibkr`、`tiger`），与 `external_id` 配对使用，`NULL` 表示手动录入 |
| `sync_batch_id` | BIGINT | FK→`broker_sync_batches.id` | 关联同步批次，`NULL` 表示手动录入 |

> **注意**：IBKR 场景下 `external_id` 存储的是 `orderID`（Order 级别唯一标识），而非 `tradeID`（TradeConfirm 级别唯一标识），因为 `trade_records` 的粒度是 Order。

#### `trade_trigger` 不做改动

`trade_trigger` 描述的是"交易为什么发生"（`MANUAL` / `OPTION` / `MARKET_EVENT`），是交易本身的业务属性，与"记录来源"无关。

**同步导入时 `trade_trigger` 的设置规则**：

| 同步进来的交易类型 | `trade_trigger` | 说明 |
|------------------|----------------|------|
| 用户主动下单的股票/ETF/期权买卖 | `MANUAL` | 交易本身是用户手动下单的 |
| 期权到期/行权/被指派 | `OPTION` | 交易由期权事件触发 |
| 拆股/代码变更等市场事件 | `MARKET_EVENT` | 交易由市场事件触发 |

**区分数据来源的方式**：

```
external_id IS NULL     → 手动录入到平台的记录
external_id IS NOT NULL → 券商同步导入的记录
```

#### 索引与约束

| 类型 | 列 | 说明 |
|------|-----|------|
| UNIQUE (partial) | `(external_broker, external_id) WHERE external_id IS NOT NULL` | 防止同一券商的同一笔订单重复导入 |
| INDEX | `sync_batch_id` | 按批次查询导入的记录 |
| FK | `sync_batch_id` → `broker_sync_batches.id` | 外键关联批次 |

---

## 五、整体数据流

```
券商 API 响应 (JSON/XML)
    ↓ 反序列化 + 解析
券商专属内存模型 (如 IbkrOrderRecord)
    ↓ 写入暂存表
券商专属暂存表 (如 ibkr_staged_orders)          batch 状态: PENDING
    ↓ 字段映射 + 类型转换 + 去重校验
    ↓ 每条记录独立处理，状态更新为 IMPORTED / SKIPPED / CONFLICT / FAILED
trade_records（正式表）                          batch 状态: COMPLETED
    ↓
    设置 external_id = orderID, external_broker = 'ibkr', sync_batch_id
    设置 trade_trigger 为交易实际业务含义（MANUAL / OPTION / MARKET_EVENT）
```

---

## 六、ER 关系图

```
broker_sync_batches (1) ──── (N) ibkr_staged_orders         ← 核心：导入 trade_records 的数据源
        │                          │
        │                          │ order_id 关联（可选）
        │                          │
        │               (1) ──── (N) ibkr_staged_trade_confirms  ← 可选：执行明细附表
        │
        │ (1)
        │
        └──── (N) trade_records (通过 sync_batch_id 关联)

ibkr_staged_orders.imported_trade_id ──── trade_records.id (可选反向关联)

trade_records.(external_broker + external_id) ←→ ibkr_staged_orders.order_id
```

---

## 七、Order vs TradeConfirm 字段对比与设计依据

基于实际 XML 数据（150 条 Order / 155 条 TradeConfirm）的逐字段验证结果：

### 7.1 Order 级别字段完整性

Order 节点已包含导入 `trade_records` 所需的**全部关键业务字段**：

| 字段类别 | 状态 | 说明 |
|---------|------|------|
| 日期字段（tradeDate, settleDate, dateTime） | ✅ 始终有值 | 实测 Order 级别从不出现 MULTI，MULTI 仅出现在 SymbolSummary 层 |
| 数量与价格（quantity, price） | ✅ 始终有值 | Order 已包含聚合后的总数量和加权均价 |
| 金额字段（amount, proceeds, netCash） | ✅ 始终有值 | 聚合值 |
| 佣金（commission, commissionCurrency, tradeCharge） | ✅ 始终有值 | 聚合后的总佣金 |
| 合约信息（symbol, conid, assetCategory 等） | ✅ 始终有值 | 与 TradeConfirm 一致 |
| 期权字段（strike, expiry, putCall） | ✅ 期权有值 | 与 TradeConfirm 一致 |
| 订单信息（orderID, buySell） | ✅ 始终有值 | — |
| orderTime, orderType | ⚠️ BookTrade 为空 | 正常行为：期权到期/行权等无下单动作 |
| isAPIOrder | ⚠️ 始终为空 | Order 级别不返回此值，非关键字段 |

### 7.2 不纳入暂存表的字段

| 字段 | Order 级别 | TradeConfirm 级别 | 不纳入的原因 |
|------|-----------|------------------|------------|
| `exchange` | 始终为空 | 有值（DARK, DRCTEDGE, MEMX 等） | 属于执行细节，对用户感知层面无意义 |
| `code` | 始终为空 | 有值（O, C, C;P, O;P, A;C 等） | 属于执行细节，`trade_trigger` 的判定可通过 Order 的 `transactionType` + 上下文推断 |

### 7.3 TradeConfirm 独有字段（仅在附表中存在）

| 字段 | 说明 |
|------|------|
| `tradeID` | 成交确认 ID（全局唯一） |
| `execID` | 执行 ID（交易所分配） |
| `brokerageOrderID` | 券商内部订单 ID |
| `orderReference` | 订单引用 |
| `transactionType` | ExchTrade / BookTrade |

---

## 八、后续扩展

### 8.1 新增券商暂存表

当接入新券商（如 Tiger、Schwab）时，只需新建对应的暂存表：

```
tiger_staged_orders             → 字段 1:1 对应 TigerOrderRecord
schwab_staged_orders            → 字段 1:1 对应 SchwabTradeRecord
```

`broker_sync_batches` 表通用，无需修改。`trade_records` 扩展字段也通用（`external_broker` 区分来源，`external_id` 存各券商的订单 ID）。

### 8.2 Flyway 迁移脚本

实现时需要编写以下 Flyway 迁移脚本（按当前版本号顺序）：

| 脚本 | 内容 | 状态 |
|------|------|------|
| `V19__create_broker_sync_batches.sql` | 创建 `broker_sync_batches` 表 | ✅ 已完成 |
| `V20__create_ibkr_staged_orders.sql` | 创建 `ibkr_staged_orders` 表 | ✅ 已完成 |
| `V21__create_ibkr_staged_trade_confirms.sql` | 创建 `ibkr_staged_trade_confirms` 表 | ✅ 已完成 |
| `V22__add_external_fields_to_trade_records.sql` | 为 `trade_records` 新增 `external_id`、`external_broker`、`sync_batch_id` 字段 | ✅ 已完成 |
| `V23__add_broker_code_and_rename_batch_broker_name.sql` | `brokers` 新增 `broker_code` 列（UNIQUE 部分索引）+ `broker_sync_batches.broker_name` → `broker_code` 改名 | ✅ 已完成，详见 [broker-registration.md](./broker-registration.md) |
| `V24__add_phase_and_expand_batch_status.sql` | `broker_sync_batches` 新增 `phase` 列 + `status` 枚举扩展（PROCESSING/PARTIAL/INTERRUPTED） | ✅ 已完成，详见 [import-consistency.md](./import-consistency.md) |

### 8.3 JPA Entity 与 Repository

实现时需新建：

| 类 | 说明 | 状态 |
|----|------|------|
| `BrokerSyncBatch` (Entity) | 对应 `broker_sync_batches` 表 | ✅ 已完成 |
| `BrokerSyncBatchRepository` | 批次表的 Repository | ✅ 已完成 |
| `IbkrStagedOrder` (Entity) | 对应 `ibkr_staged_orders` 表 | ✅ 已完成 |
| `IbkrStagedOrderRepository` | IBKR 核心暂存表的 Repository | ✅ 已完成 |
| `IbkrStagedTradeConfirm` (Entity) | 对应 `ibkr_staged_trade_confirms` 表 | ✅ 已完成 |
| `IbkrStagedTradeConfirmRepository` | IBKR 明细附表的 Repository | ✅ 已完成 |
| `TradeRecord` (Entity 扩展) | 新增 `externalId`、`externalBroker`、`syncBatchId` 字段 | ✅ 已完成 |

---

## 九、开放问题与待后续讨论

> **最后整理**：2026-04-18

以下事项按优先级分为三级：**🔥 编码前必须解决**（不解决会卡住实现）、**📦 可后续再说**（不阻塞当前编码）、**✅ 已解决**（归档留痕）。

### 🔥 编码前必须解决（直接影响导入逻辑实现）

_（当前无待解决项，所有阻塞性问题已解决）_

### 📦 可后续再说（不阻塞当前编码）

| # | 问题 | 说明 | 备注 |
|---|------|------|------|
| D-1 | 暂存表数据的清理策略 | 保留多久、是否归档、自动清理还是手动 | 系统跑起来之后根据实际数据量再定 |
| D-2 | 是否需要"同步预览"功能 | 在正式导入前展示待导入数据供用户确认 | Phase 3 规划项 |
| D-3 | XML 解析方案选型 | DOM 解析 vs SAX 解析 vs JAXB | Phase 1 已跑通（当前 DOM），性能优化后续考虑，详见 [flex-web-service.md](../brokers/ibkr/flex-web-service.md) |
| D-4 | Token 存储方式 | 当前 properties 文件，后续是否迁移到数据库 | 详见 [flex-web-service.md](../brokers/ibkr/flex-web-service.md) |
| D-5 | Activity Flex Query 支持 | 除 Trade Confirmation 外的其他 IBKR 报告类型 | 详见 [flex-web-service.md](../brokers/ibkr/flex-web-service.md) |
| D-6 | Tiger 同步后续 | 入库、去重、单元测试等 | 当前优先级低，详见 [open-api.md](../brokers/tiger/open-api.md) |
| D-7 | `overall-design.md` 大量开放问题 | 早期头脑风暴产物，40+ 个未关闭 `- [ ]` 项。部分已被后续设计文档覆盖或回答，但状态仍是"方案讨论中" | 等核心功能稳定后批量清理 |

### ✅ 已解决（归档）

| # | 问题 | 解决方式 |
|---|------|---------|
| R-1 | 批次导入事务策略 | **逐条独立事务 + 幂等**，详见 [import-consistency.md](./import-consistency.md) |
| R-2 | `brokerId` 查找策略 | `brokers` 表新增 `broker_code` 列，通过 `findByBrokerCode()` 直接查找，详见 [broker-registration.md](./broker-registration.md) |
| R-3 | ETF 识别 | `assetCategory = STK` 时统一默认为 `STOCK`，历史 ETF 数据已由用户手动修正为 STOCK（ETF 枚举值保留） |
| R-4 | `trade_trigger` 是否新增 `BROKER_SYNC` | **否**，`trade_trigger` 描述"交易为什么发生"，不描述"记录来源"。通过 `external_id IS NULL` 区分手动/同步 |
| R-5 | BookTrade 的 `tradeTrigger` 判定（原 O-2） | 通过 `orderTime` + `orderType` 为空识别 BookTrade，再查关联 TradeConfirm 的 `code` 字段判定具体期权事件类型。详见 [booktrade-mapping.md](../brokers/ibkr/booktrade-mapping.md) |
| R-6 | 期权 symbol 格式转换（原 O-3） | **无需额外设计**。IBKR 暂存表已存 `strike`、`expiry`、`putCall` 为独立字段，导入时直接拼接为系统格式 `{underlying}-{expiry}-{putCall}{strike}`（如 `AAPL-20260130-C265`），无需解析 OCC 填充格式 |
| R-7 | 暂存表 → `trade_records` 字段映射（原 O-1） | **已完成完整映射规范**，详见下方「附录 A：O-1 字段映射规范」 |
| R-8 | 导入时的冲突处理策略（原 O-4） | **不做手动记录冲突匹配**。系统假设不存在手动录入的历史记录，去重仅依赖 `(external_broker, external_id)` 唯一索引。已有相同 `external_id` → SKIPPED。`CONFLICT` 状态仅保留为理论预留，当前不会触发 |
| R-9 | 前端状态交互设计（原 O-5） | **已在 [import-consistency.md](./import-consistency.md) 完整定义**。`INTERRUPTED` → Resume 按钮（复用原 batch 完整重跑）；`FAILED` → 重新同步按钮（创建新 batch）；`PARTIAL` → 仅展示失败详情，不提供重试（数据问题重试无意义） |

---

## 附录 A：O-1 字段映射规范

> **确认日期**：2026-04-18  
> **适用范围**：`ibkr_staged_orders` → `trade_records` 的字段映射，即 `IbkrImportService` 的核心转换逻辑

### A.1 直接映射（简单转换）

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

### A.2 需要逻辑判断的映射

| # | `trade_records` 字段 | 类型 | 转换规则 |
|---|---------------------|------|---------|
| 13 | `asset_type` | `AssetType` (enum) | `"STK"` → `STOCK`；`"OPT"` + `putCall="C"` → `OPTION_CALL`；`"OPT"` + `putCall="P"` → `OPTION_PUT` |
| 14 | `symbol` | `String` | **STK**：`symbol.trim()`（如 `AAPL`）<br>**OPT**：`{underlying}-{expiry}-{putCall}{normalizedStrike}`<br>示例：`AAPL-20260130-C265`，`TSM-20260320-P320` |
| 15 | `underlying_symbol` | `String` | **STK**：与 `symbol` 相同（`symbol.trim()`）<br>**OPT**：从 `description` 字段提取第一个空格前的 token（如 `"AAPL 30JAN26 265 C"` → `"AAPL"`） |
| 16 | `name` | `String` | 直接取 `description` 完整值（如 `"APPLE INC"`、`"TSM 20MAR26 320 P"`） |
| 17 | `trade_trigger` | `TradeTrigger` (enum) | 按 [booktrade-mapping.md](../brokers/ibkr/booktrade-mapping.md) 判定：<br>`orderTime` 非空 → `MANUAL`<br>`orderTime` 为空（BookTrade）→ 查关联 TradeConfirm `code` → `OPTION` |
| 18 | `trigger_ref_type` | `TriggerRefType` (enum) | 随 `trade_trigger` 一起判定（MANUAL → `NONE`，BookTrade 按 code 映射到具体类型） |
| 19 | `trigger_ref_id` | `Long` | 初始为 `0`；STK 侧 BookTrade 在批次导入完成后回填对应期权 trade_records.id |
| 20 | `strategy_id` | `Long` (nullable) | 固定为 `null`（同步导入无法自动关联策略） |

### A.3 期权 symbol 拼接细节

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

### A.4 不映射到 `trade_records` 的暂存字段

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
