# 删除交易记录与市场事件中的「证券名称」字段

> 创建日期：2026-04-21
> 状态：✅ 已实施

---

## 一、背景

在当前设计中，以下 4 张表都内嵌了一份"证券名称"字段，但这些名称字段存在重复冗余、维护成本高、语义模糊等问题：

| 表 | 名称字段 | 说明 |
|----|---------|-----|
| `trade_records` | `name` | 底层证券名称 |
| `events_stock_split` | `underlying_symbol_name` | 拆股事件的标的名称 |
| `events_symbol_change` | `underlying_symbol_name` | 代码变更前的证券名称 |
| `events_symbol_change` | `new_underlying_symbol_name` | 代码变更后的证券名称 |
| `events_dividend_in_kind` | `underlying_symbol_name` | 持仓证券名称 |
| `events_dividend_in_kind` | `dividend_symbol_name` | 分红证券名称 |

**核心问题**：

1. **多处维护、难以一致** — 同一支证券在多条记录中出现时，每条记录都需要各自填写名称，容易出现拼写差异（如 "Tesla" / "特斯拉" / "TSLA"）。
2. **名称本质上是证券的"属性"**，不应该挂在交易记录/事件上 — 未来会专门建立一张证券基础信息表来统一管理。
3. **价值低** — 前端实际上大多只依赖 `symbol` / `underlyingSymbol` 这样的代码，名称仅作为展示锦上添花；若需要展示，应在未来通过证券基础信息表在前端 Join 展示。
4. **增加填表负担** — 新增/编辑时要求用户填写名称，但这是一项机械、易错、低价值的信息录入工作。

---

## 二、目标

一次性、彻底地从当前领域模型中移除所有"证券名称"字段：

- 数据库：删除 6 个列。
- 后端实体 / DTO / Service：移除所有 `name` / `SymbolName` 字段及其 getter/setter/toString/自动回填逻辑。
- 前端页面：移除所有与名称相关的表单项、表格列、详情项，并简化布局。
- 未来方向：另行建立一张独立的 **证券基础信息表**，以 `symbol` 为主键存放名称、ISIN、市场等通用信息，各业务表通过 `symbol` 关联查询即可。

---

## 三、范围与决策

### 3.1 决策点

| 决策 | 选项 | 采用 |
|------|------|------|
| DB 迁移策略 | 一次性 DROP vs 先置空再 DROP | **一次性 DROP**（单个 V25 迁移） |
| `autoFillFromExistingTradeRecord` 是否保留 | 删除方法 vs 保留并只填 `currency` | **保留，只保留填 currency 的能力** |
| `dividend-in-kind-tab-refactor.md` 处理 | 全文删除 vs 打废弃标注 | **Option A：保留并加废弃说明** |
| Commit 拆分 | 一次 vs 两次 | **两次**：① 代码 ② 文档 |

### 3.2 涉及文件

#### 数据库迁移（新增）
- `backend/src/main/resources/db/migration/V25__drop_symbol_name_fields.sql`

#### 后端实体 / DTO
- `backend/.../entity/TradeRecord.java` — 移除 `name` 字段及其 getter/setter/toString
- `backend/.../entity/BaseMarketEvent.java` — 移除 `underlyingSymbolName` 字段及 getter/setter
- `backend/.../entity/StockSplitEvent.java` — 更新 `toString`
- `backend/.../entity/SymbolChangeEvent.java` — 移除 `newUnderlyingSymbolName` 字段及 getter/setter、toString
- `backend/.../entity/DividendInKindEvent.java` — 移除 `dividendSymbolName` 字段及 getter/setter、toString
- `backend/.../dto/PositionSnapshot.java` — 移除 `name` 字段及 getter/setter

#### 后端 Service
- `backend/.../service/StockSplitEventService.java` — `autoFillFromExistingTradeRecord` 只保留 `currency`
- `backend/.../service/SymbolChangeEventService.java` — 同上；`update()` 不再设置 `newUnderlyingSymbolName`
- `backend/.../service/DividendInKindEventService.java` — 同上；`update()` 不再设置两个名称字段
- `backend/.../service/MarketEventProcessingService.java` — 移除 4 处 `setName(...)`（拆股 1 处、代码变更 SELL/BUY 各 1 处、实物分红 1 处）
- `backend/.../service/PositionService.java` — 移除 `snapshot.setName(...)` 与"update latest name"代码块
- `backend/.../service/TradeRecordService.java` — `update()` 不再设置 `name`
- `backend/.../sync/adapter/ibkr/IbkrImportWorker.java` — 不再从 IBKR description 填 `name`

#### 后端测试
- `backend/src/test/.../service/PositionServiceTest.java` — 移除 `record.setName(...)`
- `backend/src/test/.../sync/adapter/ibkr/IbkrImportWorkerTest.java` — 移除 `assertEquals("APPLE INC", record.getName())`

#### 前端
- `frontend/src/pages/trade/TradeColumns.jsx` — "底层证券"列改为只展示 `underlyingSymbol`
- `frontend/src/pages/trade/TradeRecords.jsx` — 新增表单删除"底层证券名称"项；payload 去掉 `name`；布局调整
- `frontend/src/pages/trade/TradeRecordDetail.jsx` — 基本信息删除"底层证券名称"项；编辑表单删除对应输入与回填；payload 去掉 `name`
- `frontend/src/pages/analysis/PositionSnapshotTab.jsx` — 表格删除"底层证券名称"列
- `frontend/src/pages/market-events/StockSplitTab.jsx` — 表格删除"底层证券名称"列
- `frontend/src/pages/market-events/SymbolChangeTab.jsx` — 表格删除"旧证券名称"与"新证券名称"两列；表单删除"新底层证券名称"项
- `frontend/src/pages/market-events/DividendInKindTab.jsx` — 表格删除"底层证券名称"与"分红证券名称"两列；表单删除"分红证券名称"项；布局调整

---

## 四、数据库迁移方案

采用单个 Flyway 迁移 `V25__drop_symbol_name_fields.sql` 一次性删除所有 6 列（使用 `DROP COLUMN IF EXISTS` 以保持幂等）：

```sql
-- V25: Drop all security-name columns from trade_records and market event tables
ALTER TABLE trade_records              DROP COLUMN IF EXISTS name;
ALTER TABLE events_stock_split         DROP COLUMN IF EXISTS underlying_symbol_name;
ALTER TABLE events_symbol_change       DROP COLUMN IF EXISTS underlying_symbol_name;
ALTER TABLE events_symbol_change       DROP COLUMN IF EXISTS new_underlying_symbol_name;
ALTER TABLE events_dividend_in_kind    DROP COLUMN IF EXISTS underlying_symbol_name;
ALTER TABLE events_dividend_in_kind    DROP COLUMN IF EXISTS dividend_symbol_name;
```

**不保留历史数据** — 名称字段本身就是冗余展示信息，未来由独立的证券基础信息表统一承载。

---

## 五、后端自动回填逻辑的处理

拆股 / 代码变更 / 实物分红三个事件的 Service 中原本都有 `autoFillFromExistingTradeRecord(event)` 方法，同时填 `currency` 与 `underlyingSymbolName`。本次重构：

- **保留方法**，继续用于从最近一条交易记录中推断 `currency`。
- **移除 `underlyingSymbolName` 分支**，Javadoc / 内联注释同步收敛。
- 相应的 `update()` 方法中，不再拷贝任何 `xxxSymbolName` 字段。

保留 `currency` 自动填充的理由：它是业务计算（金额、收益、汇率折算）必须的，且可以在一次填表中省掉用户手动选择的负担。

---

## 六、前端页面调整要点

- **展示层简化** — 所有"名称"列一律删除；原先以 `代码(名称)` 形式展示底层证券的列改为只展示代码。
- **表单简化** — 删除"名称"输入项；必要时将剩余输入项重新分栏，保持页面紧凑美观。
- **无兼容期** — 因同步删除后端字段与数据库列，不存在"旧数据中名称字段可读"的情况；后端返回的响应体中也不再有 `name` / `*SymbolName` 字段。

---

## 七、验证

- 后端：`mvn test` — ✅ BUILD SUCCESS（所有测试通过）。
- 前端：`npm run build` — ✅ 构建成功、无 lint 错误。
- 搜索验证：全项目 `SymbolName` / `setName\(/getName\(` 均为 0 残留（mock Dashboard 数据与 antd Form `name="xxx"` 属性不在范围内）。

---

## 八、未来计划

- **独立证券基础信息表**（暂定名 `securities`）
  - 主键：`symbol`（或复合主键 `symbol + market`）
  - 字段：`name`、`market`、`isin`、`asset_type`、`currency` 等
  - 各业务表通过 `symbol` 关联查询名称与其它展示信息
- **数据来源**：可由用户手动维护、或接入第三方 API / 券商同步时顺带维护。
- **前端展示**：持仓快照、交易记录、市场事件等页面如需展示"名称"，通过该表 Join / 前端接口聚合展示，不再冗余存储在业务表中。
