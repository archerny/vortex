package com.vortex.repository;

import com.vortex.entity.BrokerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BrokerAccountRepository extends JpaRepository<BrokerAccount, Long> {
    List<BrokerAccount> findAllByOrderByIdAsc();
    List<BrokerAccount> findByBrokerIdOrderByIdAsc(Long brokerId);
    Optional<BrokerAccount> findByBrokerIdAndAccountId(Long brokerId, String accountId);
}
