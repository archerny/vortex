package com.vortex.sync.adapter.ibkr;

import com.vortex.dto.IbkrSyncSettingsUpdate;
import com.vortex.entity.*;
import com.vortex.repository.*;
import com.vortex.service.*;
import com.vortex.sync.exception.SyncConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IbkrSyncSettingsServiceTest {
    final IbkrSyncSettingsRepository settings = mock(IbkrSyncSettingsRepository.class);
    final BrokerRepository brokers = mock(BrokerRepository.class);
    final BrokerService brokerService = mock(BrokerService.class);
    final BrokerSyncBatchRepository batches = mock(BrokerSyncBatchRepository.class);
    final LedgerWriteLock lock = mock(LedgerWriteLock.class);
    final IbkrSecretCipher cipher = mock(IbkrSecretCipher.class);
    final IbkrFlexQueryProperties properties = new IbkrFlexQueryProperties();
    final IbkrSyncSettingsService service = new IbkrSyncSettingsService(settings, brokers, brokerService, batches, lock, cipher, properties);

    @BeforeEach void initialize() { service.loadPersistedConfiguration(); }
    IbkrSyncSettingsUpdate input(String token) {
        var value = new IbkrSyncSettingsUpdate();
        value.setBrokerId(1L); value.setQueryId("12345"); value.setToken(token); return value;
    }
    Broker broker() { var value = new Broker("Synthetic IBKR", "US"); value.setId(1L); return value; }

    @Test void databaseIsOnlySourceAndEachReadUsesOneCurrentPair() {
        properties.setFlexToken("ignored-startup-token"); properties.setTradeConfirmQueryId("999");
        assertFalse(properties.isConfigured());
        var row = new IbkrSyncSettings(); row.setTokenCiphertext("cipher-A"); row.setQueryId("111");
        when(settings.findById("ibkr")).thenReturn(Optional.of(row));
        when(cipher.decrypt("cipher-A")).thenReturn("synthetic-A");
        assertEquals(new IbkrFlexQueryProperties.Credentials("synthetic-A", "111"), properties.credentials());
        row.setTokenCiphertext("cipher-B"); row.setQueryId("222");
        when(cipher.decrypt("cipher-B")).thenReturn("synthetic-B");
        assertEquals(new IbkrFlexQueryProperties.Credentials("synthetic-B", "222"), properties.credentials());
        assertFalse(properties.credentials().toString().contains("synthetic"));
    }

    @Test void firstSaveBindsAccountAndOnlyPersistsCiphertext() {
        when(brokers.findByBrokerCode("ibkr")).thenReturn(Optional.of(broker()));
        when(cipher.encrypt("synthetic-A")).thenReturn("cipher-A");
        var view = service.save(input(" synthetic-A "));
        var captor = org.mockito.ArgumentCaptor.forClass(IbkrSyncSettings.class);
        verify(settings).saveAndFlush(captor.capture());
        assertEquals("cipher-A", captor.getValue().getTokenCiphertext());
        assertEquals("12345", captor.getValue().getQueryId());
        verify(brokerService).bindSyncCode(1L, "ibkr"); verify(lock).acquire();
        assertTrue(view.ready()); assertFalse(view.toString().contains("synthetic-A"));
    }

    @Test void blankTokenRetainsStoredValueAndMissingKeyCannotBeSilentlyReplaced() {
        var row = new IbkrSyncSettings(); row.setTokenCiphertext("cipher-A"); row.setQueryId("111");
        when(settings.findById("ibkr")).thenReturn(Optional.of(row));
        when(brokers.findByBrokerCode("ibkr")).thenReturn(Optional.of(broker()));
        when(cipher.decrypt("cipher-A")).thenReturn("synthetic-A");
        when(cipher.encrypt("synthetic-A")).thenReturn("cipher-A");
        service.save(input(""));
        verify(cipher).encrypt("synthetic-A");
        when(cipher.decrypt("cipher-A")).thenThrow(new IllegalStateException("key missing"));
        assertThrows(IllegalStateException.class, () -> service.save(input("synthetic-replacement")));
        verify(cipher, never()).encrypt("synthetic-replacement");
    }

    @Test void clearWorksWithoutKeyAndCannotReviveStartupCredentials() {
        var row = new IbkrSyncSettings(); row.setTokenCiphertext("lost-key-cipher"); row.setQueryId("111");
        when(settings.findById("ibkr")).thenReturn(Optional.of(row));
        service.clear();
        assertNull(row.getTokenCiphertext()); assertNull(row.getQueryId());
        verifyNoInteractions(cipher);
        assertFalse(properties.isConfigured());
    }

    @ParameterizedTest @ValueSource(strings = {"PENDING", "PROCESSING", "CLEANUP_FAILED"})
    void activeBatchPreventsSaveAndClearBeforeAnyAccountOrCredentialWrite(String status) {
        var batch = new BrokerSyncBatch(); batch.setId(9L); batch.setStatus(status);
        when(batches.findFirstByStatusInOrderByIdDesc(any())).thenReturn(Optional.of(batch));
        assertThrows(SyncConflictException.class, () -> service.save(input("synthetic-A")));
        assertThrows(SyncConflictException.class, service::clear);
        verifyNoInteractions(cipher, brokerService, settings);
    }

    @Test void rejectsMissingFirstTokenAndInvalidQueryWithoutPersisting() {
        assertThrows(IllegalArgumentException.class, () -> service.save(input(null)));
        var invalid = input("synthetic-A"); invalid.setQueryId("QUERY_NAME");
        assertThrows(IllegalArgumentException.class, () -> service.save(invalid));
        verify(settings, never()).saveAndFlush(any());
    }
}
