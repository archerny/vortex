package com.vortex.sync.adapter.tiger;

import com.vortex.entity.TigerStagedOrder;
import com.vortex.entity.TradeRecord;
import com.vortex.entity.enums.AssetType;
import com.vortex.entity.enums.Currency;
import com.vortex.entity.enums.TradeTrigger;
import com.vortex.entity.enums.TradeType;
import com.vortex.entity.enums.TriggerRefType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

/**
 * Pure (Spring-free) mapper from {@link TigerStagedOrder} to {@link TradeRecord}.
 *
 * <p>Encapsulates the Tiger-specific pre-filter rules (see
 * {@code docs/broker-sync/brokers/tiger/staging-schema.md §5.1}) and the
 * field-level conversion logic (§5.2 / §5.3 / §5.4).
 *
 * <p>This class is deliberately stateless and free of Spring dependencies so it
 * can be covered by lightweight unit tests. Persistence wiring lives in the
 * caller (the Import Worker).
 */
public class TigerTradeRecordMapper {

    public static final String BROKER_CODE = "tiger";

    private static final ZoneId TIGER_ZONE = ZoneId.of("Asia/Shanghai");

    private static final Set<String> SUPPORTED_SEC_TYPES = Set.of("STK", "OPT");
    private static final Set<String> SUPPORTED_ACTIONS = Set.of("BUY", "SELL");
    private static final Set<String> SUPPORTED_PUT_CALL = Set.of("CALL", "PUT");

    // ============ Pre-filter ============

    /**
     * Applies the seven pre-filter rules from {@code staging-schema.md §5.1}.
     *
     * @param staged           the staged order (non-null)
     * @param alreadyImported  result of
     *                         {@code tradeRecordRepository.existsByExternalBrokerAndExternalId(
     *                         "tiger", staged.getTigerId())}
     * @return a {@link FilterResult} indicating PASS / SKIPPED / FAILED
     */
    public FilterResult preFilter(TigerStagedOrder staged, boolean alreadyImported) {
        if (staged == null) {
            throw new IllegalArgumentException("staged must not be null");
        }

        // Rule 1: already imported
        if (alreadyImported) {
            return FilterResult.skipped("Already imported in trade_records (external_id="
                    + staged.getTigerId() + ")");
        }

        // Rule 2: not actually filled
        BigDecimal filledQty = parseBigDecimalOrNull(staged.getFilledQuantity());
        if (filledQty == null || filledQty.signum() <= 0) {
            return FilterResult.skipped("Not actually filled (filled_quantity <= 0)");
        }

        // Rule 3: unsupported secType
        String secType = staged.getSecType();
        if (secType == null || !SUPPORTED_SEC_TYPES.contains(secType)) {
            return FilterResult.failed("Unsupported secType: " + secType
                    + ". Only STK/OPT are supported.");
        }

        // Rule 4: fractional shares
        int scale = parseIntOrZero(staged.getQuantityScale());
        if (scale > 0) {
            BigDecimal rawQty = parseBigDecimalOrNull(staged.getQuantity());
            BigDecimal real = rawQty == null
                    ? null
                    : rawQty.movePointLeft(scale);
            return FilterResult.failed("Fractional share not supported. Raw qty="
                    + staged.getQuantity() + ", scale=" + scale
                    + ", realQty=" + (real == null ? "?" : real.toPlainString()));
        }

        // Rule 5: attrDesc non-empty (option event — mapping TBD)
        String attrDesc = staged.getAttrDesc();
        if (attrDesc != null && !attrDesc.trim().isEmpty()) {
            return FilterResult.failed("Option event attrDesc=" + attrDesc
                    + " — mapping TBD (will be supported after sample review)");
        }

        // Rule 6: OPT must have valid putCall
        if ("OPT".equals(secType)) {
            String putCall = staged.getPutCall();
            if (putCall == null || !SUPPORTED_PUT_CALL.contains(putCall)) {
                return FilterResult.failed("Option missing or invalid putCall: " + putCall);
            }
        }

        // Rule 7: action must be BUY/SELL
        String action = staged.getAction();
        if (action == null || !SUPPORTED_ACTIONS.contains(action)) {
            return FilterResult.failed("Unsupported action: " + action);
        }

        return FilterResult.pass();
    }

    // ============ Field mapping ============

    /**
     * Maps a pre-filter-passing {@link TigerStagedOrder} to a new
     * {@link TradeRecord}. Callers must ensure {@link #preFilter} returned
     * {@link FilterResult.Kind#PASS}; otherwise behavior is undefined.
     */
    public TradeRecord toTradeRecord(TigerStagedOrder staged, Long brokerId, Long batchId) {
        if (staged == null) {
            throw new IllegalArgumentException("staged must not be null");
        }
        if (brokerId == null) {
            throw new IllegalArgumentException("brokerId must not be null");
        }

        TradeRecord tr = new TradeRecord();

        // §5.2 direct mappings
        tr.setTradeDate(parseTradeTimeToLocalDate(staged.getTradeTime()));
        tr.setBrokerId(brokerId);
        tr.setCurrency(mapCurrency(staged.getCurrency()));
        tr.setTradeType(mapTradeType(staged.getAction()));

        int quantity = parseAbsQuantity(staged.getFilledQuantity());
        tr.setQuantity(quantity);

        BigDecimal price = new BigDecimal(staged.getAvgFillPrice().trim());
        tr.setPrice(price);

        // amount = qty * price * multiplier (multiplier = 1 for STK)
        AssetType assetType = mapAssetType(staged.getSecType(), staged.getPutCall());
        BigDecimal multiplier = ("OPT".equals(staged.getSecType()))
                ? parseMultiplier(staged.getMultiplier())
                : BigDecimal.ONE;
        BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity)).multiply(multiplier);
        tr.setAmount(amount);

        tr.setFee(calculateFee(staged.getCommission(), staged.getGst()));
        tr.setExternalId(staged.getTigerId());
        tr.setExternalBroker(BROKER_CODE);
        tr.setSyncBatchId(batchId);
        tr.setIsDeleted(false);
        tr.setStrategyId(null);

        // §5.3 logic mappings
        tr.setAssetType(assetType);
        tr.setSymbol(buildSymbol(staged));
        tr.setUnderlyingSymbol(safeTrim(staged.getSymbol()));

        // attrDesc is guaranteed empty after pre-filter rule 5 → MANUAL / NONE / 0
        tr.setTradeTrigger(TradeTrigger.MANUAL);
        tr.setTriggerRefType(TriggerRefType.NONE);
        tr.setTriggerRefId(0L);

        return tr;
    }

    // ============ Helpers (package-private for test visibility) ============

    /**
     * Maps raw Tiger currency code to system {@link Currency}.
     *
     * <ul>
     *   <li>{@code USD → USD}</li>
     *   <li>{@code HKD → HKD}</li>
     *   <li>{@code CNH → CNY} (offshore CNY normalized to onshore)</li>
     * </ul>
     *
     * @throws IllegalArgumentException for any unsupported value
     */
    public Currency mapCurrency(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("Unsupported currency: null");
        }
        switch (raw.trim().toUpperCase()) {
            case "USD":
                return Currency.USD;
            case "HKD":
                return Currency.HKD;
            case "CNH":
            case "CNY":
                return Currency.CNY;
            default:
                throw new IllegalArgumentException("Unsupported currency: " + raw);
        }
    }

    /**
     * Builds the system-side {@code symbol} for a staged order.
     *
     * <ul>
     *   <li>STK → {@code staged.symbol.trim()}</li>
     *   <li>OPT → {@code {underlying}-{expiry}-{C|P}{normalizedStrike}}</li>
     * </ul>
     */
    public String buildSymbol(TigerStagedOrder staged) {
        String secType = staged.getSecType();
        String underlying = safeTrim(staged.getSymbol());
        if (!"OPT".equals(secType)) {
            return underlying;
        }
        return buildOptionSymbol(underlying, staged.getExpiry(),
                staged.getPutCall(), staged.getStrike());
    }

    /**
     * Builds an option symbol string like {@code AAPL-20260130-C265}.
     *
     * @param underlying underlying symbol (e.g. {@code AAPL})
     * @param expiry     Tiger expiry, already {@code yyyyMMdd}
     * @param putCall    {@code CALL} / {@code PUT}
     * @param strike     strike price as a numeric string
     */
    public String buildOptionSymbol(String underlying, String expiry, String putCall, String strike) {
        if (underlying == null || underlying.trim().isEmpty()) {
            throw new IllegalArgumentException("underlying must not be empty");
        }
        if (expiry == null || expiry.trim().isEmpty()) {
            throw new IllegalArgumentException("expiry must not be empty");
        }
        if (strike == null || strike.trim().isEmpty()) {
            throw new IllegalArgumentException("strike must not be empty");
        }
        String pc;
        if ("CALL".equalsIgnoreCase(putCall)) {
            pc = "C";
        } else if ("PUT".equalsIgnoreCase(putCall)) {
            pc = "P";
        } else {
            throw new IllegalArgumentException("Invalid putCall: " + putCall);
        }
        String normalizedStrike = new BigDecimal(strike.trim())
                .stripTrailingZeros()
                .toPlainString();
        return underlying.trim() + "-" + expiry.trim() + "-" + pc + normalizedStrike;
    }

    AssetType mapAssetType(String secType, String putCall) {
        if ("STK".equals(secType)) {
            return AssetType.STOCK;
        }
        if ("OPT".equals(secType)) {
            if ("CALL".equalsIgnoreCase(putCall)) {
                return AssetType.OPTION_CALL;
            }
            if ("PUT".equalsIgnoreCase(putCall)) {
                return AssetType.OPTION_PUT;
            }
        }
        throw new IllegalArgumentException("Unsupported secType/putCall: " + secType + "/" + putCall);
    }

    TradeType mapTradeType(String action) {
        if ("BUY".equals(action)) {
            return TradeType.BUY;
        }
        if ("SELL".equals(action)) {
            return TradeType.SELL;
        }
        throw new IllegalArgumentException("Unsupported action: " + action);
    }

    int parseAbsQuantity(String raw) {
        BigDecimal bd = new BigDecimal(raw.trim()).abs();
        return bd.intValueExact();
    }

    BigDecimal calculateFee(String commission, String gst) {
        BigDecimal c = parseAbsOrZero(commission);
        BigDecimal g = parseAbsOrZero(gst);
        return c.add(g);
    }

    BigDecimal parseMultiplier(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            // Defensive default — options should always have a multiplier but fall
            // back to 100 rather than crashing if the feed omitted it.
            return new BigDecimal("100");
        }
        return new BigDecimal(raw.trim());
    }

    java.time.LocalDate parseTradeTimeToLocalDate(String tradeTime) {
        if (tradeTime == null || tradeTime.trim().isEmpty()) {
            throw new IllegalArgumentException("trade_time is required");
        }
        long epochMs = Long.parseLong(tradeTime.trim());
        return Instant.ofEpochMilli(epochMs).atZone(TIGER_ZONE).toLocalDate();
    }

    // ---- Low-level parse helpers ----

    private static BigDecimal parseBigDecimalOrNull(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static BigDecimal parseAbsOrZero(String raw) {
        BigDecimal v = parseBigDecimalOrNull(raw);
        return v == null ? BigDecimal.ZERO : v.abs();
    }

    private static int parseIntOrZero(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    // ============ FilterResult ============

    /**
     * Outcome of {@link TigerTradeRecordMapper#preFilter}.
     */
    public static final class FilterResult {

        public enum Kind {
            /** Pre-filter passed; caller should proceed to {@code toTradeRecord}. */
            PASS,
            /** Record should be marked SKIPPED in the staging table. */
            SKIPPED,
            /** Record should be marked FAILED in the staging table. */
            FAILED
        }

        private final Kind kind;
        private final String message;

        private FilterResult(Kind kind, String message) {
            this.kind = kind;
            this.message = message;
        }

        public static FilterResult pass() {
            return new FilterResult(Kind.PASS, null);
        }

        public static FilterResult skipped(String message) {
            return new FilterResult(Kind.SKIPPED, message);
        }

        public static FilterResult failed(String message) {
            return new FilterResult(Kind.FAILED, message);
        }

        public Kind getKind() {
            return kind;
        }

        public String getMessage() {
            return message;
        }

        public boolean isPass() {
            return kind == Kind.PASS;
        }
    }
}
