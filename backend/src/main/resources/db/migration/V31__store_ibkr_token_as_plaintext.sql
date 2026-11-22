DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM ibkr_sync_settings WHERE flex_token_ciphertext IS NOT NULL)
       AND current_setting('vortex.ibkr_v31_converted', true) IS DISTINCT FROM 'true' THEN
        RAISE EXCEPTION 'V31 requires the IBKR credential conversion callback; run the application migration';
    END IF;
END $$;

ALTER TABLE ibkr_sync_settings RENAME COLUMN flex_token_ciphertext TO flex_token;

COMMENT ON COLUMN ibkr_sync_settings.flex_token IS 'IBKR Flex Token（本地个人使用，明文存储）';
COMMENT ON TABLE ibkr_sync_settings IS 'IBKR settings with plaintext Token and Query ID';
