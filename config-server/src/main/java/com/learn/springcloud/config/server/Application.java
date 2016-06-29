package com.learn.springcloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/6/24
 * Time: 下午3:22
 * CopyRight: taobao
 * Descrption:
 * 启动后通过:http://localhost:8888/test/default访问
 *
 * http://localhost:8888/fool/daily
 */
@Configuration
@EnableAutoConfiguration
@RestController
@EnableConfigServer

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
