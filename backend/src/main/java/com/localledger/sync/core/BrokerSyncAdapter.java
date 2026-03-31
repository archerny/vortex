package com.localledger.sync.core;

/**
 * 券商同步适配器统一接口
 *
 * 采用适配器模式（策略模式 + 适配器模式组合）屏蔽各券商 API 差异。
 * 新增券商时只需实现此接口并注册为 Spring Bean，无需修改任何现有代码。
 *
 * Phase 1 职责：调用券商 API → 获取原始数据 → 日志输出
 * 后续阶段扩展：原始数据 → 统一模型转换 → 入库
 */
public interface BrokerSyncAdapter {

    /**
     * 获取该适配器支持的券商名称标识
     * 用于与 SyncRequest.brokerName 匹配
     *
     * @return 券商名称标识，如 "tiger"、"ibkr"、"futu"
     */
    String getBrokerName();

    /**
     * 执行同步操作
     *
     * Phase 1 实现：
     * 1. 调用券商 API 获取已成交订单
     * 2. 将 API 响应反序列化为券商专属模型
     * 3. 通过日志输出每条订单记录
     * 4. 返回同步结果
     *
     * @param request 同步请求参数
     * @return 同步结果
     */
    SyncResult sync(SyncRequest request);
}
