-- =============================================
-- V26: Create tiger_staged_orders table
-- =============================================
-- Tiger staged orders table (Order granularity).
-- Fields 1:1 correspond to TigerOrderRecord.java. All data fields use VARCHAR(255)
-- for lossless staging, following the same convention as ibkr_staged_orders.
--
-- Tiger Open API's FILLED_ORDERS endpoint only returns TradeOrder (order-level)
-- data, so there is no separate trade confirm table (unlike IBKR).
-- Deduplication key is `tiger_id`, which stores TradeOrder.getId() — the
-- globally unique Tiger order ID (not the ambiguous `orderId` / `externalId`).

CREATE TABLE tiger_staged_orders (
    -- Management fields
    id                      BIGSERIAL       PRIMARY KEY,
    batch_id                BIGINT          NOT NULL REFERENCES broker_sync_batches(id),
    status                  VARCHAR(32)     NOT NULL DEFAULT 'PENDING',
    imported_trade_id       BIGINT,
    error_message           TEXT,

    -- Tiger Order data fields (27 fields, all VARCHAR(255) for lossless staging)
    tiger_id                VARCHAR(255)    NOT NULL,
    account                 VARCHAR(255),
    action                  VARCHAR(255),
    status_raw              VARCHAR(255),
    order_time              VARCHAR(255),
    trade_time              VARCHAR(255),
    quantity                VARCHAR(255),
    quantity_scale          VARCHAR(255),
    filled_quantity         VARCHAR(255),
    avg_fill_price          VARCHAR(255),
    commission              VARCHAR(255),
    gst                     VARCHAR(255),
    realized_pnl            VARCHAR(255),
    symbol                  VARCHAR(255),
    contract_name           VARCHAR(255),
    sec_type                VARCHAR(255),
    currency                VARCHAR(255),
    exchange                VARCHAR(255),
    market                  VARCHAR(255),
    identifier              VARCHAR(255),
    multiplier              VARCHAR(255),
    expiry                  VARCHAR(255),
    strike                  VARCHAR(255),
    put_call                VARCHAR(255),
    order_type              VARCHAR(255),
    limit_price             VARCHAR(255),
    attr_desc               VARCHAR(255),

    -- Audit fields
    created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Unique constraint: Tiger global order ID is unique per order
CREATE UNIQUE INDEX idx_tiger_staged_orders_tiger_id ON tiger_staged_orders (tiger_id);

-- Indexes
CREATE INDEX idx_tiger_staged_orders_batch_id ON tiger_staged_orders (batch_id);
CREATE INDEX idx_tiger_staged_orders_status ON tiger_staged_orders (status);

-- Comments
COMMENT ON TABLE tiger_staged_orders IS 'Tiger staged orders table - Order granularity, 1:1 mapping to TigerOrderRecord';
COMMENT ON COLUMN tiger_staged_orders.batch_id IS 'FK to broker_sync_batches.id';
COMMENT ON COLUMN tiger_staged_orders.status IS 'Record status: PENDING, IMPORTED, SKIPPED, CONFLICT, FAILED';
COMMENT ON COLUMN tiger_staged_orders.imported_trade_id IS 'FK to trade_records.id after successful import';
COMMENT ON COLUMN tiger_staged_orders.error_message IS 'Record-level error message (conversion failure, conflict details, etc.)';
COMMENT ON COLUMN tiger_staged_orders.tiger_id IS 'Tiger global unique order ID (TradeOrder.getId()) - deduplication key';
COMMENT ON COLUMN tiger_staged_orders.status_raw IS 'Raw Tiger order status (e.g. FILLED, PARTIALLY_FILLED)';
COMMENT ON COLUMN tiger_staged_orders.quantity_scale IS 'Quantity offset; real qty = quantity * 10^-quantityScale (fractional shares)';
COMMENT ON COLUMN tiger_staged_orders.attr_desc IS 'Order attribute description - non-empty indicates option event (Exercise/Assignment/Expired)';
