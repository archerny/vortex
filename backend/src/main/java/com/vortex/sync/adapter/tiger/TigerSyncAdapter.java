package com.vortex.sync.adapter.tiger;

import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAdapter;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;
import com.vortex.sync.core.SyncRequest;
import com.vortex.sync.core.SyncResult;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.BatchOrderItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrder;
import com.tigerbrokers.stock.openapi.client.https.request.trade.QueryOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.BatchOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tiger Brokers Sync Adapter
 *
 * Implements BrokerSyncAdapter to sync filled orders from Tiger Open API.
 *
 * Phase 3 behavior (aligned with IbkrSyncAdapter):
 * <ol>
 *   <li>Validate Tiger API configuration</li>
 *   <li>Parse date range from SyncRequest (default: endDate = today, startDate = endDate - 90d)</li>
 *   <li>Split date range into &le; 90-day windows (Tiger API limit) and fetch filled orders
 *       via {@code get_filled_orders} (server returns full list per window — no client-side
 *       pagination is needed; see external-resource/tiger-api/docs/orderinfo.md)</li>
 *   <li>phase=STAGING: {@link TigerStagingService#stageAll(Long, List)} — idempotent</li>
 *   <li>phase=IMPORTING: {@link TigerImportService#importAll(Long)} — staged → trade_records</li>
 *   <li>Re-count IMPORTED/SKIPPED/FAILED from DB and return {@link SyncResult}</li>
 * </ol>
 *
 * <p>Batch lifecycle (PROCESSING → COMPLETED or FAILED/CLEANUP_FAILED) is owned by
 * {@code BrokerSyncAsyncExecutor}; this adapter only updates the {@code phase} field.
 *
 * @see TigerStagingService
 * @see TigerImportService
 * @see TigerTradeRecordMapper
 */
@Component
public class TigerSyncAdapter implements BrokerSyncAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TigerSyncAdapter.class);

    /** Tiger API maximum date range per request (days). */
    private static final int MAX_QUERY_DAYS = 90;

    /** Terminal staged-row statuses that contribute to a healthy sync result. */
    private static final List<String> TERMINAL_STAGED_STATUSES =
            List.of("IMPORTED", "SKIPPED", "FAILED");

    /** Cap on how many residual staged-row ids we dump in the WARN log. */
    private static final int RESIDUAL_ID_LOG_CAP = 20;

    /** Date formatter for SyncRequest + Tiger API (yyyy-MM-dd). */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final TigerApiProperties tigerApiProperties;
    private final TigerStagingService stagingService;
    private final TigerImportService importService;
    private final BrokerSyncBatchService batchService;
    private final TigerStagedOrderRepository stagedOrderRepository;

    public TigerSyncAdapter(TigerApiProperties tigerApiProperties,
                            TigerStagingService stagingService,
                            TigerImportService importService,
                            BrokerSyncBatchService batchService,
                            TigerStagedOrderRepository stagedOrderRepository) {
        this.tigerApiProperties = tigerApiProperties;
        this.stagingService = stagingService;
        this.importService = importService;
        this.batchService = batchService;
        this.stagedOrderRepository = stagedOrderRepository;
    }

    @Override
    public String getBrokerCode() {
        return "tiger";
    }

    @Override
    public SyncResult sync(SyncRequest request) {
        long startMs = System.currentTimeMillis();
        Long batchId = request.getBatchId();

        // 1. Check configuration
        if (!tigerApiProperties.isConfigured()) {
            logger.error("[TigerSync] batch={} [AUTH] Tiger API credentials not configured. " +
                    "Please set broker.tiger.* properties in application-local.properties", batchId);
            return SyncResult.failure(getBrokerCode(),
                    CategorizedSyncException.format(FailureCategory.AUTH, null,
                            "Tiger API credentials not configured"),
                    System.currentTimeMillis() - startMs);
        }

        try {
            // 2. Initialize client
            TigerHttpClient client = createClient();
            logger.info("[TigerSync] batch={} Tiger API client initialized, account: {}",
                    batchId, tigerApiProperties.getAccount());

            // 3. Resolve date range (phase=FETCHING already set by BrokerSyncAsyncExecutor)
            LocalDate endDate = resolveEndDate(request);
            LocalDate startDate = resolveStartDate(request, endDate);
            logger.info("[TigerSync] batch={} Sync date range: {} ~ {}", batchId, startDate, endDate);

            // 4. Fetch all filled orders in 90-day windows
            List<TigerOrderRecord> allRecords = fetchOrdersInWindows(client, batchId, startDate, endDate);
            logger.info("[TigerSync] batch={} Total fetched: {} filled orders", batchId, allRecords.size());
            logRecordsForDebug(allRecords);

            // 5. Stage into tiger_staged_orders
            if (batchId != null) {
                batchService.updatePhase(batchId, "STAGING");
            }
            logger.info("[TigerSync] batch={} Entering STAGING phase", batchId);
            stagingService.stageAll(batchId, allRecords);

            // 6. Import from staged to trade_records
            if (batchId != null) {
                batchService.updatePhase(batchId, "IMPORTING");
            }
            logger.info("[TigerSync] batch={} Entering IMPORTING phase", batchId);
            importService.importAll(batchId);

            // 7. Re-count results from DB (accurate even after resume)
            long importedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "IMPORTED");
            long skippedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "SKIPPED");
            long failedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "FAILED");
            // Residual = staged rows that ended in a non-terminal state (typically PENDING).
            // See IbkrSyncAdapter for the rationale — P0-2 of the data-loss chain fix.
            long residualCount = stagedOrderRepository.countByBatchIdAndStatusNotIn(
                    batchId, TERMINAL_STAGED_STATUSES);
            int totalCount = (int) (importedCount + skippedCount + failedCount);
            long durationMs = System.currentTimeMillis() - startMs;

            if (residualCount > 0) {
                List<Long> residualIds = stagedOrderRepository.findIdsByBatchIdAndStatusNotIn(
                        batchId, TERMINAL_STAGED_STATUSES, PageRequest.of(0, RESIDUAL_ID_LOG_CAP));
                logger.warn("[TigerSync] batch={} Residual non-terminal staged rows " +
                                "(showing first {} of {} ids): {}",
                        batchId, residualIds.size(), residualCount, residualIds);
            }

            if (failedCount > 0 || residualCount > 0) {
                // v2 fail-fast: any per-record failure (or residue that should have been
                // terminal) escalates to whole-batch cleanup. Returning a failure result
                // routes through BrokerSyncAsyncExecutor → SyncBatchFailureHandler, which
                // wipes the staged rows + any partially imported trade_records and
                // finalizes the batch as FAILED (or CLEANUP_FAILED if cleanup itself
                // fails). We return failure directly (rather than throwing) so that the
                // executor only sees one error path, not throw→catch→failure.
                //
                // Batch-level category is UNRECOGNIZED: per-row failures with category
                // AUTH/NETWORK/INTERNAL should not be possible at this point (those
                // kinds of failures abort the sync before the import loop). Per-row
                // details (including their own [CATEGORY] prefixes) live on the
                // staged rows' error_message fields.
                String reason = String.format(
                        "%d record(s) failed import in batch %d " +
                                "(imported=%d, skipped=%d, failed=%d, residual_non_terminal=%d, duration=%dms)",
                        failedCount + residualCount, batchId,
                        importedCount, skippedCount, failedCount, residualCount, durationMs);
                String formatted = CategorizedSyncException.format(
                        FailureCategory.UNRECOGNIZED, null, reason);
                logger.error("[TigerSync] batch={} {} — triggering fail-fast cleanup", batchId, formatted);
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
                // Upstream network/SDK exception or anything else we didn't explicitly
                // categorise — default to NETWORK for the known API-call path (rethrown
                // as RuntimeException in fetchFilledOrders), INTERNAL otherwise.
                FailureCategory category = isNetworkFailure(e)
                        ? FailureCategory.NETWORK
                        : FailureCategory.INTERNAL;
                formatted = CategorizedSyncException.format(category, null,
                        e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
            }
            logger.error("[TigerSync] batch={} Sync failed: {}", batchId, formatted, e);
            return SyncResult.failure(getBrokerCode(), formatted, durationMs);
        }
    }

    /**
     * Heuristic for the outer catch: classify exceptions originating from the
     * Tiger SDK or the JDK's network/IO stack as NETWORK. Everything else
     * stays INTERNAL (our own bug) to avoid silently mislabelling a
     * programming error as a transient network issue.
     *
     * <p>Mirrors {@code IbkrSyncAdapter.isNetworkFailure} so both adapters
     * honour the same {@code java.net.*} / {@code java.io.*} / SSL /
     * TLS-class fallback promised by
     * {@code docs/broker-sync/framework/unrecognized-data-logging.md §5.2}.
     */
    private static boolean isNetworkFailure(Throwable e) {
        if (e == null) {
            return false;
        }
        // Our own rethrown wrapper messages from fetchFilledOrders (kept for
        // backwards safety even though those paths now throw
        // CategorizedSyncException directly).
        String msg = e.getMessage();
        if (msg != null && msg.startsWith("Tiger API ")) {
            return true;
        }
        String className = e.getClass().getName();
        if (className.startsWith("com.tigerbrokers.")
                || className.startsWith("java.net.")
                || className.startsWith("java.io.")
                || className.startsWith("javax.net.ssl.")
                || className.startsWith("org.springframework.web.client.")) {
            return true;
        }
        String simpleName = e.getClass().getSimpleName();
        return simpleName.contains("Timeout") || simpleName.contains("SSL");
    }

    // ============ Client Initialization ============

    /**
     * Create a TigerHttpClient instance using credentials from TigerApiProperties
     * (no dependency on tiger_openapi_config.properties file).
     */
    private TigerHttpClient createClient() {
        ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
        clientConfig.tigerId = tigerApiProperties.getTigerId();
        clientConfig.defaultAccount = tigerApiProperties.getAccount();
        clientConfig.privateKey = tigerApiProperties.getPrivateKey();

        return TigerHttpClient.getInstance().clientConfig(clientConfig);
    }

    // ============ Date Resolution ============

    private LocalDate resolveStartDate(SyncRequest request, LocalDate endDate) {
        if (request.getStartTime() != null && !request.getStartTime().isBlank()) {
            return LocalDate.parse(request.getStartTime(), DATE_FORMATTER);
        }
        // Default: 90 days back from endDate (Tiger API hard limit)
        return endDate.minusDays(MAX_QUERY_DAYS);
    }

    private LocalDate resolveEndDate(SyncRequest request) {
        if (request.getEndTime() != null && !request.getEndTime().isBlank()) {
            return LocalDate.parse(request.getEndTime(), DATE_FORMATTER);
        }
        // Default: today
        return LocalDate.now();
    }

    // ============ Windowed Fetching ============

    /**
     * Split the date range into &le; 90-day windows and fetch filled orders for each window.
     *
     * <p>Tiger API's {@code get_filled_orders} requires start_time and end_time within 90 days.
     * The server returns the full list per window (no client-side pagination).
     */
    private List<TigerOrderRecord> fetchOrdersInWindows(TigerHttpClient client, Long batchId,
                                                        LocalDate startDate, LocalDate endDate) {
        List<TigerOrderRecord> allRecords = new ArrayList<>();

        LocalDate windowStart = startDate;

        // Loop condition and window advancement mirror IbkrSyncAdapter.fetchInWindows:
        //   !isAfter(endDate) — correctly handles single-day ranges (startDate == endDate)
        //   windowEnd.plusDays(1) — no overlap between adjacent windows
        while (!windowStart.isAfter(endDate)) {
            LocalDate windowEnd = windowStart.plusDays(MAX_QUERY_DAYS);
            if (windowEnd.isAfter(endDate)) {
                windowEnd = endDate;
            }

            logger.info("[TigerSync] batch={} Fetching window: {} ~ {}", batchId, windowStart, windowEnd);
            List<TigerOrderRecord> windowRecords = fetchFilledOrders(client, batchId, windowStart, windowEnd);
            allRecords.addAll(windowRecords);
            logger.info("[TigerSync] batch={} Window {} ~ {} returned {} records",
                    batchId, windowStart, windowEnd, windowRecords.size());

            windowStart = windowEnd.plusDays(1);
        }

        return allRecords;
    }

    /**
     * Call Tiger API for a single window.
     *
     * <p>v2 fail-fast contract: any upstream error (API failure, non-success response,
     * network exception) is re-thrown so the outer {@link #sync(SyncRequest)} catch-all
     * can escalate the whole batch to {@code SyncBatchFailureHandler}. Previously this
     * method swallowed exceptions and returned an empty list, which caused API errors
     * to be silently treated as "no orders in this window" — a data-loss hazard.
     *
     * <p>An empty result (response ok but zero orders) is still returned as an empty
     * list, which is the normal "no activity in this window" case.
     */
    private List<TigerOrderRecord> fetchFilledOrders(TigerHttpClient client, Long batchId,
                                                     LocalDate startDate, LocalDate endDate) {
        QueryOrderRequest request = new QueryOrderRequest(MethodName.FILLED_ORDERS);
        String bizContent = AccountParamBuilder.instance()
                .account(tigerApiProperties.getAccount())
                .startDate(startDate.format(DATE_FORMATTER))
                .endDate(endDate.format(DATE_FORMATTER))
                .buildJson();
        request.setBizContent(bizContent);

        BatchOrderResponse response;
        try {
            response = client.execute(request);
        } catch (Exception e) {
            // Network / SDK failure — rethrow as CategorizedSyncException so the
            // outer sync() catch escalates to fail-fast cleanup. We deliberately
            // do NOT log here: the outer catch already logs with full stack
            // trace, and double-logging the same stack just adds noise.
            throw new CategorizedSyncException(FailureCategory.NETWORK, null,
                    String.format("Tiger API call threw exception for window %s ~ %s: %s",
                            startDate, endDate, e.getMessage()), e);
        }

        if (response == null) {
            throw new CategorizedSyncException(FailureCategory.NETWORK, null,
                    String.format("Tiger API returned null response for window %s ~ %s",
                            startDate, endDate));
        }
        if (!response.isSuccess()) {
            // Non-success response (auth failure, rate limit, server error, etc.) must not be
            // silently treated as "no orders" — doing so would drop real trades from the sync.
            throw new CategorizedSyncException(FailureCategory.NETWORK, null,
                    String.format("Tiger API returned error for window %s ~ %s: code=%s, message=%s",
                            startDate, endDate, response.getCode(), response.getMessage()));
        }

        List<TigerOrderRecord> records = new ArrayList<>();
        BatchOrderItem orderItem = response.getItem();
        if (orderItem == null || orderItem.getOrders() == null) {
            logger.info("[TigerSync] batch={} No filled orders in this window", batchId);
            return records;
        }

        for (TradeOrder order : orderItem.getOrders()) {
            TigerOrderRecord record = convertToRecord(order);
            records.add(record);
        }
        return records;
    }

    // ============ Data Conversion ============

    /**
     * Convert Tiger SDK's TradeOrder to TigerOrderRecord (DTO used by staging/mapper).
     */
    private TigerOrderRecord convertToRecord(TradeOrder order) {
        TigerOrderRecord record = new TigerOrderRecord();

        // Basic order info
        record.setAccount(order.getAccount());
        record.setOrderId(order.getId() != null ? order.getId() : 0L);
        record.setOrderTime(order.getOpenTime() != null ? order.getOpenTime() : 0L);
        record.setTradeTime(order.getLatestTime() != null ? order.getLatestTime() : 0L);
        record.setAction(order.getAction());
        record.setStatus(order.getStatus() != null ? order.getStatus().name() : null);

        // Quantity + price (totalQuantity / filledQuantity are Long)
        record.setQuantity(order.getTotalQuantity() != null ? order.getTotalQuantity().intValue() : 0);
        record.setQuantityScale(order.getTotalQuantityScale() != null ? order.getTotalQuantityScale() : 0);
        record.setFilledQuantity(order.getFilledQuantity() != null ? order.getFilledQuantity().intValue() : 0);
        record.setAvgFillPrice(order.getAvgFillPrice() != null
                ? BigDecimal.valueOf(order.getAvgFillPrice()) : BigDecimal.ZERO);

        // Fees
        record.setCommission(order.getCommission() != null
                ? BigDecimal.valueOf(order.getCommission()) : BigDecimal.ZERO);
        record.setGst(order.getGst() != null
                ? BigDecimal.valueOf(order.getGst()) : BigDecimal.ZERO);
        record.setRealizedPnl(order.getRealizedPnl() != null
                ? BigDecimal.valueOf(order.getRealizedPnl()) : BigDecimal.ZERO);

        // Contract info
        if (order.getSymbol() != null) {
            record.setSymbol(order.getSymbol());
        }
        if (order.getName() != null) {
            record.setContractName(order.getName());
        }
        if (order.getSecType() != null) {
            record.setSecType(order.getSecType());
        }
        if (order.getCurrency() != null) {
            record.setCurrency(order.getCurrency());
        }
        if (order.getMarket() != null) {
            record.setMarket(order.getMarket());
        }
        if (order.getIdentifier() != null) {
            record.setIdentifier(order.getIdentifier());
        }
        if (order.getMultiplier() != null) {
            record.setMultiplier(BigDecimal.valueOf(order.getMultiplier()));
        }

        // Option-specific fields
        if (order.getExpiry() != null) {
            record.setExpiry(order.getExpiry());
        }
        if (order.getStrike() != null && !order.getStrike().isEmpty()) {
            try {
                record.setStrike(new BigDecimal(order.getStrike()));
            } catch (NumberFormatException e) {
                logger.warn("[TigerSync] Failed to parse strike value: {}", order.getStrike());
            }
        }
        if (order.getRight() != null) {
            record.setPutCall(order.getRight());
        }

        // Order type
        if (order.getOrderType() != null) {
            record.setOrderType(order.getOrderType());
        }
        if (order.getLimitPrice() != null) {
            record.setLimitPrice(BigDecimal.valueOf(order.getLimitPrice()));
        }

        // attrDesc — option event marker ("Exercise" / "Assigned" / "Expired"); null for ordinary trades
        record.setAttrDesc(order.getAttrDesc());

        return record;
    }

    // ============ Debug Logging ============

    /**
     * Dump each record at DEBUG level for troubleshooting. Kept from Phase 1
     * but demoted from INFO to DEBUG — in production we rely on the DB tables
     * (tiger_staged_orders + trade_records) as the source of truth.
     */
    private void logRecordsForDebug(List<TigerOrderRecord> records) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        logger.debug("[TigerSync] ====== Raw fetch result: {} filled orders total ======", records.size());
        for (int i = 0; i < records.size(); i++) {
            logger.debug("[TigerSync] [{}] {}", i + 1, records.get(i));
        }
        logger.debug("[TigerSync] ====== Log output complete ======");
    }
}
