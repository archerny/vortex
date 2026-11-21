CREATE TABLE ibkr_sync_settings (
    broker_code VARCHAR(16) PRIMARY KEY CHECK (broker_code = 'ibkr'),
    flex_token_ciphertext TEXT,
    trade_confirm_query_id VARCHAR(64),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_ibkr_settings_complete CHECK (
        (flex_token_ciphertext IS NULL AND trade_confirm_query_id IS NULL)
        OR (flex_token_ciphertext IS NOT NULL AND trade_confirm_query_id IS NOT NULL
            AND length(flex_token_ciphertext) > 0 AND length(trade_confirm_query_id) > 0)
    )
);
COMMENT ON TABLE ibkr_sync_settings IS 'IBKR settings; token is authenticated ciphertext, never plaintext';
