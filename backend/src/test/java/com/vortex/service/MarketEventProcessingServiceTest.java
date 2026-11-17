package com.vortex.service;

import com.vortex.entity.SymbolChangeEvent;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TradeType;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.DividendInKindEventRepository;
import com.vortex.repository.StockSplitEventRepository;
import com.vortex.repository.SymbolChangeEventRepository;
import com.vortex.repository.TradeRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketEventProcessingServiceTest {
    @org.mockito.Mock private com.vortex.service.MarketEventHistoryService eventHistory;

    private static final LocalDate EVENT_DATE = LocalDate.of(2026, 4, 1);
    private static final LocalDate AS_OF = EVENT_DATE.minusDays(1);
    private static final String OLD = "SYNTH-OLD";
    private static final String NEW = "SYNTH-NEW";

    @Mock private TradeRecordRepository trades;
    @Mock private BrokerRepository brokers;
    @Mock private StockSplitEventRepository splits;
    @Mock private SymbolChangeEventRepository changes;
    @Mock private DividendInKindEventRepository dividends;
    @Spy private PositionService positions = new PositionService();
    @InjectMocks private MarketEventProcessingService service;

    @BeforeEach
    void wireRealPositionCalculation() {
        ReflectionTestUtils.setField(positions, "tradeRecordRepository", trades);
        ReflectionTestUtils.setField(positions, "brokerRepository", brokers);
    }

    private void add(List<TradeRecord> history, String symbol, long broker, TradeType type,
                     int quantity, String amount) {
        TradeRecord record = new TradeRecord();
        record.setId((long) history.size() + 1);
        record.setTradeDate(LocalDate.of(2026, 1, 1).plusDays(history.size()));
        record.setBrokerId(broker);
        record.setAssetType(AssetType.STOCK);
        record.setSymbol(symbol);
        record.setUnderlyingSymbol(symbol);
        record.setCurrency(Currency.USD);
        record.setTradeType(type);
        record.setQuantity(quantity);
        record.setPrice(BigDecimal.ONE);
        record.setAmount(new BigDecimal(amount));
        record.setFee(BigDecimal.ZERO);
        record.setIsDeleted(false);
        record.setTradeTrigger(TradeTrigger.MANUAL);
        record.setTriggerRefType(TriggerRefType.NONE);
        record.setTriggerRefId(0L);
        history.add(record);
    }

    private List<TradeRecord> fractionalAverageHistory(int[] sells) {
        List<TradeRecord> history = new ArrayList<>();
        add(history, OLD, 1L, TradeType.BUY, 10000, "10000.00");
        add(history, OLD, 1L, TradeType.BUY, 20000, "40000.00");
        for (int quantity : sells) add(history, OLD, 1L, TradeType.SELL, quantity, "0.00");
        return history;
    }

    private SymbolChangeEvent givenHistory(List<TradeRecord> history, boolean hasPosition) {
        SymbolChangeEvent event = new SymbolChangeEvent();
        event.setId(42L);
        event.setSymbol(OLD);
        event.setOldSymbol(OLD);
        event.setNewSymbol(NEW);
        event.setEventDate(EVENT_DATE);
        event.setCurrency(Currency.USD);
        when(changes.findById(42L)).thenReturn(java.util.Optional.of(event));
        when(trades.findByTradeDateLessThanEqualAndIsDeletedFalseOrderByTradeDateAsc(AS_OF))
                .thenReturn(history);
        if (hasPosition) {
            when(trades.findByTradeDateLessThanEqualAndBrokerIdAndIsDeletedFalseOrderByTradeDateAsc(
                    eq(AS_OF), anyLong())).thenAnswer(invocation -> history.stream()
                    .filter(record -> record.getBrokerId().equals(invocation.getArgument(1)))
                    .toList());
        }
        return event;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<TradeRecord> generatedRecords() {
        ArgumentCaptor<Iterable<TradeRecord>> captor = ArgumentCaptor.forClass((Class) Iterable.class);
        verify(trades).saveAll(captor.capture());
        List<TradeRecord> records = new ArrayList<>();
        captor.getValue().forEach(records::add);
        return records;
    }

    private void assertTransfer(List<TradeRecord> records, long broker, int quantity, String price, String amount) {
        List<TradeRecord> pair = records.stream().filter(record -> record.getBrokerId() == broker).toList();
        assertEquals(2, pair.size());
        assertEquals(TradeType.SELL, pair.get(0).getTradeType());
        assertEquals(OLD, pair.get(0).getSymbol());
        assertEquals(TradeType.BUY, pair.get(1).getTradeType());
        assertEquals(NEW, pair.get(1).getSymbol());
        for (TradeRecord record : pair) {
            assertEquals(quantity, record.getQuantity());
            assertEquals(0, new BigDecimal(price).compareTo(record.getPrice()));
            assertEquals(0, new BigDecimal(amount).compareTo(record.getAmount()));
            assertEquals(EVENT_DATE, record.getTradeDate());
            assertEquals(Currency.USD, record.getCurrency());
            assertEquals(0, BigDecimal.ZERO.compareTo(record.getFee()));
            assertEquals(TradeTrigger.MARKET_EVENT, record.getTradeTrigger());
            assertEquals(TriggerRefType.SYMBOL_CHANGE, record.getTriggerRefType());
            assertEquals(42L, record.getTriggerRefId());
        }
    }

    static Stream<int[]> closeOuts() {
        return Stream.of(new int[] {30000}, new int[] {10000, 20000}, new int[] {9999, 10000, 10001});
    }

    @ParameterizedTest
    @MethodSource("closeOuts")
    void liquidationThenReentryShouldTransferOnlyTheNewCost(int[] sells) {
        List<TradeRecord> history = fractionalAverageHistory(sells);
        add(history, OLD, 1L, TradeType.BUY, 1, "10.00");
        List<BigDecimal> originalAmounts = history.stream().map(TradeRecord::getAmount).toList();
        SymbolChangeEvent event = givenHistory(history, true);

        service.processSymbolChangeEvent(event);

        assertTransfer(generatedRecords(), 1L, 1, "10.0000", "10.00");
        assertEquals(originalAmounts, history.stream().map(TradeRecord::getAmount).toList());
        assertTrue(event.getProcessed());
    }

    @Test
    void zeroCostEventSharesShouldNotLeaveAPositiveTailAfterLiquidation() {
        List<TradeRecord> history = new ArrayList<>();
        add(history, OLD, 1L, TradeType.BUY, 10000, "10000.00");
        add(history, OLD, 1L, TradeType.BUY, 20000, "0.00");
        TradeRecord split = history.get(1);
        split.setPrice(BigDecimal.ZERO);
        split.setTradeTrigger(TradeTrigger.MARKET_EVENT);
        split.setTriggerRefType(TriggerRefType.STOCK_SPLIT);
        split.setTriggerRefId(7L);
        add(history, OLD, 1L, TradeType.SELL, 30000, "0.00");
        add(history, OLD, 1L, TradeType.BUY, 1, "10.00");
        SymbolChangeEvent event = givenHistory(history, true);

        service.processSymbolChangeEvent(event);

        assertTransfer(generatedRecords(), 1L, 1, "10.0000", "10.00");
    }

    @Test
    void repeatedLiquidationsShouldNotCarryCostAcrossPositionLifetimes() {
        List<TradeRecord> history = fractionalAverageHistory(new int[] {30000});
        add(history, OLD, 1L, TradeType.BUY, 10000, "10000.00");
        add(history, OLD, 1L, TradeType.BUY, 20000, "40000.00");
        add(history, OLD, 1L, TradeType.SELL, 30000, "0.00");
        add(history, OLD, 1L, TradeType.BUY, 1, "10.00");
        SymbolChangeEvent event = givenHistory(history, true);

        service.processSymbolChangeEvent(event);

        assertTransfer(generatedRecords(), 1L, 1, "10.0000", "10.00");
    }

    @Test
    void partialSaleShouldRetainTheExistingAverageCostPrecision() {
        List<TradeRecord> history = new ArrayList<>();
        add(history, OLD, 1L, TradeType.BUY, 3, "3.00");
        add(history, OLD, 1L, TradeType.BUY, 4, "8.00");
        add(history, OLD, 1L, TradeType.SELL, 2, "0.00");
        SymbolChangeEvent event = givenHistory(history, true);

        service.processSymbolChangeEvent(event);

        assertTransfer(generatedRecords(), 1L, 5, "1.5714", "7.8570");
    }

    @Test
    void closedPositionWithoutReentryShouldGenerateNoTransfer() {
        SymbolChangeEvent event = givenHistory(fractionalAverageHistory(new int[] {30000}), false);

        service.processSymbolChangeEvent(event);

        verify(trades, never()).saveAll(any());
        assertTrue(event.getProcessed());
    }

    @Test
    void otherSymbolsAndBrokersShouldNotAffectTheReopenedCost() {
        List<TradeRecord> history = fractionalAverageHistory(new int[] {30000});
        add(history, "SYNTH-OTHER", 1L, TradeType.BUY, 100, "12300.00");
        add(history, OLD, 2L, TradeType.BUY, 7, "140.00");
        add(history, OLD, 1L, TradeType.BUY, 1, "10.00");
        SymbolChangeEvent event = givenHistory(history, true);

        service.processSymbolChangeEvent(event);

        List<TradeRecord> records = generatedRecords();
        assertEquals(4, records.size());
        assertTransfer(records, 1L, 1, "10.0000", "10.00");
        assertTransfer(records, 2L, 7, "20.0000", "140.00");
    }
}
