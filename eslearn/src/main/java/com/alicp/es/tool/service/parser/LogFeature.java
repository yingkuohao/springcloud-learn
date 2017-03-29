package com.alicp.es.tool.service.parser;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/29
 * Time: 上午11:34
 * CopyRight: taobao
 * Descrption: 日志字段描述
 *
 * 如下:比如我们想监控code字段 ,就应该构造一个对象
 * name--"code"
 * type--"String"
 * bizType--"sendRequest"
 * featureId--"<12:0091:SITE_40506360> OXi::openAPI::sendRequest: "
 *
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest: <?xml version="1.0" encoding="utf-8" ?><oxip version="6.0">
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:     <response>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:         <returnStatus>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <code>001</code>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <message>success</message>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <debug>02/06-16:59:59 </debug>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:         </returnStatus>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:         <respClientAuth>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <token>h/uYW9ibk3htjq/AZnuJx4EpZfRP4rFhHBDgqx7mHorbFXsY+iszx1JUeAucRkkcUpttsvLS</token>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:         </respClientAuth>
 * 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:         <respTokenRefresh>
 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <expiryTime>2017-02-06 17:59:59</expiryTime>
 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <timeToLive>3600000</timeToLive>
 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <token>kfuYW9ibknh2zF96XVacEGwtTz0kIODcfwAr+xzx8niBUl4p0Oyd6atN3v6UYqx3uaicnE1B</token>
 02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:         </respTokenRefresh>
 02/06-16:59:59.579 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:     </response>
 02/06-16:59:59.579 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest: </oxip>
 */

public class LogFeature {
    private String bizType;        //业务类型,如订单,登录,结算
    private String featureId;        //字段前缀,如
    private Integer patternType;        //pattern类型,1:简单 2:group
    private TreeMap<String,FieldPattern> patternMap;       //特征解析key和类型


    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public TreeMap<String, FieldPattern> getPatternMap() {
        return patternMap;
    }

    public void setPatternMap(TreeMap<String, FieldPattern> patternMap) {
        this.patternMap = patternMap;
    }

    public Integer getPatternType() {
        return patternType;
    }

    public void setPatternType(Integer patternType) {
        this.patternType = patternType;
    }
}
