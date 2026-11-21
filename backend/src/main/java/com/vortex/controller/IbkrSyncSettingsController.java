package com.vortex.controller;

import com.vortex.dto.IbkrSyncSettingsUpdate;
import com.vortex.sync.adapter.ibkr.IbkrSyncSettingsService;
import com.vortex.sync.exception.SyncConflictException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/broker-sync/ibkr/settings")
public class IbkrSyncSettingsController {
    private final IbkrSyncSettingsService service;
    public IbkrSyncSettingsController(IbkrSyncSettingsService service) { this.service = service; }
    @GetMapping public Map<String, Object> get() { return success(service.get()); }
    @PutMapping public Map<String, Object> save(@RequestBody IbkrSyncSettingsUpdate input) { return success(service.save(input)); }
    @DeleteMapping public Map<String, Object> clear() { return success(service.clear()); }
    private Map<String, Object> success(Object data) { return Map.of("status", "SUCCESS", "data", data); }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> invalid(IllegalArgumentException error) { return ResponseEntity.badRequest().body(Map.of("status", "ERROR", "message", error.getMessage())); }
    @ExceptionHandler(SyncConflictException.class)
    ResponseEntity<?> active(SyncConflictException error) { return ResponseEntity.status(409).body(Map.of("status", "ERROR", "message", error.getMessage())); }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> conflict() { return ResponseEntity.status(409).body(Map.of("status", "ERROR", "message", "配置保存冲突，请刷新后重试")); }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?> unavailable() { return ResponseEntity.status(500).body(Map.of("status", "ERROR", "message", "无法安全读写IBKR凭证，请检查后端凭证密钥")); }
}
