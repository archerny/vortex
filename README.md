# Vortex

Vortex is a personal investment ledger and profit-and-loss analysis system. It currently supports trades, position quantities, cash flows, brokers, strategies, market events, and broker synchronization. Production profit-and-loss analysis is not yet implemented.

## Current Capabilities

| Module | Implemented Behavior and Limits |
| --- | --- |
| Trade records | Query, create, edit, soft-delete, related-trade display, and statistics for stocks, ETFs, calls, and puts. Directions are BUY/SELL; currencies are CNY/HKD/USD. Quantities are integers, prices have 4 decimal places, and trade amounts have 2. Fee storage, aggregation, display, and editing preserve numeric precision. |
| Position snapshots | Aggregate BUY minus SELL quantities by cutoff date, symbol, and broker, including generated market-event trades. Position cost and profit/loss are not calculated. |
| Basic management | Query and create cash flows; query, create, and update brokers; query, create, update, and delete strategies. The backend also exposes cash-flow soft deletion, but no cash-flow update endpoint. |
| Market events | Stock splits, symbol changes, and dividends in kind generate trades for the selected event using prior-day positions. Events are processed in order, lock earlier history for related securities, and can be undone only when the required conditions hold. No automatic cascading recalculation. |
| IBKR synchronization | Asynchronous fetching, staging, order-level import, content checks for duplicate IDs, and exact, unique option-event linkage. Only single-account TCF reports with the prescribed structure and integer quantities are supported. |
| Tiger synchronization | SDK fetching, staging, and import code exists, but staged timestamps do not match the import parser's format. Normal new orders can fail the entire batch. This defect remains unresolved. |
| Sync batches | At most one active batch globally. Failed batches are cleaned up in full; cleanup failure blocks new synchronization. No resume or automatic scheduling. The staged-data viewer covers IBKR only. |
| Data verification | Six categories of trade anomaly checks report issues without automatically repairing the ledger. |
| Frontend demos | The dashboard and stock, strategy, and account profit/loss pages are placeholders. The chart-selection demo uses fixed synthetic data for charts and an option chain, without ledger or market-data integration. |

Detailed rules are documented in [Trades and Positions](knowledge/trades-and-positions.md), [Market Events](knowledge/market-events.md), [Broker Synchronization](knowledge/broker-sync/README.md), and [Frontend Architecture](knowledge/frontend-architecture.md). Known unresolved issues are tracked in [TODO](docs/TODO.md).

## Tech Stack

- Backend: Java compilation target 17, Spring Boot 3.2.0, Maven, Spring Data JPA/Hibernate, HikariCP, PostgreSQL, and Flyway V1–V29.
- Frontend: React 18, Ant Design 5, Vite 5, Axios, and Day.js; chart dependencies include Recharts, Lightweight Charts, ECharts, and Plotly.
- Testing: JUnit 5/Mockito and Vitest. Separate disposable PostgreSQL runners in `e2e/` verify real transactions and migrations.
- Brokers: Tiger OpenAPI Java SDK 2.4.7 and IBKR Flex Web Service.

Backend versions are defined in [pom.xml](backend/pom.xml). Frontend declarations and resolved versions are recorded in [package.json](frontend/package.json) and [package-lock.json](frontend/package-lock.json). Existing synthetic validation on PostgreSQL 18.6 is documented in the [e2e guide](e2e/README.md); it does not establish compatibility with every PostgreSQL version.

## Development and Verification

Select the execution environment and check the existing Java, Maven, Node, and npm tools according to the [development rules](AGENTS.md#1-环境隔离). Install frontend dependencies from the lockfile; Vite 5 supports Node 18.x or 20 and newer. Before integration work, configure the `dev` profile to use an isolated development database. The base configuration points to `vortex`; selecting `dev` alone does not switch databases. Application startup connects to the database and may apply Flyway migrations.

Run these commands from the current worktree root:

```bash
# Initial frontend dependency installation
npm --prefix frontend ci

# Start the backend and frontend in separate terminals
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
npm --prefix frontend run dev

# In-memory unit tests and frontend build
mvn -f backend/pom.xml test
npm --prefix frontend test
npm --prefix frontend run build
```

The backend defaults to `http://localhost:8080`, with status available at `/api/health`. The frontend defaults to `http://localhost:3000`, and its development proxy forwards `/api` to the backend. Unit tests do not connect to databases or brokers; parser tests use synthetic XML and require no personal fixtures. See the [backend guide](docs/backend-guide.md), [frontend guide](docs/frontend-guide.md), [configuration guide](docs/local-config-guide.md), and [unit-test specification](docs/unit-test-spec.md) for details.

## Project Structure and Documentation

| Directory or Entry Point | Purpose |
| --- | --- |
| [`backend/`](backend/) | Controllers, services, repositories, entities and DTOs, broker adapters, migrations, and backend unit tests |
| [`frontend/`](frontend/) | React entry point, layout and contexts, hash navigation, pages, API wrappers, and frontend unit tests |
| [Current Knowledge](knowledge/README.md) | Implemented capabilities, business contracts, architecture, and source references |
| [Operations and Development Guides](docs/README.md) | Environment setup, development, deployment, market-event operations, sync recovery, and verification |
| [Design Index](design/README.md) | Historical decisions and pending proposals; designs alone do not establish current capabilities |
| [TODO](docs/TODO.md) / [Archive](docs/TODO-ARCHIVE.md) | Open work and dated resolution and validation evidence |
| [Independent Validation](e2e/README.md) | Synthetic data, temporary databases, read-only diagnostics, and UI tools using mock APIs |
| [Build and Deployment](deploy/README.md) | Build scripts, output directories, and current parameter limitations |
| [External References](external-resource/README.md) | Offline broker documentation; available references do not imply API integration |

## Distribution Builds

```bash
./deploy/build-backend.sh
./deploy/build-frontend.sh
```

Artifacts are written to `deploy/output/`. The backend JAR includes local profile files present in the resource directory at build time. Before distribution, ensure that no credential files are included and inject configuration at runtime. The generated `start.sh` defaults to `local`; explicitly pass `dev` for an isolated development environment. Frontend requests use relative `/api` paths, so the hosting service must provide same-origin routing or a reverse proxy. See the [deployment guide](deploy/README.md) for the actual effects of script parameters.

Production profit/loss analysis, live market data, additional broker adapters, automatic sync scheduling, and data export are not currently implemented. Futu and Longbridge remain design drafts; Schwab has reference documentation but no project adapter.

Private project. All rights reserved.
