# Vortex 当前知识

这里记录已实现的功能、业务边界和代码入口。条目按当前源码、测试源码和数据库迁移核对。设计背景和后续提案见 [设计方案索引](../design/README.md)。配置、运行和排障见 [项目文档入口](../README.md#documentation)。

| 主题 | 当前范围 |
| --- | --- |
| [交易与持仓](trades-and-positions.md) | 交易来源、持仓快照、关联交易、异常核对和盈亏边界 |
| [市场事件](market-events.md) | 拆股、代码变更、实物分红、派生交易和重算范围 |
| [券商同步](broker-sync/README.md) | IBKR、Tiger、异步批次、暂存和导入、失败清理 |
| [前端架构](frontend-architecture.md) | 应用入口、菜单路由、页面分层、API 封装和界面能力 |

资金流水、策略和券商基础管理的服务端入口分别是 [CashFlowRecordController](../backend/src/main/java/com/vortex/controller/CashFlowRecordController.java)、[StrategyController](../backend/src/main/java/com/vortex/controller/StrategyController.java) 和 [BrokerController](../backend/src/main/java/com/vortex/controller/BrokerController.java)。

后端基础配置的默认库名为 `vortex`，开发测试库约定为 `vortex_dev`，实际连接按执行环境配置。用户名单独指定。 [配置指南](../docs/local-config-guide.md) · [命名方案](../design/0023-2026-11-11-database-names.md)
