package com.alicp.es.tool.service.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/10/20
 * Time: 下午2:05
 * CopyRight: taobao
 * Descrption:
 */

public class LocalHostUtil {

    private final static Log log = LogFactory.getLog(LocalHostUtil.class);

    private static String hostName;
    private static String hostAddress;

    static {
        try {
            hostName = InetAddress.getLocalHost().getHostName();
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("init hostname error!", e);
        }
    }

    /**
     *
     * @return host
     */
    public static String getMachineName() {
        return hostName;
    }

    public static String getHostAddress() {
        return hostAddress;
    }
}
