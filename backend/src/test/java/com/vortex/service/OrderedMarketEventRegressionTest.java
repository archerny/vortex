package com.vortex.service;

import com.vortex.dto.PositionSnapshot;
import com.vortex.entity.StockSplitEvent;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.*;
import com.vortex.repository.*;
import com.vortex.sync.core.CategorizedSyncException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderedMarketEventRegressionTest {
    @org.junit.jupiter.api.BeforeEach
    void wireManagedAccountFixture() {
        org.springframework.test.util.ReflectionTestUtils.setField(tradeService, "brokerAccountService", com.vortex.fixtures.AccountFixtures.service("SYNTH-ACCOUNT"));
    }

    @Mock TradeRecordRepository trades;
    @Mock BrokerRepository brokers;
    @Mock StrategyRepository strategies;
    @Mock StockSplitEventRepository splits;
    @Mock SymbolChangeEventRepository changes;
    @Mock DividendInKindEventRepository dividends;
    @Mock PositionService positions;
    @Mock LedgerWriteLock writeLock;
    @Mock BrokerSyncBatchRepository batches;
    @InjectMocks TradeRecordService tradeService;
    @InjectMocks MarketEventProcessingService processor;
    @InjectMocks StockSplitEventService splitService;

    static final LocalDate DATE = LocalDate.of(2026, 3, 10);

    @BeforeEach
    void wireHistoryRules() {
        MarketEventHistoryService history = new MarketEventHistoryService(writeLock, splits, changes, dividends, trades, batches);
        org.springframework.test.util.ReflectionTestUtils.setField(tradeService, "history", history);
        org.springframework.test.util.ReflectionTestUtils.setField(processor, "history", history);
        org.springframework.test.util.ReflectionTestUtils.setField(splitService, "history", history);
    }

    @Test
    void shouldRejectAnEarlierTradeAfterItsSplitWasProcessed() {
        StockSplitEvent event = split(1L, DATE);
        event.setProcessed(true);
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(event));
        when(brokers.existsById(1L)).thenReturn(true);
        TradeRecord trade = new TradeRecord(); trade.setBrokerAccountId(1L);
        trade.setTradeDate(DATE.minusDays(1)); trade.setBrokerId(1L);
        trade.setAssetType(AssetType.STOCK); trade.setSymbol("SYNTH-A");
        trade.setUnderlyingSymbol("SYNTH-A"); trade.setTradeType(TradeType.BUY);
        trade.setQuantity(100); trade.setPrice(BigDecimal.ONE); trade.setFee(BigDecimal.ZERO);
        trade.setCurrency(Currency.USD);
        assertThrows(CategorizedSyncException.class, () -> tradeService.create(trade));
        verify(trades, never()).save(any());
    }

    @Test
    void shouldGenerateOnlyTheRequestedEventWithoutReplayingLaterEvents() {
        StockSplitEvent first = split(1L, DATE);
        StockSplitEvent later = split(2L, DATE.plusDays(10));
        when(splits.findById(1L)).thenReturn(java.util.Optional.of(first));
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(later, first));
        PositionSnapshot position = new PositionSnapshot(); position.setBrokerAccountId(1L);
        position.setSymbol("SYNTH-A"); position.setUnderlyingSymbol("SYNTH-A");
        position.setBrokerId(1L); position.setQuantity(100);
        position.setAssetType(AssetType.STOCK); position.setCurrency(Currency.USD);
        when(positions.calculatePositions(any(), isNull())).thenReturn(List.of(position));
        processor.processStockSplitEvent(first);
        @SuppressWarnings({"unchecked", "rawtypes"})
        org.mockito.ArgumentCaptor<Iterable<TradeRecord>> output = org.mockito.ArgumentCaptor.forClass((Class) Iterable.class);
        verify(trades, times(1)).saveAll(output.capture());
        var generated = new java.util.ArrayList<TradeRecord>();
        output.getValue().forEach(generated::add);
        assertEquals(1, generated.size());
        assertEquals(1L, generated.get(0).getTriggerRefId());
        assertEquals(100, generated.get(0).getQuantity());
        assertEquals(DATE, generated.get(0).getTradeDate());
        assertTrue(first.getProcessed());
        assertFalse(later.getProcessed());
    }

    @Test void updateChecksBothOldAndNewRangeBeforeMutatingExistingRecord() {
        StockSplitEvent event = split(1L, DATE); event.setProcessed(true);
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(event));
        TradeRecord existing = trade("SYNTH-X", DATE.plusDays(1)); existing.setId(5L);
        when(trades.findById(5L)).thenReturn(Optional.of(existing));
        when(brokers.existsById(1L)).thenReturn(true);
        assertThrows(MarketEventConflictException.class, () -> tradeService.update(5L, trade("SYNTH-A", DATE.minusDays(1))));
        assertEquals("SYNTH-X", existing.getSymbol());
        assertEquals(DATE.plusDays(1), existing.getTradeDate());
        existing.setSymbol("SYNTH-A"); existing.setTradeDate(DATE.minusDays(1));
        assertThrows(MarketEventConflictException.class, () -> tradeService.update(5L, trade("SYNTH-X", DATE.plusDays(1))));
        assertThrows(MarketEventConflictException.class, () -> tradeService.softDelete(5L));
        assertFalse(existing.getIsDeleted());
        verify(trades, never()).save(any());
        var order = inOrder(writeLock, trades);
        order.verify(writeLock).acquire(); order.verify(trades).findById(5L);
    }

    @Test void rejectsSystemTradeCreateEditDeleteAndClientSuppliedCreateId() {
        TradeRecord system = trade("SYNTH-A", DATE);
        system.setTradeTrigger(TradeTrigger.MARKET_EVENT); system.setTriggerRefId(1L);
        system.setTriggerRefType(TriggerRefType.STOCK_SPLIT);
        when(brokers.existsById(1L)).thenReturn(true);
        assertThrows(MarketEventConflictException.class, () -> tradeService.create(system));
        system.setId(5L); when(trades.findById(5L)).thenReturn(Optional.of(system));
        assertThrows(MarketEventConflictException.class, () -> tradeService.update(5L, trade("SYNTH-X", DATE)));
        assertThrows(MarketEventConflictException.class, () -> tradeService.softDelete(5L));
        TradeRecord forged = trade("SYNTH-X", DATE); forged.setId(5L);
        assertThrows(IllegalArgumentException.class, () -> tradeService.create(forged));
        assertFalse(system.getIsDeleted()); verify(trades, never()).save(any());
    }

    @Test void publicProcessorReloadsStateAndDoesNotReprocessStaleObject() {
        StockSplitEvent stale = split(1L, DATE);
        StockSplitEvent actual = split(1L, DATE); actual.setProcessed(true);
        when(splits.findById(1L)).thenReturn(Optional.of(actual));
        processor.processStockSplitEvent(stale);
        verifyNoInteractions(positions);
        verify(trades, never()).saveAll(any());
        actual.setIsDeleted(true);
        assertThrows(IllegalArgumentException.class, () -> processor.processStockSplitEvent(stale));
        when(splits.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> processor.processStockSplitEvent(stale));
    }

    static TradeRecord trade(String symbol, LocalDate date) {
        TradeRecord record = new TradeRecord(); record.setBrokerAccountId(1L);
        record.setSymbol(symbol); record.setUnderlyingSymbol(symbol); record.setTradeDate(date);
        record.setBrokerId(1L); record.setQuantity(100); record.setPrice(BigDecimal.ONE);
        record.setFee(BigDecimal.ZERO); record.setCurrency(Currency.USD); record.setAssetType(AssetType.STOCK);
        record.setTradeType(TradeType.BUY); record.setTradeTrigger(TradeTrigger.MANUAL);
        record.setTriggerRefType(TriggerRefType.NONE); record.setTriggerRefId(0L);
        return record;
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(strings = {"deleted", "processed"})
    void legacyNullStateCannotBeProcessedEditedOrUndone(String field) {
        StockSplitEvent stored = split(1L, DATE);
        if (field.equals("deleted")) stored.setIsDeleted(null); else stored.setProcessed(null);
        when(splits.findById(1L)).thenReturn(Optional.of(stored));
        assertThrows(IllegalArgumentException.class, () -> processor.processStockSplitEvent(split(1L, DATE)));
        assertThrows(MarketEventConflictException.class, () -> splitService.update(1L, split(2L, DATE.plusDays(1))));
        assertThrows(MarketEventConflictException.class, () -> splitService.delete(1L));
        assertEquals(DATE, stored.getEventDate());
        verify(splits, never()).save(any()); verify(trades, never()).deleteEventTrades(any(), any(), any());
    }

    static StockSplitEvent split(long id, LocalDate date) {
        StockSplitEvent event = new StockSplitEvent();
        event.setId(id); event.setSymbol("SYNTH-A"); event.setEventDate(date);
        event.setCurrency(Currency.USD); event.setRatioFrom(1); event.setRatioTo(2);
        return event;
    }
}
