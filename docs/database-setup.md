# PostgreSQL 数据库配置指南

## 选择数据库

后端使用 PostgreSQL。联调和迁移验证前，先确认数据库主机、端口、库名和用户。开发与测试使用隔离的开发库、合成数据和 `dev` profile。`local` profile 面向真实账本，启动应用时可能运行 Flyway 迁移。后端单测通过 Mockito 隔离数据库依赖，可直接运行 `mvn -f backend/pom.xml test`。 [验证与数据安全](../AGENTS.md#验证与数据安全)

## 准备开发库

由数据库管理员按环境创建开发库和专用用户。用于 Flyway 迁移的数据库用户需要在目标 schema 中创建和修改对象的权限。以下示例在连接到已确认的隔离 PostgreSQL 实例后执行，库名和角色名按环境替换。

```sql
CREATE ROLE vortex_dev LOGIN;
\password vortex_dev
CREATE DATABASE vortex_dev OWNER vortex_dev;
```

`\password` 在 `psql` 中交互输入密码。应用端使用 `backend/src/main/resources/application-dev.properties` 或由运行环境注入配置。填写实际连接信息前，先确认目标库可用于开发测试。受版本控制的 `application.properties` 保留默认值与占位符。 [本地配置指南](local-config-guide.md)

```properties
spring.datasource.url=jdbc:postgresql://<db-host>:5432/<dev-db>
spring.datasource.username=<dev-user>
spring.datasource.password=<dev-password>
```

## 表结构与默认配置

- Flyway 通过 [`db/migration/`](../backend/src/main/resources/db/migration/) 下的脚本初始化空库并升级已有数据库。新结构变更通过追加迁移完成。
- `spring.jpa.hibernate.ddl-auto=validate` 让 Hibernate 检查实体映射与数据库结构。表结构变更由 Flyway 管理。
- HikariCP 的连接池配置和 Hibernate 的 JDBC 时区设置见 [application.properties](../backend/src/main/resources/application.properties)。需要覆盖时写入所选 profile 的配置。
- [旧库手动基线脚本](../backend/src/main/resources/db/manual/flyway_baseline_for_existing_db.sql) 只适用于已经人工应用 V1 至 V18、尚无 Flyway 记录的旧库。使用前须逐项核对目标库结构与迁移记录。

## 启动与验证

确认 `dev` 配置指向隔离开发库后，从工作树根目录启动后端。

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

从启动日志核对生效的 profile、数据库连接、Flyway 和 JPA 初始化结果。应用启动后可查询服务状态。

```bash
curl http://localhost:8080/api/health
```

## 排查与重置

- 连接被拒绝时，检查 PostgreSQL 服务、目标端口和所选环境到数据库的网络路径。
- 认证失败时，检查生效的 profile、数据库用户和凭据来源。库不存在时，检查配置中的库名和创建记录。
- Flyway 或 JPA 校验失败时，核对迁移历史、目标 schema 和实体映射。保留已应用迁移的原样。
- 开发库重置脚本会删除并重建目标库的 `public` schema。运行前须确认目标库可丢弃，并获得本次清空授权。 [脚本说明](../e2e/README.md)
