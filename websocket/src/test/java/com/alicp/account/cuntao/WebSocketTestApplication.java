package com.alicp.account.cuntao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 村淘服务启动类
 * @author risheng
 */
@EnableAutoConfiguration
@ComponentScan("com.taobao.tail")
@SpringBootApplication
public class WebSocketTestApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WebSocketTestApplication.class, args);
	}
	
}
