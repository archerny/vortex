package com.vortex.service;

import com.vortex.dto.BrokerSyncConnectionUpdate;
import com.vortex.entity.Broker;
import com.vortex.entity.BrokerSyncConnection;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.BrokerAccountRepository;
import com.vortex.entity.BrokerAccount;
import com.vortex.repository.BrokerSyncConnectionRepository;
import com.vortex.sync.adapter.ibkr.IbkrFlexQueryProperties;
import com.vortex.sync.adapter.tiger.TigerApiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrokerSyncConnectionServiceTest {
    final BrokerRepository brokers = mock(BrokerRepository.class);
    final BrokerAccountRepository accounts = mock(BrokerAccountRepository.class);
    final BrokerSyncConnectionRepository connections = mock(BrokerSyncConnectionRepository.class);
    final IbkrFlexQueryProperties ibkr = new IbkrFlexQueryProperties();
    final TigerApiProperties tiger = new TigerApiProperties();
    final BrokerSyncConnectionService service = new BrokerSyncConnectionService(brokers, accounts, connections, ibkr, tiger);

    @BeforeEach void initialize() { service.useDatabaseCredentials(); }

    BrokerAccount broker(long id, String code) {
        var broker = new Broker("Synthetic-" + id, "US");
        broker.setId(id); broker.setBrokerCode(code);
        when(brokers.findById(id)).thenReturn(Optional.of(broker));
        var account = new BrokerAccount(); account.setId(id); account.setBrokerId(id);
        account.setAccountId("SYNTH-ACCOUNT-" + id); account.setAccountName("Synthetic-" + id);
        when(accounts.findById(id)).thenReturn(Optional.of(account));
        return account;
    }

    BrokerSyncConnection row(long id, String code, Map<String, String> values) {
        var row = new BrokerSyncConnection();
        row.setBrokerAccountId(id); row.setBrokerCode(code); row.setCredentials(values);
        return row;
    }

    @Test void listsAllManagedAccountsAndOnlyReturnsPublicFields() {
        var first = broker(1L, "ibkr"); var second = broker(2L, "tiger"); var manual = broker(3L, null);
        second.setIsActive(false);
        when(accounts.findAllByOrderByIdAsc()).thenReturn(List.of(first, second, manual));
        when(connections.findAll()).thenReturn(List.of(row(1L, "ibkr", Map.of("token", "synthetic-token", "queryId", "123"))));
        var result = service.list();
        assertEquals(3, result.size());
        assertTrue(result.get(0).ready());
        assertEquals(Map.of("queryId", "123"), result.get(0).values());
        assertTrue(result.get(0).configuredSecrets().contains("token"));
        assertFalse(result.get(1).active());
        assertFalse(result.get(2).supported());
        assertFalse(result.toString().contains("synthetic-token"));
    }

    @Test void savesPlaintextForExistingAccountWithoutUpdatingAccountDetails() {
        var broker = broker(1L, "ibkr");
        assertTrue(service.save(1L, new BrokerSyncConnectionUpdate("ibkr", Map.of("token", "synthetic-token", "queryId", "123"))).ready());
        var capture = org.mockito.ArgumentCaptor.forClass(BrokerSyncConnection.class);
        verify(connections).saveAndFlush(capture.capture());
        assertEquals(1L, capture.getValue().getBrokerAccountId());
        assertEquals("synthetic-token", capture.getValue().getCredentials().get("token"));
        assertEquals(1L, broker.getBrokerId());
        verify(brokers, never()).save(any());
    }

    @Test void refusesUnknownOrUnassignedAccountAndStaleBrokerType() {
        assertThrows(IllegalArgumentException.class, () -> service.save(9L, new BrokerSyncConnectionUpdate("ibkr", Map.of())));
        broker(1L, null);
        assertThrows(IllegalArgumentException.class, () -> service.save(1L, new BrokerSyncConnectionUpdate("ibkr", Map.of())));
        broker(2L, "tiger");
        assertThrows(IllegalArgumentException.class, () -> service.save(2L, new BrokerSyncConnectionUpdate("ibkr", Map.of("token", "synthetic", "queryId", "123"))));
        verify(connections, never()).saveAndFlush(any());
        verify(brokers, never()).save(any());
    }

    @Test void tigerPrivateKeyIsRetainedReplacedAndExcludedFromMetadata() {
        broker(2L, "tiger");
        var row = row(2L, "tiger", Map.of("tigerId", "100", "privateKey", "synthetic-private-key", "account", "SYNTH-ACCOUNT"));
        when(connections.findById(2L)).thenReturn(Optional.of(row));
        var view = service.save(2L, new BrokerSyncConnectionUpdate("tiger", Map.of("tigerId", "200", "account", "SYNTH-NEXT")));
        assertEquals("synthetic-private-key", row.getCredentials().get("privateKey"));
        assertEquals(Map.of("tigerId", "200"), view.values());
        assertFalse(view.toString().contains("synthetic-private-key"));
        service.save(2L, new BrokerSyncConnectionUpdate("tiger", Map.of("tigerId", "200", "account", "SYNTH-NEXT", "privateKey", "synthetic-new-key")));
        assertEquals("synthetic-new-key", row.getCredentials().get("privateKey"));
    }

    @Test void validatesRequiredSecretsAndQueryId() {
        broker(1L, "ibkr"); broker(2L, "tiger");
        assertThrows(IllegalArgumentException.class, () -> service.save(1L, new BrokerSyncConnectionUpdate("ibkr", Map.of("queryId", "123"))));
        assertThrows(IllegalArgumentException.class, () -> service.save(1L, new BrokerSyncConnectionUpdate("ibkr", Map.of("queryId", "NAME", "token", "synthetic"))));
        assertThrows(IllegalArgumentException.class, () -> service.save(2L, new BrokerSyncConnectionUpdate("tiger", Map.of("tigerId", "100", "account", "SYNTH"))));
        verify(connections, never()).saveAndFlush(any());
    }

    @Test void bothRuntimeProvidersUseOnlyCurrentAccountDatabaseCredentials() {
        var first = broker(1L, "ibkr"); var second = broker(2L, "tiger");
        ibkr.setFlexToken("ignored-startup"); tiger.setPrivateKey("ignored-startup");
        assertFalse(ibkr.isConfigured()); assertFalse(tiger.isConfigured());

        when(connections.findById(1L)).thenReturn(Optional.of(row(1L, "ibkr", Map.of("token", "synthetic-A", "queryId", "123"))));
        var tigerRow = row(2L, "tiger", Map.of("tigerId", "100", "privateKey", "synthetic-key", "account", "SYNTH"));
        when(connections.findById(2L)).thenReturn(Optional.of(tigerRow));
        assertEquals("synthetic-A", ibkr.credentials(1L).token());
        assertEquals(new TigerApiProperties.Credentials("100", "synthetic-key", "SYNTH-ACCOUNT-2"), tiger.credentials(2L));
        tigerRow.setBrokerCode("ibkr");
        assertFalse(tiger.credentials(2L).isConfigured());
        assertFalse(tiger.credentials(2L).toString().contains("synthetic-key"));
    }

    @Test void clearOnlyDeletesTheSelectedConnectionAndDisabledAccountsAreNotReady() {
        var broker = broker(2L, "tiger"); broker.setIsActive(false);
        var view = service.save(2L, new BrokerSyncConnectionUpdate("tiger", Map.of("tigerId", "100", "account", "SYNTH", "privateKey", "synthetic-key")));
        assertTrue(view.configured()); assertFalse(view.ready());
        assertFalse(service.clear(2L).configured());
        verify(connections).deleteById(2L);
        verify(brokers, never()).deleteById(any());
    }

    @Test void sameBrokerAccountsHaveIndependentCredentialsAndLegacyApiRejectsAmbiguity() {
        var first = broker(1L, "ibkr");
        var second = new BrokerAccount(); second.setId(11L); second.setBrokerId(1L);
        second.setAccountId("SYNTH-SECOND"); second.setAccountName("Second");
        when(accounts.findById(11L)).thenReturn(Optional.of(second));
        when(connections.findById(1L)).thenReturn(Optional.of(row(1L, "ibkr", Map.of("token", "synthetic-first", "queryId", "123"))));
        when(connections.findById(11L)).thenReturn(Optional.of(row(11L, "ibkr", Map.of("token", "synthetic-second", "queryId", "456"))));
        assertEquals("synthetic-first", ibkr.credentials(1L).token());
        assertEquals("synthetic-second", ibkr.credentials(11L).token());
        assertFalse(ibkr.isConfigured());
        var broker = new Broker("Synthetic", "US"); broker.setId(1L);
        when(brokers.findByBrokerCode("ibkr")).thenReturn(Optional.of(broker));
        when(accounts.findByBrokerIdOrderByIdAsc(1L)).thenReturn(List.of(first, second));
        assertThrows(IllegalArgumentException.class, () -> service.getForBrokerCode("ibkr"));
        first.setAccountId(null);
        assertTrue(service.get(1L).configured());
        assertFalse(service.get(1L).ready());
    }
}
