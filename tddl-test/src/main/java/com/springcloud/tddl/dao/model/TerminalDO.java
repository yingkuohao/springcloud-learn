package com.springcloud.tddl.dao.model;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/7
 * Time: 下午8:24
 * CopyRight: taobao
 * Descrption:
 */

public class TerminalDO {

    /**
     * 终端ID
     */
    private Long id;


    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 1：可用
     * 2：不可用
     */
    private Integer businessStatus;

    /**
     * 1：可用
     * 2：不可用
     */
    private Integer manageStatus;

    /**
     * 中心终端标识
     */
    private Long centerTerminalNo;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 中心门店id
     */
    private Integer centerStoreId;

    /**
     * 终端sn
     */
    private String sn;

    /**
     * setter for column 终端ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter for column 终端ID
     */
    public Long getId() {
        return this.id;
    }


    /**
     * setter for column 门店ID
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * getter for column 门店ID
     */
    public Long getStoreId() {
        return this.storeId;
    }

    /**
     * setter for column 1：可用
     * 2：不可用
     */
    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }

    /**
     * getter for column 1：可用
     * 2：不可用
     */
    public Integer getBusinessStatus() {
        return this.businessStatus;
    }

    /**
     * setter for column 1：可用
     * 2：不可用
     */
    public void setManageStatus(Integer manageStatus) {
        this.manageStatus = manageStatus;
    }

    /**
     * getter for column 1：可用
     * 2：不可用
     */
    public Integer getManageStatus() {
        return this.manageStatus;
    }

    /**
     * setter for column 中心终端标识
     */
    public void setCenterTerminalNo(Long centerTerminalNo) {
        this.centerTerminalNo = centerTerminalNo;
    }

    /**
     * getter for column 中心终端标识
     */
    public Long getCenterTerminalNo() {
        return this.centerTerminalNo;
    }

    /**
     * setter for column 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * getter for column 创建时间
     */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /**
     * setter for column 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * getter for column 修改时间
     */
    public Date getGmtModified() {
        return this.gmtModified;
    }

    /**
     * setter for column 中心门店id
     */
    public void setCenterStoreId(Integer centerStoreId) {
        this.centerStoreId = centerStoreId;
    }

    /**
     * getter for column 中心门店id
     */
    public Integer getCenterStoreId() {
        return this.centerStoreId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

}
