package com.jinzhun.auth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@RefreshScope
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

	/**
	 * 认证配置
	 */
	private AuthProperties auth;
	
	/**
	 * 白名单配置
	 */
	private PermitProperties ignore;
	
}
