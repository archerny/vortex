package com.vortex.service;

import com.vortex.entity.*;
import com.vortex.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrokerServiceTest {
    final BrokerRepository brokers = mock(BrokerRepository.class);
    final BrokerService service = new BrokerService(brokers);
    Broker broker(Long id, String code) { var b = new Broker("Synthetic", "US"); b.setId(id); b.setBrokerCode(code); return b; }

    @Test void updatesSubmittedSyncCodeAndRejectsDuplicateBinding() {
        var existing = broker(1L, null); when(brokers.findById(1L)).thenReturn(Optional.of(existing));
        service.update(1L, broker(null, "ibkr")); assertEquals("ibkr", existing.getBrokerCode());
        when(brokers.findByBrokerCode("ibkr")).thenReturn(Optional.of(broker(2L, "ibkr")));
        assertThrows(IllegalArgumentException.class, () -> service.update(1L, broker(null, "ibkr")));
    }

    @Test void rejectsSuppliedCreateIdAndAllowsBrokerCodeChanges() {
        assertThrows(IllegalArgumentException.class, () -> service.create(broker(1L, "ibkr")));
        var existing = broker(1L, "ibkr"); when(brokers.findById(1L)).thenReturn(Optional.of(existing));
        service.update(1L, broker(null, null));
        assertNull(existing.getBrokerCode());
        service.delete(1L);
        verify(brokers).deleteById(1L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"futu", "schwab", "firstrade", "csc"})
    void acceptsReferenceCodesForCreateAndUpdateWithoutSyncAdapter(String code) {
        var input = broker(null, " " + code + " ");
        when(brokers.save(any(Broker.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertSame(input, service.create(input));
        assertEquals(code, input.getBrokerCode());

        var existing = broker(1L, null);
        existing.setIntroduction("Original introduction");
        when(brokers.findById(1L)).thenReturn(Optional.of(existing));
        when(brokers.findByBrokerCode(code)).thenReturn(Optional.of(existing));
        assertSame(existing, service.update(1L, broker(null, code)));
        assertEquals(code, existing.getBrokerCode());
        assertEquals("Original introduction", existing.getIntroduction());
    }

    @ParameterizedTest
    @ValueSource(strings = {"futu", "schwab", "firstrade", "csc"})
    void rejectsDuplicateReferenceCodesBeforeSavingOrMutating(String code) {
        var existing = broker(1L, null);
        when(brokers.findById(1L)).thenReturn(Optional.of(existing));
        when(brokers.findByBrokerCode(code)).thenReturn(Optional.of(broker(2L, code)));
        assertThrows(IllegalArgumentException.class, () -> service.create(broker(null, code)));
        assertThrows(IllegalArgumentException.class, () -> service.update(1L, broker(null, code)));
        assertNull(existing.getBrokerCode());
        verify(brokers, never()).save(any());
    }

    @Test void savesIntroductionAndKeepsItSeparateFromAccountNotes() {
        var input = broker(null, "ibkr"); input.setIntroduction(" Synthetic broker introduction ");
        when(brokers.save(input)).thenReturn(input);
        assertEquals("Synthetic broker introduction", service.create(input).getIntroduction());
        input.setIntroduction("x".repeat(501));
        assertThrows(IllegalArgumentException.class, () -> service.create(input));
        verify(brokers, times(1)).save(input);
    }
    @Test void introductionUpdateRetainsOmittedValueClearsEmptyAndRejectsLengthBeforeMutation() {
        var existing = broker(1L, "ibkr"); existing.setIntroduction("Original introduction");
        when(brokers.findById(1L)).thenReturn(Optional.of(existing));
        var input = broker(null, "ibkr"); service.update(1L, input);
        assertEquals("Original introduction", existing.getIntroduction());
        input.setIntroduction("x".repeat(501));
        assertThrows(IllegalArgumentException.class, () -> service.update(1L, input));
        assertEquals("Original introduction", existing.getIntroduction());
        input.setIntroduction(" Updated introduction "); service.update(1L, input);
        assertEquals("Updated introduction", existing.getIntroduction());
        input.setIntroduction(" "); service.update(1L, input);
        assertNull(existing.getIntroduction());
    }
}
