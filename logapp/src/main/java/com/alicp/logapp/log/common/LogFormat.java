package com.alicp.logapp.log.common;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/19
 * Time: 上午10:36
 * CopyRight: taobao
 * Descrption:
 */

public class LogFormat {
    private String loglevel;  //日志级别
    private String requestTime;
    private String ip;
    private String platfom;   //业务类型,比如1: h5 2:独客
    private String bizType;   //业务类型,比如1:彩票 2:房产c
    private String appName;    //应用名称
    private String serviceName;  //服务名称
    private String methodName;    //方法名称
    private String operType; //操作类型
    private String bizInfo; //操作类型

    public String getLoglevel() {
        return loglevel;
    }

    public void setLoglevel(String loglevel) {
        this.loglevel = loglevel;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPlatfom() {
        return platfom;
    }

    public void setPlatfom(String platfom) {
        this.platfom = platfom;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getBizInfo() {
        return bizInfo;
    }

    public void setBizInfo(String bizInfo) {
        this.bizInfo = bizInfo;
    }


}
