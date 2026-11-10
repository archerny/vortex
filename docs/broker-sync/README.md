# 券商同步操作指南

同步管理支持手动提交 IBKR 和 Tiger 的交易记录同步。每次提交创建一个批次，随后由后台拉取、暂存并导入。通过批次列表查看进度和结果，通过刷新获取最新状态。

| 任务 | 入口 |
| --- | --- |
| 配置 IBKR Flex Query，查询 IBKR 暂存数据 | [IBKR 接入与使用](brokers/ibkr/README.md) |
| 配置 Tiger SDK 凭据并提交同步 | [Tiger 接入与使用](brokers/tiger/README.md) |
| 排查同步失败、冲突或残留数据 | [故障排查与恢复](recovery.md) |
| 查阅当前架构、数据契约或适配器实现 | [券商同步知识](../../knowledge/broker-sync/README.md) |
| 查阅设计决策、旧版本或待实现方案 | [设计方案索引](../../design/README.md) |

## 提交与查看批次

1. 在券商管理中确认已配置正确的券商记录和 Broker Code。后端同步凭据按所用 Spring profile 注入。测试时使用隔离数据环境，真实账户不用于自动验证。 [环境与配置说明](../../backend/src/main/resources/README.md)
2. 打开**同步管理 → 同步批次**，点击**新建同步**，选择券商和日期范围。可用券商由后台适配器发现接口提供。
3. 提交后查看批次列表。批次提交成功只表示任务进入后台，完成状态和错误信息以刷新后的批次为准。HTTP 409 表示已有批次占用同步名额。 [故障排查与恢复](recovery.md)

富途和长桥仍是待实现方案。 [富途草案](../../design/0020-2026-04-23-futu-sync.md) · [长桥草案](../../design/0021-2026-04-24-longbridge-sync.md)
