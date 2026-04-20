-- ============================================
-- V25: Remove all "symbol name" fields from trade records and market event tables
-- Created: 2026-04-21
--
-- Rationale:
--   1) trade_records.name was semantically ambiguous (company name for stocks,
--      contract description for options), didn't participate in any business
--      calculation, and was only used for display.
--   2) The xxx_symbol_name columns on events_symbol_change / events_stock_split /
--      events_dividend_in_kind depended on trade_records.name for auto-fill and
--      carried no business meaning either.
--   3) Going forward, security metadata (names, industry, ISIN, etc.) will be
--      owned by a dedicated security master-data table. Keeping these ad-hoc
--      name columns around would mislead future development.
-- ============================================

-- 1. Trade records
ALTER TABLE trade_records
    DROP COLUMN IF EXISTS name;

-- 2. Symbol change events (both old and new name columns)
ALTER TABLE events_symbol_change
    DROP COLUMN IF EXISTS underlying_symbol_name;

ALTER TABLE events_symbol_change
    DROP COLUMN IF EXISTS new_underlying_symbol_name;

-- 3. Stock split events
ALTER TABLE events_stock_split
    DROP COLUMN IF EXISTS underlying_symbol_name;

-- 4. Dividend-in-kind events (held symbol name + dividend symbol name)
ALTER TABLE events_dividend_in_kind
    DROP COLUMN IF EXISTS underlying_symbol_name;

ALTER TABLE events_dividend_in_kind
    DROP COLUMN IF EXISTS dividend_symbol_name;
