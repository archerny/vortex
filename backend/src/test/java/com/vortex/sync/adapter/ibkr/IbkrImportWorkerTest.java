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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IbkrImportWorker}.
 *
 * Verifies worker behavior in memory; transaction boundaries are checked under e2e/.
 *
 * Covers:
 * - importSingleOrder: field mapping, deduplication, error handling
 * - BookTrade detection and trigger determination
 * - backfillSingleStkRecord: exact unique linkage and propagated failures
 * Fee calculation and precision cases live in {@link TradeFeePrecisionTest}.
 */
@ExtendWith(MockitoExtension.class)
class IbkrImportWorkerTest {
    @org.mockito.Mock private com.vortex.service.MarketEventHistoryService eventHistory;

    @Mock
    private IbkrStagedOrderRepository stagedOrderRepository;

    @Mock
    private IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository;

    @Mock
    private TradeRecordRepository tradeRecordRepository;

    private IbkrImportWorker importWorker;

    @BeforeEach
    void setUp() {
        importWorker = new IbkrImportWorker(
                stagedOrderRepository, stagedTradeConfirmRepository, tradeRecordRepository, eventHistory);
    }

    // ============ Test data builders ============

    private IbkrStagedOrder buildStkOrder(String orderId) {
        IbkrStagedOrder staged = new IbkrStagedOrder();
        staged.setId(100L);
        staged.setBatchId(1L);
        staged.setStatus("PENDING");
        staged.setOrderId(orderId);
        staged.setAccountId("U12345");
        staged.setCurrency("USD");
        staged.setAssetCategory("STK");
        staged.setSymbol("AAPL");
        staged.setDescription("APPLE INC");
        staged.setTradeDate("20260115");
        staged.setBuySell("BUY");
        staged.setOrderType("LMT");
        staged.setOrderTime("20260115;100000");
        staged.setQuantity("100");
        staged.setPrice("150.50");
        staged.setAmount("15050.00");
        staged.setCommission("-1.50");
        staged.setTradeCharge("-0.30");
        return staged;
    }

    private IbkrStagedOrder buildOptOrder(String orderId) {
        IbkrStagedOrder staged = buildStkOrder(orderId);
        staged.setAssetCategory("OPT");
        staged.setSymbol("AAPL  260130C00265000");
        staged.setDescription("AAPL 30JAN26 265 C");
        staged.setExpiry("20260130");
        staged.setPutCall("C");
        staged.setStrike("265.00");
        return staged;
    }

    private IbkrStagedOrder buildBookTradeOrder(String orderId) {
        IbkrStagedOrder staged = buildStkOrder(orderId);
        staged.setOrderTime(null); // BookTrade: no orderTime
        staged.setOrderType(null); // BookTrade: no orderType
        return staged;
    }

    // ========================================================
    // importSingleOrder — field mapping
    // ========================================================
    @Nested
    @DisplayName("importSingleOrder() - field mapping")
    class ImportSingleOrderMappingTest {

        @ParameterizedTest
        @CsvSource(value = {"Ex|C|100|true", "A|C|100.00|true", "Ex|C|<null>|false", "Ex|C|' '|false",
                "A|C|invalid|false", "Ex|C|50|false", "Ex|P|50|false", "Ep|C|<null>|true"},
                delimiter = '|', nullValues = "<null>")
        void shouldValidateMultiplierOnlyForNewOptionDeliveryEvents(String code, String putCall, String multiplier, boolean valid) {
            IbkrStagedOrder staged = buildOptOrder("SYNTH-NEW-OPTION");
            staged.setOrderTime(null); staged.setOrderType(null); staged.setMultiplier(multiplier); staged.setPutCall(putCall);
            staged.setPrice("0"); staged.setAmount("0"); staged.setCommission("0"); staged.setTradeCharge("0");
            staged.setBuySell(code.equals("Ex") ? "SELL" : "BUY");
            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm(); confirm.setCode(code);
            when(stagedTradeConfirmRepository.findByOrderId(staged.getOrderId())).thenReturn(List.of(confirm));
            if (valid) {
                when(tradeRecordRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
                importWorker.importSingleOrder(1L, 1L, staged);
                assertEquals("IMPORTED", staged.getStatus()); verify(tradeRecordRepository).save(any());
            } else {
                ImportOneFailedException failure = assertThrows(ImportOneFailedException.class,
                        () -> importWorker.importSingleOrder(1L, 1L, staged));
                CategorizedSyncException reason = assertInstanceOf(CategorizedSyncException.class, failure.getCause());
                assertEquals(FailureCategory.UNRECOGNIZED, reason.getCategory());
                assertTrue(reason.getMessage().contains("expected 100"));
                verify(tradeRecordRepository, never()).save(any());
                verify(stagedOrderRepository, never()).save(any());
            }
        }

        @Test
        void shouldSkipMatchingExistingEventWithoutRequiringHistoricMultiplier() {
            IbkrStagedOrder staged = buildOptOrder("SYNTH-REPEAT-OPTION");
            staged.setOrderTime(null); staged.setOrderType(null); staged.setMultiplier("100");
            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm(); confirm.setCode("A");
            when(stagedTradeConfirmRepository.findByOrderId(staged.getOrderId())).thenReturn(List.of(confirm));
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", staged.getOrderId())).thenReturn(false, true);
            var stored = new java.util.concurrent.atomic.AtomicReference<TradeRecord>();
            when(tradeRecordRepository.save(any())).thenAnswer(invocation -> {
                TradeRecord record = invocation.getArgument(0); record.setId(500L); stored.set(record); return record;
            });
            importWorker.importSingleOrder(1L, 1L, staged);
            when(tradeRecordRepository.findByExternalBrokerAndExternalId("ibkr", staged.getOrderId()))
                    .thenAnswer(invocation -> Optional.of(stored.get()));
            staged.setMultiplier(null);
            importWorker.importSingleOrder(2L, 1L, staged);
            assertEquals("SKIPPED", staged.getStatus());
            assertEquals(1L, stored.get().getSyncBatchId());
            verify(tradeRecordRepository).save(any());
        }

        @Test
        @DisplayName("should map STK order correctly")
        void shouldMapStkOrderCorrectly() {
            IbkrStagedOrder staged = buildStkOrder("ORD001");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD001")).thenReturn(false);

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(500L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            TradeRecord record = captor.getValue();

            assertEquals(LocalDate.of(2026, 1, 15), record.getTradeDate());
            assertEquals(1L, record.getBrokerId());
            assertEquals(Currency.USD, record.getCurrency());
            assertEquals(TradeType.BUY, record.getTradeType());
            assertEquals(100, record.getQuantity());
            assertEquals(new BigDecimal("150.50"), record.getPrice());
            assertEquals(new BigDecimal("15050.00"), record.getAmount());
            // fee = abs(-1.50) + abs(-0.30) = 1.80
            assertEquals(new BigDecimal("1.80"), record.getFee());
            assertEquals(AssetType.STOCK, record.getAssetType());
            assertEquals("AAPL", record.getSymbol());
            assertEquals("AAPL", record.getUnderlyingSymbol());
            assertEquals("ORD001", record.getExternalId());
            assertEquals("ibkr", record.getExternalBroker());
            assertEquals(1L, record.getSyncBatchId());
            assertFalse(record.getIsDeleted());
            // Normal trade (not BookTrade)
            assertEquals(TradeTrigger.MANUAL, record.getTradeTrigger());
            assertEquals(TriggerRefType.NONE, record.getTriggerRefType());

            // Staged order updated
            assertEquals("IMPORTED", staged.getStatus());
            assertEquals(500L, staged.getImportedTradeId());
        }

        @Test
        @DisplayName("should map OPT order with correct symbol format")
        void shouldMapOptOrderWithCorrectSymbol() {
            IbkrStagedOrder staged = buildOptOrder("ORD002");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD002")).thenReturn(false);

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(501L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            TradeRecord record = captor.getValue();

            assertEquals(AssetType.OPTION_CALL, record.getAssetType());
            assertEquals("AAPL-20260130-C265", record.getSymbol());
            assertEquals("AAPL", record.getUnderlyingSymbol());
        }

        @Test
        @DisplayName("should map SELL trade type")
        void shouldMapSellTradeType() {
            IbkrStagedOrder staged = buildStkOrder("ORD003");
            staged.setBuySell("SELL");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD003")).thenReturn(false);

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(502L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            assertEquals(TradeType.SELL, captor.getValue().getTradeType());
        }
    }

    // ========================================================
    // Deduplication
    // ========================================================
    @Nested
    @DisplayName("importSingleOrder() - deduplication")
    class DeduplicationTest {

        @Test
        @DisplayName("should skip duplicate records")
        void shouldSkipDuplicateRecords() {
            IbkrStagedOrder staged = buildStkOrder("ORD_DUP");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_DUP")).thenReturn(true);
            TradeRecord existing = importWorker.mapToTradeRecord(99L, 1L, staged);
            existing.setId(99L);
            when(tradeRecordRepository.findByExternalBrokerAndExternalId("ibkr", "ORD_DUP"))
                    .thenReturn(Optional.of(existing));

            importWorker.importSingleOrder(1L, 1L, staged);

            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
            assertEquals("SKIPPED", staged.getStatus());
            assertTrue(staged.getErrorMessage().contains("Duplicate"));
        }
    }

    // ========================================================
    // Error handling
    // ========================================================
    @Nested
    @DisplayName("importSingleOrder() - error handling")
    class ErrorHandlingTest {

        @Test
        @DisplayName("should throw ImportOneFailedException when mapping throws")
        void shouldThrowWhenMappingThrows() {
            IbkrStagedOrder staged = buildStkOrder("ORD_ERR");
            staged.setTradeDate(null); // will cause parse error
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_ERR")).thenReturn(false);

            // Per P0-1 fix: worker no longer saves FAILED itself — that would
            // run inside the rolled-back REQUIRES_NEW tx and be lost. It now
            // wraps + rethrows so IbkrImportService can invoke markFailed() in
            // a fresh tx via the Spring AOP proxy.
            ImportOneFailedException ex = assertThrows(ImportOneFailedException.class,
                    () -> importWorker.importSingleOrder(1L, 1L, staged));
            assertSame(staged, ex.getStaged());
            assertNotNull(ex.getCause());

            // Status is NOT mutated to FAILED by the worker — that's markFailed's job.
            assertEquals("PENDING", staged.getStatus());
            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
            // Worker no longer persists staged row in the error path.
            verify(stagedOrderRepository, never()).save(any(IbkrStagedOrder.class));
        }

        @Test
        @DisplayName("should throw ImportOneFailedException when save() throws")
        void shouldThrowWhenSaveThrows() {
            IbkrStagedOrder staged = buildStkOrder("ORD_SAVE_ERR");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_SAVE_ERR")).thenReturn(false);
            when(tradeRecordRepository.save(any(TradeRecord.class)))
                    .thenThrow(new RuntimeException("DB down"));

            ImportOneFailedException ex = assertThrows(ImportOneFailedException.class,
                    () -> importWorker.importSingleOrder(1L, 1L, staged));
            assertSame(staged, ex.getStaged());
            assertEquals("DB down", ex.getCause().getMessage());
        }
    }

    // ========================================================
    // markFailed — P0-1 fix
    // ========================================================
    @Nested
    @DisplayName("markFailed()")
    class MarkFailedTest {

        @Test
        @DisplayName("should re-read row, set FAILED + errorMessage, and save")
        void shouldPersistFailedStatus() {
            IbkrStagedOrder stale = buildStkOrder("ORD_MF");
            stale.setStatus("PENDING");

            IbkrStagedOrder fresh = buildStkOrder("ORD_MF");
            fresh.setStatus("PENDING");

            when(stagedOrderRepository.findById(100L)).thenReturn(java.util.Optional.of(fresh));

            importWorker.markFailed(stale, "Import error: boom");

            // Re-read happens via findById before mutation
            verify(stagedOrderRepository).findById(100L);
            // The fresh instance is the one mutated + saved (not the stale arg)
            assertEquals("FAILED", fresh.getStatus());
            assertEquals("Import error: boom", fresh.getErrorMessage());
            verify(stagedOrderRepository).save(fresh);
            // Importantly, the stale arg passed in was not saved
            verify(stagedOrderRepository, never()).save(stale);
        }

        @Test
        @DisplayName("should throw IllegalStateException when row disappeared")
        void shouldThrowWhenRowMissing() {
            IbkrStagedOrder stale = buildStkOrder("ORD_GONE");
            when(stagedOrderRepository.findById(100L)).thenReturn(java.util.Optional.empty());

            IllegalStateException ex = assertThrows(IllegalStateException.class,
                    () -> importWorker.markFailed(stale, "Import error: x"));
            assertTrue(ex.getMessage().contains("Staged row disappeared"));
            assertTrue(ex.getMessage().contains("id=100"));
        }
    }

    // ========================================================
    // BookTrade detection
    // ========================================================
    @Nested
    @DisplayName("BookTrade detection")
    class BookTradeDetectionTest {

        @Test
        @DisplayName("should detect BookTrade when orderTime and orderType are null")
        void shouldDetectBookTrade() {
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_BOOK");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_BOOK")).thenReturn(false);

            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm();
            confirm.setCode("Ex");
            when(stagedTradeConfirmRepository.findByOrderId("ORD_BOOK")).thenReturn(List.of(confirm));

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(600L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            TradeRecord record = captor.getValue();

            assertEquals(TradeTrigger.OPTION, record.getTradeTrigger());
            assertEquals(TriggerRefType.OPTION_EXERCISE, record.getTriggerRefType());
        }

        @Test
        @DisplayName("should detect Ep code as OPTION_EXPIRE")
        void shouldDetectExpireCode() {
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_EXPIRE");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_EXPIRE")).thenReturn(false);

            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm();
            confirm.setCode("Ep;P");
            when(stagedTradeConfirmRepository.findByOrderId("ORD_EXPIRE")).thenReturn(List.of(confirm));

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(601L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            assertEquals(TriggerRefType.OPTION_EXPIRE, captor.getValue().getTriggerRefType());
        }

        @Test
        @DisplayName("should detect A code as OPTION_ASSIGNED")
        void shouldDetectAssignedCode() {
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_ASSIGN");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_ASSIGN")).thenReturn(false);

            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm();
            confirm.setCode("A");
            when(stagedTradeConfirmRepository.findByOrderId("ORD_ASSIGN")).thenReturn(List.of(confirm));

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(602L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            assertEquals(TriggerRefType.OPTION_ASSIGNED, captor.getValue().getTriggerRefType());
        }

        @Test
        @DisplayName("should throw ImportOneFailedException when BookTrade has no TradeConfirm")
        void shouldThrowWhenNoConfirm() {
            // P0-3 fix: BookTrade with no confirm used to silently default to
            // trigger_ref_type=NONE, producing garbage trade_records rows. It
            // now throws and routes through the FAILED → cleanup path.
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_NO_CONF");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_NO_CONF")).thenReturn(false);
            when(stagedTradeConfirmRepository.findByOrderId("ORD_NO_CONF")).thenReturn(Collections.emptyList());

            ImportOneFailedException ex = assertThrows(ImportOneFailedException.class,
                    () -> importWorker.importSingleOrder(1L, 1L, staged));
            assertSame(staged, ex.getStaged());
            assertInstanceOf(IllegalStateException.class, ex.getCause());
            assertTrue(ex.getCause().getMessage().contains("no associated TradeConfirm"),
                    "expected 'no associated TradeConfirm' in: " + ex.getCause().getMessage());

            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
            verify(stagedOrderRepository, never()).save(any(IbkrStagedOrder.class));
        }

        @Test
        @DisplayName("should throw ImportOneFailedException when BookTrade code is blank")
        void shouldThrowWhenBlankCode() {
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_BLANK");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_BLANK")).thenReturn(false);

            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm();
            confirm.setCode("   ");
            when(stagedTradeConfirmRepository.findByOrderId("ORD_BLANK")).thenReturn(List.of(confirm));

            ImportOneFailedException ex = assertThrows(ImportOneFailedException.class,
                    () -> importWorker.importSingleOrder(1L, 1L, staged));
            assertInstanceOf(IllegalStateException.class, ex.getCause());
            assertTrue(ex.getCause().getMessage().contains("blank code"),
                    "expected 'blank code' in: " + ex.getCause().getMessage());
        }

        @Test
        @DisplayName("should throw ImportOneFailedException when BookTrade code is unknown")
        void shouldThrowWhenUnknownCode() {
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_UNK");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_UNK")).thenReturn(false);

            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm();
            confirm.setCode("XYZ;Q");
            when(stagedTradeConfirmRepository.findByOrderId("ORD_UNK")).thenReturn(List.of(confirm));

            ImportOneFailedException ex = assertThrows(ImportOneFailedException.class,
                    () -> importWorker.importSingleOrder(1L, 1L, staged));
            assertInstanceOf(IllegalStateException.class, ex.getCause());
            assertTrue(ex.getCause().getMessage().contains("did not match any known option event"),
                    "expected 'did not match' in: " + ex.getCause().getMessage());
        }

        @Test
        @DisplayName("should detect GEA as OPTION_ASSIGNED")
        void shouldDetectGeaAssignedCode() {
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_GEA");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_GEA")).thenReturn(false);

            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm();
            confirm.setCode("GEA");
            when(stagedTradeConfirmRepository.findByOrderId("ORD_GEA")).thenReturn(List.of(confirm));

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(605L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            assertEquals(TriggerRefType.OPTION_ASSIGNED, captor.getValue().getTriggerRefType());
        }

        @Test
        @DisplayName("Exercise codes should take priority over Expire in compound code")
        void exerciseShouldTakePriorityOverExpire() {
            IbkrStagedOrder staged = buildBookTradeOrder("ORD_COMPOUND");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_COMPOUND")).thenReturn(false);

            IbkrStagedTradeConfirm confirm = new IbkrStagedTradeConfirm();
            confirm.setCode("Ep;Ex");
            when(stagedTradeConfirmRepository.findByOrderId("ORD_COMPOUND")).thenReturn(List.of(confirm));

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(604L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            assertEquals(TriggerRefType.OPTION_EXERCISE, captor.getValue().getTriggerRefType());
        }
    }

    // ========================================================
    // backfillSingleStkRecord
    // ========================================================
    @Nested
    @DisplayName("backfillSingleStkRecord()")
    class BackfillTest {

        private TradeRecord stock() {
            TradeRecord record = new TradeRecord();
            record.setId(10L); record.setBrokerId(1L); record.setSyncBatchId(2L);
            record.setSymbol("SYNTH"); record.setUnderlyingSymbol("SYNTH");
            record.setTradeDate(LocalDate.of(2026, 1, 15)); record.setCurrency(Currency.USD);
            record.setAssetType(AssetType.STOCK); record.setTradeType(TradeType.BUY);
            record.setTradeTrigger(TradeTrigger.OPTION); record.setTriggerRefType(TriggerRefType.OPTION_EXERCISE);
            record.setTriggerRefId(0L); record.setIsDeleted(false);
            record.setQuantity(200); record.setPrice(new BigDecimal("100.0000"));
            return record;
        }

        private TradeRecord option() {
            TradeRecord record = stock();
            record.setId(20L); record.setSyncBatchId(1L);
            record.setExternalBroker("ibkr"); record.setExternalId("SYNTH-OPTION");
            record.setSymbol("SYNTH-20260220-C100"); record.setAssetType(AssetType.OPTION_CALL);
            record.setTradeType(TradeType.SELL); record.setQuantity(2); record.setPrice(BigDecimal.ZERO);
            return record;
        }

        private void candidates(TradeRecord stock, List<TradeRecord> options) {
            when(tradeRecordRepository.findById(10L)).thenReturn(Optional.of(stock));
            when(tradeRecordRepository.findOptSideBookTradesForMatching(any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(options);
        }

        private void assertRejected(TradeRecord stock, int matches) {
            CategorizedSyncException error = assertThrows(CategorizedSyncException.class,
                    () -> importWorker.backfillSingleStkRecord(stock));
            assertEquals(FailureCategory.UNRECOGNIZED, error.getCategory());
            assertTrue(error.getMessage().contains("exact_matches=" + matches));
            assertTrue(error.getMessage().contains("manual investigation required"));
            assertEquals(0L, stock.getTriggerRefId());
            verify(tradeRecordRepository, never()).save(any());
        }

        @ParameterizedTest
        @CsvSource({"OPTION_EXERCISE,OPTION_CALL,BUY,SELL", "OPTION_EXERCISE,OPTION_PUT,SELL,SELL",
                "OPTION_ASSIGNED,OPTION_CALL,SELL,BUY", "OPTION_ASSIGNED,OPTION_PUT,BUY,BUY"})
        void shouldBackfillUniqueExactMatchAcrossBatches(TriggerRefType event, AssetType asset,
                                                        TradeType stockDirection, TradeType optionDirection) {
            TradeRecord stock = stock(), option = option();
            stock.setTriggerRefType(event); stock.setTradeType(stockDirection);
            option.setTriggerRefType(event); option.setAssetType(asset); option.setTradeType(optionDirection);
            option.setSymbol("SYNTH-20260220-" + (asset == AssetType.OPTION_CALL ? "C" : "P") + "100.00");
            candidates(stock, List.of(option));
            importWorker.backfillSingleStkRecord(stock);
            assertEquals(20L, stock.getTriggerRefId());
            assertEquals(0L, option.getTriggerRefId());
            assertEquals(1L, option.getSyncBatchId());
            verifyNoInteractions(stagedOrderRepository);
            verify(tradeRecordRepository).save(stock);
        }

        @ParameterizedTest
        @ValueSource(strings = {"broker", "currency", "date", "underlying", "trigger", "event", "deleted",
                "asset", "direction", "stockDirection", "quantity", "strike", "symbol", "expiry", "symbolUnderlying", "symbolType",
                "overflow", "zeroQuantity", "nullQuantity"})
        void shouldRejectEvenSingleCandidateUnlessEveryConditionMatches(String mismatch) {
            TradeRecord stock = stock(), option = option();
            switch (mismatch) {
                case "broker" -> option.setBrokerId(2L);
                case "currency" -> option.setCurrency(Currency.HKD);
                case "date" -> option.setTradeDate(stock.getTradeDate().minusDays(1));
                case "underlying" -> option.setUnderlyingSymbol("SYNTH-OTHER");
                case "trigger" -> option.setTradeTrigger(TradeTrigger.MANUAL);
                case "event" -> option.setTriggerRefType(TriggerRefType.OPTION_ASSIGNED);
                case "deleted" -> option.setIsDeleted(true);
                case "asset" -> option.setAssetType(AssetType.STOCK);
                case "direction" -> option.setTradeType(TradeType.BUY);
                case "stockDirection" -> stock.setTradeType(TradeType.SELL);
                case "quantity" -> option.setQuantity(1);
                case "strike" -> option.setSymbol("SYNTH-20260220-C100.00000001");
                case "symbol" -> option.setSymbol("unparseable");
                case "expiry" -> option.setSymbol("SYNTH-20260230-C100");
                case "symbolUnderlying" -> option.setSymbol("OTHER-20260220-C100");
                case "symbolType" -> option.setSymbol("SYNTH-20260220-P100");
                case "overflow" -> { option.setQuantity(42_949_674); stock.setQuantity(104); } // int product wraps to 104
                case "zeroQuantity" -> option.setQuantity(0);
                case "nullQuantity" -> option.setQuantity(null);
                default -> throw new AssertionError(mismatch);
            }
            candidates(stock, List.of(option));
            assertRejected(stock, 0);
        }

        @Test
        void shouldFailWhenNoMatchExists() {
            TradeRecord stock = stock();
            candidates(stock, List.of());
            assertRejected(stock, 0);
        }

        @ParameterizedTest
        @ValueSource(booleans = {false, true})
        void shouldRequireStrikeAndQuantityTogetherRegardlessOfCandidateOrder(boolean reverse) {
            TradeRecord stock = stock(), wrongQuantity = option(), right = option();
            wrongQuantity.setId(21L); wrongQuantity.setQuantity(1);
            candidates(stock, reverse ? List.of(right, wrongQuantity) : List.of(wrongQuantity, right));
            importWorker.backfillSingleStkRecord(stock);
            assertEquals(right.getId(), stock.getTriggerRefId());
            verify(tradeRecordRepository).save(stock);
        }

        @ParameterizedTest
        @ValueSource(booleans = {false, true})
        void shouldRejectDifferentExpiryAmbiguityRegardlessOfCandidateOrder(boolean reverse) {
            TradeRecord stock = stock(), first = option(), second = option();
            second.setId(21L); second.setSymbol("SYNTH-20260320-C100");
            candidates(stock, reverse ? List.of(second, first) : List.of(first, second));
            assertRejected(stock, 2);
        }

        @Test
        void shouldPropagateSaveFailureInsteadOfCompletingImport() {
            TradeRecord stock = stock();
            candidates(stock, List.of(option()));
            RuntimeException failure = new IllegalStateException("synthetic save failure");
            when(tradeRecordRepository.save(stock)).thenThrow(failure);
            assertSame(failure, assertThrows(RuntimeException.class,
                    () -> importWorker.backfillSingleStkRecord(stock)));
        }

        @Test
        void shouldNotResolveAmbiguityByDiscardingAnAlreadyUsedCandidate() {
            TradeRecord stock = stock(), first = option(), second = option(); second.setId(21L);
            candidates(stock, List.of(first, second));
            lenient().when(tradeRecordRepository.existsByTradeTriggerAndTriggerRefIdAndAssetTypeInAndIsDeletedFalse(
                    TradeTrigger.OPTION, first.getId(), List.of(AssetType.STOCK, AssetType.ETF))).thenReturn(true);
            assertRejected(stock, 2);
            verify(tradeRecordRepository, never()).existsByTradeTriggerAndTriggerRefIdAndAssetTypeInAndIsDeletedFalse(any(), any(), any());
        }

        @Test
        void shouldRejectAnAlreadyUsedOptionWithoutChangingEitherRecord() {
            TradeRecord stock = stock(), option = option(), otherStock = stock();
            otherStock.setId(11L); otherStock.setTriggerRefId(option.getId());
            candidates(stock, List.of(option));
            when(tradeRecordRepository.existsByTradeTriggerAndTriggerRefIdAndAssetTypeInAndIsDeletedFalse(
                    TradeTrigger.OPTION, 20L, List.of(AssetType.STOCK, AssetType.ETF))).thenReturn(true);
            CategorizedSyncException error = assertThrows(CategorizedSyncException.class,
                    () -> importWorker.backfillSingleStkRecord(stock));
            assertTrue(error.getMessage().contains("already linked"));
            assertEquals(0L, stock.getTriggerRefId()); assertEquals(0L, option.getTriggerRefId());
            verify(tradeRecordRepository, never()).save(any());
        }

        @ParameterizedTest
        @ValueSource(strings = {"asset", "trigger", "symbol", "ref", "deleted"})
        void shouldRecheckCurrentStockEligibilityAfterReload(String fault) {
            TradeRecord stale = stock(), current = stock();
            switch (fault) {
                case "asset" -> current.setAssetType(AssetType.OPTION_CALL);
                case "trigger" -> current.setTradeTrigger(TradeTrigger.MANUAL);
                case "symbol" -> current.setSymbol("OTHER");
                case "ref" -> current.setTriggerRefId(null);
                case "deleted" -> current.setIsDeleted(true);
                default -> throw new AssertionError(fault);
            }
            when(tradeRecordRepository.findById(10L)).thenReturn(Optional.of(current));
            CategorizedSyncException error = assertThrows(CategorizedSyncException.class,
                    () -> importWorker.backfillSingleStkRecord(stale));
            assertTrue(error.getMessage().contains("not eligible"));
            verifyNoMoreInteractions(tradeRecordRepository);
        }

        @Test
        void shouldNotRewriteAnAlreadyLinkedRecord() {
            TradeRecord stock = stock(); stock.setTriggerRefId(99L);
            when(tradeRecordRepository.findById(10L)).thenReturn(Optional.of(stock));
            importWorker.backfillSingleStkRecord(stock);
            assertEquals(99L, stock.getTriggerRefId());
            verifyNoMoreInteractions(tradeRecordRepository);
        }
    }
}
