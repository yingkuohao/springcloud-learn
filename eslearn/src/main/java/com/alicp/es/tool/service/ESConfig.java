package com.alicp.es.tool.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/2/20
 * Time: 上午11:34
 * CopyRight: taobao
 * Descrption:
 */
@Component
@ConfigurationProperties(prefix = "config.elk.es")
public class ESConfig {

    private String host;      //es 地址
    private int port;      //es 端口
    private String clusterName;      //集群名称

    private String scriptBasePath;      //脚本基础路径

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getScriptBasePath() {
        return scriptBasePath;
    }

    public void setScriptBasePath(String scriptBasePath) {
        this.scriptBasePath = scriptBasePath;
    }
}
