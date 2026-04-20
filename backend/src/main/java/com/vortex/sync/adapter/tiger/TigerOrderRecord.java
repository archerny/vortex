package com.vortex.sync.adapter.tiger;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * 老虎证券专属原始订单模型
 *
 * 用于承载从 Tiger Open API 获取的已成交订单（get_filled_orders）原始数据。
 * 字段直接对应 Tiger API 返回的 Order 对象属性，保留原始语义，方便：
 * 1. 日志打印时与券商平台核对
 * 2. 排查问题时定位是 API 返回有误还是映射逻辑有误
 *
 * 本类不是数据库实体，不持久化，仅作为内存中的中间数据载体。
 *
 * @see <a href="https://docs.itigerup.com/docs/appendix-object-detail#order">Tiger Order 对象文档</a>
 */
public class TigerOrderRecord {

    // ============ 订单基本信息 ============

    /** 所属账户 */
    private String account;

    /** 全局订单 ID（Tiger 系统唯一标识） */
    private long orderId;

    /** 下单时间（毫秒时间戳） */
    private long orderTime;

    /** 成交时间 / 订单状态更新时间（毫秒时间戳） */
    private long tradeTime;

    /** 交易方向：BUY / SELL */
    private String action;

    /** 订单状态：FILLED / PARTIALLY_FILLED 等 */
    private String status;

    // ============ 数量与价格 ============

    /** 下单数量（原始值，需结合 quantityScale 得到真实数量） */
    private int quantity;

    /**
     * 下单数量偏移量，默认为 0
     * 真实数量 = quantity * 10^(-quantityScale)
     * 用于碎股场景，如 quantity=111, quantityScale=2 → 真实数量=1.11
     */
    private int quantityScale;

    /** 成交数量 */
    private int filledQuantity;

    /** 不含佣金的平均成交价 */
    private BigDecimal avgFillPrice;

    // ============ 费用 ============

    /** 佣金（包含佣金、印花税、证监会费等系列费用） */
    private BigDecimal commission;

    /** 税费 Goods and Service Tax */
    private BigDecimal gst;

    /** 已实现盈亏 */
    private BigDecimal realizedPnl;

    // ============ 合约信息 ============

    /** 证券代码，如 AAPL、00700 */
    private String symbol;

    /** 合约名称 */
    private String contractName;

    /**
     * 证券类型（Tiger 枚举值）：
     * STK-股票、OPT-期权、WAR-窝轮、IOPT-牛熊证、FUT-期货、FUND-基金
     */
    private String secType;

    /** 币种（Tiger 枚举值）：USD、HKD、CNH 等 */
    private String currency;

    /** 交易所 */
    private String exchange;

    /** 市场：US、HK、CN 等 */
    private String market;

    /** 合约唯一标识（股票与 symbol 相同；期权为 21 位标识符如 'AAPL 220729C00150000'） */
    private String identifier;

    /** 每手数量（期权/期货/窝轮） */
    private BigDecimal multiplier;

    /** 是否为 ETF */
    private boolean etf;

    // ============ 期权专有字段 ============

    /** 期权过期日，格式如 '20231215' */
    private String expiry;

    /** 期权行权价 */
    private BigDecimal strike;

    /** 期权方向：CALL / PUT */
    private String putCall;

    // ============ 订单类型与有效期 ============

    /** 订单类型：MKT/LMT/STP/STP_LMT/TRAIL */
    private String orderType;

    /** 限价单价格 */
    private BigDecimal limitPrice;

    // ============ Constructors ============

    public TigerOrderRecord() {
    }

    // ============ 业务方法 ============

    /**
     * 获取真实下单数量（考虑 quantityScale）
     */
    public BigDecimal getRealQuantity() {
        if (quantityScale == 0) {
            return BigDecimal.valueOf(quantity);
        }
        return BigDecimal.valueOf(quantity).movePointLeft(quantityScale);
    }

    /**
     * 获取真实成交数量（考虑 quantityScale）
     */
    public BigDecimal getRealFilledQuantity() {
        if (quantityScale == 0) {
            return BigDecimal.valueOf(filledQuantity);
        }
        return BigDecimal.valueOf(filledQuantity).movePointLeft(quantityScale);
    }

    /**
     * 获取成交日期（从 tradeTime 毫秒时间戳转换，使用东八区）
     */
    public LocalDate getTradeDate() {
        if (tradeTime <= 0) {
            return null;
        }
        return Instant.ofEpochMilli(tradeTime)
                .atZone(ZoneId.of("Asia/Shanghai"))
                .toLocalDate();
    }

    /**
     * 获取总费用（佣金 + 税费）
     */
    public BigDecimal getTotalFee() {
        BigDecimal total = BigDecimal.ZERO;
        if (commission != null) {
            total = total.add(commission);
        }
        if (gst != null) {
            total = total.add(gst);
        }
        return total;
    }

    /**
     * 获取成交金额（avgFillPrice * realFilledQuantity）
     * 注意：期权需要额外乘以 multiplier
     */
    public BigDecimal getFilledAmount() {
        if (avgFillPrice == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal amount = avgFillPrice.multiply(getRealFilledQuantity());
        if ("OPT".equals(secType) && multiplier != null) {
            amount = amount.multiply(multiplier);
        }
        return amount;
    }

    // ============ Getters and Setters ============

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(long tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityScale() {
        return quantityScale;
    }

    public void setQuantityScale(int quantityScale) {
        this.quantityScale = quantityScale;
    }

    public int getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(int filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public BigDecimal getAvgFillPrice() {
        return avgFillPrice;
    }

    public void setAvgFillPrice(BigDecimal avgFillPrice) {
        this.avgFillPrice = avgFillPrice;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public BigDecimal getRealizedPnl() {
        return realizedPnl;
    }

    public void setRealizedPnl(BigDecimal realizedPnl) {
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

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    public boolean isEtf() {
        return etf;
    }

    public void setEtf(boolean etf) {
        this.etf = etf;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public BigDecimal getStrike() {
        return strike;
    }

    public void setStrike(BigDecimal strike) {
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

    public BigDecimal getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(BigDecimal limitPrice) {
        this.limitPrice = limitPrice;
    }

    // ============ toString ============

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TigerOrderRecord{");
        sb.append("orderId=").append(orderId);
        sb.append(", tradeDate=").append(getTradeDate());
        sb.append(", action='").append(action).append('\'');
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", secType='").append(secType).append('\'');

        if ("OPT".equals(secType)) {
            sb.append(", expiry='").append(expiry).append('\'');
            sb.append(", strike=").append(strike);
            sb.append(", putCall='").append(putCall).append('\'');
            sb.append(", identifier='").append(identifier).append('\'');
        }

        sb.append(", realQty=").append(getRealQuantity());
        sb.append(", realFilledQty=").append(getRealFilledQuantity());
        sb.append(", avgFillPrice=").append(avgFillPrice);
        sb.append(", filledAmount=").append(getFilledAmount());
        sb.append(", commission=").append(commission);
        sb.append(", gst=").append(gst);
        sb.append(", totalFee=").append(getTotalFee());
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", market='").append(market).append('\'');
        sb.append(", contractName='").append(contractName).append('\'');
        sb.append(", status='").append(status).append('\'');

        if (etf) {
            sb.append(", etf=true");
        }

        sb.append('}');
        return sb.toString();
    }
}
