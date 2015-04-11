package org.ow2.aspirerdfid.tracking.demo.dao;

import java.util.List;
import org.ow2.aspirerdfid.tracking.demo.model.Order_info;

public interface OrderDao {
	public void addOrder(Order_info order);

	public List<Order_info> getOrdersById(int id);
	
	public List<Order_info> getOrdersByTranscationID(String TranscationID);

	public List<Order_info> getOrders();

	public void updateOrderById(int id, String transcationID);

	public void updateLastOrder(int id);

	public List<Order_info> getOrders(String start, String end);

	public List<Order_info> getOrdersById(String start, String end, int id);
}
