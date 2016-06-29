package com.learn.springcloud.eureka.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *  此模块代表一个注册管理器,即eureka的服务器,服务启动时会自动注册自己到eureka服务器,每一个服务都有一个名字,
 *  这个名字会被注册到eureka服务器.使用服务的一方只需要使用该名字加上方法名就可以调到服务.
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServer.class).web(true).run(args);
    }

}
