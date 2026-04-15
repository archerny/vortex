package com.localledger.sync.adapter.ibkr;

import com.localledger.sync.core.BrokerSyncAdapter;
import com.localledger.sync.core.SyncRequest;
import com.localledger.sync.core.SyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * IBKR (Interactive Brokers) Sync Adapter
 *
 * Implements BrokerSyncAdapter to sync trade records from IBKR via Flex Web Service.
 *
 * Phase 1 behavior:
 * 1. Validate IBKR configuration (token + queryId)
 * 2. Parse date range from SyncRequest (yyyy-MM-dd → yyyyMMdd)
 * 3. Split date range into ≤365-day windows (IBKR Flex API limit)
 * 4. For each window: call IbkrFlexClient → parse XML → collect Order records
 * 5. Log each Order record for manual verification
 * 6. Return SyncResult with total record count
 *
 * @see IbkrFlexClient HTTP client for IBKR Flex Web Service
 * @see FlexQueryParser XML parser for Flex Query responses
 * @see IbkrOrderRecord Order-level data model
 */
@Component
public class IbkrSyncAdapter implements BrokerSyncAdapter {

    private static final Logger logger = LoggerFactory.getLogger(IbkrSyncAdapter.class);

    /** IBKR Flex API maximum date range per request (days) */
    private static final int MAX_QUERY_DAYS = 365;

    /** Date formatter for SyncRequest (yyyy-MM-dd) */
    private static final DateTimeFormatter REQUEST_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Date formatter for IBKR Flex API (yyyyMMdd) */
    private static final DateTimeFormatter IBKR_DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

    private final IbkrFlexQueryProperties properties;
    private final IbkrFlexClient flexClient;
    private final FlexQueryParser flexQueryParser;

    public IbkrSyncAdapter(IbkrFlexQueryProperties properties,
                           IbkrFlexClient flexClient,
                           FlexQueryParser flexQueryParser) {
        this.properties = properties;
        this.flexClient = flexClient;
        this.flexQueryParser = flexQueryParser;
    }

    @Override
    public String getBrokerName() {
        return "ibkr";
    }

    @Override
    public SyncResult sync(SyncRequest request) {
        long startMs = System.currentTimeMillis();

        // 1. Check configuration
        if (!properties.isConfigured()) {
            logger.error("[IbkrSync] IBKR Flex Query credentials not configured. " +
                    "Please set broker.ibkr.flex-token and broker.ibkr.trade-confirm-query-id " +
                    "in application-local.properties");
            return SyncResult.failure(getBrokerName(),
                    "IBKR Flex Query credentials not configured",
                    System.currentTimeMillis() - startMs);
        }

        try {
            // 2. Resolve date range
            LocalDate endDate = resolveEndDate(request);
            LocalDate startDate = resolveStartDate(request, endDate);
            logger.info("[IbkrSync] Sync date range: {} ~ {}", startDate, endDate);

            // 3. Fetch orders in windows (≤365 days each)
            List<IbkrOrderRecord> allOrders = fetchOrdersInWindows(startDate, endDate);

            // 4. Log records
            logRecords(allOrders);

            long durationMs = System.currentTimeMillis() - startMs;
            return SyncResult.success(getBrokerName(), allOrders.size(), durationMs);

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startMs;
            logger.error("[IbkrSync] Sync failed with exception", e);
            return SyncResult.failure(getBrokerName(), e.getMessage(), durationMs);
        }
    }

    // ============ Date Resolution ============

    private LocalDate resolveStartDate(SyncRequest request, LocalDate endDate) {
        if (request.getStartTime() != null && !request.getStartTime().isBlank()) {
            return LocalDate.parse(request.getStartTime(), REQUEST_DATE_FORMATTER);
        }
        // Default: 90 days back from endDate (reasonable default for incremental sync)
        return endDate.minusDays(90);
    }

    private LocalDate resolveEndDate(SyncRequest request) {
        if (request.getEndTime() != null && !request.getEndTime().isBlank()) {
            return LocalDate.parse(request.getEndTime(), REQUEST_DATE_FORMATTER);
        }
        // Default: today
        return LocalDate.now();
    }

    // ============ Windowed Fetching ============

    /**
     * Split the date range into ≤365-day windows and fetch orders for each window.
     *
     * IBKR Flex API restricts fd/td date range to a maximum of 365 days.
     * For larger ranges (e.g., multi-year historical backfill), automatically
     * split into consecutive windows.
     */
    private List<IbkrOrderRecord> fetchOrdersInWindows(LocalDate startDate, LocalDate endDate) {
        List<IbkrOrderRecord> allOrders = new ArrayList<>();

        LocalDate windowStart = startDate;

        while (windowStart.isBefore(endDate)) {
            LocalDate windowEnd = windowStart.plusDays(MAX_QUERY_DAYS);
            if (windowEnd.isAfter(endDate)) {
                windowEnd = endDate;
            }

            logger.info("[IbkrSync] Fetching window: {} ~ {}", windowStart, windowEnd);
            List<IbkrOrderRecord> windowOrders = fetchOrdersForWindow(windowStart, windowEnd);
            allOrders.addAll(windowOrders);
            logger.info("[IbkrSync] Window {} ~ {} returned {} order records",
                    windowStart, windowEnd, windowOrders.size());

            windowStart = windowEnd;
        }

        return allOrders;
    }

    /**
     * Fetch and parse orders for a single date window.
     */
    private List<IbkrOrderRecord> fetchOrdersForWindow(LocalDate startDate, LocalDate endDate) {
        // Convert yyyy-MM-dd → yyyyMMdd for IBKR Flex API
        String fromDate = startDate.format(IBKR_DATE_FORMATTER);
        String toDate = endDate.format(IBKR_DATE_FORMATTER);

        // Fetch raw XML report
        String xmlReport = flexClient.fetchReport(fromDate, toDate);

        // Parse XML into structured result
        FlexQueryParseResult parseResult = flexQueryParser.parse(xmlReport);

        logger.info("[IbkrSync] Parsed report: account={}, range={} ~ {}, orders={}, tradeConfirms={}",
                parseResult.getAccountId(),
                parseResult.getFromDate(),
                parseResult.getToDate(),
                parseResult.getOrderCount(),
                parseResult.getTradeConfirmCount());

        return parseResult.getOrders();
    }

    // ============ Logging ============

    /**
     * Log each Order record for manual verification against the IBKR platform.
     *
     * Phase 1: log output only, no database persistence.
     * This allows manual cross-checking with IBKR's own trade records
     * before we implement data import in a later phase.
     */
    private void logRecords(List<IbkrOrderRecord> orders) {
        logger.info("[IbkrSync] ====== Sync result: {} order records total ======", orders.size());

        for (int i = 0; i < orders.size(); i++) {
            IbkrOrderRecord order = orders.get(i);
            logger.info("[IbkrSync] [{}] {}", i + 1, order);
        }

        logger.info("[IbkrSync] ====== Log output complete ======");
    }
}
