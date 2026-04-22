# 券商同步 — 数据持久化设计（框架层）

> **创建日期**：2026-04-13
> **最后更新**：2026-04-23（随 import-consistency.md v2.4 更新：Phase 4 前端完成 — 恢复按钮与 `resumeSync` 客户端已删除，状态过滤器精简为 v2 状态集，`CLEANUP_FAILED` 展示与 409 Modal 已上线；v2 状态模型端到端全部落地）
> **状态**：✅ 表结构与 Entity 已实现（DB 迁移 V19 + V22-V24 + V28）；✅ 应用层与前端行为已完整切换到 v2（fail-fast cleanup + 409 conflict + 无 resume）
> **关联**：[architecture.md](../architecture.md) | [import-consistency.md](./import-consistency.md) | [broker-registration.md](./broker-registration.md) | [brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md)

本文档定义**框架层通用**的数据持久化方案：`broker_sync_batches`（通用批次表）、`trade_records` 扩展字段、以及所有券商共同遵守的「暂存 → 导入」两阶段原则。各券商专属的暂存表结构与字段映射在各自的 `brokers/<code>/staging-schema.md` 中定义（如 [brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md)）。

---

## 一、背景与目标

Phase 1 已跑通「券商 API → 解析 → 日志输出」的基本流程，数据正确性已通过日志核对验证。本文档设计 **Phase 2 的数据持久化方案**：将从券商拉取的交易数据安全地存入数据库。

**核心原则**：

1. **不污染正式数据** — 同步数据先写入暂存表（staged table），确认后再导入 `trade_records`
2. **按券商独立暂存** — 各券商 API 返回结构差异大，每个券商一张暂存表，字段 1:1 对应各自专属内存模型
3. **暂存表存解析后的结构化数据** — 不存原始 raw_data，解析失败则整个批次失败
4. **暂存表字段使用 VARCHAR** — 保持数据无损，不做类型转换
5. **暂存粒度为用户感知的"一次交易"** — `trade_records` 记录的是用户感知的"一次下单"，对应 IBKR 的 `<Order>`、Tiger 的订单等，而非底层成交明细

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

---

## 三、数据库变更概览

| 变更 | 类型 | 说明 |
|------|------|------|
| `broker_sync_batches` | 新建表 | 通用同步批次元信息表（本文档定义） |
| `trade_records` 扩展 | 新增字段 | 新增 `external_id`、`external_broker`、`sync_batch_id` 三个字段（本文档定义） |
| `ibkr_staged_orders` | 新建表 | IBKR 核心暂存表 —— 详见 [brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md) |
| `ibkr_staged_trade_confirms` | 新建表 | IBKR 执行明细附表 —— 详见 [brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md) |

---

## 四、`broker_sync_batches` — 通用同步批次表

记录每次同步操作的元信息，所有券商共用。

### 4.1 表结构

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | BIGSERIAL | PK | 主键 |
| `broker_code` | VARCHAR(50) | NOT NULL | 券商技术标识（如 `ibkr`、`tiger`），详见 [broker-registration.md](./broker-registration.md) |
| `sync_date_from` | DATE | NOT NULL | 同步数据的起始日期 |
| `sync_date_to` | DATE | NOT NULL | 同步数据的结束日期 |
| `total_count` | INTEGER | NOT NULL DEFAULT 0 | 同步记录总数 |
| `imported_count` | INTEGER | NOT NULL DEFAULT 0 | 已导入正式表的数量 |
| `skipped_count` | INTEGER | NOT NULL DEFAULT 0 | 跳过的数量（重复记录等） |
| ~~`failed_count`~~ | ~~INTEGER~~ | — | **已由 V28 删除**（v2：没有"部分失败"概念，任一记录失败会触发整批清理。保留该行仅为历史参考；V19-V27 时期存在此列） |
| `status` | VARCHAR(32) | NOT NULL | 批次状态（见下方状态枚举） |
| `phase` | VARCHAR(32) | | `PROCESSING` 时表示当前阶段（FETCHING/STAGING/IMPORTING）；`CLEANUP_FAILED` 时保留清理发起时的阶段用于诊断；其他状态为 NULL。详见 [import-consistency.md](./import-consistency.md) |
| `error_message` | TEXT | | 批次级错误信息 |
| `started_at` | TIMESTAMP | | 同步开始时间 |
| `completed_at` | TIMESTAMP | | 同步完成时间 |
| `created_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 创建时间 |
| `updated_at` | TIMESTAMP | NOT NULL DEFAULT NOW() | 更新时间 |

### 4.2 批次状态枚举

> v2（import-consistency.md）将状态机简化为"活跃态 + 三终态"。老的 `PARTIAL` / `INTERRUPTED` 已废弃，被"失败即清理"模型取代。

| 状态 | 含义 |
|------|------|
| `PENDING` | 已创建，等待异步线程处理（活跃态，阻塞新 sync） |
| `PROCESSING` | 正在同步 / 导入中（活跃态，阻塞新 sync） |
| `COMPLETED` | 全部成功（终态） |
| `FAILED` | 失败且数据已清理（终态，保证无残留） |
| `CLEANUP_FAILED` | 失败且清理本身失败，数据可能有残留，阻塞所有新 sync 直到人工介入（活跃态） |

> 状态机细节、Phase 字段含义、并发控制（DB 级唯一约束）、清理机制、CLEANUP_FAILED 人工处理 SOP 详见 [import-consistency.md](./import-consistency.md)。
>
> **v1 → v2 对照**（仅供历史追溯，v1 的 `PARTIAL` / `INTERRUPTED` 不再使用）：
> - v1 `PARTIAL`（部分成功） → v2：整批 `FAILED` + 清理
> - v1 `INTERRUPTED`（进程中断） → v2：启动检测 → 清理 → `FAILED`

### 4.3 索引

| 索引 | 列 | 说明 |
|------|-----|------|
| `idx_sync_batches_broker_code` | `broker_code` | 按券商筛选批次 |
| `idx_sync_batches_status` | `status` | 按状态筛选批次 |

---

## 五、`trade_records` 扩展字段

在现有 `trade_records` 表上新增 3 个字段，用于关联同步来源信息。所有券商共用。

### 5.1 新增字段

| 新增列名 | 类型 | 约束 | 说明 |
|---------|------|------|------|
| `external_id` | VARCHAR(100) | | 券商原始订单 ID（IBKR 的 `orderID`、Tiger 的 `orderId` 等），`NULL` 表示手动录入 |
| `external_broker` | VARCHAR(50) | | 来源券商标识（如 `ibkr`、`tiger`），与 `external_id` 配对使用，`NULL` 表示手动录入 |
| `sync_batch_id` | BIGINT | FK→`broker_sync_batches.id` | 关联同步批次，`NULL` 表示手动录入 |

> **注意**：`external_id` 存储的是券商"订单级别"的唯一标识，与 `trade_records` 的 Order 粒度一致，而非底层成交明细 ID（如 IBKR 的 `tradeID`）。

### 5.2 `trade_trigger` 不做改动

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

### 5.3 索引与约束

| 类型 | 列 | 说明 |
|------|-----|------|
| UNIQUE (partial) | `(external_broker, external_id) WHERE external_id IS NOT NULL` | 防止同一券商的同一笔订单重复导入 |
| INDEX | `sync_batch_id` | 按批次查询导入的记录 |
| FK | `sync_batch_id` → `broker_sync_batches.id` | 外键关联批次 |

---

## 六、整体数据流

```
券商 API 响应 (JSON/XML)
    ↓ 反序列化 + 解析
券商专属内存模型 (如 IbkrOrderRecord / TigerOrderRecord)
    ↓ 写入暂存表
券商专属暂存表 (如 ibkr_staged_orders)          batch 状态: PROCESSING
    ↓ 字段映射 + 类型转换 + 去重校验
    ↓ 每条记录独立事务，状态更新为 IMPORTED / SKIPPED / CONFLICT / FAILED
trade_records（正式表）                          batch 状态: COMPLETED / FAILED / CLEANUP_FAILED
    ↓
    设置 external_id = 券商订单 ID, external_broker = 券商 code, sync_batch_id
    设置 trade_trigger 为交易实际业务含义（MANUAL / OPTION / MARKET_EVENT）
```

---

## 七、ER 关系图（框架层视角）

```
broker_sync_batches (1) ──── (N) <broker>_staged_orders      ← 按券商独立
        │                          │
        │                          │ （可选）(N) <broker>_staged_trade_confirms
        │
        │ (1)
        │
        └──── (N) trade_records (通过 sync_batch_id 关联)

trade_records.(external_broker + external_id) ←→ <broker>_staged_orders.order_id
```

各券商专属暂存表的详细结构参见：
- IBKR：[brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md)
- Tiger：（暂存表 Phase 3 接入，参考 [brokers/tiger/open-api.md](../brokers/tiger/open-api.md)）

---

## 八、后续扩展

### 8.1 新增券商暂存表

当接入新券商（如 Schwab、Futu）时，只需新建对应的暂存表：

```
tiger_staged_orders             → 字段 1:1 对应 TigerOrderRecord
schwab_staged_orders            → 字段 1:1 对应 SchwabTradeRecord
futu_staged_orders              → 字段 1:1 对应 FutuOrderRecord
```

`broker_sync_batches` 表通用，无需修改。`trade_records` 扩展字段也通用（`external_broker` 区分来源，`external_id` 存各券商的订单 ID）。每个新券商应在 `brokers/<code>/` 目录下新建一份类似 [brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md) 的文档，定义自己的暂存表结构与字段映射规范。

### 8.2 Flyway 迁移脚本

| 脚本 | 内容 | 状态 |
|------|------|------|
| `V19__create_broker_sync_batches.sql` | 创建 `broker_sync_batches` 表 | ✅ 已完成 |
| `V20__create_ibkr_staged_orders.sql` | 创建 `ibkr_staged_orders` 表（IBKR 专属，见 [brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md)） | ✅ 已完成 |
| `V21__create_ibkr_staged_trade_confirms.sql` | 创建 `ibkr_staged_trade_confirms` 表（IBKR 专属） | ✅ 已完成 |
| `V22__add_external_fields_to_trade_records.sql` | 为 `trade_records` 新增 `external_id`、`external_broker`、`sync_batch_id` 字段 | ✅ 已完成 |
| `V23__add_broker_code_and_rename_batch_broker_name.sql` | `brokers` 新增 `broker_code` 列（UNIQUE 部分索引）+ `broker_sync_batches.broker_name` → `broker_code` 改名 | ✅ 已完成，详见 [broker-registration.md](./broker-registration.md) |
| `V24__add_phase_and_expand_batch_status.sql` | `broker_sync_batches` 新增 `phase` 列 + `status` 枚举扩展（引入 `PROCESSING` / `PARTIAL` / `INTERRUPTED`） | ✅ 已执行（**历史脚本，`PARTIAL` / `INTERRUPTED` 已由 V28 在 v2 模型中废弃**），详见 [import-consistency.md](./import-consistency.md) |
| `V28__simplify_sync_batch_state_model.sql` | 删除 `failed_count` 列；刷新 `status` / `phase` 注释（5 态模型）；新增部分唯一索引 `uk_only_one_active`（PostgreSQL 部分索引，约束"至多一个活跃 batch"） | ✅ 已执行，详见 [import-consistency.md § 四 / § 六](./import-consistency.md) |

### 8.3 JPA Entity 与 Repository（框架层）

| 类 | 说明 | 状态 |
|----|------|------|
| `BrokerSyncBatch` (Entity) | 对应 `broker_sync_batches` 表 | ✅ 已完成 |
| `BrokerSyncBatchRepository` | 批次表的 Repository | ✅ 已完成 |
| `TradeRecord` (Entity 扩展) | 新增 `externalId`、`externalBroker`、`syncBatchId` 字段 | ✅ 已完成 |

（IBKR 专属 Entity/Repository 见 [brokers/ibkr/staging-schema.md](../brokers/ibkr/staging-schema.md)）

---

## 九、开放问题与待后续讨论

> **最后整理**：2026-04-21

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

### ✅ 已解决（归档）

| # | 问题 | 解决方式 |
|---|------|---------|
| R-1 | 批次导入事务策略 | **逐条独立事务 + 幂等**，详见 [import-consistency.md](./import-consistency.md) |
| R-2 | `brokerId` 查找策略 | `brokers` 表新增 `broker_code` 列，通过 `findByBrokerCode()` 直接查找，详见 [broker-registration.md](./broker-registration.md) |
| R-3 | ETF 识别 | `assetCategory = STK` 时统一默认为 `STOCK`，历史 ETF 数据已由用户手动修正为 STOCK（ETF 枚举值保留） |
| R-4 | `trade_trigger` 是否新增 `BROKER_SYNC` | **否**，`trade_trigger` 描述"交易为什么发生"，不描述"记录来源"。通过 `external_id IS NULL` 区分手动/同步 |
| R-5 | BookTrade 的 `tradeTrigger` 判定 | 通过 `orderTime` + `orderType` 为空识别 BookTrade，再查关联 TradeConfirm 的 `code` 字段判定具体期权事件类型。详见 [booktrade-mapping.md](../brokers/ibkr/booktrade-mapping.md) |
| R-6 | 期权 symbol 格式转换 | **无需额外设计**。IBKR 暂存表已存 `strike`、`expiry`、`putCall` 为独立字段，导入时直接拼接为系统格式 `{underlying}-{expiry}-{putCall}{strike}`（如 `AAPL-20260130-C265`） |
| R-7 | 暂存表 → `trade_records` 字段映射 | **已完成完整映射规范**，详见 [brokers/ibkr/staging-schema.md § 五](../brokers/ibkr/staging-schema.md#五字段映射规范ibkr_staged_orders--trade_records) |
| R-8 | 导入时的冲突处理策略 | **不做手动记录冲突匹配**。系统假设不存在手动录入的历史记录，去重仅依赖 `(external_broker, external_id)` 唯一索引。已有相同 `external_id` → SKIPPED。`CONFLICT` 状态仅保留为理论预留，当前不会触发 |
| R-9 | 前端状态交互设计 | **已在 [import-consistency.md](./import-consistency.md) v2 完整定义**。三终态（`COMPLETED` / `FAILED` / `CLEANUP_FAILED`）+ 两活跃态（`PENDING` / `PROCESSING`）；失败即清理，不再有 Resume / 部分成功。`FAILED` → 用户可重新触发新 sync；`CLEANUP_FAILED` → 前端展示人工介入提示。（v1 曾设计的 "Resume 按钮 / PARTIAL 失败详情" 已整体废弃） |
