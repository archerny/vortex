-- =============================================
-- V28: Simplify sync batch state model (v2 "fail-fast + cleanup")
-- =============================================
-- v2 consistency model replaces the v1 six-state machine with a five-state one:
--   PENDING / PROCESSING  (active)
--   COMPLETED / FAILED    (terminal, nothing left behind)
--   CLEANUP_FAILED        (terminal, requires manual intervention)
--
-- Key changes vs v1:
--   1. No more PARTIAL or INTERRUPTED states.
--   2. No more failed_count column — failed imports trigger whole-batch cleanup
--      and the batch transitions straight to FAILED (no aggregate retained).
--   3. At most one active batch at any time, enforced by a DB-level partial
--      unique index (see step 4).
--
-- See docs/broker-sync/framework/import-consistency.md for the full design.

-- ============ 1. Drop failed_count column ============
-- v2 has no "partial success" concept; failed records trigger cleanup, not
-- aggregation. The column is no longer meaningful.

ALTER TABLE broker_sync_batches
    DROP COLUMN IF EXISTS failed_count;

-- ============ 2. Refresh status column comment ============

COMMENT ON COLUMN broker_sync_batches.status IS
    'Batch status: PENDING, PROCESSING, COMPLETED, FAILED, CLEANUP_FAILED';

-- ============ 3. Refresh phase column comment ============
-- Phase is kept for PROCESSING (current progress) and CLEANUP_FAILED
-- (diagnostic: which phase was running when cleanup kicked in).

COMMENT ON COLUMN broker_sync_batches.phase IS
    'Sub-stage: FETCHING, STAGING, IMPORTING. Indicates current progress when status=PROCESSING; preserved for diagnostics when status=CLEANUP_FAILED; NULL otherwise.';

-- ============ 4. Enforce "at most one active batch" ============
-- Partial unique index (PostgreSQL native): all active rows share the constant
-- key (1); non-active rows are excluded from the index entirely. A second
-- active row therefore violates uniqueness and INSERT fails with
-- DataIntegrityViolationException, which the application translates to
-- SyncConflictException -> HTTP 409.
--
-- Active = status IN (PENDING, PROCESSING, CLEANUP_FAILED).
-- CLEANUP_FAILED is deliberately treated as active so that it blocks new syncs
-- until an operator resolves the orphaned data and flips the status manually.

CREATE UNIQUE INDEX uk_only_one_active
    ON broker_sync_batches ((1))
    WHERE status IN ('PENDING', 'PROCESSING', 'CLEANUP_FAILED');

COMMENT ON INDEX uk_only_one_active IS
    'Partial unique index: blocks a second active sync batch at the DB level. Released automatically when status becomes COMPLETED or FAILED.';
