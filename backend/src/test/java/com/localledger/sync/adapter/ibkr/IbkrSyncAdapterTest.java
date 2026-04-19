package com.localledger.sync.adapter.ibkr;

import com.localledger.repository.IbkrStagedOrderRepository;
import com.localledger.service.BrokerSyncBatchService;
import com.localledger.sync.core.SyncRequest;
import com.localledger.sync.core.SyncResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IbkrSyncAdapter}.
 *
 * Covers:
 * - getBrokerCode() returns "ibkr"
 * - Sync with unconfigured credentials returns failure
 * - Sync with valid config: single window (≤365 days)
 * - Sync with valid config: multiple windows (>365 days, date splitting)
 * - Sync with no date range (defaults)
 * - Exception handling during sync
 * - Phase updates during sync lifecycle
 */
@ExtendWith(MockitoExtension.class)
class IbkrSyncAdapterTest {

    @Mock
    private IbkrFlexQueryProperties properties;

    @Mock
    private IbkrFlexClient flexClient;

    @Mock
    private FlexQueryParser flexQueryParser;

    @Mock
    private IbkrStagingService stagingService;

    @Mock
    private IbkrImportService importService;

    @Mock
    private BrokerSyncBatchService batchService;

    @Mock
    private IbkrStagedOrderRepository stagedOrderRepository;

    private IbkrSyncAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new IbkrSyncAdapter(properties, flexClient, flexQueryParser,
                stagingService, importService, batchService, stagedOrderRepository);
    }

    @Test
    @DisplayName("getBrokerCode() should return 'ibkr'")
    void getBrokerCodeShouldReturnIbkr() {
        assertEquals("ibkr", adapter.getBrokerCode());
    }

    @Nested
    @DisplayName("sync() - configuration validation")
    class ConfigValidationTest {

        @Test
        @DisplayName("should return failure when credentials not configured")
        void shouldFailWhenNotConfigured() {
            when(properties.isConfigured()).thenReturn(false);
            SyncRequest request = new SyncRequest("ibkr");

            SyncResult result = adapter.sync(request);

            assertFalse(result.isSuccess());
            assertEquals("ibkr", result.getBrokerCode());
            assertTrue(result.getMessage().contains("not configured"));
            verify(flexClient, never()).fetchReport(anyString(), anyString());
        }
    }

    @Nested
    @DisplayName("sync() - single window (≤365 days)")
    class SingleWindowTest {

        @Test
        @DisplayName("should fetch single window for short date range")
        void shouldFetchSingleWindow() {
            when(properties.isConfigured()).thenReturn(true);

            SyncRequest request = new SyncRequest("ibkr", "2026-01-01", "2026-03-31");
            request.setBatchId(1L);

            String xmlReport = "<FlexQueryResponse/>";
            when(flexClient.fetchReport("20260101", "20260331")).thenReturn(xmlReport);

            FlexQueryParseResult parseResult = buildParseResult(50, 5);
            when(flexQueryParser.parse(xmlReport)).thenReturn(parseResult);

            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "IMPORTED")).thenReturn(45L);
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "SKIPPED")).thenReturn(5L);
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "FAILED")).thenReturn(0L);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(50, result.getTotalRecords());
            assertEquals(45, result.getImportedCount());
            assertEquals(5, result.getSkippedCount());
            assertEquals(0, result.getFailedCount());
            assertEquals("ibkr", result.getBrokerCode());
            assertTrue(result.getDurationMs() >= 0);

            verify(flexClient, times(1)).fetchReport(anyString(), anyString());
            verify(batchService).updatePhase(1L, "STAGING");
            verify(batchService).updatePhase(1L, "IMPORTING");
            verify(stagingService).stageAll(eq(1L), anyList(), anyList());
            verify(importService).importAll(1L);
        }
    }

    @Nested
    @DisplayName("sync() - multiple windows (>365 days)")
    class MultiWindowTest {

        @Test
        @DisplayName("should split into multiple windows for >365 day range")
        void shouldSplitIntoMultipleWindows() {
            when(properties.isConfigured()).thenReturn(true);

            // 2024-01-01 to 2026-01-01 = 731 days → 2 windows (no overlap):
            //   Window 1: 2024-01-01 ~ 2024-12-31 (365 days)
            //   Window 2: 2025-01-01 ~ 2026-01-01 (366 days, capped to endDate)
            SyncRequest request = new SyncRequest("ibkr", "2024-01-01", "2026-01-01");
            request.setBatchId(2L);

            // Mock: each window returns some orders
            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<xml/>");

            FlexQueryParseResult result1 = buildParseResult(100, 10);
            FlexQueryParseResult result2 = buildParseResult(100, 10);
            when(flexQueryParser.parse("<xml/>"))
                    .thenReturn(result1)
                    .thenReturn(result2);

            when(stagedOrderRepository.countByBatchIdAndStatus(2L, "IMPORTED")).thenReturn(190L);
            when(stagedOrderRepository.countByBatchIdAndStatus(2L, "SKIPPED")).thenReturn(10L);
            when(stagedOrderRepository.countByBatchIdAndStatus(2L, "FAILED")).thenReturn(0L);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(200, result.getTotalRecords());

            // Should have made 2 calls (windows don't overlap, each ≤365 days)
            verify(flexClient, times(2)).fetchReport(anyString(), anyString());
        }

        @Test
        @DisplayName("should fetch single day when startDate equals endDate")
        void shouldFetchSingleDayWhenStartEqualsEnd() {
            when(properties.isConfigured()).thenReturn(true);

            // Same day: 2026-03-15 to 2026-03-15
            SyncRequest request = new SyncRequest("ibkr", "2026-03-15", "2026-03-15");
            request.setBatchId(6L);

            when(flexClient.fetchReport("20260315", "20260315")).thenReturn("<xml/>");

            FlexQueryParseResult parseResult = buildParseResult(3, 1);
            when(flexQueryParser.parse("<xml/>")).thenReturn(parseResult);

            when(stagedOrderRepository.countByBatchIdAndStatus(6L, "IMPORTED")).thenReturn(3L);
            when(stagedOrderRepository.countByBatchIdAndStatus(6L, "SKIPPED")).thenReturn(0L);
            when(stagedOrderRepository.countByBatchIdAndStatus(6L, "FAILED")).thenReturn(0L);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(3, result.getTotalRecords());
            verify(flexClient, times(1)).fetchReport("20260315", "20260315");
        }

        @Test
        @DisplayName("should split into 3 windows for >730 day range")
        void shouldSplitIntoThreeWindows() {
            when(properties.isConfigured()).thenReturn(true);

            // 2023-01-01 to 2026-01-02 = 732 days → 3 windows:
            //   Window 1: 2023-01-01 ~ 2024-01-01 (365+1 days, capped by plusDays(365))
            //   Window 2: 2024-01-02 ~ 2025-01-01
            //   Window 3: 2025-01-02 ~ 2026-01-02 (capped to endDate)
            SyncRequest request = new SyncRequest("ibkr", "2023-01-01", "2026-01-02");
            request.setBatchId(7L);

            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<xml/>");

            FlexQueryParseResult r1 = buildParseResult(50, 5);
            FlexQueryParseResult r2 = buildParseResult(50, 5);
            FlexQueryParseResult r3 = buildParseResult(50, 5);
            when(flexQueryParser.parse("<xml/>"))
                    .thenReturn(r1)
                    .thenReturn(r2)
                    .thenReturn(r3);

            when(stagedOrderRepository.countByBatchIdAndStatus(7L, "IMPORTED")).thenReturn(150L);
            when(stagedOrderRepository.countByBatchIdAndStatus(7L, "SKIPPED")).thenReturn(0L);
            when(stagedOrderRepository.countByBatchIdAndStatus(7L, "FAILED")).thenReturn(0L);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(150, result.getTotalRecords());
            verify(flexClient, times(3)).fetchReport(anyString(), anyString());
        }
    }

    @Nested
    @DisplayName("sync() - default date range")
    class DefaultDateRangeTest {

        @Test
        @DisplayName("should use defaults when no dates provided")
        void shouldUseDefaultDates() {
            when(properties.isConfigured()).thenReturn(true);

            SyncRequest request = new SyncRequest("ibkr");
            request.setBatchId(3L);
            // No startTime/endTime set

            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<xml/>");

            FlexQueryParseResult parseResult = buildParseResult(10, 1);
            when(flexQueryParser.parse("<xml/>")).thenReturn(parseResult);

            when(stagedOrderRepository.countByBatchIdAndStatus(3L, "IMPORTED")).thenReturn(10L);
            when(stagedOrderRepository.countByBatchIdAndStatus(3L, "SKIPPED")).thenReturn(0L);
            when(stagedOrderRepository.countByBatchIdAndStatus(3L, "FAILED")).thenReturn(0L);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(10, result.getTotalRecords());

            // Should call fetchReport exactly once (90 days default ≤ 365)
            verify(flexClient, times(1)).fetchReport(anyString(), anyString());
        }
    }

    @Nested
    @DisplayName("sync() - exception handling")
    class ExceptionHandlingTest {

        @Test
        @DisplayName("should return failure when flexClient throws exception")
        void shouldReturnFailureOnClientException() {
            when(properties.isConfigured()).thenReturn(true);

            SyncRequest request = new SyncRequest("ibkr", "2026-01-01", "2026-03-31");
            request.setBatchId(4L);
            when(flexClient.fetchReport(anyString(), anyString()))
                    .thenThrow(new IbkrFlexClient.IbkrFlexClientException("HTTP 500 error"));

            SyncResult result = adapter.sync(request);

            assertFalse(result.isSuccess());
            assertEquals("ibkr", result.getBrokerCode());
            assertTrue(result.getMessage().contains("HTTP 500 error"));
            assertTrue(result.getDurationMs() >= 0);
        }

        @Test
        @DisplayName("should return failure when parser throws exception")
        void shouldReturnFailureOnParseException() {
            when(properties.isConfigured()).thenReturn(true);

            SyncRequest request = new SyncRequest("ibkr", "2026-01-01", "2026-03-31");
            request.setBatchId(5L);
            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<bad-xml/>");
            when(flexQueryParser.parse("<bad-xml/>"))
                    .thenThrow(new FlexQueryParser.FlexQueryParseException("Invalid XML"));

            SyncResult result = adapter.sync(request);

            assertFalse(result.isSuccess());
            assertTrue(result.getMessage().contains("Invalid XML"));
        }
    }

    @Nested
    @DisplayName("sync() - batchId null safety")
    class BatchIdNullSafetyTest {

        @Test
        @DisplayName("should skip phase updates when batchId is null")
        void shouldSkipPhaseUpdatesWhenBatchIdNull() {
            when(properties.isConfigured()).thenReturn(true);

            SyncRequest request = new SyncRequest("ibkr", "2026-01-01", "2026-03-31");
            // batchId NOT set (remains null)

            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<xml/>");

            FlexQueryParseResult parseResult = buildParseResult(5, 0);
            when(flexQueryParser.parse("<xml/>")).thenReturn(parseResult);

            when(stagedOrderRepository.countByBatchIdAndStatus(null, "IMPORTED")).thenReturn(5L);
            when(stagedOrderRepository.countByBatchIdAndStatus(null, "SKIPPED")).thenReturn(0L);
            when(stagedOrderRepository.countByBatchIdAndStatus(null, "FAILED")).thenReturn(0L);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            // Phase updates should not be called when batchId is null
            verify(batchService, never()).updatePhase(anyLong(), anyString());
        }
    }

    // ============ Test helpers ============

    /**
     * Build a FlexQueryParseResult with the given number of dummy Order records
     * and TradeConfirm records.
     */
    private FlexQueryParseResult buildParseResult(int orderCount, int tradeConfirmCount) {
        FlexQueryParseResult result = new FlexQueryParseResult();
        result.setQueryName("TEST");
        result.setAccountId("U12345");
        result.setFromDate("20260101");
        result.setToDate("20260331");
        result.setWhenGenerated("20260401;120000");

        List<IbkrOrderRecord> orders = new ArrayList<>();
        for (int i = 0; i < orderCount; i++) {
            IbkrOrderRecord order = new IbkrOrderRecord();
            order.setOrderID(String.valueOf(1000 + i));
            order.setSymbol("AAPL");
            order.setBuySell("BUY");
            order.setQuantity("100");
            order.setPrice("150.00");
            order.setAccountId("U12345");
            order.setCurrency("USD");
            order.setAssetCategory("STK");
            orders.add(order);
        }
        result.setOrders(orders);

        List<IbkrTradeConfirm> confirms = new ArrayList<>();
        for (int i = 0; i < tradeConfirmCount; i++) {
            IbkrTradeConfirm confirm = new IbkrTradeConfirm();
            confirm.setTradeID("TC" + (2000 + i));
            confirm.setOrderID(String.valueOf(1000 + i));
            confirm.setSymbol("AAPL");
            confirms.add(confirm);
        }
        result.setTradeConfirms(confirms);

        return result;
    }
}
