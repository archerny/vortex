# Vortex


A personal investment portfolio management system designed for individual investors who trade across multiple brokers and markets. Vortex provides a unified platform to manage trade records, cash flows, position snapshots, and market events — with the goal of accurate profit & loss analysis.

## Features

### Core Functionality

- **Trade Record Management** — Full CRUD with soft-delete for stock, ETF, and options (calls & puts) trades across US (USD), HK (HKD), and CN (CNY) markets. Automatic amount calculation with contract multiplier support for options.
- **Position Snapshots** — Real-time position aggregation by `(symbol, brokerId)`, derived purely from BUY/SELL trade records.
- **Cash Flow Records** — Track deposits and withdrawals across brokers and currencies.
- **Broker Management** — Manage multiple brokerage accounts.
- **Strategy Management** — Organize trades by investment strategies.

### Market Event Processing

- **Stock Splits** — Generates zero-cost BUY records when the split ratio increases the share count.
- **Symbol Changes** — Generates SELL (old symbol) + BUY (new symbol) records to seamlessly transfer cost basis (e.g., FB → META).
- **Dividends in Kind** — Generates BUY records for dividend securities with fair-value cost basis.
- **Historical Recalculation** — Changing an event recalculates events selected by affected symbols and dates in the same transaction. Calculations use prior-day positions. [Market Event Knowledge](knowledge/market-events.md)

### Trade Origin Tracking

Every trade record carries explicit origin metadata:
- `tradeTrigger` — Why the trade happened: `MANUAL`, `OPTION`, or `MARKET_EVENT`
- `triggerRefType` — Specific sub-type (e.g., `STOCK_SPLIT`, `OPTION_EXERCISE`, `OPTION_ASSIGNED`)
- `triggerRefId` — Links to the originating event or trade record

### Broker Data Sync

- **IBKR and Tiger** — Both adapters have asynchronous fetch, staging and import code. The staged-data viewer currently exposes IBKR data. [Broker Sync Knowledge](knowledge/broker-sync/README.md)
- **Batch management** — The UI shows batch status and errors. The framework cleans up failed batches and blocks new submissions while a batch requires manual recovery. [Broker Sync Guide](docs/broker-sync/README.md)

### Data Verification

Six built-in verification rules for trade data anomaly detection:
- Option symbol format validation
- HK stock symbol format validation
- Passive option operation fee/price checks
- US stock symbol format validation
- Symbol-to-asset-type consistency
- Trigger type and ref type consistency

### Frontend

- Dashboard and profit-analysis page placeholders
- Trade record list with detail pages, statistics panel, and related trades display
- Cash flow management
- Market event management (Stock Split / Symbol Change / Dividend in Kind tabs)
- Trade anomaly analysis and position snapshot views
- Sync management page
- Amount visibility toggle (show/hide sensitive data)

## Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Backend** | Java + Spring Boot | Java 17, Spring Boot 3.2.0 |
| **Build (Backend)** | Maven | 3.6+ |
| **ORM** | Spring Data JPA (Hibernate) | — |
| **Database** | PostgreSQL | 12+ |
| **Migrations** | Flyway | V1–V28 |
| **Connection Pool** | HikariCP | — |
| **Frontend** | React | 18.2 |
| **UI Library** | Ant Design | 5.12 |
| **Build (Frontend)** | Vite | 5.0 |
| **HTTP Client** | Axios | 1.6 |
| **Charts** | Recharts | 2.12 |
| **Date Handling** | Day.js | 1.11 |
| **Backend Testing** | JUnit 5 + Mockito | — |
| **Frontend Testing** | Vitest | 1.6 |
| **Broker SDKs** | Tiger Open API SDK, IBKR Flex Web Service | Tiger SDK 2.4.7 |

## Getting Started

### Prerequisites

- **JDK 17+**
- **Maven 3.6+**
- **Node.js 18+**
- **PostgreSQL 12+**

### Database Setup

Set up an isolated database for development and configure the `dev` profile before starting the backend. Flyway applies migrations at startup. Verify the datasource first, especially when using the `local` profile for a real ledger. [Database Setup](docs/database-setup.md) · [Local Config Guide](docs/local-config-guide.md)

### Backend

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

The backend starts at `http://localhost:8080`.

### Frontend

```bash
cd frontend

npm install
npm run dev
```

The frontend starts at `http://localhost:3000` and proxies API requests to the backend.

### Running Tests

```bash
# Backend unit tests
mvn -f backend/pom.xml test

# Frontend unit tests
npm --prefix frontend test
```

> **Testing philosophy**: All unit tests run in pure memory with no database connections. Backend tests use Mockito for repository isolation; frontend tests focus on constant/config layer validation. See [Unit Test Spec](docs/unit-test-spec.md) for details.

## Project Structure

```
vortex/
├── backend/                            # Spring Boot backend
│   ├── pom.xml                         # Maven config
│   └── src/
│       ├── main/java/com/vortex/
│       │   ├── Application.java        # Main entry point
│       │   ├── config/                 # Configuration (Async, Database)
│       │   ├── controller/             # REST controllers
│       │   ├── dto/                    # Data transfer objects
│       │   ├── entity/                 # JPA entities
│       │   │   └── enums/              # Enums (AssetType, TradeType, Currency, ...)
│       │   ├── repository/             # Data access layer
│       │   ├── service/                # Business logic
│       │   └── sync/                   # Broker sync module
│       │       ├── core/               # Sync core (adapter interface, async executor)
│       │       └── adapter/            # Broker adapters (Tiger, IBKR)
│       └── main/resources/
│           ├── application.properties  # Base config (no secrets)
│           └── db/migration/           # Flyway migrations
├── frontend/                           # React + Vite frontend
│   ├── package.json
│   └── src/
│       ├── components/                 # Layout components
│       ├── contexts/                   # React contexts
│       ├── constants/                  # Constants and config
│       ├── services/                   # API service layer
│       └── pages/                      # Page components
│           ├── trade/                  # Trade records (list, detail, stats)
│           ├── market-events/          # Market events (3 tabs)
│           ├── analysis/               # Anomaly detection + position snapshot
│           ├── profit/                 # P&L analysis (placeholder)
│           └── sync/                   # Sync management
├── deploy/                             # Build scripts
│   ├── build-backend.sh                # Backend build → JAR + start script
│   └── build-frontend.sh              # Frontend build → static files
├── docs/                               # Setup, usage and recovery guides
├── knowledge/                          # Current capabilities and architecture
├── design/                             # Historical decisions and proposals
├── e2e/                                # E2E test scripts
│   └── reset-dev-db.sh                 # Dev database reset utility
└── external-resource/                  # Broker API reference docs
    ├── tiger-api/
    ├── ibkr-api/
    ├── futu-api/
    ├── schwab-api/
    └── longbridge-api/
```

## Production Build

```bash
# Backend (outputs JAR + start script + archive)
./deploy/build-backend.sh           # with tests
./deploy/build-backend.sh -s        # skip tests

# Frontend (outputs static files + archive)
./deploy/build-frontend.sh
```

Build artifacts are written to `deploy/output/`. For distribution, use a worktree without local credentials in backend resources and inject configuration at runtime. Serve the frontend with `/api` routed to the backend. [Backend Guide](docs/backend-guide.md) · [Frontend Guide](docs/frontend-guide.md)

## Configuration

Sensitive configuration (database passwords, broker API keys) is managed via Spring Boot profiles:

- `application.properties` — Base config with placeholders (committed to repo)
- `application-local.properties` — Local overrides with real secrets (gitignored)

See [Local Config Guide](docs/local-config-guide.md) for details.

## Documentation

| Document | Description |
|----------|-------------|
| [Backend Guide](docs/backend-guide.md) | Backend tech stack, startup instructions, project structure |
| [Frontend Guide](docs/frontend-guide.md) | Frontend tech stack, startup instructions, debugging guide |
| [Frontend Structure](docs/frontend-structure.md) | Guide for adding frontend pages |
| [Database Setup](docs/database-setup.md) | PostgreSQL installation and configuration |
| [Local Config Guide](docs/local-config-guide.md) | Spring Boot profile mechanism and secret management |
| [Unit Test Spec](docs/unit-test-spec.md) | Testing standards and conventions |
| [Current Knowledge](knowledge/README.md) | Implemented behavior, architecture and code references |
| [Design History](design/README.md) | Numbered design records and pending proposals |
| [Broker Sync Guide](docs/broker-sync/README.md) | Broker setup, synchronization and recovery |

## Roadmap

- [ ] Profit & loss analysis module (per-stock, per-strategy, per-account)
- [ ] Chart-based analysis (profit trends, asset distribution)
- [ ] Additional broker adapters (Futu, Longbridge)
- [ ] Automatic sync scheduling with conflict resolution
- [ ] Data export functionality

## License

Private project. All rights reserved.
