package com.localledger.controller;

import com.localledger.sync.core.BrokerSyncService;
import com.localledger.sync.core.SyncRequest;
import com.localledger.sync.core.SyncResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 券商同步控制器
 *
 * 提供手动触发券商交易记录同步的 REST API。
 * Phase 1 不做前端，通过 curl / Postman 等工具直接调用。
 *
 * 接口列表：
 * - POST /api/broker-sync/trigger        触发同步
 * - GET  /api/broker-sync/brokers        查询支持的券商列表
 */
@RestController
@RequestMapping("/api/broker-sync")
public class BrokerSyncController {

    private final BrokerSyncService brokerSyncService;

    public BrokerSyncController(BrokerSyncService brokerSyncService) {
        this.brokerSyncService = brokerSyncService;
    }

    /**
     * 触发券商同步
     *
     * POST /api/broker-sync/trigger
     *
     * 请求体示例（最简）：
     * { "brokerName": "tiger" }
     *
     * 请求体示例（带时间范围）：
     * { "brokerName": "tiger", "startTime": "2025-01-01", "endTime": "2025-03-31" }
     *
     * @param request 同步请求参数
     * @return 同步结果
     */
    @PostMapping("/trigger")
    public ResponseEntity<Map<String, Object>> triggerSync(@RequestBody SyncRequest request) {
        // 参数校验
        if (request.getBrokerName() == null || request.getBrokerName().isBlank()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "brokerName 不能为空");
        }

        SyncResult result = brokerSyncService.sync(request);

        if (result.isSuccess()) {
            return buildSuccessResponse("同步完成", result);
        } else {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, result.getMessage());
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
