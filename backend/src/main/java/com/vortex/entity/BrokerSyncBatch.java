package com.vortex.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 券商同步批次实体类
 *
 * 记录每次券商数据同步操作的元信息，包括同步日期范围、记录计数、
 * 状态和时间信息。所有券商共用同一张表。
 *
 * 对应数据库 broker_sync_batches 表
 */
@Entity
@Table(name = "broker_sync_batches")
public class BrokerSyncBatch extends BaseEntity {

    /**
     * 券商技术标识（如 ibkr、tiger）
     */
    @Column(name = "broker_code", nullable = false, length = 50)
    private String brokerCode;

    /**
     * 同步数据的起始日期
     */
    @Column(name = "sync_date_from", nullable = false)
    private LocalDate syncDateFrom;

    /**
     * 同步数据的结束日期
     */
    @Column(name = "sync_date_to", nullable = false)
    private LocalDate syncDateTo;

    /**
     * 同步记录总数
     */
    @Column(name = "total_count", nullable = false)
    private Integer totalCount = 0;

    /**
     * 已导入正式表的数量
     */
    @Column(name = "imported_count", nullable = false)
    private Integer importedCount = 0;

    /**
     * 跳过的数量（重复记录等）
     */
    @Column(name = "skipped_count", nullable = false)
    private Integer skippedCount = 0;

    /**
     * 失败的数量
     */
    @Column(name = "failed_count", nullable = false)
    private Integer failedCount = 0;

    /**
     * 批次主状态：PENDING, PROCESSING, COMPLETED, PARTIAL, FAILED, INTERRUPTED
     */
    @Column(name = "status", nullable = false, length = 32)
    private String status;

    /**
     * 子阶段：FETCHING, STAGING, IMPORTING。仅 PROCESSING 时有意义。
     * INTERRUPTED 时保留中断时的值用于诊断。
     */
    @Column(name = "phase", length = 32)
    private String phase;

    /**
     * 批次级错误信息
     */
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    /**
     * 同步开始时间
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /**
     * 同步完成时间
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // ============ Constructors ============

    public BrokerSyncBatch() {
    }

    // ============ Getters and Setters ============

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public LocalDate getSyncDateFrom() {
        return syncDateFrom;
    }

    public void setSyncDateFrom(LocalDate syncDateFrom) {
        this.syncDateFrom = syncDateFrom;
    }

    public LocalDate getSyncDateTo() {
        return syncDateTo;
    }

    public void setSyncDateTo(LocalDate syncDateTo) {
        this.syncDateTo = syncDateTo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getImportedCount() {
        return importedCount;
    }

    public void setImportedCount(Integer importedCount) {
        this.importedCount = importedCount;
    }

    public Integer getSkippedCount() {
        return skippedCount;
    }

    public void setSkippedCount(Integer skippedCount) {
        this.skippedCount = skippedCount;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "BrokerSyncBatch{" +
                "id=" + getId() +
                ", brokerCode='" + brokerCode + '\'' +
                ", syncDateFrom=" + syncDateFrom +
                ", syncDateTo=" + syncDateTo +
                ", totalCount=" + totalCount +
                ", importedCount=" + importedCount +
                ", skippedCount=" + skippedCount +
                ", failedCount=" + failedCount +
                ", status='" + status + '\'' +
                ", phase='" + phase + '\'' +
                ", startedAt=" + startedAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
