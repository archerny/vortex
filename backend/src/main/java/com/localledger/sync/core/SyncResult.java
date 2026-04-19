package com.localledger.sync.core;

/**
 * 同步结果
 *
 * 封装一次同步操作的执行结果信息。
 */
public class SyncResult {

    /** 是否执行成功 */
    private boolean success;

    /** 券商技术标识符 */
    private String brokerCode;

    /** 获取到的订单记录数 */
    private int totalRecords;

    /** 已导入正式表的数量 */
    private int importedCount;

    /** 跳过的数量（重复记录等） */
    private int skippedCount;

    /** 失败的数量 */
    private int failedCount;

    /** 结果消息（成功时为概要信息，失败时为错误原因） */
    private String message;

    /** 同步耗时（毫秒） */
    private long durationMs;

    // ============ Constructors ============

    public SyncResult() {
    }

    // ============ 静态工厂方法 ============

    public static SyncResult success(String brokerCode, int totalRecords, long durationMs) {
        SyncResult result = new SyncResult();
        result.success = true;
        result.brokerCode = brokerCode;
        result.totalRecords = totalRecords;
        result.durationMs = durationMs;
        result.message = String.format("Sync completed: fetched %d order records from %s in %d ms",
                totalRecords, brokerCode, durationMs);
        return result;
    }

    public static SyncResult success(String brokerCode, int totalRecords, int importedCount,
                                     int skippedCount, int failedCount, long durationMs) {
        SyncResult result = new SyncResult();
        result.success = true;
        result.brokerCode = brokerCode;
        result.totalRecords = totalRecords;
        result.importedCount = importedCount;
        result.skippedCount = skippedCount;
        result.failedCount = failedCount;
        result.durationMs = durationMs;
        result.message = String.format(
                "Sync completed [%s]: total=%d, imported=%d, skipped=%d, failed=%d in %d ms",
                brokerCode, totalRecords, importedCount, skippedCount, failedCount, durationMs);
        return result;
    }

    public static SyncResult failure(String brokerCode, String errorMessage, long durationMs) {
        SyncResult result = new SyncResult();
        result.success = false;
        result.brokerCode = brokerCode;
        result.totalRecords = 0;
        result.durationMs = durationMs;
        result.message = String.format("Sync failed [%s]: %s (took %d ms)",
                brokerCode, errorMessage, durationMs);
        return result;
    }

    // ============ Getters and Setters ============

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getImportedCount() {
        return importedCount;
    }

    public void setImportedCount(int importedCount) {
        this.importedCount = importedCount;
    }

    public int getSkippedCount() {
        return skippedCount;
    }

    public void setSkippedCount(int skippedCount) {
        this.skippedCount = skippedCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
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
                ", brokerCode='" + brokerCode + '\'' +
                ", totalRecords=" + totalRecords +
                ", importedCount=" + importedCount +
                ", skippedCount=" + skippedCount +
                ", failedCount=" + failedCount +
                ", durationMs=" + durationMs +
                ", message='" + message + '\'' +
                '}';
    }
}
