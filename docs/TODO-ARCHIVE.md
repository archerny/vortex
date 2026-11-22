# 待办归档

这里保留已完成、已取消或已合并事项的背景、处理历史和验证证据。当前需要继续处理的事项见 [待办事项](TODO.md)，维护规则见 [项目研发约定](../AGENTS.md#todo-维护与完成)。

最近归档日期：2026-11-22。进展记录保留当时的状态，以条目最后的完成或处置记录为准；取消、合并不代表已修复并通过验收。

本轮未提交的 0047—0051 设计草稿已合并为 [0047 统一方案](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。下文继续按 V31—V35 分别保留阶段行为和验证证据，早期结果不代表后续版本的全部验证已通过。

补充证据：[IBKR 样例与初次审阅](#ibkr-样例与初次审阅) · [测试清理依据](#测试清理依据) · [期权关联验收明细](#期权关联验收明细)。运行方法见 [e2e 指南](../e2e/README.md)，未完成事项继续位于 TODO。

## 已完成

- [x] **清空开发库测试数据并生成页面演示数据**

  2026-11-22 按用户明确指令“先清空 dev 数据库中的测试数据，然后生成一些数据放到 dev 数据库中”完成。本次为运维及合成数据准备，未修改产品代码或接口，当前能力知识无需调整，也未添加数据库迁移。

  目标经核对为 `ubuntu-dev` 当前工作树的 `127.0.0.1:5432/vortex_dev`、角色 `vortex_dev`、V35、无活动同步批次。先停止已记录的当前后端并保存私有数据库备份，再于一个 Spring 事务内 `TRUNCATE` 全部 15 张业务表并重置序列，保留表结构及 Flyway 历史；不使用跨表集的 CASCADE。数据主要经现有账户、交易、流水及市场事件 Service 创建，同一事务中执行交易核对、分账户持仓和迁移历史不变断言，通过后才提交。同步批次及暂存行为合成展示记录，未发起同步或保存券商凭证。

  最终数据为 3 家券商、6 个账户（同券商双账户、待补全及停用账户）、4 个策略、8 笔出入金、24 笔原始交易及 6 笔市场事件派生交易；三类已处理事件各 1 个，2026-11-22 持仓为 17 条。同步历史有 2 个 COMPLETED、1 个 FAILED，IBKR 订单／成交确认各 2 行、Tiger 暂存 1 行，无活动批次。包含 USD／HKD／CNY、股票／ETF／期权、到期及行权关联、高精度费用、逐账户拆股和分红、代码变更；真实交易核对接口返回 30 笔、0 异常。

  使用已有 Java25.0.5、Python3.14.4、PostgreSQL18.6 及既有编译类路径。一次性脚本、运行日志、提交标记和数量摘要在 Git 忽略的 `backend/target/dev-demo-2026-11-22/`。`rehearse.py` 在自建临时 PostgreSQL 中两次通过后执行 `prepare.py`；正式首次尝试因非特权角色无权查询 `data_directory` 在 Spring 启动前退出，尚未清空，后端已恢复。该检查随后仅用于自有临时集群；正式目标在应用启动前验证库／角色／活动批次，并使用受控环境及最高优先级固定数据源。最终重试退出 0，`commit.marker`、COMMITTED 和 PASS 均存在，临时配置已删除。失败路径可区分已提交后的报告错误，不将所有非零退出误称为回滚。

  `verify-api.py` 退出 0，经真实前端代理核对上述数量、三类事件处理状态、分账户 AAPL 持仓 80／150、17 条持仓和 0 异常、6 个连接条目均未配置／未就绪。宿主现有 Node／Chrome 的 `ui.mjs` 退出 0，读取真实开发页面：账户、交易、持仓、代码变更标签及批次均可展示；24 次 API 请求全部 GET，无浏览器异常，临时浏览器目录已移除。三类事件都经 API 校验，未扩大为三个事件标签均已点验。截图 `accounts.png`、`positions.png` 已检查。

  清空前备份另存 `.local/dev-backups/vortex-dev-before-demo-2026-11-22.dump`，权限 0600 且被 Git 忽略，不受常规构建清理影响。独立 Agent 只读复核脚本、目标／事务／服务恢复、提交与失败记录、摘要及 API／UI 结果后通过；276 项产品输入哈希未变。未读取备份内容，未执行全量测试或真实券商请求。前后端服务已恢复，用户可直接进行页面操作。

- [x] **补齐券商简介并将启停限定到账户**

  2026-11-22 完成，方案 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。按用户三项要求：新增独立券商简介 `brokers.introduction`（500 字符），移除托管账户页“券商管理”按钮，并通过 V35 删除 `brokers.is_active`。券商只维护名称、技术代码、国家／地区和简介；账户备注仍是 `broker_accounts.description`，不反向复制为券商简介。

  券商实体、服务、仓储、页面及同步流程均移除券商状态判断。连接的 active／ready 和同步触发只检查账户自身状态、真实 ID 及凭证；适配器发现只检查对应券商资料存在。旧 `GET /api/brokers/active` 兼容返回全部券商，旧前端导出查询普通列表。V34→V35 保持已有账户状态、资料及账本／连接／批次逐字段不变；V33 当时按旧券商状态初始化待补全账户的历史迁移保持原样。简介新增／更新修剪空白，省略／null 保留、空串清空，超长拒绝。

  在 `ubuntu-dev` 当前工作树使用已有 Java25.0.5／Maven3.10.1、Node22.22.1／npm9.2.0、Python3.14.4、PostgreSQL18.6，沿用缓存，产物在工作树、临时文件在 `/tmp`。无工具安装或依赖变更，本轮只运行针对性验证。

  - `TMPDIR=/tmp MAVEN_OPTS=-Djava.io.tmpdir=/tmp mvn -B -ntp -o -f backend/pom.xml -Dtest=BrokerServiceTest,BrokerSyncConnectionServiceTest,BrokerSyncServiceTest,BrokerSyncControllerTest test` 退出 0，4 个源类 31 个案例，无失败／错误／跳过。逐项核对实际方法及嵌套 suite，清单 `backend/target/broker-reference/test-manifest.json`，日志 `backend-tests.log`；覆盖简介增改／保留／清空／长度及账户启停、发现和路由。
  - `npm --prefix frontend run build` 退出 0，保留既有 CJS／大分块提示，日志 `backend/target/broker-reference/frontend-build.log`。前端验证以实际页面交互为主，未重复运行无关单测。
  - `python3 -B e2e/verify-account-profiles.py` 退出 0，4 组 PASS，日志 `backend/target/broker-reference/database-check.log`。旧联系人升级明确固定 V33→V34，另验证 V34→V35 的锁失败回滚重试、原停用券商下的启用账户保留状态，以及账户／资料／交易／流水／连接／批次完整 JSON 快照不变。空库启动至当前结构并完成 JPA 校验；真实 HTTP 验证简介创建、读取、修改、字段省略保留及清空后 GET，券商响应无状态、旧列表兼容、账户连接就绪和停用后拒绝触发、券商发现仍存在、其他账户及账户备注保留。显式 null 简介语义由服务同一分支及单测覆盖，未称作独立 HTTP 场景。没有请求真实券商，临时应用与集群已清理。
  - 宿主已有 Node26.4.0／Chrome157.0.8103.79 执行 `node backend/target/broker-reference/ui.mjs` 退出 0，全部 API 拦截为合成数据。3 组点验覆盖简介新增／回填／修改及表格显示、券商无状态列／开关、账户页无跨页按钮、账户状态仍可编辑，并保留联系人和待补全账户回归。合成截图 `brokers.png`／`accounts.png` 已检查；日志 `ui.log` 和请求清单 `ui-requests.json` 位于同目录。无未捕获异常，临时 Chrome 目录已清理。
  - 5 个 Java 验收 helper 编译通过，日志 `backend/target/broker-reference/helper-compile.log`；旧设置脚本改为编辑账户状态，升级前 SQL 仍保留原版本字段。未执行其他较宽数据库入口或全量测试，不将编译通过或旧 V33 全量结果扩大为当前全部回归通过。当前代码／测试／运行器／构建配置哈希保存在 `source-inputs.json`。

  开发库确认 `dev/vortex_dev` 且无活动批次后，保存权限 0600 的升级前私有备份，再重启后端执行 V35 和 JPA 校验；没有清库。前端已重启提供新模块，当前知识、账户／同步／部署及 e2e 文档已同步。独立 Agent 已只读复核本次迁移、完整状态调用链、两页面、用例／验收、当前知识及部署／归档文档，并复算 276 项输入哈希、4 源类／8 suite／31 个命名案例及 PG／UI 记录，结论通过，无剩余可行动问题。未重审无关账本算法或真实券商路径。最终检查 30 份变更 Markdown 的 605 处本地链接／锚点、新文件空白及 `git diff --check` 均通过；私有备份权限 0600 且被 Git 忽略。宿主前端、后端健康和代理券商 API 均 HTTP 200，确认简介字段存在、券商状态字段已移除，实际前端模块为最终版本。最后检查曾因工具用量限制中断，用户要求继续后已完成；未重复运行已通过测试。最终检查记录 `backend/target/broker-reference/delivery-checks.json`。未提交代码。

- [x] **将邮箱、电话与账户备注迁至托管账户**

  2026-11-22 完成，方案 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。V33 拆分后原 `brokers.email`、`phone` 和“券商账户描述”仍在券商层，用户明确要求这些信息归实际账户。V34 新增账户同名三列并迁移资料，删除券商旧列；实体、服务、券商列表／表单与账户列表／表单同步调整。券商仅维护名称、代码、国家和状态，联系方式与备注按账户独立保存，不影响同步凭证和账本口径。

  旧资料优先归入唯一待补全账户，否则只有一个账户时归入它；零账户、多真实账户或多个待补全账户无法确认归属时，新建“待补全账户（原券商资料）”保存。无资料券商不新增账户。旧字符串、已有账户审计字段及账本／批次／连接保持原值。账户更新省略或传 null 保留已有可选字段，传空字符串清空；新写入修剪首尾空白，长度沿用 100／30／200。前端邮箱验证兼容历史首尾空白。

  在 `ubuntu-dev` 当前工作树使用既有 Java25.0.5／Maven3.10.1、Node22.22.1／npm9.2.0、Python3.14.4、PostgreSQL18.6；缓存沿用，产物在工作树，临时集群在 `/tmp`。未安装工具或修改依赖。以下为 V34 的实际针对性验证，此前 566／60 项全量结果属于 V33，本轮未重复执行全量单测或整套回归。

  - `TMPDIR=/tmp MAVEN_OPTS=-Djava.io.tmpdir=/tmp mvn -B -ntp -o -f backend/pom.xml -Dtest=BrokerAccountServiceTest,BrokerServiceTest,BrokerSyncConnectionServiceTest test` 退出 0，3 类 17 例，失败／错误／跳过均为 0。逐方法对应实际清单 `backend/target/account-profiles/test-manifest.json`，日志 `backend-tests.log`；新增覆盖规范化、长度限制、旧请求保留、明确清空、同券商账户独立及验证失败不部分修改实体。
  - `npm --prefix frontend test -- src/services/brokerAccountApi.test.js` 退出 0，1 文件 2 例，覆盖联系人字段与账户写入请求；最终 `npm --prefix frontend run build` 退出 0，保留既有 CJS／大分块提示。日志分别为 `backend/target/account-profiles/frontend-tests.log` 和 `frontend-build.log`。
  - `python3 -B e2e/verify-account-profiles.py` 最终退出 0，日志 `backend/target/account-profiles/database-check.log`。临时 PostgreSQL 验证空库 V34、V33 带资料升级的全部回填分支、字段原值及旧账户审计保留、旧列删除、交易／批次／连接不变、强制失败完整回滚及重试。真实 Spring/JPA／HTTP 验证创建／读取、省略和显式 JSON null 保留、空串清空后重新 GET、长度拒绝、同券商两账户独立及券商／嵌套券商响应不再含账户资料。临时应用及集群已清理，无真实券商请求或现有账本访问。
  - 宿主机已有 Node26.4.0／Chrome157.0.8103.79 执行 `node backend/target/account-profiles/ui.mjs` 退出 0，全部 API 由 CDP 拦截为合成数据。点验券商表格／表单无账户资料，账户资料新增／显示／回填／编辑／清空、历史邮箱空白、失败保留输入、同券商账户隔离，以及待补全账户资料编辑。无未捕获页面异常；截图已检查，临时浏览器配置已移除。日志、合成截图和请求清单位于 `backend/target/account-profiles/ui.log`、`ui.png`、`ui-requests.json`。
  - 五个数据库 Java helper 均编译通过，日志 `backend/target/account-profiles/helper-compile.log`。多账户 helper 的历史迁移阶段明确固定至 V33，保留原场景；正常应用阶段校验当前结构。本轮没有执行其余较宽数据库运行器，不将编译通过描述为运行通过。当前代码／测试／运行器／构建配置的 275 文件哈希保存在 `backend/target/account-profiles/source-inputs.json`。

  开发环境确认 `dev/vortex_dev`、无活动批次后，先在忽略的产物目录保存权限 0600 的升级前数据库备份，再由新后端执行 V34 及 JPA 校验；没有重置开发库。前端已重启提供最终模块。账户、数据库、后端资源及当前知识已同步；历史方案和原全量记录保留，交易／事件／同步业务规则未改变，对应算法文档无需修改。独立 Agent 只读复核本次 V34、实体／服务／API、页面、用例、运行器及当前知识／部署／归档，复算原始报告和 275 项哈希后通过，无剩余可行动问题；未重审无关账本算法或真实券商路径。29 份变更 Markdown 的 597 处本地链接及锚点、新文件空白和 `git diff --check` 均通过，前端、后端健康及代理券商 API 均 HTTP 200，并确认券商响应无账户资料、实际前端模块含新字段。未提交代码。

- [x] **真实托管账户 ID 与同券商多账户**

  **2026-11-22 全量验证补充：**用户明确要求“全量测一下”后，在同一 `ubuntu-dev` 工作树运行以下检查，全部退出 0。执行环境、工具及写入范围与下文一致，生产代码、测试和依赖未修改；此前局部结果保留为历史记录。日志目录为 `backend/target/full-validation-2026-11-22/`。

  | 命令 | 实际结果 | 日志 |
  | --- | --- | --- |
  | `mvn -B -ntp -o -f backend/pom.xml test` | 38 个源类、98 个含嵌套 suite、566 个实际案例；失败／错误／跳过均为 0 | `backend-unit-1.log` |
  | `npm --prefix frontend test -- --reporter=default --reporter=json --outputFile=../backend/target/full-validation-2026-11-22/frontend-unit.json` | 9 文件、60 个案例全部通过，无跳过 | `frontend-1.log` |
  | `npm --prefix frontend run build` | 构建成功；保留既有 CJS／大分块提示 | `frontend-2.log` |
  | `python3 -B e2e/verify-fee-precision.py` | 费用迁移、真实 ORM 精度与汇总、历史数值保留及迁移失败回滚／重试通过 | `database-1.log` |
  | `python3 -B e2e/verify-ordered-market-events.py` | 全部现有事件顺序／历史锁、事务／并发、同步清理及精确期权关联场景通过 | `database-2.log` |
  | `python3 -B e2e/verify-ibkr-settings.py` | 旧库升级、明文凭证／事务／重启、真实 HTTP 及活动批次凭证快照场景通过 | `database-3.log` |
  | `python3 -B e2e/verify-ibkr-settings.py --connections-only` | V33 历史迁移、分账户导入／持仓／事件／凭证及 HTTP 的 8 组场景通过 | `database-4.log` |

  后端全量报告在后续费用运行器重新执行指定测试前已独立保存至 `unit-reports/`。`full-unit-manifest.json` 逐项对应所有源测试类、注解方法、嵌套 suite、参数化展开及前端命名案例，确认无缺失、重复或跳过；`source-inputs.json` 记录 272 个代码、测试、运行器及构建配置文件的哈希。各命令、退出状态和耗时保存在 `*-results.json`，最终汇总为 `summary.json`。独立 Agent 从原始 XML／Vitest JSON 及日志复算清单、数量、数据库场景和清理结果，确认与归档一致且输入哈希未变化，未发现需要修正的问题。四个数据库入口使用各自临时 PostgreSQL 和合成数据，均已停止应用／数据库并移除临时目录；未连接真实券商或现有账本，未清空开发库。上轮合成浏览器点验继续有效，本次没有重复运行或扩大为完整浏览器回归。Tiger 正常暂存时间链和 IBKR 错误码仍按 TODO 处理，测试通过不代表这两项已修复。开发服务检查为前后端 HTTP 200，文档 95 处本地链接及锚点、`git diff --check` 均通过。

  2026-11-22 完成，方案 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。保留券商名称及代码，新增 `broker_accounts`，独立维护本地账户主键、真实账户 ID、名称和启停。页面支持同券商多账户；连接、批次、交易、流水、持仓、期权关联、市场事件成本及取整按账户区分。Tiger 资金账号来自账户资料，连接只保存 Tiger ID／私钥；IBKR 保存 Token／Query ID。明文凭证及允许同步期间编辑的决定沿用，进一步保护继续留在 TODO。

  用户确认旧数据每券商迁至一个真实 ID 为空的“待补全账户”。V33 保留全部历史交易／流水（含删除行、事件行）的原券商、数值、关联和更新时间，不猜测真实账号或重算。原连接迁至该账户；历史批次仅依据正式交易确定唯一原归属，券商代码转移不能改变历史账户，空／多归属批次保持未知。历史暂存空账户保留，新写入／更新须有来源账户。待补全账户可改名或停用，补齐后才可同步，已补齐 ID 不可清空。旧仅传券商的请求必须能唯一确定账户，否则拒绝。

  环境为 `ubuntu-dev` 当前工作树 `/home/suzywang.guest/workspace/vortex`，Java25.0.5、Maven3.10.1、Node22.22.1／npm9.2.0、Python3.14.4、PostgreSQL18.6。产物在工作树、临时集群在 `/tmp`，沿用既有缓存，无安装或依赖变更。仅执行直接相关单测和针对性验收，未执行全量单测或整套回归。

  - 后端以 `mvn -B -ntp -o -f backend/pom.xml -Dtest=<所选类> test` 分组运行账户、连接、批次、交易、持仓、事件及两券商暂存／导入／去重相关类。日志位于 `backend/target/multiple-accounts-{core,pipeline,dedup,owner,final-owner,pending,option-move}-tests.log`，命令与所选类见各日志。逐项核对 18 个所选源类全部注解方法、49 个含嵌套 suite 和参数展开后的 327 个有效案例，当前失败／错误／跳过均为 0，清单 `backend/target/multiple-accounts-manifest.json`。最初 pipeline 组的来源账户空白规范化错误已修正并重跑受影响组；该次原失败不计作通过。327 是复用未受后续改动影响结果的组合，非一次全量命令。
  - `npm --prefix frontend test -- src/services/brokerAccountApi.test.js src/services/syncConnectionApi.test.js src/services/ibkrSettingsApi.test.js src/constants/__tests__/menuConfig.test.js` 退出 0，4 文件 15 例。日志 `backend/target/multiple-accounts-frontend-tests.log`。最终 `npm --prefix frontend run build` 退出 0，保留既有 CJS／大分块提示，日志 `backend/target/multiple-accounts-frontend-build.log`。
  - `python3 -B e2e/verify-ibkr-settings.py --connections-only` 最终退出 0，8 组 PASS，日志 `backend/target/multiple-accounts-db-check.log`。临时 PostgreSQL 实际验证空库 V33、V32 历史升级、强制失败回滚／重试、外键、唯一约束及凭证事务回滚。真实 Spring/JPA／IBKR 调用链覆盖同 ID 跨账户、重复／冲突／部分失败、两张 IBKR 暂存表和正式行清理、其他账户保留、全局活动批次限制、拉取期间券商代码转移后的固定归属、期权关联、期权来源移动的反向引用校验与软删除引用放行、持仓、逐账户代码变更及分红取整。Tiger 暂存及 ORM 导入使用明确的 epoch 夹具，未覆盖且未修复正常暂存时间缺陷。
  - 同一 focused 运行器的 HTTP 验证覆盖账户 CRUD、待补全改名／停用／补齐及禁止再次清空 ID、重复／关联删除拒绝、交易改账户后的响应及查询过滤、流水归属、秘密留空／独立清空和歧义旧请求拒绝。所选第二 IBKR 账户异步触发两次 loopback 假 Flex 请求，核对该账户 Token 及 Query ID／ReferenceCode，合法空报表完成。没有真实券商访问，临时应用和集群已清理。
  - 宿主机用已有 Node26.4.0／Chrome157.0.8103.79 运行 `node backend/target/multiple-accounts-ui.mjs` 退出 0，全部 API 由 CDP 拦截为合成数据。4 组点验覆盖券商字段、同券商新增第三账户、待补全改名／停用／补齐、两类分账户凭证、失败保留、保存期间禁止关闭、秘密留空与独立清空、提交本地主键及显示真实 ID、关联交易／触发源隔离。无未捕获异常，临时浏览器配置已清理；日志、合成截图及请求清单为 `backend/target/multiple-accounts-ui.log`、`multiple-accounts-ui.png`、`multiple-accounts-ui-requests.json`。页面点验与真实数据库验收分别执行。
  - 四个数据库 Java helper 均已编译适配当前 API 和 V33 约束；费用迁移阶段保持 V29 断言，ORM 阶段使用当前结构。除 focused 入口外，旧配置、费用及完整事件三个较宽运行器本轮没有执行，旧版本结果不能证明当前全部回归通过。

  开发库核对目标后已升级 V33，`dev/vortex_dev` 后端和前端已重启，未重置开发库。操作、API、部署及备份／回退已更新 [账户指南](accounts.md)、同步及配置文档和当前知识。独立 Agent 只读复核 V33、账户／账本／事件服务、连接与批次、两适配器完整归属调用链、前端、相关测试和 helper、当前知识及部署文档。复核发现的 JPA 更新响应、代码转移后的导入归属、待补全编辑、期权反向引用移动及验收证据边界问题均已修正并补验；未发现剩余可行动问题。排除不相关业务的重审及真实券商对账。交付检查覆盖 28 份变更 Markdown 的 588 处本地链接和锚点、新文件空白及 `git diff --check`；均通过。宿主机访问前端、后端健康及前端 API 代理均 HTTP 200，并确认前端实际提供最终账户模块。未提交代码，未验证真实券商非空响应或现有用户账本；Tiger 时间缺陷及 IBKR 错误码待办继续保留。

- [x] **托管账户统一管理与IBKR／Tiger账户连接**

  本条记录 V32 阶段，当时复用了券商行作为账户，尚无独立真实账户 ID；该模型现已由上方 V33 多账户实现取代。以下保留当时过程和验证结果。

  2026-11-22完成。按 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)，账户统一在托管账户管理中录入、选择券商及启停；同步配置列出全部已有账户，按账户配置IBKR的Token／Query ID或Tiger的Tiger ID／PKCS#8私钥／资金账号。同步页不创建账户，连接保存不修改账户资料。Token／私钥首次必填、之后留空保留或填写替换，确认后清空所选连接。批次选项仅允许已启用且配置完整的账户。

  V32新增 `broker_sync_connections`，按账户保存明文JSONB凭证，迁入匹配账户的旧明文IBKR配置并移除单例表；孤立配置使迁移回滚，修正账户关联后可重试。两券商生产凭证只读数据库，Tiger每批捕获同一组值供客户端及全部日期窗口使用。沿用每券商代码唯一的账户模型；旧IBKR接口由统一连接服务提供，保存要求账户已在账户管理选择IBKR。部署顺序、失败恢复及旧profile不回退的说明已同步。未来保护设计继续保留在 [TODO](TODO.md)。

  局部验证在 `ubuntu-dev` 当前工作树执行，Java25.0.5、Maven3.10.1、Node22.22.1／npm9.2.0、Python3.14.4、PostgreSQL18.6；产物在工作树，临时集群在 `/tmp`，沿用既有缓存，无依赖或工具安装。

  - `mvn -B -ntp -o -f backend/pom.xml -Dtest=BrokerSyncConnectionServiceTest,BrokerSyncConnectionControllerTest,IbkrSyncSettingsServiceTest,IbkrSyncSettingsControllerTest,BrokerServiceTest,TigerSyncAdapterTest test` 退出0，6类14例，0失败／错误／跳过。逐项核对实际方法清单，覆盖全部所选用例；日志 `backend/target/account-connections-tests.log`，清单 `backend/target/account-connections-manifest.json`。
  - `npm --prefix frontend test -- src/services/syncConnectionApi.test.js src/services/ibkrSettingsApi.test.js src/constants/__tests__/menuConfig.test.js` 退出0，3文件13例，验证账户请求范围、秘密字段留空省略、多行私钥、旧接口及菜单。日志 `backend/target/account-connections-frontend-tests.log`。最终页面 `npm --prefix frontend run build` 退出0，保留既有CJS和大分块提示；日志 `backend/target/account-connections-frontend-build.log`。
  - `python3 -B e2e/verify-ibkr-settings.py --connections-only` 最终退出0，验证空库V32实际Spring启动、V31明文旧配置升级归属、孤立配置回滚／修正重试、两类数据库凭证及真实事务回滚；实际HTTP验证全账户元数据、Tiger私钥留空保留、跨账户保存／清空隔离及Token／私钥不回传或写入日志。假Flex固定为loopback且无请求，临时应用及PostgreSQL集群已清理。日志 `backend/target/account-connections-check.log`。
  - 宿主机使用已有Node26.4.0和Chrome157.0.8103.79执行 `node backend/target/account-connections-ui.mjs`，以临时隔离浏览器配置和CDP拦截全部 `/api` 请求，仅使用合成账户／凭证。实际页面覆盖账户管理跳转与新建、同步列表、两类字段、首次必填、失败保留输入、留空保留、单账户确认清空及批次选项；挂起保存响应时关闭按钮和取消禁用，Esc／遮罩点击经过动画等待后弹窗仍可见且处于保存中。无未捕获页面异常。日志 `backend/target/account-connections-ui.log`，合成截图 `backend/target/account-connections-ui.png`，请求路径清单 `backend/target/account-connections-ui-requests.json`，临时浏览器配置已清理。该页面验收与真实数据库验证分别执行，不代表真实券商联调。

  独立Agent只读复核连接服务／API、V32、两个适配器调用链、前端、局部用例／运行器及相关当前知识和部署文档；发现的保存期间弹窗关闭竞态已修正，验收也补充秘密不回传、跨账户独立保存及假Flex端点断言，最终无未解决问题。未重审不相关账本业务。

  开发后端确认 `dev/vortex_dev` 且无活动批次后已恢复，Flyway升级V32及JPA校验成功。页面验收发现共享挂载中的Vite仍返回旧模块，已重启当前工作树记录的前端进程并确认实际返回新账户连接页；Mac访问前端、后端健康和前端代理连接接口均HTTP200。遵照用户约定，本轮未执行全量单测或整套回归，旧IBKR较宽验收模式也未重跑。真实券商授权／非空交易导入未验证，Tiger既有时间格式缺陷继续保留在TODO。

- [x] **移除未使用的 IBKR 加密兼容**

  2026-11-21完成。用户进一步确认从未使用旧加密方案、没有历史凭证，按 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md) 删除早期实现中的旧密文转换回调、密钥读取及对应合成夹具和验收场景。已应用V31 SQL按研发约定保留原样。当前凭证通过页面明文保存和读取，当前部署及恢复说明已同步。下方早期阶段的已执行验证保留为历史记录。

  验证使用同一 `ubuntu-dev` 工作树，Java25.0.5、Maven3.10.1、Python3.14.4、PostgreSQL18.6，产物位于工作树，临时文件位于 `/tmp`。测试前删除已移除回调的旧编译文件，确认后端类路径中该类不存在。

  - `TMPDIR=/tmp MAVEN_OPTS=-Djava.io.tmpdir=/tmp mvn -B -ntp -o -f backend/pom.xml test` 退出0，551例、0失败／错误／跳过；34源类、94suite及551个case与上一轮完整清单逐项一致。日志 `backend/target/plaintext-no-legacy-tests.log`，清单 `backend/target/plaintext-no-legacy-manifest.json`。
  - `python3 -B e2e/verify-ibkr-settings.py` 退出0，日志 `backend/target/plaintext-no-legacy-e2e.log`。实际验证无凭证V29／V30库升级至V31及账户保留、空库初始化、明文保存／读取／保留／替换／清空、事务回滚、应用重启、活动批次期间修改及假Flex请求使用配置，临时集群和进程已清理。
  - 独立Agent复核本轮源码、调用链和文档，确认旧密钥／解密依赖和兼容夹具已移除，未发现未解决问题。前端源码和验证条件与上一轮一致，复用56项单测和构建结果。

  开发后端已重新启动，Flyway成功验证全部31条迁移及既有V31校验和，Mac访问前端、后端健康和配置代理均HTTP200。此轮使用合成验收，真实券商及浏览器页面点验沿用前轮说明。

- [x] **按个人本地使用场景简化 IBKR 凭证配置**

  2026-11-21完成。用户要求页面保存凭证到数据库明文并直接读取，将复杂保护交由后续TODO安排。按 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)，运行时改为直接保存／读取 `flex_token`，删除加密及密钥组件，配置与账户写入移除活动批次检查和专用短写锁。页面继续使用现有API和Token留空保留／替换／清空流程。

  V31 SQL与迁移专用回调在同一事务中保留V30已有凭证，转换后运行及数据库备份恢复以明文数据库为准。历史密文升级仅在转换时使用旧密钥。未注册回调、缺失或错误旧密钥、SQL失败均经过回滚和重试验证。0045／0046和下方2026-11-20记录保留历史含义，本段保留早期验收事实，最终范围见上方兼容清理记录。未来保护设计在 [TODO](TODO.md) 中继续未勾选。

  验证环境为当前工作树映射的 `ubuntu-dev`，Java25.0.5、Maven3.10.1、Node22.22.1、npm9.2.0、Python3.14.4、PostgreSQL18.6，临时文件使用 `/tmp`，产物写入工作树，使用既有Maven／npm缓存。首次离线检查发现测试插件缓存不足，使用既有依赖源补齐后执行最终检查，依赖清单及锁文件保持一致。

  - `TMPDIR=/tmp MAVEN_OPTS=-Djava.io.tmpdir=/tmp mvn -B -ntp -o -f backend/pom.xml test` 退出0，551例，0失败／错误／跳过。34个测试源类、94个实际suite及全部case核对通过，清单 `backend/target/plaintext-settings-manifest.json`，日志 `backend/target/plaintext-settings-tests.log`。与旧555例相比，删除已撤回的加密组件1例和活动批次拒绝3个参数用例；对应新行为由明文单测及集成验收覆盖。
  - `npm --prefix frontend test` 退出0，7文件56例通过；`npm --prefix frontend run build` 退出0，保留既有大分块提示。日志分别为 `backend/target/plaintext-settings-frontend-tests.log` 和 `backend/target/plaintext-settings-build.log`。
  - `python3 -B e2e/verify-ibkr-settings.py` 退出0。自建临时PostgreSQL验证空库至V31、V29／V30历史库实际Spring启动升级、旧凭证保留、迁移失败回滚重试、保存事务回滚、留空保留／替换／清空及实际应用重启；受控假Flex服务验证活动批次期间配置和账户修改成功，当前报表沿用已捕获凭证，下一报表使用新明文。日志 `backend/target/plaintext-settings-e2e.log`，临时集群、应用及合成旧密钥已清理。
  - 独立Agent只读复核代码、迁移／回调、测试和文档差异，提出的验收竞态及遗漏回调问题已修正并实际验证；最终复核无未解决问题。Markdown链接及锚点、`git diff --check` 通过。

  开发后端已重新加载当前代码，`dev/vortex_dev`成功升级至V31；Mac访问前端、后端健康及前端代理配置接口均为HTTP200。这是开发环境启动检查，实际验收仅使用独立合成环境。真实券商授权及真实交易导入未执行，页面浏览器点验未在本轮重跑。

- [x] **P1 补齐IBKR账户选择和数据库凭证配置入口**

  2026-11-20发现与完成：同步发现只返回有Broker Code绑定的账户，现有账户没有IBKR绑定，且券商更新接口漏存该字段；Flex凭证仅有启动配置，没有页面入口。用户明确要求凭证存数据库。按 [0045](../design/0045-2026-11-20-ibkr-database-settings-ui.md)及 [0046修订](../design/0046-2026-11-20-ibkr-settings-database-snapshots.md)，新增同步配置页、账户新建／绑定、Token只写不读与留空保留／替换／明确清空；V30保存AES-256-GCM密文，密钥在被Git忽略的工作树内，数据库是生产唯一凭据来源，不回退旧profile。

  配置与账户绑定在同一事务保存，和批次创建共用短写锁；活动批次拒绝配置保存／清空，以及账户代码变更／停用／删除。运行时按请求读取同一组数据库凭证，不用提交后缓存回调；密钥缺失或损坏不自动换钥覆盖。修复券商代码更新漏存，拒绝新建指定已有ID，停用或缺失账户不请求券商。页面缺配置时给出入口，IBKR读取失败不影响其他券商；Token不进入浏览器存储、响应或错误日志，URL编码并遮盖参数，服务端原始错误不回显。既有错误码／重试待办保持未完成。

  验证环境：ubuntu-dev当前工作树，使用已有Java25.0.5、Maven3.10.0、Node26.13.0／npm11.20.0、Python3.14.4、PostgreSQL18.6；TMPDIR=/tmp、MAVEN_OPTS=-Djava.io.tmpdir=/tmp，沿用缓存，无安装及依赖变更。

  - `mvn -e -o -f backend/pom.xml test`退出0：最终555例，0失败／错误／跳过；95个实际执行suite、35个测试源类逐项核对无遗漏，清单`backend/target/ibkr-settings-manifest.json`，日志`backend/target/ibkr-settings-final-tests.log`。新增或扩展回归覆盖加密篡改、配置无回退、Token保留／清空、不可读密钥、活动批次、账户旁路、响应及异常不泄露。首轮554及客户端3例专项是中间结果，以最终全量为准。
  - `npm --prefix frontend test`退出0：56例、7文件通过，含新增配置API契约2例；`npm --prefix frontend run build`最终退出0，日志`backend/target/ibkr-settings-final-build.log`，保留既有分块体积及CJS提示，未改依赖。
  - `python3 -B e2e/verify-ibkr-settings.py`退出0，日志`backend/target/ibkr-settings-e2e.log`：自建临时PG验证空库、V29→V30及合成旧账户不变，真实事务回滚／并发锁与提交可见性，实际应用重启后保存／清空状态，密钥权限及符号链接拒绝，真实HTTP配置与密文落库，活动批次拒绝保存／清空／停用／删除，密钥丢失不再生成，以及本地假Flex完整两步请求的合法零交易同步。临时进程、密钥与集群已停止并清理。
  - 同一运行器`--serve`配合浏览器实际点验：未配置IBKR占位→配置入口→新建合成账户→保存→IBKR可选；Token输入框清空、刷新持久化、留空保留；密钥不可读时Tiger仍可选择，明确清空后可重新配置，console无合成Token。截图`target/ibkr-settings-ui.png`。这些验证仅用合成数据和本地假服务，未使用真实券商或账本。

  独立只读复核逐文件检查了配置／加密／账户／同步边界、V30、前端及回归验收；已修复其他券商选项受IBKR异常影响、远程错误泄露路径及旧配置指引。复核555例清单与报告逐项一致。文档同步当前配置、接口、迁移和密钥备份恢复要求。开发后端随后确认dev／vortex_dev且无活动批次，加载新接口就绪；这一步是本地应用更新，不作为合成测试结果。真实授权及实际交易导入没有验证，由用户填写凭证后手动发起。

- [x] **P1 明确 IBKR 期权关联回填的匹配范围与歧义处理**

  股票侧期权交易的候选查询未限定券商，多个候选经过行权价、数量匹配后仍可能直接取首条。同一天、同一标的出现不同券商或多个合约的行权、指派记录时，关联可能指向错误的期权交易，候选返回顺序也会影响结果。 [候选查询](../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java) · [关联回填与消歧](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java)

  验收需用合成的跨券商、不同到期日、相同行权价和相同数量记录覆盖唯一匹配、无匹配与多候选。成功回填须能证明关联属于正确的券商和期权合约。交换候选顺序或重试时结果应一致，无法唯一确定的关联应按明确的设计暴露并处理。

  2026-11-16 复核：在 `ubuntu-dev` 使用真实 Worker、Mockito 仓储和合成记录运行复现。相同行权价的两个候选中，即使只有第二个数量吻合，仍选择第一个；交换候选顺序后结果改变。仅返回其他券商候选时也直接接受。真实 XML 文件内的 45 组配对均唯一，但未核对数据库历史候选，不能据此证明线上关联正确。详见 [初次审阅 R3](#ibkr-样例与初次审阅)。本条继续未勾选，下一步明确匹配及无匹配处理口径，先写方案再修复。

  2026-11-18 用户决策：精确关联，不得跨券商；找不到期权侧记录时整次同步失败，人工排查；全部必要条件核对后仍有多个候选也整次失败。不将没有前置关联继续视为成功。这里的关联对象是同次行权／指派的期权侧簿记记录，原始开仓／持仓完整性不属于本次回填。

  2026-11-18 完成：按 [0040](../design/0040-2026-11-18-ibkr-exact-option-event-linkage.md) 和独立复核补充 [0041](../design/0041-2026-11-18-ibkr-option-link-candidate-provenance.md)，SQL限定同券商／币种、同日／标的／事件和有效期权记录，历史同步候选须来自COMPLETED批次，本批及无来源人工记录按已定义边界处理，旧失败残留被排除。所有匹配条件同时核对，包括CALL／PUT与买卖方向、精确行权价和long计算的100倍数量；严格解析合约代码及日历日期，不按事件日猜到期日。先统计全部匹配，0或多个失败，唯一候选被有效股票／ETF事件引用也失败，不靠先排除占用项猜选。同步候选须核实同批次暂存来源及100乘数；缺失／非法／非100拒绝，人工记录沿用既有标准模型。持锁重读股票后重验资格，原关联不自动改写。

  移除回填吞异常及取第一条兜底，错误向Adapter传播，整次同步进入既有失败清理。清理成功FAILED、清理失败CLEANUP_FAILED，批次保留匹配数量或失败类别、内部股票行ID和人工排查提示；原成功批次不删除或重算。没有新增表、迁移、依赖或API变化，也未自动修复历史关联。拆分／合并簿记、非标准交割及历史开仓重建仍需另外明确规则。

  验证环境：ubuntu-dev当前工作树，Java25.0.5、Maven3.10.0、已有Python／PostgreSQL工具；TMPDIR=/tmp、MAVEN_OPTS=-Djava.io.tmpdir=/tmp，沿用缓存，未安装工具。先用合成回归复现旧逻辑：当时45项中23失败、退出1，日志`backend/target/ibkr-exact-link-red.log`。最终`mvn -o -f backend/pom.xml test`退出0，538项全通过，无失败／错误／跳过，日志`backend/target/ibkr-exact-link-final.log`；31源类、317注解方法的实际执行清单为`backend/target/ibkr-exact-link-manifest.json`。原回填两方法由严格回归替换，其他既有案例无缺失。保留既有Surefire退出握手等待提示，没有改超时或减少验证范围。

  `python3 -B e2e/verify-ordered-market-events.py`退出0，在运行器自行创建的临时PostgreSQL中验证实际JPQL券商／币种／稳定历史过滤、跨成功批次关联、零／多匹配失败、引用占用、整批清理、重复／修正重试，以及真实关联UPDATE失败的事务回滚。旧市场事件与同步并发保护场景一并通过，临时服务已停止且目录删除，日志`backend/target/ibkr-exact-link-db-final.log`。报表提供器使用合成XML，没有真实网络、个人XML或vortex／vortex_dev访问。前端未改，未重跑页面／构建。 [完整验收及限制](#期权关联验收明细)

  当前knowledge、恢复说明及e2e入口已同步；实现、最终执行证据和归档经未参与实现的Agent独立复核通过，无剩余可行动问题。原问题与旧历史完整保留，当前待办剩2项；17文件差异范围、12份Markdown的239处本地链接、新增文件空白及`git diff --check`均通过。

  2026-11-18 后续范围修订：用户明确历史数据准确，无需兼容。按 [0042](../design/0042-2026-11-18-trust-existing-option-event-records.md) 撤回0041的历史批次状态筛选、旧暂存来源反查和人工记录例外；既有正式期权记录直接信任并按同一业务条件匹配。合约100乘数只检查本次新导入、尚无正式记录的期权行权／指派记录；正式重复兜底不追加此门槛，原暂存同ID内容冲突规则继续生效。保留0040同券商唯一精确匹配、零／多匹配失败、一对一占用、正常重读及整批清理，不扫描或修改历史数据，也不改已提交的其他市场事件保护。上文0041实现及538项结果仅记录当时版本，当前以本段及最新验收为准。

  0042验收：同一ubuntu-dev环境下，`mvn -o -f backend/pom.xml test`全量539项通过，退出0，日志`backend/target/ibkr-trusted-history-final.log`；之后仅补PUT交割及期权到期两个输入边界，生产代码未变，最终`mvn -o -f backend/pom.xml -Dtest=IbkrImportWorkerTest,IbkrImportServiceTest test`专项70项通过、退出0，日志`backend/target/ibkr-trusted-history-focused-final.log`。最终组合去重清单`backend/target/ibkr-trusted-history-manifest.json`为31类、317注解方法、541当前案例（未改套件471加最终专项70），无失败／错误／跳过；撤回6个旧兼容案例，新增9个当前输入／正式重复案例，其他无缺失。541并非另一次独立全量命令的数量。

  `python3 -B e2e/verify-ordered-market-events.py`在自建临时PostgreSQL中通过，日志`backend/target/ibkr-trusted-history-db.log`，验证旧暂存来源不存在也能使用准确正式期权记录，以及精确匹配、失败／回滚／清理和重试；临时集群停止并删除。0041新增的期权关联旧失败残留和篡改旧引用模拟已移除，正常占用与既有同步／市场事件验收保留。未访问真实账本、券商或个人数据。最终实现、组合清单、文档和归档独立复核通过，无剩余可行动问题；18文件范围、13份Markdown的244处本地链接、新增文件空白及`git diff --check`通过，0040／0041历史正文和原有归档完整保留。

- [x] **P2 用可提交的合成数据替换解析测试对个人夹具的依赖**

  `FlexQueryParserTest` 从 classpath 读取一个被 Git 忽略的个人 XML 文件，并断言该资源存在。干净检出缺少资源会导致测试失败，开发者还需要自行提供账户样本。 [解析测试](../backend/src/test/java/com/vortex/sync/adapter/ibkr/FlexQueryParserTest.java) · [个人夹具忽略规则](../backend/.gitignore)

  验收需使用可提交的合成 XML 或测试内构造的数据，覆盖当前解析断言和必要的异常输入。干净检出应能离线运行解析测试，并且夹具、断言及测试输出均使用合成数据。

  2026-11-17 完成：按 [0039](../design/0039-2026-11-17-test-suite-maintenance.md)，移除 4 个个人资源方法，使用测试内最小合成 TCF 报表覆盖字符串／流两个入口、封套元数据、Order、ExchTrade、BookTrade、费用原值及日期／时间转换；保留合法零交易、缺层级诊断，非法 XML／DOCTYPE 由既有 63 项结构回归覆盖。去掉个人数据和调试明细输出，Maven 显式排除个人 XML 测试资源；原个人文件未读取、改动或删除。默认后端全量命令无需筛选方法，测试源码不再访问该文件，生成测试资源中也没有该文件。未另建干净 checkout，验证不依赖个人夹具的依据是合成代码、资源排除和默认实际运行结果。

  验证环境：`ubuntu-dev` 当前工作树 `/home/bytedance.guest/workspace/private/vortex`，Java 25.0.5、Maven 3.10.0、Node 26.13.0、npm 11.20.0；`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`，沿用已有工具和缓存，没有安装或连接数据库／券商。`mvn -o -f backend/pom.xml test` 最终 496 项通过，失败／错误／跳过均为 0，BUILD SUCCESS；`npm --prefix frontend test` 为 6 文件、54 项通过，退出 0。日志为 `backend/target/test-cleanup-final.log`、`frontend/test-cleanup-after.log`；默认全量仍有既有 Surefire 退出握手提示，未通过缩小测试范围或改超时规避。

  本次同时清理已证实重复的测试。逐项对照、保留断言及验证限制见 [测试清理依据](#测试清理依据)，执行清单为 `backend/target/test-cleanup-final-manifest.json`：31 个后端源测试类全部执行，原 504 个实际案例仅移除 10 项、增加 2 个合成入口案例，无其他缺失或跳过。当前运行说明、单测维护规范和 IBKR knowledge 已同步；既有数量、费用和结构验收记录保持当时原貌。

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

  解析器只读第一份 FlexStatement 和第一组 TradeConfirms；缺少节点时返回空结果，不校验报告类型。合成两份 statement 只解析一份；不含所需节点的 AF 报表、含成交但无订单层的 TCF 报表均可由适配器报告成功 0。上述结果在 `ubuntu-dev` 通过真实解析器与模拟仓储复现；本次真实文件的结构完整，未触发。 [解析入口](../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L107) · [初次审阅 R4](#ibkr-样例与初次审阅)

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

  暂存仅按 orderID 是否存在决定跳过，不比较内容。后续同 ID 订单的数量、金额或费用变化不会更新正式交易，新 tradeID 的成交却可能继续进入暂存表，造成明细与账本不一致，批次仍成功。合成数量 10→20 的同 ID 订单已在 `ubuntu-dev` 复现第二次跳过；本次真实文件没有同 ID 不同快照，未证明已有账本发生漏单。 [暂存去重](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrStagingWorker.java#L51) · [初次审阅 R2](#ibkr-样例与初次审阅)

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

  本次 XML 的 295 条订单费用超过两位小数，佣金最多 8 位；当前 mapper 保留源精度，正式表 fee 却为 `DECIMAL(12,2)`，按声明 schema 写入将逐单舍入。此为文件统计及静态 schema 结论，未实际插库。是否接受按分舍入仍待用户确认。 [费用列](../backend/src/main/resources/db/migration/V3__create_trade_records.sql#L66) · [初次审阅 R1](#ibkr-样例与初次审阅)

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

  `BigDecimal.intValue()` 会将小数股截为整数，也可能让超范围值溢出为仍合法的正整数。当前 mapper 在 `ubuntu-dev` 将合成数量 1.5 和 4294967297 均转换为 1，来源金额没有同步改变。真实文件均为范围内整数，未触发。 [数量转换](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java#L400) · [初次审阅 R5](#ibkr-样例与初次审阅)

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

  Parser 与 Adapter 在 INFO 日志直接打印 accountId，真实同步会将账户标识写入日志。`ubuntu-dev` 的合成日志捕获已确认账户字符串原样出现；本轮未将真实 XML 交给该日志链路。 [解析日志](../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L170) · [同步日志](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapter.java#L281) · [初次审阅 R6](#ibkr-样例与初次审阅)

  2026-11-16 处置：用户明确接受当前阶段在应用日志中打印账户 ID，认为现阶段无需对此脱敏。已在 AGENTS.md 的数据安全约定中明确此例外，取消本项修复及原账户脱敏验收要求，保留发现和复现记录。本项不再属于活动待办，也不标记为已修复完成。密码、Token、密钥和交易明细仍遵循原约定；未来若调整账户日志要求，再重新评估并制定方案。

## 补充调查与验收证据

以下内容合并自原审计记录，补充上方条目未完整记录的样例核对、测试取舍及验收细节；日期和结果属于当时基线。`target/` 中的调查程序、清单和日志是被 Git 忽略的本地证据，不随仓库分发，不能据此承诺干净检出后可直接重现当次调查。可维护的验证入口仍在 `e2e/` 和单测源码中。

### IBKR 样例与初次审阅

审阅日期：2026-11-16，源码基线 `e6d5945`。当次仅离线读取 Trade Confirmation XML 并运行合成复现，未请求 IBKR、查询或写入账本。样例 SHA-256 为 `2ec115850d1de8c7aa4c7446191fa73248168dde672dd332a59eaa638a20ef5c`；这里只保留统计，不记录账户、证券、订单编号或交易金额。

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

“574 笔成交只生成 538 条正式交易”不能单独作为漏单证据：正式交易采用订单粒度，此文件的逐单汇总全部对上。SymbolSummary 是另一层汇总，不应再次生成交易。Order 层未暂存的 exchange、code、tradeID、execID、transactionType、brokerageOrderID、orderReference 在该文件中均为空，未发现这些字段造成的样例信息丢失。

初次发现的完整问题、用户口径与修复记录已保留在上方对应条目；以下补充原审阅的触发边界，均属于 `e6d5945`，不代表修复后的行为：

- R1 费用：当时实体与 V3 的费用列为 `DECIMAL(12,2)`，后续迁移尚未扩展。入库舍入为 schema 与 [PostgreSQL Numeric Types](https://www.postgresql.org/docs/18/datatype-numeric.html#DATATYPE-NUMERIC-DECIMAL) 的静态推断，初次审阅未实际插库；V29 的后续实际落库验证另见费用归档。
- R2 重复：文件有 32 个多笔成交订单，但无同 ID 不同快照或跨日订单。合成数量 10→20 的订单被跳过、新成交仍可暂存，不证明该文件或实际账本已漏单。
- R3 关联：原查询无券商限制，单候选直接接受；多候选先按行权价取首个，仅无价格匹配时才按数量，再兜底首条。交换候选顺序可改变结果；无候选时保留 ref_id=0 并警告，批次仍可成功。文件内 45 组唯一匹配不证明数据库历史候选也唯一。
- R4 结构：原 Parser 只取第一份 statement／成交组、不校验根节点和 TCF 类型；两份 statement 各一单只返回一单，AF 无 TradeConfirms 或 TCF 非空成交但无 Order 可成功零条。样例结构完整，未触发这些边界。
- R5 数量：`intValue()` 把合成 1.5 和 4294967297 都转成 1，来源金额不变；样例均为范围内整数，未触发。
- R6 日志：合成日志捕获确认账户 ID 原样进入 INFO。真实样例只由独立统计脚本读取，没有交给该日志链路；此项随后按用户决定接受。
- R7 错误码：1005、1006、1012 的说明及 NETWORK 分类不符官方语义；此为静态对照，未逐码模拟响应，也未请求真实服务。问题仍在 [TODO](TODO.md)，不能把成功样例或其他修复当作该路径已验收。

初次验证环境为 `ubuntu-dev`，工作树 `/home/bytedance.guest/workspace/private/vortex`；Temurin 25.0.5、Maven 3.10.0、Python 3.14。沿用离线缓存，JVM 临时目录 `/tmp`，编译／测试在 `backend/target/`，调查程序在 `target/ibkr-audit/`，未安装工具或依赖。

| 入口／命令 | 结果 |
| --- | --- |
| `python3 -B target/ibkr-audit/sample-check.py target/ibkr-samples/ibkr-trade-confirms-6521969318.xml` | 退出 0；逐单六项汇总一致，统计见上表 |
| `mvn -o -f backend/pom.xml -Dtest=IbkrFlexClientTest,IbkrSyncAdapterTest,IbkrStagingServiceTest,IbkrStagingWorkerTest,IbkrImportServiceTest,IbkrImportWorkerTest test` | 退出 0；51 项通过，无失败、错误或跳过 |
| `target/ibkr-audit/AuditProbe.java`，运行 `com.vortex.sync.adapter.ibkr.AuditProbe` | 编译／运行均 0；11 项断言通过，包含控制场景，不代表 11 个独立缺陷 |

AuditProbe 使用现有编译类及 `backend/target/dev-run/runtime-classpath.txt`，JVM 参数为 `-Djava.io.tmpdir=/tmp -XX:+EnableDynamicAgentLoading`。调用真实 Parser、暂存、映射和回填 Worker，仓储与网络均 Mockito；不启动 Spring 应用或数据库。初次没有运行依赖个人 XML 的四个 Parser 方法，后续合成替换见下节。

初次逐文件阅读范围为 IBKR 目录全部 13 个 Java 文件、两种暂存实体／仓储、TradeRecord／仓储及相关枚举；批次 Controller／Service、SyncRequest、路由／异步执行、失败处理和清理链；V3、V20、V21、V22、V28 全文并检索其他迁移；上述六个测试类全文，Parser 测试在隐藏个人字面值后核对控制流程；基础配置、环境说明、同步知识、恢复指南及 TODO。历史设计仅检索 0009、0012、0013、0018、0019 的相关段落，没有重审全文。

范围不包含全仓库、真实账本对账、PostgreSQL 事务／唯一索引集成、启动恢复、多实例并发、前端原始数据、其他券商及市场事件。没有证明 XML 覆盖全部历史或请求日期范围，也没有证明历史持仓、盈亏或既有人工导入正确。后续修复和验收不反向改变这些原审阅限制。

### 测试清理依据

日期：2026-11-17，清理前基线 `4c876b2`，方案 [0039](../design/0039-2026-11-17-test-suite-maintenance.md)。原个人夹具问题及完成过程见上方条目；这里保留删除／合并依据及完整执行范围。没有发现应整组删除的下线功能测试；生产代码、迁移、依赖和前端代码未变。

原位置可用 `git show 4c876b2:<仓库相对路径>` 复核；以下链接指向清理后保留的断言。

| 原测试（共 10 个执行案例） | 原输入与断言 | 保留依据 |
| --- | --- | --- |
| SyncResultTest：GetterSetterTest.defaultConstructor、settersShouldWork，ToStringTest.shouldContainKeyFields（3） | 默认字段、逐字段写读、调试字符串含字段 | 存储与调试实现没有独立契约；[6 个工厂案例](../backend/src/test/java/com/vortex/sync/core/SyncResultTest.java#L19)仍保护状态、计数、耗时、原因和三参数兼容接口。 |
| SyncResultTest：SuccessFactory3ParamTest.messageShouldBeEnglish、FailureFactoryTest.messageShouldBeEnglish（2） | 工厂消息含英文前缀 | 前缀并入[成功](../backend/src/test/java/com/vortex/sync/core/SyncResultTest.java#L25)和[失败](../backend/src/test/java/com/vortex/sync/core/SyncResultTest.java#L90)案例，保留券商、数量／错误、耗时；不再检查整条消息没有汉字，因为业务原因允许中文，运行时消息未改。 |
| SyncBatchFailureHandlerTest.CleanupExhausted.retriesExactlyMaxAttempts（1） | 每次清理抛异常，重试恰好最大次数 | [allAttemptsFail_marksCleanupFailed](../backend/src/test/java/com/vortex/sync/core/SyncBatchFailureHandlerTest.java#L125)同路径已有次数检查，并验证 CLEANUP_FAILED、原始／清理原因及不标记 FAILED，严格包含被删断言。 |
| FlexQueryParserTest.parseInvalidXmlShouldThrow（1） | 非法 XML 抛异常 | [结构回归](../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrReportStructureTest.java#L53)在字符串／流入口覆盖同类语法异常和 DOCTYPE 拒绝；缺层级诊断保留。 |
| IbkrImportWorkerTest.FeeCalculationTest.shouldCalculateFeeCorrectly、shouldHandleNullFeeFields（2） | 两项负费用绝对值相加；两项 null 为零 | [费用参数化回归](../backend/src/test/java/com/vortex/sync/adapter/ibkr/TradeFeePrecisionTest.java#L43)经同一 importSingleOrder 路径，6 项覆盖负值、进位、长小数、大数、零及 null，以 BigDecimal 精确比较；[普通股票映射](../backend/src/test/java/com/vortex/sync/adapter/ibkr/IbkrImportWorkerTest.java#L116)仍检查费用，未改舍入口径。 |
| TigerImportWorkerTest.PreFilterTerminalPersistsStaged.skippedBranchSavesStaged（1） | 同一合成订单命中重复 ID，暂存保存一次 | [SkippedPath.shouldSkipAlreadyImported](../backend/src/test/java/com/vortex/sync/adapter/tiger/TigerImportWorkerTest.java#L124)同输入／路径检查相同次数，并验证 SKIPPED、原因和禁止正式保存／历史写入，严格包含被删断言。 |

四个个人资源方法 `parseRealFlexQueryXml`、`verifyOrderFieldsParsedCorrectly`、`verifyTradeConfirmFieldsParsedCorrectly`、`verifyBookTradeTradeConfirm` 未纳入运行基线。替代方法 [shouldParseSyntheticTradeFields](../backend/src/test/java/com/vortex/sync/adapter/ibkr/FlexQueryParserTest.java#L23)参数化字符串／流两个入口，保留封套元数据、Order／TradeConfirm 数量、普通记录 ID／标的／方向／数量／价格，精确数量／价格／佣金转换和日期／时间；BookTrade 检查类型与分类辅助方法，不把它描述为全字段映射。合法零交易、缺层级 UNRECOGNIZED／原因和 63 项结构回归保留。个人账户和真实成交快照数量不作为稳定契约。

[Maven](../backend/pom.xml#L86)显式排除个人 XML，生成测试资源中没有该文件，原个人文件没有复制、读取或删除。没有新建干净检出；无需个人夹具的依据是合成源码、资源排除和默认命令实际运行。

[事件回归](../backend/src/test/java/com/vortex/service/OrderedMarketEventRegressionTest.java#L67)保留案例，将不再调用的级联查询模拟改为严格模拟实际历史查询；捕获实际写入的唯一行，验证事件 ID、日期和数量，后续事件保持未处理，另一处实际查询也取消 lenient。

保留清仓成本、费用精度、整数拒绝、重复内容／跨窗口与整批失败、Controller／Service／Worker 各层边界；保留首次／后续清理成功、耗尽、中断和二次状态异常，中断仍用非零退避触发。有效共享的 lenient 模拟、Token 异常链脱敏、三参数工厂兼容均保留；单字段和批次重复不按名称合并。前端六份测试与 e2e 工具未删。原当时未解决的期权歧义已在后续关联条目完成。

| 逐文件阅读范围 | 文件（省略共同目录） |
| --- | --- |
| `backend/src/test/java/com/vortex/controller/` | BrokerSyncControllerTest.java、MarketEventConflictAdviceTest.java |
| `backend/src/test/java/com/vortex/service/` | BrokerSyncBatchServiceTest.java、MarketEventHistoryServiceTest.java、MarketEventProcessingServiceTest.java、OrderedMarketEventRegressionTest.java、PositionServiceTest.java、TradeRecordServiceTest.java、TradeVerificationServiceTest.java |
| `backend/src/test/java/com/vortex/sync/core/` | BrokerSyncAsyncExecutorTest.java、BrokerSyncServiceTest.java、SyncBatchCleanupServiceTest.java、SyncBatchFailureHandlerTest.java、SyncBatchRecoveryRunnerTest.java、SyncResultTest.java |
| `backend/src/test/java/com/vortex/sync/adapter/ibkr/` | FlexQueryParserTest.java、IbkrDuplicateBatchTest.java、IbkrDuplicateConsistencyTest.java、IbkrFlexClientTest.java、IbkrImportServiceTest.java、IbkrImportWorkerTest.java、IbkrQuantityValidationTest.java、IbkrReportStructureTest.java、IbkrStagingServiceTest.java、IbkrStagingWorkerTest.java、IbkrSyncAdapterTest.java、TradeFeePrecisionTest.java |
| `backend/src/test/java/com/vortex/sync/adapter/tiger/` | TigerImportServiceTest.java、TigerImportWorkerTest.java、TigerStagingServiceTest.java、TigerTradeRecordMapperTest.java |
| `frontend/src/` | constants/__tests__/menuConfig.test.js、constants/__tests__/tradeConstants.test.js、pages/profit/chart-demo/__tests__/mockData.test.js、services/__tests__/tradeFeePrecision.test.js、utils/__tests__/marketEventError.test.js、utils/__tests__/tradeFee.test.jsx |
| `e2e/` | README.md、reset-dev-db.sh、check-market-event-history.sql、verify-fee-precision.py、FeePrecisionCheck.java、verify-ordered-market-events.py、OrderedMarketEventCheck.java、market-event-ui-server.mjs |

对照受影响的工厂、失败处理、IBKR Parser／辅助转换／费用映射、Tiger 导入及事件调用链、POM／前端清单／Vitest 配置和文档。不是全部生产源码／迁移审计；个人 XML、SQL 备份、下载样本及临时程序排除，当次只阅读 e2e 源码，没有运行数据库、券商或浏览器验证。

环境为 `ubuntu-dev` 当前工作树 `/home/bytedance.guest/workspace/private/vortex`；Java 25.0.5（目标 17）、Maven 3.10.0、Node 26.13.0、npm 11.20.0，`TMPDIR=/tmp`、`MAVEN_OPTS=-Djava.io.tmpdir=/tmp`。沿用工具和缓存，仓储／网络全模拟，不使用应用 profile。

| 阶段与命令 | 结果与原日志 |
| --- | --- |
| `mvn -o -f backend/pom.xml '-Dtest=*Test,!FlexQueryParserTest' test` | 清理前 501 项通过、退出 0；`backend/target/test-cleanup-before.log` |
| `mvn -o -f backend/pom.xml '-Dtest=FlexQueryParserTest#parseEmptyTradeConfirms+parseXmlWithoutTradeConfirms+parseInvalidXmlShouldThrow' test` | 清理前解析合成组 3 项通过、退出 0；`backend/target/test-cleanup-before-parser.log`，4 个个人方法明确未执行 |
| `mvn -o -f backend/pom.xml test` | 清理后 496 项，无失败／错误／跳过，BUILD SUCCESS；`backend/target/test-cleanup-final.log`，2026-11-17 20:23:56 +08:00 完成 |
| `npm --prefix frontend test` | 清理前后均 6 文件／54 项、退出 0；`frontend/test-cleanup-before.log`、`frontend/test-cleanup-after.log` |

`backend/target/test-cleanup-before-manifest.json` 为 504 个实际唯一案例，不含个人四方法；`backend/target/test-cleanup-final-manifest.json` 为 496 个案例，31 个源类／306 个注解方法含全限定名、嵌套和参数化均执行。按当次日志的 Running 套件选取 Surefire XML，排除残留旧报告；`backend/target/test-cleanup-case-comparison.json` 仅移除表中 10 项、增加两个合成入口，无其他遗漏或新增跳过。净减少 8 项不是覆盖充分或提速证据。

当次全量仍有 Surefire 退出握手等待 30 秒提示，随后成功；没有改框架、超时、并行或跳过，没有性能优化结论。数据库、浏览器和构建未重跑，因为对应生产／迁移／页面／依赖未改。独立复核校正了 BookTrade 断言范围及全限定名注解扫描，确认最终清单与有效断言相符，文档链接与空白检查通过。

### 期权关联验收明细

日期：2026-11-18，基线 `269abfd`，方案 [0040](../design/0040-2026-11-18-ibkr-exact-option-event-linkage.md)／[0042](../design/0042-2026-11-18-trust-existing-option-event-records.md)。0041 的历史状态／来源门槛已撤回；完整问题、旧算法复现、中间决策、538 项与最终 0042 的命令／清单已保留在上方关联条目，以下补充场景明细。

当次生产范围只包含 TradeRecordRepository 候选／占用查询与 IbkrImportWorker，沿用 ImportService、Adapter、Executor、失败／清理与写锁链，核对 BookTrade 映射、当前乘数、异常传播及测试，参考 V3／V10／V20／V22／V28。没有新增接口、前端、依赖或迁移，没有读取实际账本、个人 XML、备份或样本。关联对象为同次事件的期权簿记，历史完整性、异常历史适配和非标准交割不在范围内；报表语义参见 [IBKR FAQ](https://www.interactivebrokers.com/support/faq/content/happens-am-assigned-stock-option-32642502)。

| 验收场景 | 原运行证据 |
| --- | --- |
| 同券商唯一精确匹配 | SQL限定券商、币种、事件、标的和日期；同时核对方向、合约代码、精确行权价及100倍数量。四种 CALL／PUT 行权／指派方向通过，交换候选顺序结果不变。 |
| 零／多匹配与占用 | 先统计完整匹配，再检查唯一候选占用；零或多个失败，不取首条。有效股票／ETF引用阻止再次使用；传播错误、实际整批清理和重试通过。 |
| 信任已有正式记录 | 四种方向单测不访问暂存仓储；临时PG先导入准确期权事件再移除旧暂存，新股票仍成功关联，没有历史状态／来源门槛或人工专用路径。 |
| 当前新输入范围 | 8组参数化覆盖交割乘数100／100.00、缺失／空白／非法／非100，以及到期缺乘数仍导入；原普通OPT／STK事件保留。 |
| 正式重复 | 实际导入准确期权事件后，以缺乘数但正式投影相同的输入命中重复兜底，SKIPPED且不新增；既有暂存内容冲突仍拒绝。 |
| 数值与并发重读 | long拒绝42,949,674张乘100在int中回绕104；行权价使用BigDecimal无容差。持锁重读股票后重验，正常删除／编辑变化拒绝，已有正确关联不改。 |
| 实际查询与事务 | PG排除其他券商／币种，允许跨批次；触发器使关联UPDATE失败，真实事务回滚、整批清理并FAILED，解除故障后重试成功，旧关联保留。 |
| 其他同步与事件保护 | 重复内容、部分失败、清理失败／中断、活动批次及既有真实并发验收保留，只撤回0041新增的历史处理。 |

0042 撤回历史来源缺失／空白／非法／非100／批次不符五个参数案例和人工例外一个案例，新增当前输入／正式重复九个案例。未知删除状态改为正常删除以保留重读保护；撤回旧 FAILED 期权残留和篡改旧引用的兼容夹具，保留正常占用。这是用户业务范围变化，不作为提速手段。100乘数只校验当前新导入交割，在正式重复返回之后、保存前；不放宽暂存层内容冲突规则。

环境为同一 `ubuntu-dev` 工作树、Java 25.0.5、Maven 3.10.0、PostgreSQL 18.6 及已有 Python。TMPDIR／JVM临时目录为 `/tmp`，缓存沿用，日志／构建在工作树，数据库和 e2e 编译在独占临时目录。

| 原命令与日志 | 时间及结果补充 |
| --- | --- |
| `mvn -o -f backend/pom.xml test`；`backend/target/ibkr-trusted-history-final.log` | 539项全过、退出0，1分34秒，02:41:57 +08:00结束 |
| `mvn -o -f backend/pom.xml -Dtest=IbkrImportWorkerTest,IbkrImportServiceTest test`；`backend/target/ibkr-trusted-history-focused-final.log` | 之后只补PUT／到期两个参数边界，生产代码未变；最终70项全过、退出0，12.642秒，02:43:45 +08:00结束 |
| `python3 -B e2e/verify-ordered-market-events.py`；`backend/target/ibkr-trusted-history-db.log` | 退出0，关联及既有场景PASS，停止并删除集群；初轮与单测并行，helper名称整理后最终重跑于02:57:32 +08:00通过 |

最终组合清单 `backend/target/ibkr-trusted-history-manifest.json` 按实际 Running 套件选择报告：471 个未改套件案例与70个最终专项去重为541，31类／317注解方法全部执行，无失败／错误／跳过。相对0041的538仅撤回6项、新增9项，其余无遗漏；541不是另一轮独立全量命令。独立复核逐项确认清单、实现和归档通过。

运行器校验data_directory，使用合成XML、内存报表提供器与真实Spring/JPA事务和仓储，不加载应用profile，不访问IBKR或现有vortex／vortex_dev。没有实际账本对账，也没有重跑未改前端。全量原有Surefire退出握手等待提示保留，未修改超时或忽略失败。
