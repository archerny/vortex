# 同步原始数据查看功能设计

> 创建日期：2026-04-21
> 状态：✅ 已实现
> 最后更新：2026-04-21

## 背景

原有同步管理页面只能查看 `broker_sync_batches` 的批次级汇总（总记录数、导入数、失败数等），无法下钻到每个批次真正同步到 staging 表的逐笔原始数据。当批次出现 `FAILED` 或数字对不上时，用户需要直接查看 staging 表的原始字段来审计和排障。

此外，原页面中的券商下拉（过滤用）硬编码了 IBKR/Tiger/Futu/Schwab 四个选项，违反了项目原则："券商管理是券商选项的单一数据源，其他地方应从 `/api/brokers` 读取"。本次一并修正。

## 目标

1. 为 IBKR 两张 staging 表提供只读查询入口：
   - `ibkr_staged_orders`（参与交易记录导入）
   - `ibkr_staged_trade_confirms`（仅用于审计对账，不参与导入）
2. 在同步管理页面新增 **原始数据** Tab，与原有 **同步批次** Tab 并列。
3. 原始数据 Tab 中用户先选券商，券商选项来自 `/api/brokers`。
4. 顺手修正同步批次 Tab 中券商过滤下拉也改为从 `/api/brokers` 拉取。

## 决策记录

| # | 决策 | 原因 |
|---|------|------|
| 1 | 全字段平铺，不做列隐藏 | 用户要求"数据库有什么字段就显示什么字段"。通过横向滚动 + 前两列冻结解决视觉压力。 |
| 2 | 不引入 DTO，直接返回实体 | staging 字段均为 VARCHAR，无 lazy 关系、无敏感字段。引入 DTO 会造成 60+ getter 冗余。 |
| 3 | 不引入 `Page/Pageable`，返回 `List` | 现有后端统一返回 `List<T>`，从未用过 Pageable。前端用 Ant Design Table 客户端分页（默认 50 条/页）。 |
| 4 | 券商下拉源 = `/api/brokers`（券商管理） | 用户明确："券商管理中输入了几个，其他各地应该就有多少选项"。 |
| 5 | 跨券商分发：`brokerCode === 'ibkr'` → IBKR 面板；其他 → Empty 占位 | Tiger/Futu/Schwab 当前没有 staging 表，展示"暂未实现"。 |
| 6 | Trade Confirms Tab 加 audit-only 提示 | 避免用户误以为 confirms 表参与导入。 |

## 架构

### 后端

**新增 Controller**：`com.vortex.controller.IbkrStagedDataController`

| Method | Path | 参数 | 返回 |
|--------|------|------|------|
| GET | `/api/sync/ibkr/orders` | `batchId`（可选） | `List<IbkrStagedOrder>` |
| GET | `/api/sync/ibkr/trade-confirms` | `batchId`（可选） | `List<IbkrStagedTradeConfirm>` |

- 响应统一包裹为 `{status, message, data}` 信封，与项目其他 controller 一致
- 排序：`tradeDate DESC, id DESC`（最近交易在前）
- 复用现有 Repository（`findByBatchId(Long)` / `findAll(Sort)`），不新增 Repository 方法

**未改动**：Entity / Repository / Service，纯新增 Controller。

### 前端

**新增 API 层**：`frontend/src/services/syncStagedDataApi.js`
- `fetchIbkrStagedOrders({ batchId })`
- `fetchIbkrStagedTradeConfirms({ batchId })`

**新增页面组件**：
```
pages/sync/
├── SyncManagement.jsx                    # 改造为 Tabs 容器
├── batches/
│   └── SyncBatchesTab.jsx                # 原 SyncManagement 主体迁移至此
└── staged/
    ├── StagedDataTab.jsx                 # 券商选择 + 分发
    └── ibkr/
        ├── IbkrStagedPanel.jsx           # Orders / Confirms 内嵌 Tabs
        ├── IbkrStagedOrdersTable.jsx     # 全字段表格（37 列）
        └── IbkrStagedConfirmsTable.jsx   # 全字段表格（41 列）
```

**表格设计**：
- 列顺序与实体字段声明顺序一致
- 前两列（`tradeDate`、`symbol`）`fixed: 'left'`，便于横向滚动时定位行
- 数值列（quantity/price/amount 等）右对齐
- `Description`、`Error Message` 用 `ellipsis + Tooltip` 避免超长文本破坏列宽
- `scroll={{ x: 'max-content' }}`：列宽之和决定横向滚动条
- 分页：默认 50 条/页，支持 20/50/100/200 切换
- 顶部工具栏：批次 ID 输入框（可选）+ 查询 / 重置 / 刷新按钮

## 同步批次 Tab 的附带修正

- `SyncBatchesTab.jsx` 中的**过滤券商下拉**从硬编码 4 个选项改为 `fetchAllBrokers()` 动态加载
- **新建同步 Modal 内**的券商下拉保持 `fetchSupportedBrokers()`（仍需从同步适配器取，因为要确保所选券商真的有适配器实现）
- 页面顶部的 `<Title>` 上移到外层 `SyncManagement.jsx`，避免两个 Tab 都重复渲染标题

## 验证

- 后端：`mvn test` → 150/150 通过，BUILD SUCCESS
- 前端：`npm run build` → 成功（3884 modules transformed）
- 前端 Lint：0 错误

## 未实现（后续若需）

- ❌ 编辑 / 删除 staging 数据（原始数据按设计就是只读）
- ❌ Tiger / Futu / Schwab 的原始数据面板（这些券商还没 staging 表）
- ❌ 从批次列表跳转到原始数据并自动预填 `batchId`（P1，当前走手动输入）
- ❌ CSV / Excel 导出
- ❌ 列显示 / 隐藏开关（若 UX 反馈再加）

## 相关文件

**新增**：
- `backend/src/main/java/com/vortex/controller/IbkrStagedDataController.java`
- `frontend/src/services/syncStagedDataApi.js`
- `frontend/src/pages/sync/batches/SyncBatchesTab.jsx`
- `frontend/src/pages/sync/staged/StagedDataTab.jsx`
- `frontend/src/pages/sync/staged/ibkr/IbkrStagedPanel.jsx`
- `frontend/src/pages/sync/staged/ibkr/IbkrStagedOrdersTable.jsx`
- `frontend/src/pages/sync/staged/ibkr/IbkrStagedConfirmsTable.jsx`

**修改**：
- `frontend/src/pages/sync/SyncManagement.jsx`（改造为 Tabs 容器；过滤券商下拉改为 `fetchAllBrokers`）
