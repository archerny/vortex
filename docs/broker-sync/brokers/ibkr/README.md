# IBKR 接入与使用

## 配置

1. 在 IBKR Client Portal 配置 Flex Web Service token，并创建 Trade Confirmation Flex Query。查询应包含当前适配器需要的订单和成交确认字段。 [查询配置参考](../../../../external-resource/ibkr-api/docs/flex/create-trade-confirmation-flex-query.md) · [项目接入方案](../../../../design/0009-2026-04-14-ibkr-flex-web-service.md)
2. 打开**同步管理 → 同步配置**，选择已有的启用记账账户，或点击**新建IBKR账户**。同一适配器只能绑定一个账户；已绑定其他适配器的账户不能复用，不按名称猜测或转移。
3. 输入Flex Token和Trade Confirmation查询的数字Query ID，点击**保存配置**。Query ID不是查询名称或下载ReferenceCode。凭证保存到当前后端连接的数据库；Token加密且只写不读，再次打开不回显，留空保留、填新值替换。保存后后续请求读取已提交配置，无须重启。
4. 通过 [同步批次入口](../../README.md#提交与查看批次) 选择IBKR和日期范围。未配置时下拉框提供配置入口；保存仅表示本地配置完整，不请求券商或验证授权。Activity Flex Query不在支持范围内。

PENDING／PROCESSING／CLEANUP_FAILED批次会阻止保存、清空凭证，以及更换同步标识、停用或删除绑定账户。完成同步或按恢复流程处理后再改。券商管理现已支持保存Broker Code，同样执行唯一绑定和批次保护。

## 凭证、密钥与部署

`ibkr_sync_settings`是运行时凭证的唯一来源；旧profile中的Token／Query ID不回退、不自动导入。**清空凭证**须明确确认，清空后重启保持未配置，已有交易和账户绑定保留。当前是单IBKR配置模型，没有新增多账户或登录授权功能，沿用本地应用的访问范围。

Token使用JDK AES-256-GCM加密，主密钥不存数据库。默认首次保存时在工作树根目录生成`.local/ibkr-sync.key`，目录700、文件600，均被Git忽略；仅查看不会创建密钥。从根目录或`backend/`启动使用同一路径。打包部署时用`broker.ibkr.secret-key-file`显式指定稳定的持久化路径，避免移动启动目录后找不到密钥。密钥目录仅供应用用户读写，不允许符号链接或宽松权限。

备份及搬迁需要同时保管数据库和对应密钥。密钥丢失、权限错误或密文损坏时，读取／保存明确失败，即使填新Token也不自动换钥覆盖。优先恢复原密钥及权限；确认放弃旧凭证后可在页面明确清空，再排除损坏的密钥文件或路径后重新配置。不要删除其他环境的密钥。IBKR配置读取失败只禁用IBKR，其他券商仍可选择。

部署先应用新增 [V30](../../../../backend/src/main/resources/db/migration/V30__create_ibkr_sync_settings.sql)，再启用新后端／页面；Spring Boot启动会自动迁移。V30只新增配置表，不改历史交易，也不自动创建或绑定账户。迁移失败时停止发布并排查，PostgreSQL事务回滚后重试，不修改旧迁移或伪造成功记录。回退应用可保留新表；旧版本凭据读取方式见其历史设计。 [0045](../../../../design/0045-2026-11-20-ibkr-database-settings-ui.md) · [0046修订](../../../../design/0046-2026-11-20-ibkr-settings-database-snapshots.md)

## 报表支持范围

只支持单账户、单份 TCF：`FlexQueryResponse type="TCF"` 下依次为唯一的 `FlexStatements`、带非空 `accountId` 的 `FlexStatement`、`TradeConfirms`。`FlexStatements` 的 count 若提供须为 `1`。查询须选择订单与成交两个导出层级；非空报表同时含 `Order` 和 `TradeConfirm`，可附带 `SymbolSummary`，记录必须是直接子元素且以属性表达字段。多份 statement、多组 TradeConfirms、不同账户或未知层级会报错，不能靠只导出成交或汇总来替代订单层。

无交易时仍应返回完整结构和空的 TradeConfirms，系统允许成功零条；缺少 TradeConfirms 不表示零交易。一个同步批次可按日期范围依次下载多份文件，每个文件都执行上述检查；账户一致性校验仅限文件内部，不新增跨窗口或独立请求的账户绑定。输入结构不符合时查看批次原因，修正 IBKR 查询配置后重新提交。 [当前解析契约](../../../../knowledge/broker-sync/brokers/ibkr.md#支持的报表结构)

## 查看暂存数据

打开**同步管理 → 原始数据**，选择 IBKR，再查看订单或成交确认。可填入批次 ID 缩小查询范围。表格只读，正常清理后的失败批次可能已经没有暂存记录。成交确认也为 BookTrade 导入提供触发类型判定。

批次排障见 [故障排查与恢复](../../recovery.md)。字段和映射细节见 [IBKR 适配器知识](../../../../knowledge/broker-sync/brokers/ibkr.md)。
