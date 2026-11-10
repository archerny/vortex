# 设计方案索引

本目录保存设计决策、实施计划和待实现草案。项目现行行为见 [当前知识索引](../knowledge/README.md)。券商配置和排障见 [券商同步操作文档](../docs/broker-sync/README.md)。

历史方案按可核实的原始设计日期编号。来源列给出 Git 快照和原路径，可用 `git show <提交>:<原路径>` 核对原件。归档调整 Markdown 内部链接目标，并用合成数据替换了 0009 中的一段 XML 样例。

| 编号 | 原始日期 | 方案 | 状态与后续决策 | 来源提交和原路径 |
| --- | --- | --- | --- | --- |
| 0001 | 2026-03-08 | [交易触发来源](0001-2026-03-08-trade-trigger.md) | 已落地 | `a6623dd` · `docs/trade-trigger-design.md` |
| 0002 | 2026-03-08 | [市场事件处理与重算](0002-2026-03-08-market-event-processing.md) | 已落地，按现行实现核对边界 | `a6623dd` · `docs/market-event-processing-design.md` |
| 0003 | 2026-03-09 | [TradeType 精简](0003-2026-03-09-trade-type-refactor.md) | 方案二已落地 | `a6623dd` · `docs/trade-type-refactor-discussion.md` |
| 0004 | 2026-03-12 | [相关交易展示](0004-2026-03-12-related-trades-display.md) | 已落地 | `a6623dd` · `docs/related-trades-display-design.md` |
| 0005 | 2026-03-14 | [实物分红 Tab 重构](0005-2026-03-14-dividend-in-kind-tab-refactor.md) | 已落地，名称字段决策由 0014 替代 | `a6623dd` · `docs/dividend-in-kind-tab-refactor.md` |
| 0006 | 2026-03-14 | [Tiger Open API 接入](0006-2026-03-14-tiger-open-api.md) | 已落地，后续见 0016、0017 | `a6623dd` · `docs/broker-sync/brokers/tiger/open-api.md` |
| 0007 | 2026-03-14 | [券商同步总体方案](0007-2026-03-14-broker-sync-overall.md) | 历史分期与方向 | `fd638ac` · `docs/broker-sync/overall-design.md` |
| 0008 | 2026-04-13 | [同步数据持久化](0008-2026-04-13-broker-sync-data-persistence.md) | 已落地，含多阶段历史决策 | `a6623dd` · `docs/broker-sync/framework/data-persistence.md` |
| 0009 | 2026-04-14 | [IBKR Flex Web Service 接入](0009-2026-04-14-ibkr-flex-web-service.md) | 已落地，归档中的 XML 响应使用合成样例 | `a6623dd` · `docs/broker-sync/brokers/ibkr/flex-web-service.md` |
| 0010 | 2026-04-16 | [导入一致性 v1](0010-2026-04-16-broker-import-consistency-v1.md) | 已由 0018 替代 | `53550b5` · `docs/broker-sync/framework/import-consistency.md` |
| 0011 | 2026-04-17 | [Broker Code 与适配器注册发现](0011-2026-04-17-broker-registration.md) | 已落地 | `a6623dd` · `docs/broker-sync/framework/broker-registration.md` |
| 0012 | 2026-04-18 | [IBKR BookTrade 触发来源映射](0012-2026-04-18-ibkr-booktrade-mapping.md) | 已落地，失败语义见 0019 | `a6623dd` · `docs/broker-sync/brokers/ibkr/booktrade-mapping.md` |
| 0013 | 2026-04-21 | [IBKR 暂存设计](0013-2026-04-21-ibkr-staging-schema.md) | 已落地，现行契约另见当前知识 | `a6623dd` · `docs/broker-sync/brokers/ibkr/staging-schema.md` |
| 0014 | 2026-04-21 | [删除交易与事件的证券名称字段](0014-2026-04-21-drop-symbol-name-fields.md) | 已落地，独立证券主表为后续计划 | `a6623dd` · `docs/drop-symbol-name-fields.md` |
| 0015 | 2026-04-21 | [同步原始数据查看](0015-2026-04-21-sync-staged-data-viewer.md) | 已落地，查看面板仅覆盖 IBKR | `a6623dd` · `docs/sync-staged-data-viewer.md` |
| 0016 | 2026-04-21 | [Tiger 暂存设计](0016-2026-04-21-tiger-staging-schema.md) | 已落地，现行契约另见当前知识 | `a6623dd` · `docs/broker-sync/brokers/tiger/staging-schema.md` |
| 0017 | 2026-04-21 | [Tiger Phase 3 实施计划](0017-2026-04-21-tiger-phase3-plan.md) | 两阶段导入已落地，增量议题保留 | `a6623dd` · `docs/broker-sync/brokers/tiger/phase3-plan.md` |
| 0018 | 2026-04-22 | [导入一致性 v2](0018-2026-04-22-broker-import-consistency-v2.md) | 取代 0010，来源快照含后续修订 | `a6623dd` · `docs/broker-sync/framework/import-consistency.md` |
| 0019 | 2026-04-23 | [导入数据丢失链修复](0019-2026-04-23-broker-import-data-loss-fix.md) | 已落地，修订 0018 的失败路径 | `a6623dd` · `docs/broker-sync/fix-p0-data-loss-chain.md` |
| 0020 | 2026-04-23 | [富途同步草案](0020-2026-04-23-futu-sync.md) | 待实现 | `a6623dd` · `docs/broker-sync/brokers/futu/README.md` |
| 0021 | 2026-04-24 | [长桥同步草案](0021-2026-04-24-longbridge-sync.md) | 待实现 | `a6623dd` · `docs/broker-sync/brokers/longbridge/README.md` |
| 0022 | 2026-11-11 | [历史文档迁移与当前知识初始化](0022-2026-11-11-documentation-migration.md) | 本次文档整理 | 本次新增 |
