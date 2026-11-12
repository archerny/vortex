# 待办事项

以下条目来自静态代码审阅和合成场景推演，修复后的实际运行结果待验证。P1 涉及账本结果或同步导入，P2 涉及测试环境的可复现性。本轮日志脱敏的方案与验收见 [0024 IBKR 请求日志 Token 脱敏](../design/0024-2026-11-12-ibkr-token-log-redaction.md)。

- [ ] **P1 统一 Tiger 暂存与导入的时间格式**

  暂存阶段将成交时间写成带 `T` 的日期时间文本，导入映射却只接受毫秒时间戳数字。经正常暂存流程写入的已成交订单会被判为无法识别，进而触发批次失败及清理。 [暂存时间转换](../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerStagingWorker.java) · [导入时间解析](../backend/src/main/java/com/vortex/sync/adapter/tiger/TigerTradeRecordMapper.java)

  验收需用合成订单贯通暂存和导入，确认订单成功生成交易，交易日期符合确定的时区口径。覆盖跨日时间、缺失或非法时间，并验证历史暂存记录能按已定义的兼容规则处理。

- [ ] **P1 补齐历史交易改动和跨标的事件的重算范围**

  普通交易新增、修改或软删除后，后续市场事件的派生交易仍保留旧结果。事件重算也只查询初始标的集合，未继续追踪代码变更或分红引出的新标的。例如已有 100 股、后续两倍拆股，再补录拆股前的 100 股买入，当前可能得到 300 股。另一个场景是 A 变更为 B 后 B 再拆股，补录 A 的早期拆股只重算到 A→B，B 的拆股结果仍旧。 [普通交易写入](../backend/src/main/java/com/vortex/service/TradeRecordService.java) · [事件重算](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java) · [代码变更事件查询](../backend/src/main/java/com/vortex/repository/SymbolChangeEventRepository.java)

  验收需覆盖补录、修改和软删除历史交易，以及 A→B→后续拆股或分红的依赖链。第一种合成场景的最终持仓应为 400 股。修改标的、券商或日期时，新旧受影响范围均须与从原始记录完整重算的结果一致，重复重算应得到相同结果，处理失败时须保持事务一致性。

- [ ] **P1 消除清仓后的平均成本残差**

  卖出时先将平均成本舍入为 6 位小数，再减记成本池，完全清仓时没有归零。合成场景中先买 10,000 股、单价 1，再买 20,000 股、单价 2，全部卖出后成本池会剩下 −0.01。重新买入 1 股、单价 10，后续代码变更会按 9.9900 转移成本。 [成本池计算](../backend/src/main/java/com/vortex/service/MarketEventProcessingService.java)

  验收需明确成本金额和单价的精度及舍入时点。上述场景重建持仓后，每股成本应恢复为 10.0000，代码变更前后的总成本应一致。还需覆盖多次部分卖出、全部卖出和重新建仓，确认历史残差不会累积到新持仓。

- [ ] **P1 明确 IBKR 期权关联回填的匹配范围与歧义处理**

  股票侧期权交易的候选查询未限定券商，多个候选经过行权价、数量匹配后仍可能直接取首条。同一天、同一标的出现不同券商或多个合约的行权、指派记录时，关联可能指向错误的期权交易，候选返回顺序也会影响结果。 [候选查询](../backend/src/main/java/com/vortex/repository/TradeRecordRepository.java) · [关联回填与消歧](../backend/src/main/java/com/vortex/sync/adapter/ibkr/IbkrImportWorker.java)

  验收需用合成的跨券商、不同到期日、相同行权价和相同数量记录覆盖唯一匹配、无匹配与多候选。成功回填须能证明关联属于正确的券商和期权合约。交换候选顺序或重试时结果应一致，无法唯一确定的关联应按明确的设计暴露并处理。

- [ ] **P2 同步前端 Vitest 声明与依赖锁文件**

  `package.json` 已声明 Vitest 和测试命令，`package-lock.json` 却缺少对应的开发依赖和锁定记录，干净检出的测试依赖无法由现有锁文件完整重现。 [依赖与命令](../frontend/package.json) · [依赖锁文件](../frontend/package-lock.json) · [测试配置](../frontend/vite.config.js)

  验收需在所选环境的干净检出中完成按锁安装，并成功运行前端单测。安装过程应保持清单与锁文件一致，重复安装应得到相同的依赖版本。

- [ ] **P2 用可提交的合成数据替换解析测试对个人夹具的依赖**

  `FlexQueryParserTest` 从 classpath 读取一个被 Git 忽略的个人 XML 文件，并断言该资源存在。干净检出缺少资源会导致测试失败，开发者还需要自行提供账户样本。 [解析测试](../backend/src/test/java/com/vortex/sync/adapter/ibkr/FlexQueryParserTest.java) · [个人夹具忽略规则](../backend/.gitignore)

  验收需使用可提交的合成 XML 或测试内构造的数据，覆盖当前解析断言和必要的异常输入。干净检出应能离线运行解析测试，并且夹具、断言及测试输出均使用合成数据。
