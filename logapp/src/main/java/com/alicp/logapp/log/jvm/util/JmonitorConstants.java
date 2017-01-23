package com.alicp.logapp.log.jvm.util;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:25
 * CopyRight: taobao
 * Descrption:
 */

public class JmonitorConstants {
    public final static String VERSION = "1.0";
    public final static String charset = "UTF-8";

    public final static String newLine = "\n";

    public final static String MSG_CONNECT = "Connect";
    public final static String MSG_CONNECT_RESP = "ConnectResp";
    public final static String MSG_GETATTRIBUTE = "GetAttribute";
    public final static String MSG_GETATTRIBUTE_RESP = "GetAttributeResp";
    public final static String MSG_HEARTBEAT = "Heartbeat";
    public final static String MSG_TS = "TS";
    public final static String MSG_S = "S";
    public final static String MSG_T = "T";
    public final static String MSG_VAL = "VAL";
    public final static String MSG_ERROR = "ERROR";

    // 不兼容dragoon的命名规则
    public final static String JMX_JVM_INFO_NAME = "com.alibaba.alimonitor.jmonitor:type=JVMInfo";
    public final static String JMX_JVM_MEMORY_NAME = "com.alibaba.alimonitor.jmonitor:type=JVMMemory";
    public final static String JMX_JVM_GC_NAME = "com.alibaba.alimonitor.jmonitor:type=JVMGC";
    public final static String JMX_JVM_THREAD_NAME = "com.alibaba.alimonitor.jmonitor:type=JVMThread";
    public final static String JMX_EXCEPTION_NAME = "com.alibaba.alimonitor.jmonitor:type=Exception";
    public final static String JMX_SPRING_METHOD_NAME = "com.alibaba.alimonitor.jmonitor:type=SpringMethod";
    public final static String JMX_WEB_URL_NAME = "com.alibaba.alimonitor.jmonitor:type=WebUrl";
    public final static String JMX_WEB_URL_PROFILE_NAME = "com.alibaba.alimonitor.jmonitor:type=WebUrlProfile";
    public final static String JMX_WEB_IP_NAME = "com.alibaba.alimonitor.jmonitor:type=WebIP";
    public final static String JMX_SQL_NAME = "com.alibaba.alimonitor.jmonitor:type=DruidSql";
    public final static String JMX_DRUID_DATASOURCE_NAME = "com.alibaba.druid:type=DruidDataSourceStat";

    // 可配置的参数名称

    public final static String JMONITOR_AGENTPORT = "Jmonitor_AgentPort";
    public final static String JMONITOR_AGENTHOST = "Jmonitor_AgentHost";
    public final static String JMONITOR_ENABLE_MONITOR_IP = "Jmonitor_Enable_Monitor_IP";
    public final static String JMONITOR_ENABLE_DRUID_FILTER = "Jmonitor_Enable_Druid_Filter";
    public final static String JMONITOR_ENABLE_DRUID = "Jmonitor_Enable_Druid";
    public final static Integer JMONITOR_SQL_TOTAL_LIMIT = 150;
    public final static Integer JMONITOR_SQL_INDEX_LIMIT = 50;
    // 兼容dragoon
    public final static String JMONITOR_APPNUM = "APP_NUM";

    public static boolean enableMonitorIp = false;
    public static boolean enableDruidFilter = true;


    public final static String JMX_LOG_KEY_APP = "J_App_Name";
    public final static String JMX_LOG_KEY_IP = "J_IP";
    public final static String JMX_LOG_KEY_TIME = "J_Timestamp";

    public final static String JMX_LOG_KEY_JVM_TYPE = "J_JvmType";

    public final static String JMX_LOG_VALUE_MEMORY = "memory";
    public final static String JMX_LOG_VALUE_GC = "gc";
    public final static String JMX_LOG_VALUE_THREAD = "thread";



}
