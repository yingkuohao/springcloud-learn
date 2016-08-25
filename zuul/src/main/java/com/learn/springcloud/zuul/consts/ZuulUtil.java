package com.learn.springcloud.zuul.consts;

import com.learn.springcloud.zuul.hystrix.ResourceVO;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/22
 * Time: 下午8:42
 * CopyRight: taobao
 * Descrption:
 */

public class ZuulUtil {

    public static final String SERVICE_A = "serviceA";

    static {
        init();
    }


    static void init() {
        ZuulConsts.blackUrlList.add("/black/*");
        ZuulConsts.whiteUrlList.add("/white/*");

        ZuulConsts.blackIPList.add("127.0.0.1");
        initSentinalPool();
    }


    private static void initSentinalPool() {
        ResourceVO resourceVO = new ResourceVO();
        resourceVO.setUrlPattern("/serviceA");
        resourceVO.setTime(60);
        resourceVO.setCapacity(6);
        resourceVO.setCallback("/failover");
        resourceVO.setBizId(SERVICE_A);
        //初始化用户自定义的限流业务策略
        ZuulConsts.sentinelMap.put(SERVICE_A, resourceVO);
        //初始化LRUmap
        ZuulConsts.lruMap.put(SERVICE_A, SentinelUtil.getLRUMap(resourceVO));
    }


    /**
     * checks if the path matches the uri()
     *
     * @param path usually the RequestURI()
     * @return true if the pattern matches
     */
    public static boolean checkPath(String path) {
        if (ZuulConsts.blackUrlList.contains(path)) {
            return false;
        } else if (ZuulConsts.whiteUrlList.contains(path)) {
            return true;
        }
        return true;
    }

    static boolean checkIP(String IP) {
        if (ZuulConsts.blackIPList.contains(IP)) {
            return false;
        }
        return true;
    }


    public static boolean format(String path, String patternStr) {
        boolean tag = true;
        final Pattern pattern = Pattern.compile(patternStr);
        final Matcher mat = pattern.matcher(path);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    public static String getURI() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();
        return uri;
    }

    public static HttpServletRequest getRequest() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest();
    }

    public static HttpServletResponse getResponse() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getResponse();
    }


    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");

    }

    public static boolean isPreException() {
        return ZuulUtil.getResponse().containsHeader(ZuulConsts.ALICP_HEADER_EXCEPTION);
    }

}
