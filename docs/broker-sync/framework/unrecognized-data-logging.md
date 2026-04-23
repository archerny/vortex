# 未识别数据与失败日志规范

**状态**：📋 设计中（未实现）
**适用范围**：所有 broker adapter（跨 broker 通用契约）
**最后更新**：2026-04-24

## 1. 背景与目标

任何 broker 的同步实现都会遇到"我们没见过的数据"——新的 symbol 类型、未知的字段枚举、未见过的交易动作等。如果这类数据被**静默忽略**，会导致账本缺数据但没人知道；如果**整批失败但不留证据**，会导致下次重试还是同样的失败，没法定位问题。

本文档规定**所有 broker adapter 在遇到异常或未识别数据时的日志和失败分类契约**，目标：

1. **任何失败都可追溯**：看日志 + 看 `broker_sync_batches.error_message` 就能知道"哪一笔数据、什么问题、从哪个 broker 来"
2. **UNKNOWN 不静默**：遇到未识别的 symbol / 字段枚举 / 数据类型时，**整批失败**，绝不"跳过然后忘了"
3. **失败分类统一**：跨 broker 使用同一套失败原因分类，便于后续做失败率统计和运维告警

---

## 2. 失败分类（跨 broker 通用）

所有同步失败归入以下 4 类之一，写入 `broker_sync_batches.error_message` 的**前缀**中：

| 分类 | 前缀 | 含义 | 举例 |
|------|------|------|------|
| **AUTH** | `[AUTH]` | 认证/授权失败 | token 过期、签名错误、账号被 broker 锁定 |
| **NETWORK** | `[NETWORK]` | 网络/对端异常 | 超时、5xx、DNS 失败、对端限流 |
| **UNRECOGNIZED** | `[UNRECOGNIZED]` | 数据无法识别或该 broker adapter 当前版本未实现处理 | 新 symbol 类型、该 adapter 当前版本未支持的 secType、未见过的字段枚举、schema 偏离文档 |
| **INTERNAL** | `[INTERNAL]` | 其他内部错误 | NPE、DB 写入失败、配置缺失 |

**通用简化原则**：adapter **不维护"已识别但不支持"的白名单**。凡是该 adapter 当前版本不映射到合法 `AssetType` 的数据，一律按 `UNRECOGNIZED` 处理。理由：

1. 所有"不能落库"的数据都走同样的 fail-fast 路径，再细分分类不产生行为差异，只会增加维护成本
2. "支持哪些类型"是 **per-broker** 的决策（例如 Tiger/IBKR 已支持 OPTION_CALL/OPTION_PUT，长桥 v0.2 暂时只支持 STOCK），在 framework 层做全局分类会引入错误假设
3. 若某个 broker 后续需要"软降级"（识别但跳过+落 staged 归档），届时再针对该 broker 设计 `UNSUPPORTED` 分类

**`error_message` 格式**：

```
[分类] <人类可读的短描述> | broker=<broker_code> | ext_id=<external_id or N/A> | <key>=<value> ...
```

示例：

```
[UNRECOGNIZED] unknown symbol type | broker=longbridge | ext_id=TR123456 | symbol=XYZ.HK | raw_type=FOO
[UNRECOGNIZED] sec_type not supported by this adapter version | broker=longbridge | ext_id=TR789012 | symbol=TSLA240621C00200000 | raw_type=OPTION
[AUTH] access token expired | broker=longbridge | ext_id=N/A
[NETWORK] upstream timeout after 30s | broker=longbridge | ext_id=N/A | endpoint=/v1/trade/history
```

---

## 3. UNRECOGNIZED 触发条件与处理

### 3.1 触发条件（必须整批 fail）

一旦 adapter 在处理某条数据时遇到以下任一情况，**立即中断批次，走失败路径**：

1. **证券类型无法映射到合法 `AssetType`**：
   - 完全没见过的 raw_type（如 `FOO`）
   - 见过但该 broker adapter 当前版本未实现处理（例如长桥 v0.2 未处理 OPTION；IBKR/Tiger 已处理 OPTION）
   - 判定规则参见 `symbol-classification.md` 和对应 broker 的 design doc
2. **枚举值未见过**：例如 trade 的 `side` 字段出现非 `BUY`/`SELL` 的值
3. **必填字段缺失**：broker 文档说一定有但实际没有的字段
4. **schema 与文档不符**：字段类型不对、嵌套结构变了

### 3.2 处理路径

```
遇到 UNRECOGNIZED 数据
  → log.error(...)，含完整 raw_payload 片段
  → 不再处理剩余数据（不 best-effort 继续）
  → batch 直接进入失败流程（见 import-consistency.md）
  → error_message 前缀 [UNRECOGNIZED]
  → cursor 不推进
```

**为什么不"跳过继续"**：如果跳过，staged 表和后续统计会缺失这条数据，而且"跳过"本身会被人遗忘。让它整批失败才能强制人类介入。

### 3.3 Staged 表的单条 FAILED 与 batch 级 fail-fast

Tiger / IBKR / 长桥等 adapter 使用两阶段模型（拉取 → staging → 落库）。staged 行本身可以先被标为 `FAILED` 用于**单条归因记账**（记下 `error_message`、raw_payload），但 adapter 汇总层**必须**在发现 `failedCount > 0` 时抛出异常或返回 `SyncResult.failure`，触发 `SyncBatchFailureHandler` 清理所有 staged 行和已导入的 `trade_records`。

换言之：**staged FAILED 只在 batch 失败窗口期内用于诊断**，最终整批被清除。这符合框架"绝不部分成功"的 fail-fast 设计意图。

### 3.4 未来演进预留：软降级 `UNSUPPORTED`

当前**所有 adapter 均采用硬失败**（整批 cleanup）。未来若某个 broker 需要"识别但软降级"（例如允许账户里包含 framework 认识但该 adapter 暂未实现处理的类型，staged 留痕不进主表、batch 仍 COMPLETED、cursor 照常推进），可针对该 broker 新增 `UNSUPPORTED` 分类。届时需要：

- 新增 `[UNSUPPORTED]` 前缀
- 该 adapter 汇总层对 `UNSUPPORTED` 单条不触发 fail-fast
- cursor 正常推进
- staged 行永久保留（或独立归档表）

**目前无 adapter 采用此模式**——任何落不了主表的数据统一走 `UNRECOGNIZED`。

---

## 4. 日志规范

### 4.1 日志级别约定

| 级别 | 用途 |
|------|------|
| `ERROR` | 导致批次失败的异常（含 AUTH / NETWORK / UNRECOGNIZED / INTERNAL） |
| `WARN` | 非阻塞异常（如：retry 成功前的中间失败、数据字段偏离预期但能兜底） |
| `INFO` | 批次级别的里程碑（开始、完成、进入 COMPLETED/FAILED） |
| `DEBUG` | 单条数据处理详情（默认关闭，排查时开启） |

### 4.2 ERROR 级别日志的必填字段

任何 `log.error(...)` 必须在结构化 MDC 或 message 中包含：

- `broker_code`：broker 标识
- `batch_id`：`broker_sync_batches.id`
- `account_id`：该次同步的账户（若已知）
- `external_id`：出问题那条数据的 broker 侧 ID（未知则写 `N/A`）
- `category`：失败分类（见第 2 节）
- `raw_payload_snippet`：导致失败的原始数据的关键片段（**脱敏**后的；不超过 500 字符；超过则截断）

### 4.3 raw_payload 脱敏规则

打日志时**必须**脱敏以下字段：

- `access_token` / `refresh_token` / `api_key` / `api_secret` → 替换为 `***`
- `account_number`（完整账号）→ 只保留后 4 位，前面打 `*`
- 任何 PII（姓名、身份证、手机号、邮箱）→ 打 `***`

**完整 raw_payload 不进日志**——它的归档位置是 `<broker>_staged_*` 表的 `raw_payload` 字段（参见 [`data-persistence.md`](./data-persistence.md) 和各 broker 的 `staging-schema.md`）。日志只记关键片段用于现场定位。

---

## 5. Adapter 实现约定

每个 BrokerSyncAdapter 实现**必须**：

1. 定义一个 `<Broker>SyncException extends RuntimeException`，构造函数强制传入 `category`（enum）和 `externalId`
2. 在 adapter 的顶层 try/catch 捕获该异常，转成 `error_message` 写入 batch 并让 `SyncBatchFailureHandler` 走 FAILED 路径
3. 在遇到 UNRECOGNIZED 数据时，**立即抛出**该异常，不继续循环
4. 网络/鉴权异常由 adapter 自己的 retry 机制处理；超过 retry 次数后转成对应分类的异常上抛

**反模式**（禁止）：

- ❌ `catch (Exception e) { log.warn(...); continue; }` —— 静默吞掉异常
- ❌ `if (unknownType) { skip(); }` —— 未识别数据静默跳过
- ❌ `log.error("error: " + e.getMessage())` —— 没有分类前缀、没有 external_id

---

## 6. 运维与观测

### 6.1 查询"最近 24 小时的 UNRECOGNIZED 失败"

```sql
SELECT id, broker_code, account_id, error_message, created_at
FROM broker_sync_batches
WHERE status = 'FAILED'
  AND error_message LIKE '[UNRECOGNIZED]%'
  AND created_at >= now() - interval '24 hours'
ORDER BY created_at DESC;
```

### 6.2 失败率按分类统计

```sql
SELECT
  broker_code,
  substring(error_message from '^\[([A-Z_]+)\]') AS category,
  count(*) AS failure_count
FROM broker_sync_batches
WHERE status = 'FAILED'
  AND created_at >= now() - interval '7 days'
GROUP BY broker_code, category
ORDER BY broker_code, failure_count DESC;
```

### 6.3 UNRECOGNIZED 失败的定位流程

1. 从 `error_message` 提取 `ext_id` 和 `broker_code`
2. 去对应的 `<broker>_staged_*` 表查 `raw_payload`（按 `external_id + external_broker` 查）
3. 分析 raw_payload，判断是 broker 加了新枚举/新字段，还是我们的分类器遗漏
4. 更新 adapter 代码（白名单、分类器、或字段映射）
5. 前端重新触发同一区间的 sync（框架当前无"自动重试失败 batch"入口；失败的 batch 已被清理，重触发是幂等的——见 [`sync-lifecycle.md § 6.2`](./sync-lifecycle.md#62-重跑安全)）

---

## 7. 相关文档

- [`data-persistence.md`](./data-persistence.md)：批次表结构（`broker_sync_batches`）、staged 表语义、`raw_payload` 字段存储规范
- [`import-consistency.md`](./import-consistency.md)：批次失败后的清理路径（`BrokerCleanupStrategy` / `SyncBatchFailureHandler`）
- [`symbol-classification.md`](./symbol-classification.md)：UNRECOGNIZED 中"证券类型无法归类"的详细规则
- [`sync-lifecycle.md`](./sync-lifecycle.md)：批次生命周期、adapter 实现指南、失败路径
