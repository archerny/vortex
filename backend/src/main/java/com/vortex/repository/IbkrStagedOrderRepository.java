package com.vortex.repository;

import com.vortex.entity.IbkrStagedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
