package com.vortex.service;

import com.vortex.entity.*;
import com.vortex.entity.enums.*;
import com.vortex.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketEventHistoryServiceTest {
    @Mock LedgerWriteLock writeLock;
    @Mock StockSplitEventRepository splits;
    @Mock SymbolChangeEventRepository changes;
    @Mock DividendInKindEventRepository dividends;
    @Mock TradeRecordRepository trades;
    @Mock BrokerSyncBatchRepository batches;
    @InjectMocks MarketEventHistoryService service;
    static final LocalDate DATE = LocalDate.of(2026, 3, 10);

    StockSplitEvent split(long id, String symbol, LocalDate date) {
        StockSplitEvent event = new StockSplitEvent();
        event.setId(id); event.setSymbol(symbol); event.setEventDate(date);
        event.setRatioFrom(1); event.setRatioTo(2); event.setProcessed(true);
        return event;
    }

    TradeRecord trade(String symbol, LocalDate date) {
        TradeRecord record = new TradeRecord(); record.setBrokerAccountId(1L);
        record.setSymbol(symbol); record.setTradeDate(date); record.setBrokerId(999L);
        record.setTradeTrigger(TradeTrigger.MANUAL);
        return record;
    }

    void code(String expected, Runnable action) {
        assertEquals(expected, assertThrows(MarketEventConflictException.class, action::run).getCode());
    }

    @Test void splitLocksOnlyEarlierOwnSymbolAcrossBrokers() {
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(split(1, "SYNTH-A", DATE)));
        code("HISTORY_LOCKED", () -> service.assertHistoryWritable(trade("SYNTH-A", DATE.minusDays(1))));
        assertDoesNotThrow(() -> service.assertHistoryWritable(trade("SYNTH-A", DATE)));
        assertDoesNotThrow(() -> service.assertHistoryWritable(trade("SYNTH-A", DATE.plusDays(1))));
        assertDoesNotThrow(() -> service.assertHistoryWritable(trade("SYNTH-X", DATE.minusYears(1))));
        TradeRecord option = trade("SYNTH-A-OPTION", DATE.minusDays(1));
        option.setUnderlyingSymbol("SYNTH-A");
        assertDoesNotThrow(() -> service.assertHistoryWritable(option));
    }

    @Test void renameLocksBothCodesAndDividendLocksBothSecurities() {
        SymbolChangeEvent rename = new SymbolChangeEvent();
        rename.setId(1L); rename.setOldSymbol("SYNTH-A"); rename.setNewSymbol("SYNTH-B");
        rename.setEventDate(DATE); rename.setProcessed(true);
        when(changes.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(rename));
        DividendInKindEvent dividend = new DividendInKindEvent();
        dividend.setId(1L); dividend.setSymbol("SYNTH-C"); dividend.setDividendSymbol("SYNTH-D");
        dividend.setEventDate(DATE); dividend.setProcessed(true);
        when(dividends.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(dividend));
        for (String symbol : List.of("SYNTH-A", "SYNTH-B", "SYNTH-C", "SYNTH-D")) {
            code("HISTORY_LOCKED", () -> service.assertHistoryWritable(trade(symbol, DATE.minusDays(1))));
        }
        // Historical lock derivation does not need old ratio/currency calculation fields or derived rows.
        verifyNoInteractions(trades);
    }

    @Test void unprocessedAndDeletedEventsDoNotLockButZeroOutputProcessedEventDoes() {
        StockSplitEvent unprocessed = split(1, "SYNTH-A", DATE); unprocessed.setProcessed(false);
        StockSplitEvent deleted = split(2, "SYNTH-B", DATE); deleted.setIsDeleted(true);
        StockSplitEvent zeroOutput = split(3, "SYNTH-C", DATE);
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(unprocessed, deleted, zeroOutput));
        assertDoesNotThrow(() -> service.assertHistoryWritable(trade("SYNTH-A", DATE.minusDays(1))));
        assertDoesNotThrow(() -> service.assertHistoryWritable(trade("SYNTH-B", DATE.minusDays(1))));
        code("HISTORY_LOCKED", () -> service.assertHistoryWritable(trade("SYNTH-C", DATE.minusDays(1))));
    }

    @Test void newestConstraintWinsAndRemovingItLeavesOlderConstraint() {
        StockSplitEvent old = split(1, "SYNTH-A", DATE);
        StockSplitEvent latest = split(2, "SYNTH-A", DATE.plusDays(10));
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(old, latest));
        var error = assertThrows(MarketEventConflictException.class,
                () -> service.assertHistoryWritable(trade("SYNTH-A", DATE.minusDays(1))));
        assertEquals(2L, error.getEventId());
        latest.setIsDeleted(true);
        code("HISTORY_LOCKED", () -> service.assertHistoryWritable(trade("SYNTH-A", DATE.minusDays(1))));
        assertDoesNotThrow(() -> service.assertHistoryWritable(trade("SYNTH-A", DATE.plusDays(1))));
    }

    @ParameterizedTest @ValueSource(ints = {0, 1, 10})
    void relatedSameOrLaterEventBlocksProcessingAndUndo(int offset) {
        StockSplitEvent next = split(2, "SYNTH-A", DATE.plusDays(offset));
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(next));
        StockSplitEvent candidate = split(1, "SYNTH-A", DATE); candidate.setProcessed(false);
        code("EVENT_ORDER_CONFLICT", () -> service.assertCanProcess(candidate, TriggerRefType.STOCK_SPLIT));
        candidate.setProcessed(true);
        code("EVENT_ORDER_CONFLICT", () -> service.assertCanUndo(candidate, TriggerRefType.STOCK_SPLIT));
    }

    @Test void earlierRelatedAndSameDayUnrelatedEventsAreAllowed() {
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(
                split(1, "SYNTH-A", DATE.minusDays(1)), split(2, "SYNTH-X", DATE)));
        StockSplitEvent candidate = split(3, "SYNTH-A", DATE); candidate.setProcessed(false);
        assertDoesNotThrow(() -> service.assertCanProcess(candidate, TriggerRefType.STOCK_SPLIT));
    }

    @Test void selfExclusionRequiresTypeAndId() {
        StockSplitEvent self = split(1, "SYNTH-A", DATE);
        when(splits.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(self));
        assertDoesNotThrow(() -> service.assertCanUndo(self, TriggerRefType.STOCK_SPLIT));
        SymbolChangeEvent differentTypeSameId = new SymbolChangeEvent();
        differentTypeSameId.setId(1L); differentTypeSameId.setOldSymbol("SYNTH-X");
        differentTypeSameId.setNewSymbol("SYNTH-A"); differentTypeSameId.setEventDate(DATE);
        differentTypeSameId.setProcessed(true);
        when(changes.findByIsDeletedFalseOrderByEventDateDesc()).thenReturn(List.of(differentTypeSameId));
        code("EVENT_ORDER_CONFLICT", () -> service.assertCanUndo(self, TriggerRefType.STOCK_SPLIT));
    }

    @Test void ordinaryFollowingTradesBlockProcessingAndUndoOnInclusiveDateBoundary() {
        when(trades.existsBySymbolInAndTradeDateGreaterThanEqualAndTradeTriggerNotAndIsDeletedFalse(
                Set.of("SYNTH-A"), DATE, TradeTrigger.MARKET_EVENT)).thenReturn(true);
        StockSplitEvent event = split(1, "SYNTH-A", DATE); event.setProcessed(false);
        code("FOLLOWING_TRADES_EXIST", () -> service.assertCanProcess(event, TriggerRefType.STOCK_SPLIT));
        event.setProcessed(true);
        code("FOLLOWING_TRADES_EXIST", () -> service.assertCanUndo(event, TriggerRefType.STOCK_SPLIT));
    }

    @ParameterizedTest @ValueSource(strings = {"PENDING", "PROCESSING", "CLEANUP_FAILED"})
    void activeSyncBlocksEveryEventMutation(String state) {
        BrokerSyncBatch batch = new BrokerSyncBatch(); batch.setStatus(state);
        when(batches.findFirstByStatusInOrderByIdDesc(Set.of("PENDING", "PROCESSING", "CLEANUP_FAILED")))
                .thenReturn(Optional.of(batch));
        StockSplitEvent event = split(1, "SYNTH-A", DATE);
        code("SYNC_IN_PROGRESS", () -> service.assertCanProcess(event, TriggerRefType.STOCK_SPLIT));
        code("SYNC_IN_PROGRESS", () -> service.assertEditable(event, TriggerRefType.STOCK_SPLIT));
        code("SYNC_IN_PROGRESS", () -> service.assertCanUndo(event, TriggerRefType.STOCK_SPLIT));
    }

    @Test void rejectsManagedCreateStateProcessedEditsAndManualSystemTrades() {
        StockSplitEvent event = split(1, "SYNTH-A", DATE);
        assertThrows(IllegalArgumentException.class, () -> service.assertNewEvent(event, TriggerRefType.STOCK_SPLIT));
        event.setId(null);
        assertThrows(IllegalArgumentException.class, () -> service.assertNewEvent(event, TriggerRefType.STOCK_SPLIT));
        code("PROCESSED_EVENT_IMMUTABLE", () -> service.assertEditable(event, TriggerRefType.STOCK_SPLIT));
        TradeRecord record = trade("SYNTH-A", DATE); record.setTradeTrigger(TradeTrigger.MARKET_EVENT);
        code("SYSTEM_TRADE_READ_ONLY", () -> service.assertUserTradeWritable(record));
    }

    @Test void residualRowsOfUnprocessedEventBlockProcessingEditingAndUndo() {
        StockSplitEvent event = split(1, "SYNTH-A", DATE); event.setProcessed(false);
        when(trades.existsByTradeTriggerAndTriggerRefTypeAndTriggerRefId(
                TradeTrigger.MARKET_EVENT, TriggerRefType.STOCK_SPLIT, 1L)).thenReturn(true);
        code("LEGACY_EVENT_STATE", () -> service.assertCanProcess(event, TriggerRefType.STOCK_SPLIT));
        code("LEGACY_EVENT_STATE", () -> service.assertEditable(event, TriggerRefType.STOCK_SPLIT));
        code("LEGACY_EVENT_STATE", () -> service.assertCanUndo(event, TriggerRefType.STOCK_SPLIT));
    }

    @Test void explicitNullManagedFlagsCannotCreateAnEventInvisibleToHistoryQueries() {
        StockSplitEvent event = split(1, "SYNTH-A", DATE); event.setId(null); event.setProcessed(false);
        event.setIsDeleted(null);
        assertThrows(IllegalArgumentException.class, () -> service.assertNewEvent(event, TriggerRefType.STOCK_SPLIT));
        event.setIsDeleted(false); event.setProcessed(null);
        assertThrows(IllegalArgumentException.class, () -> service.assertNewEvent(event, TriggerRefType.STOCK_SPLIT));
    }

    @Test void rejectsMissingOrNoncanonicalIdentityAndInvalidNewRatios() {
        for (String symbol : new String[]{null, "", " SYNTH-A", "SYNTH-A "}) {
            assertThrows(IllegalArgumentException.class, () -> service.assertHistoryWritable(trade(symbol, DATE)));
        }
        StockSplitEvent event = split(1, "SYNTH-A", DATE); event.setProcessed(false); event.setRatioFrom(0);
        assertThrows(IllegalArgumentException.class, () -> service.assertCanProcess(event, TriggerRefType.STOCK_SPLIT));
        event.setRatioFrom(1); event.setEventDate(LocalDate.MIN);
        assertThrows(IllegalArgumentException.class, () -> service.assertCanProcess(event, TriggerRefType.STOCK_SPLIT));
    }
}
