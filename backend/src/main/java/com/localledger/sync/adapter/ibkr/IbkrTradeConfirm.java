package com.localledger.sync.adapter.ibkr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 盈透证券 IBKR 单笔成交确认原始数据模型
 *
 * 对应 Flex Query XML 中 {@code <TradeConfirm>} 节点。
 * 这是最细粒度的交易记录，一个 Order 可能被拆分为多笔 TradeConfirm。
 * 字段直接对应 XML 属性，保留原始语义。
 *
 * 本类不是数据库实体，不持久化，仅作为内存中的中间数据载体。
 * 主要用于审计、对账场景，以及未来需要精确到每笔成交时的数据源。
 *
 * @see IbkrOrderRecord 订单层汇总
 */
public class IbkrTradeConfirm {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd;HHmmss");

    // ============ 账户信息 ============

    /** IBKR 账户 ID */
    private String accountId;

    /** 账户别名 */
    private String acctAlias;

    // ============ 合约信息 ============

    /** 币种 */
    private String currency;

    /** 资产类别：STK / OPT / FUT / CASH / FUND */
    private String assetCategory;

    /** 证券代码 */
    private String symbol;

    /** 证券描述 */
    private String description;

    /** IBKR 合约 ID */
    private String conid;

    /** 证券 ID（ISIN） */
    private String securityID;

    /** 证券 ID 类型 */
    private String securityIDType;

    /** 合约乘数 */
    private String multiplier;

    // ============ 期权专有字段 ============

    /** 行权价 */
    private String strike;

    /** 到期日 */
    private String expiry;

    /** 期权类型：C / P */
    private String putCall;

    // ============ 交易标识 ============

    /**
     * 交易类型：
     * ExchTrade - 交易所撮合成交
     * BookTrade - 账簿交易（内部转账、ACATS 转入等）
     */
    private String transactionType;

    /** 成交确认 ID（全局唯一，推荐用作交易记录主键） */
    private String tradeID;

    /** 所属订单 ID */
    private String orderID;

    /** 执行 ID（交易所分配） */
    private String execID;

    /** 券商内部订单 ID */
    private String brokerageOrderID;

    /** 订单引用 */
    private String orderReference;

    // ============ 时间信息 ============

    /** 下单时间 */
    private String orderTime;

    /** 成交时间 */
    private String dateTime;

    /** 交割日期 */
    private String settleDate;

    /** 交易日期 */
    private String tradeDate;

    // ============ 交易信息 ============

    /** 成交交易所 */
    private String exchange;

    /** 买卖方向：BUY / SELL */
    private String buySell;

    /** 成交数量（卖出为负数） */
    private String quantity;

    /** 成交价格 */
    private String price;

    /** 成交金额 */
    private String amount;

    /** 收入/支出 */
    private String proceeds;

    /** 净现金流 */
    private String netCash;

    // ============ 费用 ============

    /** 佣金 */
    private String commission;

    /** 佣金币种 */
    private String commissionCurrency;

    /** 交易附加费 */
    private String tradeCharge;

    // ============ 其他 ============

    /**
     * 交易代码标记（可组合，用分号分隔）：
     * O - Opening（开仓）
     * C - Closing（平仓）
     * P - Partial execution（部分成交）
     * A - ACATS 转入
     * Ep - 期权到期（Expired）
     */
    private String code;

    /** 订单类型：LMT / MKT 等 */
    private String orderType;

    /** 交易员 ID */
    private String traderID;

    /** 是否通过 API 下单：Y / N */
    private String isAPIOrder;

    // ============ Constructors ============

    public IbkrTradeConfirm() {
    }

    // ============ 业务方法 ============

    /**
     * 获取成交数量（绝对值）
     */
    public BigDecimal getAbsQuantity() {
        if (quantity == null || quantity.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(quantity).abs();
    }

    /**
     * 获取成交价格
     */
    public BigDecimal getPriceBigDecimal() {
        if (price == null || price.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(price);
    }

    /**
     * 获取成交金额（绝对值）
     */
    public BigDecimal getAbsAmount() {
        if (amount == null || amount.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(amount).abs();
    }

    /**
     * 获取佣金（绝对值）
     */
    public BigDecimal getAbsCommission() {
        if (commission == null || commission.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(commission).abs();
    }

    /**
     * 获取交易日期
     */
    public LocalDate getTradeDateAsLocalDate() {
        if (tradeDate == null || tradeDate.isEmpty()) return null;
        return LocalDate.parse(tradeDate, DATE_FORMATTER);
    }

    /**
     * 获取成交时间
     */
    public LocalDateTime getDateTimeAsLocalDateTime() {
        if (dateTime == null || dateTime.isEmpty()) return null;
        return LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
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
     * 是否为交易所撮合成交
     */
    public boolean isExchangeTrade() {
        return "ExchTrade".equalsIgnoreCase(transactionType);
    }

    /**
     * 是否为账簿交易（内部转账等）
     */
    public boolean isBookTrade() {
        return "BookTrade".equalsIgnoreCase(transactionType);
    }

    /**
     * 是否为部分成交
     */
    public boolean isPartialExecution() {
        return code != null && code.contains("P");
    }

    /**
     * 是否为开仓
     */
    public boolean isOpening() {
        return code != null && code.contains("O");
    }

    /**
     * 是否为平仓
     */
    public boolean isClosing() {
        return code != null && code.contains("C");
    }

    /**
     * 是否为期权到期
     */
    public boolean isExpired() {
        return code != null && code.contains("Ep");
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

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getTradeID() { return tradeID; }
    public void setTradeID(String tradeID) { this.tradeID = tradeID; }

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public String getExecID() { return execID; }
    public void setExecID(String execID) { this.execID = execID; }

    public String getBrokerageOrderID() { return brokerageOrderID; }
    public void setBrokerageOrderID(String brokerageOrderID) { this.brokerageOrderID = brokerageOrderID; }

    public String getOrderReference() { return orderReference; }
    public void setOrderReference(String orderReference) { this.orderReference = orderReference; }

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

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    public String getTraderID() { return traderID; }
    public void setTraderID(String traderID) { this.traderID = traderID; }

    public String getIsAPIOrder() { return isAPIOrder; }
    public void setIsAPIOrder(String isAPIOrder) { this.isAPIOrder = isAPIOrder; }

    // ============ toString ============

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IbkrTradeConfirm{");
        sb.append("tradeID='").append(tradeID).append('\'');
        sb.append(", orderID='").append(orderID).append('\'');
        sb.append(", tradeDate='").append(tradeDate).append('\'');
        sb.append(", buySell='").append(buySell).append('\'');
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", assetCategory='").append(assetCategory).append('\'');
        sb.append(", transactionType='").append(transactionType).append('\'');

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
        sb.append(", exchange='").append(exchange).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
