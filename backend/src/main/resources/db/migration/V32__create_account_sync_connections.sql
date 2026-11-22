CREATE TABLE broker_sync_connections (
    broker_id BIGINT PRIMARY KEY REFERENCES brokers(id) ON DELETE CASCADE,
    broker_code VARCHAR(50) NOT NULL,
    credentials JSONB NOT NULL CHECK (jsonb_typeof(credentials) = 'object'),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM ibkr_sync_settings WHERE flex_token IS NOT NULL)
       AND NOT EXISTS (SELECT 1 FROM brokers WHERE broker_code = 'ibkr') THEN
        RAISE EXCEPTION 'IBKR settings require an existing IBKR managed account before V32';
    END IF;
END $$;

INSERT INTO broker_sync_connections(broker_id, broker_code, credentials, updated_at)
SELECT b.id, 'ibkr', jsonb_build_object('token', s.flex_token, 'queryId', s.trade_confirm_query_id), s.updated_at
FROM ibkr_sync_settings s
JOIN brokers b ON b.broker_code = s.broker_code
WHERE s.flex_token IS NOT NULL;

DROP TABLE ibkr_sync_settings;
