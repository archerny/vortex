# IBKR 交易样例与同步导入审阅

审阅日期：2026-11-16。源码基线：`e6d5945`。本轮为分析与复现，未修改同步实现，未查询或写入账本数据库，未请求 IBKR 服务。

## 样例核对

离线读取 `target/ibkr-samples/` 下本次下载的 Trade Confirmation XML。文件 SHA-256 为 `2ec115850d1de8c7aa4c7446191fa73248168dde672dd332a59eaa638a20ef5c`。本文只保留统计，不记录账户、证券、订单编号或交易金额。

| 核对项 | 结果 |
| --- | --- |
| 报表与账户 | 1 份 FlexStatement、1 个账户 |
| 数据层级 | 491 条 SymbolSummary、538 条 Order、574 条 TradeConfirm |
| 订单资产 | 55 条股票、483 条期权；全部为美元，期权乘数均为 100 |
| 订单与成交关联 | 506 个订单各 1 笔成交，28 个各 2 笔，4 个各 3 笔；无孤立成交、重复订单 ID 或重复成交 ID |
| 逐单对账 | 数量、金额、佣金、附加费、proceeds、netCash 六项，订单值与所属成交之和均一致 |
| 日期与数量 | 无非法交易日、跨交易日订单或小数数量 |
| 事件分类 | 常规订单 277 条；指派 88 条、行权 2 条、到期 171 条，共 261 条 BookTrade |
| BookTrade 判定 | 空下单时间与空订单类型的判定，与成交明细的 transactionType 全部一致；同一订单内无混合 code |
| 行权／指派配对 | 文件内 45 条股票侧记录均有唯一期权候选，行权价及数量均吻合 |
| 费用精度 | 295 条订单的费用超过两位小数；佣金最高有 8 位小数 |

因此，不能把“574 笔成交只生成 538 条正式交易”本身视为漏单：当前正式交易明确采用订单粒度，且这份文件的汇总能完整对上。SymbolSummary 是另一层汇总，也不应再次生成交易。Order 层未暂存的 exchange、code、tradeID、execID、transactionType、brokerageOrderID、orderReference，在此文件中均为空，未发现由这几项造成的样例字段丢失。

## 发现

### R1 · P2：正式费用列会丢失样例中的佣金精度

- 触发：导入费用超过两位小数的订单。本文件有 295 条符合条件。
- 预期：保留来源精度，或明确约定逐单舍入方式及可接受的对账差额。
- 当前：导入 Worker 保留 `abs(commission) + abs(tradeCharge)` 的 BigDecimal 精度，但 [TradeRecord.java:92](../../backend/src/main/java/com/vortex/entity/TradeRecord.java#L92) 与 [V3:66](../../backend/src/main/resources/db/migration/V3__create_trade_records.sql#L66) 把 fee 定义为 `DECIMAL(12,2)`。现有后续迁移未扩展该列。
- 影响：按迁移声明的表结构落库，会逐单舍入，正式费用无法与原始值精确一致，后续使用这些费用计算的结果也会带入差额。暂存表使用文本，原始费用仍在暂存记录中。
- 证据类型：实际文件离线统计、源码及迁移静态核对；未在 PostgreSQL 实际插入。舍入行为依据 [PostgreSQL 18 Numeric Types](https://www.postgresql.org/docs/18/datatype-numeric.html#DATATYPE-NUMERIC-DECIMAL)。
- 待确认：账本是否接受逐单按分舍入。这是业务精度口径，不能在修复中自行决定；精度损失本身已确认。

### R2 · P1：同订单后续新增成交或修正会被直接跳过

- 触发：后续报表或另一日期窗口出现相同 orderID，但数量、金额或费用已经变化。
- 预期：重复的相同数据保持幂等；同 ID 的不同内容应更新、重建，或明确报冲突。
- 当前：[IbkrStagingWorker.java:51](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrStagingWorker.java#L51) 只判断订单是否存在，存在就跳过，不比较内容；新 tradeID 的成交仍可暂存。导入只读本批 PENDING 订单，正式表又按外部订单 ID 去重。[IbkrImportService.java:87](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportService.java#L87) · [IbkrImportWorker.java:88](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java#L88)
- 影响：新成交明细与旧正式交易可能不一致，批次却仍成功。拆窗时同一订单跨窗口出现也有同样风险。
- 验证：合成同 ID 订单先暂存数量 10，再传数量 20，第二次返回跳过、订单仓储只保存一次；新成交确认仍保存成功。
- 样例边界：文件有 32 个多笔成交订单，但没有同 ID 的不同快照，也没有跨日订单。因此本轮没有证据证明这份文件已经因此漏单，更不能据此断言数据库已有漏单。

### R3 · P1：期权关联可能选错，且受候选返回顺序影响

- 触发：同日、同标的、同事件类型存在其他券商记录，或多个不同期权合约候选。
- 预期：关联必须属于正确券商与合约；无法唯一确定时，应按明确规则暴露和处理。
- 当前：[候选查询 TradeRecordRepository.java:190](../../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java#L190) 无券商约束；单候选直接接受。多候选时，[IbkrImportWorker.java:173](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java#L173) 先取首个行权价匹配项，只在一个也找不到时才按数量匹配，而非同时核对。最终仍可取首条；没有唯一性检查。
- 影响：股票交易关联错误期权，影响事件追溯以及依赖关联的展示、核对。无候选时保留 ref_id=0，仅记警告；本批订单仍可保持 IMPORTED，成功判定不检查关联是否完整。
- 验证：合成两个相同行权价候选，只有第二个的数量吻合，仍选择第一个；交换候选顺序，关联结果改变。仅返回其他券商的候选时，也直接接受。
- 样例边界：文件内 45 组均唯一匹配。未读取数据库中的其他历史记录，不能证明实际数据库查询仍只有这些候选。此问题延续既有 TODO，本轮补充运行复现。

### R4 · P1：不支持的报表结构会被静默截断或当成零条成功

- 触发：多份 FlexStatement、多组 TradeConfirms，或 Flex 配置没有生成所需 Order 层／TradeConfirms 节点。
- 预期：处理全部明确支持的内容；不支持的结构应明确失败，不能静默丢弃数据。合法的空 TradeConfirms 仍可表示没有交易。
- 当前：[FlexQueryParser.java:122](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L122) 只读第一份 statement，第 135 行只读第一组 TradeConfirms；缺少节点时直接返回空结果。解析器不校验根节点或 TCF 类型。适配器成功判定只依赖已暂存订单的状态计数。[IbkrSyncAdapter.java:137](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapter.java#L137)
- 影响：第二份及以后的内容丢失；查询类型或导出层级配置错误时，也可能显示 COMPLETED / 0，而非配置错误。
- 验证：合成两份 statement 各一条订单，实际只返回一条；AF 类型且没有 TradeConfirms 的报表，经过真实解析器与模拟仓储的适配器返回成功 0；非空 TradeConfirm 但无 Order 的 TCF 报表同样返回成功 0。
- 样例边界：本文件只有一份 statement、一组 TradeConfirms，且同时有订单与成交层，未触发此问题。单账户支持范围可以保持，但应拒绝超出范围的输入。

### R5 · P1：数量转换会静默截断小数并允许整数溢出

**后续处理：已修复并完成内存回归验证。** 用户决定当前拒绝小数数量，不截断或舍入；实现按 [0027](../../design/0027-2026-11-16-ibkr-reject-fractional-quantities.md) 使用精确整数转换，同时拒绝零、非法、缺失和超范围值，沿用整批失败清理并保留失败原因。验收记录见 [待办归档](../TODO-ARCHIVE.md#已完成)。以下保留基线 `e6d5945` 的原始发现与复现结果，不代表修复后的当前行为。

- 触发：小数股数量，或超出 Integer 范围的来源数量。
- 预期：支持小数数量，或明确拒绝不支持的数量；不得改变数量后仍成功写入。
- 当前：[IbkrImportWorker.java:400](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java#L400) 使用 `BigDecimal.intValue()`，之后才取绝对值；正式实体也是 Integer。金额直接使用来源值，不随截断数量调整。
- 影响：例如合成数量 1.5 变成 1，金额仍为 1.5 对应的金额，持仓与单价、金额不一致。更大的整数也可能溢出为仍能通过正数约束的小整数。
- 验证：运行当前 mapper，合成数量 1.5 得到 1；4294967297 也得到 1。
- 样例边界：本文件数量均为整数且在范围内，未触发此问题。

### R6 · P2：正常解析会把真实账户 ID 写入 INFO 日志

**处置状态：当前接受，取消修复。** 2026-11-16，用户明确允许当前阶段在应用日志中打印账户 ID；[研发约定](../../AGENTS.md) 已同步记录此例外，处置记录见 [待办归档](../TODO-ARCHIVE.md#已取消或合并)。以下保留原审计口径及证据，不再将此行为列为当前待修复缺陷。密码、Token、密钥和交易明细不在此例外内。

- 触发：正常解析／同步真实报表。
- 原预期：仅记录脱敏标识与必要的计数、批次信息；现按上述用户决定允许打印账户 ID。
- 当前：[FlexQueryParser.java:170](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L170) 和 [IbkrSyncAdapter.java:281](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapter.java#L281) 都直接输出 accountId。请求 Token 脱敏的既有修复未覆盖账户 ID。
- 原影响判断：真实账户 ID 进入应用日志，与审计时的项目约定不一致；用户调整约定后，该行为属于当前允许范围。
- 验证：捕获解析器 INFO 日志，合成账户字符串原样出现。本轮真实文件仅由离线统计脚本读取，未交给会输出账户的应用解析链路。

### R7 · P2：Flex 错误码说明与官方定义不符

- 触发：服务返回 1005、1006、1012 等错误。
- 预期：错误说明、重试与认证／网络分类符合服务端实际语义。
- 当前：[IbkrFlexClient.java:323](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrFlexClient.java#L323) 把 1005 写成 Token 无效、1006 写成限流、1012 写成查询不存在。官方定义分别为交割数据未就绪、FIFO 盈亏数据未就绪、Token 已过期；实际 Token 无效为 1015。1006 还在第 310 行被当作限流异常处理。
- 影响：给出错误排查方向；本可等待数据就绪的情况会直接失败。适配器也将所有 FlexClientException 统一归入 NETWORK，不能准确区分认证问题。
- 证据类型：源码静态核对与 [IBKR 官方 Version 3 Error Codes](https://www.ibkrguides.com/orgportal/performanceandstatements/flex3error.htm) 对照；未模拟全部错误码响应，未请求真实服务。样例是成功报告，不涉及此路径。

## 运行验证

环境为 `.dev-environment.local.md` 指定的 `ubuntu-dev`，工作树 `/home/bytedance.guest/workspace/private/vortex`；既有 Temurin 25.0.5、Maven 3.10.0、Python 3.14。没有安装工具或新依赖，Maven 使用离线缓存；JVM 临时目录为 `/tmp`，编译与测试产物在 `backend/target/`，审计脚本在根 `target/ibkr-audit/`。

| 验证 | 命令／入口 | 退出状态与结果 |
| --- | --- | --- |
| 真实 XML 离线核对 | `python3 -B target/ibkr-audit/sample-check.py target/ibkr-samples/ibkr-trade-confirms-6521969318.xml` | 0；逐单六项汇总完全一致，统计见上表 |
| 既有 IBKR 合成单测 | `mvn -o -f backend/pom.xml -Dtest=IbkrFlexClientTest,IbkrSyncAdapterTest,IbkrStagingServiceTest,IbkrStagingWorkerTest,IbkrImportServiceTest,IbkrImportWorkerTest test` | 0；51 项通过，无失败、无错误、无跳过 |
| 新增合成复现 | `target/ibkr-audit/AuditProbe.java`，由既有 javac 编译，再运行 `com.vortex.sync.adapter.ibkr.AuditProbe` | 编译 0、运行 0；11 项断言通过，确认当前缺陷与控制场景 |

复现程序使用现有 `backend/target/classes` 和 `backend/target/dev-run/runtime-classpath.txt` 中的缓存依赖；执行 JVM 参数为 `-Djava.io.tmpdir=/tmp -XX:+EnableDynamicAgentLoading`。它直接调用真实解析器、暂存 Worker、映射及回填 Worker；仓储和网络客户端均为 Mockito 模拟，没有启动 Spring 应用、数据库或真实网络客户端请求。11 项包含首次暂存成功等控制断言，不代表 11 个独立缺陷。脚本和真实 XML 均受 `target/` 忽略规则保护。

未运行 `FlexQueryParserTest`：其中四项依赖被 Git 忽略、当前不存在的个人 XML 夹具，并打印交易记录；本轮使用独立合成复现覆盖相关边界，未将真实下载文件复制成测试资源。既有个人夹具问题已在 TODO 保留。

## 范围与限制

逐文件完整阅读的实现范围：

- `backend/src/main/java/com/vortex/sync/adapter/ibkr/` 下全部 13 个 Java 文件：客户端、配置、DOM 解析器、解析结果、两类原始模型、暂存服务与 Worker、导入服务与 Worker、异常、清理策略。
- 实体：`IbkrStagedOrder`、`IbkrStagedTradeConfirm`、`TradeRecord`；仓储：`IbkrStagedOrderRepository`、`IbkrStagedTradeConfirmRepository`、`TradeRecordRepository`；相关资产、币种、触发来源与引用类型枚举。
- 调用链：`BrokerSyncController`、`BrokerSyncBatchService`、`BrokerSyncService`、`SyncRequest`、`BrokerSyncAsyncExecutor`、`SyncBatchFailureHandler`、`SyncBatchCleanupService`。
- 迁移：V3、V20、V21、V22、V28 全文；另检索全部迁移，核对费用列是否被后续脚本扩展。
- 测试：上表六个 IBKR 测试类全文；`FlexQueryParserTest` 的控制流程及断言在隐藏个人字面值后阅读。
- 配置与现状文档：`application.properties`、资源配置 README、当前工作树的本地开发环境说明、`knowledge/README.md`、券商同步知识入口／生命周期／持久化／适配器契约／IBKR 专页、恢复指南、TODO。通过设计索引检索 0009、0012、0013、0018、0019 的相关决策段落，未逐篇重审这些历史方案全文。

本轮不构成全仓库或真实账本正确性证明。未覆盖 PostgreSQL 实际事务、唯一索引及异常提交时机的集成验证；未对运行中数据库与 XML 做对账，因此不能判断既有人工／历史导入记录是否会重复，也未证明历史盈亏或持仓正确。启动恢复、多实例并发、前端原始数据展示、其他券商、市场事件与持仓重算不在本轮完整审阅范围。未验证 XML 是否覆盖券商的全部历史交易或与用户要求的查询起止日期完全一致。

费用舍入为 schema 与官方规则推断，其他已标明的缺陷使用合成运行复现；实际文件核对是独立的只读数据分析，不是实际导入测试。

## 后续顺序

R5 已按用户决定实施整数数量限制并归档，R6 已按用户决定取消修复，均保留原审计证据；本轮审阅仍有 R1、R2、R3、R4、R7 共 5 项活动待办。后续先确定费用精度、订单粒度更新和期权关联的业务口径，再新增修复 design，优先处理 R2、R3、R4 的静默错误。R1 是这份真实样例按当前 schema 入库时即可遇到的精度问题。修复需要对应回归测试，涉及 schema 时须覆盖空库初始化与历史库升级；完成后还须独立复核完整差异与验收场景。

初次审阅只新增审计记录、更新 [TODO](../TODO.md)，并按用户决定在 AGENTS.md 中明确账户 ID 日志例外，没有新增已实现能力。后续 R5 的实现与验证另见 0027 和待办归档，相关 knowledge 已随修复同步；历史设计正文保持原样。
