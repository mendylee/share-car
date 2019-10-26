package com.jinzhun.common.constant;

/**
 * 全局公共常量
 */
public interface CommonConstant {

	/**
	 * token请求头名称
	 */
	String TOKEN_HEADER = "Authorization";

	/**
	 * 超级管理员用户名
	 */
	String ADMIN_USER_NAME = "admin";

	/**
	 * 公共日期格式
	 */
	String MONTH_FORMAT = "yyyy-MM";
	String DATE_FORMAT = "yyyy-MM-dd";
	String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	String SIMPLE_MONTH_FORMAT = "yyyyMM";
	String SIMPLE_DATE_FORMAT = "yyyyMMdd";
	String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";
	
	/**
	 * 默认用户密码
	 */
	String DEF_USER_PASSWORD = "123456";
	
	/**
	 * 锁的key前缀
	 */
	String LOCK_KEY_PREFIX = "LOCK_KEY:";
}
