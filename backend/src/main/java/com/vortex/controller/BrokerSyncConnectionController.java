package com.vortex.controller;

import com.vortex.dto.BrokerSyncConnectionUpdate;
import com.vortex.service.BrokerSyncConnectionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/broker-sync/connections")
public class BrokerSyncConnectionController {
    private final BrokerSyncConnectionService service;
    public BrokerSyncConnectionController(BrokerSyncConnectionService service) { this.service = service; }
    @GetMapping public Map<String, Object> list() { return success(service.list()); }
    @GetMapping("/{brokerAccountId}") public Map<String, Object> get(@PathVariable Long brokerAccountId) { return success(service.get(brokerAccountId)); }
    @PutMapping("/{brokerAccountId}") public Map<String, Object> save(@PathVariable Long brokerAccountId, @RequestBody BrokerSyncConnectionUpdate input) {
        return success(service.save(brokerAccountId, input));
    }
    @DeleteMapping("/{brokerAccountId}") public Map<String, Object> clear(@PathVariable Long brokerAccountId) { return success(service.clear(brokerAccountId)); }
    private Map<String, Object> success(Object data) { return Map.of("status", "SUCCESS", "data", data); }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> invalid(IllegalArgumentException error) {
        return ResponseEntity.badRequest().body(Map.of("status", "ERROR", "message", error.getMessage()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> conflict() {
        return ResponseEntity.status(409).body(Map.of("status", "ERROR", "message", "账户或连接已变更，请刷新后重试"));
    }
}
