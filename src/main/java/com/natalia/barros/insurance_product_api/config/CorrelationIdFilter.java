package com.natalia.barros.insurance_product_api.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
@Component
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
            System.out.println(
                    "REQUEST " +
                            httpRequest.getMethod() +
                            " " +
                            httpRequest.getRequestURI()
            );

            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            System.out.println(
                    "RESPONSE " +
                            httpRequest.getMethod() +
                            " " +
                            httpRequest.getRequestURI() +
                            " " +
                            httpResponse.getStatus() +
                            " " +
                            duration + "ms"
            );


            MDC.remove(CORRELATION_ID_HEADER);
        }
    }

}
