package org.ow2.aspirerdfid.tracking.demo.db;

import java.util.HashMap;

public class BriefParams implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 600641629327419029L;
	public static final int SHIPPING = 0;
	public static final int RECEIVING = 1;
	
	private String transactionID;
	private HashMap<String, Long> orderInfo;
	private String sendTime;//发货时间
	private String warehouseID;//仓库编号
	private int eventType;//事件类型，发货为0，收货为1
	private long routeID;//ordernumber
	
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
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public long getRouteID() {
		return routeID;
	}
	public void setRouteID(long routeID) {
		this.routeID = routeID;
	}
	
}
