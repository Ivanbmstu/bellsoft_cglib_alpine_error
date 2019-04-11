package com.example.server.logger;

import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Order(2)
@Component
public class LoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String NEW_LINE = "\n";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            log(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }


    private void log(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        String responseBody = new String(response.getContentAsByteArray(), UTF_8);
        int responseStatus = response.getStatus();

        Map<String, Object> requestContext = new HashMap<>();
        requestContext.put("response_code", responseStatus);
        requestContext.put("path", request.getRequestURI());
        requestContext.put("method", request.getMethod());
        requestContext.put("client_ip", request.getRemoteAddr());

        String requestLog = "responseBody: \n" + responseBody + NEW_LINE +
                "requestParams: \n" + request.getQueryString() + NEW_LINE +
                "requestHeaders: \n" + headersToString(request);
        log.info(Markers.appendEntries(requestContext), requestLog);
    }

    private String headersToString(ContentCachingRequestWrapper request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder builder = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            builder.append(header).append("=").append(request.getHeader(header)).append(NEW_LINE);
        }
        return builder.toString();
    }

}
