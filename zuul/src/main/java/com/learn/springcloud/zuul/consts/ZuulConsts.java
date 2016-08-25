package com.learn.springcloud.zuul.consts;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.learn.springcloud.zuul.hystrix.ResourceVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/22
 * Time: 下午8:42
 * CopyRight: taobao
 * Descrption:
 */

public class ZuulConsts {

    public static List<String> blackUrlList = new ArrayList<String>();

    public static List<String> whiteUrlList = new ArrayList<String>();

    public static List<String> blackIPList = new ArrayList<String>();

    public static final String uri_prefix_pattern = "\\alicp\\.*";

    public static Map<String, LongAdder> pvMap = new ConcurrentHashMap<String, LongAdder>();


    public static Map<String, ResourceVO> sentinelMap = new HashMap<String, ResourceVO>(); //限流map,保存用户的配置信息

    public static Map<String, ConcurrentLinkedHashMap> lruMap = new HashMap<String, ConcurrentLinkedHashMap>(); //保存资源的实际请求流水

    public static String ALICP_HEADER_VERSION = "x-ca-version";
    public static String ALICP_HEADER_COPYRIGHT = "x-ca-copyright";
    public static String    ALICP_HEADER_EXCEPTION = "x-ca-exception";

    public static String ALICP_FAILOVER_URL = "/failover";

    public static void main(String[] args) {
    }
}
