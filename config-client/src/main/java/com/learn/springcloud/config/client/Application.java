package com.learn.springcloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/6/24
 * Time: ÏÂÎç3:22
 * CopyRight: taobao
 * Descrption:
 *
 */
@EnableAutoConfiguration
@ComponentScan
@RestController
@RefreshScope
public class Application {

        @Value("${name:World!}")
        String bar;

        @RequestMapping("/")
        String hello() {
            return "Hello " + bar + "!";
        }

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
}
