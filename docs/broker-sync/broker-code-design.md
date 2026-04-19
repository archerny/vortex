# Broker Code 关联与同步器注册发现 — 设计文档

> **创建日期**：2026-04-17  
> **最后更新**：2026-04-18  
> **状态**：✅ 已实现（全链路 brokerName→brokerCode 重命名 + BrokerSyncInfo DTO + brokers 表关联 + 前端动态发现）  
> **关联**：[data-persistence-design.md](./data-persistence-design.md) | [import-consistency-design.md](./import-consistency-design.md) | [overall-design.md](./overall-design.md)  
> **前置**：Phase 2 数据库变更已完成（V19-V24 + Entity + Repository）

---

## 一、背景与目标

当前同步模块中，券商的标识方式存在以下问题：

1. **`BrokerSyncAdapter.getBrokerName()` 命名不准确** — 返回的是技术标识符（`"ibkr"`、`"tiger"`），不是给用户看的展示名称，应该叫 `getBrokerCode()`
2. **`broker_sync_batches.broker_name` 列名不准确** — 存储的是技术标识符，列名应该是 `broker_code`
3. **同步器与 `brokers` 表没有关联** — 前端硬编码了券商下拉选项 `[IBKR, Tiger, Futu, Schwab]`，`/brokers` 端点返回的是 `List<String>` 而非带展示信息的结构化数据
4. **前端 `SyncRequest`、`SyncResult` 等 DTO 中的 `brokerName` 字段同样需要改名** — 保持全链路一致

**核心目标**：趁没有历史数据，一步到位将整条链路的 `brokerName` 统一改为 `brokerCode`，并建立同步器 ↔ `brokers` 表的关联关系。

---

## 二、核心概念

### 2.1 同步器注册发现模式

每个 `BrokerSyncAdapter` 实现类就是一个**已注册的同步器**。Spring 自动扫描所有 `@Component` 实现类，`BrokerSyncService` 构造时构建 `Map<brokerCode, adapter>`。

### 2.2 `brokerCode` vs `brokerName` 语义区分

| 字段 | 用途 | 例子 |
|------|------|------|
| `broker_code` | 技术标识符，给代码和 API 用 | `"ibkr"`、`"tiger"` |
| `broker_name` | 展示名称，给用户看 | `"盈透证券"`、`"老虎证券"` |

`brokers` 表中原有的 `broker_name` 字段**不改名**，它就是券商的展示名称，在整个系统中被大量引用（`CashFlow.jsx`、`TradeRecords.jsx`、`PositionSnapshot` 等）。新增的 `broker_code` 是一个独立维度。

### 2.3 同步器 → `brokers` 表关联规则

- 每个 `BrokerSyncAdapter` 通过 `getBrokerCode()` 声明自己的技术标识（如 `"ibkr"`）
- `brokers` 表新增 `broker_code` 字段，有同步适配器的券商填入对应值（如 `broker_code = 'ibkr'`），纯手动录入的券商 `broker_code = NULL`
- **重要规则**：如果某个同步器的 `brokerCode` 在 `brokers` 表中找不到对应记录，**该同步器不会在前端显示**。这意味着必须先在 `brokers` 表中创建券商记录并设置 `broker_code`，同步器才可用

---

## 三、关键设计决策

| # | 决策项 | 结论 | 理由 |
|---|--------|------|------|
| 1 | 接口方法命名 | `getBrokerName()` → `getBrokerCode()` | 返回的是技术标识符，不是展示名称 |
| 2 | 数据库列名 | `broker_sync_batches.broker_name` → `broker_code` | 语义一致性 |
| 3 | DTO 字段命名 | `SyncRequest.brokerName` / `SyncResult.brokerName` → `brokerCode` | 全链路统一 |
| 4 | `brokers` 表关联 | 新增 `broker_code` 列（`VARCHAR(50)`, `UNIQUE WHERE NOT NULL`, 可空） | 建立同步器与 broker 的正式关联 |
| 5 | 前端券商列表来源 | 不再硬编码，改为从 `GET /api/broker-sync/brokers` 获取 | 后端是唯一数据源 |
| 6 | 同步器无 broker 记录时 | 不显示在前端 | 必须先配置 broker 记录，避免用户看到无法使用的同步器 |

---

## 四、数据库变更

### 4.1 `brokers` 表新增 `broker_code` 列

```sql
ALTER TABLE brokers ADD COLUMN broker_code VARCHAR(50);
CREATE UNIQUE INDEX idx_brokers_broker_code ON brokers (broker_code) WHERE broker_code IS NOT NULL;
COMMENT ON COLUMN brokers.broker_code IS 'Technical identifier for sync adapter association (e.g. ibkr, tiger). NULL for manual-only brokers.';
```

- `VARCHAR(50)`，可空
- 部分唯一索引（`WHERE broker_code IS NOT NULL`）：有值时不允许重复，`NULL` 不受约束
- 有同步适配器的券商：`broker_code = 'ibkr'`
- 纯手动的券商：`broker_code = NULL`

### 4.2 `broker_sync_batches` 列改名

```sql
ALTER TABLE broker_sync_batches RENAME COLUMN broker_name TO broker_code;

-- 索引同步更新
ALTER INDEX idx_sync_batches_broker_name RENAME TO idx_sync_batches_broker_code;

-- 注释更新
COMMENT ON COLUMN broker_sync_batches.broker_code IS 'Broker technical identifier (e.g. ibkr, tiger)';
```

### 4.3 Flyway 脚本

两项变更合并为一个迁移脚本（趁没有历史数据）：

| 脚本 | 内容 |
|------|------|
| `V23__add_broker_code_and_rename_batch_broker_name.sql` | `brokers` 新增 `broker_code` 列 + `broker_sync_batches.broker_name` → `broker_code` |

---

## 五、后端代码变更

### 5.1 接口层

| 文件 | 变更 |
|------|------|
| `BrokerSyncAdapter.java` | `getBrokerName()` → `getBrokerCode()` |
| `IbkrSyncAdapter.java` | 实现 `getBrokerCode()` 返回 `"ibkr"` |
| `TigerSyncAdapter.java` | 实现 `getBrokerCode()` 返回 `"tiger"` |

### 5.2 Core DTO/Model

| 文件 | 变更 |
|------|------|
| `SyncRequest.java` | `brokerName` → `brokerCode`（字段、getter/setter、构造函数、toString） |
| `SyncResult.java` | `brokerName` → `brokerCode`（字段、getter/setter、工厂方法、toString） |

### 5.3 Entity

| 文件 | 变更 |
|------|------|
| `Broker.java` | 新增 `brokerCode` 字段（`@Column(name = "broker_code", unique = true, length = 50)`） |
| `BrokerSyncBatch.java` | `brokerName` → `brokerCode`（字段 + `@Column(name = "broker_code")` + getter/setter + toString） |

### 5.4 Repository

| 文件 | 变更 |
|------|------|
| `BrokerRepository.java` | 新增 `findByBrokerCode(String brokerCode)` 方法 |
| `BrokerSyncBatchRepository.java` | `findByBrokerName*` → `findByBrokerCode*`（4 个方法全部改名） |

### 5.5 Service

| 文件 | 变更 |
|------|------|
| `BrokerSyncService.java` | `adapterMap` 的 key 从 `getBrokerName()` 改为 `getBrokerCode()`；`getSupportedBrokers()` 改为 `getSupportedBrokerInfos()`；新方法通过 `brokerCode` 反查 `brokers` 表拿展示信息，**找不到记录的同步器不返回** |
| `BrokerSyncBatchService.java` | 所有 `brokerName` 参数 → `brokerCode` |
| `BrokerService.java` | 新增 `findByBrokerCode(String brokerCode)` 委托方法 |

### 5.6 Controller

| 文件 | 变更 |
|------|------|
| `BrokerSyncController.java` | `/trigger` 端点参数校验从 `getBrokerName()` 改为 `getBrokerCode()`；`/brokers` 端点返回 `List<BrokerSyncInfo>` 而非 `List<String>` |

### 5.7 Executor

| 文件 | 变更 |
|------|------|
| `BrokerSyncAsyncExecutor.java` | 日志中 `request.getBrokerName()` → `request.getBrokerCode()` |

### 5.8 新增 DTO

| 文件 | 说明 |
|------|------|
| `BrokerSyncInfo.java`（**新建**） | 供 `/brokers` 端点返回给前端的 DTO |

```java
public class BrokerSyncInfo {
    private String brokerCode;     // 技术标识，如 "ibkr"
    private String brokerName;     // 展示名称，如 "盈透证券" (from brokers 表)
    private String country;        // 国家/地区 (from brokers 表)
    private Long brokerId;         // brokers 表主键，方便前端关联
}
```

---

## 六、前端变更

| 文件 | 变更 |
|------|------|
| `brokerSyncApi.js` | 注释中 `brokerName` → `brokerCode`；`triggerSync` 的 `data.brokerName` → `data.brokerCode` |
| `SyncManagement.jsx` | 去掉硬编码的券商下拉选项 `[IBKR, Tiger, Futu, Schwab]`，改为调用 `/brokers` 端点获取 `BrokerSyncInfo` 列表渲染下拉框；`filters.brokerName` → `filters.brokerCode`；提交 payload 的 `brokerName` → `brokerCode`；表格列 `dataIndex: 'brokerName'` → `'brokerCode'`（或通过 brokerCode 反查展示名称） |

---

## 七、数据流（重新设计后）

```
Spring 启动
  → 自动扫描所有 BrokerSyncAdapter 实现
  → BrokerSyncService 构建 Map<brokerCode, adapter>
     key = adapter.getBrokerCode()  // "ibkr", "tiger" ...

GET /api/broker-sync/brokers 时：
  → 遍历所有 adapter.getBrokerCode()
  → 对每个 code，通过 brokerRepository.findByBrokerCode(code)
  → 找不到记录 → 跳过（不返回给前端）
  → 找到记录 → 组装 BrokerSyncInfo { brokerCode, brokerName(展示名), country, brokerId }
  → 返回 List<BrokerSyncInfo> 给前端

前端渲染同步器列表：
  → 下拉框 label = brokerInfo.brokerName（如 "盈透证券"）
  → 下拉框 value = brokerInfo.brokerCode（如 "ibkr"）

用户发起同步：
  → POST /api/broker-sync/trigger { brokerCode: "ibkr", startTime, endTime }
  → Controller 从 Map<brokerCode, adapter> 路由到 IbkrSyncAdapter
  → 创建 BrokerSyncBatch (broker_code = "ibkr")
  → 异步执行同步...
```

---

## 八、同步器注册但无 broker 记录的处理

| 场景 | 行为 | 说明 |
|------|------|------|
| adapter 注册了 `getBrokerCode() = "ibkr"`，`brokers` 表有 `broker_code = 'ibkr'` 的记录 | ✅ 正常显示在前端同步器列表中 | 标准场景 |
| adapter 注册了 `getBrokerCode() = "schwab"`，`brokers` 表没有对应记录 | ❌ 不返回给前端 | 用户需要先在 Broker 管理中创建券商记录并关联 `broker_code` |
| `brokers` 表有 `broker_code = NULL` 的记录（纯手动券商） | — 不影响同步模块 | 没有 adapter 与之关联，同步模块不涉及 |

> **为什么不做降级显示？** 同步器能否使用取决于 broker 配置是否完整（包括 API 凭证、broker 记录等）。如果 broker 记录都没创建，说明配置还不完整，显示出来会给用户造成困惑。

---

## 九、与现有文档的关系

本文档的变更需要同步更新以下文档：

| 文档 | 更新内容 |
|------|---------|
| [data-persistence-design.md](./data-persistence-design.md) | `broker_sync_batches` 表结构中 `broker_name` → `broker_code`；索引名更新；「待后续讨论」中的 `brokerId 查找策略` 标记为已解决 |
| [import-consistency-design.md](./import-consistency-design.md) | 所有代码示例和文字描述中的 `brokerName` → `brokerCode` |
| [README.md](./README.md) | 文档索引新增本文档引用 |
