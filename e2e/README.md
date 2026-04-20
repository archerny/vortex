# End-to-End Tests

This directory contains global end-to-end test scripts for the Vortex project.

## Scripts

| Script | Description |
|--------|-------------|
| `reset-dev-db.sh` | Drop all objects (tables, sequences, triggers, enum types, Flyway history) in the dev database, returning it to an empty state. Next app startup will re-run all Flyway migrations. |
