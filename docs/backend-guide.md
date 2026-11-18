# Vortex 后端开发指南

后端使用 Java 编译目标 17、Spring Boot 3.2.0、Maven、PostgreSQL、Spring Data JPA 和 Flyway。依赖、编译与测试参数见 [pom.xml](../backend/pom.xml)，当前业务规则见 [知识索引](../knowledge/README.md)。

## 环境与启动

按 [环境隔离约定](../AGENTS.md#1-环境隔离) 选择当前工作树对应的环境，并检查已有工具：

```bash
java -version
mvn -version
```

POM 的 Surefire 配置包含 `-XX:+EnableDynamicAgentLoading`。运行单测时需使用接受该参数的 JDK；既有验收使用 Java 25、编译目标保持 17，运行环境不应仅凭编译目标判断。安装或更换工具遵循环境约定。

先确认隔离数据库与 `dev` 配置。基础配置默认连接 `vortex`，没有提供开发库覆盖时，即使激活 `dev` 也仍继承该默认值。启动会执行数据库连接、Flyway 自动迁移和 Hibernate 结构校验。 [数据库指南](database-setup.md) · [配置指南](local-config-guide.md)

从工作树根目录运行：

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

默认端口 8080。启动后检查日志中的 profile、Flyway 和 JPA 初始化，并查询应用状态：

```bash
curl http://localhost:8080/api/health
```

健康接口是应用状态入口，不能替代账本一致性、迁移或券商连通性验收。

## Build and run the JAR

```bash
mvn -f backend/pom.xml clean package
java -jar backend/target/vortex-backend-1.0.0.jar --spring.profiles.active=dev
```

`clean package` 默认执行单测。确需跳过时显式使用 `-DskipTests`，不能将跳过后的构建描述为测试通过。Maven 会打包资源目录中存在的本地 profile 文件，分发构建应使用没有凭据文件的工作树，在运行时注入配置。

`deploy/build-backend.sh` 生成 JAR、启动脚本及压缩包。脚本的 `-p` 当前不影响 Maven 构建或生成脚本的默认 profile；启动脚本需显式传入 profile，详见 [部署指南](../deploy/README.md)。

## 代码入口

| 路径 | 职责 |
| --- | --- |
| [`Application.java`](../backend/src/main/java/com/vortex/Application.java) | Spring Boot 启动入口 |
| [`controller/`](../backend/src/main/java/com/vortex/controller/) | HTTP 接口与错误响应 |
| [`service/`](../backend/src/main/java/com/vortex/service/) | 业务规则、事务、事件历史约束与账本短写协调 |
| [`repository/`](../backend/src/main/java/com/vortex/repository/) | JPA 查询与数据库访问 |
| [`entity/`](../backend/src/main/java/com/vortex/entity/)／[`dto/`](../backend/src/main/java/com/vortex/dto/) | 持久化对象、枚举与接口数据 |
| [`sync/core/`](../backend/src/main/java/com/vortex/sync/core/) | 适配器路由、异步执行、失败处理与批次清理 |
| [`sync/adapter/`](../backend/src/main/java/com/vortex/sync/adapter/) | IBKR／Tiger 拉取、暂存、映射和单条 Worker |
| [`db/migration/`](../backend/src/main/resources/db/migration/) | 当前 V1–V29，结构变更追加迁移 |

Controller／Service／Repository 分层、同步整批失败清理、精度和历史数据要求见 [研发约定](../AGENTS.md)。市场事件写入与同步有共同协调约束，修改前阅读 [市场事件](../knowledge/market-events.md) 和 [同步生命周期](../knowledge/broker-sync/lifecycle.md)。

## 单元测试

从工作树根目录执行，无需数据库配置或应用 profile：

```bash
mvn -f backend/pom.xml test
mvn -f backend/pom.xml -Dtest=TradeRecordServiceTest test
mvn -f backend/pom.xml '-Dtest=TradeVerificationServiceTest#validCallOptionSymbol_shouldPass' test
```

JUnit 5／Mockito 隔离 Repository 和券商客户端；解析夹具全部使用合成 XML。测试包含 Controller、Service、同步核心和两个适配器，覆盖金额与费用、数量限制、持仓、事件顺序／历史锁、重复内容、期权关联、失败清理和恢复。清单以实际源测试及本轮 Surefire 报告为准，不维护固定用例数量。筛选运行时须核对嵌套及参数化案例。 [单测规范](unit-test-spec.md)

内存单测不能证明真实事务、迁移和数据库查询正确；对应验证入口见 [e2e 指南](../e2e/README.md)。已有运行结果和验收证据见 [待办归档](TODO-ARCHIVE.md)。

## 排查

- 端口冲突：通过当前 profile 或启动参数覆盖 `server.port`，并同步调整前端 Vite 代理目标。
- 连接／迁移失败：核对实际生效的 datasource、数据库权限及 Flyway 历史，保留已应用迁移原样。
- 依赖下载失败：检查所选环境的仓库、网络和现有缓存；只有缓存完整时才使用 Maven `-o`。
- Surefire 退出握手等待：既有全量验收出现过该提示，详见验收记录；它不应被跳过测试或修改超时来掩盖。
- 同步失败或 HTTP 409：按 [恢复指南](broker-sync/recovery.md) 查看活动批次、失败阶段与历史锁，不直接删除成功批次。
