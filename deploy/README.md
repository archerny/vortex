# 构建与部署脚本

从当前工作树根目录执行。先按 [环境约定](../AGENTS.md#1-环境隔离) 核对已有 Java／Maven、Node／npm、`tar` 和 `zip`，以及缓存与产物写入范围。两个脚本只构建和打包，不会启动应用或部署到服务器。

## 后端

```bash
./deploy/build-backend.sh
./deploy/build-backend.sh -s
```

[脚本](build-backend.sh) 执行 `mvn clean package`，默认含单测，`-s` 添加 `-DskipTests`。输出：

- `backend/target/vortex-backend-1.0.0.jar`
- `deploy/output/backend/` 下的 JAR 与 `start.sh`
- `deploy/output/vortex-backend-<时间戳>.tar.gz` 和 `.zip`

`-p/--profile` 当前仅解析为脚本变量，没有传给 Maven，也没有写入生成的启动脚本。因此指定 `-p prod` 不会产生 profile 专属构建。生成脚本默认 `local`，实际 profile 由运行参数决定：

```bash
./deploy/output/backend/start.sh dev
```

启动会连接数据库并可能应用 Flyway。`dev` 必须有明确的隔离库配置；基础 URL 默认指向 `vortex`。JAR 会包含资源目录中存在的本地配置文件，分发前确认没有凭据文件，在运行时注入 datasource 和券商配置。 [配置指南](../docs/local-config-guide.md)

## 前端

```bash
./deploy/build-frontend.sh
./deploy/build-frontend.sh -s
```

[脚本](build-frontend.sh) 默认执行 `npm ci` 再构建；`-s/--skip-install` 仅在 `node_modules/` 已存在时跳过安装，不存在时仍运行 `npm ci`。输出为 `frontend/dist/`、`deploy/output/frontend/` 及带时间戳的 tar.gz／zip。

`-a/--api-url` 只为构建设置 `VITE_API_BASE_URL`，当前前端请求代码未读取它。发布时配置静态站点的同源 `/api` 路由或反向代理到后端；Vite 开发代理不会进入构建产物。preview 仅供本地检查，详见 [前端指南](../docs/frontend-guide.md)。

## 清理与验证

两个脚本的 `-c` 只清理对应构建目录；后端还执行 `mvn clean`，前端删除 `dist/`。脚本不会删除之前生成的时间戳压缩包。运行前确认这些目录仅存放可丢弃的构建产物。

构建成功不等于完成运行验收。必要的内存测试、迁移／事务和页面交互分别按 [单测规范](../docs/unit-test-spec.md) 和 [e2e 指南](../e2e/README.md) 执行；跳过项必须在交付中说明。
