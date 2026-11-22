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
    @Test void responsesContainMetadataOnlyAndInvalidInputIsReported() throws Exception {
        var service = mock(IbkrSyncSettingsService.class);
        var mvc = MockMvcBuilders.standaloneSetup(new IbkrSyncSettingsController(service)).build();
        when(service.save(any())).thenReturn(new IbkrSyncSettingsView(1L, "Synthetic", "123", true, true));
        mvc.perform(put("/api/broker-sync/ibkr/settings").contentType("application/json")
                .content("{\"brokerId\":1,\"queryId\":\"123\",\"token\":\"synthetic-secret\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.ready").value(true))
                .andExpect(jsonPath("$.data.token").doesNotExist())
                .andExpect(content().string(not(containsString("synthetic-secret"))));
        when(service.save(any())).thenThrow(new IllegalArgumentException("请选择IBKR记账账户"));
        mvc.perform(put("/api/broker-sync/ibkr/settings").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("请选择IBKR记账账户"));
    }
}
