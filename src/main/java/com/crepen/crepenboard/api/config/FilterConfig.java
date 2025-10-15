package com.crepen.crepenboard.api.config;

import com.crepen.crepenboard.api.common.filter.CommonExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CommonExceptionFilter> registerExceptionFilter(){
        FilterRegistrationBean<CommonExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CommonExceptionFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }


}
