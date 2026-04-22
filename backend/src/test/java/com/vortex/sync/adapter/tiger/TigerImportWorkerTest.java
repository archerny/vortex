package com.vortex.sync.adapter.tiger;

import com.vortex.entity.TigerStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.repository.TradeRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TigerImportWorker}.
 *
 * <p>Covers the pre-filter routing (PASS/SKIPPED/FAILED), the happy path
 * (map → save → mark IMPORTED), and the unexpected-exception path. Field-
 * level mapping correctness lives in {@link TigerTradeRecordMapperTest}; here
 * we only verify that the Worker wires the pieces together and writes the
 * right status + error message to the staging table.
 */
@ExtendWith(MockitoExtension.class)
class TigerImportWorkerTest {

    @Mock
    private TigerStagedOrderRepository stagedOrderRepository;

    @Mock
    private TradeRecordRepository tradeRecordRepository;

    private TigerImportWorker worker;

    @BeforeEach
    void setUp() {
        worker = new TigerImportWorker(stagedOrderRepository, tradeRecordRepository);
    }

    // ============ Helpers ============

    private TigerStagedOrder validStockOrder() {
        TigerStagedOrder staged = new TigerStagedOrder();
        staged.setId(100L);
        staged.setBatchId(1L);
        staged.setStatus("PENDING");
        staged.setTigerId("T-123456");
        staged.setAccount("U1234567");
        staged.setAction("BUY");
        staged.setTradeTime("1704067200000"); // 2024-01-01 08:00 Asia/Shanghai
        staged.setFilledQuantity("100");
        staged.setQuantityScale("0");
        staged.setAvgFillPrice("150.25");
        staged.setCommission("-1.50");
        staged.setGst("0");
        staged.setSymbol("AAPL");
        staged.setSecType("STK");
        staged.setCurrency("USD");
        staged.setMultiplier("1");
        return staged;
    }

    // ========================================================
    // PASS path — successful import
    // ========================================================
    @Nested
    @DisplayName("importOne() — PASS path")
    class PassPath {

        @Test
        @DisplayName("should save TradeRecord and mark staged order IMPORTED")
        void shouldImportSuccessfully() {
            TigerStagedOrder staged = validStockOrder();
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("tiger", "T-123456"))
                    .thenReturn(false);

            TradeRecord persisted = new TradeRecord();
            persisted.setId(9001L);
            when(tradeRecordRepository.save(any(TradeRecord.class))).thenReturn(persisted);

            worker.importOne(1L, 42L, staged);

            // A TradeRecord should have been persisted
            ArgumentCaptor<TradeRecord> trCaptor = ArgumentCaptor.forClass(TradeRecord.class);
            verify(tradeRecordRepository).save(trCaptor.capture());
            TradeRecord built = trCaptor.getValue();
            assertEquals(42L, built.getBrokerId());
            assertEquals("AAPL", built.getSymbol());
            assertEquals("tiger", built.getExternalBroker());
            assertEquals("T-123456", built.getExternalId());

            // Staged row should be flipped to IMPORTED with the new trade id
            assertEquals("IMPORTED", staged.getStatus());
            assertEquals(9001L, staged.getImportedTradeId());
            assertNull(staged.getErrorMessage());
            verify(stagedOrderRepository).save(staged);
        }
    }

    // ========================================================
    // SKIPPED path — pre-filter tells us to skip
    // ========================================================
    @Nested
    @DisplayName("importOne() — SKIPPED path")
    class SkippedPath {

        @Test
        @DisplayName("should skip when already imported in trade_records")
        void shouldSkipAlreadyImported() {
            TigerStagedOrder staged = validStockOrder();
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("tiger", "T-123456"))
                    .thenReturn(true);

            worker.importOne(1L, 42L, staged);

            assertEquals("SKIPPED", staged.getStatus());
            assertNotNull(staged.getErrorMessage());
            verify(stagedOrderRepository).save(staged);
            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
        }

        @Test
        @DisplayName("should skip when filled_quantity is zero")
        void shouldSkipZeroFilledQuantity() {
            TigerStagedOrder staged = validStockOrder();
            staged.setFilledQuantity("0");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("tiger", "T-123456"))
                    .thenReturn(false);

            worker.importOne(1L, 42L, staged);

            assertEquals("SKIPPED", staged.getStatus());
            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
        }
    }

    // ========================================================
    // FAILED path — pre-filter rejects
    // ========================================================
    @Nested
    @DisplayName("importOne() — FAILED (pre-filter) path")
    class FailedByFilterPath {

        @Test
        @DisplayName("should mark FAILED for unsupported secType")
        void shouldFailUnsupportedSecType() {
            TigerStagedOrder staged = validStockOrder();
            staged.setSecType("FUT");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("tiger", "T-123456"))
                    .thenReturn(false);

            worker.importOne(1L, 42L, staged);

            assertEquals("FAILED", staged.getStatus());
            assertNotNull(staged.getErrorMessage());
            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
            verify(stagedOrderRepository).save(staged);
        }

        @Test
        @DisplayName("should mark FAILED when attrDesc is non-empty (option event)")
        void shouldFailOptionEventAttrDesc() {
            TigerStagedOrder staged = validStockOrder();
            staged.setAttrDesc("Exercise");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("tiger", "T-123456"))
                    .thenReturn(false);

            worker.importOne(1L, 42L, staged);

            assertEquals("FAILED", staged.getStatus());
            assertNotNull(staged.getErrorMessage());
            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
        }
    }

    // ========================================================
    // Exception path — unexpected error during mapping or save
    // ========================================================
    @Nested
    @DisplayName("importOne() — unexpected exception path")
    class ExceptionPath {

        @Test
        @DisplayName("should throw ImportOneFailedException when save() throws")
        void shouldThrowWhenSaveThrows() {
            TigerStagedOrder staged = validStockOrder();
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("tiger", "T-123456"))
                    .thenReturn(false);
            when(tradeRecordRepository.save(any(TradeRecord.class)))
                    .thenThrow(new RuntimeException("DB down"));

            // Per P0-1 fix: worker no longer saves FAILED itself — that would
            // run inside the rolled-back REQUIRES_NEW tx and be lost. It now
            // wraps + rethrows so TigerImportService can invoke markFailed()
            // in a fresh tx via the Spring AOP proxy.
            ImportOneFailedException ex = assertThrows(ImportOneFailedException.class,
                    () -> worker.importOne(1L, 42L, staged));
            assertSame(staged, ex.getStaged());
            assertEquals("DB down", ex.getCause().getMessage());

            // Status is NOT mutated to FAILED by the worker.
            assertEquals("PENDING", staged.getStatus());
            // Worker no longer persists staged row in the error path.
            verify(stagedOrderRepository, never()).save(any(TigerStagedOrder.class));
        }

        @Test
        @DisplayName("should throw ImportOneFailedException when mapping throws (e.g. malformed numeric field)")
        void shouldThrowWhenMappingThrows() {
            TigerStagedOrder staged = validStockOrder();
            staged.setAvgFillPrice("not-a-number");
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId("tiger", "T-123456"))
                    .thenReturn(false);

            ImportOneFailedException ex = assertThrows(ImportOneFailedException.class,
                    () -> worker.importOne(1L, 42L, staged));
            assertSame(staged, ex.getStaged());
            assertNotNull(ex.getCause());

            assertEquals("PENDING", staged.getStatus());
            verify(tradeRecordRepository, never()).save(any(TradeRecord.class));
            verify(stagedOrderRepository, never()).save(any(TigerStagedOrder.class));
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
            TigerStagedOrder stale = validStockOrder();
            stale.setStatus("PENDING");

            TigerStagedOrder fresh = validStockOrder();
            fresh.setStatus("PENDING");

            when(stagedOrderRepository.findById(100L)).thenReturn(Optional.of(fresh));

            worker.markFailed(stale, "Import error: boom");

            verify(stagedOrderRepository).findById(100L);
            // Mutation + save target the fresh instance, not the stale arg.
            assertEquals("FAILED", fresh.getStatus());
            assertEquals("Import error: boom", fresh.getErrorMessage());
            verify(stagedOrderRepository).save(fresh);
            verify(stagedOrderRepository, never()).save(stale);
        }

        @Test
        @DisplayName("should throw IllegalStateException when row disappeared")
        void shouldThrowWhenRowMissing() {
            TigerStagedOrder stale = validStockOrder();
            when(stagedOrderRepository.findById(100L)).thenReturn(Optional.empty());

            IllegalStateException ex = assertThrows(IllegalStateException.class,
                    () -> worker.markFailed(stale, "Import error: x"));
            assertTrue(ex.getMessage().contains("Staged row disappeared"));
            assertTrue(ex.getMessage().contains("id=100"));
        }
    }

    // ========================================================
    // Invariant — SKIPPED/FAILED pre-filter branches still persist staged row
    // (importOne's error path no longer does — see ExceptionPath).
    // ========================================================
    @Nested
    @DisplayName("importOne() — pre-filter terminal branches persist staged row")
    class PreFilterTerminalPersistsStaged {

        @Test
        @DisplayName("SKIPPED branch should save staged row once")
        void skippedBranchSavesStaged() {
            TigerStagedOrder staged = validStockOrder();
            when(tradeRecordRepository.existsByExternalBrokerAndExternalId(eq("tiger"), anyString()))
                    .thenReturn(true); // routes to SKIPPED

            worker.importOne(1L, 42L, staged);

            verify(stagedOrderRepository).save(staged);
        }
    }
}
