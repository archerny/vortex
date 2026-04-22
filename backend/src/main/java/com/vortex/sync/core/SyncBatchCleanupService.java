package com.vortex.sync.core;

import com.vortex.repository.TradeRecordRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Deletes every row this sync batch has written.
 *
 * <p>Used by {@link SyncBatchFailureHandler} as part of the v2 fail-fast
 * model: when a sync batch fails (at any phase, or on startup recovery), we
 * first wipe its footprint and only then mark the batch terminal. See
 * {@code docs/broker-sync/framework/import-consistency.md § 5}.</p>
 *
 * <h3>Cleanup scope</h3>
 * <ul>
 *   <li>Broker-specific staged tables: dispatched via
 *       {@link BrokerCleanupStrategy}. Each {@link BrokerSyncAdapter}
 *       provides a matching strategy bean (e.g. {@code IbkrCleanupStrategy},
 *       {@code TigerCleanupStrategy}).</li>
 *   <li>Common table (all brokers): {@code trade_records} matched by
 *       {@code sync_batch_id}.</li>
 *   <li>Also: any {@code trade_record_tags} rows pointing at deleted
 *       {@code trade_records} are cascade-deleted by the DB foreign key
 *       ({@code V4__create_tags.sql} — {@code ON DELETE CASCADE}).</li>
 * </ul>
 *
 * <h3>Transaction model</h3>
 * All DELETEs run in a single {@code @Transactional} block. If any DELETE
 * throws, Spring rolls back the whole transaction — callers therefore see
 * an all-or-nothing outcome. The batch record's own status transition is
 * handled by {@link SyncBatchFailureHandler} in a separate transaction so
 * that a status update can proceed even if a DB error occurred here.
 *
 * <h3>Adding a new broker</h3>
 * <ol>
 *   <li>Implement a new {@link BrokerSyncAdapter}.</li>
 *   <li>Add a matching {@link BrokerCleanupStrategy} {@code @Component}
 *       that knows how to wipe the new broker's staged tables.</li>
 * </ol>
 * <p>Forgetting step 2 fails the application at startup via the
 * {@link #verifyAdapterCoverage()} check — it is impossible to ship a
 * deployment where an adapter has no cleanup strategy.</p>
 */
@Service
public class SyncBatchCleanupService {

    private static final Logger logger = LoggerFactory.getLogger(SyncBatchCleanupService.class);

    private final Map<String, BrokerCleanupStrategy> strategiesByBroker;
    private final Set<String> registeredAdapterBrokerCodes;
    private final TradeRecordRepository tradeRecordRepository;

    public SyncBatchCleanupService(List<BrokerCleanupStrategy> strategies,
                                   List<BrokerSyncAdapter> adapters,
                                   TradeRecordRepository tradeRecordRepository) {
        // Guard against two strategies claiming the same broker code.
        this.strategiesByBroker = strategies.stream()
                .collect(Collectors.toUnmodifiableMap(
                        BrokerCleanupStrategy::brokerCode,
                        s -> s,
                        (a, b) -> {
                            throw new IllegalStateException(
                                    "Multiple BrokerCleanupStrategy beans declared for broker '"
                                            + a.brokerCode() + "': "
                                            + a.getClass().getName() + " and "
                                            + b.getClass().getName());
                        }));
        this.registeredAdapterBrokerCodes = adapters.stream()
                .map(BrokerSyncAdapter::getBrokerCode)
                .collect(Collectors.toUnmodifiableSet());
        this.tradeRecordRepository = tradeRecordRepository;
    }

    /**
     * Fail-fast at startup if any registered broker adapter has no matching
     * cleanup strategy. This turns "forgot to register cleanup for a new
     * broker" from a latent runtime disaster (CLEANUP_FAILED → permanent
     * {@code uk_only_one_active} lock) into an obvious boot-time error.
     */
    @PostConstruct
    void verifyAdapterCoverage() {
        Set<String> missing = new TreeSet<>(registeredAdapterBrokerCodes);
        missing.removeAll(strategiesByBroker.keySet());
        if (!missing.isEmpty()) {
            throw new IllegalStateException(
                    "BrokerSyncAdapter(s) have no matching BrokerCleanupStrategy: " + missing
                            + ". Each adapter must be paired with a cleanup strategy bean. "
                            + "Registered strategies: " + new TreeSet<>(strategiesByBroker.keySet()));
        }
        logger.info("[CleanupService] Broker cleanup coverage OK: adapters={}, strategies={}",
                new TreeSet<>(registeredAdapterBrokerCodes),
                new TreeSet<>(strategiesByBroker.keySet()));
    }

    /**
     * Delete everything the given batch has written.
     *
     * @param batchId     the failing batch's ID
     * @param brokerCode  the batch's broker code (used to look up the
     *                    per-broker cleanup strategy)
     * @throws IllegalArgumentException if arguments are invalid or the
     *         broker code has no registered cleanup strategy
     */
    @Transactional
    public void cleanupBatchData(Long batchId, String brokerCode) {
        if (batchId == null) {
            throw new IllegalArgumentException("batchId is required");
        }
        if (brokerCode == null || brokerCode.isBlank()) {
            throw new IllegalArgumentException("brokerCode is required");
        }

        BrokerCleanupStrategy strategy = strategiesByBroker.get(brokerCode);
        if (strategy == null) {
            // Should be unreachable once @PostConstruct has validated, but
            // keep a precise failure mode for the "runtime passes an unknown
            // broker code" case (e.g. a malformed batch row).
            throw new IllegalArgumentException("Unknown brokerCode for cleanup: " + brokerCode
                    + ". Registered: " + Collections.unmodifiableSet(new HashSet<>(strategiesByBroker.keySet())));
        }

        strategy.deleteStagedRows(batchId);
        int tradeRecordsDeleted = tradeRecordRepository.deleteBySyncBatchId(batchId);

        logger.info("Cleanup complete for batch {} ({}): tradeRecords={}",
                batchId, brokerCode, tradeRecordsDeleted);
    }
}
