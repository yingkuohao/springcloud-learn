package com.learn.springcloud.zuul;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/7/18
 * Time: 下午3:17
 * CopyRight: taobao
 * Descrption:
 */
@EnableZuulProxy
@SpringCloudApplication
public class App {
    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).web(true).run(args);
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    public RoutingFilter routingFilter() {
        return new RoutingFilter();
    }
}
