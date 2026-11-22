CREATE TABLE broker_accounts (
    id BIGSERIAL PRIMARY KEY,
    broker_id BIGINT NOT NULL REFERENCES brokers(id),
    account_id VARCHAR(100),
    account_name VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_broker_accounts_identity UNIQUE (broker_id, account_id),
    CONSTRAINT uk_broker_accounts_owner UNIQUE (id, broker_id),
    CONSTRAINT ck_broker_accounts_id CHECK (account_id IS NULL OR (length(account_id) > 0 AND account_id !~ '\s'))
);

-- Keep historical ownership without inventing an external account identity.
INSERT INTO broker_accounts(id, broker_id, account_name, is_active)
SELECT id, id, '待补全账户', COALESCE(is_active, FALSE) FROM brokers;
SELECT setval('broker_accounts_id_seq', COALESCE(MAX(id), 1), MAX(id) IS NOT NULL) FROM broker_accounts;

ALTER TABLE trade_records ADD COLUMN broker_account_id BIGINT;
ALTER TABLE trade_records DISABLE TRIGGER update_trade_records_updated_at;
UPDATE trade_records SET broker_account_id = broker_id;
ALTER TABLE trade_records ENABLE TRIGGER update_trade_records_updated_at;
ALTER TABLE trade_records ALTER COLUMN broker_account_id SET NOT NULL;
ALTER TABLE trade_records ADD CONSTRAINT fk_trade_account_owner
    FOREIGN KEY (broker_account_id, broker_id) REFERENCES broker_accounts(id, broker_id);
CREATE INDEX idx_trade_records_account ON trade_records(broker_account_id);

ALTER TABLE cash_flow_records ADD COLUMN broker_account_id BIGINT;
ALTER TABLE cash_flow_records DISABLE TRIGGER update_cash_flow_records_updated_at;
UPDATE cash_flow_records SET broker_account_id = broker_id;
ALTER TABLE cash_flow_records ENABLE TRIGGER update_cash_flow_records_updated_at;
ALTER TABLE cash_flow_records ALTER COLUMN broker_account_id SET NOT NULL;
ALTER TABLE cash_flow_records ADD CONSTRAINT fk_cash_flow_account_owner
    FOREIGN KEY (broker_account_id, broker_id) REFERENCES broker_accounts(id, broker_id);
CREATE INDEX idx_cash_flow_records_account ON cash_flow_records(broker_account_id);

ALTER TABLE broker_sync_connections DROP CONSTRAINT broker_sync_connections_broker_id_fkey;
ALTER TABLE broker_sync_connections RENAME COLUMN broker_id TO broker_account_id;
ALTER TABLE broker_sync_connections ADD CONSTRAINT fk_sync_connection_account
    FOREIGN KEY (broker_account_id) REFERENCES broker_accounts(id) ON DELETE CASCADE;
-- Preserve existing JSON; runtime ignores the old Tiger account key and uses managed identity.

ALTER TABLE broker_sync_batches ADD COLUMN broker_account_id BIGINT REFERENCES broker_accounts(id);
ALTER TABLE broker_sync_batches ADD COLUMN account_id VARCHAR(100);
-- Existing imported trades are authoritative; current broker codes may have moved.
UPDATE broker_sync_batches batch SET broker_account_id = ownership.broker_id
FROM (SELECT sync_batch_id, MIN(broker_id) AS broker_id FROM trade_records
      WHERE sync_batch_id IS NOT NULL GROUP BY sync_batch_id HAVING COUNT(DISTINCT broker_id) = 1) ownership
WHERE batch.id = ownership.sync_batch_id;
CREATE INDEX idx_sync_batches_account ON broker_sync_batches(broker_account_id);

DROP INDEX idx_trade_records_external_uniq;
CREATE UNIQUE INDEX idx_trade_records_external_uniq
    ON trade_records(broker_account_id, external_broker, external_id) WHERE external_id IS NOT NULL;
DROP INDEX idx_ibkr_staged_orders_order_id;
CREATE UNIQUE INDEX idx_ibkr_staged_orders_order_id ON ibkr_staged_orders(account_id, order_id);
DROP INDEX idx_ibkr_staged_trade_confirms_trade_id;
CREATE UNIQUE INDEX idx_ibkr_staged_trade_confirms_trade_id ON ibkr_staged_trade_confirms(account_id, trade_id);
DROP INDEX idx_tiger_staged_orders_tiger_id;
CREATE UNIQUE INDEX idx_tiger_staged_orders_tiger_id ON tiger_staged_orders(account, tiger_id);

-- Preserve unknown historical source fields; all new/updated staged rows need an identity.
ALTER TABLE ibkr_staged_orders ADD CONSTRAINT ck_ibkr_order_account
    CHECK (account_id IS NOT NULL AND length(btrim(account_id)) > 0) NOT VALID;
ALTER TABLE ibkr_staged_trade_confirms ADD CONSTRAINT ck_ibkr_confirm_account
    CHECK (account_id IS NOT NULL AND length(btrim(account_id)) > 0) NOT VALID;
ALTER TABLE tiger_staged_orders ADD CONSTRAINT ck_tiger_order_account
    CHECK (account IS NOT NULL AND length(btrim(account)) > 0) NOT VALID;

COMMENT ON TABLE broker_accounts IS '托管账户；account_id为券商实际账户ID，NULL仅用于待补全的历史账户';
COMMENT ON COLUMN broker_accounts.id IS '本地账户主键，与券商实际账户ID区分';
