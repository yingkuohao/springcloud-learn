package com.alicp.es.tool.service.parser;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/29
 * Time: 下午3:13
 * CopyRight: taobao
 * Descrption:
 */

public class FieldPattern {
    private String fieldName;  //字段名
    private Class type;  //字段类型
    private String pattern;  //正则表达

    public FieldPattern(String fieldName, Class type, String pattern) {
        this.fieldName = fieldName;
        this.type = type;
        this.pattern = pattern;
    }

    public FieldPattern(String fieldName, Class type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    public FieldPattern() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
