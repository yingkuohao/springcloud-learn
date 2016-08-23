package com.learn.springcloud.zuul;

import com.learn.springcloud.zuul.consts.ZuulConsts;
import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/22
 * Time: 下午7:56
 * CopyRight: taobao
 * Descrption:静态响应处理,避免流量流入集群
 */
@Component
public class StaticResponseFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(StaticResponseFilter.class);

        @Override
        public String filterType() {
            return FilterTypeEnum.pre.getCode();
        }

        @Override
        public int filterOrder() {
            return 4;
        }

        @Override
        public boolean shouldFilter() {
            return true;
        }

        @Override
        public Object run() {
            log.info("---4.StaticResponseFilter run!");
            //yingkhtodo:desc:forward


            return null;
        }
}
