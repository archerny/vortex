package com.localledger.controller;

import com.localledger.entity.BrokerSyncBatch;
import com.localledger.service.BrokerSyncBatchService;
import com.localledger.sync.core.BrokerSyncAsyncExecutor;
import com.localledger.sync.core.BrokerSyncService;
import com.localledger.sync.core.SyncRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Broker sync controller.
 *
 * Provides REST API for triggering broker trade-record sync and
 * querying sync batch history.
 *
 * Endpoints:
 * - POST /api/broker-sync/trigger            Submit a sync task (async)
 * - GET  /api/broker-sync/brokers            Supported brokers
 * - GET  /api/broker-sync/batches            Batch list (filterable)
 * - GET  /api/broker-sync/batches/{id}       Single batch detail
 */
@RestController
@RequestMapping("/api/broker-sync")
public class BrokerSyncController {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncController.class);

    private final BrokerSyncService brokerSyncService;
    private final BrokerSyncBatchService batchService;
    private final BrokerSyncAsyncExecutor asyncExecutor;

    public BrokerSyncController(BrokerSyncService brokerSyncService,
                                BrokerSyncBatchService batchService,
                                BrokerSyncAsyncExecutor asyncExecutor) {
        this.brokerSyncService = brokerSyncService;
        this.batchService = batchService;
        this.asyncExecutor = asyncExecutor;
    }

    /**
     * Submit a broker sync task.
     *
     * Creates a PENDING batch record and submits the actual sync work
     * to the async executor.  Returns immediately with the batch info
     * so the frontend can close the modal and show the new row.
     *
     * POST /api/broker-sync/trigger
     *
     * @param request sync parameters (brokerName required; startTime/endTime optional)
     * @return batch info with status=PENDING
     */
    @PostMapping("/trigger")
    public ResponseEntity<Map<String, Object>> triggerSync(@RequestBody SyncRequest request) {
        // Step 1: Parameter validation
        if (request.getBrokerName() == null || request.getBrokerName().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "brokerName is required");
        }

        // Validate that the broker is supported before creating a batch
        List<String> supported = brokerSyncService.getSupportedBrokers();
        if (!supported.contains(request.getBrokerName())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    String.format("Unsupported broker: %s, available: %s",
                            request.getBrokerName(), supported));
        }

        // Step 2: Parse optional date range
        LocalDate syncDateFrom = parseDate(request.getStartTime());
        LocalDate syncDateTo = parseDate(request.getEndTime());

        // Step 3: Create batch record (PENDING) — committed immediately
        BrokerSyncBatch batch = batchService.createBatch(
                request.getBrokerName(), syncDateFrom, syncDateTo);

        // Step 4: Submit async execution (non-blocking)
        asyncExecutor.execute(batch.getId(), request);
        logger.info("Sync task submitted: batchId={}, broker={}",
                batch.getId(), request.getBrokerName());

        // Step 5: Return immediately
        return buildSuccessResponse("Sync task submitted", batch);
    }

    /**
     * Parse a date string (yyyy-MM-dd) to LocalDate, return null if blank or invalid.
     */
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            logger.warn("Failed to parse date string: {}", dateStr);
            return null;
        }
    }

    /**
     * 查询支持的券商列表
     *
     * GET /api/broker-sync/brokers
     */
    @GetMapping("/brokers")
    public ResponseEntity<Map<String, Object>> getSupportedBrokers() {
        List<String> brokers = brokerSyncService.getSupportedBrokers();
        return buildSuccessResponse("查询成功", brokers);
    }

    /**
     * 查询同步批次列表
     *
     * GET /api/broker-sync/batches
     * GET /api/broker-sync/batches?brokerName=ibkr
     * GET /api/broker-sync/batches?status=COMPLETED
     * GET /api/broker-sync/batches?brokerName=ibkr&status=COMPLETED
     *
     * @param brokerName optional broker name filter
     * @param status     optional status filter (PENDING, IMPORTING, COMPLETED, FAILED)
     * @return list of sync batches
     */
    @GetMapping("/batches")
    public ResponseEntity<Map<String, Object>> listBatches(
            @RequestParam(required = false) String brokerName,
            @RequestParam(required = false) String status) {
        List<BrokerSyncBatch> batches = batchService.listBatches(brokerName, status);
        return buildSuccessResponse("Query successful", batches);
    }

    /**
     * 查询单个同步批次详情
     *
     * GET /api/broker-sync/batches/{id}
     *
     * @param id batch ID
     * @return batch details or 404
     */
    @GetMapping("/batches/{id}")
    public ResponseEntity<Map<String, Object>> getBatchById(@PathVariable Long id) {
        return batchService.findById(id)
                .map(batch -> buildSuccessResponse("Query successful", batch))
                .orElse(buildErrorResponse(HttpStatus.NOT_FOUND,
                        String.format("Sync batch not found: id=%d", id)));
    }

    // ============ 响应构建工具方法 ============

    private ResponseEntity<Map<String, Object>> buildSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}
