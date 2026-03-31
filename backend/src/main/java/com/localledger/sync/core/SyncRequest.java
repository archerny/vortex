package com.localledger.sync.core;

/**
 * 同步请求参数
 *
 * 封装触发一次券商同步所需的参数。
 * Phase 1 仅支持指定券商名称，后续可扩展时间范围、证券类型等过滤条件。
 */
public class SyncRequest {

    /**
     * 券商名称标识，用于匹配对应的适配器
     * 如 "tiger"、"ibkr"、"futu" 等
     */
    private String brokerName;

    /**
     * 同步的起始时间（可选），格式如 "2025-01-01" 或毫秒时间戳
     * 为空时由适配器自行决定默认范围
     */
    private String startTime;

    /**
     * 同步的截止时间（可选），格式如 "2025-03-31" 或毫秒时间戳
     * 为空时由适配器自行决定默认范围
     */
    private String endTime;

    // ============ Constructors ============

    public SyncRequest() {
    }

    public SyncRequest(String brokerName) {
        this.brokerName = brokerName;
    }

    public SyncRequest(String brokerName, String startTime, String endTime) {
        this.brokerName = brokerName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ============ Getters and Setters ============

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SyncRequest{" +
                "brokerName='" + brokerName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
