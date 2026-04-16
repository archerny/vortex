-- =============================================
-- V22: Add external source fields to trade_records
-- =============================================
-- Add external_id, external_broker, sync_batch_id to trade_records
-- for tracking the origin of synced records.
-- NULL values indicate manually entered records.

ALTER TABLE trade_records
    ADD COLUMN external_id VARCHAR(100),
    ADD COLUMN external_broker VARCHAR(50),
    ADD COLUMN sync_batch_id BIGINT;

-- Partial unique index: prevent duplicate imports from the same broker
CREATE UNIQUE INDEX idx_trade_records_external_uniq
    ON trade_records (external_broker, external_id)
    WHERE external_id IS NOT NULL;

-- Index for batch-based queries
CREATE INDEX idx_trade_records_sync_batch_id ON trade_records (sync_batch_id);

-- Foreign key to broker_sync_batches
ALTER TABLE trade_records
    ADD CONSTRAINT fk_trade_records_sync_batch
    FOREIGN KEY (sync_batch_id) REFERENCES broker_sync_batches(id);

-- Comments
COMMENT ON COLUMN trade_records.external_id IS 'Broker original order ID (e.g. IBKR orderID), NULL for manual entries';
COMMENT ON COLUMN trade_records.external_broker IS 'Source broker identifier (e.g. ibkr, tiger), NULL for manual entries';
COMMENT ON COLUMN trade_records.sync_batch_id IS 'FK to broker_sync_batches.id, NULL for manual entries';
