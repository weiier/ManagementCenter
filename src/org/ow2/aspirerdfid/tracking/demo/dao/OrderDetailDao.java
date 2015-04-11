package org.ow2.aspirerdfid.tracking.demo.dao;
import org.ow2.aspirerdfid.tracking.demo.model.Order_detail;

public interface OrderDetailDao {
	public void addDetail(Order_detail detail);
	public void updateOrderDetail(int orderID,int eventType,int FactoryID);
}
