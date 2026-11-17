package com.vortex.sync.adapter.ibkr;

import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.IbkrStagedTradeConfirm;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TradeType;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import com.vortex.repository.TradeRecordRepository;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import jakarta.persistence.Column;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IbkrDuplicateConsistencyTest {
    @Mock private IbkrStagedOrderRepository orders;
    @Mock private IbkrStagedTradeConfirmRepository confirms;
    @Mock private TradeRecordRepository trades;

    @Test
    void shouldRejectChangedOrder() {
        IbkrStagedOrder stored = new IbkrStagedOrder();
        stored.setId(11L);
        stored.setBatchId(1L);
        stored.setOrderId("SYNTH-ORDER");
        stored.setQuantity("10");
        IbkrOrderRecord incoming = new IbkrOrderRecord();
        incoming.setOrderID("SYNTH-ORDER");
        incoming.setQuantity("20");
        when(orders.findByOrderId("SYNTH-ORDER")).thenReturn(Optional.of(stored));

        CategorizedSyncException error = assertThrows(CategorizedSyncException.class,
                () -> new IbkrStagingWorker(orders, confirms).stageOrder(2L, incoming));

        assertEquals(FailureCategory.UNRECOGNIZED, error.getCategory());
        assertTrue(error.getMessage().contains("quantity"));
        assertEquals("10", stored.getQuantity());
        assertEquals(1L, stored.getBatchId());
        verify(orders, never()).save(any());
    }

    @Test
    void shouldRejectChangedTrade() {
        IbkrStagedTradeConfirm stored = new IbkrStagedTradeConfirm();
        stored.setId(12L);
        stored.setBatchId(1L);
        stored.setTradeId("SYNTH-TRADE");
        stored.setOrderId("SYNTH-ORDER");
        stored.setCommission("-1.25");
        IbkrTradeConfirm incoming = new IbkrTradeConfirm();
        incoming.setTradeID("SYNTH-TRADE");
        incoming.setOrderID("SYNTH-ORDER");
        incoming.setCommission("-1.50");
        when(confirms.findByTradeId("SYNTH-TRADE")).thenReturn(Optional.of(stored));

        CategorizedSyncException error = assertThrows(CategorizedSyncException.class,
                () -> new IbkrStagingWorker(orders, confirms).stageTradeConfirm(2L, incoming));

        assertEquals(FailureCategory.UNRECOGNIZED, error.getCategory());
        assertTrue(error.getMessage().contains("commission"));
        assertEquals("-1.25", stored.getCommission());
        verify(confirms, never()).save(any());
    }

    // Derive coverage from the persistence schema, not the comparison implementation.
    static Stream<String> orderFields() {
        return sourceFields(IbkrStagedOrder.class, Set.of("status", "errorMessage", "orderId"));
    }

    static Stream<String> confirmFields() {
        return sourceFields(IbkrStagedTradeConfirm.class, Set.of("tradeId"));
    }

    private static Stream<String> sourceFields(Class<?> type, Set<String> excluded) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.getType() == String.class && field.isAnnotationPresent(Column.class))
                .map(java.lang.reflect.Field::getName).filter(name -> !excluded.contains(name));
    }

    @ParameterizedTest
    @MethodSource("orderFields")
    void shouldDetectEveryPersistedOrderSourceField(String field) {
        IbkrOrderRecord incoming = order();
        IbkrStagedOrder stored = storedOrder(incoming);
        ReflectionTestUtils.setField(stored, field, "SYNTH-CONFLICT-VALUE");

        var error = assertThrows(CategorizedSyncException.class,
                () -> staging().stageOrder(2L, incoming));

        assertTrue(error.getMessage().contains(field));
        assertFalse(error.getFormattedMessage().contains("SYNTH-CONFLICT-VALUE"));
        assertFalse(error.getFormattedMessage().contains(incoming.getOrderID()));
        assertTrue(error.getFormattedMessage().contains("stored-order-row:11"));
        verify(orders, never()).save(any());
    }

    @ParameterizedTest
    @MethodSource("confirmFields")
    void shouldDetectEveryPersistedConfirmSourceField(String field) {
        IbkrTradeConfirm incoming = confirm();
        IbkrStagedTradeConfirm stored = storedConfirm(incoming);
        ReflectionTestUtils.setField(stored, field, "SYNTH-CONFLICT-VALUE");

        var error = assertThrows(CategorizedSyncException.class,
                () -> staging().stageTradeConfirm(2L, incoming));

        assertTrue(error.getMessage().contains(field));
        assertFalse(error.getFormattedMessage().contains("SYNTH-CONFLICT-VALUE"));
        assertFalse(error.getFormattedMessage().contains(incoming.getTradeID()));
        verify(confirms, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"multiplier", "strike", "quantity", "price", "amount", "proceeds", "netCash", "commission", "tradeCharge"})
    void shouldTreatNumericallyEqualOrderValuesAsDuplicates(String field) {
        IbkrOrderRecord incoming = order();
        ReflectionTestUtils.setField(incoming, field, "1.00000000000000000001");
        IbkrStagedOrder stored = storedOrder(incoming);
        ReflectionTestUtils.setField(incoming, field, "100000000000000000001e-20");

        assertFalse(staging().stageOrder(2L, incoming));

        assertEquals("1.00000000000000000001", ReflectionTestUtils.getField(stored, field));
        verify(orders, never()).save(any());
    }

    @Test
    void shouldSkipIdenticalOrderWithoutChangingProcessingMetadata() {
        IbkrOrderRecord incoming = order();
        IbkrStagedOrder stored = storedOrder(incoming);
        stored.setStatus("IMPORTED");
        stored.setImportedTradeId(55L);
        stored.setErrorMessage("prior synthetic note");
        stored.setCreatedAt(LocalDateTime.of(2026, 1, 1, 0, 0));
        stored.setUpdatedAt(LocalDateTime.of(2026, 1, 2, 0, 0));
        incoming.setAccountId(" " + incoming.getAccountId() + " ");
        incoming.setAcctAlias("   ");

        assertFalse(staging().stageOrder(2L, incoming));

        assertEquals(1L, stored.getBatchId());
        assertEquals("IMPORTED", stored.getStatus());
        assertEquals(55L, stored.getImportedTradeId());
        assertEquals("prior synthetic note", stored.getErrorMessage());
        assertEquals(LocalDateTime.of(2026, 1, 2, 0, 0), stored.getUpdatedAt());
        verify(orders, never()).save(any());
    }

    @Test
    void shouldCompareConfirmNumbersAndCodeTokensWithoutRejectingPartialExecutionMarks() {
        IbkrTradeConfirm incoming = confirm();
        incoming.setCode("O;P");
        incoming.setCommission("-0.000000000123456789");
        IbkrStagedTradeConfirm stored = storedConfirm(incoming);
        incoming.setCode(" P ; O ; P ; ");
        incoming.setCommission("-1.23456789e-10");

        assertFalse(staging().stageTradeConfirm(2L, incoming));

        assertEquals("O;P", stored.getCode());
        assertEquals(1L, stored.getBatchId());
        verify(confirms, never()).save(any());
    }

    @Test
    void shouldTreatEmptyCodeTokenSetAsMissing() {
        IbkrTradeConfirm incoming = confirm();
        storedConfirm(incoming);
        incoming.setCode("; ;");
        assertFalse(staging().stageTradeConfirm(2L, incoming));
    }

    @Test
    void shouldRejectTinyNumericDifferenceAndSignChangeWithoutRounding() {
        IbkrOrderRecord incoming = order();
        incoming.setCommission("-0.00000000000000000001");
        storedOrder(incoming);
        incoming.setCommission("-0.00000000000000000002");
        assertThrows(CategorizedSyncException.class, () -> staging().stageOrder(2L, incoming));
        incoming.setCommission("0.00000000000000000001");
        assertThrows(CategorizedSyncException.class, () -> staging().stageOrder(2L, incoming));
        verify(orders, never()).save(any());
    }

    @Test
    void shouldNotTreatMissingFeeAsZero() {
        IbkrOrderRecord incoming = order();
        incoming.setCommission(null);
        storedOrder(incoming);
        incoming.setCommission("0");
        assertThrows(CategorizedSyncException.class, () -> staging().stageOrder(2L, incoming));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void shouldRejectMissingOrderAndTradeIdentifiers(String id) {
        IbkrOrderRecord order = order();
        order.setOrderID(id);
        IbkrTradeConfirm confirm = confirm();
        confirm.setTradeID(id);
        assertThrows(CategorizedSyncException.class, () -> staging().stageOrder(2L, order));
        assertThrows(CategorizedSyncException.class, () -> staging().stageTradeConfirm(2L, confirm));
        confirm.setTradeID("SYNTH-TRADE");
        confirm.setOrderID(id);
        assertThrows(CategorizedSyncException.class, () -> staging().stageTradeConfirm(2L, confirm));
        verifyNoInteractions(orders, confirms);
    }

    @ParameterizedTest
    @CsvSource({"order,1", "order,2", "trade,1", "trade,2", "parent,1", "parent,2"})
    void shouldRejectWhitespaceInIdentifiersBeforeAnyDuplicateLookup(String kind, long batch) {
        IbkrOrderRecord order = order();
        IbkrTradeConfirm confirm = confirm();
        if (kind.equals("order")) {
            storedOrder(order);
            order.setOrderID(" " + order.getOrderID());
            assertThrows(CategorizedSyncException.class, () -> staging().stageOrder(batch, order));
            verifyNoInteractions(orders);
        } else {
            storedConfirm(confirm);
            if (kind.equals("trade")) confirm.setTradeID(confirm.getTradeID() + " ");
            else confirm.setOrderID(" " + confirm.getOrderID() + " ");
            assertThrows(CategorizedSyncException.class, () -> staging().stageTradeConfirm(batch, confirm));
            verifyNoInteractions(confirms);
        }
    }

    @Test
    void shouldStoreDifferentIdentifiersAsNewRecords() {
        assertTrue(staging().stageOrder(1L, order()));
        IbkrOrderRecord different = order();
        different.setOrderID("SYNTH-OTHER-ORDER");
        assertTrue(staging().stageOrder(1L, different));
        IbkrTradeConfirm first = confirm();
        first.setCode("P");
        assertTrue(staging().stageTradeConfirm(1L, first));
        IbkrTradeConfirm second = confirm();
        second.setTradeID("SYNTH-OTHER-TRADE");
        assertTrue(staging().stageTradeConfirm(1L, second));
        verify(orders, times(2)).save(any());
        verify(confirms, times(2)).save(any());
    }

    static Stream<Arguments> tradeChanges() {
        return Stream.of(
                change("tradeDate", r -> r.setTradeDate(LocalDate.of(2026, 1, 6))),
                change("brokerId", r -> r.setBrokerId(2L)),
                change("assetType", r -> r.setAssetType(AssetType.ETF)),
                change("symbol", r -> r.setSymbol("SYNTH-OTHER")),
                change("underlyingSymbol", r -> r.setUnderlyingSymbol("SYNTH-OTHER")),
                change("tradeType", r -> r.setTradeType(TradeType.SELL)),
                change("quantity", r -> r.setQuantity(11)),
                change("price", r -> r.setPrice(new BigDecimal("10.0000000000001"))),
                change("amount", r -> r.setAmount(new BigDecimal("100.000000001"))),
                change("fee", r -> r.setFee(new BigDecimal("1.250000000000000001"))),
                change("currency", r -> r.setCurrency(Currency.HKD)),
                change("tradeTrigger", r -> r.setTradeTrigger(TradeTrigger.OPTION)),
                change("triggerRefType", r -> r.setTriggerRefType(TriggerRefType.OPTION_EXERCISE)));
    }

    private static Arguments change(String field, Consumer<TradeRecord> mutation) {
        return Arguments.of(field, mutation);
    }

    @ParameterizedTest
    @MethodSource("tradeChanges")
    void shouldRejectDifferentExistingFormalTrade(String field, Consumer<TradeRecord> mutation) {
        IbkrStagedOrder staged = storedOrder(order());
        IbkrImportWorker worker = new IbkrImportWorker(orders, confirms, trades);
        TradeRecord existing = worker.mapToTradeRecord(1L, 1L, staged);
        existing.setId(55L);
        mutation.accept(existing);
        when(trades.existsByExternalBrokerAndExternalId("ibkr", staged.getOrderId())).thenReturn(true);
        when(trades.findByExternalBrokerAndExternalId("ibkr", staged.getOrderId())).thenReturn(Optional.of(existing));

        var error = assertThrows(ImportOneFailedException.class,
                () -> worker.importSingleOrder(2L, 1L, staged));

        var cause = assertInstanceOf(CategorizedSyncException.class, error.getCause());
        assertEquals(FailureCategory.UNRECOGNIZED, cause.getCategory());
        assertTrue(cause.getReason().contains(field));
        assertTrue(cause.getFormattedMessage().contains("stored-trade-record-row:55"));
        assertEquals(1L, existing.getSyncBatchId());
        assertEquals("PENDING", staged.getStatus());
        verify(trades, never()).save(any());
        verify(orders, never()).save(any());
    }

    @Test
    void shouldSkipMatchingFormalTradeWithoutResettingUserAndDerivedState() {
        IbkrStagedOrder staged = storedOrder(order());
        staged.setBatchId(2L);
        staged.setOrderTime(null);
        staged.setOrderType(null);
        IbkrStagedTradeConfirm event = new IbkrStagedTradeConfirm();
        event.setCode("Ex;P");
        when(confirms.findByOrderId(staged.getOrderId())).thenReturn(List.of(event));
        IbkrImportWorker worker = new IbkrImportWorker(orders, confirms, trades);
        TradeRecord existing = worker.mapToTradeRecord(1L, 1L, staged);
        existing.setId(55L);
        existing.setTriggerRefId(999L);
        existing.setStrategyId(888L);
        existing.setIsDeleted(true);
        existing.setPrice(new BigDecimal("10.0000"));
        when(trades.existsByExternalBrokerAndExternalId("ibkr", staged.getOrderId())).thenReturn(true);
        when(trades.findByExternalBrokerAndExternalId("ibkr", staged.getOrderId())).thenReturn(Optional.of(existing));

        worker.importSingleOrder(2L, 1L, staged);

        assertEquals("SKIPPED", staged.getStatus());
        assertEquals(999L, existing.getTriggerRefId());
        assertEquals(888L, existing.getStrategyId());
        assertTrue(existing.getIsDeleted());
        assertEquals(1L, existing.getSyncBatchId());
        verify(trades, never()).save(any());
    }

    @Test
    void shouldFailIfExistingFormalTradeDisappearsBeforeContentCanBeVerified() {
        IbkrStagedOrder staged = storedOrder(order());
        when(trades.existsByExternalBrokerAndExternalId("ibkr", staged.getOrderId())).thenReturn(true);
        var error = assertThrows(ImportOneFailedException.class,
                () -> new IbkrImportWorker(orders, confirms, trades).importSingleOrder(2L, 1L, staged));
        assertEquals(FailureCategory.INTERNAL,
                assertInstanceOf(CategorizedSyncException.class, error.getCause()).getCategory());
        verify(trades, never()).save(any());
    }

    @Test
    void shouldRejectWhitespaceInFormalIdentifierBeforeCheckingForDuplicates() {
        IbkrStagedOrder staged = new IbkrStagedOrder();
        staged.setId(11L);
        staged.setOrderId("\u2003SYNTH-ORDER\u2003");
        var error = assertThrows(ImportOneFailedException.class,
                () -> new IbkrImportWorker(orders, confirms, trades).importSingleOrder(2L, 1L, staged));
        assertTrue(error.getCause().getMessage().contains("surrounding whitespace"));
        verifyNoInteractions(trades, orders);
    }

    private IbkrStagingWorker staging() {
        return new IbkrStagingWorker(orders, confirms);
    }

    private IbkrStagedOrder storedOrder(IbkrOrderRecord incoming) {
        staging().stageOrder(1L, incoming);
        var captor = ArgumentCaptor.forClass(IbkrStagedOrder.class);
        verify(orders).save(captor.capture());
        IbkrStagedOrder stored = captor.getValue();
        stored.setId(11L);
        lenient().when(orders.findByOrderId(incoming.getOrderID())).thenReturn(Optional.of(stored));
        clearInvocations(orders);
        return stored;
    }

    private IbkrStagedTradeConfirm storedConfirm(IbkrTradeConfirm incoming) {
        staging().stageTradeConfirm(1L, incoming);
        var captor = ArgumentCaptor.forClass(IbkrStagedTradeConfirm.class);
        verify(confirms).save(captor.capture());
        IbkrStagedTradeConfirm stored = captor.getValue();
        stored.setId(12L);
        lenient().when(confirms.findByTradeId(incoming.getTradeID())).thenReturn(Optional.of(stored));
        clearInvocations(confirms);
        return stored;
    }

    private IbkrOrderRecord order() {
        IbkrOrderRecord order = new IbkrOrderRecord();
        order.setOrderID("SYNTH-ORDER");
        order.setAccountId("SYNTH-ACCOUNT");
        order.setCurrency("USD");
        order.setAssetCategory("STK");
        order.setSymbol("SYNTH");
        order.setTradeDate("20260105");
        order.setOrderTime("20260105;100000");
        order.setOrderType("LMT");
        order.setBuySell("BUY");
        order.setQuantity("10");
        order.setPrice("10");
        order.setAmount("100");
        order.setCommission("-1.25");
        return order;
    }

    private IbkrTradeConfirm confirm() {
        IbkrTradeConfirm confirm = new IbkrTradeConfirm();
        confirm.setTradeID("SYNTH-TRADE");
        confirm.setOrderID("SYNTH-ORDER");
        confirm.setAccountId("SYNTH-ACCOUNT");
        confirm.setQuantity("10");
        confirm.setPrice("10");
        confirm.setAmount("100");
        return confirm;
    }
}
