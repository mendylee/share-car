package com.jinzhun.auth2.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.AntPathMatcher;

import com.jinzhun.auth2.properties.SecurityProperties;
import com.jinzhun.auth2.service.IPermissionService;
import com.jinzhun.auth2.util.AuthUtils;
import com.jinzhun.common.constant.CommonConstant;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultPermissionServiceImpl implements IPermissionService {
    
	@Autowired private SecurityProperties securityProperties;
	
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		if(HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
	          //判断是否开启url权限验证
            if (!securityProperties.getAuth().isUrlEnabled()) {
                return true;
            }
            //超级管理员admin不需认证
            String username = AuthUtils.getUsername(authentication);
            if (CommonConstant.ADMIN_USER_NAME.equals(username)) {
                return true;
            }
            //判断认证通过后，所有用户都能访问的url
            for (String path : securityProperties.getIgnore().getMenusPaths()) {
                if (antPathMatcher.match(path, request.getRequestURI())) {
                    return true;
                }
            }
            List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }
		}
		return false;
	}

}
