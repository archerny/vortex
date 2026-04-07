package com.localledger.sync.adapter.ibkr;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 盈透证券 IBKR Flex Query 配置属性
 *
 * 从 application.properties / application-local.properties 中读取配置：
 * - broker.ibkr.flex-token
 * - broker.ibkr.trade-confirm-query-id
 */
@Component
@ConfigurationProperties(prefix = "broker.ibkr")
public class IbkrFlexQueryProperties {

    /** Flex Web Service 访问令牌 */
    private String flexToken;

    /** Trade Confirm 类型 Flex Query 的 ID */
    private String tradeConfirmQueryId;

    // ============ Getters and Setters ============

    public String getFlexToken() {
        return flexToken;
    }

    public void setFlexToken(String flexToken) {
        this.flexToken = flexToken;
    }

    public String getTradeConfirmQueryId() {
        return tradeConfirmQueryId;
    }

    public void setTradeConfirmQueryId(String tradeConfirmQueryId) {
        this.tradeConfirmQueryId = tradeConfirmQueryId;
    }

    /**
     * 检查配置是否完整
     */
    public boolean isConfigured() {
        return flexToken != null && !flexToken.isBlank()
                && tradeConfirmQueryId != null && !tradeConfirmQueryId.isBlank();
    }
}
