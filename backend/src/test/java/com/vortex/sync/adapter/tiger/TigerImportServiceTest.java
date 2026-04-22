package com.vortex.sync.adapter.tiger;

import com.vortex.entity.Broker;
import com.vortex.entity.TigerStagedOrder;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.TigerStagedOrderRepository;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TigerImportService}.
 *
 * <p>Focuses on orchestration responsibilities — resolving the broker id and
 * iterating over PENDING staged orders. Per-record logic is mocked via
 * {@link TigerImportWorker} and covered in {@link TigerImportWorkerTest}.
 *
 * <p>Aggregate counting is intentionally <strong>not</strong> performed by
 * {@code TigerImportService} (aligned with {@code IbkrImportService}); the
 * adapter re-queries the staging table itself, so this test suite does not
 * assert on count values.
 */
@ExtendWith(MockitoExtension.class)
class TigerImportServiceTest {

    @Mock
    private TigerStagedOrderRepository stagedOrderRepository;

    @Mock
    private BrokerRepository brokerRepository;

    @Mock
    private TigerImportWorker importWorker;

    private TigerImportService importService;

    @BeforeEach
    void setUp() {
        importService = new TigerImportService(stagedOrderRepository, brokerRepository, importWorker);
    }

    private Broker buildBroker() {
        Broker broker = new Broker("Tiger Securities", "US");
        broker.setId(7L);
        broker.setBrokerCode("tiger");
        return broker;
    }

    private TigerStagedOrder buildStagedOrder(String tigerId) {
        TigerStagedOrder staged = new TigerStagedOrder();
        staged.setId(100L);
        staged.setBatchId(1L);
        staged.setStatus("PENDING");
        staged.setTigerId(tigerId);
        return staged;
    }

    @Nested
    @DisplayName("importAll()")
    class ImportAll {

        @Test
        @DisplayName("should throw when broker with code 'tiger' is not found")
        void shouldThrowWhenBrokerNotFound() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> importService.importAll(1L));
            verifyNoInteractions(importWorker);
        }

        @Test
        @DisplayName("should complete silently and not touch worker when no PENDING rows")
        void shouldHandleNoPendingOrders() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(buildBroker()));
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(Collections.emptyList());

            assertDoesNotThrow(() -> importService.importAll(1L));

            verify(importWorker, never()).importOne(anyLong(), anyLong(), any());
        }

        @Test
        @DisplayName("should delegate every PENDING order to the worker with the resolved broker id")
        void shouldDelegateEachOrderToWorker() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(buildBroker()));
            TigerStagedOrder o1 = buildStagedOrder("T-1");
            TigerStagedOrder o2 = buildStagedOrder("T-2");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(List.of(o1, o2));

            importService.importAll(1L);

            verify(importWorker).importOne(1L, 7L, o1);
            verify(importWorker).importOne(1L, 7L, o2);
        }

        @Test
        @DisplayName("should call markFailed via proxy when worker throws ImportOneFailedException")
        void shouldInvokeMarkFailedOnImportFailure() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(buildBroker()));
            TigerStagedOrder bad = buildStagedOrder("T-FAIL");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(List.of(bad));

            RuntimeException root = new RuntimeException("parse error");
            doThrow(new ImportOneFailedException(bad, root))
                    .when(importWorker).importOne(1L, 7L, bad);

            assertDoesNotThrow(() -> importService.importAll(1L));

            verify(importWorker).markFailed(same(bad), contains("parse error"));
        }

        @Test
        @DisplayName("should swallow markFailed exception so adapter-level residual check can pick it up")
        void shouldSwallowMarkFailedException() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(buildBroker()));
            TigerStagedOrder bad = buildStagedOrder("T-MF-FAIL");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING")).thenReturn(List.of(bad));

            doThrow(new ImportOneFailedException(bad, new RuntimeException("boom")))
                    .when(importWorker).importOne(1L, 7L, bad);
            doThrow(new IllegalStateException("markFailed also failed"))
                    .when(importWorker).markFailed(same(bad), anyString());

            assertDoesNotThrow(() -> importService.importAll(1L));

            verify(importWorker).markFailed(same(bad), anyString());
        }

        @Test
        @DisplayName("should keep iterating remaining orders after one import failure")
        void shouldKeepIteratingAfterFailure() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(buildBroker()));
            TigerStagedOrder good1 = buildStagedOrder("T-GOOD1");
            TigerStagedOrder bad = buildStagedOrder("T-FAIL");
            TigerStagedOrder good2 = buildStagedOrder("T-GOOD2");
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(List.of(good1, bad, good2));

            doThrow(new ImportOneFailedException(bad, new RuntimeException("row2 boom")))
                    .when(importWorker).importOne(1L, 7L, bad);

            importService.importAll(1L);

            verify(importWorker).importOne(1L, 7L, good1);
            verify(importWorker).importOne(1L, 7L, bad);
            verify(importWorker).importOne(1L, 7L, good2);
            verify(importWorker).markFailed(same(bad), anyString());
        }
    }
}
