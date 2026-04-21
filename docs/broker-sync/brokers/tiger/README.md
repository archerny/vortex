# 老虎证券同步

> **状态**：✅ Phase 1 已实现（API → 日志输出），入库/去重等 Phase 3 再接入
> **适配器**：`TigerSyncAdapter`
> **API 类型**：Tiger Open API（REST，官方 Java SDK）
> **Broker Code**：`tiger`

---

## 当前实现

| 能力 | 状态 | 实现位置 |
|------|------|----------|
| Tiger Open API 调用（`get_filled_orders`） | ✅ | `TigerSyncAdapter` |
| 反序列化为 `TigerOrderRecord` | ✅ | `TigerOrderRecord` |
| 日志逐条打印（核对原始数据） | ✅ | `TigerSyncAdapter` |
| 暂存表 `tiger_staged_orders` | 📋 待规划 | — |
| 导入 `trade_records` | 📋 待规划 | — |

---

## 文档索引

| 文档 | 说明 |
|------|------|
| [open-api.md](./open-api.md) | Tiger Open API 接入方案（SDK 选型、认证流程、数据范围） |

---

## 已知限制

- **当前仅跑通 Phase 1（日志输出）**：不写入数据库，仅用于核对 API 返回数据的正确性
- **Phase 3 规划项**（详见 data-persistence.md D-6）：
  - 新建 `tiger_staged_orders` 暂存表
  - 实现 `TigerImportService`（暂存 → `trade_records`）
  - 去重机制（基于 Tiger `orderId`）
  - 单元测试

---

## 配置

```properties
# application-local.properties
broker.tiger.tiger-id=<your_tiger_id>
broker.tiger.private-key=<your_rsa_private_key>
broker.tiger.account=<your_account>
```

详见 [open-api.md § 凭证管理](./open-api.md)。
