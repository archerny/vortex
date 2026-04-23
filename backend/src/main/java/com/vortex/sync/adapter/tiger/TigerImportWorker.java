package com.vortex.sync.adapter.tiger;

import com.vortex.entity.TigerStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.repository.TradeRecordRepository;
import com.vortex.sync.core.CategorizedSyncException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Worker bean for Tiger import operations that require independent transactions.
 *
 * <p>Extracted from {@link TigerImportService} to ensure that
 * {@code @Transactional(propagation = REQUIRES_NEW)} is properly intercepted by
 * the Spring AOP proxy. Self-invocation inside a single bean would bypass the
 * proxy and silently disable the per-record transaction boundary.
 *
 * <p>Each call to {@link #importOne} processes exactly one staged order in its
 * own transaction. A failure on one row never rolls back the surrounding batch.
 *
 * @see TigerImportService
 */
@Component
public class TigerImportWorker {

    private static final Logger logger = LoggerFactory.getLogger(TigerImportWorker.class);

    private static final String BROKER_CODE = TigerTradeRecordMapper.BROKER_CODE;

    private final TigerStagedOrderRepository stagedOrderRepository;
    private final TradeRecordRepository tradeRecordRepository;
    private final TigerTradeRecordMapper mapper;

    public TigerImportWorker(TigerStagedOrderRepository stagedOrderRepository,
                             TradeRecordRepository tradeRecordRepository) {
        this.stagedOrderRepository = stagedOrderRepository;
        this.tradeRecordRepository = tradeRecordRepository;
        this.mapper = new TigerTradeRecordMapper();
    }

    /**
     * Import a single staged order into {@code trade_records}.
     *
     * <p>Runs in its own transaction ({@link Propagation#REQUIRES_NEW}) so one
     * bad row does not poison the whole batch. On success, the staged row is
     * updated to {@code IMPORTED} and {@code imported_trade_id} is set.
     *
     * <p><b>Failure contract (P0-1 fix)</b>: on any <i>unexpected</i> exception
     * (mapping failure, DB error, etc.), this method rethrows as
     * {@link ImportOneFailedException} instead of trying to persist
     * {@code status=FAILED} in the same rolled-back transaction. The caller
     * ({@link TigerImportService}) invokes {@link #markFailed} through the
     * Spring AOP proxy, which opens a fresh REQUIRES_NEW transaction that can
     * actually commit the FAILED status. The <i>expected</i> pre-filter
     * branches (SKIPPED / FAILED) continue to write their terminal state
     * inline — they happen before any state mutation, so the tx is still
     * clean at that point.
     *
     * @param batchId  enclosing broker-sync batch id (for logging)
     * @param brokerId resolved broker id used when building the TradeRecord
     * @param staged   the staged order to import (must not be null)
     * @throws ImportOneFailedException if mapping or persistence throws
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void importOne(Long batchId, Long brokerId, TigerStagedOrder staged) {
        try {
            boolean alreadyImported = tradeRecordRepository
                    .existsByExternalBrokerAndExternalId(BROKER_CODE, staged.getTigerId());

            TigerTradeRecordMapper.FilterResult filterResult = mapper.preFilter(staged, alreadyImported);

            switch (filterResult.getKind()) {
                case SKIPPED:
                    staged.setStatus("SKIPPED");
                    staged.setErrorMessage(filterResult.getMessage());
                    stagedOrderRepository.save(staged);
                    logger.debug("[TigerImport] Skipped: tigerId={}, reason={}",
                            staged.getTigerId(), filterResult.getMessage());
                    return;

                case FAILED:
                    staged.setStatus("FAILED");
                    staged.setErrorMessage(CategorizedSyncException.format(
                            filterResult.getCategory(),
                            staged.getTigerId(),
                            filterResult.getMessage()));
                    stagedOrderRepository.save(staged);
                    logger.warn("[TigerImport] Filter-failed: tigerId={}, category={}, reason={}",
                            staged.getTigerId(), filterResult.getCategory(), filterResult.getMessage());
                    return;

                case PASS:
                default:
                    // fall through to mapping + save
            }

            TradeRecord tradeRecord = mapper.toTradeRecord(staged, brokerId, batchId);
            TradeRecord saved = tradeRecordRepository.save(tradeRecord);

            staged.setStatus("IMPORTED");
            staged.setImportedTradeId(saved.getId());
            staged.setErrorMessage(null);
            stagedOrderRepository.save(staged);

            logger.debug("[TigerImport] Imported: tigerId={} -> tradeRecordId={}",
                    staged.getTigerId(), saved.getId());

        } catch (Exception e) {
            // Do NOT save staged here — this tx is already rollback-only.
            // Propagate to the service, which invokes markFailed() via the AOP
            // proxy so the FAILED write runs in a fresh REQUIRES_NEW tx.
            logger.warn("[TigerImport] Failed to import tigerId={} (batchId={}): {}",
                    staged.getTigerId(), batchId, e.getMessage(), e);
            throw new ImportOneFailedException(staged, e);
        }
    }

    /**
     * Persist {@code status=FAILED} + {@code error_message} for a single
     * staged row in a fresh REQUIRES_NEW transaction — separated from
     * {@link #importOne} because that method's tx is already rollback-only
     * once the importing exception fires.
     *
     * <p>Re-reads the row by id before mutating to avoid detached-entity
     * pitfalls.
     *
     * <p>Must be called through the Spring AOP proxy (i.e. from another
     * bean — {@link TigerImportService}). Self-invocation would bypass the
     * proxy and silently drop the REQUIRES_NEW semantic.
     *
     * @param staged       the staged row that failed (needs a non-null id)
     * @param errorMessage the error message to persist
     * @throws IllegalStateException if the row no longer exists in the DB
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(TigerStagedOrder staged, String errorMessage) {
        TigerStagedOrder fresh = stagedOrderRepository.findById(staged.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "Staged row disappeared while marking FAILED: id=" + staged.getId()));
        fresh.setStatus("FAILED");
        fresh.setErrorMessage(errorMessage);
        stagedOrderRepository.save(fresh);
    }
}
