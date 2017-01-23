package com.alicp.logapp.log.jvm.plugin;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:11
 * CopyRight: taobao
 * Descrption:
 */

public interface JVMInfoMBean {
    Date getStartTime();

    String getJVM();

    String getJavaVersion();

    String getPID();

    String getInputArguments();

    String getJavaHome();

    String getArch();

    String getOSName();

    String getOSVersion();

    String getJavaSpecificationVersion();

    String getJavaLibraryPath();

    String getFileEncode();

    int getAvailableProcessors();

    int getLoadedClassCount();

    long getTotalLoadedClassCount();

    long getUnloadedClassCount();

    long getTotalCompilationTime();

}
