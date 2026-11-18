# 前端架构

## 入口与导航

[index.html](../frontend/index.html) 加载 [main.jsx](../frontend/src/main.jsx)，由 React `StrictMode` 挂载 [App](../frontend/src/App.jsx)。[AppLayout](../frontend/src/components/AppLayout.jsx) 提供 Ant Design 主布局、导航、后端状态和全局上下文。

页面使用 URL hash 导航。[menuConfig.js](../frontend/src/constants/menuConfig.js) 定义菜单、路径映射和父菜单关系。`AppLayout` 监听 `hashchange` 并渲染相应页面。`#/trade-detail/<id>` 由布局单独解析，归属交易记录菜单。页面通过 [PageHeaderContext](../frontend/src/contexts/PageHeaderContext.jsx) 提供面包屑，布局渲染面包屑，页面可在内容区使用 [PageHeaderTitle](../frontend/src/components/PageHeader.jsx)。

## 页面与数据入口

| 功能 | 页面和实际能力 |
| --- | --- |
| 交易记录 | [`trade/`](../frontend/src/pages/trade/) 提供列表、统计、新增、详情和详情中的编辑、删除。详情在前端根据期权合约和关联 ID 整理相关交易，复用列表列定义。 |
| 交易数据分析 | [`analysis/`](../frontend/src/pages/analysis/) 查询指定日期的持仓快照，并按需调用交易核对接口。持仓的计算口径见 [交易与持仓](trades-and-positions.md)。 |
| 市场事件 | [`market-events/`](../frontend/src/pages/market-events/) 通过三个标签页查询、新增并处理、受限撤销代码变更、拆股和实物分红；提示录入顺序及关联历史锁。 |
| 同步管理 | [`sync/`](../frontend/src/pages/sync/) 展示同步批次，支持创建同步任务和筛选批次。历史批次标签与 v2 筛选条件分别维护。原始数据查看面板仅覆盖 IBKR，订单与成交确认均为只读展示。 [券商同步](broker-sync/README.md) |
| 出入金、券商和策略 | [CashFlow](../frontend/src/pages/CashFlow.jsx) 查询和新增出入金记录。[BrokerManagement](../frontend/src/pages/BrokerManagement.jsx) 查询、新增和更新券商。[StrategyManagement](../frontend/src/pages/StrategyManagement.jsx) 支持策略的查询、新增、编辑和删除。 |
| 仪表盘和盈亏 | [Dashboard](../frontend/src/pages/Dashboard.jsx) 显示零值统计和空列表。[个股](../frontend/src/pages/profit/StockProfitAnalysis.jsx)、[策略](../frontend/src/pages/profit/StrategyProfitAnalysis.jsx)和[账户](../frontend/src/pages/profit/AccountProfitAnalysis.jsx)盈亏页面显示占位提示。 |
| 技术选型 Demo | 盈亏分析下的 [`chart-demo/`](../frontend/src/pages/profit/chart-demo/) 使用固定合成数据展示金融图、分析图、三维曲面和期权链表格；不接入账本、行情或券商业务接口。 |

[AmountVisibilityContext](../frontend/src/contexts/AmountVisibilityContext.jsx) 控制接入该上下文的页面如何显示金额等数据。持仓快照和暂存数据表会继续显示各自的数值字段。[系统设置](../frontend/src/pages/Settings.jsx) 通过 [TradeEditableContext](../frontend/src/contexts/TradeEditableContext.jsx) 将开关保存在浏览器的 `localStorage`，交易详情据此显示普通交易的编辑和删除按钮，MARKET_EVENT 系统交易保持只读。交易列表的新增入口独立于此开关。两项开关均在浏览器中生效。

## 请求与验证

[services/](../frontend/src/services/) 按领域封装 Axios 请求，使用相对的 `/api` 路径并返回响应体。布局直接查询 `GET /api/health`。开发模式下，[Vite 配置](../frontend/vite.config.js) 默认监听 3000 端口，并把 `/api` 代理到 `http://localhost:8080`。发布时应让静态页面的同源服务处理 `/api`。前端部署脚本的 `-a` 只注入 `VITE_API_BASE_URL`，当前请求代码未读取该变量。

交易费用在列表、详情、统计及编辑中统一保留原始数字精度，不固定显示两位小数。页面使用后端的精确十进制字符串，兼容旧数字字段；统计仍在后端按币种汇总。新增和编辑使用 `InputNumber.stringMode`，以原字符串提交，展示与保存使用同一数值。费用列按精确十进制比较排序，极小费用不会因 JavaScript 数字下溢被隐藏；原有遮罩、缺省值和零费用提示保留。相关契约见 [0029](../design/0029-2026-11-17-preserve-trade-fee-precision.md)、[0030 展示修订](../design/0030-2026-11-17-display-exact-trade-fees.md) 与 [交易与持仓](trades-and-positions.md)。

[package.json](../frontend/package.json) 定义开发、构建和测试命令，`package-lock.json` 包含 Vitest 和图表依赖的锁定记录。Vitest 在 Node 环境中运行，[常量测试](../frontend/src/constants/__tests__/) 覆盖菜单映射和交易常量，[演示数据测试](../frontend/src/pages/profit/chart-demo/__tests__/mockData.test.js) 覆盖合成价格、报价、收益边界和曲面维度。运行和排查步骤见 [前端指南](../docs/frontend-guide.md)。

[费用 API 回归](../frontend/src/services/__tests__/tradeFeePrecision.test.js) 通过模拟 Axios 验证高精度费用读取、回填和提交；[展示及排序回归](../frontend/src/utils/__tests__/tradeFee.test.jsx) 在 Node 中使用真实列表列和统计组件的静态渲染，覆盖完整小数、极小费用、零／缺省值、遮罩、精确排序及旧响应兼容。原费用修订的单测、构建与合成接口浏览器验收记录见 [归档](../docs/TODO-ARCHIVE.md)，没有连接真实后端或用户账本；不在架构说明中维护固定测试数量。

市场事件页面通过共享撤销组件确认操作。失败保留弹窗和原因，成功刷新列表；交易与事件写入的冲突提示显示后端提供的阻挡事件、日期和证券，相关接口返回 409。三类撤销交互的既有合成接口点验见 [归档](../docs/TODO-ARCHIVE.md)，未操作真实账本。 [操作指南](../docs/market-events.md) · [冲突格式化测试](../frontend/src/utils/__tests__/marketEventError.test.js)

## 图表技术选型演示

`#/profit-chart-demo` 由布局按需加载，四个标签页的图表代码也分别加载，切换后卸载旧面板并清理图表实例及大小监听。相关方案见 [0026](../design/0026-2026-11-15-frontend-chart-selection-demo.md)。

- Lightweight Charts 5.3.0：K 线、MA5/MA20、成交量窗格和合成买卖点；提供区间、均线和重置视图控制，保留 TradingView 署名及链接。
- Apache ECharts 6.1.0：净值/回撤、策略到期收益、Delta/Gamma 示意、波动率微笑和热力图；图表组件按需注册。
- Plotly.js 4.3.0：使用官方 `plotly.js-gl3d-dist-min` 分发包展示波动率和收益示意曲面；支持旋转、缩放、模式切换、偏斜参数和视角重置。浏览器缺少 WebGL 时显示提示，渲染异常提供错误提示和重试入口。
- Ant Design Table：Call/Put 双侧报价、到期日、41/121/401 档样本规模、近平值筛选、列排序、Greeks 列切换、定位平值、虚拟滚动和所选合约明细。

数据来自 [mockData.js](../frontend/src/pages/profit/chart-demo/mockData.js)，标的为 `DEMO-A`/`DEMO-B`，样本时点固定为 2026-10-30，货币为 USD。报价、Greeks 和收益曲面只用于渲染示意；到期收益使用演示假设（乘数 100、不计费用），不构成已实现的正式定价或盈亏分析能力。布局仍执行已有的 `/api/health` 检查。

ECharts 与 Plotly 的构建分块较大，当前通过页面和标签页的动态加载控制首次访问影响；本演示验证功能和交互，未开展大规模行情性能基准测试。

相关决策见 [相关交易展示](../design/0004-2026-03-12-related-trades-display.md) 和 [同步原始数据查看](../design/0015-2026-04-21-sync-staged-data-viewer.md)。
