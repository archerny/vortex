package com.vortex.service;

import com.vortex.entity.enums.TriggerRefType;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;

import java.time.LocalDate;
import java.util.Set;

public class MarketEventConflictException extends CategorizedSyncException {
    private final String code;
    private final Long eventId;
    private final TriggerRefType eventType;
    private final LocalDate eventDate;
    private final Set<String> symbols;

    public MarketEventConflictException(String code, String message, Long eventId,
                                       TriggerRefType eventType, LocalDate eventDate, Set<String> symbols) {
        super(FailureCategory.UNRECOGNIZED, null, message);
        this.code = code;
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.symbols = Set.copyOf(symbols);
    }

    public String getCode() { return code; }
    public Long getEventId() { return eventId; }
    public TriggerRefType getEventType() { return eventType; }
    public LocalDate getEventDate() { return eventDate; }
    public Set<String> getSymbols() { return symbols; }
}
