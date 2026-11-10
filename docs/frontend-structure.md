# 前端工程结构

应用入口、菜单路由、页面分层和 API 封装见 [前端架构](../knowledge/frontend-architecture.md)。开发、调试和构建命令见 [前端指南](frontend-guide.md)。

## 添加页面

1. 在 `frontend/src/pages/` 下的对应功能目录创建页面组件。
2. 在 [menuConfig.js](../frontend/src/constants/menuConfig.js) 中添加菜单和路径映射。页面属于子菜单时，同步更新父菜单映射。
3. 在 [AppLayout.jsx](../frontend/src/components/AppLayout.jsx) 中导入页面，并在 `renderContent()` 中添加对应分支。
4. 页面需要请求后端时，复用 `frontend/src/services/` 中对应领域的 API 封装。
