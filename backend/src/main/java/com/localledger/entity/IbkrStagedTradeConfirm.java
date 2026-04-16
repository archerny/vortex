package com.localledger.entity;

import jakarta.persistence.*;

/**
 * IBKR staged trade confirm entity.
 *
 * Corresponds to the ibkr_staged_trade_confirms table. Stores parsed IBKR
 * TradeConfirm data (1:1 mapping to IbkrTradeConfirm) in VARCHAR format.
 * Used for audit and reconciliation purposes only — does NOT participate
 * in the trade_records import flow.
 *
 * @see com.localledger.sync.adapter.ibkr.IbkrTradeConfirm
 */
@Entity
@Table(name = "ibkr_staged_trade_confirms")
public class IbkrStagedTradeConfirm extends BaseEntity {

    // ============ Management fields ============

    /**
     * FK to broker_sync_batches.id
     */
    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    // ============ IBKR TradeConfirm data fields (37 fields, all VARCHAR) ============

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "acct_alias")
    private String acctAlias;

    @Column(name = "currency")
    private String currency;

    @Column(name = "asset_category")
    private String assetCategory;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "description")
    private String description;

    @Column(name = "conid")
    private String conid;

    @Column(name = "security_id")
    private String securityId;

    @Column(name = "security_id_type")
    private String securityIdType;

    @Column(name = "multiplier")
    private String multiplier;

    @Column(name = "strike")
    private String strike;

    @Column(name = "expiry")
    private String expiry;

    @Column(name = "put_call")
    private String putCall;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "exec_id")
    private String execId;

    @Column(name = "brokerage_order_id")
    private String brokerageOrderId;

    @Column(name = "order_reference")
    private String orderReference;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "date_time")
    private String dateTime;

    @Column(name = "settle_date")
    private String settleDate;

    @Column(name = "trade_date")
    private String tradeDate;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "buy_sell")
    private String buySell;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "price")
    private String price;

    @Column(name = "amount")
    private String amount;

    @Column(name = "proceeds")
    private String proceeds;

    @Column(name = "net_cash")
    private String netCash;

    @Column(name = "commission")
    private String commission;

    @Column(name = "commission_currency")
    private String commissionCurrency;

    @Column(name = "trade_charge")
    private String tradeCharge;

    @Column(name = "code")
    private String code;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "trader_id")
    private String traderId;

    @Column(name = "is_api_order")
    private String isApiOrder;

    // ============ Constructors ============

    public IbkrStagedTradeConfirm() {
    }

    // ============ Getters and Setters ============

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAcctAlias() {
        return acctAlias;
    }

    public void setAcctAlias(String acctAlias) {
        this.acctAlias = acctAlias;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConid() {
        return conid;
    }

    public void setConid(String conid) {
        this.conid = conid;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getSecurityIdType() {
        return securityIdType;
    }

    public void setSecurityIdType(String securityIdType) {
        this.securityIdType = securityIdType;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getStrike() {
        return strike;
    }

    public void setStrike(String strike) {
        this.strike = strike;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getPutCall() {
        return putCall;
    }

    public void setPutCall(String putCall) {
        this.putCall = putCall;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExecId() {
        return execId;
    }

    public void setExecId(String execId) {
        this.execId = execId;
    }

    public String getBrokerageOrderId() {
        return brokerageOrderId;
    }

    public void setBrokerageOrderId(String brokerageOrderId) {
        this.brokerageOrderId = brokerageOrderId;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getBuySell() {
        return buySell;
    }

    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProceeds() {
        return proceeds;
    }

    public void setProceeds(String proceeds) {
        this.proceeds = proceeds;
    }

    public String getNetCash() {
        return netCash;
    }

    public void setNetCash(String netCash) {
        this.netCash = netCash;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    public void setCommissionCurrency(String commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
    }

    public String getTradeCharge() {
        return tradeCharge;
    }

    public void setTradeCharge(String tradeCharge) {
        this.tradeCharge = tradeCharge;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTraderId() {
        return traderId;
    }

    public void setTraderId(String traderId) {
        this.traderId = traderId;
    }

    public String getIsApiOrder() {
        return isApiOrder;
    }

    public void setIsApiOrder(String isApiOrder) {
        this.isApiOrder = isApiOrder;
    }

    @Override
    public String toString() {
        return "IbkrStagedTradeConfirm{" +
                "id=" + getId() +
                ", batchId=" + batchId +
                ", tradeId='" + tradeId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", buySell='" + buySell + '\'' +
                ", symbol='" + symbol + '\'' +
                ", assetCategory='" + assetCategory + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                ", commission='" + commission + '\'' +
                ", currency='" + currency + '\'' +
                ", exchange='" + exchange + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
