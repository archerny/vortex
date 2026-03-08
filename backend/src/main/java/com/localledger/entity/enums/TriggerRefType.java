package com.localledger.entity.enums;

/**
 * 触发关联类型枚举
 * 用于区分 trigger_ref_id 指向哪张表
 */
public enum TriggerRefType {
    /** 无关联 */
    NONE,
    /** 拆股事件，关联 events_stock_split 表 */
    STOCK_SPLIT,
    /** 代码变更事件，关联 events_symbol_change 表 */
    SYMBOL_CHANGE,
    /** 实物分红事件，关联 events_dividend_in_kind 表 */
    DIVIDEND_IN_KIND,
    /** 期权记录，关联期权相关记录表（待定） */
    OPTION
}
