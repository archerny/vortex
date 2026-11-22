package com.vortex.service;

import com.vortex.entity.*;
import com.vortex.repository.*;
import org.junit.jupiter.api.Test;
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
