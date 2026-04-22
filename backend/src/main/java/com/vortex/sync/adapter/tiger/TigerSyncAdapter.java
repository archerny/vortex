package com.vortex.sync.adapter.tiger;

import com.vortex.repository.TigerStagedOrderRepository;
import com.vortex.service.BrokerSyncBatchService;
import com.vortex.sync.core.BrokerSyncAdapter;
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
 * <p>Batch lifecycle (PROCESSING → COMPLETED/PARTIAL/FAILED) is owned by
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
            logger.error("[TigerSync] Tiger API credentials not configured. " +
                    "Please set broker.tiger.* properties in application-local.properties");
            return SyncResult.failure(getBrokerCode(),
                    "Tiger API credentials not configured",
                    System.currentTimeMillis() - startMs);
        }

        try {
            // 2. Initialize client
            TigerHttpClient client = createClient();
            logger.info("[TigerSync] Tiger API client initialized, account: {}", tigerApiProperties.getAccount());

            // 3. Resolve date range (phase=FETCHING already set by BrokerSyncAsyncExecutor)
            LocalDate endDate = resolveEndDate(request);
            LocalDate startDate = resolveStartDate(request, endDate);
            logger.info("[TigerSync] Sync date range: {} ~ {}", startDate, endDate);

            // 4. Fetch all filled orders in 90-day windows
            List<TigerOrderRecord> allRecords = fetchOrdersInWindows(client, startDate, endDate);
            logger.info("[TigerSync] Total fetched: {} filled orders", allRecords.size());
            logRecordsForDebug(allRecords);

            // 5. Stage into tiger_staged_orders
            if (batchId != null) {
                batchService.updatePhase(batchId, "STAGING");
            }
            stagingService.stageAll(batchId, allRecords);

            // 6. Import from staged to trade_records
            if (batchId != null) {
                batchService.updatePhase(batchId, "IMPORTING");
            }
            importService.importAll(batchId);

            // 7. Re-count results from DB (accurate even after resume)
            long importedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "IMPORTED");
            long skippedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "SKIPPED");
            long failedCount = stagedOrderRepository.countByBatchIdAndStatus(batchId, "FAILED");
            int totalCount = (int) (importedCount + skippedCount + failedCount);

            long durationMs = System.currentTimeMillis() - startMs;
            return SyncResult.success(getBrokerCode(), totalCount,
                    (int) importedCount, (int) skippedCount, (int) failedCount, durationMs);

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startMs;
            logger.error("[TigerSync] Sync failed with exception", e);
            return SyncResult.failure(getBrokerCode(), e.getMessage(), durationMs);
        }
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
    private List<TigerOrderRecord> fetchOrdersInWindows(TigerHttpClient client, LocalDate startDate, LocalDate endDate) {
        List<TigerOrderRecord> allRecords = new ArrayList<>();

        LocalDate windowStart = startDate;

        while (windowStart.isBefore(endDate)) {
            LocalDate windowEnd = windowStart.plusDays(MAX_QUERY_DAYS);
            if (windowEnd.isAfter(endDate)) {
                windowEnd = endDate;
            }

            logger.info("[TigerSync] Fetching window: {} ~ {}", windowStart, windowEnd);
            List<TigerOrderRecord> windowRecords = fetchFilledOrders(client, windowStart, windowEnd);
            allRecords.addAll(windowRecords);
            logger.info("[TigerSync] Window {} ~ {} returned {} records",
                    windowStart, windowEnd, windowRecords.size());

            windowStart = windowEnd;
        }

        return allRecords;
    }

    /**
     * Call Tiger API for a single window.
     */
    private List<TigerOrderRecord> fetchFilledOrders(TigerHttpClient client, LocalDate startDate, LocalDate endDate) {
        List<TigerOrderRecord> records = new ArrayList<>();

        try {
            QueryOrderRequest request = new QueryOrderRequest(MethodName.FILLED_ORDERS);
            String bizContent = AccountParamBuilder.instance()
                    .account(tigerApiProperties.getAccount())
                    .startDate(startDate.format(DATE_FORMATTER))
                    .endDate(endDate.format(DATE_FORMATTER))
                    .buildJson();
            request.setBizContent(bizContent);

            BatchOrderResponse response = client.execute(request);

            if (response == null || !response.isSuccess()) {
                String errorMsg = response != null ? response.getMessage() : "response is null";
                logger.warn("[TigerSync] API call failed: {}", errorMsg);
                return records;
            }

            BatchOrderItem orderItem = response.getItem();
            if (orderItem == null || orderItem.getOrders() == null) {
                logger.info("[TigerSync] No filled orders in this window");
                return records;
            }

            for (TradeOrder order : orderItem.getOrders()) {
                TigerOrderRecord record = convertToRecord(order);
                records.add(record);
            }

        } catch (Exception e) {
            logger.error("[TigerSync] Exception while querying {} ~ {}: {}",
                    startDate, endDate, e.getMessage(), e);
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
