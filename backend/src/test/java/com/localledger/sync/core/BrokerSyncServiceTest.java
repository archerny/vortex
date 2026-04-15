package com.localledger.sync.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link BrokerSyncService}.
 *
 * Covers:
 * - Adapter routing (dispatches to the correct adapter based on brokerName)
 * - Unsupported broker handling (returns failure result)
 * - getSupportedBrokers() listing
 * - Empty adapter list edge case
 */
class BrokerSyncServiceTest {

    private BrokerSyncAdapter ibkrAdapter;
    private BrokerSyncAdapter tigerAdapter;

    @BeforeEach
    void setUp() {
        ibkrAdapter = mock(BrokerSyncAdapter.class);
        when(ibkrAdapter.getBrokerName()).thenReturn("ibkr");

        tigerAdapter = mock(BrokerSyncAdapter.class);
        when(tigerAdapter.getBrokerName()).thenReturn("tiger");
    }

    private BrokerSyncService createService(BrokerSyncAdapter... adapters) {
        return new BrokerSyncService(List.of(adapters));
    }

    @Nested
    @DisplayName("sync() - adapter routing")
    class SyncRoutingTest {

        @Test
        @DisplayName("should route to IBKR adapter when brokerName is 'ibkr'")
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
        @DisplayName("should route to Tiger adapter when brokerName is 'tiger'")
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
            assertEquals("futu", result.getBrokerName());
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
            assertEquals("ibkr", result.getBrokerName());
            assertTrue(result.getMessage().contains("API timeout"));
        }
    }

    @Nested
    @DisplayName("getSupportedBrokers()")
    class GetSupportedBrokersTest {

        @Test
        @DisplayName("should return all registered broker names")
        void shouldReturnAllBrokerNames() {
            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);
            List<String> brokers = service.getSupportedBrokers();

            assertEquals(2, brokers.size());
            assertTrue(brokers.contains("ibkr"));
            assertTrue(brokers.contains("tiger"));
        }

        @Test
        @DisplayName("should return empty list when no adapters registered")
        void shouldReturnEmptyListWhenNoAdapters() {
            BrokerSyncService service = new BrokerSyncService(Collections.emptyList());
            List<String> brokers = service.getSupportedBrokers();

            assertNotNull(brokers);
            assertTrue(brokers.isEmpty());
        }

        @Test
        @DisplayName("returned list should be immutable")
        void returnedListShouldBeImmutable() {
            BrokerSyncService service = createService(ibkrAdapter);
            List<String> brokers = service.getSupportedBrokers();

            assertThrows(UnsupportedOperationException.class, () -> brokers.add("futu"));
        }
    }

    @Nested
    @DisplayName("Constructor - adapter registration")
    class ConstructorTest {

        @Test
        @DisplayName("should register single adapter")
        void shouldRegisterSingleAdapter() {
            BrokerSyncService service = createService(ibkrAdapter);

            assertEquals(1, service.getSupportedBrokers().size());
            assertTrue(service.getSupportedBrokers().contains("ibkr"));
        }

        @Test
        @DisplayName("should register multiple adapters")
        void shouldRegisterMultipleAdapters() {
            BrokerSyncService service = createService(ibkrAdapter, tigerAdapter);

            assertEquals(2, service.getSupportedBrokers().size());
        }
    }
}
