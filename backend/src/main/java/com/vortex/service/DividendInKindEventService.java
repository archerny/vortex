package com.vortex.service;

import com.vortex.entity.DividendInKindEvent;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.DividendInKindEventRepository;
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
 * 实物分红事件业务逻辑服务
 */
@Service
@Transactional(readOnly = true)
public class DividendInKindEventService {

    private static final Logger log = LoggerFactory.getLogger(DividendInKindEventService.class);

    @Autowired
    private DividendInKindEventRepository dividendInKindEventRepository;

    @Autowired
    private TradeRecordRepository tradeRecordRepository;

    @Autowired
    private MarketEventProcessingService marketEventProcessingService;

    @Autowired
    private MarketEventHistoryService history;

    /**
     * 查询所有未删除的实物分红事件
     */
    public List<DividendInKindEvent> findAll() {
        return dividendInKindEventRepository.findByIsDeletedFalseOrderByEventDateDesc();
    }

    /**
     * 根据ID查询
     */
    public Optional<DividendInKindEvent> findById(Long id) {
        return dividendInKindEventRepository.findById(id);
    }

    /**
     * 根据证券代码查询
     */
    public List<DividendInKindEvent> findBySymbol(String symbol) {
        return dividendInKindEventRepository.findBySymbolAndIsDeletedFalseOrderByEventDateDesc(symbol);
    }

    /**
     * 根据分红证券代码查询
     */
    public List<DividendInKindEvent> findByDividendSymbol(String dividendSymbol) {
        return dividendInKindEventRepository.findByDividendSymbolAndIsDeletedFalseOrderByEventDateDesc(dividendSymbol);
    }

    /**
     * 根据币种查询
     */
    public List<DividendInKindEvent> findByCurrency(Currency currency) {
        return dividendInKindEventRepository.findByCurrencyAndIsDeletedFalseOrderByEventDateDesc(currency);
    }

    /**
     * 根据事件日期范围查询
     */
    public List<DividendInKindEvent> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return dividendInKindEventRepository.findByEventDateBetweenAndIsDeletedFalseOrderByEventDateDesc(startDate, endDate);
    }

    /**
     * 新增实物分红事件，并自动生成系统交易记录
     */
    @Transactional
    public DividendInKindEvent create(DividendInKindEvent event) {
        history.lock();
        history.assertNewEvent(event, TriggerRefType.DIVIDEND_IN_KIND);
        validateDividendCurrency(event);
        autoFillFromExistingTradeRecord(event);
        DividendInKindEvent saved = dividendInKindEventRepository.save(event);
        log.info("Dividend-in-kind event saved: id={}, symbol={}, dividendSymbol={}, eventDate={}",
                saved.getId(), saved.getSymbol(), saved.getDividendSymbol(), saved.getEventDate());
        marketEventProcessingService.processDividendInKindEvent(saved);
        return saved;
    }

    /**
     * 更新实物分红事件（仅未处理事件），随后生成本事件的系统交易
     */
    @Transactional
    public DividendInKindEvent update(Long id, DividendInKindEvent data) {
        history.lock();
        DividendInKindEvent existing = dividendInKindEventRepository.findById(id)
                .filter(event -> !Boolean.TRUE.equals(event.getIsDeleted()))
                .orElseThrow(() -> new IllegalArgumentException("Market event not found, ID: " + id));
        history.assertEditable(existing, TriggerRefType.DIVIDEND_IN_KIND);
        validateDividendCurrency(data);
        existing.setSymbol(data.getSymbol());
        existing.setCurrency(data.getCurrency());
        existing.setEventDate(data.getEventDate());
        existing.setDividendSymbol(data.getDividendSymbol());
        existing.setDividendCurrency(data.getDividendCurrency());
        existing.setRatioFrom(data.getRatioFrom());
        existing.setRatioTo(data.getRatioTo());
        existing.setFairValuePerShare(data.getFairValuePerShare());
        existing.setDescription(data.getDescription());
        autoFillFromExistingTradeRecord(existing);
        DividendInKindEvent saved = dividendInKindEventRepository.save(existing);
        marketEventProcessingService.processDividendInKindEvent(saved);
        return saved;
    }

    /**
     * 软删除实物分红事件，只清理本事件的派生交易
     */
    @Transactional
    public void delete(Long id) {
        history.lock();
        DividendInKindEvent existing = dividendInKindEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Market event not found, ID: " + id));
        if (Boolean.TRUE.equals(existing.getIsDeleted())) return;
        history.assertCanUndo(existing, TriggerRefType.DIVIDEND_IN_KIND);
        tradeRecordRepository.deleteEventTrades(com.vortex.entity.enums.TradeTrigger.MARKET_EVENT,
                TriggerRefType.DIVIDEND_IN_KIND, existing.getId());
        existing.setIsDeleted(true);
        existing.setProcessed(false);
        existing.setProcessedAt(null);
        dividendInKindEventRepository.save(existing);
    }

    /**
     * Validate that dividendCurrency is provided (required field).
     */
    private void validateDividendCurrency(DividendInKindEvent event) {
        if (event.getDividendCurrency() == null) {
            throw new IllegalArgumentException("Dividend currency is required for dividend-in-kind events");
        }
    }

    /**
     * Auto-fill currency from existing trade records of the held symbol.
     * If the symbol has no trade record, abort to prevent dirty data.
     */
    private void autoFillFromExistingTradeRecord(DividendInKindEvent event) {
        String querySymbol = event.getSymbol();

        var tradeRecordOpt = tradeRecordRepository.findFirstBySymbolAndIsDeletedFalseOrderByTradeDateDesc(querySymbol);

        if (tradeRecordOpt.isPresent()) {
            var record = tradeRecordOpt.get();
            // Auto-fill currency
            if (event.getCurrency() == null) {
                event.setCurrency(record.getCurrency());
                log.debug("Auto-filled currency: {} (from trade record of {})", record.getCurrency(), querySymbol);
            }
        } else {
            log.error("Failed to auto-fill dividend-in-kind event: no trade record found for symbol='{}', aborting to prevent dirty data", querySymbol);
            throw new IllegalArgumentException("No trade record found for symbol '" + querySymbol + "', cannot auto-fill currency. Please create a trade record for this symbol first.");
        }
    }
}
