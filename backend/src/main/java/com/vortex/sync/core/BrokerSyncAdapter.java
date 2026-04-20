package com.vortex.sync.core;

/**
 * 券商同步适配器统一接口
 *
 * 采用适配器模式（策略模式 + 适配器模式组合）屏蔽各券商 API 差异。
 * 新增券商时只需实现此接口并注册为 Spring Bean，无需修改任何现有代码。
 *
 * Phase 2 职责：调用券商 API → 写入暂存表 → 导入正式表 → 返回统计结果
 */
public interface BrokerSyncAdapter {

    /**
     * 获取该适配器支持的券商技术标识符
     * 用于与 SyncRequest.brokerCode 匹配，以及关联 brokers 表的 broker_code 列
     *
     * @return 券商技术标识符，如 "tiger"、"ibkr"、"futu"
     */
    String getBrokerCode();

    /**
     * 执行同步操作
     *
     * 1. 调用券商 API 获取已成交订单
     * 2. 将 API 响应反序列化为券商专属模型
     * 3. 写入暂存表（staged table）
     * 4. 从暂存表导入到 trade_records
     * 5. 返回同步结果
     *
     * @param request 同步请求参数
     * @return 同步结果
     */
    SyncResult sync(SyncRequest request);
}
