-- =============================================
-- V24: Add phase column and expand batch status
-- =============================================
-- 1. Add phase column to broker_sync_batches for sub-stage tracking
-- 2. Migrate existing IMPORTING status to PROCESSING
--
-- New status model:
--   status: PENDING / PROCESSING / COMPLETED / PARTIAL / FAILED / INTERRUPTED
--   phase:  null / FETCHING / STAGING / IMPORTING (only meaningful when status=PROCESSING)

-- ============ 1. Add phase column ============

ALTER TABLE broker_sync_batches ADD COLUMN phase VARCHAR(32);

COMMENT ON COLUMN broker_sync_batches.phase IS 'Sub-stage within PROCESSING status: FETCHING, STAGING, IMPORTING. NULL when not processing.';

-- ============ 2. Migrate IMPORTING → PROCESSING ============

UPDATE broker_sync_batches
SET status = 'PROCESSING',
    updated_at = NOW()
WHERE status = 'IMPORTING';

-- Update status comment to reflect expanded values
COMMENT ON COLUMN broker_sync_batches.status IS 'Batch status: PENDING, PROCESSING, COMPLETED, PARTIAL, FAILED, INTERRUPTED';
