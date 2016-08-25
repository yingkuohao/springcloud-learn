package com.learn.springcloud.zuul.consts;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;
import com.learn.springcloud.zuul.hystrix.ResourceVO;

import java.time.Instant;
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


    public static void increment(String uri, String ip) {
        ResourceVO resourceVO = getResourceByUri(uri);
        if (resourceVO != null) {
            resourceVO.getCurrentViewCount().increment();
        }
        ConcurrentLinkedHashMap<String, Long> lruMap = SentinelUtil.getLRUMapByBizId(resourceVO.getBizId());
        if (lruMap == null) {
            lruMap = getLRUMap(resourceVO);
            ZuulConsts.lruMap.put(resourceVO.getBizId(), lruMap);
        }
        //这里的key以uri和ip和时间戳拼接,防止同一个ip狂刷,value为时间戳
        String key = uri + "-" + ip + "-";
        key += Instant.now().toString();
        lruMap.put(key, Instant.now().toEpochMilli());
    }

    /**
     * pv自增
     *
     * @param resourceVO
     */
    public static void increment(ResourceVO resourceVO) {
        resourceVO.getCurrentViewCount().increment();
    }

    public static ConcurrentLinkedHashMap<String, Long> getLRUMapByBizId(String uri) {
        return ZuulConsts.lruMap.get(uri);
    }

    /**
     * 根据资源路径找到资源对象
     *
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

    /**
     * 获取LRUmap,实现缓存
     *
     * @param resourceVO
     * @return
     */
    public static ConcurrentLinkedHashMap<String, Long> getLRUMap(ResourceVO resourceVO) {
        Integer capacity = resourceVO.getCapacity();
        final int limitTime = resourceVO.getTime();
        //key: ip  value:timestamp
        EvictionListener<String, Long> listener = new EvictionListener<String, Long>() {

            @Override
            public void onEviction(String key, Long value) {     //失效监听
                System.out.println("Evicted key=" + key + ", value=" + value);
                //当触发失效了,说明队列满了,这时判断被失效的这个元素的时间戳是不是距离当前时间在limit时间内,如果在则说明在limit时间内访问的次数过多了,需要限流
                boolean outofFlow = Instant.ofEpochMilli(value).plusMillis(limitTime).isAfter(Instant.now());
                System.out.println("outofFlow=" + outofFlow);
            }
        };
        ConcurrentLinkedHashMap<String, Long> map = new ConcurrentLinkedHashMap.Builder<String, Long>().maximumWeightedCapacity(capacity).listener(listener).build();
        return map;
    }


    public static boolean outOfFlow(String uri) {
        ResourceVO resourceVO = getResourceByUri(uri);
        ConcurrentLinkedHashMap<String, Long> lruMap = SentinelUtil.getLRUMapByBizId(resourceVO.getBizId());
        System.out.println("lruMap=" + lruMap.ascendingMap().toString());
        //获取第一个元素
        Long pre = lruMap.ascendingMap().entrySet().iterator().next().getValue();
//        Object[] keyArr = lruMap.keySet().toArray();
//        Long pre = lruMap.get(keyArr[0]);
//        Long last = lruMap.get(keyArr[keyArr.length - 1]);
        //校验第一个节点加上用户设定的时间是否大于当前时间,如果大于说明超过阈值.      如果lru队列已满,且首节点时间在距离当前时间的limittime内,说明溢出
        return lruMap.size() == resourceVO.getCapacity() && Instant.ofEpochMilli(pre).plusSeconds(resourceVO.getTime()).isAfter(Instant.now());
    }

    public static void main(String[] args) {
        ZuulUtil zuulUtil = new ZuulUtil();
        String uri = "/serviceA/test1";
        SentinelUtil.increment(uri, "127.0.0.1");
        SentinelUtil.increment(uri, "127.0.0.2");
        SentinelUtil.increment(uri, "127.0.0.3");
        SentinelUtil.increment(uri, "127.0.0.4");
        System.out.println("outOflow" + outOfFlow(uri));
        SentinelUtil.increment(uri, "127.0.0.5");
        System.out.println("outOflow" + outOfFlow(uri));
        SentinelUtil.increment(uri, "127.0.0.6");
        System.out.println("outOflow" + outOfFlow(uri));
        SentinelUtil.increment(uri, "127.0.0.1");
        System.out.println("outOflow" + outOfFlow(uri));
        SentinelUtil.increment(uri, "127.0.0.1");
        System.out.println("outOflow" + outOfFlow(uri));
        SentinelUtil.increment(uri, "127.0.0.1");
        System.out.println("outOflow" + outOfFlow(uri));
    }
}
