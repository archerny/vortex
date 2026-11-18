# 单元测试规范

## 核心原则

> **⚠️ 单元测试不得操作数据库中的数据。**

所有单元测试必须在纯内存环境中运行，不得连接、读取、写入或修改任何真实数据库。这是本项目单元测试的首要规范，违反此规则的测试代码不允许合入。

## 为什么不允许操作数据库

1. **数据安全**：测试可能意外修改或删除生产/开发环境的真实数据
2. **测试隔离**：依赖外部数据库的测试结果不稳定，容易因环境差异导致误判
3. **执行速度**：纯内存测试执行速度远快于需要数据库连接的测试
4. **环境无关**：开发者无需配置数据库即可运行测试，降低协作门槛

## 后端测试规范（Java）

### 框架要求

- 测试框架：**JUnit 5**（通过 `spring-boot-starter-test` 内置）
- Mock 框架：**Mockito**（通过 `spring-boot-starter-test` 内置）

### 必须遵守的规则

1. **有 Mockito 依赖的测试优先使用 `@ExtendWith(MockitoExtension.class)`**；纯解析、转换或工厂测试无需 Mockito。不得使用 `@SpringBootTest` 等启动应用并连接数据库的注解。
2. **所有 Repository 必须使用模拟对象**，可用 `@Mock` 或 `Mockito.mock()`，不得注入真实 Repository 实现。
3. **被测试的 Service 可用 `@InjectMocks` 或显式构造**；多组件内存调用链可以组合真实业务对象，但数据库和网络依赖仍须模拟。
4. **使用 `when(...).thenReturn(...)` 预设 Repository 的返回数据**，而非从真实数据库查询

### 标准测试类结构

```java
@ExtendWith(MockitoExtension.class)
class XxxServiceTest {

    @Mock
    private XxxRepository xxxRepository;  // Mock 数据库依赖

    @InjectMocks
    private XxxService xxxService;        // 被测试的 Service

    @Test
    void shouldDoSomething() {
        // given - 准备测试数据和 Mock 行为
        when(xxxRepository.findAll()).thenReturn(List.of(...));

        // when - 执行被测方法
        var result = xxxService.doSomething();

        // then - 验证结果
        assertNotNull(result);
        verify(xxxRepository).findAll();
    }
}
```

### 禁止的写法

```java
// ❌ 禁止：使用 @SpringBootTest 会启动容器并连接数据库
@SpringBootTest
class XxxServiceTest { ... }

// ❌ 禁止：使用 @DataJpaTest 会连接数据库
@DataJpaTest
class XxxRepositoryTest { ... }

// ❌ 禁止：使用 @Autowired 注入真实的 Repository
@Autowired
private XxxRepository xxxRepository;

// ❌ 禁止：直接调用 EntityManager 操作数据库
entityManager.persist(entity);
```

### 测试目录结构

```
backend/src/test/java/com/vortex/
├── controller/          # HTTP 错误与响应契约
├── service/             # 交易、持仓、事件和历史边界
└── sync/
    ├── core/            # 批次执行、失败、清理与恢复
    └── adapter/
        ├── ibkr/        # 合成 XML、映射、重复与期权关联
        └── tiger/       # 暂存、导入与映射
```

### 运行命令

```bash
# 从工作树根目录执行
mvn -e -f backend/pom.xml test
mvn -e -f backend/pom.xml -Dtest=XxxServiceTest test
mvn -e -f backend/pom.xml '-Dtest=XxxServiceTest#methodName' test
```

## 前端测试规范（JavaScript）

### 框架要求

- 测试框架：**Vitest**，Node 环境配置见 [vite.config.js](../frontend/vite.config.js)。

### 必须遵守的规则

1. **测试运行环境设置为 `node`**，不得连接任何后端服务或数据库
2. **不得在测试中发起真实的网络请求**（`fetch`、`axios` 等），如需测试涉及网络请求的逻辑，必须使用 Mock
3. **测试对象包括纯函数、常量、配置、合成数据和模拟 API 封装**；也可使用真实组件的静态渲染验证展示契约。现有费用测试在 Node 中渲染列表列和统计组件，这不等同于浏览器交互验收。

### 测试目录结构

测试文件统一放置在对应模块的 `__tests__` 目录下：

```
frontend/src/
├── constants/
│   └── __tests__/
│       ├── tradeConstants.test.js
│       └── menuConfig.test.js
├── services/__tests__/       # 模拟 Axios 的费用契约
├── utils/__tests__/          # 费用展示／排序与冲突格式
└── pages/profit/chart-demo/__tests__/  # 固定合成图表数据
```

### 运行命令

```bash
# 从工作树根目录执行；安装前遵守环境与锁文件约定
npm --prefix frontend ci
npm --prefix frontend test
npm --prefix frontend test -- --watch
```

## 命名约定

| 约定项 | 规则 | 示例 |
|---|---|---|
| 测试文件名 | 被测类名 + `Test` 后缀（后端）或 `.test.js`／`.test.jsx` 后缀（前端） | `TradeRecordServiceTest.java`、`tradeFee.test.jsx` |
| 测试方法名 | 采用 `should + 预期行为` 或 `场景描述_期望结果` 格式 | `shouldCalculateAmountForStock`、`validCallOptionSymbol_shouldPass` |
| 测试结构 | 遵循 **Given-When-Then** 模式 | 准备数据 → 执行方法 → 验证结果 |

## 测试维护与清理

- 新增测试前，先明确要保护的业务契约、边界或曾发生的缺陷，检查现有用例能否扩展，避免为同一输入、同一执行路径和相同断言重复建例。一般不单独测试纯 getter、setter 或调试用 `toString()`；自定义校验、转换、序列化等实际契约另行判断。
- 测试较旧或缺陷已修复，不是删除依据。删除或合并前，证明行为已移除，或列出保留用例如何覆盖原输入、执行路径和有效断言；不能仅凭名称、总数或覆盖率判断重复。失败恢复、边界、并发及不同验证层次的用例应分别核对。
- 提交的夹具和断言只使用合成数据，不依赖个人账户文件，不保留调查时的大量调试输出。临时复现程序有持续回归价值时再纳入测试。
- 清理后运行相关完整测试集，核对实际执行的类、嵌套类和参数化案例；不要通过跳过、放宽断言或缩小执行范围获得更快结果。历史 Surefire XML 可能残留，清单以本轮日志中的实际运行套件为准。

逐项取舍和验证记录见 [测试清理依据](TODO-ARCHIVE.md#测试清理依据)。从工作树根目录执行 `mvn -e -f backend/pom.xml test` 和 `npm --prefix frontend test`；已有完整 Maven 缓存时可加 `-o` 离线运行。解析测试已使用合成 XML，无需排除个人夹具用例。

单测数量随实现变化，以本轮执行的类、嵌套与参数化案例清单为准。数据库迁移、实际事务／并发和浏览器交互分别使用 [e2e 工具](../e2e/README.md) 和合成接口验收；它们与纯内存单测分开记录，不能相互替代。

## 测试执行效率

后端 Surefire 默认在两个独立 JVM 中执行，将测试输出保存到 `backend/target/surefire-reports/*-output.txt`，保留控制台测试汇总和失败状态：

```sh
mvn -e -f backend/pom.xml test
```

后端测试命令统一加 `-e`，在 Maven 失败时打印完整异常调用栈及 `Caused by`；JVM 尚未启动时的原生错误诊断也须保留在完整命令日志中。已有完整 Maven 缓存时可加 `-o`；诊断时可用 `-DforkCount=1`、`-Dmaven.test.redirectTestOutputToFile=false` 覆盖默认配置。成功日志落盘后只返回汇总，失败时再读取原日志，并保留 Maven 退出码。

Token 脱敏回归在测试中替换等待方法，保留真实两阶段请求路径及全部脱敏断言，跳过报表生成和限流的实际休眠，不增加返回耗时断言。生产等待保持原实现；整批清理测试中的退避仍需另行优化。当前部分测试临时修改共享退避参数或 logger，启用 JVM 内方法并行前须先隔离这些状态。前端 Vitest 已默认并行。

当前 4 CPU／约 4 GiB 的 `ubuntu-dev` 暖运行估测为 14.837→8.945 秒，完整 541 项逐项一致，无重复／失败／错误／跳过；成功日志控制台字节量约减少 98.3%，这不等于整轮 AI Token 少 98%。两／三／四 JVM 对照、客户端改动的独立复核、Token 估算前提和原始证据统一见 [测试执行优化方案](../design/0044-2026-11-18-test-execution-optimization.md)。

只改局部业务时先运行相关完整测试类以快速反馈，交付前完成改动所需的全部相关验收；代码和验证条件未变时复用有效结果。不要反复 `clean`、重新安装依赖或输出整份成功日志。

## 检查清单

在提交单元测试代码前，请对照以下清单自查：

- [ ] 测试类未使用 `@SpringBootTest`、`@DataJpaTest` 等启动容器的注解
- [ ] 所有数据库依赖（Repository）均为模拟对象
- [ ] 测试中不存在直接的数据库连接或 SQL 执行
- [ ] 前端测试中不存在真实的网络请求调用
- [ ] 测试可以在无数据库环境下独立运行通过
- [ ] 测试方法命名清晰，能表达测试意图
