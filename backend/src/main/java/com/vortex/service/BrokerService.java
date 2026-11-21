package com.vortex.service;

import com.vortex.entity.Broker;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.BrokerSyncBatchRepository;
import com.vortex.sync.exception.SyncConflictException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.Set;

/**
 * 券商业务逻辑服务
 */
@Service
@Transactional(readOnly = true)
public class BrokerService {

    private final BrokerRepository brokerRepository;
    private final LedgerWriteLock lock;
    private final BrokerSyncBatchRepository batches;

    public BrokerService(BrokerRepository brokerRepository, LedgerWriteLock lock, BrokerSyncBatchRepository batches) {
        this.brokerRepository = brokerRepository; this.lock = lock; this.batches = batches;
    }

    /**
     * 查询所有券商
     */
    public List<Broker> findAll() {
        return brokerRepository.findAll();
    }

    /**
     * 根据ID查询券商
     */
    public Optional<Broker> findById(Long id) {
        return brokerRepository.findById(id);
    }

    /**
     * 根据券商名称查询
     */
    public Optional<Broker> findByBrokerName(String brokerName) {
        return brokerRepository.findByBrokerName(brokerName);
    }

    /**
     * 查询所有启用的券商
     */
    public List<Broker> findActiveBrokers() {
        return brokerRepository.findByIsActive(true);
    }

    /**
     * 查询所有未启用的券商
     */
    public List<Broker> findInactiveBrokers() {
        return brokerRepository.findByIsActive(false);
    }

    /**
     * 根据国家/地区查询券商
     */
    public List<Broker> findByCountry(String country) {
        return brokerRepository.findByCountry(country);
    }

    /**
     * 根据关键词模糊搜索券商
     */
    public List<Broker> searchByName(String keyword) {
        return brokerRepository.findByBrokerNameContainingIgnoreCase(keyword);
    }

    /**
     * 新增券商
     */
    @Transactional
    public Broker create(Broker broker) {
        if (broker.getId() != null) throw new IllegalArgumentException("新建券商不能指定已有ID");
        lock.acquire();
        String code = normalizeCode(broker.getBrokerCode());
        if (code != null) ensureIdle();
        requireAvailableCode(code, null); broker.setBrokerCode(code);
        if (brokerRepository.existsByBrokerName(broker.getBrokerName())) {
            throw new IllegalArgumentException("Broker name already exists: " + broker.getBrokerName());
        }
        return brokerRepository.save(broker);
    }

    /**
     * 更新券商信息
     */
    @Transactional
    public Broker update(Long id, Broker brokerData) {
        lock.acquire();
        Broker existing = brokerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Broker not found, ID: " + id));

        // 如果修改了名称，需要检查新名称是否已存在
        if (!existing.getBrokerName().equals(brokerData.getBrokerName())
                && brokerRepository.existsByBrokerName(brokerData.getBrokerName())) {
            throw new IllegalArgumentException("Broker name already exists: " + brokerData.getBrokerName());
        }

        String code = normalizeCode(brokerData.getBrokerCode());
        if (!Objects.equals(code, existing.getBrokerCode())
                || (existing.getBrokerCode() != null && !Objects.equals(existing.getIsActive(), brokerData.getIsActive()))) {
            ensureIdle();
        }
        requireAvailableCode(code, id);
        existing.setBrokerCode(code);
        existing.setBrokerName(brokerData.getBrokerName());
        existing.setCountry(brokerData.getCountry());
        existing.setDescription(brokerData.getDescription());
        existing.setEmail(brokerData.getEmail());
        existing.setPhone(brokerData.getPhone());
        existing.setIsActive(brokerData.getIsActive());

        return brokerRepository.save(existing);
    }

    /**
     * 删除券商
     */
    @Transactional
    public void delete(Long id) {
        lock.acquire();
        Broker broker = brokerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Broker not found"));
        if (broker.getBrokerCode() != null) ensureIdle();
        brokerRepository.deleteById(id);
    }

    @Transactional
    public Broker bindSyncCode(Long id, String code) {
        lock.acquire(); ensureIdle();
        Broker broker = brokerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("请选择已有的记账账户"));
        if (!Boolean.TRUE.equals(broker.getIsActive())) throw new IllegalArgumentException("请先启用该记账账户");
        if (broker.getBrokerCode() != null && !broker.getBrokerCode().equals(code)) {
            throw new IllegalArgumentException("该账户已关联其他券商同步，请选择未关联的账户");
        }
        requireAvailableCode(code, id); broker.setBrokerCode(code);
        return brokerRepository.save(broker);
    }

    private String normalizeCode(String code) {
        if (code == null || code.isBlank()) return null;
        String value = code.trim();
        if (!value.matches("[a-z][a-z0-9_-]{0,49}")) throw new IllegalArgumentException("券商代码格式不正确");
        return value;
    }

    private void requireAvailableCode(String code, Long id) {
        if (code != null) brokerRepository.findByBrokerCode(code).ifPresent(other -> {
            if (!Objects.equals(other.getId(), id)) throw new IllegalArgumentException("该同步券商已关联其他记账账户");
        });
    }

    private void ensureIdle() {
        batches.findFirstByStatusInOrderByIdDesc(Set.of("PENDING", "PROCESSING", "CLEANUP_FAILED")).ifPresent(batch -> {
            throw new SyncConflictException("同步进行中或清理未完成，暂不能更改同步账户", batch.getId(), batch.getStatus());
        });
    }
}
