-- 新增交易触发来源相关字段
-- 设计文档：trade-trigger-design.md

-- 1. 新增 trade_trigger 字段（VARCHAR，NOT NULL，无默认值）
-- 先添加为可空字段，回填数据后再设置 NOT NULL
ALTER TABLE trade_records ADD COLUMN trade_trigger VARCHAR(32);

-- 2. 新增 trigger_ref_id 字段（BIGINT，NOT NULL DEFAULT 0）
ALTER TABLE trade_records ADD COLUMN trigger_ref_id BIGINT NOT NULL DEFAULT 0;

-- 3. 新增 trigger_ref_type 字段（VARCHAR，NOT NULL DEFAULT 'NONE'）
ALTER TABLE trade_records ADD COLUMN trigger_ref_type VARCHAR(32) NOT NULL DEFAULT 'NONE';

-- 4. 回填历史数据：所有现有交易记录标记为 MANUAL（手动交易）
UPDATE trade_records SET trade_trigger = 'MANUAL' WHERE trade_trigger IS NULL;

-- 5. 设置 trade_trigger 为 NOT NULL（回填完成后）
ALTER TABLE trade_records ALTER COLUMN trade_trigger SET NOT NULL;

-- 6. 添加字段注释
COMMENT ON COLUMN trade_records.trade_trigger IS '交易触发来源：MANUAL-手动交易，OPTION-期权相关，MARKET_EVENT-市场事件';
COMMENT ON COLUMN trade_records.trigger_ref_id IS '触发来源的关联记录ID，0表示无关联';
COMMENT ON COLUMN trade_records.trigger_ref_type IS '触发来源的关联记录类型：NONE-无关联，STOCK_SPLIT-拆股事件，SYMBOL_CHANGE-代码变更，DIVIDEND_IN_KIND-实物分红，OPTION-期权记录';
