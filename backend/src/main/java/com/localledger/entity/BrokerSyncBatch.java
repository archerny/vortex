package com.localledger.entity;

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
     * 券商标识（如 ibkr、tiger）
     */
    @Column(name = "broker_name", nullable = false, length = 50)
    private String brokerName;

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
     * 批次状态：PENDING-待处理, IMPORTING-导入中, COMPLETED-已完成, FAILED-失败
     */
    @Column(name = "status", nullable = false, length = 32)
    private String status;

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

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
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

    @Override
    public String toString() {
        return "BrokerSyncBatch{" +
                "id=" + getId() +
                ", brokerName='" + brokerName + '\'' +
                ", syncDateFrom=" + syncDateFrom +
                ", syncDateTo=" + syncDateTo +
                ", totalCount=" + totalCount +
                ", importedCount=" + importedCount +
                ", skippedCount=" + skippedCount +
                ", failedCount=" + failedCount +
                ", status='" + status + '\'' +
                ", startedAt=" + startedAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
