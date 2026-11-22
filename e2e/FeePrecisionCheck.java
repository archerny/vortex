import com.vortex.entity.Broker;
import com.vortex.entity.BrokerAccount;
import com.vortex.entity.Strategy;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TradeType;
import com.vortex.repository.TradeRecordRepository;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

/** Separate from unit tests: accepts only the launcher-owned temporary cluster. */
public class FeePrecisionCheck {
    private static final String USER = "vortex_fee_check";
    private static final String MIGRATIONS = "filesystem:" + Path.of("backend/src/main/resources/db/migration").toAbsolutePath();
    private static int port;
    private static String dataDirectory;

    public static void main(String[] args) throws Exception {
        port = Integer.parseInt(args[0]);
        dataDirectory = Path.of(args[1]).toRealPath().toString();
        check(dataDirectory.startsWith("/tmp/vortex-fee-precision-") && dataDirectory.endsWith("/data"),
                "Refusing a non-disposable cluster path");
        try (Connection admin = connect("postgres")) {
            for (String database : new String[] {"fee_empty", "fee_upgrade", "fee_failure"}) {
                admin.createStatement().execute("CREATE DATABASE " + database);
            }
        }

        check(flyway("fee_empty", null).migrate().migrationsExecuted == 29, "Fresh migration count");
        verifyOrmAndConstraints("fee_empty");
        System.out.println("PASS empty database: V1 -> V29, ORM read/write, repository sums and constraints");

        flyway("fee_upgrade", "28").migrate();
        String history;
        try (Connection connection = connect("fee_upgrade")) {
            long broker = broker(connection);
            insert(connection, broker, "SYNTH-HISTORY", "1.25");
            history = scalar(connection, "SELECT row_to_json(t)::text FROM trade_records t");
            connection.setAutoCommit(false);
            insert(connection, broker, "SYNTH-OLD-PRECISION", "1.12345678");
            check(new BigDecimal("1.12").compareTo(new BigDecimal(scalar(connection,
                    "SELECT fee FROM trade_records WHERE symbol = 'SYNTH-OLD-PRECISION'"))) == 0,
                    "Old schema should reproduce the original rounding");
            connection.rollback();
        }
        check(flyway("fee_upgrade", null).migrate().migrationsExecuted == 1, "Upgrade migration count");
        try (Connection connection = connect("fee_upgrade")) {
            check(history.equals(scalar(connection, "SELECT row_to_json(t)::text FROM trade_records t")),
                    "Historical two-decimal row changed");
        }
        verifyOrmAndConstraints("fee_upgrade");
        check(flyway("fee_upgrade", null).migrate().migrationsExecuted == 0, "Repeat migration is not a no-op");
        System.out.println("PASS V28 -> V29: old rounding reproduced, historical row unchanged, full precision retained");

        flyway("fee_failure", "28").migrate();
        try (Connection blocker = connect("fee_failure")) {
            blocker.setAutoCommit(false);
            blocker.createStatement().execute("LOCK TABLE trade_records IN ACCESS EXCLUSIVE MODE");
            try {
                flyway("fee_failure", null, "SET lock_timeout = '150ms'").migrate();
                throw new AssertionError("Expected migration failure while table is locked");
            } catch (org.flywaydb.core.api.FlywayException expected) {
                check(expected.getMessage().contains("Migration"), "Unexpected failure before migration");
            }
            blocker.rollback();
        }
        try (Connection connection = connect("fee_failure")) {
            check("2".equals(scalar(connection, "SELECT numeric_scale FROM information_schema.columns "
                    + "WHERE table_name = 'trade_records' AND column_name = 'fee'")), "Failed migration changed scale");
            check("28".equals(scalar(connection, "SELECT max(version::int) FROM flyway_schema_history WHERE success")),
                    "Failed migration advanced version");
        }
        check(flyway("fee_failure", null).migrate().migrationsExecuted == 1, "Retry did not apply V29");
        System.out.println("PASS migration lock failure: original schema retained, transaction rolled back, retry succeeded");
    }

    private static Flyway flyway(String database, String target, String... initSql) {
        var config = Flyway.configure().dataSource(url(database), USER, "").locations(MIGRATIONS)
                .initSql(String.join(";", initSql));
        config.target(target == null ? "29" : target);
        return config.load();
    }

    private static String url(String database) {
        return "jdbc:postgresql://127.0.0.1:" + port + "/" + database;
    }

    private static Connection connect(String database) throws Exception {
        Connection connection = DriverManager.getConnection(url(database), USER, "");
        try {
            check(dataDirectory.equals(scalar(connection, "SHOW data_directory")), "Connection target is not our temporary cluster");
            return connection;
        } catch (Throwable error) {
            connection.close();
            throw error;
        }
    }

    private static void verifyOrmAndConstraints(String database) throws Exception {
        // Keep fee-migration assertions scoped to V29; use the current schema for current entities.
        Flyway.configure().dataSource(url(database), USER, "").locations(MIGRATIONS).load().migrate();
        long broker;
        try (Connection connection = connect(database)) {
            check("-1".equals(scalar(connection, "SELECT atttypmod FROM pg_attribute "
                    + "WHERE attrelid = 'trade_records'::regclass AND attname = 'fee'")), "Fee still has fixed scale");
            broker = broker(connection);
        }
        try (var factory = new Configuration().addAnnotatedClass(TradeRecord.class)
                .addAnnotatedClass(Broker.class).addAnnotatedClass(BrokerAccount.class).addAnnotatedClass(Strategy.class)
                .setProperty("hibernate.connection.url", url(database))
                .setProperty("hibernate.connection.username", USER)
                .setProperty("hibernate.hbm2ddl.auto", "validate")
                .setProperty("hibernate.show_sql", "false")
                .buildSessionFactory(); var session = factory.openSession()) {
            TradeRecordRepository repository = new JpaRepositoryFactory(session).getRepository(TradeRecordRepository.class);
            session.beginTransaction();
            BigDecimal expected = BigDecimal.ZERO;
            for (String fee : new String[] {"1.12345678", "0.0000000001234567890123456789", "123456789012.1234567890123456789"}) {
                TradeRecord record = new TradeRecord();
                record.setTradeDate(LocalDate.of(2026, 11, 17));
                record.setBrokerId(broker); record.setBrokerAccountId(broker);
                record.setAssetType(AssetType.STOCK);
                record.setSymbol("SYNTH-PRECISE");
                record.setUnderlyingSymbol("SYNTH");
                record.setTradeType(TradeType.BUY);
                record.setTradeTrigger(TradeTrigger.MANUAL);
                record.setQuantity(1);
                record.setPrice(BigDecimal.TEN);
                record.setAmount(BigDecimal.TEN);
                record.setFee(new BigDecimal(fee));
                // Use a different currency from the synthetic historical USD row.
                record.setCurrency(Currency.CNY);
                repository.save(record);
                session.flush();
                session.clear();
                var reread = repository.findById(record.getId()).orElseThrow();
                check(record.getFee().compareTo(reread.getFee()) == 0, "ORM changed fee digits");
                expected = expected.add(record.getFee());
            }
            check(expected.compareTo(repository.sumFeeByIsDeletedFalseAndCurrency(Currency.CNY)) == 0,
                    "Repository aggregate differs from full-precision sum");
            session.getTransaction().commit();
        }
        try (Connection connection = connect(database)) {
            expectConstraint(connection, broker, "-0.000000001", "23514");
            expectConstraint(connection, broker, null, "23502");
            connection.createStatement().execute("INSERT INTO trade_records "
                    + "(trade_date,broker_id,broker_account_id,asset_type,symbol,underlying_symbol,trade_type,quantity,price,amount,trade_trigger) "
                    + "VALUES ('2026-11-17'," + broker + "," + broker + ",'STOCK','SYNTH-DEFAULT','SYNTH','BUY',1,10,10,'MANUAL')");
            check(BigDecimal.ZERO.compareTo(new BigDecimal(scalar(connection,
                    "SELECT fee::text FROM trade_records WHERE symbol = 'SYNTH-DEFAULT'"))) == 0,
                    "Default fee changed");
        }
    }

    private static void expectConstraint(Connection connection, long broker, String fee, String state) throws Exception {
        try {
            insert(connection, broker, "SYNTH-INVALID", fee);
            throw new AssertionError("Expected constraint violation " + state);
        } catch (SQLException expected) {
            check(state.equals(expected.getSQLState()), "Unexpected constraint SQL state: " + expected.getSQLState());
        }
    }

    private static long broker(Connection connection) throws Exception {
        try (var result = connection.createStatement().executeQuery("INSERT INTO brokers (broker_name,country) "
                + "VALUES ('SYNTH-' || nextval('brokers_id_seq'),'SYNTH') RETURNING id")) {
            result.next();
            long id = result.getLong(1);
            if (scalar(connection, "SELECT to_regclass('broker_accounts')") != null) {
                connection.createStatement().execute("INSERT INTO broker_accounts(id,broker_id,account_id,account_name) VALUES ("+id+","+id+",'SYNTH-"+id+"','Synthetic account')");
            }
            return id;
        }
    }

    private static void insert(Connection connection, long broker, String symbol, String fee) throws Exception {
        boolean scoped = scalar(connection, "SELECT to_regclass('broker_accounts')") != null;
        try (var statement = connection.prepareStatement("INSERT INTO trade_records "
                + "(trade_date,broker_id,"+(scoped ? "broker_account_id," : "")+"asset_type,symbol,underlying_symbol,trade_type,quantity,price,amount,fee,currency,trade_trigger) "
                + "VALUES ('2026-11-17',?,"+(scoped ? "?," : "")+"'STOCK',?,'SYNTH','BUY',1,10,10,?,'USD','MANUAL')")) {
            int parameter = 1;
            statement.setLong(parameter++, broker);
            if (scoped) statement.setLong(parameter++, broker);
            statement.setString(parameter++, symbol);
            statement.setBigDecimal(parameter, fee == null ? null : new BigDecimal(fee));
            statement.executeUpdate();
        }
    }

    private static String scalar(Connection connection, String sql) throws Exception {
        try (var result = connection.createStatement().executeQuery(sql)) {
            result.next();
            return result.getString(1);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
