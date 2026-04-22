package com.vortex.entity;

import jakarta.persistence.*;

/**
 * Tiger staged order entity.
 *
 * Corresponds to the tiger_staged_orders table. Stores parsed Tiger Order data
 * (1:1 mapping to {@link com.vortex.sync.adapter.tiger.TigerOrderRecord}) in
 * VARCHAR format for lossless staging.
 *
 * Tiger Open API's FILLED_ORDERS endpoint only returns TradeOrder (order-level)
 * data, so Tiger has no trade-confirm granularity table (unlike IBKR).
 *
 * Part of the two-phase import flow: Tiger API → staged table → trade_records.
 *
 * @see com.vortex.sync.adapter.tiger.TigerOrderRecord
 */
@Entity
@Table(name = "tiger_staged_orders")
public class TigerStagedOrder extends BaseEntity {

    // ============ Management fields ============

    /**
     * FK to broker_sync_batches.id
     */
    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    /**
     * Record status: PENDING, IMPORTED, SKIPPED, CONFLICT, FAILED
     */
    @Column(name = "status", nullable = false, length = 32)
    private String status = "PENDING";

    /**
     * FK to trade_records.id after successful import (for reverse tracing)
     */
    @Column(name = "imported_trade_id")
    private Long importedTradeId;

    /**
     * Record-level error message (conversion failure, conflict details, etc.)
     */
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    // ============ Tiger Order data fields (27 fields, all VARCHAR) ============

    /**
     * Tiger global unique order ID ({@code TradeOrder.getId()}). Deduplication key.
     * NOTE: Tiger has three ID-like fields — {@code id} (globally unique, used here),
     * {@code orderId} (local auto-increment, NOT unique) and {@code externalId}
     * (typically equals orderId, NOT unique). Always use {@code id}.
     */
    @Column(name = "tiger_id", nullable = false)
    private String tigerId;

    @Column(name = "account")
    private String account;

    @Column(name = "action")
    private String action;

    /** Raw Tiger order status (e.g. FILLED, PARTIALLY_FILLED). */
    @Column(name = "status_raw")
    private String statusRaw;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "trade_time")
    private String tradeTime;

    @Column(name = "quantity")
    private String quantity;

    /** Quantity offset; real qty = quantity * 10^-quantityScale (fractional shares). */
    @Column(name = "quantity_scale")
    private String quantityScale;

    @Column(name = "filled_quantity")
    private String filledQuantity;

    @Column(name = "avg_fill_price")
    private String avgFillPrice;

    @Column(name = "commission")
    private String commission;

    @Column(name = "gst")
    private String gst;

    @Column(name = "realized_pnl")
    private String realizedPnl;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "sec_type")
    private String secType;

    @Column(name = "currency")
    private String currency;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "market")
    private String market;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "multiplier")
    private String multiplier;

    @Column(name = "expiry")
    private String expiry;

    @Column(name = "strike")
    private String strike;

    @Column(name = "put_call")
    private String putCall;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "limit_price")
    private String limitPrice;

    /**
     * Order attribute description. Non-empty indicates an option event
     * (e.g. Exercise / Assignment / Expired). Currently all non-empty values
     * are routed to FAILED status pending a real-sample mapping review.
     */
    @Column(name = "attr_desc")
    private String attrDesc;

    // ============ Constructors ============

    public TigerStagedOrder() {
    }

    // ============ Getters and Setters ============

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getImportedTradeId() {
        return importedTradeId;
    }

    public void setImportedTradeId(Long importedTradeId) {
        this.importedTradeId = importedTradeId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTigerId() {
        return tigerId;
    }

    public void setTigerId(String tigerId) {
        this.tigerId = tigerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatusRaw() {
        return statusRaw;
    }

    public void setStatusRaw(String statusRaw) {
        this.statusRaw = statusRaw;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantityScale() {
        return quantityScale;
    }

    public void setQuantityScale(String quantityScale) {
        this.quantityScale = quantityScale;
    }

    public String getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(String filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public String getAvgFillPrice() {
        return avgFillPrice;
    }

    public void setAvgFillPrice(String avgFillPrice) {
        this.avgFillPrice = avgFillPrice;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getRealizedPnl() {
        return realizedPnl;
    }

    public void setRealizedPnl(String realizedPnl) {
        this.realizedPnl = realizedPnl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getStrike() {
        return strike;
    }

    public void setStrike(String strike) {
        this.strike = strike;
    }

    public String getPutCall() {
        return putCall;
    }

    public void setPutCall(String putCall) {
        this.putCall = putCall;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getAttrDesc() {
        return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
    }

    @Override
    public String toString() {
        return "TigerStagedOrder{" +
                "id=" + getId() +
                ", batchId=" + batchId +
                ", status='" + status + '\'' +
                ", tigerId='" + tigerId + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                ", action='" + action + '\'' +
                ", symbol='" + symbol + '\'' +
                ", secType='" + secType + '\'' +
                ", filledQuantity='" + filledQuantity + '\'' +
                ", avgFillPrice='" + avgFillPrice + '\'' +
                ", commission='" + commission + '\'' +
                ", currency='" + currency + '\'' +
                ", attrDesc='" + attrDesc + '\'' +
                '}';
    }
}
