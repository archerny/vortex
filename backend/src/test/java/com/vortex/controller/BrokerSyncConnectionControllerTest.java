package com.vortex.controller;

import com.vortex.dto.BrokerSyncConnectionUpdate;
import com.vortex.dto.BrokerSyncConnectionView;
import com.vortex.service.BrokerSyncConnectionService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Map;
import java.util.Set;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BrokerSyncConnectionControllerTest {
    @Test void writesSecretsForThePathAccountAndReturnsOnlyMetadata() throws Exception {
        var service = mock(BrokerSyncConnectionService.class);
        var mvc = MockMvcBuilders.standaloneSetup(new BrokerSyncConnectionController(service)).build();
        when(service.save(eq(2L), any())).thenReturn(new BrokerSyncConnectionView(2L, "Synthetic", "tiger", true, true, true, true,
                Map.of("tigerId", "100", "account", "SYNTH"), Set.of("privateKey")));
        mvc.perform(put("/api/broker-sync/connections/2").contentType("application/json")
                .content("{\"brokerCode\":\"tiger\",\"credentials\":{\"tigerId\":\"100\",\"account\":\"SYNTH\",\"privateKey\":\"synthetic-key\"}}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.ready").value(true))
                .andExpect(jsonPath("$.data.values.privateKey").doesNotExist())
                .andExpect(jsonPath("$.data.credentials").doesNotExist());
        var input = org.mockito.ArgumentCaptor.forClass(BrokerSyncConnectionUpdate.class);
        verify(service).save(eq(2L), input.capture());
        org.junit.jupiter.api.Assertions.assertEquals("synthetic-key", input.getValue().credentials().get("privateKey"));
    }
}
