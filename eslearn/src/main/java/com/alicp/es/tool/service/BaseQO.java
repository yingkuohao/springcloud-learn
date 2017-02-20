package com.alicp.es.tool.service;

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
}
