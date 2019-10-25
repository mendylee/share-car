package com.jinzhun.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 微信授权登录配置
 */
@Configuration
public class WechatConfiguration {

	/**
	 * 客户端id：对应各平台的appKey
	 */
	@Value("${wechat.client_id}")
	private String clientId;

	/**
	 * 客户端Secret：对应各平台的appSecret
	 */
	@Value("${wechat.clientSecret}")
	private String clientSecret;

	/**
	 * 登录成功后的回调地址
	 */
	@Value("${wechat.redirectUri}")
	private String redirectUri;

	/**
	 * 访问AuthorizeUrl后回调时带的参数code
	 */
	@Value("${wechat.code}")
	private String code;

	/**
	 * 访问AuthorizeUrl后回调时带的参数state，用于和请求AuthorizeUrl前的state比较，防止CSRF攻击
	 */
	@Value("${wechat.state}")
	private String state;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
