package com.taobao.tail.config;

import com.taobao.tail.logcore.EchoLogLsHandler;
import com.taobao.tail.logcore.TailLogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/13
 * Time: 下午5:38
 * CopyRight: taobao
 * Descrption:
 */
@Configuration
@EnableWebSocket
public class WsConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(echoLogLsHandler(), "/logls");        //注册日志ls的handler
        registry.addHandler(tailLogHandler(), "/logtail");        //注册日志ls的handler

    }

    @Bean
    public WebSocketHandler echoLogLsHandler() {
        return new EchoLogLsHandler();
    }

    @Bean
    public WebSocketHandler tailLogHandler() {
        return new TailLogHandler();
    }
}
