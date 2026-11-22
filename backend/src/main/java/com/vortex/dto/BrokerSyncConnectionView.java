package com.vortex.dto;

import java.util.Map;
import java.util.Set;

public record BrokerSyncConnectionView(Long brokerId, String brokerName, String brokerCode,
        boolean active, boolean supported, boolean configured, boolean ready,
        Map<String, String> values, Set<String> configuredSecrets,
        Long brokerAccountId, String accountId, String accountName) {
    public BrokerSyncConnectionView(Long brokerId, String brokerName, String brokerCode,
            boolean active, boolean supported, boolean configured, boolean ready,
            Map<String, String> values, Set<String> configuredSecrets) {
        this(brokerId, brokerName, brokerCode, active, supported, configured, ready, values, configuredSecrets,
                null, null, null);
    }
}
