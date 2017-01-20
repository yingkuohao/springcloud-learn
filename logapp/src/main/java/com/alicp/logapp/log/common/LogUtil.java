package com.alicp.logapp.log.common;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/18
 * Time: 上午9:48
 * CopyRight: taobao
 * Descrption:
 */

public class LogUtil {


    public static StringBuilder logPrefix() {     //host|machine|beginTime|appName|Class|method
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|").append(LocalHostUtil.getHostAddress()).append("|").append(LocalHostUtil.getMachineName()).append("|").append(System.nanoTime()).append("|").append("logapp").append("|");
        return stringBuilder;

    }

    public static LogFormat getLogFormat() {
        LogFormat logFormat = new LogFormat();
        logFormat.setAppName("logapp");
        logFormat.setIp(LocalHostUtil.getHostAddress());
        logFormat.setPlatfom("1");
        logFormat.setBizType("caipiao");
        return logFormat;
    }

    public static Map getBaseLog() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appName", "logapp");
        map.put("ip", LocalHostUtil.getHostAddress());
        map.put("platform", "1");
        map.put("bizType", "caipiao");
        return map;
    }

}
