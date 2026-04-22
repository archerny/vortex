package com.vortex.repository;

import com.vortex.entity.IbkrStagedTradeConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for IBKR staged trade confirm operations.
 *
 * Provides query methods for the ibkr_staged_trade_confirms table,
 * supporting batch-based queries and order-based correlation lookups.
 * This table is used for audit and reconciliation purposes.
 */
@Repository
public interface IbkrStagedTradeConfirmRepository extends JpaRepository<IbkrStagedTradeConfirm, Long> {

    /**
     * Find all staged trade confirms by batch ID.
     */
    List<IbkrStagedTradeConfirm> findByBatchId(Long batchId);

    /**
     * Find staged trade confirms by order ID (correlate with ibkr_staged_orders).
     */
    List<IbkrStagedTradeConfirm> findByOrderId(String orderId);

    /**
     * Find a staged trade confirm by IBKR trade ID (globally unique).
     */
    Optional<IbkrStagedTradeConfirm> findByTradeId(String tradeId);

    /**
     * Check if a staged trade confirm exists for the given IBKR trade ID.
     */
    boolean existsByTradeId(String tradeId);

    /**
     * Delete all staged trade confirms for the given batch. Used by
     * {@code SyncBatchCleanupService} when rolling back a failed sync.
     *
     * @return number of rows deleted
     */
    @Modifying
    long deleteByBatchId(Long batchId);
}
