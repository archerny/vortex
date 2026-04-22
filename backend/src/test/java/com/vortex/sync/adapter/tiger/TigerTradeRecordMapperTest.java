package com.vortex.sync.adapter.tiger;

import com.vortex.entity.TigerStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TradeType;
import com.vortex.entity.enums.TriggerRefType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TigerTradeRecordMapper}.
 *
 * <p>All cases exercise the pure mapping logic without Spring context or mocks.
 */
class TigerTradeRecordMapperTest {

    private final TigerTradeRecordMapper mapper = new TigerTradeRecordMapper();

    private static final Long BROKER_ID = 77L;
    private static final Long BATCH_ID = 42L;

    // Fixed timestamp: 2026-03-10 10:30:00 Asia/Shanghai → epoch ms
    private static final long TRADE_TIME_MS =
            ZonedDateTime.of(2026, 3, 10, 10, 30, 0, 0, ZoneId.of("Asia/Shanghai"))
                    .toInstant().toEpochMilli();
    private static final String TRADE_TIME_STR = String.valueOf(TRADE_TIME_MS);

    // ============ Helpers ============

    private TigerStagedOrder baseStk() {
        TigerStagedOrder s = new TigerStagedOrder();
        s.setTigerId("TG100001");
        s.setAccount("U1234567");
        s.setAction("BUY");
        s.setStatusRaw("FILLED");
        s.setOrderTime(TRADE_TIME_STR);
        s.setTradeTime(TRADE_TIME_STR);
        s.setQuantity("100");
        s.setQuantityScale("0");
        s.setFilledQuantity("100");
        s.setAvgFillPrice("150.25");
        s.setCommission("1.99");
        s.setGst(null);
        s.setSymbol("AAPL");
        s.setContractName("Apple Inc.");
        s.setSecType("STK");
        s.setCurrency("USD");
        s.setExchange("NASDAQ");
        s.setMarket("US");
        s.setIdentifier(null);
        s.setMultiplier(null);
        s.setExpiry(null);
        s.setStrike(null);
        s.setPutCall(null);
        s.setOrderType("LMT");
        s.setLimitPrice("150.30");
        s.setAttrDesc(null);
        return s;
    }

    private TigerStagedOrder baseOptCall() {
        TigerStagedOrder s = baseStk();
        s.setTigerId("TG200001");
        s.setSymbol("AAPL");
        s.setContractName("AAPL 20260130 265 CALL");
        s.setSecType("OPT");
        s.setCurrency("USD");
        s.setMultiplier("100");
        s.setExpiry("20260130");
        s.setStrike("265");
        s.setPutCall("CALL");
        s.setFilledQuantity("2");
        s.setAvgFillPrice("3.50");
        s.setCommission("0.65");
        return s;
    }

    // ============ Case 1: STK BUY / USD ============

    @Test
    @DisplayName("STK BUY / USD / 100 shares → normal mapping")
    void stkBuyUsd() {
        TigerStagedOrder s = baseStk();
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertTrue(fr.isPass());

        TradeRecord tr = mapper.toTradeRecord(s, BROKER_ID, BATCH_ID);

        assertEquals(LocalDate.of(2026, 3, 10), tr.getTradeDate());
        assertEquals(BROKER_ID, tr.getBrokerId());
        assertEquals(Currency.USD, tr.getCurrency());
        assertEquals(TradeType.BUY, tr.getTradeType());
        assertEquals(100, tr.getQuantity());
        assertEquals(0, new BigDecimal("150.25").compareTo(tr.getPrice()));
        // amount = 100 * 150.25 * 1 = 15025.00
        assertEquals(0, new BigDecimal("15025.00").compareTo(tr.getAmount()));
        assertEquals(0, new BigDecimal("1.99").compareTo(tr.getFee()));
        assertEquals("TG100001", tr.getExternalId());
        assertEquals("tiger", tr.getExternalBroker());
        assertEquals(BATCH_ID, tr.getSyncBatchId());
        assertEquals(AssetType.STOCK, tr.getAssetType());
        assertEquals("AAPL", tr.getSymbol());
        assertEquals("AAPL", tr.getUnderlyingSymbol());
        assertEquals(TradeTrigger.MANUAL, tr.getTradeTrigger());
        assertEquals(TriggerRefType.NONE, tr.getTriggerRefType());
        assertEquals(0L, tr.getTriggerRefId());
        assertNull(tr.getStrategyId());
        assertEquals(Boolean.FALSE, tr.getIsDeleted());
    }

    // ============ Case 2: STK SELL / HKD / HK 5-digit ============

    @Test
    @DisplayName("STK SELL / HKD / HK 5-digit symbol")
    void stkSellHkd() {
        TigerStagedOrder s = baseStk();
        s.setAction("SELL");
        s.setCurrency("HKD");
        s.setMarket("HK");
        s.setExchange("SEHK");
        s.setSymbol("00700");
        s.setFilledQuantity("200");
        s.setAvgFillPrice("320.00");
        s.setCommission("10.00");

        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertTrue(fr.isPass());

        TradeRecord tr = mapper.toTradeRecord(s, BROKER_ID, BATCH_ID);
        assertEquals(TradeType.SELL, tr.getTradeType());
        assertEquals(Currency.HKD, tr.getCurrency());
        assertEquals(200, tr.getQuantity());
        assertEquals("00700", tr.getSymbol());
        assertEquals(0, new BigDecimal("64000.00").compareTo(tr.getAmount()));
    }

    // ============ Case 3: CNH → CNY ============

    @Test
    @DisplayName("STK / CNH → system CNY")
    void cnhMapsToCny() {
        TigerStagedOrder s = baseStk();
        s.setCurrency("CNH");
        s.setMarket("CN");
        s.setSymbol("600519");
        s.setFilledQuantity("100");
        s.setAvgFillPrice("1680.00");

        TradeRecord tr = mapper.toTradeRecord(s, BROKER_ID, BATCH_ID);
        assertEquals(Currency.CNY, tr.getCurrency());
        assertEquals("600519", tr.getSymbol());
    }

    // ============ Case 4: OPT CALL → symbol build + OPTION_CALL ============

    @Test
    @DisplayName("OPT CALL → AAPL-20260130-C265, asset_type=OPTION_CALL, amount includes multiplier")
    void optCall() {
        TigerStagedOrder s = baseOptCall();
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertTrue(fr.isPass());

        TradeRecord tr = mapper.toTradeRecord(s, BROKER_ID, BATCH_ID);
        assertEquals(AssetType.OPTION_CALL, tr.getAssetType());
        assertEquals("AAPL-20260130-C265", tr.getSymbol());
        assertEquals("AAPL", tr.getUnderlyingSymbol());
        // amount = 2 * 3.50 * 100 = 700.00
        assertEquals(0, new BigDecimal("700.00").compareTo(tr.getAmount()));
    }

    // ============ Case 5: OPT PUT with decimal strike ============

    @Test
    @DisplayName("OPT PUT + decimal strike 17.50 → normalized to 17.5, asset_type=OPTION_PUT")
    void optPutDecimalStrike() {
        TigerStagedOrder s = baseOptCall();
        s.setPutCall("PUT");
        s.setStrike("17.50");
        s.setSymbol("F");
        s.setExpiry("20260220");
        s.setFilledQuantity("5");
        s.setAvgFillPrice("0.75");

        TradeRecord tr = mapper.toTradeRecord(s, BROKER_ID, BATCH_ID);
        assertEquals(AssetType.OPTION_PUT, tr.getAssetType());
        assertEquals("F-20260220-P17.5", tr.getSymbol());
        // amount = 5 * 0.75 * 100 = 375.00
        assertEquals(0, new BigDecimal("375.00").compareTo(tr.getAmount()));
    }

    // ============ Case 6: filledQuantity=0 → SKIPPED ============

    @Test
    @DisplayName("filled_quantity=0 → SKIPPED (not actually filled)")
    void filledZeroSkipped() {
        TigerStagedOrder s = baseStk();
        s.setFilledQuantity("0");
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.SKIPPED, fr.getKind());
        assertTrue(fr.getMessage().contains("Not actually filled"));
    }

    @Test
    @DisplayName("filled_quantity=null → SKIPPED")
    void filledNullSkipped() {
        TigerStagedOrder s = baseStk();
        s.setFilledQuantity(null);
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.SKIPPED, fr.getKind());
    }

    // ============ Case 7-8: unsupported secType ============

    @Test
    @DisplayName("secType=WAR → FAILED(Unsupported secType)")
    void secTypeWarFailed() {
        TigerStagedOrder s = baseStk();
        s.setSecType("WAR");
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.FAILED, fr.getKind());
        assertTrue(fr.getMessage().contains("Unsupported secType: WAR"));
    }

    @Test
    @DisplayName("secType=FUT / FUND → FAILED")
    void secTypeFutFundFailed() {
        for (String t : new String[]{"FUT", "FUND", "IOPT", "CASH", "CC"}) {
            TigerStagedOrder s = baseStk();
            s.setSecType(t);
            TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
            assertEquals(TigerTradeRecordMapper.FilterResult.Kind.FAILED, fr.getKind(),
                    "secType=" + t + " should be FAILED");
            assertTrue(fr.getMessage().contains("Unsupported secType: " + t));
        }
    }

    // ============ Case 9: fractional shares ============

    @Test
    @DisplayName("quantity_scale>0 → FAILED(Fractional share not supported)")
    void fractionalShareFailed() {
        TigerStagedOrder s = baseStk();
        s.setQuantity("15050");
        s.setQuantityScale("2");
        s.setFilledQuantity("15050");
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.FAILED, fr.getKind());
        assertTrue(fr.getMessage().contains("Fractional share not supported"));
        assertTrue(fr.getMessage().contains("realQty=150.50"),
                "Expected realQty=150.50 in message, got: " + fr.getMessage());
    }

    // ============ Case 10-11: attrDesc ============

    @Test
    @DisplayName("attrDesc=\"Exercise\" → FAILED(Option event TBD)")
    void attrDescNonEmptyFailed() {
        TigerStagedOrder s = baseOptCall();
        s.setAttrDesc("Exercise");
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.FAILED, fr.getKind());
        assertTrue(fr.getMessage().contains("attrDesc=Exercise"));
        assertTrue(fr.getMessage().contains("mapping TBD"));
    }

    @Test
    @DisplayName("attrDesc=\"\" (empty string) → treated as empty → PASS")
    void attrDescEmptyPass() {
        TigerStagedOrder s = baseStk();
        s.setAttrDesc("   ");
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertTrue(fr.isPass());
    }

    // ============ Case 12: unsupported action ============

    @Test
    @DisplayName("action=CANCEL → FAILED(Unsupported action)")
    void actionCancelFailed() {
        TigerStagedOrder s = baseStk();
        s.setAction("CANCEL");
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.FAILED, fr.getKind());
        assertTrue(fr.getMessage().contains("Unsupported action: CANCEL"));
    }

    // ============ Case 13: OPT missing putCall ============

    @Test
    @DisplayName("OPT missing putCall → FAILED")
    void optMissingPutCallFailed() {
        TigerStagedOrder s = baseOptCall();
        s.setPutCall(null);
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.FAILED, fr.getKind());
        assertTrue(fr.getMessage().contains("Option missing or invalid putCall"));
    }

    @Test
    @DisplayName("OPT invalid putCall (STRADDLE) → FAILED")
    void optInvalidPutCallFailed() {
        TigerStagedOrder s = baseOptCall();
        s.setPutCall("STRADDLE");
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, false);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.FAILED, fr.getKind());
    }

    // ============ Case 14: fee calculation ============

    @Test
    @DisplayName("fee: commission=-1.50, gst=null → 1.50")
    void feeNegativeCommissionNullGst() {
        TigerStagedOrder s = baseStk();
        s.setCommission("-1.50");
        s.setGst(null);
        TradeRecord tr = mapper.toTradeRecord(s, BROKER_ID, BATCH_ID);
        assertEquals(0, new BigDecimal("1.50").compareTo(tr.getFee()));
    }

    @Test
    @DisplayName("fee: commission=-2.00, gst=-0.16 → 2.16 (both abs-summed)")
    void feeNegativeCommissionNegativeGst() {
        TigerStagedOrder s = baseStk();
        s.setCommission("-2.00");
        s.setGst("-0.16");
        TradeRecord tr = mapper.toTradeRecord(s, BROKER_ID, BATCH_ID);
        assertEquals(0, new BigDecimal("2.16").compareTo(tr.getFee()));
    }

    // ============ Case 15: already imported ============

    @Test
    @DisplayName("already imported (alreadyImported=true) → SKIPPED")
    void alreadyImportedSkipped() {
        TigerStagedOrder s = baseStk();
        TigerTradeRecordMapper.FilterResult fr = mapper.preFilter(s, true);
        assertEquals(TigerTradeRecordMapper.FilterResult.Kind.SKIPPED, fr.getKind());
        assertTrue(fr.getMessage().contains("Already imported"));
        assertTrue(fr.getMessage().contains("TG100001"));
    }

    // ============ Extra: unit-level helpers ============

    @Nested
    @DisplayName("mapCurrency")
    class MapCurrency {
        @Test
        void usd() { assertEquals(Currency.USD, mapper.mapCurrency("USD")); }

        @Test
        void hkd() { assertEquals(Currency.HKD, mapper.mapCurrency("HKD")); }

        @Test
        void cnhNormalized() { assertEquals(Currency.CNY, mapper.mapCurrency("CNH")); }

        @Test
        void cnyAccepted() { assertEquals(Currency.CNY, mapper.mapCurrency("CNY")); }

        @Test
        void lowercaseAccepted() { assertEquals(Currency.USD, mapper.mapCurrency("usd")); }

        @Test
        void unknownThrows() {
            assertThrows(IllegalArgumentException.class, () -> mapper.mapCurrency("EUR"));
        }

        @Test
        void nullThrows() {
            assertThrows(IllegalArgumentException.class, () -> mapper.mapCurrency(null));
        }
    }

    @Nested
    @DisplayName("buildOptionSymbol")
    class BuildOptionSymbol {
        @Test
        void integerStrike() {
            assertEquals("AAPL-20260130-C265",
                    mapper.buildOptionSymbol("AAPL", "20260130", "CALL", "265"));
        }

        @Test
        void decimalStrikeStripsTrailingZeros() {
            assertEquals("F-20260220-P17.5",
                    mapper.buildOptionSymbol("F", "20260220", "PUT", "17.50"));
        }

        @Test
        void trailingZerosOnIntegerLike() {
            // "265.00" → "265"
            assertEquals("AAPL-20260130-C265",
                    mapper.buildOptionSymbol("AAPL", "20260130", "CALL", "265.00"));
        }

        @Test
        void emptyUnderlyingThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> mapper.buildOptionSymbol("", "20260130", "CALL", "265"));
        }

        @Test
        void invalidPutCallThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> mapper.buildOptionSymbol("AAPL", "20260130", "X", "265"));
        }
    }
}
