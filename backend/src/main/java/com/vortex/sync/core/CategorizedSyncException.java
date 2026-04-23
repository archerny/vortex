package com.vortex.sync.core;

/**
 * Runtime exception carrying a {@link FailureCategory} and an optional
 * upstream {@code externalId}, used uniformly across all broker
 * adapters for both batch-level and staged-row-level failures.
 *
 * <p>Adapters throw this at known failure points (credentials missing,
 * API/network errors, unrecognised secType/currency/etc). Callers —
 * either the adapter's outer {@code catch} or an {@code ImportService}
 * — extract the formatted message via {@link #getFormattedMessage()}
 * and persist it as {@code error_message} on the batch or staged row.</p>
 *
 * <p>Framework ships a single unified type rather than per-broker
 * subclasses — the {@link FailureCategory} is sufficient for operators
 * to filter/group failures.</p>
 */
public class CategorizedSyncException extends RuntimeException {

    private final FailureCategory category;
    /** Upstream ID of the offending record (e.g. Tiger {@code id}, IBKR {@code order_id}). Null for batch-level failures. */
    private final String externalId;

    public CategorizedSyncException(FailureCategory category, String externalId, String reason) {
        this(category, externalId, reason, null);
    }

    public CategorizedSyncException(FailureCategory category, String externalId, String reason, Throwable cause) {
        super(reason, cause);
        if (category == null) {
            throw new IllegalArgumentException("category is required");
        }
        this.category = category;
        this.externalId = externalId;
    }

    public FailureCategory getCategory() {
        return category;
    }

    public String getExternalId() {
        return externalId;
    }

    /** Plain reason without category prefix or ext_id field. */
    public String getReason() {
        return getMessage();
    }

    /**
     * Structured message suitable for persisting as {@code error_message}.
     *
     * <p>Format:</p>
     * <ul>
     *   <li>With ext_id: {@code "[UNRECOGNIZED] ext_id=12345 reason: Unsupported secType/putCall: FUT/null"}</li>
     *   <li>Without ext_id: {@code "[AUTH] reason: Tiger API credentials not configured"}</li>
     * </ul>
     */
    public String getFormattedMessage() {
        return format(category, externalId, getMessage());
    }

    /**
     * Same format as {@link #getFormattedMessage()} but without needing to
     * construct an exception. Used by callers that already have the three
     * components separately (e.g. pre-filter results) or that want to
     * re-wrap a non-categorized throwable.
     */
    public static String format(FailureCategory category, String externalId, String reason) {
        if (category == null) {
            throw new IllegalArgumentException("category is required");
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append(category.prefix());
        if (externalId != null && !externalId.isEmpty()) {
            sb.append(" ext_id=").append(externalId);
        }
        sb.append(" reason: ").append(reason != null ? reason : "(no detail)");
        return sb.toString();
    }
}
