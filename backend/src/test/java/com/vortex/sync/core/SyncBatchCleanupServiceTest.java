package com.vortex.sync.core;

import com.vortex.repository.TradeRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SyncBatchCleanupService}.
 *
 * <p>Verifies the v2.4.2 strategy-based cleanup architecture:</p>
 * <ul>
 *   <li>Per-broker dispatch routes to the correct {@link BrokerCleanupStrategy}</li>
 *   <li>Shared {@code trade_records} cleanup runs regardless of broker</li>
 *   <li>Unknown broker code at runtime throws (defensive fallback)</li>
 *   <li>Null/blank arguments are rejected up-front</li>
 *   <li>Exception from any step propagates (so {@code @Transactional} rolls back)</li>
 *   <li>{@code @PostConstruct} coverage check fails when an adapter has no matching
 *       strategy (makes "forgot to register cleanup for a new broker" a boot-time error
 *       instead of a latent CLEANUP_FAILED disaster)</li>
 *   <li>Duplicate strategy beans for the same broker code are rejected at construction</li>
 * </ul>
 */
class SyncBatchCleanupServiceTest {

    // ------------------------------------------------------------------------
    // Helper fakes
    // ------------------------------------------------------------------------

    /** Captures invocations so we can assert dispatch without Mockito per-bean. */
    private static final class RecordingStrategy implements BrokerCleanupStrategy {
        final String code;
        final List<Long> deletedBatches = new ArrayList<>();
        RuntimeException toThrow;

        RecordingStrategy(String code) {
            this.code = code;
        }

        @Override
        public String brokerCode() {
            return code;
        }

        @Override
        public void deleteStagedRows(Long batchId) {
            deletedBatches.add(batchId);
            if (toThrow != null) {
                throw toThrow;
            }
        }
    }

    private static final class FakeAdapter implements BrokerSyncAdapter {
        final String code;

        FakeAdapter(String code) {
            this.code = code;
        }

        @Override
        public String getBrokerCode() {
            return code;
        }

        @Override
        public com.vortex.sync.core.SyncResult sync(com.vortex.sync.core.SyncRequest request) {
            throw new UnsupportedOperationException("not used in these tests");
        }
    }

    // ------------------------------------------------------------------------
    // Construction / @PostConstruct coverage check
    // ------------------------------------------------------------------------

    @Nested
    @DisplayName("construction & verifyAdapterCoverage()")
    class ConstructionTest {

        @Test
        @DisplayName("coverage OK: all adapters have matching strategies")
        void coverageOk() {
            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(new RecordingStrategy("ibkr"), new RecordingStrategy("tiger")),
                    List.of(new FakeAdapter("ibkr"), new FakeAdapter("tiger")),
                    mock(TradeRecordRepository.class));

            // No exception from @PostConstruct helper
            assertDoesNotThrow(svc::verifyAdapterCoverage);
        }

        @Test
        @DisplayName("missing strategy: @PostConstruct fails, listing uncovered broker(s)")
        void missingStrategyFailsStartup() {
            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(new RecordingStrategy("ibkr")),                         // no tiger!
                    List.of(new FakeAdapter("ibkr"), new FakeAdapter("tiger")),
                    mock(TradeRecordRepository.class));

            IllegalStateException ex = assertThrows(IllegalStateException.class,
                    svc::verifyAdapterCoverage);
            assertTrue(ex.getMessage().contains("tiger"),
                    "error message should mention the uncovered broker: " + ex.getMessage());
        }

        @Test
        @DisplayName("extra strategy without an adapter: allowed (strategies may predate adapter registration)")
        void extraStrategyAllowed() {
            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(new RecordingStrategy("ibkr"), new RecordingStrategy("tiger")),
                    List.of(new FakeAdapter("ibkr")),                               // only ibkr adapter
                    mock(TradeRecordRepository.class));

            assertDoesNotThrow(svc::verifyAdapterCoverage);
        }

        @Test
        @DisplayName("duplicate strategy beans for same broker: construction fails")
        void duplicateStrategyFails() {
            IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                    new SyncBatchCleanupService(
                            List.of(new RecordingStrategy("ibkr"), new RecordingStrategy("ibkr")),
                            List.of(new FakeAdapter("ibkr")),
                            mock(TradeRecordRepository.class)));
            assertTrue(ex.getMessage().contains("ibkr"));
        }
    }

    // ------------------------------------------------------------------------
    // cleanupBatchData() — dispatch
    // ------------------------------------------------------------------------

    @Nested
    @DisplayName("cleanupBatchData() — broker dispatch")
    class DispatchTest {

        @Test
        @DisplayName("ibkr: routes to IBKR strategy and deletes trade_records")
        void ibkrDispatch() {
            RecordingStrategy ibkr = new RecordingStrategy("ibkr");
            RecordingStrategy tiger = new RecordingStrategy("tiger");
            TradeRecordRepository tradeRepo = mock(TradeRecordRepository.class);
            when(tradeRepo.deleteBySyncBatchId(42L)).thenReturn(3);

            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(ibkr, tiger),
                    List.of(new FakeAdapter("ibkr"), new FakeAdapter("tiger")),
                    tradeRepo);

            svc.cleanupBatchData(42L, "ibkr");

            assertEquals(List.of(42L), ibkr.deletedBatches);
            assertEquals(Collections.emptyList(), tiger.deletedBatches);
            verify(tradeRepo).deleteBySyncBatchId(42L);
        }

        @Test
        @DisplayName("tiger: routes to Tiger strategy and deletes trade_records")
        void tigerDispatch() {
            RecordingStrategy ibkr = new RecordingStrategy("ibkr");
            RecordingStrategy tiger = new RecordingStrategy("tiger");
            TradeRecordRepository tradeRepo = mock(TradeRecordRepository.class);

            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(ibkr, tiger),
                    List.of(new FakeAdapter("ibkr"), new FakeAdapter("tiger")),
                    tradeRepo);

            svc.cleanupBatchData(11L, "tiger");

            assertEquals(List.of(11L), tiger.deletedBatches);
            assertEquals(Collections.emptyList(), ibkr.deletedBatches);
            verify(tradeRepo).deleteBySyncBatchId(11L);
        }

        @Test
        @DisplayName("unknown broker at runtime: throws and trade_records not touched")
        void unknownBrokerThrows() {
            RecordingStrategy ibkr = new RecordingStrategy("ibkr");
            TradeRecordRepository tradeRepo = mock(TradeRecordRepository.class);

            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(ibkr),
                    List.of(new FakeAdapter("ibkr")),
                    tradeRepo);

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> svc.cleanupBatchData(1L, "mystery-broker"));
            assertTrue(ex.getMessage().contains("mystery-broker"));

            assertEquals(Collections.emptyList(), ibkr.deletedBatches);
            verify(tradeRepo, never()).deleteBySyncBatchId(anyLong());
        }
    }

    // ------------------------------------------------------------------------
    // cleanupBatchData() — argument validation
    // ------------------------------------------------------------------------

    @Nested
    @DisplayName("cleanupBatchData() — argument validation")
    class ArgumentValidationTest {

        private SyncBatchCleanupService svc(TradeRecordRepository tradeRepo) {
            return new SyncBatchCleanupService(
                    List.of(new RecordingStrategy("ibkr")),
                    List.of(new FakeAdapter("ibkr")),
                    tradeRepo);
        }

        @Test
        @DisplayName("null batchId is rejected")
        void nullBatchIdRejected() {
            TradeRecordRepository tradeRepo = mock(TradeRecordRepository.class);
            assertThrows(IllegalArgumentException.class,
                    () -> svc(tradeRepo).cleanupBatchData(null, "ibkr"));
            verifyNoInteractions(tradeRepo);
        }

        @Test
        @DisplayName("null brokerCode is rejected")
        void nullBrokerCodeRejected() {
            assertThrows(IllegalArgumentException.class,
                    () -> svc(mock(TradeRecordRepository.class))
                            .cleanupBatchData(1L, null));
        }

        @Test
        @DisplayName("blank brokerCode is rejected")
        void blankBrokerCodeRejected() {
            assertThrows(IllegalArgumentException.class,
                    () -> svc(mock(TradeRecordRepository.class))
                            .cleanupBatchData(1L, "   "));
        }
    }

    // ------------------------------------------------------------------------
    // cleanupBatchData() — exception propagation
    // ------------------------------------------------------------------------

    @Nested
    @DisplayName("cleanupBatchData() — exception propagation")
    class ExceptionPropagationTest {

        @Test
        @DisplayName("staging DELETE failure propagates; trade_records not touched")
        void stagingFailurePropagates() {
            RecordingStrategy ibkr = new RecordingStrategy("ibkr");
            ibkr.toThrow = new RuntimeException("DB locked");
            TradeRecordRepository tradeRepo = mock(TradeRecordRepository.class);

            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(ibkr),
                    List.of(new FakeAdapter("ibkr")),
                    tradeRepo);

            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> svc.cleanupBatchData(1L, "ibkr"));
            assertTrue(thrown.getMessage().contains("DB locked"));
            verify(tradeRepo, never()).deleteBySyncBatchId(anyLong());
        }

        @Test
        @DisplayName("trade_records DELETE failure propagates")
        void tradeRecordFailurePropagates() {
            RecordingStrategy ibkr = new RecordingStrategy("ibkr");
            TradeRecordRepository tradeRepo = mock(TradeRecordRepository.class);
            when(tradeRepo.deleteBySyncBatchId(1L))
                    .thenThrow(new RuntimeException("FK constraint"));

            SyncBatchCleanupService svc = new SyncBatchCleanupService(
                    List.of(ibkr),
                    List.of(new FakeAdapter("ibkr")),
                    tradeRepo);

            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> svc.cleanupBatchData(1L, "ibkr"));
            assertTrue(thrown.getMessage().contains("FK constraint"));
            assertEquals(List.of(1L), ibkr.deletedBatches, "staging delete should have run first");
        }
    }
}
