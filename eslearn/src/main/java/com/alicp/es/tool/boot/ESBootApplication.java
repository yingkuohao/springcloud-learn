package com.alicp.es.tool.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 村淘服务启动类
 * @author risheng
 */
@EnableAutoConfiguration
@ComponentScan({"com.alicp.es","com.alicp.middleware.log"})
@SpringBootApplication
public class ESBootApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ESBootApplication.class, args);
	}
	
}
