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
 * Unit tests for {@link BrokerSyncAsyncExecutor} under the v2 state model.
 *
 * <p>Covers:
 * <ul>
 *   <li>Successful sync: {@code PROCESSING(FETCHING) → COMPLETED}.</li>
 *   <li>Adapter-level failure result: routed through
 *       {@link SyncBatchFailureHandler#handleFailure}.</li>
 *   <li>Exception inside {@link BrokerSyncService#sync}: same path —
 *       {@code failureHandler.handleFailure} with {@code "Unexpected error: ..."}.</li>
 *   <li>Exception during {@code markAsProcessing}: caught by the outer
 *       try/catch, {@code sync} is never invoked, {@code handleFailure} is
 *       still called for defensive cleanup.</li>
 *   <li>{@code handleFailure} itself throwing (programming error): swallowed.</li>
 *   <li>batchId injection into {@code SyncRequest}.</li>
 * </ul>
 *
 * <p>v2 has no PARTIAL branch. The executor no longer calls
 * {@code markAsFailed} directly — all failure paths delegate to
 * {@link SyncBatchFailureHandler}, which owns the cleanup + terminal status
 * transition.</p>
 */
@ExtendWith(MockitoExtension.class)
class BrokerSyncAsyncExecutorTest {

    @Mock
    private BrokerSyncService brokerSyncService;

    @Mock
    private BrokerSyncBatchService batchService;

    @Mock
    private SyncBatchFailureHandler failureHandler;

    @InjectMocks
    private BrokerSyncAsyncExecutor asyncExecutor;

    @Nested
    @DisplayName("execute() - successful sync")
    class SuccessfulSyncTest {

        @Test
        @DisplayName("should transition PROCESSING → COMPLETED on success; never invoke failureHandler")
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

            verifyNoInteractions(failureHandler);
            verify(batchService, never()).markAsFailed(anyLong(), anyString());
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
            verifyNoInteractions(failureHandler);
        }

        @Test
        @DisplayName("v2: a success result with non-zero skipped still maps to COMPLETED (no PARTIAL)")
        void successWithSkippedRoutesToCompleted() {
            Long batchId = 10L;
            SyncRequest request = new SyncRequest("ibkr");
            SyncResult result = SyncResult.success("ibkr", 100, 85, 15, 3000);

            when(brokerSyncService.sync(request)).thenReturn(result);

            asyncExecutor.execute(batchId, request);

            verify(batchService).markAsCompleted(batchId, result);
            verifyNoInteractions(failureHandler);
        }
    }

    @Nested
    @DisplayName("execute() - adapter returns failure")
    class AdapterFailureTest {

        @Test
        @DisplayName("should route adapter failure through failureHandler with the adapter's message")
        void shouldDelegateToFailureHandler() {
            Long batchId = 2L;
            SyncRequest request = new SyncRequest("ibkr");
            SyncResult failResult = SyncResult.failure("ibkr", "API timeout", 3000);

            when(brokerSyncService.sync(request)).thenReturn(failResult);

            asyncExecutor.execute(batchId, request);

            InOrder inOrder = inOrder(batchService, brokerSyncService, failureHandler);
            inOrder.verify(batchService).markAsProcessing(batchId, "FETCHING");
            inOrder.verify(brokerSyncService).sync(request);
            inOrder.verify(failureHandler).handleFailure(
                    eq(batchId),
                    eq("ibkr"),
                    argThat(msg -> msg != null && msg.contains("API timeout")));

            verify(batchService, never()).markAsCompleted(anyLong(), any());
            verify(batchService, never()).markAsFailed(anyLong(), anyString());
        }
    }

    @Nested
    @DisplayName("execute() - exception during sync")
    class ExceptionDuringSyncTest {

        @Test
        @DisplayName("should route exception through failureHandler with 'Unexpected error: ...' message")
        void shouldDelegateOnException() {
            Long batchId = 3L;
            SyncRequest request = new SyncRequest("ibkr");

            when(brokerSyncService.sync(request)).thenThrow(new RuntimeException("Network error"));

            asyncExecutor.execute(batchId, request);

            verify(batchService).markAsProcessing(batchId, "FETCHING");
            verify(failureHandler).handleFailure(
                    eq(batchId), eq("ibkr"), argThat(msg ->
                            msg.contains("Unexpected error") && msg.contains("Network error")));
            verify(batchService, never()).markAsCompleted(anyLong(), any());
            verify(batchService, never()).markAsFailed(anyLong(), anyString());
        }

        @Test
        @DisplayName("should swallow exception raised by failureHandler itself (double fault)")
        void shouldSwallowFailureHandlerException() {
            Long batchId = 4L;
            SyncRequest request = new SyncRequest("ibkr");

            when(brokerSyncService.sync(request)).thenThrow(new RuntimeException("Sync error"));
            doThrow(new RuntimeException("Handler went sideways"))
                    .when(failureHandler).handleFailure(eq(batchId), anyString(), anyString());

            // Should NOT throw — the double fault is caught and logged
            assertDoesNotThrow(() -> asyncExecutor.execute(batchId, request));
        }
    }

    @Nested
    @DisplayName("execute() - markAsProcessing exception")
    class MarkAsProcessingExceptionTest {

        @Test
        @DisplayName("should delegate to failureHandler if markAsProcessing throws; sync never invoked")
        void shouldHandleMarkAsProcessingFailure() {
            Long batchId = 99L;
            SyncRequest request = new SyncRequest("ibkr");

            doThrow(new IllegalArgumentException("Batch not found: 99"))
                    .when(batchService).markAsProcessing(batchId, "FETCHING");

            assertDoesNotThrow(() -> asyncExecutor.execute(batchId, request));

            // sync should NOT be called since markAsProcessing failed
            verify(brokerSyncService, never()).sync(any());
            // but failureHandler is still invoked (defensive cleanup)
            verify(failureHandler).handleFailure(
                    eq(batchId), eq("ibkr"), argThat(msg -> msg.contains("Batch not found: 99")));
        }
    }
}
