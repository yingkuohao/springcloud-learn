package com.alicp.logapp.log.common;

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
        stringBuilder.append(LocalHostUtil.getHostAddress()).append("|").append(LocalHostUtil.getMachineName()).append("|").append(System.nanoTime()).append("|").append("logapp").append("|");
        return stringBuilder;

    }

}
