-- Read-only cutover diagnostics. Confirm the connection target before running.
-- Zero output is legal; this checks state and associations, not historical calculation correctness.
WITH events AS (
    SELECT 'STOCK_SPLIT' AS kind, id, event_date, processed, processed_at, is_deleted,
           ARRAY[symbol] AS symbols FROM events_stock_split
    UNION ALL
    SELECT 'SYMBOL_CHANGE', id, event_date, processed, processed_at, is_deleted,
           ARRAY[old_symbol, new_symbol] FROM events_symbol_change
    UNION ALL
    SELECT 'DIVIDEND_IN_KIND', id, event_date, processed, processed_at, is_deleted,
           ARRAY[symbol, dividend_symbol] FROM events_dividend_in_kind
), output AS (
    SELECT * FROM trade_records WHERE trade_trigger = 'MARKET_EVENT'
), issues AS (
    SELECT 'MISSING_EVENT_STATE' AS issue, e.kind AS record_type, e.id, NULL::bigint AS related_id
    FROM events e WHERE e.processed IS NULL OR e.is_deleted IS NULL
    UNION ALL
    SELECT 'UNPROCESSED_WITH_OUTPUT' AS issue, e.kind AS record_type, e.id, NULL::bigint AS related_id
    FROM events e WHERE e.processed IS NOT TRUE AND EXISTS (
        SELECT 1 FROM output t WHERE t.trigger_ref_type::text = e.kind AND t.trigger_ref_id = e.id)
    UNION ALL
    SELECT 'DELETED_WITH_OUTPUT', e.kind, e.id, NULL::bigint
    FROM events e WHERE e.is_deleted IS TRUE AND EXISTS (
        SELECT 1 FROM output t WHERE t.trigger_ref_type::text = e.kind AND t.trigger_ref_id = e.id)
    UNION ALL
    SELECT 'MISSING_PROCESSED_TIME', e.kind, e.id, NULL::bigint
    FROM events e WHERE e.processed IS TRUE AND e.processed_at IS NULL
    UNION ALL
    SELECT 'ORPHANED_OUTPUT', 'TRADE', t.id, t.trigger_ref_id
    FROM output t WHERE NOT EXISTS (SELECT 1 FROM events e WHERE e.kind = t.trigger_ref_type::text AND e.id = t.trigger_ref_id)
    UNION ALL
    SELECT 'OUTPUT_SCOPE_OR_DATE_MISMATCH', 'TRADE', t.id, e.id
    FROM output t JOIN events e ON e.kind = t.trigger_ref_type::text AND e.id = t.trigger_ref_id
    WHERE t.symbol <> ALL(e.symbols) OR t.trade_date <> e.event_date OR t.is_deleted IS TRUE
    UNION ALL
    SELECT 'SOURCE_MISMATCH', 'TRADE', t.id, t.trigger_ref_id FROM trade_records t
    WHERE t.trade_trigger <> 'MARKET_EVENT' AND t.trigger_ref_type::text IN ('STOCK_SPLIT', 'SYMBOL_CHANGE', 'DIVIDEND_IN_KIND')
    UNION ALL
    SELECT 'SAME_DAY_OVERLAP', a.kind, a.id, b.id
    FROM events a JOIN events b ON (a.kind, a.id) < (b.kind, b.id)
        AND a.event_date = b.event_date AND a.symbols && b.symbols
    WHERE a.processed IS TRUE AND b.processed IS TRUE AND a.is_deleted IS FALSE AND b.is_deleted IS FALSE
    UNION ALL
    SELECT 'ACTIVE_SYNC', 'BATCH', id, NULL::bigint FROM broker_sync_batches
    WHERE status IN ('PENDING', 'PROCESSING', 'CLEANUP_FAILED')
    UNION ALL
    SELECT 'FAILED_BATCH_FOOTPRINT', 'BATCH', b.id, NULL::bigint FROM broker_sync_batches b
    WHERE b.status = 'FAILED' AND EXISTS (SELECT 1 FROM trade_records t WHERE t.sync_batch_id = b.id AND t.is_deleted IS FALSE)
)
SELECT issue, record_type, id, related_id FROM issues ORDER BY issue, record_type, id, related_id;
