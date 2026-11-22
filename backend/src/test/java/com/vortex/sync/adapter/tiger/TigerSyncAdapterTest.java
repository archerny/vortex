package com.vortex.sync.adapter.tiger;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.trade.QueryOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.BatchOrderResponse;
import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.SyncRequest;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TigerSyncAdapterTest {
    @Test void selectedAccountCredentialsAreUsedAndForeignOrdersNeverReachStaging() throws Exception {
        var properties = new TigerApiProperties();
        var selected = new TigerApiProperties.Credentials("200", "synthetic-key-B", "SYNTH-ACCOUNT-B");
        properties.useAccountCredentials(key -> key == 2L ? selected : new TigerApiProperties.Credentials("100", "synthetic-key-A", "SYNTH-ACCOUNT-A"));
        var staging = mock(TigerStagingService.class);
        var importing = mock(TigerImportService.class);
        var adapter = spy(new TigerSyncAdapter(properties, staging, importing, mock(BrokerSyncBatchService.class), mock(TigerStagedOrderRepository.class)));
        var client = mock(TigerHttpClient.class); doReturn(client).when(adapter).createClient(selected);
        var response = mock(BatchOrderResponse.class);
        var item = mock(com.tigerbrokers.stock.openapi.client.https.domain.trade.item.BatchOrderItem.class);
        var order = mock(com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrder.class);
        when(order.getAccount()).thenReturn("SYNTH-FOREIGN");
        when(item.getOrders()).thenReturn(java.util.List.of(order));
        when(response.isSuccess()).thenReturn(true); when(response.getItem()).thenReturn(item);
        when(client.execute(any(QueryOrderRequest.class))).thenAnswer(call -> {
            assertTrue(((QueryOrderRequest) call.getArgument(0)).getBizContent().contains("SYNTH-ACCOUNT-B")); return response;
        });
        var request = com.vortex.fixtures.AccountFixtures.request("SYNTH-ACCOUNT-B", "tiger", "2026-01-01", "2026-01-02");
        request.setBrokerAccountId(2L); request.setBatchId(1L);
        var result = adapter.sync(request);
        assertFalse(result.isSuccess()); assertTrue(result.getMessage().contains("does not match"));
        verifyNoInteractions(staging, importing);
        verify(adapter).createClient(selected);
    }

    @Test void allWindowsUseTheCredentialSnapshotCapturedAtBatchStart() throws Exception {
        var properties = new TigerApiProperties();
        var first = new TigerApiProperties.Credentials("100", "synthetic-key-A", "SYNTH-ACCOUNT-A");
        var current = new AtomicReference<>(first);
        properties.useDatabaseCredentials(current::get);
        var adapter = spy(new TigerSyncAdapter(properties, mock(TigerStagingService.class), mock(TigerImportService.class),
                mock(BrokerSyncBatchService.class), mock(TigerStagedOrderRepository.class)));
        var client = mock(TigerHttpClient.class);
        doReturn(client).when(adapter).createClient(first);
        var response = mock(BatchOrderResponse.class);
        when(response.isSuccess()).thenReturn(true);
        when(client.execute(any(QueryOrderRequest.class))).thenAnswer(call -> {
            var request = (QueryOrderRequest) call.getArgument(0);
            assertTrue(request.getBizContent().contains("SYNTH-ACCOUNT-A"));
            current.set(new TigerApiProperties.Credentials("200", "synthetic-key-B", "SYNTH-ACCOUNT-B"));
            return response;
        });
        var request = com.vortex.fixtures.AccountFixtures.request("SYNTH-ACCOUNT-A", "tiger");
        request.setBatchId(1L); request.setStartTime("2026-01-01"); request.setEndTime("2026-07-03");
        assertTrue(adapter.sync(request).isSuccess());
        verify(client, times(3)).execute(any(QueryOrderRequest.class));
        verify(adapter).createClient(first);
        assertEquals("SYNTH-ACCOUNT-B", properties.credentials().account());
    }
}
