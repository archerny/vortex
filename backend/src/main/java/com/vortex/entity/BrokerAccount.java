package com.vortex.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "broker_accounts")
public class BrokerAccount extends BaseEntity {
    @Column(name = "broker_id", nullable = false)
    private Long brokerId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "broker_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Broker broker;
    @Column(name = "account_id", length = 100)
    private String accountId;
    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "phone", length = 30)
    private String phone;
    @Column(name = "description", length = 200)
    private String description;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public Long getBrokerId() { return brokerId; }
    public void setBrokerId(Long value) { brokerId = value; }
    public Broker getBroker() { return broker; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String value) { accountId = value; }
    public String getAccountName() { return accountName; }
    public void setAccountName(String value) { accountName = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    public String getPhone() { return phone; }
    public void setPhone(String value) { phone = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean value) { isActive = value; }
}
