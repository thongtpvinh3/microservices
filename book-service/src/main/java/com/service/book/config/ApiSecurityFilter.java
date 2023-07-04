package com.service.book.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class ApiSecurityFilter implements Filter {
    private static final String GATEWAY_TOKEN_HEADER = "X-Forwarded-For";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                System.out.println("Header: " + httpRequest.getHeader(headerNames.nextElement()));
            }
        }

        if (httpRequest.getHeader(GATEWAY_TOKEN_HEADER) == null) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_GATEWAY, "Bad Gateway API call");
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            servletResponse.setContentType("application/json");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
