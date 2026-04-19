package com.localledger.sync.adapter.ibkr;

import com.localledger.entity.IbkrStagedOrder;
import com.localledger.entity.IbkrStagedTradeConfirm;
import com.localledger.entity.TradeRecord;
import com.localledger.entity.enums.AssetType;
import com.localledger.entity.enums.Currency;
import com.localledger.entity.enums.TradeTrigger;
import com.localledger.entity.enums.TradeType;
import com.localledger.entity.enums.TriggerRefType;
import com.localledger.repository.IbkrStagedOrderRepository;
import com.localledger.repository.IbkrStagedTradeConfirmRepository;
import com.localledger.repository.TradeRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * Worker bean for IBKR import operations that require independent transactions.
 *
 * Extracted from {@link IbkrImportService} to ensure that
 * {@code @Transactional(propagation = REQUIRES_NEW)} is properly intercepted
 * by the Spring AOP proxy. When these methods lived in IbkrImportService,
 * internal (self) calls bypassed the proxy, so REQUIRES_NEW never took effect.
 *
 * Contains:
 * - {@link #importSingleOrder} — import one staged order into trade_records
 * - {@link #backfillSingleStkRecord} — back-fill trigger_ref_id for one STK-side BookTrade
 * - All field-mapping helpers required by the above two methods
 *
 * @see IbkrImportService
 */
@Component
public class IbkrImportWorker {

    private static final Logger logger = LoggerFactory.getLogger(IbkrImportWorker.class);

    private static final String BROKER_CODE = "ibkr";
    private static final DateTimeFormatter IBKR_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    /** IBKR code tokens that indicate option exercise events */
    private static final Set<String> EXERCISE_CODES = Set.of("Ex", "MEx", "AEx");

    private final IbkrStagedOrderRepository stagedOrderRepository;
    private final IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository;
    private final TradeRecordRepository tradeRecordRepository;

    public IbkrImportWorker(IbkrStagedOrderRepository stagedOrderRepository,
                            IbkrStagedTradeConfirmRepository stagedTradeConfirmRepository,
                            TradeRecordRepository tradeRecordRepository) {
        this.stagedOrderRepository = stagedOrderRepository;
        this.stagedTradeConfirmRepository = stagedTradeConfirmRepository;
        this.tradeRecordRepository = tradeRecordRepository;
    }

    /**
     * Import a single staged order into trade_records.
     * Runs in its own transaction (REQUIRES_NEW) so that one failure
     * does not roll back the entire batch.
     *
     * @param batchId  the batch ID
     * @param brokerId the resolved broker ID
     * @param staged   the staged order to import
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void importSingleOrder(Long batchId, Long brokerId, IbkrStagedOrder staged) {
        try {
            // Deduplication check
            if (tradeRecordRepository.existsByExternalBrokerAndExternalId(BROKER_CODE, staged.getOrderId())) {
                staged.setStatus("SKIPPED");
                staged.setErrorMessage("Duplicate: already exists in trade_records");
                stagedOrderRepository.save(staged);
                logger.debug("[IbkrImport] Skipped duplicate: orderId={}", staged.getOrderId());
                return;
            }

            // Map to TradeRecord
            TradeRecord tradeRecord = mapToTradeRecord(batchId, brokerId, staged);
            TradeRecord saved = tradeRecordRepository.save(tradeRecord);

            // Update staged order status
            staged.setStatus("IMPORTED");
            staged.setImportedTradeId(saved.getId());
            stagedOrderRepository.save(staged);

            logger.debug("[IbkrImport] Imported: orderId={} → tradeRecordId={}", staged.getOrderId(), saved.getId());

        } catch (Exception e) {
            staged.setStatus("FAILED");
            staged.setErrorMessage("Import error: " + e.getMessage());
            stagedOrderRepository.save(staged);
            logger.warn("[IbkrImport] Failed to import orderId={}: {}", staged.getOrderId(), e.getMessage(), e);
        }
    }

    /**
     * Back-fill trigger_ref_id for a single STK-side BookTrade record.
     * Runs in its own transaction (REQUIRES_NEW).
     *
     * @param stkRecord the STK-side trade record needing back-fill
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void backfillSingleStkRecord(TradeRecord stkRecord) {
        try {
            List<TradeRecord> optCandidates = tradeRecordRepository.findOptSideBookTradesForMatching(
                    TradeTrigger.OPTION,
                    stkRecord.getTriggerRefType(),
                    List.of(AssetType.OPTION_CALL, AssetType.OPTION_PUT),
                    stkRecord.getUnderlyingSymbol(),
                    stkRecord.getTradeDate());

            if (optCandidates.isEmpty()) {
                logger.warn("[IbkrImport] No OPT-side match found for STK record id={}, " +
                        "symbol={}, date={}", stkRecord.getId(), stkRecord.getSymbol(), stkRecord.getTradeDate());
                return;
            }

            TradeRecord matched;
            if (optCandidates.size() == 1) {
                matched = optCandidates.get(0);
            } else {
                // Disambiguate by strike price
                matched = disambiguateByStrike(stkRecord, optCandidates);
                if (matched == null) {
                    // Further disambiguate by quantity
                    matched = disambiguateByQuantity(stkRecord, optCandidates);
                }
                if (matched == null) {
                    // Last resort: take the first one and warn
                    matched = optCandidates.get(0);
                    logger.warn("[IbkrImport] Multiple OPT-side matches for STK record id={}, " +
                            "using first match (OPT id={})", stkRecord.getId(), matched.getId());
                }
            }

            stkRecord.setTriggerRefId(matched.getId());
            tradeRecordRepository.save(stkRecord);
            logger.debug("[IbkrImport] Back-filled STK record id={} → OPT id={}",
                    stkRecord.getId(), matched.getId());

        } catch (Exception e) {
            logger.warn("[IbkrImport] Failed to back-fill STK record id={}: {}",
                    stkRecord.getId(), e.getMessage(), e);
        }
    }

    // ============ Field Mapping — TradeRecord ============

    /**
     * Map IbkrStagedOrder → TradeRecord following Appendix A mapping rules.
     */
    TradeRecord mapToTradeRecord(Long batchId, Long brokerId, IbkrStagedOrder staged) {
        TradeRecord record = new TradeRecord();

        // A.1 Direct mappings
        record.setTradeDate(parseIbkrDate(staged.getTradeDate()));
        record.setBrokerId(brokerId);
        record.setCurrency(mapCurrency(staged.getCurrency()));
        record.setTradeType(mapTradeType(staged.getBuySell()));
        record.setQuantity(parseAbsQuantity(staged.getQuantity()));
        record.setPrice(parseBigDecimal(staged.getPrice()));
        record.setAmount(parseBigDecimal(staged.getAmount()).abs());
        record.setFee(calculateFee(staged.getCommission(), staged.getTradeCharge()));
        record.setExternalId(staged.getOrderId());
        record.setExternalBroker(BROKER_CODE);
        record.setSyncBatchId(batchId);
        record.setIsDeleted(false);

        // A.2 Logic-dependent mappings
        record.setAssetType(mapAssetType(staged.getAssetCategory(), staged.getPutCall()));
        record.setSymbol(buildSymbol(staged));
        record.setUnderlyingSymbol(extractUnderlyingSymbol(staged));
        record.setName(staged.getDescription());
        record.setStrategyId(null);

        // A.2 BookTrade trigger determination
        TriggerInfo triggerInfo = determineTriggerInfo(staged);
        record.setTradeTrigger(triggerInfo.tradeTrigger);
        record.setTriggerRefType(triggerInfo.triggerRefType);
        record.setTriggerRefId(0L); // Back-filled later for STK-side BookTrades

        return record;
    }

    // ============ BookTrade Detection & Trigger Determination ============

    /**
     * Determine trade_trigger and trigger_ref_type for a staged order.
     *
     * ExchTrade (normal): orderTime and orderType both non-empty → MANUAL
     * BookTrade (option event): orderTime and orderType both empty → look up TradeConfirm code
     */
    private TriggerInfo determineTriggerInfo(IbkrStagedOrder staged) {
        boolean isBookTrade = isBlank(staged.getOrderTime()) && isBlank(staged.getOrderType());

        if (!isBookTrade) {
            return new TriggerInfo(TradeTrigger.MANUAL, TriggerRefType.NONE);
        }

        // BookTrade: look up the code from associated TradeConfirm
        TriggerRefType refType = resolveBookTradeRefType(staged);
        return new TriggerInfo(TradeTrigger.OPTION, refType);
    }

    /**
     * Resolve the TriggerRefType for a BookTrade by looking up the associated
     * TradeConfirm's code field.
     */
    private TriggerRefType resolveBookTradeRefType(IbkrStagedOrder staged) {
        List<IbkrStagedTradeConfirm> confirms =
                stagedTradeConfirmRepository.findByOrderId(staged.getOrderId());

        if (confirms.isEmpty()) {
            logger.warn("[IbkrImport] BookTrade orderId={} has no associated TradeConfirm, " +
                    "defaulting to MANUAL", staged.getOrderId());
            return TriggerRefType.NONE;
        }

        String code = confirms.get(0).getCode();
        if (isBlank(code)) {
            logger.warn("[IbkrImport] BookTrade orderId={} has empty code in TradeConfirm, " +
                    "defaulting to MANUAL", staged.getOrderId());
            return TriggerRefType.NONE;
        }

        // Parse code tokens (semicolon-separated)
        String[] tokens = code.split(";");

        // Priority-based matching (exact token match, not substring)
        for (String token : tokens) {
            String trimmed = token.trim();
            if (EXERCISE_CODES.contains(trimmed)) {
                return TriggerRefType.OPTION_EXERCISE;
            }
        }
        for (String token : tokens) {
            String trimmed = token.trim();
            if ("Ep".equals(trimmed)) {
                return TriggerRefType.OPTION_EXPIRE;
            }
        }
        for (String token : tokens) {
            String trimmed = token.trim();
            if ("A".equals(trimmed) || "GEA".equals(trimmed)) {
                return TriggerRefType.OPTION_ASSIGNED;
            }
        }

        logger.warn("[IbkrImport] BookTrade orderId={} code='{}' did not match any known option event, " +
                "defaulting to MANUAL", staged.getOrderId(), code);
        return TriggerRefType.NONE;
    }

    // ============ Disambiguation helpers ============

    private TradeRecord disambiguateByStrike(TradeRecord stkRecord, List<TradeRecord> optCandidates) {
        BigDecimal stkPrice = stkRecord.getPrice();
        if (stkPrice == null) return null;

        return optCandidates.stream()
                .filter(opt -> {
                    BigDecimal optStrike = extractStrikeFromSymbol(opt.getSymbol());
                    return optStrike != null && stkPrice.compareTo(optStrike) == 0;
                })
                .findFirst()
                .orElse(null);
    }

    private TradeRecord disambiguateByQuantity(TradeRecord stkRecord, List<TradeRecord> optCandidates) {
        if (stkRecord.getQuantity() == null) return null;
        int stkQty = stkRecord.getQuantity();

        return optCandidates.stream()
                .filter(opt -> {
                    if (opt.getQuantity() == null) return false;
                    int expectedStkQty = opt.getQuantity() * 100;
                    return stkQty == expectedStkQty;
                })
                .findFirst()
                .orElse(null);
    }

    private BigDecimal extractStrikeFromSymbol(String symbol) {
        if (symbol == null) return null;
        try {
            int lastDash = symbol.lastIndexOf('-');
            if (lastDash < 0 || lastDash >= symbol.length() - 2) return null;
            String strikeStr = symbol.substring(lastDash + 2);
            return new BigDecimal(strikeStr);
        } catch (Exception e) {
            return null;
        }
    }

    // ============ Field Mapping Helpers ============

    private LocalDate parseIbkrDate(String dateStr) {
        if (isBlank(dateStr) || "MULTI".equals(dateStr)) {
            throw new IllegalArgumentException("Cannot parse trade date: " + dateStr);
        }
        return LocalDate.parse(dateStr, IBKR_DATE_FORMAT);
    }

    private Currency mapCurrency(String ibkrCurrency) {
        if (ibkrCurrency == null) return Currency.USD;
        return switch (ibkrCurrency.toUpperCase()) {
            case "USD" -> Currency.USD;
            case "HKD" -> Currency.HKD;
            case "CNY", "CNH" -> Currency.CNY;
            default -> throw new IllegalArgumentException("Unsupported currency: " + ibkrCurrency);
        };
    }

    private TradeType mapTradeType(String buySell) {
        if ("BUY".equalsIgnoreCase(buySell)) return TradeType.BUY;
        if ("SELL".equalsIgnoreCase(buySell)) return TradeType.SELL;
        throw new IllegalArgumentException("Unknown buySell value: " + buySell);
    }

    private AssetType mapAssetType(String assetCategory, String putCall) {
        if ("STK".equalsIgnoreCase(assetCategory)) {
            return AssetType.STOCK;
        }
        if ("OPT".equalsIgnoreCase(assetCategory)) {
            if ("C".equalsIgnoreCase(putCall)) return AssetType.OPTION_CALL;
            if ("P".equalsIgnoreCase(putCall)) return AssetType.OPTION_PUT;
            throw new IllegalArgumentException("Option with unknown putCall: " + putCall);
        }
        throw new IllegalArgumentException("Unsupported assetCategory: " + assetCategory);
    }

    private Integer parseAbsQuantity(String quantity) {
        if (isBlank(quantity)) return 0;
        return Math.abs(new BigDecimal(quantity).intValue());
    }

    private BigDecimal parseBigDecimal(String value) {
        if (isBlank(value)) return BigDecimal.ZERO;
        return new BigDecimal(value);
    }

    private BigDecimal calculateFee(String commission, String tradeCharge) {
        BigDecimal commissionAbs = isBlank(commission) ? BigDecimal.ZERO : new BigDecimal(commission).abs();
        BigDecimal chargeAbs = isBlank(tradeCharge) ? BigDecimal.ZERO : new BigDecimal(tradeCharge).abs();
        return commissionAbs.add(chargeAbs);
    }

    private String buildSymbol(IbkrStagedOrder staged) {
        if ("OPT".equalsIgnoreCase(staged.getAssetCategory())) {
            String underlying = extractUnderlyingFromDescription(staged.getDescription());
            String expiry = staged.getExpiry();
            String putCall = staged.getPutCall();
            String normalizedStrike = new BigDecimal(staged.getStrike()).stripTrailingZeros().toPlainString();
            return underlying + "-" + expiry + "-" + putCall + normalizedStrike;
        }
        return staged.getSymbol() != null ? staged.getSymbol().trim() : "";
    }

    private String extractUnderlyingSymbol(IbkrStagedOrder staged) {
        if ("OPT".equalsIgnoreCase(staged.getAssetCategory())) {
            return extractUnderlyingFromDescription(staged.getDescription());
        }
        return staged.getSymbol() != null ? staged.getSymbol().trim() : "";
    }

    private String extractUnderlyingFromDescription(String description) {
        if (isBlank(description)) return "";
        String[] parts = description.trim().split("\\s+");
        return parts[0];
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    // ============ Inner Classes ============

    /**
     * Holds the determined trade_trigger and trigger_ref_type for a record.
     */
    private static class TriggerInfo {
        final TradeTrigger tradeTrigger;
        final TriggerRefType triggerRefType;

        TriggerInfo(TradeTrigger tradeTrigger, TriggerRefType triggerRefType) {
            this.tradeTrigger = tradeTrigger;
            this.triggerRefType = triggerRefType;
        }
    }
}
