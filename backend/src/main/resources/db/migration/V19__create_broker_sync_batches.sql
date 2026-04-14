-- =============================================
-- V19: Create broker_sync_batches table
-- =============================================
-- General sync batch metadata table, shared by all brokers.
-- Records metadata for each sync operation: date range, record counts, status, etc.

CREATE TABLE broker_sync_batches (
    id              BIGSERIAL       PRIMARY KEY,
    broker_name     VARCHAR(50)     NOT NULL,
    sync_date_from  DATE            NOT NULL,
    sync_date_to    DATE            NOT NULL,
    total_count     INTEGER         NOT NULL DEFAULT 0,
    imported_count  INTEGER         NOT NULL DEFAULT 0,
    skipped_count   INTEGER         NOT NULL DEFAULT 0,
    failed_count    INTEGER         NOT NULL DEFAULT 0,
    status          VARCHAR(32)     NOT NULL,
    error_message   TEXT,
    started_at      TIMESTAMP,
    completed_at    TIMESTAMP,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Indexes
CREATE INDEX idx_sync_batches_broker_name ON broker_sync_batches (broker_name);
CREATE INDEX idx_sync_batches_status ON broker_sync_batches (status);

COMMENT ON TABLE broker_sync_batches IS 'Broker sync batch metadata table - records each sync operation';
COMMENT ON COLUMN broker_sync_batches.broker_name IS 'Broker identifier, e.g. ibkr, tiger';
COMMENT ON COLUMN broker_sync_batches.sync_date_from IS 'Start date of the sync data range';
COMMENT ON COLUMN broker_sync_batches.sync_date_to IS 'End date of the sync data range';
COMMENT ON COLUMN broker_sync_batches.total_count IS 'Total number of records fetched';
COMMENT ON COLUMN broker_sync_batches.imported_count IS 'Number of records imported to trade_records';
COMMENT ON COLUMN broker_sync_batches.skipped_count IS 'Number of records skipped (duplicates, etc.)';
COMMENT ON COLUMN broker_sync_batches.failed_count IS 'Number of records that failed to import';
COMMENT ON COLUMN broker_sync_batches.status IS 'Batch status: PENDING, IMPORTING, COMPLETED, FAILED';
COMMENT ON COLUMN broker_sync_batches.error_message IS 'Batch-level error message';
COMMENT ON COLUMN broker_sync_batches.started_at IS 'Sync start time';
COMMENT ON COLUMN broker_sync_batches.completed_at IS 'Sync completion time';
