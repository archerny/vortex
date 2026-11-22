ALTER TABLE broker_accounts ADD COLUMN email VARCHAR(100);
ALTER TABLE broker_accounts ADD COLUMN phone VARCHAR(30);
ALTER TABLE broker_accounts ADD COLUMN description VARCHAR(200);

-- Keep legacy profile data with the original pending account where unambiguous.
-- Never guess a real account when several managed accounts already exist.
DO $$
DECLARE
    source RECORD;
    account_count BIGINT;
    pending_count BIGINT;
    target_id BIGINT;
BEGIN
    FOR source IN SELECT id, email, phone, description, is_active FROM brokers
        WHERE email IS NOT NULL OR phone IS NOT NULL OR description IS NOT NULL
    LOOP
        SELECT COUNT(*), COUNT(*) FILTER (WHERE account_id IS NULL)
            INTO account_count, pending_count FROM broker_accounts WHERE broker_id = source.id;
        IF pending_count = 1 THEN
            SELECT id INTO target_id FROM broker_accounts WHERE broker_id = source.id AND account_id IS NULL;
        ELSIF account_count = 1 THEN
            SELECT id INTO target_id FROM broker_accounts WHERE broker_id = source.id;
        ELSE
            INSERT INTO broker_accounts(broker_id, account_name, is_active)
                VALUES (source.id, '待补全账户（原券商资料）', COALESCE(source.is_active, FALSE))
                RETURNING id INTO target_id;
        END IF;
        UPDATE broker_accounts SET email = source.email, phone = source.phone,
            description = source.description WHERE id = target_id;
    END LOOP;
END $$;

ALTER TABLE brokers DROP COLUMN email;
ALTER TABLE brokers DROP COLUMN phone;
ALTER TABLE brokers DROP COLUMN description;

COMMENT ON COLUMN broker_accounts.email IS '账户关联邮箱';
COMMENT ON COLUMN broker_accounts.phone IS '账户关联手机号或电话，含国际区号';
COMMENT ON COLUMN broker_accounts.description IS '账户备注';
