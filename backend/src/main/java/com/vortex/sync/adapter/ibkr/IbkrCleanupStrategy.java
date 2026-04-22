package com.vortex.sync.adapter.ibkr;

import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import com.vortex.sync.core.BrokerCleanupStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * IBKR cleanup strategy.
 *
 * <p>Wipes the two IBKR-private staged tables: {@code ibkr_staged_orders}
 * and {@code ibkr_staged_trade_confirms}. The shared {@code trade_records}
 * table is handled by
 * {@link com.vortex.sync.core.SyncBatchCleanupService}.</p>
 */
@Component
public class IbkrCleanupStrategy implements BrokerCleanupStrategy {

    private static final Logger logger = LoggerFactory.getLogger(IbkrCleanupStrategy.class);

    private final IbkrStagedOrderRepository stagedOrderRepository;
    private final IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository;

    public IbkrCleanupStrategy(IbkrStagedOrderRepository stagedOrderRepository,
                               IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository) {
        this.stagedOrderRepository = stagedOrderRepository;
        this.stagedTradeConfirmRepository = stagedTradeConfirmRepository;
    }

    @Override
    public String brokerCode() {
        return "ibkr";
    }

    @Override
    public void deleteStagedRows(Long batchId) {
        long orders = stagedOrderRepository.deleteByBatchId(batchId);
        long confirms = stagedTradeConfirmRepository.deleteByBatchId(batchId);
        logger.info("[IbkrCleanup] batch={} deleted stagedOrders={}, stagedTradeConfirms={}",
                batchId, orders, confirms);
    }
}
