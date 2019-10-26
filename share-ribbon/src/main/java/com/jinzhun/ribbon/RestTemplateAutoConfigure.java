package com.jinzhun.ribbon;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.jinzhun.ribbon.config.RestTemplateProperties;

@EnableConfigurationProperties(RestTemplateProperties.class)
public class RestTemplateAutoConfigure {
    
	@Autowired private RestTemplateProperties restTemplateProperties;
   
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory());
        return restTemplate;
    }
    
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }
    
    @Bean
	public HttpClient httpClient() {
    	RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
    	registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
    	registryBuilder.register("https", SSLConnectionSocketFactory.getSocketFactory());
		Registry<ConnectionSocketFactory> registry = registryBuilder.build();
		
		// 连接沲管理
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
		// 最大链接数
		connectionManager.setMaxTotal(restTemplateProperties.getMaxTotal());
		// 同路由并发数20
		connectionManager.setDefaultMaxPerRoute(restTemplateProperties.getMaxPerRoute());

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(restTemplateProperties.getReadTimeout())// 读超时
				.setConnectTimeout(restTemplateProperties.getConnectTimeout())// 链接超时
				.setConnectionRequestTimeout(restTemplateProperties.getReadTimeout()).build();// 链接不够用的等待时间

		//HttpClient请求构建
		HttpClientBuilder clientBuilder = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig);
		clientBuilder.setConnectionManager(connectionManager);
		clientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
		
		return clientBuilder.build();
	}
}
