package com.springcloud.tddl.dao.model;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/6
 * Time: 下午5:38
 * CopyRight: taobao
 * Descrption:
 */

public class StoreDO {

    /**
   	 * 门店
   	 */
   	private Long id;

   	/**
   	 * 中心ID
   	 */
   	private Long centerId;

   	/**
   	 * 1：体彩
   	 * 2：福彩
   	 * 3：体彩&福彩
   	 */
   	private Integer orgType;

   	/**
   	 * 1：省SR
   	 * 2：市门店
   	 */
   	private Integer storeType;

   	/**
   	 * 中心门店ID？varchar？
   	 */
   	private Integer centerStoreId;

   	/**
   	 * 佣金比例
   	 */
   	private Integer commissionRate;

   	/**
   	 * 渠道ID
   	 */
   	private Long channelId;

   	/**
   	 * 渠道编码
   	 */
   	private String channelNo;

   	/**
   	 * 删除标志
   	 */
   	private Integer deleteFlag;

		/**
		 * 创建时间
		 */
		private Date gmtCreate;

		/**
		 * 修改时间
		 */
		private Date gmtModified;

   	/**
   	 * 所属中心名称（冗余）
   	 */
   	private String centerName;

   	/**
   	 * setter for column 门店
   	 */
   	public void setId(Long id) {
   		this.id = id;
   	}

   	/**
   	 * getter for column 门店
   	 */
   	public Long getId() {
   		return this.id;
   	}

   	/**
   	 * setter for column 中心ID
   	 */
   	public void setCenterId(Long centerId) {
   		this.centerId = centerId;
   	}

   	/**
   	 * getter for column 中心ID
   	 */
   	public Long getCenterId() {
   		return this.centerId;
   	}

   	/**
   	 * setter for column 1：体彩
   	 * 2：福彩
   	 * 3：体彩&福彩
   	 */
   	public void setOrgType(Integer orgType) {
   		this.orgType = orgType;
   	}

   	/**
   	 * getter for column 1：体彩
   	 * 2：福彩
   	 * 3：体彩&福彩
   	 */
   	public Integer getOrgType() {
   		return this.orgType;
   	}

   	/**
   	 * setter for column 1：省SR
   	 * 2：市门店
   	 */
   	public void setStoreType(Integer storeType) {
   		this.storeType = storeType;
   	}

   	/**
   	 * getter for column 1：省SR
   	 * 2：市门店
   	 */
   	public Integer getStoreType() {
   		return this.storeType;
   	}

   	/**
   	 * setter for column 中心门店ID？varchar？
   	 */
   	public void setCenterStoreId(Integer centerStoreId) {
   		this.centerStoreId = centerStoreId;
   	}

   	/**
   	 * getter for column 中心门店ID？varchar？
   	 */
   	public Integer getCenterStoreId() {
   		return this.centerStoreId;
   	}

   	/**
   	 * setter for column 佣金比例
   	 */
   	public void setCommissionRate(Integer commissionRate) {
   		this.commissionRate = commissionRate;
   	}

   	/**
   	 * getter for column 佣金比例
   	 */
   	public Integer getCommissionRate() {
   		return this.commissionRate;
   	}

   	/**
   	 * setter for column 渠道ID
   	 */
   	public void setChannelId(Long channelId) {
   		this.channelId = channelId;
   	}

   	/**
   	 * getter for column 渠道ID
   	 */
   	public Long getChannelId() {
   		return this.channelId;
   	}

   	/**
   	 * setter for column 渠道编码
   	 */
   	public void setChannelNo(String channelNo) {
   		this.channelNo = channelNo;
   	}

   	/**
   	 * getter for column 渠道编码
   	 */
   	public String getChannelNo() {
   		return this.channelNo;
   	}

   	/**
   	 * setter for column 删除标志
   	 */
   	public void setDeleteFlag(Integer deleteFlag) {
   		this.deleteFlag = deleteFlag;
   	}

   	/**
   	 * getter for column 删除标志
   	 */
   	public Integer getDeleteFlag() {
   		return this.deleteFlag;
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
   	 * setter for column 所属中心名称（冗余）
   	 */
   	public void setCenterName(String centerName) {
   		this.centerName = centerName;
   	}

   	/**
   	 * getter for column 所属中心名称（冗余）
   	 */
   	public String getCenterName() {
   		return this.centerName;
   	}
}
