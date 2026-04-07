package com.localledger.sync.adapter.ibkr;

import java.util.ArrayList;
import java.util.List;

/**
 * Flex Query XML 解析结果
 *
 * 封装从 IBKR Flex Query 返回的 XML 中解析出的所有数据。
 * 包含三个层级的数据：
 * 1. Order（订单层）- 面向用户展示的核心层级
 * 2. TradeConfirm（成交层）- 最细粒度，用于审计对账
 * 3. 元数据（账户、查询范围等）
 */
public class FlexQueryParseResult {

    // ============ 元数据 ============

    /** Flex Query 名称 */
    private String queryName;

    /** 报告类型，如 TCF（Trade Confirms） */
    private String type;

    /** 账户 ID */
    private String accountId;

    /** 数据起始日期，格式 yyyyMMdd */
    private String fromDate;

    /** 数据截止日期，格式 yyyyMMdd */
    private String toDate;

    /** 报告生成时间，格式 yyyyMMdd;HHmmss */
    private String whenGenerated;

    // ============ 数据 ============

    /** Order（订单层）记录列表 */
    private List<IbkrOrderRecord> orders = new ArrayList<>();

    /** TradeConfirm（成交层）记录列表 */
    private List<IbkrTradeConfirm> tradeConfirms = new ArrayList<>();

    // ============ Constructors ============

    public FlexQueryParseResult() {
    }

    // ============ 便捷方法 ============

    /**
     * 获取 Order 记录数
     */
    public int getOrderCount() {
        return orders.size();
    }

    /**
     * 获取 TradeConfirm 记录数
     */
    public int getTradeConfirmCount() {
        return tradeConfirms.size();
    }

    /**
     * 添加一条 Order 记录
     */
    public void addOrder(IbkrOrderRecord order) {
        this.orders.add(order);
    }

    /**
     * 添加一条 TradeConfirm 记录
     */
    public void addTradeConfirm(IbkrTradeConfirm tradeConfirm) {
        this.tradeConfirms.add(tradeConfirm);
    }

    // ============ Getters and Setters ============

    public String getQueryName() { return queryName; }
    public void setQueryName(String queryName) { this.queryName = queryName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getFromDate() { return fromDate; }
    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }
    public void setToDate(String toDate) { this.toDate = toDate; }

    public String getWhenGenerated() { return whenGenerated; }
    public void setWhenGenerated(String whenGenerated) { this.whenGenerated = whenGenerated; }

    public List<IbkrOrderRecord> getOrders() { return orders; }
    public void setOrders(List<IbkrOrderRecord> orders) { this.orders = orders; }

    public List<IbkrTradeConfirm> getTradeConfirms() { return tradeConfirms; }
    public void setTradeConfirms(List<IbkrTradeConfirm> tradeConfirms) { this.tradeConfirms = tradeConfirms; }

    @Override
    public String toString() {
        return "FlexQueryParseResult{" +
                "queryName='" + queryName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", orders=" + orders.size() +
                ", tradeConfirms=" + tradeConfirms.size() +
                '}';
    }
}
