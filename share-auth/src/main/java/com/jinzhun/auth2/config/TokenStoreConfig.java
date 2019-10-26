package com.jinzhun.auth2.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.jinzhun.auth2.store.AuthDbTokenStore;
import com.jinzhun.auth2.store.AuthJwtTokenStore;
import com.jinzhun.auth2.store.AuthRedisTokenStore;
import com.jinzhun.auth2.store.ResJwtTokenStore;

/**
 * Token存储配置，支持JDBC,Redis,JWT,ResJWT
 */
public class TokenStoreConfig {

	/**
	 * 数据库token存储配置
	 */
    @Configuration
    @ConditionalOnProperty(prefix = "oauth2.token.store", name = "type", havingValue = "db")
    @Import(AuthDbTokenStore.class)
    public class JdbcTokenConfig {}
    
	/**
	 * Redis token存储配置
	 */
    @Configuration
    @ConditionalOnProperty(prefix = "oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
    @Import(AuthRedisTokenStore.class)
    public class RedisTokenConfig {}
    
	/**
	 * JWT RSA 非对称加密
	 */
    @Configuration
    @ConditionalOnProperty(prefix = "oauth2.token.store", name = "type", havingValue = "authJwt")
    @Import(AuthJwtTokenStore.class)
    public class AuthJwtTokenConfig {}
    
    @Configuration
    @ConditionalOnProperty(prefix = "oauth2.token.store", name = "type", havingValue = "resJwt")
    @Import(ResJwtTokenStore.class)
    public class ResJwtTokenConfig {}
}
