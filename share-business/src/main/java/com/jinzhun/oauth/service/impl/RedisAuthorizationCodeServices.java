package com.jinzhun.oauth.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * Redis随机授权码实现，生成随机的授权码，默认授权码有效时间为：10min
 */
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
	
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		redisTemplate.opsForValue().set(redisKey(code), authentication, 10, TimeUnit.MINUTES);
	}

	@Override
	protected OAuth2Authentication remove(String code) {
        String codeKey = redisKey(code);
        OAuth2Authentication token = (OAuth2Authentication) redisTemplate.opsForValue().get(codeKey);
        this.redisTemplate.delete(codeKey);
        return token;
	}

    private String redisKey(String code) {
        return "oauth:code:" + code;
    }
}
