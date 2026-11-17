package com.vortex.service;

import com.vortex.entity.*;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TriggerRefType;
import com.vortex.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/** Derives restrictions from event facts; stores no separate lock dates. */
@Service
public class MarketEventHistoryService {
    private final LedgerWriteLock writeLock;
    private final StockSplitEventRepository splits;
    private final SymbolChangeEventRepository changes;
    private final DividendInKindEventRepository dividends;
    private final TradeRecordRepository trades;
    private final BrokerSyncBatchRepository batches;

    public MarketEventHistoryService(LedgerWriteLock writeLock, StockSplitEventRepository splits,
                                    SymbolChangeEventRepository changes, DividendInKindEventRepository dividends,
                                    TradeRecordRepository trades, BrokerSyncBatchRepository batches) {
        this.writeLock = writeLock;
        this.splits = splits;
        this.changes = changes;
        this.dividends = dividends;
        this.trades = trades;
        this.batches = batches;
    }

    public void lock() { writeLock.acquire(); }

    public void assertUserTradeWritable(TradeRecord record) {
        if (record.getTradeTrigger() == TradeTrigger.MARKET_EVENT) {
            throw conflict("SYSTEM_TRADE_READ_ONLY", "市场事件生成的交易只能通过撤销所属事件维护。", null, null, Set.of());
        }
        assertHistoryWritable(record);
    }

    public void assertHistoryWritable(TradeRecord record) {
        String symbol = validSymbol(record.getSymbol());
        LocalDate date = record.getTradeDate();
        if (date == null) throw new IllegalArgumentException("Trade date is required");
        for (Event event : processedEvents()) {
            if (event.symbols.contains(symbol) && date.isBefore(event.value.getEventDate())) {
                throw conflict("HISTORY_LOCKED", "关联证券的历史已被市场事件锁定，请先按顺序回退后续相关数据并撤销事件。",
                        event.value, event.type, event.symbols);
            }
        }
    }

    public void assertNewEvent(BaseMarketEvent event, TriggerRefType type) {
        if (event.getId() != null || !Boolean.FALSE.equals(event.getProcessed()) || !Boolean.FALSE.equals(event.getIsDeleted())
                || event.getCreatedAt() != null || event.getUpdatedAt() != null || event.getProcessedAt() != null) {
            throw new IllegalArgumentException("New event id and processing state are managed by the server");
        }
        assertCanProcess(event, type);
    }

    public void assertCanProcess(BaseMarketEvent event, TriggerRefType type) {
        assertNoActiveSync();
        assertValidState(event, type);
        validateEvent(event);
        assertNoResidualTrades(event, type);
        assertTail(event, type);
    }

    public void assertEditable(BaseMarketEvent event, TriggerRefType type) {
        assertNoActiveSync();
        assertValidState(event, type);
        if (Boolean.TRUE.equals(event.getProcessed())) {
            throw conflict("PROCESSED_EVENT_IMMUTABLE", "已处理事件不能直接修改，请先满足撤销条件后撤销，再重新创建。",
                    event, type, symbols(event));
        }
        assertNoResidualTrades(event, type);
    }

    public void assertCanUndo(BaseMarketEvent event, TriggerRefType type) {
        assertNoActiveSync();
        assertValidState(event, type);
        if (Boolean.TRUE.equals(event.getProcessed())) {
            validateEvent(event);
            assertTail(event, type);
        } else {
            assertNoResidualTrades(event, type);
        }
    }

    private void assertNoResidualTrades(BaseMarketEvent event, TriggerRefType type) {
        if (event.getId() != null && trades.existsByTradeTriggerAndTriggerRefTypeAndTriggerRefId(
                TradeTrigger.MARKET_EVENT, type, event.getId())) {
            throw conflict("LEGACY_EVENT_STATE", "未处理事件已有系统交易，请先核对存量状态，不会自动修改或删除。",
                    event, type, symbols(event));
        }
    }

    private void assertValidState(BaseMarketEvent event, TriggerRefType type) {
        if (!Boolean.FALSE.equals(event.getIsDeleted()) || event.getProcessed() == null) {
            throw conflict("LEGACY_EVENT_STATE", "事件状态异常，请先核对存量记录，不会自动处理或修复。",
                    event, type, symbols(event));
        }
    }

    private void assertTail(BaseMarketEvent event, TriggerRefType type) {
        Set<String> scope = symbols(event);
        for (Event other : processedEvents()) {
            boolean self = event.getId() != null && type == other.type && event.getId().equals(other.value.getId());
            if (!self && !Collections.disjoint(scope, other.symbols)
                    && !other.value.getEventDate().isBefore(event.getEventDate())) {
                throw conflict("EVENT_ORDER_CONFLICT", "存在同日或更晚的关联事件，请先按逆序撤销后续相关事件。",
                        other.value, other.type, other.symbols);
            }
        }
        if (trades.existsBySymbolInAndTradeDateGreaterThanEqualAndTradeTriggerNotAndIsDeletedFalse(
                scope, event.getEventDate(), TradeTrigger.MARKET_EVENT)) {
            throw conflict("FOLLOWING_TRADES_EXIST", "关联证券已有事件当天或之后的普通交易，请先手动回退后续相关数据。",
                    event, type, scope);
        }
    }

    private void assertNoActiveSync() {
        if (batches.findFirstByStatusInOrderByIdDesc(Set.of("PENDING", "PROCESSING", "CLEANUP_FAILED")).isPresent()) {
            throw conflict("SYNC_IN_PROGRESS", "同步批次尚未结束或清理，请先完成同步处理后再操作市场事件。",
                    null, null, Set.of());
        }
    }

    private List<Event> processedEvents() {
        List<Event> result = new ArrayList<>();
        add(result, splits.findByIsDeletedFalseOrderByEventDateDesc(), TriggerRefType.STOCK_SPLIT);
        add(result, changes.findByIsDeletedFalseOrderByEventDateDesc(), TriggerRefType.SYMBOL_CHANGE);
        add(result, dividends.findByIsDeletedFalseOrderByEventDateDesc(), TriggerRefType.DIVIDEND_IN_KIND);
        result.sort(Comparator.comparing((Event e) -> e.value.getEventDate()).reversed()
                .thenComparing(e -> e.type.name()).thenComparing(e -> e.value.getId(), Comparator.nullsLast(Long::compareTo)));
        return result;
    }

    private void add(List<Event> result, List<? extends BaseMarketEvent> events, TriggerRefType type) {
        for (BaseMarketEvent event : events) {
            if (Boolean.TRUE.equals(event.getProcessed()) && !Boolean.TRUE.equals(event.getIsDeleted())) {
                if (event.getEventDate() == null) throw new IllegalArgumentException("Stored event date is missing");
                result.add(new Event(event, type, symbols(event)));
            }
        }
    }

    private void validateEvent(BaseMarketEvent event) {
        if (event.getEventDate() == null || event.getEventDate().equals(LocalDate.MIN)) {
            throw new IllegalArgumentException("Event date is required and must support a preceding date");
        }
        symbols(event);
        if (event instanceof StockSplitEvent split && (split.getRatioFrom() == null || split.getRatioTo() == null
                || split.getRatioFrom() <= 0 || split.getRatioTo() <= 0)) {
            throw new IllegalArgumentException("Split ratios must be positive");
        }
        if (event instanceof DividendInKindEvent dividend && (dividend.getRatioFrom() == null || dividend.getRatioTo() == null
                || dividend.getRatioFrom() <= 0 || dividend.getRatioTo() <= 0 || dividend.getDividendCurrency() == null)) {
            throw new IllegalArgumentException("Dividend ratios must be positive and dividend currency is required");
        }
    }

    private Set<String> symbols(BaseMarketEvent event) {
        Set<String> result = new TreeSet<>();
        if (event instanceof SymbolChangeEvent change) {
            result.add(validSymbol(change.getOldSymbol()));
            result.add(validSymbol(change.getNewSymbol()));
        } else {
            result.add(validSymbol(event.getSymbol()));
            if (event instanceof DividendInKindEvent dividend) result.add(validSymbol(dividend.getDividendSymbol()));
        }
        return Set.copyOf(result);
    }

    private String validSymbol(String symbol) {
        if (symbol == null || symbol.isBlank() || !symbol.equals(symbol.strip())) {
            throw new IllegalArgumentException("Security code must be nonblank and have no surrounding whitespace");
        }
        return symbol;
    }

    private MarketEventConflictException conflict(String code, String reason, BaseMarketEvent event,
                                                   TriggerRefType type, Set<String> symbols) {
        return new MarketEventConflictException(code, reason, event == null ? null : event.getId(), type,
                event == null ? null : event.getEventDate(), symbols);
    }

    private record Event(BaseMarketEvent value, TriggerRefType type, Set<String> symbols) {}
}
