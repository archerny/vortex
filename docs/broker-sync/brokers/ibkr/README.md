# IBKR（盈透证券）同步

> **状态**：✅ Phase 2 已实现（API → 暂存 → 导入 全链路），待真实环境验证；🔧 失败清理机制待 [import-consistency.md](../../framework/import-consistency.md) v2 实施完成后全面对齐
> **适配器**：`IbkrSyncAdapter`
> **API 类型**：Flex Web Service（REST + XML）
> **Broker Code**：`ibkr`

---

## 当前实现

| 能力 | 状态 | 实现位置 |
|------|------|----------|
| Flex Web Service API 调用（SendRequest + GetStatement 轮询） | ✅ | `IbkrFlexClient` |
| XML 解析为 `IbkrOrderRecord` / `IbkrTradeConfirm` | ✅ | `FlexQueryParser` |
| 写入暂存表 `ibkr_staged_orders` + `ibkr_staged_trade_confirms` | ✅ | `IbkrStagingService` |
| 暂存表 → `trade_records` 字段映射 | ✅ | `IbkrImportService` |
| BookTrade 触发判定（期权到期/行权/被指派） | ✅ | `IbkrImportService` + [booktrade-mapping.md](./booktrade-mapping.md) |
| `triggerRefId` 反向回填（STK 侧关联期权 trade_records.id） | ✅ | `IbkrImportService` |
| 异步执行（后台线程池 + 前端提交后立即返回） | ✅ | 详见 [architecture.md § 四](../../architecture.md#四异步执行模型) |

---

## 文档索引

| 文档 | 说明 |
|------|------|
| [flex-web-service.md](./flex-web-service.md) | IBKR Flex Web Service 接入方案（Token/Query ID 获取、两步 HTTP 流程、XML 解析） |
| [staging-schema.md](./staging-schema.md) | IBKR 暂存表结构（`ibkr_staged_orders` + `ibkr_staged_trade_confirms`）与字段映射规范 |
| [booktrade-mapping.md](./booktrade-mapping.md) | BookTrade 识别、code 解析、期权事件 trigger 映射、反向回填 |

---

## 已知限制

- **XML 解析器**：当前使用 DOM 解析（`javax.xml.parsers.DocumentBuilder`），大批量数据下可考虑改 SAX/JAXB（详见 data-persistence.md D-3）
- **Token 存储**：当前放在 `application-local.properties`，后续可能迁移到数据库（D-4）
- **仅支持 Trade Confirmation Flex Query**：Activity Flex Query（分红、利息等）暂未支持（D-5）
- **冲突检测**：当前仅依赖 `(external_broker, external_id)` 唯一索引做去重，不做手动记录的冲突匹配（R-8）

---

## 配置

```properties
# application-local.properties
broker.ibkr.token=<your_24_digit_token>
broker.ibkr.query-id=<your_query_id>
```

详见 [flex-web-service.md § 用户配置](./flex-web-service.md)。
