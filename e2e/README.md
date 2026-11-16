# End-to-End Tests

This directory contains global end-to-end test scripts for the Vortex project.

## Scripts

| Script | Description |
|--------|-------------|
| `reset-dev-db.sh` | Drop all objects (tables, sequences, triggers, enum types, Flyway history) in the dev database, returning it to an empty state. Next app startup will re-run all Flyway migrations. |
| `verify-fee-precision.py` | Start a disposable PostgreSQL cluster under `/tmp`, verify fresh migrations and V28-to-V29 upgrades with synthetic data, check real ORM fee persistence and repository sums, then stop and remove the cluster. Never connects to an existing ledger. |

## Fee precision migration check

From the current worktree root in the configured development environment:

```bash
python3 -B e2e/verify-fee-precision.py
```

Requires existing Java, Maven, PostgreSQL server binaries (`initdb`, `pg_ctl` via `pg_config --bindir`) and the project's cached Maven dependencies. Installs no tools. Maven runs offline, writes build output to `backend/target/`, and reuses its existing cache. The script runs the in-memory `TradeFeePrecisionTest` to compile current sources and obtain the resolved classpath, then compiles `FeePrecisionCheck.java` into its temporary directory. Database checks are separate from unit tests.

The launcher chooses a free local port and creates its own cluster; it accepts no database URL or credentials. The checker confirms the server's data directory before creating synthetic databases. Coverage includes V1→V29, V28→V29 with an unchanged two-decimal historical row, the original V28 rounding behavior, high-precision ORM save/read and currency-specific repository sums, existing fee constraints/default, repeat migration, and lock-failure rollback/retry. Temporary files and the server are cleaned up even on a failed assertion; if stopping the server fails, its directory is retained for recovery.
