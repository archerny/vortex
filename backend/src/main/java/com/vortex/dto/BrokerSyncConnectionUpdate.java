package com.vortex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record BrokerSyncConnectionUpdate(String brokerCode,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Map<String, String> credentials) {
    @Override public String toString() { return "BrokerSyncConnectionUpdate[redacted]"; }
}
