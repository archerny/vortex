package com.localledger.sync.adapter.tiger;

import com.localledger.sync.core.BrokerSyncAdapter;
import com.localledger.sync.core.SyncRequest;
import com.localledger.sync.core.SyncResult;
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
 * 老虎证券同步适配器
 *
 * 实现 BrokerSyncAdapter 接口，通过 Tiger Open API 获取已成交订单数据。
 *
 * Phase 1 行为：
 * 1. 初始化 TigerHttpClient（使用 application-local.properties 中的凭证）
 * 2. 调用 get_filled_orders API 获取已成交订单
 * 3. 处理 90 天分页限制（自动拆分时间窗口）
 * 4. 将 API 返回的 TradeOrder 转换为 TigerOrderRecord
 * 5. 逐条日志输出（供核对原始数据）
 *
 * 注意：start_time 和 end_time 之间的间隔不能超过 90 天
 */
@Component
public class TigerSyncAdapter implements BrokerSyncAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TigerSyncAdapter.class);

    /** Tiger API 单次查询最大时间跨度（天） */
    private static final int MAX_QUERY_DAYS = 90;

    /** 日期格式化器 */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final TigerApiProperties tigerApiProperties;

    public TigerSyncAdapter(TigerApiProperties tigerApiProperties) {
        this.tigerApiProperties = tigerApiProperties;
    }

    @Override
    public String getBrokerName() {
        return "tiger";
    }

    @Override
    public SyncResult sync(SyncRequest request) {
        long startMs = System.currentTimeMillis();

        // 1. 检查配置
        if (!tigerApiProperties.isConfigured()) {
            logger.error("[TigerSync] Tiger API credentials not configured. " +
                    "Please set broker.tiger.* properties in application-local.properties");
            return SyncResult.failure(getBrokerName(), "API credentials not configured", System.currentTimeMillis() - startMs);
        }

        try {
            // 2. 初始化客户端
            TigerHttpClient client = createClient();
            logger.info("[TigerSync] Tiger API client initialized, account: {}", tigerApiProperties.getAccount());

            // 3. 确定时间范围
            LocalDate endDate = resolveEndDate(request);
            LocalDate startDate = resolveStartDate(request, endDate);
            logger.info("[TigerSync] Sync date range: {} ~ {}", startDate, endDate);

            // 4. 分段查询（90 天限制）
            List<TigerOrderRecord> allRecords = fetchOrdersInWindows(client, startDate, endDate);

            // 5. 日志输出
            logRecords(allRecords);

            long durationMs = System.currentTimeMillis() - startMs;
            return SyncResult.success(getBrokerName(), allRecords.size(), durationMs);

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startMs;
            logger.error("[TigerSync] Sync failed with exception", e);
            return SyncResult.failure(getBrokerName(), e.getMessage(), durationMs);
        }
    }

    // ============ 客户端初始化 ============

    /**
     * 创建 TigerHttpClient 实例
     * 使用硬编码配置方式（不依赖 tiger_openapi_config.properties 文件）
     */
    private TigerHttpClient createClient() {
        ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
        clientConfig.tigerId = tigerApiProperties.getTigerId();
        clientConfig.defaultAccount = tigerApiProperties.getAccount();
        clientConfig.privateKey = tigerApiProperties.getPrivateKey();

        return TigerHttpClient.getInstance().clientConfig(clientConfig);
    }

    // ============ 时间范围解析 ============

    private LocalDate resolveStartDate(SyncRequest request, LocalDate endDate) {
        if (request.getStartTime() != null && !request.getStartTime().isBlank()) {
            return LocalDate.parse(request.getStartTime(), DATE_FORMATTER);
        }
        // 默认：从 endDate 往前推 90 天
        return endDate.minusDays(MAX_QUERY_DAYS);
    }

    private LocalDate resolveEndDate(SyncRequest request) {
        if (request.getEndTime() != null && !request.getEndTime().isBlank()) {
            return LocalDate.parse(request.getEndTime(), DATE_FORMATTER);
        }
        // 默认：今天
        return LocalDate.now();
    }

    // ============ 分段查询 ============

    /**
     * 将时间范围按 90 天窗口拆分，逐段查询已成交订单
     *
     * Tiger API 限制 start_time 和 end_time 之间的间隔不能超过 90 天，
     * 因此如果请求的时间范围超过 90 天，需要自动拆分为多个子窗口依次查询。
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
            logger.info("[TigerSync] Window {} ~ {} returned {} records", windowStart, windowEnd, windowRecords.size());

            windowStart = windowEnd;
        }

        return allRecords;
    }

    /**
     * 调用 Tiger API 获取指定时间范围内的已成交订单
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
            logger.error("[TigerSync] Exception while querying {} ~ {}: {}", startDate, endDate, e.getMessage(), e);
        }

        return records;
    }

    // ============ 数据转换 ============

    /**
     * 将 Tiger SDK 的 TradeOrder 对象转换为 TigerOrderRecord
     */
    private TigerOrderRecord convertToRecord(TradeOrder order) {
        TigerOrderRecord record = new TigerOrderRecord();

        // 订单基本信息
        record.setAccount(order.getAccount());
        record.setOrderId(order.getId() != null ? order.getId() : 0L);
        record.setOrderTime(order.getOpenTime() != null ? order.getOpenTime() : 0L);
        record.setTradeTime(order.getLatestTime() != null ? order.getLatestTime() : 0L);
        record.setAction(order.getAction());
        record.setStatus(order.getStatus() != null ? order.getStatus().name() : null);

        // 数量与价格（totalQuantity 和 filledQuantity 是 Long 类型）
        record.setQuantity(order.getTotalQuantity() != null ? order.getTotalQuantity().intValue() : 0);
        record.setQuantityScale(order.getTotalQuantityScale() != null ? order.getTotalQuantityScale() : 0);
        record.setFilledQuantity(order.getFilledQuantity() != null ? order.getFilledQuantity().intValue() : 0);
        record.setAvgFillPrice(order.getAvgFillPrice() != null
                ? BigDecimal.valueOf(order.getAvgFillPrice()) : BigDecimal.ZERO);

        // 费用
        record.setCommission(order.getCommission() != null
                ? BigDecimal.valueOf(order.getCommission()) : BigDecimal.ZERO);
        record.setGst(order.getGst() != null
                ? BigDecimal.valueOf(order.getGst()) : BigDecimal.ZERO);
        record.setRealizedPnl(order.getRealizedPnl() != null
                ? BigDecimal.valueOf(order.getRealizedPnl()) : BigDecimal.ZERO);

        // 合约信息
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

        // 期权专有字段
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

        // 订单类型
        if (order.getOrderType() != null) {
            record.setOrderType(order.getOrderType());
        }
        if (order.getLimitPrice() != null) {
            record.setLimitPrice(BigDecimal.valueOf(order.getLimitPrice()));
        }

        return record;
    }

    // ============ 日志输出 ============

    /**
     * 逐条打印订单记录，用于与券商平台核对原始数据
     */
    private void logRecords(List<TigerOrderRecord> records) {
        logger.info("[TigerSync] ====== Sync result: {} filled orders total ======", records.size());

        for (int i = 0; i < records.size(); i++) {
            TigerOrderRecord record = records.get(i);
            logger.info("[TigerSync] [{}] {}", i + 1, record);
        }

        logger.info("[TigerSync] ====== Log output complete ======");
    }
}
