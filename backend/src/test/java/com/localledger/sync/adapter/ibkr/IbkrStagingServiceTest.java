package com.localledger.sync.adapter.ibkr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IbkrStagingService}.
 *
 * After the Worker extraction refactoring, IbkrStagingService is responsible
 * only for orchestrating the staging loop and counting results.
 *
 * Per-record staging and deduplication logic are tested in
 * {@link IbkrStagingWorkerTest}.
 */
@ExtendWith(MockitoExtension.class)
class IbkrStagingServiceTest {

    @Mock
    private IbkrStagingWorker stagingWorker;

    private IbkrStagingService stagingService;

    @BeforeEach
    void setUp() {
        stagingService = new IbkrStagingService(stagingWorker);
    }

    private IbkrOrderRecord buildOrder(String orderId) {
        IbkrOrderRecord order = new IbkrOrderRecord();
        order.setOrderID(orderId);
        order.setAccountId("U12345");
        order.setCurrency("USD");
        order.setAssetCategory("STK");
        order.setSymbol("AAPL");
        order.setDescription("APPLE INC");
        order.setBuySell("BUY");
        order.setQuantity("100");
        order.setPrice("150.00");
        order.setAmount("15000.00");
        order.setCommission("-1.00");
        order.setTradeDate("20260115");
        return order;
    }

    private IbkrTradeConfirm buildConfirm(String tradeId) {
        IbkrTradeConfirm confirm = new IbkrTradeConfirm();
        confirm.setTradeID(tradeId);
        confirm.setOrderID("ORD001");
        confirm.setAccountId("U12345");
        confirm.setCurrency("USD");
        confirm.setSymbol("AAPL");
        confirm.setBuySell("BUY");
        return confirm;
    }

    @Nested
    @DisplayName("stageAll()")
    class StageAllTest {

        @Test
        @DisplayName("should delegate each order and confirm to stagingWorker")
        void shouldDelegateToWorker() {
            List<IbkrOrderRecord> orders = List.of(buildOrder("ORD001"), buildOrder("ORD002"));
            List<IbkrTradeConfirm> confirms = List.of(buildConfirm("TC001"));

            when(stagingWorker.stageOrder(eq(1L), any(IbkrOrderRecord.class))).thenReturn(true);
            when(stagingWorker.stageTradeConfirm(eq(1L), any(IbkrTradeConfirm.class))).thenReturn(true);

            int result = stagingService.stageAll(1L, orders, confirms);

            assertEquals(2, result);
            verify(stagingWorker, times(2)).stageOrder(eq(1L), any(IbkrOrderRecord.class));
            verify(stagingWorker, times(1)).stageTradeConfirm(eq(1L), any(IbkrTradeConfirm.class));
        }

        @Test
        @DisplayName("should return 0 when all orders are duplicates")
        void shouldReturnZeroWhenAllDuplicates() {
            List<IbkrOrderRecord> orders = List.of(buildOrder("ORD_DUP"));

            when(stagingWorker.stageOrder(eq(1L), any(IbkrOrderRecord.class))).thenReturn(false);

            int result = stagingService.stageAll(1L, orders, Collections.emptyList());

            assertEquals(0, result);
        }

        @Test
        @DisplayName("should handle empty lists")
        void shouldHandleEmptyLists() {
            int result = stagingService.stageAll(1L, Collections.emptyList(), Collections.emptyList());

            assertEquals(0, result);
            verifyNoInteractions(stagingWorker);
        }

        @Test
        @DisplayName("should count only newly staged orders (not confirms)")
        void shouldCountOnlyNewOrders() {
            List<IbkrOrderRecord> orders = List.of(
                    buildOrder("ORD001"), buildOrder("ORD002"), buildOrder("ORD003"));
            List<IbkrTradeConfirm> confirms = List.of(buildConfirm("TC001"));

            // 2 new + 1 duplicate
            when(stagingWorker.stageOrder(1L, orders.get(0))).thenReturn(true);
            when(stagingWorker.stageOrder(1L, orders.get(1))).thenReturn(false);
            when(stagingWorker.stageOrder(1L, orders.get(2))).thenReturn(true);
            when(stagingWorker.stageTradeConfirm(eq(1L), any())).thenReturn(true);

            int result = stagingService.stageAll(1L, orders, confirms);

            assertEquals(2, result); // only 2 newly staged orders
        }
    }
}
