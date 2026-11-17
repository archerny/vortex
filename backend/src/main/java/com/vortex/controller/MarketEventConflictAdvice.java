package com.vortex.controller;

import com.vortex.service.MarketEventConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = {TradeRecordController.class, MarketEventController.class})
public class MarketEventConflictAdvice {
    @ExceptionHandler(MarketEventConflictException.class)
    public ResponseEntity<Map<String, Object>> handle(MarketEventConflictException error) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", "ERROR"); body.put("message", error.getMessage()); body.put("code", error.getCode());
        body.put("blockingEventId", error.getEventId()); body.put("blockingEventType", error.getEventType());
        body.put("eventDate", error.getEventDate() == null ? null : error.getEventDate().toString());
        body.put("symbols", error.getSymbols());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
