# 测试有效性核对与清理

日期：2026-11-17。方案：[0039](../../design/0039-2026-11-17-test-suite-maintenance.md)。清理前版本：`4c876b2`。

## 结论与范围

未发现应整组删除的已下线功能测试。本次删除或合并 10 个已执行的重复／无独立契约价值用例，将另 4 个依赖个人 XML 的方法替换成两个入口的合成映射案例，并修正一处不再执行的旧级联查询模拟。金额、历史成本、历史锁定、同步一致性、失败恢复和并发防护均保留。生产业务代码、依赖、数据库迁移和前端代码没有变化。

逐文件阅读了以下 31 个后端测试类、6 份前端测试和 8 份 e2e 文件；对照了本次受影响的 SyncResult 工厂、批次失败处理、IBKR 解析／记录辅助转换／费用映射、Tiger 导入及市场事件当前入口与调用路径，核对 Maven、前端包和 Vitest 配置、单测规范及相关 knowledge／design。静态阅读、合成内存运行和既有数据库验收记录分开判断。本次不是对全部生产代码或全部迁移的完整审计。

| 已阅读目录 | 文件（省略共同目录） |
| --- | --- |
| `backend/src/test/java/com/vortex/controller/` | BrokerSyncControllerTest.java、MarketEventConflictAdviceTest.java |
| `backend/src/test/java/com/vortex/service/` | BrokerSyncBatchServiceTest.java、MarketEventHistoryServiceTest.java、MarketEventProcessingServiceTest.java、OrderedMarketEventRegressionTest.java、PositionServiceTest.java、TradeRecordServiceTest.java、TradeVerificationServiceTest.java |
| `backend/src/test/java/com/vortex/sync/core/` | BrokerSyncAsyncExecutorTest.java、BrokerSyncServiceTest.java、SyncBatchCleanupServiceTest.java、SyncBatchFailureHandlerTest.java、SyncBatchRecoveryRunnerTest.java、SyncResultTest.java |
| `backend/src/test/java/com/vortex/sync/adapter/ibkr/` | FlexQueryParserTest.java、IbkrDuplicateBatchTest.java、IbkrDuplicateConsistencyTest.java、IbkrFlexClientTest.java、IbkrImportServiceTest.java、IbkrImportWorkerTest.java、IbkrQuantityValidationTest.java、IbkrReportStructureTest.java、IbkrStagingServiceTest.java、IbkrStagingWorkerTest.java、IbkrSyncAdapterTest.java、TradeFeePrecisionTest.java |
| `backend/src/test/java/com/vortex/sync/adapter/tiger/` | TigerImportServiceTest.java、TigerImportWorkerTest.java、TigerStagingServiceTest.java、TigerTradeRecordMapperTest.java |
| `frontend/src/` | constants/__tests__/menuConfig.test.js、constants/__tests__/tradeConstants.test.js、pages/profit/chart-demo/__tests__/mockData.test.js、services/__tests__/tradeFeePrecision.test.js、utils/__tests__/marketEventError.test.js、utils/__tests__/tradeFee.test.jsx |
| `e2e/` | README.md、reset-dev-db.sh、check-market-event-history.sql、verify-fee-precision.py、FeePrecisionCheck.java、verify-ordered-market-events.py、OrderedMarketEventCheck.java、market-event-ui-server.mjs |

排除个人 XML、根目录 SQL 备份、下载样本和忽略的临时调查程序：它们不是可提交的合成回归，本次没有读取、改写或删除。未启动应用、浏览器、真实券商连接或数据库；e2e 工具仅阅读源码，真实事务、迁移和页面交互没有在本次重新运行验证。单测无法证明这些路径仍正确，原有 e2e 验证入口继续保留。

## 删除／合并对应

以下原位置均以 `4c876b2` 下同名文件及方法为依据，可用 `git show 4c876b2:<仓库相对路径>` 复核；链接指向清理后保留的断言。

| 原测试（共 10 个已执行案例） | 触发与原断言 | 判断及保留依据 |
| --- | --- | --- |
| SyncResultTest：GetterSetterTest.defaultConstructor、settersShouldWork，ToStringTest.shouldContainKeyFields（3） | 默认字段、逐字段写读、调试字符串含字段 | 纯存储与调试实现没有独立业务契约；移除模板断言。工厂的状态、数量、耗时、原因及兼容接口仍由 [6 个工厂案例](../../backend/src/test/java/com/vortex/sync/core/SyncResultTest.java#L19)验证。没有删除仍存在的三参数工厂测试。 |
| SyncResultTest：SuccessFactory3ParamTest.messageShouldBeEnglish、FailureFactoryTest.messageShouldBeEnglish（2） | 成功／失败工厂消息含英文前缀 | 前缀分别并入[成功](../../backend/src/test/java/com/vortex/sync/core/SyncResultTest.java#L25)和[失败](../../backend/src/test/java/com/vortex/sync/core/SyncResultTest.java#L90)工厂案例；保留券商、数量／错误、耗时断言。取消对整条消息不得含汉字的实现检查，错误原因本身允许来自业务说明；没有改变运行时消息。 |
| SyncBatchFailureHandlerTest.CleanupExhausted.retriesExactlyMaxAttempts（1） | 清理每次抛异常，检查恰好最大重试次数 | [allAttemptsFail_marksCleanupFailed](../../backend/src/test/java/com/vortex/sync/core/SyncBatchFailureHandlerTest.java#L125)使用相同故障路径，已有相同次数检查，并验证 CLEANUP_FAILED、原始／清理原因及不标记 FAILED，是严格包含关系。 |
| FlexQueryParserTest.parseInvalidXmlShouldThrow（1） | 非法 XML 抛异常 | [结构回归](../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrReportStructureTest.java#L53)在字符串与流两个入口覆盖同类语法错误异常，另验证 DOCTYPE 拒绝；保留缺层级的具体诊断测试。 |
| IbkrImportWorkerTest.FeeCalculationTest.shouldCalculateFeeCorrectly、shouldHandleNullFeeFields（2） | 两项负费用取绝对值相加；两项 null 为零 | [费用精度参数化案例](../../backend/src/test/java/com/vortex/sync/adapter/ibkr/TradeFeePrecisionTest.java#L43)经同一真实 importSingleOrder→映射→费用路径，6 项覆盖两项负值相加、跨位进位、长小数、大数、显式零、两项 null，精确比较 BigDecimal；[普通股票完整映射](../../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrImportWorkerTest.java#L116)仍检查普通费用。未改变舍入口径。 |
| TigerImportWorkerTest.PreFilterTerminalPersistsStaged.skippedBranchSavesStaged（1） | 同一合成订单、重复 ID 分支保存暂存行一次 | [SkippedPath.shouldSkipAlreadyImported](../../backend/src/test/java/com/vortex/sync/adapter/tiger/TigerImportWorkerTest.java#L124)同输入、同路径，验证相同保存次数，加上 SKIPPED、原因及禁止正式保存／历史写检查，严格覆盖被删项。 |

四个未纳入运行基线的个人方法 `parseRealFlexQueryXml`、`verifyOrderFieldsParsedCorrectly`、`verifyTradeConfirmFieldsParsedCorrectly`、`verifyBookTradeTradeConfirm` 不再读取本地资源或打印成交明细。替代的 [shouldParseSyntheticTradeFields](../../backend/src/test/java/com/vortex/sync/adapter/ibkr/FlexQueryParserTest.java#L23)用同一最小合成报表参数化运行字符串／流两个入口：

- 保留报表类型、查询、账户、起止日和生成时间，以及 Order／TradeConfirm 数量断言。
- 检查 Order 与普通 ExchTrade 的 ID、标的、方向、数量和价格等核心字段；Order 的数量、价格及佣金辅助转换精确比较 BigDecimal，普通成交验证日期／时间和开仓分类，BookTrade 验证类型与分类辅助方法。费用源字符串的其他断言仍由既有结构及精度测试负责，不将 BookTrade 分类检查描述为全字段映射检查。
- 继续保留合法零交易、缺少 TradeConfirms 的 UNRECOGNIZED 与原因断言；结构回归的 63 项未改。个人账户、真实成交快照数量不是稳定契约，不再断言。

[Maven 测试资源配置](../../backend/pom.xml#L86)显式排除被忽略的个人 XML，防止其进入测试产物；检查生成目录中没有该文件。原个人文件保留，没有复制或作为新夹具提交。测试现在无需资源文件、数据库或账户信息，默认命令不必筛选用例；没有执行新的干净检出，但运行内容和资源核对已证明解析组不再读取该文件。

[市场事件回归](../../backend/src/test/java/com/vortex/service/OrderedMarketEventRegressionTest.java#L67)不删除案例：原模拟的是已不再调用的级联查询，即使返回 later 对象也不能证明新规则。改为严格模拟实际历史查询，返回当前与后续事件，并捕获实际写入，核对仅生成一行、来源事件 ID、日期及数量，后续事件保持未处理。另一处实际使用的历史查询也取消 lenient，便于发现路径失效。

## 明确保留的回归

- 历史成本清仓归零、费用原始精度、整数股拒绝、同 ID 内容一致／冲突、跨窗口与批次失败清理：当前规则仍有效，不能因缺陷已修复删除。
- Controller 错误契约、Service 规则、Worker 单条行为、带真实组件的模拟调用链验证不同边界。批次重复与单字段重复测试不按名称合并；内存调用链不替代真实事务。
- 清理首次／后续重试成功、重试耗尽、中断及记录失败的二次异常继续保留；中断测试的非零退避用于真实触发中断处理。有效共用的 lenient 故障模拟不一概删除。
- Token 测试捕获异常堆栈是脱敏断言，保留；三参数工厂仍存在，保留兼容验证。期权回填歧义是独立未解决 TODO，未将现有单候选案例描述成已消歧。
- 前端常量、菜单、图表合成数据、费用原值与市场事件错误展示仍被当前页面使用，6 份测试未改。e2e 的真实事务、并发、迁移与 UI 工具仍有独立价值，未删。

## 实际运行与案例核对

环境：`ubuntu-dev`，工作树 `/home/bytedance.guest/workspace/private/vortex`；Java 25.0.5（编译目标 17）、Maven 3.10.0、Node 26.13.0、npm 11.20.0。沿用现有工具和缓存，不安装依赖或工具。命令从该工作树根目录执行，`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`；日志、报告和对照清单在工作树。仓储／网络全部模拟，不使用任何应用 profile。

| 阶段 | 实际命令 | 结果与日志 |
| --- | --- | --- |
| 清理前 | `mvn -o -f backend/pom.xml '-Dtest=*Test,!FlexQueryParserTest' test` | 501 项通过，退出 0；`backend/target/test-cleanup-before.log` |
| 清理前解析合成组 | `mvn -o -f backend/pom.xml '-Dtest=FlexQueryParserTest#parseEmptyTradeConfirms+parseXmlWithoutTradeConfirms+parseInvalidXmlShouldThrow' test` | 3 项通过，退出 0；`backend/target/test-cleanup-before-parser.log`。另外 4 个个人方法明确未执行。 |
| 清理后最终全量 | `mvn -o -f backend/pom.xml test` | 496 项，失败／错误／跳过均为 0，BUILD SUCCESS；`backend/target/test-cleanup-final.log`，完成于 2026-11-17 20:23:56 +08:00。 |
| 前端清理前、后 | `npm --prefix frontend test` | 两次均 6 文件、54 项通过，退出 0；`frontend/test-cleanup-before.log`、`frontend/test-cleanup-after.log`。前端没有改动。 |

基线清单 `backend/target/test-cleanup-before-manifest.json` 为 504 个实际唯一案例，不含 4 个个人方法。最终清单 `backend/target/test-cleanup-final-manifest.json` 为 496 个案例，核对全部 31 个源测试类和 306 个注解测试方法均有执行记录，包括全限定名注解、嵌套类与参数化实例。用本轮日志的 `Running` 套件选取 Surefire XML，避免将删除用例残留的旧 XML 算入本轮。逐项比较结果写入 `backend/target/test-cleanup-case-comparison.json`：仅上述 10 个执行案例移除、2 个合成入口案例增加，无其他案例缺失或新增跳过。净减少 8 个实际案例，不将总数本身当成覆盖充分或提速证据。

全量运行仍出现已有 Surefire 退出握手等待 30 秒提示，随后 BUILD SUCCESS；没有调整框架、超时、并行度或跳过测试来掩盖它。本次没有性能优化结论。没有运行数据库集成、浏览器交互或前端构建：对应生产、迁移、页面和依赖均未改，仍以各自既有验收记录和保留的入口为限。

## 文档与独立复核

更新 [单测维护规范](../unit-test-spec.md#测试维护与清理)、[当前运行入口](../../e2e/README.md#ordered-market-event-check)及 [IBKR 当前验证说明](../../knowledge/broker-sync/brokers/ibkr.md#验证边界)。其他业务和架构知识无需调整，因为运行规则、数据结构和接口均未改变。个人夹具 TODO 的原始问题及完成证据保留到 [归档](../TODO-ARCHIVE.md)，其他待办不改变验收范围。

涉及账本及同步的测试差异、最终运行清单和文档由未参与实现的 Agent 独立复核通过。复核中收窄了 BookTrade 断言范围的文案，并修正源扫描漏计全限定名注解的问题；最终 31 类、306 方法与实际执行清单一致，496 案例的增删均有对应依据。原 TODO 及历史归档保留完整，当前 3 项待办不变。8 份 Markdown 的 163 处本地链接和源码行号有效，新增文件空白检查及 `git diff --check` 通过，没有剩余可行动问题。
