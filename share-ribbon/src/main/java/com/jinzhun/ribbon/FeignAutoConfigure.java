package com.jinzhun.ribbon;

import org.springframework.context.annotation.Bean;

import feign.Logger;

/**
 * Feign统一配置
 */
public class FeignAutoConfigure {

	/**
	 * Feign 日志级别
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
