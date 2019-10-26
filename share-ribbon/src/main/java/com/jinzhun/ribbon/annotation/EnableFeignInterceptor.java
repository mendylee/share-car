package com.jinzhun.ribbon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.jinzhun.ribbon.config.FeignInterceptorConfig;

/**
 * 开启自动登录用户对象注入
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(FeignInterceptorConfig.class)
public @interface EnableFeignInterceptor {

}
