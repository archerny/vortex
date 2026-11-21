import com.vortex.Application;
import com.vortex.dto.IbkrSyncSettingsUpdate;
import com.vortex.entity.Broker;
import com.vortex.service.BrokerService;
import com.vortex.sync.adapter.ibkr.*;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.DriverManager;
import java.util.concurrent.*;

/** Disposable full application: never loads local/dev properties or real broker credentials. */
public class IbkrSettingsCheck {
    static final String USER = "vortex_settings_check";
    static void check(boolean ok, String message) { if (!ok) throw new AssertionError(message); }
    static IbkrSyncSettingsUpdate input(long broker, String token, String query) {
        var result = new IbkrSyncSettingsUpdate(); result.setBrokerId(broker);
        result.setToken(token); result.setQueryId(query); return result;
    }
    public static void main(String[] args) throws Exception {
        Path scratch = Path.of(args[0]).toRealPath();
        check(scratch.toString().startsWith("/tmp/vortex-ibkr-settings-"), "Unsafe scratch path");
        String url = "jdbc:postgresql://127.0.0.1:" + Integer.parseInt(args[1]) + "/postgres";
        try (var connection = DriverManager.getConnection(url, USER, ""); var statement = connection.createStatement()) {
            var result = statement.executeQuery("SHOW data_directory"); result.next();
            check(result.getString(1).equals(scratch.resolve("data").toString()), "Unexpected database target");
            statement.execute("CREATE DATABASE settings_upgrade");
        }
        String upgrade = url.replace("/postgres", "/settings_upgrade");
        Flyway.configure().dataSource(upgrade, USER, "").target("29").load().migrate();
        try (var connection = DriverManager.getConnection(upgrade, USER, ""); var statement = connection.createStatement()) {
            statement.execute("INSERT INTO brokers(broker_name,country,is_active) VALUES ('SYNTH-UPGRADE','US',true)");
        }
        Flyway.configure().dataSource(upgrade, USER, "").load().migrate();
        try (var connection = DriverManager.getConnection(upgrade, USER, ""); var statement = connection.createStatement()) {
            var result = statement.executeQuery("SELECT count(*) FROM brokers WHERE broker_name='SYNTH-UPGRADE' AND country='US' AND is_active");
            result.next(); check(result.getInt(1) == 1, "Historical row changed during upgrade");
            result = statement.executeQuery("SELECT count(*) FROM ibkr_sync_settings"); result.next();
            check(result.getInt(1) == 0, "Upgrade created credentials");
        }
        System.out.println("PASS historical V29 -> V30 upgrade preserves synthetic data");

        // Isolated config location prevents loading application-local/dev and their true ledger connections.
        Path config = scratch.resolve("isolated.properties");
        Files.writeString(config, "spring.datasource.url=" + url + "\nspring.datasource.username=" + USER + "\n"
                + "spring.datasource.password=\nspring.jpa.hibernate.ddl-auto=validate\nspring.jpa.show-sql=false\n"
                + "server.address=127.0.0.1\nserver.port=0\nlogging.level.root=WARN\n"
                + "broker.ibkr.secret-key-file=" + scratch.resolve("keys/ibkr.key") + "\n"
                + "broker.ibkr.base-url=http://127.0.0.1:" + Integer.parseInt(args[2]) + "\n");
        var app = new SpringApplication(Application.class);
        try (var context = (ServletWebServerApplicationContext) app.run("--spring.config.location=" + config.toUri(),
                "--spring.profiles.active=isolated-settings-check")) {
            var service = context.getBean(IbkrSyncSettingsService.class);
            var properties = context.getBean(IbkrFlexQueryProperties.class);
            var brokers = context.getBean(BrokerService.class);
            var tx = new TransactionTemplate(context.getBean(PlatformTransactionManager.class));
            Broker broker = brokers.create(new Broker("SYNTH-SETTINGS", "US"));
            long id = broker.getId();
            check(!service.get().ready(), "Empty database should not be configured");
            check(!Files.exists(scratch.resolve("keys/ibkr.key")), "Reading created a key");
            try {
                tx.execute(status -> { service.save(input(id, "synthetic-rollback", "111")); throw new IllegalStateException("synthetic rollback"); });
            } catch (IllegalStateException expected) { }
            check(!properties.isConfigured(), "Rolled back credentials became visible");
            check(brokers.findById(id).orElseThrow().getBrokerCode() == null, "Rolled back binding persisted");
            service.save(input(id, "synthetic-A", "111"));
            check(properties.credentials().equals(new IbkrFlexQueryProperties.Credentials("synthetic-A", "111")), "Committed credentials not visible");
            // Real transaction lock: second save must wait, then win without any stale callback.
            var entered = new CountDownLatch(1); var release = new CountDownLatch(1);
            var pool = Executors.newFixedThreadPool(2);
            try {
                Future<?> first = pool.submit(() -> tx.execute(status -> {
                    service.save(input(id, "synthetic-B", "222")); entered.countDown();
                    try { if (!release.await(10, TimeUnit.SECONDS)) throw new AssertionError("Release timeout"); }
                    catch (InterruptedException error) { throw new IllegalStateException(error); }
                    return null;
                }));
                check(entered.await(10, TimeUnit.SECONDS), "First save did not enter transaction");
                Future<?> second = pool.submit(() -> service.save(input(id, "synthetic-C", "333")));
                try { second.get(200, TimeUnit.MILLISECONDS); throw new AssertionError("Second save bypassed lock"); }
                catch (TimeoutException expected) { }
                check(properties.credentials().equals(new IbkrFlexQueryProperties.Credentials("synthetic-A", "111")), "Uncommitted pair became visible");
                release.countDown(); first.get(10, TimeUnit.SECONDS); second.get(10, TimeUnit.SECONDS);
                check(properties.credentials().equals(new IbkrFlexQueryProperties.Credentials("synthetic-C", "333")), "Last commit lost");
            } finally { release.countDown(); pool.shutdownNow(); }
            service.clear();
            check(!properties.isConfigured(), "Clear did not take effect");
            var keys = context.getBean(IbkrCredentialKeyStore.class);
            Path key = scratch.resolve("keys/ibkr.key");
            check(Files.getPosixFilePermissions(key).equals(PosixFilePermissions.fromString("rw-------")), "Key permissions");
            check(Files.getPosixFilePermissions(key.getParent()).equals(PosixFilePermissions.fromString("rwx------")), "Key directory permissions");
            Files.setPosixFilePermissions(key, PosixFilePermissions.fromString("rw-r--r--"));
            try { keys.load(false); throw new AssertionError("Unsafe key permissions accepted"); } catch (IllegalStateException expected) { }
            Files.setPosixFilePermissions(key, PosixFilePermissions.fromString("rw-------"));
            Path backup = key.resolveSibling("saved.key"); Files.move(key, backup); Files.createSymbolicLink(key, backup);
            try { keys.load(false); throw new AssertionError("Symbolic key accepted"); } catch (IllegalStateException expected) { }
            Files.delete(key); Files.move(backup, key);
            System.out.println("PASS fresh migration, transaction rollback, commit visibility, competing saves, clear and key permissions/symlinks");
            // UI checks begin from an unbound account so both binding and creation remain reviewable.
            brokers.delete(id);
        }
        long restartBroker;
        try (var context = (ServletWebServerApplicationContext) new SpringApplication(Application.class).run(
                "--spring.config.location=" + config.toUri(), "--spring.profiles.active=isolated-settings-check")) {
            var service = context.getBean(IbkrSyncSettingsService.class);
            check(!service.get().ready(), "Cleared credentials revived after restart");
            restartBroker = context.getBean(BrokerService.class).create(new Broker("SYNTH-RESTART", "US")).getId();
            service.save(input(restartBroker, "synthetic-restart", "444"));
        }
        try (var context = (ServletWebServerApplicationContext) new SpringApplication(Application.class).run(
                "--spring.config.location=" + config.toUri(), "--spring.profiles.active=isolated-settings-check")) {
            check(context.getBean(IbkrFlexQueryProperties.class).credentials().equals(
                    new IbkrFlexQueryProperties.Credentials("synthetic-restart", "444")), "Saved credentials lost after restart");
            context.getBean(IbkrSyncSettingsService.class).clear();
            context.getBean(BrokerService.class).delete(restartBroker);
            System.out.println("PASS real application restarts retain saved credentials and cleared state");
            Files.writeString(scratch.resolve("ready"), Integer.toString(context.getWebServer().getPort()));
            System.out.println("READY synthetic application (loopback only)");
            System.in.read();
        }
    }
}
