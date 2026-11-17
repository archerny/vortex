# IBKR 同步

## 拉取与暂存

`IbkrSyncAdapter` 使用 Trade Confirmation Flex Query。`IbkrFlexClient` 先调用 `SendRequest`，再轮询 `GetStatement` 获取 XML。适配器按日期范围分窗，窗口末日最多取起始日加 365 天，下一窗从上一窗末日的下一天开始。请求日期使用 `yyyyMMdd`。适配器使用 `broker.ibkr.flex-token` 和 `broker.ibkr.trade-confirm-query-id`，配置步骤见 [IBKR 接入指南](../../../docs/broker-sync/brokers/ibkr/README.md)。

`FlexQueryParser` 校验单账户、单份规定结构的 TCF 报表。`Order` 写入 `ibkr_staged_orders`，`TradeConfirm` 写入 `ibkr_staged_trade_confirms`，`SymbolSummary` 不导入。暂存 Worker 按 `order_id` 和 `trade_id` 分别去重。前端可按批次查看两表，查询接口为 `GET /api/sync/ibkr/orders` 和 `GET /api/sync/ibkr/trade-confirms`。见 [Flex 客户端](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrFlexClient.java)、[XML 解析器](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java)和 [原始数据控制器](../../../backend/src/main/java/com/vortex/controller/IbkrStagedDataController.java)。

## 支持的报表结构

两个解析入口都只接受 `FlexQueryResponse type="TCF" → FlexStatements → FlexStatement → TradeConfirms` 的直接父子结构，三个内部层级各恰好一个；不再取第一份或在缺失时返回空成功。`FlexStatements.count` 可省略，提供时必须为 `1`。报表账户必须非空，来源 Order／TradeConfirm／SymbolSummary 如指定非空 accountId，去首尾空白后须与报表账户一致，不改写字段。

TradeConfirms 只接受直接的属性式 Order、TradeConfirm、SymbolSummary。未知节点、嵌套包装、记录子元素及非空文本明确拒绝，允许格式空白、注释及额外属性。非空报表须同时包含 Order 和 TradeConfirm；仅订单、仅成交或仅汇总均不完整。合法零交易是完整封套、非空账户及没有记录的 TradeConfirms，不是缺少节点。

结构错误按 UNRECOGNIZED 返回固定的 `Unsupported IBKR TCF report structure: …` 原因，不输出账户或来源值。所有窗口解析完成后才进入暂存；任何窗口不支持都会使当前批次失败并沿既有清理流程处理，原成功批次保留。日期分窗仍可返回多个文件，每份分别校验；账户校验限每份报表，不新增跨窗口／独立请求的账户绑定，不证明成交集合完整或券商数据覆盖全部历史。 [0034 方案](../../../design/0034-2026-11-17-ibkr-single-account-tcf-validation.md) · [结构回归](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrReportStructureTest.java) · [故障处理](../../../docs/broker-sync/recovery.md#ibkr-报表结构不受支持)

2026-11-17 在 `ubuntu-dev` 验证上述结构规则及相关同步测试共 300 项通过，旧解析器的 3 项纯合成方法另行运行通过，Maven 均退出 0；完整组有既有 Surefire 退出握手提示，3 项单独运行正常结束。新增结构 63 项及批次 8 项使用合成 XML 与 Mockito 仓储／网络，覆盖拒绝、合法空报表、重复、重试、后续窗口失败及旧批次不变；没有真实数据库事务或券商返回验证，4 个个人夹具方法仍未运行。完整命令、历史复现和限制见 [完成归档](../../../docs/TODO-ARCHIVE.md)。

## 导入映射

当前业务前提：按用户于 2026-11-17 的决定，默认信任 Flex 报表中的订单及成交已执行完成，按报表记录导入，不额外查询原始委托量、剩余量或最终状态，不维护未完成订单的持续执行与合并。成交带有 `Code=P` 或同订单包含多笔成交，不作为拒绝导入的依据。这是项目采用的前提，不是已验证的券商终态保证；同 ID 的重复记录按下文执行内容一致性校验。 [0031 决策](../../../design/0031-2026-11-17-trust-ibkr-reported-completion.md) · [0032 实施方案](../../../design/0032-2026-11-17-ibkr-duplicate-content-consistency.md)

决策原因：用户的实际使用范围是同步历史交易记录，系统关注已经发生的成交，不管理实时挂单。基于这一使用范围，采用上述“默认已执行完成”的前提，不引入实时订单执行跟踪；此处描述业务使用范围，没有新增日期或订单状态校验。

- 正式交易以订单为粒度。`STK` 映射到股票，`OPT` 按 `putCall` 的 `C` 或 `P` 映射到期权。期权 symbol 使用 `description` 的首个词作为标的，再拼到期日、方向和去掉尾零的行权价。
- `tradeDate` 使用 `yyyyMMdd`。`BUY`、`SELL` 分别映射为买卖方向。`CNH` 与 `CNY` 都映射为 `CNY`，`USD` 和 `HKD` 保持原值。金额取原始 `amount` 的绝对值，费用为佣金和附加费的绝对值之和。数量先取 BigDecimal 绝对值，只接受非零且不超过 2147483647 的数值整数，再用 `intValueExact()` 无损转换；拒绝小数、零、缺失、非法与超范围数量，不截断或舍入。
- `order_time` 和 `order_type` 同时为空时，订单进入 BookTrade 分支，交易触发来源为 `OPTION`。其他订单使用 `MANUAL/NONE`。BookTrade 按 `order_id` 读取第一条成交确认的 `code`，按分号拆分后精确匹配。`Ex`、`MEx`、`AEx` 的行权判定优先于 `Ep` 的到期判定，后者优先于 `A`、`GEA` 的指派判定。确认记录缺失、code 为空或无法识别时，该订单失败并最终触发整批清理。
- 期权事件初始 `trigger_ref_id` 为 0。导入后，对本批股票侧行权或指派记录回填关联的期权交易 ID。期权候选按事件类型、标的和交易日查询，可能来自其他批次。多候选时优先匹配行权价，其次匹配数量，仍无法区分时取首条。找不到候选则保留 0 并记录警告。

映射和回填见 [导入 Worker](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java)、[导入服务](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportService.java)及 [交易仓储查询](../../../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java)。

费用计算继续使用原始十进制文本构造 `BigDecimal`，按既有绝对值之和计算，不设置固定小数位或有限有效位数。V29 解除正式费用列的两位限制，保存、读取和分币种汇总均保留来源数值精度；前端展示与编辑也使用原始费用精度。不修复或重放历史数据，费用组成／币种规则保持原样；当前重复记录规则见下文。 [0029 费用精度方案](../../../design/0029-2026-11-17-preserve-trade-fee-precision.md) · [0030 展示修订](../../../design/0030-2026-11-17-display-exact-trade-fees.md) · [V29](../../../backend/src/main/resources/db/migration/V29__preserve_trade_fee_precision.sql)

## 重复记录内容一致性

| 检查入口 | 比较范围 |
| --- | --- |
| 订单暂存，按 `orderID` | 既有暂存行实际保存的全部来源字段；不新增 Order 层未保存的 exchange／code |
| 成交暂存，按 `tradeID` | 全部已保存来源字段，包含父 orderID、执行标识及 Code |
| 正式入库，按 `external_broker + external_id` | 已有正式记录可表达的业务投影：券商、日期、证券、方向、数量、价格、金额、费用、币种及触发类型／引用类型 |

新 ID 正常处理。相同 ID 只有内容一致才跳过，不覆盖原记录或改动其批次；不同内容按 UNRECOGNIZED 报错，整批失败并仅清理本批新增的数据。暂存冲突中止 STAGING，正式入库冲突沿用独立失败标记、适配器检查及框架清理。此前成功批次保留，同一冲突直接重试仍失败；清理失败继续进入 CLEANUP_FAILED。

比较忽略处理状态、批次、错误说明、数据库主键及审计时间。数值用完整精度 `BigDecimal.compareTo`，尾零和科学计数法差异不冲突，不舍入或忽略正负号。普通文本去首尾空白，空白与 null 等价；缺失数值不等于零；Code 按分号拆成集合比较。订单 orderID、成交 tradeID 及其父 orderID 必须存在且不含首尾空白，异常标识直接拒绝，不修剪后保存，避免绕过去重。诊断包含已有内部行引用和差异字段名，不打印字段前后值；正式入库原有的软删除、用户策略及后续回填关联不被重置。 [0033 标识补充](../../../design/0033-2026-11-17-ibkr-strict-duplicate-identifiers.md)

暂存来源一致时不重新比较用户后来编辑的正式交易。缺少来源暂存而命中正式记录时，兜底仅比较正式表的业务投影，不能核对未保存的账户、费用组成或原始符号；既有单价／金额固定小数位或旧费用舍入也可能导致拒绝，不作自动修复。新 tradeID 仍作为新明细处理，不额外证明成交集合完整性或订单终态；不自动合并后续成交、不覆盖或重算历史账本。 [比较实现](../../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrRecordContent.java) · [0032](../../../design/0032-2026-11-17-ibkr-duplicate-content-consistency.md) · [冲突排查](../../../docs/broker-sync/recovery.md#ibkr-重复记录内容冲突)

[重复内容回归](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrDuplicateConsistencyTest.java) 和 [批次调用链回归](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrDuplicateBatchTest.java) 使用合成数据与 Mockito 仓储／网络。2026-11-17 在 `ubuntu-dev`，新增 115 项及相关测试共 229 项全部通过、Maven 退出 0；相关整组运行有既有 Surefire 退出握手等待提示，新增 115 项单独运行正常结束。覆盖全部已持久化来源字段、规范化、异常 ID、正式投影、重复及冲突、整批清理、重试和原成功批次逐字段不变。未连接真实数据库、券商或个人 XML，不能把内存调用链验证表述为真实事务或并发唯一约束验证。完整命令、结果和验证边界见 [完成记录](../../../docs/TODO-ARCHIVE.md)。

## 数量限制与失败说明

当前不支持小数股或小数合约数量。`100`、`100.000`、`-100.000` 都可按数值整数导入；`1.5`、`-1.5`、`0.25` 等非整数以 UNRECOGNIZED 拒绝，错误说明为 `Fractional IBKR quantities are not supported; no truncation or rounding is allowed`。原始文本在解析和暂存时保留，不修改源数量、价格或金额。此检查针对正式导入所使用的订单汇总数量，成交确认仍为原始明细暂存。

数量不支持的订单不会保存正式交易。ImportService 在独立事务中记录失败，Adapter 沿用失败计数触发整批清理；本批其他已导入订单也会被清理，其他批次不受影响。Adapter 在清理前按批次读取首条 FAILED 记录，将其已有错误附在批次摘要的 `first failure:` 后，清理后仍能查看原因；没有可用说明时保留计数摘要。同样的数据重新同步仍会拒绝。重复订单先按上文校验内容，本次不修正历史账本。数量限制设计与未来升级边界见 [0027](../../../design/0027-2026-11-16-ibkr-reject-fractional-quantities.md)。

## 验证边界

请求 URL 的 DEBUG 日志统一由 `executeHttpGet` 输出，其中 Token 参数通过 `maskToken` 隐藏。非法请求配置使用固定错误消息，避免原始 URI 异常携带 Token。客户端回归测试使用模拟 HTTP 响应和合成 Token，覆盖 SendRequest、GetStatement 两阶段，并核对非法 URL 的异常链。 [日志回归测试](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrFlexClientTest.java) · [日志修复方案](../../../design/0024-2026-11-12-ibkr-token-log-redaction.md)

[适配器测试](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapterTest.java)和 [导入 Worker 测试](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrImportWorkerTest.java)覆盖分窗、失败残留、映射与 BookTrade 边界。[数量回归测试](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrQuantityValidationTest.java)覆盖整数、小数、非法值、溢出，以及真实解析／暂存／导入／异步执行／清理组件配合模拟仓储的调用链。2026-11-16 在 `ubuntu-dev` 运行数量与相关同步测试共 104 项，全部断言通过，Maven 退出 0；未连接真实数据库或账户，不代表已验证数据库事务。解析器测试中部分用例依赖被 Git 忽略的本地 XML，本轮未运行这些个人夹具用例。完整命令及运行限制随数量问题的验收记录保留。

历史方案见 [Flex Web Service](../../../design/0009-2026-04-14-ibkr-flex-web-service.md)、[BookTrade](../../../design/0012-2026-04-18-ibkr-booktrade-mapping.md)、[IBKR 暂存](../../../design/0013-2026-04-21-ibkr-staging-schema.md)和 [原始数据查看](../../../design/0015-2026-04-21-sync-staged-data-viewer.md)。

2026-11-17 在 `ubuntu-dev` 运行 [费用精度回归](../../../backend/src/test/java/com/vortex/sync/adapter/ibkr/TradeFeePrecisionTest.java) 及相关 IBKR 导入、Tiger 映射、交易和市场事件服务单测，展示修订后共 86 项全部通过，费用用例 10 项。单测全部使用内存模拟仓储；0029 已通过 [独立迁移脚本](../../../e2e/README.md#fee-precision-migration-check) 在 PostgreSQL 18.6 临时集群验证实际落库和汇总，复现旧 V28 的两位舍入并确认 V29 无此损失。0030 未修改迁移或 ORM，沿用该隔离验证；临时服务已停止且清理。未执行真实券商同步、个人 XML 导入或历史账本对账。
