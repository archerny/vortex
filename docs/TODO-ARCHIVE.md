# 待办归档

这里保留已完成、已取消或已合并事项的背景、处理历史和验证证据。当前需要继续处理的事项见 [待办事项](TODO.md)，维护规则见 [项目研发约定](../AGENTS.md#todo-维护与完成)。

本次归档日期：2026-11-16。进展记录保留当时的状态，以条目最后的完成或处置记录为准；取消、合并不代表已修复并通过验收。

## 已完成

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
