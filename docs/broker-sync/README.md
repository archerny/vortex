# 券商交易记录同步 - 设计文档

> 本目录包含「券商交易记录同步」功能板块的所有设计方案和讨论文档。

---

## 文档索引

| 文档 | 说明 | 状态 |
|------|------|------|
| [architecture.md](./architecture.md) | 架构概览（包结构 + 数据流 + 异步执行模型） | ✅ 当前架构 |
| **框架层（所有券商通用）** | | |
| [framework/data-persistence.md](./framework/data-persistence.md) | 数据持久化设计（批次表 + trade_records 扩展 + 两阶段导入原则） | ✅ 已实现（Flyway V19-V24 + Entity + Repository） |
| [framework/import-consistency.md](./framework/import-consistency.md) | 数据一致性与中断恢复设计（事务策略 + 状态机 + 失败分类 + Resume 机制） | ✅ 已实现 |
| [framework/broker-registration.md](./framework/broker-registration.md) | Broker Code 关联与同步器注册发现 | ✅ 已实现 |
| **券商层（各券商专属）** | | |
| [brokers/tiger/README.md](./brokers/tiger/README.md) | 老虎证券同步状态页 | ✅ |
| [brokers/tiger/open-api.md](./brokers/tiger/open-api.md) | 老虎证券同步方案（设计与实现记录） | ✅ Phase 1 已实现 |
| [brokers/ibkr/README.md](./brokers/ibkr/README.md) | IBKR 同步状态页 | ✅ |
| [brokers/ibkr/flex-web-service.md](./brokers/ibkr/flex-web-service.md) | IBKR Flex Web Service 同步方案 | ✅ Phase 2 已实现 |
| [brokers/ibkr/staging-schema.md](./brokers/ibkr/staging-schema.md) | IBKR 暂存表结构与字段映射规范 | ✅ 已实现 |
| [brokers/ibkr/booktrade-mapping.md](./brokers/ibkr/booktrade-mapping.md) | BookTrade 触发判定与期权事件导入映射 | ✅ 已实现 |

---

## 架构速览

详细架构参见 [architecture.md](./architecture.md)。核心要点：

- **适配器模式**：`BrokerSyncAdapter` 接口屏蔽各券商差异，新增券商只需实现接口并加 `@Component`
- **两阶段导入**：暂存表（按券商独立）→ 正式表，避免污染数据
- **异步执行**：`@Async` + 独立线程池，Controller 提交后立即返回
- **每券商专属原始模型**：`IbkrOrderRecord` / `TigerOrderRecord`，日志打印和暂存表字段 1:1 对应

---

## 开放问题快速索引

> **最后整理**：2026-04-21

所有待讨论问题汇总在 [framework/data-persistence.md § 九、开放问题与待后续讨论](./framework/data-persistence.md#九开放问题与待后续讨论)，按优先级分类：

| 级别 | 数量 | 含义 |
|------|------|------|
| 🔥 编码前必须解决 | 0 个 | （暂无，阻塞性问题均已解决） |
| 📦 可后续再说 | 6 个（D-1 ~ D-6） | 不阻塞当前编码 |
| ✅ 已解决 | 9 个（R-1 ~ R-9） | 归档留痕 |

---

## 背景

当前 Vortex 系统中的所有交易记录均由用户手动录入。随着交易频次增加，手动录入效率低下且容易出错。

**目标**：实现从券商平台 API 自动同步个人交易记录到系统中，减少人工操作，提升数据准确性。

## 分期计划

| 阶段 | 范围 | 关键能力 | 状态 |
|------|------|---------|------|
| **Phase 1** | 老虎证券 + 手动触发 + 日志输出 | 跑通基本流程、核对原始数据 | ✅ 已完成 |
| **Phase 2** | IBKR 适配 + 多券商适配 + 去重机制 + 同步日志 + 入库 | 生产可用（IBKR 方案详见 [brokers/ibkr/flex-web-service.md](./brokers/ibkr/flex-web-service.md)） | ✅ IBKR 同步全链路已实现（API→暂存→导入→Resume），待真实环境验证 |
| **Phase 3** | 自动同步 + 冲突处理 + 前端交互 | 完整体验 | 📋 待规划 |

## 涉及的券商

| 券商 | 优先级 | API 类型 | 适配器 |
|------|--------|---------|--------|
| 老虎证券 | Phase 1 | Tiger Open API (REST) | `TigerSyncAdapter` |
| 盈透证券 (IBKR) | Phase 2+ | Flex Web Service | `IbkrSyncAdapter` |
| 嘉信证券 | Phase 2+ | Schwab API | `SchwabSyncAdapter` |
| 富途证券 | Phase 2+ | OpenD + OpenAPI | `FutuSyncAdapter` |
