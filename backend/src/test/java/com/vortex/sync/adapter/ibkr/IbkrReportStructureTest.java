package com.vortex.sync.adapter.ibkr;

import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/** Synthetic input contracts, independent of personal XML fixtures and storage. */
class IbkrReportStructureTest {
    private final FlexQueryParser parser = new FlexQueryParser();

    @ParameterizedTest
    @MethodSource("invalidReports")
    void shouldRejectUnsupportedStructureFromString(String xml) {
        assertStructureFailure(() -> parser.parse(xml));
    }

    @ParameterizedTest
    @MethodSource("invalidReports")
    void shouldRejectUnsupportedStructureFromStream(String xml) {
        assertStructureFailure(() -> parser.parse(stream(xml)));
    }

    @ParameterizedTest
    @MethodSource("validReports")
    void shouldAcceptCompleteReportAndPreserveSourceValues(String xml) {
        for (var result : new FlexQueryParseResult[] {parser.parse(xml), parser.parse(stream(xml))}) {
            assertEquals("TCF", result.getType());
            assertEquals("SYNTH-ACCOUNT", result.getAccountId());
            assertEquals(1, result.getOrderCount());
            assertEquals(1, result.getTradeConfirmCount());
            assertEquals("10.000", result.getOrders().get(0).getQuantity());
            assertEquals("-0.1234567890123456789", result.getTradeConfirms().get(0).getCommission());
            assertEquals("O;P", result.getTradeConfirms().get(0).getCode());
        }
    }

    @ParameterizedTest
    @MethodSource("emptyReports")
    void shouldAcceptExplicitlyEmptyCompleteReport(String xml) {
        assertEquals(0, parser.parse(xml).getOrderCount());
        assertEquals(0, parser.parse(stream(xml)).getTradeConfirmCount());
    }

    @Test
    void shouldKeepMalformedXmlAndDoctypeRejectedAtBothEntrypoints() {
        for (String xml : new String[] {"<FlexQueryResponse>",
                "<!DOCTYPE FlexQueryResponse [<!ENTITY example 'SYNTH'>]>" + report("")}) {
            assertThrows(FlexQueryParser.FlexQueryParseException.class, () -> parser.parse(xml));
            assertThrows(FlexQueryParser.FlexQueryParseException.class, () -> parser.parse(stream(xml)));
        }
    }

    private static void assertStructureFailure(org.junit.jupiter.api.function.Executable action) {
        var failure = assertThrows(CategorizedSyncException.class, action);
        assertEquals(FailureCategory.UNRECOGNIZED, failure.getCategory());
        assertNull(failure.getExternalId());
        assertTrue(failure.getMessage().startsWith("Unsupported IBKR TCF report structure:"));
        assertFalse(failure.getMessage().contains("SYNTH"));
    }

    private static ByteArrayInputStream stream(String xml) {
        return new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
    }

    private static Stream<String> validReports() {
        String valid = report(rows());
        return Stream.of(valid,
                valid.replace(" count='1'", ""),
                valid.replace("<TradeConfirms>", "<TradeConfirms>\n<!-- formatting -->\n")
                        .replace("<Order ", "<Order extra='SYNTH-EXTRA' "),
                valid.replace(" accountId='SYNTH-ACCOUNT' quantity", " quantity"),
                valid.replace(" accountId='SYNTH-ACCOUNT' quantity", " accountId='  SYNTH-ACCOUNT  ' quantity"));
    }

    private static Stream<String> emptyReports() {
        return Stream.of(report(""), report(" \n<!-- no transactions -->\n "),
                report("").replace("<TradeConfirms></TradeConfirms>", "<TradeConfirms/>"));
    }

    private static Stream<String> invalidReports() {
        String valid = report(rows());
        String emptyStatement = statement("");
        return Stream.of(
                valid.replace("FlexQueryResponse", "SYNTH-WrongRoot"),
                valid.replace(" type='TCF'", ""),
                valid.replace("type='TCF'", "type='AF'"),
                "<FlexQueryResponse type='TCF'/>",
                "<FlexQueryResponse type='TCF'>" + emptyStatement + "</FlexQueryResponse>",
                valid.replace("</FlexQueryResponse>", "<FlexStatements/>" + "</FlexQueryResponse>"),
                valid.replace("</FlexQueryResponse>", "<SYNTH-Extra/>" + "</FlexQueryResponse>"),
                "<FlexQueryResponse type='TCF'><FlexStatements/></FlexQueryResponse>",
                "<FlexQueryResponse type='TCF'><FlexStatements>" + emptyStatement
                        + statement(rows()) + "</FlexStatements></FlexQueryResponse>",
                "<FlexQueryResponse type='TCF'><FlexStatements>" + statement(rows())
                        + emptyStatement + "</FlexStatements></FlexQueryResponse>",
                valid.replace("count='1'", "count='0'"),
                valid.replace("count='1'", "count='2'"),
                valid.replace("count='1'", "count='SYNTH'"),
                valid.replace("<FlexStatement accountId='SYNTH-ACCOUNT'>", "<FlexStatement>"),
                valid.replace("<FlexStatement accountId='SYNTH-ACCOUNT'>", "<FlexStatement accountId='   '>"),
                valid.replace("<TradeConfirms>" + rows() + "</TradeConfirms>", ""),
                valid.replace("</FlexStatement>", "<TradeConfirms/></FlexStatement>"),
                report("<Order orderID='SYNTH-ORDER'/>"),
                report("<TradeConfirm tradeID='SYNTH-TRADE' orderID='SYNTH-ORDER'/>"),
                report("<SymbolSummary accountId='SYNTH-ACCOUNT'/>"),
                report(rows() + "<SYNTH-Unknown/>"),
                report("<SYNTH-Wrapper>" + rows() + "</SYNTH-Wrapper>"),
                report(rows() + "SYNTH-unexpected-text"),
                report(rows().replace("<Order orderID", "<Order accountId='SYNTH-OTHER' orderID")),
                report(rows().replace("accountId='SYNTH-ACCOUNT' quantity", "accountId='SYNTH-OTHER' quantity")),
                report(rows() + "<SymbolSummary accountId='SYNTH-OTHER'/>"),
                report(rows().replace("<Order orderID='SYNTH-ORDER' quantity='10.000'/>",
                        "<Order orderID='SYNTH-ORDER'><SYNTH-Nested/></Order>")));
    }

    private static String rows() {
        return "<Order orderID='SYNTH-ORDER' quantity='10.000'/>"
                + "<TradeConfirm tradeID='SYNTH-TRADE' orderID='SYNTH-ORDER'"
                + " accountId='SYNTH-ACCOUNT' quantity='10' commission='-0.1234567890123456789' code='O;P'/>";
    }

    private static String statement(String rows) {
        return "<FlexStatement accountId='SYNTH-ACCOUNT'><TradeConfirms>"
                + rows + "</TradeConfirms></FlexStatement>";
    }

    private static String report(String rows) {
        return "<FlexQueryResponse type='TCF'><FlexStatements count='1'>" + statement(rows)
                + "</FlexStatements></FlexQueryResponse>";
    }
}
