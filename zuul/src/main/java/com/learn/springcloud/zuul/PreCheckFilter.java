package com.learn.springcloud.zuul;

import com.learn.springcloud.zuul.consts.ZuulConsts;
import com.learn.springcloud.zuul.consts.ZuulUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/22
 * Time: 下午7:53
 * CopyRight: taobao
 * Descrption:
 * 识别面向各类资源的验证并拒绝那些与要求不符的请求
 */
@Component
public class PreCheckFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(PreCheckFilter.class);

    @Override
    public String filterType() {
        return FilterTypeEnum.pre.getCode();
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //校验要不要拦截,以alicp开头的才拦截
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String path = request.getRequestURI();
        return ZuulUtil.format(path, ZuulConsts.uri_prefix_pattern);
    }

    @Override
    public Object run() {
        log.info("--0. PreCheckFilter run!");
        //yingkhtodo:desc:forward
        return null;
    }



}
