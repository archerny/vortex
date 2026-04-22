package com.vortex.sync.core;

import com.vortex.service.BrokerSyncBatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link BrokerSyncAsyncExecutor}.
 *
 * Covers (v2 state model):
 * - Successful sync: PENDING → PROCESSING(FETCHING) → COMPLETED
 * - Failed sync (adapter returns failure): PENDING → PROCESSING → FAILED
 * - Exception during sync: PENDING → PROCESSING → FAILED (catch-all)
 * - Exception during markAsFailed: logged but not re-thrown
 * - Correct invocation order of lifecycle methods
 * - batchId injection into SyncRequest
 *
 * <p>v2 removed the PARTIAL branch; markAsPartial is never invoked from the executor.</p>
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation") // markAsPartial is a v2 bridge; these tests verify it is NEVER called
class BrokerSyncAsyncExecutorTest {

    @Mock
    private BrokerSyncService brokerSyncService;

    @Mock
    private BrokerSyncBatchService batchService;

    @InjectMocks
    private BrokerSyncAsyncExecutor asyncExecutor;

    @Nested
    @DisplayName("execute() - successful sync (all imported)")
    class SuccessfulSyncTest {

        @Test
        @DisplayName("should transition PENDING → PROCESSING → COMPLETED on success")
        void shouldCompleteSuccessfully() {
            Long batchId = 1L;
            SyncRequest request = new SyncRequest("ibkr");
            SyncResult successResult = SyncResult.success("ibkr", 100, 90, 10, 2000);

            when(brokerSyncService.sync(request)).thenReturn(successResult);

            asyncExecutor.execute(batchId, request);

            InOrder inOrder = inOrder(batchService, brokerSyncService);
            inOrder.verify(batchService).markAsProcessing(batchId, "FETCHING");
            inOrder.verify(brokerSyncService).sync(request);
            inOrder.verify(batchService).markAsCompleted(batchId, successResult);

            verify(batchService, never()).markAsFailed(anyLong(), anyString());
            verify(batchService, never()).markAsPartial(anyLong(), any());
        }

        @Test
        @DisplayName("should inject batchId into request before sync")
        void shouldInjectBatchIdIntoRequest() {
            Long batchId = 42L;
            SyncRequest request = new SyncRequest("tiger");
            SyncResult result = SyncResult.success("tiger", 200, 5000);

            when(brokerSyncService.sync(request)).thenReturn(result);

            asyncExecutor.execute(batchId, request);

            assertEquals(42L, request.getBatchId());
            verify(batchService).markAsCompleted(42L, result);
        }
    }

    @Nested
    @DisplayName("execute() - v2: success always maps to COMPLETED (no PARTIAL)")
    class NoPartialBranchTest {

        @Test
        @DisplayName("success result always routes to markAsCompleted, never markAsPartial")
        void successAlwaysRoutesToCompleted() {
            Long batchId = 10L;
            SyncRequest request = new SyncRequest("ibkr");
            // Even with a lopsided result (e.g. 85 imported / 15 skipped), v2 routes to COMPLETED.
            SyncResult result = SyncResult.success("ibkr", 100, 85, 15, 3000);

            when(brokerSyncService.sync(request)).thenReturn(result);

            asyncExecutor.execute(batchId, request);

            verify(batchService).markAsCompleted(batchId, result);
            verify(batchService, never()).markAsPartial(anyLong(), any());
            verify(batchService, never()).markAsFailed(anyLong(), anyString());
        }
    }

    @Nested
    @DisplayName("execute() - adapter returns failure")
    class AdapterFailureTest {

        @Test
        @DisplayName("should transition PENDING → PROCESSING → FAILED when adapter returns failure")
        void shouldMarkAsFailedOnAdapterFailure() {
            Long batchId = 2L;
            SyncRequest request = new SyncRequest("ibkr");
            SyncResult failResult = SyncResult.failure("ibkr", "API timeout", 3000);

            when(brokerSyncService.sync(request)).thenReturn(failResult);

            asyncExecutor.execute(batchId, request);

            InOrder inOrder = inOrder(batchService, brokerSyncService);
            inOrder.verify(batchService).markAsProcessing(batchId, "FETCHING");
            inOrder.verify(brokerSyncService).sync(request);
            inOrder.verify(batchService).markAsFailed(batchId, failResult.getMessage());

            verify(batchService, never()).markAsCompleted(anyLong(), any());
            verify(batchService, never()).markAsPartial(anyLong(), any());
        }
    }

    @Nested
    @DisplayName("execute() - exception during sync")
    class ExceptionDuringSyncTest {

        @Test
        @DisplayName("should mark batch as FAILED when sync throws unexpected exception")
        void shouldMarkAsFailedOnException() {
            Long batchId = 3L;
            SyncRequest request = new SyncRequest("ibkr");

            when(brokerSyncService.sync(request)).thenThrow(new RuntimeException("Network error"));

            asyncExecutor.execute(batchId, request);

            verify(batchService).markAsProcessing(batchId, "FETCHING");
            verify(batchService).markAsFailed(eq(batchId), contains("Network error"));
            verify(batchService, never()).markAsCompleted(anyLong(), any());
        }

        @Test
        @DisplayName("should handle error in markAsFailed gracefully (double fault)")
        void shouldHandleDoubleFaultGracefully() {
            Long batchId = 4L;
            SyncRequest request = new SyncRequest("ibkr");

            when(brokerSyncService.sync(request)).thenThrow(new RuntimeException("Sync error"));
            doThrow(new RuntimeException("DB connection lost"))
                    .when(batchService).markAsFailed(eq(batchId), anyString());

            // Should NOT throw — the double fault is caught and logged
            assertDoesNotThrow(() -> asyncExecutor.execute(batchId, request));
        }

        @Test
        @DisplayName("should include exception message in failure message")
        void shouldIncludeExceptionMessageInFailure() {
            Long batchId = 5L;
            SyncRequest request = new SyncRequest("ibkr");
            String errorMsg = "IBKR Flex API returned 500";

            when(brokerSyncService.sync(request)).thenThrow(new RuntimeException(errorMsg));

            asyncExecutor.execute(batchId, request);

            verify(batchService).markAsFailed(eq(batchId), argThat(msg ->
                    msg.contains("Unexpected error") && msg.contains(errorMsg)));
        }
    }

    @Nested
    @DisplayName("execute() - markAsProcessing exception")
    class MarkAsProcessingExceptionTest {

        @Test
        @DisplayName("should mark as FAILED if markAsProcessing throws (batch not found)")
        void shouldHandleMarkAsProcessingFailure() {
            Long batchId = 99L;
            SyncRequest request = new SyncRequest("ibkr");

            doThrow(new IllegalArgumentException("Batch not found: 99"))
                    .when(batchService).markAsProcessing(batchId, "FETCHING");

            // The catch-all should handle this
            assertDoesNotThrow(() -> asyncExecutor.execute(batchId, request));

            // sync should NOT be called since markAsProcessing failed
            verify(brokerSyncService, never()).sync(any());
        }
    }
}
