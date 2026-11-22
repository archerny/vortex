import com.vortex.Application;
import com.vortex.dto.BrokerSyncConnectionUpdate;
import com.vortex.entity.*;
import com.vortex.entity.enums.*;
import com.vortex.repository.*;
import com.vortex.service.*;
import com.vortex.sync.adapter.ibkr.*;
import com.vortex.sync.adapter.tiger.*;
import com.vortex.sync.core.*;
import com.vortex.sync.exception.SyncConflictException;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.math.BigDecimal;
import java.nio.file.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/** Focused multi-account checks in the launcher-owned temporary PostgreSQL cluster. */
public class AccountConnectionsCheck {
    static final String USER = "vortex_settings_check";
    static void check(boolean value, String message) { if (!value) throw new AssertionError(message); }
    static void sql(String url, String statement) throws Exception {
        try (var connection = DriverManager.getConnection(url, USER, ""); var sql = connection.createStatement()) { sql.execute(statement); }
    }
    static void verify(String url, String statement) throws Exception {
        try (var connection = DriverManager.getConnection(url, USER, ""); var sql = connection.createStatement(); var row = sql.executeQuery(statement)) {
            check(row.next() && row.getBoolean(1), "Database assertion failed: " + statement);
        }
    }
    static Broker broker(String name, String code) { var value = new Broker(name, "US"); value.setBrokerCode(code); return value; }
    static BrokerAccount account(BrokerAccountService service, Broker broker, String identity) {
        var value = new BrokerAccount(); value.setBrokerId(broker.getId()); value.setAccountId(identity); value.setAccountName(identity);
        return service.create(value);
    }
    static void migration(String url) throws Exception {
        sql(url, "CREATE DATABASE account_upgrade");
        String upgrade = url.replace("/postgres", "/account_upgrade");
        Flyway.configure().dataSource(upgrade, USER, "").target("32").load().migrate();
        sql(upgrade, """
            INSERT INTO brokers(id,broker_name,country,broker_code) VALUES (10,'SYNTH-OLD','US',NULL),(20,'SYNTH-CURRENT','US','ibkr');
            INSERT INTO broker_sync_batches(id,broker_code,sync_date_from,sync_date_to,status) VALUES
                (401,'ibkr','2026-01-01','2026-01-02','COMPLETED'),(402,'ibkr','2026-01-01','2026-01-02','COMPLETED');
            INSERT INTO trade_records(id,broker_id,trade_date,asset_type,symbol,underlying_symbol,trade_type,quantity,price,amount,fee,currency,
                trade_trigger,trigger_ref_type,trigger_ref_id,sync_batch_id,is_deleted,updated_at) VALUES
                (501,10,'2026-01-01','STOCK','SYNTH-HISTORY','SYNTH-HISTORY','BUY',3,2.1234,6.37,0.123456789,'USD','MANUAL','NONE',0,401,true,'2025-01-01'),
                (502,10,'2026-01-02','STOCK','SYNTH-HISTORY','SYNTH-HISTORY','BUY',3,0,0,0,'USD','MARKET_EVENT','STOCK_SPLIT',42,NULL,false,'2025-01-01');
            INSERT INTO cash_flow_records(id,broker_id,record_date,record_type,amount,currency,is_deleted,updated_at)
                VALUES (601,20,'2026-01-01','DEPOSIT',1.23,'USD',true,'2025-01-01');
            INSERT INTO broker_sync_connections(broker_id,broker_code,credentials) VALUES
                (20,'ibkr','{"token":"synthetic-token-upgrade","queryId":"123"}');
            INSERT INTO ibkr_staged_orders(id,batch_id,account_id,order_id,status,imported_trade_id)
                VALUES (701,401,NULL,'SYNTH-HIST-ORDER','IMPORTED',501);
            CREATE FUNCTION synthetic_fail_account_upgrade() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN RAISE EXCEPTION 'synthetic migration failure'; END $$;
            CREATE TRIGGER synthetic_account_upgrade BEFORE UPDATE ON trade_records FOR EACH ROW EXECUTE FUNCTION synthetic_fail_account_upgrade();
            """);
        try { Flyway.configure().dataSource(upgrade, USER, "").target("33").load().migrate(); throw new AssertionError("Migration should roll back"); }
        catch (FlywayException expected) { }
        verify(upgrade, "SELECT to_regclass('broker_accounts') IS NULL AND EXISTS(SELECT 1 FROM broker_sync_connections WHERE broker_id=20)");
        sql(upgrade, "DROP TRIGGER synthetic_account_upgrade ON trade_records; DROP FUNCTION synthetic_fail_account_upgrade()");
        Flyway.configure().dataSource(upgrade, USER, "").target("33").load().migrate();
        verify(upgrade, "SELECT count(*)=2 AND bool_and(account_id IS NULL) FROM broker_accounts");
        verify(upgrade, "SELECT count(*)=2 AND bool_and(broker_account_id=10 AND updated_at='2025-01-01') FROM trade_records");
        verify(upgrade, "SELECT price=2.1234 AND amount=6.37 AND fee=0.123456789 AND is_deleted AND trigger_ref_id=0 FROM trade_records WHERE id=501");
        verify(upgrade, "SELECT trigger_ref_id=42 AND trade_trigger='MARKET_EVENT' FROM trade_records WHERE id=502");
        verify(upgrade, "SELECT broker_account_id=20 AND amount=1.23 AND is_deleted AND updated_at='2025-01-01' FROM cash_flow_records WHERE id=601");
        verify(upgrade, "SELECT broker_account_id=10 AND account_id IS NULL FROM broker_sync_batches WHERE id=401");
        verify(upgrade, "SELECT broker_account_id IS NULL FROM broker_sync_batches WHERE id=402");
        verify(upgrade, "SELECT credentials->>'token'='synthetic-token-upgrade' FROM broker_sync_connections WHERE broker_account_id=20");
        verify(upgrade, "SELECT account_id IS NULL FROM ibkr_staged_orders WHERE id=701");
        System.out.println("PASS V32 to V33 pending-account ownership, moved broker code, deleted/event history, exact values/timestamps and failure rollback/retry");
    }
    static TradeRecord trade(BrokerAccount owner, String symbol, int quantity, String price, TradeType direction) {
        var value = new TradeRecord(); value.setBrokerId(owner.getBrokerId()); value.setBrokerAccountId(owner.getId());
        value.setTradeDate(LocalDate.of(2026, 1, 1)); value.setSymbol(symbol); value.setUnderlyingSymbol(symbol);
        value.setAssetType(AssetType.STOCK); value.setTradeType(direction); value.setQuantity(quantity);
        value.setPrice(new BigDecimal(price)); value.setAmount(new BigDecimal(price).multiply(BigDecimal.valueOf(quantity)));
        value.setFee(BigDecimal.ZERO); value.setCurrency(com.vortex.entity.enums.Currency.USD);
        value.setTradeTrigger(TradeTrigger.MANUAL); value.setTriggerRefType(TriggerRefType.NONE); value.setTriggerRefId(0L);
        return value;
    }
    record Order(String id, String quantity) { }
    static String report(String identity, Order... orders) {
        var xml = new StringBuilder("<FlexQueryResponse type=\"TCF\"><FlexStatements count=\"1\"><FlexStatement accountId=\"").append(identity).append("\"><TradeConfirms>");
        for (var order : orders) {
            // Source account omitted intentionally: inherit the validated statement identity.
            xml.append("<Order orderID=\"").append(order.id()).append("\" assetCategory=\"STK\" symbol=\"SYNTH-IMPORT\" currency=\"USD\" tradeDate=\"20260110\" buySell=\"BUY\" quantity=\"")
                .append(order.quantity()).append("\" price=\"10\" amount=\"100\" commission=\"-0.123456789\" orderTime=\"20260110;100000\" orderType=\"LMT\"/>");
            xml.append("<TradeConfirm tradeID=\"FILL-").append(order.id()).append("\" orderID=\"").append(order.id()).append("\" assetCategory=\"STK\" symbol=\"SYNTH-IMPORT\" quantity=\"")
                .append(order.quantity()).append("\" price=\"10\" amount=\"100\" commission=\"-0.123456789\" tradeDate=\"20260110\" buySell=\"BUY\" currency=\"USD\"/>");
        }
        return xml.append("</TradeConfirms></FlexStatement></FlexStatements></FlexQueryResponse>").toString();
    }
    static BrokerSyncBatch run(BrokerAccount owner, String xml, AtomicReference<String> content,
            BrokerSyncService router, BrokerSyncBatchService batches, BrokerSyncAsyncExecutor executor) {
        content.set(xml);
        var request = new SyncRequest("ibkr", "2026-01-01", "2026-01-31"); request.setBrokerAccountId(owner.getId());
        router.prepareRequest(request);
        var batch = batches.createBatch("ibkr", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31), request.getBrokerAccountId(), request.getAccountId());
        executor.execute(batch.getId(), request);
        return batches.findById(batch.getId()).orElseThrow();
    }
    static void ledger(ServletWebServerApplicationContext context, BrokerAccount first, BrokerAccount second) {
        var trades = context.getBean(TradeRecordService.class);
        var positions = context.getBean(PositionService.class);
        var original = trades.create(trade(first, "SYNTH-MOVE", 1, "1", TradeType.BUY));
        var updated = trades.update(original.getId(), trade(second, "SYNTH-MOVE", 1, "1", TradeType.BUY));
        check(updated.getBrokerAccountId().equals(second.getId()) && updated.getBrokerAccount().getAccountId().equals(second.getAccountId()), "Updated trade response retained the old account association");
        var source = trade(first, "SYNTH-REF-20261218-C10", 1, "10", TradeType.BUY);
        source.setAssetType(AssetType.OPTION_CALL); source.setUnderlyingSymbol("SYNTH-REF"); source = trades.create(source);
        var dependent = trade(first, "SYNTH-REF", 100, "10", TradeType.BUY);
        dependent.setTradeTrigger(TradeTrigger.OPTION); dependent.setTriggerRefType(TriggerRefType.OPTION_EXERCISE); dependent.setTriggerRefId(source.getId());
        dependent = trades.create(dependent);
        var moveSource = trade(second, source.getSymbol(), 1, "10", TradeType.BUY);
        moveSource.setAssetType(AssetType.OPTION_CALL); moveSource.setUnderlyingSymbol("SYNTH-REF");
        try { trades.update(source.getId(), moveSource); throw new AssertionError("Referenced option moved across accounts"); }
        catch (IllegalArgumentException expected) { }
        check(trades.findById(source.getId()).orElseThrow().getBrokerAccountId().equals(first.getId()), "Rejected move changed source ownership");
        trades.softDelete(dependent.getId());
        check(trades.update(source.getId(), moveSource).getBrokerAccountId().equals(second.getId()), "Deleted reference prevented an otherwise valid move");
        trades.create(trade(first, "SYNTH-POSITION", 100, "1", TradeType.BUY));
        trades.create(trade(second, "SYNTH-POSITION", 100, "1", TradeType.SELL));
        var snapshots = positions.calculatePositions(LocalDate.of(2026, 1, 2), first.getBrokerId()).stream().filter(p -> p.getSymbol().equals("SYNTH-POSITION")).toList();
        check(snapshots.size()==2 && snapshots.stream().mapToInt(p -> p.getQuantity()).sum()==0, "Same-broker positions offset across accounts");
        check(positions.calculatePositions(LocalDate.of(2026, 1, 2), null, second.getId()).stream().anyMatch(p -> p.getQuantity()==-100), "Account position filter ignored");
        trades.create(trade(first, "SYNTH-COST", 100, "10", TradeType.BUY));
        trades.create(trade(second, "SYNTH-COST", 100, "20", TradeType.BUY));
        var change = new SymbolChangeEvent(); change.setSymbol("SYNTH-COST"); change.setOldSymbol("SYNTH-COST"); change.setNewSymbol("SYNTH-NEW");
        change.setEventDate(LocalDate.of(2026, 2, 1)); change.setCurrency(com.vortex.entity.enums.Currency.USD);
        change = context.getBean(SymbolChangeEventRepository.class).save(change);
        context.getBean(MarketEventProcessingService.class).processSymbolChangeEvent(change);
        var repository = context.getBean(TradeRecordRepository.class);
        var generated = repository.findByTriggerRefIdAndTriggerRefTypeAndIsDeletedFalse(change.getId(), TriggerRefType.SYMBOL_CHANGE);
        check(generated.size()==4, "Symbol change did not generate per-account transfers");
        for (BrokerAccount owner : List.of(first, second)) {
            var transfers = generated.stream().filter(t -> t.getBrokerAccountId().equals(owner.getId())).toList();
            check(transfers.size()==2, "Account did not receive exactly two symbol transfers");
            check(transfers.stream().filter(t -> t.getTradeType()==TradeType.SELL && t.getSymbol().equals("SYNTH-COST")).count()==1,
                "Account did not receive its old-symbol sale");
            check(transfers.stream().filter(t -> t.getTradeType()==TradeType.BUY && t.getSymbol().equals("SYNTH-NEW")).count()==1,
                "Account did not receive its new-symbol purchase");
        }
        check(generated.stream().filter(t -> t.getBrokerAccountId().equals(first.getId())).allMatch(t -> t.getPrice().compareTo(new BigDecimal("10"))==0), "First account cost was mixed");
        check(generated.stream().filter(t -> t.getBrokerAccountId().equals(second.getId())).allMatch(t -> t.getPrice().compareTo(new BigDecimal("20"))==0), "Second account cost was mixed");
        trades.create(trade(first, "SYNTH-DIV", 1, "1", TradeType.BUY));
        trades.create(trade(second, "SYNTH-DIV", 1, "1", TradeType.BUY));
        var dividend = new DividendInKindEvent(); dividend.setSymbol("SYNTH-DIV"); dividend.setDividendSymbol("SYNTH-DIV-NEW");
        dividend.setEventDate(LocalDate.of(2026, 2, 2)); dividend.setRatioFrom(2); dividend.setRatioTo(1);
        dividend.setCurrency(com.vortex.entity.enums.Currency.USD); dividend.setDividendCurrency(com.vortex.entity.enums.Currency.USD);
        dividend = context.getBean(DividendInKindEventRepository.class).save(dividend);
        context.getBean(MarketEventProcessingService.class).processDividendInKindEvent(dividend);
        check(repository.findByTriggerRefIdAndTriggerRefTypeAndIsDeletedFalse(dividend.getId(), TriggerRefType.DIVIDEND_IN_KIND).isEmpty(), "Dividend rounding was combined across accounts");
        var cash = new CashFlowRecord(); cash.setBrokerAccountId(second.getId()); cash.setRecordDate(LocalDate.of(2026, 1, 1));
        cash.setRecordType(RecordType.DEPOSIT); cash.setAmount(BigDecimal.ONE); cash.setCurrency(com.vortex.entity.enums.Currency.USD);
        context.getBean(CashFlowRecordService.class).create(cash);
        check(cash.getBrokerId().equals(second.getBrokerId()), "Cash flow did not use selected account owner");
        System.out.println("PASS real ORM account-owned trades/cash, independent positions, per-account event cost and dividend rounding");
    }
    static void optionLinks(ServletWebServerApplicationContext context, BrokerAccount first, BrokerAccount second, Long batchId) {
        var repository = context.getBean(TradeRecordRepository.class);
        var option = trade(second, "SYNTH-LINK-20261218-C30", 1, "30", TradeType.SELL);
        option.setAssetType(AssetType.OPTION_CALL); option.setUnderlyingSymbol("SYNTH-LINK"); option.setTradeTrigger(TradeTrigger.OPTION); option.setTriggerRefType(TriggerRefType.OPTION_EXERCISE);
        var other = repository.save(option);
        var stock = trade(first, "SYNTH-LINK", 100, "30", TradeType.BUY);
        stock.setTradeTrigger(TradeTrigger.OPTION); stock.setTriggerRefType(TriggerRefType.OPTION_EXERCISE); stock.setSyncBatchId(batchId);
        stock = repository.save(stock);
        var worker = context.getBean(IbkrImportWorker.class);
        try { worker.backfillSingleStkRecord(stock); throw new AssertionError("Cross-account option was linked"); }
        catch (CategorizedSyncException expected) { }
        var own = trade(first, "SYNTH-LINK-20261218-C30", 1, "30", TradeType.SELL);
        own.setAssetType(AssetType.OPTION_CALL); own.setUnderlyingSymbol("SYNTH-LINK"); own.setTradeTrigger(TradeTrigger.OPTION); own.setTriggerRefType(TriggerRefType.OPTION_EXERCISE);
        own = repository.save(own);
        worker.backfillSingleStkRecord(stock);
        check(repository.findById(stock.getId()).orElseThrow().getTriggerRefId().equals(own.getId()), "Own-account option was not linked");
        check(repository.findById(other.getId()).orElseThrow().getTriggerRefId()==0L, "Other account option changed");
        System.out.println("PASS real JPQL option linkage excludes another account with the identical contract and event");
    }
    static void tigerScope(ServletWebServerApplicationContext context, BrokerAccount first, BrokerAccount second) {
        var batches = context.getBean(BrokerSyncBatchRepository.class);
        var staged = context.getBean(TigerStagedOrderRepository.class);
        var worker = context.getBean(TigerImportWorker.class);
        for (BrokerAccount account : List.of(first, second)) {
            // Synthetic completed batch is only a container for isolated repository/Worker checks.
            var batch = new BrokerSyncBatch(); batch.setBrokerCode("tiger"); batch.setBrokerAccountId(account.getId()); batch.setAccountId(account.getAccountId());
            batch.setSyncDateFrom(LocalDate.of(2026,1,1)); batch.setSyncDateTo(LocalDate.of(2026,1,2)); batch.setStatus("COMPLETED"); batch = batches.save(batch);
            var source = new TigerOrderRecord(); source.setAccount(account.getAccountId()); source.setOrderId(900);
            check(context.getBean(TigerStagingWorker.class).stageOrder(batch.getId(), source), "Cross-account Tiger stage ID was skipped");
            check(!context.getBean(TigerStagingWorker.class).stageOrder(batch.getId(), source), "Same-account Tiger stage duplicate was inserted");
            // Epoch fixture deliberately isolates account ownership from the known normal-stage time-format defect.
            var row = new TigerStagedOrder(); row.setBatchId(batch.getId()); row.setTigerId("901"); row.setAccount(account.getAccountId());
            row.setStatus("PENDING"); row.setAction("BUY"); row.setTradeTime("1704067200000"); row.setFilledQuantity("1"); row.setQuantity("1"); row.setQuantityScale("0");
            row.setAvgFillPrice("10"); row.setCommission("0"); row.setGst("0"); row.setSymbol("SYNTH-TIGER-ORDER"); row.setSecType("STK"); row.setCurrency("USD");
            row = staged.save(row);
            worker.importOne(batch.getId(), account.getBrokerId(), account.getId(), row);
            worker.importOne(batch.getId(), account.getBrokerId(), account.getId(), staged.findById(row.getId()).orElseThrow());
        }
        var repository = context.getBean(TradeRecordRepository.class);
        check(repository.findByBrokerAccountIdAndExternalBrokerAndExternalId(first.getId(),"tiger","901").isPresent(),"First Tiger account import lost");
        check(repository.findByBrokerAccountIdAndExternalBrokerAndExternalId(second.getId(),"tiger","901").isPresent(),"Second Tiger account import lost");
        check(repository.findAll().stream().filter(row -> "tiger".equals(row.getExternalBroker()) && "901".equals(row.getExternalId())).count()==2,"Tiger duplicate scope mixed accounts");
        System.out.println("PASS Tiger same-ID cross-account staging and real ORM import/dedup with epoch fixtures; normal-stage time defect remains excluded");
    }
    public static void main(String[] args) throws Exception {
        Path scratch = Path.of(args[0]).toRealPath();
        check(scratch.toString().startsWith("/tmp/vortex-ibkr-settings-"), "Unexpected scratch directory");
        String url = "jdbc:postgresql://127.0.0.1:" + Integer.parseInt(args[1]) + "/postgres";
        verify(url, "SELECT current_setting('data_directory')='"+scratch.resolve("data")+"'");
        migration(url);
        Path config = scratch.resolve("accounts.properties");
        Files.writeString(config, "spring.datasource.url="+url+"\nspring.datasource.username="+USER+"\nspring.datasource.password=\nspring.jpa.hibernate.ddl-auto=validate\nspring.jpa.show-sql=false\nserver.address=127.0.0.1\nserver.port=0\nlogging.level.root=WARN\nbroker.ibkr.base-url=http://127.0.0.1:"+Integer.parseInt(args[2])+"\n");
        try (var context = (ServletWebServerApplicationContext) new SpringApplication(Application.class).run("--spring.config.location="+config.toUri(), "--spring.profiles.active=isolated-accounts-check")) {
            var brokers = context.getBean(BrokerService.class); var accounts = context.getBean(BrokerAccountService.class);
            var ibkr = brokers.create(broker("SYNTH-IBKR", "ibkr")); var tiger = brokers.create(broker("SYNTH-TIGER", "tiger"));
            var manual = brokers.create(broker("SYNTH-MANUAL", null));
            var first = account(accounts, ibkr, "SYNTH-IBKR-A"); var second = account(accounts, ibkr, "SYNTH-IBKR-B");
            var tigerA = account(accounts, tiger, "SYNTH-TIGER-A"); var tigerB = account(accounts, tiger, "SYNTH-TIGER-B");
            account(accounts, manual, "SYNTH-MANUAL-A");
            var connections = context.getBean(BrokerSyncConnectionService.class);
            connections.save(first.getId(), new BrokerSyncConnectionUpdate("ibkr", Map.of("token","synthetic-token-A","queryId","123")));
            connections.save(second.getId(), new BrokerSyncConnectionUpdate("ibkr", Map.of("token","synthetic-token-B","queryId","456")));
            connections.save(tigerA.getId(), new BrokerSyncConnectionUpdate("tiger", Map.of("tigerId","100","privateKey","synthetic-private-key-A")));
            connections.save(tigerB.getId(), new BrokerSyncConnectionUpdate("tiger", Map.of("tigerId","200","privateKey","synthetic-private-key-B")));
            var properties = context.getBean(IbkrFlexQueryProperties.class); var tigerProperties = context.getBean(TigerApiProperties.class);
            check(properties.credentials(first.getId()).token().equals("synthetic-token-A") && properties.credentials(second.getId()).token().equals("synthetic-token-B"), "IBKR account credentials mixed");
            check(tigerProperties.credentials(tigerA.getId()).account().equals(tigerA.getAccountId()) && tigerProperties.credentials(tigerB.getId()).privateKey().equals("synthetic-private-key-B"), "Tiger account identity/credentials mixed");
            check(!properties.isConfigured() && !tigerProperties.isConfigured(), "Unscoped runtime chose an account");
            try { connections.getForBrokerCode("ibkr"); throw new AssertionError("Legacy API chose an arbitrary account"); } catch (IllegalArgumentException expected) { }
            try { accounts.resolve(null, ibkr.getId()); throw new AssertionError("Ambiguous broker-only request accepted"); } catch (IllegalArgumentException expected) { }
            var transaction = new TransactionTemplate(context.getBean(PlatformTransactionManager.class));
            try { transaction.execute(status -> { connections.save(first.getId(),new BrokerSyncConnectionUpdate("ibkr",Map.of("token","synthetic-token-rollback","queryId","999"))); throw new IllegalStateException("synthetic rollback"); }); }
            catch (IllegalStateException expected) { }
            check(properties.credentials(first.getId()).token().equals("synthetic-token-A"), "Uncommitted credentials became visible");
            System.out.println("PASS fresh current schema, same-broker IBKR/Tiger independent DB credentials, real credential rollback and ambiguity rejection");
            var content = new AtomicReference<String>();
            var moveCode = new java.util.concurrent.atomic.AtomicBoolean();
            var alternate = brokers.create(broker("SYNTH-ALTERNATE", null));
            var client = new IbkrFlexClient(properties) { @Override public String fetchReport(Long accountKey,String from,String to) {
                check(properties.credentials(accountKey).token()!=null,"Missing selected credentials");
                if (moveCode.getAndSet(false)) {
                    try { sql(url,"UPDATE brokers SET broker_code=NULL WHERE id="+ibkr.getId()+"; UPDATE brokers SET broker_code='ibkr' WHERE id="+alternate.getId()); }
                    catch (Exception error) { throw new IllegalStateException(error); }
                }
                return content.get();
            } };
            var batches = context.getBean(BrokerSyncBatchService.class);
            var adapter = new IbkrSyncAdapter(properties,client,context.getBean(FlexQueryParser.class),context.getBean(IbkrStagingService.class),context.getBean(IbkrImportService.class),batches,context.getBean(IbkrStagedOrderRepository.class));
            var router = new BrokerSyncService(List.of(adapter),context.getBean(BrokerRepository.class),accounts);
            var executor = new BrokerSyncAsyncExecutor(router,batches,context.getBean(SyncBatchFailureHandler.class));
            var firstBatch = run(first,report(first.getAccountId(),new Order("SYNTH-SHARED","10")),content,router,batches,executor);
            check(firstBatch.getStatus().equals("COMPLETED"),"First account import failed: "+firstBatch.getErrorMessage());
            check(run(second,report(second.getAccountId(),new Order("SYNTH-SHARED","10")),content,router,batches,executor).getStatus().equals("COMPLETED"),"Second account import failed");
            check(run(first,report(first.getAccountId(),new Order("SYNTH-SHARED","10")),content,router,batches,executor).getStatus().equals("COMPLETED"),"Same-account repeat failed");
            check(run(first,report(first.getAccountId(),new Order("SYNTH-SHARED","20")),content,router,batches,executor).getStatus().equals("FAILED"),"Duplicate content conflict was ignored");
            var mismatch=run(first,report(second.getAccountId()),content,router,batches,executor);
            check(mismatch.getStatus().equals("FAILED"),"Wrong-account empty report accepted");
            var partial=run(first,report(first.getAccountId(),new Order("SYNTH-NEW","10"),new Order("SYNTH-BAD","0.5")),content,router,batches,executor);
            check(partial.getStatus().equals("FAILED"),"Partial failure did not fail the batch");
            var trades=context.getBean(TradeRecordRepository.class);
            check(trades.findByBrokerAccountIdAndExternalBrokerAndExternalId(first.getId(),"ibkr","SYNTH-SHARED").isPresent(),"First account history lost");
            check(trades.findByBrokerAccountIdAndExternalBrokerAndExternalId(second.getId(),"ibkr","SYNTH-SHARED").isPresent(),"Second account history lost");
            check(trades.findBySyncBatchIdAndIsDeletedFalse(partial.getId()).isEmpty(),"Failed batch retained new trades");
            check(context.getBean(IbkrStagedOrderRepository.class).findByBatchId(partial.getId()).isEmpty(),"Failed batch retained staging");
            check(context.getBean(IbkrStagedTradeConfirmRepository.class).findByBatchId(partial.getId()).isEmpty(),"Failed batch retained trade confirms");
            verify(url,"SELECT count(*)=2 FROM ibkr_staged_orders WHERE order_id='SYNTH-SHARED' AND account_id IS NOT NULL");
            var pending=batches.createBatch("ibkr",null,null,first.getId(),first.getAccountId());
            try { batches.createBatch("ibkr",null,null,second.getId(),second.getAccountId()); throw new AssertionError("Global active batch constraint lost"); }
            catch (SyncConflictException expected) { }
            context.getBean(SyncBatchFailureHandler.class).handleFailure(pending.getId(),"ibkr","synthetic pending cleanup");
            System.out.println("PASS same external IDs across accounts, repeated/conflicting imports, missing source-account inheritance, wrong empty report, partial-failure cleanup and global batch exclusion");
            moveCode.set(true);
            try {
                var moved = run(first,report(first.getAccountId(),new Order("SYNTH-MOVED","10")),content,router,batches,executor);
                check(moved.getStatus().equals("COMPLETED"),"Import resolved current broker code instead of fixed batch ownership");
                check(trades.findByBrokerAccountIdAndExternalBrokerAndExternalId(first.getId(),"ibkr","SYNTH-MOVED").orElseThrow().getBrokerId().equals(ibkr.getId()),"Moved code changed the trade owner");
            } finally { sql(url,"UPDATE brokers SET broker_code=NULL WHERE id="+alternate.getId()+"; UPDATE brokers SET broker_code='ibkr' WHERE id="+ibkr.getId()); }
            System.out.println("PASS broker-code transfer during fetch retains the batch account and original broker on import");
            optionLinks(context,first,second,firstBatch.getId());
            ledger(context,first,second);
            tigerScope(context,tigerA,tigerB);
            Files.writeString(scratch.resolve("ready"),Integer.toString(context.getWebServer().getPort()));
            System.in.read();
        }
    }
}
