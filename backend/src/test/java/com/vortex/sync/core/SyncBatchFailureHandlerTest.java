package com.vortex.sync.core;

import com.vortex.service.BrokerSyncBatchService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SyncBatchFailureHandler}.
 *
 * Verifies the v2 fail-fast cleanup contract:
 * - On first successful cleanup: batch → FAILED (no CLEANUP_FAILED).
 * - On cleanup succeeding at attempt 2 or 3: same outcome, no escalation.
 * - When all {@link SyncBatchFailureHandler#MAX_CLEANUP_ATTEMPTS} cleanup
 *   attempts fail: batch → CLEANUP_FAILED with a message that includes both
 *   the cleanup failure and the original error.
 * - Between attempts, the handler sleeps for a fixed backoff; if the worker
 *   thread is interrupted during the sleep, remaining retries are skipped
 *   and the batch is escalated to CLEANUP_FAILED.
 * - Second-order failures (status updates throwing) must be swallowed — the
 *   handler never re-throws, because its callers have no recovery path.
 *
 * <p>The backoff is shortened to 0ms for tests so retry scenarios do not
 * pay real wall-clock time. The original value is restored after the
 * class finishes, to keep test-runtime state clean.
 */
@ExtendWith(MockitoExtension.class)
class SyncBatchFailureHandlerTest {

    private static long originalBackoffMs;

    @BeforeAll
    static void shortenBackoff() {
        originalBackoffMs = SyncBatchFailureHandler.CLEANUP_RETRY_BACKOFF_MS;
        SyncBatchFailureHandler.CLEANUP_RETRY_BACKOFF_MS = 0L;
    }

    @AfterAll
    static void restoreBackoff() {
        SyncBatchFailureHandler.CLEANUP_RETRY_BACKOFF_MS = originalBackoffMs;
    }

    @Mock
    private SyncBatchCleanupService cleanupService;

    @Mock
    private BrokerSyncBatchService batchService;

    @InjectMocks
    private SyncBatchFailureHandler handler;

    private static final Long BATCH_ID = 42L;
    private static final String BROKER = "ibkr";
    private static final String ORIGINAL_ERROR = "Broker API timeout";

    @Nested
    @DisplayName("When cleanup succeeds")
    class CleanupSucceeds {

        @Test
        @DisplayName("first-attempt success → markAsFailed once, no CLEANUP_FAILED")
        void firstAttemptSuccess_marksFailed() {
            // cleanupService does nothing (void default)
            handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

            InOrder inOrder = inOrder(cleanupService, batchService);
            inOrder.verify(cleanupService).cleanupBatchData(BATCH_ID, BROKER);
            inOrder.verify(batchService).markAsFailed(BATCH_ID, ORIGINAL_ERROR);

            verify(cleanupService, times(1)).cleanupBatchData(anyLong(), anyString());
            verify(batchService, never()).markAsCleanupFailed(anyLong(), anyString());
        }

        @Test
        @DisplayName("succeeds on 2nd attempt → markAsFailed once, no CLEANUP_FAILED")
        void secondAttemptSuccess_marksFailed() {
            doThrow(new RuntimeException("transient DB error"))
                    .doNothing()
                    .when(cleanupService).cleanupBatchData(BATCH_ID, BROKER);

            handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

            verify(cleanupService, times(2)).cleanupBatchData(BATCH_ID, BROKER);
            verify(batchService, times(1)).markAsFailed(BATCH_ID, ORIGINAL_ERROR);
            verify(batchService, never()).markAsCleanupFailed(anyLong(), anyString());
        }

        @Test
        @DisplayName("succeeds on 3rd (last) attempt → markAsFailed once, no CLEANUP_FAILED")
        void thirdAttemptSuccess_marksFailed() {
            doThrow(new RuntimeException("fail 1"))
                    .doThrow(new RuntimeException("fail 2"))
                    .doNothing()
                    .when(cleanupService).cleanupBatchData(BATCH_ID, BROKER);

            handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

            verify(cleanupService, times(SyncBatchFailureHandler.MAX_CLEANUP_ATTEMPTS))
                    .cleanupBatchData(BATCH_ID, BROKER);
            verify(batchService, times(1)).markAsFailed(BATCH_ID, ORIGINAL_ERROR);
            verify(batchService, never()).markAsCleanupFailed(anyLong(), anyString());
        }
    }

    @Nested
    @DisplayName("When cleanup fails permanently")
    class CleanupExhausted {

        @Test
        @DisplayName("all 3 attempts fail → markAsCleanupFailed with combined message, never markAsFailed")
        void allAttemptsFail_marksCleanupFailed() {
            RuntimeException cleanupError = new RuntimeException("DB connection refused");
            doThrow(cleanupError).when(cleanupService).cleanupBatchData(BATCH_ID, BROKER);

            handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

            verify(cleanupService, times(SyncBatchFailureHandler.MAX_CLEANUP_ATTEMPTS))
                    .cleanupBatchData(BATCH_ID, BROKER);
            verify(batchService, never()).markAsFailed(anyLong(), anyString());

            ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
            verify(batchService, times(1)).markAsCleanupFailed(eq(BATCH_ID), messageCaptor.capture());

            String combinedMessage = messageCaptor.getValue();
            assertThat(combinedMessage)
                    .as("Combined message must mention attempt count, cleanup error, and original error")
                    .contains(String.valueOf(SyncBatchFailureHandler.MAX_CLEANUP_ATTEMPTS))
                    .contains("DB connection refused")
                    .contains(ORIGINAL_ERROR);
        }

        @Test
        @DisplayName("attempts repeat exactly MAX_CLEANUP_ATTEMPTS times")
        void retriesExactlyMaxAttempts() {
            doThrow(new RuntimeException("boom")).when(cleanupService).cleanupBatchData(anyLong(), anyString());

            handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

            verify(cleanupService, times(SyncBatchFailureHandler.MAX_CLEANUP_ATTEMPTS))
                    .cleanupBatchData(BATCH_ID, BROKER);
        }
    }

    @Nested
    @DisplayName("When the worker thread is interrupted during backoff")
    class InterruptHandling {

        @Test
        @DisplayName("interrupt during backoff → skip remaining retries, escalate to CLEANUP_FAILED, preserve interrupt flag")
        void interruptDuringBackoff_skipsRetriesAndEscalates() {
            // Arrange: cleanup always fails, and we pre-set the thread's
            // interrupt flag so that Thread.sleep(...) inside the handler
            // throws InterruptedException on the first backoff.
            doThrow(new RuntimeException("transient DB error"))
                    .when(cleanupService).cleanupBatchData(BATCH_ID, BROKER);

            // Force a non-zero backoff for this test so Thread.sleep actually
            // checks the interrupt flag (sleep(0) returns immediately on some
            // JVMs without throwing).
            long savedBackoff = SyncBatchFailureHandler.CLEANUP_RETRY_BACKOFF_MS;
            SyncBatchFailureHandler.CLEANUP_RETRY_BACKOFF_MS = 50L;
            try {
                Thread.currentThread().interrupt();

                handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

                // After the first failed cleanup, the handler enters backoff,
                // Thread.sleep throws, handler breaks out → exactly 1 attempt.
                verify(cleanupService, times(1)).cleanupBatchData(BATCH_ID, BROKER);
                verify(batchService, times(1)).markAsCleanupFailed(eq(BATCH_ID), anyString());
                verify(batchService, never()).markAsFailed(anyLong(), anyString());

                // Interrupt flag must be preserved (restored by the handler).
                assertThat(Thread.currentThread().isInterrupted())
                        .as("Handler must restore the interrupt flag")
                        .isTrue();
            } finally {
                SyncBatchFailureHandler.CLEANUP_RETRY_BACKOFF_MS = savedBackoff;
                // Clear the interrupt flag so it doesn't leak into later tests.
                Thread.interrupted();
            }
        }
    }

    @Nested
    @DisplayName("Second-order failures are swallowed")
    class SecondOrderFailures {

        @Test
        @DisplayName("markAsFailed throws → handler returns normally, no markAsCleanupFailed")
        void markAsFailedThrows_swallowedAndNoEscalation() {
            doThrow(new RuntimeException("DB down during status update"))
                    .when(batchService).markAsFailed(BATCH_ID, ORIGINAL_ERROR);

            // must NOT throw
            handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

            verify(cleanupService, times(1)).cleanupBatchData(BATCH_ID, BROKER);
            verify(batchService, times(1)).markAsFailed(BATCH_ID, ORIGINAL_ERROR);
            // If cleanup succeeded, we do NOT escalate to CLEANUP_FAILED just because
            // the status write failed — the batch is stuck, but that is logged, not
            // overwritten with a different terminal state.
            verify(batchService, never()).markAsCleanupFailed(anyLong(), anyString());
        }

        @Test
        @DisplayName("markAsCleanupFailed throws → handler returns normally")
        void markAsCleanupFailedThrows_swallowed() {
            doThrow(new RuntimeException("cleanup")).when(cleanupService)
                    .cleanupBatchData(BATCH_ID, BROKER);
            doThrow(new RuntimeException("DB down during status update"))
                    .when(batchService).markAsCleanupFailed(eq(BATCH_ID), anyString());

            // must NOT throw
            handler.handleFailure(BATCH_ID, BROKER, ORIGINAL_ERROR);

            verify(cleanupService, times(SyncBatchFailureHandler.MAX_CLEANUP_ATTEMPTS))
                    .cleanupBatchData(BATCH_ID, BROKER);
            verify(batchService, times(1)).markAsCleanupFailed(eq(BATCH_ID), anyString());
            verify(batchService, never()).markAsFailed(anyLong(), anyString());
        }
    }
}
