package com.alicp.logapp.log.jvm.msg;

import com.alibaba.fastjson.annotation.JSONField;
import com.alicp.logapp.log.jvm.util.JmonitorConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:27
 * CopyRight: taobao
 * Descrption:
 */

public class GetAttribute extends BaseMessage {

    private int sequence;

    @JSONField(name = "OBJ_NAME")
    private String objectName;

    @JSONField(name = "ATTRS")
    private List<String> attributeNames;

    @JSONField(name = "OPTS")
    private List<String> options;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<String> getAttributeNames() {
        return attributeNames;
    }

    public void setAttributeNames(List<String> attributeNames) {
        this.attributeNames = attributeNames;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public String getType() {
        return JmonitorConstants.MSG_GETATTRIBUTE;
    }

    @Override
    public Map<String, Object> getBody() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("OBJ_NAME", objectName);
        map.put("ATTRS", attributeNames);
        map.put("OPTS", options);
        return map;
    }

    @Override
    public boolean isRequestFormat() {
        return true;
    }
}
