package org.ow2.aspirerdfid.tracking.demo.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Factory_log {
	private int id;
	private int factoryState;
	private Date stateTime;
	private Factory_info factory;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFactoryState() {
		return factoryState;
	}
	public void setFactoryState(int factoryState) {
		this.factoryState = factoryState;
	}
	public Date getStateTime() {
		return stateTime;
	}
	public void setStateTime(Date stateTime) {
		this.stateTime = stateTime;
	}
	@ManyToOne
	@JoinColumn(name="factory_id")
	public Factory_info getFactory() {
		return factory;
	}
	public void setFactory(Factory_info factory) {
		this.factory = factory;
	}
	
	
}
