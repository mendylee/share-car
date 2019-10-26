package com.jinzhun.auth2.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
/**
 * 认证服务器使用Redis存取令牌
 */
public class AuthRedisTokenStore {
    
	@Autowired private RedisTemplate<String, Object> redisTemplate ;
	
	@Bean
	public TokenStore tokenStore() {
		RedisTemplateTokenStore redisTemplateStore = new RedisTemplateTokenStore();
		redisTemplateStore.setRedisTemplate(redisTemplate);
		return redisTemplateStore;
	}
}
