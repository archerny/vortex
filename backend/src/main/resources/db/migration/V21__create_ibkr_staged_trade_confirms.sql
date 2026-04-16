-- =============================================
-- V21: Create ibkr_staged_trade_confirms table
-- =============================================
-- IBKR staged trade confirms table (TradeConfirm granularity).
-- Fields 1:1 correspond to IbkrTradeConfirm.java (37 data fields).
-- Used for audit and reconciliation, does NOT participate in trade_records import flow.

CREATE TABLE ibkr_staged_trade_confirms (
    -- Management fields
    id                      BIGSERIAL       PRIMARY KEY,
    batch_id                BIGINT          NOT NULL REFERENCES broker_sync_batches(id),

    -- IBKR TradeConfirm data fields (37 fields, all VARCHAR(255))
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
    transaction_type        VARCHAR(255),
    trade_id                VARCHAR(255),
    order_id                VARCHAR(255),
    exec_id                 VARCHAR(255),
    brokerage_order_id      VARCHAR(255),
    order_reference         VARCHAR(255),
    order_time              VARCHAR(255),
    date_time               VARCHAR(255),
    settle_date             VARCHAR(255),
    trade_date              VARCHAR(255),
    exchange                VARCHAR(255),
    buy_sell                VARCHAR(255),
    quantity                VARCHAR(255),
    price                   VARCHAR(255),
    amount                  VARCHAR(255),
    proceeds                VARCHAR(255),
    net_cash                VARCHAR(255),
    commission              VARCHAR(255),
    commission_currency     VARCHAR(255),
    trade_charge            VARCHAR(255),
    code                    VARCHAR(255),
    order_type              VARCHAR(255),
    trader_id               VARCHAR(255),
    is_api_order            VARCHAR(255),

    -- Audit fields
    created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Unique constraint: IBKR tradeID is globally unique
CREATE UNIQUE INDEX idx_ibkr_staged_trade_confirms_trade_id ON ibkr_staged_trade_confirms (trade_id);

-- Indexes
CREATE INDEX idx_ibkr_staged_trade_confirms_batch_id ON ibkr_staged_trade_confirms (batch_id);
CREATE INDEX idx_ibkr_staged_trade_confirms_order_id ON ibkr_staged_trade_confirms (order_id);

-- Comments
COMMENT ON TABLE ibkr_staged_trade_confirms IS 'IBKR staged trade confirms - TradeConfirm granularity, for audit and reconciliation';
COMMENT ON COLUMN ibkr_staged_trade_confirms.batch_id IS 'FK to broker_sync_batches.id';
COMMENT ON COLUMN ibkr_staged_trade_confirms.trade_id IS 'IBKR trade confirmation ID - globally unique';
COMMENT ON COLUMN ibkr_staged_trade_confirms.order_id IS 'Parent order ID - correlates with ibkr_staged_orders.order_id';
