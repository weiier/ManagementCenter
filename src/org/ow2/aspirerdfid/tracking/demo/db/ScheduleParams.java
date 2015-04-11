package org.ow2.aspirerdfid.tracking.demo.db;

import java.util.HashMap;

public class ScheduleParams implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2476862065650326431L;
	private String prevWarehouse;//上一站 发货仓库
	private String nextWarehouse;//下一站 收货仓库
	private String transactionID;//id
	private HashMap<String, Long> orderInfo;//
	private String sendTime;//命令的发送时间
	private long routeID;//ordernumber
	private String prevIP;//上一站ip
	private String nextIP;//下一站ip
	
	public String getPrevWarehouse() {
		return prevWarehouse;
	}
	public void setPrevWarehouse(String prevWarehouse) {
		this.prevWarehouse = prevWarehouse;
	}
	public String getNextWarehouse() {
		return nextWarehouse;
	}
	public void setNextWarehouse(String nextWarehouse) {
		this.nextWarehouse = nextWarehouse;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public HashMap<String, Long> getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(HashMap<String, Long> orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public long getRouteID() {
		return routeID;
	}
	public void setRouteID(long routeID) {
		this.routeID = routeID;
	}
	public String getPrevIP() {
		return prevIP;
	}
	public void setPrevIP(String prevIP) {
		this.prevIP = prevIP;
	}
	public String getNextIP() {
		return nextIP;
	}
	public void setNextIP(String nextIP) {
		this.nextIP = nextIP;
	}
	
}
