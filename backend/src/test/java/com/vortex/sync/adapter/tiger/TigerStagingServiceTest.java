package com.vortex.sync.adapter.tiger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link TigerStagingService}.
 *
 * <p>Primary focus: verify the v2.4.2 fail-fast contract. Per-record
 * exceptions from {@link TigerStagingWorker#stageOrder} must propagate so
 * that {@link TigerSyncAdapter} can escalate the whole batch to cleanup
 * instead of silently dropping rows.</p>
 */
@ExtendWith(MockitoExtension.class)
class TigerStagingServiceTest {

    @Mock
    private TigerStagingWorker stagingWorker;

    @InjectMocks
    private TigerStagingService stagingService;

    @Test
    @DisplayName("empty records: returns zero-count result without touching worker")
    void emptyRecordsReturnsZeroCount() {
        TigerStagingService.StagingResult result =
                stagingService.stageAll(1L, Collections.emptyList());

        assertEquals(0, result.attempted);
        assertEquals(0, result.inserted);
        assertEquals(0, result.skipped);
        verifyNoInteractions(stagingWorker);
    }

    @Test
    @DisplayName("all newly inserted: counts inserted, zero skipped")
    void allNewlyInsertedCountsCorrectly() {
        List<TigerOrderRecord> records = List.of(
                orderRecord(1L, "AAPL"),
                orderRecord(2L, "TSLA"),
                orderRecord(3L, "MSFT"));
        when(stagingWorker.stageOrder(eq(1L), any(TigerOrderRecord.class))).thenReturn(true);

        TigerStagingService.StagingResult result = stagingService.stageAll(1L, records);

        assertEquals(3, result.attempted);
        assertEquals(3, result.inserted);
        assertEquals(0, result.skipped);
    }

    @Test
    @DisplayName("duplicates skipped: worker returns false → counted as skipped, not failed")
    void duplicatesAreSkipped() {
        List<TigerOrderRecord> records = List.of(
                orderRecord(1L, "AAPL"),
                orderRecord(2L, "TSLA"));
        when(stagingWorker.stageOrder(eq(1L), any(TigerOrderRecord.class))).thenReturn(false);

        TigerStagingService.StagingResult result = stagingService.stageAll(1L, records);

        assertEquals(2, result.attempted);
        assertEquals(0, result.inserted);
        assertEquals(2, result.skipped);
    }

    @Test
    @DisplayName("v2 fail-fast: worker exception propagates (no silent data loss)")
    void workerExceptionPropagates() {
        List<TigerOrderRecord> records = List.of(
                orderRecord(1L, "AAPL"),
                orderRecord(2L, "TSLA"),
                orderRecord(3L, "MSFT"));

        when(stagingWorker.stageOrder(eq(1L), any(TigerOrderRecord.class)))
                .thenReturn(true)                                          // record #1 OK
                .thenThrow(new RuntimeException("DB constraint violated")) // record #2 explodes
                .thenReturn(true);                                         // would succeed, but never reached

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> stagingService.stageAll(1L, records));

        assertTrue(thrown.getMessage().contains("DB constraint violated"));

        // Loop aborted at record #2 — record #3 must NOT have been attempted.
        verify(stagingWorker, times(2)).stageOrder(eq(1L), any(TigerOrderRecord.class));
    }

    // -------------------------------------------------------------------------

    private static TigerOrderRecord orderRecord(long orderId, String symbol) {
        TigerOrderRecord r = new TigerOrderRecord();
        r.setOrderId(orderId);
        r.setSymbol(symbol);
        return r;
    }
}
