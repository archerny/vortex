package com.vortex.sync.adapter.ibkr;

import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/** Field mapping and conversion contracts; structure/security cases live in IbkrReportStructureTest. */
class FlexQueryParserTest {
    private final FlexQueryParser parser = new FlexQueryParser();

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void shouldParseSyntheticTradeFields(boolean fromStream) {
        String xml = """
                <FlexQueryResponse queryName="SYNTHETIC_TCF" type="TCF">
                  <FlexStatements count="1">
                    <FlexStatement accountId="SYNTH-ACCOUNT" fromDate="20260101" toDate="20260131" whenGenerated="20260201;090000">
                      <TradeConfirms>
                        <Order accountId="SYNTH-ACCOUNT" orderID="SYNTH-ORDER" currency="USD" assetCategory="STK"
                          symbol="SYNTH" buySell="BUY" quantity="25" price="42.1250" commission="-0.12345678"/>
                        <Order accountId="SYNTH-ACCOUNT" orderID="SYNTH-BOOK-ORDER" currency="USD" assetCategory="STK"
                          symbol="SYNTH-BOOK" buySell="BUY" quantity="10" price="0"/>
                        <TradeConfirm accountId="SYNTH-ACCOUNT" tradeID="SYNTH-TRADE" orderID="SYNTH-ORDER"
                          currency="USD" assetCategory="STK" symbol="SYNTH" transactionType="ExchTrade"
                          buySell="BUY" quantity="25" price="42.1250" code="O"
                          tradeDate="20260105" dateTime="20260105;153045"/>
                        <TradeConfirm accountId="SYNTH-ACCOUNT" tradeID="SYNTH-BOOK-TRADE" orderID="SYNTH-BOOK-ORDER"
                          currency="USD" assetCategory="STK" symbol="SYNTH-BOOK" transactionType="BookTrade"
                          buySell="BUY" quantity="10" price="0" code="A"/>
                      </TradeConfirms>
                    </FlexStatement>
                  </FlexStatements>
                </FlexQueryResponse>
                """;
        FlexQueryParseResult result = fromStream
                ? parser.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) : parser.parse(xml);

        assertEquals("SYNTHETIC_TCF", result.getQueryName());
        assertEquals("TCF", result.getType());
        assertEquals("SYNTH-ACCOUNT", result.getAccountId());
        assertEquals("20260101", result.getFromDate());
        assertEquals("20260131", result.getToDate());
        assertEquals("20260201;090000", result.getWhenGenerated());
        assertEquals(2, result.getOrderCount());
        assertEquals(2, result.getTradeConfirmCount());

        IbkrOrderRecord order = result.getOrders().get(0);
        assertEquals("SYNTH-ACCOUNT", order.getAccountId());
        assertEquals("SYNTH-ORDER", order.getOrderID());
        assertEquals("SYNTH", order.getSymbol());
        assertEquals("BUY", order.getBuySell());
        assertEquals("USD", order.getCurrency());
        assertEquals("STK", order.getAssetCategory());
        assertEquals("25", order.getQuantity());
        assertEquals("42.1250", order.getPrice());
        assertEquals(new BigDecimal("42.1250"), order.getPriceBigDecimal());
        assertEquals(new BigDecimal("25"), order.getAbsQuantity());
        assertEquals(new BigDecimal("0.12345678"), order.getAbsCommission());
        assertTrue(order.isBuy());
        assertTrue(order.isStock());
        assertFalse(order.isOption());

        IbkrTradeConfirm trade = result.getTradeConfirms().get(0);
        assertEquals("SYNTH-ACCOUNT", trade.getAccountId());
        assertEquals("SYNTH-TRADE", trade.getTradeID());
        assertEquals("SYNTH-ORDER", trade.getOrderID());
        assertEquals("SYNTH", trade.getSymbol());
        assertEquals("ExchTrade", trade.getTransactionType());
        assertEquals("BUY", trade.getBuySell());
        assertEquals("25", trade.getQuantity());
        assertEquals("42.1250", trade.getPrice());
        assertEquals("O", trade.getCode());
        assertTrue(trade.isBuy());
        assertTrue(trade.isExchangeTrade());
        assertFalse(trade.isBookTrade());
        assertTrue(trade.isOpening());
        assertFalse(trade.isClosing());
        assertEquals(LocalDate.of(2026, 1, 5), trade.getTradeDateAsLocalDate());
        assertEquals(LocalDateTime.of(2026, 1, 5, 15, 30, 45), trade.getDateTimeAsLocalDateTime());

        IbkrTradeConfirm bookTrade = result.getTradeConfirms().get(1);
        assertEquals("BookTrade", bookTrade.getTransactionType());
        assertTrue(bookTrade.isBookTrade());
        assertFalse(bookTrade.isExchangeTrade());
    }

    @Test
    void parseEmptyTradeConfirms() {
        String xml = """
                <FlexQueryResponse queryName="TEST" type="TCF">
                  <FlexStatements count="1">
                    <FlexStatement accountId="SYNTH-ACCOUNT">
                      <TradeConfirms/>
                    </FlexStatement>
                  </FlexStatements>
                </FlexQueryResponse>
                """;
        FlexQueryParseResult result = parser.parse(xml);
        assertEquals("TEST", result.getQueryName());
        assertEquals("SYNTH-ACCOUNT", result.getAccountId());
        assertEquals(0, result.getOrderCount());
        assertEquals(0, result.getTradeConfirmCount());
    }

    @Test
    void parseXmlWithoutTradeConfirms() {
        String xml = """
                <FlexQueryResponse queryName="TEST" type="TCF">
                  <FlexStatements count="1">
                    <FlexStatement accountId="SYNTH-ACCOUNT"/>
                  </FlexStatements>
                </FlexQueryResponse>
                """;
        CategorizedSyncException failure = assertThrows(CategorizedSyncException.class, () -> parser.parse(xml));
        assertEquals(FailureCategory.UNRECOGNIZED, failure.getCategory());
        assertTrue(failure.getMessage().contains("TradeConfirms"));
    }
}
