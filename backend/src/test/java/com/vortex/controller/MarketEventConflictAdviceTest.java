package com.vortex.controller;

import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MarketEventConflictAdviceTest {
    @Mock TradeRecordService trades;
    @Test void conflictUses409AndKeepsMessageWithBlockingEventMetadata() throws Exception {
        TradeRecordController controller = new TradeRecordController();
        ReflectionTestUtils.setField(controller, "tradeRecordService", trades);
        when(trades.create(any(TradeRecord.class))).thenThrow(new MarketEventConflictException(
                "HISTORY_LOCKED", "synthetic conflict reason", 7L, TriggerRefType.STOCK_SPLIT,
                LocalDate.of(2026, 3, 10), Set.of("SYNTH-A")));
        MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new MarketEventConflictAdvice()).build()
                .perform(post("/api/trade-records").contentType("application/json").content("{}"))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.message").value("synthetic conflict reason"))
                .andExpect(jsonPath("$.code").value("HISTORY_LOCKED"))
                .andExpect(jsonPath("$.blockingEventId").value(7))
                .andExpect(jsonPath("$.blockingEventType").value("STOCK_SPLIT"))
                .andExpect(jsonPath("$.eventDate").value("2026-03-10"))
                .andExpect(jsonPath("$.symbols[0]").value("SYNTH-A"));
    }
}
