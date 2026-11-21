package com.vortex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ibkr_sync_settings")
public class IbkrSyncSettings {
    @Id
    @Column(name = "broker_code", length = 16)
    private String brokerCode = "ibkr";
    @JsonIgnore
    @Column(name = "flex_token_ciphertext")
    private String tokenCiphertext;
    @Column(name = "trade_confirm_query_id", length = 64)
    private String queryId;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist @PreUpdate
    void touch() { updatedAt = LocalDateTime.now(); }
    public String getBrokerCode() { return brokerCode; }
    @JsonIgnore public String getTokenCiphertext() { return tokenCiphertext; }
    public void setTokenCiphertext(String value) { tokenCiphertext = value; }
    public String getQueryId() { return queryId; }
    public void setQueryId(String value) { queryId = value; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
