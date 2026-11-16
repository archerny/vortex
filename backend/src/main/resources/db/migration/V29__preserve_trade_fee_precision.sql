-- Preserve source fee precision; rounding belongs only to display formatting.
ALTER TABLE trade_records
    ALTER COLUMN fee TYPE NUMERIC;

COMMENT ON COLUMN trade_records.fee IS '交易费用（保留来源小数精度，不舍入）';
