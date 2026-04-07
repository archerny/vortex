package com.localledger.sync.adapter.ibkr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 盈透证券 IBKR 订单层原始数据模型
 *
 * 对应 Flex Query XML 中 {@code <Order>} 节点。
 * 一个 Order 是用户的一次下单操作，可能被拆分为多笔 TradeConfirm 成交。
 * 字段直接对应 XML 属性，保留原始语义，方便与 IBKR 平台核对。
 *
 * 本类不是数据库实体，不持久化，仅作为内存中的中间数据载体。
 *
 * @see IbkrTradeConfirm 单笔成交明细
 */
public class IbkrOrderRecord {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd;HHmmss");

    // ============ 账户信息 ============

    /** IBKR 账户 ID */
    private String accountId;

    /** 账户别名 */
    private String acctAlias;

    // ============ 合约信息 ============

    /** 币种，如 USD、HKD */
    private String currency;

    /**
     * 资产类别：
     * STK-股票、OPT-期权、FUT-期货、CASH-外汇、FUND-基金
     */
    private String assetCategory;

    /** 证券代码，如 AAPL；期权格式如 "TSM   260320P00320000" */
    private String symbol;

    /** 证券描述，如 "APPLE INC"、"TSM 20MAR26 320 P" */
    private String description;

    /** IBKR 合约 ID（全局唯一，标识一个可交易标的） */
    private String conid;

    /** 证券 ID（通常为 ISIN），如 US0378331005 */
    private String securityID;

    /** 证券 ID 类型，如 ISIN */
    private String securityIDType;

    /** 合约乘数（期权/期货用），股票默认为 1 */
    private String multiplier;

    // ============ 期权专有字段 ============

    /** 期权行权价 */
    private String strike;

    /** 期权到期日，格式 yyyyMMdd */
    private String expiry;

    /** 期权类型：C-Call，P-Put */
    private String putCall;

    // ============ 订单信息 ============

    /** 订单 ID（一个订单可对应多笔 TradeConfirm） */
    private String orderID;

    /** 下单时间，格式 yyyyMMdd;HHmmss */
    private String orderTime;

    /** 成交时间（订单级汇总），格式 yyyyMMdd;HHmmss */
    private String dateTime;

    /** 交割日期，格式 yyyyMMdd，可能为 "MULTI" */
    private String settleDate;

    /** 交易日期，格式 yyyyMMdd，可能为 "MULTI" */
    private String tradeDate;

    /** 成交交易所（订单级汇总时可能为空或 "MULTI"） */
    private String exchange;

    /** 买卖方向：BUY / SELL */
    private String buySell;

    /** 订单类型：LMT-限价、MKT-市价 等 */
    private String orderType;

    /** 是否通过 API 下单：Y/N */
    private String isAPIOrder;

    // ============ 数量与金额 ============

    /** 成交数量（卖出为负数） */
    private String quantity;

    /** 成交均价 */
    private String price;

    /** 成交金额（quantity × price，含正负号） */
    private String amount;

    /** 收入/支出（与 amount 符号相反） */
    private String proceeds;

    /** 净现金流（proceeds - commission） */
    private String netCash;

    // ============ 费用 ============

    /** 佣金（通常为负数，表示支出） */
    private String commission;

    /** 佣金币种 */
    private String commissionCurrency;

    /** 交易附加费 */
    private String tradeCharge;

    // ============ 其他 ============

    /** 交易代码标记，如 O-开仓、C-平仓、P-部分成交、A-ACATS 转入、Ep-期权到期 */
    private String code;

    /** 交易员 ID */
    private String traderID;

    // ============ Constructors ============

    public IbkrOrderRecord() {
    }

    // ============ 业务方法 ============

    /**
     * 获取成交数量（绝对值，BigDecimal）
     */
    public BigDecimal getAbsQuantity() {
        if (quantity == null || quantity.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(quantity).abs();
    }

    /**
     * 获取成交价格（BigDecimal）
     */
    public BigDecimal getPriceBigDecimal() {
        if (price == null || price.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(price);
    }

    /**
     * 获取成交金额（绝对值，BigDecimal）
     */
    public BigDecimal getAbsAmount() {
        if (amount == null || amount.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(amount).abs();
    }

    /**
     * 获取佣金（绝对值，BigDecimal）
     */
    public BigDecimal getAbsCommission() {
        if (commission == null || commission.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(commission).abs();
    }

    /**
     * 获取交易日期（LocalDate）
     * 如果 tradeDate 为 "MULTI" 或为空，返回 null
     */
    public LocalDate getTradeDateAsLocalDate() {
        if (tradeDate == null || tradeDate.isEmpty() || "MULTI".equals(tradeDate)) {
            return null;
        }
        return LocalDate.parse(tradeDate, DATE_FORMATTER);
    }

    /**
     * 获取下单时间（LocalDateTime）
     */
    public LocalDateTime getOrderTimeAsLocalDateTime() {
        if (orderTime == null || orderTime.isEmpty()) return null;
        return LocalDateTime.parse(orderTime, DATETIME_FORMATTER);
    }

    /**
     * 获取成交时间（LocalDateTime）
     */
    public LocalDateTime getDateTimeAsLocalDateTime() {
        if (dateTime == null || dateTime.isEmpty()) return null;
        return LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
    }

    /**
     * 获取合约乘数（BigDecimal）
     */
    public BigDecimal getMultiplierBigDecimal() {
        if (multiplier == null || multiplier.isEmpty()) return BigDecimal.ONE;
        return new BigDecimal(multiplier);
    }

    /**
     * 是否为买入
     */
    public boolean isBuy() {
        return "BUY".equalsIgnoreCase(buySell);
    }

    /**
     * 是否为期权
     */
    public boolean isOption() {
        return "OPT".equalsIgnoreCase(assetCategory);
    }

    /**
     * 是否为股票
     */
    public boolean isStock() {
        return "STK".equalsIgnoreCase(assetCategory);
    }

    // ============ Getters and Setters ============

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getAcctAlias() { return acctAlias; }
    public void setAcctAlias(String acctAlias) { this.acctAlias = acctAlias; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getAssetCategory() { return assetCategory; }
    public void setAssetCategory(String assetCategory) { this.assetCategory = assetCategory; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getConid() { return conid; }
    public void setConid(String conid) { this.conid = conid; }

    public String getSecurityID() { return securityID; }
    public void setSecurityID(String securityID) { this.securityID = securityID; }

    public String getSecurityIDType() { return securityIDType; }
    public void setSecurityIDType(String securityIDType) { this.securityIDType = securityIDType; }

    public String getMultiplier() { return multiplier; }
    public void setMultiplier(String multiplier) { this.multiplier = multiplier; }

    public String getStrike() { return strike; }
    public void setStrike(String strike) { this.strike = strike; }

    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }

    public String getPutCall() { return putCall; }
    public void setPutCall(String putCall) { this.putCall = putCall; }

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public String getOrderTime() { return orderTime; }
    public void setOrderTime(String orderTime) { this.orderTime = orderTime; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public String getSettleDate() { return settleDate; }
    public void setSettleDate(String settleDate) { this.settleDate = settleDate; }

    public String getTradeDate() { return tradeDate; }
    public void setTradeDate(String tradeDate) { this.tradeDate = tradeDate; }

    public String getExchange() { return exchange; }
    public void setExchange(String exchange) { this.exchange = exchange; }

    public String getBuySell() { return buySell; }
    public void setBuySell(String buySell) { this.buySell = buySell; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    public String getIsAPIOrder() { return isAPIOrder; }
    public void setIsAPIOrder(String isAPIOrder) { this.isAPIOrder = isAPIOrder; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getProceeds() { return proceeds; }
    public void setProceeds(String proceeds) { this.proceeds = proceeds; }

    public String getNetCash() { return netCash; }
    public void setNetCash(String netCash) { this.netCash = netCash; }

    public String getCommission() { return commission; }
    public void setCommission(String commission) { this.commission = commission; }

    public String getCommissionCurrency() { return commissionCurrency; }
    public void setCommissionCurrency(String commissionCurrency) { this.commissionCurrency = commissionCurrency; }

    public String getTradeCharge() { return tradeCharge; }
    public void setTradeCharge(String tradeCharge) { this.tradeCharge = tradeCharge; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTraderID() { return traderID; }
    public void setTraderID(String traderID) { this.traderID = traderID; }

    // ============ toString ============

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IbkrOrderRecord{");
        sb.append("orderID='").append(orderID).append('\'');
        sb.append(", tradeDate='").append(tradeDate).append('\'');
        sb.append(", buySell='").append(buySell).append('\'');
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", assetCategory='").append(assetCategory).append('\'');

        if (isOption()) {
            sb.append(", strike='").append(strike).append('\'');
            sb.append(", expiry='").append(expiry).append('\'');
            sb.append(", putCall='").append(putCall).append('\'');
            sb.append(", multiplier='").append(multiplier).append('\'');
        }

        sb.append(", quantity='").append(quantity).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", amount='").append(amount).append('\'');
        sb.append(", commission='").append(commission).append('\'');
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", orderType='").append(orderType).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
