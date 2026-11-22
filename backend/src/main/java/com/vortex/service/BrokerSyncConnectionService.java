package com.vortex.service;

import com.vortex.dto.BrokerSyncConnectionUpdate;
import com.vortex.dto.BrokerSyncConnectionView;
import com.vortex.entity.Broker;
import com.vortex.entity.BrokerAccount;
import com.vortex.repository.BrokerAccountRepository;
import com.vortex.entity.BrokerSyncConnection;
import com.vortex.repository.BrokerRepository;
import com.vortex.repository.BrokerSyncConnectionRepository;
import com.vortex.sync.adapter.ibkr.IbkrFlexQueryProperties;
import com.vortex.sync.adapter.tiger.TigerApiProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BrokerSyncConnectionService {
    private static final Map<String, List<String>> FIELDS = Map.of(
            "ibkr", List.of("token", "queryId"), "tiger", List.of("tigerId", "privateKey"));
    private static final Set<String> SECRETS = Set.of("token", "privateKey");
    private final BrokerRepository brokers;
    private final BrokerAccountRepository accounts;
    private final BrokerSyncConnectionRepository connections;
    private final IbkrFlexQueryProperties ibkr;
    private final TigerApiProperties tiger;

    public BrokerSyncConnectionService(BrokerRepository brokers, BrokerAccountRepository accounts, BrokerSyncConnectionRepository connections,
            IbkrFlexQueryProperties ibkr, TigerApiProperties tiger) {
        this.brokers = brokers; this.accounts = accounts; this.connections = connections; this.ibkr = ibkr; this.tiger = tiger;
    }

    @PostConstruct
    public void useDatabaseCredentials() {
        ibkr.useAccountCredentials(id -> {
            var value = credentialsFor(id, "ibkr");
            return new IbkrFlexQueryProperties.Credentials(value.get("token"), value.get("queryId"));
        });
        tiger.useAccountCredentials(id -> {
            var value = credentialsFor(id, "tiger");
            String identity = accounts.findById(id).map(BrokerAccount::getAccountId).orElse(null);
            return new TigerApiProperties.Credentials(value.get("tigerId"), value.get("privateKey"), identity);
        });
    }

    private Map<String, String> credentialsFor(Long id, String code) {
        if (id == null) return Map.of();
        var account = accounts.findById(id).orElse(null);
        if (account == null || !code.equals(requireBroker(account).getBrokerCode())) return Map.of();
        return connections.findById(id).filter(connection -> code.equals(connection.getBrokerCode()))
                .map(BrokerSyncConnection::getCredentials).orElseGet(Map::of);
    }

    @Transactional(readOnly = true)
    public List<BrokerSyncConnectionView> list() {
        var stored = connections.findAll().stream().collect(Collectors.toMap(BrokerSyncConnection::getBrokerAccountId, value -> value));
        return accounts.findAllByOrderByIdAsc().stream().map(account -> view(account, stored.get(account.getId()))).toList();
    }

    @Transactional(readOnly = true)
    public BrokerSyncConnectionView get(Long id) {
        return view(requireAccount(id), connections.findById(id).orElse(null));
    }

    /** Legacy broker-wide API cannot choose between multiple managed accounts. */
    @Transactional(readOnly = true)
    public Optional<BrokerSyncConnectionView> getForBrokerCode(String code) {
        var broker = brokers.findByBrokerCode(code).orElse(null);
        if (broker == null) return Optional.empty();
        var values = accounts.findByBrokerIdOrderByIdAsc(broker.getId());
        if (values.size() > 1) throw new IllegalArgumentException("该券商存在多个账户，请使用账户连接配置");
        return values.isEmpty() ? Optional.empty() : Optional.of(get(values.get(0).getId()));
    }

    @Transactional
    public BrokerSyncConnectionView save(Long brokerAccountId, BrokerSyncConnectionUpdate input) {
        BrokerAccount account = requireAccount(brokerAccountId);
        Broker broker = requireBroker(account);
        String code = broker.getBrokerCode();
        if (!FIELDS.containsKey(code == null ? "" : code)) throw new IllegalArgumentException("请先在账户管理中选择支持同步的券商");
        if (!Objects.equals(code, input.brokerCode())) throw new IllegalArgumentException("账户券商已变更，请刷新后重新配置");
        if (input.credentials() == null) throw new IllegalArgumentException("请填写同步连接信息");
        BrokerSyncConnection row = connections.findById(brokerAccountId).orElseGet(BrokerSyncConnection::new);
        Map<String, String> previous = code.equals(row.getBrokerCode()) ? row.getCredentials() : Map.of();
        Map<String, String> values = new HashMap<>();
        for (String field : FIELDS.get(code)) {
            String value = input.credentials().get(field);
            value = value == null ? "" : value.trim();
            if (SECRETS.contains(field) && value.isEmpty()) value = previous.getOrDefault(field, "");
            if (value.isBlank()) throw new IllegalArgumentException("请填写完整连接信息，首次配置须提供Token或私钥");
            if (value.length() > (field.equals("privateKey") ? 65536 : 4096)) throw new IllegalArgumentException("连接字段长度超出限制");
            values.put(field, value);
        }
        if (code.equals("ibkr") && (!values.get("queryId").matches("[0-9]{1,64}")
                || values.get("token").chars().anyMatch(Character::isWhitespace))) {
            throw new IllegalArgumentException("请输入有效Flex Token和数字Query ID");
        }
        row.setBrokerAccountId(brokerAccountId); row.setBrokerCode(code); row.setCredentials(values);
        connections.saveAndFlush(row);
        return view(account, row);
    }

    @Transactional
    public BrokerSyncConnectionView clear(Long id) {
        BrokerAccount account = requireAccount(id);
        connections.deleteById(id);
        return view(account, null);
    }

    private BrokerAccount requireAccount(Long id) {
        if (id == null) throw new IllegalArgumentException("请选择已有托管账户");
        return accounts.findById(id).orElseThrow(() -> new IllegalArgumentException("托管账户不存在，请到账户管理录入"));
    }

    private Broker requireBroker(BrokerAccount account) {
        return brokers.findById(account.getBrokerId()).orElseThrow(() -> new IllegalArgumentException("账户所属券商不存在"));
    }

    private BrokerSyncConnectionView view(BrokerAccount account, BrokerSyncConnection connection) {
        Broker broker = requireBroker(account);
        List<String> fields = FIELDS.getOrDefault(broker.getBrokerCode() == null ? "" : broker.getBrokerCode(), List.of());
        Map<String, String> credentials = connection != null && Objects.equals(broker.getBrokerCode(), connection.getBrokerCode())
                ? connection.getCredentials() : Map.of();
        Map<String, String> visible = new HashMap<>();
        Set<String> configuredSecrets = new HashSet<>();
        for (String field : fields) {
            String value = credentials.getOrDefault(field, "");
            if (SECRETS.contains(field)) {
                if (!value.isBlank()) configuredSecrets.add(field);
            } else visible.put(field, value);
        }
        boolean configured = !fields.isEmpty() && fields.stream().allMatch(field -> !credentials.getOrDefault(field, "").isBlank());
        boolean active = Boolean.TRUE.equals(account.getIsActive());
        boolean identified = account.getAccountId() != null && !account.getAccountId().isBlank();
        return new BrokerSyncConnectionView(broker.getId(), broker.getBrokerName(), broker.getBrokerCode(), active,
                !fields.isEmpty(), configured, active && identified && configured, Map.copyOf(visible), Set.copyOf(configuredSecrets),
                account.getId(), account.getAccountId(), account.getAccountName());
    }
}
