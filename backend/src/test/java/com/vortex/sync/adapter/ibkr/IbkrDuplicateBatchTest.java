package com.vortex.sync.adapter.ibkr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortex.entity.Broker;
import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.IbkrStagedTradeConfirm;
import com.vortex.entity.TradeRecord;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import com.vortex.repository.TradeRecordRepository;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAsyncExecutor;
import com.vortex.sync.core.BrokerSyncService;
import com.vortex.sync.core.SyncBatchCleanupService;
import com.vortex.sync.core.SyncBatchFailureHandler;
import com.vortex.sync.core.SyncRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Real component call chain with in-memory repository behavior; no database transactions. */
@ExtendWith(MockitoExtension.class)
class IbkrDuplicateBatchTest {
    @Mock private IbkrStagedOrderRepository orders;
    @Mock private IbkrStagedTradeConfirmRepository confirms;
    @Mock private TradeRecordRepository trades;
    @Mock private BrokerRepository brokers;
    @Mock private BrokerSyncBatchService batches;
    @Mock private IbkrFlexClient client;

    @ParameterizedTest
    @ValueSource(strings = {"order", "trade"})
    void shouldFailCleanAndRepeatConflictsWithoutChangingSuccessfulHistory(String kind) throws Exception {
        Harness h = new Harness();
        h.run(99L);
        String before = h.snapshot();
        h.report = report(kind.equals("order") ? "20" : "10",
                kind.equals("trade") ? "-9.87654321" : "-1.25", true);
        String field = kind.equals("order") ? "quantity" : "commission";

        for (long batch : new long[] {100L, 101L}) {
            h.run(batch);
            verify(batches).markAsFailed(eq(batch), contains(field));
            verify(orders).deleteByBatchId(batch);
            verify(confirms).deleteByBatchId(batch);
            verify(trades).deleteBySyncBatchId(batch);
            assertEquals(before, h.snapshot());
        }
        verify(batches, never()).markAsCompleted(eq(100L), any());
        verify(batches, never()).markAsCompleted(eq(101L), any());
        verify(trades, times(1)).save(any()); // Only the first successful historical import.
    }

    @Test
    void shouldSkipIdenticalRepeatsAndImportOnlyOnce() throws Exception {
        Harness h = new Harness();
        h.run(99L);
        String before = h.snapshot();
        h.run(100L);
        verify(batches).markAsCompleted(eq(100L), argThat(result -> result.getImportedCount() == 0));
        verify(batches, never()).markAsFailed(anyLong(), anyString());
        verify(trades, times(1)).save(any());
        assertEquals(before, h.snapshot());
    }

    @Test
    void shouldHandleRepeatedRowsAndPartialExecutionCodesInOneReport() {
        Harness h = new Harness();
        h.report = h.report.replace("</TradeConfirms>",
                orderXml("SYNTH-OLD", "10") + confirmXml("SYNTH-FILL", "SYNTH-OLD", "10", "-1.25")
                        + "</TradeConfirms>");
        h.run(100L);
        verify(batches).markAsCompleted(eq(100L), argThat(result -> result.getImportedCount() == 1));
        assertEquals(1, h.staged.size());
        assertEquals(1, h.fills.size());
        assertEquals(1, h.persisted.size());
    }

    @Test
    void shouldFailAndCleanAConflictingDuplicateInsideOneBatch() {
        Harness h = new Harness();
        h.report = h.report.replace("</TradeConfirms>", orderXml("SYNTH-OLD", "20") + "</TradeConfirms>");
        h.run(100L);
        verify(batches).markAsFailed(eq(100L), contains("quantity"));
        verify(batches, never()).markAsCompleted(anyLong(), any());
        assertTrue(h.staged.isEmpty());
        assertTrue(h.fills.isEmpty());
        assertTrue(h.persisted.isEmpty());
    }

    @Test
    void shouldDetectAConflictingOrderAcrossFetchWindows() {
        Harness h = new Harness();
        when(client.fetchReport(anyString(), anyString())).thenReturn(
                report("10", "-1.25", false).replace("20260105", "20250105"),
                report("20", "-1.25", false));
        h.executor.execute(100L, new SyncRequest("ibkr", "2025-01-01", "2026-01-05"));
        verify(client, times(2)).fetchReport(anyString(), anyString());
        verify(batches).markAsFailed(eq(100L), contains("quantity"));
        assertTrue(h.staged.isEmpty());
        assertTrue(h.persisted.isEmpty());
    }

    @Test
    void shouldPreserveAnExistingFormalTradeWhenItsStagingBaselineIsMissing() throws Exception {
        Harness h = new Harness();
        h.run(99L);
        h.staged.clear();
        h.fills.clear();
        String before = h.snapshot();
        h.report = report("20", "-1.25", true);

        h.run(100L);

        verify(batches).markAsFailed(eq(100L), contains("stored-trade-record-row:"));
        assertEquals(before, h.snapshot());
        assertEquals(1, h.persisted.size());
        verify(batches, never()).markAsCompleted(eq(100L), any());
    }

    @Test
    void shouldKeepCleanupFailureBlockingAndPreserveEarlierHistory() {
        Harness h = new Harness();
        h.run(99L);
        h.report = report("20", "-1.25", true);
        when(confirms.deleteByBatchId(100L)).thenThrow(new IllegalStateException("Synthetic cleanup failure"));

        h.run(100L);

        verify(batches).markAsCleanupFailed(eq(100L), contains("quantity"));
        verify(confirms, times(3)).deleteByBatchId(100L);
        verify(batches, never()).markAsCompleted(eq(100L), any());
        assertEquals(1, h.persisted.size());
        assertTrue(h.persisted.values().stream().allMatch(record -> record.getSyncBatchId() == 99L));
        assertTrue(h.staged.values().stream().anyMatch(row -> row.getBatchId() == 99L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"multiple", "wrong-type", "missing-orders", "missing-executions", "mixed-account", "unknown"})
    void shouldRejectIncompleteOrUnsupportedReportsAndPreserveSuccessfulHistory(String kind) throws Exception {
        Harness h = new Harness();
        h.run(99L);
        String before = h.snapshot();
        String valid = h.report;
        h.report = switch (kind) {
            case "multiple" -> valid.replace("<FlexStatements count=\"1\">", "<FlexStatements count=\"2\"><FlexStatement accountId=\"SYNTH-ACCOUNT\"><TradeConfirms/></FlexStatement>");
            case "wrong-type" -> valid.replace("type=\"TCF\"", "type=\"AF\"");
            case "missing-orders" -> document(confirmXml("SYNTH-FILL", "SYNTH-OLD", "10", "-1.25"));
            case "missing-executions" -> document(orderXml("SYNTH-OLD", "10"));
            case "mixed-account" -> valid.replace("<FlexStatement accountId=\"SYNTH-ACCOUNT\">", "<FlexStatement accountId=\"SYNTH-OTHER\">");
            default -> valid.replace("</TradeConfirms>", "<SYNTH-Unknown/></TradeConfirms>");
        };

        for (long batch : new long[] {100L, 101L}) {
            h.run(batch);
            verify(batches).markAsFailed(eq(batch), contains("[UNRECOGNIZED]"));
            verify(batches, never()).markAsCompleted(eq(batch), any());
            verify(batches, never()).updatePhase(batch, "STAGING");
            verify(orders).deleteByBatchId(batch);
            verify(confirms).deleteByBatchId(batch);
            verify(trades).deleteBySyncBatchId(batch);
            assertEquals(before, h.snapshot());
        }
        verify(trades, times(1)).save(any());
        verify(orders, times(2)).save(any()); // Historical PENDING and IMPORTED only.
        verify(confirms, times(1)).save(any());
    }

    @Test
    void shouldCompleteALegitimatelyEmptyReportWithoutChangingHistory() throws Exception {
        Harness h = new Harness();
        h.run(99L);
        String before = h.snapshot();
        h.report = document("");
        h.run(100L);
        verify(batches).markAsCompleted(eq(100L), argThat(result -> result.getTotalRecords() == 0));
        verify(batches, never()).markAsFailed(anyLong(), anyString());
        assertEquals(before, h.snapshot());
        verify(trades, times(1)).save(any());
    }

    @Test
    void shouldRejectAnInvalidLaterWindowBeforeStagingAnyWindow() throws Exception {
        Harness h = new Harness();
        h.run(99L);
        String before = h.snapshot();
        when(client.fetchReport(anyString(), anyString())).thenReturn(
                report("10", "-1.25", true), document(confirmXml("SYNTH-FILL", "SYNTH-OLD", "10", "0")));
        h.executor.execute(100L, new SyncRequest("ibkr", "2025-01-01", "2026-01-05"));
        verify(batches).markAsFailed(eq(100L), contains("[UNRECOGNIZED]"));
        verify(batches, never()).updatePhase(100L, "STAGING");
        verify(batches, never()).markAsCompleted(eq(100L), any());
        assertEquals(before, h.snapshot());
        verify(trades, times(1)).save(any());
        verify(orders, times(2)).save(any());
    }

    private class Harness {
        final Map<Long, IbkrStagedOrder> staged = new LinkedHashMap<>();
        final Map<Long, IbkrStagedTradeConfirm> fills = new LinkedHashMap<>();
        final Map<Long, TradeRecord> persisted = new LinkedHashMap<>();
        final AtomicLong ids = new AtomicLong(200);
        final BrokerSyncAsyncExecutor executor;
        String report = report("10", "-1.25", false);

        Harness() {
            lenient().when(orders.findByOrderId(anyString())).thenAnswer(i -> staged.values().stream()
                    .filter(row -> row.getOrderId().equals(i.getArgument(0))).findFirst());
            lenient().when(confirms.findByTradeId(anyString())).thenAnswer(i -> fills.values().stream()
                    .filter(row -> row.getTradeId().equals(i.getArgument(0))).findFirst());
            lenient().when(orders.save(any())).thenAnswer(i -> {
                IbkrStagedOrder row = i.getArgument(0);
                if (row.getId() == null) row.setId(ids.incrementAndGet());
                staged.put(row.getId(), row);
                return row;
            });
            lenient().when(confirms.save(any())).thenAnswer(i -> {
                IbkrStagedTradeConfirm row = i.getArgument(0);
                if (row.getId() == null) row.setId(ids.incrementAndGet());
                fills.put(row.getId(), row);
                return row;
            });
            lenient().when(orders.findById(anyLong())).thenAnswer(i -> Optional.ofNullable(staged.get(i.getArgument(0))));
            lenient().when(orders.findByBatchIdAndStatus(anyLong(), anyString())).thenAnswer(i -> staged.values().stream()
                    .filter(row -> Objects.equals(row.getBatchId(), i.getArgument(0)) && row.getStatus().equals(i.getArgument(1))).toList());
            lenient().when(orders.countByBatchIdAndStatus(anyLong(), anyString())).thenAnswer(i -> staged.values().stream()
                    .filter(row -> Objects.equals(row.getBatchId(), i.getArgument(0)) && row.getStatus().equals(i.getArgument(1))).count());
            lenient().when(orders.countByBatchIdAndStatusNotIn(anyLong(), anyCollection())).thenAnswer(i -> staged.values().stream()
                    .filter(row -> Objects.equals(row.getBatchId(), i.getArgument(0))
                            && !((java.util.Collection<?>) i.getArgument(1)).contains(row.getStatus())).count());
            lenient().when(orders.findFirstByBatchIdAndStatusOrderByIdAsc(anyLong(), anyString())).thenAnswer(i -> staged.values().stream()
                    .filter(row -> Objects.equals(row.getBatchId(), i.getArgument(0)) && row.getStatus().equals(i.getArgument(1))).findFirst());
            lenient().when(trades.existsByExternalBrokerAndExternalId(anyString(), anyString())).thenAnswer(i -> persisted.values().stream()
                    .anyMatch(row -> Objects.equals(row.getExternalBroker(), i.getArgument(0))
                            && Objects.equals(row.getExternalId(), i.getArgument(1))));
            lenient().when(trades.findByExternalBrokerAndExternalId(anyString(), anyString())).thenAnswer(i -> persisted.values().stream()
                    .filter(row -> Objects.equals(row.getExternalBroker(), i.getArgument(0))
                            && Objects.equals(row.getExternalId(), i.getArgument(1))).findFirst());
            lenient().when(trades.save(any())).thenAnswer(i -> {
                TradeRecord row = i.getArgument(0);
                if (row.getId() == null) row.setId(ids.incrementAndGet());
                persisted.put(row.getId(), row);
                return row;
            });
            lenient().when(orders.deleteByBatchId(anyLong())).thenAnswer(i -> {
                int count = staged.size();
                staged.values().removeIf(row -> Objects.equals(row.getBatchId(), i.getArgument(0)));
                return (long) count - staged.size();
            });
            lenient().when(confirms.deleteByBatchId(anyLong())).thenAnswer(i -> {
                int count = fills.size();
                fills.values().removeIf(row -> Objects.equals(row.getBatchId(), i.getArgument(0)));
                return (long) count - fills.size();
            });
            lenient().when(trades.deleteBySyncBatchId(anyLong())).thenAnswer(i -> {
                int count = persisted.size();
                persisted.values().removeIf(row -> Objects.equals(row.getSyncBatchId(), i.getArgument(0)));
                return count - persisted.size();
            });
            Broker broker = new Broker("Synthetic broker", "US");
            broker.setId(1L);
            lenient().when(brokers.findByBrokerCode("ibkr")).thenReturn(Optional.of(broker));
            lenient().when(client.fetchReport(anyString(), anyString())).thenAnswer(i -> report);
            IbkrFlexQueryProperties properties = new IbkrFlexQueryProperties();
            properties.setFlexToken("synthetic-token");
            properties.setTradeConfirmQueryId("synthetic-query");
            IbkrImportWorker importer = new IbkrImportWorker(orders, confirms, trades);
            IbkrSyncAdapter adapter = new IbkrSyncAdapter(properties, client, new FlexQueryParser(),
                    new IbkrStagingService(new IbkrStagingWorker(orders, confirms)),
                    new IbkrImportService(orders, trades, brokers, importer), batches, orders);
            SyncBatchCleanupService cleanup = new SyncBatchCleanupService(
                    List.of(new IbkrCleanupStrategy(orders, confirms)), List.of(adapter), trades);
            executor = new BrokerSyncAsyncExecutor(new BrokerSyncService(List.of(adapter), brokers),
                    batches, new SyncBatchFailureHandler(cleanup, batches));
        }

        void run(long batch) {
            executor.execute(batch, new SyncRequest("ibkr", "2026-01-01", "2026-01-05"));
        }

        String snapshot() throws Exception {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(List.of(
                    new ArrayList<>(staged.values()), new ArrayList<>(fills.values()), new ArrayList<>(persisted.values())));
        }
    }

    private static String report(String quantity, String tradeFee, boolean includeNew) {
        return document((includeNew ? orderXml("SYNTH-NEW", "3") : "")
                + orderXml("SYNTH-OLD", quantity)
                + (includeNew ? confirmXml("SYNTH-NEW-FILL", "SYNTH-NEW", "3", "0") : "")
                + confirmXml("SYNTH-FILL", "SYNTH-OLD", quantity, tradeFee));
    }

    private static String document(String rows) {
        return "<FlexQueryResponse type=\"TCF\"><FlexStatements count=\"1\"><FlexStatement accountId=\"SYNTH-ACCOUNT\"><TradeConfirms>"
                + rows + "</TradeConfirms></FlexStatement></FlexStatements></FlexQueryResponse>";
    }

    private static String orderXml(String id, String quantity) {
        return """
                <Order orderID="%s" accountId="SYNTH-ACCOUNT" assetCategory="STK" symbol="SYNTH"
                  currency="USD" tradeDate="20260105" buySell="BUY" orderType="LMT" orderTime="20260105;100000"
                  quantity="%s" price="10" amount="%s" commission="-1.25"/>
                """.formatted(id, quantity, new BigDecimal(quantity).multiply(BigDecimal.TEN).toPlainString());
    }

    private static String confirmXml(String id, String order, String quantity, String fee) {
        return """
                <TradeConfirm tradeID="%s" orderID="%s" accountId="SYNTH-ACCOUNT" assetCategory="STK"
                  symbol="SYNTH" currency="USD" tradeDate="20260105" buySell="BUY" quantity="%s"
                  price="10" amount="%s" commission="%s" code="O;P"/>
                """.formatted(id, order, quantity, new BigDecimal(quantity).multiply(BigDecimal.TEN).toPlainString(), fee);
    }
}
