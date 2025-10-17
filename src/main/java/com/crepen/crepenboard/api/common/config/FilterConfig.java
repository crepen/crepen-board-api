package com.crepen.crepenboard.api.common.config;

import com.crepen.crepenboard.api.common.filter.CommonExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final MessageSource messageSource;

    @Bean
    public FilterRegistrationBean<CommonExceptionFilter> registerExceptionFilter(){
        FilterRegistrationBean<CommonExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CommonExceptionFilter(messageSource));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }


}
