package com.vortex.controller;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAsyncExecutor;
import com.vortex.sync.core.BrokerSyncInfo;
import com.vortex.sync.core.BrokerSyncService;
import com.vortex.sync.core.SyncRequest;
import com.vortex.sync.exception.SyncConflictException;
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
 * - POST /api/broker-sync/trigger      Submit a sync task (async)
 * - GET  /api/broker-sync/brokers      Supported brokers
 * - GET  /api/broker-sync/batches      Batch list (filterable)
 * - GET  /api/broker-sync/batches/{id} Single batch detail
 *
 * <p>v2 state model has no resume endpoint: failed or stuck batches are
 * cleaned up automatically (fail-fast) and the next sync is a fresh
 * batch. See {@code docs/broker-sync/framework/import-consistency.md}.</p>
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
     * @param request sync parameters (brokerCode required; startTime/endTime optional)
     * @return batch info with status=PENDING
     */
    @PostMapping("/trigger")
    public ResponseEntity<Map<String, Object>> triggerSync(@RequestBody SyncRequest request) {
        // Step 1: Parameter validation
        if (request.getBrokerCode() == null || request.getBrokerCode().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "brokerCode is required");
        }

        // Validate that the broker is supported before creating a batch
        if (!brokerSyncService.isSupported(request.getBrokerCode())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    String.format("Unsupported broker: %s", request.getBrokerCode()));
        }

        // Step 2: Parse optional date range
        LocalDate syncDateFrom = parseDate(request.getStartTime());
        LocalDate syncDateTo = parseDate(request.getEndTime());

        // Step 3: Create batch record (PENDING) — committed immediately
        BrokerSyncBatch batch = batchService.createBatch(
                request.getBrokerCode(), syncDateFrom, syncDateTo);

        // Step 4: Submit async execution (non-blocking)
        asyncExecutor.execute(batch.getId(), request);
        logger.info("Sync task submitted: batchId={}, broker={}",
                batch.getId(), request.getBrokerCode());

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
     * List supported brokers with structured info.
     *
     * GET /api/broker-sync/brokers
     *
     * @return list of BrokerSyncInfo (brokerCode, brokerName, country, brokerId)
     */
    @GetMapping("/brokers")
    public ResponseEntity<Map<String, Object>> getSupportedBrokers() {
        List<BrokerSyncInfo> brokers = brokerSyncService.getSupportedBrokerInfos();
        return buildSuccessResponse("Query successful", brokers);
    }

    /**
     * List sync batches, optionally filtered by brokerCode and/or status.
     *
     * GET /api/broker-sync/batches
     * GET /api/broker-sync/batches?brokerCode=ibkr
     * GET /api/broker-sync/batches?status=COMPLETED
     * GET /api/broker-sync/batches?brokerCode=ibkr&status=COMPLETED
     *
     * @param brokerCode optional broker code filter
     * @param status     optional status filter (v2: PENDING / PROCESSING /
     *                   COMPLETED / FAILED / CLEANUP_FAILED)
     * @return list of sync batches
     */
    @GetMapping("/batches")
    public ResponseEntity<Map<String, Object>> listBatches(
            @RequestParam(required = false) String brokerCode,
            @RequestParam(required = false) String status) {
        List<BrokerSyncBatch> batches = batchService.listBatches(brokerCode, status);
        return buildSuccessResponse("Query successful", batches);
    }

    /**
     * Get a single sync batch by ID.
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

    // ============ Response builders ============

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

    /**
     * Map {@link SyncConflictException} to {@code HTTP 409 Conflict}.
     *
     * <p>The response body exposes {@code conflictingBatchId} and
     * {@code conflictingStatus} so the frontend can distinguish between
     * "another sync is still running, please wait" and "a previous batch is
     * in {@code CLEANUP_FAILED}, please resolve it first".</p>
     */
    @ExceptionHandler(SyncConflictException.class)
    public ResponseEntity<Map<String, Object>> handleSyncConflict(SyncConflictException ex) {
        logger.warn("Sync conflict: conflictingBatchId={}, conflictingStatus={}, message={}",
                ex.getConflictingBatchId(), ex.getConflictingStatus(), ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", ex.getMessage());
        response.put("conflictingBatchId", ex.getConflictingBatchId());
        response.put("conflictingStatus", ex.getConflictingStatus());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
