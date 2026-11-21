# 后端资源与配置

[`application.properties`](application.properties) 是受版本控制的基础配置；本地 `application-dev.properties` 和 `application-local.properties` 被 Git 忽略，按需创建。完整启动步骤、环境变量和凭据来源见 [配置指南](../../../../docs/local-config-guide.md)。

## 当前默认值

| 配置 | 基础文件值与含义 |
| --- | --- |
| 服务 | `server.port=8080`，应用名 `vortex-backend` |
| 数据库 | `jdbc:postgresql://localhost:5432/vortex`，用户名 `dermesos`，密码为空 |
| JPA | `ddl-auto=validate`，JDBC 时区 `UTC+8`；Hibernate 不负责建表 |
| 迁移 | POM 引入 Flyway，Spring Boot 自动执行 `db/migration/`，当前 V1–V30 |
| 连接池 | HikariCP 最大 10、最小空闲 5；超时与生命周期见基础文件 |
| 日志 | root 为 INFO，`com.vortex` 为 DEBUG，`show-sql=true` |
| 券商 | Tiger凭据占位符为空；IBKR Token／Query ID由同步配置页保存到数据库，base URL有默认值 |

`dev`、`local` 是项目用途约定，没有内置的自动库名切换：激活 profile 只加载存在的覆盖文件。`dev` 必须显式指向隔离开发库（约定 `vortex_dev`）；`local` 用于真实账本。两个 profile 都可能运行 Flyway，不能把 `validate` 理解为整个应用不会修改 schema。没有有效覆盖时仍连接基础默认库。

## 配置加载与分发

常用来源从低到高为基础文件、所选 profile 文件、环境变量和启动参数。通过 `SPRING_PROFILES_ACTIVE` 选择 profile，通过 `SPRING_DATASOURCE_URL`、`SPRING_DATASOURCE_USERNAME`、`SPRING_DATASOURCE_PASSWORD` 覆盖连接。

从工作树根目录，在确认隔离库配置后启动：

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

Maven 会把本目录中存在的 profile 文件打包进 JAR。Git 忽略不等于打包排除；分发前确认没有本地凭据文件，在运行环境注入配置。凭据不得提交或写入共享日志；账户 ID 日志的当前例外见 [研发约定](../../../../AGENTS.md#验证与数据安全)。

## 数据库脚本

IBKR不再从profile中的Token／Query ID回退或自动迁入凭据。不同数据库各自维护配置；密钥首次保存时生成在工作树根目录`.local/ibkr-sync.key`，从根目录或`backend/`启动路径一致。目录700、文件600；打包部署可用`broker.ibkr.secret-key-file`指定稳定的持久化路径，备份数据库时另外保管对应密钥。详见 [IBKR配置与恢复](../../../../docs/broker-sync/brokers/ibkr/README.md#凭证密钥与部署)。

[`db/migration/`](db/migration/) 保存追加式 Flyway 迁移，已应用脚本保持原样；[`db/manual/flyway_baseline_for_existing_db.sql`](db/manual/flyway_baseline_for_existing_db.sql) 仅适用于已经人工应用 V1–V18、没有 Flyway 历史的旧库，不能用于任意现有数据库。 [数据库指南](../../../../docs/database-setup.md)

后端单测用模拟仓储和合成数据，不加载应用 profile 或连接数据库。迁移与真实事务验证使用自建临时库，见 [e2e 指南](../../../../e2e/README.md)。
