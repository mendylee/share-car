package com.jinzhun.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jinzhun.common.constant.ServiceNameConstants;
import com.jinzhun.common.feign.fallback.UserServiceFallbackFactory;
import com.jinzhun.common.model.LoginUser;
import com.jinzhun.common.model.SysUser;

@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {

	/**
	 * feign rpc访问远程/users/{username}接口 查询用户实体对象SysUser
	 *
	 * @param username	用户名称
	 * @return
	 */
    @GetMapping(value = "/users/name/{username}")
    SysUser selectByUsername(@PathVariable("username") String username);
    
	/**
	 * feign rpc访问远程/users-anon/login接口
	 *
	 * @param username 用户名称
	 * @return
	 */
    @GetMapping(value = "/users/login", params = "username")
    LoginUser findByUsername(@RequestParam("username") String username);
    
	/**
	 * 通过手机号查询用户、角色信息
	 *
	 * @param mobile 手机号
	 */
    @GetMapping(value = "/users/mobile", params = "mobile")
    LoginUser findByMobile(@RequestParam("mobile") String mobile);
    
	/**
	 * 根据OpenId查询用户信息
	 *
	 * @param openId openId
	 */
    @GetMapping(value = "/users/openId", params = "openId")
    LoginUser findByOpenId(@RequestParam("openId") String openId);
}
