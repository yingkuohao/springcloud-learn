package com.learn.springcloud.zuul;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/7/18
 * Time: 下午3:17
 * CopyRight: taobao
 * Descrption:
 */
@EnableZuulProxy
//@SpringBootApplication
public class App {
    public static void main( String[] args ) {
            SpringApplication.run(App.class, args);
        }
}
