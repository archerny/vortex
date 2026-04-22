package com.vortex.sync.core;

import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.repository.TradeRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Deletes every row this sync batch has written.
 *
 * <p>Used by {@link SyncBatchFailureHandler} as part of the v2 fail-fast
 * model: when a sync batch fails (at any phase, or on startup recovery), we
 * first wipe its footprint and only then mark the batch terminal. See
 * {@code docs/broker-sync/framework/import-consistency.md § 5}.</p>
 *
 * <h3>Cleanup scope</h3>
 * <ul>
 *   <li>Broker-specific staged tables (dispatched by {@code brokerCode}):
 *     <ul>
 *       <li>{@code ibkr} — {@code ibkr_staged_orders}, {@code ibkr_staged_trade_confirms}</li>
 *       <li>{@code tiger} — {@code tiger_staged_orders}</li>
 *     </ul>
 *   </li>
 *   <li>Common table (all brokers): {@code trade_records} matched by {@code sync_batch_id}</li>
 * </ul>
 *
 * <h3>Transaction model</h3>
 * All DELETEs run in a single {@code @Transactional} block. If any DELETE
 * throws, Spring rolls back the whole transaction — callers therefore see
 * an all-or-nothing outcome. The batch record's own status transition is
 * handled by {@link SyncBatchFailureHandler} in a separate transaction so
 * that a status update can proceed even if a DB error occurred here.
 *
 * <h3>Adding a new broker</h3>
 * Any new broker that writes its own staged table MUST be registered in
 * {@link #cleanupBatchData(Long, String)} below. Forgetting to do so leaves
 * orphaned rows after a failed sync.
 */
@Service
public class SyncBatchCleanupService {

    private static final Logger logger = LoggerFactory.getLogger(SyncBatchCleanupService.class);

    private final IbkrStagedOrderRepository ibkrStagedOrderRepository;
    private final IbkrStagedTradeConfirmRepository ibkrStagedTradeConfirmRepository;
    private final TigerStagedOrderRepository tigerStagedOrderRepository;
    private final TradeRecordRepository tradeRecordRepository;

    public SyncBatchCleanupService(IbkrStagedOrderRepository ibkrStagedOrderRepository,
                                   IbkrStagedTradeConfirmRepository ibkrStagedTradeConfirmRepository,
                                   TigerStagedOrderRepository tigerStagedOrderRepository,
                                   TradeRecordRepository tradeRecordRepository) {
        this.ibkrStagedOrderRepository = ibkrStagedOrderRepository;
        this.ibkrStagedTradeConfirmRepository = ibkrStagedTradeConfirmRepository;
        this.tigerStagedOrderRepository = tigerStagedOrderRepository;
        this.tradeRecordRepository = tradeRecordRepository;
    }

    /**
     * Delete everything the given batch has written.
     *
     * @param batchId     the failing batch's ID
     * @param brokerCode  the batch's broker code (used to dispatch
     *                    broker-specific staging cleanup)
     * @throws IllegalStateException if {@code brokerCode} is unknown
     */
    @Transactional
    public void cleanupBatchData(Long batchId, String brokerCode) {
        if (batchId == null) {
            throw new IllegalArgumentException("batchId is required");
        }
        if (brokerCode == null || brokerCode.isBlank()) {
            throw new IllegalArgumentException("brokerCode is required");
        }

        long stagedDeleted;
        long stagedConfirmDeleted = 0L;
        switch (brokerCode) {
            case "ibkr":
                stagedDeleted = ibkrStagedOrderRepository.deleteByBatchId(batchId);
                stagedConfirmDeleted = ibkrStagedTradeConfirmRepository.deleteByBatchId(batchId);
                break;
            case "tiger":
                stagedDeleted = tigerStagedOrderRepository.deleteByBatchId(batchId);
                break;
            default:
                throw new IllegalStateException("Unknown brokerCode for cleanup: " + brokerCode);
        }

        int tradeRecordsDeleted = tradeRecordRepository.deleteBySyncBatchId(batchId);

        logger.info("Cleanup complete for batch {} ({}): stagedOrders={}, stagedTradeConfirms={}, tradeRecords={}",
                batchId, brokerCode, stagedDeleted, stagedConfirmDeleted, tradeRecordsDeleted);
    }
}
