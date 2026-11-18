# 券商同步故障排查与恢复

## 先确认批次状态

| 状态或反馈 | 排查方向 |
| --- | --- |
| `PENDING` 或 `PROCESSING` | 刷新批次列表，确认后台任务是否仍在运行。应用重启会把残留的这两类批次交给失败清理。 |
| `FAILED` | 查看批次错误和对应日志，修复原因后重新提交。正常进入该状态时，失败批次的暂存和导入记录已清理。 |
| `CLEANUP_FAILED` | 批次清理失败或重试被中断，状态会阻止新同步。保留批次和诊断信息，按下文处理。 |
| HTTP 409 | 检查是否存在 `PENDING`、`PROCESSING` 或 `CLEANUP_FAILED` 批次。该限制跨券商生效。 |

状态、阶段、重试和启动恢复见 [同步生命周期](../../knowledge/broker-sync/lifecycle.md)。

## IBKR 数量不受支持

IBKR 批次的 `first failure:` 后若显示 `Fractional IBKR quantities are not supported`，表示订单包含当前不支持的非整数数量。正式导入只接受绝对值在 1～2147483647 内的整数；`100.000` 属于整数，可以无损导入。缺失、非法、零或超范围数量也会给出对应拒绝原因。见 [数量限制](../../knowledge/broker-sync/brokers/ibkr.md#数量限制与失败说明)。

这种失败沿用整批清理，成功进入 FAILED 表示本批暂存和正式数据已清理；此前其他批次仍保留。批次摘要保留首条失败说明，同一数据直接重试仍会失败。遇到真实小数股时，应保留原始下载文件并另行设计支持方案，不应通过截断、四舍五入或修改券商原始数量来绕过校验。本次不自动修正历史账本。

## IBKR 报表结构不受支持

批次原因包含 `[UNRECOGNIZED]` 及 `Unsupported IBKR TCF report structure: …` 时，返回内容超出单账户、单份规定结构，或缺少必要层级。按提示核对查询类型为 TCF、账户、FlexStatements／FlexStatement／TradeConfirms 的唯一直接层级、可选 count 为 1，以及非空报表同时选择订单和成交明细。未知层级和混用账户会拒绝，系统不再只取第一份或把缺失结构当作成功零条。 [支持范围](../../knowledge/broker-sync/brokers/ibkr.md#支持的报表结构)

完整且确实无记录的 TradeConfirms 可成功零条。任一日期窗口结构错误时，整个批次在暂存前失败，仍执行本批清理；原成功批次保留，清理失败继续进入 CLEANUP_FAILED。应修正 Flex 查询配置后重新提交，不通过删除旧成功批次、删掉多余报表或伪造空节点绕过校验。本规则不增加订单终态查询或历史账本修复。

## IBKR 重复记录内容冲突

出现 `IBKR duplicate … content conflict; differing fields: …` 时，同一业务 ID 的来源内容或正式入库业务投影不一致。`ext_id=stored-order-row:<id>`、`stored-trade-confirm-row:<id>`、`stored-trade-record-row:<id>` 分别指已有订单暂存、成交暂存或正式交易的内部行 ID；提示仅列出差异字段名，不输出原值和新值。 [规则与范围](../../knowledge/broker-sync/brokers/ibkr.md#重复记录内容一致性)

正常进入 FAILED 表示本批新写入的数据已清理，此前成功批次及其账本保留。系统不会覆盖、合并或重算旧记录；相同冲突直接重试仍失败。保留原始下载文件，由用户核对查询范围、字段配置及来源内容，确认原因后另行决定如何处理，不能通过删除旧成功批次或修改券商原始值绕过检查。若进入 CLEANUP_FAILED，先按下文恢复清理。默认信任历史记录已执行完成，不因 `P` 或多笔成交单独拒绝，也不额外查询订单终态。

订单 ID、成交 ID 和成交所属订单 ID 缺失、为空或带首尾空白时会直接拒绝，不通过修剪和改写标识绕过检查。缺少来源暂存时，正式入库兜底只能比较正式表保存的业务投影，无法核对未保存的账户、费用组成或原始符号；原有价格／金额固定小数位或旧费用舍入也可能导致拒绝，需要人工核对，不自动修复。 [0033 标识补充](../../design/0033-2026-11-17-ibkr-strict-duplicate-identifiers.md)

## IBKR 期权事件关联失败

出现 `[UNRECOGNIZED]`、`exact_matches=0`／`exact_matches=2` 等匹配数量及 `manual investigation required` 时，本批股票侧行权／指派没有恰好一个能验证的期权侧簿记记录。`stock_record_id` 是本地内部行ID，不是券商订单号。它指向同一次事件的期权侧记录：行权为 SELL，指派为 BUY，不能统一称为 SELL 平仓；也不是更早的开仓交易。其他提示还包括候选已被使用或本批股票记录失去资格。 [匹配条件](../../knowledge/broker-sync/brokers/ibkr.md#导入映射)

应核对本次报表是否包含事件两侧，以及券商、币种、日期、标的、行权／指派、买卖方向、行权价与100倍数量关系是否一致。两个到期日不同的合约仍可能同时符合条件，这时也必须人工排查。已有正式记录按用户确认直接信任，不回查历史批次状态或补齐旧来源，不做历史数据兼容。本次新导入期权交割记录若出现 `Unsupported IBKR option-event multiplier; expected 100`，核对当前报表乘数；非标准交割、一对多／多对一暂不自动支持。

正常进入 FAILED 时，本批暂存和正式记录已整批清理，关联失败原因仍保留在批次；原成功批次不变。相同问题直接重试仍会失败，先保留下载报表并核对来源、查询范围和历史记录，明确原因与处理方案后再同步。不能选择列表第一条、伪造数量／行权价、删除旧成功批次或只改关联ID绕过校验。保存失败同样触发失败清理；若出现 CLEANUP_FAILED，先按下文恢复清理。

## 市场事件锁定历史

若新导入交易被提示“关联证券的历史已被市场事件锁定”，系统不会跳过或重算，应先核对同步范围及关联事件。需要补入历史时，按 [事件操作顺序](../market-events.md) 回退后续相关数据、撤销事件，再同步并顺序重建。完全一致的重复继续按原规则跳过。IBKR 关联回填也不可修改被锁定的旧股票侧交易。

PENDING／PROCESSING／CLEANUP_FAILED 期间事件处理、修改、撤销均被阻止，避免依赖半批结果。自动清理在任何删除前检查本批有效正式交易的历史约束；旧 FAILED 残留若已被事件使用，也会拒绝删除。此时保留诊断、先核对依赖，不能绕过锁定直接删行或删除成功批次。COMPLETED 批次不允许经清理服务删除。

旧 FAILED 残留若因历史锁导致失败清理，最终可能转为 CLEANUP_FAILED：清理等待事件解除历史锁，事件撤销又被活动批次阻止。这种异常存量不能通过普通页面的“先撤销、再清理”恢复。应停止全部写入、确认 Worker 已退出、备份并核对批次与事件依赖，单独授权和评审维护处置；本版本不提供自动解锁或通用修复命令，不能仅修改状态假装清理完成。正常流程先清理再置 FAILED，不产生这一组合；切换前须查出旧异常。

## 其他已知问题

Tiger 报错 `trade_time is not a valid epoch-millis long` 时，先核对 [已知时间格式缺陷](../../knowledge/broker-sync/brokers/tiger.md#时间格式限制)：暂存保存日期时间文本，映射要求毫秒数字。当前未修复，同一路径直接重试通常仍会失败，不应手工修改暂存时间绕过校验。

IBKR 客户端的部分错误码说明与 AUTH／NETWORK 分类尚未对齐官方语义，见 [TODO](../TODO.md)。遇到 1005、1006、1012 等错误时结合原错误码核对厂商定义，不能只依赖当前客户端文字判断 Token 或限流问题。

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
2. 在停止全部写入且使用当前应用写协调的事务内，重新核对目标批次为 `CLEANUP_FAILED`、券商相符，并检查本批有效正式交易未受市场事件历史锁约束。有依赖则停止普通清理，按上文异常存量方式单独评审维护，不直接删除或尝试页面撤销。无依赖时优先使用当前清理服务，通过检查后才按当前策略先清理该券商本批暂存行，再清理 `trade_records.sync_batch_id` 指向该批次的行。
3. 确认没有该批次的残留行后，将该批次状态置为 `FAILED`、阶段置空，保留原始错误并记录人工处理时间。核对批次和残留数量再提交事务。任一步失败时回滚。
4. 刷新批次列表，确认该批次已退出唯一索引的占用范围。原同步问题解决后，才重新提交新的同步批次。

清理顺序见 [清理服务](../../backend/src/main/java/com/vortex/sync/core/SyncBatchCleanupService.java)，并发保护条件见 [V28 迁移](../../backend/src/main/resources/db/migration/V28__simplify_sync_batch_state_model.sql)。
