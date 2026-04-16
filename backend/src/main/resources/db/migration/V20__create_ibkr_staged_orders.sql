-- =============================================
-- V20: Create ibkr_staged_orders table
-- =============================================
-- IBKR staged orders table (Order granularity).
-- Fields 1:1 correspond to IbkrOrderRecord.java, excluding exchange and code
-- (always empty at Order level). All data fields use VARCHAR for lossless storage.

CREATE TABLE ibkr_staged_orders (
    -- Management fields
    id                      BIGSERIAL       PRIMARY KEY,
    batch_id                BIGINT          NOT NULL REFERENCES broker_sync_batches(id),
    status                  VARCHAR(32)     NOT NULL DEFAULT 'PENDING',
    imported_trade_id       BIGINT,
    error_message           TEXT,

    -- IBKR Order data fields (30 fields, all VARCHAR(255))
    account_id              VARCHAR(255),
    acct_alias              VARCHAR(255),
    currency                VARCHAR(255),
    asset_category          VARCHAR(255),
    symbol                  VARCHAR(255),
    description             VARCHAR(255),
    conid                   VARCHAR(255),
    security_id             VARCHAR(255),
    security_id_type        VARCHAR(255),
    multiplier              VARCHAR(255),
    strike                  VARCHAR(255),
    expiry                  VARCHAR(255),
    put_call                VARCHAR(255),
    order_id                VARCHAR(255),
    order_time              VARCHAR(255),
    date_time               VARCHAR(255),
    settle_date             VARCHAR(255),
    trade_date              VARCHAR(255),
    buy_sell                VARCHAR(255),
    order_type              VARCHAR(255),
    is_api_order            VARCHAR(255),
    quantity                VARCHAR(255),
    price                   VARCHAR(255),
    amount                  VARCHAR(255),
    proceeds                VARCHAR(255),
    net_cash                VARCHAR(255),
    commission              VARCHAR(255),
    commission_currency     VARCHAR(255),
    trade_charge            VARCHAR(255),
    trader_id               VARCHAR(255),

    -- Audit fields
    created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Unique constraint: prevent duplicate orders in staging
CREATE UNIQUE INDEX idx_ibkr_staged_orders_order_id ON ibkr_staged_orders (order_id);

-- Indexes
CREATE INDEX idx_ibkr_staged_orders_batch_id ON ibkr_staged_orders (batch_id);
CREATE INDEX idx_ibkr_staged_orders_status ON ibkr_staged_orders (status);

-- Comments
COMMENT ON TABLE ibkr_staged_orders IS 'IBKR staged orders table - Order granularity, 1:1 mapping to IbkrOrderRecord';
COMMENT ON COLUMN ibkr_staged_orders.batch_id IS 'FK to broker_sync_batches.id';
COMMENT ON COLUMN ibkr_staged_orders.status IS 'Record status: PENDING, IMPORTED, SKIPPED, CONFLICT, FAILED';
COMMENT ON COLUMN ibkr_staged_orders.imported_trade_id IS 'FK to trade_records.id after successful import';
COMMENT ON COLUMN ibkr_staged_orders.error_message IS 'Record-level error message (conversion failure, conflict details, etc.)';
COMMENT ON COLUMN ibkr_staged_orders.order_id IS 'IBKR order ID - unique identifier for deduplication';
