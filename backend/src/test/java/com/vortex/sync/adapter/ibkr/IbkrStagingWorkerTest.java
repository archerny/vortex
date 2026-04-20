package com.vortex.sync.adapter.ibkr;

import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.IbkrStagedTradeConfirm;
import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link IbkrStagingWorker}.
 *
 * Tests the per-record transactional staging logic that was extracted
 * from IbkrStagingService to fix the Spring AOP self-invocation issue.
 *
 * Covers:
 * - stageOrder: field mapping, new record, duplicate record
 * - stageTradeConfirm: new record, duplicate record
 */
@ExtendWith(MockitoExtension.class)
class IbkrStagingWorkerTest {

    @Mock
    private IbkrStagedOrderRepository stagedOrderRepository;

    @Mock
    private IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository;

    private IbkrStagingWorker stagingWorker;

    @BeforeEach
    void setUp() {
        stagingWorker = new IbkrStagingWorker(stagedOrderRepository, stagedTradeConfirmRepository);
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

    // ========================================================
    // stageOrder — field mapping
    // ========================================================
    @Nested
    @DisplayName("stageOrder() - field mapping")
    class StageOrderMappingTest {

        @Test
        @DisplayName("should map all fields correctly from IbkrOrderRecord to IbkrStagedOrder")
        void shouldMapFieldsCorrectly() {
            IbkrOrderRecord order = buildOrder("ORD_MAP");
            when(stagedOrderRepository.existsByOrderId("ORD_MAP")).thenReturn(false);

            stagingWorker.stageOrder(1L, order);

            ArgumentCaptor<IbkrStagedOrder> captor = ArgumentCaptor.forClass(IbkrStagedOrder.class);
            verify(stagedOrderRepository).save(captor.capture());

            IbkrStagedOrder staged = captor.getValue();
            assertEquals(1L, staged.getBatchId());
            assertEquals("PENDING", staged.getStatus());
            assertEquals("ORD_MAP", staged.getOrderId());
            assertEquals("U12345", staged.getAccountId());
            assertEquals("USD", staged.getCurrency());
            assertEquals("STK", staged.getAssetCategory());
            assertEquals("AAPL", staged.getSymbol());
            assertEquals("BUY", staged.getBuySell());
            assertEquals("100", staged.getQuantity());
            assertEquals("150.00", staged.getPrice());
        }
    }

    // ========================================================
    // stageOrder — deduplication
    // ========================================================
    @Nested
    @DisplayName("stageOrder() - deduplication")
    class StageOrderDeduplicationTest {

        @Test
        @DisplayName("should skip existing order and return false")
        void shouldSkipExistingOrder() {
            when(stagedOrderRepository.existsByOrderId("ORD_EXISTS")).thenReturn(true);

            boolean result = stagingWorker.stageOrder(1L, buildOrder("ORD_EXISTS"));

            assertFalse(result);
            verify(stagedOrderRepository, never()).save(any());
        }

        @Test
        @DisplayName("should save new order and return true")
        void shouldSaveNewOrder() {
            when(stagedOrderRepository.existsByOrderId("ORD_NEW")).thenReturn(false);

            boolean result = stagingWorker.stageOrder(1L, buildOrder("ORD_NEW"));

            assertTrue(result);
            verify(stagedOrderRepository).save(any(IbkrStagedOrder.class));
        }
    }

    // ========================================================
    // stageTradeConfirm — deduplication
    // ========================================================
    @Nested
    @DisplayName("stageTradeConfirm() - deduplication")
    class StageTradeConfirmDeduplicationTest {

        @Test
        @DisplayName("should skip existing trade confirm and return false")
        void shouldSkipExistingConfirm() {
            when(stagedTradeConfirmRepository.existsByTradeId("TC_EXISTS")).thenReturn(true);

            boolean result = stagingWorker.stageTradeConfirm(1L, buildConfirm("TC_EXISTS"));

            assertFalse(result);
            verify(stagedTradeConfirmRepository, never()).save(any());
        }

        @Test
        @DisplayName("should save new trade confirm and return true")
        void shouldSaveNewConfirm() {
            when(stagedTradeConfirmRepository.existsByTradeId("TC_NEW")).thenReturn(false);

            boolean result = stagingWorker.stageTradeConfirm(1L, buildConfirm("TC_NEW"));

            assertTrue(result);
            verify(stagedTradeConfirmRepository).save(any(IbkrStagedTradeConfirm.class));
        }
    }
}
