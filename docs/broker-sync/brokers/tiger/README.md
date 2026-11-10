# Tiger 接入与使用

## 配置

1. 准备 Tiger 开放平台的 Tiger ID、SDK 使用的 PKCS#8 RSA 私钥和资金账户。 [接入参考](../../../../external-resource/tiger-api/docs/basic-java.md)
2. 在使用的 Spring profile 中注入以下属性。示例均为占位符，真实值存放在版本控制之外。 [配置说明](../../../local-config-guide.md)

   ```properties
   broker.tiger.tiger-id=<tiger-id>
   broker.tiger.private-key=<private-key>
   broker.tiger.account=<account>
   ```

3. 在券商管理中确认有 Broker Code 为 `tiger` 的记录。V27 仅为匹配名称的已有券商设置代码。新建券商时填写代码，当前编辑接口不会更新该字段。 [V27 迁移](../../../../backend/src/main/resources/db/migration/V27__seed_tiger_broker_code.sql)

## 提交同步

通过 [同步批次入口](../../README.md#提交与查看批次) 选择 Tiger 和需要的日期范围。Tiger 的订单会写入后台暂存表，前端原始数据页目前只提供 IBKR 面板。遇到碎股、未支持的证券类型或期权事件订单时，按 [适配器当前规则](../../../../knowledge/broker-sync/brokers/tiger.md) 和 [故障排查指南](../../recovery.md) 确认批次结果。
