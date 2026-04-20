package com.vortex.sync.adapter.ibkr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IBKR staging service.
 *
 * Stages parsed IBKR data (orders + trade confirms) into the
 * ibkr_staged_orders and ibkr_staged_trade_confirms tables.
 *
 * Each record is written in an independent transaction (REQUIRES_NEW)
 * via {@link IbkrStagingWorker} for idempotent, crash-safe staging.
 * Duplicate records are detected via orderId / tradeId and silently skipped.
 *
 * Note: Per-record transactional methods are in {@link IbkrStagingWorker}
 * (a separate Spring bean) to ensure that {@code @Transactional(REQUIRES_NEW)}
 * is properly intercepted by the AOP proxy. Self-calls within the same bean
 * would bypass the proxy.
 *
 * @see IbkrStagingWorker
 * @see com.vortex.entity.IbkrStagedOrder
 * @see com.vortex.entity.IbkrStagedTradeConfirm
 */
@Service
public class IbkrStagingService {

    private static final Logger logger = LoggerFactory.getLogger(IbkrStagingService.class);

    private final IbkrStagingWorker stagingWorker;

    public IbkrStagingService(IbkrStagingWorker stagingWorker) {
        this.stagingWorker = stagingWorker;
    }

    /**
     * Stage all orders and trade confirms for a batch.
     *
     * @param batchId       the batch ID
     * @param orders        parsed order records
     * @param tradeConfirms parsed trade confirm records
     * @return the number of newly staged orders (excluding duplicates)
     */
    public int stageAll(Long batchId, List<IbkrOrderRecord> orders, List<IbkrTradeConfirm> tradeConfirms) {
        logger.info("[IbkrStaging] Starting staging: batchId={}, orders={}, tradeConfirms={}",
                batchId, orders.size(), tradeConfirms.size());

        int stagedOrderCount = 0;
        int skippedOrderCount = 0;

        for (IbkrOrderRecord order : orders) {
            boolean staged = stagingWorker.stageOrder(batchId, order);
            if (staged) {
                stagedOrderCount++;
            } else {
                skippedOrderCount++;
            }
        }

        int stagedConfirmCount = 0;
        int skippedConfirmCount = 0;

        for (IbkrTradeConfirm confirm : tradeConfirms) {
            boolean staged = stagingWorker.stageTradeConfirm(batchId, confirm);
            if (staged) {
                stagedConfirmCount++;
            } else {
                skippedConfirmCount++;
            }
        }

        logger.info("[IbkrStaging] Staging complete: batchId={}, " +
                        "orders(staged={}, skipped={}), tradeConfirms(staged={}, skipped={})",
                batchId, stagedOrderCount, skippedOrderCount, stagedConfirmCount, skippedConfirmCount);

        return stagedOrderCount;
    }
}
