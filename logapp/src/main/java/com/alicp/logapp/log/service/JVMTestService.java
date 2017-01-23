package com.alicp.logapp.log.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/23
 * Time: 上午11:36
 * CopyRight: taobao
 * Descrption:
 */
@Component
public class JVMTestService {

    private static final int _1M = 1024 * 1024;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    //设定-Xmn=10M
    public void testMinorGC() {
        logger.info("testMinorGC execute");
        byte[] alloc1, alloc2, alloc3, alloc4;
        int n = new Random().nextInt(2);
        alloc1 = new byte[2 * n * _1M];
        alloc2 = new byte[2 * n * _1M];
        alloc3 = new byte[2 * n * _1M];
        alloc4 = new byte[4 * _1M];     //出现一次Minor GC
    }

    //测试大对象直接进入年老代  ,-XXPretenureSizeThreshold=5096
    public void testPretenureSize() {
        logger.info("testPretenureSize execute");
        byte[] alloc5 = new byte[5 * _1M];    //直接分配到年老代
    }

    public void testFullGC() {
        logger.info("testFullGC execute");
        //模拟fullgc场景
        //场景1 使用System.gc
        List<Object> l = new ArrayList<Object>();
        try {
            //随机
            int m = new Random().nextInt(10);
            int k = 5 + m;
            for (int i = 0; i < 30; i++) {
                l.add(new byte[_1M]);
//                logger.info("allocat one obj,i=" + i);

                if (i % k == 0) {
                    l = new ArrayList<Object>();
                    System.gc();
                }
            }
        } catch (Exception e) {
            logger.error("e:", e);
            l = new ArrayList<Object>();
        } finally {
            l = new ArrayList<Object>();
            System.gc();
        }
    }

    public void testOldEmpty() {
        logger.info("testOldEmpty execute");
        //模拟fullgc场景
        int m = new Random().nextInt(10);
        int k = 20 + m;
        byte[] alloc5 = new byte[k * _1M];    //
    }
}
