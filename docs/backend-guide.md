# Vortex Backend

The Spring Boot backend provides APIs for trades, positions, cash flows, market events, and broker synchronization.

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Maven
- PostgreSQL, Spring Data JPA, and Flyway

## Getting Started

### Prerequisites

Select the execution environment and check its tools as described in [AGENTS.md](../AGENTS.md#1-环境隔离).

- **JDK 17 or higher**
  ```bash
  # Check Java version
  java -version
  ```
- **Maven 3.6+**
  ```bash
  # Check Maven version
  mvn -version
  ```

### Running the Application

Application startup connects to PostgreSQL and may run Flyway migrations. Set up a confirmed, isolated database and the `dev` profile before starting the application for integration work. The `local` profile is for the real ledger. [Database setup](database-setup.md) · [Profile configuration](local-config-guide.md)

#### Run with Maven

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Build and run the JAR

Maven packages files from `backend/src/main/resources/`, including local profile files present at build time. Build distributable artifacts from a checkout without credential files in that directory, and supply database credentials and profile settings through the runtime environment.

```bash
mvn -f backend/pom.xml clean package
java -jar backend/target/vortex-backend-1.0.0.jar --spring.profiles.active=dev
```

The deployment script also copies the JAR into `deploy/output/` and archives it. Choose the profile when running the generated `start.sh` or JAR.

To package without running unit tests, use the explicit Maven option.

```bash
mvn -f backend/pom.xml clean package -DskipTests
```

### Verifying the Service

The service defaults to `http://localhost:8080`.

#### 1. Check Console Logs

Upon successful startup, the console should display information similar to:
```
Started Application in X.XXX seconds
```

#### 2. Check the health endpoint

```bash
curl http://localhost:8080/api/health
```

The endpoint returns the application status. Review startup logs separately for the database connection, Flyway migration, and JPA initialization.

### Troubleshooting

#### Port Already in Use

If port 8080 is occupied, set `server.port` in the selected profile's local override. If you also use the frontend, update the Vite proxy target for that session to match.

```properties
server.port=8081
```

#### Java Version Mismatch

Ensure you are using JDK 17 or higher, as required by the project configuration.

#### Slow Maven Dependency Download

The first run downloads dependencies. Check the configured repository and any existing Maven mirror in the selected environment. Changes to user-wide Maven configuration require approval under the repository's environment rules.

### Integration with Frontend

After the backend starts at `http://localhost:8080`, start the frontend from the same execution environment. Follow the dependency setup in the [frontend guide](frontend-guide.md).

```bash
npm --prefix frontend run dev
```

The frontend will start at `http://localhost:3000` and automatically proxy API requests to the backend.

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── vortex/
│   │   │           ├── Application.java          # Main application entry point
│   │   │           ├── controller/               # REST endpoints, including HealthController
│   │   │           ├── service/                  # Business rules and transactions
│   │   │           ├── repository/               # Database access
│   │   │           └── sync/                     # Broker adapters and sync orchestration
│   │   └── resources/
│   │       ├── application.properties           # Base configuration and placeholders
│   │       └── db/migration/                     # Versioned Flyway migrations
│   └── test/
│       └── java/com/vortex/                      # Controller, service, and sync unit tests
└── pom.xml                                        # Maven configuration
```

## 单元测试

> 📋 完整的单元测试规范请参阅 [单元测试规范](unit-test-spec.md)。核心原则：**单元测试不得操作数据库中的数据**。

### 测试框架

- **JUnit 5** - 测试框架（通过 `spring-boot-starter-test` 内置）
- **Mockito** - Mock 框架（通过 `spring-boot-starter-test` 内置）

### 运行测试

```bash
# 在工作树根目录运行所有测试
mvn -f backend/pom.xml test

# 运行指定测试类
mvn -f backend/pom.xml -Dtest=TradeVerificationServiceTest test

# 运行指定测试方法
mvn -f backend/pom.xml '-Dtest=TradeVerificationServiceTest#validCallOptionSymbol_shouldPass' test
```

### 测试思路

后端单测使用 Mockito 隔离 Repository、券商客户端等外部依赖，无需启动应用或连接数据库。以下列出交易核对、交易服务和持仓服务的用例示例，同步与控制器用例见 `backend/src/test/java/com/vortex/`。

### 业务测试示例

#### 1. TradeVerificationServiceTest（交易数据核对验证）

Mock 依赖：`TradeRecordRepository`

测试覆盖全部 6 条核对规则，每条规则包含正常通过和异常检出的场景：

| 规则 | 测试场景 | 用例数 |
|---|---|---|
| 规则1：期权证券代码格式 | 合法 CALL/PUT 代码通过、格式不匹配报错、底层证券代码不匹配报错、期权类型标识错配报错、日期非法报错、非期权类型跳过 | 7 |
| 规则2：港股证券代码格式 | 纯数字通过、含字母报错、HKD 期权跳过、非 HKD 币种跳过 | 4 |
| 规则3：期权被动操作费用价格 | 到期 fee=0/price=0 通过、fee 不为 0 报错、price 不为 0 报错、行权期权侧 fee=0 通过、fee 不为 0 报错、股票侧不触发 | 6 |
| 规则4：美股证券代码格式 | 纯字母通过、含数字报错、期权跳过、非 USD 跳过 | 4 |
| 规则5：证券代码类别一致性 | 同 symbol 同类型通过、同 symbol 不同类型报错、不同 symbol 各异不冲突 | 3 |
| 规则6：触发类型关联类型一致性 | OPTION+OPTION_EXPIRE 通过、OPTION+STOCK_SPLIT 报错、MARKET_EVENT+STOCK_SPLIT 通过、MARKET_EVENT+OPTION_EXERCISE 报错、MANUAL 跳过 | 5 |
| 综合场景 | 空记录列表通过、多条记录混合异常统计 | 2 |

**测试设计要点：**
- 每条规则都测试「符合条件 → 通过」和「不符合条件 → 检出异常」两面
- 验证规则的「跳过条件」：如非期权记录应跳过规则 1，非 HKD 币种应跳过规则 2
- 综合场景验证多条记录中错误数量的统计正确性

#### 2. TradeRecordServiceTest（交易记录服务）

Mock 依赖：`TradeRecordRepository`、`BrokerRepository`、`StrategyRepository`

| 模块 | 测试场景 | 用例数 |
|---|---|---|
| 创建交易记录 | 股票金额自动计算（数量×价格）、期权金额自动计算（数量×价格×100）、券商不存在报错、底层证券代码为空报错、数量为 0 报错、价格为负报错、策略不存在报错 | 7 |
| 触发来源一致性校验 | MANUAL+refId=0+NONE 通过、MANUAL+refId≠0 报错、MARKET_EVENT+refId=0 报错、MARKET_EVENT+NONE 报错、MARKET_EVENT+期权子类型报错、OPTION+市场事件子类型报错、OPTION+EXPIRE+price≠0 报错 | 7 |
| 软删除 | 存在的记录成功标记删除、不存在的记录抛异常 | 2 |
| 更新记录 | 更新成功并重新计算金额、更新不存在的记录抛异常 | 2 |

**测试设计要点：**
- 验证「金额自动计算」逻辑：股票直接乘，期权额外×100 合约乘数
- 验证「默认值填充」：创建时自动设置 `TradeTrigger.MANUAL`、`TriggerRefType.NONE`、`triggerRefId=0`
- 验证「触发来源交叉校验」：MANUAL/MARKET_EVENT/OPTION 各有合法的 refType 子集，交叉使用应报错

#### 3. PositionServiceTest（持仓计算服务）

Mock 依赖：`TradeRecordRepository`、`BrokerRepository`

| 测试场景 | 说明 |
|---|---|
| 空交易记录 | 返回空持仓列表 |
| 单次买入 | 正确产生持仓 |
| 买入后全部卖出 | 持仓为空（数量为 0 的记录被过滤） |
| 买入后部分卖出 | 持仓为剩余数量 |
| 同证券不同券商 | 按券商分别计算持仓 |
| 按券商筛选 | 仅返回指定券商的持仓 |
| 多次买入累加 | 同证券同券商的持仓正确累加 |

**测试设计要点：**
- 持仓 = BUY 累加 - SELL 累减，按 `(symbol, brokerId)` 分组
- 持仓数量为 0 的记录应被过滤，不出现在结果中
- 传入 `brokerId` 参数时走按券商筛选的查询路径
