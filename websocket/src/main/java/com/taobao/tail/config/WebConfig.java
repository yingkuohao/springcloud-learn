package com.taobao.tail.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/10
 * Time: 下午5:43
 * CopyRight: taobao
 * Descrption:
 */
@Configuration
public class WebConfig {


    @Bean(name = "velocityViewResolver")
    public VelocityLayoutViewResolver getVelocityLayoutViewResolver() {
        VelocityLayoutViewResolver vr = new VelocityLayoutViewResolver();
        vr.setCache(true);
        vr.setPrefix("/templates/");
        vr.setSuffix(".vm");
        vr.setLayoutUrl("layout.vm");
        vr.setContentType("text/html;charset=UTF-8");
        vr.setExposeSpringMacroHelpers(true);
        vr.setLayoutKey("layout");
        vr.setScreenContentKey("screen_content");
        vr.setDateToolAttribute("dateTool");
        vr.setNumberToolAttribute("numberTool");
        vr.setAllowRequestOverride(true);
        vr.setAllowSessionOverride(true);
        vr.setExposeRequestAttributes(true);
        vr.setExposeSessionAttributes(true);
        vr.setRequestContextAttribute("rc");
        return vr;
    }

//    @Bean
       public FilterRegistrationBean filterRegistrationBean(){
           FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//           filterRegistrationBean.setFilter(myFilter);
           filterRegistrationBean.setEnabled(true);
           filterRegistrationBean.addUrlPatterns("/web");
           return filterRegistrationBean;
       }
}
