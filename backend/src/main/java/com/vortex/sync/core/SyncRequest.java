package com.vortex.sync.core;

/**
 * 同步请求参数
 *
 * 封装触发一次券商同步所需的参数。
 * Phase 2 新增 batchId，供适配器在 staging/importing 阶段引用批次记录。
 */
public class SyncRequest {

    /**
     * 券商技术标识符，用于匹配对应的适配器
     * 如 "tiger"、"ibkr"、"futu" 等
     */
    private String brokerCode;

    /**
     * 关联的同步批次 ID（由 BrokerSyncAsyncExecutor 在调用 sync 前设置）
     * 适配器通过此 ID 更新 phase、关联 staging/import 记录
     */
    private Long batchId;

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

    public SyncRequest(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public SyncRequest(String brokerCode, String startTime, String endTime) {
        this.brokerCode = brokerCode;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ============ Getters and Setters ============

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
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
                "brokerCode='" + brokerCode + '\'' +
                ", batchId=" + batchId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
