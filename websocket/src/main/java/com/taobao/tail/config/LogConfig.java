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

    private String keyGenPath;      //keygen的路径
    private String userName;      //访问远程主机的用户名
    private String pwd;      //访问远程主机的密码,keygen生成时指定


    public String getLogBaseDir() {
        return logBaseDir;
    }

    public void setLogBaseDir(String logBaseDir) {
        this.logBaseDir = logBaseDir;
    }

    public String getKeyGenPath() {
        return keyGenPath;
    }

    public void setKeyGenPath(String keyGenPath) {
        this.keyGenPath = keyGenPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
