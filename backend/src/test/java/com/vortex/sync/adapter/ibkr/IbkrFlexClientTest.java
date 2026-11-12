package com.vortex.sync.adapter.ibkr;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IbkrFlexClientTest {

    private static final String TOKEN = "synthetic-flex-token";

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> submissionResponse;

    @Mock
    private HttpResponse<String> reportResponse;

    private final Logger logger = (Logger) LoggerFactory.getLogger(IbkrFlexClient.class);
    private Level previousLevel;
    private ListAppender<ILoggingEvent> logAppender;

    @BeforeEach
    void captureDebugLogs() {
        previousLevel = logger.getLevel();
        logger.setLevel(Level.DEBUG);
        logAppender = new ListAppender<>();
        logAppender.start();
        logger.addAppender(logAppender);
    }

    @AfterEach
    void restoreLogger() {
        logger.detachAppender(logAppender);
        logAppender.stop();
        logger.setLevel(previousLevel);
    }

    @Test
    void shouldMaskTokenInBothRequestPhasesWithoutChangingRequests() throws Exception {
        IbkrFlexQueryProperties properties = new IbkrFlexQueryProperties();
        properties.setFlexToken(TOKEN);
        properties.setTradeConfirmQueryId("example-query");
        IbkrFlexClient client = new IbkrFlexClient(properties);
        ReflectionTestUtils.setField(client, "httpClient", httpClient);

        when(submissionResponse.statusCode()).thenReturn(200);
        when(submissionResponse.body()).thenReturn("""
                <FlexStatementResponse>
                    <Status>Success</Status>
                    <ReferenceCode>example-reference</ReferenceCode>
                </FlexStatementResponse>
                """);
        when(reportResponse.statusCode()).thenReturn(200);
        when(reportResponse.body()).thenReturn("<FlexQueryResponse/>");
        when(httpClient.send(any(HttpRequest.class),
                org.mockito.ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(submissionResponse)
                .thenReturn(reportResponse);

        assertEquals("<FlexQueryResponse/>", client.fetchReport("20260101", "20260131"));

        ArgumentCaptor<HttpRequest> requests = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient, times(2)).send(requests.capture(),
                org.mockito.ArgumentMatchers.<HttpResponse.BodyHandler<String>>any());
        assertEquals("/AccountManagement/FlexWebService/SendRequest",
                requests.getAllValues().get(0).uri().getPath());
        assertEquals("/AccountManagement/FlexWebService/GetStatement",
                requests.getAllValues().get(1).uri().getPath());
        for (HttpRequest request : requests.getAllValues()) {
            assertTrue(request.uri().getRawQuery().contains("t=" + TOKEN));
        }

        String output = logAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .collect(Collectors.joining("\n"));
        assertFalse(output.contains(TOKEN), "Flex Token must not appear in client logs");
        assertTrue(output.contains("SendRequest sending request to:"));
        assertTrue(output.contains("GetStatement sending request to:"));
        assertEquals(2, logAppender.list.stream()
                .filter(event -> event.getFormattedMessage().contains("t=***"))
                .count());
    }

    @Test
    void shouldNotExposeTokenInInvalidRequestExceptions() {
        IbkrFlexQueryProperties properties = new IbkrFlexQueryProperties();
        properties.setBaseUrl("https://example.invalid/invalid path");
        properties.setFlexToken(TOKEN);
        properties.setTradeConfirmQueryId("example-query");
        IbkrFlexClient client = new IbkrFlexClient(properties);
        ReflectionTestUtils.setField(client, "httpClient", httpClient);

        IbkrFlexClient.IbkrFlexClientException error = assertThrows(
                IbkrFlexClient.IbkrFlexClientException.class,
                () -> client.fetchReport("20260101", "20260131"));

        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        assertFalse(stackTrace.toString().contains(TOKEN));
        assertEquals("SendRequest failed: invalid HTTP request configuration", error.getMessage());
        verifyNoInteractions(httpClient);
    }
}
