# 券商同步

当前实现包含 IBKR（`ibkr`）和 Tiger（`tiger`）两个适配器。用户从同步管理页面选择券商和日期范围，后端创建批次并异步执行。适配器先将订单和相关明细写入券商专属暂存表，再将订单映射到 `trade_records`。

IBKR 仅支持规定封套的单账户 TCF、订单粒度和整数数量；Tiger 的正常暂存／导入时间格式衔接仍有缺陷。适配器存在与发现接口可见均不等于所有来源数据可以成功导入。没有自动调度、增量游标或 Resume。 [已知问题](../../docs/TODO.md)

| 主题 | 内容 |
| --- | --- |
| [生命周期](lifecycle.md) | 接口、异步执行、批次状态、并发限制和恢复 |
| [数据持久化](persistence.md) | 批次、暂存表、外部订单标识和去重 |
| [适配器契约](adapter-contracts.md) | 注册发现、阶段切换、失败分类和清理 |
| [IBKR](brokers/ibkr.md) | Flex Query、暂存和 BookTrade 映射 |
| [Tiger](brokers/tiger.md) | 已成交订单、导入范围和时间格式限制 |

## 使用入口

同步管理页提供「同步批次」「同步配置」和「原始数据」三个页签。配置页可新建／绑定IBKR记账账户并保存或清空Flex Token与Query ID；凭证以数据库为唯一来源，Token加密且只写不读，保存不请求券商。批次页可手动触发同步、按券商和状态筛选，并刷新结果；缺IBKR配置时给出配置入口，配置失败不影响其他券商选项。原始数据页当前只提供 IBKR 订单和成交确认的只读查看。Tiger 已有暂存表，前端尚无相应查看面板。见 [同步管理页面](../../frontend/src/pages/sync/SyncManagement.jsx)和 [原始数据页](../../frontend/src/pages/sync/staged/StagedDataTab.jsx)。

配置及人工排查见 [券商同步操作指南](../../docs/broker-sync/README.md)和 [失败恢复指南](../../docs/broker-sync/recovery.md)。真实券商环境的联调需要另行授权。

## 决策来源

历史分期见 [券商同步总体方案](../../design/0007-2026-03-14-broker-sync-overall.md)。[导入一致性 v2](../../design/0018-2026-04-22-broker-import-consistency-v2.md) 取代了 [v1](../../design/0010-2026-04-16-broker-import-consistency-v1.md) 的部分成功与续跑机制，后续又修正了 [导入失败与数据丢失路径](../../design/0019-2026-04-23-broker-import-data-loss-fix.md)。[富途](../../design/0020-2026-04-23-futu-sync.md)和 [长桥](../../design/0021-2026-04-24-longbridge-sync.md)仍为草案。
