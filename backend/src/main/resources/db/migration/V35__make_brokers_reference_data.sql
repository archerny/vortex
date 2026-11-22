ALTER TABLE brokers ADD COLUMN introduction VARCHAR(500);
ALTER TABLE brokers DROP COLUMN is_active;

COMMENT ON COLUMN brokers.introduction IS '券商简介；与托管账户备注独立';
