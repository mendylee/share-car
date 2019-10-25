package com.jinzhun.redis.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "cache-manager")
public class CacheManagerProperties {

	private List<CacheConfig> configs;

	@Setter
	@Getter
	public static class CacheConfig {
		private String key;
		private long second = 60;
	}
}
