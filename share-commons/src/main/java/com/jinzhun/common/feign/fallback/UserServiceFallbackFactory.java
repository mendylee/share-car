package com.jinzhun.common.feign.fallback;

import org.springframework.stereotype.Component;

import com.jinzhun.common.feign.UserService;
import com.jinzhun.common.model.LoginUser;
import com.jinzhun.common.model.SysUser;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户降级工厂
 */
@Slf4j
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {

	@Override
	public UserService create(Throwable throwable) {
		
        return new UserService() {
            @Override
            public SysUser selectByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new SysUser();
            }

            @Override
            public LoginUser findByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new LoginUser();
            }

            @Override
            public LoginUser findByMobile(String mobile) {
                log.error("通过手机号查询用户异常:{}", mobile, throwable);
                return new LoginUser();
            }

            @Override
            public LoginUser findByOpenId(String openId) {
                log.error("通过openId查询用户异常:{}", openId, throwable);
                return new LoginUser();
            }
        };
	}

}
