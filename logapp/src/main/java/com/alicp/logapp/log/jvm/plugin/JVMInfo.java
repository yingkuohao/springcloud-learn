package com.alicp.logapp.log.jvm.plugin;

import com.alicp.logapp.log.jvm.util.IPUtils;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:12
 * CopyRight: taobao
 * Descrption:
 */

public class JVMInfo implements JVMInfoMBean {
    private static JVMInfo instance = new JVMInfo();

    public static JVMInfo getInstance() {
        return instance;
    }

    private RuntimeMXBean runtimeMXBean;
    private ClassLoadingMXBean classLoadingMXBean;
    private CompilationMXBean compilationMXBean;
    private Properties properties;
    private String inputArguments;
    private int availableProcessors = 0;
    private String pid;

    private JVMInfo() {
        classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        compilationMXBean = ManagementFactory.getCompilationMXBean();
        runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        properties = System.getProperties();
    }

    private String getSystemProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public Date getStartTime() {
        return new Date(runtimeMXBean.getStartTime());
    }

    @Override
    public String getJVM() {
        return runtimeMXBean.getVmName() + " (" + runtimeMXBean.getVmVersion() + ", "
                + getSystemProperty("java.vm.info") + ")";
    }

    @Override
    public String getJavaVersion() {
        return getSystemProperty("java.version") + "[jmonitor:" + 1.0 + "]";
    }

    @Override
    public String getPID() {
        if (null == pid) {
            pid = runtimeMXBean.getName().split("@")[0];
        }
        return pid;
    }

    @Override
    public String getInputArguments() {
        if (null == inputArguments) {
            inputArguments = runtimeMXBean.getInputArguments().toString();
        }
        return inputArguments;
    }

    @Override
    public String getJavaHome() {
        return getSystemProperty("java.home");
    }

    @Override
    public String getArch() {
        return getSystemProperty("os.arch");
    }

    @Override
    public String getOSName() {
        return getSystemProperty("os.name");
    }

    @Override
    public String getOSVersion() {
        return getSystemProperty("os.version");
    }

    @Override
    public String getJavaSpecificationVersion() {
        return getSystemProperty("java.specification.version");
    }

    @Override
    public String getJavaLibraryPath() {
        return getSystemProperty("java.library.path");
    }

    @Override
    public String getFileEncode() {
        return getSystemProperty("file.encoding");
    }

    @Override
    public int getAvailableProcessors() {
        if (availableProcessors == 0) {
            availableProcessors = Runtime.getRuntime().availableProcessors();
        }
        return availableProcessors;
    }

    @Override
    public int getLoadedClassCount() {
        return classLoadingMXBean.getLoadedClassCount();
    }

    @Override
    public long getTotalLoadedClassCount() {
        return classLoadingMXBean.getTotalLoadedClassCount();
    }

    @Override
    public long getUnloadedClassCount() {
        return classLoadingMXBean.getUnloadedClassCount();
    }

    @Override
    public long getTotalCompilationTime() {
        return compilationMXBean.getTotalCompilationTime();
    }

    public String getHostname() {
        return IPUtils.getLocalHostName();
    }

    public String getLocalIP() {
        return IPUtils.getAllIpv4NoLoopbackAddresses().toString();
    }
}
