package com.learn.springcloud.zuul;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/17
 * Time: 下午3:54
 * CopyRight: taobao
 * Descrption:   过滤器类型枚举:
 */

public enum FilterTypeEnum {
    pre("pre", "前置filter"),
    routing("routing", "routingfilter"),
    post("post", "返回filter"),
    error("error", "异常filter"),
    custom("custom", "用户自定义filter");

    private String code;
    private String name;

    FilterTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
