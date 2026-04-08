#!/usr/bin/env bash
# ============================================================
# Reset Dev Database
# ============================================================
# Drop ALL objects (tables, sequences, triggers, enum types,
# Flyway history) in the dev database, returning it to a
# completely empty state. Next application startup will let
# Flyway re-run all migrations from scratch.
#
# Required environment variables:
#   VORTEX_DEV_DB_HOST     - PostgreSQL host     (e.g. 10.211.55.3)
#   VORTEX_DEV_DB_PORT     - PostgreSQL port     (e.g. 5432)
#   VORTEX_DEV_DB_NAME     - Database name       (e.g. vtxdev)
#   VORTEX_DEV_DB_USER     - Database user       (e.g. vtxdev)
#   VORTEX_DEV_DB_PASSWORD - Database password
#
# Usage:
#   export VORTEX_DEV_DB_HOST=10.211.55.3 VORTEX_DEV_DB_PORT=5432 \
#          VORTEX_DEV_DB_NAME=vtxdev VORTEX_DEV_DB_USER=vtxdev VORTEX_DEV_DB_PASSWORD=xxx
#   ./e2e/reset-dev-db.sh
# ============================================================

set -euo pipefail

# ---------- Validate environment variables ----------
REQUIRED_VARS=(VORTEX_DEV_DB_HOST VORTEX_DEV_DB_PORT VORTEX_DEV_DB_NAME VORTEX_DEV_DB_USER VORTEX_DEV_DB_PASSWORD)
for var in "${REQUIRED_VARS[@]}"; do
  if [[ -z "${!var:-}" ]]; then
    echo "ERROR: Environment variable $var is not set." >&2
    exit 1
  fi
done

echo "=== Dev Database Full Reset ==="
echo "Target: postgresql://${VORTEX_DEV_DB_USER}@${VORTEX_DEV_DB_HOST}:${VORTEX_DEV_DB_PORT}/${VORTEX_DEV_DB_NAME}"
echo ""
echo "WARNING: This will DROP all tables, sequences, triggers, enum types,"
echo "         and Flyway migration history. The database will be empty."
echo ""

# ---------- Safety confirmation ----------
read -r -p "Type 'yes' to confirm full database reset of [${VORTEX_DEV_DB_NAME}]: " confirm
if [[ "$confirm" != "yes" ]]; then
  echo "Aborted."
  exit 0
fi

# ---------- Build PSQL connection string ----------
export PGPASSWORD="${VORTEX_DEV_DB_PASSWORD}"
PSQL="psql -h ${VORTEX_DEV_DB_HOST} -p ${VORTEX_DEV_DB_PORT} -U ${VORTEX_DEV_DB_USER} -d ${VORTEX_DEV_DB_NAME} -v ON_ERROR_STOP=1"

# ---------- Drop everything in the public schema ----------
echo "Dropping all objects in public schema..."

${PSQL} <<'SQL'
-- Drop all tables (including flyway_schema_history) with CASCADE
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- Restore default privileges so Flyway and the app can create objects
GRANT ALL ON SCHEMA public TO PUBLIC;
SQL

echo ""
echo "Done. Database [${VORTEX_DEV_DB_NAME}] is now empty."
echo "Restart the application to let Flyway re-create all tables from migrations."
