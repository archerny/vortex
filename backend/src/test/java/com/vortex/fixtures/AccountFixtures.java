package com.vortex.fixtures;

import com.vortex.entity.BrokerAccount;
import com.vortex.entity.BrokerSyncBatch;
import com.vortex.repository.BrokerSyncBatchRepository;
import com.vortex.service.BrokerAccountService;
import com.vortex.sync.core.SyncRequest;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Shared single-account fixtures for existing business-rule tests. */
public final class AccountFixtures {
    private AccountFixtures() { }
    public static com.vortex.repository.BrokerAccountRepository accounts() {
        var accounts = mock(com.vortex.repository.BrokerAccountRepository.class);
        var account = new BrokerAccount(); account.setId(1L); account.setBrokerId(1L);
        account.setAccountId("SYNTH-ACCOUNT"); account.setAccountName("Synthetic account");
        lenient().when(accounts.findById(1L)).thenReturn(Optional.of(account));
        return accounts;
    }
    public static BrokerSyncBatchRepository batches() {
        var repository = mock(BrokerSyncBatchRepository.class);
        lenient().when(repository.findById(anyLong())).thenAnswer(call -> {
            var batch = new BrokerSyncBatch(); batch.setId(call.getArgument(0)); batch.setBrokerAccountId(1L);
            return Optional.of(batch);
        });
        return repository;
    }
    public static BrokerAccountService service(String identity) {
        var service = mock(BrokerAccountService.class);
        lenient().when(service.resolve(any(), any())).thenAnswer(call -> {
            var account = new BrokerAccount(); account.setId(1L); account.setBrokerId(call.getArgument(1));
            account.setAccountId(identity); account.setAccountName("Synthetic account");
            return account;
        });
        return service;
    }
    public static SyncRequest request(String identity, String... values) {
        SyncRequest request = new SyncRequest(values.length == 0 ? "ibkr" : values[0]);
        if (values.length > 1) request.setStartTime(values[1]);
        if (values.length > 2) request.setEndTime(values[2]);
        request.setBrokerAccountId(1L); request.setAccountId(identity);
        return request;
    }
}
