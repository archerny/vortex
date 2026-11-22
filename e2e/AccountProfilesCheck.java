import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortex.Application;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.util.Map;

/** Focused profile checks, run only through the launcher-owned temporary PostgreSQL. */
public class AccountProfilesCheck {
    static final String USER = "vortex_profile_check";
    static final HttpClient HTTP = HttpClient.newHttpClient();
    static final ObjectMapper JSON = new ObjectMapper();
    static void check(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
    static void sql(String url, String statement) throws Exception {
        try (var connection = DriverManager.getConnection(url, USER, ""); var query = connection.createStatement()) { query.execute(statement); }
    }
    static void verify(String url, String statement) throws Exception {
        try (var connection = DriverManager.getConnection(url, USER, ""); var query = connection.createStatement(); var row = query.executeQuery(statement)) {
            check(row.next() && row.getBoolean(1), "Synthetic migration assertion failed");
        }
    }
    static void migration(String url) throws Exception {
        sql(url, "CREATE DATABASE profile_upgrade");
        String upgrade = url.replace("/postgres", "/profile_upgrade");
        Flyway.configure().dataSource(upgrade, USER, "").target("33").load().migrate();
        sql(upgrade, """
            INSERT INTO brokers(id,broker_name,country,email,phone,description,is_active) VALUES
                (1,'SYNTH-PENDING','US',' pending@example.test ','+1 202 555 0101','Pending note',true),
                (2,'SYNTH-SOLE','US','sole@example.test','+1 202 555 0102','Sole note',true),
                (3,'SYNTH-MULTI','US','multi@example.test','+1 202 555 0103','Ambiguous note',true),
                (4,'SYNTH-ZERO','US','zero@example.test','+1 202 555 0104','No account note',false),
                (5,'SYNTH-NO-PROFILE','US',NULL,NULL,NULL,true),
                (6,'SYNTH-MANY-PENDING','US',NULL,NULL,'Preserve note only',true);
            INSERT INTO broker_accounts(id,broker_id,account_id,account_name,is_active,created_at,updated_at) VALUES
                (10,1,NULL,'Original pending',false,'2025-01-01','2025-01-02'),
                (11,1,'SYNTH-REAL-A','Real A',true,'2025-01-01','2025-01-02'),
                (20,2,'SYNTH-SOLE','Sole',true,'2025-01-01','2025-01-02'),
                (30,3,'SYNTH-MULTI-A','Multi A',true,'2025-01-01','2025-01-02'),
                (31,3,'SYNTH-MULTI-B','Multi B',true,'2025-01-01','2025-01-02'),
                (50,5,'SYNTH-NO-PROFILE','No profile',true,'2025-01-01','2025-01-02'),
                (60,6,NULL,'Pending A',true,'2025-01-01','2025-01-02'),
                (61,6,NULL,'Pending B',true,'2025-01-01','2025-01-02');
            SELECT setval('broker_accounts_id_seq',61,true);
            INSERT INTO broker_sync_batches(id,broker_code,broker_account_id,account_id,sync_date_from,sync_date_to,status)
                VALUES (1,'ibkr',20,'SYNTH-SOLE','2026-01-01','2026-01-02','COMPLETED');
            INSERT INTO trade_records(broker_id,broker_account_id,trade_date,asset_type,symbol,underlying_symbol,trade_type,quantity,price,amount,fee,currency,
                trade_trigger,trigger_ref_type,trigger_ref_id,sync_batch_id,is_deleted,updated_at) VALUES
                (2,20,'2026-01-01','STOCK','SYNTH-PROFILE','SYNTH-PROFILE','BUY',3,2.1234,6.37,0.123456789,'USD','MANUAL','NONE',0,1,true,'2025-01-01');
            INSERT INTO broker_sync_connections(broker_account_id,broker_code,credentials)
                VALUES (20,'ibkr','{"token":"synthetic-profile-token","queryId":"123"}');
            CREATE FUNCTION fail_profile_migration() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN RAISE EXCEPTION 'synthetic profile failure'; END $$;
            CREATE TRIGGER synthetic_profile_failure BEFORE UPDATE ON broker_accounts FOR EACH ROW EXECUTE FUNCTION fail_profile_migration();
            """);
        try { Flyway.configure().dataSource(upgrade, USER, "").target("34").load().migrate(); throw new AssertionError("Expected profile migration failure"); }
        catch (FlywayException expected) { }
        verify(upgrade, "SELECT count(*)=3 FROM information_schema.columns WHERE table_name='brokers' AND column_name IN ('email','phone','description')");
        verify(upgrade, "SELECT count(*)=0 FROM information_schema.columns WHERE table_name='broker_accounts' AND column_name IN ('email','phone','description')");
        verify(upgrade, "SELECT count(*)=8 FROM broker_accounts");
        sql(upgrade, "DROP TRIGGER synthetic_profile_failure ON broker_accounts; DROP FUNCTION fail_profile_migration()");
        Flyway.configure().dataSource(upgrade, USER, "").target("34").load().migrate();
        verify(upgrade, "SELECT count(*)=0 FROM information_schema.columns WHERE table_name='brokers' AND column_name IN ('email','phone','description')");
        verify(upgrade, "SELECT email=' pending@example.test ' AND phone='+1 202 555 0101' AND description='Pending note' AND NOT is_active FROM broker_accounts WHERE id=10");
        verify(upgrade, "SELECT email IS NULL AND description IS NULL FROM broker_accounts WHERE id=11");
        verify(upgrade, "SELECT email='sole@example.test' AND phone='+1 202 555 0102' AND description='Sole note' FROM broker_accounts WHERE id=20");
        verify(upgrade, "SELECT count(*)=8 AND bool_and(created_at='2025-01-01' AND updated_at='2025-01-02') FROM broker_accounts WHERE id<=61");
        verify(upgrade, "SELECT count(*)=11 FROM broker_accounts");
        verify(upgrade, "SELECT count(*)=1 AND bool_and(account_id IS NULL AND email='multi@example.test') FROM broker_accounts WHERE broker_id=3 AND description='Ambiguous note'");
        verify(upgrade, "SELECT bool_and(email IS NULL) FROM broker_accounts WHERE id IN (30,31)");
        verify(upgrade, "SELECT count(*)=1 AND bool_and(account_id IS NULL AND email='zero@example.test' AND NOT is_active) FROM broker_accounts WHERE broker_id=4");
        verify(upgrade, "SELECT count(*)=1 FROM broker_accounts WHERE broker_id=5");
        verify(upgrade, "SELECT count(*)=1 AND bool_and(account_id IS NULL) FROM broker_accounts WHERE broker_id=6 AND description='Preserve note only'");
        verify(upgrade, "SELECT price=2.1234 AND amount=6.37 AND fee=0.123456789 AND broker_account_id=20 AND is_deleted AND updated_at='2025-01-01' FROM trade_records");
        verify(upgrade, "SELECT broker_account_id=20 AND account_id='SYNTH-SOLE' AND status='COMPLETED' FROM broker_sync_batches WHERE id=1");
        verify(upgrade, "SELECT credentials->>'token'='synthetic-profile-token' FROM broker_sync_connections WHERE broker_account_id=20");
        System.out.println("PASS V33 to V34 exact legacy profiles, pending/sole/zero/multiple accounts, unchanged audit/ledger/connections, rollback and retry");
    }
    static JsonNode api(int port, String method, String path, Object body, int expected) throws Exception {
        var builder = HttpRequest.newBuilder(URI.create("http://127.0.0.1:"+port+"/api"+path)).header("Content-Type","application/json");
        builder.method(method, body==null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(body)));
        var response = HTTP.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        check(response.statusCode()==expected, "Unexpected synthetic HTTP status: "+method+" "+path+" "+response.statusCode());
        return JSON.readTree(response.body()).path("data");
    }
    static Map<String,String> ledgerSnapshot(String url) throws Exception {
        var snapshot = new java.util.HashMap<String,String>();
        for (String table : java.util.List.of("broker_accounts","trade_records","cash_flow_records","broker_sync_batches","broker_sync_connections")) {
            try (var connection=DriverManager.getConnection(url,USER,""); var query=connection.createStatement();
                    var row=query.executeQuery("SELECT jsonb_agg(to_jsonb(t) ORDER BY "+(table.equals("broker_sync_connections")?"broker_account_id":"id")+")::text FROM "+table+" t")) {
                row.next(); snapshot.put(table,row.getString(1));
            }
        }
        return snapshot;
    }
    static void brokerReferenceMigration(String url) throws Exception {
        String upgrade=url.replace("/postgres","/profile_upgrade");
        sql(upgrade,"UPDATE brokers SET is_active=false WHERE id=2");
        var before=ledgerSnapshot(upgrade);
        try (var blocker=DriverManager.getConnection(upgrade,USER,"")) {
            blocker.setAutoCommit(false);
            blocker.createStatement().execute("LOCK TABLE brokers IN ACCESS EXCLUSIVE MODE");
            try {
                Flyway.configure().dataSource(upgrade,USER,"").initSql("SET lock_timeout='150ms'").load().migrate();
                throw new AssertionError("Expected locked broker migration to fail");
            } catch (FlywayException expected) { }
            blocker.rollback();
        }
        verify(upgrade,"SELECT count(*)=1 FROM information_schema.columns WHERE table_name='brokers' AND column_name='is_active'");
        verify(upgrade,"SELECT count(*)=0 FROM information_schema.columns WHERE table_name='brokers' AND column_name='introduction'");
        Flyway.configure().dataSource(upgrade,USER,"").load().migrate();
        verify(upgrade,"SELECT count(*)=0 FROM information_schema.columns WHERE table_name='brokers' AND column_name='is_active'");
        verify(upgrade,"SELECT count(*)=6 AND bool_and(introduction IS NULL) FROM brokers");
        verify(upgrade,"SELECT is_active AND description='Sole note' FROM broker_accounts WHERE id=20");
        verify(upgrade,"SELECT NOT is_active FROM broker_accounts WHERE id=10");
        check(before.equals(ledgerSnapshot(upgrade)),"Broker reference migration changed account profiles, states or ledger data");
        System.out.println("PASS V34 to V35 removes broker state, adds independent introduction, preserves every account/profile/ledger row, lock rollback and retry");
    }
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]); Path data = Path.of(args[1]).toRealPath(); Path scratch=data.getParent();
        check(scratch.toString().startsWith("/tmp/vortex-account-profiles-"), "Unexpected scratch path");
        String url="jdbc:postgresql://127.0.0.1:"+port+"/postgres";
        verify(url,"SELECT current_setting('data_directory')='"+data+"'");
        migration(url);
        brokerReferenceMigration(url);
        Path config=scratch.resolve("profiles.properties");
        Files.writeString(config,"spring.datasource.url="+url+"\nspring.datasource.username="+USER+"\nspring.datasource.password=\nspring.jpa.hibernate.ddl-auto=validate\nspring.jpa.show-sql=false\nserver.address=127.0.0.1\nserver.port=0\nlogging.level.root=WARN\n");
        try (var context=(ServletWebServerApplicationContext)new SpringApplication(Application.class).run("--spring.config.location="+config.toUri(),"--spring.profiles.active=isolated-profile-check")) {
            int apiPort=context.getWebServer().getPort();
            var broker=api(apiPort,"POST","/brokers",Map.of("brokerName","SYNTH-PROFILE","brokerCode","ibkr","country","US","introduction"," Synthetic broker introduction "),201);
            check(!broker.has("email") && !broker.has("phone") && !broker.has("description"),"Broker still exposes profile fields");
            check(!broker.has("isActive") && broker.path("introduction").asText().equals("Synthetic broker introduction"),"Broker state remains or introduction missing");
            long brokerId=broker.path("id").asLong();
            api(apiPort,"PUT","/brokers/"+brokerId,Map.of("brokerName","SYNTH-PROFILE","brokerCode","ibkr","country","US"),200);
            check(api(apiPort,"GET","/brokers/"+brokerId,null,200).path("introduction").asText().equals("Synthetic broker introduction"),"Omitted introduction was erased");
            api(apiPort,"PUT","/brokers/"+brokerId,Map.of("brokerName","SYNTH-PROFILE","brokerCode","ibkr","country","US","introduction","Changed broker introduction"),200);
            check(api(apiPort,"GET","/brokers/"+brokerId,null,200).path("introduction").asText().equals("Changed broker introduction"),"Introduction update not persisted");
            check(api(apiPort,"GET","/brokers/active",null,200).equals(api(apiPort,"GET","/brokers",null,200)),"Legacy active list still filters reference data");
            var first=api(apiPort,"POST","/broker-accounts",Map.of("brokerId",brokerId,"accountId","SYNTH-A","accountName","First","isActive",true,
                "email"," first@example.test ","phone"," +1 202 555 0101 ","description"," First note "),201);
            var second=api(apiPort,"POST","/broker-accounts",Map.of("brokerId",brokerId,"accountId","SYNTH-B","accountName","Second","isActive",true,
                "email","second@example.test","phone","+1 202 555 0102","description","Second note"),201);
            long id=first.path("id").asLong();
            check(first.path("email").asText().equals("first@example.test") && first.path("phone").asText().equals("+1 202 555 0101"),"New account profile not saved or normalized");
            var original=Map.of("brokerId",brokerId,"accountId","SYNTH-A","accountName","First renamed","isActive",true);
            var retained=api(apiPort,"PUT","/broker-accounts/"+id,original,200);
            check(retained.path("email").asText().equals("first@example.test") && retained.path("description").asText().equals("First note"),"Old update erased profile");
            var nullInput = new java.util.HashMap<String,Object>(original);
            nullInput.put("email",null); nullInput.put("phone",null); nullInput.put("description",null);
            api(apiPort,"PUT","/broker-accounts/"+id,nullInput,200);
            var retainedRead=api(apiPort,"GET","/broker-accounts/"+id,null,200);
            check(retainedRead.path("email").asText().equals("first@example.test") && retainedRead.path("phone").asText().equals("+1 202 555 0101")
                && retainedRead.path("description").asText().equals("First note"),"Explicit null input erased persisted profile");
            var cleared=api(apiPort,"PUT","/broker-accounts/"+id,Map.of("brokerId",brokerId,"accountId","SYNTH-A","accountName","First","isActive",true,"email","","phone","","description",""),200);
            check(cleared.path("email").isNull() && cleared.path("phone").isNull() && cleared.path("description").isNull(),"Explicit profile clear failed");
            var clearedRead=api(apiPort,"GET","/broker-accounts/"+id,null,200);
            check(clearedRead.path("email").isNull() && clearedRead.path("phone").isNull() && clearedRead.path("description").isNull(),"Cleared profile was not persisted");
            var other=api(apiPort,"GET","/broker-accounts/"+second.path("id").asLong(),null,200);
            check(other.path("email").asText().equals("second@example.test") && other.path("phone").asText().equals("+1 202 555 0102") && other.path("description").asText().equals("Second note"),"Another account profile changed");
            api(apiPort,"PUT","/broker-accounts/"+id,Map.of("brokerId",brokerId,"accountId","SYNTH-A","accountName","First","isActive",true,"email","x".repeat(101)),400);
            var brokerRead=api(apiPort,"GET","/brokers/"+brokerId,null,200);
            check(!brokerRead.has("email") && !brokerRead.has("phone") && !brokerRead.has("description"),"Broker GET still exposes profile fields");
            check(!other.path("broker").has("email"),"Nested broker exposes profile fields");
            check(other.path("broker").path("introduction").asText().equals("Changed broker introduction") && !other.path("broker").has("isActive"),"Account nested broker not reference data");
            System.out.println("PASS fresh current Spring/JPA and HTTP account profile create/read/retain/clear/limits, same-broker isolation and broker fields removed");
            api(apiPort,"PUT","/broker-sync/connections/"+id,Map.of("brokerCode","ibkr","credentials",Map.of("token","synthetic-reference-token","queryId","123")),200);
            check(api(apiPort,"GET","/broker-sync/connections/"+id,null,200).path("ready").asBoolean(),"Enabled account did not become ready");
            var request=new com.vortex.sync.core.SyncRequest("ibkr"); request.setBrokerAccountId(id);
            context.getBean(com.vortex.sync.core.BrokerSyncService.class).prepareRequest(request);
            api(apiPort,"PUT","/broker-accounts/"+id,Map.of("brokerId",brokerId,"accountId","SYNTH-A","accountName","First","isActive",false),200);
            check(!api(apiPort,"GET","/broker-sync/connections/"+id,null,200).path("ready").asBoolean(),"Disabled account stayed ready");
            api(apiPort,"POST","/broker-sync/trigger",Map.of("brokerCode","ibkr","brokerAccountId",id),400);
            check(api(apiPort,"GET","/broker-sync/brokers",null,200).size()==1,"Disabled account hid objective broker reference data");
            check(api(apiPort,"GET","/broker-accounts/"+second.path("id").asLong(),null,200).path("isActive").asBoolean(),"Disabling one account changed another");
            api(apiPort,"PUT","/brokers/"+brokerId,Map.of("brokerName","SYNTH-PROFILE","brokerCode","ibkr","country","US","introduction",""),200);
            check(api(apiPort,"GET","/brokers/"+brokerId,null,200).path("introduction").isNull(),"Introduction clear not persisted");
            check(api(apiPort,"GET","/broker-accounts/"+second.path("id").asLong(),null,200).path("description").asText().equals("Second note"),"Broker introduction changed account notes");
            System.out.println("PASS broker introduction create/update/retain/clear, no broker state, legacy list compatibility and account-only sync readiness/validation/discovery");
        }
    }
}
