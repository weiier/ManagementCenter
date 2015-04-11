package org.ow2.aspirerdfid.tracking.demo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ow2.aspirerdfid.tracking.demo.dao.OrderDao;
import org.ow2.aspirerdfid.tracking.demo.model.Order_info;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
@Component("orderDao")
public class OrderDaoImpl implements OrderDao{
	private HibernateTemplate hibernateTemplate;
	@Override
	public void addOrder(Order_info order) {
		this.hibernateTemplate.save(order);		
	}

	@Override
	public List<Order_info> getOrdersById(int id) {
		return this.hibernateTemplate.find("from Order_info o where o.id='" + id +"'");
	}
	
	@Override
	public List<Order_info> getOrdersByTranscationID(String TranscationID) {
		return this.hibernateTemplate.find("from Order_info o where o.transcationID='"+TranscationID+"'");
	}
	
	@Override
	public List<Order_info> getOrders() {
		return this.hibernateTemplate.find("from Order_info");
	}
	
	@Override
	public List<Order_info> getOrders(String start, String end) {
		return  this.hibernateTemplate.find("from Order_info o where o.startTime between '"+start+
				"' and '"+end+"'");
	}

	@Override
	public List<Order_info> getOrdersById(String start, String end, int id) {
		return this.hibernateTemplate.find("from Order_info o  where o.id='"+id+"' and o.startTime between '"
				+start+"' and '"+end+"'");
	}
	@Override
	public void updateOrderById(int id, String transcationID) {
		this.hibernateTemplate.bulkUpdate("update Order_info set transcationID=? where id=?",new Object[]{transcationID,id});
	}
	
	@Override
	public void updateLastOrder(int id) {
		this.hibernateTemplate.bulkUpdate("update Order_info set state=1,finalTime=? where id=?",new Object[]{new Date(),id});
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
