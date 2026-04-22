package com.vortex.sync.adapter.tiger;

import com.vortex.entity.TigerStagedOrder;
import com.vortex.repository.TigerStagedOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Worker bean for Tiger staging operations that require independent transactions.
 *
 * Extracted from {@link TigerStagingService} to ensure that
 * {@code @Transactional(propagation = REQUIRES_NEW)} is properly intercepted
 * by the Spring AOP proxy. Methods that call save() on the staging repository
 * inside a service-level loop must live in a separate bean; otherwise internal
 * self-calls would bypass the proxy and REQUIRES_NEW would never take effect.
 *
 * @see TigerStagingService
 */
@Component
public class TigerStagingWorker {

    private static final Logger logger = LoggerFactory.getLogger(TigerStagingWorker.class);

    /** Formatter for converting epoch-ms timestamps to ISO-8601 strings for staging. */
    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));

    private final TigerStagedOrderRepository stagedOrderRepository;

    public TigerStagingWorker(TigerStagedOrderRepository stagedOrderRepository) {
        this.stagedOrderRepository = stagedOrderRepository;
    }

    /**
     * Stage a single Tiger order record. Idempotent: skips if the Tiger global
     * order id ({@code TradeOrder.getId()}) is already staged.
     *
     * Runs in its own transaction (REQUIRES_NEW) so that a failure on one
     * record does not poison the surrounding batch transaction.
     *
     * @param batchId the batch ID
     * @param record  the parsed order record
     * @return true if a new staged row was created, false if skipped (duplicate)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean stageOrder(Long batchId, TigerOrderRecord record) {
        String tigerId = String.valueOf(record.getOrderId());

        if (stagedOrderRepository.existsByTigerId(tigerId)) {
            logger.debug("[TigerStaging] Order already staged, skipping: tigerId={}", tigerId);
            return false;
        }

        TigerStagedOrder staged = mapToStagedOrder(batchId, record);
        stagedOrderRepository.save(staged);
        logger.debug("[TigerStaging] Staged order: tigerId={}, symbol={}, action={}, filledQty={}",
                tigerId, record.getSymbol(), record.getAction(), record.getFilledQuantity());
        return true;
    }

    // ============ Mapping ============

    /**
     * Map {@link TigerOrderRecord} (in-memory) → {@link TigerStagedOrder} (entity).
     * All business fields are stringified for lossless staging, matching the
     * VARCHAR(255) convention used by {@code ibkr_staged_orders}.
     */
    private TigerStagedOrder mapToStagedOrder(Long batchId, TigerOrderRecord r) {
        TigerStagedOrder staged = new TigerStagedOrder();

        // Management
        staged.setBatchId(batchId);
        staged.setStatus("PENDING");

        // Tiger data fields (1:1 mapping)
        staged.setTigerId(String.valueOf(r.getOrderId()));
        staged.setAccount(r.getAccount());
        staged.setAction(r.getAction());
        staged.setStatusRaw(r.getStatus());
        staged.setOrderTime(formatEpochMillis(r.getOrderTime()));
        staged.setTradeTime(formatEpochMillis(r.getTradeTime()));
        staged.setQuantity(Integer.toString(r.getQuantity()));
        staged.setQuantityScale(Integer.toString(r.getQuantityScale()));
        staged.setFilledQuantity(Integer.toString(r.getFilledQuantity()));
        staged.setAvgFillPrice(toPlainString(r.getAvgFillPrice()));
        staged.setCommission(toPlainString(r.getCommission()));
        staged.setGst(toPlainString(r.getGst()));
        staged.setRealizedPnl(toPlainString(r.getRealizedPnl()));
        staged.setSymbol(r.getSymbol());
        staged.setContractName(r.getContractName());
        staged.setSecType(r.getSecType());
        staged.setCurrency(r.getCurrency());
        staged.setExchange(r.getExchange());
        staged.setMarket(r.getMarket());
        staged.setIdentifier(r.getIdentifier());
        staged.setMultiplier(toPlainString(r.getMultiplier()));
        staged.setExpiry(r.getExpiry());
        staged.setStrike(toPlainString(r.getStrike()));
        staged.setPutCall(r.getPutCall());
        staged.setOrderType(r.getOrderType());
        staged.setLimitPrice(toPlainString(r.getLimitPrice()));
        staged.setAttrDesc(r.getAttrDesc());

        return staged;
    }

    /**
     * Format epoch-millis timestamp to ISO-like string; returns null for <= 0.
     * Using a stable string representation at staging time makes downstream
     * inspection and debugging easier without losing the original semantics
     * (the value is derived from the same epoch-ms and uses Asia/Shanghai
     * which matches {@link TigerOrderRecord#getTradeDate()}).
     */
    private String formatEpochMillis(long epochMs) {
        if (epochMs <= 0L) {
            return null;
        }
        return TIMESTAMP_FORMATTER.format(Instant.ofEpochMilli(epochMs));
    }

    /**
     * Convert BigDecimal to plain string (no scientific notation); null-safe.
     */
    private String toPlainString(BigDecimal value) {
        return value == null ? null : value.toPlainString();
    }
}
