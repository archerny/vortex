# BookTrade 触发判定与期权事件导入映射设计

> **创建日期**：2026-04-18  
> **最后更新**：2026-04-18  
> **状态**：✅ 已实现（IbkrImportService: BookTrade 判定 + code 解析 + triggerRefId 回填 + STK 侧语义匹配 + 歧义消解）  
> **关联**：[data-persistence.md](../../framework/data-persistence.md) | [trade-trigger-design.md](../../../trade-trigger-design.md) | [trade-type-refactor-discussion.md](../../../trade-type-refactor-discussion.md) | [flex-web-service.md](./flex-web-service.md)  
> **解决问题**：[data-persistence.md § R-5](../../framework/data-persistence.md#九开放问题与待后续讨论)（BookTrade 的 `tradeTrigger` 判定）

---

## 一、背景与问题

### 1.1 问题来源

在 IBKR 数据导入流程中，`ibkr_staged_orders` → `trade_records` 的映射需要为每条记录设置 `trade_trigger` 字段：

- 用户主动下单 → `MANUAL`
- 期权到期/行权/被指派 → `OPTION`

**核心难点**：`ibkr_staged_orders` 是 Order 级别的数据，而 Order 级别**没有** `transactionType` 和 `code` 字段（这两个字段仅存在于 TradeConfirm 级别）。因此无法直接从 Order 记录判断一笔交易是否为 BookTrade（期权事件）。

### 1.2 BookTrade 的特征

基于实际数据验证（155 条 TradeConfirm / 150 条 Order），BookTrade 在 Order 级别的特征为：

| 字段 | ExchTrade（正常交易） | BookTrade（期权事件） |
|------|---------------------|---------------------|
| `orderTime` | 有值 | **为空** |
| `orderType` | 有值（LMT, MKT 等） | **为空** |

---

## 二、方案设计

### 2.1 BookTrade 识别规则

**在 Order 级别**，通过以下条件判定一条记录是否为 BookTrade：

```
IF orderTime 为空 AND orderType 为空:
    → 该 Order 对应的是 BookTrade（期权事件产生的簿记交易）
ELSE:
    → 该 Order 对应的是 ExchTrade（用户主动下单的交易所成交）
```

### 2.2 BookTrade 的 `tradeTrigger` / `triggerRefType` 判定

识别为 BookTrade 后，需要进一步确定具体的期权事件类型。这需要查询同一 Order 对应的 **TradeConfirm 记录**（存储在 `ibkr_staged_trade_confirms` 表中）的 `code` 字段。

#### 查找 TradeConfirm 的方式

```sql
SELECT code FROM ibkr_staged_trade_confirms
WHERE order_id = :orderIdFromStagedOrder
  AND batch_id = :sameBatchId
LIMIT 1
```

> 同一 Order 下的多条 TradeConfirm（部分成交）具有相同的 `code` 值，取任意一条即可。

#### `code` 字段解析规则

`code` 是多值字段，用分号 `;` 分隔。解析步骤：

1. **按分号分割为独立标记数组**（如 `"A;C"` → `["A", "C"]`）
2. **逐标记匹配**，按以下优先级判定：

| 优先级 | 匹配标记 | 判定结果 | `trade_trigger` | `trigger_ref_type` |
|--------|---------|---------|----------------|-------------------|
| 1 | `MEx` | 手动行权（股息相关） | `OPTION` | `OPTION_EXERCISE` |
| 2 | `AEx` | 自动行权（股息相关） | `OPTION` | `OPTION_EXERCISE` |
| 3 | `Ex` | 行权（持有人主动行权） | `OPTION` | `OPTION_EXERCISE` |
| 4 | `Ep` | 期权到期 | `OPTION` | `OPTION_EXPIRE` |
| 5 | `A` | 被指派 | `OPTION` | `OPTION_ASSIGNED` |

> **重要**：使用分割后的独立标记进行**精确匹配**（不是子串包含），避免 `AEx` 被误匹配为 `A` + `Ex`。

#### 解析示例

| `code` 原始值 | 分割结果 | 匹配标记 | 判定 |
|--------------|---------|---------|------|
| `C;Ep` | `["C", "Ep"]` | `Ep` | OPTION_EXPIRE |
| `A;C` | `["A", "C"]` | `A` | OPTION_ASSIGNED |
| `A;O` | `["A", "O"]` | `A` | OPTION_ASSIGNED |
| `Ex;C` | `["Ex", "C"]` | `Ex` | OPTION_EXERCISE |
| `Ex;O` | `["Ex", "O"]` | `Ex` | OPTION_EXERCISE |
| `MEx;C` | `["MEx", "C"]` | `MEx` | OPTION_EXERCISE |

### 2.3 完整判定流程

```
导入一条 ibkr_staged_order 记录时：

1. 检查 orderTime 和 orderType：
   ┌─ 均非空 → trade_trigger = MANUAL, trigger_ref_type = NONE, trigger_ref_id = 0
   │           （正常交易，判定结束）
   │
   └─ 均为空 → 识别为 BookTrade，继续步骤 2

2. 查询同 order_id 的 TradeConfirm 记录，获取 code 字段

3. 按分号分割 code，逐标记匹配：
   ┌─ 含 "Ep"           → OPTION_EXPIRE
   ├─ 含 "A"            → OPTION_ASSIGNED
   ├─ 含 "Ex"/"MEx"/"AEx" → OPTION_EXERCISE
   └─ 均未匹配          → ⚠️ 异常情况，记录 WARNING 日志，
                           默认设为 MANUAL（保守策略）

4. 设置 trade_trigger = OPTION, trigger_ref_type = 匹配结果
```

---

## 三、期权事件的 `trigger_ref_id` 分配规则

### 3.1 角色定义

一个期权事件（到期/行权/被指派）在 IBKR 数据中通常产生 **1~2 条 Order 记录**：

| 角色 | 资产类别 | 说明 |
|------|---------|------|
| **期权侧（OPT 侧）** | `assetCategory = OPT` | 期权合约的清算记录（持仓归零） |
| **股票侧（STK 侧）** | `assetCategory = STK` | 由期权事件触发的股票交易（仅行权/被指派有） |

### 3.2 `trigger_ref_id` 分配

| 记录角色 | `trigger_ref_id` | 说明 |
|---------|-----------------|------|
| 期权侧（OPT） | `0` | 事件源头，终态标记 |
| 股票侧（STK） — 到期 | — | 不存在 STK 侧记录（到期不产生股票交易） |
| 股票侧（STK） — 行权/被指派 | 期权侧 `trade_records.id` | 指向对应的 OPT 侧记录 |

### 3.3 关于 `trigger_ref_id` 的语义澄清

`trigger_ref_id` 追溯的是**直接触发关系（一层）**，不是原始开仓追溯：

```
开仓记录:  101(SELL OPT)  102(SELL OPT)     ← 普通交易，trade_trigger = MANUAL
                    ↕ (持仓层面合并为 -2 张)
被指派OPT: 201(qty=-2, A;C)                  ← trigger_ref_id = 0（期权侧终态）
被指派STK: 301(qty=200, A;O)                 ← trigger_ref_id = 201
```

- 201 "消灭"了 101+102 的持仓，这是**持仓计算层面**的事情（FIFO/加权平均）
- `trigger_ref_id` 只表达 301 是由 201 这个"被指派事件"触发的

---

## 四、STK 侧 `trigger_ref_id` 回填机制

### 4.1 问题背景

导入过程中，OPT 侧和 STK 侧的 Order 记录是**独立的两条记录**，没有直接关联字段。需要通过**语义匹配**来建立关联。

### 4.2 导入顺序与回填时机

```
阶段 1：逐条导入所有 staged orders
        - ExchTrade → trade_trigger = MANUAL（直接完成）
        - BookTrade OPT 侧 → trade_trigger = OPTION, trigger_ref_id = 0（直接完成）
        - BookTrade STK 侧 → trade_trigger = OPTION, trigger_ref_id = 0（暂时为 0）

阶段 2：回填 STK 侧的 trigger_ref_id
        - 查找所有 trigger_ref_id = 0 且 asset_type = STOCK 且 trade_trigger = OPTION 的记录
        - 对每条进行语义匹配，找到对应的 OPT 侧记录
        - 回填 trigger_ref_id = OPT 侧 trade_records.id
```

### 4.3 语义匹配条件

对一条 STK 侧记录，查找其对应的 OPT 侧记录：

```sql
SELECT id, symbol FROM trade_records
WHERE trade_trigger = 'OPTION'
  AND trigger_ref_type = :sameRefType           -- OPTION_ASSIGNED 或 OPTION_EXERCISE
  AND trigger_ref_id = 0                         -- 期权侧标记
  AND asset_type IN ('OPTION_CALL', 'OPTION_PUT')
  AND underlying_symbol = :stkSymbol             -- 如 AAPL
  AND trade_date = :stkTradeDate                 -- 同一天
  AND is_deleted = false
```

### 4.4 多结果歧义消解

当以上查询返回多条记录时（同一天、同一标的、多个期权合约同时被指派/行权），追加**行权价匹配**：

```java
// STK 侧的成交价格 == 期权的行权价（strike）
BigDecimal stkPrice = stkRecord.getPrice();  // 股票成交价 = 行权价

matchedOptRecords.stream()
    .filter(opt -> extractStrike(opt.getSymbol()).equals(stkPrice))
    .findFirst();
```

其中 `extractStrike()` 从 OPT 侧的 `symbol`（如 `GOOG-20250620-P250`）中解析出行权价。

### 4.5 进一步歧义消解

如果 strike 匹配后仍有多条（极端情况：同一天、同一标的、同一行权价、不同到期日的多个合约同时触发），追加 **quantity 比例匹配**：

```java
// STK 数量 = abs(OPT 数量) * multiplier（通常 100）
matchedOptRecords.stream()
    .filter(opt -> Math.abs(opt.getQuantity()) * getMultiplier(opt) == Math.abs(stkRecord.getQuantity()))
    .findFirst();
```

### 4.6 匹配结果处理

| 情况 | 处理 |
|------|------|
| 唯一匹配 | 正常回填 `trigger_ref_id = opt.id` |
| 多条匹配 + strike 精确匹配成功 | 正常回填 |
| 多条匹配 + strike + quantity 匹配成功 | 正常回填 |
| 仍然多条（完全无法区分） | 按顺序一一配对（业务上等价），WARNING 日志 |
| 未匹配到任何记录 | 保持 `trigger_ref_id = 0`，WARNING 日志 |

> **匹配失败保持 `trigger_ref_id = 0` 的设计意图**：后续 `TradeVerificationService` 的核对规则可以检测出"股票侧 trigger_ref_id=0"的异常记录（参见 [trade-trigger-design.md § 3.7](../trade-trigger-design.md) 校验规则：`OPTION` 股票侧记录的 `trigger_ref_id` 不应为 0）。

---

## 五、各场景的完整数据映射

### 5.1 期权到期（Expire）

IBKR 产生 **1 条 Order**（仅 OPT 侧）：

| 字段 | IBKR Order 值 | → `trade_records` |
|------|--------------|-------------------|
| `assetCategory` | `OPT` | `asset_type` = `OPTION_CALL` 或 `OPTION_PUT` |
| `buySell` | `SELL`（空头到期）或 `BUY`（多头到期） | `trade_type` = `SELL`（统一为持仓归零方向） |
| `quantity` | 非零值（带符号） | `quantity` = abs(原值) |
| `price` | `0` | `price` = 0 |
| `amount` | `0` | `amount` = 0 |
| `orderTime` | 空 | — |
| `orderType` | 空 | — |
| TradeConfirm `code` | `C;Ep` 或 `Ep` | `trade_trigger` = `OPTION`, `trigger_ref_type` = `OPTION_EXPIRE` |
| — | — | `trigger_ref_id` = `0` |

### 5.2 被指派（Assigned）

IBKR 产生 **2 条 Order**：

#### OPT 侧

| 字段 | IBKR Order 值 | → `trade_records` |
|------|--------------|-------------------|
| `assetCategory` | `OPT` | `asset_type` = `OPTION_CALL` 或 `OPTION_PUT` |
| `buySell` | 对应方向 | `trade_type` = `SELL`（持仓归零） |
| `price` | `0` | `price` = 0 |
| `amount` | `0` | `amount` = 0 |
| TradeConfirm `code` | `A;C` | `trade_trigger` = `OPTION`, `trigger_ref_type` = `OPTION_ASSIGNED` |
| — | — | `trigger_ref_id` = `0` |

#### STK 侧

| 字段 | IBKR Order 值 | → `trade_records` |
|------|--------------|-------------------|
| `assetCategory` | `STK` | `asset_type` = `STOCK` |
| `buySell` | `BUY`（Put 被指派买入）或 `SELL`（Call 被指派卖出） | `trade_type` = 对应方向 |
| `price` | 行权价（如 250） | `price` = 行权价 |
| `amount` | 行权价 × 数量 | `amount` = 对应金额 |
| TradeConfirm `code` | `A;O` 或 `A;C` | `trade_trigger` = `OPTION`, `trigger_ref_type` = `OPTION_ASSIGNED` |
| — | — | `trigger_ref_id` = OPT 侧 `trade_records.id`（回填） |

### 5.3 行权（Exercise）

IBKR 产生 **2 条 Order**，模式与被指派**完全对称**：

#### OPT 侧

| 字段 | IBKR Order 值 | → `trade_records` |
|------|--------------|-------------------|
| TradeConfirm `code` | `Ex;C` | `trade_trigger` = `OPTION`, `trigger_ref_type` = `OPTION_EXERCISE` |
| — | — | `trigger_ref_id` = `0` |
| 其他字段 | 同被指派 OPT 侧 | 同上 |

#### STK 侧

| 字段 | IBKR Order 值 | → `trade_records` |
|------|--------------|-------------------|
| TradeConfirm `code` | `Ex;O` 或 `Ex;C` | `trade_trigger` = `OPTION`, `trigger_ref_type` = `OPTION_EXERCISE` |
| — | — | `trigger_ref_id` = OPT 侧 `trade_records.id`（回填） |
| 其他字段 | 同被指派 STK 侧 | 同上 |

---

## 六、IBKR Code 标记完整参考

来源：[IBKR 官方报告指南](https://www.ibkrguides.com/reportingreference/reportguide/codes_default.htm)

| Code 标记 | 全称 | 含义 | 本系统映射 |
|-----------|------|------|-----------|
| `O` | Opening | 建仓（新开仓位） | 不影响 trigger 判定 |
| `C` | Closing | 平仓（关闭仓位） | 不影响 trigger 判定 |
| `P` | Partial | 部分成交 | 不影响 trigger 判定 |
| `A` | Assignment | 被指派（义务方） | → `OPTION_ASSIGNED` |
| `Ep` | Expired Position | 期权到期失效 | → `OPTION_EXPIRE` |
| `Ex` | Exercise | 行权（持有人主动行权） | → `OPTION_EXERCISE` |
| `MEx` | Manual Exercise (dividend) | 手动行权（股息相关） | → `OPTION_EXERCISE` |
| `AEx` | Automatic Exercise (dividend) | 自动行权（股息相关） | → `OPTION_EXERCISE` |
| `GEA` | Expiration/Assignment from offsetting | 由抵消仓位产生 | → `OPTION_ASSIGNED`（保守归类） |

> **设计决策**：`MEx`、`AEx`、`Ex` 统一映射为 `OPTION_EXERCISE`，不做细分。主动行权和自动行权在结果上完全一样，是否自动触发只是券商的执行细节。

---

## 七、实现要点

### 7.1 服务方法签名

```java
public class IbkrImportService {

    /**
     * Determine trade_trigger and trigger_ref_type for a BookTrade order.
     *
     * @param stagedOrder the staged order being imported
     * @param batchId     the batch ID to look up associated TradeConfirms
     * @return TriggerInfo containing tradeTrigger and triggerRefType
     */
    TriggerInfo determineBookTradeTrigger(IbkrStagedOrder stagedOrder, Long batchId);

    /**
     * Back-fill trigger_ref_id for STK-side records after all records are imported.
     *
     * @param batchId the batch ID to scope the back-fill
     */
    void backfillStockSideTriggerRefId(Long batchId);
}
```

### 7.2 回填的批次范围

回填操作的 scope 限定在**当前 batch** 新导入的记录中：

```sql
-- 查找需要回填的 STK 侧记录
SELECT * FROM trade_records
WHERE sync_batch_id = :batchId
  AND trade_trigger = 'OPTION'
  AND trigger_ref_type IN ('OPTION_ASSIGNED', 'OPTION_EXERCISE')
  AND trigger_ref_id = 0
  AND asset_type = 'STOCK'
  AND is_deleted = false
```

这避免了跨批次的干扰，确保每次导入的回填是独立的。

### 7.3 跨批次匹配

特殊场景：如果 OPT 侧和 STK 侧被分在不同的同步批次中导入（理论上 IBKR 同一日期范围的查询会包含两者，但边界日期可能出现分割），回填时匹配范围不限定 `sync_batch_id`：

```sql
-- 匹配 OPT 侧时不限 batch，确保跨批次也能找到
SELECT id, symbol FROM trade_records
WHERE trade_trigger = 'OPTION'
  AND trigger_ref_type = :sameRefType
  AND trigger_ref_id = 0
  AND asset_type IN ('OPTION_CALL', 'OPTION_PUT')
  AND underlying_symbol = :stkSymbol
  AND trade_date = :stkTradeDate
  AND is_deleted = false
```

---

## 八、与现有设计的一致性验证

本方案产出的 `trade_trigger` + `trigger_ref_type` + `trigger_ref_id` 组合，与以下文档中的定义**完全一致**：

| 文档 | 相关章节 | 一致性 |
|------|---------|--------|
| [trade-trigger-design.md](../trade-trigger-design.md) | § 3.4 字段组合语义 | ✅ |
| [trade-type-refactor-discussion.md](../trade-type-refactor-discussion.md) | § 三、重构后的期权操作场景映射 | ✅ |
| [trade-trigger-design.md](../trade-trigger-design.md) | § 3.7 应用层校验规则 | ✅ |

---

## 九、待确认事项

| # | 问题 | 当前决策 | 备注 |
|---|------|---------|------|
| 1 | `GEA` code 的映射 | 暂归为 `OPTION_ASSIGNED` | 实际数据中未出现，待遇到时确认 |
| 2 | 回填失败的 STK 记录如何在前端展示 | 保持 `trigger_ref_id = 0`，校验规则标记异常 | 前端异常提示待设计 |
