package com.localledger.sync.adapter.ibkr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * IBKR Flex Web Service HTTP Client
 *
 * Encapsulates the two-step HTTP request flow for IBKR Flex Web Service:
 * 1. SendRequest — submit a query, receive a ReferenceCode
 * 2. GetStatement — poll with the ReferenceCode until the report is ready
 *
 * Includes:
 * - Polling/retry logic for report generation (error code 1019)
 * - Rate limiting (1 req/sec, 10 req/min per token)
 * - Error code handling (1003-1019)
 * - User-Agent header (required by IBKR)
 *
 * @see <a href="https://www.interactivebrokers.com/en/software/am/am/manageaccount/flex-web-service.htm">
 *     IBKR Flex Web Service Documentation</a>
 */
@Component
public class IbkrFlexClient {

    private static final Logger logger = LoggerFactory.getLogger(IbkrFlexClient.class);

    /** User-Agent header value (required by IBKR) */
    private static final String USER_AGENT = "LocalLedger/1.0";

    /** Initial wait time (ms) after SendRequest before first GetStatement attempt */
    private static final long INITIAL_WAIT_MS = 5_000;

    /** Wait time (ms) between GetStatement retries */
    private static final long RETRY_INTERVAL_MS = 3_000;

    /** Maximum number of GetStatement retry attempts */
    private static final int MAX_RETRIES = 10;

    /** Minimum interval (ms) between any two requests (rate limit: 1 req/sec) */
    private static final long MIN_REQUEST_INTERVAL_MS = 1_100;

    /** HTTP client with reasonable timeouts */
    private final HttpClient httpClient;

    /** Timestamp of last request sent (for rate limiting) */
    private volatile long lastRequestTimeMs = 0;

    private final IbkrFlexQueryProperties properties;

    public IbkrFlexClient(IbkrFlexQueryProperties properties) {
        this.properties = properties;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    // ============ Public API ============

    /**
     * Fetch a Flex Query report: SendRequest + poll GetStatement.
     *
     * @param fromDate start date in yyyyMMdd format
     * @param toDate   end date in yyyyMMdd format
     * @return raw XML report content
     * @throws IbkrFlexClientException on any failure
     */
    public String fetchReport(String fromDate, String toDate) {
        String token = properties.getFlexToken();
        String queryId = properties.getTradeConfirmQueryId();

        logger.info("[IbkrFlexClient] Fetching report: queryId={}, dateRange={} ~ {}", queryId, fromDate, toDate);

        // Step 1: SendRequest
        String referenceCode = sendRequest(token, queryId, fromDate, toDate);
        logger.info("[IbkrFlexClient] SendRequest succeeded, referenceCode={}", referenceCode);

        // Step 2: Wait then poll GetStatement
        sleep(INITIAL_WAIT_MS, "initial wait before GetStatement");

        String report = pollGetStatement(token, referenceCode);
        logger.info("[IbkrFlexClient] GetStatement succeeded, report length={} chars", report.length());

        return report;
    }

    // ============ Step 1: SendRequest ============

    /**
     * Send a Flex Query request and extract the ReferenceCode from the response.
     *
     * Request: GET {baseUrl}/SendRequest?t={token}&q={queryId}&v=3&fd={fromDate}&td={toDate}
     * Response XML:
     * <pre>{@code
     * <FlexStatementResponse timestamp='...'>
     *     <Status>Success</Status>
     *     <ReferenceCode>1234567890</ReferenceCode>
     *     <Url>...</Url>
     * </FlexStatementResponse>
     * }</pre>
     */
    private String sendRequest(String token, String queryId, String fromDate, String toDate) {
        String url = String.format("%s/SendRequest?t=%s&q=%s&fd=%s&td=%s&v=3",
                properties.getBaseUrl(), token, queryId, fromDate, toDate);

        logger.debug("[IbkrFlexClient] SendRequest full URL (for curl debugging): {}", url);

        String responseBody = executeHttpGet(url, "SendRequest");
        Document doc = parseXmlResponse(responseBody, "SendRequest");

        // Check status
        String status = getElementText(doc, "Status");
        if (!"Success".equalsIgnoreCase(status)) {
            String errorCode = getElementText(doc, "ErrorCode");
            String errorMessage = getElementText(doc, "ErrorMessage");
            handleErrorCode(errorCode, errorMessage, "SendRequest");
        }

        // Extract ReferenceCode
        String referenceCode = getElementText(doc, "ReferenceCode");
        if (referenceCode == null || referenceCode.isBlank()) {
            throw new IbkrFlexClientException("SendRequest returned Success but no ReferenceCode");
        }

        return referenceCode;
    }

    // ============ Step 2: GetStatement (with polling) ============

    /**
     * Poll GetStatement until the report is ready or max retries exceeded.
     *
     * Request: GET {baseUrl}/GetStatement?t={token}&q={referenceCode}&v=3
     */
    private String pollGetStatement(String token, String referenceCode) {
        String url = String.format("%s/GetStatement?t=%s&q=%s&v=3",
                properties.getBaseUrl(), token, referenceCode);

        logger.debug("[IbkrFlexClient] GetStatement full URL (for curl debugging): {}", url);

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            logger.debug("[IbkrFlexClient] GetStatement attempt {}/{}", attempt, MAX_RETRIES);

            String responseBody = executeHttpGet(url, "GetStatement");

            // Check if the response is a Flex Query report (starts with <FlexQueryResponse>)
            // or an error/status response (starts with <FlexStatementResponse>)
            if (isFlexQueryReport(responseBody)) {
                return responseBody;
            }

            // Parse as status/error response
            Document doc = parseXmlResponse(responseBody, "GetStatement");
            String status = getElementText(doc, "Status");
            String errorCode = getElementText(doc, "ErrorCode");
            String errorMessage = getElementText(doc, "ErrorMessage");

            if ("Success".equalsIgnoreCase(status)) {
                // Should not happen (Success without report content), but handle gracefully
                logger.warn("[IbkrFlexClient] GetStatement returned Success but response is not a report");
                return responseBody;
            }

            // Check if retryable
            if (isRetryableError(errorCode)) {
                logger.info("[IbkrFlexClient] Report generation in progress (errorCode={}), retrying in {} ms...",
                        errorCode, RETRY_INTERVAL_MS);
                sleep(RETRY_INTERVAL_MS, "retry wait for GetStatement");
                continue;
            }

            // Non-retryable error
            handleErrorCode(errorCode, errorMessage, "GetStatement");
        }

        throw new IbkrFlexClientException(
                "GetStatement failed after " + MAX_RETRIES + " attempts - report generation timed out");
    }

    // ============ HTTP Execution ============

    /**
     * Execute an HTTP GET request with rate limiting and User-Agent header.
     */
    private String executeHttpGet(String url, String operation) {
        enforceRateLimit();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", USER_AGENT)
                    .timeout(Duration.ofSeconds(60))
                    .GET()
                    .build();

            logger.debug("[IbkrFlexClient] {} sending request to: {}", operation, maskToken(url));

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IbkrFlexClientException(
                        String.format("%s HTTP error: status=%d", operation, response.statusCode()));
            }

            return response.body();

        } catch (IbkrFlexClientException e) {
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IbkrFlexClientException(operation + " interrupted", e);
        } catch (Exception e) {
            throw new IbkrFlexClientException(operation + " failed: " + e.getMessage(), e);
        }
    }

    // ============ Rate Limiting ============

    /**
     * Simple rate limiter: ensure at least MIN_REQUEST_INTERVAL_MS between requests.
     * This enforces the "1 request per second" limit.
     */
    private synchronized void enforceRateLimit() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRequestTimeMs;

        if (elapsed < MIN_REQUEST_INTERVAL_MS) {
            long waitMs = MIN_REQUEST_INTERVAL_MS - elapsed;
            logger.debug("[IbkrFlexClient] Rate limit: waiting {} ms before next request", waitMs);
            sleep(waitMs, "rate limit enforcement");
        }

        lastRequestTimeMs = System.currentTimeMillis();
    }

    // ============ XML Parsing Helpers ============

    /**
     * Parse XML response string into a DOM Document.
     */
    private Document parseXmlResponse(String xml, String operation) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new IbkrFlexClientException(
                    String.format("%s failed to parse XML response: %s", operation, e.getMessage()), e);
        }
    }

    /**
     * Get text content of a named element from a DOM Document.
     */
    private String getElementText(Document doc, String tagName) {
        var nodes = doc.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return null;
        }
        return nodes.item(0).getTextContent();
    }

    /**
     * Check if the response body looks like a Flex Query report
     * (as opposed to a status/error response).
     */
    private boolean isFlexQueryReport(String responseBody) {
        if (responseBody == null) return false;
        String trimmed = responseBody.trim();
        // Flex Query reports start with <?xml ...> then <FlexQueryResponse>
        // Status/error responses start with <?xml ...> then <FlexStatementResponse>
        return trimmed.contains("<FlexQueryResponse");
    }

    // ============ Error Handling ============

    /**
     * Check if the error code indicates a retryable condition.
     * 1003 = Statement generation in progress
     * 1019 = Statement generation in progress (retry later)
     */
    private boolean isRetryableError(String errorCode) {
        return "1003".equals(errorCode) || "1019".equals(errorCode);
    }

    /**
     * Handle non-retryable error codes by throwing descriptive exceptions.
     */
    private void handleErrorCode(String errorCode, String errorMessage, String operation) {
        String description = describeErrorCode(errorCode);
        String msg = String.format("[IbkrFlexClient] %s error: code=%s (%s), message=%s",
                operation, errorCode, description, errorMessage);
        logger.error(msg);

        // For rate limit errors, wait and let the caller retry if appropriate
        if ("1006".equals(errorCode) || "1018".equals(errorCode)) {
            throw new IbkrFlexClientException(
                    String.format("%s rate limited (code=%s): %s - please retry later",
                            operation, errorCode, errorMessage));
        }

        throw new IbkrFlexClientException(
                String.format("%s failed (code=%s): %s", operation, errorCode, errorMessage));
    }

    /**
     * Map error codes to human-readable descriptions.
     */
    private String describeErrorCode(String errorCode) {
        if (errorCode == null) return "unknown";
        switch (errorCode) {
            case "1003": return "Statement generation in progress";
            case "1004": return "Statement could not be generated";
            case "1005": return "Invalid token";
            case "1006": return "Too many requests (per-second limit)";
            case "1012": return "Query not found";
            case "1018": return "Too many requests (per-minute limit)";
            case "1019": return "Statement generation in progress (retry later)";
            default: return "unknown error code";
        }
    }

    // ============ Utility ============

    /**
     * Sleep for specified duration, handling InterruptedException.
     */
    private void sleep(long ms, String reason) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IbkrFlexClientException("Interrupted during " + reason, e);
        }
    }

    /**
     * Mask the token in URL for safe logging.
     */
    private String maskToken(String url) {
        return url.replaceAll("t=[^&]+", "t=***");
    }

    // ============ Custom Exception ============

    /**
     * Exception thrown by IbkrFlexClient for any failure in the Flex Web Service interaction.
     */
    public static class IbkrFlexClientException extends RuntimeException {

        public IbkrFlexClientException(String message) {
            super(message);
        }

        public IbkrFlexClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
