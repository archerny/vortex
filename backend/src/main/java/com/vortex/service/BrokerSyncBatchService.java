package com.vortex.service;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import com.vortex.sync.core.SyncResult;
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
 * 提供同步批次记录的创建、状态流转和查询功能。
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
     * List all sync batches, optionally filtered by broker code and/or status.
     *
     * @param brokerCode optional broker code filter
     * @param status     optional status filter
     * @return list of matching batches, ordered by startedAt descending
     */
    public List<BrokerSyncBatch> listBatches(String brokerCode, String status) {
        boolean hasBroker = brokerCode != null && !brokerCode.isBlank();
        boolean hasStatus = status != null && !status.isBlank();

        if (hasBroker && hasStatus) {
            logger.debug("Querying sync batches with brokerCode={} and status={}", brokerCode, status);
            return batchRepository.findByBrokerCodeAndStatusOrderByStartedAtDesc(brokerCode, status);
        } else if (hasBroker) {
            logger.debug("Querying sync batches with brokerCode={}", brokerCode);
            return batchRepository.findByBrokerCodeOrderByStartedAtDesc(brokerCode);
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
     */
    public Optional<BrokerSyncBatch> findById(Long id) {
        return batchRepository.findById(id);
    }

    /**
     * Create a new sync batch record with PENDING status.
     *
     * @param brokerCode   broker technical identifier
     * @param syncDateFrom start date of sync range (nullable, defaults to today)
     * @param syncDateTo   end date of sync range (nullable, defaults to today)
     * @return persisted batch entity
     */
    @Transactional
    public BrokerSyncBatch createBatch(String brokerCode, LocalDate syncDateFrom, LocalDate syncDateTo) {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setBrokerCode(brokerCode);
        batch.setSyncDateFrom(syncDateFrom != null ? syncDateFrom : LocalDate.now());
        batch.setSyncDateTo(syncDateTo != null ? syncDateTo : LocalDate.now());
        batch.setStatus("PENDING");
        batch.setTotalCount(0);
        batch.setImportedCount(0);
        batch.setSkippedCount(0);

        BrokerSyncBatch saved = batchRepository.save(batch);
        logger.info("Created sync batch: id={}, broker={}, dateRange=[{} ~ {}]",
                saved.getId(), brokerCode, saved.getSyncDateFrom(), saved.getSyncDateTo());
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

    /**
     * Transition a batch to PROCESSING status with a specific phase.
     *
     * @param batchId the batch ID
     * @param phase   the initial phase (FETCHING, STAGING, or IMPORTING)
     */
    @Transactional
    public void markAsProcessing(Long batchId, String phase) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setStatus("PROCESSING");
        batch.setPhase(phase);
        batch.setStartedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.info("Batch {} status changed to PROCESSING (phase={})", batchId, phase);
    }

    /**
     * Update the phase within PROCESSING status.
     *
     * @param batchId the batch ID
     * @param phase   the new phase (FETCHING, STAGING, or IMPORTING)
     */
    @Transactional
    public void updatePhase(Long batchId, String phase) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setPhase(phase);
        batchRepository.save(batch);
        logger.debug("Batch {} phase updated to {}", batchId, phase);
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
        batch.setPhase(null);
        batch.setTotalCount(result.getTotalRecords());
        batch.setImportedCount(result.getImportedCount());
        batch.setSkippedCount(result.getSkippedCount());
        batch.setCompletedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.info("Batch {} status changed to COMPLETED, total={}, imported={}, skipped={}",
                batchId, result.getTotalRecords(), result.getImportedCount(),
                result.getSkippedCount());
    }

    /**
     * Transition a batch to PARTIAL status (some records imported, some failed).
     *
     * @param batchId the batch ID
     * @param result  the sync result containing record counts
     * @deprecated v2 has no PARTIAL state. This method is retained only as a
     *             bridge so that phase-1b compiles without removing the call
     *             site in {@link com.vortex.sync.core.BrokerSyncAsyncExecutor}.
     *             Phase 3 will delete this method along with the
     *             {@code markAsInterrupted} / resume code paths.
     */
    @Deprecated
    @Transactional
    public void markAsPartial(Long batchId, SyncResult result) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setStatus("PARTIAL");
        batch.setPhase(null);
        batch.setTotalCount(result.getTotalRecords());
        batch.setImportedCount(result.getImportedCount());
        batch.setSkippedCount(result.getSkippedCount());
        batch.setCompletedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.warn("Batch {} status changed to PARTIAL, total={}, imported={}, skipped={}",
                batchId, result.getTotalRecords(), result.getImportedCount(),
                result.getSkippedCount());
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
        batch.setPhase(null);
        batch.setErrorMessage(errorMessage);
        batch.setCompletedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.warn("Batch {} status changed to FAILED: {}", batchId, errorMessage);
    }

    /**
     * Transition a batch to INTERRUPTED status (preserving phase for diagnostics).
     *
     * @param batchId      the batch ID
     * @param errorMessage description of the interruption
     * @deprecated v2 removed the INTERRUPTED state. This method is retained as
     *             a bridge until phase 3 rewires {@code SyncBatchRecoveryRunner}
     *             to use the fail-fast cleanup path.
     */
    @Deprecated
    @Transactional
    public void markAsInterrupted(Long batchId, String errorMessage) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setStatus("INTERRUPTED");
        // phase preserved for diagnostics
        batch.setErrorMessage(errorMessage);
        batchRepository.save(batch);
        logger.warn("Batch {} status changed to INTERRUPTED (phase={}): {}",
                batchId, batch.getPhase(), errorMessage);
    }
}
