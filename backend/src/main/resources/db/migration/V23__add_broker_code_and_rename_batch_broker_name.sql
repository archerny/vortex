-- =============================================
-- V23: Add broker_code to brokers + rename broker_sync_batches.broker_name
-- =============================================
-- 1. brokers table: add broker_code column with partial unique index
-- 2. broker_sync_batches table: rename broker_name → broker_code

-- ============ 1. brokers.broker_code ============

ALTER TABLE brokers ADD COLUMN broker_code VARCHAR(50);

-- Partial unique index: broker_code must be unique when not null
CREATE UNIQUE INDEX idx_brokers_broker_code
    ON brokers (broker_code)
    WHERE broker_code IS NOT NULL;

COMMENT ON COLUMN brokers.broker_code IS 'Technical identifier for sync adapter association (e.g. ibkr, tiger). NULL for manual-only brokers.';

-- ============ 2. broker_sync_batches: broker_name → broker_code ============

ALTER TABLE broker_sync_batches RENAME COLUMN broker_name TO broker_code;

-- Rename the existing index to match the new column name
ALTER INDEX idx_sync_batches_broker_name RENAME TO idx_sync_batches_broker_code;

COMMENT ON COLUMN broker_sync_batches.broker_code IS 'Broker technical identifier (e.g. ibkr, tiger)';
