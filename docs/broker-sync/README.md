# 券商交易记录同步 - 设计文档

> 本目录包含「券商交易记录同步」功能板块的所有设计方案和讨论文档。

---

## 文档索引

| 文档 | 说明 | 状态 |
|------|------|------|
| [overall-design.md](./overall-design.md) | 总体方案设计（核心问题讨论与决策记录） | 方案讨论中 |
| [tiger-sync-design.md](./tiger-sync-design.md) | 老虎证券同步方案（设计与实现记录） | Phase 1 已实现 |
| [ibkr-flex-web-service-design.md](./ibkr-flex-web-service-design.md) | IBKR Flex Web Service 同步方案（讨论记录） | ✅ Phase 1 已实现（API + 解析 + 异步执行 + 前端管理） |
| [data-persistence-design.md](./data-persistence-design.md) | 数据持久化设计（Order 暂存表 + 批次表 + 明细附表 + trade_records 扩展） | ✅ 数据库变更已完成（Flyway V20-V22 + Entity + Repository） |
| [import-consistency-design.md](./import-consistency-design.md) | 数据一致性与中断恢复设计（事务策略 + 状态机 + 失败分类 + Resume 机制） | 方案已确认，待实现 |
| [broker-code-design.md](./broker-code-design.md) | Broker Code 关联与同步器注册发现（brokerName→brokerCode 重命名 + brokers 表关联 + 前端发现机制） | 方案已确认，待实现 |
| [booktrade-trigger-mapping-design.md](./booktrade-trigger-mapping-design.md) | BookTrade 触发判定与期权事件导入映射（code 解析 + triggerRefId 回填 + 语义匹配） | 方案已确认，待实现 |

---

## 开放问题快速索引

> **最后整理**：2026-04-18

所有待讨论问题已汇总到 [data-persistence-design.md § 九、开放问题与待后续讨论](./data-persistence-design.md#九开放问题与待后续讨论)，按三级优先级分类：

| 级别 | 数量 | 含义 |
|------|------|------|
| 🔥 编码前必须解决 | 4 个（O-1, O-3 ~ O-5） | 不解决会卡住导入逻辑实现 |
| 📦 可后续再说 | 7 个（D-1 ~ D-7） | 不阻塞当前编码 |
| ✅ 已解决 | 5 个（R-1 ~ R-5） | 归档留痕 |

> 其他文档中的零散待讨论项（如 `overall-design.md` 的 40+ 个早期问题、`tiger-sync-design.md` 和 `ibkr-flex-web-service-design.md` 中的增强项）已在 D-3 ~ D-7 中归纳引用，不重复列举。

---

## 背景

当前 Local Ledger 系统中的所有交易记录均由用户手动录入。随着交易频次增加，手动录入效率低下且容易出错。

**目标**：实现从券商平台 API 自动同步个人交易记录到系统中，减少人工操作，提升数据准确性。

## 分期计划

| 阶段 | 范围 | 关键能力 | 状态 |
|------|------|---------|------|
| **Phase 1** | 老虎证券 + 手动触发 + 日志输出 | 跑通基本流程、核对原始数据 | ✅ 已完成 |
| **Phase 2** | IBKR 适配 + 多券商适配 + 去重机制 + 同步日志 + 入库 | 生产可用（IBKR 方案详见 [ibkr-flex-web-service-design.md](./ibkr-flex-web-service-design.md)） | 🔧 IBKR API+解析+异步已完成，数据库变更已完成（V20-V22 + Entity），导入逻辑待实现 |
| **Phase 3** | 自动同步 + 冲突处理 + 前端交互 | 完整体验 | 📋 待规划 |

## 涉及的券商

| 券商 | 优先级 | API 类型 | 适配器 |
|------|--------|---------|--------|
| 老虎证券 | Phase 1 | Tiger Open API (REST) | `TigerSyncAdapter` |
| 盈透证券 (IBKR) | Phase 2+ | Flex Web Service | `IbkrSyncAdapter` |
| 嘉信证券 | Phase 2+ | Schwab API | `SchwabSyncAdapter` |
| 富途证券 | Phase 2+ | OpenD + OpenAPI | `FutuSyncAdapter` |
