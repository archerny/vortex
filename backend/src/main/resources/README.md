# Configuration Files Guide

## Overview

This project uses **Spring Boot Profile** mechanism to manage configurations for different environments.
Profiles are loaded in layers — later profiles override earlier ones.

## File Structure

```
resources/
├── application.properties        # Base config (defaults + placeholders), committed to Git
├── application-local.properties  # Production-like local overrides (real credentials), gitignored
├── application-dev.properties    # Dev/test environment (all config including credentials), gitignored
└── README.md                     # This file
```

## How Profile Loading Works

Spring Boot loads configuration in the following order (later overrides earlier):

```
1. application.properties              ← always loaded (base defaults)
2. application-{profile}.properties    ← loaded based on active profile, overrides base
```

### Example: Production-like Local Environment

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Loading order:
#   application.properties → application-local.properties
```

### Example: Dev/Test Environment

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Loading order:
#   application.properties → application-dev.properties
```

## Environment Comparison

| Aspect             | local (production-like)         | dev (development/test)          |
|--------------------|---------------------------------|---------------------------------|
| Database           | `ledgerdb` (real data)          | `local_ledger_test` (test data) |
| JPA ddl-auto       | `update` (preserve data)        | `create-drop` (clean each run)  |
| Broker credentials | Real API tokens                 | Empty (not needed)              |
| Purpose            | Daily use, real trading data    | Testing, safe to break          |

## Setup for New Developers

1. **For production-like local environment:**
   - Create `application-local.properties`
   - Fill in your real database credentials and broker API tokens

2. **For dev/test environment:**
   - Create `application-dev.properties`
   - Fill in your test database credentials (see `application-dev.properties` section in base config for required keys)

## Security Notes

- `application-local.properties` and `application-dev.properties` are **gitignored** — they contain sensitive credentials
- **NEVER** commit real passwords or API tokens to Git
- `application.properties` (base config) uses empty placeholders for all secrets and is safe to commit
