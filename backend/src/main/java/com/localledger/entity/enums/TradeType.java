package com.localledger.entity.enums;

/**
 * 交易类型枚举
 * 对应数据库 trade_type_enum
 *
 * 仅有 BUY / SELL 两个值，描述「做了什么动作」。
 * 「为什么发生」由 trade_trigger + trigger_ref_type 表达。
 * 详见 trade-type-refactor-discussion.md
 */
public enum TradeType {
    /** 买入 */
    BUY,
    /** 卖出 */
    SELL
}
