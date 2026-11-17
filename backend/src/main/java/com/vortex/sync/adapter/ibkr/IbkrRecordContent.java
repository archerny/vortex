package com.vortex.sync.adapter.ibkr;

import com.vortex.entity.IbkrStagedOrder;
import com.vortex.entity.IbkrStagedTradeConfirm;
import com.vortex.entity.TradeRecord;
import com.vortex.sync.core.CategorizedSyncException;
import com.vortex.sync.core.FailureCategory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** Explicit source-content comparisons; management and derived fields are excluded. */
final class IbkrRecordContent {
    private static final Set<String> NUMERIC_FIELDS = Set.of(
            "multiplier", "strike", "quantity", "price", "amount", "proceeds", "netCash",
            "commission", "tradeCharge", "fee");

    private IbkrRecordContent() {}

    static void requireId(String field, String id) {
        if (text(id) == null) {
            throw new CategorizedSyncException(FailureCategory.UNRECOGNIZED, null,
                    "IBKR " + field + " is required for duplicate content validation");
        }
        if (!id.equals(id.strip())) {
            throw new CategorizedSyncException(FailureCategory.UNRECOGNIZED, null,
                    "IBKR " + field + " must not contain surrounding whitespace");
        }
    }

    static void requireSameOrder(IbkrStagedOrder stored, IbkrStagedOrder incoming) {
        requireSame("order", stored.getId(), orderFields(stored), orderFields(incoming));
    }

    static void requireSameConfirm(IbkrStagedTradeConfirm stored, IbkrStagedTradeConfirm incoming) {
        requireSame("trade-confirm", stored.getId(), confirmFields(stored), confirmFields(incoming));
    }

    static void requireSameTrade(TradeRecord stored, TradeRecord incoming) {
        requireSame("trade-record", stored.getId(), tradeFields(stored), tradeFields(incoming));
    }

    private static void requireSame(String type, Long storedId,
                                    Map<String, String> stored, Map<String, String> incoming) {
        var differences = stored.keySet().stream()
                .filter(field -> !same(field, stored.get(field), incoming.get(field))).toList();
        if (!differences.isEmpty()) {
            // Use an internal row reference, never source/account values, in diagnostics.
            throw new CategorizedSyncException(FailureCategory.UNRECOGNIZED,
                    "stored-" + type + "-row:" + storedId,
                    "IBKR duplicate " + type + " content conflict; differing fields: "
                            + String.join(", ", differences) + ". Existing record was not changed.");
        }
    }

    private static boolean same(String field, String left, String right) {
        left = text(left);
        right = text(right);
        if (Objects.equals(left, right)) return true;
        if (field.equals("code")) return codes(left).equals(codes(right));
        if (left == null || right == null) return false;
        if (NUMERIC_FIELDS.contains(field)) {
            try {
                return new BigDecimal(left).compareTo(new BigDecimal(right)) == 0;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
        return false;
    }

    private static String text(String value) {
        return value == null || value.isBlank() ? null : value.strip();
    }

    private static Set<String> codes(String value) {
        if (value == null) return Set.of();
        return Arrays.stream(value.split(";")).map(String::trim)
                .filter(token -> !token.isEmpty()).collect(Collectors.toSet());
    }

    private static Map<String, String> orderFields(IbkrStagedOrder row) {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("accountId", row.getAccountId());
        fields.put("acctAlias", row.getAcctAlias());
        fields.put("currency", row.getCurrency());
        fields.put("assetCategory", row.getAssetCategory());
        fields.put("symbol", row.getSymbol());
        fields.put("description", row.getDescription());
        fields.put("conid", row.getConid());
        fields.put("securityId", row.getSecurityId());
        fields.put("securityIdType", row.getSecurityIdType());
        fields.put("multiplier", row.getMultiplier());
        fields.put("strike", row.getStrike());
        fields.put("expiry", row.getExpiry());
        fields.put("putCall", row.getPutCall());
        fields.put("orderId", row.getOrderId());
        fields.put("orderTime", row.getOrderTime());
        fields.put("dateTime", row.getDateTime());
        fields.put("settleDate", row.getSettleDate());
        fields.put("tradeDate", row.getTradeDate());
        fields.put("buySell", row.getBuySell());
        fields.put("orderType", row.getOrderType());
        fields.put("isApiOrder", row.getIsApiOrder());
        fields.put("quantity", row.getQuantity());
        fields.put("price", row.getPrice());
        fields.put("amount", row.getAmount());
        fields.put("proceeds", row.getProceeds());
        fields.put("netCash", row.getNetCash());
        fields.put("commission", row.getCommission());
        fields.put("commissionCurrency", row.getCommissionCurrency());
        fields.put("tradeCharge", row.getTradeCharge());
        fields.put("traderId", row.getTraderId());
        return fields;
    }

    private static Map<String, String> confirmFields(IbkrStagedTradeConfirm row) {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("accountId", row.getAccountId());
        fields.put("acctAlias", row.getAcctAlias());
        fields.put("currency", row.getCurrency());
        fields.put("assetCategory", row.getAssetCategory());
        fields.put("symbol", row.getSymbol());
        fields.put("description", row.getDescription());
        fields.put("conid", row.getConid());
        fields.put("securityId", row.getSecurityId());
        fields.put("securityIdType", row.getSecurityIdType());
        fields.put("multiplier", row.getMultiplier());
        fields.put("strike", row.getStrike());
        fields.put("expiry", row.getExpiry());
        fields.put("putCall", row.getPutCall());
        fields.put("transactionType", row.getTransactionType());
        fields.put("tradeId", row.getTradeId());
        fields.put("orderId", row.getOrderId());
        fields.put("execId", row.getExecId());
        fields.put("brokerageOrderId", row.getBrokerageOrderId());
        fields.put("orderReference", row.getOrderReference());
        fields.put("orderTime", row.getOrderTime());
        fields.put("dateTime", row.getDateTime());
        fields.put("settleDate", row.getSettleDate());
        fields.put("tradeDate", row.getTradeDate());
        fields.put("exchange", row.getExchange());
        fields.put("buySell", row.getBuySell());
        fields.put("quantity", row.getQuantity());
        fields.put("price", row.getPrice());
        fields.put("amount", row.getAmount());
        fields.put("proceeds", row.getProceeds());
        fields.put("netCash", row.getNetCash());
        fields.put("commission", row.getCommission());
        fields.put("commissionCurrency", row.getCommissionCurrency());
        fields.put("tradeCharge", row.getTradeCharge());
        fields.put("code", row.getCode());
        fields.put("orderType", row.getOrderType());
        fields.put("traderId", row.getTraderId());
        fields.put("isApiOrder", row.getIsApiOrder());
        return fields;
    }

    private static Map<String, String> tradeFields(TradeRecord row) {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("tradeDate", Objects.toString(row.getTradeDate(), null));
        fields.put("brokerId", Objects.toString(row.getBrokerId(), null));
        fields.put("assetType", Objects.toString(row.getAssetType(), null));
        fields.put("symbol", row.getSymbol());
        fields.put("underlyingSymbol", row.getUnderlyingSymbol());
        fields.put("tradeType", Objects.toString(row.getTradeType(), null));
        fields.put("quantity", Objects.toString(row.getQuantity(), null));
        fields.put("price", Objects.toString(row.getPrice(), null));
        fields.put("amount", Objects.toString(row.getAmount(), null));
        fields.put("fee", Objects.toString(row.getFee(), null));
        fields.put("currency", Objects.toString(row.getCurrency(), null));
        fields.put("tradeTrigger", Objects.toString(row.getTradeTrigger(), null));
        fields.put("triggerRefType", Objects.toString(row.getTriggerRefType(), null));
        return fields;
    }
}
