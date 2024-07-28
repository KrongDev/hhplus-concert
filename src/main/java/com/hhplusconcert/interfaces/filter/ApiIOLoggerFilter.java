package com.hhplusconcert.interfaces.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class ApiIOLoggerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        long now = System.currentTimeMillis();
        performRequestAudit(requestWrapper);
        chain.doFilter(request, response);
        performResponseAudit(responseWrapper, now);
    }

    public void performRequestAudit(ContentCachingRequestWrapper requestWrapper) {
        //
        log.info("Request Log:: URL: {}", requestWrapper.getRequestURI());
    }

    public void performResponseAudit(ContentCachingResponseWrapper responseWrapper, long startTime) {
        //
        log.info("Response Log:: response status: {}, executionTime: {}ms", responseWrapper.getStatus(), System.currentTimeMillis() - startTime);
    }
}
