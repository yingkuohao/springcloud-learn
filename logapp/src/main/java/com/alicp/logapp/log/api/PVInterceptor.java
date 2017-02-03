package com.alicp.logapp.log.api;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/25
 * Time: 下午6:28
 * CopyRight: taobao
 * Descrption:
 */
@Service
public class PVInterceptor implements HandlerInterceptor {
    private static final Logger logger = AlicpLog.biz;

    @Autowired
    private CounterService counterService;
    @Autowired
    private GaugeService gaugeService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        logger.info("SecurityHandlerInterceptor preHanler execute!");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method != null) {
            PV pv = method.getAnnotation(PV.class);
            if (pv != null) {
                String key = pv.key();
                if (StringUtils.isNotBlank(key)) {
                    //如果有PV的注解,就++
                    String pvKey = "JPV" + key + "_" + method.getName();
                    this.counterService.increment(pvKey);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
