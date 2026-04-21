package com.vortex.controller;

import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.IbkrStagedTradeConfirm;
import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.repository.IbkrStagedTradeConfirmRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IBKR staged data query controller.
 *
 * Exposes read-only access to the IBKR staging tables (ibkr_staged_orders and
 * ibkr_staged_trade_confirms) so the frontend can inspect the raw synced
 * records for auditing and troubleshooting.
 *
 * Endpoints:
 * - GET /api/sync/ibkr/orders          List staged orders (optionally filtered by batchId)
 * - GET /api/sync/ibkr/trade-confirms  List staged trade confirms (optionally filtered by batchId)
 *
 * Results are sorted by tradeDate descending (then id descending as tiebreaker)
 * to surface recent activity first. Consistent with other controllers in this
 * project, results are returned as plain lists wrapped in the standard
 * {status, message, data} envelope; the frontend performs pagination on the
 * client side.
 */
@RestController
@RequestMapping("/api/sync/ibkr")
public class IbkrStagedDataController {

    /** Default sort: most recent trade first, with id as deterministic tiebreaker. */
    private static final Sort DEFAULT_SORT = Sort.by(
            Sort.Order.desc("tradeDate"),
            Sort.Order.desc("id"));

    /** In-memory comparator equivalent to DEFAULT_SORT, used when filtering by batchId via named query. */
    private static final Comparator<IbkrStagedOrder> ORDER_COMPARATOR =
            Comparator.comparing(IbkrStagedOrder::getTradeDate,
                            Comparator.nullsLast(Comparator.reverseOrder()))
                    .thenComparing(IbkrStagedOrder::getId,
                            Comparator.nullsLast(Comparator.reverseOrder()));

    private static final Comparator<IbkrStagedTradeConfirm> CONFIRM_COMPARATOR =
            Comparator.comparing(IbkrStagedTradeConfirm::getTradeDate,
                            Comparator.nullsLast(Comparator.reverseOrder()))
                    .thenComparing(IbkrStagedTradeConfirm::getId,
                            Comparator.nullsLast(Comparator.reverseOrder()));

    private final IbkrStagedOrderRepository orderRepository;
    private final IbkrStagedTradeConfirmRepository tradeConfirmRepository;

    public IbkrStagedDataController(IbkrStagedOrderRepository orderRepository,
                                    IbkrStagedTradeConfirmRepository tradeConfirmRepository) {
        this.orderRepository = orderRepository;
        this.tradeConfirmRepository = tradeConfirmRepository;
    }

    /**
     * List IBKR staged orders.
     *
     * GET /api/sync/ibkr/orders
     * GET /api/sync/ibkr/orders?batchId=123
     *
     * @param batchId optional batch ID filter
     * @return list of staged orders sorted by tradeDate descending
     */
    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> listOrders(
            @RequestParam(required = false) Long batchId) {
        List<IbkrStagedOrder> orders;
        if (batchId != null) {
            orders = orderRepository.findByBatchId(batchId).stream()
                    .sorted(ORDER_COMPARATOR)
                    .toList();
        } else {
            orders = orderRepository.findAll(DEFAULT_SORT);
        }
        return buildSuccessResponse("Query successful", orders);
    }

    /**
     * List IBKR staged trade confirms.
     *
     * GET /api/sync/ibkr/trade-confirms
     * GET /api/sync/ibkr/trade-confirms?batchId=123
     *
     * @param batchId optional batch ID filter
     * @return list of staged trade confirms sorted by tradeDate descending
     */
    @GetMapping("/trade-confirms")
    public ResponseEntity<Map<String, Object>> listTradeConfirms(
            @RequestParam(required = false) Long batchId) {
        List<IbkrStagedTradeConfirm> confirms;
        if (batchId != null) {
            confirms = tradeConfirmRepository.findByBatchId(batchId).stream()
                    .sorted(CONFIRM_COMPARATOR)
                    .toList();
        } else {
            confirms = tradeConfirmRepository.findAll(DEFAULT_SORT);
        }
        return buildSuccessResponse("Query successful", confirms);
    }

    // ============ Response builders ============

    private ResponseEntity<Map<String, Object>> buildSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
}
