# IBKR 接入与使用

## 配置

1. 在 IBKR Client Portal 配置 Flex Web Service token，并创建 Trade Confirmation Flex Query。查询应包含当前适配器需要的订单和成交确认字段。 [查询配置参考](../../../../external-resource/ibkr-api/docs/flex/create-trade-confirmation-flex-query.md) · [项目接入方案](../../../../design/0009-2026-04-14-ibkr-flex-web-service.md)
2. 在使用的 Spring profile 中注入以下属性。示例均为占位符，真实值存放在版本控制之外。 [配置说明](../../../local-config-guide.md)

   ```properties
   broker.ibkr.flex-token=<flex-token>
   broker.ibkr.trade-confirm-query-id=<trade-confirm-query-id>
   ```

3. 在券商管理中确认有 Broker Code 为 `ibkr` 的记录。新建券商时填写代码。已有记录的修改以实际保存结果为准，当前编辑接口不会更新 Broker Code。
4. 通过 [同步批次入口](../../README.md#提交与查看批次) 提交 IBKR 同步。Activity Flex Query 不在当前适配器的同步范围内。

## 报表支持范围

只支持单账户、单份 TCF：`FlexQueryResponse type="TCF"` 下依次为唯一的 `FlexStatements`、带非空 `accountId` 的 `FlexStatement`、`TradeConfirms`。`FlexStatements` 的 count 若提供须为 `1`。查询须选择订单与成交两个导出层级；非空报表同时含 `Order` 和 `TradeConfirm`，可附带 `SymbolSummary`，记录必须是直接子元素且以属性表达字段。多份 statement、多组 TradeConfirms、不同账户或未知层级会报错，不能靠只导出成交或汇总来替代订单层。

无交易时仍应返回完整结构和空的 TradeConfirms，系统允许成功零条；缺少 TradeConfirms 不表示零交易。一个同步批次可按日期范围依次下载多份文件，每个文件都执行上述检查；账户一致性校验仅限文件内部，不新增跨窗口或独立请求的账户绑定。输入结构不符合时查看批次原因，修正 IBKR 查询配置后重新提交。 [当前解析契约](../../../../knowledge/broker-sync/brokers/ibkr.md#支持的报表结构)

## 查看暂存数据

打开**同步管理 → 原始数据**，选择 IBKR，再查看订单或成交确认。可填入批次 ID 缩小查询范围。表格只读，正常清理后的失败批次可能已经没有暂存记录。成交确认也为 BookTrade 导入提供触发类型判定。

批次排障见 [故障排查与恢复](../../recovery.md)。字段和映射细节见 [IBKR 适配器知识](../../../../knowledge/broker-sync/brokers/ibkr.md)。
