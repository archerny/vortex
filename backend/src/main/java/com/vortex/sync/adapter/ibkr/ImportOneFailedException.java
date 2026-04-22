package com.vortex.sync.adapter.ibkr;

import com.vortex.entity.IbkrStagedOrder;

/**
 * Internal hand-off exception thrown by {@link IbkrImportWorker#importSingleOrder}
 * when a single staged order cannot be imported.
 *
 * <p>Its sole purpose is to carry the still-attached {@link IbkrStagedOrder}
 * reference (plus the original cause) out of the rolled-back REQUIRES_NEW
 * transaction so that {@link IbkrImportService} can open a fresh REQUIRES_NEW
 * tx via {@link IbkrImportWorker#markFailed} and persist the {@code FAILED}
 * status.
 *
 * <p>Package-private on purpose — this is a per-broker, per-row plumbing
 * detail, not a public contract.
 *
 * <p>Fixes the P0-1 link of the data-loss chain (see
 * {@code docs/broker-sync/fix-p0-data-loss-chain.md}).
 */
class ImportOneFailedException extends RuntimeException {

    private final IbkrStagedOrder staged;

    ImportOneFailedException(IbkrStagedOrder staged, Throwable cause) {
        super(cause != null ? cause.getMessage() : null, cause);
        this.staged = staged;
    }

    IbkrStagedOrder getStaged() {
        return staged;
    }
}
