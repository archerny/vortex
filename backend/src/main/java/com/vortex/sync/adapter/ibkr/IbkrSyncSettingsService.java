package com.vortex.sync.adapter.ibkr;

import com.vortex.dto.IbkrSyncSettingsUpdate;
import com.vortex.dto.IbkrSyncSettingsView;
import com.vortex.entity.IbkrSyncSettings;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.BrokerSyncBatchRepository;
import com.vortex.repository.IbkrSyncSettingsRepository;
import com.vortex.service.BrokerService;
import com.vortex.service.LedgerWriteLock;
import com.vortex.sync.exception.SyncConflictException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
public class IbkrSyncSettingsService {
    private final IbkrSyncSettingsRepository settings;
    private final BrokerRepository brokers;
    private final BrokerService brokerService;
    private final BrokerSyncBatchRepository batches;
    private final LedgerWriteLock lock;
    private final IbkrSecretCipher cipher;
    private final IbkrFlexQueryProperties properties;

    public IbkrSyncSettingsService(IbkrSyncSettingsRepository settings, BrokerRepository brokers,
            BrokerService brokerService, BrokerSyncBatchRepository batches, LedgerWriteLock lock,
            IbkrSecretCipher cipher, IbkrFlexQueryProperties properties) {
        this.settings = settings; this.brokers = brokers; this.brokerService = brokerService;
        this.batches = batches; this.lock = lock; this.cipher = cipher; this.properties = properties;
    }

    @PostConstruct
    public void loadPersistedConfiguration() {
        properties.useDatabaseCredentials(this::loadCredentials);
    }

    public IbkrFlexQueryProperties.Credentials loadCredentials() {
        return settings.findById("ibkr").map(row -> new IbkrFlexQueryProperties.Credentials(
                row.getTokenCiphertext() == null ? null : cipher.decrypt(row.getTokenCiphertext()), row.getQueryId()))
                .orElseGet(() -> new IbkrFlexQueryProperties.Credentials(null, null));
    }

    public IbkrSyncSettingsView get() {
        var broker = brokers.findByBrokerCode("ibkr").orElse(null);
        var value = loadCredentials();
        boolean token = value.token() != null && !value.token().isBlank();
        return new IbkrSyncSettingsView(broker == null ? null : broker.getId(), broker == null ? null : broker.getBrokerName(),
                value.queryId(), token, broker != null && Boolean.TRUE.equals(broker.getIsActive()) && token && value.queryId() != null);
    }

    @Transactional
    public IbkrSyncSettingsView save(IbkrSyncSettingsUpdate input) {
        if (input.getBrokerId() == null) throw new IllegalArgumentException("请选择IBKR记账账户");
        String queryId = input.getQueryId() == null ? "" : input.getQueryId().trim();
        if (!queryId.matches("[0-9]{1,64}")) throw new IllegalArgumentException("请输入Trade Confirmation查询的数字Query ID");
        String token = input.getToken() == null ? "" : input.getToken().trim();
        if (token.length() > 4096 || token.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException("Flex Token格式不正确");
        }
        lock.acquire(); ensureIdle();
        brokerService.bindSyncCode(input.getBrokerId(), "ibkr");
        IbkrSyncSettings row = settings.findById("ibkr").orElseGet(IbkrSyncSettings::new);
        String current = row.getTokenCiphertext() == null ? null : cipher.decrypt(row.getTokenCiphertext());
        if (token.isEmpty()) token = current;
        if (token == null || token.isBlank()) throw new IllegalArgumentException("首次配置请输入Flex Token");
        row.setTokenCiphertext(cipher.encrypt(token)); row.setQueryId(queryId);
        settings.saveAndFlush(row);
        var broker = brokers.findByBrokerCode("ibkr").orElseThrow();
        return new IbkrSyncSettingsView(broker.getId(), broker.getBrokerName(), queryId, true, true);
    }

    @Transactional
    public IbkrSyncSettingsView clear() {
        lock.acquire(); ensureIdle();
        IbkrSyncSettings row = settings.findById("ibkr").orElseGet(IbkrSyncSettings::new);
        row.setTokenCiphertext(null); row.setQueryId(null); settings.saveAndFlush(row);
        var broker = brokers.findByBrokerCode("ibkr").orElse(null);
        return new IbkrSyncSettingsView(broker == null ? null : broker.getId(), broker == null ? null : broker.getBrokerName(),
                null, false, false);
    }

    private void ensureIdle() {
        batches.findFirstByStatusInOrderByIdDesc(Set.of("PENDING", "PROCESSING", "CLEANUP_FAILED")).ifPresent(batch -> {
            throw new SyncConflictException("同步进行中或清理未完成，暂不能修改IBKR配置", batch.getId(), batch.getStatus());
        });
    }

}
