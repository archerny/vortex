-- ============================================================
-- Flyway Baseline Script for Existing Database
-- ============================================================
-- Purpose: Initialize flyway_schema_history table in a database
--          that already has all V1~V18 migrations applied manually.
-- Usage:   Run this SQL ONCE against your existing database (e.g. ledgerdb).
--          Do NOT run this on a fresh/empty database — Flyway will handle it automatically.
-- ============================================================

-- 1. Create Flyway metadata table
CREATE TABLE flyway_schema_history (
    installed_rank INTEGER NOT NULL PRIMARY KEY,
    version        VARCHAR(50),
    description    VARCHAR(200) NOT NULL,
    type           VARCHAR(20) NOT NULL,
    script         VARCHAR(1000) NOT NULL,
    checksum       INTEGER,
    installed_by   VARCHAR(100) NOT NULL,
    installed_on   TIMESTAMP NOT NULL DEFAULT now(),
    execution_time INTEGER NOT NULL,
    success        BOOLEAN NOT NULL
);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

-- 2. Mark all existing migrations as already applied
INSERT INTO flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) VALUES
(1,  '1',  'create cash flow tables',                       'SQL', 'V1__create_cash_flow_tables.sql',                          1319016967,  'ledger', now(), 0, true),
(2,  '2',  'add bank field',                                'SQL', 'V2__add_bank_field.sql',                                   2001915993,  'ledger', now(), 0, true),
(3,  '3',  'create trade records',                          'SQL', 'V3__create_trade_records.sql',                              1097347788,  'ledger', now(), 0, true),
(4,  '4',  'create tags',                                   'SQL', 'V4__create_tags.sql',                                      -579939356,  'ledger', now(), 0, true),
(5,  '5',  'alter underlying symbol not null',              'SQL', 'V5__alter_underlying_symbol_not_null.sql',                  -1280285234, 'ledger', now(), 0, true),
(6,  '6',  'rename name comment to underlying name',        'SQL', 'V6__rename_name_comment_to_underlying_name.sql',            802501944,   'ledger', now(), 0, true),
(7,  '7',  'update trade type comment',                     'SQL', 'V7__update_trade_type_comment.sql',                         519508342,   'ledger', now(), 0, true),
(8,  '8',  'create market event tables',                    'SQL', 'V8__create_market_event_tables.sql',                        -1328081816, 'ledger', now(), 0, true),
(9,  '9',  'remove early exercise trade type',              'SQL', 'V9__remove_early_exercise_trade_type.sql',                  -917789024,  'ledger', now(), 0, true),
(10, '10', 'add trade trigger fields',                      'SQL', 'V10__add_trade_trigger_fields.sql',                         1146146207,  'ledger', now(), 0, true),
(11, '11', 'refactor trade type and trigger ref type',      'SQL', 'V11__refactor_trade_type_and_trigger_ref_type.sql',          1069343584,  'ledger', now(), 0, true),
(12, '12', 'remove legacy enum values',                     'SQL', 'V12__remove_legacy_enum_values.sql',                        -2028833442, 'ledger', now(), 0, true),
(13, '13', 'add market event processing fields',            'SQL', 'V13__add_market_event_processing_fields.sql',               284336629,   'ledger', now(), 0, true),
(14, '14', 'rename symbol name to underlying symbol name',  'SQL', 'V14__rename_symbol_name_to_underlying_symbol_name.sql',     -2044580484, 'ledger', now(), 0, true),
(15, '15', 'add new underlying symbol name to symbol change','SQL','V15__add_new_underlying_symbol_name_to_symbol_change.sql',  -1375478375, 'ledger', now(), 0, true),
(16, '16', 'refactor dividend in kind ratio fields',        'SQL', 'V16__refactor_dividend_in_kind_ratio_fields.sql',            -84180471,   'ledger', now(), 0, true),
(17, '17', 'add dividend currency to dividend in kind',     'SQL', 'V17__add_dividend_currency_to_dividend_in_kind.sql',         1807662122,  'ledger', now(), 0, true),
(18, '18', 'make dividend currency not null',               'SQL', 'V18__make_dividend_currency_not_null.sql',                   -230124371,  'ledger', now(), 0, true);
