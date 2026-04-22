package com.vortex.repository;

import com.vortex.entity.TigerStagedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Tiger staged order operations.
 *
 * Provides query methods for the tiger_staged_orders staging table,
 * supporting batch-based queries, status filtering, and order deduplication
 * by Tiger global unique order ID ({@code tiger_id}).
 */
@Repository
public interface TigerStagedOrderRepository extends JpaRepository<TigerStagedOrder, Long> {

    /**
     * Find all staged orders by batch ID.
     */
    List<TigerStagedOrder> findByBatchId(Long batchId);

    /**
     * Find staged orders by batch ID and status.
     */
    List<TigerStagedOrder> findByBatchIdAndStatus(Long batchId, String status);

    /**
     * Find a staged order by Tiger global unique order ID (for deduplication).
     */
    Optional<TigerStagedOrder> findByTigerId(String tigerId);

    /**
     * Check if a staged order exists for the given Tiger global unique order ID.
     */
    boolean existsByTigerId(String tigerId);

    /**
     * Count staged orders by batch ID and status.
     */
    long countByBatchIdAndStatus(Long batchId, String status);

    /**
     * Delete all staged orders for the given batch. Used by
     * {@code SyncBatchCleanupService} when rolling back a failed sync.
     *
     * @return number of rows deleted
     */
    @Modifying
    long deleteByBatchId(Long batchId);
}
