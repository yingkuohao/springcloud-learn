package com.learn.springcloud.hytrix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/6/27
 * Time: 下午5:51
 * CopyRight: taobao
 * Descrption:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class RibbonApplication {

    @Bean
    	@LoadBalanced
    	RestTemplate restTemplate() {
    		return new RestTemplate();
    	}

    	public static void main(String[] args) {
    		SpringApplication.run(RibbonApplication.class, args);
    	}
}
