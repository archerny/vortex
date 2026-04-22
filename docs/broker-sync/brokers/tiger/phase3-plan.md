# Tiger Phase 3 — 编码与工作计划

> **创建日期**：2026-04-21
> **最近更新**：2026-04-22
> **状态**：🔧 进行中 — 阶段 1、2 已完成
> **目标**：将老虎证券同步从 Phase 1（API → 日志）升级为与 IBKR 对齐的两阶段导入（API → `tiger_staged_orders` → `trade_records`）
> **关联**：[staging-schema.md](./staging-schema.md) | [open-api.md](./open-api.md) | [../../framework/data-persistence.md](../../framework/data-persistence.md) | [../../framework/import-consistency.md](../../framework/import-consistency.md) | [../ibkr/flex-web-service.md](../ibkr/flex-web-service.md)

---

## 一、背景与确认的决策

本计划基于以下**已确认**的设计选择（2026-04-21 与用户对齐）：

| # | 决策点 | 选择 |
|---|--------|------|
| 1 | 是否做 Phase 3（暂存 + 导入 `trade_records`） | ✅ 做 |
| 2 | `attrDesc` 期权事件映射 | **先留空**：`attrDesc` 非空的订单统一 `FAILED`，保留原值，后续收集样本再补 |
| 3 | 碎股（`quantityScale > 0`） | **本期不支持**：暂存为 `FAILED` |
| 4 | `WAR` / `IOPT` / `FUT` / `FUND` | **本期不支持**：暂存为 `FAILED` |
| 5 | 文档路径 | `docs/broker-sync/brokers/tiger/staging-schema.md` + 本文件 |
| 6 | 单元测试 | **需要**：至少覆盖字段映射的纯函数逻辑 |

完整数据契约见 [staging-schema.md](./staging-schema.md)。

---

## 二、复用与改动总览

### 2.1 零改动（框架层）

- `BrokerSyncAdapter` 接口
- `BrokerSyncController` / `BrokerSyncService`（控制器与编排）
- `broker_sync_batches` 通用批次表
- `BrokerSyncBatchService`（批次管理：状态机、phase、Resume 条件）
- `SyncBatchRecoveryRunner`（应用启动恢复）
- `trade_records` 扩展字段（`external_id` / `external_broker` / `sync_batch_id`）
- 前端动态券商列表（`/api/broker-sync/brokers`）

### 2.2 新增文件

| 路径 | 对标 IBKR | 说明 |
|------|-----------|------|
| `backend/src/main/resources/db/migration/V26__create_tiger_staged_orders.sql` | V20 | 建表 + 索引 + FK |
| `backend/src/main/resources/db/migration/V27__seed_tiger_broker_code.sql` | — | 幂等设置 `brokers.broker_code='tiger'` |
| `com/vortex/entity/TigerStagedOrder.java` | `IbkrStagedOrder` | JPA Entity |
| `com/vortex/repository/TigerStagedOrderRepository.java` | `IbkrStagedOrderRepository` | Repository |
| `com/vortex/sync/adapter/tiger/TigerStagingService.java` | `IbkrStagingService` | 暂存阶段编排 |
| `com/vortex/sync/adapter/tiger/TigerStagingWorker.java` | `IbkrStagingWorker` | `@Transactional(REQUIRES_NEW)` 逐条暂存 |
| `com/vortex/sync/adapter/tiger/TigerImportService.java` | `IbkrImportService` | 导入阶段编排 |
| `com/vortex/sync/adapter/tiger/TigerImportWorker.java` | `IbkrImportWorker` | `@Transactional(REQUIRES_NEW)` 逐条导入 + 字段映射 |
| `com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java` | （IBKR 未单独抽出） | **独立可测试的纯转换类**，承载 5.2/5.3/5.4 映射逻辑；单测主要打在这里 |
| `backend/src/test/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapperTest.java` | — | 单元测试（纯 POJO，无 Spring 上下文） |
| `backend/src/test/java/com/vortex/sync/adapter/tiger/TigerImportFilterTest.java` | — | 前置过滤规则（§ 5.1）单测 |

### 2.3 修改现有文件

| 文件 | 改动 |
|------|------|
| `com/vortex/sync/adapter/tiger/TigerOrderRecord.java` | **新增 `attrDesc` 字段** + getter/setter，并在 `toString()` 中输出 |
| `com/vortex/sync/adapter/tiger/TigerSyncAdapter.java` | `sync()` 流程改造：取数完成后调用 staging → import，从 DB 重新统计 `totalRecords`；注入 `BrokerSyncBatchService` / `TigerStagingService` / `TigerImportService` / `TigerStagedOrderRepository`；在 `convertToRecord()` 中补取 `order.getAttrDesc()` |

### 2.4 不新建的内容

- ❌ `tiger_staged_trade_confirms`（Tiger 无 TradeConfirm 粒度数据）
- ❌ Tiger 专属 Resume 逻辑（复用框架通用 Resume 即可）

---

## 三、阶段划分（线性依赖）

### 阶段 1：DB 迁移 + Entity + Repository ✅ 已完成（2026-04-22）

**目标**：数据库骨架就绪，可在后续阶段中读写。

- [x] 编写 `V26__create_tiger_staged_orders.sql`
  - 字段顺序、长度、约束严格按 [staging-schema.md § 3](./staging-schema.md#三tiger_staged_orders--tiger-核心暂存表order-粒度)
  - 创建 `UNIQUE(tiger_id)`、`INDEX(batch_id)`、`INDEX(status)`、FK→`broker_sync_batches.id`
- [x] 编写 `V27__seed_tiger_broker_code.sql`
  - 幂等：仅当 `brokers` 表中存在 Tiger 记录且 `broker_code` 为 NULL 或不等于 `tiger` 时更新
  - 若未来初始化脚本已包含 Tiger 行，此脚本可为 no-op
- [x] 新建 `TigerStagedOrder` Entity（字段与列名 1:1，纯 `String` 存储业务字段，保留 `@PrePersist` / `@PreUpdate` 审计时间戳 via `BaseEntity`）
- [x] 新建 `TigerStagedOrderRepository`，包含：
  - `Optional<TigerStagedOrder> findByTigerId(String tigerId)`
  - `boolean existsByTigerId(String tigerId)`
  - `List<TigerStagedOrder> findByBatchId(Long batchId)`
  - `List<TigerStagedOrder> findByBatchIdAndStatus(Long batchId, String status)`
  - `long countByBatchIdAndStatus(Long batchId, String status)`
- [x] 编译通过（`mvn -q -DskipTests compile` 本地验证通过，2026-04-22）
- [ ] 启动本地环境验证 Flyway 正常执行，表结构正确（延后到阶段 5 端到端验证时一并完成）

**产出**：编译通过 ✅；Entity 与 Repository 就绪；Flyway 迁移就位等待首次启动执行。

---

### 阶段 2：`TigerOrderRecord.attrDesc` 扩展 + Staging Worker ✅ 已完成（2026-04-22）

**目标**：暂存写入链路跑通，API → 暂存表。

- [x] `TigerOrderRecord.java` 新增 `private String attrDesc;` + getter/setter + `toString()` 输出（仅在非空时输出，避免日志噪音）
- [x] `TigerSyncAdapter.convertToRecord()` 中补 `record.setAttrDesc(order.getAttrDesc())`（T-7 已在 Stage 1 期间通过 `javap` 确认 SDK 2.4.7 有 `getAttrDesc()`）
- [x] 新建 `TigerStagingWorker`
  - `@Transactional(propagation = REQUIRES_NEW)`
  - `stageOrder(Long batchId, TigerOrderRecord record)`：
    1. 先 `existsByTigerId`，若存在则返回 `false`（幂等）
    2. 否则 `mapToStagedOrder(...)`，`status=PENDING`，`save`，返回 `true`
  - 时间字段统一用 `yyyy-MM-dd'T'HH:mm:ss`（Asia/Shanghai）格式化；BigDecimal 用 `toPlainString()` 避免科学计数法
- [x] 新建 `TigerStagingService`
  - 入参：`Long batchId, List<TigerOrderRecord> records`
  - 对每条调用 `stagingWorker.stageOrder(...)`，**单条失败不影响其他**（try/catch 汇总）
  - 返回 `StagingResult { attempted, inserted, skipped, failed }`（`attempted == inserted + skipped + failed` 不变量）
  - **不负责** API 调用，纯处理内存数据 → DB
- [x] 编译通过（`mvn -q -DskipTests compile` 本地验证通过，2026-04-22）
- [ ] 单元测试（`TigerStagingWorkerTest`）：本阶段未写；计划中本身将其标为"可选"，实际可放在 Stage 3 的单测批次里或延后

**产出**：Stage 2 代码就绪；等阶段 3 的 Mapper 单测完成后，再在集成/冒烟测试中验证"mock 一组 `TigerOrderRecord` → 正确写入 `tiger_staged_orders` 且幂等"。

---

### 阶段 3：`TigerTradeRecordMapper` + 单元测试（核心）

**目标**：纯函数的字段映射逻辑完备且被测试覆盖。

- [ ] 新建 `TigerTradeRecordMapper`
  - 方法 1：`FilterResult preFilter(TigerStagedOrder s, Optional<TradeRecord> existing)` — 返回 `PASS` / `SKIPPED` / `FAILED(msg)`，覆盖 [§ 5.1](./staging-schema.md#51-前置过滤不映射直接-failed-或-skipped) 七条规则
  - 方法 2：`TradeRecord toTradeRecord(TigerStagedOrder s, Long brokerId, Long batchId)` — 覆盖 5.2 / 5.3
  - 方法 3：`String buildOptionSymbol(String underlying, String expiry, String putCall, String strike)` — 独立可测
  - 方法 4：`Currency mapCurrency(String raw)` — `USD/HKD/CNH→CNY`，其他抛 `IllegalArgumentException`
- [ ] 单测文件 `TigerTradeRecordMapperTest`
  - 覆盖用例（≥ 15 个）：
    1. STK BUY / USD / 100 股 / 手续费 → 正常映射
    2. STK SELL / HKD / 200 股（港股 5 位代码）
    3. STK / CNH → 系统 CNY
    4. OPT CALL → `AAPL-20260130-C265`，`asset_type=OPTION_CALL`，`amount = qty × price × multiplier`
    5. OPT PUT + strike 含小数（`17.50` → `17.5`）
    6. `filledQuantity=0` → `SKIPPED`（未实际成交）
    7. `secType=WAR` → `FAILED("Unsupported secType: WAR")`
    8. `secType=FUT` / `FUND` 同上
    9. `quantityScale=2` → `FAILED("Fractional share not supported...")`
    10. `attrDesc="Exercise"` → `FAILED("Option event attrDesc=Exercise — mapping TBD")`
    11. `attrDesc=""`（空字符串）→ 视为空，`PASS`
    12. `action="CANCEL"` → `FAILED("Unsupported action: CANCEL")`
    13. OPT 缺 `putCall` → `FAILED`
    14. `fee` 计算：`commission=-1.50, gst=null` → `1.50`
    15. 已存在 `trade_records`（同 `external_id`）→ `SKIPPED`
  - `amount` 浮点计算用 `BigDecimal.compareTo`，不用 `equals`

**产出**：单测全绿，字段映射逻辑锁定（后续 `ImportWorker` 只做 DB 交互）。

---

### 阶段 4：`TigerImportWorker` + `TigerImportService`

**目标**：从暂存表读取 → 调用 `Mapper` → 写入 `trade_records`，并回写暂存状态。

- [ ] 新建 `TigerImportWorker`
  - `@Transactional(propagation = REQUIRES_NEW)`
  - `importOne(Long batchId, Long stagedId, Long brokerId)`:
    1. `stagedOrderRepository.findById(stagedId)` → 进入 `PENDING` 处理
    2. `tradeRecordRepository.findByExternalBrokerAndExternalId("tiger", tigerId)` → `Optional`
    3. 调 `mapper.preFilter(...)`：
       - `SKIPPED` / `FAILED`：直接回写暂存状态，返回
       - `PASS`：继续
    4. `TradeRecord tr = mapper.toTradeRecord(staged, brokerId, batchId)`
    5. `tradeRecordRepository.save(tr)`
    6. 回写 `staged.status=IMPORTED, imported_trade_id=tr.id`
    7. 异常：`staged.status=FAILED, error_message=e.getMessage()`
- [ ] 新建 `TigerImportService`
  - 入参：`Long batchId`
  - 1. `brokerId = brokersRepository.findByBrokerCode("tiger").getId()`（批次内只查一次）
  - 2. `List<Long> pendingIds = stagedOrderRepository.findByBatchIdAndStatus(batchId, "PENDING").stream().map(...)`
  - 3. 逐个调用 `importWorker.importOne(batchId, stagedId, brokerId)`
  - 4. 返回 `ImportResult { attempted, imported, skipped, failed }`（从 DB 重新统计以防漏单）

**产出**：端到端：`batch_id` → 暂存表若干条 `PENDING` → 运行 `TigerImportService` → 所有记录进入 `IMPORTED` / `SKIPPED` / `FAILED`，`trade_records` 中出现对应数据。

---

### 阶段 5：`TigerSyncAdapter` 流程改造（两阶段接入）

**目标**：`POST /api/broker-sync/trigger` 从入口到落库全链路跑通。

参照 `IbkrSyncAdapter.sync()` 骨架：

```
1. 前置检查（配置 / TigerApiProperties.isConfigured）
2. 创建批次：batchService.createAndStart(brokerCode="tiger", startDate, endDate)
3. 进入 phase=FETCHING：
   - 调用 Tiger API（现有 fetchOrdersInWindows） → List<TigerOrderRecord>
   - ⚠️ 确认分页：get_filled_orders 是否自动翻页
     · 方案 A：SDK 自动翻页 → 无需改
     · 方案 B：需要手动循环 pageToken → 在 fetchFilledOrders 里加 while(pageToken != null)
4. 进入 phase=STAGING：
   - TigerStagingService.stageAll(batchId, records)
5. 进入 phase=IMPORTING：
   - TigerImportService.importAll(batchId)
6. 进入 phase=COMPLETED：
   - 从 DB 重新统计 totalRecords（只算 IMPORTED）
   - batchService.markSuccess(batchId, imported, skipped, failed)
7. 异常：batchService.markFailed(batchId, errorMessage)；phase 停在失败处
8. 返回 SyncResult（注意：异步执行，Controller 直接返回 batchId）
```

- [ ] 注入 `BrokerSyncBatchService`、`TigerStagingService`、`TigerImportService`、`TigerStagedOrderRepository`
- [ ] `convertToRecord()` 补 `attrDesc`（阶段 2 已做，此处只是确认）
- [ ] 确认分页行为（必要时加 `pageToken` 循环）
- [ ] 移除或保留原先的"日志逐条打印"：默认保留在 DEBUG 级别，便于排障
- [ ] 验证 Resume：启动时 `SyncBatchRecoveryRunner` 能接续未完成批次

**产出**：端到端可跑；Postman/curl 触发后，`broker_sync_batches` / `tiger_staged_orders` / `trade_records` 三张表联动正确。

---

### 阶段 6：`attrDesc` 实证与期权事件映射补齐（待真实数据到手后再做）

> **本阶段在 Phase 3 主干合入后异步进行**，不阻塞 Phase 3 发布。

- [ ] 从生产/真实 Tiger 环境抓取若干期权事件订单（行权、被指派、到期作废）
- [ ] 从 `tiger_staged_orders` 中筛选 `status=FAILED AND error_message LIKE '%Option event attrDesc%'`，汇总 `attr_desc` 实际取值
- [ ] 编写 `attrDesc → TriggerRefType` 映射表，更新 [staging-schema.md § 5.1 规则 5](./staging-schema.md#51-前置过滤不映射直接-failed-或-skipped) 和 `TigerTradeRecordMapper`
- [ ] 补充 STK 侧 `trigger_ref_id` 回填（如 Put 被行权得到股票），参考 `IbkrImportService.backfillStockSideTriggerRefId`
- [ ] 补齐单测用例
- [ ] 新建独立的小版本发布（不放进 Phase 3 主干）

---

### 阶段 7：文档同步（贯穿始终，合入前必须完成）

严格执行项目 **Documentation Sync — Zero Debt Policy**。每次提交都必须同步更新相关文档，而不是留到最后。

提交前 checklist：

- [ ] `docs/broker-sync/brokers/tiger/staging-schema.md` — 随实现调整（如字段长度实际用了 VARCHAR(50) 等）
- [ ] `docs/broker-sync/brokers/tiger/phase3-plan.md`（本文件）— 勾选已完成阶段，状态行更新
- [ ] `docs/broker-sync/brokers/tiger/README.md` — 能力表 Phase 3 从 📋 改 ✅，移除"Phase 3 规划项"遗留条目
- [ ] `docs/broker-sync/brokers/tiger/open-api.md` — "后续待办"中已完成项打勾；追加 "Phase 3 实现记录" 章节
- [ ] `docs/broker-sync/README.md` — 文档索引状态、分期计划的 Phase 3 状态
- [ ] `docs/broker-sync/framework/data-persistence.md` — `trade_records` 字段映射章节加 Tiger 行；D-6 从"可后续再说"移到"已解决"

---

## 四、单元测试策略

### 4.1 必测点

| 测试类 | 目标 | 覆盖范围 |
|--------|------|---------|
| `TigerTradeRecordMapperTest` | 字段映射正确性 | § 三 阶段 3 列出的 15 个用例 |
| `TigerImportFilterTest` | 前置过滤规则全覆盖 | 7 条规则 × 正/反各一 |
| `TigerStagingWorkerTest`（可选） | 幂等性 | 同一 `tigerId` 重复 stage 一次 vs 两次 |

### 4.2 框架与依赖

- JUnit 5 + AssertJ（项目已有）
- `TigerTradeRecordMapper` 设计为**无 Spring 依赖的纯类**，测试不需要 `@SpringBootTest`
- `TigerStagingWorker` / `TigerImportWorker` 涉及 `@Transactional`，单测用 `@DataJpaTest` 或 Mockito mock Repository

### 4.3 不测的内容（风险可控）

- Tiger SDK 调用层（需要真实凭证，放到手动冒烟测试）
- `BrokerSyncBatchService`（框架层已在 IBKR 上被充分验证）

---

## 五、风险与回滚

| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| `TigerOrderRecord.attrDesc` 对应的 SDK getter 不存在 | 阶段 2 编译失败 | 先看 SDK 源码/反射确认；若 SDK 2.4.7 无此字段，升级 SDK 或通过 `Order` 对象的 `attr` / `orderDesc` 等字段变通 |
| 分页未处理导致漏数据 | 导入不完整 | 阶段 5 必须验证 SDK 是否自动翻页；增加"单次返回条数 == limit 时记 warn 日志"的断言 |
| HK 股代码格式不一致导致 `symbol` 与历史数据对不上 | 重复记录 | 首次真实同步后人工对账；必要时在 Mapper 中加 `normalizeHkSymbol()` |
| 碎股订单被拒后用户感知差 | UX 不友好 | `error_message` 写清楚"真实数量=X.XX"，用户可手动补录；前端可在失败列表展示 |

**回滚策略**：

- 阶段 1~4 的改动都是新增文件（除 `TigerOrderRecord.attrDesc`），可以单独 revert
- 阶段 5 修改 `TigerSyncAdapter.sync()` 主流程，若需回滚，保留"只走日志"的旧代码路径作为降级开关（如 `broker.tiger.dry-run=true` 时走 Phase 1 行为）—— **不强制，看实施时的紧迫度**

---

## 六、工作量估算

| 阶段 | 粗估 | 备注 |
|------|------|------|
| 阶段 1：DB + Entity + Repo | 1-2 小时 | 照抄 IBKR 模板 |
| 阶段 2：attrDesc + Staging | 2-3 小时 | SDK 确认可能耗时 |
| 阶段 3：Mapper + 单测 | 3-4 小时 | 核心逻辑，加 15 个单测 |
| 阶段 4：ImportWorker/Service | 2-3 小时 | 照抄 IBKR 骨架 |
| 阶段 5：Adapter 集成 + 分页确认 | 2-3 小时 | 分页行为验证可能需调研 |
| 阶段 7：文档同步 | 1 小时 | 贯穿各阶段，最终收尾 |
| **合计** | **≈ 11-16 小时** | 不含真实环境冒烟 |

阶段 6（`attrDesc` 实证）另计，需等真实样本。

---

## 七、提交粒度建议

按 Conventional Commits 拆分，**每阶段一条提交**，便于回滚与代码评审：

| 提交 | 类型 | 建议 message |
|------|------|-------------|
| 1 | feat(backend) | `feat(backend): create tiger_staged_orders table and seed broker_code` |
| 2 | feat(backend) | `feat(backend): add attrDesc to TigerOrderRecord and stage tiger orders to DB` |
| 3 | feat(backend) | `feat(backend): add TigerTradeRecordMapper with unit tests` |
| 4 | feat(backend) | `feat(backend): import tiger staged orders into trade_records` |
| 5 | feat(backend) | `feat(backend): wire TigerSyncAdapter with staging and import services` |
| 6 | docs | `docs(broker-sync): mark tiger phase 3 complete in status pages` |

**每次提交前必须：**
1. 单测跑通
2. 相关文档已同步
3. 等待用户确认后再 `git commit` + `git push`（按项目规则"Git Commit Approval"）

---

## 八、已知限制清单（Phase 3 交付后的对外说明）

> 发布时同步更新到 [README.md](./README.md) 与 [open-api.md](./open-api.md)。

- ✅ 支持：STK（美股/港股/A 股连通）、OPT（CALL/PUT 正常交易）
- ❌ 本期不支持：
  - 碎股（`quantityScale > 0`）
  - 期权事件订单（`Exercise` / `Assignment` / `Expired` 等，`attrDesc` 非空） — 等待样本补充映射
  - `WAR` / `IOPT` / `FUT` / `FUND` / `CASH` / `CC` 资产类型
- 上述所有不支持场景会**暂存并标记 `FAILED`**，数据不丢失，`error_message` 中保留原因

---

## 九、开放待确认项（实施期间即时处理）

> 实施过程中若出现无法绕过的阻塞，先在此登记，再联系用户确认；否则按默认策略推进。

| 代号 | 待确认 | 默认策略（无回应时） |
|------|--------|--------------------|
| T-4 | SDK 是否自动分页？ | 阶段 5 用真实账号跑一次 ≥ 300 条订单的窗口验证 |
| T-7 | ~~`TradeOrder.getAttrDesc()` getter 是否存在？~~ | ✅ 2026-04-22 已通过 `javap` 在 SDK 2.4.7 本地 jar 中确认 `attrDesc` 字段及其 getter/setter 均存在，无需降级方案 |
| T-8 | `brokers` 表是否已存在 Tiger 行？ | V27 设计为幂等 UPDATE，两种情况都兼容 |
