# 前端架构

## 入口与导航

[index.html](../frontend/index.html) 加载 [main.jsx](../frontend/src/main.jsx)，由 React `StrictMode` 挂载 [App](../frontend/src/App.jsx)。[AppLayout](../frontend/src/components/AppLayout.jsx) 提供 Ant Design 主布局、导航、后端状态和全局上下文。

页面使用 URL hash 导航。[menuConfig.js](../frontend/src/constants/menuConfig.js) 定义菜单、路径映射和父菜单关系。`AppLayout` 监听 `hashchange` 并渲染相应页面。`#/trade-detail/<id>` 由布局单独解析，归属交易记录菜单。页面通过 [PageHeaderContext](../frontend/src/contexts/PageHeaderContext.jsx) 提供面包屑，布局渲染面包屑，页面可在内容区使用 [PageHeaderTitle](../frontend/src/components/PageHeader.jsx)。

## 页面与数据入口

| 功能 | 页面和实际能力 |
| --- | --- |
| 交易记录 | [`trade/`](../frontend/src/pages/trade/) 提供列表、统计、新增、详情和详情中的编辑、删除。详情在前端根据期权合约和关联 ID 整理相关交易，复用列表列定义。 |
| 交易数据分析 | [`analysis/`](../frontend/src/pages/analysis/) 查询指定日期的持仓快照，并按需调用交易核对接口。持仓的计算口径见 [交易与持仓](trades-and-positions.md)。 |
| 市场事件 | [`market-events/`](../frontend/src/pages/market-events/) 通过三个标签页查询和新增代码变更、拆股和实物分红。 |
| 同步管理 | [`sync/`](../frontend/src/pages/sync/) 展示同步批次，支持创建同步任务和筛选批次。历史批次标签与 v2 筛选条件分别维护。原始数据查看面板仅覆盖 IBKR，订单与成交确认均为只读展示。 [券商同步](broker-sync/README.md) |
| 出入金、券商和策略 | [CashFlow](../frontend/src/pages/CashFlow.jsx) 查询和新增出入金记录。[BrokerManagement](../frontend/src/pages/BrokerManagement.jsx) 查询、新增和更新券商。[StrategyManagement](../frontend/src/pages/StrategyManagement.jsx) 支持策略的查询、新增、编辑和删除。 |
| 仪表盘和盈亏 | [Dashboard](../frontend/src/pages/Dashboard.jsx) 显示零值统计和空列表。[个股](../frontend/src/pages/profit/StockProfitAnalysis.jsx)、[策略](../frontend/src/pages/profit/StrategyProfitAnalysis.jsx)和[账户](../frontend/src/pages/profit/AccountProfitAnalysis.jsx)盈亏页面显示占位提示。 |

[AmountVisibilityContext](../frontend/src/contexts/AmountVisibilityContext.jsx) 控制接入该上下文的页面如何显示金额等数据。持仓快照和暂存数据表会继续显示各自的数值字段。[系统设置](../frontend/src/pages/Settings.jsx) 通过 [TradeEditableContext](../frontend/src/contexts/TradeEditableContext.jsx) 将开关保存在浏览器的 `localStorage`，交易详情据此显示编辑和删除按钮。交易列表的新增入口独立于此开关。两项开关均在浏览器中生效。

## 请求与验证

[services/](../frontend/src/services/) 按领域封装 Axios 请求，使用相对的 `/api` 路径并返回响应体。布局直接查询 `GET /api/health`。开发模式下，[Vite 配置](../frontend/vite.config.js) 默认监听 3000 端口，并把 `/api` 代理到 `http://localhost:8080`。发布时应让静态页面的同源服务处理 `/api`。前端部署脚本的 `-a` 只注入 `VITE_API_BASE_URL`，当前请求代码未读取该变量。

[package.json](../frontend/package.json) 定义开发、构建和测试命令。Vitest 在 Node 环境中运行，[现有测试](../frontend/src/constants/__tests__/) 覆盖菜单映射和交易常量。当前 `package-lock.json` 缺少 `package.json` 声明的 Vitest，干净安装前需要核对锁文件。运行和排查步骤见 [前端指南](../docs/frontend-guide.md)。

相关决策见 [相关交易展示](../design/0004-2026-03-12-related-trades-display.md) 和 [同步原始数据查看](../design/0015-2026-04-21-sync-staged-data-viewer.md)。
