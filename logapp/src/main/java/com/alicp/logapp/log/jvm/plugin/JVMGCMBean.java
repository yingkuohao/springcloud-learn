package com.alicp.logapp.log.jvm.plugin;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:39
 * CopyRight: taobao
 * Descrption:
 */

public interface JVMGCMBean {
    long getYoungGCCollectionCount();

    long getYoungGCCollectionTime();

    long getFullGCCollectionCount();

    long getFullGCCollectionTime();

    // 下面的数字是做过差计算的,启动后的第二次开始才能做差值
    long getSpanYoungGCCollectionCount();

    long getSpanYoungGCCollectionTime();

    long getSpanFullGCCollectionCount();

    long getSpanFullGCCollectionTime();
}
