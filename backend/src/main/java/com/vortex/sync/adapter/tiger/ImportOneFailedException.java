package com.vortex.sync.adapter.tiger;

import com.vortex.entity.TigerStagedOrder;

/**
 * Internal hand-off exception thrown by {@link TigerImportWorker#importOne}
 * when a single staged order cannot be imported.
 *
 * <p>Its sole purpose is to carry the still-attached {@link TigerStagedOrder}
 * reference (plus the original cause) out of the rolled-back REQUIRES_NEW
 * transaction so that {@link TigerImportService} can open a fresh REQUIRES_NEW
 * tx via {@link TigerImportWorker#markFailed} and persist the {@code FAILED}
 * status.
 *
 * <p>Package-private on purpose — this is a per-broker, per-row plumbing
 * detail, not a public contract.
 *
 * <p>Fixes the P0-1 link of the data-loss chain (see
 * {@code docs/broker-sync/fix-p0-data-loss-chain.md}).
 */
class ImportOneFailedException extends RuntimeException {

    private final TigerStagedOrder staged;

    ImportOneFailedException(TigerStagedOrder staged, Throwable cause) {
        super(cause != null ? cause.getMessage() : null, cause);
        this.staged = staged;
    }

    TigerStagedOrder getStaged() {
        return staged;
    }
}
