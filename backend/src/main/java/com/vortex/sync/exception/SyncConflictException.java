package com.vortex.sync.exception;

/**
 * Thrown when a new sync request cannot proceed because another sync batch
 * is currently active (PENDING / PROCESSING) or is blocked in CLEANUP_FAILED
 * state.
 *
 * <p>This exception corresponds to the DB-level guard provided by the partial
 * unique index {@code uk_only_one_active} (see Flyway V28 and
 * {@code docs/broker-sync/framework/import-consistency.md § 4}). Controllers
 * should translate this into an HTTP {@code 409 Conflict} response; the
 * conflicting {@link #getConflictingBatchId()} and
 * {@link #getConflictingStatus()} are exposed so the frontend can render the
 * correct guidance (retry vs. resolve CLEANUP_FAILED).</p>
 *
 * <p>A {@code CLEANUP_FAILED} batch must be resolved manually (e.g. by
 * flipping its status to {@code FAILED} after the operator has verified that
 * any leftover data has been cleaned) before the system will accept a new
 * sync request.</p>
 */
public class SyncConflictException extends RuntimeException {

    private final Long conflictingBatchId;
    private final String conflictingStatus;

    public SyncConflictException(String message,
                                 Long conflictingBatchId,
                                 String conflictingStatus) {
        super(message);
        this.conflictingBatchId = conflictingBatchId;
        this.conflictingStatus = conflictingStatus;
    }

    public SyncConflictException(String message,
                                 Long conflictingBatchId,
                                 String conflictingStatus,
                                 Throwable cause) {
        super(message, cause);
        this.conflictingBatchId = conflictingBatchId;
        this.conflictingStatus = conflictingStatus;
    }

    /**
     * @return the ID of the batch currently blocking new sync requests, or
     *         {@code null} if the conflict was detected via the DB unique
     *         index and the blocking batch's ID could not be determined.
     */
    public Long getConflictingBatchId() {
        return conflictingBatchId;
    }

    /**
     * @return the status of the blocking batch — one of {@code PENDING},
     *         {@code PROCESSING}, {@code CLEANUP_FAILED}, or {@code null} if
     *         unknown.
     */
    public String getConflictingStatus() {
        return conflictingStatus;
    }
}
