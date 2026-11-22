# 券商同步数据持久化

| 表 | 当前职责 | 数据库依据 |
| --- | --- | --- |
| `brokers` | 保存券商基础信息及独立简介，不设启停状态。`broker_code` 是可空的技术标识，非空值唯一 | [V23](../../backend/src/main/resources/db/migration/V23__add_broker_code_and_rename_batch_broker_name.sql) |
| `broker_accounts` | 本地主键 `id`，关联券商 `broker_id`；保存真实 `account_id`、账户名称、邮箱、电话、账户备注和状态，同券商真实 ID 唯一 | [V33](../../backend/src/main/resources/db/migration/V33__separate_brokers_and_accounts.sql) |
| `broker_sync_connections` | 以 `broker_account_id` 主键／外键关联账户，保存券商代码、明文 JSONB 凭证及更新时间；账户删除级联清理，不参与批次清理 | V32 建表，V33 改为账户关联 |
| `broker_sync_batches` | 保存券商、本地账户主键、真实账户 ID 快照、日期范围、状态、阶段、计数及错误信息 | [V19](../../backend/src/main/resources/db/migration/V19__create_broker_sync_batches.sql)、[V24](../../backend/src/main/resources/db/migration/V24__add_phase_and_expand_batch_status.sql)、[V28](../../backend/src/main/resources/db/migration/V28__simplify_sync_batch_state_model.sql) |
| `ibkr_staged_orders` | IBKR 订单级暂存，按 `(account_id, order_id)` 去重 | [V20](../../backend/src/main/resources/db/migration/V20__create_ibkr_staged_orders.sql) |
| `ibkr_staged_trade_confirms` | IBKR 成交明细，按 `(account_id, trade_id)` 去重。BookTrade 导入按同来源账户的 `order_id` 查询其 `code` | [V21](../../backend/src/main/resources/db/migration/V21__create_ibkr_staged_trade_confirms.sql) |
| `tiger_staged_orders` | Tiger 订单级暂存，按 `(account, tiger_id)` 去重 | [V26](../../backend/src/main/resources/db/migration/V26__create_tiger_staged_orders.sql) |
| `trade_records` | 保存导入后的正式交易。用 `broker_account_id`、`external_broker`、`external_id` 识别账户订单，用 `sync_batch_id` 关联批次 | [V22](../../backend/src/main/resources/db/migration/V22__add_external_fields_to_trade_records.sql) |

## V33 账户迁移

每个旧券商建立真实 ID 为空的“待补全账户”。所有历史交易和流水（含已删除和事件生成记录）保留原券商，新增本地账户关联；金额、数量、日期、触发引用及原更新时间保持原值。组合外键约束交易／流水的账户与券商一致。V32 连接迁给对应待补全账户，不删除 Tiger 旧 JSON 中的资金账号；运行时资金账号只从托管账户读取。

历史批次仅在已有正式交易能证明唯一原券商时补本地账户，不能根据当前券商代码猜测归属；空批次和归属不唯一者保留未知，真实 ID 快照不猜测。历史暂存中的空账户保留，`NOT VALID` 检查约束要求新写入／更新的暂存行具有非空来源账户。新唯一索引均包含账户维度。升级顺序、备份和失败恢复见 [账户指南](../../docs/accounts.md#迁移与恢复)，方案见 [0047](../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。

V34 将旧 `brokers.email`、`phone`、`description` 迁至账户后删除旧列。资料优先归入唯一待补全账户，其次是唯一账户；否则新建待补全账户保留原资料，不猜测真实归属。已有账户审计字段、账本和连接不修改，联系资料不参与同步凭证或就绪判定。见 [0047](../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。

V35 新增独立券商简介 `brokers.introduction` 并删除 `brokers.is_active`。从 V34 升级不改变任何已有账户状态；同步就绪与触发仅由账户启停、真实 ID 和凭证控制。账户备注不反向填入券商简介；V33 当时初始化账户状态的历史规则保持原样。见 [0047](../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。

## 暂存与导入

券商管理负责券商名称及代码，托管账户管理负责该券商下的账户名称、真实 ID 及状态；连接服务按本地账户主键单独保存凭证。IBKR与Tiger均直接从数据库读取当前账户的明文凭证，旧profile不回退。API仅返回非秘密字段及秘密字段是否配置；Token／私钥留空保留、填写替换，清空删除该账户连接。券商代码改变后不匹配的旧连接不作为当前配置使用；账户所属券商创建后固定。活动批次期间可以修改配置及账户，批次失败清理保留用户配置。

V32 将 V31 明文 IBKR 配置按匹配的 `brokers` 券商行保存并移除旧 `ibkr_sync_settings`；有 Token 却没有对应 IBKR 券商行时整次迁移回滚，修正关联后重试。V33 再把连接迁至该券商的待补全账户。用户确认没有历史加密凭证，备份和恢复使用当前数据库，见 [部署说明](../../docs/broker-sync/brokers/ibkr/README.md#凭证与部署)及 [0047](../../design/0047-2026-11-21-broker-accounts-and-sync-connections.md)。

- 三张暂存表以 `batch_id` 引用批次表，保留解析后的结构化字段。当前表结构无 `raw_payload` 列。IBKR 成交确认以 `order_id` 与订单关联，无订单表外键，也无导入状态字段。
- 新订单暂存为 `PENDING`。IBKR 暂存遇到已存在的订单或成交业务键时比较来源内容，只有一致才跳过，差异导致本批失败；原记录仍属于原批次。Tiger 暂存维持原有按键跳过规则。导入阶段只读取当前批次的 `PENDING` 订单，按本地账户、券商和原始订单 ID 检查正式表重复项；IBKR 在命中时还比较正式业务投影，一致才 SKIPPED，不同则 FAILED。结果写入 `IMPORTED`、`SKIPPED` 或 `FAILED`。`imported_trade_id` 记录成功导入的交易 ID，当前 SQL 未为该字段定义外键。 [0032 比较范围与边界](../../design/0032-2026-11-17-ibkr-duplicate-content-consistency.md)
- `trade_records` 的部分唯一索引只检查 `external_id IS NOT NULL`，键为 `(broker_account_id, external_broker, external_id)`。软删除的交易仍受该索引约束。手动录入、事件生成等交易可无券商来源字段。`trade_trigger` 与 `trigger_ref_type` 表示交易为什么发生，导入来源单独由上述字段记录。
- 适配器按当前批次的暂存订单统计导入结果。暂存时已跳过的重复订单不进入本批计数。`FAILED` 或残留非终态订单导致整批清理。清理成功后保留批次记录，删除该批暂存和正式交易。成功批次的暂存记录继续可供查询。

新正式交易写入前检查关联证券的事件历史锁，IBKR 关联回填及批次清理同样保护历史基数。历史约束直接从事件表推导，不新增锁日期列，也不在启动时改写既有交易；共享费用列的 V29 结构变化见下文。 [事件规则](../market-events.md) · [0038 清理保护](../../design/0038-2026-11-17-sync-cleanup-history-guard.md)

IBKR 期权关联只写本批股票侧 `trigger_ref_id`，不修改被引用的期权记录。用户确认历史准确，候选只按正式业务字段查询，不依赖旧暂存来源或历史批次状态，不为人工记录设置例外。项目乘数为100，仅新导入的期权交割记录在保存前验证当前暂存乘数；正式重复兜底不追加此门槛，原有重复来源内容校验保留。有效股票／ETF事件对同一期权行的重复引用被拒绝，整个匹配与写入在同一账本短写事务锁内完成。V33 将候选查询限定同一本地账户；仍不扫描、修复或重算历史关联。 [0042](../../design/0042-2026-11-18-trust-existing-option-event-records.md)

V28 移除了批次表的 `failed_count`，用部分唯一索引保护活跃批次。当前模式没有 `active_flag`；V33 为批次增加 `broker_account_id` 和真实 `account_id` 快照，仍保持全局单活动批次。现行状态及恢复见 [生命周期](lifecycle.md#状态与恢复)。

V29 将共享正式费用列 `trade_records.fee` 从两位小数扩展为不固定 precision/scale 的 `NUMERIC`，保留非空、非负和默认零约束。IBKR、Tiger 和手工交易均可保存来源费用的小数精度，后端汇总不舍入；成交价、成交金额及券商费用组成规则不变。迁移不更改旧费用的数值，不包含历史回填。空库初始化、带两位合成历史数据升级、真实 ORM 费用保存读取及仓储汇总、迁移失败回滚重试已在独立临时 PostgreSQL 集群验证；没有连接现有账本。 [V29](../../backend/src/main/resources/db/migration/V29__preserve_trade_fee_precision.sql) · [0029](../../design/0029-2026-11-17-preserve-trade-fee-precision.md) · [验证入口](../../e2e/README.md#fee-precision-migration-check)

历史背景见 [数据持久化方案](../../design/0008-2026-04-13-broker-sync-data-persistence.md)、[Broker Code 注册方案](../../design/0011-2026-04-17-broker-registration.md)、[IBKR 暂存设计](../../design/0013-2026-04-21-ibkr-staging-schema.md)和 [Tiger 暂存设计](../../design/0016-2026-04-21-tiger-staging-schema.md)。
