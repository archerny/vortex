package com.vortex.sync.core;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Startup recovery runner for broker sync batches.
 *
 * On application startup, scans for any batches stuck in PROCESSING status
 * (which means the application crashed or was restarted during sync) and
 * marks them as INTERRUPTED. The phase field is preserved for diagnostics,
 * so operators can see at which stage the interruption occurred.
 *
 * Users can later trigger a resume via POST /api/broker-sync/batches/{id}/resume
 * to restart the sync from the beginning (complete re-run with idempotent staging).
 *
 * @see BrokerSyncBatchRepository#findByStatus(String)
 */
@Component
public class SyncBatchRecoveryRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SyncBatchRecoveryRunner.class);

    private final BrokerSyncBatchRepository batchRepository;

    public SyncBatchRecoveryRunner(BrokerSyncBatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<BrokerSyncBatch> stuckBatches = batchRepository.findByStatus("PROCESSING");

        if (stuckBatches.isEmpty()) {
            logger.info("[SyncRecovery] No stuck PROCESSING batches found on startup");
            return;
        }

        logger.warn("[SyncRecovery] Found {} batch(es) stuck in PROCESSING status, marking as INTERRUPTED",
                stuckBatches.size());

        for (BrokerSyncBatch batch : stuckBatches) {
            batch.setStatus("INTERRUPTED");
            batch.setErrorMessage("Interrupted: application restarted during sync");
            // phase is preserved for diagnostics — shows which stage was interrupted
            batchRepository.save(batch);
            logger.warn("[SyncRecovery] Batch {} marked as INTERRUPTED (was PROCESSING, phase={})",
                    batch.getId(), batch.getPhase());
        }
    }
}
