package com.learn.springcloud.zuul;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/22
 * Time: 下午7:50
 * CopyRight: taobao
 * Descrption:   loadBalance:
 * 负载均衡filter
 *压力测试:动态增加负载流量,从而计算性能水平
 */
@Component
public class LBFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(LBFilter.class);

          @Override
          public String filterType() {
              return FilterTypeEnum.pre.getCode();
          }

          @Override
          public int filterOrder() {
              return 3;
          }

          @Override
          public boolean shouldFilter() {
              return true;
          }

          @Override
          public Object run() {
              log.info("---3. LBFilter run!");
              //yingkhtodo:desc:forward
              return null;
          }
}
