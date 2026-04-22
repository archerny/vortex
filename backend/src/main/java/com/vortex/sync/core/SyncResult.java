package com.vortex.sync.core;

/**
 * 同步结果
 *
 * 封装一次同步操作的执行结果信息。
 *
 * <p><b>v2 note:</b> {@code failedCount} is intentionally absent. v2 uses a
 * fail-fast model — any per-record failure triggers whole-batch cleanup and
 * transitions the batch straight to FAILED, so there is no "partial failure
 * count" to surface. Adapters either return {@code success(...)} with
 * imported+skipped counts, or {@code failure(...)}.</p>
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
                                     int skippedCount, long durationMs) {
        SyncResult result = new SyncResult();
        result.success = true;
        result.brokerCode = brokerCode;
        result.totalRecords = totalRecords;
        result.importedCount = importedCount;
        result.skippedCount = skippedCount;
        result.durationMs = durationMs;
        result.message = String.format(
                "Sync completed [%s]: total=%d, imported=%d, skipped=%d in %d ms",
                brokerCode, totalRecords, importedCount, skippedCount, durationMs);
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
                ", durationMs=" + durationMs +
                ", message='" + message + '\'' +
                '}';
    }
}
