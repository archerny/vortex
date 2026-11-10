# 券商同步故障排查与恢复

## 先确认批次状态

| 状态或反馈 | 排查方向 |
| --- | --- |
| `PENDING` 或 `PROCESSING` | 刷新批次列表，确认后台任务是否仍在运行。应用重启会把残留的这两类批次交给失败清理。 |
| `FAILED` | 查看批次错误和对应日志，修复原因后重新提交。正常进入该状态时，失败批次的暂存和导入记录已清理。 |
| `CLEANUP_FAILED` | 批次清理失败或重试被中断，状态会阻止新同步。保留批次和诊断信息，按下文处理。 |
| HTTP 409 | 检查是否存在 `PENDING`、`PROCESSING` 或 `CLEANUP_FAILED` 批次。该限制跨券商生效。 |

状态、阶段、重试和启动恢复见 [同步生命周期](../../knowledge/broker-sync/lifecycle.md)。

## 只读检查

先核对目标库。研发验证使用获授权的隔离库和合成数据。下面的 `batch_id` 是 psql 变量，查询前设置为已核对的批次 ID。先看阶段和错误，再按已确认的券商选择其暂存表。

```sql
SELECT id, broker_code, status, phase, error_message, started_at, completed_at
FROM broker_sync_batches
WHERE id = :'batch_id'::bigint;

SELECT count(*) FROM trade_records WHERE sync_batch_id = :'batch_id'::bigint;
```

IBKR 查 `ibkr_staged_orders` 和 `ibkr_staged_trade_confirms`，Tiger 查 `tiger_staged_orders`。每张表按 `batch_id` 过滤。批次错误可能在分类前包裹券商和耗时信息，排查时检查错误全文。同步日志可能包含账户、请求令牌或原始交易字段，分享前先核对并脱敏。 [暂存与导入契约](../../knowledge/broker-sync/persistence.md) · [错误与日志契约](../../knowledge/broker-sync/adapter-contracts.md)

## 人工恢复 CLEANUP_FAILED

1. 确认同步工作线程已退出，查明自动清理失败原因。记录目标库、批次 ID、券商代码和影响范围，由获授权的数据库操作人员处理。
2. 以事务锁定目标批次，重新核对其状态为 `CLEANUP_FAILED`。按当前清理策略先清理该券商的暂存表中属于该批次的行，再清理 `trade_records.sync_batch_id` 指向该批次的行。
3. 确认没有该批次的残留行后，将该批次状态置为 `FAILED`、阶段置空，保留原始错误并记录人工处理时间。核对批次和残留数量再提交事务。任一步失败时回滚。
4. 刷新批次列表，确认该批次已退出唯一索引的占用范围。原同步问题解决后，才重新提交新的同步批次。

清理顺序见 [清理服务](../../backend/src/main/java/com/vortex/sync/core/SyncBatchCleanupService.java)，并发保护条件见 [V28 迁移](../../backend/src/main/resources/db/migration/V28__simplify_sync_batch_state_model.sql)。
