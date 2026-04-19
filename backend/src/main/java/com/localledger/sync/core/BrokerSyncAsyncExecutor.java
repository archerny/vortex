package com.localledger.sync.core;

import com.localledger.service.BrokerSyncBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Async executor for broker sync tasks.
 *
 * This is a dedicated Spring bean that runs sync operations on the
 * {@code syncTaskExecutor} thread pool.  It is intentionally separated
 * from {@link BrokerSyncService} and the controller so that Spring's
 * AOP proxy can intercept the {@code @Async} annotation correctly.
 *
 * <h3>Exception safety</h3>
 * The {@link #execute} method guarantees that the batch record will
 * <strong>never</strong> be left in an intermediate state (e.g. PROCESSING
 * forever).  Any unhandled exception is caught and the batch is marked
 * as FAILED with the error message.
 *
 * <h3>Status lifecycle</h3>
 * PENDING → PROCESSING (phase=FETCHING) → adapter.sync() → COMPLETED / PARTIAL / FAILED
 */
@Service
public class BrokerSyncAsyncExecutor {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncAsyncExecutor.class);

    private final BrokerSyncService brokerSyncService;
    private final BrokerSyncBatchService batchService;

    public BrokerSyncAsyncExecutor(BrokerSyncService brokerSyncService,
                                   BrokerSyncBatchService batchService) {
        this.brokerSyncService = brokerSyncService;
        this.batchService = batchService;
    }

    /**
     * Execute the sync operation asynchronously.
     *
     * <p>Lifecycle:
     * <ol>
     *   <li>Mark batch as PROCESSING with phase=FETCHING</li>
     *   <li>Inject batchId into the request so adapters can reference the batch</li>
     *   <li>Delegate to {@link BrokerSyncService#sync(SyncRequest)}</li>
     *   <li>Mark batch as COMPLETED, PARTIAL, or FAILED based on the result</li>
     * </ol>
     *
     * @param batchId the persisted batch ID (must already exist in DB)
     * @param request the sync request parameters
     */
    @Async("syncTaskExecutor")
    public void execute(Long batchId, SyncRequest request) {
        logger.info("Async sync started: batchId={}, broker={}", batchId, request.getBrokerCode());

        try {
            // Step 1: Mark as PROCESSING (phase=FETCHING)
            batchService.markAsProcessing(batchId, "FETCHING");

            // Step 2: Inject batchId into request so adapters can reference the batch
            request.setBatchId(batchId);

            // Step 3: Execute the actual sync (may take seconds to minutes)
            SyncResult result = brokerSyncService.sync(request);

            // Step 4: Update batch based on result
            if (result.isSuccess()) {
                if (result.getFailedCount() > 0) {
                    // Some records failed → PARTIAL
                    batchService.markAsPartial(batchId, result);
                    logger.warn("Async sync partial: batchId={}, total={}, imported={}, failed={}",
                            batchId, result.getTotalRecords(), result.getImportedCount(), result.getFailedCount());
                } else {
                    batchService.markAsCompleted(batchId, result);
                    logger.info("Async sync completed: batchId={}, total={}, imported={}, skipped={}",
                            batchId, result.getTotalRecords(), result.getImportedCount(), result.getSkippedCount());
                }
            } else {
                batchService.markAsFailed(batchId, result.getMessage());
                logger.warn("Async sync failed: batchId={}, message={}",
                        batchId, result.getMessage());
            }
        } catch (Exception e) {
            // Catch-all: ensure batch is never stuck in PROCESSING
            logger.error("Unexpected error during async sync: batchId={}", batchId, e);
            try {
                batchService.markAsFailed(batchId,
                        "Unexpected error: " + e.getMessage());
            } catch (Exception ex) {
                // Even the fail-safe update failed — log and give up
                logger.error("Failed to mark batch {} as FAILED after exception", batchId, ex);
            }
        }
    }
}
