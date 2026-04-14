package com.localledger.service;

import com.localledger.entity.BrokerSyncBatch;
import com.localledger.repository.BrokerSyncBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 券商同步批次业务逻辑服务
 *
 * 提供同步批次记录的查询功能，支持按券商名称和状态筛选。
 */
@Service
@Transactional(readOnly = true)
public class BrokerSyncBatchService {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncBatchService.class);

    private final BrokerSyncBatchRepository batchRepository;

    public BrokerSyncBatchService(BrokerSyncBatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    /**
     * List all sync batches, optionally filtered by broker name and/or status.
     *
     * @param brokerName optional broker name filter
     * @param status     optional status filter
     * @return list of matching batches, ordered by startedAt descending
     */
    public List<BrokerSyncBatch> listBatches(String brokerName, String status) {
        boolean hasBroker = brokerName != null && !brokerName.isBlank();
        boolean hasStatus = status != null && !status.isBlank();

        if (hasBroker && hasStatus) {
            logger.debug("Querying sync batches with brokerName={} and status={}", brokerName, status);
            return batchRepository.findByBrokerNameAndStatusOrderByStartedAtDesc(brokerName, status);
        } else if (hasBroker) {
            logger.debug("Querying sync batches with brokerName={}", brokerName);
            return batchRepository.findByBrokerNameOrderByStartedAtDesc(brokerName);
        } else if (hasStatus) {
            logger.debug("Querying sync batches with status={}", status);
            return batchRepository.findByStatusOrderByStartedAtDesc(status);
        } else {
            logger.debug("Querying all sync batches");
            return batchRepository.findAllByOrderByStartedAtDesc();
        }
    }

    /**
     * 根据ID查询单个同步批次
     *
     * @param id 批次ID
     * @return 批次（如存在）
     */
    public Optional<BrokerSyncBatch> findById(Long id) {
        return batchRepository.findById(id);
    }
}
