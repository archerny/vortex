package com.localledger.sync.core;

import com.localledger.entity.BrokerSyncBatch;
import com.localledger.repository.BrokerSyncBatchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SyncBatchRecoveryRunner}.
 *
 * Covers:
 * - No stuck batches: should do nothing
 * - Single stuck batch: should mark as INTERRUPTED, preserve phase
 * - Multiple stuck batches: should mark all as INTERRUPTED
 */
@ExtendWith(MockitoExtension.class)
class SyncBatchRecoveryRunnerTest {

    @Mock
    private BrokerSyncBatchRepository batchRepository;

    @InjectMocks
    private SyncBatchRecoveryRunner recoveryRunner;

    @Test
    @DisplayName("should do nothing when no stuck batches found")
    void shouldDoNothingWhenNoStuckBatches() {
        when(batchRepository.findByStatus("PROCESSING")).thenReturn(Collections.emptyList());

        recoveryRunner.run(null);

        verify(batchRepository, never()).save(any());
    }

    @Test
    @DisplayName("should mark stuck batch as INTERRUPTED and preserve phase")
    void shouldMarkStuckBatchAsInterrupted() {
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(1L);
        batch.setBrokerCode("ibkr");
        batch.setStatus("PROCESSING");
        batch.setPhase("STAGING");

        when(batchRepository.findByStatus("PROCESSING")).thenReturn(List.of(batch));

        recoveryRunner.run(null);

        assertEquals("INTERRUPTED", batch.getStatus());
        assertEquals("STAGING", batch.getPhase()); // preserved
        assertNotNull(batch.getErrorMessage());
        assertTrue(batch.getErrorMessage().contains("restarted"));
        verify(batchRepository).save(batch);
    }

    @Test
    @DisplayName("should mark multiple stuck batches as INTERRUPTED")
    void shouldMarkAllStuckBatches() {
        BrokerSyncBatch batch1 = new BrokerSyncBatch();
        batch1.setId(1L);
        batch1.setStatus("PROCESSING");
        batch1.setPhase("FETCHING");

        BrokerSyncBatch batch2 = new BrokerSyncBatch();
        batch2.setId(2L);
        batch2.setStatus("PROCESSING");
        batch2.setPhase("IMPORTING");

        when(batchRepository.findByStatus("PROCESSING")).thenReturn(List.of(batch1, batch2));

        recoveryRunner.run(null);

        assertEquals("INTERRUPTED", batch1.getStatus());
        assertEquals("INTERRUPTED", batch2.getStatus());
        assertEquals("FETCHING", batch1.getPhase()); // preserved
        assertEquals("IMPORTING", batch2.getPhase()); // preserved
        verify(batchRepository, times(2)).save(any());
    }
}
