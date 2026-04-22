package com.vortex.sync.adapter.ibkr;

import com.vortex.entity.Broker;
import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.TradeRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IbkrImportService}.
 *
 * After the Worker extraction refactoring, IbkrImportService is responsible
 * only for orchestration: resolving brokerId, iterating pending orders,
 * delegating to IbkrImportWorker, and triggering back-fill.
 *
 * Per-record mapping and transactional logic are tested in
 * {@link IbkrImportWorkerTest}.
 */
@ExtendWith(MockitoExtension.class)
class IbkrImportServiceTest {

    @Mock
    private IbkrStagedOrderRepository stagedOrderRepository;

    @Mock
    private TradeRecordRepository tradeRecordRepository;

    @Mock
    private BrokerRepository brokerRepository;

    @Mock
    private IbkrImportWorker importWorker;

    private IbkrImportService importService;

    @BeforeEach
    void setUp() {
        importService = new IbkrImportService(
                stagedOrderRepository, tradeRecordRepository,
                brokerRepository, importWorker);
    }

    private Broker buildBroker() {
        Broker broker = new Broker("盈透证券", "US");
        broker.setId(1L);
        broker.setBrokerCode("ibkr");
        return broker;
    }

    private IbkrStagedOrder buildStagedOrder(String orderId) {
        IbkrStagedOrder staged = new IbkrStagedOrder();
        staged.setId(100L);
        staged.setBatchId(1L);
        staged.setStatus("PENDING");
        staged.setOrderId(orderId);
        return staged;
    }

    // ========================================================
    // importAll — orchestration tests
    // ========================================================
    @Nested
    @DisplayName("importAll()")
    class ImportAllTest {

        @Test
        @DisplayName("should throw when broker not found")
        void shouldThrowWhenBrokerNotFound() {
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> importService.importAll(1L));
            verifyNoInteractions(importWorker);
        }

        @Test
        @DisplayName("should handle no pending orders gracefully")
        void shouldHandleNoPendingOrders() {
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(buildBroker()));
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(Collections.emptyList());

            assertDoesNotThrow(() -> importService.importAll(1L));

            verify(importWorker, never()).importSingleOrder(anyLong(), anyLong(), any());
        }

        @Test
        @DisplayName("should delegate each pending order to importWorker")
        void shouldDelegateEachOrderToWorker() {
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(buildBroker()));
            IbkrStagedOrder order1 = buildStagedOrder("ORD001");
            IbkrStagedOrder order2 = buildStagedOrder("ORD002");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(List.of(order1, order2));

            // No STK-side BookTrades to back-fill
            when(tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                    eq(1L), eq(TradeTrigger.OPTION),
                    eq(List.of(TriggerRefType.OPTION_ASSIGNED, TriggerRefType.OPTION_EXERCISE)),
                    eq(AssetType.STOCK)))
                    .thenReturn(Collections.emptyList());

            importService.importAll(1L);

            verify(importWorker).importSingleOrder(1L, 1L, order1);
            verify(importWorker).importSingleOrder(1L, 1L, order2);
        }

        @Test
        @DisplayName("should trigger back-fill for STK-side BookTrades after import")
        void shouldTriggerBackfillAfterImport() {
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(buildBroker()));
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(Collections.emptyList());

            TradeRecord stkRecord1 = new TradeRecord();
            stkRecord1.setId(10L);
            TradeRecord stkRecord2 = new TradeRecord();
            stkRecord2.setId(20L);

            when(tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                    eq(1L), eq(TradeTrigger.OPTION),
                    eq(List.of(TriggerRefType.OPTION_ASSIGNED, TriggerRefType.OPTION_EXERCISE)),
                    eq(AssetType.STOCK)))
                    .thenReturn(List.of(stkRecord1, stkRecord2));

            importService.importAll(1L);

            verify(importWorker).backfillSingleStkRecord(stkRecord1);
            verify(importWorker).backfillSingleStkRecord(stkRecord2);
        }

        @Test
        @DisplayName("should call markFailed via proxy when worker throws ImportOneFailedException")
        void shouldInvokeMarkFailedOnImportFailure() {
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(buildBroker()));
            IbkrStagedOrder bad = buildStagedOrder("ORD_FAIL");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(List.of(bad));

            // Worker throws the wrapped exception (per P0-1 fix)
            RuntimeException root = new RuntimeException("parse error");
            doThrow(new ImportOneFailedException(bad, root))
                    .when(importWorker).importSingleOrder(1L, 1L, bad);

            // Backfill path: no STK records to process
            when(tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                    eq(1L), eq(TradeTrigger.OPTION),
                    eq(List.of(TriggerRefType.OPTION_ASSIGNED, TriggerRefType.OPTION_EXERCISE)),
                    eq(AssetType.STOCK)))
                    .thenReturn(Collections.emptyList());

            // Must NOT bubble out — the loop must keep going and batch must finalize.
            assertDoesNotThrow(() -> importService.importAll(1L));

            // Service invokes markFailed on the exception's staged ref with a wrapped message.
            verify(importWorker).markFailed(same(bad), contains("parse error"));
        }

        @Test
        @DisplayName("should swallow markFailed exception so adapter-level residual check can pick it up")
        void shouldSwallowMarkFailedException() {
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(buildBroker()));
            IbkrStagedOrder bad = buildStagedOrder("ORD_MF_FAIL");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(List.of(bad));

            doThrow(new ImportOneFailedException(bad, new RuntimeException("mapping boom")))
                    .when(importWorker).importSingleOrder(1L, 1L, bad);
            doThrow(new IllegalStateException("markFailed also failed"))
                    .when(importWorker).markFailed(same(bad), anyString());

            when(tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                    eq(1L), eq(TradeTrigger.OPTION),
                    eq(List.of(TriggerRefType.OPTION_ASSIGNED, TriggerRefType.OPTION_EXERCISE)),
                    eq(AssetType.STOCK)))
                    .thenReturn(Collections.emptyList());

            // Per markFailedSafely: swallow + error-log. Adapter residual check catches the leftover.
            assertDoesNotThrow(() -> importService.importAll(1L));

            verify(importWorker).markFailed(same(bad), anyString());
        }

        @Test
        @DisplayName("should keep iterating remaining orders after one import failure")
        void shouldKeepIteratingAfterFailure() {
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(buildBroker()));
            IbkrStagedOrder good1 = buildStagedOrder("ORD_GOOD1");
            IbkrStagedOrder bad = buildStagedOrder("ORD_FAIL");
            IbkrStagedOrder good2 = buildStagedOrder("ORD_GOOD2");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(List.of(good1, bad, good2));

            doThrow(new ImportOneFailedException(bad, new RuntimeException("row2 boom")))
                    .when(importWorker).importSingleOrder(1L, 1L, bad);

            when(tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                    eq(1L), eq(TradeTrigger.OPTION),
                    eq(List.of(TriggerRefType.OPTION_ASSIGNED, TriggerRefType.OPTION_EXERCISE)),
                    eq(AssetType.STOCK)))
                    .thenReturn(Collections.emptyList());

            importService.importAll(1L);

            // All three were attempted.
            verify(importWorker).importSingleOrder(1L, 1L, good1);
            verify(importWorker).importSingleOrder(1L, 1L, bad);
            verify(importWorker).importSingleOrder(1L, 1L, good2);
            verify(importWorker).markFailed(same(bad), anyString());
        }
    }
}
