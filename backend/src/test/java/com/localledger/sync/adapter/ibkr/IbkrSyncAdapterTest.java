package com.localledger.sync.adapter.ibkr;

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
 * - getBrokerName() returns "ibkr"
 * - Sync with unconfigured credentials returns failure
 * - Sync with valid config: single window (≤365 days)
 * - Sync with valid config: multiple windows (>365 days, date splitting)
 * - Sync with no date range (defaults)
 * - Exception handling during sync
 */
@ExtendWith(MockitoExtension.class)
class IbkrSyncAdapterTest {

    @Mock
    private IbkrFlexQueryProperties properties;

    @Mock
    private IbkrFlexClient flexClient;

    @Mock
    private FlexQueryParser flexQueryParser;

    private IbkrSyncAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new IbkrSyncAdapter(properties, flexClient, flexQueryParser);
    }

    @Test
    @DisplayName("getBrokerName() should return 'ibkr'")
    void getBrokerNameShouldReturnIbkr() {
        assertEquals("ibkr", adapter.getBrokerName());
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
            assertEquals("ibkr", result.getBrokerName());
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

            String xmlReport = "<FlexQueryResponse/>";
            when(flexClient.fetchReport("20260101", "20260331")).thenReturn(xmlReport);

            FlexQueryParseResult parseResult = buildParseResult(50);
            when(flexQueryParser.parse(xmlReport)).thenReturn(parseResult);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(50, result.getTotalRecords());
            assertEquals("ibkr", result.getBrokerName());
            assertTrue(result.getDurationMs() >= 0);

            verify(flexClient, times(1)).fetchReport(anyString(), anyString());
        }
    }

    @Nested
    @DisplayName("sync() - multiple windows (>365 days)")
    class MultiWindowTest {

        @Test
        @DisplayName("should split into multiple windows for >365 day range")
        void shouldSplitIntoMultipleWindows() {
            when(properties.isConfigured()).thenReturn(true);

            // 2024-01-01 to 2026-01-01 = 731 days → 2 full windows + 1 partial
            SyncRequest request = new SyncRequest("ibkr", "2024-01-01", "2026-01-01");

            // Mock: each window returns some orders
            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<xml/>");

            FlexQueryParseResult result1 = buildParseResult(100);
            FlexQueryParseResult result2 = buildParseResult(80);
            FlexQueryParseResult result3 = buildParseResult(20);
            when(flexQueryParser.parse("<xml/>"))
                    .thenReturn(result1)
                    .thenReturn(result2)
                    .thenReturn(result3);

            SyncResult result = adapter.sync(request);

            assertTrue(result.isSuccess());
            // 100 + 80 + 20 = 200
            assertEquals(200, result.getTotalRecords());

            // Should have made 3 calls (731 days / 365 = 2 full + 1 partial)
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
            // No startTime/endTime set

            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<xml/>");

            FlexQueryParseResult parseResult = buildParseResult(10);
            when(flexQueryParser.parse("<xml/>")).thenReturn(parseResult);

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
            when(flexClient.fetchReport(anyString(), anyString()))
                    .thenThrow(new IbkrFlexClient.IbkrFlexClientException("HTTP 500 error"));

            SyncResult result = adapter.sync(request);

            assertFalse(result.isSuccess());
            assertEquals("ibkr", result.getBrokerName());
            assertTrue(result.getMessage().contains("HTTP 500 error"));
            assertTrue(result.getDurationMs() >= 0);
        }

        @Test
        @DisplayName("should return failure when parser throws exception")
        void shouldReturnFailureOnParseException() {
            when(properties.isConfigured()).thenReturn(true);

            SyncRequest request = new SyncRequest("ibkr", "2026-01-01", "2026-03-31");
            when(flexClient.fetchReport(anyString(), anyString())).thenReturn("<bad-xml/>");
            when(flexQueryParser.parse("<bad-xml/>"))
                    .thenThrow(new FlexQueryParser.FlexQueryParseException("Invalid XML"));

            SyncResult result = adapter.sync(request);

            assertFalse(result.isSuccess());
            assertTrue(result.getMessage().contains("Invalid XML"));
        }
    }

    // ============ Test helpers ============

    /**
     * Build a FlexQueryParseResult with the given number of dummy Order records.
     */
    private FlexQueryParseResult buildParseResult(int orderCount) {
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

        return result;
    }
}
