package org.ow2.aspirerdfid.tracking.demo.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Order_info {
	private int id;
	private String transcationID;
	private Factory_info origin;
	private Factory_info destination;
	private String itemName;
	private String route;
	private int itemQuantity;
	private int state;
	private Date startTime;
	private Date finalTime;
	private List<Order_detail> details;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTranscationID() {
		return transcationID;
	}
	public void setTranscationID(String transcationID) {
		this.transcationID = transcationID;
	}
	@ManyToOne
	@JoinColumn(name="origin")
	public Factory_info getOrigin() {
		return origin;
	}
	public void setOrigin(Factory_info origin) {
		this.origin = origin;
	}
	@ManyToOne
	@JoinColumn(name="destination")
	public Factory_info getDestination() {
		return destination;
	}
	public void setDestination(Factory_info destination) {
		this.destination = destination;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinalTime() {
		return finalTime;
	}
	public void setFinalTime(Date finalTime) {
		this.finalTime = finalTime;
	}
	@OneToMany(mappedBy="order",fetch=FetchType.EAGER)
	public List<Order_detail> getDetails() {
		return details;
	}
	public void setDetails(List<Order_detail> details) {
		this.details = details;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	
}
