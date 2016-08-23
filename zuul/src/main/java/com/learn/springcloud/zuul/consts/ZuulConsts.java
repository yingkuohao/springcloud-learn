package com.learn.springcloud.zuul.consts;

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

        public static  Map<String, LongAdder> pvMap = new ConcurrentHashMap<String, LongAdder>();

    public static void main(String[] args) {
    }
}
