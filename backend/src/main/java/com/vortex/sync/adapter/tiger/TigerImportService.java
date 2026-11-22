package com.vortex.sync.adapter.tiger;

import com.vortex.entity.BrokerAccount;
import com.vortex.entity.TigerStagedOrder;
import com.vortex.repository.BrokerAccountRepository;
import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Tiger import service — stage 2 of the two-phase pipeline.
 *
 * <p>Orchestrates the import loop that walks every {@code PENDING} row in
 * {@code tiger_staged_orders} for a given batch and delegates per-record
 * import to {@link TigerImportWorker#importOne} (each in its own transaction).
 *
 * <p>Per-record transactional methods live in {@link TigerImportWorker} (a
 * separate Spring bean) so that {@code @Transactional(REQUIRES_NEW)} is
 * correctly intercepted by the AOP proxy — a self-call inside the same bean
 * would silently bypass it.
 *
 * <p>Aggregated result counts (IMPORTED / SKIPPED / FAILED) are intentionally
 * <strong>not</strong> returned from here. The adapter ({@link TigerSyncAdapter})
 * re-queries them from the staging table once the pipeline finishes, mirroring
 * the {@code IbkrImportService} contract and avoiding redundant COUNT queries.
 *
 * @see TigerImportWorker
 * @see TigerTradeRecordMapper
 */
@Service
public class TigerImportService {

    private static final Logger logger = LoggerFactory.getLogger(TigerImportService.class);

    private final TigerStagedOrderRepository stagedOrderRepository;
    private final BrokerAccountRepository accountRepository;
    private final TigerImportWorker importWorker;
    private final com.vortex.repository.BrokerSyncBatchRepository batches;

    public TigerImportService(TigerStagedOrderRepository stagedOrderRepository,
                              BrokerAccountRepository accountRepository,
                              TigerImportWorker importWorker, com.vortex.repository.BrokerSyncBatchRepository batches) {
        this.batches = batches;
        this.stagedOrderRepository = stagedOrderRepository;
        this.accountRepository = accountRepository;
        this.importWorker = importWorker;
    }

    /**
     * Import all {@code PENDING} staged orders for the given batch into
     * {@code trade_records}. Each row is processed in its own transaction
     * via {@link TigerImportWorker#importOne}.
     *
     * <p><b>Failure handling (P0-1 fix)</b>: when
     * {@link TigerImportWorker#importOne} cannot import a row it throws
     * {@link ImportOneFailedException}; we catch it here and invoke
     * {@link TigerImportWorker#markFailed} through the Spring AOP proxy,
     * which opens a fresh REQUIRES_NEW transaction to persist the FAILED
     * status. If {@code markFailed} itself also fails, the row stays
     * PENDING and is caught later by the adapter's residual-non-terminal
     * check (P0-2).
     *
     * @param batchId the broker-sync batch id
     */
    public void importAll(Long batchId) {
        BrokerAccount owner = resolveAccount(batchId);
        Long brokerId = owner.getBrokerId();
        Long brokerAccountId = owner.getId();

        List<TigerStagedOrder> pendingOrders =
                stagedOrderRepository.findByBatchIdAndStatus(batchId, "PENDING");

        logger.info("[TigerImport] Starting import: batchId={}, pendingOrders={}",
                batchId, pendingOrders.size());

        for (TigerStagedOrder staged : pendingOrders) {
            try {
                importWorker.importOne(batchId, brokerId, brokerAccountId, staged);
            } catch (ImportOneFailedException e) {
                markFailedSafely(e.getStaged(), formatStagedError(e.getStaged(), e.getCause()));
            } catch (Exception e) {
                // Defensive: importOne should always wrap in
                // ImportOneFailedException, but belt-and-suspenders in case a
                // future change lets a raw exception escape.
                logger.error("[TigerImport] Unexpected non-wrapped exception importing tigerId={}: {}",
                        staged.getTigerId(), e.getMessage(), e);
                markFailedSafely(staged, formatStagedError(staged, e));
            }
        }

        logger.info("[TigerImport] Import complete: batchId={}", batchId);
    }

    /**
     * Invoke {@link TigerImportWorker#markFailed} and swallow any exception
     * from the markFailed call itself. A markFailed failure leaves the row
     * in PENDING; the adapter-level residual-non-terminal check will still
     * catch it and escalate the batch to fail-fast cleanup.
     */
    private void markFailedSafely(TigerStagedOrder staged, String errorMessage) {
        try {
            importWorker.markFailed(staged, errorMessage);
        } catch (Exception markErr) {
            logger.error("[TigerImport] Failed to mark staged id={} as FAILED — row will stay PENDING " +
                            "and be caught by the adapter-level residual check: {}",
                    staged.getId(), markErr.getMessage(), markErr);
        }
    }

    private static String rootMessage(Throwable cause) {
        if (cause == null) {
            return "unknown";
        }
        String msg = cause.getMessage();
        return msg != null ? msg : cause.getClass().getSimpleName();
    }

    /**
     * Format the staged row's {@code error_message} with the standard
     * {@code [CATEGORY] ext_id=... reason: ...} prefix.
     *
     * <p>If the root cause is a {@link CategorizedSyncException} with an
     * {@code externalId} already bound, use it as-is. Otherwise classify as
     * {@link FailureCategory#INTERNAL} and attach the staged row's {@code tigerId}
     * as ext_id so operators can still trace back to the raw payload.</p>
     */
    private static String formatStagedError(TigerStagedOrder staged, Throwable cause) {
        if (cause instanceof CategorizedSyncException) {
            CategorizedSyncException cse = (CategorizedSyncException) cause;
            // Back-fill ext_id with tigerId if the mapper didn't attach one.
            if (cse.getExternalId() == null || cse.getExternalId().isEmpty()) {
                return CategorizedSyncException.format(
                        cse.getCategory(), staged.getTigerId(), cse.getMessage());
            }
            return cse.getFormattedMessage();
        }
        return CategorizedSyncException.format(
                FailureCategory.INTERNAL, staged.getTigerId(), rootMessage(cause));
    }

    // ============ Helpers ============

    private BrokerAccount resolveAccount(Long batchId) {
        Long key = batches.findById(batchId).map(com.vortex.entity.BrokerSyncBatch::getBrokerAccountId)
                .orElseThrow(() -> new IllegalStateException("Sync batch has no managed account"));
        return accountRepository.findById(key)
                .orElseThrow(() -> new IllegalStateException("Managed account not found for sync batch"));
    }
}
