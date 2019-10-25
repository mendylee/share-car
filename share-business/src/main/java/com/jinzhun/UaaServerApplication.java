package com.jinzhun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableApolloConfig
@EnableDiscoveryClient
@SpringBootApplication
public class UaaServerApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(UaaServerApplication.class, args);
	}

}
