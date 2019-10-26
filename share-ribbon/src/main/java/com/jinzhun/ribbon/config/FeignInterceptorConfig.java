package com.jinzhun.ribbon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;

/**
 * Feign拦截器
 * 
 * <b>使用feign client访问别的微服务时，将access_token放入参数或者header ，Authorization:Bearer xxx</b>
 * <b>或者url?access_token=xxx</b>
 * 
 */
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = template -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                if (authentication instanceof OAuth2Authentication) {
                    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                    String access_token = details.getTokenValue();
                    template.header("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + access_token);
                }
            }
        };
        return requestInterceptor;
    }
}
