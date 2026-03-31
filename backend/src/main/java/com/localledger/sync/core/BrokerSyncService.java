package com.localledger.sync.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 券商同步编排服务
 *
 * 作为同步模块的核心入口，负责：
 * 1. 管理所有已注册的 BrokerSyncAdapter
 * 2. 根据请求参数路由到对应的适配器
 * 3. 执行同步并返回结果
 *
 * 通过 Spring 的自动注入机制，所有实现了 BrokerSyncAdapter 接口的 Bean
 * 会自动注册到 adapters 列表中，新增券商适配器无需修改此类。
 */
@Service
public class BrokerSyncService {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncService.class);

    /** 按券商名称索引的适配器 Map */
    private final Map<String, BrokerSyncAdapter> adapterMap;

    /**
     * 构造方法：Spring 自动注入所有 BrokerSyncAdapter 实现
     */
    public BrokerSyncService(List<BrokerSyncAdapter> adapters) {
        this.adapterMap = adapters.stream()
                .collect(Collectors.toMap(BrokerSyncAdapter::getBrokerName, Function.identity()));

        logger.info("[BrokerSync] 已注册 {} 个券商适配器: {}",
                adapterMap.size(), adapterMap.keySet());
    }

    /**
     * 执行同步
     *
     * @param request 同步请求参数（包含券商名称、时间范围等）
     * @return 同步结果
     */
    public SyncResult sync(SyncRequest request) {
        String brokerName = request.getBrokerName();

        logger.info("[BrokerSync] 收到同步请求: {}", request);

        // 查找适配器
        BrokerSyncAdapter adapter = adapterMap.get(brokerName);
        if (adapter == null) {
            String errorMsg = String.format("不支持的券商: %s，当前支持: %s", brokerName, adapterMap.keySet());
            logger.warn("[BrokerSync] {}", errorMsg);
            return SyncResult.failure(brokerName, errorMsg, 0);
        }

        // 执行同步
        logger.info("[BrokerSync] 使用 {} 适配器开始同步...", brokerName);
        SyncResult result = adapter.sync(request);
        logger.info("[BrokerSync] 同步完成: {}", result);

        return result;
    }

    /**
     * 获取所有已注册的券商名称
     */
    public List<String> getSupportedBrokers() {
        return List.copyOf(adapterMap.keySet());
    }
}
