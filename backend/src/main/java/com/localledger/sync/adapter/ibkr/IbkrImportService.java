package com.localledger.sync.adapter.ibkr;

import com.localledger.entity.Broker;
import com.localledger.entity.IbkrStagedOrder;
import com.localledger.entity.TradeRecord;
import com.localledger.entity.enums.AssetType;
import com.localledger.entity.enums.TradeTrigger;
import com.localledger.entity.enums.TriggerRefType;
import com.localledger.repository.BrokerRepository;
import com.localledger.repository.IbkrStagedOrderRepository;
import com.localledger.repository.TradeRecordRepository;
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
     * @param batchId the batch ID
     */
    public void importAll(Long batchId) {
        // Resolve broker_id once for the entire batch
        Long brokerId = resolveBrokerId();

        List<IbkrStagedOrder> pendingOrders = stagedOrderRepository.findByBatchIdAndStatus(batchId, "PENDING");
        logger.info("[IbkrImport] Starting import: batchId={}, pendingOrders={}", batchId, pendingOrders.size());

        for (IbkrStagedOrder staged : pendingOrders) {
            importWorker.importSingleOrder(batchId, brokerId, staged);
        }

        // Back-fill trigger_ref_id for STK-side BookTrade records
        backfillStockSideTriggerRefId(batchId);

        logger.info("[IbkrImport] Import complete: batchId={}", batchId);
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
