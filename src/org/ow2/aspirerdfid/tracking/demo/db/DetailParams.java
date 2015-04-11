package org.ow2.aspirerdfid.tracking.demo.db;

import java.util.HashSet;

import org.ow2.aspirerdfid.tracking.demo.db.DeliveryItem;

public class DetailParams  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2839651026167049210L;
	public static final int SHIPPING = 0;
	public static final int RECEIVING = 1;
	
	private String transactionID;
	private HashSet<DeliveryItem> orderInfo;
	//private String sendTime;
	private String warehouseID;
	private int eventType;
	private String nextWareHouseIP;
	private long routeID;
	
	public DetailParams() {
		
	}
	
	public long getRouteID() {
		return routeID;
	}

	public void setRouteID(long routeID) {
		this.routeID = routeID;
	}

	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public HashSet<DeliveryItem> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(HashSet<DeliveryItem> orderInfo) {
		this.orderInfo = orderInfo;
	}

//	public String getSendTime() {
//		return sendTime;
//	}
//	public void setSendTime(String sendTime) {
//		this.sendTime = sendTime;
//	}

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

	public String getNextWareHouseIP() {
		return nextWareHouseIP;
	}

	public void setNextWareHouseIP(String nextWareHouseIP) {
		this.nextWareHouseIP = nextWareHouseIP;
	}
	
}
