package com.localledger.service;

import com.localledger.entity.BrokerSyncBatch;
import com.localledger.repository.BrokerSyncBatchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * BrokerSyncBatchService 单元测试
 * 覆盖列表查询（各种筛选组合）和单条查询逻辑
 */
@ExtendWith(MockitoExtension.class)
class BrokerSyncBatchServiceTest {

    @Mock
    private BrokerSyncBatchRepository batchRepository;

    @InjectMocks
    private BrokerSyncBatchService batchService;

    // ============ 辅助方法 ============

    private BrokerSyncBatch buildBatch(Long id, String brokerName, String status) {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(id);
        batch.setBrokerName(brokerName);
        batch.setStatus(status);
        batch.setSyncDateFrom(LocalDate.of(2026, 1, 1));
        batch.setSyncDateTo(LocalDate.of(2026, 1, 31));
        batch.setTotalCount(10);
        batch.setImportedCount(8);
        batch.setSkippedCount(1);
        batch.setFailedCount(1);
        return batch;
    }

    // ========================================================
    // 列表查询测试
    // ========================================================
    @Nested
    @DisplayName("查询同步批次列表")
    class ListBatchesTest {

        @Test
        @DisplayName("无筛选条件 - 应查询全部记录")
        void listWithNoFilters_shouldCallFindAll() {
            List<BrokerSyncBatch> expected = Arrays.asList(
                    buildBatch(1L, "ibkr", "COMPLETED"),
                    buildBatch(2L, "tiger", "FAILED")
            );
            when(batchRepository.findAllByOrderByStartedAtDesc()).thenReturn(expected);

            List<BrokerSyncBatch> result = batchService.listBatches(null, null);

            assertEquals(2, result.size());
            verify(batchRepository).findAllByOrderByStartedAtDesc();
            verify(batchRepository, never()).findByBrokerNameOrderByStartedAtDesc(anyString());
            verify(batchRepository, never()).findByStatusOrderByStartedAtDesc(anyString());
            verify(batchRepository, never()).findByBrokerNameAndStatusOrderByStartedAtDesc(anyString(), anyString());
        }

        @Test
        @DisplayName("空字符串筛选条件 - 应等效于无筛选")
        void listWithBlankFilters_shouldCallFindAll() {
            when(batchRepository.findAllByOrderByStartedAtDesc()).thenReturn(List.of());

            batchService.listBatches("", "   ");

            verify(batchRepository).findAllByOrderByStartedAtDesc();
        }

        @Test
        @DisplayName("仅按券商名称筛选 - 应调用 findByBrokerName")
        void listByBrokerNameOnly_shouldCallFindByBrokerName() {
            List<BrokerSyncBatch> expected = List.of(buildBatch(1L, "ibkr", "COMPLETED"));
            when(batchRepository.findByBrokerNameOrderByStartedAtDesc("ibkr")).thenReturn(expected);

            List<BrokerSyncBatch> result = batchService.listBatches("ibkr", null);

            assertEquals(1, result.size());
            assertEquals("ibkr", result.get(0).getBrokerName());
            verify(batchRepository).findByBrokerNameOrderByStartedAtDesc("ibkr");
        }

        @Test
        @DisplayName("仅按状态筛选 - 应调用 findByStatus")
        void listByStatusOnly_shouldCallFindByStatus() {
            List<BrokerSyncBatch> expected = List.of(
                    buildBatch(1L, "ibkr", "FAILED"),
                    buildBatch(2L, "tiger", "FAILED")
            );
            when(batchRepository.findByStatusOrderByStartedAtDesc("FAILED")).thenReturn(expected);

            List<BrokerSyncBatch> result = batchService.listBatches(null, "FAILED");

            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(b -> "FAILED".equals(b.getStatus())));
            verify(batchRepository).findByStatusOrderByStartedAtDesc("FAILED");
        }

        @Test
        @DisplayName("同时按券商和状态筛选 - 应调用 findByBrokerNameAndStatus")
        void listByBrokerNameAndStatus_shouldCallCombinedMethod() {
            List<BrokerSyncBatch> expected = List.of(buildBatch(1L, "ibkr", "COMPLETED"));
            when(batchRepository.findByBrokerNameAndStatusOrderByStartedAtDesc("ibkr", "COMPLETED"))
                    .thenReturn(expected);

            List<BrokerSyncBatch> result = batchService.listBatches("ibkr", "COMPLETED");

            assertEquals(1, result.size());
            assertEquals("ibkr", result.get(0).getBrokerName());
            assertEquals("COMPLETED", result.get(0).getStatus());
            verify(batchRepository).findByBrokerNameAndStatusOrderByStartedAtDesc("ibkr", "COMPLETED");
        }

        @Test
        @DisplayName("查询结果为空 - 应返回空列表")
        void listWithNoResults_shouldReturnEmptyList() {
            when(batchRepository.findByBrokerNameOrderByStartedAtDesc("unknown")).thenReturn(List.of());

            List<BrokerSyncBatch> result = batchService.listBatches("unknown", null);

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    // ========================================================
    // 单条查询测试
    // ========================================================
    @Nested
    @DisplayName("查询单个同步批次")
    class FindByIdTest {

        @Test
        @DisplayName("ID存在 - 应返回批次")
        void findExistingBatch_shouldReturnPresent() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "COMPLETED");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));

            Optional<BrokerSyncBatch> result = batchService.findById(1L);

            assertTrue(result.isPresent());
            assertEquals(1L, result.get().getId());
            assertEquals("ibkr", result.get().getBrokerName());
        }

        @Test
        @DisplayName("ID不存在 - 应返回空")
        void findNonExistentBatch_shouldReturnEmpty() {
            when(batchRepository.findById(999L)).thenReturn(Optional.empty());

            Optional<BrokerSyncBatch> result = batchService.findById(999L);

            assertFalse(result.isPresent());
        }
    }
}
