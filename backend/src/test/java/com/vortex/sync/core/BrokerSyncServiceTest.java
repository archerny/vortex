package com.vortex.sync.core;

import com.vortex.entity.Broker;
import com.vortex.repository.BrokerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link BrokerSyncService}.
 *
 * Covers:
 * - Adapter routing (dispatches to the correct adapter based on brokerCode)
 * - Unsupported broker handling (returns failure result)
 * - getSupportedBrokerInfos() with and without matching broker records
 * - isSupported() check
 * - Empty adapter list edge case
 */
class BrokerSyncServiceTest {

    private BrokerSyncAdapter ibkrAdapter;
    private BrokerSyncAdapter tigerAdapter;
    private BrokerRepository brokerRepository;

    @BeforeEach
    void setUp() {
        ibkrAdapter = mock(BrokerSyncAdapter.class);
        when(ibkrAdapter.getBrokerCode()).thenReturn("ibkr");

        tigerAdapter = mock(BrokerSyncAdapter.class);
        when(tigerAdapter.getBrokerCode()).thenReturn("tiger");

        brokerRepository = mock(BrokerRepository.class);
    }

    private BrokerSyncService createService(BrokerSyncAdapter... adapters) {
        return new BrokerSyncService(List.of(adapters), brokerRepository);
    }

    @Nested
    @DisplayName("sync() - adapter routing")
    class SyncRoutingTest {

        @Test
        @DisplayName("should route to IBKR adapter when brokerCode is 'ibkr'")
        void shouldRouteToIbkrAdapter() {
            SyncResult expectedResult = SyncResult.success("ibkr", 100, 1000);
            SyncRequest request = new SyncRequest("ibkr");
            when(ibkrAdapter.sync(request)).thenReturn(expectedResult);

            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);
            SyncResult result = service.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(100, result.getTotalRecords());
            verify(ibkrAdapter).sync(request);
            verify(tigerAdapter, never()).sync(any());
        }

        @Test
        @DisplayName("should route to Tiger adapter when brokerCode is 'tiger'")
        void shouldRouteToTigerAdapter() {
            SyncResult expectedResult = SyncResult.success("tiger", 50, 800);
            SyncRequest request = new SyncRequest("tiger");
            when(tigerAdapter.sync(request)).thenReturn(expectedResult);

            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);
            SyncResult result = service.sync(request);

            assertTrue(result.isSuccess());
            assertEquals(50, result.getTotalRecords());
            verify(tigerAdapter).sync(request);
            verify(ibkrAdapter, never()).sync(any());
        }

        @Test
        @DisplayName("should return failure for unsupported broker")
        void shouldReturnFailureForUnsupportedBroker() {
            SyncRequest request = new SyncRequest("futu");

            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);
            SyncResult result = service.sync(request);

            assertFalse(result.isSuccess());
            assertEquals("futu", result.getBrokerCode());
            assertTrue(result.getMessage().contains("Unsupported broker"));
            assertTrue(result.getMessage().contains("futu"));
            verify(ibkrAdapter, never()).sync(any());
            verify(tigerAdapter, never()).sync(any());
        }

        @Test
        @DisplayName("should propagate adapter failure result")
        void shouldPropagateAdapterFailure() {
            SyncResult failResult = SyncResult.failure("ibkr", "API timeout", 3000);
            SyncRequest request = new SyncRequest("ibkr");
            when(ibkrAdapter.sync(request)).thenReturn(failResult);

            BrokerSyncService service = createService(ibkrAdapter);
            SyncResult result = service.sync(request);

            assertFalse(result.isSuccess());
            assertEquals("ibkr", result.getBrokerCode());
            assertTrue(result.getMessage().contains("API timeout"));
        }
    }

    @Nested
    @DisplayName("getSupportedBrokerInfos()")
    class GetSupportedBrokerInfosTest {

        @Test
        @DisplayName("should return info for adapters with matching broker records")
        void shouldReturnInfoForMatchingBrokers() {
            Broker ibkrBroker = new Broker("盈透证券", "US");
            ibkrBroker.setId(1L);
            ibkrBroker.setBrokerCode("ibkr");
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(ibkrBroker));

            Broker tigerBroker = new Broker("老虎证券", "US");
            tigerBroker.setId(2L);
            tigerBroker.setBrokerCode("tiger");
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.of(tigerBroker));

            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);
            List<BrokerSyncInfo> infos = service.getSupportedBrokerInfos();

            assertEquals(2, infos.size());
        }

        @Test
        @DisplayName("should skip adapters without matching broker records")
        void shouldSkipAdaptersWithoutBrokerRecord() {
            Broker ibkrBroker = new Broker("盈透证券", "US");
            ibkrBroker.setId(1L);
            ibkrBroker.setBrokerCode("ibkr");
            when(brokerRepository.findByBrokerCode("ibkr")).thenReturn(Optional.of(ibkrBroker));
            when(brokerRepository.findByBrokerCode("tiger")).thenReturn(Optional.empty());

            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);
            List<BrokerSyncInfo> infos = service.getSupportedBrokerInfos();

            assertEquals(1, infos.size());
            assertEquals("ibkr", infos.get(0).getBrokerCode());
        }

        @Test
        @DisplayName("should return empty list when no adapters registered")
        void shouldReturnEmptyListWhenNoAdapters() {
            BrokerSyncService service = new BrokerSyncService(Collections.emptyList(), brokerRepository);
            List<BrokerSyncInfo> infos = service.getSupportedBrokerInfos();

            assertNotNull(infos);
            assertTrue(infos.isEmpty());
        }
    }

    @Nested
    @DisplayName("isSupported()")
    class IsSupportedTest {

        @Test
        @DisplayName("should return true for registered broker")
        void shouldReturnTrueForRegistered() {
            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);
            assertTrue(service.isSupported("ibkr"));
            assertTrue(service.isSupported("tiger"));
        }

        @Test
        @DisplayName("should return false for unregistered broker")
        void shouldReturnFalseForUnregistered() {
            BrokerSyncService service = createService(ibkrAdapter);
            assertFalse(service.isSupported("futu"));
        }
    }

    @Nested
    @DisplayName("getAdapter()")
    class GetAdapterTest {

        @Test
        @DisplayName("should return adapter for registered broker")
        void shouldReturnAdapter() {
            BrokerSyncService service = createService(ibkrAdapter);
            assertNotNull(service.getAdapter("ibkr"));
        }

        @Test
        @DisplayName("should return null for unregistered broker")
        void shouldReturnNullForUnregistered() {
            BrokerSyncService service = createService(ibkrAdapter);
            assertNull(service.getAdapter("futu"));
        }
    }
}
