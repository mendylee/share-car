package com.jinzhun.oauth.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinzhun.common.utils.ResponseUtil;
import com.jinzhun.oauth.handler.OauthLogoutHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 认证错误处理
 */
@Slf4j
@Configuration
public class SecurityHandlerConfig {
    
	@Resource private ObjectMapper objectMapper;
	
    @Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return (request, response, exception) -> {
			String msg;
			if (exception instanceof BadCredentialsException) {
				msg = "密码错误";
				log.error(msg);
			} else {
				msg = exception.getMessage();
			}
			ResponseUtil.responseWriter(objectMapper, response, msg, HttpStatus.UNAUTHORIZED.value());
		};
	}
    
    @Bean
    public OauthLogoutHandler oauthLogoutHandler() {
        return new OauthLogoutHandler();
    }
    
    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
    	
        return new DefaultWebResponseExceptionTranslator() {
        	
            public static final String BAD_MSG = "坏的凭证";

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                OAuth2Exception oAuth2Exception;
                if (e.getMessage() != null && e.getMessage().equals(BAD_MSG)) {
                	log.error("webResponseExceptionTranslator:用户名或密码错误");
                    oAuth2Exception = new InvalidGrantException("用户名或密码错误", e);
                } else if (e instanceof InternalAuthenticationServiceException) {
                	log.error("webResponseExceptionTranslator:无效的授权");
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof RedirectMismatchException) {
                	log.error("webResponseExceptionTranslator:重定向异常");
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof InvalidScopeException) {
                	log.error("webResponseExceptionTranslator:授权范围无效");
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else {
                	log.error("webResponseExceptionTranslator:服务内部错误");
                    oAuth2Exception = new UnsupportedResponseTypeException("服务内部错误", e);
                }
                ResponseEntity<OAuth2Exception> response = super.translate(oAuth2Exception);
                ResponseEntity.status(oAuth2Exception.getHttpErrorCode());
                response.getBody().addAdditionalInformation("resp_code", oAuth2Exception.getHttpErrorCode() + "");
                response.getBody().addAdditionalInformation("resp_msg", oAuth2Exception.getMessage());
                return response;
            }
        };
    }
    
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
    	
        return new SavedRequestAwareAuthenticationSuccessHandler() {
        	
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
}
