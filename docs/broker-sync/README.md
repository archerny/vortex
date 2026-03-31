# 券商交易记录同步 - 设计文档

> 本目录包含「券商交易记录同步」功能板块的所有设计方案和讨论文档。

---

## 文档索引

| 文档 | 说明 | 状态 |
|------|------|------|
| [overall-design.md](./overall-design.md) | 总体方案设计（核心问题讨论与决策记录） | 方案讨论中 |
| [tiger-sync-design.md](./tiger-sync-design.md) | 老虎证券同步方案（设计与实现记录） | Phase 1 已实现 |
| [ibkr-flex-web-service-design.md](./ibkr-flex-web-service-design.md) | IBKR Flex Web Service 同步方案（讨论记录） | 方案讨论完成，待实现 |

---

## 背景

当前 Local Ledger 系统中的所有交易记录均由用户手动录入。随着交易频次增加，手动录入效率低下且容易出错。

**目标**：实现从券商平台 API 自动同步个人交易记录到系统中，减少人工操作，提升数据准确性。

## 分期计划

| 阶段 | 范围 | 关键能力 | 状态 |
|------|------|---------|------|
| **Phase 1** | 老虎证券 + 手动触发 + 日志输出 | 跑通基本流程、核对原始数据 | 🚧 进行中 |
| **Phase 2** | 多券商适配 + 去重机制 + 同步日志 + 入库 | 生产可用 | 📋 待规划 |
| **Phase 3** | 自动同步 + 冲突处理 + 前端交互 | 完整体验 | 📋 待规划 |

## 涉及的券商

| 券商 | 优先级 | API 类型 | 适配器 |
|------|--------|---------|--------|
| 老虎证券 | Phase 1 | Tiger Open API (REST) | `TigerSyncAdapter` |
| 盈透证券 (IBKR) | Phase 2+ | Client Portal API / TWS API | `IbkrSyncAdapter` |
| 嘉信证券 | Phase 2+ | Schwab API | `SchwabSyncAdapter` |
| 富途证券 | Phase 2+ | OpenD + OpenAPI | `FutuSyncAdapter` |
