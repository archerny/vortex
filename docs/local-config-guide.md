# Vortex 后端配置指南

## 选择 profile

| Profile | 用途 |
| --- | --- |
| `dev` | 使用隔离开发库和合成数据进行联调或迁移验证。启动前确认连接目标。 |
| `local` | 运行使用真实账本的应用。启动时可能触发 Flyway 迁移。 |

单元测试通过模拟对象隔离数据库和券商服务。从工作树根目录执行 `mvn -f backend/pom.xml test` 即可。 [验证与数据安全](../AGENTS.md#验证与数据安全)

## 配置文件

| 文件 | 用途 |
| --- | --- |
| [`application.properties`](../backend/src/main/resources/application.properties) | 受版本控制的基础配置，包含默认值和凭据占位符。 |
| `application-dev.properties` | 被 Git 忽略的开发库配置，按需创建。 |
| `application-local.properties` | 被 Git 忽略的真实账本配置，按需创建。 |

基础配置的默认库名为 `vortex`，开发测试库约定为 `vortex_dev`。创建 `dev` 配置时，将连接 URL 覆盖为实际开发库地址。数据库用户名与库名独立，按实际角色设置。外部 profile、环境变量和命令行中的连接配置也须与目标库一致。 [命名方案](../design/0023-2026-11-11-database-names.md)

可在 `backend/src/main/resources/` 创建所选 profile 的文件，填写该环境的数据库连接配置。下例使用开发库占位符。真实凭据通过本地文件或运行环境的秘密存储提供。 [数据库配置指南](database-setup.md)

```properties
spring.datasource.url=jdbc:postgresql://<db-host>:5432/<dev-db>
spring.datasource.username=<dev-user>
spring.datasource.password=<dev-password>
```

在本项目的启动方式下，常用配置来源的覆盖顺序从低到高为基础配置、所选 profile 的配置、环境变量、启动命令参数。可通过环境变量 `SPRING_PROFILES_ACTIVE` 选择 profile，通过 `SPRING_DATASOURCE_URL`、`SPRING_DATASOURCE_USERNAME` 和 `SPRING_DATASOURCE_PASSWORD` 注入数据库设置。 [配置文件说明](../backend/src/main/resources/README.md)

## 启动与检查

核对目标开发库后，从工作树根目录通过 Maven 启动应用。

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

在 IDE 中运行 `com.vortex.Application` 时，将 `SPRING_PROFILES_ACTIVE` 设置为 `dev`。运行 JAR 时，通过命令参数选择 `dev`。

```bash
java -jar backend/target/vortex-backend-1.0.0.jar --spring.profiles.active=dev
```

Maven 会将资源目录中存在的 profile 文件打包。构建分发用 JAR 前，确认资源目录没有本地凭据文件，部署时从运行环境注入配置。 [后端指南](backend-guide.md#build-and-run-the-jar)

检查启动日志中的 profile、Flyway 迁移和 JPA 初始化。应用就绪后，可在同一执行环境查询服务状态。

```bash
curl http://localhost:8080/api/health
```

连接失败时，核对启动参数、生效的 profile 文件、数据库目标和凭据来源。向他人转发日志前移除真实凭据及账户数据。
