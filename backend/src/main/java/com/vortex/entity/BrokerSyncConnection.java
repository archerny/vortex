package com.vortex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "broker_sync_connections")
public class BrokerSyncConnection {
    @Id
    @Column(name = "broker_account_id")
    private Long brokerAccountId;
    @Column(name = "broker_code", nullable = false)
    private String brokerCode;
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "credentials", nullable = false, columnDefinition = "jsonb")
    private Map<String, String> credentials;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist @PreUpdate
    void touch() { updatedAt = LocalDateTime.now(); }
    public Long getBrokerAccountId() { return brokerAccountId; }
    public void setBrokerAccountId(Long value) { brokerAccountId = value; }
    public String getBrokerCode() { return brokerCode; }
    public void setBrokerCode(String value) { brokerCode = value; }
    @JsonIgnore public Map<String, String> getCredentials() { return credentials; }
    public void setCredentials(Map<String, String> value) { credentials = Map.copyOf(value); }
}
