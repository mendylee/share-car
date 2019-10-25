package com.jinzhun.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ZltUserDetailsService extends UserDetailsService {

	UserDetails loadUserByMobile(String mobile);
}
