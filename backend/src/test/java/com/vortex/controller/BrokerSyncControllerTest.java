package com.vortex.controller;

import com.vortex.entity.BrokerSyncBatch;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAsyncExecutor;
import com.vortex.sync.core.BrokerSyncService;
import com.vortex.sync.core.SyncRequest;
import com.vortex.sync.exception.SyncConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link BrokerSyncController}, focused on the v2 conflict
 * handling. Only the paths that the controller owns (parameter validation,
 * batch creation, exception → HTTP mapping) are covered here; the happy path
 * through the async executor has its own test in
 * {@code BrokerSyncAsyncExecutorTest}.
 */
@ExtendWith(MockitoExtension.class)
class BrokerSyncControllerTest {

    @Mock
    private BrokerSyncService brokerSyncService;

    @Mock
    private BrokerSyncBatchService batchService;

    @Mock
    private BrokerSyncAsyncExecutor asyncExecutor;

    @InjectMocks
    private BrokerSyncController controller;

    private SyncRequest request;

    @BeforeEach
    void setUp() {
        request = new SyncRequest("ibkr");
    }

    @Test
    @DisplayName("triggerSync: rejects missing brokerCode with 400")
    void triggerSyncRejectsMissingBroker() {
        SyncRequest empty = new SyncRequest();
        ResponseEntity<Map<String, Object>> response = controller.triggerSync(empty);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ERROR", response.getBody().get("status"));
        verify(batchService, never()).createBatch(any(), any(), any());
    }

    @Test
    @DisplayName("triggerSync: rejects unsupported broker with 400")
    void triggerSyncRejectsUnsupportedBroker() {
        when(brokerSyncService.isSupported("ibkr")).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = controller.triggerSync(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(batchService, never()).createBatch(any(), any(), any());
    }

    @Test
    @DisplayName("triggerSync: happy path creates batch and submits async execution")
    void triggerSyncHappyPath() {
        when(brokerSyncService.isSupported("ibkr")).thenReturn(true);
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(1L);
        batch.setBrokerCode("ibkr");
        batch.setStatus("PENDING");
        when(batchService.createBatch(eq("ibkr"), any(), any())).thenReturn(batch);

        ResponseEntity<Map<String, Object>> response = controller.triggerSync(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(asyncExecutor).execute(1L, request);
    }

    @Test
    @DisplayName("triggerSync: parses provided date strings into LocalDate")
    void triggerSyncParsesDates() {
        request.setStartTime("2026-01-15");
        request.setEndTime("2026-02-15");
        when(brokerSyncService.isSupported("ibkr")).thenReturn(true);
        BrokerSyncBatch batch = new BrokerSyncBatch();
        batch.setId(2L);
        when(batchService.createBatch(eq("ibkr"),
                eq(LocalDate.of(2026, 1, 15)),
                eq(LocalDate.of(2026, 2, 15)))).thenReturn(batch);

        ResponseEntity<Map<String, Object>> response = controller.triggerSync(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(asyncExecutor).execute(2L, request);
    }

    @Test
    @DisplayName("handleSyncConflict: maps SyncConflictException to 409 with rich body")
    void handleSyncConflictMapsTo409WithBatchId() {
        SyncConflictException ex = new SyncConflictException(
                "Cannot start a new sync: batch 42 is PROCESSING. "
                        + "Wait for it to finish or resolve it before retrying.",
                42L,
                "PROCESSING");

        ResponseEntity<Map<String, Object>> response = controller.handleSyncConflict(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("ERROR", body.get("status"));
        assertEquals(42L, body.get("conflictingBatchId"));
        assertEquals("PROCESSING", body.get("conflictingStatus"));
        assertTrue(body.get("message").toString().contains("42"));
    }

    @Test
    @DisplayName("handleSyncConflict: CLEANUP_FAILED conflict exposes correct status")
    void handleSyncConflictMapsCleanupFailed() {
        SyncConflictException ex = new SyncConflictException(
                "Cannot start a new sync: batch 9 is CLEANUP_FAILED.",
                9L,
                "CLEANUP_FAILED");

        ResponseEntity<Map<String, Object>> response = controller.handleSyncConflict(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("CLEANUP_FAILED", body.get("conflictingStatus"));
        assertEquals(9L, body.get("conflictingBatchId"));
    }

    @Test
    @DisplayName("handleSyncConflict: DB-level fallback (no batch ID) still returns 409")
    void handleSyncConflictFallbackNoBatchId() {
        SyncConflictException ex = new SyncConflictException(
                "Cannot start a new sync: another sync is already active.",
                null,
                null,
                new RuntimeException("unique violation"));

        ResponseEntity<Map<String, Object>> response = controller.handleSyncConflict(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertNull(body.get("conflictingBatchId"));
        assertNull(body.get("conflictingStatus"));
    }

    @Test
    @DisplayName("resumeSync: returns 404 for missing batch")
    void resumeSyncReturns404WhenMissing() {
        when(batchService.findById(999L)).thenReturn(java.util.Optional.empty());

        ResponseEntity<Map<String, Object>> response = controller.resumeSync(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(asyncExecutor, never()).execute(anyLong(), any());
    }
}
