package com.vortex.sync.adapter.ibkr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortex.dto.TradeStatistics;
import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TradeType;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import com.vortex.repository.StrategyRepository;
import com.vortex.repository.TradeRecordRepository;
import com.vortex.service.TradeRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeFeePrecisionTest {
    @org.mockito.Mock private com.vortex.service.MarketEventHistoryService eventHistory;
    @Mock private IbkrStagedOrderRepository orders;
    @Mock private IbkrStagedTradeConfirmRepository confirms;
    @Mock private TradeRecordRepository trades;
    @Mock private BrokerRepository brokers;
    @Mock private StrategyRepository strategies;
    @InjectMocks private TradeRecordService service;

    @ParameterizedTest
    @CsvSource(value = {
            "-1.12345678|-0.00000001|1.12345679",
            "-0.99999999|-0.00000001|1.00000000",
            "-0.0000000001234567890123456789|0|0.0000000001234567890123456789",
            "-123456789012.1234567890123456789|-0.0000000000000000001|123456789012.1234567890123456790",
            "0|0|0",
            "||0"
    }, delimiter = '|')
    void shouldPreserveFeeDigitsThroughIbkrImport(String commission, String charge, String expected) {
        IbkrStagedOrder order = new IbkrStagedOrder();
        order.setOrderId("SYNTH-FEE");
        order.setTradeDate("20261117");
        order.setCurrency("USD");
        order.setAssetCategory("STK");
        order.setSymbol("SYNTH");
        order.setBuySell("BUY");
        order.setQuantity("1");
        order.setPrice("10");
        order.setAmount("10");
        order.setOrderType("LMT");
        order.setCommission(commission);
        order.setTradeCharge(charge);
        when(trades.save(any(TradeRecord.class))).thenAnswer(invocation -> {
            TradeRecord record = invocation.getArgument(0);
            record.setId(42L);
            return record;
        });

        new IbkrImportWorker(orders, confirms, trades, eventHistory).importSingleOrder(1L, 1L, order);

        var captor = org.mockito.ArgumentCaptor.forClass(TradeRecord.class);
        verify(trades).save(captor.capture());
        assertEquals(new BigDecimal(expected), captor.getValue().getFee());
        assertEquals("IMPORTED", order.getStatus());
        assertEquals(commission, order.getCommission());
        assertEquals(charge, order.getTradeCharge());
    }

    @Test
    void shouldPreserveFeeInBothJsonRepresentationsAndAcceptDecimalStrings() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String exact = "123456789012.1234567890123456789";
        TradeRecord fromString = mapper.readValue("{\"fee\":\"" + exact + "\"}", TradeRecord.class);
        TradeRecord fromNumber = mapper.readValue("{\"fee\":" + exact + "}", TradeRecord.class);
        assertEquals(new BigDecimal(exact), fromString.getFee());
        assertEquals(fromString.getFee(), fromNumber.getFee());

        String json = mapper.writeValueAsString(fromString);
        assertTrue(json.contains("\"fee\":" + exact));
        assertEquals(exact, mapper.readTree(json).get("feeExact").textValue());
        assertEquals(fromString.getFee(), mapper.readValue(json, TradeRecord.class).getFee());
        assertEquals(new BigDecimal("1.25"), mapper.readValue(
                "{\"fee\":1.25,\"feeExact\":\"999.99\"}", TradeRecord.class).getFee());
    }

    @Test
    void shouldPreserveFeeWhenCreatingAndUpdatingTrades() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TradeRecord record = stock(mapper, "1.1234567890123456789");
        when(brokers.existsById(1L)).thenReturn(true);
        when(trades.save(any(TradeRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));
        TradeRecord created = service.create(record);
        assertEquals(new BigDecimal("1.1234567890123456789"), created.getFee());

        created.setId(42L);
        when(trades.findById(42L)).thenReturn(Optional.of(created));
        TradeRecord edited = stock(mapper, "0.0000000001234567890123456789");
        edited.setSymbol("SYNTH-UPDATED");
        TradeRecord updated = service.update(42L, edited);
        assertEquals(edited.getFee(), updated.getFee());
        assertEquals("SYNTH-UPDATED", updated.getSymbol());
    }

    @Test
    void shouldReturnFullPrecisionStatisticsWithoutRoundingOrCurrencyMixing() throws Exception {
        BigDecimal usd = new BigDecimal("123456789012.1234567890123456789");
        BigDecimal cny = new BigDecimal("0.0000000001234567890123456789");
        BigDecimal hkd = new BigDecimal("1.25");
        when(trades.sumFeeByIsDeletedFalseAndCurrency(Currency.USD)).thenReturn(usd);
        when(trades.sumFeeByIsDeletedFalseAndCurrency(Currency.CNY)).thenReturn(cny);
        when(trades.sumFeeByIsDeletedFalseAndCurrency(Currency.HKD)).thenReturn(hkd);
        when(trades.findTopTradedSymbols()).thenReturn(List.of());
        TradeStatistics stats = service.getStatistics();
        assertEquals(usd, stats.getTotalFeeUSD());
        assertEquals(cny, stats.getTotalFeeCNY());
        assertEquals(hkd, stats.getTotalFeeHKD());
        String json = new ObjectMapper().writeValueAsString(stats);
        var tree = new ObjectMapper().readTree(json);
        assertTrue(tree.get("totalFeeUSD").isNumber());
        assertEquals(usd.toPlainString(), tree.get("totalFeeUSDExact").textValue());
        assertEquals(cny.toPlainString(), tree.get("totalFeeCNYExact").textValue());
        assertEquals(hkd.toPlainString(), tree.get("totalFeeHKDExact").textValue());
        TradeStatistics reread = new ObjectMapper().readValue(json, TradeStatistics.class);
        assertEquals(usd, reread.getTotalFeeUSD());
        assertEquals(cny, reread.getTotalFeeCNY());
        assertEquals(hkd, reread.getTotalFeeHKD());
    }

    @Test
    void shouldKeepZeroAndMissingStatisticsDistinctAndIgnoreExactInputFields() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TradeStatistics stats = mapper.readValue("{\"totalFeeUSD\":0,\"totalFeeUSDExact\":\"999\"}", TradeStatistics.class);
        var tree = mapper.readTree(mapper.writeValueAsString(stats));
        assertEquals("0", tree.get("totalFeeUSDExact").textValue());
        assertTrue(tree.get("totalFeeCNYExact").isNull());
        assertTrue(tree.get("totalFeeHKDExact").isNull());
    }

    private TradeRecord stock(ObjectMapper mapper, String fee) throws Exception {
        TradeRecord record = mapper.readValue("{\"fee\":\"" + fee + "\"}", TradeRecord.class);
        record.setTradeDate(LocalDate.of(2026, 11, 17));
        record.setBrokerId(1L);
        record.setSymbol("SYNTH");
        record.setUnderlyingSymbol("SYNTH");
        record.setAssetType(AssetType.STOCK);
        record.setTradeType(TradeType.BUY);
        record.setQuantity(1);
        record.setPrice(BigDecimal.TEN);
        return record;
    }
}
