package com.learn.springcloud.zuul;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/2
 * Time: 下午8:33
 * CopyRight: taobao
 * Descrption:      路由
 */

public class RoutingFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("---routing run---");
        System.out.println("---routing run---");
        return null;
    }
}
