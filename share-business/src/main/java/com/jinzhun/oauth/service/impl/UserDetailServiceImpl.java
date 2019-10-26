package com.jinzhun.oauth.service.impl;

import javax.annotation.Resource;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import com.jinzhun.common.feign.UserService;
import com.jinzhun.common.model.LoginUser;
import com.jinzhun.oauth.service.ZltUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailServiceImpl implements ZltUserDetailsService, SocialUserDetailsService {
	
	@Resource UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser loginUser = userService.findByUsername(username);
		if (loginUser == null) {
			log.error("loadUserByUsername:{}","用户名或密码错误");
			throw new InternalAuthenticationServiceException("用户名或密码错误");
		}
		return checkUser(loginUser);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String openId) throws UsernameNotFoundException {
		LoginUser loginUser = userService.findByOpenId(openId);
		return checkUser(loginUser);
	}

	@Override
	public UserDetails loadUserByMobile(String mobile) {
		LoginUser loginUser = userService.findByMobile(mobile);
		return checkUser(loginUser);
	}

	private LoginUser checkUser(LoginUser loginUser) {
		return loginUser;
	}

}
