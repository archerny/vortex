package com.localledger.sync.core;

/**
 * 同步结果
 *
 * 封装一次同步操作的执行结果信息。
 */
public class SyncResult {

    /** 是否执行成功 */
    private boolean success;

    /** 券商名称 */
    private String brokerName;

    /** 获取到的订单记录数 */
    private int totalRecords;

    /** 结果消息（成功时为概要信息，失败时为错误原因） */
    private String message;

    /** 同步耗时（毫秒） */
    private long durationMs;

    // ============ Constructors ============

    public SyncResult() {
    }

    // ============ 静态工厂方法 ============

    public static SyncResult success(String brokerName, int totalRecords, long durationMs) {
        SyncResult result = new SyncResult();
        result.success = true;
        result.brokerName = brokerName;
        result.totalRecords = totalRecords;
        result.durationMs = durationMs;
        result.message = String.format("Sync completed: fetched %d order records from %s in %d ms",
                totalRecords, brokerName, durationMs);
        return result;
    }

    public static SyncResult failure(String brokerName, String errorMessage, long durationMs) {
        SyncResult result = new SyncResult();
        result.success = false;
        result.brokerName = brokerName;
        result.totalRecords = 0;
        result.durationMs = durationMs;
        result.message = String.format("Sync failed [%s]: %s (took %d ms)",
                brokerName, errorMessage, durationMs);
        return result;
    }

    // ============ Getters and Setters ============

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    @Override
    public String toString() {
        return "SyncResult{" +
                "success=" + success +
                ", brokerName='" + brokerName + '\'' +
                ", totalRecords=" + totalRecords +
                ", durationMs=" + durationMs +
                ", message='" + message + '\'' +
                '}';
    }
}
