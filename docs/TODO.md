# 待办事项

本文件只维护仍需处理的事项，包括待确认、受阻和待验证的事项。已完成、已取消或已合并的记录见 [待办归档](TODO-ARCHIVE.md)。

以下条目的证据类型及验证状态分别记录在条目中，未勾选项尚未完成修复验收。P1 优先处理账本或导入中的静默错误，P2 包括精度口径、诊断、隐私和测试可复现性。既有请求日志脱敏方案见 [0024 IBKR 请求日志 Token 脱敏](../design/0024-2026-11-12-ibkr-token-log-redaction.md)。2026-11-16 的实际 XML 离线核对及合成复现见 [IBKR 同步审阅](audits/2026-11-16-ibkr-sync-review.md)。

- [ ] **P1 统一 Tiger 暂存与导入的时间格式**

  暂存阶段将成交时间写成带 `T` 的日期时间文本，导入映射却只接受毫秒时间戳数字。经正常暂存流程写入的已成交订单会被判为无法识别，进而触发批次失败及清理。 [暂存时间转换](../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerStagingWorker.java) · [导入时间解析](../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java)

  验收需用合成订单贯通暂存和导入，确认订单成功生成交易，交易日期符合确定的时区口径。覆盖跨日时间、缺失或非法时间，并验证历史暂存记录能按已定义的兼容规则处理。

- [ ] **P2 对齐 IBKR Flex 错误码、分类与重试语义**

  客户端把 1005、1006、1012 分别描述为 Token 无效、限流、查询不存在，与官方的交割数据未就绪、FIFO 盈亏数据未就绪、Token 过期不一致；1006 还进入限流异常分支。适配器把所有客户端异常统一分类为 NETWORK。此项为源码与官方文档静态对照，尚未逐码运行响应复现。 [客户端错误处理](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrFlexClient.java#L303) · [官方错误码](https://www.ibkrguides.com/orgportal/performanceandstatements/flex3error.htm) · [审阅 R7](audits/2026-11-16-ibkr-sync-review.md#r7--p2flex-错误码说明与官方定义不符)

  验收需用模拟 HTTP 响应覆盖生成中、数据未就绪、Token 过期／无效、查询无效、限流和未知错误码，验证说明、认证／网络分类、重试及退出条件，并保证日志与异常链继续脱敏。本条尚未修复；下一步新增 design。
