package com.vortex.sync.core;

import com.vortex.service.BrokerSyncBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Async executor for broker sync tasks.
 *
 * <p>This is a dedicated Spring bean that runs sync operations on the
 * {@code syncTaskExecutor} thread pool. It is intentionally separated
 * from {@link BrokerSyncService} and the controller so that Spring's
 * AOP proxy can intercept the {@code @Async} annotation correctly.</p>
 *
 * <h3>Exception safety</h3>
 * <p>The {@link #execute} method guarantees the batch record will
 * <strong>never</strong> be left in an intermediate state (e.g. stuck in
 * {@code PROCESSING}). Any failure — whether an adapter-level failure
 * result, an unhandled exception, or the v2 per-record fail-fast escalation
 * — is funneled through {@link SyncBatchFailureHandler#handleFailure},
 * which performs cleanup + status finalization in a single place.</p>
 *
 * <h3>Status lifecycle (v2)</h3>
 * <pre>
 *                       success
 *                     ┌────────→  COMPLETED
 *   PENDING → PROCESSING
 *                     └────────→  cleanup → FAILED
 *                       failure              │
 *                                            └────→  CLEANUP_FAILED
 *                                  (if cleanup itself exhausts retries)
 * </pre>
 *
 * <p>v2 has no PARTIAL / INTERRUPTED states: a success result always becomes
 * {@code COMPLETED}, and any per-record failure escalates to whole-batch
 * cleanup before the batch is marked terminal.</p>
 */
@Service
public class BrokerSyncAsyncExecutor {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncAsyncExecutor.class);

    private final BrokerSyncService brokerSyncService;
    private final BrokerSyncBatchService batchService;
    private final SyncBatchFailureHandler failureHandler;

    public BrokerSyncAsyncExecutor(BrokerSyncService brokerSyncService,
                                   BrokerSyncBatchService batchService,
                                   SyncBatchFailureHandler failureHandler) {
        this.brokerSyncService = brokerSyncService;
        this.batchService = batchService;
        this.failureHandler = failureHandler;
    }

    /**
     * Execute the sync operation asynchronously.
     *
     * <p>Lifecycle:
     * <ol>
     *   <li>Mark batch as PROCESSING with phase=FETCHING</li>
     *   <li>Inject batchId into the request so adapters can reference the batch</li>
     *   <li>Delegate to {@link BrokerSyncService#sync(SyncRequest)}</li>
     *   <li>On success → {@code markAsCompleted}.
     *       On failure result or exception → {@link SyncBatchFailureHandler#handleFailure},
     *       which cleans up staged / trade_records rows and flips the batch to
     *       {@code FAILED} (or {@code CLEANUP_FAILED} if cleanup exhausts retries).</li>
     * </ol>
     *
     * @param batchId the persisted batch ID (must already exist in DB)
     * @param request the sync request parameters
     */
    @Async("syncTaskExecutor")
    public void execute(Long batchId, SyncRequest request) {
        String brokerCode = request.getBrokerCode();
        logger.info("Async sync started: batchId={}, broker={}", batchId, brokerCode);

        try {
            // Step 1: Mark as PROCESSING (phase=FETCHING). If this fails the batch
            // doesn't exist or DB is down — fall through to catch, which will try
            // cleanup; cleanup on a non-existent batch is safe (no-op deletes).
            batchService.markAsProcessing(batchId, "FETCHING");

            // Step 2: Inject batchId into request so adapters can reference the batch
            request.setBatchId(batchId);

            // Step 3: Execute the actual sync (may take seconds to minutes)
            SyncResult result = brokerSyncService.sync(request);

            // Step 4: Dispatch on result.
            //   v2: success → COMPLETED (no PARTIAL).
            //       failure → fail-fast cleanup + FAILED via failureHandler.
            if (result.isSuccess()) {
                batchService.markAsCompleted(batchId, result);
                logger.info("Async sync completed: batchId={}, total={}, imported={}, skipped={}",
                        batchId, result.getTotalRecords(), result.getImportedCount(), result.getSkippedCount());
            } else {
                logger.warn("Async sync returned failure: batchId={}, message={}",
                        batchId, result.getMessage());
                failureHandler.handleFailure(batchId, brokerCode, result.getMessage());
            }
        } catch (Exception e) {
            // Catch-all: ensure batch is never stuck in PROCESSING. The failure
            // handler is responsible for cleaning up staged rows / trade_records
            // and transitioning to FAILED (or CLEANUP_FAILED on cleanup failure).
            logger.error("Unexpected error during async sync: batchId={}", batchId, e);
            try {
                failureHandler.handleFailure(batchId, brokerCode,
                        "Unexpected error: " + e.getMessage());
            } catch (Exception ex) {
                // handleFailure swallows its own second-order exceptions, so reaching
                // this catch would indicate a programming error — log loudly.
                logger.error("Failure handler itself threw for batch {}; "
                        + "batch may be stuck in PROCESSING", batchId, ex);
            }
        }
    }
}
