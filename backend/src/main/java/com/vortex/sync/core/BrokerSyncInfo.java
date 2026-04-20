package com.vortex.sync.core;

/**
 * Broker sync info DTO.
 *
 * Returned by the /api/broker-sync/brokers endpoint to provide
 * structured broker information for the frontend sync management UI.
 */
public class BrokerSyncInfo {

    /** Technical identifier, e.g. "ibkr" */
    private String brokerCode;

    /** Display name from brokers table, e.g. "盈透证券" */
    private String brokerName;

    /** Country/region from brokers table */
    private String country;

    /** Primary key from brokers table */
    private Long brokerId;

    // ============ Constructors ============

    public BrokerSyncInfo() {
    }

    public BrokerSyncInfo(String brokerCode, String brokerName, String country, Long brokerId) {
        this.brokerCode = brokerCode;
        this.brokerName = brokerName;
        this.country = country;
        this.brokerId = brokerId;
    }

    // ============ Getters and Setters ============

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    @Override
    public String toString() {
        return "BrokerSyncInfo{" +
                "brokerCode='" + brokerCode + '\'' +
                ", brokerName='" + brokerName + '\'' +
                ", country='" + country + '\'' +
                ", brokerId=" + brokerId +
                '}';
    }
}
