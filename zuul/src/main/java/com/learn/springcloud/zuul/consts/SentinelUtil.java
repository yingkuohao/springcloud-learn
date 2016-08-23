package com.learn.springcloud.zuul.consts;

import com.learn.springcloud.zuul.hystrix.ResourceVO;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/23
 * Time: 下午4:34
 * CopyRight: taobao
 * Descrption:
 */

public class SentinelUtil {


    public static  void increment(String uri) {
        ResourceVO resourceVO = getResourceByUri(uri);
        if (resourceVO != null) {
            resourceVO.getCurrentViewCount().increment();
        }
    }

    /**
     * pv自增
     * @param resourceVO
     */
    public static void increment(ResourceVO resourceVO) {
        resourceVO.getCurrentViewCount().increment();
    }

    /**
     * 根据资源路径找到资源对象
     * @param uri
     * @return
     */
    public static ResourceVO getResourceByUri(String uri) {
        for (Map.Entry<String, ResourceVO> entry : ZuulConsts.sentinelMap.entrySet()) {
            ResourceVO resourceVO = entry.getValue();
            //regex
            boolean isMatch = ZuulUtil.format(uri, resourceVO.getUrlPattern());
            if (isMatch) {
                return resourceVO;
            }
        }
        return null;
    }
}
