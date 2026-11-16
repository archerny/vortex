package com.vortex.sync.adapter.ibkr;

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
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import com.vortex.sync.core.SyncBatchCleanupService;
import com.vortex.sync.core.SyncBatchFailureHandler;
import com.vortex.sync.core.SyncRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IbkrQuantityValidationTest {

    @Mock private IbkrStagedOrderRepository orders;
    @Mock private IbkrStagedTradeConfirmRepository confirms;
    @Mock private TradeRecordRepository trades;
    private IbkrImportWorker worker;

    @BeforeEach
    void setUp() {
        worker = new IbkrImportWorker(orders, confirms, trades);
    }

    private IbkrStagedOrder order(String quantity) {
        IbkrStagedOrder order = new IbkrStagedOrder();
        order.setId(1L);
        order.setBatchId(100L);
        order.setOrderId("synthetic-order");
        order.setTradeDate("20260105");
        order.setCurrency("USD");
        order.setAssetCategory("STK");
        order.setSymbol("SYNTH");
        order.setDescription("SYNTH TEST");
        order.setBuySell("BUY");
        order.setOrderType("LMT");
        order.setOrderTime("20260105;100000");
        order.setQuantity(quantity);
        order.setPrice("10");
        order.setAmount("15");
        order.setCommission("0");
        return order;
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "100.000, 100", "-100.000, 100", "2147483647, 2147483647",
            "-2147483647, 2147483647"})
    void shouldImportWholeQuantitiesExactly(String raw, int expected) {
        IbkrStagedOrder order = order(raw);
        order.setAmount(new BigDecimal(raw).abs().multiply(new BigDecimal("10")).toPlainString());
        when(trades.save(any(TradeRecord.class))).thenAnswer(invocation -> {
            TradeRecord record = invocation.getArgument(0);
            record.setId(5L);
            return record;
        });

        worker.importSingleOrder(100L, 1L, order);

        ArgumentCaptor<TradeRecord> saved = ArgumentCaptor.forClass(TradeRecord.class);
        verify(trades).save(saved.capture());
        assertEquals(expected, saved.getValue().getQuantity());
        assertEquals(new BigDecimal(order.getPrice()), saved.getValue().getPrice());
        assertEquals(new BigDecimal(order.getAmount()), saved.getValue().getAmount());
        assertEquals(raw, order.getQuantity());
        assertEquals("IMPORTED", order.getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.5", "-1.5", "0.25", "0.00000001", "1.00000000000000000001"})
    void shouldRejectFractionsBeforeSavingAnyTrade(String raw) {
        IbkrStagedOrder order = order(raw);

        ImportOneFailedException error = assertThrows(ImportOneFailedException.class,
                () -> worker.importSingleOrder(100L, 1L, order));

        CategorizedSyncException cause = assertInstanceOf(CategorizedSyncException.class, error.getCause());
        assertEquals(FailureCategory.UNRECOGNIZED, cause.getCategory());
        assertTrue(cause.getReason().contains("Fractional IBKR quantities are not supported"));
        assertFalse(cause.getReason().contains(raw));
        assertEquals(raw, order.getQuantity());
        assertEquals("PENDING", order.getStatus());
        verify(trades, never()).save(any());
        verify(orders, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-0.000", "invalid", "NaN", "   ", "2147483648", "-2147483648",
            "4294967297", "-4294967297"})
    @NullAndEmptySource
    void shouldRejectInvalidZeroMissingAndOverflowingQuantities(String raw) {
        ImportOneFailedException error = assertThrows(ImportOneFailedException.class,
                () -> worker.importSingleOrder(100L, 1L, order(raw)));

        CategorizedSyncException cause = assertInstanceOf(CategorizedSyncException.class, error.getCause());
        assertEquals(FailureCategory.UNRECOGNIZED, cause.getCategory());
        verify(trades, never()).save(any());
    }

    @Test
    void shouldRejectFractionalContractQuantityToo() {
        IbkrStagedOrder order = order("1.5");
        order.setAssetCategory("OPT");
        order.setPutCall("C");
        order.setExpiry("20260116");
        order.setStrike("10");
        order.setDescription("SYNTH 16JAN26 10 C");

        ImportOneFailedException error = assertThrows(ImportOneFailedException.class,
                () -> worker.importSingleOrder(100L, 1L, order));

        assertTrue(error.getCause().getMessage().contains("Fractional IBKR quantities are not supported"));
        verify(trades, never()).save(any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void shouldRetainFailureSummaryWhenFirstErrorHasNoExplanation(String explanation) {
        IbkrFlexQueryProperties properties = new IbkrFlexQueryProperties();
        properties.setFlexToken("synthetic-token");
        properties.setTradeConfirmQueryId("synthetic-query");
        IbkrFlexClient client = mock(IbkrFlexClient.class);
        when(client.fetchReport(anyString(), anyString())).thenReturn("<FlexQueryResponse/>");
        IbkrStagedOrder failed = order("1.5");
        failed.setErrorMessage(explanation);
        when(orders.countByBatchIdAndStatus(100L, "IMPORTED")).thenReturn(0L);
        when(orders.countByBatchIdAndStatus(100L, "SKIPPED")).thenReturn(0L);
        when(orders.countByBatchIdAndStatus(100L, "FAILED")).thenReturn(1L);
        when(orders.findFirstByBatchIdAndStatusOrderByIdAsc(100L, "FAILED"))
                .thenReturn(Optional.of(failed));
        IbkrSyncAdapter adapter = new IbkrSyncAdapter(properties, client, new FlexQueryParser(),
                mock(IbkrStagingService.class), mock(IbkrImportService.class),
                mock(BrokerSyncBatchService.class), orders);
        SyncRequest request = new SyncRequest("ibkr", "2026-01-01", "2026-01-05");
        request.setBatchId(100L);

        var result = adapter.sync(request);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("failed=1"));
        assertFalse(result.getMessage().contains("first failure:"));
    }

    @ParameterizedTest
    @CsvSource({"1.5, Fractional IBKR quantities are not supported",
            "-1.5, Fractional IBKR quantities are not supported",
            "4294967297, IBKR quantity exceeds the supported maximum"})
    void shouldFailAndCleanMixedBatchAndRepeatWithoutChangingEarlierTrades(String raw, String reason) {
        Map<Long, IbkrStagedOrder> staged = new LinkedHashMap<>();
        Map<Long, IbkrStagedTradeConfirm> stagedConfirms = new LinkedHashMap<>();
        Map<Long, TradeRecord> persisted = new LinkedHashMap<>();
        AtomicLong ids = new AtomicLong(200);
        TradeRecord previous = new TradeRecord();
        previous.setId(10L);
        previous.setSyncBatchId(99L);
        persisted.put(10L, previous);

        when(orders.existsByOrderId(anyString())).thenAnswer(i -> staged.values().stream()
                .anyMatch(o -> o.getOrderId().equals(i.getArgument(0))));
        when(orders.save(any())).thenAnswer(i -> {
            IbkrStagedOrder value = i.getArgument(0);
            if (value.getId() == null) value.setId(ids.incrementAndGet());
            staged.put(value.getId(), value);
            return value;
        });
        when(orders.findById(anyLong())).thenAnswer(i -> Optional.ofNullable(staged.get(i.getArgument(0))));
        when(orders.findByBatchIdAndStatus(anyLong(), anyString())).thenAnswer(i -> staged.values().stream()
                .filter(o -> Objects.equals(o.getBatchId(), i.getArgument(0)) && o.getStatus().equals(i.getArgument(1)))
                .toList());
        when(orders.countByBatchIdAndStatus(anyLong(), anyString())).thenAnswer(i -> staged.values().stream()
                .filter(o -> Objects.equals(o.getBatchId(), i.getArgument(0)) && o.getStatus().equals(i.getArgument(1)))
                .count());
        when(orders.findFirstByBatchIdAndStatusOrderByIdAsc(anyLong(), anyString())).thenAnswer(i -> staged.values().stream()
                .filter(o -> Objects.equals(o.getBatchId(), i.getArgument(0)) && o.getStatus().equals(i.getArgument(1)))
                .findFirst());
        when(confirms.save(any())).thenAnswer(i -> {
            IbkrStagedTradeConfirm value = i.getArgument(0);
            value.setId(ids.incrementAndGet());
            stagedConfirms.put(value.getId(), value);
            return value;
        });
        when(trades.save(any())).thenAnswer(i -> {
            TradeRecord value = i.getArgument(0);
            value.setId(ids.incrementAndGet());
            persisted.put(value.getId(), value);
            return value;
        });
        when(orders.deleteByBatchId(anyLong())).thenAnswer(i -> {
            long count = staged.size();
            staged.values().removeIf(o -> Objects.equals(o.getBatchId(), i.getArgument(0)));
            return count - staged.size();
        });
        when(confirms.deleteByBatchId(anyLong())).thenAnswer(i -> {
            long count = stagedConfirms.size();
            stagedConfirms.values().removeIf(o -> Objects.equals(o.getBatchId(), i.getArgument(0)));
            return count - stagedConfirms.size();
        });
        when(trades.deleteBySyncBatchId(anyLong())).thenAnswer(i -> {
            int count = persisted.size();
            persisted.values().removeIf(t -> Objects.equals(t.getSyncBatchId(), i.getArgument(0)));
            return count - persisted.size();
        });

        BrokerRepository brokers = mock(BrokerRepository.class);
        Broker broker = new Broker("Synthetic broker", "US");
        broker.setId(1L);
        when(brokers.findByBrokerCode("ibkr")).thenReturn(Optional.of(broker));
        IbkrFlexClient client = mock(IbkrFlexClient.class);
        when(client.fetchReport(anyString(), anyString())).thenReturn("""
                <FlexQueryResponse type="TCF"><FlexStatement accountId="synthetic-account">
                  <TradeConfirms>
                    <Order orderID="synthetic-good" assetCategory="STK" symbol="SYNTH" currency="USD"
                      tradeDate="20260105" buySell="BUY" orderType="LMT" orderTime="20260105;100000"
                      quantity="10" price="10" amount="100"/>
                    <Order orderID="synthetic-invalid" assetCategory="STK" symbol="SYNTH" currency="USD"
                      tradeDate="20260105" buySell="BUY" orderType="LMT" orderTime="20260105;100000"
                      quantity="%s" price="10" amount="15"/>
                    <TradeConfirm tradeID="synthetic-fill" orderID="synthetic-invalid" quantity="%s"/>
                  </TradeConfirms>
                </FlexStatement></FlexQueryResponse>
                """.formatted(raw, raw));
        IbkrFlexQueryProperties properties = new IbkrFlexQueryProperties();
        properties.setFlexToken("synthetic-token");
        properties.setTradeConfirmQueryId("synthetic-query");
        BrokerSyncBatchService batches = mock(BrokerSyncBatchService.class);
        IbkrImportService importer = new IbkrImportService(orders, trades, brokers, worker);
        IbkrSyncAdapter adapter = new IbkrSyncAdapter(properties, client, new FlexQueryParser(),
                new IbkrStagingService(new IbkrStagingWorker(orders, confirms)), importer, batches, orders);
        SyncBatchCleanupService cleanup = new SyncBatchCleanupService(
                List.of(new IbkrCleanupStrategy(orders, confirms)), List.of(adapter), trades);
        BrokerSyncAsyncExecutor executor = new BrokerSyncAsyncExecutor(
                new BrokerSyncService(List.of(adapter), brokers), batches, new SyncBatchFailureHandler(cleanup, batches));

        for (long batch : new long[] {100L, 101L}) {
            executor.execute(batch, new SyncRequest("ibkr", "2026-01-01", "2026-01-05"));

            verify(batches).markAsFailed(eq(batch), contains(reason));
            verify(orders).deleteByBatchId(batch);
            verify(confirms).deleteByBatchId(batch);
            verify(trades).deleteBySyncBatchId(batch);
            assertTrue(staged.isEmpty());
            assertTrue(stagedConfirms.isEmpty());
            assertEquals(Map.of(10L, previous), persisted);
        }
        verify(batches, never()).markAsCompleted(anyLong(), any());
        ArgumentCaptor<TradeRecord> saved = ArgumentCaptor.forClass(TradeRecord.class);
        verify(trades, times(2)).save(saved.capture());
        assertTrue(saved.getAllValues().stream().allMatch(t -> t.getExternalId().equals("synthetic-good")
                && t.getQuantity() == 10));
        ArgumentCaptor<IbkrStagedOrder> rawOrders = ArgumentCaptor.forClass(IbkrStagedOrder.class);
        verify(orders, atLeastOnce()).save(rawOrders.capture());
        assertTrue(rawOrders.getAllValues().stream().filter(o -> o.getOrderId().equals("synthetic-invalid"))
                .allMatch(o -> o.getQuantity().equals(raw)));
    }
}
