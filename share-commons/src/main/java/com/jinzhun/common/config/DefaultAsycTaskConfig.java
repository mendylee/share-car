package com.jinzhun.common.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EnableAsync(proxyTargetClass = true)
public class DefaultAsycTaskConfig {

	/**
	 * 线程池维护线程的最小数量.
	 */
	@Value("${asyc-task.corePoolSize:10}")
	private int corePoolSize;
	
	/**
	 * 线程池维护线程的最大数量
	 */
	@Value("${asyc-task.maxPoolSize:200}")
	private int maxPoolSize;
	
	/**
	 * 队列最大长度
	 */
	@Value("${asyc-task.queueCapacity:10}")
	private int queueCapacity;
	
	/**
	 * 线程池前缀
	 */
	@Value("${asyc-task.threadNamePrefix:ZltExecutor-}")
	private String threadNamePrefix;
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
}
