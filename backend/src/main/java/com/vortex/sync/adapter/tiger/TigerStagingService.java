package com.vortex.sync.adapter.tiger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Tiger staging service.
 *
 * Stages parsed Tiger order records into the {@code tiger_staged_orders} table.
 *
 * Each record is written in an independent transaction (REQUIRES_NEW) via
 * {@link TigerStagingWorker}, so a single failing row does not roll back the
 * entire batch. Duplicate records (same Tiger global order id) are detected
 * via {@code existsByTigerId} and silently skipped, making the staging step
 * fully idempotent — re-running the same batch is safe.
 *
 * Unlike IBKR, Tiger's {@code FILLED_ORDERS} endpoint only returns order-level
 * data (no separate trade-confirm granularity), so this service stages a
 * single collection.
 *
 * @see TigerStagingWorker
 * @see com.vortex.entity.TigerStagedOrder
 */
@Service
public class TigerStagingService {

    private static final Logger logger = LoggerFactory.getLogger(TigerStagingService.class);

    private final TigerStagingWorker stagingWorker;

    public TigerStagingService(TigerStagingWorker stagingWorker) {
        this.stagingWorker = stagingWorker;
    }

    /**
     * Stage all Tiger order records for the given batch.
     *
     * Per-record failures are caught and counted — they never abort the loop,
     * so a single corrupt record cannot block staging of the rest of the batch.
     * The offending record simply does not get staged; the caller can detect
     * the gap via {@link StagingResult#failed}.
     *
     * @param batchId the batch ID
     * @param records parsed order records
     * @return aggregated staging result
     */
    public StagingResult stageAll(Long batchId, List<TigerOrderRecord> records) {
        logger.info("[TigerStaging] Starting staging: batchId={}, records={}",
                batchId, records.size());

        int inserted = 0;
        int skipped = 0;
        int failed = 0;

        for (TigerOrderRecord record : records) {
            try {
                boolean newlyInserted = stagingWorker.stageOrder(batchId, record);
                if (newlyInserted) {
                    inserted++;
                } else {
                    skipped++;
                }
            } catch (Exception e) {
                failed++;
                logger.error("[TigerStaging] Failed to stage order: tigerId={}, symbol={}, error={}",
                        record.getOrderId(), record.getSymbol(), e.getMessage(), e);
                // Swallow the exception so the loop continues.
            }
        }

        StagingResult result = new StagingResult(records.size(), inserted, skipped, failed);
        logger.info("[TigerStaging] Staging complete: batchId={}, {}", batchId, result);
        return result;
    }

    /**
     * Aggregated outcome of a staging pass.
     *
     * <ul>
     *   <li>{@code attempted} — total records received (sanity check)</li>
     *   <li>{@code inserted} — new rows written to {@code tiger_staged_orders}</li>
     *   <li>{@code skipped}  — records whose Tiger id was already staged (idempotent dedup)</li>
     *   <li>{@code failed}   — records that threw an exception during staging</li>
     * </ul>
     *
     * Invariant: {@code attempted == inserted + skipped + failed}.
     */
    public static final class StagingResult {
        public final int attempted;
        public final int inserted;
        public final int skipped;
        public final int failed;

        public StagingResult(int attempted, int inserted, int skipped, int failed) {
            this.attempted = attempted;
            this.inserted = inserted;
            this.skipped = skipped;
            this.failed = failed;
        }

        @Override
        public String toString() {
            return "StagingResult{attempted=" + attempted +
                    ", inserted=" + inserted +
                    ", skipped=" + skipped +
                    ", failed=" + failed + '}';
        }
    }
}
