package com.jinzhun.auth2.store;

import java.security.KeyPair;

import javax.annotation.Resource;

import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 认证服务器使用 JWT RSA 非对称加密令牌
 */
public class AuthJwtTokenStore {

    @Bean("keyProp")
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;
    
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }
    
    @Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(keyProperties.getKeyStore().getLocation(),
												 keyProperties.getKeyStore().getSecret().toCharArray())
												.getKeyPair(keyProperties.getKeyStore().getAlias());
		converter.setKeyPair(keyPair);
		return converter;
	}
}
