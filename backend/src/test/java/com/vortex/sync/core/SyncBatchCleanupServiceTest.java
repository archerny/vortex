package com.vortex.sync.core;

import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.repository.TradeRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SyncBatchCleanupService}.
 *
 * Verifies:
 * - Correct broker-specific dispatch (ibkr vs. tiger)
 * - trade_records is always cleaned regardless of broker
 * - Unknown brokers throw IllegalStateException (and do NOT touch any table)
 * - Null/blank arguments are rejected up-front
 * - Repository exceptions propagate (so the @Transactional wrapper rolls back)
 */
@ExtendWith(MockitoExtension.class)
class SyncBatchCleanupServiceTest {

    @Mock
    private IbkrStagedOrderRepository ibkrStagedOrderRepository;

    @Mock
    private IbkrStagedTradeConfirmRepository ibkrStagedTradeConfirmRepository;

    @Mock
    private TigerStagedOrderRepository tigerStagedOrderRepository;

    @Mock
    private TradeRecordRepository tradeRecordRepository;

    @InjectMocks
    private SyncBatchCleanupService cleanupService;

    @Nested
    @DisplayName("cleanupBatchData() — broker dispatch")
    class BrokerDispatchTest {

        @Test
        @DisplayName("ibkr: should delete from both staging tables and trade_records")
        void ibkrShouldDeleteStagingAndTradeRecords() {
            when(ibkrStagedOrderRepository.deleteByBatchId(42L)).thenReturn(7L);
            when(ibkrStagedTradeConfirmRepository.deleteByBatchId(42L)).thenReturn(9L);
            when(tradeRecordRepository.deleteBySyncBatchId(42L)).thenReturn(3);

            cleanupService.cleanupBatchData(42L, "ibkr");

            verify(ibkrStagedOrderRepository).deleteByBatchId(42L);
            verify(ibkrStagedTradeConfirmRepository).deleteByBatchId(42L);
            verify(tradeRecordRepository).deleteBySyncBatchId(42L);
            verifyNoInteractions(tigerStagedOrderRepository);
        }

        @Test
        @DisplayName("tiger: should delete from tiger_staged_orders and trade_records")
        void tigerShouldDeleteStagingAndTradeRecords() {
            when(tigerStagedOrderRepository.deleteByBatchId(11L)).thenReturn(4L);
            when(tradeRecordRepository.deleteBySyncBatchId(11L)).thenReturn(2);

            cleanupService.cleanupBatchData(11L, "tiger");

            verify(tigerStagedOrderRepository).deleteByBatchId(11L);
            verify(tradeRecordRepository).deleteBySyncBatchId(11L);
            verifyNoInteractions(ibkrStagedOrderRepository, ibkrStagedTradeConfirmRepository);
        }

        @Test
        @DisplayName("unknown broker: should throw and not touch any repository")
        void unknownBrokerShouldThrowAndNotTouchRepos() {
            assertThrows(IllegalStateException.class,
                    () -> cleanupService.cleanupBatchData(1L, "mystery-broker"));

            verifyNoInteractions(ibkrStagedOrderRepository,
                    ibkrStagedTradeConfirmRepository,
                    tigerStagedOrderRepository,
                    tradeRecordRepository);
        }
    }

    @Nested
    @DisplayName("cleanupBatchData() — argument validation")
    class ArgumentValidationTest {

        @Test
        @DisplayName("null batchId is rejected")
        void nullBatchIdRejected() {
            assertThrows(IllegalArgumentException.class,
                    () -> cleanupService.cleanupBatchData(null, "ibkr"));
            verifyNoInteractions(ibkrStagedOrderRepository, tradeRecordRepository);
        }

        @Test
        @DisplayName("null brokerCode is rejected")
        void nullBrokerCodeRejected() {
            assertThrows(IllegalArgumentException.class,
                    () -> cleanupService.cleanupBatchData(1L, null));
        }

        @Test
        @DisplayName("blank brokerCode is rejected")
        void blankBrokerCodeRejected() {
            assertThrows(IllegalArgumentException.class,
                    () -> cleanupService.cleanupBatchData(1L, "   "));
        }
    }

    @Nested
    @DisplayName("cleanupBatchData() — exception propagation")
    class ExceptionPropagationTest {

        @Test
        @DisplayName("staging DELETE failure propagates (so @Transactional rolls back)")
        void stagingDeleteFailurePropagates() {
            when(ibkrStagedOrderRepository.deleteByBatchId(1L))
                    .thenThrow(new RuntimeException("DB locked"));

            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> cleanupService.cleanupBatchData(1L, "ibkr"));
            assertTrue(thrown.getMessage().contains("DB locked"));

            // trade_records should NOT have been touched because the exception
            // aborted the method before it got there
            verify(tradeRecordRepository, never()).deleteBySyncBatchId(anyLong());
        }

        @Test
        @DisplayName("trade_records DELETE failure propagates")
        void tradeRecordDeleteFailurePropagates() {
            when(ibkrStagedOrderRepository.deleteByBatchId(1L)).thenReturn(0L);
            when(ibkrStagedTradeConfirmRepository.deleteByBatchId(1L)).thenReturn(0L);
            when(tradeRecordRepository.deleteBySyncBatchId(1L))
                    .thenThrow(new RuntimeException("FK constraint"));

            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> cleanupService.cleanupBatchData(1L, "ibkr"));
            assertTrue(thrown.getMessage().contains("FK constraint"));
        }
    }
}
