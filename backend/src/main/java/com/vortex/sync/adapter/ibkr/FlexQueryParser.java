package com.vortex.sync.adapter.ibkr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.springframework.stereotype.Component;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;

/**
 * IBKR Flex Query XML 解析器
 *
 * 负责将 Flex Query 返回的 XML 内容解析为结构化的 {@link FlexQueryParseResult}。
 * 使用 JDK 内置的 DOM 解析器（无需额外依赖），因为 Flex Query XML 采用
 * attribute-based 格式，所有字段都是 XML 属性而非子元素。
 *
 * XML 结构：
 * <pre>
 * {@code
 * <FlexQueryResponse queryName="..." type="TCF">
 *   <FlexStatements count="1">
 *     <FlexStatement accountId="..." fromDate="..." toDate="..." whenGenerated="...">
 *       <TradeConfirms>
 *         <SymbolSummary .../> (忽略，可从 Order/TradeConfirm 聚合)
 *         <Order .../>
 *         <TradeConfirm .../>
 *         ...
 *       </TradeConfirms>
 *     </FlexStatement>
 *   </FlexStatements>
 * </FlexQueryResponse>
 * }
 * </pre>
 *
 * 解析策略：
 * - 只解析 {@code <Order>} 和 {@code <TradeConfirm>} 节点
 * - 忽略 {@code <SymbolSummary>} 节点（汇总数据可从明细聚合）
 * - 所有字段以 String 形式原样保留，业务转换交给模型类的方法
 */
@Component
public class FlexQueryParser {

    private static final Logger logger = LoggerFactory.getLogger(FlexQueryParser.class);

    /**
     * 从 XML 字符串解析
     *
     * @param xmlContent Flex Query 返回的 XML 内容
     * @return 解析结果
     * @throws FlexQueryParseException 解析失败时抛出
     */
    public FlexQueryParseResult parse(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 安全设置：禁用外部实体，防止 XXE 攻击
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
            return parseDocument(document);
        } catch (FlexQueryParseException e) {
            throw e;
        } catch (Exception e) {
            throw new FlexQueryParseException("Failed to parse Flex Query XML: " + e.getMessage(), e);
        }
    }

    /**
     * 从 InputStream 解析（用于从 HTTP 响应直接解析）
     *
     * @param inputStream XML 输入流
     * @return 解析结果
     * @throws FlexQueryParseException 解析失败时抛出
     */
    public FlexQueryParseResult parse(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            return parseDocument(document);
        } catch (FlexQueryParseException e) {
            throw e;
        } catch (Exception e) {
            throw new FlexQueryParseException("Failed to parse Flex Query XML from InputStream: " + e.getMessage(), e);
        }
    }

    // ============ 内部解析逻辑 ============

    /**
     * 解析 DOM 文档
     */
    private FlexQueryParseResult parseDocument(Document document) {
        FlexQueryParseResult result = new FlexQueryParseResult();

        // 解析根元素 <FlexQueryResponse>
        Element root = document.getDocumentElement();
        result.setQueryName(getAttr(root, "queryName"));
        result.setType(getAttr(root, "type"));

        // 解析 <FlexStatement>
        NodeList statementNodes = root.getElementsByTagName("FlexStatement");
        if (statementNodes.getLength() == 0) {
            logger.warn("[FlexQueryParser] FlexStatement node not found in XML");
            return result;
        }

        Element statement = (Element) statementNodes.item(0);
        result.setAccountId(getAttr(statement, "accountId"));
        result.setFromDate(getAttr(statement, "fromDate"));
        result.setToDate(getAttr(statement, "toDate"));
        result.setWhenGenerated(getAttr(statement, "whenGenerated"));

        // 解析 <TradeConfirms> 下的子节点
        NodeList tradeConfirmsNodes = statement.getElementsByTagName("TradeConfirms");
        if (tradeConfirmsNodes.getLength() == 0) {
            logger.warn("[FlexQueryParser] TradeConfirms node not found in XML");
            return result;
        }

        Element tradeConfirmsElement = (Element) tradeConfirmsNodes.item(0);
        NodeList children = tradeConfirmsElement.getChildNodes();

        int orderCount = 0;
        int tradeConfirmCount = 0;
        int symbolSummaryCount = 0;

        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            Element element = (Element) node;
            String tagName = element.getTagName();

            switch (tagName) {
                case "Order":
                    result.addOrder(parseOrderElement(element));
                    orderCount++;
                    break;
                case "TradeConfirm":
                    result.addTradeConfirm(parseTradeConfirmElement(element));
                    tradeConfirmCount++;
                    break;
                case "SymbolSummary":
                    // 忽略汇总记录，可从明细数据聚合
                    symbolSummaryCount++;
                    break;
                default:
                    logger.debug("[FlexQueryParser] Ignoring unknown node: {}", tagName);
                    break;
            }
        }

        logger.info("[FlexQueryParser] Parse complete - account: {}, range: {} ~ {}, " +
                        "orders: {}, tradeConfirms: {}, symbolSummaries: {} (ignored)",
                result.getAccountId(), result.getFromDate(), result.getToDate(),
                orderCount, tradeConfirmCount, symbolSummaryCount);

        return result;
    }

    /**
     * 解析 {@code <Order>} 节点为 IbkrOrderRecord
     */
    private IbkrOrderRecord parseOrderElement(Element element) {
        IbkrOrderRecord record = new IbkrOrderRecord();

        // 账户信息
        record.setAccountId(getAttr(element, "accountId"));
        record.setAcctAlias(getAttr(element, "acctAlias"));

        // 合约信息
        record.setCurrency(getAttr(element, "currency"));
        record.setAssetCategory(getAttr(element, "assetCategory"));
        record.setSymbol(getAttr(element, "symbol"));
        record.setDescription(getAttr(element, "description"));
        record.setConid(getAttr(element, "conid"));
        record.setSecurityID(getAttr(element, "securityID"));
        record.setSecurityIDType(getAttr(element, "securityIDType"));
        record.setMultiplier(getAttr(element, "multiplier"));

        // 期权专有
        record.setStrike(getAttr(element, "strike"));
        record.setExpiry(getAttr(element, "expiry"));
        record.setPutCall(getAttr(element, "putCall"));

        // 订单信息
        record.setOrderID(getAttr(element, "orderID"));
        record.setOrderTime(getAttr(element, "orderTime"));
        record.setDateTime(getAttr(element, "dateTime"));
        record.setSettleDate(getAttr(element, "settleDate"));
        record.setTradeDate(getAttr(element, "tradeDate"));
        record.setExchange(getAttr(element, "exchange"));
        record.setBuySell(getAttr(element, "buySell"));
        record.setOrderType(getAttr(element, "orderType"));
        record.setIsAPIOrder(getAttr(element, "isAPIOrder"));

        // 数量与金额
        record.setQuantity(getAttr(element, "quantity"));
        record.setPrice(getAttr(element, "price"));
        record.setAmount(getAttr(element, "amount"));
        record.setProceeds(getAttr(element, "proceeds"));
        record.setNetCash(getAttr(element, "netCash"));

        // 费用
        record.setCommission(getAttr(element, "commission"));
        record.setCommissionCurrency(getAttr(element, "commissionCurrency"));
        record.setTradeCharge(getAttr(element, "tradeCharge"));

        // 其他
        record.setCode(getAttr(element, "code"));
        record.setTraderID(getAttr(element, "traderID"));

        return record;
    }

    /**
     * 解析 {@code <TradeConfirm>} 节点为 IbkrTradeConfirm
     */
    private IbkrTradeConfirm parseTradeConfirmElement(Element element) {
        IbkrTradeConfirm record = new IbkrTradeConfirm();

        // 账户信息
        record.setAccountId(getAttr(element, "accountId"));
        record.setAcctAlias(getAttr(element, "acctAlias"));

        // 合约信息
        record.setCurrency(getAttr(element, "currency"));
        record.setAssetCategory(getAttr(element, "assetCategory"));
        record.setSymbol(getAttr(element, "symbol"));
        record.setDescription(getAttr(element, "description"));
        record.setConid(getAttr(element, "conid"));
        record.setSecurityID(getAttr(element, "securityID"));
        record.setSecurityIDType(getAttr(element, "securityIDType"));
        record.setMultiplier(getAttr(element, "multiplier"));

        // 期权专有
        record.setStrike(getAttr(element, "strike"));
        record.setExpiry(getAttr(element, "expiry"));
        record.setPutCall(getAttr(element, "putCall"));

        // 交易标识
        record.setTransactionType(getAttr(element, "transactionType"));
        record.setTradeID(getAttr(element, "tradeID"));
        record.setOrderID(getAttr(element, "orderID"));
        record.setExecID(getAttr(element, "execID"));
        record.setBrokerageOrderID(getAttr(element, "brokerageOrderID"));
        record.setOrderReference(getAttr(element, "orderReference"));

        // 时间信息
        record.setOrderTime(getAttr(element, "orderTime"));
        record.setDateTime(getAttr(element, "dateTime"));
        record.setSettleDate(getAttr(element, "settleDate"));
        record.setTradeDate(getAttr(element, "tradeDate"));

        // 交易信息
        record.setExchange(getAttr(element, "exchange"));
        record.setBuySell(getAttr(element, "buySell"));
        record.setQuantity(getAttr(element, "quantity"));
        record.setPrice(getAttr(element, "price"));
        record.setAmount(getAttr(element, "amount"));
        record.setProceeds(getAttr(element, "proceeds"));
        record.setNetCash(getAttr(element, "netCash"));

        // 费用
        record.setCommission(getAttr(element, "commission"));
        record.setCommissionCurrency(getAttr(element, "commissionCurrency"));
        record.setTradeCharge(getAttr(element, "tradeCharge"));

        // 其他
        record.setCode(getAttr(element, "code"));
        record.setOrderType(getAttr(element, "orderType"));
        record.setTraderID(getAttr(element, "traderID"));
        record.setIsAPIOrder(getAttr(element, "isAPIOrder"));

        return record;
    }

    // ============ 工具方法 ============

    /**
     * 安全获取 XML 元素的属性值
     * 返回空字符串时视为 null（IBKR XML 中空属性表示无值）
     */
    private String getAttr(Element element, String name) {
        String value = element.getAttribute(name);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }

    // ============ 自定义异常 ============

    /**
     * Flex Query XML 解析异常
     */
    public static class FlexQueryParseException extends RuntimeException {

        public FlexQueryParseException(String message) {
            super(message);
        }

        public FlexQueryParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
