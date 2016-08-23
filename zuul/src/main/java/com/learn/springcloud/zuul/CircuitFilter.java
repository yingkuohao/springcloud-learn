package com.learn.springcloud.zuul;

import com.learn.springcloud.zuul.consts.SentinelUtil;
import com.learn.springcloud.zuul.consts.ZuulConsts;
import com.learn.springcloud.zuul.consts.ZuulUtil;
import com.learn.springcloud.zuul.hystrix.ResourceVO;
import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

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
    ThreadLocal<ResourceVO> threadLocal = new ThreadLocal<ResourceVO>();

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
        //解析uri, 遍历限流map,判断有没有符合的条件的
        ResourceVO resourceVO = SentinelUtil.getResourceByUri(ZuulUtil.getURI());
        System.out.println("---resourceVO---" + resourceVO.toString());
        threadLocal.set(resourceVO);
        return resourceVO != null;
    }

    @Override
    public Object run() {
        log.info("--2. CircuitFilter run!");
        //yingkhtodo:desc:forward
        ResourceVO resourceVO = threadLocal.get();
        if (resourceVO.outOfFlow()) {
            //如果超过流控,则进行callback 回调.
            String callbackurl = resourceVO.getCallback();
            if (StringUtils.isEmpty(callbackurl)) {
                callbackurl = "/failover";
            }
            try {
                log.error("--out of flow ,failover---");
                ZuulUtil.getResponse().sendRedirect(callbackurl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
