-- 更新交易类型字段注释：将"行权买入"改为"行权买股"，"行权卖出"改为"行权卖股"
COMMENT ON COLUMN trade_records.trade_type IS '交易类型：BUY-买入，SELL-卖出，OPTION_EXPIRE-期权到期，EXERCISE_BUY-行权买股，EXERCISE_SELL-行权卖股，EARLY_EXERCISE-提前行权';
