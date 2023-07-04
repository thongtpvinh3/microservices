package com.service.book.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterConfig {

    public FilterRegistrationBean<ApiSecurityFilter> apiSecurityFilter() {
        FilterRegistrationBean<ApiSecurityFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ApiSecurityFilter());
        registrationBean.addUrlPatterns("/**");

        return registrationBean;
    }
}
