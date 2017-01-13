package com.taobao.tail.consts;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/13
 * Time: 上午11:30
 * CopyRight: taobao
 * Descrption:  服务器信息
 */

public class ServerVO {

    private String ip;
    private String userName;
    private String pwd;

    public ServerVO(String ip, String userName, String pwd) {
        this.ip = ip;
        this.userName = userName;
        this.pwd = pwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
