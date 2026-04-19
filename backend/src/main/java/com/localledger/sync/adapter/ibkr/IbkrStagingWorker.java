package com.localledger.sync.adapter.ibkr;

import com.localledger.entity.IbkrStagedOrder;
import com.localledger.entity.IbkrStagedTradeConfirm;
import com.localledger.repository.IbkrStagedOrderRepository;
import com.localledger.repository.IbkrStagedTradeConfirmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Worker bean for IBKR staging operations that require independent transactions.
 *
 * Extracted from {@link IbkrStagingService} to ensure that
 * {@code @Transactional(propagation = REQUIRES_NEW)} is properly intercepted
 * by the Spring AOP proxy. When these methods were in IbkrStagingService itself,
 * internal (self) calls bypassed the proxy, so the REQUIRES_NEW propagation
 * never took effect.
 *
 * @see IbkrStagingService
 */
@Component
public class IbkrStagingWorker {

    private static final Logger logger = LoggerFactory.getLogger(IbkrStagingWorker.class);

    private final IbkrStagedOrderRepository stagedOrderRepository;
    private final IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository;

    public IbkrStagingWorker(IbkrStagedOrderRepository stagedOrderRepository,
                             IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository) {
        this.stagedOrderRepository = stagedOrderRepository;
        this.stagedTradeConfirmRepository = stagedTradeConfirmRepository;
    }

    /**
     * Stage a single order record. Idempotent: skips if orderId already exists.
     * Runs in its own transaction (REQUIRES_NEW).
     *
     * @param batchId the batch ID
     * @param order   the parsed order record
     * @return true if a new record was created, false if skipped (duplicate)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean stageOrder(Long batchId, IbkrOrderRecord order) {
        String orderId = order.getOrderID();

        // Idempotent check: skip if already staged
        if (stagedOrderRepository.existsByOrderId(orderId)) {
            logger.debug("[IbkrStaging] Order already staged, skipping: orderId={}", orderId);
            return false;
        }

        IbkrStagedOrder staged = mapToStagedOrder(batchId, order);
        stagedOrderRepository.save(staged);
        logger.debug("[IbkrStaging] Staged order: orderId={}", orderId);
        return true;
    }

    /**
     * Stage a single trade confirm record. Idempotent: skips if tradeId already exists.
     * Runs in its own transaction (REQUIRES_NEW).
     *
     * @param batchId the batch ID
     * @param confirm the parsed trade confirm record
     * @return true if a new record was created, false if skipped (duplicate)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean stageTradeConfirm(Long batchId, IbkrTradeConfirm confirm) {
        String tradeId = confirm.getTradeID();

        // Idempotent check: skip if already staged
        if (stagedTradeConfirmRepository.existsByTradeId(tradeId)) {
            logger.debug("[IbkrStaging] TradeConfirm already staged, skipping: tradeId={}", tradeId);
            return false;
        }

        IbkrStagedTradeConfirm staged = mapToStagedTradeConfirm(batchId, confirm);
        stagedTradeConfirmRepository.save(staged);
        logger.debug("[IbkrStaging] Staged trade confirm: tradeId={}", tradeId);
        return true;
    }

    // ============ Mapping methods ============

    /**
     * Map IbkrOrderRecord (in-memory) → IbkrStagedOrder (entity).
     * All fields are stored as VARCHAR for lossless staging.
     */
    private IbkrStagedOrder mapToStagedOrder(Long batchId, IbkrOrderRecord order) {
        IbkrStagedOrder staged = new IbkrStagedOrder();

        // Management fields
        staged.setBatchId(batchId);
        staged.setStatus("PENDING");

        // IBKR data fields (1:1 mapping, all VARCHAR)
        staged.setAccountId(order.getAccountId());
        staged.setAcctAlias(order.getAcctAlias());
        staged.setCurrency(order.getCurrency());
        staged.setAssetCategory(order.getAssetCategory());
        staged.setSymbol(order.getSymbol());
        staged.setDescription(order.getDescription());
        staged.setConid(order.getConid());
        staged.setSecurityId(order.getSecurityID());
        staged.setSecurityIdType(order.getSecurityIDType());
        staged.setMultiplier(order.getMultiplier());
        staged.setStrike(order.getStrike());
        staged.setExpiry(order.getExpiry());
        staged.setPutCall(order.getPutCall());
        staged.setOrderId(order.getOrderID());
        staged.setOrderTime(order.getOrderTime());
        staged.setDateTime(order.getDateTime());
        staged.setSettleDate(order.getSettleDate());
        staged.setTradeDate(order.getTradeDate());
        staged.setBuySell(order.getBuySell());
        staged.setOrderType(order.getOrderType());
        staged.setIsApiOrder(order.getIsAPIOrder());
        staged.setQuantity(order.getQuantity());
        staged.setPrice(order.getPrice());
        staged.setAmount(order.getAmount());
        staged.setProceeds(order.getProceeds());
        staged.setNetCash(order.getNetCash());
        staged.setCommission(order.getCommission());
        staged.setCommissionCurrency(order.getCommissionCurrency());
        staged.setTradeCharge(order.getTradeCharge());
        staged.setTraderId(order.getTraderID());

        return staged;
    }

    /**
     * Map IbkrTradeConfirm (in-memory) → IbkrStagedTradeConfirm (entity).
     * All fields are stored as VARCHAR for lossless staging.
     */
    private IbkrStagedTradeConfirm mapToStagedTradeConfirm(Long batchId, IbkrTradeConfirm confirm) {
        IbkrStagedTradeConfirm staged = new IbkrStagedTradeConfirm();

        // Management field
        staged.setBatchId(batchId);

        // IBKR data fields (1:1 mapping, all VARCHAR)
        staged.setAccountId(confirm.getAccountId());
        staged.setAcctAlias(confirm.getAcctAlias());
        staged.setCurrency(confirm.getCurrency());
        staged.setAssetCategory(confirm.getAssetCategory());
        staged.setSymbol(confirm.getSymbol());
        staged.setDescription(confirm.getDescription());
        staged.setConid(confirm.getConid());
        staged.setSecurityId(confirm.getSecurityID());
        staged.setSecurityIdType(confirm.getSecurityIDType());
        staged.setMultiplier(confirm.getMultiplier());
        staged.setStrike(confirm.getStrike());
        staged.setExpiry(confirm.getExpiry());
        staged.setPutCall(confirm.getPutCall());
        staged.setTransactionType(confirm.getTransactionType());
        staged.setTradeId(confirm.getTradeID());
        staged.setOrderId(confirm.getOrderID());
        staged.setExecId(confirm.getExecID());
        staged.setBrokerageOrderId(confirm.getBrokerageOrderID());
        staged.setOrderReference(confirm.getOrderReference());
        staged.setOrderTime(confirm.getOrderTime());
        staged.setDateTime(confirm.getDateTime());
        staged.setSettleDate(confirm.getSettleDate());
        staged.setTradeDate(confirm.getTradeDate());
        staged.setExchange(confirm.getExchange());
        staged.setBuySell(confirm.getBuySell());
        staged.setQuantity(confirm.getQuantity());
        staged.setPrice(confirm.getPrice());
        staged.setAmount(confirm.getAmount());
        staged.setProceeds(confirm.getProceeds());
        staged.setNetCash(confirm.getNetCash());
        staged.setCommission(confirm.getCommission());
        staged.setCommissionCurrency(confirm.getCommissionCurrency());
        staged.setTradeCharge(confirm.getTradeCharge());
        staged.setCode(confirm.getCode());
        staged.setOrderType(confirm.getOrderType());
        staged.setTraderId(confirm.getTraderID());
        staged.setIsApiOrder(confirm.getIsAPIOrder());

        return staged;
    }
}
