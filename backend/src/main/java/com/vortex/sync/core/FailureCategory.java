package com.vortex.sync.core;

/**
 * Broker-sync failure categories.
 *
 * <p>Used to prefix {@code error_message} on both batch-level and
 * staged-row level failures so operators can filter/group failures by
 * cause without parsing free-form text.</p>
 *
 * <p>See {@code docs/broker-sync/framework/unrecognized-data-logging.md}
 * and {@code docs/broker-sync/framework/sync-lifecycle.md § 7.1}.</p>
 */
public enum FailureCategory {

    /**
     * Credentials are missing, invalid, or expired. The upstream broker
     * refused to authenticate. Operator should rotate credentials.
     */
    AUTH,

    /**
     * Upstream API failed for transport reasons — timeout, connection
     * refused, 5xx, malformed response envelope. Typically transient.
     */
    NETWORK,

    /**
     * Upstream data cannot be classified against our domain model — a
     * new {@code secType}, an unknown currency, an unrecognised enum
     * value, or a value that fails format validation. Requires a
     * domain-model update or explicit decision to skip.
     */
    UNRECOGNIZED,

    /**
     * Our own bug — DB error, null we didn't expect, invariant
     * violation. Not the broker's fault. Operator should check logs and
     * file a defect.
     */
    INTERNAL;

    /** {@code "[AUTH]"}, {@code "[NETWORK]"}, etc. */
    public String prefix() {
        return "[" + name() + "]";
    }
}
