package com.alicp.logapp.log.api;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/25
 * Time: 下午7:04
 * CopyRight: taobao
 * Descrption:
 */

public class WebAppConfig extends WebMvcConfigurerAdapter {
    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PVInterceptor()).addPathPatterns("/*/**");
    }
}
