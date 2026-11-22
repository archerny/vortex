package com.vortex.sync.adapter.ibkr;

import com.vortex.entity.BrokerAccount;
import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.BrokerAccountRepository;
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
    private BrokerAccountRepository accountRepository;

    @Mock
    private IbkrImportWorker importWorker;

    private IbkrImportService importService;

    @BeforeEach
    void setUp() {
        importService = new IbkrImportService(stagedOrderRepository, tradeRecordRepository, accountRepository, importWorker, com.vortex.fixtures.AccountFixtures.batches());
    }

    private BrokerAccount buildAccount() {
        var account = new BrokerAccount(); account.setId(1L); account.setBrokerId(1L);
        account.setAccountId("SYNTH-ACCOUNT"); account.setAccountName("Synthetic account"); return account;
    }

    private IbkrStagedOrder buildStagedOrder(String orderId) {
        IbkrStagedOrder staged = new IbkrStagedOrder(); staged.setAccountId("SYNTH-ACCOUNT");
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
        void shouldThrowWhenAccountNotFound() {
            when(accountRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> importService.importAll(1L));
            verifyNoInteractions(importWorker);
        }

        @Test
        @DisplayName("should handle no pending orders gracefully")
        void shouldHandleNoPendingOrders() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(buildAccount()));
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(Collections.emptyList());

            assertDoesNotThrow(() -> importService.importAll(1L));

            verify(importWorker, never()).importSingleOrder(anyLong(), anyLong(), org.mockito.ArgumentMatchers.eq(1L), any());
        }

        @Test
        @DisplayName("should delegate each pending order to importWorker")
        void shouldDelegateEachOrderToWorker() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(buildAccount()));
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

            verify(importWorker).importSingleOrder(1L, 1L, 1L, order1);
            verify(importWorker).importSingleOrder(1L, 1L, 1L, order2);
        }

        @Test
        @DisplayName("should trigger back-fill for STK-side BookTrades after import")
        void shouldTriggerBackfillAfterImport() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(buildAccount()));
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(Collections.emptyList());

            TradeRecord stkRecord1 = new TradeRecord(); stkRecord1.setBrokerAccountId(1L);
            stkRecord1.setId(10L);
            TradeRecord stkRecord2 = new TradeRecord(); stkRecord2.setBrokerAccountId(1L);
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
        void shouldPropagateLinkageFailureAndStopFurtherBackfills() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(buildAccount()));
            TradeRecord stock = new TradeRecord(), later = new TradeRecord();
            when(tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                    eq(1L), eq(TradeTrigger.OPTION), anyList(), eq(AssetType.STOCK)))
                    .thenReturn(List.of(stock, later));
            RuntimeException failure = new com.vortex.sync.core.CategorizedSyncException(
                    com.vortex.sync.core.FailureCategory.UNRECOGNIZED, null, "Synthetic linkage failure");
            doThrow(failure).when(importWorker).backfillSingleStkRecord(stock);
            assertSame(failure, assertThrows(RuntimeException.class, () -> importService.importAll(1L)));
            verify(importWorker, never()).backfillSingleStkRecord(later);
        }

        @Test
        @DisplayName("should call markFailed via proxy when worker throws ImportOneFailedException")
        void shouldInvokeMarkFailedOnImportFailure() {
            when(accountRepository.findById(1L)).thenReturn(Optional.of(buildAccount()));
            IbkrStagedOrder bad = buildStagedOrder("ORD_FAIL");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(List.of(bad));

            // Worker throws the wrapped exception (per P0-1 fix)
            RuntimeException root = new RuntimeException("parse error");
            doThrow(new ImportOneFailedException(bad, root))
                    .when(importWorker).importSingleOrder(1L, 1L, 1L, bad);

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
            when(accountRepository.findById(1L)).thenReturn(Optional.of(buildAccount()));
            IbkrStagedOrder bad = buildStagedOrder("ORD_MF_FAIL");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(List.of(bad));

            doThrow(new ImportOneFailedException(bad, new RuntimeException("mapping boom")))
                    .when(importWorker).importSingleOrder(1L, 1L, 1L, bad);
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
            when(accountRepository.findById(1L)).thenReturn(Optional.of(buildAccount()));
            IbkrStagedOrder good1 = buildStagedOrder("ORD_GOOD1");
            IbkrStagedOrder bad = buildStagedOrder("ORD_FAIL");
            IbkrStagedOrder good2 = buildStagedOrder("ORD_GOOD2");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(List.of(good1, bad, good2));

            doThrow(new ImportOneFailedException(bad, new RuntimeException("row2 boom")))
                    .when(importWorker).importSingleOrder(1L, 1L, 1L, bad);

            when(tradeRecordRepository.findStkSideBookTradesNeedingBackfill(
                    eq(1L), eq(TradeTrigger.OPTION),
                    eq(List.of(TriggerRefType.OPTION_ASSIGNED, TriggerRefType.OPTION_EXERCISE)),
                    eq(AssetType.STOCK)))
                    .thenReturn(Collections.emptyList());

            importService.importAll(1L);

            // All three were attempted.
            verify(importWorker).importSingleOrder(1L, 1L, 1L, good1);
            verify(importWorker).importSingleOrder(1L, 1L, 1L, bad);
            verify(importWorker).importSingleOrder(1L, 1L, 1L, good2);
            verify(importWorker).markFailed(same(bad), anyString());
        }
    }
}
