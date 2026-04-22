package com.vortex.repository;

import com.vortex.entity.TigerStagedOrder;
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
     * Count staged orders in a batch whose status is <b>not</b> in the given
     * set. Used by {@link com.vortex.sync.adapter.tiger.TigerSyncAdapter} to
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
    @Query("SELECT s.id FROM TigerStagedOrder s " +
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

