package com.vortex.sync.core;

import com.vortex.service.BrokerSyncBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Unified failure-handling entry point for sync batches (v2).
 *
 * <p>When a sync batch fails — whether during fetch, staging, import, or
 * during startup recovery — this handler is responsible for:
 * <ol>
 *   <li>Cleaning up every row the batch wrote, via
 *       {@link SyncBatchCleanupService#cleanupBatchData(Long, String)}.</li>
 *   <li>If cleanup succeeds, transitioning the batch to {@code FAILED} with
 *       the original error message.</li>
 *   <li>If cleanup fails repeatedly, transitioning the batch to
 *       {@code CLEANUP_FAILED} — a terminal, protective state that blocks
 *       all subsequent sync requests (via the partial unique index
 *       {@code uk_only_one_active}) until an operator resolves it.</li>
 * </ol>
 *
 * <p>Cleanup is retried up to {@link #MAX_CLEANUP_ATTEMPTS} times with a
 * fixed {@link #CLEANUP_RETRY_BACKOFF_MS} pause between attempts, so that
 * transient DB issues (brief pool exhaustion, short-lived lock conflicts,
 * serialization failures) can clear before we escalate.</p>
 *
 * <p>See {@code docs/broker-sync/framework/import-consistency.md § 5.4}.</p>
 */
@Service
public class SyncBatchFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(SyncBatchFailureHandler.class);

    /** Number of times to attempt cleanup before giving up and escalating to CLEANUP_FAILED. */
    static final int MAX_CLEANUP_ATTEMPTS = 3;

    /**
     * Fixed backoff between cleanup retry attempts, in milliseconds.
     *
     * <p>Rationale: cleanup is a handful of DB-local DELETEs and normally
     * completes in tens of milliseconds. The failure modes we want to absorb
     * here are <em>transient</em>: a brief connection-pool exhaustion, a
     * short-lived lock conflict, or a Postgres serialization failure that
     * resolves within a second or two. A fixed 2s pause is long enough to
     * let those clear without making the worst-case failure path feel slow
     * (worst case: 2 sleeps = 4s before escalating to {@code CLEANUP_FAILED}).
     * Exponential backoff would only help for remote-cascade scenarios that
     * do not apply to a local DB; fixed spacing is simpler and more
     * predictable in logs.
     *
     * <p>Package-private and non-final so tests can shorten it to 0 without
     * paying real wall-clock time for retry scenarios. Production code must
     * not mutate this.
     */
    static long CLEANUP_RETRY_BACKOFF_MS = 2000L;

    private final SyncBatchCleanupService cleanupService;
    private final BrokerSyncBatchService batchService;

    public SyncBatchFailureHandler(SyncBatchCleanupService cleanupService,
                                   BrokerSyncBatchService batchService) {
        this.cleanupService = cleanupService;
        this.batchService = batchService;
    }

    /**
     * Run cleanup with retries, then mark the batch terminal.
     *
     * <p>Behavior:
     * <ul>
     *   <li>On any successful cleanup attempt: batch → {@code FAILED} with
     *       {@code errorMessage = originalError}. Returns normally.</li>
     *   <li>After {@link #MAX_CLEANUP_ATTEMPTS} failed cleanup attempts:
     *       batch → {@code CLEANUP_FAILED} with an {@code errorMessage} that
     *       mentions both the cleanup failure and the original error.
     *       Returns normally (does not re-throw).</li>
     *   <li>If the final status update itself throws (e.g. DB down), the
     *       exception is logged and swallowed — the caller (typically
     *       {@code BrokerSyncAsyncExecutor}) must never see a second-order
     *       exception here, as it has no recovery path.</li>
     * </ul>
     *
     * @param batchId       the failing batch's ID (must exist in DB)
     * @param brokerCode    the broker for this batch (used for cleanup dispatch)
     * @param originalError short description of the original sync failure;
     *                      preserved in the final {@code error_message}
     */
    public void handleFailure(Long batchId, String brokerCode, String originalError) {
        Exception lastCleanupException = null;

        for (int attempt = 1; attempt <= MAX_CLEANUP_ATTEMPTS; attempt++) {
            try {
                cleanupService.cleanupBatchData(batchId, brokerCode);
                // Cleanup succeeded — mark FAILED and return
                safelyMarkAsFailed(batchId, originalError, attempt);
                return;
            } catch (Exception e) {
                lastCleanupException = e;
                logger.error("Cleanup attempt {}/{} failed for batch {} ({}): {}",
                        attempt, MAX_CLEANUP_ATTEMPTS, batchId, brokerCode, e.getMessage(), e);
            }

            // Sleep between attempts (but not after the last one) so that
            // transient DB issues have a chance to clear. If the worker
            // thread is interrupted (e.g. during shutdown), stop retrying
            // immediately and escalate to CLEANUP_FAILED — continuing to
            // sleep would violate graceful-shutdown semantics.
            if (attempt < MAX_CLEANUP_ATTEMPTS) {
                try {
                    Thread.sleep(CLEANUP_RETRY_BACKOFF_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.warn("Cleanup retry for batch {} interrupted; "
                            + "skipping remaining attempts and escalating to CLEANUP_FAILED",
                            batchId);
                    break;
                }
            }
        }

        // All attempts failed (or we were interrupted) → CLEANUP_FAILED
        String cleanupMessage = lastCleanupException != null
                ? lastCleanupException.getMessage()
                : "unknown";
        String combinedMessage = String.format(
                "Cleanup failed after %d attempts: %s. Original error: %s",
                MAX_CLEANUP_ATTEMPTS, cleanupMessage, originalError);
        safelyMarkAsCleanupFailed(batchId, combinedMessage);
    }

    private void safelyMarkAsFailed(Long batchId, String errorMessage, int cleanupAttempt) {
        try {
            batchService.markAsFailed(batchId, errorMessage);
            logger.info("Batch {} cleaned up and marked FAILED (cleanup attempt {})",
                    batchId, cleanupAttempt);
        } catch (Exception e) {
            // Second-order failure: cleanup worked but status update failed.
            // We cannot recover from here; log loudly and move on.
            logger.error("Batch {}: cleanup succeeded but markAsFailed threw; "
                    + "batch may be stuck in PROCESSING", batchId, e);
        }
    }

    private void safelyMarkAsCleanupFailed(Long batchId, String combinedMessage) {
        try {
            batchService.markAsCleanupFailed(batchId, combinedMessage);
        } catch (Exception e) {
            logger.error("Batch {}: failed to mark as CLEANUP_FAILED; "
                    + "batch may be stuck in PROCESSING", batchId, e);
        }
    }
}
