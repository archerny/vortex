package com.vortex.service;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import com.vortex.sync.core.SyncResult;
import com.vortex.sync.exception.SyncConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 券商同步批次业务逻辑服务
 *
 * 提供同步批次记录的创建、状态流转和查询功能。
 */
@Service
@Transactional(readOnly = true)
public class BrokerSyncBatchService {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncBatchService.class);

    /**
     * Statuses that count as "active" for the purpose of the v2 conflict check.
     * Matches the {@code WHERE} clause of the partial unique index
     * {@code uk_only_one_active} (Flyway V28). CLEANUP_FAILED is included on
     * purpose: it is a protective terminal state that must be manually
     * resolved before new syncs are allowed.
     */
    static final Set<String> ACTIVE_STATUSES =
            Set.of("PENDING", "PROCESSING", "CLEANUP_FAILED");

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
     * <p><b>v2 conflict guard:</b> if there is already an active batch in
     * {@code PENDING} / {@code PROCESSING} / {@code CLEANUP_FAILED}, this
     * method throws a {@link SyncConflictException} carrying the conflicting
     * batch's ID and status. Controllers map that exception to
     * {@code HTTP 409 Conflict}. The application-layer check is a fast-path;
     * the partial unique index {@code uk_only_one_active} (Flyway V28) is the
     * authoritative guard and will catch any race that slips past it —
     * {@link DataIntegrityViolationException} from the DB is likewise
     * converted to {@link SyncConflictException}.</p>
     *
     * @param brokerCode   broker technical identifier
     * @param syncDateFrom start date of sync range (nullable, defaults to today)
     * @param syncDateTo   end date of sync range (nullable, defaults to today)
     * @return persisted batch entity
     * @throws SyncConflictException when another batch is active
     */
    @Transactional
    public BrokerSyncBatch createBatch(String brokerCode, LocalDate syncDateFrom, LocalDate syncDateTo) {
        // Fast-path: surface the conflicting batch's ID/status if any.
        batchRepository.findFirstByStatusInOrderByIdDesc(ACTIVE_STATUSES)
                .ifPresent(active -> {
                    throw new SyncConflictException(
                            String.format(
                                    "Cannot start a new sync: batch %d is %s. "
                                            + "Wait for it to finish or resolve it before retrying.",
                                    active.getId(), active.getStatus()),
                            active.getId(),
                            active.getStatus());
                });

        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setBrokerCode(brokerCode);
        batch.setSyncDateFrom(syncDateFrom != null ? syncDateFrom : LocalDate.now());
        batch.setSyncDateTo(syncDateTo != null ? syncDateTo : LocalDate.now());
        batch.setStatus("PENDING");
        batch.setTotalCount(0);
        batch.setImportedCount(0);
        batch.setSkippedCount(0);

        BrokerSyncBatch saved;
        try {
            saved = batchRepository.save(batch);
        } catch (DataIntegrityViolationException e) {
            // Race: between the fast-path check above and the INSERT above,
            // another request won the partial unique index `uk_only_one_active`.
            // Translate to 409 so callers get a consistent signal.
            logger.warn("createBatch hit uk_only_one_active; converting to SyncConflictException", e);
            throw new SyncConflictException(
                    "Cannot start a new sync: another sync is already active. "
                            + "Wait for it to finish or resolve it before retrying.",
                    null,
                    null,
                    e);
        }
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
     * Transition a batch to CLEANUP_FAILED status after repeated cleanup
     * attempts have exhausted. This is v2's protective terminal state:
     * something in {@code trade_records} or a staged table may still be
     * referencing this batch, so the partial unique index
     * {@code uk_only_one_active} keeps this batch "active" and refuses new
     * sync requests until an operator manually resolves it.
     *
     * <p>Unlike {@link #markAsFailed(Long, String)}, this method <b>preserves
     * {@code phase}</b> so the diagnostic UI can show where the failed sync
     * was when cleanup went wrong.</p>
     *
     * @param batchId      the batch ID
     * @param errorMessage description of the cleanup failure (should also
     *                     reference the original sync error)
     */
    @Transactional
    public void markAsCleanupFailed(Long batchId, String errorMessage) {
        BrokerSyncBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found: " + batchId));
        batch.setStatus("CLEANUP_FAILED");
        // phase intentionally preserved for diagnostics
        batch.setErrorMessage(errorMessage);
        batch.setCompletedAt(LocalDateTime.now());
        batchRepository.save(batch);
        logger.error("Batch {} status changed to CLEANUP_FAILED (phase={}): {}",
                batchId, batch.getPhase(), errorMessage);
    }
}
