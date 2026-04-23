package com.vortex.sync.adapter.ibkr;

import com.vortex.repository.IbkrStagedOrderRepository;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAdapter;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import com.vortex.sync.core.SyncRequest;
import com.vortex.sync.core.SyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
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

    /** Terminal staged-row statuses that contribute to a healthy sync result. */
    private static final List<String> TERMINAL_STAGED_STATUSES =
            List.of("IMPORTED", "SKIPPED", "FAILED");

    /** Cap on how many residual staged-row ids we dump in the WARN log. */
    private static final int RESIDUAL_ID_LOG_CAP = 20;

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
            logger.error("[IbkrSync] batch={} [AUTH] IBKR Flex Query credentials not configured. " +
                    "Please set broker.ibkr.flex-token and broker.ibkr.trade-confirm-query-id " +
                    "in application-local.properties", batchId);
            return SyncResult.failure(getBrokerCode(),
                    CategorizedSyncException.format(FailureCategory.AUTH, null,
                            "IBKR Flex Query credentials not configured"),
                    System.currentTimeMillis() - startMs);
        }

        try {
            // 2. Resolve date range (phase=FETCHING already set by AsyncExecutor)
            LocalDate endDate = resolveEndDate(request);
            LocalDate startDate = resolveStartDate(request, endDate);
            logger.info("[IbkrSync] batch={} Sync date range: {} ~ {}", batchId, startDate, endDate);

            // 3. Fetch all data in windows (≤365 days each)
            List<FlexQueryParseResult> allResults = fetchInWindows(batchId, startDate, endDate);
            List<IbkrOrderRecord> allOrders = new ArrayList<>();
            List<IbkrTradeConfirm> allTradeConfirms = new ArrayList<>();
            for (FlexQueryParseResult result : allResults) {
                allOrders.addAll(result.getOrders());
                allTradeConfirms.addAll(result.getTradeConfirms());
            }
            logger.info("[IbkrSync] batch={} Total fetched: {} orders, {} tradeConfirms",
                    batchId, allOrders.size(), allTradeConfirms.size());

            // 4. Stage into ibkr_staged_* tables
            if (batchId != null) {
                batchService.updatePhase(batchId, "STAGING");
            }
            logger.info("[IbkrSync] batch={} Entering STAGING phase", batchId);
            stagingService.stageAll(batchId, allOrders, allTradeConfirms);

            // 5. Import from staged to trade_records
            if (batchId != null) {
                batchService.updatePhase(batchId, "IMPORTING");
            }
            logger.info("[IbkrSync] batch={} Entering IMPORTING phase", batchId);
            importService.importAll(batchId);

            // 6. Count results from DB (not from memory — accurate even after resume)
            long importedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "IMPORTED");
            long skippedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "SKIPPED");
            long failedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "FAILED");
            // Residual = staged rows that ended in a non-terminal state (typically PENDING).
            // Before the P0-2 fix the adapter silently ignored them, which, combined with
            // P0-1 (failed rows staying PENDING because the FAILED write was lost inside
            // a rolled-back tx), let the batch finalize as COMPLETED while rows were
            // actually broken or never imported. Detect and escalate to fail-fast cleanup.
            long residualCount = stagedOrderRepository.countByBatchIdAndStatusNotIn(
                    batchId, TERMINAL_STAGED_STATUSES);
            int totalCount = (int) (importedCount + skippedCount + failedCount);
            long durationMs = System.currentTimeMillis() - startMs;

            if (residualCount > 0) {
                // Log a bounded sample of residual ids so operators have breadcrumbs.
                List<Long> residualIds = stagedOrderRepository.findIdsByBatchIdAndStatusNotIn(
                        batchId, TERMINAL_STAGED_STATUSES, PageRequest.of(0, RESIDUAL_ID_LOG_CAP));
                logger.warn("[IbkrSync] batch={} Residual non-terminal staged rows " +
                                "(showing first {} of {} ids): {}",
                        batchId, residualIds.size(), residualCount, residualIds);
            }

            if (failedCount > 0 || residualCount > 0) {
                // v2 fail-fast: any per-record failure (or residue that should have been
                // terminal) escalates to whole-batch cleanup. Per-row details (with their
                // own [CATEGORY] prefixes) live on the staged rows' error_message.
                String reason = String.format(
                        "%d record(s) failed import in batch %d " +
                                "(imported=%d, skipped=%d, failed=%d, residual_non_terminal=%d, duration=%dms)",
                        failedCount + residualCount, batchId,
                        importedCount, skippedCount, failedCount, residualCount, durationMs);
                String formatted = CategorizedSyncException.format(
                        FailureCategory.UNRECOGNIZED, null, reason);
                logger.error("[IbkrSync] batch={} {} — triggering fail-fast cleanup", batchId, formatted);
                return SyncResult.failure(getBrokerCode(), formatted, durationMs);
            }

            return SyncResult.success(getBrokerCode(), totalCount,
                    (int) importedCount, (int) skippedCount, durationMs);

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startMs;
            String formatted;
            if (e instanceof CategorizedSyncException) {
                formatted = ((CategorizedSyncException) e).getFormattedMessage();
            } else {
                // Upstream network/XML-parse exceptions or anything else not
                // explicitly categorised — default to NETWORK for IbkrFlexClient
                // failures, INTERNAL otherwise so programming errors don't get
                // silently mislabelled as transient.
                FailureCategory category = isNetworkFailure(e)
                        ? FailureCategory.NETWORK
                        : FailureCategory.INTERNAL;
                formatted = CategorizedSyncException.format(category, null,
                        e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
            }
            logger.error("[IbkrSync] batch={} Sync failed: {}", batchId, formatted, e);
            return SyncResult.failure(getBrokerCode(), formatted, durationMs);
        }
    }

    /**
     * Heuristic for the outer catch: classify exceptions originating from
     * IbkrFlexClient (HTTP / network) or FlexQueryParser (malformed XML) as
     * NETWORK. Everything else stays INTERNAL.
     */
    private static boolean isNetworkFailure(Throwable e) {
        if (e == null) {
            return false;
        }
        String className = e.getClass().getName();
        if (className.startsWith("java.net.")
                || className.startsWith("java.io.")
                || className.startsWith("org.springframework.web.client.")
                || className.startsWith("org.xml.sax.")
                || className.startsWith("javax.xml.")) {
            return true;
        }
        String simpleName = e.getClass().getSimpleName();
        return simpleName.contains("FlexQuery") || simpleName.contains("FlexClient");
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
    private List<FlexQueryParseResult> fetchInWindows(Long batchId, LocalDate startDate, LocalDate endDate) {
        List<FlexQueryParseResult> allResults = new ArrayList<>();

        LocalDate windowStart = startDate;

        while (!windowStart.isAfter(endDate)) {
            LocalDate windowEnd = windowStart.plusDays(MAX_QUERY_DAYS);
            if (windowEnd.isAfter(endDate)) {
                windowEnd = endDate;
            }

            logger.info("[IbkrSync] batch={} Fetching window: {} ~ {}", batchId, windowStart, windowEnd);
            FlexQueryParseResult parseResult = fetchForWindow(batchId, windowStart, windowEnd);
            allResults.add(parseResult);
            logger.info("[IbkrSync] batch={} Window {} ~ {} returned {} orders, {} tradeConfirms",
                    batchId, windowStart, windowEnd,
                    parseResult.getOrderCount(), parseResult.getTradeConfirmCount());

            windowStart = windowEnd.plusDays(1);
        }

        return allResults;
    }

    /**
     * Fetch and parse data for a single date window.
     */
    private FlexQueryParseResult fetchForWindow(Long batchId, LocalDate startDate, LocalDate endDate) {
        // Convert to yyyyMMdd for IBKR Flex API
        String fromDate = startDate.format(IBKR_DATE_FORMATTER);
        String toDate = endDate.format(IBKR_DATE_FORMATTER);

        // Fetch raw XML report
        String xmlReport = flexClient.fetchReport(fromDate, toDate);

        // Parse XML into structured result
        FlexQueryParseResult parseResult = flexQueryParser.parse(xmlReport);

        logger.info("[IbkrSync] batch={} Parsed report: account={}, range={} ~ {}, orders={}, tradeConfirms={}",
                batchId,
                parseResult.getAccountId(),
                parseResult.getFromDate(),
                parseResult.getToDate(),
                parseResult.getOrderCount(),
                parseResult.getTradeConfirmCount());

        return parseResult;
    }
}
