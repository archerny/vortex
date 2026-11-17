package com.vortex.service;

import com.vortex.entity.StockSplitEvent;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.StockSplitEventRepository;
import com.vortex.repository.TradeRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 拆股事件业务逻辑服务
 */
@Service
@Transactional(readOnly = true)
public class StockSplitEventService {

    private static final Logger log = LoggerFactory.getLogger(StockSplitEventService.class);

    @Autowired
    private StockSplitEventRepository stockSplitEventRepository;

    @Autowired
    private TradeRecordRepository tradeRecordRepository;

    @Autowired
    private MarketEventProcessingService marketEventProcessingService;

    @Autowired
    private MarketEventHistoryService history;

    /**
     * 查询所有未删除的拆股事件
     */
    public List<StockSplitEvent> findAll() {
        return stockSplitEventRepository.findByIsDeletedFalseOrderByEventDateDesc();
    }

    /**
     * 根据ID查询
     */
    public Optional<StockSplitEvent> findById(Long id) {
        return stockSplitEventRepository.findById(id);
    }

    /**
     * 根据证券代码查询
     */
    public List<StockSplitEvent> findBySymbol(String symbol) {
        return stockSplitEventRepository.findBySymbolAndIsDeletedFalseOrderByEventDateDesc(symbol);
    }

    /**
     * 根据币种查询
     */
    public List<StockSplitEvent> findByCurrency(Currency currency) {
        return stockSplitEventRepository.findByCurrencyAndIsDeletedFalseOrderByEventDateDesc(currency);
    }

    /**
     * 根据事件日期范围查询
     */
    public List<StockSplitEvent> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return stockSplitEventRepository.findByEventDateBetweenAndIsDeletedFalseOrderByEventDateDesc(startDate, endDate);
    }

    /**
     * 新增拆股事件，并自动生成系统交易记录
     * 自动填充以下字段：
     * - currency：从 symbol 的已有交易记录中获取
     */
    @Transactional
    public StockSplitEvent create(StockSplitEvent event) {
        history.lock();
        history.assertNewEvent(event, TriggerRefType.STOCK_SPLIT);
        // 从 symbol 的已有交易记录中自动填充 currency
        autoFillFromExistingTradeRecord(event);

        StockSplitEvent saved = stockSplitEventRepository.save(event);
        log.info("Stock split event saved: id={}, symbol={}, eventDate={}", saved.getId(), saved.getSymbol(), saved.getEventDate());
        marketEventProcessingService.processStockSplitEvent(saved);
        return saved;
    }

    /**
     * 更新拆股事件（仅未处理事件），随后生成本事件的系统交易
     */
    @Transactional
    public StockSplitEvent update(Long id, StockSplitEvent data) {
        history.lock();
        StockSplitEvent existing = stockSplitEventRepository.findById(id)
                .filter(event -> !Boolean.TRUE.equals(event.getIsDeleted()))
                .orElseThrow(() -> new IllegalArgumentException("Market event not found, ID: " + id));
        history.assertEditable(existing, TriggerRefType.STOCK_SPLIT);
        existing.setSymbol(data.getSymbol());
        existing.setEventDate(data.getEventDate());
        existing.setRatioFrom(data.getRatioFrom());
        existing.setRatioTo(data.getRatioTo());
        existing.setDescription(data.getDescription());
        autoFillFromExistingTradeRecord(existing);
        StockSplitEvent saved = stockSplitEventRepository.save(existing);
        marketEventProcessingService.processStockSplitEvent(saved);
        return saved;
    }

    /**
     * 软删除拆股事件，只清理本事件的派生交易
     */
    @Transactional
    public void delete(Long id) {
        history.lock();
        StockSplitEvent existing = stockSplitEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Market event not found, ID: " + id));
        if (Boolean.TRUE.equals(existing.getIsDeleted())) return;
        history.assertCanUndo(existing, TriggerRefType.STOCK_SPLIT);
        tradeRecordRepository.deleteEventTrades(com.vortex.entity.enums.TradeTrigger.MARKET_EVENT,
                TriggerRefType.STOCK_SPLIT, existing.getId());
        existing.setIsDeleted(true);
        existing.setProcessed(false);
        existing.setProcessedAt(null);
        stockSplitEventRepository.save(existing);
    }

    /**
     * 从 symbol 的已有交易记录中自动填充 currency
     * 如果 symbol 有交易记录，则用其 currency 填充
     * 如果没有交易记录，则抛异常阻止脏数据写入
     */
    private void autoFillFromExistingTradeRecord(StockSplitEvent event) {
        String querySymbol = event.getSymbol();

        var tradeRecordOpt = tradeRecordRepository.findFirstBySymbolAndIsDeletedFalseOrderByTradeDateDesc(querySymbol);

        if (tradeRecordOpt.isPresent()) {
            var record = tradeRecordOpt.get();
            // 自动填充币种
            if (event.getCurrency() == null) {
                event.setCurrency(record.getCurrency());
                log.debug("Auto-filled currency: {} (from trade record of {})", record.getCurrency(), querySymbol);
            }
        } else {
            log.error("Failed to auto-fill stock split event: no trade record found for symbol='{}', aborting to prevent dirty data", querySymbol);
            throw new IllegalArgumentException("No trade record found for symbol '" + querySymbol + "', cannot auto-fill currency. Please create a trade record for this symbol first.");
        }
    }
}
