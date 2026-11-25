# Vortex 当前知识

这里记录已实现的功能、业务边界和代码入口，以当前源码、测试和数据库迁移为准。设计背景和后续提案见 [设计方案索引](../design/README.md)。配置、运行和排障见 [项目文档入口](../docs/README.md)。

| 主题 | 当前范围 |
| --- | --- |
| [交易与持仓](trades-and-positions.md) | 交易来源、持仓快照、关联交易、异常核对和盈亏边界 |
| [市场事件](market-events.md) | 拆股、代码变更、实物分红、顺序处理、历史锁与后端受限撤销（页面无撤销入口） |
| [券商同步](broker-sync/README.md) | IBKR、Tiger、异步批次、暂存和导入、失败清理 |
| [前端架构](frontend-architecture.md) | 应用入口、菜单路由、页面分层、API 封装和界面能力 |

托管账户通过 [BrokerAccountController](../backend/src/main/java/com/vortex/controller/BrokerAccountController.java) 管理，券商与账户一对多；账户资料及迁移说明见 [账户指南](../docs/accounts.md)。资金流水、策略和券商基础管理的服务端入口分别是 [CashFlowRecordController](../backend/src/main/java/com/vortex/controller/CashFlowRecordController.java)、[StrategyController](../backend/src/main/java/com/vortex/controller/StrategyController.java) 和 [BrokerController](../backend/src/main/java/com/vortex/controller/BrokerController.java)。

资金流水 API 支持查询、新增和软删除，没有更新接口；前端只提供查询和新增。券商管理维护名称、可空且非空值唯一的技术代码、国家／地区和独立简介，不设启停状态；代码选项覆盖 `futu`、`tiger`、`ibkr`、`schwab`、`firstrade`、`csc`，填写代码不代表已接入同步，见 [0053 券商代码](../design/0053-2026-11-26-broker-management.md#券商代码与同步能力)。托管账户独立维护所属券商、账户名称、真实账户 ID、邮箱、手机号／电话、账户备注及启停状态。同券商可有多个账户，真实账户 ID 在该券商下唯一。V33 为每个旧券商创建“待补全账户”并保留历史关联；补齐真实 ID 后才可同步，补齐前可改名或停用。交易、流水、持仓、期权关联及同步均使用本地账户主键区分账户。同步连接按账户保存 IBKR Token／Query ID 或 Tiger ID／私钥，Tiger 资金账号来自账户资料；凭证以数据库明文为唯一运行时来源。活动批次期间仍允许编辑账户和连接，批次记录选定账户与真实 ID 快照，账户模型、联系方式迁移及券商资料边界见 [0047 统一方案](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。策略支持查询、新增、更新和软删除。前端开关只控制本地按钮或展示，不是服务端权限控制。

正式持仓成本／盈亏、实时行情、自动同步调度、富途／长桥／Schwab 适配器和导出尚未实现；图表 Demo 是合成演示。已知未修复缺陷为 Tiger 时间格式衔接和 IBKR 错误码／分类，见 [TODO](../docs/TODO.md)。运行证据见 [待办归档](../docs/TODO-ARCHIVE.md)。

后端基础配置的默认库名为 `vortex`，开发测试库约定为 `vortex_dev`，默认数据库用户名为 `dermesos`。实际连接和用户名可按执行环境覆盖。 [配置指南](../docs/local-config-guide.md) · [库名方案](../design/0023-2026-11-11-database-names.md) · [用户名方案](../design/0025-2026-11-15-database-username.md)
