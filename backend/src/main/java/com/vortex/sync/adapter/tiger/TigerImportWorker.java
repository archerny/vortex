package com.vortex.sync.adapter.tiger;

import com.vortex.entity.TigerStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.repository.TradeRecordRepository;
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
     * updated to {@code IMPORTED} and {@code imported_trade_id} is set. On
     * any uncaught exception, the row is marked {@code FAILED}.
     *
     * @param batchId  enclosing broker-sync batch id (for logging)
     * @param brokerId resolved broker id used when building the TradeRecord
     * @param staged   the staged order to import (must not be null)
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
                    staged.setErrorMessage(filterResult.getMessage());
                    stagedOrderRepository.save(staged);
                    logger.warn("[TigerImport] Filter-failed: tigerId={}, reason={}",
                            staged.getTigerId(), filterResult.getMessage());
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
            staged.setStatus("FAILED");
            staged.setErrorMessage("Import error: " + e.getMessage());
            stagedOrderRepository.save(staged);
            logger.warn("[TigerImport] Failed to import tigerId={} (batchId={}): {}",
                    staged.getTigerId(), batchId, e.getMessage(), e);
        }
    }
}
