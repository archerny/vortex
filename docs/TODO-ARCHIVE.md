# 待办归档

这里保留已完成、已取消或已合并事项的背景、处理历史和验证证据。当前需要继续处理的事项见 [待办事项](TODO.md)，维护规则见 [项目研发约定](../AGENTS.md#todo-维护与完成)。

本次归档日期：2026-11-16。进展记录保留当时的状态，以条目最后的完成或处置记录为准；取消、合并不代表已修复并通过验收。

## 已完成

- [x] **P2 同步前端 Vitest 声明与依赖锁文件**

  原问题：`package.json` 已声明 Vitest 和测试命令，`package-lock.json` 却缺少对应的开发依赖和锁定记录，干净检出的测试依赖无法由现有锁文件完整重现。 [依赖与命令](../frontend/package.json) · [依赖锁文件](../frontend/package-lock.json) · [测试配置](../frontend/vite.config.js)

  验收需在所选环境的干净检出中完成按锁安装，并成功运行前端单测。安装过程应保持清单与锁文件一致，重复安装应得到相同的依赖版本。

  2026-11-15 进展：在 [0026 图表演示](../design/0026-2026-11-15-frontend-chart-selection-demo.md) 中补齐 Vitest 1.6.1 及传递依赖的锁定记录；既有锁定包未升级。`ubuntu-dev`（Node 26.13.0、npm 11.20.0）中，当前工作树执行 `npm --prefix frontend test`，31 项通过，退出码 0；`npm --prefix frontend run build` 通过，退出码 0。

  本次另以 Git 文件清单复制当前前端源码至被忽略的 `backend/target/chart-demo-validation/clean/frontend/`，不包含已有依赖和构建缓存；在该副本连续执行两次 `npm --prefix <副本路径> ci --registry=https://registry.npmjs.org --no-audit --no-fund`，退出码均为 0，清单与锁文件字节不变，随后测试 31 项及构建均通过。副本已清理。由于本次变更尚未提交，原验收要求的独立干净 Git 检出复验仍待执行，本条保持未勾选；下一步在对应提交的干净检出中运行 `npm ci` 和单测后完成验收。

  2026-11-16 完成：修复已随提交 `e6d5945539690f552f0fc140c6b46388352200d5`（`feat(frontend): add interactive chart selection demo`）交付。本轮在 `ubuntu-dev`（Linux arm64、Node 26.13.0、npm 11.20.0）将该提交独立克隆至工作树内临时目录 `target/vitest-lock-validation/checkout/`，确认初始 Git 状态干净且不存在 `frontend/node_modules/`，再从此检出根目录验证：

  - 连续两次执行 `npm --prefix frontend ci --registry=https://registry.npmjs.org --no-audit --no-fund`，退出码均为 0；每次均按锁重新安装 449 个包。两次 `npm --prefix frontend ls --all --json` 均退出 0，规范化完整依赖树完全一致，SHA-256 为 `b9cefbce74a10bd9de4a98523dfd9dd1f246792dc4ab91d79f8e9513b79b55f2`。Vitest 实际版本为 1.6.1。
  - `npm --prefix frontend test` 退出 0，3 个测试文件、31 项测试全部通过；`npm --prefix frontend run build` 退出 0。
  - `package.json` 和 `package-lock.json` 在安装、测试及构建前后字节不变，最终 Git 状态仍干净；当前工作树的这两个文件也与该提交一致。npm 使用已有缓存，临时目录和产物位于本次验证目录内；验证完成后整个临时检出及其依赖、产物和日志已清理。

  安装与构建仍有既有依赖弃用、esbuild 安装脚本策略提示、Vite CJS API 弃用和较大构建分块提示，未导致上述命令失败。本轮仅补齐原方案 [0026](../design/0026-2026-11-15-frontend-chart-selection-demo.md) 的提交后验收并关闭本条，未修改或升级依赖；当前 knowledge 与前端指南已经描述正确的锁定安装方式，无需更新。

## 已取消或合并

- [ ] **已取消（当前接受）：IBKR 正常解析与同步日志中的账户标识脱敏（原 P2）**

  Parser 与 Adapter 在 INFO 日志直接打印 accountId，真实同步会将账户标识写入日志。`ubuntu-dev` 的合成日志捕获已确认账户字符串原样出现；本轮未将真实 XML 交给该日志链路。 [解析日志](../backend/src/main/java/com/vortex/sync/adapter/ibkr/FlexQueryParser.java#L170) · [同步日志](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrSyncAdapter.java#L281) · [审阅 R6](audits/2026-11-16-ibkr-sync-review.md#r6--p2正常解析会把真实账户-id-写入-info-日志)

  2026-11-16 处置：用户明确接受当前阶段在应用日志中打印账户 ID，认为现阶段无需对此脱敏。已在 AGENTS.md 的数据安全约定中明确此例外，取消本项修复及原账户脱敏验收要求，保留发现和复现记录。本项不再属于活动待办，也不标记为已修复完成。密码、Token、密钥和交易明细仍遵循原约定；未来若调整账户日志要求，再重新评估并制定方案。
