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
 * {@code tiger_staged_orders} for a given batch, delegates per-record import
 * to {@link TigerImportWorker#importOne} (each in its own transaction), and
 * re-queries the DB at the end to produce reliable aggregated counts.
 *
 * <p>Per-record transactional methods live in {@link TigerImportWorker} (a
 * separate Spring bean) so that {@code @Transactional(REQUIRES_NEW)} is
 * correctly intercepted by the AOP proxy — a self-call inside the same bean
 * would silently bypass it.
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
     * {@code trade_records}.
     *
     * <p>Counts are re-queried from the DB after the loop so they match the
     * final state of the staging table even if a previous partial run had
     * already imported some rows.
     *
     * @param batchId the broker-sync batch id
     * @return aggregated import result
     */
    public ImportResult importAll(Long batchId) {
        Long brokerId = resolveBrokerId();

        List<TigerStagedOrder> pendingOrders =
                stagedOrderRepository.findByBatchIdAndStatus(batchId, "PENDING");

        logger.info("[TigerImport] Starting import: batchId={}, pendingOrders={}",
                batchId, pendingOrders.size());

        int attempted = pendingOrders.size();

        for (TigerStagedOrder staged : pendingOrders) {
            importWorker.importOne(batchId, brokerId, staged);
        }

        // Re-query the DB so counts reflect the final truth (and pick up rows
        // that were already IMPORTED/SKIPPED/FAILED from a previous partial run).
        long imported = stagedOrderRepository.countByBatchIdAndStatus(batchId, "IMPORTED");
        long skipped = stagedOrderRepository.countByBatchIdAndStatus(batchId, "SKIPPED");
        long failed = stagedOrderRepository.countByBatchIdAndStatus(batchId, "FAILED");

        ImportResult result = new ImportResult(attempted, (int) imported, (int) skipped, (int) failed);
        logger.info("[TigerImport] Import complete: batchId={}, {}", batchId, result);
        return result;
    }

    // ============ Helpers ============

    private Long resolveBrokerId() {
        Optional<Broker> brokerOpt = brokerRepository.findByBrokerCode(BROKER_CODE);
        return brokerOpt.map(Broker::getId)
                .orElseThrow(() -> new IllegalStateException(
                        "Broker not found for code: " + BROKER_CODE
                                + ". Ensure brokers table has a record with broker_code='tiger'"));
    }

    /**
     * Aggregated outcome of an import pass.
     *
     * <ul>
     *   <li>{@code attempted} — number of PENDING rows the loop processed</li>
     *   <li>{@code imported} — total IMPORTED rows for this batch (post-run)</li>
     *   <li>{@code skipped}  — total SKIPPED rows for this batch (post-run)</li>
     *   <li>{@code failed}   — total FAILED rows for this batch (post-run)</li>
     * </ul>
     *
     * Note: {@code imported + skipped + failed} may exceed {@code attempted}
     * when a previous partial run already classified some rows — that's
     * expected and makes the result robust against resume/retry scenarios.
     */
    public static final class ImportResult {
        public final int attempted;
        public final int imported;
        public final int skipped;
        public final int failed;

        public ImportResult(int attempted, int imported, int skipped, int failed) {
            this.attempted = attempted;
            this.imported = imported;
            this.skipped = skipped;
            this.failed = failed;
        }

        @Override
        public String toString() {
            return "ImportResult{attempted=" + attempted +
                    ", imported=" + imported +
                    ", skipped=" + skipped +
                    ", failed=" + failed + '}';
        }
    }
}
