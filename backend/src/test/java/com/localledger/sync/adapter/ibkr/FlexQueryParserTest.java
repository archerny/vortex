package com.localledger.sync.adapter.ibkr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FlexQueryParser 单元测试
 *
 * 使用真实的 Flex Query XML 样本文件进行解析测试，
 * 验证解析逻辑的正确性并打印所有交易记录。
 */
class FlexQueryParserTest {

    private FlexQueryParser parser;

    @BeforeEach
    void setUp() {
        parser = new FlexQueryParser();
    }

    @Test
    @DisplayName("解析真实的 IBKR Flex Query Trade Confirms XML 文件")
    void parseRealFlexQueryXml() {
        // 加载测试资源文件
        InputStream inputStream = getClass().getResourceAsStream("/ibkr-flex-trade-confirms.xml");
        assertNotNull(inputStream, "测试资源文件 ibkr-flex-trade-confirms.xml 应存在");

        // 解析
        FlexQueryParseResult result = parser.parse(inputStream);

        // ====== 验证元数据 ======
        assertNotNull(result, "解析结果不应为 null");
        assertEquals("IBKR_TC_BACKFILL", result.getQueryName(), "queryName 应为 IBKR_TC_BACKFILL");
        assertEquals("TCF", result.getType(), "type 应为 TCF");
        assertEquals("U18316975", result.getAccountId(), "accountId 应正确解析");
        assertEquals("20260101", result.getFromDate(), "fromDate 应为 20260101");
        assertEquals("20260331", result.getToDate(), "toDate 应为 20260331");
        assertNotNull(result.getWhenGenerated(), "whenGenerated 不应为 null");

        // ====== 验证数据量 ======
        assertTrue(result.getOrderCount() > 0, "应解析到至少一条 Order 记录");
        assertTrue(result.getTradeConfirmCount() > 0, "应解析到至少一条 TradeConfirm 记录");

        System.out.println("========================================");
        System.out.println("Parse result summary: " + result);
        System.out.println("========================================");

        // ====== 打印所有 Order 记录 ======
        List<IbkrOrderRecord> orders = result.getOrders();
        System.out.println();
        System.out.println("============ Order records (total: " + orders.size() + ") ============");
        for (int i = 0; i < orders.size(); i++) {
            System.out.printf("[Order %d] %s%n", i + 1, orders.get(i));
        }

        // ====== 打印所有 TradeConfirm 记录 ======
        List<IbkrTradeConfirm> tradeConfirms = result.getTradeConfirms();
        System.out.println();
        System.out.println("============ TradeConfirm records (total: " + tradeConfirms.size() + ") ============");
        for (int i = 0; i < tradeConfirms.size(); i++) {
            System.out.printf("[TradeConfirm %d] %s%n", i + 1, tradeConfirms.get(i));
        }

        System.out.println();
        System.out.println("========================================");
        System.out.printf("合计: %d 条 Order, %d 条 TradeConfirm%n", orders.size(), tradeConfirms.size());
        System.out.println("========================================");
    }

    @Test
    @DisplayName("验证 Order 记录字段解析正确性")
    void verifyOrderFieldsParsedCorrectly() {
        InputStream inputStream = getClass().getResourceAsStream("/ibkr-flex-trade-confirms.xml");
        assertNotNull(inputStream);

        FlexQueryParseResult result = parser.parse(inputStream);
        assertFalse(result.getOrders().isEmpty(), "应至少有一条 Order");

        // 验证第一条 Order 的关键字段
        IbkrOrderRecord firstOrder = result.getOrders().get(0);

        assertNotNull(firstOrder.getAccountId(), "accountId 不应为 null");
        assertNotNull(firstOrder.getSymbol(), "symbol 不应为 null");
        assertNotNull(firstOrder.getBuySell(), "buySell 不应为 null");
        assertNotNull(firstOrder.getCurrency(), "currency 不应为 null");
        assertNotNull(firstOrder.getAssetCategory(), "assetCategory 不应为 null");

        // 验证第一条是 AAPL 买入
        assertEquals("AAPL", firstOrder.getSymbol());
        assertEquals("BUY", firstOrder.getBuySell());
        assertEquals("USD", firstOrder.getCurrency());
        assertEquals("STK", firstOrder.getAssetCategory());
        assertEquals("4797726468", firstOrder.getOrderID());
        assertEquals("100", firstOrder.getQuantity());
        assertEquals("247.41", firstOrder.getPrice());

        // 验证业务方法
        assertTrue(firstOrder.isBuy());
        assertTrue(firstOrder.isStock());
        assertFalse(firstOrder.isOption());
        assertNotNull(firstOrder.getPriceBigDecimal());
        assertNotNull(firstOrder.getAbsQuantity());
        assertNotNull(firstOrder.getAbsCommission());
    }

    @Test
    @DisplayName("验证 TradeConfirm 记录字段解析正确性")
    void verifyTradeConfirmFieldsParsedCorrectly() {
        InputStream inputStream = getClass().getResourceAsStream("/ibkr-flex-trade-confirms.xml");
        assertNotNull(inputStream);

        FlexQueryParseResult result = parser.parse(inputStream);
        assertFalse(result.getTradeConfirms().isEmpty(), "应至少有一条 TradeConfirm");

        // 验证第一条 TradeConfirm 的关键字段
        IbkrTradeConfirm firstTc = result.getTradeConfirms().get(0);

        assertNotNull(firstTc.getAccountId(), "accountId 不应为 null");
        assertNotNull(firstTc.getSymbol(), "symbol 不应为 null");
        assertNotNull(firstTc.getTradeID(), "tradeID 不应为 null");
        assertNotNull(firstTc.getTransactionType(), "transactionType 不应为 null");

        // 验证第一条 TradeConfirm 是 AAPL 的 ExchTrade
        assertEquals("AAPL", firstTc.getSymbol());
        assertEquals("ExchTrade", firstTc.getTransactionType());
        assertEquals("8856974303", firstTc.getTradeID());
        assertEquals("4797726468", firstTc.getOrderID());
        assertEquals("BUY", firstTc.getBuySell());
        assertEquals("100", firstTc.getQuantity());
        assertEquals("247.41", firstTc.getPrice());
        assertEquals("O", firstTc.getCode());

        // 验证业务方法
        assertTrue(firstTc.isBuy());
        assertTrue(firstTc.isExchangeTrade());
        assertFalse(firstTc.isBookTrade());
        assertTrue(firstTc.isOpening());
        assertFalse(firstTc.isClosing());
        assertNotNull(firstTc.getTradeDateAsLocalDate());
        assertNotNull(firstTc.getDateTimeAsLocalDateTime());
    }

    @Test
    @DisplayName("验证 TradeConfirm 中 BookTrade（ACATS 转入）解析正确")
    void verifyBookTradeTradeConfirm() {
        InputStream inputStream = getClass().getResourceAsStream("/ibkr-flex-trade-confirms.xml");
        assertNotNull(inputStream);

        FlexQueryParseResult result = parser.parse(inputStream);

        // 找到第一条 BookTrade 类型的 TradeConfirm
        IbkrTradeConfirm bookTrade = result.getTradeConfirms().stream()
                .filter(IbkrTradeConfirm::isBookTrade)
                .findFirst()
                .orElse(null);

        assertNotNull(bookTrade, "应存在 BookTrade 类型的 TradeConfirm");
        assertEquals("BookTrade", bookTrade.getTransactionType());
        assertTrue(bookTrade.isBookTrade());
        assertFalse(bookTrade.isExchangeTrade());

        System.out.println("BookTrade example: " + bookTrade);
    }

    @Test
    @DisplayName("验证解析空 TradeConfirms 节点不会报错")
    void parseEmptyTradeConfirms() {
        String xml = """
                <FlexQueryResponse queryName="TEST" type="TCF">
                    <FlexStatements count="1">
                        <FlexStatement accountId="TEST123" fromDate="20260101" toDate="20260331" whenGenerated="20260405;124357">
                            <TradeConfirms>
                            </TradeConfirms>
                        </FlexStatement>
                    </FlexStatements>
                </FlexQueryResponse>
                """;

        FlexQueryParseResult result = parser.parse(xml);

        assertNotNull(result);
        assertEquals("TEST", result.getQueryName());
        assertEquals("TEST123", result.getAccountId());
        assertEquals(0, result.getOrderCount());
        assertEquals(0, result.getTradeConfirmCount());
    }

    @Test
    @DisplayName("验证解析无 TradeConfirms 节点的 XML")
    void parseXmlWithoutTradeConfirms() {
        String xml = """
                <FlexQueryResponse queryName="TEST" type="TCF">
                    <FlexStatements count="1">
                        <FlexStatement accountId="TEST123" fromDate="20260101" toDate="20260331" whenGenerated="20260405;124357">
                        </FlexStatement>
                    </FlexStatements>
                </FlexQueryResponse>
                """;

        FlexQueryParseResult result = parser.parse(xml);

        assertNotNull(result);
        assertEquals(0, result.getOrderCount());
        assertEquals(0, result.getTradeConfirmCount());
    }

    @Test
    @DisplayName("解析无效 XML 应抛出 FlexQueryParseException")
    void parseInvalidXmlShouldThrow() {
        String invalidXml = "<invalid>not a valid flex query xml";

        assertThrows(FlexQueryParser.FlexQueryParseException.class, () -> {
            parser.parse(invalidXml);
        });
    }
}
