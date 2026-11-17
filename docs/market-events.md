# 市场事件操作

当前规则见 [市场事件知识](../knowledge/market-events.md)，决策见 [0035](../design/0035-2026-11-17-ordered-market-events-and-history-locks.md)、[0036](../design/0036-2026-11-17-market-event-write-coordination.md)、[0037](../design/0037-2026-11-17-ordered-event-state-validation.md) 和 [0038](../design/0038-2026-11-17-sync-cleanup-history-guard.md)。

## 录入与修正

1. 先补齐事件关联证券在事件日前的交易。拆股关联原证券；代码变更关联新旧代码；实物分红关联原证券和分红证券，均覆盖所有券商。
2. 在市场事件页新增。系统按前一日持仓一次生成本事件的 BUY／SELL；无持仓也会处理成功并施加历史限制。关联证券已有事件当天或以后的普通交易，或存在同日／更晚的已处理关联事件时，先回退这些数据。
3. 处理成功后再录入事件当天及以后的普通交易。更早的关联交易不再允许新增、编辑、移动或删除；无关证券仍可补录。
4. 需要修正历史时，从后向前手动回退后续相关交易与事件，在列表点击“撤销事件”。满足条件才清理该事件的系统交易并解除其约束；其他事件的约束仍保留。修正后按日期重新创建事件，不自动级联重算。

已处理事件不可直接修改，MARKET_EVENT 交易不可在普通交易页编辑、删除或改写来源。第一版不支持同日有关联的多个事件，无关证券的同日事件可以分别处理。系统不能凭现有记录证明更早券商历史完整，录入前仍须核对。

## 冲突反馈

交易与事件写入的业务冲突返回 HTTP 409，响应保留 `status=ERROR`、`message`，补充 `code`、`blockingEventId`、`blockingEventType`、`eventDate`、`symbols`。阻挡事件不适用时对应字段为空。页面显示原因与可用的事件、日期、证券信息；撤销失败保留弹窗，成功后刷新列表。

| code | 下一步 |
| --- | --- |
| HISTORY_LOCKED | 先回退相关后续数据并撤销事件，再修正早期交易 |
| EVENT_ORDER_CONFLICT | 按逆序撤销同日或更晚的关联事件；同日关联事件暂不支持 |
| FOLLOWING_TRADES_EXIST | 先手动回退关联证券在事件当天或之后的普通交易 |
| PROCESSED_EVENT_IMMUTABLE | 满足撤销条件后撤销，再重新创建 |
| SYSTEM_TRADE_READ_ONLY | 通过所属事件维护系统交易 |
| SYNC_IN_PROGRESS | 等待同步结束；CLEANUP_FAILED 按恢复指南处理 |
| LEGACY_EVENT_STATE | 停止处理，核对旧状态与派生记录，不自动修复 |

券商同步遇到新锁定历史时整批失败，沿用 UNRECOGNIZED 原因和批次清理；合法重复仍按既有规则校验并跳过。 [恢复指南](broker-sync/recovery.md)

## 存量切换

部署不新增表、不重算或重写旧事件。先停止写入及同步，在明确的隔离副本上运行 [只读诊断](../e2e/check-market-event-history.sql)。连接目标必须先人工核对；本次只在临时合成库验证该 SQL，未检查实际账本。

```sh
psql -X -v ON_ERROR_STOP=1 -f e2e/check-market-event-history.sql
```

SQL 报告管理状态缺失、未处理／已撤销事件仍有派生行、缺少处理时间、孤立或范围／日期不符的派生交易、来源类型不符、同日关联重叠、活动批次和旧 FAILED 正式残留。零输出本身合法。处理标记、查询无异常或当前锁定都不能证明旧成本与数量正确；还须核对旧结果和原始来源，异常的修复另行明确范围。未处理但有派生行、管理状态为空的事件不能经修改、处理或撤销入口自动修复。

旧失败批次与事件依赖同时存在，且清理失败转为 CLEANUP_FAILED 时，普通页面无法先撤销事件再清理；需停写、确认工作线程退出并单独评审获授权的维护处置，详见 [恢复边界](broker-sync/recovery.md#市场事件锁定历史)。本次未自动解除这类存量异常。

所有应用写入口必须使用同一版本。默认 PostgreSQL READ COMMITTED 下短写事务互斥生效；其他隔离级别明确拒绝。外部 SQL 或旧应用若直接修改账本，不受 Service 约束，维护时需停写并先核对关联影响。当前用户实际历史的核对和修复不属于自动验收。

## 验证

纯内存单测模拟所有 Repository；真实事务验证使用独立临时 PostgreSQL、合成交易及真实 Spring/JPA 入口。实际浏览器使用合成 API 点验三类事件的冲突保留、撤销成功和列表刷新。命令、覆盖与环境边界见 [独立验证指南](../e2e/README.md#ordered-market-event-check)，运行结果及原 TODO 历史见 [待办归档](TODO-ARCHIVE.md)。
