# 待办归档

这里保留已完成、已取消或已合并事项的背景、处理历史和验证证据。当前需要继续处理的事项见 [待办事项](TODO.md)，维护规则见 [项目研发约定](../AGENTS.md#todo-维护与完成)。

最近归档日期：2026-11-17。进展记录保留当时的状态，以条目最后的完成或处置记录为准；取消、合并不代表已修复并通过验收。

## 已完成

- [x] **P1 市场事件改为顺序处理并锁定关联证券历史**

  普通交易新增、修改或软删除后，后续市场事件的派生交易仍保留旧结果。事件重算也只查询初始标的集合，未继续追踪代码变更或分红引出的新标的。例如已有 100 股、后续两倍拆股，再补录拆股前的 100 股买入，当前可能得到 300 股。另一个场景是 A 变更为 B 后 B 再拆股，补录 A 的早期拆股只重算到 A→B，B 的拆股结果仍旧。 [普通交易写入](../backend/src/main/java/com/vortex/service/TradeRecordService.java) · [事件重算](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [代码变更事件查询](../backend/src/main/java/com/vortex/repository/SymbolChangeEventRepository.java)

  验收需覆盖补录、修改和软删除历史交易，以及 A→B→后续拆股或分红的依赖链。第一种合成场景的最终持仓应为 400 股。修改标的、券商或日期时，新旧受影响范围均须与从原始记录完整重算的结果一致，重复重算应得到相同结果，处理失败时须保持事务一致性。

  2026-11-17 范围调整：原条目“补齐历史交易改动和跨标的事件的重算范围”的问题与验收记录保留如上。用户决定保留事件一键生成交易，用顺序处理及历史锁定替代自动级联重算，不采用全量重放后续事件或完全手工录入调整交易。用户明确只锁定事件关联证券，允许无关证券补录；第一版拒绝同日关联事件；关联证券已有事件当天或之后的普通交易时，拒绝撤销，要求先手动回退后续相关数据。方案见 [0035](../design/0035-2026-11-17-ordered-market-events-and-history-locks.md)。

  新验收以拒绝使已处理事件失效的操作、保持原账本不变为准：上述补录应拒绝且仍为 200 股；满足条件并显式撤销、补录、按顺序重新处理后才得到 400 股。覆盖事件新旧代码／分红证券、多券商、零输出事件、手工交易与 IBKR／Tiger 正式导入、重复校验、整批失败清理、同日及撤销限制，并验证真实隔离数据库中的事务与并发。当前只确认设计，业务代码仍使用原级联逻辑，历史数据未改动；实现、完整回归、独立复核及 knowledge 同步前继续未勾选。

  2026-11-17 完成：按 [0035](../design/0035-2026-11-17-ordered-market-events-and-history-locks.md)、[0036](../design/0036-2026-11-17-market-event-write-coordination.md)、[0037](../design/0037-2026-11-17-ordered-event-state-validation.md) 和 [0038](../design/0038-2026-11-17-sync-cleanup-history-guard.md)，保留事件一键生成，移除自动级联重算。只处理当前事件；锁定其关联证券在事件日之前的正式交易，覆盖全部券商，零输出事件也生效，多个约束叠加。普通交易增改删检查旧、新范围，MARKET_EVENT 行不可经普通 CRUD 维护；事件只能按顺序处理，同日关联拒绝，已处理字段不可直接编辑。撤销只允许关联尾部、无事件当天或之后普通交易的事件，事务内只删本事件派生行并软删除本事件。无关证券可补录。

  所有相关短写事务通过同一 PostgreSQL 事务级 advisory mutex 协调，在 READ COMMITTED 下等待后重新检查。同步活动批次阻止事件操作；IBKR／Tiger 新历史写入、IBKR 关联回填和批次清理保护同一历史。合法重复仍跳过，新冲突使整批失败并清理，旧成功批次不变。清理核对批次归属／允许状态，并在任何删除前检查有效正式残留；COMPLETED 拒绝清理，旧 FAILED 残留已被事件使用时保守拒绝。未处理事件已有派生行、旧管理状态为空均拒绝处理、修改、撤销，不自动修复；公开处理入口持锁重读而不信任旧对象。创建不接受客户端指定的 ID、审计或异常处理状态。

  页面补充三类事件撤销、录入顺序提示、冲突原因与阻挡事件元数据；失败保留弹窗，成功刷新。当前知识、事件操作、同步生命周期／持久化及恢复说明已在同一变更同步。 [当前规则](../knowledge/market-events.md) · [操作指南](market-events.md) · [验证入口](../e2e/README.md#ordered-market-event-check)

  验证环境：ubuntu-dev，Ubuntu 26.04.1、Temurin 25.0.5、Maven 3.10.0、Node 26.13.0、npm 11.20.0、Python 3.14.4、PostgreSQL 18.6，工作树 /home/bytedance.guest/workspace/private/vortex。沿用现有工具和缓存；TMPDIR=/tmp、MAVEN_OPTS=-Djava.io.tmpdir=/tmp。构建及日志在工作树，临时数据库及编译文件位于本次 /tmp/vortex-ordered-events-*，完成后停止并清理。没有安装工具、修改依赖、新增迁移或连接真实券商、个人 XML、vortex／vortex_dev。

  - 旧实现先运行 OrderedMarketEventRegressionTest，当时新增2项均失败，退出1、0错误，复现早期交易未拒绝及请求一个事件却级联重放；日志 backend/target/ordered-events-red.log。
  - 完整运行 `mvn -o -f backend/pom.xml '-Dtest=*Test,!FlexQueryParserTest' test`，退出0，498项全过；日志 backend/target/ordered-events-backend.log。原有Surefire退出确认等待30秒提示仍在，Maven BUILD SUCCESS，未修改超时或掩盖退出提示。
  - 单独运行 `mvn -o -f backend/pom.xml '-Dtest=FlexQueryParserTest#parseEmptyTradeConfirms+parseXmlWithoutTradeConfirms+parseInvalidXmlShouldThrow' test`，退出0，3项全过；日志 backend/target/ordered-events-parser.log。4个依赖被忽略个人XML的既有方法明确未运行，替换个人夹具仍为活动TODO，没有新增跳过标记。
  - 最后补齐显式空状态和旧NULL状态保护后，运行 `mvn -o -f backend/pom.xml -Dtest=MarketEventHistoryServiceTest,OrderedMarketEventRegressionTest,MarketEventProcessingServiceTest,MarketEventConflictAdviceTest test`，退出0，33项全过（17／7／8／1），正常退出；日志 backend/target/ordered-events-final-focused.log。复用未受后续变更影响的完整组结果，最后仅整理注释标点和空行。按31个测试类、嵌套类和参数化案例名称逐条核对，合并504个唯一案例，不重复累计33项；清单 backend/target/ordered-events-test-manifest.json 与报告逐条对应，无遗漏、失败、错误或跳过。
  - `python3 e2e/verify-ordered-market-events.py` 最终退出0，总PASS，临时集群停止并删除；日志 backend/target/ordered-events-integration.log。真实Spring事务/JPA覆盖100→200拒绝补录、撤销修正后400、旧新范围与系统交易只读、两券商、代码链和分红目标后续拆股、同日限制、零输出与叠加约束；验证外层及撤销回滚、真实派生行数值约束失败整体回滚、重试和隔离级别拒绝；并发事件只成功一次，交易先行／事件先行及批次创建先行均等待提交后重查；实际IBKR受锁导入回滚、清理、COMPLETED保护、旧FAILED残留保护及解除依赖后重试。只读诊断在合成旧NULL、残留派生行与同日重叠记录上验证。中间验收器曾因合成订单缺少普通成交字段、异常包装类型、数值边界夹具和提示文本断言不符而失败，修正验收器后完整重跑，不改生产数量／金额／费用规则。单测不作为数据库事务证据。
  - `npm --prefix frontend test` 退出0，54项通过；`npm --prefix frontend run build` 退出0。日志 frontend/ordered-events-tests.log、frontend/ordered-events-build.log；保留既有图表大分块构建提示。实际浏览器使用独立合成API逐一验证三类撤销409时保留原因、成功后刷新列表；截图 target/ordered-events-ui-conflict.png。未使用真实后端或账本进行页面写入。

  边界：没有自动验证或修复用户实际历史。processed=true不证明旧结果正确，部署前需按操作指南在隔离副本只读核对存量，异常维护另行授权。若旧FAILED残留清理转为CLEANUP_FAILED且又依赖市场事件，普通页面撤销与清理会互相阻挡；当前保守停止，须停写、确认Worker退出、备份核对并单独评审维护，未提供自动解锁或通用修复命令。原数量取整、成本／费用精度、卖空和同日交易排序规则不变；Tiger时间格式和IBKR匹配歧义继续独立TODO。

  实现、全部差异、验收场景与实际运行证据经未参与实现的Agent独立复核通过；发现的旧处理对象、未处理残留、旧NULL状态及FAILED残留清理绕过已修复并回归。没有剩余可行动发现，原问题和范围变更历史完整保留于此。


- [x] **P1 校验 IBKR 报表结构及导出层级**

  解析器只读第一份 FlexStatement 和第一组 TradeConfirms；缺少节点时返回空结果，不校验报告类型。合成两份 statement 只解析一份；不含所需节点的 AF 报表、含成交但无订单层的 TCF 报表均可由适配器报告成功 0。上述结果在 `ubuntu-dev` 通过真实解析器与模拟仓储复现；本次真实文件的结构完整，未触发。 [解析入口](../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L107) · [审阅 R4](audits/2026-11-16-ibkr-sync-review.md#r4--p1不支持的报表结构会被静默截断或当成零条成功)

  验收需区分合法零交易与缺少导出层级、错误查询类型、异常响应和不支持的多报表结构。明确支持范围并完整处理，或清楚报错；不得静默丢弃后续内容。用可提交的合成 XML 验证完整解析→暂存／导入入口的结果，覆盖第一份为空而后续非空，以及非空成交明细但无订单层。本条尚未修复；下一步新增 design。

  2026-11-17 范围确认：用户采用单账户、单份规定结构的 TCF 报表，超出范围或缺少必要层级时报错；不新增多账户或多 statement 导入。按 [0034](../design/0034-2026-11-17-ibkr-single-account-tcf-validation.md) 要求完整容器、账户及单组 TradeConfirms，非空报表同时具备订单和成交层，完整空报表允许成功零条。日期分窗仍逐份下载、逐份校验，结构错误不能被当作正常零交易；原发现和验收历史保留，回归及独立复核完成前继续未勾选。

  2026-11-17 完成：按 [0034](../design/0034-2026-11-17-ibkr-single-account-tcf-validation.md) 在字符串和流两个解析入口统一校验 TCF 类型、唯一直接封套、可选 count、非空报表账户及记录账户一致性；拒绝未知层级、嵌套记录和非空文本。非空报表同时要求 Order 与 TradeConfirm，合法的完整空报表仍成功零条。结构错误为 UNRECOGNIZED，使用固定、可定位的配置原因，不输出账户或 XML 值；不再取第一份或将缺层级当作零交易。日期窗口分别校验，任一文件不支持时，在暂存前使整批失败并执行既有清理，原成功批次保留。

  验证环境：`ubuntu-dev`，Ubuntu 26.04.1、Temurin 25.0.5、Maven 3.10.0，工作树 `/home/bytedance.guest/workspace/private/vortex`。沿用既有离线缓存，`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`，日志和构建产物位于 `backend/target/`，没有安装工具或修改依赖。

  - 原实现运行 `mvn -o -f backend/pom.xml -Dtest=IbkrReportStructureTest test`：退出 1，63 项中 54 项因未拒绝结构异常失败，0 错误，其余控制场景通过。日志为 `backend/target/ibkr-structure-red-tests.log`。
  - 修复后运行 `mvn -o -f backend/pom.xml -Dtest=IbkrReportStructureTest,IbkrDuplicateBatchTest,IbkrQuantityValidationTest test`：退出 0，107 项全部通过，日志为 `backend/target/ibkr-structure-focused-tests.log`。首轮同命令有 3 项既有错误摘要用例因使用不完整简写 XML 提前失败；仅将夹具改成合法空 TCF，保留原摘要断言，首轮证据在 `backend/target/ibkr-structure-focused-first-tests.log`。
  - `mvn -o -f backend/pom.xml -Dtest=IbkrReportStructureTest,IbkrDuplicateConsistencyTest,IbkrDuplicateBatchTest,IbkrStagingWorkerTest,IbkrStagingServiceTest,IbkrImportWorkerTest,IbkrImportServiceTest,IbkrSyncAdapterTest,IbkrQuantityValidationTest,TradeFeePrecisionTest,BrokerSyncAsyncExecutorTest,SyncBatchCleanupServiceTest,SyncBatchFailureHandlerTest test`：退出 0，300 项全部通过，无失败、错误或跳过，日志为 `backend/target/ibkr-structure-green-tests.log`。逐类核对原有相关套件的全部测试类及嵌套类，包含新增结构 63 项和新增批次 8 项。
  - `mvn -o -f backend/pom.xml -Dtest=FlexQueryParserTest#parseEmptyTradeConfirms+parseXmlWithoutTradeConfirms+parseInvalidXmlShouldThrow test`：退出 0，旧解析器的 3 个纯合成方法通过，正常结束，日志为 `backend/target/ibkr-structure-legacy-tests.log`。仅更新缺少 TradeConfirms 的旧断言为明确拒绝；4 个依赖个人 XML 的方法未改动、未运行，个人夹具 TODO 继续保留。

  曾把方法选择器和多个类放在同一命令中，228 项虽通过，但 Surefire 遗漏了嵌套测试；该轮日志保留为 `backend/target/ibkr-structure-selected-tests.log`，不作为完整验收。最终按上述两个独立命令验证全部 300 项及旧解析 3 项。完整组和 focused 仍有既有 Surefire 在退出握手等待 30 秒后终止子 JVM 的提示，Maven 均为 BUILD SUCCESS；旧解析 3 项单独运行无该提示，未据此改动框架或运行时。

  [结构回归](../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrReportStructureTest.java) 覆盖两种入口、第一份为空后续非空、多份容器／报表／成交组、错误类型／count、账户缺失或混用、仅订单／成交／汇总、未知或嵌套节点、非空文本、非法 XML、DOCTYPE，以及合法空／非空、注释和完整精度原值。[批次回归](../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrDuplicateBatchTest.java) 使用真实解析、暂存、导入、异步执行和清理组件，覆盖同报表重复、非法输入与重试、合法空报表、后续日期窗口失败；检查暂存前拒绝、当前批次清理和原成功批次逐字段不变。既有数量、费用、失败及清理回归继续通过。

  仓储及网络全部 Mockito，数据全部合成，未连接真实数据库、券商、个人 XML 或现有账本；不将内存链路表述为真实数据库事务或并发验证。账户范围限单份文件，不新增跨窗口／独立请求的账户绑定或成交集合完整性证明；保留历史已执行前提及 Code=P 行为。没有数据库迁移、前端/API 结构变化、历史数据修复或重算，输入结构收紧需要查询配置符合规定封套及两个导出层级。知识、接入和恢复说明已同步；实现、测试、文档与运行证据经未参与实现的 Agent 独立复核通过，最终归档随本变更交付。


  最终 26 文件差异及归档已独立复核，无剩余可行动问题；13 份文档的 210 处本地链接、源码行号及新增文件空白检查通过，`git diff --check` 退出 0。活动 TODO 保留 5 项未完成事项。

- [x] **P1 IBKR 重复记录内容一致性校验**

  暂存仅按 orderID 是否存在决定跳过，不比较内容。后续同 ID 订单的数量、金额或费用变化不会更新正式交易，新 tradeID 的成交却可能继续进入暂存表，造成明细与账本不一致，批次仍成功。合成数量 10→20 的同 ID 订单已在 `ubuntu-dev` 复现第二次跳过；本次真实文件没有同 ID 不同快照，未证明已有账本发生漏单。 [暂存去重](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrStagingWorker.java#L51) · [审阅 R2](audits/2026-11-16-ibkr-sync-review.md#r2--p1同订单后续新增成交或修正会被直接跳过)

  验收需先明确订单快照更新、跨窗口成交和券商修正的业务键及处理方式。覆盖完全相同报表重复同步、部分成交后新增成交、跨日／跨窗口同订单、费用修正、失败后重试，确认不重复、不静默漏更新，正式交易与成交明细可对账。涉及替换历史数据时须验证批次失败恢复及后续重算。本条尚未修复；下一步新增 design。

  2026-11-17 范围调整：用户决定默认相信 IBKR 报表中的订单及成交均已执行完成，见 [0031](../design/0031-2026-11-17-trust-ibkr-reported-completion.md)。不查询订单终态、不维护未完成订单生命周期、不因 `Code=P` 或多笔成交拒绝导入。后续本项聚焦重复业务 ID 的内容一致性：保留相同数据重复同步、同 ID 新增成交或数量／金额／费用变化、跨窗口差异及失败重试的防御性验收，不要求跟踪或自动合并真实未完成订单。原合成复现与历史验收记录保留；如何比较并处理差异须另写实施方案。本次只确认业务前提，未修改去重或导入代码，本条继续未勾选。

  用户补充决策原因：实际只同步历史交易记录，关注已经发生的成交，不管理实时挂单，因此默认按已执行完成的记录导入。本说明补充上述业务前提的依据，不增加实时订单跟踪或日期校验，也不将本条标记为已修复。

  2026-11-17 实施口径：用户明确要求“ID 相同、内容一致则跳过；ID 相同、内容不同则报错，暂不自动覆盖或重算”。据此将原“处理 IBKR 同订单的后续成交与内容修正”改名，按 [0032](../design/0032-2026-11-17-ibkr-duplicate-content-consistency.md) 校验两种暂存及正式入库重复分支；验收聚焦语义一致的重复、业务字段冲突、诊断、整批清理与原成功批次保留，不要求自动接受券商修正或合并未完成订单。原发现和合成复现保留，完成实际回归和独立复核前保持未勾选。

  2026-11-17 完成：按 [0032](../design/0032-2026-11-17-ibkr-duplicate-content-consistency.md) 在订单暂存、成交暂存及正式交易重复分支比较业务内容；同 ID 且内容相同跳过，内容不同以 UNRECOGNIZED 报错，沿既有失败链路清理当前整批，不覆盖、重算或修改原成功批次。比较全部已持久化来源字段，排除处理状态、批次及审计字段；数值按完整精度比较，普通文本统一空白语义，Code 按分号集合比较。错误给出内部行引用及差异字段名，不输出字段前后值。独立复核发现标识首尾空白可绕过去重，先新增 [0033](../design/0033-2026-11-17-ibkr-strict-duplicate-identifiers.md)，再将订单 ID、成交 ID 及父订单 ID 的缺失和首尾空白在查询／保存前拒绝，正式导入共用同一校验，不修剪或改写来源 ID。

  验证环境：`ubuntu-dev`，Ubuntu 26.04.1、Temurin 25.0.5、Maven 3.10.0，工作树 `/home/bytedance.guest/workspace/private/vortex`。沿用 Maven 离线缓存，`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`，产物及日志位于 `backend/target/`；没有安装工具、修改依赖或新增迁移。

  - 原算法先运行 `mvn -o -f backend/pom.xml -Dtest=IbkrDuplicateConsistencyTest test`，退出 1，当时新增的 2 项用例均因未抛出冲突异常而失败，无运行错误；复现同订单数量变化、同成交佣金变化仍被跳过。日志为 `backend/target/ibkr-duplicate-red-tests.log`。
  - 最终运行 `mvn -o -f backend/pom.xml -Dtest=IbkrDuplicateConsistencyTest,IbkrDuplicateBatchTest test`，退出 0，新增 115 项测试全部通过且正常退出，日志为 `backend/target/ibkr-duplicate-focused-tests.log`。
  - 运行 `mvn -o -f backend/pom.xml -Dtest=IbkrDuplicateConsistencyTest,IbkrDuplicateBatchTest,IbkrStagingWorkerTest,IbkrStagingServiceTest,IbkrImportWorkerTest,IbkrImportServiceTest,IbkrSyncAdapterTest,IbkrQuantityValidationTest,TradeFeePrecisionTest,BrokerSyncAsyncExecutorTest,SyncBatchCleanupServiceTest,SyncBatchFailureHandlerTest test`，退出 0，229 项全部通过，无失败、错误或跳过，日志为 `backend/target/ibkr-duplicate-green-tests.log`。整组保留既有 Surefire 退出握手等待 30 秒的提示，线程转储停在 ForkedBooter 退出确认阶段；Maven 为 BUILD SUCCESS，新增 115 项单独运行无该提示，未据此修改框架或运行时。

  [内容回归](../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrDuplicateConsistencyTest.java) 覆盖全部已持久化来源字段、数值表示与微小差异、空值、Code、元数据、同／跨批异常 ID、正式交易投影及旧记录不被保存。[批次回归](../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrDuplicateBatchTest.java) 使用真实解析、暂存、导入、适配器、执行与清理组件，覆盖相同报表、同批重复、订单／成交冲突、跨窗口、重试、缺失暂存基线的正式冲突及清理失败；逐字段检查原成功批次不变，清理失败保留 CLEANUP_FAILED。仓储和网络全部 Mockito、数据全部合成，未连接真实数据库、券商、个人 XML 或现有账本，不将内存调用链验证表述为真实数据库事务或并发唯一约束验证。

  边界：暂存基线存在时比较来源记录，不反向校验用户编辑后的正式交易；基线缺失时只比较正式表保留的业务投影，无法证明账号、费用组成、来源符号等未保留字段一致，旧列精度或旧费用舍入造成的差异也会安全拒绝。新 tradeID 仍作为新明细，不校验完整成交子集，不查询订单终态。自动接受券商修正、合并未完成订单、修改历史数据及真实数据库并发不属于用户修订后的验收范围。当前规则与恢复办法已同步到 knowledge 和恢复指南；实现、回归和运行证据经未参与实现的 Agent 独立复核通过，空白 ID 发现已修复并回归。最终 21 文件差异及文档归档复核通过，无剩余可行动问题；11 份文档的 185 处本地链接、全部新增文件空白检查和 `git diff --check` 通过。


- [x] **P2 明确并实现 IBKR 费用精度与舍入口径**

  本次 XML 的 295 条订单费用超过两位小数，佣金最多 8 位；当前 mapper 保留源精度，正式表 fee 却为 `DECIMAL(12,2)`，按声明 schema 写入将逐单舍入。此为文件统计及静态 schema 结论，未实际插库。是否接受按分舍入仍待用户确认。 [费用列](../backend/src/main/resources/db/migration/V3__create_trade_records.sql#L66) · [审阅 R1](audits/2026-11-16-ibkr-sync-review.md#r1--p2正式费用列会丢失样例中的佣金精度)

  验收需明确存储、计算、展示的精度与舍入时点。以含 8 位小数佣金的合成订单验证保存后读取及汇总对账；若保留两位，应明确并验证允许的对账差额，不能依赖未说明的数据库舍入。若修改 schema，新增迁移并验证空库、历史库升级及恢复。本条待业务口径确认，尚未修复。

  2026-11-17 范围确认：用户要求费用在数据库与后端完整保留来源精度，不截断、不舍入；前端列表、详情和统计仍显示两位，仅限展示，编辑和提交不得反向降低精度。用户说明此前 Tiger 历史费用不超过两位，本次不修复历史数据。按 [0029](../design/0029-2026-11-17-preserve-trade-fee-precision.md) 验证高精度费用计算、保存读取、汇总及编辑往返，保留空库初始化、两位合成历史数据升级与失败恢复验收；完成前继续未勾选。

  2026-11-17 完成：新增 [V29](../backend/src/main/resources/db/migration/V29__preserve_trade_fee_precision.sql)，将正式费用列改为不指定 precision/scale 的 `NUMERIC`，同步实体映射，保留非空、非负及默认零约束。后端费用计算和分币种汇总继续使用完整精度 `BigDecimal`，不改变佣金及附加费的绝对值之和、币种或去重规则。保留 JSON 数字 `fee`，新增只读十进制字符串 `feeExact`；前端费用输入使用 `stringMode`，编辑按原始字符串回填和提交，列表／详情／统计仍仅显示两位。原始数值精度在 PostgreSQL 支持范围内保留，不要求尾零的文本形式相同；不调整其他金额字段。

  验证环境：`ubuntu-dev`，Ubuntu 26.04.1、Temurin 25.0.5、Maven 3.10.0、Node 26.13.0、npm 11.20.0、Python 3.14.4、PostgreSQL 18.6，工作树 `/home/bytedance.guest/workspace/private/vortex`。使用既有依赖缓存，`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`；后端构建产物在 `backend/target/`，本轮日志在 `backend/target/` 和 `frontend/target/`，前端构建产物在 `frontend/dist/`，没有安装工具或升级依赖。

  - `mvn -o -f backend/pom.xml -Dtest=TradeFeePrecisionTest,IbkrImportWorkerTest,TradeRecordServiceTest,MarketEventProcessingServiceTest,TigerTradeRecordMapperTest test`：退出 0，85 项通过，含新增 [费用回归](../backend/src/test/java/com/vortex/sync/adapter/ibkr/TradeFeePrecisionTest.java) 9 项。覆盖 8 位及更多小数、极小值、相加进位、零／缺省费用、API 数字与字符串往返、交易新增／更新及统计；仓储全部 Mockito，不连接数据库。首轮 85 项中有 1 项将无损的科学计数法输出误判为损失，改为反序列化后精确数值比较，完整复跑通过。
  - `npm --prefix frontend test`：退出 0，37 项通过，含新增 [模拟 API 回归](../frontend/src/services/__tests__/tradeFeePrecision.test.js) 6 项。`npm --prefix frontend run build`：退出 0。保留既有 Vite CJS API 弃用与大分块提示，未修改依赖。
  - `python3 -B e2e/verify-fee-precision.py`：退出 0。脚本先通过上述新增 9 项内存测试，再启动 `/tmp` 下的新 PostgreSQL 集群，核对数据目录后运行 [独立检查](../e2e/FeePrecisionCheck.java)。空库 V1→V29、V28 两位合成历史行升级后逐字段不变、实际 ORM 高精度保存／读取和仓储分币种汇总、非空／非负／默认值、重复迁移无操作、锁超时失败回滚及重试，三组检查全部通过。旧 V28 写入合成 `1.12345678` 得到 `1.12`，实际复现原舍入；V29 可保存超过 8 位的小数并精确汇总。集群随后停止并删除。既有 Flyway 9.22.3 提示 PostgreSQL 18 超出其已测试版本，未阻止本次用例通过；未据此升级框架。
  - 实际浏览器使用真实 React 页面和仅含合成交易的临时接口：详情展示 `1.12`，仅将 `symbol` 改为 `SYNTH-UPDATED` 并保存，实际 PUT 的 `fee` 为字符串 `1.1234567890123456789`，重新打开表单后输入仍完整。没有真实后端连接；临时前端进程和监听端口均已关闭。

  独立复核已覆盖实现、测试、迁移检查脚本、方案、知识文档和运行证据，无可行动发现；文档链接与 `git diff --check` 通过。未连接现有 `vortex`／`vortex_dev`、真实券商或个人 XML，未做历史修复、重同步或重算；生产数据对账与实际部署锁等待不属于本次验证。部署先应用 V29，失败时由事务回滚后重试，应用回退保留扩展列。原审阅的“未实际插库”只描述当时的审计状态，不代表本次未验证数据库。

  2026-11-17 展示口径修订：用户进一步要求前端也直接保留原始数字精度，不再固定展示两位。按新增 [0030](../design/0030-2026-11-17-display-exact-trade-fees.md) 实现列表、关联交易表、详情和统计直接显示精确十进制文本，输入／保存沿用同一原值；统计响应补充各币种只读精确文本并兼容原数字字段，费用排序按十进制数值精确比较，遮罩、零费用和空值语义保留。此前两位展示决定及其验收记录作为历史保留，当前展示以本修订为准。未修改 0029 正文、V29 或 ORM，不新增迁移、不操作历史账本。

  同环境重新执行 `mvn -o -f backend/pom.xml -Dtest=TradeFeePrecisionTest,IbkrImportWorkerTest,TradeRecordServiceTest,MarketEventProcessingServiceTest,TigerTradeRecordMapperTest test`，退出 0，86 项通过，其中费用回归 10 项；`npm --prefix frontend test` 退出 0，51 项通过，包含新增 14 项真实列渲染、统计组件、精确排序及极小值／零／遮罩回归；`npm --prefix frontend run build` 退出 0，既有 CJS API 和大分块提示保留。日志分别位于 `backend/target/fee-exact-display-tests.log`、`frontend/target/fee-exact-display-tests.log`、`frontend/target/fee-exact-display-build.log`。实际浏览器在仅含合成记录的临时接口上确认列表、详情及统计均展示完整的 `1.1234567890123456789`；临时前端已停止。本次未重跑无改动的数据库迁移，沿用上次隔离验证结果。

  本修订的完整 24 文件差异、当前知识、归档和运行证据已由未参与实现的 Agent 独立复核，无可行动发现；217 处本地链接及新增文件空白检查通过，`git diff --check` 退出 0。

- [x] **P1 消除清仓后的平均成本残差**

  卖出时先将平均成本舍入为 6 位小数，再减记成本池，完全清仓时没有归零。合成场景中先买 10,000 股、单价 1，再买 20,000 股、单价 2，全部卖出后成本池会剩下 −0.01。重新买入 1 股、单价 10，后续代码变更会按 9.9900 转移成本。 [成本池计算](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java)

  验收需明确成本金额和单价的精度及舍入时点。上述场景重建持仓后，每股成本应恢复为 10.0000，代码变更前后的总成本应一致。还需覆盖多次部分卖出、全部卖出和重新建仓，确认历史残差不会累积到新持仓。

  2026-11-16 范围确认：用户要求修复历史成本回放的清仓尾差，认为现有历史记录无需处理。本项当前只影响证券代码变更时的历史成本回放与系统交易定价，不是日常持仓查询的数量误差。按 [0028](../design/0028-2026-11-16-clear-cost-pool-on-liquidation.md) 在恰好全部卖出时结清成本池，保留部分卖出 6 位和最终均价 4 位 HALF_UP 及原金额生成规则；验收确认原复现中新建仓的 10 成本能正确转移，不扩大为所有计价精度问题的修复。实现、回归及独立复核完成前保持未勾选，不连接或重算历史账本。

  2026-11-16 完成：按 [0028](../design/0028-2026-11-16-clear-cost-pool-on-liquidation.md) 在历史回放的 SELL 数量恰好等于当前正持仓时，直接结清成本池，扣减数量后两者同时为零。清仓后重新建仓仅使用新买入金额。部分卖出、最终均价、费用和代码变更金额生成口径不变；没有迁移、历史数据修复脚本或自动重算。

  运行验证：`ubuntu-dev`，Temurin 25.0.5、Maven 3.10.0，工作树 `/home/bytedance.guest/workspace/private/vortex`，Maven 离线缓存，`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`，产物位于 `backend/target/`。先在原算法上执行 `mvn -o -f backend/pom.xml -Dtest=MarketEventProcessingServiceTest test`，退出 1：8 项中 5 项因重新建仓成本偏差失败，无运行错误，确认用例捕获原缺陷。加入清仓分支后执行 `mvn -o -f backend/pom.xml -Dtest=MarketEventProcessingServiceTest,PositionServiceTest,TradeRecordServiceTest test`，退出 0：33 项全部通过，新增 8 项也全部通过。

  合成场景覆盖一次清仓、分批卖出后清仓、多轮清仓、正负尾差、零成本派生股、普通部分卖出、清仓后无持仓和证券／券商隔离。测试通过真实 `processSymbolChangeEvent`、持仓聚合和历史回放，检查生成的旧代码 SELL／新代码 BUY 的数量、价格、金额、币种、费用与来源，并检查原交易金额不被修改。原复现重新建仓后的均价和转移金额均恢复为 10，较广的计价精度规则不在本项验收范围。

  所有 Repository 为 Mockito，未启动 Spring 应用或真实数据库，未读取、修改或重算用户历史账本；不将内存调用链回归表述为真实数据库事务验证。实现、测试、方案、当前知识和最终归档文档已由未参与实现的 Agent 独立复核，无可行动发现。相关记录与实现一并交付。

- [x] **P1 禁止 IBKR 数量转换静默截断或溢出**

  `BigDecimal.intValue()` 会将小数股截为整数，也可能让超范围值溢出为仍合法的正整数。当前 mapper 在 `ubuntu-dev` 将合成数量 1.5 和 4294967297 均转换为 1，来源金额没有同步改变。真实文件均为范围内整数，未触发。 [数量转换](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java#L400) · [审阅 R5](audits/2026-11-16-ibkr-sync-review.md#r5--p1数量转换会静默截断小数并允许整数溢出)

  2026-11-16 口径确认：用户决定当前明确拒绝小数股，不截断或四舍五入；未来遇到实际案例再评估升级。验收据此调整为：正式导入只接受非零且绝对值在 Integer 正数范围内的整数数量，整数形式的十进制文本可无损接受；拒绝小数、零、缺失、非法与超范围值，保证不写入错误交易，混合批次失败后整批清理且保留拒绝原因。实现与验证按 [0027 数量校验方案](../design/0027-2026-11-16-ibkr-reject-fractional-quantities.md) 执行，完成前保持未勾选。

  2026-11-16 完成：按用户决定实施 [0027 数量校验方案](../design/0027-2026-11-16-ibkr-reject-fractional-quantities.md)。在 BigDecimal 绝对值上校验整数、非零和范围后使用 `intValueExact()`；不截断或舍入，错误使用 UNRECOGNIZED 分类，不保存不支持的正式交易。混合批次按原链路整批清理，批次摘要保留首条失败说明。价格、金额、历史账本、schema 和依赖不变；只针对本次正式导入使用的订单数量，既有去重行为保留。

  验证环境为 `ubuntu-dev`（Temurin 25.0.5、Maven 3.10.0），工作树 `/home/bytedance.guest/workspace/private/vortex`，沿用 Maven 缓存，`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`，产物在 `backend/target/`。执行 `mvn -o -f backend/pom.xml -Dtest=IbkrQuantityValidationTest,IbkrImportWorkerTest,IbkrImportServiceTest,IbkrSyncAdapterTest,IbkrStagingServiceTest,IbkrStagingWorkerTest,BrokerSyncAsyncExecutorTest,SyncBatchCleanupServiceTest,SyncBatchFailureHandlerTest test`，退出码 0，104 项全部通过；再单独执行 `mvn -o -f backend/pom.xml -Dtest=IbkrQuantityValidationTest test`，退出码 0，新增 28 项全部通过。

  覆盖小数、零、缺失、非法、正负边界、此前溢出为 1 的数量、合法整数文本、期权整数限制及空错误说明回退。纯内存调用链回归使用真实解析、暂存、导入、执行和清理组件，仓储及网络为 Mockito 模拟：正常订单可写入，含小数或溢出的混合批次最终失败、本批数据全部删除、其他批次保留，再次提交新批次仍拒绝且无残留。未连接真实数据库、券商或个人 XML，不将这些单测表述为实际数据库事务验证。

  首轮新增的 3 项回退用例因 Mockito 严格模式下的计数模拟不完整失败，补齐模拟后上述 104 项通过。整组运行另有 Surefire 在退出握手等待 30 秒后终止子 JVM 的提示，线程转储停在 ForkedBooter 的退出确认阶段，Maven 仍为 BUILD SUCCESS；单独运行新增 28 项正常退出且无该提示，未据此修改运行时或测试框架。实现、测试、方案、知识、故障处理及最终 TODO／归档差异已由另一 Agent 独立复核，无可行动发现；未独立验证真实数据库事务、派生查询实际执行或前端展示。相关实现与本验收记录一并提交。

- [x] **P2 同步前端 Vitest 声明与依赖锁文件**

  原问题：`package.json` 已声明 Vitest 和测试命令，`package-lock.json` 却缺少对应的开发依赖和锁定记录，干净检出的测试依赖无法由现有锁文件完整重现。 [依赖与命令](../frontend/package.json) · [依赖锁文件](../frontend/package-lock.json) · [测试配置](../frontend/vite.config.js)

  验收需在所选环境的干净检出中完成按锁安装，并成功运行前端单测。安装过程应保持清单与锁文件一致，重复安装应得到相同的依赖版本。

  2026-11-15 进展：在 [0026 图表演示](../design/0026-2026-11-15-frontend-chart-selection-demo.md) 中补齐 Vitest 1.6.1 及传递依赖的锁定记录；既有锁定包未升级。`ubuntu-dev`（Node 26.13.0、npm 11.20.0）中，当前工作树执行 `npm --prefix frontend test`，31 项通过，退出码 0；`npm --prefix frontend run build` 通过，退出码 0。

  本次另以 Git 文件清单复制当前前端源码至被忽略的 `backend/target/chart-demo-validation/clean/frontend/`，不包含已有依赖和构建缓存；在该副本连续执行两次 `npm --prefix <副本路径> ci --registry=https://registry.npmjs.org --no-audit --no-fund`，退出码均为 0，清单与锁文件字节不变，随后测试 31 项及构建均通过。副本已清理。由于本次变更尚未提交，原验收要求的独立干净 Git 检出复验仍待执行，本条保持未勾选；下一步在对应提交的干净检出中运行 `npm ci` 和单测后完成验收。

  2026-11-16 完成：修复已随提交 `e6d5945539690f552f0fc140c6b46388352200d5`（`feat(frontend): add interactive chart selection demo`）交付。本轮在 `ubuntu-dev`（Linux arm64、Node 26.13.0、npm 11.20.0）将该提交独立克隆至工作树内临时目录 `target/vitest-lock-validation/checkout/`，确认初始 Git 状态干净且不存在 `frontend/node_modules/`，再从此检出根目录验证：

  - 连续两次执行 `npm --prefix frontend ci --registry=https://registry.npmjs.org --no-audit --no-fund`，退出码均为 0；每次均按锁重新安装 449 个包。两次 `npm --prefix frontend ls --all --json` 均退出 0，规范化完整依赖树完全一致，SHA-256 为 `b9cefbce74a10bd9de4a98523dfd9dd1f246792dc4ab91d79f8e9513b79b55f2`。Vitest 实际版本为 1.6.1。
  - `npm --prefix frontend test` 退出 0，3 个测试文件、31 项测试全部通过；`npm --prefix frontend run build` 退出 0。
  - `package.json` 和 `package-lock.json` 在安装、测试及构建前后字节不变，最终 Git 状态仍干净；当前工作树的这两个文件也与该提交一致。npm 使用已有缓存，临时目录和产物位于本次验证目录内；验证完成后整个临时检出及其依赖、产物和日志已清理。

  安装与构建仍有既有依赖弃用、esbuild 安装脚本策略提示、Vite CJS API 弃用和较大构建分块提示，未导致上述命令失败。本轮仅补齐原方案 [0026](../design/0026-2026-11-15-frontend-chart-selection-demo.md) 的提交后验收并关闭本条，未修改或升级依赖；当前 knowledge 与前端指南已经描述正确的锁定安装方式，无需更新。

## 已取消或合并

- [ ] **已取消（当前接受）：IBKR 正常解析与同步日志中的账户标识脱敏（原 P2）**

  Parser 与 Adapter 在 INFO 日志直接打印 accountId，真实同步会将账户标识写入日志。`ubuntu-dev` 的合成日志捕获已确认账户字符串原样出现；本轮未将真实 XML 交给该日志链路。 [解析日志](../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L170) · [同步日志](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapter.java#L281) · [审阅 R6](audits/2026-11-16-ibkr-sync-review.md#r6--p2正常解析会把真实账户-id-写入-info-日志)

  2026-11-16 处置：用户明确接受当前阶段在应用日志中打印账户 ID，认为现阶段无需对此脱敏。已在 AGENTS.md 的数据安全约定中明确此例外，取消本项修复及原账户脱敏验收要求，保留发现和复现记录。本项不再属于活动待办，也不标记为已修复完成。密码、Token、密钥和交易明细仍遵循原约定；未来若调整账户日志要求，再重新评估并制定方案。
