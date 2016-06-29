package com.learn.springcloud.eureka.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @EnableEurekaClient: 该注解表明应用既作为eureka实例又为eureka client 可以发现注册的服务
 * 访问 http://localhost:9090/discovery 实现服务发现。
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableAutoConfiguration
public class ApplicationClient {
    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationClient.class).web(true).run(args);
    }
}
