# 富途证券同步

> **状态**：📋 设计稿 v0.1（未实现，待 review 与决策确认后落地）
> **适配器（规划）**：`FutuSyncAdapter`
> **API 类型**：Futu OpenAPI（OpenD 本地网关 + Protobuf over TCP，官方 Java SDK）
> **Broker Code**：`futu`
> **账户前提**：一个牛牛号可绑定多个「综合账户」（证券 / 期货），每个综合账户下按 `trdMarket` 分别查询

---

## 文档定位

这是一份**设计阶段**文档，尚未编码。目的：
1. 对齐与 IBKR / Tiger 的架构一致性
2. 把 Futu 的**独特差异**（OpenD 本地网关、回调式 SDK、30s/10 次限频、仅实盘、账户×市场维度展开）显式记录下来
3. 列出 **Open questions**，在编码前敲定

所有引用的 Futu 协议字段都来自仓库内 `external-resource/futu-api/docs/` 下的官方文档快照。

---

## 1. Futu 与 IBKR / Tiger 的关键差异

| 维度 | IBKR | Tiger | **Futu** |
|---|---|---|---|
| 接入方式 | 云端 Flex Web Service | 云端 OpenAPI（HTTPS + RSA 签名） | **本地 OpenD 网关（TCP/SSL，默认 11111）** |
| 传输协议 | HTTPS / XML | HTTPS / JSON | **Protobuf over TCP** |
| SDK 调用模型 | 同步 REST | 同步 REST | **回调式（`FTSPI_Trd#onReply_*`）→ 需包成 `CompletableFuture`** |
| 历史成交接口 | Flex Statement | `get_filled_orders` | **`Trd_GetHistoryOrderFillList` (2222)** |
| 交易前置动作 | 无 | 无 | **必须先 `Trd_UnlockTrade`（交易密码 MD5）** |
| 时间窗口限制 | 365 天 | 90 天 | **90 天（需拆分）** |
| 接口限频 | 宽松 | 宽松 | **30s 内同一协议最多 10 次** |
| 账户模型 | 单账户 / 子账户 | 单账户 | **牛牛号 → N 个综合账户，每账户再按 `trdMarket`（HK/US/CN/SG/JP…）分别查询** |
| 历史数据可用性 | 实盘 + 模拟 | 实盘 | **仅实盘，模拟环境无 fill 历史** |

### 1.1 OpenD 是新的变量

Futu 的后端**不直连富途云端**，而是连到用户本机 / 局域网运行的 **OpenD 进程**，由 OpenD 代为登录富途并与云端通信。影响：

- 凭证配置项换成 **OpenD 地址/端口 + 交易解锁密码 MD5**，不再是 API Key / Secret
- 用户未启动 OpenD → 同步必须 fail-fast 并给出清晰错误（而不是连接超时）
- OpenD 的版本、登录状态、市场订阅权限，都是同步前置条件

---

## 2. 接入架构

### 2.1 包结构（沿用 IBKR / Tiger 骨架）

```
backend/src/main/java/com/vortex/sync/adapter/futu/
├── FutuSyncAdapter.java              # BrokerSyncAdapter 实现
├── FutuOpenDClient.java              # OpenD 连接 / 解锁 / 请求封装（回调 → CompletableFuture）
├── FutuCredentials.java              # host / port / unlockPwdMd5 / accFilter / trdMarkets
├── FutuOrderFill.java                # 历史成交原始模型（1:1 对应 Protobuf）
├── FutuHistoryOrder.java             # 历史订单原始模型（补字段用）
├── FutuAccount.java                  # 综合账户 + 市场权限
├── FutuRawFillParser.java            # Protobuf → FutuOrderFill
├── FutuStagedFillRepository.java     # staged 表读写
├── FutuTradeRecordMapper.java        # staged → trade_records 的字段映射
└── FutuCleanupStrategy.java          # ★ BrokerCleanupStrategy 实现：清理 futu_staged_order_fills（framework 硬约束，缺则启动失败）
```

> **framework 约束**：每个 broker adapter 必须配套提供 `BrokerCleanupStrategy` 实现并注册为 `@Component`，`SyncBatchCleanupService` 在 `@PostConstruct` 阶段会做 coverage check，缺失任何一个 adapter 的 strategy 会导致应用启动失败。详见 [`framework/import-consistency.md` § 5.3](../../framework/import-consistency.md#53-清理事务设计)。

### 2.2 OpenD 调用模型

Futu SDK 是**回调式**（`FTSPI_Trd#onReply_*`），需在 `FutuOpenDClient` 做一层薄封装成**同步式 / `CompletableFuture`**，才能嵌入现有同步流水线：

```
FutuOpenDClient.getHistoryOrderFillList(accId, trdMarket, beginTime, endTime)
  └─ TrdGetHistoryOrderFillList.C2S.Builder → sendRequest(seqNo)
  └─ onReply_GetHistoryOrderFillList(seqNo, ret, msg) → future.complete(...)
  └─ 返回 CompletableFuture<List<FutuOrderFill>>
```

单个 `FTAPI_Conn_Trd` 实例在一次同步任务内复用（登录 + 解锁成本较高），任务结束统一 `close()`。

---

## 3. 数据源选型

### 3.1 核心接口

**`Trd_GetHistoryOrderFillList` (2222, 历史成交列表)** ← **作为 `trade_records` 的唯一真源**

关键字段（引自 `external-resource/futu-api/docs/trade_get-history-order-fill-list.md`）：

| 字段 | 说明 | 在我们这边的角色 |
|---|---|---|
| `fillID` / `fillIDEx` | 成交编号 / 全局唯一成交编号 | **`fillIDEx` → `broker_ref`**（dedup key） |
| `orderID` | 关联订单 | 关联 HistoryOrderList 查 currency |
| `code` / `name` | 标的代码 / 名称 | → `trade_records.symbol` |
| `qty` / `price` | 成交数量 / 价格 | → quantity / price |
| `createTime` | 成交时间 | → `trade_time` |
| `counterBrokerID` / `counterBrokerName` | 对手方 | 保留在 staged，暂不进 trade_records |
| `trdSide` | BUY / SELL | → `direction` |
| `trdMarket` / `secMarket` | 市场 | → `market` |
| `status` | 成交状态 | 过滤非正常状态用 |

### 3.2 辅助接口

**`Trd_GetHistoryOrderList` (2221, 历史订单列表)** ← 补 fill 接口**缺少**的字段：
- `currency` ← 计价货币（fill 接口没有，但 `trade_records.currency` 需要）
- `fillAvgPrice` / `fillQty` ← 交叉校验
- `session` ← 盘前 / 盘中 / 盘后（未来用）

**`Trd_GetAccList` (2001, 账户列表)** 只在**初次导入**或**凭证变更**时调用一次，缓存综合账户列表 + 每账户的 trdMarket 权限到 broker 配置里；后续增量同步按缓存遍历。

### 3.3 不采用的接口

| 接口 | 不采用理由 |
|---|---|
| `Trd_GetOrderFillList` (2212, 当日成交) | 只做历史数据同步，不做实时 |
| `Trd_SubAccPush` / `Trd_UpdateOrderFill` | 推送式不符合批次同步语义 |
| `Trd_GetOrderFee` (2225, 订单费用) | 对齐 IBKR / Tiger —— 当前不导入手续费字段 |

---

## 4. 数据模型

### 4.1 Staged 表：`futu_staged_order_fills`

> **⚠️ 当前 DDL 已知问题（v0.2 必须修）**：下方 SQL 是 v0.1 早期草稿，与项目实际约定有以下偏差，编码前必须重写：
> 1. **方言错误**：项目用 PostgreSQL（参见 V19–V28 Flyway），但下方 DDL 是 MySQL 方言（`AUTO_INCREMENT` / `TINYINT` / `DATETIME` / `JSON` / `KEY ...` / `ENGINE=InnoDB`）—— 长桥 v0.2.1 已修过同款问题
> 2. **缺 v2 状态字段**：未包含 `status` / `imported_trade_id` / `error_message` / `updated_at`（对照 [`tiger/staging-schema.md § 3.1`](../tiger/staging-schema.md) / [`ibkr/staging-schema.md`](../ibkr/staging-schema.md)）
> 3. **raw 字段类型与 staging 框架冲突**：使用了 `DECIMAL(20,8)` / `DATETIME` / `TINYINT` 等强类型，与 framework 约定的 "staged 表 raw 字段一律 `VARCHAR` 保持数据无损" 原则冲突（见 [`framework/data-persistence.md`](../../framework/data-persistence.md)）
>
> v0.2 review 时务必参照 IBKR / Tiger 现有 staging-schema 文档重写 DDL。

沿用"每券商一张 staged 表，1:1 对应原始接口字段"约定：

```sql
CREATE TABLE futu_staged_order_fills (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    batch_id        VARCHAR(64)    NOT NULL,
    -- Raw fields (1:1 from GetHistoryOrderFillList)
    fill_id_ex      VARCHAR(64)    NOT NULL,          -- 全局唯一标识
    fill_id         VARCHAR(64),                      -- 原始 fillID
    order_id        VARCHAR(64)    NOT NULL,
    code            VARCHAR(32)    NOT NULL,
    name            VARCHAR(128),
    trd_side        TINYINT        NOT NULL,          -- 1=BUY, 2=SELL
    qty             DECIMAL(20, 8) NOT NULL,
    price           DECIMAL(20, 8) NOT NULL,
    create_time     DATETIME       NOT NULL,
    counter_broker_id    INT,
    counter_broker_name  VARCHAR(128),
    trd_market      TINYINT        NOT NULL,          -- 1=HK, 2=US, ...
    sec_market      TINYINT,
    status          TINYINT,
    -- Enriched from GetHistoryOrderList (见 § 3.2)
    currency        VARCHAR(8),
    order_session   TINYINT,
    -- Sync metadata
    acc_id          BIGINT         NOT NULL,          -- 富途综合账户 ID
    trd_env         TINYINT        NOT NULL,          -- 0=SIMULATE, 1=REAL
    raw_payload     JSON,                             -- 完整 Protobuf JSON 备份（排错用）
    created_at      DATETIME       NOT NULL,
    UNIQUE KEY uk_batch_fill (batch_id, fill_id_ex),
    KEY idx_batch (batch_id),
    KEY idx_order_fill_id_ex (fill_id_ex)
) ENGINE=InnoDB;
```

### 4.2 字段映射：`futu_staged_order_fills` → `trade_records`

| `trade_records` 字段 | 来源 | 规则 |
|---|---|---|
| `broker_id` | 配置 | Futu 的 broker 记录 ID |
| `broker_ref` | `fill_id_ex` | 全局唯一，天然 dedup key |
| `trade_time` | `create_time` | 直接用（OpenD 返回 UTC+8） |
| `symbol` | `code` | HK 股票需做 `00700.HK` → `0700` 归一化（复用 Tiger 现有 `SymbolNormalizer`） |
| `market` | `sec_market` | 枚举转字符串：HK / US / CN / SG / JP |
| `direction` | `trd_side` | `1 → BUY`, `2 → SELL` |
| `quantity` | `qty` | 直接用 |
| `price` | `price` | 直接用 |
| `currency` | `currency`（来自订单表） | 见 § 3.2 |
| `fee` | 不填 | 见 § 3.3 |
| `source_batch_id` | staged 行 `batch_id` | |

### 4.3 期权 / 特殊事件补偿

- IBKR 用 `BookTrade` 捕获行权 / 分配
- Tiger 用 `attrDesc` 标记期权事件
- **Futu**：快速扫 `trade_overview.md`，未发现专门的行权 / 分配事件类型——期权行权表现为普通 fill（`trdSide = BUY/SELL`，`status` 正常）。
- **策略**：Phase 1 **不引入**专门的期权事件补偿；保留 `raw_payload` 事后可回填。见 Open question **D3**。

---

## 5. 同步流程（`FutuSyncAdapter#sync`）

```
1. 加载 FutuCredentials（host/port/unlockPwdMd5/accFilter/trdMarkets）
2. 连接 OpenD → initConnect → unlockTrade
3. GetAccList（仅初次 / 凭证变更时）→ 过滤出启用的综合账户 × trdMarket 组合
4. 对每个 (accId, trdMarket) 组合：
   a. 按调用方给定的时间窗（startTime → endTime），按 90 天拆分（不维护增量 cursor）
   b. 对每个 90 天窗口：
      - GetHistoryOrderFillList → List<FutuOrderFill>
      - GetHistoryOrderList     → Map<orderId, {currency, session}>
      - 合并 → 写入 futu_staged_order_fills
      - 限频：两次调用之间至少 3.5s（30s / 10 次 + 安全边际）
5. Stage → trade_records 应用（与 IBKR / Tiger 同一套机制）
6. 关闭 OpenD 连接
```

**限频实现**：在 `FutuOpenDClient` 内置 `RateLimiter`（Guava 或 `Semaphore` + 滑动窗口），每个协议 ID 独立计数。

**失败路径**（与 IBKR / Tiger 完全一致，不需要 Futu 专属逻辑）：
- 单条映射失败 → `CategorizedSyncException(...)` 冒泡 → `ImportOneFailedException` → service `markFailed` 持久化 staged=FAILED
- adapter 检测 `failedCount > 0 || residualCount > 0` → 返回 `SyncResult.failure(...)`
- `BrokerSyncAsyncExecutor` → `SyncBatchFailureHandler.handleFailure` → `FutuCleanupStrategy.deleteStagedRows` + `trade_records` 清理 → `FAILED`（清理失败兜底 `CLEANUP_FAILED`）
- `error_message` 用 `[AUTH]` / `[NETWORK]` / `[UNRECOGNIZED]` / `[INTERNAL]` 分类前缀（见 [`framework/unrecognized-data-logging.md`](../../framework/unrecognized-data-logging.md)）

---

## 6. 凭证与配置

### 6.1 `FutuCredentials` 字段（规划）

```java
record FutuCredentials(
    String host,              // OpenD 地址，默认 127.0.0.1
    int port,                 // 默认 11111
    boolean enableSSL,        // 默认 false（本机）
    String rsaKey,            // OpenD 若启用加密需要
    String unlockPwdMd5,      // 交易解锁密码 MD5
    List<Long> accIdFilter,   // 可选：只同步指定综合账户
    Set<Integer> trdMarkets   // 可选：只同步指定市场
) {}
```

### 6.2 加密存储

沿用 IBKR / Tiger 现有凭证加密（`BrokerCredentialsService`）；`unlockPwdMd5` 与 `rsaKey` 入库前再做一层对称加密。

---

## 7. 分期计划

| Phase | 范围 | 产出 |
|---|---|---|
| **P1 (MVP)** | 仅实盘 / 仅 US + HK 市场 / 仅 `GetHistoryOrderFillList` + `GetHistoryOrderList` / 手动触发 | 可导入一个综合账户最近 90 天的美股 + 港股成交到 `trade_records` |
| **P2** | 多综合账户 / 多市场（CN / SG / JP）/ 时间窗口拆分 / 限频保护 | 生产可用 |
| **P3.x** | 凭证加密完善 / OpenD 健康检查 / 对齐 v2 失败处理 / 期权事件补偿（如需要） | 对齐 IBKR / Tiger 成熟度 |

> **不做"增量 cursor"**：板块统一约定每次按调用方给定的时间窗做幂等同步，不维护增量游标（参见长桥 v0.2.1 D-增量策略；Tiger / IBKR 也无 cursor）。重复同步靠"staged 表的 dedup key + `existsByExternalBrokerAndExternalId`"保证。

---

## 8. 风险与已知约束

1. **OpenD 可用性假设**：用户需自行运维 OpenD 进程。→ 见 **D6**
2. **模拟环境无历史数据**：`trdEnv = SIMULATE` 的账户会出现"有交易但拉不到"的困惑。→ 见 **D4**
3. **限频下同步时长**：一个综合账户 × 多市场 × 多个 90 天窗口可能撞到 30s/10 次，需要在日志里清晰暴露进度。
4. **Futu 期权 / 期货**：Phase 1 不做。staged 表结构通过 `trdMarket` 已兼容。
5. **`fillID` vs `fillIDEx`**：文档明确 `fillIDEx` 全局唯一，用它做 `broker_ref`；**但 Futu 重建数据时 `fillIDEx` 是否变**未在文档中明确 —— 需要实接时验证，若变则要改用 `(accId, orderId, fillID, createTime)` 的组合键。

---

## 9. Open questions（编码前需敲定）

| # | 问题 | 推荐 | 状态 |
|---|---|---|---|
| **D1** | 是否调用 `GetHistoryOrderList` 补 `currency`？（§ 3.2） | 调用 —— 取权威 currency | ❓ 待确认 |
| **D2** | 是否同步手续费字段（`GetOrderFee`）？（§ 3.3） | 不同步 —— 与 IBKR / Tiger 对齐 | ❓ 待确认 |
| **D3** | 是否接入期权事件补偿（类似 BookTrade）？（§ 4.3） | Phase 1 不接 —— 观察真实数据后定 | ❓ 待确认 |
| **D4** | 是否禁用 `SIMULATE` 账户的历史同步？（§ 8.2） | 禁用 —— 避免误导用户 | ❓ 待确认 |
| **D5** | Phase 1 覆盖的市场范围？（§ 7） | 仅 US + HK 实盘 | ❓ 待确认 |
| **D6** | OpenD 凭证的 `host/port` 是否暴露给用户配置？ | 暴露 —— 支持远程 OpenD 部署 | ❓ 待确认 |

---

## 10. 参考资料

### Futu 官方文档（仓库快照）

| 文档 | 用途 |
|---|---|
| `external-resource/futu-api/docs/intro_intro.md` | OpenD 架构 / 账户模型 / 功能矩阵 |
| `external-resource/futu-api/docs/intro_authority.md` | 登录限制 / 开户合规 / 限频 / 额度 |
| `external-resource/futu-api/docs/intro_fee.md` | API 使用无附加费用 |
| `external-resource/futu-api/docs/trade_overview.md` | 全部交易接口清单 + 协议 ID |
| `external-resource/futu-api/docs/trade_get-acc-list.md` | `Trd_GetAccList` 账户字段 |
| `external-resource/futu-api/docs/trade_get-history-order-fill-list.md` | **核心接口** —— 字段 / Java 示例 / 限频 / 90 天窗口 |
| `external-resource/futu-api/docs/trade_get-history-order-list.md` | 历史订单字段（含 currency / fillAvgPrice / session） |

### 项目内部参考

| 文档 | 用途 |
|---|---|
| [../../architecture.md](../../architecture.md) | 同步板块整体架构 |
| [../../framework/data-persistence.md](../../framework/data-persistence.md) | 两阶段导入 / staged 表设计 |
| [../../framework/import-consistency.md](../../framework/import-consistency.md) | v2 状态模型 / fail-fast cleanup |
| [../ibkr/README.md](../ibkr/README.md) | IBKR 适配器骨架参考 |
| [../tiger/README.md](../tiger/README.md) | Tiger 适配器骨架参考 + 90 天窗口拆分经验 |

---

**文档版本**：v0.1（2026-04-23）— 初始设计稿
