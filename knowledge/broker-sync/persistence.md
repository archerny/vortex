# 券商同步数据持久化

| 表 | 当前职责 | 数据库依据 |
| --- | --- | --- |
| `brokers` | 保存券商基础信息。`broker_code` 是可空的技术标识，非空值唯一 | [V23](../../backend/src/main/resources/db/migration/V23__add_broker_code_and_rename_batch_broker_name.sql) |
| `broker_sync_batches` | 保存券商、查询日期范围、状态、阶段、计数及错误信息 | [V19](../../backend/src/main/resources/db/migration/V19__create_broker_sync_batches.sql)、[V24](../../backend/src/main/resources/db/migration/V24__add_phase_and_expand_batch_status.sql)、[V28](../../backend/src/main/resources/db/migration/V28__simplify_sync_batch_state_model.sql) |
| `ibkr_staged_orders` | IBKR 订单级暂存，按 `order_id` 去重 | [V20](../../backend/src/main/resources/db/migration/V20__create_ibkr_staged_orders.sql) |
| `ibkr_staged_trade_confirms` | IBKR 成交明细，按 `trade_id` 去重。BookTrade 导入按 `order_id` 查询其 `code` | [V21](../../backend/src/main/resources/db/migration/V21__create_ibkr_staged_trade_confirms.sql) |
| `tiger_staged_orders` | Tiger 订单级暂存，按 `tiger_id` 去重 | [V26](../../backend/src/main/resources/db/migration/V26__create_tiger_staged_orders.sql) |
| `trade_records` | 保存导入后的正式交易。用 `external_broker`、`external_id` 识别券商订单，用 `sync_batch_id` 关联批次 | [V22](../../backend/src/main/resources/db/migration/V22__add_external_fields_to_trade_records.sql) |

## 暂存与导入

- 三张暂存表以 `batch_id` 引用批次表，保留解析后的结构化字段。当前表结构无 `raw_payload` 列。IBKR 成交确认以 `order_id` 与订单关联，无订单表外键，也无导入状态字段。
- 新订单暂存为 `PENDING`。IBKR 暂存遇到已存在的订单或成交业务键时比较来源内容，只有一致才跳过，差异导致本批失败；原记录仍属于原批次。Tiger 暂存维持原有按键跳过规则。导入阶段只读取当前批次的 `PENDING` 订单，按券商和原始订单 ID 检查正式表重复项；IBKR 在命中时还比较正式业务投影，一致才 SKIPPED，不同则 FAILED。结果写入 `IMPORTED`、`SKIPPED` 或 `FAILED`。`imported_trade_id` 记录成功导入的交易 ID，当前 SQL 未为该字段定义外键。 [0032 比较范围与边界](../../design/0032-2026-11-17-ibkr-duplicate-content-consistency.md)
- `trade_records` 的部分唯一索引只检查 `external_id IS NOT NULL`，键为 `(external_broker, external_id)`。软删除的交易仍受该索引约束。手动录入、事件生成等交易可无券商来源字段。`trade_trigger` 与 `trigger_ref_type` 表示交易为什么发生，导入来源单独由上述字段记录。
- 适配器按当前批次的暂存订单统计导入结果。暂存时已跳过的重复订单不进入本批计数。`FAILED` 或残留非终态订单导致整批清理。清理成功后保留批次记录，删除该批暂存和正式交易。成功批次的暂存记录继续可供查询。

新正式交易写入前检查关联证券的事件历史锁，IBKR 关联回填及批次清理同样保护历史基数。未变更任何表结构或既有数据；历史约束从事件表推导，不新增锁日期列。 [事件规则](../market-events.md) · [0038 清理保护](../../design/0038-2026-11-17-sync-cleanup-history-guard.md)

IBKR 期权关联只写本批股票侧 `trigger_ref_id`，不修改被引用的期权记录。用户确认历史准确，候选只按正式业务字段查询，不依赖旧暂存来源或历史批次状态，不为人工记录设置例外。项目乘数为100，仅新导入的期权交割记录在保存前验证当前暂存乘数；正式重复兜底不追加此门槛，原有重复来源内容校验保留。有效股票／ETF事件对同一期权行的重复引用被拒绝，整个匹配与写入在同一账本短写事务锁内完成。没有新增外键或迁移，也不扫描、兼容或修复历史数据。 [0042](../../design/0042-2026-11-18-trust-existing-option-event-records.md)

V28 移除了批次表的 `failed_count`，用部分唯一索引保护活跃批次。当前模式没有 `active_flag`，批次表也没有 `account_id`。现行状态及恢复见 [生命周期](lifecycle.md#状态与恢复)。

V29 将共享正式费用列 `trade_records.fee` 从两位小数扩展为不固定 precision/scale 的 `NUMERIC`，保留非空、非负和默认零约束。IBKR、Tiger 和手工交易均可保存来源费用的小数精度，后端汇总不舍入；成交价、成交金额及券商费用组成规则不变。迁移不更改旧费用的数值，不包含历史回填。空库初始化、带两位合成历史数据升级、真实 ORM 费用保存读取及仓储汇总、迁移失败回滚重试已在独立临时 PostgreSQL 集群验证；没有连接现有账本。 [V29](../../backend/src/main/resources/db/migration/V29__preserve_trade_fee_precision.sql) · [0029](../../design/0029-2026-11-17-preserve-trade-fee-precision.md) · [验证入口](../../e2e/README.md#fee-precision-migration-check)

历史背景见 [数据持久化方案](../../design/0008-2026-04-13-broker-sync-data-persistence.md)、[Broker Code 注册方案](../../design/0011-2026-04-17-broker-registration.md)、[IBKR 暂存设计](../../design/0013-2026-04-21-ibkr-staging-schema.md)和 [Tiger 暂存设计](../../design/0016-2026-04-21-tiger-staging-schema.md)。
