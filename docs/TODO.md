# 待办事项

本文件只维护仍需处理的事项，包括待确认、受阻和待验证的事项。已完成、已取消或已合并的记录见 [待办归档](TODO-ARCHIVE.md)。

以下条目的证据类型及验证状态分别记录在条目中，未勾选项尚未完成修复验收。P1 优先处理账本或导入中的静默错误，P2 包括精度口径、诊断、隐私和测试可复现性。既有请求日志脱敏方案见 [0024 IBKR 请求日志 Token 脱敏](../design/0024-2026-11-12-ibkr-token-log-redaction.md)。2026-11-16 的实际 XML 离线核对及合成复现见 [IBKR 同步审阅](audits/2026-11-16-ibkr-sync-review.md)。

- [ ] **P1 统一 Tiger 暂存与导入的时间格式**

  暂存阶段将成交时间写成带 `T` 的日期时间文本，导入映射却只接受毫秒时间戳数字。经正常暂存流程写入的已成交订单会被判为无法识别，进而触发批次失败及清理。 [暂存时间转换](../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerStagingWorker.java) · [导入时间解析](../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java)

  验收需用合成订单贯通暂存和导入，确认订单成功生成交易，交易日期符合确定的时区口径。覆盖跨日时间、缺失或非法时间，并验证历史暂存记录能按已定义的兼容规则处理。

- [ ] **P1 补齐历史交易改动和跨标的事件的重算范围**

  普通交易新增、修改或软删除后，后续市场事件的派生交易仍保留旧结果。事件重算也只查询初始标的集合，未继续追踪代码变更或分红引出的新标的。例如已有 100 股、后续两倍拆股，再补录拆股前的 100 股买入，当前可能得到 300 股。另一个场景是 A 变更为 B 后 B 再拆股，补录 A 的早期拆股只重算到 A→B，B 的拆股结果仍旧。 [普通交易写入](../backend/src/main/java/com/vortex/service/TradeRecordService.java) · [事件重算](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [代码变更事件查询](../backend/src/main/java/com/vortex/repository/SymbolChangeEventRepository.java)

  验收需覆盖补录、修改和软删除历史交易，以及 A→B→后续拆股或分红的依赖链。第一种合成场景的最终持仓应为 400 股。修改标的、券商或日期时，新旧受影响范围均须与从原始记录完整重算的结果一致，重复重算应得到相同结果，处理失败时须保持事务一致性。

- [ ] **P1 消除清仓后的平均成本残差**

  卖出时先将平均成本舍入为 6 位小数，再减记成本池，完全清仓时没有归零。合成场景中先买 10,000 股、单价 1，再买 20,000 股、单价 2，全部卖出后成本池会剩下 −0.01。重新买入 1 股、单价 10，后续代码变更会按 9.9900 转移成本。 [成本池计算](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java)

  验收需明确成本金额和单价的精度及舍入时点。上述场景重建持仓后，每股成本应恢复为 10.0000，代码变更前后的总成本应一致。还需覆盖多次部分卖出、全部卖出和重新建仓，确认历史残差不会累积到新持仓。

- [ ] **P1 明确 IBKR 期权关联回填的匹配范围与歧义处理**

  股票侧期权交易的候选查询未限定券商，多个候选经过行权价、数量匹配后仍可能直接取首条。同一天、同一标的出现不同券商或多个合约的行权、指派记录时，关联可能指向错误的期权交易，候选返回顺序也会影响结果。 [候选查询](../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java) · [关联回填与消歧](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java)

  验收需用合成的跨券商、不同到期日、相同行权价和相同数量记录覆盖唯一匹配、无匹配与多候选。成功回填须能证明关联属于正确的券商和期权合约。交换候选顺序或重试时结果应一致，无法唯一确定的关联应按明确的设计暴露并处理。

  2026-11-16 复核：在 `ubuntu-dev` 使用真实 Worker、Mockito 仓储和合成记录运行复现。相同行权价的两个候选中，即使只有第二个数量吻合，仍选择第一个；交换候选顺序后结果改变。仅返回其他券商候选时也直接接受。真实 XML 文件内的 45 组配对均唯一，但未核对数据库历史候选，不能据此证明线上关联正确。详见 [审阅 R3](audits/2026-11-16-ibkr-sync-review.md#r3--p1期权关联可能选错且受候选返回顺序影响)。本条继续未勾选，下一步明确匹配及无匹配处理口径，先写方案再修复。

- [ ] **P1 处理 IBKR 同订单的后续成交与内容修正**

  暂存仅按 orderID 是否存在决定跳过，不比较内容。后续同 ID 订单的数量、金额或费用变化不会更新正式交易，新 tradeID 的成交却可能继续进入暂存表，造成明细与账本不一致，批次仍成功。合成数量 10→20 的同 ID 订单已在 `ubuntu-dev` 复现第二次跳过；本次真实文件没有同 ID 不同快照，未证明已有账本发生漏单。 [暂存去重](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrStagingWorker.java#L51) · [审阅 R2](audits/2026-11-16-ibkr-sync-review.md#r2--p1同订单后续新增成交或修正会被直接跳过)

  验收需先明确订单快照更新、跨窗口成交和券商修正的业务键及处理方式。覆盖完全相同报表重复同步、部分成交后新增成交、跨日／跨窗口同订单、费用修正、失败后重试，确认不重复、不静默漏更新，正式交易与成交明细可对账。涉及替换历史数据时须验证批次失败恢复及后续重算。本条尚未修复；下一步新增 design。

- [ ] **P1 校验 IBKR 报表结构及导出层级**

  解析器只读第一份 FlexStatement 和第一组 TradeConfirms；缺少节点时返回空结果，不校验报告类型。合成两份 statement 只解析一份；不含所需节点的 AF 报表、含成交但无订单层的 TCF 报表均可由适配器报告成功 0。上述结果在 `ubuntu-dev` 通过真实解析器与模拟仓储复现；本次真实文件的结构完整，未触发。 [解析入口](../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L107) · [审阅 R4](audits/2026-11-16-ibkr-sync-review.md#r4--p1不支持的报表结构会被静默截断或当成零条成功)

  验收需区分合法零交易与缺少导出层级、错误查询类型、异常响应和不支持的多报表结构。明确支持范围并完整处理，或清楚报错；不得静默丢弃后续内容。用可提交的合成 XML 验证完整解析→暂存／导入入口的结果，覆盖第一份为空而后续非空，以及非空成交明细但无订单层。本条尚未修复；下一步新增 design。

- [ ] **P1 禁止 IBKR 数量转换静默截断或溢出**

  `BigDecimal.intValue()` 会将小数股截为整数，也可能让超范围值溢出为仍合法的正整数。当前 mapper 在 `ubuntu-dev` 将合成数量 1.5 和 4294967297 均转换为 1，来源金额没有同步改变。真实文件均为范围内整数，未触发。 [数量转换](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java#L400) · [审阅 R5](audits/2026-11-16-ibkr-sync-review.md#r5--p1数量转换会静默截断小数并允许整数溢出)

  验收需先决定是否支持小数股。支持时贯通实体、数据库、接口及持仓计算精度；不支持时明确拒绝，禁止静默转换。覆盖小数、零、正负边界、超范围数量及合法整数，保证数量与金额一致，并验证错误触发预期批次处理。本条尚未修复；下一步新增 design。

- [ ] **P2 明确并实现 IBKR 费用精度与舍入口径**

  本次 XML 的 295 条订单费用超过两位小数，佣金最多 8 位；当前 mapper 保留源精度，正式表 fee 却为 `DECIMAL(12,2)`，按声明 schema 写入将逐单舍入。此为文件统计及静态 schema 结论，未实际插库。是否接受按分舍入仍待用户确认。 [费用列](../backend/src/main/resources/db/migration/V3__create_trade_records.sql#L66) · [审阅 R1](audits/2026-11-16-ibkr-sync-review.md#r1--p2正式费用列会丢失样例中的佣金精度)

  验收需明确存储、计算、展示的精度与舍入时点。以含 8 位小数佣金的合成订单验证保存后读取及汇总对账；若保留两位，应明确并验证允许的对账差额，不能依赖未说明的数据库舍入。若修改 schema，新增迁移并验证空库、历史库升级及恢复。本条待业务口径确认，尚未修复。

- [ ] **P2 对齐 IBKR Flex 错误码、分类与重试语义**

  客户端把 1005、1006、1012 分别描述为 Token 无效、限流、查询不存在，与官方的交割数据未就绪、FIFO 盈亏数据未就绪、Token 过期不一致；1006 还进入限流异常分支。适配器把所有客户端异常统一分类为 NETWORK。此项为源码与官方文档静态对照，尚未逐码运行响应复现。 [客户端错误处理](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrFlexClient.java#L303) · [官方错误码](https://www.ibkrguides.com/orgportal/performanceandstatements/flex3error.htm) · [审阅 R7](audits/2026-11-16-ibkr-sync-review.md#r7--p2flex-错误码说明与官方定义不符)

  验收需用模拟 HTTP 响应覆盖生成中、数据未就绪、Token 过期／无效、查询无效、限流和未知错误码，验证说明、认证／网络分类、重试及退出条件，并保证日志与异常链继续脱敏。本条尚未修复；下一步新增 design。

- [ ] **P2 用可提交的合成数据替换解析测试对个人夹具的依赖**

  `FlexQueryParserTest` 从 classpath 读取一个被 Git 忽略的个人 XML 文件，并断言该资源存在。干净检出缺少资源会导致测试失败，开发者还需要自行提供账户样本。 [解析测试](../backend/src/test/java/com/vortex/sync/adapter/ibkr/FlexQueryParserTest.java) · [个人夹具忽略规则](../backend/.gitignore)

  验收需使用可提交的合成 XML 或测试内构造的数据，覆盖当前解析断言和必要的异常输入。干净检出应能离线运行解析测试，并且夹具、断言及测试输出均使用合成数据。
