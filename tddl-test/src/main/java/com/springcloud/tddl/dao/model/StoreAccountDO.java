package com.springcloud.tddl.dao.model;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/6
 * Time: 下午5:44
 * CopyRight: taobao
 * Descrption:
 */

public class StoreAccountDO {

    /**
    	 * 账户ID
    	 */
    	private Long id;

    	/**
    	 * 门店ID
    	 */
    	private Long storeId;

    	/**
    	 * 缴款账户
    	 */
    	private Integer paymentAcconut;

    	/**
    	 * 缴款账户余额
    	 */
    	private Integer paymentAcconutQuota;

    	/**
    	 * 即开账户
    	 */
    	private Long openAccount;

    	/**
    	 * 即开账户余额
    	 */
    	private Integer openAcountQuota;

    	/**
    	 * 创建时间
    	 */
    	private Date gmtCreate;

    	/**
    	 * 修改时间
    	 */
    	private Date gmtModified;

    	/**
    	 * 门店账号（可能有多个，设计成json）
    	 */
    	private String storeAcountNumber;

    	/**
    	 * 门店账号密码
    	 */
    	private String storeAcountPassword;

    	/**
    	 * 中心门店id
    	 */
    	private Integer centerStoreId;

    	/**
    	 * setter for column 账户ID
    	 */
    	public void setId(Long id) {
    		this.id = id;
    	}

    	/**
    	 * getter for column 账户ID
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
    	 * setter for column 缴款账户
    	 */
    	public void setPaymentAcconut(Integer paymentAcconut) {
    		this.paymentAcconut = paymentAcconut;
    	}

    	/**
    	 * getter for column 缴款账户
    	 */
    	public Integer getPaymentAcconut() {
    		return this.paymentAcconut;
    	}

    	/**
    	 * setter for column 缴款账户余额
    	 */
    	public void setPaymentAcconutQuota(Integer paymentAcconutQuota) {
    		this.paymentAcconutQuota = paymentAcconutQuota;
    	}

    	/**
    	 * getter for column 缴款账户余额
    	 */
    	public Integer getPaymentAcconutQuota() {
    		return this.paymentAcconutQuota;
    	}

    	/**
    	 * setter for column 即开账户
    	 */
    	public void setOpenAccount(Long openAccount) {
    		this.openAccount = openAccount;
    	}

    	/**
    	 * getter for column 即开账户
    	 */
    	public Long getOpenAccount() {
    		return this.openAccount;
    	}

    	/**
    	 * setter for column 即开账户余额
    	 */
    	public void setOpenAcountQuota(Integer openAcountQuota) {
    		this.openAcountQuota = openAcountQuota;
    	}

    	/**
    	 * getter for column 即开账户余额
    	 */
    	public Integer getOpenAcountQuota() {
    		return this.openAcountQuota;
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
    	 * setter for column 门店账号（可能有多个，设计成json）
    	 */
    	public void setStoreAcountNumber(String storeAcountNumber) {
    		this.storeAcountNumber = storeAcountNumber;
    	}

    	/**
    	 * getter for column 门店账号（可能有多个，设计成json）
    	 */
    	public String getStoreAcountNumber() {
    		return this.storeAcountNumber;
    	}

    	/**
    	 * setter for column 门店账号密码
    	 */
    	public void setStoreAcountPassword(String storeAcountPassword) {
    		this.storeAcountPassword = storeAcountPassword;
    	}

    	/**
    	 * getter for column 门店账号密码
    	 */
    	public String getStoreAcountPassword() {
    		return this.storeAcountPassword;
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
}
