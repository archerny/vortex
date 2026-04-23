package com.vortex.sync.adapter.ibkr;

import com.vortex.entity.Broker;
import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.TradeRecordRepository;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * IBKR import service.
 *
 * Imports staged orders from ibkr_staged_orders into trade_records.
 * Each record is processed in an independent transaction (REQUIRES_NEW)
 * via {@link IbkrImportWorker} for idempotent, crash-safe importing.
 *
 * Key responsibilities:
 * 1. Orchestrate the import loop (resolve broker, iterate pending orders)
 * 2. Delegate per-record import to {@link IbkrImportWorker#importSingleOrder}
 * 3. Trigger STK-side trigger_ref_id back-fill via {@link IbkrImportWorker#backfillSingleStkRecord}
 *
 * Note: Per-record transactional methods are in {@link IbkrImportWorker}
 * (a separate Spring bean) to ensure that {@code @Transactional(REQUIRES_NEW)}
 * is properly intercepted by the AOP proxy. Self-calls within the same bean
 * would bypass the proxy.
 *
 * @see IbkrImportWorker
 * @see IbkrStagedOrder
 * @see TradeRecord
 */
@Service
public class IbkrImportService {

    private static final Logger logger = LoggerFactory.getLogger(IbkrImportService.class);

    private static final String BROKER_CODE = "ibkr";

    private final IbkrStagedOrderRepository stagedOrderRepository;
    private final TradeRecordRepository tradeRecordRepository;
    private final BrokerRepository brokerRepository;
    private final IbkrImportWorker importWorker;

    public IbkrImportService(IbkrStagedOrderRepository stagedOrderRepository,
                             TradeRecordRepository tradeRecordRepository,
                             BrokerRepository brokerRepository,
                             IbkrImportWorker importWorker) {
        this.stagedOrderRepository = stagedOrderRepository;
        this.tradeRecordRepository = tradeRecordRepository;
        this.brokerRepository = brokerRepository;
        this.importWorker = importWorker;
    }

    /**
     * Import all PENDING staged orders for a batch into trade_records.
     * Each record is processed independently via {@link IbkrImportWorker}.
     * After all records are processed, back-fill trigger_ref_id for
     * STK-side BookTrade records.
     *
     * <p><b>Failure handling (P0-1 fix)</b>: when
     * {@link IbkrImportWorker#importSingleOrder} cannot import a row it
     * throws {@link ImportOneFailedException}; we catch it here and invoke
     * {@link IbkrImportWorker#markFailed} through the Spring AOP proxy,
     * which opens a fresh REQUIRES_NEW transaction to persist the FAILED
     * status. Previously that write was attempted inside the rolled-back
     * {@code importSingleOrder} tx and silently lost, leaving rows stuck
     * in PENDING. If {@code markFailed} itself also fails, the row stays
     * PENDING and is caught later by the adapter's residual-non-terminal
     * check (P0-2).
     *
     * @param batchId the batch ID
     */
    public void importAll(Long batchId) {
        // Resolve broker_id once for the entire batch
        Long brokerId = resolveBrokerId();

        List<IbkrStagedOrder> pendingOrders = stagedOrderRepository.findByBatchIdAndStatus(batchId, "PENDING");
        logger.info("[IbkrImport] Starting import: batchId={}, pendingOrders={}", batchId, pendingOrders.size());

        for (IbkrStagedOrder staged : pendingOrders) {
            try {
                importWorker.importSingleOrder(batchId, brokerId, staged);
            } catch (ImportOneFailedException e) {
                markFailedSafely(e.getStaged(), formatStagedError(e.getStaged(), e.getCause()));
            } catch (Exception e) {
                // Defensive: importSingleOrder should always wrap in
                // ImportOneFailedException, but belt-and-suspenders in case a
                // future change lets a raw exception escape.
                logger.error("[IbkrImport] Unexpected non-wrapped exception importing orderId={}: {}",
                        staged.getOrderId(), e.getMessage(), e);
                markFailedSafely(staged, formatStagedError(staged, e));
            }
        }

        // Back-fill trigger_ref_id for STK-side BookTrade records
        backfillStockSideTriggerRefId(batchId);

        logger.info("[IbkrImport] Import complete: batchId={}", batchId);
    }

    /**
     * Invoke {@link IbkrImportWorker#markFailed} and swallow any exception
     * from the markFailed call itself. A markFailed failure leaves the row
     * in PENDING; the adapter-level residual-non-terminal check will still
     * catch it and escalate the batch to fail-fast cleanup.
     */
    private void markFailedSafely(IbkrStagedOrder staged, String errorMessage) {
        try {
            importWorker.markFailed(staged, errorMessage);
        } catch (Exception markErr) {
            logger.error("[IbkrImport] Failed to mark staged id={} as FAILED — row will stay PENDING " +
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
     * {@link FailureCategory#INTERNAL} and attach the staged row's
     * {@code orderId} as ext_id so operators can trace back to the raw payload.</p>
     */
    private static String formatStagedError(IbkrStagedOrder staged, Throwable cause) {
        if (cause instanceof CategorizedSyncException) {
            CategorizedSyncException cse = (CategorizedSyncException) cause;
            if (cse.getExternalId() == null || cse.getExternalId().isEmpty()) {
                return CategorizedSyncException.format(
                        cse.getCategory(), staged.getOrderId(), cse.getMessage());
            }
            return cse.getFormattedMessage();
        }
        return CategorizedSyncException.format(
                FailureCategory.INTERNAL, staged.getOrderId(), rootMessage(cause));
    }

    // ============ STK-side trigger_ref_id Back-fill ============

    /**
     * Back-fill trigger_ref_id for STK-side BookTrade records.
     *
     * After all records for a batch are imported, find STK-side records
     * with trigger_ref_id=0 and match them to their OPT-side counterparts.
     */
    private void backfillStockSideTriggerRefId(Long batchId) {
        List<TradeRecord> stkRecords = tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                batchId,
                TradeTrigger.OPTION,
                List.of(TriggerRefType.OPTION_ASSIGNED, TriggerRefType.OPTION_EXERCISE),
                AssetType.STOCK);

        if (stkRecords.isEmpty()) {
            logger.debug("[IbkrImport] No STK-side BookTrades to back-fill for batchId={}", batchId);
            return;
        }

        logger.info("[IbkrImport] Back-filling trigger_ref_id for {} STK-side BookTrade(s)", stkRecords.size());

        for (TradeRecord stkRecord : stkRecords) {
            importWorker.backfillSingleStkRecord(stkRecord);
        }
    }

    // ============ Helpers ============

    private Long resolveBrokerId() {
        Optional<Broker> brokerOpt = brokerRepository.findByBrokerCode(BROKER_CODE);
        return brokerOpt.map(Broker::getId)
                .orElseThrow(() -> new IllegalStateException(
                        "Broker not found for code: " + BROKER_CODE + ". Ensure brokers table has a record with broker_code='ibkr'"));
    }
}
