package com.vortex.controller;

import com.vortex.dto.IbkrSyncSettingsView;
import com.vortex.sync.adapter.ibkr.IbkrSyncSettingsService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.containsString;

class IbkrSyncSettingsControllerTest {
    @Test void responsesContainMetadataOnlyAndStorageErrorsAreSafe() throws Exception {
        var service = mock(IbkrSyncSettingsService.class);
        var mvc = MockMvcBuilders.standaloneSetup(new IbkrSyncSettingsController(service)).build();
        when(service.save(any())).thenReturn(new IbkrSyncSettingsView(1L, "Synthetic", "123", true, true));
        mvc.perform(put("/api/broker-sync/ibkr/settings").contentType("application/json")
                .content("{\"brokerId\":1,\"queryId\":\"123\",\"token\":\"synthetic-secret\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.ready").value(true))
                .andExpect(jsonPath("$.data.token").doesNotExist())
                .andExpect(content().string(not(containsString("synthetic-secret"))));
        when(service.get()).thenThrow(new IllegalStateException("synthetic-secret ciphertext"));
        mvc.perform(get("/api/broker-sync/ibkr/settings")).andExpect(status().isInternalServerError())
                .andExpect(content().string(not(containsString("synthetic-secret"))));
    }
}
