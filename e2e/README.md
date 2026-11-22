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
| `verify-ibkr-settings.py` | Own a disposable PostgreSQL cluster; `--connections-only` runs focused V33 multi-account migration, persistence, import and HTTP checks. Default mode retains IBKR configuration/snapshot checks; `--serve` holds a synthetic UI on port 4318. |
| `IbkrSettingsCheck.java` | Settings launcher helper: unconfigured database upgrade, real transaction rollback, plaintext reads and application restarts. |
| `AccountConnectionsCheck.java` | Focused helper: V32→V33 history, same-broker accounts, credential providers, fixed import ownership, account-isolated deduplication/positions/events and failure rollback/cleanup. |
| `verify-account-profiles.py` | Focused V33→V34 profile and V34→V35 broker reference-data migrations and real HTTP checks, in an owned temporary PostgreSQL cluster. |
| `AccountProfilesCheck.java` | Profile helper: unambiguous migration or pending-account preservation, rollback/retry, unchanged ledger/audit, account-scoped CRUD and broker field removal. |
| `ibkr-settings-ui-server.mjs` | Vite proxy to the explicit synthetic application port, used by the settings launcher; never proxies to the default backend. |

## 开发库重置

`reset-dev-db.sh` 会删除并重建 `public` schema，随后重启应用才由 Flyway 重建表。它不创建隔离实例，也不证明目标库可以丢弃。脚本从 `VORTEX_DEV_DB_HOST`、`VORTEX_DEV_DB_PORT`、`VORTEX_DEV_DB_NAME`、`VORTEX_DEV_DB_USER`、`VORTEX_DEV_DB_PASSWORD` 读取连接，要求全部非空，并在执行前交互输入 `yes`。没有 `--help` 或命令行参数解析。按 [环境及数据安全约定](../AGENTS.md#验证与数据安全) 核对目标并取得本次清空授权；不能把“dev”文件名当作连接目标确认。

这些临时验证运行器自行创建临时集群，不接受现有数据库连接；需要所选环境已有工具和缓存，不能因缺工具而擅自安装。费用运行器会执行 Maven 编译／单测，事件及配置运行器要求源码已编译且拒绝过期 class 文件，避免两项检查同时写同一 `backend/target/`。

## IBKR settings check

```bash
TMPDIR=/tmp MAVEN_OPTS=-Djava.io.tmpdir=/tmp mvn -B -ntp -o -f backend/pom.xml -Dtest=BrokerSyncConnectionServiceTest,BrokerSyncConnectionControllerTest,IbkrSyncSettingsServiceTest,IbkrSyncSettingsControllerTest,BrokerServiceTest,TigerSyncAdapterTest test
python3 -B e2e/verify-ibkr-settings.py --connections-only
# Optional wider IBKR scenarios, subject to the testing scope authorized for the task:
python3 -B e2e/verify-ibkr-settings.py
# Optional manual UI verification; press Enter to stop and remove the temporary cluster:
python3 -B e2e/verify-ibkr-settings.py --serve
```

Requires existing Java/javac, Python, Node, psql and PostgreSQL server binaries, plus cached Maven dependencies and the frontend's existing dependencies. It reads a successful current settings-test classpath and refuses stale main classes. Both helpers verify the owned server data directory and start Spring Boot using an isolated configuration location. They never load local/dev credentials or connect to an existing ledger. Flex uses only a loopback fake server.

`--connections-only` verifies fresh V33 initialization and a V32→V33 upgrade containing soft-deleted and event-generated ledger rows, a transferred broker code, unchanged values/timestamps, unknown empty-batch ownership, and migration rollback/retry. Real Spring/JPA checks cover same-broker IBKR/Tiger accounts and independent database credentials, transaction rollback, same external IDs across accounts, duplicate/conflicting/partial imports, whole-batch cleanup, global batch exclusion, and fixed account ownership when broker code changes during fetch. It also checks account-scoped option links, positions, event costs and dividend rounding. Tiger import uses explicit epoch fixtures to isolate account behavior; the normal-stage timestamp defect remains excluded and unresolved.

Focused HTTP checks cover account CRUD, pending-account rename/disable/completion, duplicate or linked deletion rejection, read-only associations, trade/cash account filters and moving a trade between accounts, secret retention/clear isolation, and ambiguous legacy requests. A selected second-account async sync makes two requests only to the loopback fake Flex server, verifying its Token and Query ID/reference pair and a legal empty report. No real broker is contacted. The temporary application and cluster are removed on exit. This mode does not run the wider historical event or default IBKR settings scenarios.

Default-mode checks include account/credential rollback, plaintext-only storage and metadata-only responses, retained/replaced Token, saved/cleared state across application restarts, and configuration/account edits during an active batch. A controlled fake Flex response confirms the current report keeps its captured Token while the next report reads the replacement plaintext. `--serve` then exposes the real frontend and synthetic backend at `http://127.0.0.1:4318/#/sync-management`; enter accounts through account management, then check account-row configuration, save, refresh, batch selection and clear. These manual interactions are separate from automatic API checks. Temporary databases and processes are removed on exit. No account is opened with a real broker.

## Account profile check

```bash
mvn -B -ntp -o -f backend/pom.xml -Dtest=BrokerAccountServiceTest,BrokerServiceTest,BrokerSyncConnectionServiceTest test
python3 -B e2e/verify-account-profiles.py
```

The launcher uses the current `BrokerAccountServiceTest` classpath, refuses stale production classes, verifies its temporary server data directory, and loads an isolated Spring configuration. It never reads local/dev credentials. The profile migration stage is pinned to V34. Synthetic V33 fixtures cover a unique pending account, a sole real account, zero accounts, multiple real or pending accounts, and absent old profile data. V34 preserves raw contact strings, existing account audit fields, ledger ownership and connection data; a forced failure rolls back and retries successfully. Fresh-schema HTTP checks verify optional profile create/read, omitted and explicit-null retention, empty-string clearing with subsequent GET, same-broker account isolation, length limits and removal of profile fields from broker responses. Additional V34→V35 checks remove broker state, add an independent introduction and preserve complete account/profile/ledger snapshots; a table-lock failure rolls back before retry. Current-schema HTTP checks cover introduction create/update/retention/clear, absence of broker state, the legacy all-broker list, account-only sync readiness/validation and objective broker discovery. No broker request is made; the application and temporary cluster are removed on exit. See [the consolidated design](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md) and [verification evidence](../docs/TODO-ARCHIVE.md).

## Fee precision migration check

From the current worktree root in the configured development environment:

```bash
python3 -B e2e/verify-fee-precision.py
```

Requires existing Java, Maven, PostgreSQL server binaries (`initdb`, `pg_ctl` via `pg_config --bindir`) and the project's cached Maven dependencies. Installs no tools. Maven runs offline, writes build output to `backend/target/`, and reuses its existing cache. The script runs the in-memory `TradeFeePrecisionTest` to compile current sources and obtain the resolved classpath, then compiles `FeePrecisionCheck.java` into its temporary directory. Database checks are separate from unit tests.

The launcher chooses a free local port and creates its own cluster; it accepts no database URL or credentials. The checker confirms the server's data directory before creating synthetic databases. Fee-migration assertions are pinned to V29; ORM checks then upgrade to the current schema. Coverage includes V1→V29, V28→V29 with an unchanged two-decimal historical row, the original V28 rounding behavior, high-precision ORM save/read and currency-specific repository sums, existing fee constraints/default, repeat migration, and lock-failure rollback/retry. Temporary files and the server are cleaned up even on a failed assertion; if stopping the server fails, its directory is retained for recovery.

## Ordered market event check

Run from the current checkout in the configured development environment, with existing tools and cached dependencies. Set `TMPDIR=/tmp` and `MAVEN_OPTS=-Djava.io.tmpdir=/tmp`. No application profile is loaded and no existing cluster is used.

The wider event runner is optional and must fit the task’s explicitly authorized regression scope. Compile current sources with relevant selected tests first, for example:

```sh
mvn -o -f backend/pom.xml -Dtest=MarketEventProcessingServiceTest test
python3 -B e2e/verify-ordered-market-events.py
```

Full unit suites and whole regression runs require explicit user instruction under [AGENTS.md](../AGENTS.md#测试范围与提交提醒). Initial V33 development ran focused checks and compiled all four helpers. On 2026-11-22, after the user explicitly requested full testing, the backend and frontend unit suites, frontend build and all four database modes above passed against the V33 implementation. Named-case manifests, commands, logs and remaining limits are recorded in [the verification archive](../docs/TODO-ARCHIVE.md). Synthetic browser acceptance from the unchanged preceding version was reused; no complete browser-regression suite or live-broker validation is claimed.

All parser tests now use synthetic data; the default backend command needs no exclusions or method selectors. Maven excludes the ignored personal XML from test resources. Check actual class, nested-class and parameterized-case names in Surefire reports rather than relying on totals alone; old report files can remain after test removal, so use the suites listed by the current run. See the [test cleanup evidence](../docs/TODO-ARCHIVE.md#测试清理依据) for the case comparison. After backend source changes, recompile before the integration check; the launcher refuses stale class files.

`OrderedMarketEventCheck.java` uses actual transaction proxies, repositories and services against current Flyway migrations in a new `/tmp/vortex-ordered-events-*` cluster. It checks the server data directory, installs nothing, and always stops and removes its cluster. Failure to stop preserves the directory. Covered scenarios:

- 100 shares → split → 200; earlier changes rejected unchanged; undo, correct and recreate → 400. Old and new update ranges, unrelated history, event-day trades, protected system rows and processed edits.
- Rename chains, dividend target followed by a split, same-day related rejection, unrelated events, zero-output constraints, reverse undo and two actual brokers.
- Outer transaction rollback, undo rollback, a real generated-row numeric constraint failure, rollback retry and non-READ-COMMITTED refusal.
- Concurrent same-security events, event-first and trade-first waits, batch-create-first/event wait; the waiting writer rechecks after commit.
- Real IBKR locked import rollback and batch cleanup, active sync states, completed-batch protection, old FAILED footprint history protection and retry after undo.
- Read-only cutover diagnostics on synthetic missing-state, unprocessed-output and same-day-overlap records; no automatic repair.
- Exact IBKR option-event links using synthetic XML and a fake report provider, with actual staging/import transactions, JPQL and batch failure handling: broker/currency filtering, trusted existing counterparts without historical staging sources, unique/zero/ambiguous matching, duplicate and corrected retries, occupied counterparts, real link-update failure rollback and entire-batch cleanup. No live broker request is made. Existing records remain intact; historical compatibility fixtures are not part of these checks.

For browser checks, run `node e2e/market-event-ui-server.mjs`, open `http://127.0.0.1:4317/#/market-events`, and visit each event tab. Verify that all three tabs display their fixture but expose no undo button or action column; open and cancel each create form. The source form's POST returns a synthetic 409 for optional conflict-message verification. The fixture server retains legacy DELETE responses for historical checks; the current UI does not call them. Restarting the server resets fixtures. Stop with Ctrl-C after testing. This verifies UI behavior against mocks, separate from real database integration and HTTP-controller unit checks.
