# 券商同步

当前实现包含 IBKR（`ibkr`）和 Tiger（`tiger`）两个适配器。用户从同步管理页面选择托管账户和日期范围，后端创建批次并异步执行。适配器先将订单和相关明细写入券商专属暂存表，再将订单映射到 `trade_records`。

IBKR 仅支持规定封套的单账户 TCF、订单粒度和整数数量；Tiger 的正常暂存／导入时间格式衔接仍有缺陷。适配器存在与发现接口可见均不等于所有来源数据可以成功导入。没有自动调度、增量游标或 Resume。 [已知问题](../../docs/TODO.md)

| 主题 | 内容 |
| --- | --- |
| [生命周期](lifecycle.md) | 接口、异步执行、批次状态、并发限制和恢复 |
| [数据持久化](persistence.md) | 批次、暂存表、外部订单标识和去重 |
| [适配器契约](adapter-contracts.md) | 注册发现、阶段切换、失败分类和清理 |
| [IBKR](brokers/ibkr.md) | Flex Query、暂存和 BookTrade 映射 |
| [Tiger](brokers/tiger.md) | 已成交订单、导入范围和时间格式限制 |

## 使用入口

同步管理页提供「同步批次」「同步配置」和「原始数据」三个页签。账户统一在「托管账户」管理中录入、选择券商及启停。同步配置列出全部已有账户，在账户行配置IBKR或Tiger连接；空列表及未选择支持券商的账户提供账户管理入口。保存只更新对应账户凭证，不修改账户资料或请求券商。凭证明文存数据库，Token和私钥只返回是否配置，留空保留、填新值替换，确认后可清空该账户连接。批次页仅允许账户启用、真实账户 ID 已补齐且配置完整的账户发起同步，并支持筛选及手动刷新。原始数据页当前只提供 IBKR 订单和成交确认的只读查看。Tiger 已有暂存表，前端尚无相应查看面板。见 [同步管理页面](../../frontend/src/pages/sync/SyncManagement.jsx)和 [连接配置页](../../frontend/src/pages/sync/settings/SyncConnectionSettingsTab.jsx)。

配置及人工排查见 [券商同步操作指南](../../docs/broker-sync/README.md)和 [失败恢复指南](../../docs/broker-sync/recovery.md)。真实券商环境的联调需要另行授权。

券商与账户按一对多建模，凭证、批次、重复记录、期权关联和导入归属按账户隔离。历史记录迁至待补全账户；账户模型、IBKR／Tiger 明文连接及批次期间允许编辑的决策见 [0047 统一方案](../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md) 和 [账户指南](../../docs/accounts.md)，后续保护设计见 [TODO](../../docs/TODO.md)。

## 决策来源

历史分期见 [券商同步总体方案](../../design/0007-2026-03-14-broker-sync-overall.md)。[导入一致性 v2](../../design/0018-2026-04-22-broker-import-consistency-v2.md) 取代了 [v1](../../design/0010-2026-04-16-broker-import-consistency-v1.md) 的部分成功与续跑机制，后续又修正了 [导入失败与数据丢失路径](../../design/0019-2026-04-23-broker-import-data-loss-fix.md)。[富途](../../design/0020-2026-04-23-futu-sync.md)和 [长桥](../../design/0021-2026-04-24-longbridge-sync.md)仍为草案。
