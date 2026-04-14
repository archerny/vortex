package com.localledger.repository;

import com.localledger.entity.BrokerSyncBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for broker sync batch operations.
 *
 * Provides query methods for listing, filtering, and retrieving
 * sync batch records from the broker_sync_batches table.
 */
@Repository
public interface BrokerSyncBatchRepository extends JpaRepository<BrokerSyncBatch, Long> {

    /**
     * Find all batches ordered by startedAt descending (most recent first).
     */
    List<BrokerSyncBatch> findAllByOrderByStartedAtDesc();

    /**
     * Find batches by broker name, ordered by startedAt descending.
     */
    List<BrokerSyncBatch> findByBrokerNameOrderByStartedAtDesc(String brokerName);

    /**
     * Find batches by status, ordered by startedAt descending.
     */
    List<BrokerSyncBatch> findByStatusOrderByStartedAtDesc(String status);

    /**
     * Find batches by broker name and status, ordered by startedAt descending.
     */
    List<BrokerSyncBatch> findByBrokerNameAndStatusOrderByStartedAtDesc(String brokerName, String status);
}
