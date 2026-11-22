package com.vortex.sync.adapter.ibkr;

import com.vortex.dto.BrokerSyncConnectionUpdate;
import com.vortex.dto.BrokerSyncConnectionView;
import com.vortex.dto.IbkrSyncSettingsUpdate;
import com.vortex.dto.IbkrSyncSettingsView;
import com.vortex.service.BrokerSyncConnectionService;
import org.springframework.stereotype.Service;
import java.util.HashMap;

/** Existing IBKR endpoint backed by the account connection service. */
@Service
public class IbkrSyncSettingsService {
    private final BrokerSyncConnectionService connections;

    public IbkrSyncSettingsService(BrokerSyncConnectionService connections) {
        this.connections = connections;
    }

    public IbkrSyncSettingsView get() {
        return connections.getForBrokerCode("ibkr").map(this::view)
                .orElseGet(() -> new IbkrSyncSettingsView(null, null, null, false, false));
    }

    public IbkrSyncSettingsView save(IbkrSyncSettingsUpdate input) {
        var credentials = new HashMap<String, String>();
        credentials.put("queryId", input.getQueryId());
        credentials.put("token", input.getToken());
        var selected = connections.getForBrokerCode("ibkr").orElseThrow(() -> new IllegalArgumentException("请先录入IBKR托管账户"));
        if (!java.util.Objects.equals(input.getBrokerId(), selected.brokerId())) throw new IllegalArgumentException("所选券商不匹配");
        return view(connections.save(selected.brokerAccountId(), new BrokerSyncConnectionUpdate("ibkr", credentials)));
    }

    public IbkrSyncSettingsView clear() {
        return connections.getForBrokerCode("ibkr").map(value -> view(connections.clear(value.brokerAccountId())))
                .orElseGet(() -> new IbkrSyncSettingsView(null, null, null, false, false));
    }

    private IbkrSyncSettingsView view(BrokerSyncConnectionView value) {
        return new IbkrSyncSettingsView(value.brokerId(), value.brokerName(), value.values().get("queryId"),
                value.configuredSecrets().contains("token"), value.ready());
    }
}
