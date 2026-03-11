package com.localledger.entity.enums;

/**
 * 交易类型枚举
 * 对应数据库 trade_type_enum
 *
 * 重构后仅保留 BUY / SELL 两个值，描述「做了什么动作」。
 * 「为什么发生」由 trade_trigger + trigger_ref_type 表达。
 * 详见 trade-type-refactor-discussion.md
 */
public enum TradeType {
    /** 买入 */
    BUY,
    /** 卖出 */
    SELL,

    /**
     * @deprecated 已废弃。期权到期场景改用 TradeType.SELL + TradeTrigger.OPTION + TriggerRefType.OPTION_EXPIRE。
     *             保留仅为兼容存量历史数据，待数据订正完成后移除。
     */
    @Deprecated
    OPTION_EXPIRE,

    /**
     * @deprecated 已废弃。行权买股场景改用 TradeType.BUY + TradeTrigger.OPTION + TriggerRefType.OPTION_EXERCISE。
     *             保留仅为兼容存量历史数据，待数据订正完成后移除。
     */
    @Deprecated
    EXERCISE_BUY,

    /**
     * @deprecated 已废弃。行权卖股场景改用 TradeType.SELL + TradeTrigger.OPTION + TriggerRefType.OPTION_EXERCISE。
     *             保留仅为兼容存量历史数据，待数据订正完成后移除。
     */
    @Deprecated
    EXERCISE_SELL
}
