# Vortex 当前知识

这里记录已实现的功能、业务边界和代码入口，以当前源码、测试和数据库迁移为准。设计背景和后续提案见 [设计方案索引](../design/README.md)。配置、运行和排障见 [项目文档入口](../docs/README.md)。

| 主题 | 当前范围 |
| --- | --- |
| [交易与持仓](trades-and-positions.md) | 交易来源、持仓快照、关联交易、异常核对和盈亏边界 |
| [市场事件](market-events.md) | 拆股、代码变更、实物分红、顺序处理、历史锁与受限撤销 |
| [券商同步](broker-sync/README.md) | IBKR、Tiger、异步批次、暂存和导入、失败清理 |
| [前端架构](frontend-architecture.md) | 应用入口、菜单路由、页面分层、API 封装和界面能力 |

资金流水、策略和券商基础管理的服务端入口分别是 [CashFlowRecordController](../backend/src/main/java/com/vortex/controller/CashFlowRecordController.java)、[StrategyController](../backend/src/main/java/com/vortex/controller/StrategyController.java) 和 [BrokerController](../backend/src/main/java/com/vortex/controller/BrokerController.java)。

资金流水 API 支持查询、新增和软删除，没有更新接口；前端只提供查询和新增。券商新建和更新均可保存 `brokerCode`，非空代码唯一；绑定账户的标识／启用状态变更及删除受活动同步批次保护。IBKR在同步配置页绑定账户并将Token／Query ID保存到数据库。策略支持查询、新增、更新和软删除。前端开关只控制本地按钮或展示，不是服务端权限控制。

正式持仓成本／盈亏、实时行情、自动同步调度、富途／长桥／Schwab 适配器和导出尚未实现；图表 Demo 是合成演示。已知未修复缺陷为 Tiger 时间格式衔接和 IBKR 错误码／分类，见 [TODO](../docs/TODO.md)。运行证据见 [待办归档](../docs/TODO-ARCHIVE.md)。

后端基础配置的默认库名为 `vortex`，开发测试库约定为 `vortex_dev`，默认数据库用户名为 `dermesos`。实际连接和用户名可按执行环境覆盖。 [配置指南](../docs/local-config-guide.md) · [库名方案](../design/0023-2026-11-11-database-names.md) · [用户名方案](../design/0025-2026-11-15-database-username.md)
