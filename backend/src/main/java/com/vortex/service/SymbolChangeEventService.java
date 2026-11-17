package com.vortex.service;

import com.vortex.entity.SymbolChangeEvent;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.SymbolChangeEventRepository;
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
 * 代码变更事件业务逻辑服务
 */
@Service
@Transactional(readOnly = true)
public class SymbolChangeEventService {

    private static final Logger log = LoggerFactory.getLogger(SymbolChangeEventService.class);

    @Autowired
    private SymbolChangeEventRepository symbolChangeEventRepository;

    @Autowired
    private TradeRecordRepository tradeRecordRepository;

    @Autowired
    private MarketEventProcessingService marketEventProcessingService;

    @Autowired
    private MarketEventHistoryService history;

    /**
     * 查询所有未删除的代码变更事件
     */
    public List<SymbolChangeEvent> findAll() {
        return symbolChangeEventRepository.findByIsDeletedFalseOrderByEventDateDesc();
    }

    /**
     * 根据ID查询
     */
    public Optional<SymbolChangeEvent> findById(Long id) {
        return symbolChangeEventRepository.findById(id);
    }

    /**
     * 根据证券代码查询
     */
    public List<SymbolChangeEvent> findBySymbol(String symbol) {
        return symbolChangeEventRepository.findBySymbolAndIsDeletedFalseOrderByEventDateDesc(symbol);
    }

    /**
     * 根据旧代码查询
     */
    public List<SymbolChangeEvent> findByOldSymbol(String oldSymbol) {
        return symbolChangeEventRepository.findByOldSymbolAndIsDeletedFalseOrderByEventDateDesc(oldSymbol);
    }

    /**
     * 根据新代码查询
     */
    public List<SymbolChangeEvent> findByNewSymbol(String newSymbol) {
        return symbolChangeEventRepository.findByNewSymbolAndIsDeletedFalseOrderByEventDateDesc(newSymbol);
    }

    /**
     * 根据币种查询
     */
    public List<SymbolChangeEvent> findByCurrency(Currency currency) {
        return symbolChangeEventRepository.findByCurrencyAndIsDeletedFalseOrderByEventDateDesc(currency);
    }

    /**
     * 根据事件日期范围查询
     */
    public List<SymbolChangeEvent> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return symbolChangeEventRepository.findByEventDateBetweenAndIsDeletedFalseOrderByEventDateDesc(startDate, endDate);
    }

    /**
     * 新增代码变更事件，并自动生成系统交易记录
     * 自动填充以下字段：
     * - symbol = oldSymbol（事件发生前的证券代码）
     * - currency：从 oldSymbol 的已有交易记录中获取
     */
    @Transactional
    public SymbolChangeEvent create(SymbolChangeEvent event) {
        history.lock();
        history.assertNewEvent(event, TriggerRefType.SYMBOL_CHANGE);
        // 自动填充：symbol = oldSymbol（事件是对旧代码发生的）
        event.setSymbol(event.getOldSymbol());

        // 从 oldSymbol 的已有交易记录中自动填充 currency
        autoFillFromExistingTradeRecord(event);

        SymbolChangeEvent saved = symbolChangeEventRepository.save(event);
        log.info("Symbol change event saved: id={}, {}=>{}, eventDate={}",
                saved.getId(), saved.getOldSymbol(), saved.getNewSymbol(), saved.getEventDate());
        marketEventProcessingService.processSymbolChangeEvent(saved);
        return saved;
    }

    /**
     * 更新代码变更事件（仅未处理事件），随后生成本事件的系统交易
     */
    @Transactional
    public SymbolChangeEvent update(Long id, SymbolChangeEvent data) {
        history.lock();
        SymbolChangeEvent existing = symbolChangeEventRepository.findById(id)
                .filter(event -> !Boolean.TRUE.equals(event.getIsDeleted()))
                .orElseThrow(() -> new IllegalArgumentException("Market event not found, ID: " + id));
        history.assertEditable(existing, TriggerRefType.SYMBOL_CHANGE);
        existing.setSymbol(data.getOldSymbol());
        existing.setEventDate(data.getEventDate());
        existing.setOldSymbol(data.getOldSymbol());
        existing.setNewSymbol(data.getNewSymbol());
        existing.setDescription(data.getDescription());
        autoFillFromExistingTradeRecord(existing);
        SymbolChangeEvent saved = symbolChangeEventRepository.save(existing);
        marketEventProcessingService.processSymbolChangeEvent(saved);
        return saved;
    }

    /**
     * 软删除代码变更事件，只清理本事件的派生交易
     */
    @Transactional
    public void delete(Long id) {
        history.lock();
        SymbolChangeEvent existing = symbolChangeEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Market event not found, ID: " + id));
        if (Boolean.TRUE.equals(existing.getIsDeleted())) return;
        history.assertCanUndo(existing, TriggerRefType.SYMBOL_CHANGE);
        tradeRecordRepository.deleteEventTrades(com.vortex.entity.enums.TradeTrigger.MARKET_EVENT,
                TriggerRefType.SYMBOL_CHANGE, existing.getId());
        existing.setIsDeleted(true);
        existing.setProcessed(false);
        existing.setProcessedAt(null);
        symbolChangeEventRepository.save(existing);
    }

    /**
     * 从 oldSymbol 的已有交易记录中自动填充 currency
     * 如果 oldSymbol 有交易记录，则用其 currency 填充
     * 如果没有交易记录，则抛异常阻止脏数据写入
     */
    private void autoFillFromExistingTradeRecord(SymbolChangeEvent event) {
        var tradeRecordOpt = tradeRecordRepository.findFirstBySymbolAndIsDeletedFalseOrderByTradeDateDesc(event.getOldSymbol());

        if (tradeRecordOpt.isPresent()) {
            var record = tradeRecordOpt.get();
            // 自动填充币种
            if (event.getCurrency() == null) {
                event.setCurrency(record.getCurrency());
                log.debug("Auto-filled currency: {} (from trade record of {})", record.getCurrency(), event.getOldSymbol());
            }
        } else {
            log.error("Failed to auto-fill symbol change event: no trade record found for oldSymbol='{}', aborting to prevent dirty data", event.getOldSymbol());
            throw new IllegalArgumentException("No trade record found for symbol '" + event.getOldSymbol() + "', cannot auto-fill currency. Please create a trade record for this symbol first.");
        }
    }
}
