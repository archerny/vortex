package com.vortex.controller;

import com.vortex.entity.BrokerAccount;
import com.vortex.service.BrokerAccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/broker-accounts")
public class BrokerAccountController {
    private final BrokerAccountService service;
    public BrokerAccountController(BrokerAccountService service) { this.service = service; }
    @GetMapping public Map<String, Object> list(@RequestParam(required = false) Long brokerId) { return success(service.list(brokerId)); }
    @GetMapping("/{id}") public Map<String, Object> get(@PathVariable Long id) { return success(service.get(id)); }
    @PostMapping public ResponseEntity<?> create(@RequestBody BrokerAccount input) { return ResponseEntity.status(201).body(success(service.create(input))); }
    @PutMapping("/{id}") public Map<String, Object> update(@PathVariable Long id, @RequestBody BrokerAccount input) { return success(service.update(id, input)); }
    @DeleteMapping("/{id}") public Map<String, Object> delete(@PathVariable Long id) { service.delete(id); return Map.of("status", "SUCCESS"); }
    private Map<String, Object> success(Object data) { return Map.of("status", "SUCCESS", "data", data); }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> invalid(IllegalArgumentException error) { return ResponseEntity.badRequest().body(Map.of("status", "ERROR", "message", error.getMessage())); }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> conflict() { return ResponseEntity.status(409).body(Map.of("status", "ERROR", "message", "账户ID重复，或账户仍有关联账本／同步批次")); }
}
