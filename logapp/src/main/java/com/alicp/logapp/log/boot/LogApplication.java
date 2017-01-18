package com.alicp.logapp.log.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 村淘服务启动类
 * @author risheng
 */
@EnableAutoConfiguration
@ComponentScan("com.alicp.logapp.log")
@SpringBootApplication
public class LogApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LogApplication.class, args);
	}
	
}
