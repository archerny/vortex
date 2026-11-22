package com.vortex.service;

import com.vortex.entity.Broker;
import com.vortex.repository.BrokerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

/**
 * 券商业务逻辑服务
 */
@Service
@Transactional(readOnly = true)
public class BrokerService {

    private final BrokerRepository brokerRepository;
    public BrokerService(BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
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
        broker.setIntroduction(normalizeIntroduction(broker.getIntroduction()));
        String code = normalizeCode(broker.getBrokerCode());
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
        Broker existing = brokerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Broker not found, ID: " + id));

        // 如果修改了名称，需要检查新名称是否已存在
        if (!existing.getBrokerName().equals(brokerData.getBrokerName())
                && brokerRepository.existsByBrokerName(brokerData.getBrokerName())) {
            throw new IllegalArgumentException("Broker name already exists: " + brokerData.getBrokerName());
        }

        String code = normalizeCode(brokerData.getBrokerCode());
        requireAvailableCode(code, id);
        String introduction = normalizeIntroduction(brokerData.getIntroduction());
        existing.setBrokerCode(code);
        existing.setBrokerName(brokerData.getBrokerName());
        existing.setCountry(brokerData.getCountry());
        if (brokerData.getIntroduction() != null) existing.setIntroduction(introduction);

        return brokerRepository.save(existing);
    }

    /**
     * 删除券商
     */
    @Transactional
    public void delete(Long id) {
        brokerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Broker not found"));
        brokerRepository.deleteById(id);
    }

    private String normalizeCode(String code) {
        if (code == null || code.isBlank()) return null;
        String value = code.trim();
        if (!value.matches("[a-z][a-z0-9_-]{0,49}")) throw new IllegalArgumentException("券商代码格式不正确");
        return value;
    }

    private String normalizeIntroduction(String value) {
        if (value == null) return null;
        String text = value.trim();
        if (text.length() > 500) throw new IllegalArgumentException("券商简介最多500字符");
        return text.isEmpty() ? null : text;
    }

    private void requireAvailableCode(String code, Long id) {
        if (code != null) brokerRepository.findByBrokerCode(code).ifPresent(other -> {
            if (!Objects.equals(other.getId(), id)) throw new IllegalArgumentException("该券商代码已被其他券商使用");
        });
    }

}
