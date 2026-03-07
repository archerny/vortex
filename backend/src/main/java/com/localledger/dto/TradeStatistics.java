package com.localledger.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 交易记录统计数据 DTO
 * 用于返回交易记录页面顶部的统计信息
 */
public class TradeStatistics {

    /** 总交易次数 */
    private Long totalCount;

    /** 股票交易次数 */
    private Long stockCount;

    /** CALL期权交易次数 */
    private Long optionCallCount;

    /** PUT期权交易次数 */
    private Long optionPutCount;

    /** ETF交易次数 */
    private Long etfCount;

    /** 总交易费用（USD） */
    private BigDecimal totalFeeUSD;

    /** 总交易费用（CNY） */
    private BigDecimal totalFeeCNY;

    /** 总交易费用（HKD） */
    private BigDecimal totalFeeHKD;

    /** 涉及证券数量（underlyingSymbol 去重计数） */
    private Long distinctSymbolCount;

    /** Top 5 最常交易的证券，每项包含 symbol 和 count */
    private List<Map<String, Object>> topSymbols;

    public TradeStatistics() {
    }

    // ============ Getters and Setters ============

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getStockCount() {
        return stockCount;
    }

    public void setStockCount(Long stockCount) {
        this.stockCount = stockCount;
    }

    public Long getOptionCallCount() {
        return optionCallCount;
    }

    public void setOptionCallCount(Long optionCallCount) {
        this.optionCallCount = optionCallCount;
    }

    public Long getOptionPutCount() {
        return optionPutCount;
    }

    public void setOptionPutCount(Long optionPutCount) {
        this.optionPutCount = optionPutCount;
    }

    public Long getEtfCount() {
        return etfCount;
    }

    public void setEtfCount(Long etfCount) {
        this.etfCount = etfCount;
    }

    public BigDecimal getTotalFeeUSD() {
        return totalFeeUSD;
    }

    public void setTotalFeeUSD(BigDecimal totalFeeUSD) {
        this.totalFeeUSD = totalFeeUSD;
    }

    public BigDecimal getTotalFeeCNY() {
        return totalFeeCNY;
    }

    public void setTotalFeeCNY(BigDecimal totalFeeCNY) {
        this.totalFeeCNY = totalFeeCNY;
    }

    public BigDecimal getTotalFeeHKD() {
        return totalFeeHKD;
    }

    public void setTotalFeeHKD(BigDecimal totalFeeHKD) {
        this.totalFeeHKD = totalFeeHKD;
    }

    public Long getDistinctSymbolCount() {
        return distinctSymbolCount;
    }

    public void setDistinctSymbolCount(Long distinctSymbolCount) {
        this.distinctSymbolCount = distinctSymbolCount;
    }

    public List<Map<String, Object>> getTopSymbols() {
        return topSymbols;
    }

    public void setTopSymbols(List<Map<String, Object>> topSymbols) {
        this.topSymbols = topSymbols;
    }
}
