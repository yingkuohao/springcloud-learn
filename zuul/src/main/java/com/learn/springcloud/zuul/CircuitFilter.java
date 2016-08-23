package com.learn.springcloud.zuul;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/22
 * Time: 下午7:49
 * CopyRight: taobao
 * Descrption:熔断filter
 * 当请求超过阈值时直接forward到
 */
@Component
public class CircuitFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(CircuitFilter.class);

    @Override
    public String filterType() {
        return FilterTypeEnum.pre.getCode();
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("--2. CircuitFilter run!");
        //yingkhtodo:desc:forward
        return null;
    }
}
