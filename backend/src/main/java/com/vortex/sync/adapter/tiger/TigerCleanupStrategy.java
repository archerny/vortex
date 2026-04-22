package com.vortex.sync.adapter.tiger;

import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.sync.core.BrokerCleanupStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Tiger cleanup strategy.
 *
 * <p>Wipes the Tiger-private {@code tiger_staged_orders} table. The shared
 * {@code trade_records} table is handled by
 * {@link com.vortex.sync.core.SyncBatchCleanupService}.</p>
 *
 * <p><strong>Important invariant</strong>: Tiger staging dedup
 * ({@link TigerStagingWorker#stageOrder}) uses a <em>global</em>
 * {@code existsByTigerId} check rather than a per-batch check. This relies
 * on cleanup actually deleting the staged rows of failed batches; if the
 * cleanup semantics ever change (e.g. to keep failed rows for analysis),
 * Tiger's staging dedup must be updated in lockstep, otherwise retrying a
 * failed batch would silently skip all previously-attempted orders.</p>
 */
@Component
public class TigerCleanupStrategy implements BrokerCleanupStrategy {

    private static final Logger logger = LoggerFactory.getLogger(TigerCleanupStrategy.class);

    private final TigerStagedOrderRepository stagedOrderRepository;

    public TigerCleanupStrategy(TigerStagedOrderRepository stagedOrderRepository) {
        this.stagedOrderRepository = stagedOrderRepository;
    }

    @Override
    public String brokerCode() {
        return "tiger";
    }

    @Override
    public void deleteStagedRows(Long batchId) {
        long orders = stagedOrderRepository.deleteByBatchId(batchId);
        logger.info("[TigerCleanup] batch={} deleted stagedOrders={}", batchId, orders);
    }
}
