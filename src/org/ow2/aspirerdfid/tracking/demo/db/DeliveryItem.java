/*
 * Copyright (c) 2008-2010, Aspire
 * 
 * Aspire is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License version 2.1 as published by
 * the Free Software Foundation (the "LGPL").
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library in the file COPYING-LGPL-2.1; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 * 
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY
 * KIND, either express or implied. See the GNU Lesser General Public License
 * for the specific language governing rights and limitations.
 */

package org.ow2.aspirerdfid.tracking.demo.db;

/**
 * @author nkef (Nikos Kefalakis)
 * @author Nektarios Leontiadis
 * 
 */
public class DeliveryItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 524003805181301480L;
	private String company;
	private String itemCode;
	private String name;
	private long quantityDelivered;
	private long expectedQuantity;
	private long quantityRemain;
	private String deliveryDate;
	private String measurementID;
	private long quantity;
	
	public DeliveryItem() {
		
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuantityDelivered() {
		return quantityDelivered;
	}

	public void setQuantityDelivered(long quantityDelivered) {
		this.quantityDelivered = quantityDelivered;
	}

	public long getExpectedQuantity() {
		return expectedQuantity;
	}

	public void setExpectedQuantity(long expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public long getQuantityRemain() {
		return quantityRemain;
	}

	public void setQuantityRemain(long quantityRemain) {
		this.quantityRemain = quantityRemain;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getMeasurementID() {
		return measurementID;
	}

	public void setMeasurementID(String measurementID) {
		this.measurementID = measurementID;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}


}
