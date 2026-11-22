package com.vortex.sync.adapter.ibkr;

import com.vortex.dto.*;
import com.vortex.service.BrokerSyncConnectionService;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IbkrSyncSettingsServiceTest {
    final BrokerSyncConnectionService connections = mock(BrokerSyncConnectionService.class);
    final IbkrSyncSettingsService service = new IbkrSyncSettingsService(connections);

    @Test void existingEndpointUsesTheSelectedManagedAccountConnection() {
        var metadata = new BrokerSyncConnectionView(7L, "Synthetic", "ibkr", true, true, true, true,
                Map.of("queryId", "123"), Set.of("token"), 7L, "SYNTH", "Synthetic");
        when(connections.save(eq(7L), any())).thenReturn(metadata);
        when(connections.getForBrokerCode("ibkr")).thenReturn(Optional.of(metadata));
        var input = new IbkrSyncSettingsUpdate();
        input.setBrokerId(7L); input.setQueryId("123"); input.setToken("synthetic-token");
        assertTrue(service.save(input).ready());
        var request = org.mockito.ArgumentCaptor.forClass(BrokerSyncConnectionUpdate.class);
        verify(connections).save(eq(7L), request.capture());
        assertEquals("ibkr", request.getValue().brokerCode());
        assertEquals("synthetic-token", request.getValue().credentials().get("token"));
        assertFalse(service.save(input).toString().contains("synthetic-token"));
    }

    @Test void unboundAccountReturnsUnconfiguredMetadataAndClearUsesBoundAccountId() {
        assertFalse(service.get().ready());
        assertFalse(service.clear().ready());
        var metadata = new BrokerSyncConnectionView(7L, "Synthetic", "ibkr", true, true, false, false,
                Map.of("queryId", ""), Set.of(), 7L, "SYNTH", "Synthetic");
        when(connections.getForBrokerCode("ibkr")).thenReturn(Optional.of(metadata));
        when(connections.clear(7L)).thenReturn(metadata);
        assertFalse(service.clear().ready());
        verify(connections).clear(7L);
    }
}
