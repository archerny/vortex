package com.vortex.service;

import com.vortex.dto.PositionSnapshot;
import com.vortex.entity.*;
import com.vortex.entity.enums.*;
import com.vortex.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 市场事件处理服务
 * 核心功能：顺序处理单个市场事件，生成其系统交易记录。
 *
 * 设计详见 design/0035、0036、0037。
 *
 * 历史依赖通过 MarketEventHistoryService 保护，不再执行级联重算。
 */
@Service
public class MarketEventProcessingService {

    private static final Logger log = LoggerFactory.getLogger(MarketEventProcessingService.class);

    @Autowired
    private PositionService positionService;

    @Autowired
    private TradeRecordRepository tradeRecordRepository;

    @Autowired
    private StockSplitEventRepository stockSplitEventRepository;

    @Autowired
    private SymbolChangeEventRepository symbolChangeEventRepository;

    @Autowired
    private DividendInKindEventRepository dividendInKindEventRepository;

    @Autowired
    private MarketEventHistoryService history;

    @Transactional
    public void processStockSplitEvent(StockSplitEvent event) {
        process(event, TriggerRefType.STOCK_SPLIT);
    }

    @Transactional
    public void processSymbolChangeEvent(SymbolChangeEvent event) {
        process(event, TriggerRefType.SYMBOL_CHANGE);
    }

    @Transactional
    public void processDividendInKindEvent(DividendInKindEvent event) {
        process(event, TriggerRefType.DIVIDEND_IN_KIND);
    }

    private void process(BaseMarketEvent event, TriggerRefType type) {
        history.lock();
        if (event.getId() == null) throw new IllegalArgumentException("Save market event before processing");
        event = switch (type) {
            case STOCK_SPLIT -> stockSplitEventRepository.findById(event.getId()).orElse(null);
            case SYMBOL_CHANGE -> symbolChangeEventRepository.findById(event.getId()).orElse(null);
            case DIVIDEND_IN_KIND -> dividendInKindEventRepository.findById(event.getId()).orElse(null);
            default -> throw new IllegalArgumentException("Unsupported market event type");
        };
        if (event == null || !Boolean.FALSE.equals(event.getIsDeleted()) || event.getProcessed() == null) {
            throw new IllegalArgumentException("Market event is missing, revoked or has invalid processing state");
        }
        if (Boolean.TRUE.equals(event.getProcessed())) return;
        history.assertCanProcess(event, type);
        List<TradeRecord> records = switch (type) {
            case STOCK_SPLIT -> processStockSplit((StockSplitEvent) event);
            case SYMBOL_CHANGE -> processSymbolChange((SymbolChangeEvent) event);
            case DIVIDEND_IN_KIND -> processDividendInKind((DividendInKindEvent) event);
            default -> throw new IllegalArgumentException("Unsupported market event type");
        };
        if (!records.isEmpty()) tradeRecordRepository.saveAll(records);
        event.setProcessed(true);
        event.setProcessedAt(LocalDateTime.now());
        saveEvent(event, type);
    }

    // ============================================================
    // 拆股事件处理逻辑
    // ============================================================

    /**
     * 处理拆股事件
     * 对持有该 symbol 的每个券商，生成一条 BUY 记录，数量 = 原持仓 × (ratioTo/ratioFrom - 1)
     * 价格=0，金额=0，费用=0
     */
    private List<TradeRecord> processStockSplit(StockSplitEvent event) {
        List<TradeRecord> records = new ArrayList<>();

        // 计算事件前一天的持仓快照
        LocalDate dayBefore = event.getEventDate().minusDays(1);
        List<PositionSnapshot> positions = positionService.calculatePositions(dayBefore, null);

        // 筛选持有该 symbol 的持仓
        List<PositionSnapshot> relevantPositions = positions.stream()
                .filter(p -> event.getSymbol().equals(p.getSymbol()))
                .filter(p -> p.getQuantity() != null && p.getQuantity() > 0)
                .collect(Collectors.toList());

        if (relevantPositions.isEmpty()) {
            log.info("Stock split event (id={}, symbol={}): no position at event date, skipped", event.getId(), event.getSymbol());
            return records;
        }

        for (PositionSnapshot position : relevantPositions) {
            // 新数量 = quantity × ratioTo / ratioFrom
            // 增量 = quantity × (ratioTo/ratioFrom - 1)
            long newQty = Math.round((double) position.getQuantity() * event.getRatioTo() / event.getRatioFrom());
            int delta = (int) (newQty - position.getQuantity());

            if (delta <= 0) {
                continue;
            }

            TradeRecord record = new TradeRecord();
            record.setTradeDate(event.getEventDate());
            record.setBrokerId(position.getBrokerId());
            record.setAssetType(position.getAssetType());
            record.setSymbol(event.getSymbol());
            record.setUnderlyingSymbol(position.getUnderlyingSymbol() != null ? position.getUnderlyingSymbol() : event.getSymbol());
            record.setTradeType(TradeType.BUY);
            record.setQuantity(delta);
            record.setPrice(BigDecimal.ZERO);
            record.setAmount(BigDecimal.ZERO);
            record.setFee(BigDecimal.ZERO);
            record.setCurrency(event.getCurrency() != null ? event.getCurrency() : position.getCurrency());
            record.setTradeTrigger(TradeTrigger.MARKET_EVENT);
            record.setTriggerRefId(event.getId());
            record.setTriggerRefType(TriggerRefType.STOCK_SPLIT);
            record.setIsDeleted(false);

            records.add(record);
            log.debug("Stock split: brokerId={}, symbol={}, originalQty={}, delta={}",
                    position.getBrokerId(), event.getSymbol(), position.getQuantity(), delta);
        }

        return records;
    }

    // ============================================================
    // 代码变更事件处理逻辑
    // ============================================================

    /**
     * 处理代码变更事件
     * 对持有 oldSymbol 的每个券商生成两条记录：
     * ① SELL oldSymbol 全部持仓（价格=平均成本）
     * ② BUY newSymbol 同等数量（价格=平均成本）
     * 排除期权类型
     */
    private List<TradeRecord> processSymbolChange(SymbolChangeEvent event) {
        List<TradeRecord> records = new ArrayList<>();

        // 计算事件前一天的持仓快照
        LocalDate dayBefore = event.getEventDate().minusDays(1);
        List<PositionSnapshot> positions = positionService.calculatePositions(dayBefore, null);

        // 筛选持有 oldSymbol 的持仓，排除期权
        List<PositionSnapshot> relevantPositions = positions.stream()
                .filter(p -> event.getOldSymbol().equals(p.getSymbol()))
                .filter(p -> p.getQuantity() != null && p.getQuantity() > 0)
                .filter(p -> p.getAssetType() != AssetType.OPTION_CALL && p.getAssetType() != AssetType.OPTION_PUT)
                .collect(Collectors.toList());

        if (relevantPositions.isEmpty()) {
            log.info("Symbol change event (id={}, {}->{}): no position at event date, skipped",
                    event.getId(), event.getOldSymbol(), event.getNewSymbol());
            return records;
        }

        for (PositionSnapshot position : relevantPositions) {
            // 计算平均持仓成本
            BigDecimal avgCost = calculateAverageCost(position.getSymbol(), position.getBrokerId(), dayBefore);
            BigDecimal totalCost = avgCost.multiply(BigDecimal.valueOf(position.getQuantity()));

            // ① SELL oldSymbol
            TradeRecord sellRecord = new TradeRecord();
            sellRecord.setTradeDate(event.getEventDate());
            sellRecord.setBrokerId(position.getBrokerId());
            sellRecord.setAssetType(position.getAssetType());
            sellRecord.setSymbol(event.getOldSymbol());
            sellRecord.setUnderlyingSymbol(event.getOldSymbol());
            sellRecord.setTradeType(TradeType.SELL);
            sellRecord.setQuantity(position.getQuantity());
            sellRecord.setPrice(avgCost);
            sellRecord.setAmount(totalCost);
            sellRecord.setFee(BigDecimal.ZERO);
            sellRecord.setCurrency(event.getCurrency() != null ? event.getCurrency() : position.getCurrency());
            sellRecord.setTradeTrigger(TradeTrigger.MARKET_EVENT);
            sellRecord.setTriggerRefId(event.getId());
            sellRecord.setTriggerRefType(TriggerRefType.SYMBOL_CHANGE);
            sellRecord.setIsDeleted(false);
            records.add(sellRecord);

            // ② BUY newSymbol
            TradeRecord buyRecord = new TradeRecord();
            buyRecord.setTradeDate(event.getEventDate());
            buyRecord.setBrokerId(position.getBrokerId());
            buyRecord.setAssetType(position.getAssetType());
            buyRecord.setSymbol(event.getNewSymbol());
            buyRecord.setUnderlyingSymbol(event.getNewSymbol());
            buyRecord.setTradeType(TradeType.BUY);
            buyRecord.setQuantity(position.getQuantity());
            buyRecord.setPrice(avgCost);
            buyRecord.setAmount(totalCost);
            buyRecord.setFee(BigDecimal.ZERO);
            buyRecord.setCurrency(event.getCurrency() != null ? event.getCurrency() : position.getCurrency());
            buyRecord.setTradeTrigger(TradeTrigger.MARKET_EVENT);
            buyRecord.setTriggerRefId(event.getId());
            buyRecord.setTriggerRefType(TriggerRefType.SYMBOL_CHANGE);
            buyRecord.setIsDeleted(false);
            records.add(buyRecord);

            log.debug("Symbol change: brokerId={}, SELL {} {} shares (avgCost={}), BUY {} {} shares",
                    position.getBrokerId(), event.getOldSymbol(), position.getQuantity(), avgCost,
                    event.getNewSymbol(), position.getQuantity());
        }

        return records;
    }

    // ============================================================
    // 实物分红事件处理逻辑
    // ============================================================

    /**
     * Process dividend-in-kind event.
     * For each broker holding the symbol, generate a BUY record for the dividendSymbol.
     * Quantity = floor(holdingQty × ratioTo / ratioFrom)
     * Price = fairValuePerShare
     */
    private List<TradeRecord> processDividendInKind(DividendInKindEvent event) {
        List<TradeRecord> records = new ArrayList<>();

        // 计算事件前一天的持仓快照
        LocalDate dayBefore = event.getEventDate().minusDays(1);
        List<PositionSnapshot> positions = positionService.calculatePositions(dayBefore, null);

        // 筛选持有该 symbol 且 quantity > 0 的持仓
        List<PositionSnapshot> relevantPositions = positions.stream()
                .filter(p -> event.getSymbol().equals(p.getSymbol()))
                .filter(p -> p.getQuantity() != null && p.getQuantity() > 0)
                .collect(Collectors.toList());

        if (relevantPositions.isEmpty()) {
            log.info("Dividend-in-kind event (id={}, symbol={}): no position at event date, skipped",
                    event.getId(), event.getSymbol());
            return records;
        }

        for (PositionSnapshot position : relevantPositions) {
            // Dividend qty = floor(holdingQty × ratioTo / ratioFrom)
            int dividendQty = BigDecimal.valueOf(position.getQuantity())
                    .multiply(BigDecimal.valueOf(event.getRatioTo()))
                    .divide(BigDecimal.valueOf(event.getRatioFrom()), 0, RoundingMode.FLOOR)
                    .intValue();

            if (dividendQty <= 0) {
                continue;
            }

            // 公允价格（如果未设置则为0）
            BigDecimal fairValue = event.getFairValuePerShare() != null ? event.getFairValuePerShare() : BigDecimal.ZERO;
            BigDecimal amount = fairValue.multiply(BigDecimal.valueOf(dividendQty));

            TradeRecord record = new TradeRecord();
            record.setTradeDate(event.getEventDate());
            record.setBrokerId(position.getBrokerId());
            record.setAssetType(AssetType.STOCK); // 分红获得的是股票
            record.setSymbol(event.getDividendSymbol());
            record.setUnderlyingSymbol(event.getDividendSymbol());
            record.setTradeType(TradeType.BUY);
            record.setQuantity(dividendQty);
            record.setPrice(fairValue);
            record.setAmount(amount);
            record.setFee(BigDecimal.ZERO);
            // dividend_currency is a required field, use it directly for the dividend trade record
            record.setCurrency(event.getDividendCurrency());
            record.setTradeTrigger(TradeTrigger.MARKET_EVENT);
            record.setTriggerRefId(event.getId());
            record.setTriggerRefType(TriggerRefType.DIVIDEND_IN_KIND);
            record.setIsDeleted(false);

            records.add(record);
            log.debug("Dividend-in-kind: brokerId={}, symbol={}, holdingQty={}, dividendQty={}, fairValue={}",
                    position.getBrokerId(), event.getDividendSymbol(), position.getQuantity(),
                    dividendQty, fairValue);
        }

        return records;
    }

    // ============================================================
    // 辅助方法
    // ============================================================

    /**
     * 计算某个 symbol 在某个券商下截止指定日期的加权平均持仓成本
     *
     * 使用移动加权平均法：
     * - BUY:  持仓成本池 += 买入金额，持仓数量 += 买入数量
     * - SELL: 部分卖出按当前平均成本减记，完全清仓结清剩余成本池，持仓数量 -= 卖出数量
     * 最终平均成本 = 持仓成本池 / 当前持仓数量
     *
     * TODO: 当前使用移动加权平均法，未来在做盈亏分析时需要深度考虑成本计算方式，
     *       可能需要支持 FIFO、LIFO 等不同算法，届时需重构此方法。
     */
    private BigDecimal calculateAverageCost(String symbol, Long brokerId, LocalDate asOfDate) {
        List<TradeRecord> records = tradeRecordRepository
                .findByTradeDateLessThanEqualAndBrokerIdAndIsDeletedFalseOrderByTradeDateAsc(asOfDate, brokerId);

        BigDecimal costPool = BigDecimal.ZERO;  // 持仓成本池
        int holdingQty = 0;                      // 当前持仓数量

        for (TradeRecord record : records) {
            if (!symbol.equals(record.getSymbol())) {
                continue;
            }
            BigDecimal amount = record.getAmount() != null ? record.getAmount() : BigDecimal.ZERO;
            if (record.getTradeType() == TradeType.BUY) {
                costPool = costPool.add(amount);
                holdingQty += record.getQuantity();
            } else if (record.getTradeType() == TradeType.SELL) {
                if (holdingQty > 0 && record.getQuantity() == holdingQty) {
                    // Final disposal consumes the remaining cost, including rounding tails.
                    costPool = BigDecimal.ZERO;
                } else if (holdingQty > 0) {
                    // 卖出时按当前平均成本减少成本池
                    BigDecimal avgCostBeforeSell = costPool.divide(BigDecimal.valueOf(holdingQty), 6, RoundingMode.HALF_UP);
                    costPool = costPool.subtract(avgCostBeforeSell.multiply(BigDecimal.valueOf(record.getQuantity())));
                }
                holdingQty -= record.getQuantity();
            }
        }

        if (holdingQty <= 0) {
            return BigDecimal.ZERO;
        }

        return costPool.divide(BigDecimal.valueOf(holdingQty), 4, RoundingMode.HALF_UP);
    }

    private void saveEvent(BaseMarketEvent event, TriggerRefType type) {
        switch (type) {
            case STOCK_SPLIT -> stockSplitEventRepository.save((StockSplitEvent) event);
            case SYMBOL_CHANGE -> symbolChangeEventRepository.save((SymbolChangeEvent) event);
            case DIVIDEND_IN_KIND -> dividendInKindEventRepository.save((DividendInKindEvent) event);
            default -> throw new IllegalArgumentException("Unsupported market event type");
        }
    }
}
