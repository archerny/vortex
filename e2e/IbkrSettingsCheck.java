import com.vortex.Application;
import com.vortex.dto.IbkrSyncSettingsUpdate;
import com.vortex.entity.Broker;
import com.vortex.entity.BrokerAccount;
import com.vortex.service.BrokerAccountService;
import com.vortex.service.BrokerService;
import com.vortex.sync.adapter.ibkr.*;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.nio.file.*;
import java.sql.DriverManager;

/** Disposable full application: never loads local/dev properties or real broker credentials. */
public class IbkrSettingsCheck {
    static final String USER = "vortex_settings_check";
    static void check(boolean ok, String message) { if (!ok) throw new AssertionError(message); }
    static IbkrSyncSettingsUpdate input(long broker, String token, String query) {
        var result = new IbkrSyncSettingsUpdate(); result.setBrokerId(broker);
        result.setToken(token); result.setQueryId(query); return result;
    }
    static Broker ibkrAccount(String name) {
        Broker broker = new Broker(name, "US"); broker.setBrokerCode("ibkr"); return broker;
    }
    static long managedAccount(ServletWebServerApplicationContext context, long broker) {
        var account = new BrokerAccount(); account.setBrokerId(broker); account.setAccountId("SYNTH-ACCOUNT"); account.setAccountName("Synthetic IBKR account");
        return context.getBean(BrokerAccountService.class).create(account).getId();
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
        Flyway.configure().dataSource(upgrade, USER, "").target("30").load().migrate();
        Flyway.configure().dataSource(upgrade, USER, "").load().migrate();
        try (var connection = DriverManager.getConnection(upgrade, USER, ""); var statement = connection.createStatement()) {
            var result = statement.executeQuery("SELECT count(*) FROM brokers WHERE broker_name='SYNTH-UPGRADE' AND country='US'");
            result.next(); check(result.getInt(1) == 1, "Historical row changed during upgrade");
            result = statement.executeQuery("SELECT count(*) FROM broker_sync_connections"); result.next();
            check(result.getInt(1) == 0, "Upgrade created unexpected credentials");
        }
        System.out.println("PASS V29/V30 -> current schema preserves synthetic account history with unconfigured credentials");

        // Isolated config location prevents loading application-local/dev and their true ledger connections.
        Path config = scratch.resolve("isolated.properties");
        Files.writeString(config, "spring.datasource.url=" + url + "\nspring.datasource.username=" + USER + "\n"
                + "spring.datasource.password=\nspring.jpa.hibernate.ddl-auto=validate\nspring.jpa.show-sql=false\n"
                + "server.address=127.0.0.1\nserver.port=0\nlogging.level.root=WARN\n"
                + "broker.ibkr.base-url=http://127.0.0.1:" + Integer.parseInt(args[2]) + "\n");
        var app = new SpringApplication(Application.class);
        try (var context = (ServletWebServerApplicationContext) app.run("--spring.config.location=" + config.toUri(),
                "--spring.profiles.active=isolated-settings-check")) {
            var service = context.getBean(IbkrSyncSettingsService.class);
            var properties = context.getBean(IbkrFlexQueryProperties.class);
            var brokers = context.getBean(BrokerService.class);
            var tx = new TransactionTemplate(context.getBean(PlatformTransactionManager.class));
            Broker broker = brokers.create(ibkrAccount("SYNTH-SETTINGS"));
            long id = broker.getId();
            long account = managedAccount(context, id);
            check(!service.get().ready(), "Empty database should not be configured");
            try {
                tx.execute(status -> { service.save(input(id, "synthetic-rollback", "111")); throw new IllegalStateException("synthetic rollback"); });
            } catch (IllegalStateException expected) { }
            check(properties.credentials(account).token() == null, "Rolled back credentials became visible");
            check(brokers.findById(id).orElseThrow().getBrokerCode().equals("ibkr"), "Connection save changed the account type");
            service.save(input(id, "synthetic-A", "111"));
            check(properties.credentials(account).equals(new IbkrFlexQueryProperties.Credentials("synthetic-A", "111")), "Committed credentials not visible");
            service.save(input(id, "synthetic-B", "222"));
            check(properties.credentials(account).equals(new IbkrFlexQueryProperties.Credentials("synthetic-B", "222")), "Replacement pair not visible");
            service.save(input(id, "", "333"));
            check(properties.credentials(account).equals(new IbkrFlexQueryProperties.Credentials("synthetic-B", "333")), "Blank input changed the stored Token");
            service.clear();
            check(properties.credentials(account).token() == null, "Clear did not take effect");
            System.out.println("PASS fresh schema, transaction rollback, committed plaintext, retained Token, replacement and clear");
            // Leave account creation to account management during manual UI checks.
            context.getBean(BrokerAccountService.class).delete(account);
            brokers.delete(id);
        }
        long restartBroker;
        try (var context = (ServletWebServerApplicationContext) new SpringApplication(Application.class).run(
                "--spring.config.location=" + config.toUri(), "--spring.profiles.active=isolated-settings-check")) {
            var service = context.getBean(IbkrSyncSettingsService.class);
            check(!service.get().ready(), "Cleared credentials revived after restart");
            restartBroker = context.getBean(BrokerService.class).create(ibkrAccount("SYNTH-RESTART")).getId();
            managedAccount(context, restartBroker);
            service.save(input(restartBroker, "synthetic-restart", "444"));
        }
        try (var context = (ServletWebServerApplicationContext) new SpringApplication(Application.class).run(
                "--spring.config.location=" + config.toUri(), "--spring.profiles.active=isolated-settings-check")) {
            check(context.getBean(IbkrFlexQueryProperties.class).credentials(context.getBean(BrokerAccountService.class).resolve(null, restartBroker).getId()).equals(
                    new IbkrFlexQueryProperties.Credentials("synthetic-restart", "444")), "Saved credentials lost after restart");
            context.getBean(IbkrSyncSettingsService.class).clear();
            context.getBean(BrokerAccountService.class).delete(context.getBean(BrokerAccountService.class).resolve(null, restartBroker).getId());
            context.getBean(BrokerService.class).delete(restartBroker);
            System.out.println("PASS real application restarts retain saved credentials and cleared state");
            Files.writeString(scratch.resolve("ready"), Integer.toString(context.getWebServer().getPort()));
            System.out.println("READY synthetic application (loopback only)");
            System.in.read();
        }
    }
}
