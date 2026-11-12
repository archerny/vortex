# IBKR 请求日志 Token 脱敏

## 背景与目标

`IbkrFlexClient` 在 SendRequest 和 GetStatement 阶段各输出一次完整 URL，其中包含 Flex Token。基础配置启用了该包的 DEBUG 日志。目标是消除这两条泄漏路径，保留必要的请求诊断信息。

## 范围与设计

- 删除两个阶段中重复输出完整 URL 的日志。沿用 `executeHttpGet` 已有的 `maskToken`，统一输出脱敏后的请求 URL。
- 构造请求时，非法 URI 的原始异常可能包含 Token。对此类请求配置异常只提供固定的诊断消息，避免将原始 URL 通过异常链交给外层日志。
- 使用合成 Token 和模拟 HTTP 响应，从公开的 `fetchReport` 入口覆盖两个阶段。捕获 DEBUG 日志，验证日志中 Token 已隐藏，实际发出的请求仍携带原 Token。
- 将同轮审查发现的其他六项缺陷记录到 [TODO](../docs/TODO.md)，每项列出触发场景、影响、代码入口和验收条件。

## 实现步骤

1. 编写日志回归用例，确认它能发现现有的两阶段泄漏。
2. 删除重复的完整 URL 日志，运行客户端与适配器的相关单测。
3. 更新 IBKR 当前知识及 TODO 文档入口。

## 验证与验收

- 在 `ubuntu-dev` 中运行 `IbkrFlexClientTest` 和 `IbkrSyncAdapterTest`，使用模拟响应与合成数据。
- DEBUG 日志保留 SendRequest、GetStatement 的操作信息和脱敏 URL，合成 Token 不出现在日志中。
- 两次 HTTP 请求包含原 Token，报告结果与模拟响应一致。
- 对非法 URL 配置，异常消息和完整异常链均不包含合成 Token，且未调用 HTTP 客户端。
- 检查文档链接和 `git diff --check`。

## 需要更新的当前知识

在 [IBKR 同步](../knowledge/broker-sync/brokers/ibkr.md) 中说明请求 URL 日志的脱敏入口和回归测试。相关历史设计见 [IBKR Flex Web Service](0009-2026-04-14-ibkr-flex-web-service.md)。
