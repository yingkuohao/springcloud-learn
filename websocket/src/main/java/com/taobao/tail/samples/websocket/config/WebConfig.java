package com.taobao.tail.samples.websocket.config;

import com.taobao.tail.samples.websocket.echo.DefaultEchoService;
import com.taobao.tail.samples.websocket.echo.EchoWebSocketHandler;
import com.taobao.tail.samples.websocket.snake.SnakeWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

@Configuration
//@EnableWebMvc        //这个注解加上之后上下文路径会有影响,导致css和js用不了
@EnableWebSocket
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {


        registry.addHandler(echoWebSocketHandler(), "/echo");
        registry.addHandler(snakeWebSocketHandler(), "/snake");

        registry.addHandler(echoWebSocketHandler(), "/sockjs/echo").withSockJS();
        registry.addHandler(snakeWebSocketHandler(), "/sockjs/snake").withSockJS();
    }

    @Bean
    public WebSocketHandler echoWebSocketHandler() {
        return new EchoWebSocketHandler(echoService());
    }


    @Bean
    public WebSocketHandler snakeWebSocketHandler() {
        return new PerConnectionWebSocketHandler(SnakeWebSocketHandler.class);
    }

    @Bean
    public DefaultEchoService echoService() {
        return new DefaultEchoService("Did you say \"%s\"?");
    }

    // Allow serving HTML files through the default Servlet

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
