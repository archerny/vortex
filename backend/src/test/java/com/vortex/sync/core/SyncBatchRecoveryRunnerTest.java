package com.vortex.sync.core;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
 * <p>v2 does not have an INTERRUPTED state. Any batch left in a residual
 * active status (PENDING or PROCESSING) on startup is routed through
 * {@link SyncBatchFailureHandler#handleFailure}, which performs cleanup and
 * finalizes the batch as FAILED (or CLEANUP_FAILED if cleanup exhausts its
 * retries). Covering both PENDING and PROCESSING is required because the DB
 * {@code uk_only_one_active} partial unique index treats both as active —
 * leaving either one behind permanently blocks new sync requests.</p>
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
    @DisplayName("should do nothing when no residual batches found")
    void shouldDoNothingWhenNoStuckBatches() {
        when(batchRepository.findByStatusIn(any())).thenReturn(Collections.emptyList());

        recoveryRunner.run(null);

        verifyNoInteractions(failureHandler);
        verify(batchRepository, never()).save(any());
    }

    @Test
    @DisplayName("should scan both PENDING and PROCESSING (not CLEANUP_FAILED)")
    void shouldScanPendingAndProcessing() {
        when(batchRepository.findByStatusIn(any())).thenReturn(Collections.emptyList());

        recoveryRunner.run(null);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Collection<String>> captor = ArgumentCaptor.forClass(Collection.class);
        verify(batchRepository).findByStatusIn(captor.capture());
        assertThat(captor.getValue())
                .containsExactlyInAnyOrder("PENDING", "PROCESSING")
                .doesNotContain("CLEANUP_FAILED", "COMPLETED", "FAILED");
    }

    @Test
    @DisplayName("should route a PROCESSING batch through failureHandler with its broker code")
    void shouldCleanupProcessingBatch() {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(1L);
        batch.setBrokerCode("ibkr");
        batch.setStatus("PROCESSING");
        batch.setPhase("STAGING");

        when(batchRepository.findByStatusIn(any())).thenReturn(List.of(batch));

        recoveryRunner.run(null);

        verify(failureHandler).handleFailure(
                eq(1L),
                eq("ibkr"),
                argThat(msg -> msg != null && msg.contains("Interrupted") && msg.contains("PROCESSING")));
        // Runner must not mutate the entity directly — failureHandler owns the
        // status transition (to FAILED or CLEANUP_FAILED).
        verify(batchRepository, never()).save(any());
    }

    @Test
    @DisplayName("should also clean up PENDING batches (narrow crash window before markAsProcessing)")
    void shouldCleanupPendingBatch() {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(42L);
        batch.setBrokerCode("tiger");
        batch.setStatus("PENDING");
        batch.setPhase(null);

        when(batchRepository.findByStatusIn(any())).thenReturn(List.of(batch));

        recoveryRunner.run(null);

        verify(failureHandler).handleFailure(
                eq(42L),
                eq("tiger"),
                argThat(msg -> msg != null && msg.contains("PENDING")));
    }

    @Test
    @DisplayName("should clean up all residual batches in order regardless of status mix")
    void shouldCleanupAllStuckBatches() {
        BrokerSyncBatch batch1 = new BrokerSyncBatch();
        batch1.setId(1L);
        batch1.setBrokerCode("ibkr");
        batch1.setStatus("PENDING");
        batch1.setPhase(null);

        BrokerSyncBatch batch2 = new BrokerSyncBatch();
        batch2.setId(2L);
        batch2.setBrokerCode("tiger");
        batch2.setStatus("PROCESSING");
        batch2.setPhase("IMPORTING");

        when(batchRepository.findByStatusIn(any())).thenReturn(List.of(batch1, batch2));

        recoveryRunner.run(null);

        InOrder inOrder = org.mockito.Mockito.inOrder(failureHandler);
        inOrder.verify(failureHandler).handleFailure(eq(1L), eq("ibkr"), anyString());
        inOrder.verify(failureHandler).handleFailure(eq(2L), eq("tiger"), anyString());
    }
}
