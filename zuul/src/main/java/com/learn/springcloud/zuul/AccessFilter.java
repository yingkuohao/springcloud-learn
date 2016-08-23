package com.learn.springcloud.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/1
 * Time: 下午7:39
 * CopyRight: taobao
 * Descrption:权限filter
 * <p/>
 * 1. 校验App是否合法
 * 2. 校验token是否合法
 * 3. ip是否在黑名单中
 * 4. ip是否在白名单中
 */

public class AccessFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return FilterTypeEnum.pre.getCode();         //指定filter类型为PRE
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("---1. pre run---");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        log.info("getRouteHost:" + ctx.getRouteHost());
        //校验入参中是否有token
        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                //如果token不合法跳转到error页
                log.warn("access token is empty");
                ctx.getResponse().sendRedirect("/errorpage");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("---access token ok----");
        return null;
    }

}
