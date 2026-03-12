package com.natalia.barros.insurance_product_api.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";


    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(CORRELATION_ID_HEADER, correlationId);

        long start = System.currentTimeMillis();

        try {
            log.info(
                    "REQUEST {} {}",
                    httpRequest.getMethod(),
                    httpRequest.getRequestURI()
            );

            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            log.info(
                    "RESPONSE {} {} {} {}ms",
                    httpRequest.getMethod(),
                    httpRequest.getRequestURI(),
                    httpResponse.getStatus(),
                    duration
            );


            MDC.remove(CORRELATION_ID_HEADER);
        }
    }

}
