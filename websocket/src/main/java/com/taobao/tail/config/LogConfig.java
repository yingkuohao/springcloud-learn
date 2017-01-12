package com.taobao.tail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/12
 * Time: 下午2:14
 * CopyRight: taobao
 * Descrption:
 */
@Component
@ConfigurationProperties(prefix = "config.log")

public class LogConfig {

    private String logBaseDir;      //日志目录地址


    public String getLogBaseDir() {
        return logBaseDir;
    }

    public void setLogBaseDir(String logBaseDir) {
        this.logBaseDir = logBaseDir;
    }
}
