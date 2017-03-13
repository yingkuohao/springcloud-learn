package com.alicp.es.tool.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/2/20
 * Time: 上午11:32
 * CopyRight: taobao
 * Descrption:
 */

public class BaseQO {

    private String index;   //index名称
    private String type;        //文档类型
    private String id;                 //查询id
    private Integer from; //起始位置
    private Integer size;           //每页大小

    public BaseQO() {
    }

    public BaseQO(String index) {
        this.index = index;
    }

    public BaseQO(String index, String type) {
        this.index = index;
        this.type = type;
    }

    public BaseQO(String index, String type, String id) {
        this.index = index;
        this.type = type;
        this.id = id;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
   	public String toString() {
   		return ReflectionToStringBuilder.toString(this);
   	}

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
