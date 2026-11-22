package com.vortex.service;

import com.vortex.entity.BrokerAccount;
import com.vortex.repository.BrokerAccountRepository;
import com.vortex.repository.BrokerRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrokerAccountServiceTest {
    final BrokerAccountRepository accounts = mock(BrokerAccountRepository.class);
    final BrokerRepository brokers = mock(BrokerRepository.class);
    final BrokerAccountService service = new BrokerAccountService(accounts, brokers);
    BrokerAccount account(long id, long broker, String identity) {
        var value = new BrokerAccount(); value.setId(id); value.setBrokerId(broker);
        value.setAccountId(identity); value.setAccountName("Synthetic account"); return value;
    }
    @Test void resolvesExplicitAccountAndRejectsAmbiguousOldRequestsOrWrongBroker() {
        var first = account(11, 1, "SYNTH-A"); var second = account(12, 1, "SYNTH-B");
        when(accounts.findById(12L)).thenReturn(Optional.of(second));
        when(accounts.findByBrokerIdOrderByIdAsc(1L)).thenReturn(List.of(first, second));
        assertSame(second, service.resolve(12L, 1L));
        assertThrows(IllegalArgumentException.class, () -> service.resolve(12L, 2L));
        assertThrows(IllegalArgumentException.class, () -> service.resolve(null, 1L));
        when(accounts.findByBrokerIdOrderByIdAsc(1L)).thenReturn(List.of(first));
        assertSame(first, service.resolve(null, 1L));
    }
    @Test void createsRealAccountAndRefusesBlankIdentity() {
        var input = account(1, 1, "SYNTH-A"); input.setId(null);
        when(brokers.existsById(1L)).thenReturn(true);
        when(accounts.saveAndFlush(input)).thenReturn(input);
        assertSame(input, service.create(input));
        input.setAccountId(" ");
        assertThrows(IllegalArgumentException.class, () -> service.create(input));
        verify(accounts, times(1)).saveAndFlush(input);
    }
    @Test void completesMigratedIdentityAndKeepsTheOriginalBroker() {
        var old = account(11, 1, null);
        when(accounts.findById(11L)).thenReturn(Optional.of(old));
        when(brokers.existsById(1L)).thenReturn(true);
        service.update(11L, account(11, 1, "SYNTH-A"));
        assertEquals("SYNTH-A", old.getAccountId());
        assertThrows(IllegalArgumentException.class, () -> service.update(11L, account(11, 1, null)));
        assertThrows(IllegalArgumentException.class, () -> service.update(11L, account(11, 2, "SYNTH-A")));
    }
    @Test void pendingAccountCanBeRenamedAndDisabledWithoutGuessingIdentity() {
        var old = account(11, 1, null);
        when(accounts.findById(11L)).thenReturn(Optional.of(old));
        when(brokers.existsById(1L)).thenReturn(true);
        var input = account(11, 1, ""); input.setAccountName("Pending disabled"); input.setIsActive(false);
        service.update(11L, input);
        assertNull(old.getAccountId());
        assertEquals("Pending disabled", old.getAccountName());
        assertFalse(old.getIsActive());
        verify(accounts, never()).findByBrokerIdAndAccountId(anyLong(), any());
        verify(accounts).saveAndFlush(old);
    }
    @Test void storesOptionalContactDetailsOnTheAccountWithOriginalLengthLimits() {
        var input = account(11, 1, "SYNTH-A"); input.setId(null);
        input.setEmail(" first@example.test "); input.setPhone(" +1 202 555 0101 "); input.setDescription(" Synthetic account note ");
        when(brokers.existsById(1L)).thenReturn(true);
        when(accounts.saveAndFlush(input)).thenReturn(input);
        var saved = service.create(input);
        assertEquals("first@example.test", saved.getEmail());
        assertEquals("+1 202 555 0101", saved.getPhone());
        assertEquals("Synthetic account note", saved.getDescription());
    }
    @Test void oldUpdatesRetainContactDetailsAndExplicitEmptyStringsClearOnlyThisAccount() {
        var first = account(11, 1, "SYNTH-A"); first.setEmail("first@example.test"); first.setPhone("+1 202 555 0101"); first.setDescription("First account");
        var second = account(12, 1, "SYNTH-B"); second.setEmail("second@example.test"); second.setPhone("+1 202 555 0102"); second.setDescription("Second account");
        when(accounts.findById(11L)).thenReturn(Optional.of(first));
        when(accounts.findById(12L)).thenReturn(Optional.of(second));
        when(brokers.existsById(1L)).thenReturn(true);
        service.update(11L, account(11, 1, "SYNTH-A"));
        assertEquals("first@example.test", first.getEmail());
        assertEquals("+1 202 555 0101", first.getPhone());
        assertEquals("First account", first.getDescription());
        var clear = account(11, 1, "SYNTH-A"); clear.setEmail(""); clear.setPhone(" "); clear.setDescription("");
        service.update(11L, clear);
        assertNull(first.getEmail()); assertNull(first.getPhone()); assertNull(first.getDescription());
        assertEquals("second@example.test", service.get(12L).getEmail());
        assertEquals("+1 202 555 0102", second.getPhone()); assertEquals("Second account", second.getDescription());
    }
    @Test void oversizedContactDetailsCannotPartiallyChangeExistingAccounts() {
        var old = account(11, 1, "SYNTH-A"); old.setEmail("first@example.test");
        when(accounts.findById(11L)).thenReturn(Optional.of(old)); when(brokers.existsById(1L)).thenReturn(true);
        var input = account(11, 1, "SYNTH-A"); input.setEmail("x".repeat(101));
        assertThrows(IllegalArgumentException.class, () -> service.update(11L, input));
        input.setEmail("new@example.test"); input.setPhone("1".repeat(31));
        assertThrows(IllegalArgumentException.class, () -> service.update(11L, input));
        input.setPhone("+1 202 555 0101"); input.setDescription("x".repeat(201));
        assertThrows(IllegalArgumentException.class, () -> service.update(11L, input));
        assertEquals("first@example.test", old.getEmail()); verify(accounts, never()).saveAndFlush(any());
    }
}
