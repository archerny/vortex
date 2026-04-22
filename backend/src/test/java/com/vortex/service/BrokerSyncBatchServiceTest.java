package com.vortex.service;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import com.vortex.sync.core.SyncResult;
import com.vortex.sync.exception.SyncConflictException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * BrokerSyncBatchService unit tests.
 *
 * Covers:
 * - listBatches (various filter combinations)
 * - findById
 * - createBatch
 * - save
 * - Status lifecycle: markAsProcessing, updatePhase, markAsCompleted,
 *   markAsPartial, markAsFailed, markAsInterrupted
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation") // markAsPartial / markAsInterrupted are v2 bridge methods; removed in phase 3
class BrokerSyncBatchServiceTest {

    @Mock
    private BrokerSyncBatchRepository batchRepository;

    @InjectMocks
    private BrokerSyncBatchService batchService;

    // ============ Helper methods ============

    private BrokerSyncBatch buildBatch(Long id, String brokerCode, String status) {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(id);
        batch.setBrokerCode(brokerCode);
        batch.setStatus(status);
        batch.setSyncDateFrom(LocalDate.of(2026, 1, 1));
        batch.setSyncDateTo(LocalDate.of(2026, 1, 31));
        batch.setTotalCount(10);
        batch.setImportedCount(8);
        batch.setSkippedCount(2);
        return batch;
    }

    // ========================================================
    // List batches
    // ========================================================
    @Nested
    @DisplayName("listBatches()")
    class ListBatchesTest {

        @Test
        @DisplayName("no filters — should query all records")
        void listWithNoFilters_shouldCallFindAll() {
            List<BrokerSyncBatch> expected = Arrays.asList(
                    buildBatch(1L, "ibkr", "COMPLETED"),
                    buildBatch(2L, "tiger", "FAILED")
            );
            when(batchRepository.findAllByOrderByStartedAtDesc()).thenReturn(expected);

            List<BrokerSyncBatch> result = batchService.listBatches(null, null);

            assertEquals(2, result.size());
            verify(batchRepository).findAllByOrderByStartedAtDesc();
            verify(batchRepository, never()).findByBrokerCodeOrderByStartedAtDesc(anyString());
            verify(batchRepository, never()).findByStatusOrderByStartedAtDesc(anyString());
            verify(batchRepository, never()).findByBrokerCodeAndStatusOrderByStartedAtDesc(anyString(), anyString());
        }

        @Test
        @DisplayName("blank filters — should act as no filters")
        void listWithBlankFilters_shouldCallFindAll() {
            when(batchRepository.findAllByOrderByStartedAtDesc()).thenReturn(List.of());

            batchService.listBatches("", "   ");

            verify(batchRepository).findAllByOrderByStartedAtDesc();
        }

        @Test
        @DisplayName("brokerCode only — should call findByBrokerCode")
        void listByBrokerCodeOnly_shouldCallFindByBrokerCode() {
            List<BrokerSyncBatch> expected = List.of(buildBatch(1L, "ibkr", "COMPLETED"));
            when(batchRepository.findByBrokerCodeOrderByStartedAtDesc("ibkr")).thenReturn(expected);

            List<BrokerSyncBatch> result = batchService.listBatches("ibkr", null);

            assertEquals(1, result.size());
            assertEquals("ibkr", result.get(0).getBrokerCode());
            verify(batchRepository).findByBrokerCodeOrderByStartedAtDesc("ibkr");
        }

        @Test
        @DisplayName("status only — should call findByStatus")
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
        @DisplayName("brokerCode and status — should call combined method")
        void listByBrokerCodeAndStatus_shouldCallCombinedMethod() {
            List<BrokerSyncBatch> expected = List.of(buildBatch(1L, "ibkr", "COMPLETED"));
            when(batchRepository.findByBrokerCodeAndStatusOrderByStartedAtDesc("ibkr", "COMPLETED"))
                    .thenReturn(expected);

            List<BrokerSyncBatch> result = batchService.listBatches("ibkr", "COMPLETED");

            assertEquals(1, result.size());
            assertEquals("ibkr", result.get(0).getBrokerCode());
            assertEquals("COMPLETED", result.get(0).getStatus());
            verify(batchRepository).findByBrokerCodeAndStatusOrderByStartedAtDesc("ibkr", "COMPLETED");
        }

        @Test
        @DisplayName("no results — should return empty list")
        void listWithNoResults_shouldReturnEmptyList() {
            when(batchRepository.findByBrokerCodeOrderByStartedAtDesc("unknown")).thenReturn(List.of());

            List<BrokerSyncBatch> result = batchService.listBatches("unknown", null);

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    // ========================================================
    // Find by ID
    // ========================================================
    @Nested
    @DisplayName("findById()")
    class FindByIdTest {

        @Test
        @DisplayName("existing ID — should return present")
        void findExistingBatch_shouldReturnPresent() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "COMPLETED");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));

            Optional<BrokerSyncBatch> result = batchService.findById(1L);

            assertTrue(result.isPresent());
            assertEquals(1L, result.get().getId());
            assertEquals("ibkr", result.get().getBrokerCode());
        }

        @Test
        @DisplayName("non-existing ID — should return empty")
        void findNonExistentBatch_shouldReturnEmpty() {
            when(batchRepository.findById(999L)).thenReturn(Optional.empty());

            Optional<BrokerSyncBatch> result = batchService.findById(999L);

            assertFalse(result.isPresent());
        }
    }

    // ========================================================
    // Create batch
    // ========================================================
    @Nested
    @DisplayName("createBatch()")
    class CreateBatchTest {

        @Test
        @DisplayName("should create batch with PENDING status and provided dates")
        void shouldCreateBatchWithDates() {
            LocalDate from = LocalDate.of(2026, 1, 1);
            LocalDate to = LocalDate.of(2026, 3, 31);

            when(batchRepository.save(any(BrokerSyncBatch.class))).thenAnswer(invocation -> {
                BrokerSyncBatch b = invocation.getArgument(0);
                b.setId(100L);
                return b;
            });

            BrokerSyncBatch result = batchService.createBatch("ibkr", from, to);

            assertEquals("ibkr", result.getBrokerCode());
            assertEquals("PENDING", result.getStatus());
            assertEquals(from, result.getSyncDateFrom());
            assertEquals(to, result.getSyncDateTo());
            assertEquals(0, result.getTotalCount());
            assertEquals(0, result.getImportedCount());
            assertEquals(0, result.getSkippedCount());
        }

        @Test
        @DisplayName("should default dates to today when null")
        void shouldDefaultDatesToToday() {
            when(batchRepository.save(any(BrokerSyncBatch.class))).thenAnswer(invocation -> {
                BrokerSyncBatch b = invocation.getArgument(0);
                b.setId(101L);
                return b;
            });

            BrokerSyncBatch result = batchService.createBatch("tiger", null, null);

            assertEquals(LocalDate.now(), result.getSyncDateFrom());
            assertEquals(LocalDate.now(), result.getSyncDateTo());
        }

        @Test
        @DisplayName("v2 conflict: should throw SyncConflictException when another batch is active")
        void shouldThrowWhenActiveBatchExists() {
            BrokerSyncBatch active = buildBatch(77L, "ibkr", "PROCESSING");
            when(batchRepository.findFirstByStatusInOrderByIdDesc(
                    BrokerSyncBatchService.ACTIVE_STATUSES))
                    .thenReturn(Optional.of(active));

            SyncConflictException ex = assertThrows(SyncConflictException.class,
                    () -> batchService.createBatch("ibkr", null, null));

            assertEquals(77L, ex.getConflictingBatchId());
            assertEquals("PROCESSING", ex.getConflictingStatus());
            assertTrue(ex.getMessage().contains("77"));
            assertTrue(ex.getMessage().contains("PROCESSING"));
            verify(batchRepository, never()).save(any());
        }

        @Test
        @DisplayName("v2 conflict: CLEANUP_FAILED batch should also block new syncs")
        void shouldThrowWhenCleanupFailedBatchExists() {
            BrokerSyncBatch blocked = buildBatch(88L, "tiger", "CLEANUP_FAILED");
            when(batchRepository.findFirstByStatusInOrderByIdDesc(
                    BrokerSyncBatchService.ACTIVE_STATUSES))
                    .thenReturn(Optional.of(blocked));

            SyncConflictException ex = assertThrows(SyncConflictException.class,
                    () -> batchService.createBatch("tiger", null, null));

            assertEquals(88L, ex.getConflictingBatchId());
            assertEquals("CLEANUP_FAILED", ex.getConflictingStatus());
            verify(batchRepository, never()).save(any());
        }

        @Test
        @DisplayName("v2 conflict: DB unique-index race is translated to SyncConflictException")
        void shouldTranslateUniqueIndexViolation() {
            // Fast-path finds nothing (race window)
            when(batchRepository.findFirstByStatusInOrderByIdDesc(
                    BrokerSyncBatchService.ACTIVE_STATUSES))
                    .thenReturn(Optional.empty());
            // …but the INSERT loses the uk_only_one_active race
            when(batchRepository.save(any(BrokerSyncBatch.class)))
                    .thenThrow(new DataIntegrityViolationException(
                            "ERROR: duplicate key value violates unique constraint "
                                    + "\"uk_only_one_active\""));

            SyncConflictException ex = assertThrows(SyncConflictException.class,
                    () -> batchService.createBatch("ibkr", null, null));

            assertNull(ex.getConflictingBatchId(),
                    "DB-level fallback has no way to surface the conflicting batch ID");
            assertNull(ex.getConflictingStatus());
            assertNotNull(ex.getCause(), "should wrap the DataIntegrityViolationException");
        }
    }

    // ========================================================
    // Status lifecycle
    // ========================================================
    @Nested
    @DisplayName("Status lifecycle transitions")
    class StatusLifecycleTest {

        @Test
        @DisplayName("markAsProcessing should set status and phase")
        void markAsProcessingShouldSetStatusAndPhase() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "PENDING");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
            when(batchRepository.save(any())).thenReturn(batch);

            batchService.markAsProcessing(1L, "FETCHING");

            assertEquals("PROCESSING", batch.getStatus());
            assertEquals("FETCHING", batch.getPhase());
            assertNotNull(batch.getStartedAt());
        }

        @Test
        @DisplayName("updatePhase should only change phase")
        void updatePhaseShouldOnlyChangePhase() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "PROCESSING");
            batch.setPhase("FETCHING");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
            when(batchRepository.save(any())).thenReturn(batch);

            batchService.updatePhase(1L, "IMPORTING");

            assertEquals("PROCESSING", batch.getStatus()); // unchanged
            assertEquals("IMPORTING", batch.getPhase());
        }

        @Test
        @DisplayName("markAsCompleted should set counts and clear phase")
        void markAsCompletedShouldSetCounts() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "PROCESSING");
            batch.setPhase("IMPORTING");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
            when(batchRepository.save(any())).thenReturn(batch);

            SyncResult result = SyncResult.success("ibkr", 100, 85, 15, 3000);
            batchService.markAsCompleted(1L, result);

            assertEquals("COMPLETED", batch.getStatus());
            assertNull(batch.getPhase());
            assertEquals(100, batch.getTotalCount());
            assertEquals(85, batch.getImportedCount());
            assertEquals(15, batch.getSkippedCount());
            assertNotNull(batch.getCompletedAt());
        }

        @Test
        @DisplayName("markAsPartial (deprecated v2 bridge) should set PARTIAL status and counts")
        void markAsPartialShouldSetPartialStatus() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "PROCESSING");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
            when(batchRepository.save(any())).thenReturn(batch);

            SyncResult result = SyncResult.success("ibkr", 100, 80, 20, 3000);
            batchService.markAsPartial(1L, result);

            assertEquals("PARTIAL", batch.getStatus());
            assertNull(batch.getPhase());
            assertEquals(100, batch.getTotalCount());
            assertEquals(80, batch.getImportedCount());
            assertEquals(20, batch.getSkippedCount());
        }

        @Test
        @DisplayName("markAsFailed should set error message and clear phase")
        void markAsFailedShouldSetErrorMessage() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "PROCESSING");
            batch.setPhase("FETCHING");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
            when(batchRepository.save(any())).thenReturn(batch);

            batchService.markAsFailed(1L, "API timeout");

            assertEquals("FAILED", batch.getStatus());
            assertNull(batch.getPhase());
            assertEquals("API timeout", batch.getErrorMessage());
            assertNotNull(batch.getCompletedAt());
        }

        @Test
        @DisplayName("markAsInterrupted should preserve phase for diagnostics")
        void markAsInterruptedShouldPreservePhase() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "PROCESSING");
            batch.setPhase("STAGING");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
            when(batchRepository.save(any())).thenReturn(batch);

            batchService.markAsInterrupted(1L, "Application restarted");

            assertEquals("INTERRUPTED", batch.getStatus());
            assertEquals("STAGING", batch.getPhase()); // preserved!
            assertEquals("Application restarted", batch.getErrorMessage());
        }

        @Test
        @DisplayName("markAsCleanupFailed should set CLEANUP_FAILED, preserve phase, and record error")
        void markAsCleanupFailedShouldPreservePhaseAndRecordError() {
            BrokerSyncBatch batch = buildBatch(1L, "ibkr", "PROCESSING");
            batch.setPhase("IMPORTING");
            when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
            when(batchRepository.save(any())).thenReturn(batch);

            String msg = "Cleanup failed after 3 attempts: DB connection lost. Original error: IBKR 500";
            batchService.markAsCleanupFailed(1L, msg);

            assertEquals("CLEANUP_FAILED", batch.getStatus());
            assertEquals("IMPORTING", batch.getPhase()); // preserved for diagnostics
            assertEquals(msg, batch.getErrorMessage());
            assertNotNull(batch.getCompletedAt());
        }

        @Test
        @DisplayName("markAsCleanupFailed should throw for non-existent batch")
        void markAsCleanupFailedShouldThrowForMissingBatch() {
            when(batchRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class,
                    () -> batchService.markAsCleanupFailed(999L, "any"));
        }

        @Test
        @DisplayName("markAsProcessing should throw for non-existent batch")
        void markAsProcessingShouldThrowForMissingBatch() {
            when(batchRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class,
                    () -> batchService.markAsProcessing(999L, "FETCHING"));
        }
    }
}
