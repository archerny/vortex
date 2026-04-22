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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IbkrImportWorker}.
 *
 * Tests the per-record transactional import logic that was extracted
 * from IbkrImportService to fix the Spring AOP self-invocation issue.
 *
 * Covers:
 * - importSingleOrder: field mapping, deduplication, error handling
 * - BookTrade detection and trigger determination
 * - Fee calculation
 * - backfillSingleStkRecord: matching and disambiguation
 */
@ExtendWith(MockitoExtension.class)
class IbkrImportWorkerTest {

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
                stagedOrderRepository, stagedTradeConfirmRepository, tradeRecordRepository);
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
    // Fee calculation
    // ========================================================
    @Nested
    @DisplayName("Fee calculation")
    class FeeCalculationTest {

        @Test
        @DisplayName("should calculate fee as abs(commission) + abs(tradeCharge)")
        void shouldCalculateFeeCorrectly() {
            IbkrStagedOrder staged = buildStkOrder("ORD_FEE");
            staged.setCommission("-3.50");
            staged.setTradeCharge("-0.70");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_FEE")).thenReturn(false);

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(700L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            assertEquals(new BigDecimal("4.20"), captor.getValue().getFee());
        }

        @Test
        @DisplayName("should handle null commission and tradeCharge")
        void shouldHandleNullFeeFields() {
            IbkrStagedOrder staged = buildStkOrder("ORD_NO_FEE");
            staged.setCommission(null);
            staged.setTradeCharge(null);
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("ibkr", "ORD_NO_FEE")).thenReturn(false);

            TradeRecord savedRecord = new TradeRecord();
            savedRecord.setId(701L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(savedRecord);

            importWorker.importSingleOrder(1L, 1L, staged);

            ArgumentCaptor<TradeRecord> captor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(captor.capture());
            assertEquals(BigDecimal.ZERO, captor.getValue().getFee());
        }
    }

    // ========================================================
    // backfillSingleStkRecord
    // ========================================================
    @Nested
    @DisplayName("backfillSingleStkRecord()")
    class BackfillTest {

        @Test
        @DisplayName("should set triggerRefId when single OPT match found")
        void shouldBackfillWhenSingleMatch() {
            TradeRecord stkRecord = new TradeRecord();
            stkRecord.setId(10L);
            stkRecord.setTriggerRefType(TriggerRefType.OPTION_EXERCISE);
            stkRecord.setUnderlyingSymbol("AAPL");
            stkRecord.setTradeDate(LocalDate.of(2026, 1, 15));
            stkRecord.setSymbol("AAPL");

            TradeRecord optMatch = new TradeRecord();
            optMatch.setId(20L);

            when(tradeRecordRepository.findOptSideBookTradesForMatching(
                    eq(TradeTrigger.OPTION),
                    eq(TriggerRefType.OPTION_EXERCISE),
                    eq(List.of(AssetType.OPTION_CALL, AssetType.OPTION_PUT)),
                    eq("AAPL"),
                    eq(LocalDate.of(2026, 1, 15))))
                    .thenReturn(List.of(optMatch));

            importWorker.backfillSingleStkRecord(stkRecord);

            assertEquals(20L, stkRecord.getTriggerRefId());
            verify(tradeRecordRepository).save(stkRecord);
        }

        @Test
        @DisplayName("should not save when no OPT match found")
        void shouldNotSaveWhenNoMatch() {
            TradeRecord stkRecord = new TradeRecord();
            stkRecord.setId(10L);
            stkRecord.setTriggerRefType(TriggerRefType.OPTION_EXERCISE);
            stkRecord.setUnderlyingSymbol("AAPL");
            stkRecord.setTradeDate(LocalDate.of(2026, 1, 15));
            stkRecord.setSymbol("AAPL");

            when(tradeRecordRepository.findOptSideBookTradesForMatching(
                    any(), any(), any(), any(), any()))
                    .thenReturn(Collections.emptyList());

            importWorker.backfillSingleStkRecord(stkRecord);

            verify(tradeRecordRepository, never()).save(any());
        }
    }
}
