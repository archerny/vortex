# 独立数据库与界面验收

本目录提供隔离的迁移／真实事务验收、只读历史诊断、开发库重置与合成 API 的 UI 工具。这些入口不是一套自动执行全部 HTTP 和浏览器场景的测试。内存单测仍分别由 Maven 和 Vitest 执行，见 [单测规范](../docs/unit-test-spec.md)；既有运行证据见 [待办归档](../docs/TODO-ARCHIVE.md)。

## Scripts

| Script | Description |
|--------|-------------|
| `reset-dev-db.sh` | Drop all objects (tables, sequences, triggers, enum types, Flyway history) in the dev database, returning it to an empty state. Next app startup will re-run all Flyway migrations. |
| `verify-fee-precision.py` | Start a disposable PostgreSQL cluster under `/tmp`, verify fresh migrations and V28-to-V29 upgrades with synthetic data, check real ORM fee persistence and repository sums, then stop and remove the cluster. Never connects to an existing ledger. |
| `verify-ordered-market-events.py` | Verify ordered events, historical guards, real Spring/JPA commit/rollback, concurrent writers, sync coordination and legacy cleanup in an owned temporary cluster. |
| `check-market-event-history.sql` | Read-only cutover diagnostics for event state, associations, same-day overlaps and sync remnants. Confirm the connection target before use. |
| `market-event-ui-server.mjs` | Serve the real frontend with synthetic event APIs on port 4317 for manual UI acceptance; does not access a backend or database. |
| `FeePrecisionCheck.java` | Java helper for the fee launcher; verifies migrations, ORM and repository sums with synthetic data. |
| `OrderedMarketEventCheck.java` | Java helper for ordered-event and IBKR linkage integration checks; run through its Python launcher. |

## 开发库重置

`reset-dev-db.sh` 会删除并重建 `public` schema，随后重启应用才由 Flyway 重建表。它不创建隔离实例，也不证明目标库可以丢弃。脚本从 `VORTEX_DEV_DB_HOST`、`VORTEX_DEV_DB_PORT`、`VORTEX_DEV_DB_NAME`、`VORTEX_DEV_DB_USER`、`VORTEX_DEV_DB_PASSWORD` 读取连接，要求全部非空，并在执行前交互输入 `yes`。没有 `--help` 或命令行参数解析。按 [环境及数据安全约定](../AGENTS.md#验证与数据安全) 核对目标并取得本次清空授权；不能把“dev”文件名当作连接目标确认。

后文两个 Python 运行器则自行创建临时集群，不接受现有数据库连接；需要所选环境已有工具和缓存，不能因缺工具而擅自安装。费用运行器会执行 Maven 编译／单测，事件运行器要求源码已编译且拒绝过期 class 文件，避免两项检查同时写同一 `backend/target/`。

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

All parser tests now use synthetic data; the default backend command needs no exclusions or method selectors. Maven excludes the ignored personal XML from test resources. Check actual class, nested-class and parameterized-case names in Surefire reports rather than relying on totals alone; old report files can remain after test removal, so use the suites listed by the current run. See the [test cleanup evidence](../docs/TODO-ARCHIVE.md#测试清理依据) for the case comparison. After backend source changes, recompile before the integration check; the launcher refuses stale class files.

`OrderedMarketEventCheck.java` uses actual transaction proxies, repositories and services against Flyway V1–V29 in a new `/tmp/vortex-ordered-events-*` cluster. It checks the server data directory, installs nothing, and always stops and removes its cluster. Failure to stop preserves the directory. Covered scenarios:

- 100 shares → split → 200; earlier changes rejected unchanged; undo, correct and recreate → 400. Old and new update ranges, unrelated history, event-day trades, protected system rows and processed edits.
- Rename chains, dividend target followed by a split, same-day related rejection, unrelated events, zero-output constraints, reverse undo and two actual brokers.
- Outer transaction rollback, undo rollback, a real generated-row numeric constraint failure, rollback retry and non-READ-COMMITTED refusal.
- Concurrent same-security events, event-first and trade-first waits, batch-create-first/event wait; the waiting writer rechecks after commit.
- Real IBKR locked import rollback and batch cleanup, active sync states, completed-batch protection, old FAILED footprint history protection and retry after undo.
- Read-only cutover diagnostics on synthetic missing-state, unprocessed-output and same-day-overlap records; no automatic repair.
- Exact IBKR option-event links using synthetic XML and a fake report provider, with actual staging/import transactions, JPQL and batch failure handling: broker/currency filtering, trusted existing counterparts without historical staging sources, unique/zero/ambiguous matching, duplicate and corrected retries, occupied counterparts, real link-update failure rollback and entire-batch cleanup. No live broker request is made. Existing records remain intact; historical compatibility fixtures are not part of these checks.

For browser checks, run `node e2e/market-event-ui-server.mjs`, open `http://127.0.0.1:4317/#/market-events`, and visit each event tab. First undo returns a synthetic 409, retaining the modal and reason; retry succeeds and empties that tab. The source form's POST returns a synthetic 409. Restarting the server resets fixtures. Stop with Ctrl-C after testing. This verifies UI behavior against mocks, separate from real database integration and HTTP-controller unit checks.
