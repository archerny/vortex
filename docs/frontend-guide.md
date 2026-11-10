# Vortex Frontend

The frontend provides views for trading records, cash flows, positions, market events, and broker synchronization. [Frontend architecture](../knowledge/frontend-architecture.md)

## Technology Stack

- **React 18** - Frontend framework
- **Ant Design 5** - UI component library
- **Vite** - Build tool
- **Axios** - HTTP client
- **Day.js** - Date manipulation library

## Getting Started

### Prerequisites

Select the execution environment and check its tools as described in [AGENTS.md](../AGENTS.md#1-环境隔离).

- **Node.js 18.x or 20 and newer**, as required by the locked Vite version
  ```bash
  # Check Node.js version
  node -v
  ```

- **npm**, which the repository scripts use with `package-lock.json`
  ```bash
  # Check npm version
  npm -v
  ```

### Installation

With project dependencies already installed, use the commands below from the worktree root. To set up dependencies for the first time, check that `frontend/package.json` and `frontend/package-lock.json` agree before running `npm --prefix frontend ci`.

The checked-in lockfile currently omits Vitest, which the manifest declares and `npm test` invokes. This can block a clean installation and the deployment script's `npm ci` step. Review the lockfile separately if dependency changes are in scope.

### Running in Development Mode

```bash
npm --prefix frontend run dev
```

The application will start at `http://localhost:3000`

### Building for Production

```bash
npm --prefix frontend run build
```

Vite writes static assets to `frontend/dist/`. The deployment script copies and archives them in `deploy/output/`.

Serve the static files alongside a same-origin `/api` endpoint or reverse proxy. The deployment script's `-a` option sets `VITE_API_BASE_URL`, which current frontend requests do not read.

### Preview Production Build

```bash
npm --prefix frontend run preview
```

## Debugging Guide

### Development Tools

#### 1. Browser DevTools

**Chrome/Edge DevTools:**
- Press `F12` or `Ctrl+Shift+I` (Windows/Linux) / `Cmd+Option+I` (Mac)
- **Console Tab**: View logs, errors, and warnings
- **Network Tab**: Monitor API requests and responses
- **React DevTools**: Install the React Developer Tools extension for component inspection

**Useful Console Commands:**
```javascript
// Check if backend is accessible
fetch('/api/health').then(r => r.json()).then(console.log)
```

#### 2. Vite Hot Module Replacement (HMR)

Vite provides instant feedback during development:
- Changes to `.jsx` files trigger automatic component updates
- CSS changes apply immediately without page reload
- Check terminal for compilation errors

#### 3. Network Debugging

**API Proxy Configuration** (in `vite.config.js`):
```javascript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true
  }
}
```

Check the health endpoint through the frontend proxy.

```bash
curl http://localhost:3000/api/health
```

#### 4. Common Debugging Scenarios

**Backend Connection Issues:**
1. Verify backend is running: `curl http://localhost:8080/api/health`
2. Check proxy configuration in `vite.config.js`
3. Review browser Network tab for failed requests

**Component Not Updating:**
1. Check browser console for errors
2. Verify component state management
3. Use React DevTools to inspect component props and state

**Build errors**

Check that the installed dependencies match the manifest. If Vite's dependency cache is stale, restart the development server with `npm --prefix frontend run dev -- --force`.

### IDE Configuration

**VS Code Recommended Extensions:**
- ESLint
- Prettier
- ES7+ React/Redux/React-Native snippets
- Auto Rename Tag

**VS Code Debug Configuration** (`.vscode/launch.json`):
```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "chrome",
      "request": "launch",
      "name": "Launch Chrome",
      "url": "http://localhost:3000",
      "webRoot": "${workspaceFolder}/frontend/src"
    }
  ]
}
```

## Project Structure

The app starts at `frontend/src/main.jsx`. Layout, navigation, pages, request wrappers, and the current feature boundaries are documented in [Frontend architecture](../knowledge/frontend-architecture.md). See [Adding a page](frontend-structure.md#添加页面) for the navigation wiring.

## API Endpoints

The layout checks backend availability with `GET /api/health`. Domain APIs are wrapped in [frontend/src/services/](../frontend/src/services/). The Vite development proxy forwards requests under `/api` to the backend.

## Development Guidelines

1. Ensure backend service is running at `http://localhost:8080`
2. Frontend dev server automatically proxies `/api` requests to backend
3. Use Ant Design components for UI development
4. Follow React Hooks best practices
5. Keep components small and focused
6. Use meaningful variable and function names

## Troubleshooting

### Port Already in Use

If port 3000 is occupied, pass a port for the current session.

```bash
npm --prefix frontend run dev -- --port 3001
```

### Dependency Installation Issues

Check the npm registry, network access, and lockfile in the selected environment. Changes to user-wide npm configuration or caches require approval under the repository's environment rules.

### Hot Reload Not Working

1. Check if file is saved properly
2. Restart dev server: `Ctrl+C` then `npm run dev`
3. Clear browser cache and reload

## 单元测试

> 📋 完整的单元测试规范请参阅 [单元测试规范](unit-test-spec.md)。核心原则：**单元测试不得操作数据库中的数据**。

### 测试框架

- **Vitest** - 基于 Vite 的测试框架，开箱即用，无需额外编译配置

### 配置

测试配置已集成在 `vite.config.js` 中：

```javascript
export default defineConfig({
  // ...
  test: {
    globals: true,
    environment: 'node',
  },
})
```

### 运行测试

```bash
# 在工作树根目录运行所有测试
npm --prefix frontend test

# 已安装项目依赖时，使用项目内的 Vitest 进入监听模式
cd frontend
./node_modules/.bin/vitest
```

### 测试思路

前端采用**常量/配置层优先**的测试策略，专注于数据映射的完整性和一致性验证。这些常量是整个前端 UI 展示的基础，一旦映射缺失或错误会导致页面显示异常。

测试文件统一放置在对应模块的 `__tests__` 目录下。

### 测试清单

#### 1. tradeConstants.test.js（交易常量映射）

文件路径：`src/constants/__tests__/tradeConstants.test.js`

| 测试模块 | 测试场景 |
|---|---|
| 证券类型映射 | `assetTypeMap` 包含全部 4 种类型（STOCK/ETF/OPTION_CALL/OPTION_PUT）、正反向映射一致性、颜色映射覆盖所有类型 |
| 交易类型映射 | `tradeTypeMap` 只有 BUY/SELL、正反向映射一致性、颜色映射覆盖、金额颜色映射覆盖 |
| 币种配置 | `currencyOptions` 包含 3 种币种（CNY/HKD/USD）、每项有 label+value、颜色映射覆盖 |
| 交易触发来源映射 | `tradeTriggerMap` 包含 3 种触发类型（MANUAL/OPTION/MARKET_EVENT）、颜色映射覆盖 |
| 触发关联类型映射 | `triggerRefTypeMap` 包含全部 7 种关联类型、颜色映射覆盖、期权子类型和市场事件子类型分组验证 |

**测试设计要点：**
- 验证**双向映射一致性**：`assetTypeMap` 与 `assetTypeReverseMap` 互为反转，确保前端表单提交和展示能正确互换
- 验证**颜色映射完整性**：每个枚举值都有对应的颜色配置，防止 Tag 组件渲染时出现无颜色的情况
- 验证**关联类型分组**：期权子类型（EXPIRE/EXERCISE/ASSIGNED）和市场事件子类型（STOCK_SPLIT/SYMBOL_CHANGE/DIVIDEND_IN_KIND）各成一组，与后端校验逻辑对应

#### 2. menuConfig.test.js（菜单配置）

文件路径：`src/constants/__tests__/menuConfig.test.js`

| 测试模块 | 测试场景 |
|---|---|
| 路由映射 | `menuKeyToPath` 包含测试列举的页面路由、`pathToMenuKey` 双向映射一致性、条目数量一致 |
| 父菜单映射 | 子菜单 key 都存在于 `menuKeyToPath` 中、账户管理/交易管理/盈亏分析子菜单正确映射到父组 |
| 菜单项结构 | `menuItems` 非空数组、顶级项有 key/icon/label、含子菜单项的 children 非空且结构正确、包含仪表盘/交易管理/盈亏分析/账户管理/系统设置五大模块 |

**测试设计要点：**
- 验证**路由映射不丢失**：防止新增页面后忘记添加映射，导致路由跳转或菜单高亮失效
- 验证**父子菜单关系**：确保侧边栏展开/收起状态和面包屑导航的正确性
- 验证**菜单结构完整性**：确保 Ant Design Menu 组件能正确渲染所有菜单项

## Integration with Backend

### Full Stack Development Workflow

Start both processes from the worktree root in the selected execution environment. The backend uses the configured isolated database. [Backend guide](backend-guide.md) · [Database setup](database-setup.md)

**Terminal 1 — Backend**

```bash
mvn -f backend/pom.xml spring-boot:run -Dspring-boot.run.profiles=dev
```

**Terminal 2 — Frontend**

```bash
npm --prefix frontend run dev
```

**Verify Integration:**
1. Backend: `http://localhost:8080/api/health`
2. Frontend: `http://localhost:3000`
3. Frontend API call: `http://localhost:3000/api/health` (proxied to backend)
