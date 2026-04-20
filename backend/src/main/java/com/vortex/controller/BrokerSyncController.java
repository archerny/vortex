package com.vortex.controller;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAsyncExecutor;
import com.vortex.sync.core.BrokerSyncInfo;
import com.vortex.sync.core.BrokerSyncService;
import com.vortex.sync.core.SyncRequest;
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
 * - POST /api/broker-sync/batches/{id}/resume Resume an interrupted/failed/partial batch
 * - GET  /api/broker-sync/brokers            Supported brokers
 * - GET  /api/broker-sync/batches            Batch list (filterable)
 * - GET  /api/broker-sync/batches/{id}       Single batch detail
 */
@RestController
@RequestMapping("/api/broker-sync")
public class BrokerSyncController {

    private static final Logger logger = LoggerFactory.getLogger(BrokerSyncController.class);

    /** Batch statuses that allow resume */
    private static final java.util.Set<String> RESUMABLE_STATUSES =
            java.util.Set.of("INTERRUPTED", "PARTIAL", "FAILED");

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
     * Resume a previously interrupted, partially completed, or failed sync batch.
     *
     * Reconstructs a SyncRequest from the existing batch record and resubmits
     * it to the async executor.  Because staging and import are both idempotent,
     * the full sync flow is safe to re-execute: already-staged orders will be
     * skipped, and already-imported records won't be duplicated.
     *
     * POST /api/broker-sync/batches/{id}/resume
     *
     * @param id the batch ID to resume
     * @return batch info with status=PENDING (re-queued)
     */
    @PostMapping("/batches/{id}/resume")
    public ResponseEntity<Map<String, Object>> resumeSync(@PathVariable Long id) {
        // Step 1: Fetch batch
        var batchOpt = batchService.findById(id);
        if (batchOpt.isEmpty()) {
            return buildErrorResponse(HttpStatus.NOT_FOUND,
                    String.format("Sync batch not found: id=%d", id));
        }

        BrokerSyncBatch batch = batchOpt.get();

        // Step 2: Validate status is resumable
        if (!RESUMABLE_STATUSES.contains(batch.getStatus())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    String.format("Batch %d is in %s status, only INTERRUPTED/PARTIAL/FAILED batches can be resumed",
                            id, batch.getStatus()));
        }

        // Step 3: Validate broker adapter is still available
        if (!brokerSyncService.isSupported(batch.getBrokerCode())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    String.format("Broker adapter '%s' is no longer available", batch.getBrokerCode()));
        }

        // Step 4: Reconstruct SyncRequest from batch
        SyncRequest request = new SyncRequest();
        request.setBrokerCode(batch.getBrokerCode());
        request.setStartTime(batch.getSyncDateFrom().toString());
        request.setEndTime(batch.getSyncDateTo().toString());
        // batchId will be set by asyncExecutor before calling sync

        // Step 5: Reset batch to PENDING for re-execution (save previousPhase for logging)
        String previousPhase = batch.getPhase();
        batch.setStatus("PENDING");
        batch.setPhase(null);
        batch.setErrorMessage(null);
        batch.setCompletedAt(null);
        batchService.save(batch);

        // Step 6: Submit async execution (non-blocking)
        asyncExecutor.execute(batch.getId(), request);
        logger.info("Sync batch resumed: batchId={}, broker={}, previousPhase={}",
                id, batch.getBrokerCode(), previousPhase);

        return buildSuccessResponse("Sync batch resumed", batch);
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
     * 查询同步批次列表
     *
     * GET /api/broker-sync/batches
     * GET /api/broker-sync/batches?brokerCode=ibkr
     * GET /api/broker-sync/batches?status=COMPLETED
     * GET /api/broker-sync/batches?brokerCode=ibkr&status=COMPLETED
     *
     * @param brokerCode optional broker code filter
     * @param status     optional status filter (PENDING, PROCESSING, COMPLETED, PARTIAL, FAILED, INTERRUPTED)
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
