package com.alicp.es.tool.service.parser.dao.model;

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

public class LogAgentConfigDO {

    private Integer id;
    private String bizName;       //业务名称
    private String appName; //业务线
    private String ips; //服务器ip  ,逗号分隔

    private List<LogPathConfigDO> logPathConfigDOList;

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

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public List<LogPathConfigDO> getLogPathConfigDOList() {
        return logPathConfigDOList;
    }

    public void setLogPathConfigDOList(List<LogPathConfigDO> logPathConfigDOList) {
        this.logPathConfigDOList = logPathConfigDOList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
