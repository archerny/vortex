# 券商 API 离线参考

本目录保存厂商资料和下载／转换维护说明。它们描述外部 API，存在文档不表示 Vortex 已接入对应能力。项目同步的实际契约统一见 [券商同步知识](../knowledge/broker-sync/README.md)。

| 资料 | Vortex 当前接入范围 |
| --- | --- |
| [IBKR](ibkr-api/README.md) | 使用 Trade Confirmation Flex Web Service；Web API、TWS、Activity Flex 资料不代表当前已实现。 |
| [Tiger](tiger-api/README.md) | 使用 Java SDK 查询已成交订单；暂存到导入存在未修复时间格式缺陷，见 [TODO](../docs/TODO.md)。 |
| [Futu](futu-api/README.md) | 无项目适配器，只有 [草案](../design/0020-2026-04-23-futu-sync.md)。 |
| [Longbridge](longbridge-api/README.md) | 无项目适配器，只有 [草案](../design/0021-2026-04-24-longbridge-sync.md)。 |
| [Schwab](schwab-api/README.md) | 无项目适配器；部分资料来自第三方源码或整理，不能一概视为官方契约。 |

各目录记录的文档版本、站点结构、抓取数量及成功率只代表采集时状态，离线资料不保证与厂商最新文档一致。实际使用前核对来源、厂商当前版本与 SDK。
