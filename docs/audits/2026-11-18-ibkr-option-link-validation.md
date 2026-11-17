# IBKR 期权事件精确关联验收

日期：2026-11-18。当前方案：[0040](../../design/0040-2026-11-18-ibkr-exact-option-event-linkage.md)、[0042](../../design/0042-2026-11-18-trust-existing-option-event-records.md)。基线：`269abfd`。[0041](../../design/0041-2026-11-18-ibkr-option-link-candidate-provenance.md) 是已撤回历史状态／来源处理的中间决策，不代表当前规则。

## 范围与当前结果

用户要求同券商精确唯一关联，无匹配或多匹配整次同步失败、人工排查；随后明确历史数据准确，无需兼容。因此当前直接使用可信正式期权记录，不查询历史批次状态、回查旧暂存来源或设置人工记录例外。100乘数只校验本次新导入的期权交割事件，位置在正式重复返回之后、保存新行之前。原有同ID来源内容冲突规则保留；正式重复兜底不追加乘数门槛，不表示暂存阶段允许来源内容改变。

生产改动限于 [候选及占用查询](../../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java)和 [IBKR Worker](../../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java)。沿用ImportService、Adapter、Executor及失败／清理链，补充Worker／Service测试和临时数据库验收。无HTTP接口、前端、依赖或迁移变更，没有读取或修改真实账本、个人XML、根目录备份及下载样本。

核对范围为回填及新订单导入、候选查询、BookTrade映射、当前乘数校验、异常传播／清理、既有写锁和相关测试／文档；参考V3、V10、V20、V22、V28的字段及状态依据。未对其他生产模块或全部迁移做完整审计。关联对象是同次行权／指派的期权侧簿记记录，不是原始开仓；历史完整性、异常历史适配及非标准交割不在本次范围内。IBKR报表语义参见 [官方FAQ](https://www.interactivebrokers.com/support/faq/content/happens-am-assigned-stock-option-32642502)。

## 验收对应

| 场景 | 结果和证据 |
| --- | --- |
| 同券商精确唯一匹配 | SQL限定broker_id、币种及事件／标的／日期等业务字段，代码同时核对方向、合约代码、精确行权价及100倍数量。四种CALL／PUT行权／指派方向通过，交换候选顺序不改变结果。 |
| 无匹配、歧义和重复占用 | 零匹配及多个完整匹配失败，不取第一条；先统计匹配再检查占用。有效股票／ETF引用使唯一候选不可再次使用；正常引用占用、错误传播、实际整批清理与重试通过。 |
| 信任已有正式记录 | 四种方向的单测不访问暂存Repository。临时PG先导入准确期权事件，再移除其临时旧暂存来源，新股票侧依然成功关联；没有历史状态／来源门槛或人工专用路径。 |
| 只约束当前新输入 | 8组参数化覆盖CALL／PUT交割的100及100.00、缺失／空白／非法／非100乘数，以及期权到期缺乘数仍正常导入。原普通OPT及STK事件测试保留。 |
| 正式重复 | 先实际导入一条准确期权事件，再用缺乘数但正式业务投影相同的输入触发正式重复兜底，SKIPPED且不新增正式行；既有暂存层来源冲突回归仍全部通过。 |
| 数值及并发写入 | long拒绝42,949,674张乘100在int中回绕104的场景，BigDecimal行权价无舍入或容差。持锁重读股票后重验当前资格，正常删除／编辑变化拒绝，已有正确关联不改写。 |
| 实际查询及事务 | 临时PG查询排除其他券商及币种，允许正常跨批次关联；触发器令关联UPDATE失败，真实事务回滚、整批清理并FAILED，解除合成故障后重试成功，旧关联保留。 |
| 其他同步保护 | 既有重复内容、部分失败、清理失败／中断和活动批次限制通过完整后端验证；原市场事件／同步真实并发验收一并保留。本次只撤回0041新增的历史处理，不改已提交的其他保护。 |

0042移除的专用测试为历史来源缺失／空白／非法／非100／批次不符5个参数案例和人工记录例外1个案例，删除其专用模拟。当前输入及正式重复新增9个参数／普通案例；原未知删除状态模拟改成正常删除，保留同一重读保护。期权关联验收撤回0041新增的旧FAILED残留和篡改旧引用类型模拟，保留正常占用与精确匹配。用例范围变更来自用户业务决定，不作为提速手段。

## 实际运行

约定环境`ubuntu-dev`，工作树`/home/bytedance.guest/workspace/private/vortex`，Java25.0.5、Maven3.10.0、PostgreSQL18.6及已有Python。`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`；日志和构建在工作树，临时数据库在运行器独占的`/tmp`目录，沿用缓存，没有安装工具或依赖。

| 命令／阶段 | 实际结果 |
| --- | --- |
| `mvn -o -f backend/pom.xml test` | 0042生产代码全量539项通过，失败／错误／跳过均0，退出0；`backend/target/ibkr-trusted-history-final.log`，耗时1分34秒，02:41:57 +08:00结束。 |
| `mvn -o -f backend/pom.xml -Dtest=IbkrImportWorkerTest,IbkrImportServiceTest test` | 之后仅补PUT及到期两个参数边界，生产代码和环境未变；最终专项70项全过、退出0，12.642秒，`backend/target/ibkr-trusted-history-focused-final.log`，02:43:45 +08:00结束。 |
| `python3 -B e2e/verify-ordered-market-events.py` | 退出0，精确关联及全部既有场景PASS，集群停止并删除；`backend/target/ibkr-trusted-history-db.log`。初轮与全量单测并行；helper名称整理后最终重跑于02:57:32 +08:00通过并停止清理，当前日志对应最终重跑。生产类未变，e2e在独占/tmp目录编译。 |
| 0041中间版本 | 538项及当时临时PG曾通过；日志`backend/target/ibkr-exact-link-final.log`、`backend/target/ibkr-exact-link-db-final.log`保留，仅证明被撤回方案当时的行为。原旧逻辑复现和专项记录见TODO归档。 |

最终验证清单为`backend/target/ibkr-trusted-history-manifest.json`：按本轮实际Running套件选取对应Surefire XML，受影响套件使用最终专项报告，其他套件使用全量报告。31个源类、317个注解方法均执行，共541个当前案例（471个未改套件案例加70个最终专项案例），无失败、错误或跳过。相对0041的538，仅上述6项撤回、9项当前输入／正式重复验证新增，其余案例无缺失。541是组合去重清单，不是另一次单独全量命令的数量；无需为参数测试补充重复运行无关套件。

运行器创建临时集群并校验data_directory，使用合成XML、内存报表提供器和真实Spring/JPA事务、仓储及失败处理。不加载应用profile，不访问IBKR或vortex／vortex_dev，不代表实际账本对账。前端未改，未重新运行页面或构建。全量保留既有Surefire退出握手等待提示，随后BUILD SUCCESS；没有改超时或忽略失败。

## 文档与复核

更新当前IBKR知识、生命周期、持久化、恢复说明和e2e入口；TODO保留原问题及中间决策历史，在末尾记录0042最新验收。0040／0041历史方案正文保留原样。其他架构、持仓和已提交迁移无需变更。

最终实现、组合清单和文档归档已由未参与实现的Agent独立复核通过，无剩余可行动问题。逐条核对471个未改套件案例加70个最终专项案例与清单一致，31类／317方法全部覆盖；当前18文件范围无意外修改，13份Markdown的244处本地链接及新增文件空白检查通过，`git diff --check`通过。历史方案0040／0041正文、原TODO问题和旧归档保留完整。
