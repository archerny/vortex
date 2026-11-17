package com.vortex.sync.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SyncResult}.
 *
 * Covers:
 * - success() 3-parameter factory method (Phase 1 style)
 * - success() 5-parameter factory method (Phase 2 style with import counts; v2 dropped failedCount)
 * - failure() factory method
 */
class SyncResultTest {

    @Nested
    @DisplayName("success() 3-param factory method (Phase 1)")
    class SuccessFactory3ParamTest {

        @Test
        @DisplayName("should create a success result with correct fields")
        void shouldCreateSuccessResult() {
            SyncResult result = SyncResult.success("ibkr", 150, 2500);

            assertTrue(result.isSuccess());
            assertEquals("ibkr", result.getBrokerCode());
            assertEquals(150, result.getTotalRecords());
            assertEquals(2500, result.getDurationMs());
            // importedCount/skippedCount default to 0
            assertEquals(0, result.getImportedCount());
            assertEquals(0, result.getSkippedCount());
            assertNotNull(result.getMessage());
            assertTrue(result.getMessage().contains("ibkr"));
            assertTrue(result.getMessage().contains("150"));
            assertTrue(result.getMessage().startsWith("Sync completed"));
        }

        @Test
        @DisplayName("should create success result with zero records")
        void shouldHandleZeroRecords() {
            SyncResult result = SyncResult.success("tiger", 0, 100);

            assertTrue(result.isSuccess());
            assertEquals("tiger", result.getBrokerCode());
            assertEquals(0, result.getTotalRecords());
            assertEquals(100, result.getDurationMs());
        }

    }

    @Nested
    @DisplayName("success() 5-param factory method (Phase 2 / v2)")
    class SuccessFactory5ParamTest {

        @Test
        @DisplayName("should create a success result with import counts")
        void shouldCreateSuccessResultWithCounts() {
            SyncResult result = SyncResult.success("ibkr", 100, 85, 15, 3000);

            assertTrue(result.isSuccess());
            assertEquals("ibkr", result.getBrokerCode());
            assertEquals(100, result.getTotalRecords());
            assertEquals(85, result.getImportedCount());
            assertEquals(15, result.getSkippedCount());
            assertEquals(3000, result.getDurationMs());
            assertTrue(result.getMessage().contains("imported=85"));
            assertTrue(result.getMessage().contains("skipped=15"));
        }

        @Test
        @DisplayName("should handle all-skipped scenario")
        void shouldHandleAllSkipped() {
            SyncResult result = SyncResult.success("ibkr", 50, 0, 50, 1000);

            assertTrue(result.isSuccess());
            assertEquals(0, result.getImportedCount());
            assertEquals(50, result.getSkippedCount());
        }
    }

    @Nested
    @DisplayName("failure() factory method")
    class FailureFactoryTest {

        @Test
        @DisplayName("should create a failure result with correct fields")
        void shouldCreateFailureResult() {
            SyncResult result = SyncResult.failure("ibkr", "Connection timeout", 5000);

            assertFalse(result.isSuccess());
            assertEquals("ibkr", result.getBrokerCode());
            assertEquals(0, result.getTotalRecords());
            assertEquals(5000, result.getDurationMs());
            assertNotNull(result.getMessage());
            assertTrue(result.getMessage().contains("ibkr"));
            assertTrue(result.getMessage().contains("Connection timeout"));
            assertTrue(result.getMessage().contains("5000"));
            assertTrue(result.getMessage().startsWith("Sync failed"));
        }

        @Test
        @DisplayName("should handle null-like error messages gracefully")
        void shouldHandleEmptyErrorMessage() {
            SyncResult result = SyncResult.failure("futu", "", 0);

            assertFalse(result.isSuccess());
            assertEquals("futu", result.getBrokerCode());
            assertEquals(0, result.getTotalRecords());
        }

    }
}
