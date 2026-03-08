package com.localledger.entity.enums;

/**
 * 交易触发来源枚举
 * 描述交易记录「为什么发生」，与 TradeType（描述「做了什么」）互补
 */
public enum TradeTrigger {
    /** 手动交易 — 用户在券商平台上主动下单执行的交易 */
    MANUAL,
    /** 期权 — 期权相关事件（到期行权、提前行权、到期失效、被指派等）导致的被动买入或卖出 */
    OPTION,
    /** 市场事件 — 拆股、代码变更、实物分红等市场事件产生的系统交易记录 */
    MARKET_EVENT
}
