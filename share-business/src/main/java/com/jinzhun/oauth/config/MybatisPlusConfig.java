package com.jinzhun.oauth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import com.jinzhun.db.config.DefaultMybatisPlusConfig;

@Configuration
@MapperScan({"com.jinzhun.oauth.mapper*"})
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

}
