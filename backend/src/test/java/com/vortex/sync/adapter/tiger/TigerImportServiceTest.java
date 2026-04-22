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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TigerImportService}.
 *
 * <p>Focuses on orchestration responsibilities — resolving the broker id,
 * iterating over PENDING staged orders, and re-querying counts after the
 * loop completes. Per-record logic is mocked via {@link TigerImportWorker}
 * and covered in {@link TigerImportWorkerTest}.
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
        @DisplayName("should return zero-counts and not touch worker when no PENDING rows")
        void shouldHandleNoPendingOrders() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(buildBroker()));
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(Collections.emptyList());
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "IMPORTED")).thenReturn(0L);
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "SKIPPED")).thenReturn(0L);
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "FAILED")).thenReturn(0L);

            TigerImportService.ImportResult result = assertDoesNotThrow(() -> importService.importAll(1L));

            assertEquals(0, result.attempted);
            assertEquals(0, result.imported);
            assertEquals(0, result.skipped);
            assertEquals(0, result.failed);
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
            when(stagedOrderRepository.countByBatchIdAndStatus(anyLong(), any())).thenReturn(0L);

            importService.importAll(1L);

            verify(importWorker).importOne(1L, 7L, o1);
            verify(importWorker).importOne(1L, 7L, o2);
        }

        @Test
        @DisplayName("should re-query final counts from the staging table after the loop")
        void shouldReQueryCountsAfterLoop() {
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(buildBroker()));
            when(stagedOrderRepository.findByBatchIdAndStatus(1L, "PENDING"))
                    .thenReturn(List.of(buildStagedOrder("T-1"), buildStagedOrder("T-2"),
                            buildStagedOrder("T-3")));
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "IMPORTED")).thenReturn(2L);
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "SKIPPED")).thenReturn(1L);
            when(stagedOrderRepository.countByBatchIdAndStatus(1L, "FAILED")).thenReturn(0L);

            TigerImportService.ImportResult result = importService.importAll(1L);

            assertEquals(3, result.attempted);
            assertEquals(2, result.imported);
            assertEquals(1, result.skipped);
            assertEquals(0, result.failed);
        }
    }
}
