package com.vortex.service;

import com.vortex.entity.*;
import com.vortex.repository.*;
import com.vortex.sync.exception.SyncConflictException;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrokerServiceTest {
    final BrokerRepository brokers = mock(BrokerRepository.class);
    final BrokerSyncBatchRepository batches = mock(BrokerSyncBatchRepository.class);
    final LedgerWriteLock lock = mock(LedgerWriteLock.class);
    final BrokerService service = new BrokerService(brokers, lock, batches);
    Broker broker(Long id, String code) { var b = new Broker("Synthetic", "US"); b.setId(id); b.setBrokerCode(code); return b; }

    @Test void updatesSubmittedSyncCodeAndRejectsDuplicateBinding() {
        var existing = broker(1L, null); when(brokers.findById(1L)).thenReturn(Optional.of(existing));
        service.update(1L, broker(null, "ibkr")); assertEquals("ibkr", existing.getBrokerCode());
        when(brokers.findByBrokerCode("ibkr")).thenReturn(Optional.of(broker(2L, "ibkr")));
        assertThrows(IllegalArgumentException.class, () -> service.bindSyncCode(1L, "ibkr"));
    }

    @Test void rejectsSuppliedCreateIdAndActiveBatchAccountMutationBypasses() {
        assertThrows(IllegalArgumentException.class, () -> service.create(broker(1L, "ibkr")));
        verifyNoInteractions(lock);
        var existing = broker(1L, "ibkr"); when(brokers.findById(1L)).thenReturn(Optional.of(existing));
        var batch = new BrokerSyncBatch(); batch.setId(9L); batch.setStatus("PROCESSING");
        when(batches.findFirstByStatusInOrderByIdDesc(any())).thenReturn(Optional.of(batch));
        assertThrows(SyncConflictException.class, () -> service.create(broker(null, "ibkr")));
        assertThrows(SyncConflictException.class, () -> service.update(1L, broker(null, null)));
        var disabled = broker(null, "ibkr"); disabled.setIsActive(false);
        assertThrows(SyncConflictException.class, () -> service.update(1L, disabled));
        assertThrows(SyncConflictException.class, () -> service.delete(1L));
        verify(brokers, never()).save(any()); verify(brokers, never()).deleteById(any());
    }
}
