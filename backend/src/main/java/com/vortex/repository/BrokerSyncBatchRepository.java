package com.vortex.repository;

import com.vortex.entity.BrokerSyncBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
     * Find batches by broker code, ordered by startedAt descending.
     */
    List<BrokerSyncBatch> findByBrokerCodeOrderByStartedAtDesc(String brokerCode);

    /**
     * Find batches by status, ordered by startedAt descending.
     */
    List<BrokerSyncBatch> findByStatusOrderByStartedAtDesc(String status);

    /**
     * Find batches by broker code and status, ordered by startedAt descending.
     */
    List<BrokerSyncBatch> findByBrokerCodeAndStatusOrderByStartedAtDesc(String brokerCode, String status);

    /**
     * Find batches by status (for recovery scanning).
     */
    List<BrokerSyncBatch> findByStatus(String status);

    /**
     * Find batches whose status is in the given set. Used by
     * {@link com.vortex.sync.core.SyncBatchRecoveryRunner} at startup to scan
     * for residual active batches (PENDING + PROCESSING) left behind by a
     * crashed JVM. PENDING covers the narrow window between
     * {@code createBatch} and {@code markAsProcessing}; PROCESSING covers
     * everything after the adapter starts running.
     */
    List<BrokerSyncBatch> findByStatusIn(Collection<String> statuses);

    /**
     * Find the first batch whose status is in the given set, ordered by id
     * descending. Used by the v2 sync conflict check to surface an active
     * batch (PENDING / PROCESSING / CLEANUP_FAILED) before attempting to
     * create a new one. The DB-level guard is the partial unique index
     * {@code uk_only_one_active}; this method is the application-layer
     * fast-path that lets us return a rich {@code SyncConflictException}
     * with the conflicting batch's ID/status.
     */
    Optional<BrokerSyncBatch> findFirstByStatusInOrderByIdDesc(Collection<String> statuses);
}
