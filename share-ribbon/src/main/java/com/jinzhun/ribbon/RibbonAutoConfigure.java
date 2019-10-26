package com.jinzhun.ribbon;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.DefaultPropertiesFactory;
import org.springframework.context.annotation.Bean;

import com.jinzhun.ribbon.config.RestTemplateProperties;

/**
 * Ribbon扩展配置类
 */
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RibbonAutoConfigure {
	
	@Bean
	public DefaultPropertiesFactory defaultPropertiesFactory() {
		return new DefaultPropertiesFactory();
	}
}
