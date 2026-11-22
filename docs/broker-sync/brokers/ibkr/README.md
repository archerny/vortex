# IBKR 接入与使用

## 配置

1. 在 IBKR Client Portal 配置 Flex Web Service token，并创建 Trade Confirmation Flex Query。查询应包含当前适配器需要的订单和成交确认字段。 [查询配置参考](../../../../external-resource/ibkr-api/docs/flex/create-trade-confirmation-flex-query.md) · [项目接入方案](../../../../design/0009-2026-04-14-ibkr-flex-web-service.md)
2. 在**券商管理**维护 IBKR 券商，在**托管账户**中新建其下的账户，填写真实 IBKR 账户 ID 并启用。同券商可有多个账户；历史“待补全账户”须先补齐 ID。
3. 打开**同步管理 → 同步配置**，在该账户行点击**配置连接**，输入Flex Token和Trade Confirmation查询的数字Query ID并保存。Query ID不是查询名称或下载ReferenceCode。凭证以明文保存到该账户的数据库连接记录，运行时直接读取。页面再次打开时Token输入框为空，留空保留、填新值替换，无须重启。账户名称和真实 ID 在托管账户管理修改；券商名称及技术代码在券商管理修改。
4. 通过 [同步批次入口](../../README.md#提交与查看批次) 选择具体 IBKR 账户和日期范围。账户停用、真实 ID 待补全或配置不完整时不可选择；保存仅表示本地配置完整，不请求券商或验证授权。Activity Flex Query不在支持范围内。

当前个人本地使用阶段允许在批次期间保存／清空凭证以及修改账户。一次报表的两步请求使用开始时取得的凭证，后续报表读取最新配置。批次保留所选本地账户与真实 ID 快照，导入不重新按当前券商代码选账户。资料编辑及凭证变化仍由用户安排时机。后续凭证与账户保护设计见 [TODO](../../../TODO.md)。

## 凭证与部署

`broker_sync_connections`中对应账户的记录是运行时凭证的唯一来源；旧profile中的Token／Query ID不回退、不自动导入。**清空凭证**须明确确认，仅删除该账户连接，清空后重启保持未配置，其他账户连接、已有交易和账户资料保留。同券商多个账户各自维护连接，沿用本地应用的访问范围。

当前采用明文存储，数据库备份包含Token。备份、搬迁和恢复以数据库为准，应用运行直接使用数据库凭证。

用户已确认此前的加密方案没有实际保存过凭证，V31历史SQL保持原样。本次先停止旧后端、备份目标数据库，再由新应用执行未应用的 [V32](../../../../backend/src/main/resources/db/migration/V32__create_account_sync_connections.sql) 、V33 和结构校验。V33 将券商与账户分离，历史关联迁至真实 ID 为空的账户，须补齐后才能同步；不重算旧账。完整升级、备份与恢复步骤见 [账户迁移说明](../../../accounts.md#迁移与恢复)。V32 将已有明文 IBKR 配置按匹配的 `brokers` 券商行保存，并移除旧单例表；存在 Token 却没有对应 IBKR 券商行时整次回滚，先在升级前版本修正券商关联后重试。V33 再把连接转给该券商的待补全账户。空库正常初始化。

迁移失败时排查后重试，保持已应用迁移原样。回退旧应用须同时恢复升级前数据库。历史设计见 [0045](../../../../design/0045-2026-11-20-ibkr-database-settings-ui.md)、[0046](../../../../design/0046-2026-11-20-ibkr-settings-database-snapshots.md)，明文凭证及账户连接的最终方案见 [0047](../../../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。

## 报表支持范围

只支持单账户、单份 TCF：`FlexQueryResponse type="TCF"` 下依次为唯一的 `FlexStatements`、带非空 `accountId` 的 `FlexStatement`、`TradeConfirms`。`FlexStatements` 的 count 若提供须为 `1`。查询须选择订单与成交两个导出层级；非空报表同时含 `Order` 和 `TradeConfirm`，可附带 `SymbolSummary`，记录必须是直接子元素且以属性表达字段。多份 statement、多组 TradeConfirms、不同账户或未知层级会报错，不能靠只导出成交或汇总来替代订单层。

无交易时仍应返回完整结构和空的 TradeConfirms，系统允许成功零条；缺少 TradeConfirms 不表示零交易。一个同步批次可按日期范围依次下载多份文件，每个文件都执行上述检查；每份文件（含零交易）都须与本批选定的真实账户 ID 一致，错账户报表在暂存前失败。输入结构不符合时查看批次原因，修正 IBKR 查询配置后重新提交。 [当前解析契约](../../../../knowledge/broker-sync/brokers/ibkr.md#支持的报表结构)

## 查看暂存数据

打开**同步管理 → 原始数据**，选择 IBKR，再查看订单或成交确认。可填入批次 ID 缩小查询范围。表格只读，正常清理后的失败批次可能已经没有暂存记录。成交确认也为 BookTrade 导入提供触发类型判定。

批次排障见 [故障排查与恢复](../../recovery.md)。字段和映射细节见 [IBKR 适配器知识](../../../../knowledge/broker-sync/brokers/ibkr.md)。
