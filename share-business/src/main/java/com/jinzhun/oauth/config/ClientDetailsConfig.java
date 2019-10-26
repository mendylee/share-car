package com.jinzhun.oauth.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import com.jinzhun.oauth.service.impl.RedisAuthorizationCodeServices;
import com.jinzhun.oauth.service.impl.RedisClientDetailsService;

/**
 * OAuth2 客户端信息配置
 */
@Configuration
public class ClientDetailsConfig {
    
	@Resource private DataSource dataSource;
    
	@Resource private RedisTemplate<String, Object> redisTemplate;
	
    @Bean
    public RedisClientDetailsService clientDetailsService() {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        clientDetailsService.setRedisTemplate(redisTemplate);
        return clientDetailsService;
    }
    
    @Bean
    public RandomValueAuthorizationCodeServices authorizationCodeServices() {
        RedisAuthorizationCodeServices redisAuthorizationCodeServices = new RedisAuthorizationCodeServices();
        redisAuthorizationCodeServices.setRedisTemplate(redisTemplate);
        return redisAuthorizationCodeServices;
    }
}
