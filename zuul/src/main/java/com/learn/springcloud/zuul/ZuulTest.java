package com.learn.springcloud.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.ContextLifecycleFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/7/18
 * Time: 下午2:01
 * CopyRight: taobao
 * Descrption:
 * zuul可以通过加载动态过滤机制,从而实现下面各项功能:
 * 1. 验证与安全保障:识别面向各类资源的验证要求并拒绝哪些与要求不符合的请求
 * 2. 审查与监控: 在边缘位置追踪有意义数据及统计结果, 从而为我们带来准确的生产状态结论.
 * 3. 动态路由: 以动态方式根据需要将请求路由至不同后端集群处.
 * 4. 压力测试:逐渐增加指向集群的负载流量,从而计算性能水平.
 * 5. 负载分配:为每一种负载类型分配对应容量,并弃用超出限定值得请求.
 * 6. 静态响应处理: 在边缘位置直接建立部分响应,从而避免其流入内部集群.
 * 7.  多区域弹性: 跨越AWS区域进行请求路由，旨在实现ELB使用多样化并保证边缘位置与使用者尽可能接近。
 */

@EnableZuulProxy
@SpringBootApplication
public class ZuulTest {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulTest.class).web(true).run(args);
    }

    @Component
    public static class MyCommandLineRunner implements CommandLineRunner {
        int count = 0;

        @Override
        public void run(String... strings) throws Exception {
            MonitoringHelper.initMocks();
            initJavaFilters();
        }

        private void initJavaFilters() {
            final FilterRegistry r = FilterRegistry.instance();
            //yingkhtodo:desc:pre过滤器可以做一些黑名单,权限等校验
            r.put("javaPreFilter", new ZuulFilter() {
                @Override
                public String filterType() {
                    return "pre";
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
                    count++;
                    System.out.println("running javaPreFilter!");
                    if (count < 100) {

                        RequestContext.getCurrentContext().set("name", "pre!");
                    } else {
                        RequestContext.getCurrentContext().set("error", "超过访问量!");

                    }

                    //黑白名单校验
                    //权限过滤
                    //
                    return null;
                }
            });

            //yingkhtodo:desc:
            r.put("javaRoutingFilter", new ZuulFilter() {
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
                    System.out.println("running javaRoutingFilter!");
                    try {
                        RequestContext requestContext = RequestContext.getCurrentContext();
                        Object id = requestContext.getRequest().getParameter("id");
                        if (id != null && "1".equals(id)) {
                            RequestContext.getCurrentContext().getResponse().sendRedirect("http://blog.csdn.net/liaokailin/");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });

            r.put("javaPostFilter", new ZuulFilter() {
                @Override
                public String filterType() {
                    return "post";
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
                    System.out.println("running javaPostFilter!");
                    Object value = RequestContext.getCurrentContext().get("name");
                    if (value != null) {
                        System.out.println(value.toString());
                    }
                    return null;
                }
            });
        }
    }

    @Bean
    public ServletRegistrationBean zuulServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new ZuulServlet());
        servlet.addUrlMappings("/test");
        return servlet;
    }

    @Bean
    public FilterRegistrationBean contextLIfecycleFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean(new ContextLifecycleFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }

}
