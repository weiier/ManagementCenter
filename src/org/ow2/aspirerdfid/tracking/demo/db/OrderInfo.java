package org.ow2.aspirerdfid.tracking.demo.db;

public class OrderInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5082291184740400299L;
	private String place;
	private String time;
	private int state;
	private int index;
	private int  arrived;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getArrived() {
		return arrived;
	}
	public void setArrived(int arrived) {
		this.arrived = arrived;
	}
	public OrderInfo(int index,String place, int state, 
			int arrived, String time) {
		super();
		this.place = place;
		this.time = time;
		this.state = state;
		this.index = index;
		this.arrived = arrived;
	}
	public OrderInfo(){}
}
