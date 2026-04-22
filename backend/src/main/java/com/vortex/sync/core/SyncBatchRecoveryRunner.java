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
 * <p>On application startup, scans for any batches stuck in {@code PROCESSING}
 * status — i.e. the JVM crashed or was restarted mid-sync — and routes each
 * one through {@link SyncBatchFailureHandler#handleFailure}. The handler
 * wipes whatever staged rows / trade_records the interrupted batch had
 * written and finalizes the batch as {@code FAILED} (or
 * {@code CLEANUP_FAILED} if cleanup itself exhausts retries).</p>
 *
 * <p>v2 intentionally has no "resume" concept: after a cleanup the user
 * simply triggers a fresh sync. Because staging is idempotent and trade
 * record imports skip duplicates by business key, re-running the sync is
 * safe and converges to the same final state.</p>
 *
 * @see BrokerSyncBatchRepository#findByStatus(String)
 * @see SyncBatchFailureHandler
 */
@Component
public class SyncBatchRecoveryRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SyncBatchRecoveryRunner.class);

    private final BrokerSyncBatchRepository batchRepository;
    private final SyncBatchFailureHandler failureHandler;

    public SyncBatchRecoveryRunner(BrokerSyncBatchRepository batchRepository,
                                   SyncBatchFailureHandler failureHandler) {
        this.batchRepository = batchRepository;
        this.failureHandler = failureHandler;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<BrokerSyncBatch> stuckBatches = batchRepository.findByStatus("PROCESSING");

        if (stuckBatches.isEmpty()) {
            logger.info("[SyncRecovery] No stuck PROCESSING batches found on startup");
            return;
        }

        logger.warn("[SyncRecovery] Found {} batch(es) stuck in PROCESSING status; "
                + "routing through fail-fast cleanup", stuckBatches.size());

        for (BrokerSyncBatch batch : stuckBatches) {
            logger.warn("[SyncRecovery] Cleaning up batch {} (broker={}, phase={})",
                    batch.getId(), batch.getBrokerCode(), batch.getPhase());
            // handleFailure swallows its own exceptions, so one bad batch cannot
            // block the application from starting.
            failureHandler.handleFailure(
                    batch.getId(),
                    batch.getBrokerCode(),
                    "Interrupted: application restarted during sync");
        }
    }
}
