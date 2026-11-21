package com.vortex.dto;

public record IbkrSyncSettingsView(Long brokerId, String brokerName, String queryId,
                                   boolean tokenConfigured, boolean ready) { }
