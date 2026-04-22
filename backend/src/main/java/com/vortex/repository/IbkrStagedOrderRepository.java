package com.vortex.repository;

import com.vortex.entity.IbkrStagedOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository for IBKR staged order operations.
 *
 * Provides query methods for the ibkr_staged_orders staging table,
 * supporting batch-based queries, status filtering, and order deduplication.
 */
@Repository
public interface IbkrStagedOrderRepository extends JpaRepository<IbkrStagedOrder, Long> {

    /**
     * Find all staged orders by batch ID.
     */
    List<IbkrStagedOrder> findByBatchId(Long batchId);

    /**
     * Find staged orders by batch ID and status.
     */
    List<IbkrStagedOrder> findByBatchIdAndStatus(Long batchId, String status);

    /**
     * Find a staged order by IBKR order ID (for deduplication).
     */
    Optional<IbkrStagedOrder> findByOrderId(String orderId);

    /**
     * Check if a staged order exists for the given IBKR order ID.
     */
    boolean existsByOrderId(String orderId);

    /**
     * Count staged orders by batch ID and status.
     */
    long countByBatchIdAndStatus(Long batchId, String status);

    /**
     * Count staged orders in a batch whose status is <b>not</b> in the given
     * set. Used by {@link com.vortex.sync.adapter.ibkr.IbkrSyncAdapter} to
     * detect non-terminal residue (typically PENDING) left behind after the
     * import loop — see P0-2 fix in {@code fix-p0-data-loss-chain.md}.
     */
    long countByBatchIdAndStatusNotIn(Long batchId, Collection<String> statuses);

    /**
     * Fetch up to {@link Pageable#getPageSize()} staged-row ids in a batch
     * whose status is <b>not</b> in the given set. Used to enrich the
     * adapter-level residual WARN log with a bounded sample of offending
     * ids, so operators have breadcrumbs for debugging.
     */
    @Query("SELECT s.id FROM IbkrStagedOrder s " +
            "WHERE s.batchId = :batchId AND s.status NOT IN :statuses " +
            "ORDER BY s.id ASC")
    List<Long> findIdsByBatchIdAndStatusNotIn(@Param("batchId") Long batchId,
                                              @Param("statuses") Collection<String> statuses,
                                              Pageable pageable);

    /**
     * Delete all staged orders for the given batch. Used by
     * {@code SyncBatchCleanupService} when rolling back a failed sync.
     *
     * @return number of rows deleted
     */
    @Modifying
    long deleteByBatchId(Long batchId);
}

