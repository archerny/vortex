import com.vortex.entity.*;
import com.vortex.entity.enums.*;
import com.vortex.entity.enums.Currency;
import com.vortex.repository.*;
import com.vortex.service.*;
import com.vortex.sync.adapter.ibkr.*;
import com.vortex.sync.adapter.tiger.TigerImportWorker;
import com.vortex.sync.core.*;
import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

/** Real repositories and transaction proxies; no application profiles, live broker access or real data. */
public class OrderedMarketEventCheck {
    static final String USER = "vortex_ordered_check";
    static final LocalDate DAY = LocalDate.of(2026, 3, 10);
    static String url;
    static AnnotationConfigApplicationContext context;
    static TradeRecordService trades;
    static StockSplitEventService splits;
    static SymbolChangeEventService changes;
    static DividendInKindEventService dividends;
    static TradeRecordRepository tradeRepo;
    static StockSplitEventRepository splitRepo;
    static TransactionTemplate tx;
    static long broker;

    @Configuration
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackages = "com.vortex.repository")
    @Import({BrokerAccountService.class, LedgerWriteLock.class, MarketEventHistoryService.class, TradeRecordService.class,
            PositionService.class, MarketEventProcessingService.class, StockSplitEventService.class,
            SymbolChangeEventService.class, DividendInKindEventService.class, BrokerSyncBatchService.class,
            IbkrImportWorker.class, IbkrImportService.class, IbkrStagingWorker.class, IbkrStagingService.class,
            TigerImportWorker.class, IbkrCleanupStrategy.class, SyncBatchCleanupService.class, SyncBatchFailureHandler.class})
    static class Config {
        @Bean DataSource dataSource() { return new DriverManagerDataSource(url, USER, ""); }
        @Bean LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource source) {
            var factory = new LocalContainerEntityManagerFactoryBean();
            factory.setDataSource(source); factory.setPackagesToScan("com.vortex.entity");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto", "none", "hibernate.show_sql", "false"));
            return factory;
        }
        @Bean PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
            return new JpaTransactionManager(factory);
        }
    }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        String directory = Path.of(args[1]).toRealPath().toString();
        check(directory.startsWith("/tmp/vortex-ordered-events-") && directory.endsWith("/data"), "Unsafe cluster path");
        url = "jdbc:postgresql://127.0.0.1:" + port + "/postgres";
        try (var connection = DriverManager.getConnection(url, USER, "");
             var statement = connection.createStatement(); var result = statement.executeQuery("SHOW data_directory")) {
            result.next(); check(directory.equals(result.getString(1)), "Unexpected database target");
        }
        Flyway.configure().dataSource(url, USER, "")
                .locations("filesystem:" + Path.of("backend/src/main/resources/db/migration").toAbsolutePath()).load().migrate();
        try (var app = new AnnotationConfigApplicationContext(Config.class)) {
            context = app;
            trades = app.getBean(TradeRecordService.class); splits = app.getBean(StockSplitEventService.class);
            changes = app.getBean(SymbolChangeEventService.class); dividends = app.getBean(DividendInKindEventService.class);
            tradeRepo = app.getBean(TradeRecordRepository.class); splitRepo = app.getBean(StockSplitEventRepository.class);
            tx = new TransactionTemplate(app.getBean(PlatformTransactionManager.class));
            broker = managedBroker(new Broker("SYNTH-EVENT", "SYNTH")).getId();
            lifecycle(); chainAndDividend(); zeroAndLegacy(); multipleBrokers(); transactions(); concurrency(); syncBoundary(); exactOptionLinkage(); preflight();
            System.out.println("PASS all ordered-event integration scenarios (real Spring/JPA, isolated PostgreSQL)");
        }
    }

    static Broker managedBroker(Broker broker) {
        Broker saved = context.getBean(BrokerRepository.class).save(broker);
        BrokerAccount account = new BrokerAccount(); account.setBrokerId(saved.getId()); account.setAccountId("SYNTHETIC"); account.setAccountName("Synthetic account");
        context.getBean(BrokerAccountService.class).create(account);
        return saved;
    }
    static long accountKey(long owner) { return context.getBean(BrokerAccountService.class).resolve(null, owner).getId(); }

    static void lifecycle() {
        TradeRecord original = trades.create(trade("SYNTH-LIFE", DAY.minusDays(1)));
        StockSplitEvent event = splits.create(split("SYNTH-LIFE", DAY));
        check(quantity("SYNTH-LIFE") == 200, "Split holdings");
        conflict("HISTORY_LOCKED", () -> trades.create(trade("SYNTH-LIFE", DAY.minusDays(1))));
        conflict("HISTORY_LOCKED", () -> trades.softDelete(original.getId()));
        conflict("HISTORY_LOCKED", () -> trades.update(original.getId(), trade("SYNTH-X", DAY.plusDays(1))));
        TradeRecord unrelated = trades.create(trade("SYNTH-X", DAY.minusDays(1)));
        conflict("HISTORY_LOCKED", () -> trades.update(unrelated.getId(), trade("SYNTH-LIFE", DAY.minusDays(1))));
        check(tradeRepo.findById(unrelated.getId()).orElseThrow().getSymbol().equals("SYNTH-X"), "Rejected update mutated row");
        TradeRecord system = tradeRepo.findAll().stream().filter(t -> t.getTradeTrigger() == TradeTrigger.MARKET_EVENT).findFirst().orElseThrow();
        conflict("SYSTEM_TRADE_READ_ONLY", () -> trades.softDelete(system.getId()));
        conflict("SYSTEM_TRADE_READ_ONLY", () -> trades.update(system.getId(), trade("SYNTH-X", DAY)));
        conflict("PROCESSED_EVENT_IMMUTABLE", () -> splits.update(event.getId(), split("SYNTH-X", DAY)));
        TradeRecord following = trades.create(trade("SYNTH-LIFE", DAY));
        conflict("FOLLOWING_TRADES_EXIST", () -> splits.delete(event.getId()));
        trades.softDelete(following.getId()); splits.delete(event.getId()); splits.delete(event.getId());
        check(quantity("SYNTH-LIFE") == 100, "Undo did not remove only event output");
        trades.create(trade("SYNTH-LIFE", DAY.minusDays(2)));
        StockSplitEvent replacement = splits.create(split("SYNTH-LIFE", DAY));
        check(quantity("SYNTH-LIFE") == 400, "Recreate did not use corrected prior holdings");
        context.getBean(MarketEventProcessingService.class).processStockSplitEvent(replacement);
        check(quantity("SYNTH-LIFE") == 400, "Repeat processing duplicated output");
        System.out.println("PASS per-security history, old/new updates, protected system rows, day boundary, undo/recreate and idempotency");
    }

    static void chainAndDividend() {
        trades.create(trade("SYNTH-CHAIN-A", DAY.minusDays(2)));
        SymbolChangeEvent first = changes.create(rename("SYNTH-CHAIN-A", "SYNTH-CHAIN-B", DAY));
        conflict("EVENT_ORDER_CONFLICT", () -> splits.create(split("SYNTH-CHAIN-B", DAY)));
        conflict("HISTORY_LOCKED", () -> trades.create(trade("SYNTH-CHAIN-B", DAY.minusDays(1))));
        SymbolChangeEvent next = changes.create(rename("SYNTH-CHAIN-B", "SYNTH-CHAIN-C", DAY.plusDays(1)));
        check(quantity("SYNTH-CHAIN-C") == 100 && quantity("SYNTH-CHAIN-A") == 0, "Chronological rename chain");
        conflict("EVENT_ORDER_CONFLICT", () -> changes.delete(first.getId()));
        changes.delete(next.getId()); changes.delete(first.getId());
        check(quantity("SYNTH-CHAIN-A") == 100, "Reverse rename undo");
        trades.create(trade("SYNTH-DIV-A", DAY.minusDays(1)));
        DividendInKindEvent dividend = new DividendInKindEvent();
        dividend.setSymbol("SYNTH-DIV-A"); dividend.setDividendSymbol("SYNTH-DIV-B");
        dividend.setDividendCurrency(Currency.HKD); dividend.setRatioFrom(10); dividend.setRatioTo(1);
        dividend.setEventDate(DAY); dividend.setFairValuePerShare(new BigDecimal("2.25"));
        dividend = dividends.create(dividend);
        long dividendId = dividend.getId();
        check(quantity("SYNTH-DIV-B") == 10, "Dividend output");
        conflict("HISTORY_LOCKED", () -> trades.create(trade("SYNTH-DIV-A", DAY.minusDays(1))));
        conflict("HISTORY_LOCKED", () -> trades.create(trade("SYNTH-DIV-B", DAY.minusDays(1))));
        StockSplitEvent target = splits.create(split("SYNTH-DIV-B", DAY.plusDays(1)));
        conflict("EVENT_ORDER_CONFLICT", () -> dividends.delete(dividendId));
        splits.delete(target.getId());
        dividends.delete(dividendId); check(quantity("SYNTH-DIV-B") == 0, "Dividend undo");
        System.out.println("PASS rename chain and target security locks, same-day overlap rejection, dividend output/undo");
    }

    static void zeroAndLegacy() {
        TradeRecord buy = trades.create(trade("SYNTH-ZERO", DAY.minusDays(3)));
        TradeRecord sell = trade("SYNTH-ZERO", DAY.minusDays(2)); sell.setTradeType(TradeType.SELL); trades.create(sell);
        StockSplitEvent first = splits.create(split("SYNTH-ZERO", DAY));
        check(first.getProcessed() && quantity("SYNTH-ZERO") == 0, "Zero output state");
        conflict("HISTORY_LOCKED", () -> trades.softDelete(buy.getId()));
        StockSplitEvent second = splits.create(split("SYNTH-ZERO", DAY.plusDays(1)));
        conflict("EVENT_ORDER_CONFLICT", () -> splits.delete(first.getId()));
        splits.delete(second.getId()); conflict("HISTORY_LOCKED", () -> trades.softDelete(buy.getId()));
        splits.delete(first.getId());
        StockSplitEvent legacy = splitRepo.save(split("SYNTH-LEGACY", DAY));
        TradeRecord residual = trade("SYNTH-LEGACY", DAY); residual.setTradeTrigger(TradeTrigger.MARKET_EVENT);
        residual.setTriggerRefType(TriggerRefType.STOCK_SPLIT); residual.setTriggerRefId(legacy.getId());
        residual.setAmount(new BigDecimal("1000")); tradeRepo.save(residual);
        conflict("LEGACY_EVENT_STATE", () -> splits.delete(legacy.getId()));
        conflict("LEGACY_EVENT_STATE", () -> splits.update(legacy.getId(), split("SYNTH-OTHER", DAY)));
        check(tradeRepo.findById(residual.getId()).isPresent(), "Rejected legacy undo deleted rows");
        System.out.println("PASS zero-output constraints stack, reverse release, and anomalous legacy state remains untouched");
    }

    static void transactions() {
        trades.create(trade("SYNTH-ROLLBACK", DAY.minusDays(1)));
        long events = splitRepo.count(), rows = tradeRepo.count();
        try {
            tx.execute(status -> { splits.create(split("SYNTH-ROLLBACK", DAY)); throw new DeliberateRollback(); });
            throw new AssertionError("Expected rollback");
        } catch (DeliberateRollback expected) { }
        check(splitRepo.count() == events && tradeRepo.count() == rows, "Partial event persisted after rollback");
        trades.create(trade("SYNTH-ROLLBACK", DAY.minusDays(2)));
        StockSplitEvent retried = splits.create(split("SYNTH-ROLLBACK", DAY));
        check(retried.getProcessed(), "Retry after rollback");
        long generatedRows = tradeRepo.count();
        try {
            tx.execute(status -> { splits.delete(retried.getId()); throw new DeliberateRollback(); });
            throw new AssertionError("Expected undo rollback");
        } catch (DeliberateRollback expected) { }
        check(splitRepo.findById(retried.getId()).orElseThrow().getProcessed() && tradeRepo.count() == generatedRows,
                "Undo rollback left partial state");
        trades.create(trade("SYNTH-DB-FAILURE", DAY.minusDays(1)));
        DividendInKindEvent tooLarge = new DividendInKindEvent();
        tooLarge.setSymbol("SYNTH-DB-FAILURE"); tooLarge.setDividendSymbol("SYNTH-DB-FAILURE-DIV");
        tooLarge.setEventDate(DAY); tooLarge.setDividendCurrency(Currency.USD);
        tooLarge.setRatioFrom(1); tooLarge.setRatioTo(100000); tooLarge.setFairValuePerShare(new BigDecimal("10000000000"));
        long dividendCount = context.getBean(DividendInKindEventRepository.class).count(), beforeRows = tradeRepo.count();
        try { dividends.create(tooLarge); throw new AssertionError("Expected derived amount constraint failure"); }
        catch (org.springframework.dao.DataIntegrityViolationException expected) { }
        check(context.getBean(DividendInKindEventRepository.class).count() == dividendCount && tradeRepo.count() == beforeRows,
                "Derived insert failure left partial event state");
        trades.create(trade("SYNTH-DB-FAILURE", DAY.minusDays(2)));
        var strict = new TransactionTemplate(context.getBean(PlatformTransactionManager.class));
        strict.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        try {
            strict.execute(status -> trades.create(trade("SYNTH-ISOLATION", DAY)));
            throw new AssertionError("Expected unsupported isolation rejection");
        } catch (IllegalStateException expected) { check(expected.getMessage().contains("READ COMMITTED"), "Wrong isolation failure"); }
        System.out.println("PASS outer rollback and real derived-insert failure atomicity, lock release/retry and isolation refusal");
    }

    static void multipleBrokers() {
        long second = managedBroker(new Broker("SYNTH-SECOND", "SYNTH")).getId();
        trades.create(trade("SYNTH-MULTI", DAY.minusDays(1)));
        TradeRecord other = trade("SYNTH-MULTI", DAY.minusDays(1)); other.setBrokerId(second); other.setBrokerAccountId(accountKey(second)); trades.create(other);
        StockSplitEvent event = splits.create(split("SYNTH-MULTI", DAY));
        check(quantity("SYNTH-MULTI") == 400, "Event did not include both brokers");
        TradeRecord blocked = trade("SYNTH-MULTI", DAY.minusDays(2)); blocked.setBrokerId(second); blocked.setBrokerAccountId(accountKey(second));
        conflict("HISTORY_LOCKED", () -> trades.create(blocked));
        TradeRecord following = trade("SYNTH-MULTI", DAY); following.setBrokerId(second); following.setBrokerAccountId(accountKey(second)); following = trades.create(following);
        conflict("FOLLOWING_TRADES_EXIST", () -> splits.delete(event.getId()));
        trades.softDelete(following.getId()); splits.delete(event.getId());
        check(quantity("SYNTH-MULTI") == 200, "Cross-broker undo");
        System.out.println("PASS actual two-broker generation, historical locks, later trade refusal and undo");
    }

    static void concurrency() throws Exception {
        trades.create(trade("SYNTH-RACE", DAY.minusDays(1)));
        try (var pool = Executors.newFixedThreadPool(2)) {
            CountDownLatch start = new CountDownLatch(1);
            Supplier<String> attempt = () -> { await(start); try { splits.create(split("SYNTH-RACE", DAY)); return "OK"; }
                catch (MarketEventConflictException error) { return error.getCode(); } };
            Future<String> one = pool.submit(attempt::get), two = pool.submit(attempt::get); start.countDown();
            var results = List.of(one.get(10, TimeUnit.SECONDS), two.get(10, TimeUnit.SECONDS));
            check(results.contains("OK") && results.contains("EVENT_ORDER_CONFLICT"), "Concurrent duplicate events: " + results);
            check(quantity("SYNTH-RACE") == 200, "Concurrent duplicate generation");

            trades.create(trade("SYNTH-WAIT", DAY.minusDays(1)));
            blockedWriter(pool, () -> splits.create(split("SYNTH-WAIT", DAY)),
                    () -> trades.create(trade("SYNTH-WAIT", DAY.minusDays(2))), "HISTORY_LOCKED");
            trades.create(trade("SYNTH-REVERSE", DAY.minusDays(1)));
            blockedWriter(pool, () -> trades.create(trade("SYNTH-REVERSE", DAY)),
                    () -> splits.create(split("SYNTH-REVERSE", DAY)), "FOLLOWING_TRADES_EXIST");
        }
        System.out.println("PASS concurrent duplicate events, event-first/trade-first waits and checks after commit");
    }

    static void blockedWriter(ExecutorService pool, Runnable first, Runnable second, String code) throws Exception {
        CountDownLatch held = new CountDownLatch(1), release = new CountDownLatch(1), entered = new CountDownLatch(1);
        Future<?> owner = pool.submit(() -> tx.execute(status -> { first.run(); held.countDown(); await(release); return null; }));
        check(held.await(10, TimeUnit.SECONDS), "Owner did not acquire transaction");
        Future<?> waiter = pool.submit(() -> { entered.countDown(); conflict(code, second); });
        try {
            check(entered.await(10, TimeUnit.SECONDS), "Waiter did not start");
            try { waiter.get(200, TimeUnit.MILLISECONDS); throw new AssertionError("Waiter completed before commit"); }
            catch (TimeoutException expected) { }
        } finally { release.countDown(); }
        owner.get(10, TimeUnit.SECONDS); waiter.get(10, TimeUnit.SECONDS);
    }

    static void syncBoundary() throws Exception {
        BrokerSyncBatchService batches = context.getBean(BrokerSyncBatchService.class);
        var batchRepo = context.getBean(BrokerSyncBatchRepository.class);
        trades.create(trade("SYNTH-SYNC", DAY.minusDays(1)));
        try (var pool = Executors.newFixedThreadPool(2)) {
            blockedWriter(pool, () -> batches.createBatch("ibkr", DAY.minusDays(2), DAY),
                    () -> splits.create(split("SYNTH-SYNC", DAY)), "SYNC_IN_PROGRESS");
        }
        BrokerSyncBatch active = batchRepo.findFirstByStatusInOrderByIdDesc(java.util.Set.of("PENDING")).orElseThrow();
        for (String state : List.of("PENDING", "PROCESSING", "CLEANUP_FAILED")) {
            active.setStatus(state); batchRepo.save(active);
            conflict("SYNC_IN_PROGRESS", () -> splits.create(split("SYNTH-SYNC", DAY)));
        }
        batches.markAsFailed(active.getId(), "synthetic settled");
        StockSplitEvent lock = splits.create(split("SYNTH-SYNC", DAY));
        BrokerSyncBatch importing = batches.createBatch("ibkr", DAY.minusDays(2), DAY);
        IbkrStagedOrder order = new IbkrStagedOrder();
        order.setAccountId("SYNTHETIC");
        order.setBatchId(importing.getId()); order.setOrderId("SYNTH-LOCKED-ORDER");
        order.setSymbol("SYNTH-SYNC"); order.setAssetCategory("STK"); order.setTradeDate("20260309");
        order.setCurrency("USD"); order.setBuySell("BUY"); order.setQuantity("10");
        order.setOrderType("LMT"); order.setOrderTime("20260309;100000");
        order.setPrice("10"); order.setAmount("100"); order.setCommission("0");
        order = context.getBean(IbkrStagedOrderRepository.class).save(order);
        final IbkrStagedOrder staged = order;
        try {
            context.getBean(IbkrImportWorker.class).importSingleOrder(importing.getId(), broker, accountKey(broker), staged);
            throw new AssertionError("Locked import allowed");
        } catch (RuntimeException expected) {
            check(expected.getClass().getSimpleName().equals("ImportOneFailedException"), "Wrong import wrapper");
            check(expected.getCause() instanceof MarketEventConflictException, "Import lost conflict cause");
            check(((MarketEventConflictException) expected.getCause()).getCode().equals("HISTORY_LOCKED"), "Wrong import conflict");
        }
        check(!tradeRepo.existsByExternalBrokerAndExternalId("ibkr", "SYNTH-LOCKED-ORDER"), "Locked import persisted");
        context.getBean(SyncBatchCleanupService.class).cleanupBatchData(importing.getId(), "ibkr");
        check(context.getBean(IbkrStagedOrderRepository.class).findById(staged.getId()).isEmpty(), "Staging cleanup failed");
        batches.markAsFailed(importing.getId(), "synthetic history locked");
        splits.delete(lock.getId());
        BrokerSyncBatch complete = batches.createBatch("ibkr", DAY, DAY);
        batches.markAsCompleted(complete.getId(), new SyncResult());
        try { context.getBean(SyncBatchCleanupService.class).cleanupBatchData(complete.getId(), "ibkr");
            throw new AssertionError("Completed cleanup allowed"); }
        catch (IllegalStateException expected) { check(expected.getMessage().contains("owning failed or active"), "Wrong completed rejection"); }
        BrokerSyncBatch legacyFailed = batches.createBatch("ibkr", DAY.minusDays(2), DAY);
        TradeRecord leftover = trade("SYNTH-FAILED", DAY.minusDays(1)); leftover.setSyncBatchId(legacyFailed.getId());
        leftover = trades.create(leftover);
        IbkrStagedOrder leftoverStage = new IbkrStagedOrder();
        leftoverStage.setAccountId("SYNTHETIC");
        leftoverStage.setBatchId(legacyFailed.getId()); leftoverStage.setOrderId("SYNTH-FAILED-ORDER");
        leftoverStage = context.getBean(IbkrStagedOrderRepository.class).save(leftoverStage);
        batches.markAsFailed(legacyFailed.getId(), "synthetic legacy footprint");
        StockSplitEvent dependent = splits.create(split("SYNTH-FAILED", DAY));
        conflict("HISTORY_LOCKED", () -> context.getBean(SyncBatchCleanupService.class).cleanupBatchData(legacyFailed.getId(), "ibkr"));
        check(tradeRepo.findById(leftover.getId()).isPresent()
                && context.getBean(IbkrStagedOrderRepository.class).findById(leftoverStage.getId()).isPresent()
                && quantity("SYNTH-FAILED") == 200, "Rejected cleanup changed locked footprint");
        splits.delete(dependent.getId());
        context.getBean(SyncBatchCleanupService.class).cleanupBatchData(legacyFailed.getId(), "ibkr");
        context.getBean(SyncBatchCleanupService.class).cleanupBatchData(legacyFailed.getId(), "ibkr");
        check(quantity("SYNTH-FAILED") == 0, "Unlocked failed cleanup retry");
        System.out.println("PASS sync creation/event race, active states, real locked import rollback, cleanup and completed-batch protection");
        System.out.println("PASS old FAILED footprint cleanup respects event history and remains retryable after undo");
    }

    static void exactOptionLinkage() throws Exception {
        var brokers = context.getBean(BrokerRepository.class);
        Broker ibkr = new Broker("SYNTH-IBKR", "SYNTH"); ibkr.setBrokerCode("ibkr"); ibkr = managedBroker(ibkr);
        var batches = context.getBean(BrokerSyncBatchService.class);
        String missing = linkXml("SYNTH-MISSING", "SYNTH-LINK", true, 0);
        BrokerSyncBatch first = runLinkSync(missing);
        assertLinkFailure(first, "exact_matches=0");
        assertLinkFailure(runLinkSync(missing), "exact_matches=0");
        String repaired = linkXml("SYNTH-MISSING", "SYNTH-LINK", true, 1);
        BrokerSyncBatch fixed = runLinkSync(repaired);
        check(fixed.getStatus().equals("COMPLETED"), "Repaired link retry failed");
        TradeRecord linked = tradeRepo.findByExternalBrokerAndExternalId("ibkr", "SYNTH-MISSING-STK").orElseThrow();
        TradeRecord option = tradeRepo.findByExternalBrokerAndExternalId("ibkr", "SYNTH-MISSING-OPT0").orElseThrow();
        check(linked.getTriggerRefId().equals(option.getId()), "Successful pair points at wrong option");
        check(runLinkSync(repaired).getStatus().equals("COMPLETED"), "Identical repeat failed");
        check(tradeRepo.findById(linked.getId()).orElseThrow().getTriggerRefId().equals(option.getId()), "Repeat changed old link");
        assertLinkFailure(runLinkSync(linkXml("SYNTH-AMBIG", "SYNTH-AMBIG", true, 2)), "exact_matches=2");

        BrokerSyncBatch history = runLinkSync(linkXml("SYNTH-HIST", "SYNTH-HIST", false, 1));
        check(history.getStatus().equals("COMPLETED"), "Historical option import");
        TradeRecord historical = tradeRepo.findByExternalBrokerAndExternalId("ibkr", "SYNTH-HIST-OPT0").orElseThrow();
        long otherBroker = managedBroker(new Broker("SYNTH-FOREIGN", "SYNTH")).getId();
        TradeRecord foreign = optionFixture("SYNTH-HIST", otherBroker); tradeRepo.save(foreign);
        TradeRecord otherCurrency = optionFixture("SYNTH-HIST", ibkr.getId());
        otherCurrency.setCurrency(Currency.HKD); tradeRepo.save(otherCurrency);
        // Existing formal records are trusted even when the historic staging source is unavailable.
        tx.executeWithoutResult(status -> context.getBean(IbkrStagedOrderRepository.class).deleteByBatchId(history.getId()));
        BrokerSyncBatch stockBatch = runLinkSync(linkXml("SYNTH-HIST-STOCK", "SYNTH-HIST", true, 0));
        check(stockBatch.getStatus().equals("COMPLETED"), "Completed historical counterpart was not matched");
        var actualCandidates = tradeRepo.findOptSideBookTradesForMatching(accountKey(ibkr.getId()), TradeTrigger.OPTION, TriggerRefType.OPTION_EXERCISE, List.of(AssetType.OPTION_CALL, AssetType.OPTION_PUT), "SYNTH-HIST", DAY, ibkr.getId(), Currency.USD);
        check(actualCandidates.size() == 1 && actualCandidates.get(0).getId().equals(historical.getId()),
                "Actual JPQL admitted foreign broker/currency");
        TradeRecord stock = tradeRepo.findByExternalBrokerAndExternalId("ibkr", "SYNTH-HIST-STOCK-STK").orElseThrow();
        check(stock.getTriggerRefId().equals(historical.getId()), "Cross-completed-batch linkage");
        assertLinkFailure(runLinkSync(linkXml("SYNTH-REUSE", "SYNTH-HIST", true, 0)), "already linked");
        check(tradeRepo.findById(stock.getId()).orElseThrow().getTriggerRefId().equals(historical.getId()), "Failed reuse changed old link");

        // Exercise a real UPDATE failure after the in-memory reference has changed.
        try (var connection = DriverManager.getConnection(url, USER, ""); var statement = connection.createStatement()) {
            statement.execute("CREATE FUNCTION reject_synth_link() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN "
                    + "IF NEW.symbol = 'SYNTH-ROLLBACK' AND NEW.trigger_ref_id <> 0 THEN "
                    + "RAISE EXCEPTION 'synthetic link write rejected'; END IF; RETURN NEW; END $$");
            statement.execute("CREATE TRIGGER reject_synth_link BEFORE UPDATE OF trigger_ref_id ON trade_records "
                    + "FOR EACH ROW EXECUTE FUNCTION reject_synth_link()");
        }
        String rollback = linkXml("SYNTH-ROLLBACK", "SYNTH-ROLLBACK", true, 1);
        assertLinkFailure(runLinkSync(rollback), "synthetic link write rejected");
        try (var connection = DriverManager.getConnection(url, USER, ""); var statement = connection.createStatement()) {
            statement.execute("DROP TRIGGER reject_synth_link ON trade_records"); statement.execute("DROP FUNCTION reject_synth_link()");
        }
        check(runLinkSync(rollback).getStatus().equals("COMPLETED"), "Rolled-back link retry did not recover");
        System.out.println("PASS exact IBKR links: actual JPQL broker/currency scope, trusted existing counterpart without staging source, unique/zero/ambiguous matches, "
                + "one-to-one use, duplicate/retry, real update rollback and entire-batch cleanup with old batches unchanged");
    }

    static BrokerSyncBatch runLinkSync(String xml) {
        IbkrFlexQueryProperties properties = new IbkrFlexQueryProperties();
        properties.setFlexToken("SYNTHETIC-NOT-A-CREDENTIAL"); properties.setTradeConfirmQueryId("SYNTHETIC");
        IbkrFlexClient fakeClient = new IbkrFlexClient(properties) {
            @Override public String fetchReport(Long accountKey, String from, String to) { return xml; }
        };
        var batches = context.getBean(BrokerSyncBatchService.class);
        var adapter = new IbkrSyncAdapter(properties, fakeClient, new FlexQueryParser(),
                context.getBean(IbkrStagingService.class), context.getBean(IbkrImportService.class),
                batches, context.getBean(IbkrStagedOrderRepository.class));
        var executor = new BrokerSyncAsyncExecutor(new BrokerSyncService(List.of(adapter), context.getBean(BrokerRepository.class), context.getBean(BrokerAccountService.class)),
                batches, context.getBean(SyncBatchFailureHandler.class));
        BrokerSyncBatch batch = batches.createBatch("ibkr", DAY, DAY, accountKey(context.getBean(BrokerRepository.class).findByBrokerCode("ibkr").orElseThrow().getId()), "SYNTHETIC");
        SyncRequest request = new SyncRequest(); request.setBrokerCode("ibkr");
        request.setStartTime(DAY.toString()); request.setEndTime(DAY.toString());
        executor.execute(batch.getId(), request);
        return context.getBean(BrokerSyncBatchRepository.class).findById(batch.getId()).orElseThrow();
    }

    static void assertLinkFailure(BrokerSyncBatch batch, String reason) {
        check(batch.getStatus().equals("FAILED") && batch.getErrorMessage().contains(reason)
                && !batch.getErrorMessage().contains("SYNTHETIC-NOT-A-CREDENTIAL"), "Wrong linkage failure: " + batch.getStatus());
        check(tradeRepo.findBySyncBatchIdAndIsDeletedFalse(batch.getId()).isEmpty(), "Failed sync left formal rows");
        check(context.getBean(IbkrStagedOrderRepository.class).findByBatchIdAndStatus(batch.getId(), "IMPORTED").isEmpty(),
                "Failed sync left staged orders");
        check(context.getBean(IbkrStagedTradeConfirmRepository.class).findByBatchId(batch.getId()).isEmpty(),
                "Failed sync left confirmations");
    }

    static TradeRecord optionFixture(String underlying, long brokerId) {
        TradeRecord record = trade(underlying + "-20260417-C10", DAY);
        record.setUnderlyingSymbol(underlying); record.setBrokerId(brokerId); record.setBrokerAccountId(accountKey(brokerId));
        record.setAssetType(AssetType.OPTION_CALL); record.setTradeType(TradeType.SELL); record.setQuantity(1);
        record.setPrice(BigDecimal.ZERO); record.setAmount(BigDecimal.ZERO);
        record.setTradeTrigger(TradeTrigger.OPTION); record.setTriggerRefType(TriggerRefType.OPTION_EXERCISE);
        return record;
    }

    static String linkXml(String prefix, String symbol, boolean stock, int options) {
        StringBuilder rows = new StringBuilder();
        if (stock) rows.append(linkXmlRow(prefix + "-STK", symbol, false, "20260417"));
        for (int i = 0; i < options; i++) rows.append(linkXmlRow(prefix + "-OPT" + i, symbol, true, i == 0 ? "20260417" : "20260515"));
        return "<FlexQueryResponse type='TCF'><FlexStatements count='1'><FlexStatement accountId='SYNTHETIC'>"
                + "<TradeConfirms>" + rows + "</TradeConfirms></FlexStatement></FlexStatements></FlexQueryResponse>";
    }

    static String linkXmlRow(String id, String symbol, boolean option, String expiry) {
        return "<Order orderID='" + id + "' symbol='" + symbol + "' description='" + symbol + " SYNTHETIC' "
                + "assetCategory='" + (option ? "OPT" : "STK") + "' currency='USD' buySell='" + (option ? "SELL" : "BUY")
                + "' quantity='" + (option ? "1" : "100") + "' price='" + (option ? "0" : "10")
                + "' amount='" + (option ? "0" : "1000") + "' commission='0' tradeDate='20260310' "
                + "expiry='" + expiry + "' putCall='C' strike='10' multiplier='100'/>"
                + "<TradeConfirm tradeID='" + id + "-T' orderID='" + id + "' code='Ex'/>";
    }

    static StockSplitEvent split(String symbol, LocalDate date) {
        StockSplitEvent event = new StockSplitEvent(); event.setSymbol(symbol); event.setEventDate(date);
        event.setRatioFrom(1); event.setRatioTo(2); return event;
    }

    static void preflight() throws Exception {
        for (String field : List.of("deleted", "processed")) {
            StockSplitEvent invalid = split("SYNTH-NULL-" + field, DAY);
            if (field.equals("deleted")) invalid.setIsDeleted(null); else invalid.setProcessed(null);
            final StockSplitEvent stored = splitRepo.save(invalid);
            conflict("LEGACY_EVENT_STATE", () -> splits.update(stored.getId(), split("SYNTH-CHANGED", DAY)));
            conflict("LEGACY_EVENT_STATE", () -> splits.delete(stored.getId()));
            try { context.getBean(MarketEventProcessingService.class).processStockSplitEvent(stored);
                throw new AssertionError("Null legacy state processed"); }
            catch (IllegalArgumentException expected) { }
        }
        for (int i = 0; i < 2; i++) {
            StockSplitEvent old = split("SYNTH-OLD-OVERLAP", DAY);
            old.setProcessed(true); old.setProcessedAt(java.time.LocalDateTime.now()); splitRepo.save(old);
        }
        String sql = java.nio.file.Files.readString(Path.of("e2e/check-market-event-history.sql"));
        var categories = new java.util.HashSet<String>();
        try (var connection = DriverManager.getConnection(url, USER, "")) {
            connection.setReadOnly(true);
            try (var result = connection.createStatement().executeQuery(sql)) {
                while (result.next()) categories.add(result.getString("issue"));
            }
        }
        check(categories.contains("UNPROCESSED_WITH_OUTPUT") && categories.contains("SAME_DAY_OVERLAP")
                        && categories.contains("MISSING_EVENT_STATE"),
                "Preflight did not detect synthetic legacy issues: " + categories);
        check(!categories.contains("ORPHANED_OUTPUT") && !categories.contains("SOURCE_MISMATCH"),
                "Preflight incorrectly rejected valid generated records");
        System.out.println("PASS read-only cutover query detects legacy output state and same-day overlap without rewriting rows");
    }
    static SymbolChangeEvent rename(String old, String next, LocalDate date) {
        SymbolChangeEvent event = new SymbolChangeEvent(); event.setSymbol(old); event.setOldSymbol(old);
        event.setNewSymbol(next); event.setEventDate(date); return event;
    }
    static TradeRecord trade(String symbol, LocalDate date) {
        TradeRecord record = new TradeRecord(); record.setSymbol(symbol); record.setUnderlyingSymbol(symbol);
        record.setTradeDate(date); record.setBrokerId(broker); record.setBrokerAccountId(accountKey(broker)); record.setAssetType(AssetType.STOCK);
        record.setCurrency(Currency.USD); record.setTradeType(TradeType.BUY); record.setQuantity(100);
        record.setPrice(BigDecimal.TEN); record.setFee(BigDecimal.ZERO); return record;
    }
    static int quantity(String symbol) {
        return context.getBean(PositionService.class).calculatePositions(DAY.plusDays(100), null).stream()
                .filter(p -> p.getSymbol().equals(symbol)).mapToInt(p -> p.getQuantity()).sum();
    }
    static void conflict(String code, Runnable action) {
        try { action.run(); throw new AssertionError("Expected " + code); }
        catch (MarketEventConflictException error) { check(code.equals(error.getCode()), "Expected " + code + ", got " + error.getCode()); }
    }
    static void await(CountDownLatch latch) {
        try { if (!latch.await(10, TimeUnit.SECONDS)) throw new AssertionError("Timed out waiting for transaction"); }
        catch (InterruptedException error) { Thread.currentThread().interrupt(); throw new IllegalStateException(error); }
    }
    static void check(boolean value, String message) { if (!value) throw new AssertionError(message); }
    static class DeliberateRollback extends RuntimeException { }
}
