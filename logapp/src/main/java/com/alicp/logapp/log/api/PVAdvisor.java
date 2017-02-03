package com.alicp.logapp.log.api;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/25
 * Time: 下午6:54
 * CopyRight: taobao
 * Descrption:
 */
@Configuration
public class PVAdvisor {

    private static final Logger logger = AlicpLog.biz;
    @Autowired
    private CounterService counterService;

    @Bean
    public RegexpMethodPointcutAdvisor advisor() {
        RegexpMethodPointcutAdvisor advisor = new RegexpMethodPointcutAdvisor();

        advisor.setPattern("com.logclient.test.conroller.*Controller.*");
        advisor.setAdvice(new MethodInterceptor() {

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("1111");
                long begin = System.nanoTime();
                Method method = invocation.getMethod();
                long end = System.nanoTime();
                System.out.println("interceptor total time=" + (end - begin));

                if (method != null) {
                    PV pv = method.getDeclaredAnnotation(PV.class);
                    if (pv != null) {
                        String key = pv.key();
                        if (StringUtils.isNotBlank(key)) {
                            //如果有PV的注解,就++
                            String pvKey = "JPV" + key + "_" + method.getName();
                            counterService.increment(pvKey);
                        }
                    }
                }
                return invocation.proceed();
            }

        });
        return advisor;
    }
}
