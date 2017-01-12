package com.taobao.tail.consts;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/12
 * Time: 下午3:10
 * CopyRight: taobao
 * Descrption:
 */

public class LogVO {
    private int id;
    private String name;
    private String wholePath;
    private boolean isFile;

    // 界面属性，是否是父结点
   	private Boolean isIsParent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public Boolean getIsParent() {
        return isIsParent;
    }

    public void setIsParent(Boolean isParent) {
        isIsParent = isParent;
    }

    public String getWholePath() {
        return wholePath;
    }

    public void setWholePath(String wholePath) {
        this.wholePath = wholePath;
    }
}
