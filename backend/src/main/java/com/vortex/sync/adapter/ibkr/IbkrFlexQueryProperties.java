package com.vortex.sync.adapter.ibkr;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.function.Supplier;

/**
 * 盈透证券 IBKR Flex Query 配置属性
 *
 * 正式应用的凭证由配置服务从数据库读取；独立测试可直接注入合成凭证。
 * 从启动配置读取固定服务地址：
 * - broker.ibkr.base-url（可选，默认为 IBKR Flex Web Service 正式地址）
 */
@Component
@ConfigurationProperties(prefix = "broker.ibkr")
public class IbkrFlexQueryProperties {

    /** Flex Web Service 访问令牌 */
    private String flexToken;

    /** Trade Confirm 类型 Flex Query 的 ID */
    private String tradeConfirmQueryId;
    private volatile Supplier<Credentials> credentialSource;

    public record Credentials(String token, String queryId) {
        @Override public String toString() { return "Credentials[redacted]"; }
    }

    public void useDatabaseCredentials(Supplier<Credentials> source) { credentialSource = source; }

    public Credentials credentials() {
        Supplier<Credentials> source = credentialSource;
        return source == null ? new Credentials(flexToken, tradeConfirmQueryId) : source.get();
    }

    /** Flex Web Service Base URL（带默认值，可通过配置覆盖） */
    private String baseUrl = "https://ndcdyn.interactivebrokers.com/AccountManagement/FlexWebService";

    // ============ Getters and Setters ============

    public String getFlexToken() {
        return credentials().token();
    }

    public void setFlexToken(String flexToken) {
        this.flexToken = flexToken;
    }

    public String getTradeConfirmQueryId() {
        return credentials().queryId();
    }

    public void setTradeConfirmQueryId(String tradeConfirmQueryId) {
        this.tradeConfirmQueryId = tradeConfirmQueryId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 检查配置是否完整
     */
    public boolean isConfigured() {
        Credentials value = credentials();
        return value.token() != null && !value.token().isBlank()
                && value.queryId() != null && !value.queryId().isBlank();
    }
}
