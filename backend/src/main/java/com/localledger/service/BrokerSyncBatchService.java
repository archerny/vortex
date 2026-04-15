package com.localledger.service;

import com.localledger.entity.BrokerSyncBatch;
import com.localledger.repository.BrokerSyncBatchRepository;
import com.localledger.sync.core.SyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 券商同步批次业务逻辑服务
 *
 * 提供同步批次记录的查询功能，支持按券商名称和状态筛选。
 */
@Service
@Transactional(readOnly = true)
public class BrokerSyncBatchService {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncBatchService.class);

    private final BrokerSyncBatchRepository batchRepository;

    public BrokerSyncBatchService(BrokerSyncBatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    /**
     * List all sync batches, optionally filtered by broker name and/or status.
     *
     * @param brokerName optional broker name filter
     * @param status     optional status filter
     * @return list of matching batches, ordered by startedAt descending
     */
    public List<BrokerSyncBatch> listBatches(String brokerName, String status) {
        boolean hasBroker = brokerName != null && !brokerName.isBlank();
        boolean hasStatus = status != null && !status.isBlank();

        if (hasBroker && hasStatus) {
            logger.debug("Querying sync batches with brokerName={} and status={}", brokerName, status);
            return batchRepository.findByBrokerNameAndStatusOrderByStartedAtDesc(brokerName, status);
        } else if (hasBroker) {
            logger.debug("Querying sync batches with brokerName={}", brokerName);
            return batchRepository.findByBrokerNameOrderByStartedAtDesc(brokerName);
        } else if (hasStatus) {
            logger.debug("Querying sync batches with status={}", status);
            return batchRepository.findByStatusOrderByStartedAtDesc(status);
        } else {
            logger.debug("Querying all sync batches");
            return batchRepository.findAllByOrderByStartedAtDesc();
        }
    }

    /**
     * 根据ID查询单个同步批次
     *
     * @param id 批次ID
     * @return 批次（如存在）
     */
    public Optional<BrokerSyncBatch> findById(Long id) {
        return batchRepository.findById(id);
    }

    /**
     * Create a new sync batch record with PENDING status.
     *
     * @param brokerName   broker identifier
     * @param syncDateFrom start date of sync range (nullable, defaults to today)
     * @param syncDateTo   end date of sync range (nullable, defaults to today)
     * @return persisted batch entity
     */
    @Transactional
    public BrokerSyncBatch createBatch(String brokerName, LocalDate syncDateFrom, LocalDate syncDateTo) {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setBrokerName(brokerName);
        batch.setSyncDateFrom(syncDateFrom != null ? syncDateFrom : LocalDate.now());
        batch.setSyncDateTo(syncDateTo != null ? syncDateTo : LocalDate.now());
        batch.setStatus("PENDING");
        batch.setTotalCount(0);
        batch.setImportedCount(0);
        batch.setSkippedCount(0);
        batch.setFailedCount(0);

        BrokerSyncBatch saved = batchRepository.save(batch);
        logger.info("Created sync batch: id={}, broker={}, dateRange=[{} ~ {}]",
                saved.getId(), brokerName, saved.getSyncDateFrom(), saved.getSyncDateTo());
        return saved;
    }

    /**
     * Save (update) an existing batch record.
     *
     * @param batch the batch entity to update
     * @return the updated batch
     */
    @Transactional
    public BrokerSyncBatch save(BrokerSyncBatch batch) {
        return batchRepository.save(batch);
    }

    // ============ Async lifecycle: status transition methods ============
    // Each method runs in its own transaction so that state changes
    // survive even if the subsequent step fails.

    /**
     * Transition a batch to IMPORTING status.
     *
     * @param batchId the batch ID
     */
    @Transactional
    public void markAsImporting(Long batchId) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setStatus("IMPORTING");
        batch.setStartedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.info("Batch {} status changed to IMPORTING", batchId);
    }

    /**
     * Transition a batch to COMPLETED status and populate result counts.
     *
     * @param batchId the batch ID
     * @param result  the sync result containing record counts
     */
    @Transactional
    public void markAsCompleted(Long batchId, SyncResult result) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setStatus("COMPLETED");
        batch.setTotalCount(result.getTotalRecords());
        batch.setCompletedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.info("Batch {} status changed to COMPLETED, totalRecords={}", batchId, result.getTotalRecords());
    }

    /**
     * Transition a batch to FAILED status with an error message.
     *
     * @param batchId      the batch ID
     * @param errorMessage description of the failure
     */
    @Transactional
    public void markAsFailed(Long batchId, String errorMessage) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setStatus("FAILED");
        batch.setErrorMessage(errorMessage);
        batch.setCompletedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.warn("Batch {} status changed to FAILED: {}", batchId, errorMessage);
    }
}
