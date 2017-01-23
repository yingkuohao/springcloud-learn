package com.alicp.logapp.log.jvm.util;

import com.alicp.logapp.log.common.LocalHostUtil;
import com.alicp.logapp.log.common.LogUtil;
import com.alicp.logapp.log.jvm.msg.GetAttribute;
import com.alicp.logapp.log.jvm.plugin.JVMGC;
import com.alicp.logapp.log.jvm.plugin.JVMInfo;
import com.alicp.logapp.log.jvm.plugin.JVMMemory;
import com.alicp.logapp.log.jvm.plugin.JVMThread;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularDataSupport;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:27
 * CopyRight: taobao
 * Descrption:
 */

public class JMXUtils {
    private final static Log LOG = LogFactory.getLog(JMXUtils.class);

    private static MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

    private final static String JmxListAttributeName = "JmonitorDataList";
    private final static String JmxListAttributeName_Druid_Sql = "SqlList";
    private final static String JmxListAttributeName_Druid_Datasource = "DataSourceList";

    public static void regMbean() throws Exception {
        unregMbean();
        ObjectName jvmInfoName = new ObjectName(JmonitorConstants.JMX_JVM_INFO_NAME);
        ObjectName jvmMemoryName = new ObjectName(JmonitorConstants.JMX_JVM_MEMORY_NAME);
        ObjectName jvmGCName = new ObjectName(JmonitorConstants.JMX_JVM_GC_NAME);
        ObjectName jvmThreadName = new ObjectName(JmonitorConstants.JMX_JVM_THREAD_NAME);
        ObjectName exceptionName = new ObjectName(JmonitorConstants.JMX_EXCEPTION_NAME);
        ObjectName springMethodName = new ObjectName(JmonitorConstants.JMX_SPRING_METHOD_NAME);
        ObjectName webUrlName = new ObjectName(JmonitorConstants.JMX_WEB_URL_NAME);
        ObjectName webUrlProfileName = new ObjectName(JmonitorConstants.JMX_WEB_URL_PROFILE_NAME);
        ObjectName webIPName = new ObjectName(JmonitorConstants.JMX_WEB_IP_NAME);
        ObjectName sqlName = new ObjectName(JmonitorConstants.JMX_SQL_NAME);
        mbeanServer.registerMBean(JVMInfo.getInstance(), jvmInfoName);
        mbeanServer.registerMBean(JVMGC.getInstance(), jvmGCName);
        mbeanServer.registerMBean(JVMThread.getInstance(), jvmThreadName);
        mbeanServer.registerMBean(JVMMemory.getInstance(), jvmMemoryName);
        /*      mbeanServer.registerMBean(Log4jDataManager.getInstance(), exceptionName);
          mbeanServer.registerMBean(SpringMethodDataManager.getInstance(), springMethodName);
          mbeanServer.registerMBean(WebUrlDataManager.getInstance(), webUrlName);
          mbeanServer.registerMBean(WebUrlProfileDataManager.getInstance(), webUrlProfileName);
          mbeanServer.registerMBean(WebIPDataManager.getInstance(), webIPName);*/

        // 注册druid中sql统计的mbean,这样加载的原因是即时不依赖druid,也可以正常注册
     /*     try {
              if (JmonitorConstants.enableDruidFilter) {
                  // 强制打开druid的监控功能
                  System.setProperty("druid.filters", "stat");
                  System.setProperty("druid.stat.mergeSql", "true");
                  System.setProperty("druid.useGloalDataSourceStat", "true");
              }
              Class<?> clazz = Class.forName("com.alibaba.druid.stat.JdbcStatManager");
              Object druidSqlStat = clazz.getMethod("getInstance").invoke(null);
              mbeanServer.registerMBean(druidSqlStat, sqlName);
              FileUtils.appendToLog("reg druid mbean(com.alibaba.druid.stat.JdbcStatManager) success.");
          } catch (Exception e) {
              LOG.error(e.getMessage());
          }*/

        FileUtils.appendToLog("reg mbeans success.");
        LOG.info("---initJvmMonitor success!---");
    }

    public static void unregMbean() throws Exception {
        ObjectName jvmInfoName = new ObjectName(JmonitorConstants.JMX_JVM_INFO_NAME);
        ObjectName jvmMemoryName = new ObjectName(JmonitorConstants.JMX_JVM_MEMORY_NAME);
        ObjectName jvmGCName = new ObjectName(JmonitorConstants.JMX_JVM_GC_NAME);
        ObjectName jvmThreadName = new ObjectName(JmonitorConstants.JMX_JVM_THREAD_NAME);
        ObjectName exceptionName = new ObjectName(JmonitorConstants.JMX_EXCEPTION_NAME);
        ObjectName springMethodName = new ObjectName(JmonitorConstants.JMX_SPRING_METHOD_NAME);
        ObjectName webUrlName = new ObjectName(JmonitorConstants.JMX_WEB_URL_NAME);
        ObjectName webUrlProfileName = new ObjectName(JmonitorConstants.JMX_WEB_URL_PROFILE_NAME);
        ObjectName webIPName = new ObjectName(JmonitorConstants.JMX_WEB_IP_NAME);
        ObjectName sqlName = new ObjectName(JmonitorConstants.JMX_SQL_NAME);
        List<ObjectName> objectNameList = new ArrayList<ObjectName>();
        objectNameList.add(jvmInfoName);
        objectNameList.add(jvmMemoryName);
        objectNameList.add(jvmGCName);
        objectNameList.add(jvmThreadName);
        objectNameList.add(exceptionName);
        objectNameList.add(springMethodName);
        objectNameList.add(webUrlName);
        objectNameList.add(webUrlProfileName);
        objectNameList.add(webIPName);
        objectNameList.add(sqlName);
        for (ObjectName objectName : objectNameList) {
            if (mbeanServer.isRegistered(objectName)) {
                mbeanServer.unregisterMBean(objectName);
            }
        }
    }

    /**
     * 标准的采集方式,暂时不对外暴露
     *
     * @param getAttribute
     * @return
     */
    private static Map<String, Object> getAttribute(GetAttribute getAttribute) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (null == getAttribute || StringUtils.isBlank(getAttribute.getObjectName())
                || null == getAttribute.getAttributeNames()) {
            return result;
        }
        //加入应用基本参数
        result.put(JmonitorConstants.JMX_LOG_KEY_APP, LogUtil.APP_NAME);
        result.put(JmonitorConstants.JMX_LOG_KEY_IP, LocalHostUtil.getHostAddress());
        result.put(JmonitorConstants.JMX_LOG_KEY_TIME, LocalDateTime.now());

        try {
            List<String> attributeNames = getAttribute.getAttributeNames();
            List<String> options = getAttribute.getOptions();
            ObjectName objectName = new ObjectName(getAttribute.getObjectName());
            for (String attributeName : attributeNames) {
                // 这里单个属性的失败不影响其他数据采集
                try {
                    Object value = mbeanServer.getAttribute(objectName, attributeName);
                    if (value instanceof TabularDataSupport) {
                        List<Object> list = new ArrayList<Object>();
                        TabularDataSupport tabularDataSupport = (TabularDataSupport) value;
                        for (Object itemValue : tabularDataSupport.values()) {
                            if (itemValue instanceof CompositeData) {
                                CompositeData compositeData = (CompositeData) itemValue;
                                Map<String, Object> singleLine = new HashMap<String, Object>();
                                for (String key : compositeData.getCompositeType().keySet()) {
                                    Object entryValue = compositeData.get(key);
                                    singleLine.put(key, entryValue);
                                }
                                list.add(singleLine);
                            }
                        }
                        value = list;
                    }
                    result.put(attributeName, value);
                } catch (Exception e) {
                    // LOG.error(e.getMessage(), e);
                }
            }
            if (null != options) {
                for (String option : options) {
                    if ("reset=true".equals(option)) {
                        mbeanServer.invoke(objectName, "reset", null, null);
                    }
                }
            }
        } catch (Exception e) {
            // LOG.error(e.getMessage());
            LOG.warn(e.getMessage());
        }
        return result;
    }

    /**
     * 格式化,支持alimonitor协议<br>
     * 只支持单行或者多行的数据,如果是多行直接返回数组
     *
     * @param getAttribute
     * @return
     */
    public static Object getAttributeFormatted(GetAttribute getAttribute) {
        Map<String, Object> map = getAttribute(getAttribute);
        // druid datasource的特殊处理
        if (JmonitorConstants.JMX_DRUID_DATASOURCE_NAME.equals(getAttribute.getObjectName())) {
            return map.get(JmxListAttributeName_Druid_Datasource);
        }
        // druid sql的特殊处理
        if (JmonitorConstants.JMX_SQL_NAME.equals(getAttribute.getObjectName())) {
            Object value = map.get(JmxListAttributeName_Druid_Sql);
            if (value instanceof List) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> sqlStat = (List<Map<String, Object>>) value;
                return sortAndSelect(sqlStat);
            }
            return value;
        }
        // 默认的多行数据的名称为"JmonitorDataList"
        if (null != map.get(JmxListAttributeName)) {
            return map.get(JmxListAttributeName);
        }
        return map;
    }

    /**
     * 数据过滤，防止过多的无用数据超过Monitor数据上传限制 选择方式：低于阈值不做处理，否则选择
     * 某几个指标的最高几个组成结果数据 ExecuteCount 总次数 TotalTime 总耗时 ErrorCount 错误数
     *
     * @param sqlStat
     * @return
     */
    private static List<Map<String, Object>> sortAndSelect(List<Map<String, Object>> sqlStat) {
        if (sqlStat.size() <= JmonitorConstants.JMONITOR_SQL_TOTAL_LIMIT) {
            return sqlStat;
        }
        Set<Map<String, Object>> resultSet = new HashSet<Map<String, Object>>(
                JmonitorConstants.JMONITOR_SQL_TOTAL_LIMIT);

        // For getting top TotalTime sql (降序排列)
        Collections.sort(sqlStat, new Comparator<Map<String, Object>>() {

            public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                return ((Long) arg1.get("TotalTime")).compareTo((Long) arg0.get("TotalTime"));
            }
        });
        resultSet.addAll(sqlStat.subList(0, JmonitorConstants.JMONITOR_SQL_INDEX_LIMIT));
        // For getting top ExecuteCount sql (降序排列)
        Collections.sort(sqlStat, new Comparator<Map<String, Object>>() {

            public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                return ((Long) arg1.get("ExecuteCount")).compareTo((Long) arg0.get("ExecuteCount"));
            }
        });
        resultSet.addAll(sqlStat.subList(0, JmonitorConstants.JMONITOR_SQL_INDEX_LIMIT));
        // For getting top ErrorCount sql (降序排列)
        Collections.sort(sqlStat, new Comparator<Map<String, Object>>() {

            public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                return ((Long) arg1.get("ErrorCount")).compareTo((Long) arg0.get("ErrorCount"));
            }
        });
        resultSet.addAll(sqlStat.subList(0, JmonitorConstants.JMONITOR_SQL_INDEX_LIMIT));
        return new ArrayList<Map<String, Object>>(resultSet);
    }
}

