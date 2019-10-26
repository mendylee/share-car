package com.jinzhun.auth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.jinzhun.auth2.properties.SecurityProperties;

/**
 * 资源服务器配置
 */
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired private TokenStore tokenStore;
	
	@Autowired private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired private OAuth2WebSecurityExpressionHandler expressionHandler;
	
	@Autowired private OAuth2AccessDeniedHandler accessDeniedHandler;
	
	@Autowired private SecurityProperties securityProperties;
	

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore)
				 .stateless(true)
				 .authenticationEntryPoint(authenticationEntryPoint)
				 .expressionHandler(expressionHandler)
				 .accessDeniedHandler(accessDeniedHandler);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = setHttp(http)
				.authorizeRequests().antMatchers(securityProperties.getIgnore().getUrls()).permitAll()// 白名单配置
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest();
		setAuthenticate(authorizedUrl);
		http.headers().frameOptions().disable().and().csrf().disable();
	}
	
    public HttpSecurity setAuthenticate(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl) {
        return authorizedUrl.authenticated().and();
    }
	
    public HttpSecurity setHttp(HttpSecurity http) {
        return http;
    }
}
