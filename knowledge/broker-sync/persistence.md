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
- 新订单暂存为 `PENDING`。暂存阶段遇到已存在的业务键时跳过，原记录仍属于原批次。导入阶段只读取当前批次的 `PENDING` 订单，按券商和原始订单 ID 检查正式表重复项。结果写入 `IMPORTED`、`SKIPPED` 或 `FAILED`。`imported_trade_id` 记录成功导入的交易 ID，当前 SQL 未为该字段定义外键。
- `trade_records` 的部分唯一索引只检查 `external_id IS NOT NULL`，键为 `(external_broker, external_id)`。软删除的交易仍受该索引约束。手动录入、事件生成等交易可无券商来源字段。`trade_trigger` 与 `trigger_ref_type` 表示交易为什么发生，导入来源单独由上述字段记录。
- 适配器按当前批次的暂存订单统计导入结果。暂存时已跳过的重复订单不进入本批计数。`FAILED` 或残留非终态订单导致整批清理。清理成功后保留批次记录，删除该批暂存和正式交易。成功批次的暂存记录继续可供查询。

V28 移除了批次表的 `failed_count`，用部分唯一索引保护活跃批次。当前模式没有 `active_flag`，批次表也没有 `account_id`。现行状态及恢复见 [生命周期](lifecycle.md#状态与恢复)。

历史背景见 [数据持久化方案](../../design/0008-2026-04-13-broker-sync-data-persistence.md)、[Broker Code 注册方案](../../design/0011-2026-04-17-broker-registration.md)、[IBKR 暂存设计](../../design/0013-2026-04-21-ibkr-staging-schema.md)和 [Tiger 暂存设计](../../design/0016-2026-04-21-tiger-staging-schema.md)。
