package com.vortex.sync.core;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SyncBatchRecoveryRunner} under the v2 state model.
 *
 * <p>v2 does not have an INTERRUPTED state. Any batch stuck in PROCESSING on
 * startup is routed through {@link SyncBatchFailureHandler#handleFailure},
 * which performs cleanup and finalizes the batch as FAILED (or
 * CLEANUP_FAILED if cleanup exhausts its retries).</p>
 */
@ExtendWith(MockitoExtension.class)
class SyncBatchRecoveryRunnerTest {

    @Mock
    private BrokerSyncBatchRepository batchRepository;

    @Mock
    private SyncBatchFailureHandler failureHandler;

    @InjectMocks
    private SyncBatchRecoveryRunner recoveryRunner;

    @Test
    @DisplayName("should do nothing when no stuck batches found")
    void shouldDoNothingWhenNoStuckBatches() {
        when(batchRepository.findByStatus("PROCESSING")).thenReturn(Collections.emptyList());

        recoveryRunner.run(null);

        verifyNoInteractions(failureHandler);
        verify(batchRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("should route a single stuck batch through failureHandler with its broker code")
    void shouldCleanupSingleStuckBatch() {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(1L);
        batch.setBrokerCode("ibkr");
        batch.setStatus("PROCESSING");
        batch.setPhase("STAGING");

        when(batchRepository.findByStatus("PROCESSING")).thenReturn(List.of(batch));

        recoveryRunner.run(null);

        verify(failureHandler).handleFailure(
                eq(1L),
                eq("ibkr"),
                argThat(msg -> msg != null && msg.contains("Interrupted")));
        // Runner must not mutate the entity directly — failureHandler owns the
        // status transition (to FAILED or CLEANUP_FAILED).
        verify(batchRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("should clean up all stuck batches in order")
    void shouldCleanupAllStuckBatches() {
        BrokerSyncBatch batch1 = new BrokerSyncBatch();
        batch1.setId(1L);
        batch1.setBrokerCode("ibkr");
        batch1.setStatus("PROCESSING");
        batch1.setPhase("FETCHING");

        BrokerSyncBatch batch2 = new BrokerSyncBatch();
        batch2.setId(2L);
        batch2.setBrokerCode("tiger");
        batch2.setStatus("PROCESSING");
        batch2.setPhase("IMPORTING");

        when(batchRepository.findByStatus("PROCESSING")).thenReturn(List.of(batch1, batch2));

        recoveryRunner.run(null);

        InOrder inOrder = org.mockito.Mockito.inOrder(failureHandler);
        inOrder.verify(failureHandler).handleFailure(eq(1L), eq("ibkr"), anyString());
        inOrder.verify(failureHandler).handleFailure(eq(2L), eq("tiger"), anyString());
    }
}
