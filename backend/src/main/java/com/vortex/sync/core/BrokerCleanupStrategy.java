package com.vortex.sync.core;

/**
 * Broker-specific cleanup strategy.
 *
 * <p>Each {@link BrokerSyncAdapter} implementation MUST contribute exactly
 * one {@code BrokerCleanupStrategy} bean that knows how to wipe the
 * broker-private staged tables for a given batch. {@link SyncBatchCleanupService}
 * composes these strategies (keyed by {@link #brokerCode()}) and calls the
 * matching one during fail-fast cleanup.</p>
 *
 * <p>Why a strategy per broker (instead of a switch in the cleanup service):
 * adding a new broker is a common extension path; forgetting to register a
 * cleanup case in a central switch used to silently downgrade to
 * {@code IllegalStateException → CLEANUP_FAILED}, permanently locking the
 * {@code uk_only_one_active} constraint until a DBA intervenes. With this
 * interface, {@link SyncBatchCleanupService} performs a {@code @PostConstruct}
 * coverage check: if any registered adapter lacks a matching strategy, the
 * application fails to start — you cannot ship a misconfigured deployment.</p>
 *
 * <h3>Contract</h3>
 * <ul>
 *   <li>{@link #deleteStagedRows(Long)} is always invoked from inside
 *       {@code SyncBatchCleanupService.cleanupBatchData}, which is itself
 *       wrapped in {@code @Transactional}. Implementations should not
 *       manage their own transactions.</li>
 *   <li>The implementation is responsible <strong>only</strong> for the
 *       broker-private staged tables (e.g. {@code tiger_staged_orders},
 *       {@code ibkr_staged_orders}, {@code ibkr_staged_trade_confirms}).
 *       The common {@code trade_records} table is always cleaned by the
 *       composing service.</li>
 *   <li>Any exception propagates to {@link SyncBatchCleanupService}, which
 *       in turn propagates to {@link SyncBatchFailureHandler} for retry.</li>
 * </ul>
 */
public interface BrokerCleanupStrategy {

    /**
     * Broker code this strategy handles, matching
     * {@link BrokerSyncAdapter#getBrokerCode()} (e.g. {@code "ibkr"},
     * {@code "tiger"}).
     */
    String brokerCode();

    /**
     * Delete every row this broker has staged for the given batch.
     *
     * <p>Called inside the caller's transaction; do not start a new one.</p>
     *
     * @param batchId the failing batch's ID (never null)
     */
    void deleteStagedRows(Long batchId);
}
