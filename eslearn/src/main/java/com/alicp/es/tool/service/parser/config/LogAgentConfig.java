package com.alicp.es.tool.service.parser.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/31
 * Time: 上午10:59
 * CopyRight: taobao
 * Descrption:
 */
@Component
@ConfigurationProperties(prefix = "log.agent")
public class LogAgentConfig {

    private String bizName;       //业务名称
    private String appName; //业务线
    private Set<String> ips; //服务器ip

    private List<LogPathConfig> logPathConfigList;

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Set<String> getIps() {
        return ips;
    }

    public void setIps(Set<String> ips) {
        this.ips = ips;
    }

    public List<LogPathConfig> getLogPathConfigList() {
        return logPathConfigList;
    }

    public void setLogPathConfigList(List<LogPathConfig> logPathConfigList) {
        this.logPathConfigList = logPathConfigList;
    }
}
