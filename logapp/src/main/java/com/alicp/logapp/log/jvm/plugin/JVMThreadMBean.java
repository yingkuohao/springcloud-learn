package com.alicp.logapp.log.jvm.plugin;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/23
 * Time: 上午9:55
 * CopyRight: taobao
 * Descrption:
 */

public interface JVMThreadMBean {

    int getDaemonThreadCount();

    int getThreadCount();

    long getTotalStartedThreadCount();

    int getDeadLockedThreadCount();

    BigDecimal getProcessCpuTimeRate();
}
