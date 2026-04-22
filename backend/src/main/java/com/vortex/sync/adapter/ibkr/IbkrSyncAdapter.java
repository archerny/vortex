package com.vortex.sync.adapter.ibkr;

import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAdapter;
import com.vortex.sync.core.SyncRequest;
import com.vortex.sync.core.SyncResult;
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
 * Phase 2 behavior:
 * 1. Validate IBKR configuration (token + queryId)
 * 2. Parse date range from SyncRequest (yyyy-MM-dd → yyyyMMdd)
 * 3. Split date range into ≤365-day windows (IBKR Flex API limit)
 * 4. For each window: call IbkrFlexClient → parse XML → collect records
 * 5. Stage orders + trade confirms into ibkr_staged_* tables (phase=STAGING)
 * 6. Import staged orders into trade_records (phase=IMPORTING)
 * 7. Count results from DB and return SyncResult
 *
 * @see IbkrFlexClient HTTP client for IBKR Flex Web Service
 * @see FlexQueryParser XML parser for Flex Query responses
 * @see IbkrStagingService Idempotent staging logic
 * @see IbkrImportService Staged-to-trade_records import logic
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
    private final IbkrStagingService stagingService;
    private final IbkrImportService importService;
    private final BrokerSyncBatchService batchService;
    private final IbkrStagedOrderRepository stagedOrderRepository;

    public IbkrSyncAdapter(IbkrFlexQueryProperties properties,
                           IbkrFlexClient flexClient,
                           FlexQueryParser flexQueryParser,
                           IbkrStagingService stagingService,
                           IbkrImportService importService,
                           BrokerSyncBatchService batchService,
                           IbkrStagedOrderRepository stagedOrderRepository) {
        this.properties = properties;
        this.flexClient = flexClient;
        this.flexQueryParser = flexQueryParser;
        this.stagingService = stagingService;
        this.importService = importService;
        this.batchService = batchService;
        this.stagedOrderRepository = stagedOrderRepository;
    }

    @Override
    public String getBrokerCode() {
        return "ibkr";
    }

    @Override
    public SyncResult sync(SyncRequest request) {
        long startMs = System.currentTimeMillis();
        Long batchId = request.getBatchId();

        // 1. Check configuration
        if (!properties.isConfigured()) {
            logger.error("[IbkrSync] IBKR Flex Query credentials not configured. " +
                    "Please set broker.ibkr.flex-token and broker.ibkr.trade-confirm-query-id " +
                    "in application-local.properties");
            return SyncResult.failure(getBrokerCode(),
                    "IBKR Flex Query credentials not configured",
                    System.currentTimeMillis() - startMs);
        }

        try {
            // 2. Resolve date range (phase=FETCHING already set by AsyncExecutor)
            LocalDate endDate = resolveEndDate(request);
            LocalDate startDate = resolveStartDate(request, endDate);
            logger.info("[IbkrSync] Sync date range: {} ~ {}", startDate, endDate);

            // 3. Fetch all data in windows (≤365 days each)
            List<FlexQueryParseResult> allResults = fetchInWindows(startDate, endDate);
            List<IbkrOrderRecord> allOrders = new ArrayList<>();
            List<IbkrTradeConfirm> allTradeConfirms = new ArrayList<>();
            for (FlexQueryParseResult result : allResults) {
                allOrders.addAll(result.getOrders());
                allTradeConfirms.addAll(result.getTradeConfirms());
            }
            logger.info("[IbkrSync] Total fetched: {} orders, {} tradeConfirms",
                    allOrders.size(), allTradeConfirms.size());

            // 4. Stage into ibkr_staged_* tables
            if (batchId != null) {
                batchService.updatePhase(batchId, "STAGING");
            }
            stagingService.stageAll(batchId, allOrders, allTradeConfirms);

            // 5. Import from staged to trade_records
            if (batchId != null) {
                batchService.updatePhase(batchId, "IMPORTING");
            }
            importService.importAll(batchId);

            // 6. Count results from DB (not from memory — accurate even after resume)
            long importedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "IMPORTED");
            long skippedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "SKIPPED");
            long failedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "FAILED");
            int totalCount = (int) (importedCount + skippedCount + failedCount);
            long durationMs = System.currentTimeMillis() - startMs;

            if (failedCount > 0) {
                // v2 fail-fast: any per-record failure escalates to whole-batch cleanup.
                // Returning a failure result routes through BrokerSyncAsyncExecutor →
                // SyncBatchFailureHandler, which wipes the staged rows + any partially
                // imported trade_records and finalizes the batch as FAILED (or
                // CLEANUP_FAILED if cleanup itself fails). We return failure directly
                // (rather than throwing) so that the executor only sees one error path,
                // not throw→catch→failure.
                String reason = String.format(
                        "%d record(s) failed import in batch %d (imported=%d, skipped=%d, duration=%dms)",
                        failedCount, batchId, importedCount, skippedCount, durationMs);
                logger.error("[IbkrSync] {} — triggering fail-fast cleanup", reason);
                return SyncResult.failure(getBrokerCode(), reason, durationMs);
            }

            return SyncResult.success(getBrokerCode(), totalCount,
                    (int) importedCount, (int) skippedCount, durationMs);

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startMs;
            logger.error("[IbkrSync] Sync failed with exception", e);
            return SyncResult.failure(getBrokerCode(), e.getMessage(), durationMs);
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
     * Split the date range into ≤365-day windows and fetch data for each window.
     * Returns the complete FlexQueryParseResult (orders + tradeConfirms) for each window.
     */
    private List<FlexQueryParseResult> fetchInWindows(LocalDate startDate, LocalDate endDate) {
        List<FlexQueryParseResult> allResults = new ArrayList<>();

        LocalDate windowStart = startDate;

        while (!windowStart.isAfter(endDate)) {
            LocalDate windowEnd = windowStart.plusDays(MAX_QUERY_DAYS);
            if (windowEnd.isAfter(endDate)) {
                windowEnd = endDate;
            }

            logger.info("[IbkrSync] Fetching window: {} ~ {}", windowStart, windowEnd);
            FlexQueryParseResult parseResult = fetchForWindow(windowStart, windowEnd);
            allResults.add(parseResult);
            logger.info("[IbkrSync] Window {} ~ {} returned {} orders, {} tradeConfirms",
                    windowStart, windowEnd, parseResult.getOrderCount(), parseResult.getTradeConfirmCount());

            windowStart = windowEnd.plusDays(1);
        }

        return allResults;
    }

    /**
     * Fetch and parse data for a single date window.
     */
    private FlexQueryParseResult fetchForWindow(LocalDate startDate, LocalDate endDate) {
        // Convert to yyyyMMdd for IBKR Flex API
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

        return parseResult;
    }
}
