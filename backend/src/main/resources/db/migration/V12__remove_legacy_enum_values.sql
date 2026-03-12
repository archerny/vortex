-- 清理旧体系遗留：从枚举类型中移除已废弃的值
-- 历史数据已全部订正完成，不再有记录使用这些旧值

-- 1. 从 trade_type_enum 中移除 OPTION_EXPIRE、EXERCISE_BUY、EXERCISE_SELL
ALTER TYPE trade_type_enum RENAME TO trade_type_enum_old;
CREATE TYPE trade_type_enum AS ENUM ('BUY', 'SELL');
ALTER TABLE trade_records
    ALTER COLUMN trade_type TYPE trade_type_enum
    USING trade_type::text::trade_type_enum;
DROP TYPE trade_type_enum_old;

-- 2. trigger_ref_type 字段为 VARCHAR(32)（V10 创建），不是数据库枚举类型，
--    旧值 OPTION 的清理由应用层 Java Enum 控制，无需修改数据库结构。
--    存量数据中已无 OPTION 值，仅更新注释以反映当前有效值。
COMMENT ON COLUMN trade_records.trigger_ref_type IS '触发来源的关联记录类型：NONE-无关联，STOCK_SPLIT-拆股事件，SYMBOL_CHANGE-代码变更，DIVIDEND_IN_KIND-实物分红，OPTION_EXPIRE-期权到期，OPTION_EXERCISE-行权，OPTION_ASSIGNED-被指派';
