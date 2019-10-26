package com.jinzhun.auth2.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

/**
 * 权限服务接口
 */
public interface IPermissionService {

	/**
	 * 判断请求是否有权限
	 * 
	 * @param authentication 认证信息
	 * @return 是否有权限
	 */
	boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
