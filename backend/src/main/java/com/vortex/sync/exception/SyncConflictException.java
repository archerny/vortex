package com.vortex.sync.exception;

/**
 * Thrown when a new sync request cannot proceed because another sync batch
 * is currently active (PENDING / PROCESSING) or is blocked in CLEANUP_FAILED
 * state.
 *
 * <p>This exception corresponds to the DB-level guard provided by the partial
 * unique index {@code uk_only_one_active} (see Flyway V28 and
 * {@code docs/broker-sync/framework/import-consistency.md § 4}). Controllers
 * should translate this into an HTTP {@code 409 Conflict} response.</p>
 *
 * <p>A {@code CLEANUP_FAILED} batch must be resolved manually (e.g. by
 * flipping its status to {@code FAILED} after the operator has verified that
 * any leftover data has been cleaned) before the system will accept a new
 * sync request.</p>
 */
public class SyncConflictException extends RuntimeException {

    public SyncConflictException(String message) {
        super(message);
    }

    public SyncConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
