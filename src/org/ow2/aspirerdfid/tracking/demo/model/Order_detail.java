package org.ow2.aspirerdfid.tracking.demo.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Order_detail {
	private int id;
	private Order_info order;
	private Factory_info factory;
	private int factoryDetail;
	private int arrivedState;
	private Date eventTime;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="order_id")
	public Order_info getOrder() {
		return order;
	}
	public void setOrder(Order_info order) {
		this.order = order;
	}
	@ManyToOne
	@JoinColumn(name="factory_id")
	public Factory_info getFactory() {
		return factory;
	}
	public void setFactory(Factory_info factory) {
		this.factory = factory;
	}
	public int getFactoryDetail() {
		return factoryDetail;
	}
	public void setFactoryDetail(int factoryDetail) {
		this.factoryDetail = factoryDetail;
	}
	public int getArrivedState() {
		return arrivedState;
	}
	public void setArrivedState(int arrivedState) {
		this.arrivedState = arrivedState;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	
	
}
