package org.ow2.aspirerdfid.tracking.demo.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.ow2.aspirerdfid.tracking.demo.dao.OrderDetailDao;
import org.ow2.aspirerdfid.tracking.demo.model.Order_detail;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
@Component("orderDetailDao")
public class OrderDetailDaoImpl implements OrderDetailDao {
	private HibernateTemplate hibernateTemplate;
	@Override
	public void addDetail(Order_detail detail) {
		this.hibernateTemplate.save(detail);
	}
	
	@Override
	public void updateOrderDetail(int orderID, int eventType, int FactoryID) {
		int type = -2;
		if(eventType==0){
			type = 1;
		}else if(eventType==1){
			type = 0;
		}else{
			type = eventType;
		}
		this.hibernateTemplate.bulkUpdate("update Order_detail set arrivedState=?,eventTime=? where order_id=? and factory_id=?",
				new Object[]{type,new Date(),orderID,FactoryID});
	}
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}	
}
