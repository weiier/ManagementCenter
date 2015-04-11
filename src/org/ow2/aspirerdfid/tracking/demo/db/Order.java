package org.ow2.aspirerdfid.tracking.demo.db;

import java.util.HashMap;

public class Order implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4460444583830612407L;
	private String prev;
	private String last;
	private HashMap<String, Long> OrderInfo;
	public Order(){
		prev = null;
		last = null;
		OrderInfo = null;
	}
	public HashMap<String, Long> getOrderInfo() {
		return OrderInfo;
	}
	public void setOrderInfo(HashMap<String, Long> orderInfo) {
		OrderInfo = orderInfo;
	}
	public Order( String p, String l ) {
		prev = p;
		last = l;
	}
	public String getPrev() {
		return prev;
	}
	public void setPrev(String prev) {
		this.prev = prev;
	}
	public String getlast() {
		return last;
	}
	public void setlast(String last) {
		this.last = last;
	}
	
}
