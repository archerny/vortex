package com.vortex.sync.core;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Startup recovery runner for broker sync batches (v2).
 *
 * <p>On application startup, scans for any batches left in a residual
 * <em>active</em> status — {@code PENDING} or {@code PROCESSING} — i.e. the
 * JVM crashed or was restarted mid-sync, and routes each one through
 * {@link SyncBatchFailureHandler#handleFailure}. The handler wipes whatever
 * staged rows / trade_records the interrupted batch had written and finalizes
 * the batch as {@code FAILED} (or {@code CLEANUP_FAILED} if cleanup itself
 * exhausts retries).</p>
 *
 * <p>Both states must be covered to avoid a deadlock with the DB-level
 * {@code uk_only_one_active} partial unique index:</p>
 * <ul>
 *   <li>{@code PROCESSING} — adapter started running and the crash happened
 *       during fetch/stage/import. Staged rows (and possibly some
 *       trade_records) may exist and must be cleaned up.</li>
 *   <li>{@code PENDING} — the narrow window between
 *       {@code BrokerSyncBatchService.createBatch} (which commits the row)
 *       and {@code BrokerSyncAsyncExecutor.markAsProcessing} (which happens
 *       on the @Async thread). If the JVM crashes here, a PENDING batch
 *       survives with no staged data. Without cleanup the index would
 *       permanently block every new sync request with HTTP 409.</li>
 * </ul>
 *
 * <p>v2 intentionally has no "resume" concept: after a cleanup the user
 * simply triggers a fresh sync. Because staging is idempotent and trade
 * record imports skip duplicates by business key, re-running the sync is
 * safe and converges to the same final state.</p>
 *
 * @see BrokerSyncBatchRepository#findByStatusIn(java.util.Collection)
 * @see SyncBatchFailureHandler
 */
@Component
public class SyncBatchRecoveryRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SyncBatchRecoveryRunner.class);

    /**
     * Residual active statuses that indicate a crashed sync. Must be kept in
     * sync with {@code BrokerSyncBatchService.ACTIVE_STATUSES} minus
     * {@code CLEANUP_FAILED} — the latter is an explicit terminal-ish state
     * that requires human intervention, not auto-recovery.
     */
    private static final List<String> RESIDUAL_ACTIVE_STATUSES = List.of("PENDING", "PROCESSING");

    private final BrokerSyncBatchRepository batchRepository;
    private final SyncBatchFailureHandler failureHandler;

    public SyncBatchRecoveryRunner(BrokerSyncBatchRepository batchRepository,
                                   SyncBatchFailureHandler failureHandler) {
        this.batchRepository = batchRepository;
        this.failureHandler = failureHandler;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<BrokerSyncBatch> stuckBatches = batchRepository.findByStatusIn(RESIDUAL_ACTIVE_STATUSES);

        if (stuckBatches.isEmpty()) {
            logger.info("[SyncRecovery] No residual active batches found on startup (scanned {})",
                    RESIDUAL_ACTIVE_STATUSES);
            return;
        }

        logger.warn("[SyncRecovery] Found {} batch(es) in residual active status {}; "
                + "routing through fail-fast cleanup", stuckBatches.size(), RESIDUAL_ACTIVE_STATUSES);

        for (BrokerSyncBatch batch : stuckBatches) {
            logger.warn("[SyncRecovery] Cleaning up batch {} (broker={}, status={}, phase={})",
                    batch.getId(), batch.getBrokerCode(), batch.getStatus(), batch.getPhase());
            // handleFailure swallows its own exceptions, so one bad batch cannot
            // block the application from starting.
            failureHandler.handleFailure(
                    batch.getId(),
                    batch.getBrokerCode(),
                    "Interrupted: application restarted during sync (previous status=" + batch.getStatus() + ")");
        }
    }
}

