package com.vortex.sync.adapter.tiger;

import com.vortex.entity.Broker;
import com.vortex.entity.TigerStagedOrder;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.TigerStagedOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    private static final String BROKER_CODE = TigerTradeRecordMapper.BROKER_CODE;

    private final TigerStagedOrderRepository stagedOrderRepository;
    private final BrokerRepository brokerRepository;
    private final TigerImportWorker importWorker;

    public TigerImportService(TigerStagedOrderRepository stagedOrderRepository,
                              BrokerRepository brokerRepository,
                              TigerImportWorker importWorker) {
        this.stagedOrderRepository = stagedOrderRepository;
        this.brokerRepository = brokerRepository;
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
        Long brokerId = resolveBrokerId();

        List<TigerStagedOrder> pendingOrders =
                stagedOrderRepository.findByBatchIdAndStatus(batchId, "PENDING");

        logger.info("[TigerImport] Starting import: batchId={}, pendingOrders={}",
                batchId, pendingOrders.size());

        for (TigerStagedOrder staged : pendingOrders) {
            try {
                importWorker.importOne(batchId, brokerId, staged);
            } catch (ImportOneFailedException e) {
                markFailedSafely(e.getStaged(), "Import error: " + rootMessage(e.getCause()));
            } catch (Exception e) {
                // Defensive: importOne should always wrap in
                // ImportOneFailedException, but belt-and-suspenders in case a
                // future change lets a raw exception escape.
                logger.error("[TigerImport] Unexpected non-wrapped exception importing tigerId={}: {}",
                        staged.getTigerId(), e.getMessage(), e);
                markFailedSafely(staged, "Unexpected error: " + e.getMessage());
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

    // ============ Helpers ============

    private Long resolveBrokerId() {
        Optional<Broker> brokerOpt = brokerRepository.findByBrokerCode(BROKER_CODE);
        return brokerOpt.map(Broker::getId)
                .orElseThrow(() -> new IllegalStateException(
                        "Broker not found for code: " + BROKER_CODE
                                + ". Ensure brokers table has a record with broker_code='tiger'"));
    }
}
