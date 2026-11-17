# End-to-End Tests

This directory contains global end-to-end test scripts for the Vortex project.

## Scripts

| Script | Description |
|--------|-------------|
| `reset-dev-db.sh` | Drop all objects (tables, sequences, triggers, enum types, Flyway history) in the dev database, returning it to an empty state. Next app startup will re-run all Flyway migrations. |
| `verify-fee-precision.py` | Start a disposable PostgreSQL cluster under `/tmp`, verify fresh migrations and V28-to-V29 upgrades with synthetic data, check real ORM fee persistence and repository sums, then stop and remove the cluster. Never connects to an existing ledger. |
| `verify-ordered-market-events.py` | Verify ordered events, historical guards, real Spring/JPA commit/rollback, concurrent writers, sync coordination and legacy cleanup in an owned temporary cluster. |
| `check-market-event-history.sql` | Read-only cutover diagnostics for event state, associations, same-day overlaps and sync remnants. Confirm the connection target before use. |
| `market-event-ui-server.mjs` | Serve the real frontend with synthetic event APIs on port 4317 for manual UI acceptance; does not access a backend or database. |

## Fee precision migration check

From the current worktree root in the configured development environment:

```bash
python3 -B e2e/verify-fee-precision.py
```

Requires existing Java, Maven, PostgreSQL server binaries (`initdb`, `pg_ctl` via `pg_config --bindir`) and the project's cached Maven dependencies. Installs no tools. Maven runs offline, writes build output to `backend/target/`, and reuses its existing cache. The script runs the in-memory `TradeFeePrecisionTest` to compile current sources and obtain the resolved classpath, then compiles `FeePrecisionCheck.java` into its temporary directory. Database checks are separate from unit tests.

The launcher chooses a free local port and creates its own cluster; it accepts no database URL or credentials. The checker confirms the server's data directory before creating synthetic databases. Coverage includes V1→V29, V28→V29 with an unchanged two-decimal historical row, the original V28 rounding behavior, high-precision ORM save/read and currency-specific repository sums, existing fee constraints/default, repeat migration, and lock-failure rollback/retry. Temporary files and the server are cleaned up even on a failed assertion; if stopping the server fails, its directory is retained for recovery.

## Ordered market event check

Run from the current checkout in the configured development environment, with existing tools and cached dependencies. Set `TMPDIR=/tmp` and `MAVEN_OPTS=-Djava.io.tmpdir=/tmp`. No application profile is loaded and no existing cluster is used.

```sh
mvn -o -f backend/pom.xml test
python3 -B e2e/verify-ordered-market-events.py
npm --prefix frontend test
npm --prefix frontend run build
```

All parser tests now use synthetic data; the default backend command needs no exclusions or method selectors. Maven excludes the ignored personal XML from test resources. Check actual class, nested-class and parameterized-case names in Surefire reports rather than relying on totals alone; old report files can remain after test removal, so use the suites listed by the current run. See the [test suite review](../docs/audits/2026-11-17-test-suite-review.md) for the cleanup evidence. After backend source changes, recompile before the integration check; the launcher refuses stale class files.

`OrderedMarketEventCheck.java` uses actual transaction proxies, repositories and services against Flyway V1–V29 in a new `/tmp/vortex-ordered-events-*` cluster. It checks the server data directory, installs nothing, and always stops and removes its cluster. Failure to stop preserves the directory. Covered scenarios:

- 100 shares → split → 200; earlier changes rejected unchanged; undo, correct and recreate → 400. Old and new update ranges, unrelated history, event-day trades, protected system rows and processed edits.
- Rename chains, dividend target followed by a split, same-day related rejection, unrelated events, zero-output constraints, reverse undo and two actual brokers.
- Outer transaction rollback, undo rollback, a real generated-row numeric constraint failure, rollback retry and non-READ-COMMITTED refusal.
- Concurrent same-security events, event-first and trade-first waits, batch-create-first/event wait; the waiting writer rechecks after commit.
- Real IBKR locked import rollback and batch cleanup, active sync states, completed-batch protection, old FAILED footprint history protection and retry after undo.
- Read-only cutover diagnostics on synthetic missing-state, unprocessed-output and same-day-overlap records; no automatic repair.
- Exact IBKR option-event links using synthetic XML and a fake report provider, with actual staging/import transactions, JPQL and batch failure handling: broker/currency filtering, trusted existing counterparts without historical staging sources, unique/zero/ambiguous matching, duplicate and corrected retries, occupied counterparts, real link-update failure rollback and entire-batch cleanup. No live broker request is made. Existing records remain intact; historical compatibility fixtures are not part of these checks.

For browser checks, run `node e2e/market-event-ui-server.mjs`, open `http://127.0.0.1:4317/#/market-events`, and visit each event tab. First undo returns a synthetic 409, retaining the modal and reason; retry succeeds and empties that tab. The source form's POST returns a synthetic 409. Restarting the server resets fixtures. Stop with Ctrl-C after testing. This verifies UI behavior against mocks, separate from real database integration and HTTP-controller unit checks.
