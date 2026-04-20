package com.vortex.sync.core;

import com.vortex.entity.Broker;
import com.vortex.repository.BrokerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 券商同步编排服务
 *
 * 作为同步模块的核心入口，负责：
 * 1. 管理所有已注册的 BrokerSyncAdapter
 * 2. 根据请求参数路由到对应的适配器
 * 3. 执行同步并返回结果
 * 4. 提供支持的券商信息（关联 brokers 表）
 *
 * 通过 Spring 的自动注入机制，所有实现了 BrokerSyncAdapter 接口的 Bean
 * 会自动注册到 adapters 列表中，新增券商适配器无需修改此类。
 */
@Service
public class BrokerSyncService {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncService.class);

    /** 按券商技术标识索引的适配器 Map */
    private final Map<String, BrokerSyncAdapter> adapterMap;

    private final BrokerRepository brokerRepository;

    /**
     * 构造方法：Spring 自动注入所有 BrokerSyncAdapter 实现
     */
    public BrokerSyncService(List<BrokerSyncAdapter> adapters, BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
        this.adapterMap = adapters.stream()
                .collect(Collectors.toMap(BrokerSyncAdapter::getBrokerCode, Function.identity()));

        logger.info("[BrokerSync] Registered {} broker adapter(s): {}",
                adapterMap.size(), adapterMap.keySet());
    }

    /**
     * 获取指定 brokerCode 的适配器
     *
     * @param brokerCode 券商技术标识
     * @return 适配器，如不存在则返回 null
     */
    public BrokerSyncAdapter getAdapter(String brokerCode) {
        return adapterMap.get(brokerCode);
    }

    /**
     * 执行同步
     *
     * @param request 同步请求参数（包含券商标识、时间范围等）
     * @return 同步结果
     */
    public SyncResult sync(SyncRequest request) {
        String brokerCode = request.getBrokerCode();

        logger.info("[BrokerSync] Received sync request: {}", request);

        // Find adapter
        BrokerSyncAdapter adapter = adapterMap.get(brokerCode);
        if (adapter == null) {
            String errorMsg = String.format("Unsupported broker: %s, available: %s", brokerCode, adapterMap.keySet());
            logger.warn("[BrokerSync] {}", errorMsg);
            return SyncResult.failure(brokerCode, errorMsg, 0);
        }

        // Execute sync
        logger.info("[BrokerSync] Starting sync with {} adapter...", brokerCode);
        SyncResult result = adapter.sync(request);
        logger.info("[BrokerSync] Sync completed: {}", result);

        return result;
    }

    /**
     * 获取所有已注册且在 brokers 表中有记录的券商信息列表
     *
     * 只有在 brokers 表中有对应 broker_code 记录的同步器才会返回，
     * 避免用户看到未配置的同步器。
     *
     * @return 支持同步的券商信息列表
     */
    public List<BrokerSyncInfo> getSupportedBrokerInfos() {
        List<BrokerSyncInfo> result = new ArrayList<>();

        for (String brokerCode : adapterMap.keySet()) {
            Optional<Broker> brokerOpt = brokerRepository.findByBrokerCode(brokerCode);
            if (brokerOpt.isPresent()) {
                Broker broker = brokerOpt.get();
                result.add(new BrokerSyncInfo(
                        brokerCode,
                        broker.getBrokerName(),
                        broker.getCountry(),
                        broker.getId()
                ));
            } else {
                logger.debug("[BrokerSync] Adapter '{}' registered but no matching broker record found, skipping", brokerCode);
            }
        }

        return result;
    }

    /**
     * 检查指定的 brokerCode 是否有已注册的适配器
     */
    public boolean isSupported(String brokerCode) {
        return adapterMap.containsKey(brokerCode);
    }
}
