# 券商同步 — 架构概览

> **创建日期**：2026-04-21
> **最后更新**：2026-04-28（包结构补充 longbridge / futu adapter 占位；Phase 3 状态保持已落地）
> **状态**：✅ 架构已落地（Phase 1 / Phase 2 / Phase 3 均完成；后续为 Phase 3.x 等增量能力）
> **关联**：[README.md](./README.md) | [framework/data-persistence.md](./framework/data-persistence.md) | [framework/import-consistency.md](./framework/import-consistency.md) | [framework/broker-registration.md](./framework/broker-registration.md)

本文档给出券商同步模块的高层架构视图：包结构、数据流、异步执行模型。具体的表结构、字段映射、导入一致性、券商专属实现细节，分别参见 framework/ 和 brokers/ 目录下的专题文档。

---

## 一、设计原则

1. **适配器模式 + sync 独立顶级包** — 新增券商只需实现 `BrokerSyncAdapter` 接口并加 `@Component`，无需修改任何现有代码。
2. **每个券商有自己的专属原始模型**（如 `IbkrOrderRecord`、`TigerOrderRecord`）— 不强行合并为统一模型，避免大量 NULL 字段或 JSONB 混杂；日志打印和暂存表字段 1:1 对应专属模型。
3. **两阶段导入：暂存 → 正式表** — 同步数据先写入按券商独立的暂存表（staged table），再通过导入服务转换为 `trade_records`，避免污染正式数据。详见 [data-persistence.md](./framework/data-persistence.md)。
4. **异步执行** — 同步任务通过 `@Async` + 独立线程池执行，Controller 提交后立即返回，避免 HTTP 请求长时间阻塞。
5. **配置文件管理凭证** — 券商 API 凭证统一放在 `application-local.properties`（已 `.gitignore`），与数据库密码保持一致的管理方式。

---

## 二、包结构

```
com.vortex
├── controller/
│   └── BrokerSyncController.java          ← 同步触发 API、批次查询 API
├── service/
│   └── BrokerSyncBatchService.java        ← 批次 CRUD + 状态变更（每方法独立 @Transactional）
├── entity/
│   ├── BrokerSyncBatch.java
│   ├── IbkrStagedOrder.java
│   └── IbkrStagedTradeConfirm.java
├── repository/
│   ├── BrokerSyncBatchRepository.java
│   ├── IbkrStagedOrderRepository.java
│   └── IbkrStagedTradeConfirmRepository.java
├── config/
│   └── AsyncConfig.java                   ← @EnableAsync + syncTaskExecutor 线程池
│
└── sync/                                   ← 独立的同步模块
    ├── core/
    │   ├── BrokerSyncAdapter.java          ← 统一适配器接口
    │   ├── BrokerSyncService.java          ← 核心同步编排逻辑
    │   ├── BrokerSyncAsyncExecutor.java    ← @Async 异步执行器（独立 Bean）
    │   └── SyncRequest.java                ← 同步请求参数
    └── adapter/
        ├── tiger/
        │   ├── TigerSyncAdapter.java
        │   └── TigerOrderRecord.java       ← 老虎证券专属原始模型
        ├── ibkr/
        │   ├── IbkrSyncAdapter.java
        │   ├── IbkrFlexClient.java
        │   ├── IbkrFlexQueryProperties.java
        │   ├── FlexQueryParser.java
        │   ├── FlexQueryParseResult.java
        │   ├── IbkrOrderRecord.java        ← IBKR 专属原始模型（Order 粒度）
        │   ├── IbkrTradeConfirm.java       ← IBKR 执行明细模型
        │   ├── IbkrStagingService.java     ← 暂存表写入
        │   └── IbkrImportService.java      ← 暂存 → trade_records 导入
        ├── longbridge/                     ← 📋 设计稿 v0.2.3，未编码（占位）
        └── futu/                           ← 📋 设计稿 v0.1，未编码（占位）
```

---

## 三、整体数据流

```
前端点击「新建同步」 / curl POST /api/broker-sync/trigger
    │
    ▼
BrokerSyncController
    │ ① 参数校验
    │ ② 创建 BrokerSyncBatch (status = PENDING)
    │ ③ 提交异步任务（不等待）
    │ ④ 立即返回 batch 信息
    ▼
BrokerSyncAsyncExecutor（独立线程池 sync-*）
    │ ⑤ 标记 batch 为 PROCESSING
    │ ⑥ 调用 BrokerSyncService.sync()
    ▼
BrokerSyncAdapter（按 brokerCode 路由）
    │
    ▼
券商 API 响应 (JSON/XML)
    │ 反序列化 + 解析
    ▼
券商专属内存模型（如 IbkrOrderRecord）
    │ StagingService 写入暂存表
    ▼
券商专属暂存表（如 ibkr_staged_orders）        ← batch: PROCESSING
    │ 字段映射 + 类型转换 + 去重校验
    │ 每条记录独立事务，状态 IMPORTED / SKIPPED / CONFLICT / FAILED
    ▼
trade_records（正式表）                          ← batch: COMPLETED / FAILED / CLEANUP_FAILED
    │ 设置 external_id / external_broker / sync_batch_id
    │ 按交易业务含义设置 trade_trigger（MANUAL / OPTION / MARKET_EVENT）
    ▼
前端通过列表刷新查看最终状态
```

状态机与失败分类细节详见 [import-consistency.md](./framework/import-consistency.md)。

---

## 四、异步执行模型

### 4.1 为什么异步

IBKR Flex Query 需要 `SendRequest` → 轮询 `GetStatement`，耗时可能数十秒；Tiger 批次较大时同步也较慢。HTTP 请求同步阻塞会导致前端弹窗卡死。

### 4.2 关键组件

| 组件 | 职责 |
|------|------|
| `AsyncConfig` | `@EnableAsync` + 线程池定义，独立配置类，不污染启动类 |
| `BrokerSyncAsyncExecutor` | `@Async` 异步执行器，独立 Bean（解决 Spring AOP 代理限制） |
| `BrokerSyncBatchService` | 批次状态变更方法，每个方法独立 `@Transactional`（短事务） |
| `BrokerSyncController` | 提交任务 + 立即返回，不再包含同步执行逻辑 |

### 4.3 线程池配置（`syncTaskExecutor`）

| 参数 | 值 | 说明 |
|------|-----|------|
| corePoolSize | 2 | 核心线程数 |
| maxPoolSize | 4 | 最大线程数 |
| queueCapacity | 10 | 队列容量 |
| threadNamePrefix | `sync-` | 线程名前缀，便于日志排查 |
| rejectedHandler | 记录错误 | 队列满时拒绝并记录，不默默丢弃 |

### 4.4 异常安全

- 异步方法内全覆盖 try-catch，任何异常（包括 RuntimeException）都保证 batch 最终被标记为 FAILED
- 每次状态变更都是独立事务，避免长事务问题
- 即使 `markAsFailed` 本身失败（极端情况），仍有日志记录

---

## 五、券商注册与路由

- `brokers` 表有 `broker_code` 列（如 `ibkr`、`tiger`），是同步器的唯一技术标识
- 每个 `BrokerSyncAdapter` 实现类通过 `getBrokerCode()` 声明支持的券商
- `BrokerSyncService` 根据请求参数中的 `brokerCode` 路由到对应适配器
- 前端通过 `GET /api/brokers/supported-sync` 动态发现可用的同步券商

详见 [broker-registration.md](./framework/broker-registration.md)。

---

## 六、MVP 分期

| 阶段 | 范围 | 关键能力 | 状态 |
|------|------|---------|------|
| **Phase 1** | 老虎证券 + 手动触发 + 日志输出 | 跑通基本流程、核对原始数据 | ✅ 已完成 |
| **Phase 2** | IBKR 适配 + 暂存入库 + 失败清理 + 前端管理 | 生产可用 | ✅ 已完成（v2 失败清理机制已随 import-consistency.md v2 落地） |
| **Phase 3** | Tiger 两阶段导入 + v2 状态模型 + DB 并发冲突 + 前端 v2 UI | Tiger 对齐 IBKR 全链路；完整体验 | ✅ 已完成（详见 [README.md § MVP 分期](./README.md)、[brokers/tiger/phase3-plan.md](./brokers/tiger/phase3-plan.md)、[framework/import-consistency.md](./framework/import-consistency.md)） |
| **Phase 3.x**（增量） | 自动同步 / 同步预览 / `attrDesc` 期权事件映射 / Tiger staged 查看面板 等 | 完整运维体验 | 📋 待规划（非阻塞项，按需推进） |
