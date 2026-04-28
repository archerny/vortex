# 证券类型分类与 UNKNOWN 兜底规则

**状态**：✅ 已实现（2026-04-24）
**适用范围**：所有 broker adapter（跨 broker 通用契约）
**最后更新**：2026-04-28（H6 修正：长桥分类描述与 README v0.2.3 对齐——只用 symbol 后缀正则，stock_type 不参与决策）

## 1. 背景

每个 broker 返回的证券类型字段都不一样：

- 老虎（Tiger）：`secType` 字段，取值 `STK` / `OPT` / `WAR` / `FUT` / ...
- IBKR：`assetCategory` 字段，取值 `STK` / `OPT` / `FUT` / `CASH` / `FUND` / ...
- 长桥（Longbridge）：**仅按 symbol 后缀正则识别**（`^[A-Z]+\.US$` / `^\d{1,5}\.HK$`）；上游 `stock_type` 仅作 debug 副路径，不参与分类决策
- 未来接入的 broker：又是另一套

本文档定义：

1. **本系统内部统一的证券类型枚举**（`AssetType`）
2. **分类器契约**：每个 broker adapter 必须把 broker 原生字段映射到 `AssetType`，或显式抛 UNRECOGNIZED
3. **UNRECOGNIZED 兜底规则**（跨 broker 统一）
4. **"该 adapter 支持哪些 `AssetType`"** —— 明确这是 **per-broker 决策**，不在 framework 层规定

目标：让"**什么数据能落库、什么数据应该 fail**"这件事有一个跨 broker 一致的**机制**，同时让"**我这个 adapter 支持什么**"的**范围**由每个 broker 自己声明，两者解耦。

---

## 2. 内部统一枚举：`AssetType`

现有枚举（`backend/src/main/java/com/vortex/entity/enums/AssetType.java`，请以代码为准）：

| 枚举值 | 含义 |
|--------|------|
| `STOCK` | 普通股（含港股/美股主板股票） |
| `ETF` | 交易所交易基金 |
| `OPTION_CALL` | 看涨期权 |
| `OPTION_PUT` | 看跌期权 |

**未来可能新增**（framework 层允许扩展，但新增必须配合业务语义设计，见 §7）：

- `WARRANT`：港股窝轮
- `BOND`：债券
- `FUND`：场外基金
- `CRYPTO`：数字货币

**关键**：`AssetType` 是 framework 层的**开放枚举**，代表"系统原则上能表达的资产类型"。具体**哪个 broker adapter 支持哪些 `AssetType`**，是 per-broker 的独立决策，声明在各自的 broker design doc 里（`brokers/tiger/open-api.md`、`brokers/ibkr/flex-web-service.md`、`brokers/longbridge/README.md` 等）。

当前各 broker 的支持范围详见 `docs/broker-sync/README.md` 的"各 broker 支持范围"总览表。

---

## 3. 分类机制与 UNRECOGNIZED 兜底

```
broker 原始数据
      │
      ▼
  adapter 内的 classifier
      │
      ├── 映射到该 adapter 支持的某个 AssetType ──→ ✅ 继续处理，落 staged → 落 trade_records
      │
      └── 无法映射（未见过 / 该 adapter 未实现）──→ ❌ UNRECOGNIZED
                                                    （单条 staged FAILED + adapter 汇总层 fail-fast
                                                     + SyncBatchFailureHandler 清理；
                                                     详见 unrecognized-data-logging.md）
```

**跨 broker 通用原则**：

1. **adapter 只返回"自己支持的 `AssetType`"**。例如 IBKR/Tiger 返回 `STOCK` / `OPTION_CALL` / `OPTION_PUT`；长桥 v0.2.3 只返回 `STOCK`。
2. **不维护"识别了但跳过"的软白名单**。反模式详见 §4.2。
3. **不允许"best-effort 继续"**。任何单条无法分类的数据都走 UNRECOGNIZED 路径，整批 cleanup。
4. **"能支持的 `AssetType` 子集"由 broker 自己决定**，framework 不预设范围。

---

## 4. Adapter 分类器契约

每个 broker adapter **必须**实现一个 symbol/sec-type 分类器，职责：

1. **输入**：broker 原始数据（至少包含 broker 自己的类型字段 + symbol 字符串 + 必要的辅助字段，如 IBKR 的 `putCall`）
2. **输出**：
   - 该 adapter **自己声明支持的** `AssetType` 子集中的某一个
   - 或抛出 `CategorizedSyncException(FailureCategory.UNRECOGNIZED, externalId, reason)`（框架通用异常，定义在 `backend/src/main/java/com/vortex/sync/core/CategorizedSyncException.java`）

**禁止**返回 `null` 或返回"该 adapter 声明不支持"的 `AssetType`——一旦识别为不支持的类型，直接抛 UNRECOGNIZED 异常，不要让上层去判断。

**`externalId` 的填写**：映射器层（`<Broker>TradeRecordMapper` / `parse*Date` 等）通常不直接持有 staged 行对象，可以把 `externalId` 传 `null`；`<Broker>ImportService.formatStagedError` 会在捕获后按 staged 行回填（Tiger 用 `tigerId`，IBKR 用 `orderId`）。

### 4.1 伪代码模板

```java
public AssetType classify(RawTradeRecord raw) {
    String rawType = raw.getRawSecType();   // broker 原生字段（secType / assetCategory / stock_type）
    String symbol = raw.getSymbol();

    // broker-specific 分类逻辑（仅示意，具体请看各 broker 的 design doc）
    if (isStock(rawType, symbol)) {
        return AssetType.STOCK;
    }
    if (isCallOption(rawType, raw)) {      // e.g. IBKR: assetCategory=OPT && putCall=C
        return AssetType.OPTION_CALL;
    }
    if (isPutOption(rawType, raw)) {       // e.g. IBKR: assetCategory=OPT && putCall=P
        return AssetType.OPTION_PUT;
    }

    // 未识别 / 该 adapter 当前版本不支持 → 统一走 UNRECOGNIZED
    throw new CategorizedSyncException(
        FailureCategory.UNRECOGNIZED,
        null,    // externalId 留给 ImportService.formatStagedError 回填
        "sec_type not supported by this adapter version: raw_type=" + rawType + ", symbol=" + symbol
    );
}
```

### 4.2 为什么不用 `Map<String, AssetType>` 兜底

初版设计时可能会想：

```java
// 反模式
private static final Map<String, AssetType> TYPE_MAP = Map.of(
    "STK", AssetType.STOCK,
    "OPT", AssetType.OPTION_CALL,  // ← 然后某处 if (type == OPTION_CALL && !supported) skip
    "ETF", AssetType.ETF
);
```

**不要这样做**，原因：

1. 会诱导"识别了但跳过"的静默逻辑——和 fail-fast 原则冲突
2. 期权的 CALL vs PUT 通常不能仅由 secType 决定（要看 `putCall` 字段或从 symbol 解析），单层 map 表达不了
3. 每多一条"看似支持但其实不支持"的映射就多一份技术债

**正确做法**：classifier 用显式的分支逻辑处理**该 adapter 明确支持**的类型，其他一切抛 UNRECOGNIZED。

---

## 5. Broker 侧映射

各 broker 的原始类型字段 → `AssetType` 的具体映射、symbol 格式规则（OCC 21 位 / `.HK` 后缀 / IBKR `description` 解析等）、以及 adapter 自己声明支持哪些 `AssetType`，**一律放在各自的 broker design doc 里**：

- Tiger：[`brokers/tiger/open-api.md`](../brokers/tiger/open-api.md) + [`brokers/tiger/staging-schema.md`](../brokers/tiger/staging-schema.md)
- IBKR：[`brokers/ibkr/flex-web-service.md`](../brokers/ibkr/flex-web-service.md) + [`brokers/ibkr/staging-schema.md`](../brokers/ibkr/staging-schema.md)
- 长桥：[`brokers/longbridge/README.md`](../brokers/longbridge/README.md)
- 未来新 broker 同理（至少提供 `README.md` + `staging-schema.md`）

framework 层**只**提供：分类器契约（§4）、UNRECOGNIZED 兜底路径（§3 + `unrecognized-data-logging.md`）、新 broker 接入 checklist（§6）。

---

## 6. 新 broker 接入检查清单

接入新 broker 时，必须回答以下问题并在对应的 `brokers/<broker>/design-v*.md` 里文档化：

- [ ] broker 的哪个字段表示证券类型？字段名、数据类型、可能取值？
- [ ] **本 adapter 声明支持哪些 `AssetType`**？给出明确子集（例如"STOCK + OPTION_CALL + OPTION_PUT"）
- [ ] 每一种支持的 `AssetType`，broker 原生字段如何映射？给出**精确的枚举值和判定条件**（不要用"大概是"）
- [ ] 期权场景（若支持）：CALL vs PUT 从哪个字段得到？symbol 格式是什么？
- [ ] symbol 的格式规律？（如港股 `.HK` 后缀、美股期权的 OCC 格式）
- [ ] 有没有"看起来像股票但实际不是"的情况？（如 ADR、SPAC、优先股——应如何归类）
- [ ] classifier 的单元测试是否覆盖了以下场景：
  - [ ] 每种声明支持的 `AssetType` → 正确映射 ✅
  - [ ] 明确不支持的类型 → UNRECOGNIZED 异常 ❌
  - [ ] 未见过的 raw_type → UNRECOGNIZED 异常 ❌
  - [ ] raw_type 字段为空/null → UNRECOGNIZED 异常 ❌

---

## 7. Broker 支持范围扩展流程

当某个 broker adapter 决定扩展支持范围（例如长桥未来要加 OPTION 支持）时，演进步骤：

1. **确认 `AssetType` 枚举已覆盖**：如果是 framework 尚无的类型（如 `WARRANT`），先在 `AssetType.java` 里新增枚举值
2. **在该 broker 的 design doc 里新增业务语义章节**：该类型的开仓/平仓如何表达、手续费如何记账、期权 assignment/exercise 如何触发（若适用）、汇率处理等
3. **更新该 broker 的 classifier**：把该类型从"抛 UNRECOGNIZED"改为"返回对应 `AssetType`"
4. **更新该 broker 的 ImportWorker / RecordMapper**：覆盖新类型的落库逻辑
5. **更新该 broker 的集成测试 + fixture**：至少包含该类型的正常样本和边界样本
6. **更新 README 支持范围总览表**：反映该 broker 新增支持的 `AssetType`

**禁止**在未完成第 2 步（业务语义设计）前就让 classifier 放行新类型——会导致"数据落库但业务语义错乱"的脏状态。

**注意**：扩展支持范围是**该 broker** 的事情，不影响其他 broker。例如长桥加 OPTION 支持，不需要改 Tiger 或 IBKR。

---

## 8. 相关文档

- [`unrecognized-data-logging.md`](./unrecognized-data-logging.md)：UNRECOGNIZED 失败的日志格式、batch 失败路径
- [`data-persistence.md`](./data-persistence.md)：staged 表字段约束、raw_payload 保留哪些字段以支持事后排查
- [`brokers/tiger/staging-schema.md`](../brokers/tiger/staging-schema.md)、[`brokers/ibkr/staging-schema.md`](../brokers/ibkr/staging-schema.md) 等：各 broker 具体的分类器映射和支持范围声明
- [`README.md`](../README.md)（broker-sync 目录）：各 broker 当前支持的 `AssetType` 总览表
