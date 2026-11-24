# 前端架构

## 入口与导航

[index.html](../frontend/index.html) 加载 [main.jsx](../frontend/src/main.jsx)，由 React `StrictMode` 挂载 [App](../frontend/src/App.jsx)。[AppLayout](../frontend/src/components/AppLayout.jsx) 提供 Ant Design 主布局、导航、后端状态和全局上下文。

页面使用 URL hash 导航。[menuConfig.js](../frontend/src/constants/menuConfig.js) 定义菜单、路径映射和父菜单关系。`AppLayout` 监听 `hashchange` 并渲染相应页面。`#/trade-detail/<id>` 由布局单独解析，归属交易记录菜单。页面通过 [PageHeaderContext](../frontend/src/contexts/PageHeaderContext.jsx) 提供面包屑，布局渲染面包屑，页面可在内容区使用 [PageHeaderTitle](../frontend/src/components/PageHeader.jsx)。

账户管理菜单以机构图标标识「券商管理」，以账户卡片图标标识「托管账户」。 [账户菜单](../design/0050-2026-11-24-account-display-conventions.md#账户菜单)

## 页面与数据入口

展示托管账户的表格统一将账户名称和账户 ID 放在第一、第二列，适用于交易记录、相关交易、持仓快照、出入金记录、托管账户、同步批次、同步配置和 IBKR 原始数据。 [表格列顺序](../design/0050-2026-11-24-account-display-conventions.md#表格列顺序)

| 功能 | 页面和实际能力 |
| --- | --- |
| 交易记录 | [`trade/`](../frontend/src/pages/trade/) 提供列表、统计、新增、详情和详情中的编辑、删除。新增／编辑提交本地账户主键，账户／策略／证券筛选栏自适应换行。主列表与详情的同账户相关交易表共用列定义，历史空 ID 显示「待补全」。券商名称在详情基本信息中展示。两张表使用中等行距和明确列宽，长账户名称、账户 ID 和证券代码可悬停查看全值，精确费用可换行。可用宽度低于列宽之和时在表格内横向滚动。 [0049 交易表格](../design/0049-2026-11-23-responsive-trade-records.md#交易表格) |
| 交易数据分析 | [`analysis/`](../frontend/src/pages/analysis/) 查询指定日期的持仓快照，并按需调用交易核对接口。持仓的计算口径见 [交易与持仓](trades-and-positions.md)。 |
| 市场事件 | [`market-events/`](../frontend/src/pages/market-events/) 通过三个标签页查询、新增并处理代码变更、拆股和实物分红，不提供撤销按钮；提示录入顺序及关联历史锁。 |
| 同步管理 | [`sync/`](../frontend/src/pages/sync/) 展示和筛选批次；同步配置列出全部托管账户，按账户配置IBKR或Tiger连接，保存或清空数据库明文凭证，Token／私钥重新打开为空并支持留空保留。账户新增及资料修改跳转账户管理；新建同步仅允许账户启用、真实账户 ID 已补齐且凭证完整的账户；提交本地 `brokerAccountId`，列表显示真实 `accountId` 并支持账户筛选。历史批次标签与v2筛选条件分别维护。原始数据查看仅覆盖IBKR订单与成交确认，均为只读。 [0047](../design/0047-2026-11-21-broker-accounts-and-sync-connections.md) |
| 出入金、券商、托管账户和策略 | [CashFlow](../frontend/src/pages/CashFlow.jsx) 按账户查询和新增出入金。[BrokerManagement](../frontend/src/pages/BrokerManagement.jsx) 管理券商名称、代码、国家／地区及独立简介，不提供状态开关；[BrokerAccountsManagement](../frontend/src/pages/BrokerAccountsManagement.jsx) 独立管理账户名称、真实账户 ID、所属券商、邮箱、手机号／电话、账户备注及状态，路径 `#/broker-accounts`。券商页面不保存或展示账户联系方式及备注；托管账户页不再提供“券商管理”按钮，券商入口保留在左侧菜单。同券商支持多账户，所属券商创建后固定，历史待补全账户可先改名或停用。[StrategyManagement](../frontend/src/pages/StrategyManagement.jsx) 支持策略查询、新增、编辑和删除。 |
| 仪表盘和盈亏 | [Dashboard](../frontend/src/pages/Dashboard.jsx) 显示零值统计和空列表。[个股](../frontend/src/pages/profit/StockProfitAnalysis.jsx)、[策略](../frontend/src/pages/profit/StrategyProfitAnalysis.jsx)和[账户](../frontend/src/pages/profit/AccountProfitAnalysis.jsx)盈亏页面显示占位提示。 |
| 技术选型 Demo | 盈亏分析下的 [`chart-demo/`](../frontend/src/pages/profit/chart-demo/) 使用固定合成数据展示金融图、分析图、三维曲面和期权链表格；不接入账本、行情或券商业务接口。 |

[AmountVisibilityContext](../frontend/src/contexts/AmountVisibilityContext.jsx) 控制接入该上下文的页面如何显示金额等数据。持仓快照和暂存数据表会继续显示各自的数值字段。[系统设置](../frontend/src/pages/Settings.jsx) 通过 [TradeEditableContext](../frontend/src/contexts/TradeEditableContext.jsx) 将开关保存在浏览器的 `localStorage`，交易详情据此显示普通交易的编辑和删除按钮，MARKET_EVENT 系统交易保持只读。交易列表的新增入口独立于此开关。两项开关均在浏览器中生效。

## 请求与验证

[services/](../frontend/src/services/) 按领域封装 Axios 请求，使用相对的 `/api` 路径并返回响应体。布局直接查询 `GET /api/health`。开发模式下，[Vite 配置](../frontend/vite.config.js) 默认监听 3000 端口，并把 `/api` 代理到 `http://localhost:8080`。发布时应让静态页面的同源服务处理 `/api`。前端部署脚本的 `-a` 只注入 `VITE_API_BASE_URL`，当前请求代码未读取该变量。

交易费用在列表、详情、统计及编辑中统一保留原始数字精度，不固定显示两位小数。页面使用后端的精确十进制字符串，兼容旧数字字段；统计仍在后端按币种汇总。新增和编辑使用 `InputNumber.stringMode`，以原字符串提交，展示与保存使用同一数值。费用列按精确十进制比较排序，极小费用不会因 JavaScript 数字下溢被隐藏；原有遮罩、缺省值和零费用提示保留。相关契约见 [0029](../design/0029-2026-11-17-preserve-trade-fee-precision.md)、[0030 展示修订](../design/0030-2026-11-17-display-exact-trade-fees.md) 与 [交易与持仓](trades-and-positions.md)。

[package.json](../frontend/package.json) 定义开发、构建和测试命令，`package-lock.json` 包含 Vitest 和图表依赖的锁定记录。Vitest 在 Node 环境中运行，[常量测试](../frontend/src/constants/__tests__/) 覆盖菜单映射和交易常量，[演示数据测试](../frontend/src/pages/profit/chart-demo/__tests__/mockData.test.js) 覆盖合成价格、报价、收益边界和曲面维度。运行和排查步骤见 [前端指南](../docs/frontend-guide.md)。

[交易统计面板](../frontend/src/pages/trade/TradeStatisticsPanel.jsx) 的圆环居中，半径随绘图区缩放。下方图例随宽度换行，显示各交易类型的次数及其占图中交易总数的比例。小扇区可通过图例或悬停提示读取数值。窄窗口下三张统计卡片按栅格断点换行。 [0049 统计卡片](../design/0049-2026-11-23-responsive-trade-records.md#统计卡片)

[费用 API 回归](../frontend/src/services/__tests__/tradeFeePrecision.test.js) 通过模拟 Axios 验证高精度费用读取、回填和提交；[展示及排序回归](../frontend/src/utils/__tests__/tradeFee.test.jsx) 在 Node 中使用真实列表列和统计组件的静态渲染，覆盖完整小数、极小费用、零／缺省值、遮罩、精确排序及旧响应兼容。原费用修订的单测、构建与合成接口浏览器验收记录见 [归档](../docs/TODO-ARCHIVE.md)，没有连接真实后端或用户账本；不在架构说明中维护固定测试数量。

市场事件页面不提供修改或撤销已处理事件的入口，三个列表均不显示撤销操作列。交易与事件写入的冲突提示继续显示后端提供的阻挡事件、日期和证券，相关接口返回 409。页面入口修订见 [0048](../design/0048-2026-11-22-trade-table-and-event-actions.md)，后端受限撤销 API 保持原有规则；早期撤销交互记录仅作为历史证据保留在 [归档](../docs/TODO-ARCHIVE.md)。 [操作指南](../docs/market-events.md) · [冲突格式化测试](../frontend/src/utils/__tests__/marketEventError.test.js)

## 图表技术选型演示

`#/profit-chart-demo` 由布局按需加载，四个标签页的图表代码也分别加载，切换后卸载旧面板并清理图表实例及大小监听。相关方案见 [0026](../design/0026-2026-11-15-frontend-chart-selection-demo.md)。

- Lightweight Charts 5.3.0：K 线、MA5/MA20、成交量窗格和合成买卖点；提供区间、均线和重置视图控制，保留 TradingView 署名及链接。
- Apache ECharts 6.1.0：净值/回撤、策略到期收益、Delta/Gamma 示意、波动率微笑和热力图；图表组件按需注册。
- Plotly.js 4.3.0：使用官方 `plotly.js-gl3d-dist-min` 分发包展示波动率和收益示意曲面；支持旋转、缩放、模式切换、偏斜参数和视角重置。浏览器缺少 WebGL 时显示提示，渲染异常提供错误提示和重试入口。
- Ant Design Table：Call/Put 双侧报价、到期日、41/121/401 档样本规模、近平值筛选、列排序、Greeks 列切换、定位平值、虚拟滚动和所选合约明细。

数据来自 [mockData.js](../frontend/src/pages/profit/chart-demo/mockData.js)，标的为 `DEMO-A`/`DEMO-B`，样本时点固定为 2026-10-30，货币为 USD。报价、Greeks 和收益曲面只用于渲染示意；到期收益使用演示假设（乘数 100、不计费用），不构成已实现的正式定价或盈亏分析能力。布局仍执行已有的 `/api/health` 检查。

ECharts 与 Plotly 的构建分块较大，当前通过页面和标签页的动态加载控制首次访问影响；本演示验证功能和交互，未开展大规模行情性能基准测试。

同步批次表在同一列显示开始与完成时间，并以「错误 → 查看」打开完整错误信息。侧栏展开时以 1280 CSS 像素视口可看到整张表为布局目标。可用宽度不足时筛选可换行，表格内可横向滚动。 [同步管理页面优化](../design/0051-2026-11-24-sync-management-layout.md)

同步管理使用顶部标签页切换批次与配置。批次工具栏提供新建同步和刷新，配置卡片顶部提供刷新。新建同步缺少就绪连接时，可从弹窗进入同步配置。 [布局与操作](../design/0051-2026-11-24-sync-management-layout.md#布局与操作)

原始数据的 Orders 和 Confirms 表按 `batchId` 从 IBKR 批次列表补充同步账户信息，首列展示关联账户的当前名称，第二列展示批次的账户 ID 快照。报表自己的 ID 和别名分别显示为 `IBKR Account ID` 和 `IBKR Acct Alias`。宽窗口固定账户、日期和证券列，窄窗口可横向滚动。字段缺失显示 `-`。 [IBKR 原始数据](../design/0050-2026-11-24-account-display-conventions.md#ibkr-原始数据)

相关决策见 [相关交易展示](../design/0004-2026-03-12-related-trades-display.md) 和 [同步原始数据查看](../design/0015-2026-04-21-sync-staged-data-viewer.md)。
