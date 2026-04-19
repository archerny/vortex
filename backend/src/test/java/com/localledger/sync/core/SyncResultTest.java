package com.localledger.sync.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SyncResult}.
 *
 * Covers:
 * - success() 3-parameter factory method (Phase 1 style)
 * - success() 6-parameter factory method (Phase 2 style with import counts)
 * - failure() factory method
 * - Field getters/setters
 * - toString() output
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
            // importedCount/skippedCount/failedCount default to 0
            assertEquals(0, result.getImportedCount());
            assertEquals(0, result.getSkippedCount());
            assertEquals(0, result.getFailedCount());
            assertNotNull(result.getMessage());
            assertTrue(result.getMessage().contains("ibkr"));
            assertTrue(result.getMessage().contains("150"));
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

        @Test
        @DisplayName("message should be in English")
        void messageShouldBeEnglish() {
            SyncResult result = SyncResult.success("ibkr", 50, 1000);

            assertTrue(result.getMessage().contains("Sync completed"),
                    "Message should start with English text");
            assertFalse(result.getMessage().matches(".*[\\u4e00-\\u9fa5].*"),
                    "Message should not contain Chinese characters");
        }
    }

    @Nested
    @DisplayName("success() 6-param factory method (Phase 2)")
    class SuccessFactory6ParamTest {

        @Test
        @DisplayName("should create a success result with import counts")
        void shouldCreateSuccessResultWithCounts() {
            SyncResult result = SyncResult.success("ibkr", 100, 85, 10, 5, 3000);

            assertTrue(result.isSuccess());
            assertEquals("ibkr", result.getBrokerCode());
            assertEquals(100, result.getTotalRecords());
            assertEquals(85, result.getImportedCount());
            assertEquals(10, result.getSkippedCount());
            assertEquals(5, result.getFailedCount());
            assertEquals(3000, result.getDurationMs());
            assertTrue(result.getMessage().contains("imported=85"));
            assertTrue(result.getMessage().contains("skipped=10"));
            assertTrue(result.getMessage().contains("failed=5"));
        }

        @Test
        @DisplayName("should handle all-skipped scenario")
        void shouldHandleAllSkipped() {
            SyncResult result = SyncResult.success("ibkr", 50, 0, 50, 0, 1000);

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
        }

        @Test
        @DisplayName("should handle null-like error messages gracefully")
        void shouldHandleEmptyErrorMessage() {
            SyncResult result = SyncResult.failure("futu", "", 0);

            assertFalse(result.isSuccess());
            assertEquals("futu", result.getBrokerCode());
            assertEquals(0, result.getTotalRecords());
        }

        @Test
        @DisplayName("message should be in English")
        void messageShouldBeEnglish() {
            SyncResult result = SyncResult.failure("ibkr", "API error", 500);

            assertTrue(result.getMessage().contains("Sync failed"),
                    "Message should start with English text");
            assertFalse(result.getMessage().matches(".*[\\u4e00-\\u9fa5].*"),
                    "Message should not contain Chinese characters");
        }
    }

    @Nested
    @DisplayName("Getters and setters")
    class GetterSetterTest {

        @Test
        @DisplayName("default constructor should create empty result")
        void defaultConstructor() {
            SyncResult result = new SyncResult();

            assertFalse(result.isSuccess());
            assertNull(result.getBrokerCode());
            assertEquals(0, result.getTotalRecords());
            assertEquals(0, result.getImportedCount());
            assertEquals(0, result.getSkippedCount());
            assertEquals(0, result.getFailedCount());
            assertNull(result.getMessage());
            assertEquals(0, result.getDurationMs());
        }

        @Test
        @DisplayName("setters should update fields correctly")
        void settersShouldWork() {
            SyncResult result = new SyncResult();
            result.setSuccess(true);
            result.setBrokerCode("tiger");
            result.setTotalRecords(42);
            result.setImportedCount(30);
            result.setSkippedCount(10);
            result.setFailedCount(2);
            result.setMessage("custom message");
            result.setDurationMs(9999);

            assertTrue(result.isSuccess());
            assertEquals("tiger", result.getBrokerCode());
            assertEquals(42, result.getTotalRecords());
            assertEquals(30, result.getImportedCount());
            assertEquals(10, result.getSkippedCount());
            assertEquals(2, result.getFailedCount());
            assertEquals("custom message", result.getMessage());
            assertEquals(9999, result.getDurationMs());
        }
    }

    @Nested
    @DisplayName("toString()")
    class ToStringTest {

        @Test
        @DisplayName("should contain key fields")
        void shouldContainKeyFields() {
            SyncResult result = SyncResult.success("ibkr", 10, 500);
            String str = result.toString();

            assertTrue(str.contains("success=true"));
            assertTrue(str.contains("ibkr"));
            assertTrue(str.contains("totalRecords=10"));
            assertTrue(str.contains("durationMs=500"));
            assertTrue(str.contains("importedCount="));
            assertTrue(str.contains("skippedCount="));
            assertTrue(str.contains("failedCount="));
        }
    }
}
