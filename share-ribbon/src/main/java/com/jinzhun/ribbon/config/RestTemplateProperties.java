package com.jinzhun.ribbon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * RestTemplate 配置
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "rest-template")
public class RestTemplateProperties {

	/**
	 * 最大链接数
	 */
	private int maxTotal = 200;
	
	/**
	 * 同路由最大并发数
	 */
	private int maxPerRoute = 50;
	
	/**
	 * 读取超时时间 ms
	 */
	private int readTimeout = 35000;
	
	/**
	 * 链接超时时间 ms
	 */
	private int connectTimeout = 10000;
}
