# Local Ledger

A personal investment portfolio management system designed for individual investors who trade across multiple brokers and markets. Local Ledger provides a unified platform to manage trade records, cash flows, position snapshots, and market events — with the goal of accurate profit & loss analysis.

## Features

### Core Functionality

- **Trade Record Management** — Full CRUD with soft-delete for stock, ETF, and options (calls & puts) trades across US (USD), HK (HKD), and CN (CNY) markets. Automatic amount calculation with contract multiplier support for options.
- **Position Snapshots** — Real-time position aggregation by `(symbol, brokerId)`, derived purely from BUY/SELL trade records.
- **Cash Flow Records** — Track deposits and withdrawals across brokers and currencies.
- **Broker Management** — Manage multiple brokerage accounts.
- **Strategy Management** — Organize trades by investment strategies.

### Market Event Processing

- **Stock Splits** — Auto-generates BUY records to adjust positions based on split ratios.
- **Symbol Changes** — Generates SELL (old symbol) + BUY (new symbol) records to seamlessly transfer cost basis (e.g., FB → META).
- **Dividends in Kind** — Generates BUY records for dividend securities with fair-value cost basis.
- **Cascading Recalculation** — Back-filling a historical event automatically recalculates all subsequent events, fully protected by database transactions.

### Trade Origin Tracking

Every trade record carries explicit origin metadata:
- `tradeTrigger` — Why the trade happened: `MANUAL`, `OPTION`, or `MARKET_EVENT`
- `triggerRefType` — Specific sub-type (e.g., `STOCK_SPLIT`, `OPTION_EXERCISE`, `OPTION_ASSIGNED`)
- `triggerRefId` — Links to the originating event or trade record

### Broker Data Sync (In Progress)

- **Tiger Brokers** — Phase 1 complete (manual trigger + log output via Tiger Open API)
- **Interactive Brokers (IBKR)** — Flex Web Service integration with async execution, staged data tables, and frontend management UI. Data import logic is pending.
- **Adapter Pattern** — Extensible `BrokerSyncAdapter` interface for adding new brokers (Schwab, Futu planned)

### Data Verification

Six built-in verification rules for trade data anomaly detection:
- Option symbol format validation
- HK stock symbol format validation
- Passive option operation fee/price checks
- US stock symbol format validation
- Symbol-to-asset-type consistency
- Trigger type and ref type consistency

### Frontend

- Dashboard with investment overview
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
| **Migrations** | Flyway | 22 migration scripts |
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

```bash
psql -U postgres

CREATE DATABASE ledgerdb;
CREATE USER ledger WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE ledgerdb TO ledger;
```

Database tables are created automatically by Flyway migrations on first startup.

### Backend

```bash
cd backend

# Create local config with your database password (gitignored)
cp src/main/resources/application-local.properties.template \
   src/main/resources/application-local.properties
# Edit application-local.properties with your actual credentials

# Start with local profile
mvn spring-boot:run -Dspring-boot.run.profiles=local
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
# Backend unit tests (pure in-memory, no database required)
cd backend && mvn test

# Frontend unit tests
cd frontend && npm test
```

> **Testing philosophy**: All unit tests run in pure memory with no database connections. Backend tests use Mockito for repository isolation; frontend tests focus on constant/config layer validation. See [Unit Test Spec](docs/unit-test-spec.md) for details.

## Project Structure

```
local-ledger/
├── backend/                            # Spring Boot backend
│   ├── pom.xml                         # Maven config
│   └── src/
│       ├── main/java/com/localledger/
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
│       └── src/main/resources/
│           ├── application.properties  # Base config (no secrets)
│           └── db/migration/           # Flyway migrations (V1 ~ V22)
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
├── docs/                               # Project documentation
│   ├── backend-guide.md
│   ├── frontend-guide.md
│   ├── database-setup.md
│   ├── local-config-guide.md
│   ├── unit-test-spec.md
│   ├── market-event-processing-design.md
│   ├── trade-trigger-design.md
│   ├── trade-type-refactor-discussion.md
│   ├── related-trades-display-design.md
│   └── broker-sync/                    # Broker sync design docs (8 files)
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
./deploy/build-frontend.sh -a http://api.example.com   # custom API URL
```

Build artifacts are output to `deploy/output/`.

## Key Design Decisions

| Decision | Rationale |
|----------|-----------|
| **Minimalist TradeType** — Only `BUY` and `SELL` | Options events and market events all reuse BUY/SELL. "Why it happened" is expressed via `trade_trigger` + `trigger_ref_type`, keeping concerns cleanly separated. |
| **Market events → trade records** | Events generate system trade records on write, so position calculation has zero additional overhead and `PositionService` needs no modification. |
| **Cascading recalculation** | Back-filling a historical event recalculates all subsequent events in a single transaction, ensuring data consistency. |
| **Weighted average cost** | Current cost basis calculation uses moving weighted average. Future expansion may add FIFO, LIFO, or specific lot identification. |
| **Adapter pattern for broker sync** | `BrokerSyncAdapter` interface enables plugging in new brokers without modifying core sync logic. |

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
| [Frontend Structure](docs/frontend-structure.md) | Frontend directory layout and component overview |
| [Database Setup](docs/database-setup.md) | PostgreSQL installation and configuration |
| [Local Config Guide](docs/local-config-guide.md) | Spring Boot profile mechanism and secret management |
| [Unit Test Spec](docs/unit-test-spec.md) | Testing standards and conventions |
| [Market Event Processing](docs/market-event-processing-design.md) | Stock split / symbol change / dividend-in-kind processing design |
| [Trade Trigger Design](docs/trade-trigger-design.md) | Trade origin tracking field design |
| [Trade Type Refactor](docs/trade-type-refactor-discussion.md) | TradeType enum simplification discussion |
| [Related Trades Display](docs/related-trades-display-design.md) | Option contract trade chain display design |
| [Broker Sync Docs](docs/broker-sync/) | Broker data sync design documents (8 files) |

## Roadmap

- [ ] Profit & loss analysis module (per-stock, per-strategy, per-account)
- [ ] Chart-based analysis (profit trends, asset distribution)
- [ ] IBKR data import logic (staged data → trade records)
- [ ] Additional broker adapters (Schwab, Futu)
- [ ] Automatic sync scheduling with conflict resolution
- [ ] Data export functionality

## License

Private project. All rights reserved.
