package com.springcloud.tddl.sharding.dao;

import java.io.Serializable;

public class UserTicketIndex implements Serializable{

	private static final long serialVersionUID = -6624130986474628841L;

	private long userId;
	private long lotterySn;
	private long id;
	private int lotteryType;
	private int channel;
	private int unitType;
	private int winStatus;
	private int playType;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getLotterySn() {
		return lotterySn;
	}
	public void setLotterySn(long lotterySn) {
		this.lotterySn = lotterySn;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getUnitType() {
		return unitType;
	}
	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}
	public int getWinStatus() {
		return winStatus;
	}
	public void setWinStatus(int winStatus) {
		this.winStatus = winStatus;
	}
	public int getPlayType() {
		return playType;
	}
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	
	
}
