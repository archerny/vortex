-- =============================================
-- V27: Seed broker_code='tiger' for existing Tiger broker record
-- =============================================
-- Idempotent update: if the brokers table already contains a Tiger record
-- (matched by name), ensure its broker_code is set to 'tiger' so the
-- TigerSyncAdapter can resolve the broker_id via BrokerRepository.findByBrokerCode.
--
-- Safe to re-run:
--   - WHERE clause filters to rows whose broker_code is NULL or not yet 'tiger'.
--   - If no Tiger row exists yet, this is a no-op; the user may create the
--     broker record manually via the UI and set broker_code='tiger' themselves,
--     or a future migration can insert one once the canonical name is decided.

UPDATE brokers
SET broker_code = 'tiger'
WHERE broker_code IS DISTINCT FROM 'tiger'
  AND (
      broker_name ILIKE '%tiger%'
      OR broker_name LIKE '%老虎%'
  );
