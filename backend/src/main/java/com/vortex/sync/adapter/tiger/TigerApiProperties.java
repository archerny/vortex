package com.vortex.sync.adapter.tiger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 老虎证券 API 配置属性
 *
 * 从 application.properties / application-local.properties 中读取配置：
 * - broker.tiger.tiger-id
 * - broker.tiger.private-key
 * - broker.tiger.account
 */
@Component
@ConfigurationProperties(prefix = "broker.tiger")
public class TigerApiProperties {

    /** 老虎开放平台 Tiger ID */
    private String tigerId;

    /** RSA 私钥（PKCS#8 格式） */
    private String privateKey;

    /** 资金账号 */
    private String account;

    // ============ Getters and Setters ============

    public String getTigerId() {
        return tigerId;
    }

    public void setTigerId(String tigerId) {
        this.tigerId = tigerId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 检查配置是否完整
     */
    public boolean isConfigured() {
        return tigerId != null && !tigerId.isBlank()
                && privateKey != null && !privateKey.isBlank()
                && account != null && !account.isBlank();
    }
}
