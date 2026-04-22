# P0 Fix — Close the Data-Loss Chain in Import Workers

**Status**: ✅ Implemented (2026-04-23) — all three P0 defects fixed, full test suite (242 tests) passing
**Last-updated**: 2026-04-23
**Scope**: Backend only (IBKR + Tiger import workers, BookTrade resolution)
**Related**: `docs/broker-sync/framework/import-consistency.md` (v2.4.3) | `docs/broker-sync/brokers/ibkr/booktrade-mapping.md`

---

## 1. Problem Statement

A full audit of the broker-sync module identified **three tightly coupled P0 defects** that together form a silent data-loss chain. Each defect individually is serious; their combination means a failed row can disappear without any user-visible signal — the batch will finalize as `COMPLETED` while the row never reaches `trade_records` and never shows up as `FAILED` in the staged table.

### 1.1 P0-1 — Import workers save staged rows inside a rolled-back transaction

Both `IbkrImportWorker.importSingleOrder` and `TigerImportWorker.importOne` declare `@Transactional(propagation = REQUIRES_NEW)` and wrap the whole body in `try/catch`. On exception, the catch block mutates `staged.status = "FAILED"` and calls `stagedOrderRepository.save(staged)` **inside the same REQUIRES_NEW transaction that has already been marked rollback-only by the exception**.

Behavior of that save:

- If the exception was thrown from a JPA operation that already registered the rollback-only marker, the subsequent `save(staged)` will either:
  - throw `UnexpectedRollbackException` on commit (the `FAILED` write is lost AND the original exception context is mangled), or
  - succeed at the Hibernate level but get discarded when the transaction is rolled back (the `FAILED` write is silently lost).
- Either way, the staged row remains in its **previous** status (typically `PENDING`), with no error trail.

**Files affected**:

```73:102:backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void importSingleOrder(Long batchId, Long brokerId, IbkrStagedOrder staged) {
    try {
        // ... dedup check, map, save tradeRecord, save staged IMPORTED ...
    } catch (Exception e) {
        staged.setStatus("FAILED");
        staged.setErrorMessage("Import error: " + e.getMessage());
        stagedOrderRepository.save(staged);   // ← in rolled-back tx
        logger.warn(...);
    }
}
```

```56:104:backend/src/main/java/com/vortex/sync/adapter/tiger/TigerImportWorker.java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void importOne(Long batchId, Long brokerId, TigerStagedOrder staged) {
    try {
        // ... preFilter, map, save tradeRecord, save staged IMPORTED ...
    } catch (Exception e) {
        staged.setStatus("FAILED");
        staged.setErrorMessage("Import error: " + e.getMessage());
        stagedOrderRepository.save(staged);   // ← same bug
        logger.warn(...);
    }
}
```

### 1.2 P0-2 — Adapter only counts three terminal states, ignores residual PENDING

Both adapters count `IMPORTED + SKIPPED + FAILED` from the staged table after `importAll` returns, then branch on `failedCount > 0`. They **never check for residual `PENDING`** rows. In combination with P0-1, this means a row that should have been `FAILED` but remains `PENDING` (because the FAILED write was lost) contributes zero to every counter — the adapter sees `failedCount == 0`, returns `SyncResult.success(...)`, and the executor finalizes the batch as `COMPLETED`.

**Files affected**:

```124:146:backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapter.java
long importedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "IMPORTED");
long skippedCount  = stagedOrderRepository.countByBatchIdAndStatus(batchId, "SKIPPED");
long failedCount   = stagedOrderRepository.countByBatchIdAndStatus(batchId, "FAILED");
int  totalCount    = (int) (importedCount + skippedCount + failedCount);
// ... no PENDING check ...
if (failedCount > 0) { return SyncResult.failure(...); }
return SyncResult.success(...);
```

Tiger adapter is structurally identical (L126-148).

### 1.3 P0-3 — `resolveBookTradeRefType` silently downgrades to MANUAL semantics

When an IBKR order is detected as a BookTrade (both `orderTime` and `orderType` blank), `determineTriggerInfo` calls `resolveBookTradeRefType(staged)` to resolve the concrete option-event kind from the associated `IbkrStagedTradeConfirm.code`. That method has **three** silent-downgrade branches:

```216:259:backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java
private TriggerRefType resolveBookTradeRefType(IbkrStagedOrder staged) {
    List<IbkrStagedTradeConfirm> confirms = stagedTradeConfirmRepository.findByOrderId(staged.getOrderId());
    if (confirms.isEmpty()) {
        logger.warn(... "defaulting to MANUAL" ...);
        return TriggerRefType.NONE;             // branch A: no confirm
    }
    String code = confirms.get(0).getCode();
    if (isBlank(code)) {
        logger.warn(... "defaulting to MANUAL" ...);
        return TriggerRefType.NONE;             // branch B: blank code
    }
    // ... priority-based token matching ...
    logger.warn(... "did not match any known option event" ...);
    return TriggerRefType.NONE;                 // branch C: unknown token
}
```

The caller in `determineTriggerInfo` has already committed `TradeTrigger.OPTION`:

```203:210:backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java
if (!isBookTrade) {
    return new TriggerInfo(TradeTrigger.MANUAL, TriggerRefType.NONE);
}
TriggerRefType refType = resolveBookTradeRefType(staged);
return new TriggerInfo(TradeTrigger.OPTION, refType);   // ← trigger=OPTION, refType=NONE
```

Result in `trade_records`: `trade_trigger=OPTION, trigger_ref_type=NONE, trigger_ref_id=0`. This is an **invalid combination**: a row tagged as an option event with no ref-type to back it. Downstream (position reconciliation, back-fill pairing for STK/OPT) will treat this row as an unclassified anomaly or worse, pair it incorrectly. The log message `"defaulting to MANUAL"` is misleading — the trigger is **not** downgraded to MANUAL, only the ref-type is nulled.

### 1.4 How the three defects chain

```
Exception inside importSingleOrder (e.g. BookTrade code missing, or ANY other mapping/DB error)
      │
      ▼
P0-3 ──► resolveBookTradeRefType returns NONE instead of throwing
         (order gets imported as trigger=OPTION + refType=NONE — invalid)
         ── OR ──
         any unrelated exception in the try block
      │
      ▼
P0-1 ──► catch block runs, tries to save staged=FAILED in a rolled-back REQUIRES_NEW tx
         staged row either stays PENDING (save lost) or throws UnexpectedRollbackException
      │
      ▼
P0-2 ──► adapter counts only IMPORTED/SKIPPED/FAILED
         residual PENDING is invisible → failedCount=0 → SyncResult.success
      │
      ▼
Batch finalizes COMPLETED, but user lost: either
   • a corrupt trade_records row (OPTION + NONE), or
   • a staged row stuck in PENDING (blocks future re-sync via uk_only_one_active + global uk_order_id)
```

A single BookTrade missing its `TradeConfirm` — a realistic scenario given IBKR Flex Query construction quirks — can silently corrupt data **or** silently brick the broker's re-sync capability (the PENDING row with a global-unique `order_id` will be stuck forever because `SyncBatchCleanupService` won't have been triggered; the user will hit `DataIntegrityViolationException` on the next re-sync).

---

## 2. Design Goals

1. **No more silent data loss.** Any exception or unrecognized condition during import must produce a terminal state that's visible to the adapter and the user.
2. **No more corrupt `trade_records`.** A BookTrade missing its `TradeConfirm` (or with blank/unknown code) must not reach `trade_records`.
3. **Preserve v2 fail-fast semantics, per-order granularity.** Per user choice (option A in pre-design discussion): one bad row marks itself FAILED, other rows continue trying, adapter sees `failedCount > 0` at the end, `SyncBatchFailureHandler` runs cleanup — **no** immediate-interrupt requirement.
4. **Defense in depth.** Even if some future bug causes PENDING residue again, the adapter must catch it instead of finalizing COMPLETED.
5. **Minimal surface change.** Don't restructure the worker contract or the adapter contract. Fix the three defects locally.

---

## 3. Proposed Design

### 3.1 P0-1 — Split the failure path out of the REQUIRES_NEW transaction

**Approach**: Extract the "record as FAILED" write into a separate method on the worker bean, annotated with its own `@Transactional(propagation = REQUIRES_NEW)`. Because the call from the catch block goes through the Spring AOP proxy (worker bean methods are called from the service, not self-invoked), a new REQUIRES_NEW will open a fresh transaction that is **not** rollback-only. We leverage the existing "worker is a standalone `@Component`" structure that the code already relies on for proxy interception.

Pseudocode after the fix (IBKR — Tiger mirrors this):

```java
@Component
public class IbkrImportWorker {
    // ... existing fields ...

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void importSingleOrder(Long batchId, Long brokerId, IbkrStagedOrder staged) {
        try {
            // ... existing happy path: dedup, map, save tradeRecord, save staged IMPORTED ...
        } catch (Exception e) {
            // Do NOT save inside this rolled-back tx.
            // Let the exception propagate; the outer service will invoke markFailed() via proxy.
            logger.warn("[IbkrImport] Failed to import orderId={}: {}", staged.getOrderId(), e.getMessage(), e);
            throw new ImportOneFailedException(staged, e);   // carries staged ref + cause
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(IbkrStagedOrder staged, String errorMessage) {
        // Re-read fresh from DB (staged in the caught exception may be detached or stale)
        IbkrStagedOrder fresh = stagedOrderRepository.findById(staged.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "Staged row disappeared while marking FAILED: id=" + staged.getId()));
        fresh.setStatus("FAILED");
        fresh.setErrorMessage(errorMessage);
        stagedOrderRepository.save(fresh);
    }
}
```

**Caller side** (`IbkrImportService.importAll` — the method that iterates over staged rows and calls `worker.importSingleOrder`):

```java
for (IbkrStagedOrder staged : pending) {
    try {
        importWorker.importSingleOrder(batchId, brokerId, staged);
    } catch (ImportOneFailedException e) {
        // Proxy-mediated call → opens a fresh REQUIRES_NEW tx (NOT the rolled-back one).
        try {
            importWorker.markFailed(e.getStaged(), "Import error: " + e.getCause().getMessage());
        } catch (Exception markErr) {
            // If even markFailed fails, log loudly — the row will stay PENDING and
            // P0-2's PENDING check will still catch it at adapter level.
            logger.error("[IbkrImport] Failed to mark staged id={} as FAILED: {}",
                    e.getStaged().getId(), markErr.getMessage(), markErr);
        }
    } catch (Exception e) {
        // Any non-wrapped exception: same belt-and-suspenders handling
        logger.error("[IbkrImport] Unexpected error importing orderId={}: {}",
                staged.getOrderId(), e.getMessage(), e);
        try {
            importWorker.markFailed(staged, "Unexpected error: " + e.getMessage());
        } catch (Exception markErr) {
            logger.error(...);
        }
    }
}
```

**Why this works**:
- The proxy-mediated call `importWorker.markFailed(...)` starts a **new** physical transaction separate from the (rolled-back) `importSingleOrder` one. That new transaction can write the FAILED status without `UnexpectedRollbackException`.
- Re-reading `staged` inside `markFailed` avoids stale/detached-entity pitfalls.
- If `markFailed` itself throws (DB down, constraint violation on something unrelated), the row stays PENDING — but P0-2's PENDING check then catches it and prevents COMPLETED.
- `ImportOneFailedException` is a small private class that carries the `staged` reference + original cause; it's only used for the in-process worker↔service hand-off.

**Alternatives considered**:
- `TransactionTemplate` — more explicit but adds a second transaction-management idiom to the codebase; rejected.
- `TransactionSynchronizationManager.registerSynchronization(afterCompletion ...)` — works but obscure and error-prone; rejected.
- Just rethrow everything and let outer `@Transactional` wrap the failure write — breaks per-row isolation (one bad row would roll back the batch-level commit); rejected.

### 3.2 P0-2 — Adapter must check for residual PENDING/PROCESSING

**Approach**: After `importService.importAll(batchId)` returns, the adapter re-counts the staged table. Extend the count to also include any row that is **not** in a terminal state. Treat residual non-terminal rows as an import failure, even if `failedCount == 0`.

Terminal states for staged rows: `IMPORTED`, `SKIPPED`, `FAILED`.
Non-terminal (unexpected after `importAll`): any other status, including `PENDING` or `PROCESSING` if we add it later.

Implementation options:

**Option A (preferred)**: Add a repository method:
```java
long countByBatchIdAndStatusNotIn(Long batchId, Collection<String> statuses);
```
and use it:
```java
long residualCount = stagedOrderRepository.countByBatchIdAndStatusNotIn(
        batchId, List.of("IMPORTED", "SKIPPED", "FAILED"));
if (residualCount > 0 || failedCount > 0) {
    String reason = (residualCount > 0)
            ? String.format("%d record(s) failed import in batch %d (imported=%d, skipped=%d, failed=%d, residual_non_terminal=%d, duration=%dms)",
                    failedCount + residualCount, batchId, importedCount, skippedCount, failedCount, residualCount, durationMs)
            : /* existing message */;
    logger.error("[IbkrSync] {} — triggering fail-fast cleanup", reason);
    return SyncResult.failure(getBrokerCode(), reason, durationMs);
}
```

**Option B**: Count all rows, subtract the three terminal counters, check `total - imported - skipped - failed > 0`. Same semantic but relies on a second `count(*)` query instead of `NOT IN`. Pick A for clarity.

**Both adapters get the same change.** Tiger and IBKR use different staged-repo classes but the same pattern.

**Log when residual is detected**: also dump the offending staged IDs at WARN level (cap at 20 IDs to keep logs manageable). This is the breadcrumb a future debugger will look for.

### 3.3 P0-3 — BookTrade resolution becomes fail-fast

**Approach**: Replace the three silent `return TriggerRefType.NONE` branches in `resolveBookTradeRefType` with explicit `throw`s. The thrown exception propagates up through `determineTriggerInfo` → `mapToTradeRecord` → `importSingleOrder`'s try block, which (after P0-1's fix) gets routed to `markFailed`. The staged row ends as `FAILED` with a clear error message. The batch's `failedCount > 0`, `SyncBatchFailureHandler` runs, batch ends as `FAILED` with cleanup. User sees the failure and the reason.

```java
private TriggerRefType resolveBookTradeRefType(IbkrStagedOrder staged) {
    List<IbkrStagedTradeConfirm> confirms = stagedTradeConfirmRepository.findByOrderId(staged.getOrderId());
    if (confirms.isEmpty()) {
        throw new IllegalStateException(String.format(
                "BookTrade orderId=%s has no associated TradeConfirm — cannot determine option-event type",
                staged.getOrderId()));
    }
    String code = confirms.get(0).getCode();
    if (isBlank(code)) {
        throw new IllegalStateException(String.format(
                "BookTrade orderId=%s TradeConfirm has blank code — cannot determine option-event type",
                staged.getOrderId()));
    }

    // Priority-based matching (unchanged)
    String[] tokens = code.split(";");
    for (String token : tokens) {
        if (EXERCISE_CODES.contains(token.trim())) return TriggerRefType.OPTION_EXERCISE;
    }
    for (String token : tokens) {
        if ("Ep".equals(token.trim())) return TriggerRefType.OPTION_EXPIRE;
    }
    for (String token : tokens) {
        String t = token.trim();
        if ("A".equals(t) || "GEA".equals(t)) return TriggerRefType.OPTION_ASSIGNED;
    }

    throw new IllegalStateException(String.format(
            "BookTrade orderId=%s code='%s' did not match any known option event (EXERCISE=%s, EXPIRE=Ep, ASSIGNED=A|GEA)",
            staged.getOrderId(), code, EXERCISE_CODES));
}
```

**Exception type choice**: `IllegalStateException` (unchecked, represents "code logic expected X but found Y"). Doesn't force callers to `throws` it; aligns with other mapping-error throws in the same file (`mapCurrency`, `mapTradeType`, `mapAssetType` — all throw `IllegalArgumentException`).

**No structural change** to `determineTriggerInfo` — the throws just propagate naturally. The `TriggerInfo` inner class and `TradeTrigger.OPTION` vs `MANUAL` split stay exactly as-is for the ExchTrade path.

**Tiger parity**: Tiger doesn't have BookTrade semantics (no TradeConfirm concept in Tiger), so P0-3 is IBKR-only. Included here for completeness.

---

## 4. Interaction with Existing Mechanisms

### 4.1 v2 fail-fast flow (unchanged, now actually works)

After this fix, the flow an exceptional BookTrade follows is:

1. `importSingleOrder` calls `mapToTradeRecord` → `determineTriggerInfo` → `resolveBookTradeRefType` → **throws** `IllegalStateException`.
2. `importSingleOrder`'s catch block catches it, logs, and rethrows as `ImportOneFailedException`.
3. `IbkrImportService.importAll`'s loop catches `ImportOneFailedException`, calls `importWorker.markFailed(...)` via proxy → opens a fresh tx → writes `status=FAILED` + `error_message`.
4. Loop continues with the next staged row (per-row isolation preserved).
5. After loop, adapter counts: `failedCount >= 1` (+ possibly `residualCount == 0`).
6. Adapter returns `SyncResult.failure(...)` with a descriptive reason.
7. `BrokerSyncAsyncExecutor` sees failure → invokes `SyncBatchFailureHandler.handleFailure(batchId, reason)`.
8. `SyncBatchFailureHandler` dispatches to the broker's `BrokerCleanupStrategy` → wipes staged rows + any partially-imported `trade_records` rows for this batch.
9. Batch finalizes as `FAILED` (or `CLEANUP_FAILED` if step 8 fails 3 times).
10. User sees FAILED batch in the UI with a reason that includes "BookTrade orderId=XXX has no associated TradeConfirm — cannot determine option-event type".

### 4.2 Startup recovery (unchanged)

`SyncBatchRecoveryRunner` handles PENDING + PROCESSING batches at startup (not PENDING staged rows). The new adapter-level PENDING-staged check is complementary — it catches PENDING staged rows **within** a batch that otherwise appears to have completed. Recovery deals with batch-level resumption; this check deals with row-level completeness inside a single batch's `sync()` call.

### 4.3 Global uniqueness constraints (unchanged)

IBKR `ibkr_staged_orders.uk_order_id` and Tiger `tiger_staged_orders.uk_tiger_id` remain global-unique, per V20/V26. When `SyncBatchFailureHandler` + `BrokerCleanupStrategy` deletes the failed batch's staged rows, those `orderId`s become available again for the next re-sync. **This is exactly the reason P0-2 matters** — if PENDING rows leak through as COMPLETED, cleanup never runs, the rows are stuck forever, and the next re-sync hits unique-constraint violation.

### 4.4 Batch status machine (unchanged)

Still the 5 states: PENDING / PROCESSING / COMPLETED / FAILED / CLEANUP_FAILED. No new state needed. The fix operates purely on the staged-row level + the adapter's decision of success/failure.

---

## 5. Error Messages (English, per workspace rule)

All new exception messages and log entries will be in English. Examples already shown above; to consolidate:

| Site | Message |
|------|---------|
| `resolveBookTradeRefType` branch A | `BookTrade orderId=%s has no associated TradeConfirm — cannot determine option-event type` |
| `resolveBookTradeRefType` branch B | `BookTrade orderId=%s TradeConfirm has blank code — cannot determine option-event type` |
| `resolveBookTradeRefType` branch C | `BookTrade orderId=%s code='%s' did not match any known option event (EXERCISE=%s, EXPIRE=Ep, ASSIGNED=A\|GEA)` |
| `markFailed` re-read miss | `Staged row disappeared while marking FAILED: id=%d` |
| Adapter residual detected | `%d record(s) failed import in batch %d (imported=%d, skipped=%d, failed=%d, residual_non_terminal=%d, duration=%dms)` |
| Adapter residual sample log | `[IbkrSync] Residual non-terminal staged rows in batch %d (first 20 ids): %s` |

---

## 6. Testing Strategy

### 6.1 Unit tests to add

**`IbkrImportWorkerTest`**:
1. `markFailed_persistsFailedStatus_inSeparateTransaction` — mock staged row in DB, call `markFailed`, assert status=FAILED + error_message is set.
2. `importSingleOrder_throwsImportOneFailedException_whenMappingFails` — feed a staged row that will throw (e.g. blank currency), assert `ImportOneFailedException` is thrown and carries the staged ref + cause.
3. `resolveBookTradeRefType_throws_whenNoConfirm` — mock empty confirm list, assert `IllegalStateException` with "no associated TradeConfirm".
4. `resolveBookTradeRefType_throws_whenBlankCode` — mock confirm with blank code, assert throw.
5. `resolveBookTradeRefType_throws_whenUnknownCode` — mock confirm with code="XYZ", assert throw.
6. `resolveBookTradeRefType_happy_paths` — already exist (or should); keep them green.

**`TigerImportWorkerTest`**:
1. `importOne_throwsImportOneFailedException_whenMappingFails` — mirrors IBKR case.
2. `markFailed_persistsFailedStatus` — mirrors IBKR case.

**`IbkrImportServiceTest`** / **`TigerImportServiceTest`** (service-level, @SpringBootTest or similar):
1. `importAll_marksRowFailed_evenWhenWorkerThrows` — key regression test for P0-1. Feed 3 staged rows: one throws during map, two succeed. Assert after `importAll`: exactly 1 row has `status=FAILED` (not PENDING), 2 rows have `status=IMPORTED`.

**`IbkrSyncAdapterTest`** / **`TigerSyncAdapterTest`**:
1. `sync_returnsFailure_whenResidualPendingExists` — regression test for P0-2. Mock `importAll` to leave one PENDING row, assert adapter returns `SyncResult.failure` with reason mentioning `residual_non_terminal=1`.

### 6.2 Integration test scenarios (manual or automated)

- **Scenario A**: IBKR Flex Query returns an Order that's a BookTrade (blank orderTime/orderType) but whose TradeConfirm is missing from the same response. Expected: that row ends as FAILED with "no associated TradeConfirm"; whole batch FAILED; cleanup wipes it; re-sync succeeds after the underlying IBKR data is fixed.
- **Scenario B**: Simulate transient DB exception during one `save(tradeRecord)` call (e.g., unique constraint temporarily hit). Expected: that row ends FAILED with the DB error; other rows imported; batch FAILED; cleanup wipes everything; next re-sync cleanly imports all.

### 6.3 Tests NOT to run

Per workspace rule: don't run linter/tests eagerly. The author will invoke Maven locally after implementation.

---

## 7. Documentation Updates (Zero-Debt Policy)

Files to update in the **same commit** as the code fix (or in an immediately-following `docs:` commit). All paths and existing content below have been verified against the current repository state.

### 7.1 `docs/broker-sync/brokers/ibkr/booktrade-mapping.md` (verified exists)

Confirmed current text that must change:

- **L5 — Status line**: currently `✅ 已实现（IbkrImportService: BookTrade 判定 + code 解析 + triggerRefId 回填 + STK 侧语义匹配 + 歧义消解）`. Bump last-updated date to 2026-04-23 and add note that the missing-TradeConfirm / blank-code / unknown-code paths changed from "defaults to MANUAL" to "fail-fast".
- **L100-110 — § 2.3 完整判定流程**, specifically the step `└─ 均未匹配          → ⚠️ 异常情况，记录 WARNING 日志，默认设为 MANUAL（保守策略）`. Rewrite the "均未匹配" branch to: `→ 抛出 IllegalStateException, 该行标记为 FAILED, 触发整批 fail-fast cleanup`.
- Add a new subsection §2.4 **"缺失或异常的 TradeConfirm 处理"** that enumerates the three fail-fast cases:
  - `TradeConfirm` 不存在 → FAILED
  - `code` 为空 → FAILED
  - `code` 解析后无已知期权事件标记 → FAILED
  - 理由：含糊的数据宁可拒绝导入，不允许出现 `trade_trigger=OPTION` + `trigger_ref_type=NONE` 的无效组合
- **§九 待确认事项**: no change needed (the `GEA` row stays; it IS handled by our matcher — verified in the new `resolveBookTradeRefType` code above).

### 7.2 `docs/broker-sync/framework/import-consistency.md` (v2.4.2 → v2.4.3)

- Bump version header and last-updated date.
- Add a new section **"P0 Data-Loss Chain — Fixed in 2026-04"** documenting: the three defects, the fix design (split markFailed, adapter residual-check, fail-fast BookTrade resolution), and a cross-link to this file.
- If the doc has a "Known gaps" / "遗留问题" list, remove entries now fixed.

### 7.3 `docs/broker-sync/README.md`

- Bump last-updated date.
- Add a line under the IBKR section noting the P0 fix landed.
- No phase status change (Phase 3 stays ✅).

### 7.4 This file (`fix-p0-data-loss-chain.md`)

- Flip status from `📋 Design — approved` to `✅ Implemented` when the work lands.
- Add the actual commit SHA(s) at the top.

### 7.5 Not touched (deliberately out of scope)

- `docs/broker-sync/architecture.md` Phase 3 status inconsistency — separate P2-2 task.
- `docs/broker-sync/brokers/tiger/staging-schema.md` v1 residual wording — separate P2-4 task.

---

## 8. Rollout & Risk

### 8.1 Rollout
- Single commit for code + first-pass tests.
- Second commit for docs (acceptable per workspace Zero-Debt rule since docs are substantial).
- No Flyway migration needed (purely code change).
- No frontend change needed.

### 8.2 Risk assessment

| Risk | Likelihood | Impact | Mitigation |
|------|-----------|--------|------------|
| `markFailed` itself fails (DB down, etc.) — row stays PENDING | Low | Medium | P0-2's residual-PENDING check in adapter will catch it → batch FAILED, user notified |
| `ImportOneFailedException` + new throw path alters exception stack in logs | Certain | Low | Logs are more informative, not less; existing log lines kept |
| Previously "silently imported as OPTION+NONE" trade_records rows already in DB | N/A | N/A | Not deployed to production yet — no historical data to remediate. Confirmed with user 2026-04-23. |
| Test suite time increase | Low | Low | Unit tests only; no heavy integration |

### 8.3 Out of scope (for this fix)

- P1-1 (Tiger preFilter FAILED/SKIPPED classification) — user explicitly deferred.
- P1-3 (cleanup retry backoff) — not touched.
- P2-1 (Tiger staged-data frontend panel) — not touched.
- P2-2 (doc self-contradiction on Phase 3 status) — not touched; this doc update may marginally improve it.
- Historical data remediation — **not needed**; project not yet deployed to production (confirmed with user 2026-04-23).

---

## 9. Decisions (Confirmed)

All design choices below were explicitly confirmed with the user on 2026-04-23.

| # | Question | Decision |
|---|----------|----------|
| D1 | Exception type carried from worker to service on single-row failure | New dedicated `ImportOneFailedException` (package-private, carries `staged` ref + original cause). |
| D2 | `markFailed` should re-read the staged row from DB inside its REQUIRES_NEW tx? | **Yes**, `findById(staged.getId())` then mutate and save. Avoids detached-entity pitfalls at the cost of one extra read. |
| D3 | Adapter-level residual log should dump offending staged ids? | **Yes**, first 20 ids at WARN level. |
| D4 | Historical data clean-up (already-imported OPTION+NONE rows)? | **Not needed**. Project not yet deployed to production. |
| D5 | `markFailed` retry loop? | **No**. One attempt; if it fails, P0-2's residual check catches the PENDING row. |
| D6 | P0-3 failure granularity | **Per-order (option A)**: one bad row marks itself FAILED, other rows continue, batch ends FAILED via `failedCount > 0`, `SyncBatchFailureHandler` runs cleanup. No "immediate batch interrupt". |
| D7 | Call-site safety of `staged.getId()` | Verified — all call sites iterate `findByBatchIdAndStatus(batchId, "PENDING")`, entities are attached with ids. |

---

## 10. Summary of Changes (for commit message draft)

```
fix(backend): close P0 data-loss chain in broker-sync import workers

- Split staged=FAILED persistence into a separate REQUIRES_NEW tx (markFailed)
  so it no longer runs inside the rolled-back importSingleOrder tx.
- Adapter now also counts non-terminal residual staged rows; residual > 0
  triggers fail-fast cleanup instead of finalizing COMPLETED.
- IBKR BookTrade resolution: replace three silent "default to MANUAL" branches
  in resolveBookTradeRefType with IllegalStateException throws so missing /
  blank / unknown TradeConfirm codes properly fail the batch.

Affects: IbkrImportWorker, IbkrImportService, IbkrSyncAdapter,
         TigerImportWorker, TigerImportService, TigerSyncAdapter,
         + new repository method countByBatchIdAndStatusNotIn.
```
