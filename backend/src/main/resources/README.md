# 后端资源与配置

[`application.properties`](application.properties) 是受版本控制的基础配置；本地 `application-dev.properties` 和 `application-local.properties` 被 Git 忽略，按需创建。完整启动步骤、环境变量和凭据来源见 [配置指南](../../../../docs/local-config-guide.md)。

## 当前默认值

| 配置 | 基础文件值与含义 |
| --- | --- |
| 服务 | `server.port=8080`，应用名 `vortex-backend` |
| 数据库 | `jdbc:postgresql://localhost:5432/vortex`，用户名 `dermesos`，密码为空 |
| JPA | `ddl-auto=validate`，JDBC 时区 `UTC+8`；Hibernate 不负责建表 |
| 迁移 | POM 引入 Flyway，Spring Boot 自动执行 `db/migration/`，当前 V1–V35 |
| 连接池 | HikariCP 最大 10、最小空闲 5；超时与生命周期见基础文件 |
| 日志 | root 为 INFO，`com.vortex` 为 DEBUG，`show-sql=true` |
| 券商 | IBKR／Tiger凭据由同步配置的账户行保存到数据库；IBKR base URL有默认值 |

`dev`、`local` 是项目用途约定，没有内置的自动库名切换：激活 profile 只加载存在的覆盖文件。`dev` 必须显式指向隔离开发库（约定 `vortex_dev`）；`local` 用于真实账本。两个 profile 都可能运行 Flyway，不能把 `validate` 理解为整个应用不会修改 schema。没有有效覆盖时仍连接基础默认库。

## 配置加载与分发

常用来源从低到高为基础文件、所选 profile 文件、环境变量和启动参数。通过 `SPRING_PROFILES_ACTIVE` 选择 profile，通过 `SPRING_DATASOURCE_URL`、`SPRING_DATASOURCE_USERNAME`、`SPRING_DATASOURCE_PASSWORD` 覆盖连接。

从工作树根目录，在确认隔离库配置后启动：

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

Maven 会把本目录中存在的 profile 文件打包进 JAR。Git 忽略不等于打包排除；分发前确认没有本地凭据文件，在运行环境注入配置。凭据不得提交或写入共享日志；账户 ID 日志的当前例外见 [研发约定](../../../../AGENTS.md#验证与数据安全)。

## 数据库脚本

IBKR／Tiger以数据库 `broker_sync_connections` 中的账户凭证为唯一运行时来源，各数据库分别维护配置，旧profile凭证不回退或自动导入。V32 将 V31 明文 IBKR 配置关联到匹配券商行并移除旧表；有 Token 却缺少匹配券商时回滚，修正关联后重试。用户确认没有历史加密凭证。V33 分离券商与托管账户，原连接迁给对应待补全账户；补齐真实 ID 后才能同步，Tiger 资金账号由账户资料提供。V34 将邮箱、电话和账户备注从券商移至账户，旧资料按可确定归属迁移，否则保留在新待补全账户。V35 新增独立券商简介、移除券商状态，启停仅由账户控制，已有账户状态不变。运行和备份以数据库为准，迁移与恢复见 [账户指南](../../../../docs/accounts.md#迁移与恢复)。详见 [IBKR配置与恢复](../../../../docs/broker-sync/brokers/ibkr/README.md#凭证与部署)和 [Tiger配置](../../../../docs/broker-sync/brokers/tiger/README.md)。

[`db/migration/`](db/migration/) 保存追加式 Flyway 迁移，已应用脚本保持原样；[`db/manual/flyway_baseline_for_existing_db.sql`](db/manual/flyway_baseline_for_existing_db.sql) 仅适用于已经人工应用 V1–V18、没有 Flyway 历史的旧库，不能用于任意现有数据库。 [数据库指南](../../../../docs/database-setup.md)

后端单测用模拟仓储和合成数据，不加载应用 profile 或连接数据库。迁移与真实事务验证使用自建临时库，见 [e2e 指南](../../../../e2e/README.md)。
