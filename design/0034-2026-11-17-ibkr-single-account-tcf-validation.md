# IBKR 单账户 TCF 报表结构校验

## 背景与目标

用户确认采用简单、明确的支持范围：只导入单账户、单份规定结构的 TCF 报表；超出范围或缺少必要层级时报错，合法的零交易报表仍可成功。当前解析器取第一份 FlexStatement／TradeConfirms，缺失时返回空结果，错误类型及仅有成交明细的报表会被当成成功零条。

沿用 [0009](0009-2026-04-14-ibkr-flex-web-service.md)、[0013](0013-2026-04-21-ibkr-staging-schema.md) 的订单汇总入账、成交明细暂存与属性式 XML 映射。旧方案中的示例并非现行校验契约，本方案明确要求实际使用的 `type="TCF"`。沿用 [0031](0031-2026-11-17-trust-ibkr-reported-completion.md) 的历史记录已执行完成前提，以及 [0032](0032-2026-11-17-ibkr-duplicate-content-consistency.md)／[0033](0033-2026-11-17-ibkr-strict-duplicate-identifiers.md) 的重复内容和标识校验。

## 范围与设计

- 两个解析入口共用同一结构校验。根元素必须是 `FlexQueryResponse`，类型必须为 `TCF`。仅支持直接父子关系 `FlexQueryResponse → FlexStatements → FlexStatement → TradeConfirms`；每层恰好一个所需子节点。`FlexStatements.count` 可省略，若提供必须是 `1`，不得与实际结构矛盾。
- FlexStatement 必须包含非空 accountId。Order、TradeConfirm 和被忽略的 SymbolSummary 如提供非空 accountId，须与报表账户一致；比较时去首尾空白，但不改写来源字段。账户范围只限单份报表，不新增跨请求的账户绑定或历史账户迁移。
- TradeConfirms 仅支持直接的 Order、TradeConfirm、SymbolSummary 属性式记录。其他层级不允许额外元素；未知节点、嵌套包装和非空文本明确拒绝，避免静默遗漏。XML 注释及格式空白可接受，来源的额外属性不影响现有字段映射。
- 合法空报表必须保留上述完整结构及账户，TradeConfirms 中没有任何记录。非空报表必须同时具有 Order 和 TradeConfirm；仅成交、仅订单或仅汇总均视为导出层级不完整，不得成功零条。SymbolSummary 不导入。
- 本次校验结构及账户边界，不增加订单终态、成交金额对账或订单与成交集合完整性证明；不因 Code=P 或同订单多成交拒绝。日期分窗仍按顺序下载，每个返回文件分别校验；一个批次可以有多个日期窗口，这与一个文件中多份 statement 不同。
- 结构不支持时抛出 UNRECOGNIZED 分类异常，使用固定、可操作的原因，不输出账户、查询名称、未知节点名称或 XML 值。保留既有 XML 语法错误与 XXE 禁用行为，不扩大修改 Flex 客户端错误码分类。

## 调用方、历史数据和失败处理

解析全部日期窗口后才暂存。任一窗口结构不合规时停止本批，不进入暂存／导入，沿现有异步失败与整批清理流程结束；不能标记 COMPLETED。失败原因保留在批次摘要，清理失败仍进入 CLEANUP_FAILED，已有成功批次保持不变。合法空报表仍为成功零条，正常报表与完全相同报表重复同步保持原行为。

输入契约收紧：原来容忍的直接挂载 FlexStatement、缺少账户、未知层级及缺失成交／订单层的输入将被拒绝，需修正 Flex 查询配置后重新提交。HTTP 请求／响应结构、数据库、价格与费用计算、前端和依赖不变，无迁移、历史回填或重算。

## 实现步骤

1. 写本方案和索引，追加 TODO 范围确认，保留原发现及验收要求。
2. 新增可提交的纯合成解析回归，先在旧实现上运行并确认捕获静默截断和成功零条。
3. 在解析器落实单份、单账户与完整层级校验；更新现有调用链回归中简写的 XML 为规定结构，不修改旧断言口径。
4. 用现有内存批次测试覆盖合法空／非空、同报表重复、非法输入、后续窗口失败、失败重试和旧成功批次逐字段不变；所有 Repository 和网络使用 Mockito，不连接数据库或券商。
5. 在 `.dev-environment.local.md` 指定的 ubuntu-dev 使用既有工具和 Maven 离线缓存运行新增及相关同步测试，独立复核完整差异与验收场景。
6. 同步当前知识、配置与恢复指南、审计后续；全部验收通过后把完整 TODO 移至归档。

## 验证与验收

- 两个解析入口覆盖合法非空、合法空、注释／空白、可省略的 count、来源额外属性；保持来源文本精度和 Code 原样。
- 拒绝错误根／类型、缺少或多份容器／statement／TradeConfirms、第一份空而第二份非空、错误 count、账户缺失或混用、未知或嵌套节点、仅订单／成交／汇总、非法 XML 及 DOCTYPE。
- 实际解析至异步执行及清理的内存链路：正常及空报表 COMPLETED；非法报表 FAILED 且诊断为 UNRECOGNIZED，未产生新暂存或正式记录，原成功批次逐字段不变；跨窗口和重试也不静默成功。既有清理失败、重复一致性、数量和费用精度回归继续通过。
- 本次不验证真实 PostgreSQL 事务、唯一约束或实际 IBKR 返回，不读取个人夹具。原解析测试对个人 XML 的依赖属于独立 TODO，本次新增独立的合成测试，不顺带改动该问题。
- 检查本地文档链接、新增文件与 `git diff --check`。没有前端、依赖或数据库变更，不运行无关构建或迁移。

## 当前知识交付

更新 `knowledge/broker-sync/brokers/ibkr.md`、`knowledge/broker-sync/adapter-contracts.md`、`docs/broker-sync/brokers/ibkr/README.md` 和 `docs/broker-sync/recovery.md`，说明规定结构、零交易判定、单账户边界、诊断及纠正配置后重试。TODO 及审计记录保留原发现并记录本次收窄支持范围的原因、运行证据与限制，无待定业务问题。
