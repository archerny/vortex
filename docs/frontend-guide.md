# Vortex 前端开发指南

前端使用 React 18、Ant Design 5、Vite 5、Axios 和 Day.js。页面入口、hash 导航、上下文及当前功能边界见 [前端架构](../knowledge/frontend-architecture.md)。

## 环境与依赖

先按 [环境隔离约定](../AGENTS.md#1-环境隔离) 确认执行环境与当前工作树。使用已有 Node 和 npm；Vite 5 的 Node 范围是 18.x 或 20 及以上。

```bash
node -v
npm -v
npm --prefix frontend ci
```

所有命令从工作树根目录执行。安装以 `package-lock.json` 为准；修改依赖时同步清单和锁文件，核对无关升级，并在干净依赖目录完成安装、测试和构建验收。已有依赖且未变化时无需重复安装。

## 启动与接口

```bash
npm --prefix frontend run dev
```

默认地址为 `http://localhost:3000`。[vite.config.js](../frontend/vite.config.js) 把 `/api` 代理到 `http://localhost:8080`，布局通过 `/api/health` 检查后端。业务请求复用 [services/](../frontend/src/services/) 的 Axios 封装。

需要联调时，先按 [后端指南](backend-guide.md) 启动使用隔离开发库的后端，然后从前端代理核对：

```bash
curl http://localhost:3000/api/health
```

端口被占用时可为当前会话指定 `--port 3001`；Vite 也可能自动选择后续端口，以终端输出为准。后端端口变化时需同步修改开发代理。

## 测试与构建

```bash
npm --prefix frontend test
npm --prefix frontend run build
npm --prefix frontend run preview
```

`test` 执行 `vitest run`，Node 环境下覆盖菜单／常量、合成图表数据、费用 API、真实组件的静态展示与排序、市场事件错误格式。网络调用使用模拟实现。监听模式可使用 `npm --prefix frontend test -- --watch`，不需下载全局工具。测试规范见 [单测规范](unit-test-spec.md)。

构建产物为 `frontend/dist/`；preview 仅用于本地检查静态构建，不自动提供后端业务接口。生产需配置同源 `/api` 或反向代理。部署脚本的 `-a` 仅设置 `VITE_API_BASE_URL`，当前 API 请求未读取该变量，不能用它切换生产后端地址。 [部署指南](../deploy/README.md)

`package.json` 另声明了 `lint`，但仓库当前未提供 ESLint 配置文件；不要把该脚本当作已经可用并通过的验收项。

## 添加页面

1. 在 `frontend/src/pages/` 的对应功能目录创建页面组件。
2. 在 [menuConfig.js](../frontend/src/constants/menuConfig.js) 中添加菜单和路径映射；属于子菜单时同步更新父菜单映射。
3. 在 [AppLayout.jsx](../frontend/src/components/AppLayout.jsx) 中导入页面，并在 `renderContent()` 中添加分支。
4. 需要请求后端时，复用 [services/](../frontend/src/services/) 中对应领域的 API 封装。

## 页面边界与交互验收

交易费用使用后端的 `feeExact` 和分币种精确统计字符串，展示及输入不固定小数位；表单使用 `InputNumber.stringMode`，避免保存时经过 JavaScript 浮点数。金额隐藏和编辑开关是浏览器设置，其中编辑开关只控制普通交易详情的按钮。 [交易契约](../knowledge/trades-and-positions.md)

交易表格应在卡片内横向滚动，窄屏筛选栏换行，末列详情仍可访问。市场事件应点验三个标签页均无撤销按钮，并核对新增弹窗、取消及冲突提示，操作范围见 [操作指南](market-events.md)。仅有内存单测和构建结果不能证明浏览器交互正确。使用合成 API 的本地验收工具见 [e2e 指南](../e2e/README.md#ordered-market-event-check)。

仪表盘和个股／账户／策略盈亏仍是占位页。技术选型 Demo 位于 `#/profit-chart-demo`，四个标签页展示 Lightweight Charts、ECharts、Plotly 和 Ant Design 期权链。数据固定为合成样本，不调用账本或行情接口；布局的健康检查仍会运行。Plotly 曲面需要 WebGL，页面与面板按需加载，构建可能有大分块提示。 [演示内容与限制](../knowledge/frontend-architecture.md#图表技术选型演示)

## 调试

浏览器 Console 用于查看渲染异常，Network 用于核对 `/api` 请求、响应和 409 错误原因。连通性问题先核对后端健康入口、Vite 代理和实际端口；profile 与数据库见 [配置指南](local-config-guide.md)。

HMR 或依赖缓存异常时，先保存文件并重启开发进程；需要重建 Vite 缓存可运行 `npm --prefix frontend run dev -- --force`。发布页面使用 hash 导航，交易详情由 `#/trade-detail/<id>` 单独解析，添加路由须同步菜单和布局分支。
