package com.alicp.es.tool.service.parser;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/29
 * Time: 上午11:32
 * CopyRight: taobao
 * Descrption:日志模板 ,用户需要定义日志的一些参数,然后平台来解析
 */

public class LogTemplate {
    private String appName;//业务类型
    private String logPath;//日志路径
    private String logtName;//日志名称
    private String logPattern;//日志后缀,如yyyy-MM-dd
    private Map<String,LogFeature> logFeatureMap;    //key为featureId

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getLogtName() {
        return logtName;
    }

    public void setLogtName(String logtName) {
        this.logtName = logtName;
    }

    public String getLogPattern() {
        return logPattern;
    }

    public void setLogPattern(String logPattern) {
        this.logPattern = logPattern;
    }

    public Map<String, LogFeature> getLogFeatureMap() {
        return logFeatureMap;
    }

    public void setLogFeatureMap(Map<String, LogFeature> logFeatureMap) {
        this.logFeatureMap = logFeatureMap;
    }
}
