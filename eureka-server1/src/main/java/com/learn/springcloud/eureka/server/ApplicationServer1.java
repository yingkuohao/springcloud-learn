package com.learn.springcloud.eureka.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *   @EnableEurekaServer:  此注解表明该服务为一个eureka服务,可以联合多个服务作为集群,对外提供服务注册及发现功能
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ApplicationServer1 {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationServer1.class).web(true).run(args);
    }

}
