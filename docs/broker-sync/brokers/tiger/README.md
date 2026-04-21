# 老虎证券同步

> **状态**：✅ Phase 1 已实现（API → 日志输出）；📋 Phase 3 设计稿已定（两阶段导入），待编码
> **适配器**：`TigerSyncAdapter`
> **API 类型**：Tiger Open API（REST，官方 Java SDK）
> **Broker Code**：`tiger`
> **账户前提**：老虎国际 = 环球账户（GLOBAL，U 开头账号）

---

## 当前实现

| 能力 | 状态 | 实现位置 |
|------|------|----------|
| Tiger Open API 调用（`get_filled_orders`） | ✅ | `TigerSyncAdapter` |
| 反序列化为 `TigerOrderRecord` | ✅ | `TigerOrderRecord` |
| 90 天时间窗口自动拆分 | ✅ | `TigerSyncAdapter.fetchOrdersInWindows` |
| 日志逐条打印（核对原始数据） | ✅ | `TigerSyncAdapter` |
| 暂存表 `tiger_staged_orders` | 📋 设计稿已定，待编码 | — |
| 导入 `trade_records`（含去重、过滤） | 📋 设计稿已定，待编码 | — |
| `attrDesc` 期权事件映射 | ⏭️ 延后（Phase 3.x 等真实样本） | — |

---

## 文档索引

| 文档 | 说明 |
|------|------|
| [open-api.md](./open-api.md) | Tiger Open API 接入方案（SDK 选型、认证流程、数据范围） |
| [staging-schema.md](./staging-schema.md) | Tiger 暂存表结构与字段映射规范（Phase 3 数据契约） |
| [phase3-plan.md](./phase3-plan.md) | Phase 3 编码与工作计划（阶段划分、单测策略、提交粒度） |

---

## Phase 3 范围与边界

### ✅ 本期支持
- STK（美股 / 港股 / A 股连通）
- OPT（CALL / PUT 的正常交易，即 `attrDesc` 为空的订单）

### ❌ 本期不支持（暂存但导入 `FAILED`，数据不丢失）
- 碎股（`quantityScale > 0`）
- 期权事件订单（`Exercise` / `Assignment` / `Expired` 等 `attrDesc` 非空的订单） — 等真实样本收集后在 Phase 3.x 补充映射
- `WAR` / `IOPT` / `FUT` / `FUND` / `CASH` / `CC` 资产类型

### 已知限制

- **当前已部署**：Phase 1（日志输出），不写入数据库
- **Phase 3 实施中**：设计稿已定稿，编码待开工，详见 [phase3-plan.md](./phase3-plan.md)

---

## 配置

```properties
# application-local.properties
broker.tiger.tiger-id=<your_tiger_id>
broker.tiger.private-key=<your_rsa_private_key>
broker.tiger.account=<your_account>
```

详见 [open-api.md § 凭证管理](./open-api.md)。
