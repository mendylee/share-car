package com.jinzhun.common.constant;

public interface SecurityConstants {

	/**
	 * 缓存client的redis key，这里是hash结构存储
	 */
	String CACHE_CLIENT_KEY = "oauth_client_details";

	/**
	 * OAUTH模式登录处理地址
	 */
	String OAUTH_LOGIN_PRO_URL = "/user/login";
	/**
	 * PASSWORD模式登录处理地址
	 */
	String PASSWORD_LOGIN_PRO_URL = "/oauth/user/token";
	/**
	 * 获取授权码地址
	 */
	String AUTH_CODE_URL = "/oauth/authorize";
	/**
	 * 默认的OPENID登录请求处理url
	 */
	String OPENID_TOKEN_URL = "/oauth/openId/token";
	/**
	 * 手机登录URL
	 */
	String MOBILE_TOKEN_URL = "/oauth/mobile/token";
	/**
	 * 登出URL
	 */
	String LOGOUT_URL = "/oauth/remove/token";
}
