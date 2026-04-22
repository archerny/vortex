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
 * <h3>Failure semantics (v2 fail-fast, aligned with IBKR)</h3>
 * Per-record exceptions are <strong>not</strong> swallowed — they propagate to
 * the caller so that a single broken record aborts the whole batch. The outer
 * {@link TigerSyncAdapter} catches the exception and returns
 * {@code SyncResult.failure}, which triggers full cleanup via
 * {@link com.vortex.sync.core.SyncBatchFailureHandler}.
 *
 * <p>This used to be a per-record try/catch that counted failures and kept
 * going. That path produced a silent data-loss window: rows that failed
 * staging were never written to {@code tiger_staged_orders}, so the downstream
 * {@code countByBatchIdAndStatus(FAILED)} check in the adapter never noticed
 * them, and the batch finalized as COMPLETED with a missing record. Removed
 * in v2.4.2 — see {@code docs/broker-sync/framework/import-consistency.md}.</p>
 *
 * <p>Each call into {@link TigerStagingWorker#stageOrder} still runs in its
 * own {@code REQUIRES_NEW} transaction, so successful inserts are committed
 * immediately and made idempotent via {@code existsByTigerId}. Rerunning a
 * staging pass after cleanup is safe.</p>
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
     * <p>Exceptions from the underlying {@link TigerStagingWorker#stageOrder}
     * call propagate to the caller — the loop does not swallow them. This is
     * the v2 fail-fast contract: if any record cannot be staged, the entire
     * batch is aborted and cleaned up.</p>
     *
     * @param batchId the batch ID
     * @param records parsed order records
     * @return aggregated staging counts (successful only; if this method
     *         returns normally, no record failed)
     * @throws RuntimeException if any single record fails to stage
     */
    public StagingResult stageAll(Long batchId, List<TigerOrderRecord> records) {
        logger.info("[TigerStaging] Starting staging: batchId={}, records={}",
                batchId, records.size());

        int inserted = 0;
        int skipped = 0;

        for (TigerOrderRecord record : records) {
            boolean newlyInserted = stagingWorker.stageOrder(batchId, record);
            if (newlyInserted) {
                inserted++;
            } else {
                skipped++;
            }
        }

        StagingResult result = new StagingResult(records.size(), inserted, skipped);
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
     * </ul>
     *
     * Invariant: {@code attempted == inserted + skipped}. If a record throws
     * mid-loop, {@code stageAll} propagates the exception and never constructs
     * a {@code StagingResult}, so no "failed" counter is needed.
     */
    public static final class StagingResult {
        public final int attempted;
        public final int inserted;
        public final int skipped;

        public StagingResult(int attempted, int inserted, int skipped) {
            this.attempted = attempted;
            this.inserted = inserted;
            this.skipped = skipped;
        }

        @Override
        public String toString() {
            return "StagingResult{attempted=" + attempted +
                    ", inserted=" + inserted +
                    ", skipped=" + skipped + '}';
        }
    }
}
