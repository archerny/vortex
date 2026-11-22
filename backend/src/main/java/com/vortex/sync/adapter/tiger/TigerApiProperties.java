package com.vortex.sync.adapter.tiger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.function.Supplier;

/**
 * 老虎证券 API 配置属性
 *
 * 正式应用从托管账户的数据库连接读取配置，独立测试可注入合成凭证。
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
    private volatile Supplier<Credentials> credentialSource;
    private volatile java.util.function.LongFunction<Credentials> accountCredentialSource;

    public record Credentials(String tigerId, String privateKey, String account) {
        public boolean isConfigured() {
            return tigerId != null && !tigerId.isBlank() && privateKey != null && !privateKey.isBlank()
                    && account != null && !account.isBlank();
        }
        @Override public String toString() { return "Credentials[redacted]"; }
    }

    public void useDatabaseCredentials(Supplier<Credentials> source) { credentialSource = source; }

    public void useAccountCredentials(java.util.function.LongFunction<Credentials> source) { accountCredentialSource = source; }

    public Credentials credentials(Long brokerAccountId) {
        if (accountCredentialSource == null) return credentials();
        return brokerAccountId == null ? new Credentials(null, null, null) : accountCredentialSource.apply(brokerAccountId);
    }

    public Credentials credentials() {
        if (accountCredentialSource != null) return credentials((Long) null);
        Supplier<Credentials> source = credentialSource;
        return source == null ? new Credentials(tigerId, privateKey, account) : source.get();
    }

    // ============ Getters and Setters ============

    public String getTigerId() {
        return credentials().tigerId();
    }

    public void setTigerId(String tigerId) {
        this.tigerId = tigerId;
    }

    public String getPrivateKey() {
        return credentials().privateKey();
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAccount() {
        return credentials().account();
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 检查配置是否完整
     */
    public boolean isConfigured() {
        return credentials().isConfigured();
    }
}
