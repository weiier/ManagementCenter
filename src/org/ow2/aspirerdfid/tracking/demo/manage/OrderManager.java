package org.ow2.aspirerdfid.tracking.demo.manage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ow2.aspirerdfid.tracking.demo.dao.FactoryDao;
import org.ow2.aspirerdfid.tracking.demo.dao.OrderDao;
import org.ow2.aspirerdfid.tracking.demo.dao.OrderDetailDao;
import org.ow2.aspirerdfid.tracking.demo.db.ScheduleParams;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.ow2.aspirerdfid.tracking.demo.model.Order_detail;
import org.ow2.aspirerdfid.tracking.demo.model.Order_info;
import org.ow2.aspirerdfid.tracking.demo.util.ServicesUtil;
import org.springframework.stereotype.Component;
import com.ws.services.IForwardServices;

@Component
public class OrderManager {
	private OrderDetailDao orderDetail;
	private OrderDao orderDao;
	private FactoryDao factoryDao;
	private OrderDetailDao detailDao;
	/**
	 * 发布命令,完成发布第一条命令和存入数据库订单信息的功能
	 * */
	public Map<Integer,List<Integer>> publish(Order_info order,int flag){
		Map<Integer,List<Integer>> result = new HashMap<Integer,List<Integer>>();
		int origin = order.getOrigin().getId();
		int destination = order.getDestination().getId();
		String thisIP = null;
		String nextIP = null;
		List<Integer> routes = null;
		ScheduleParams schedule = new ScheduleParams();
		List<Factory_info> factorys = this.factoryDao.getFactorys();
		if(flag == 0){
			//计算路径
			 routes = ServicesUtil.getRoute(factorys, origin, destination);
			order.setRoute(ServicesUtil.FormatList(routes));
			this.orderDao.addOrder(order);
		}else{
			routes = ServicesUtil.FormatString(order.getRoute());
			this.orderDao.addOrder(order);
		}
		System.out.println("-------输出调度测试："+routes.toString()+"---------");
		for(int f = 0; f < factorys.size(); f++ ){
			if(origin == factorys.get(f).getId()){
				thisIP = factorys.get(f).getFactoryIP();
			}
			if(destination == factorys.get(f).getId()){
				nextIP = factorys.get(f).getFactoryIP();
			}
		}
		// 设置命令
		schedule.setNextIP(nextIP);
		schedule.setNextWarehouse(ServicesUtil.FormatIDtoString(routes.get(1)));
		schedule.setRouteID(order.getId());
		HashMap<String,Long> orderDetail= new HashMap<String,Long>();
		orderDetail.put(order.getItemName(), (long) order.getItemQuantity());
		schedule.setOrderInfo(orderDetail);
		
		//初始化路径上各个仓库对应的订单详情
		for(int r=0;r<routes.size();r++){
			Order_detail detail = new Order_detail();
			Factory_info detail_factory = new Factory_info();
			Order_info detail_order = new Order_info();
			detail_factory.setId(routes.get(r));
			detail_order.setId(order.getId());
			detail.setEventTime(new Date());
			detail.setFactory(detail_factory);
			detail.setOrder(detail_order);
			if(r==0){
				detail.setFactoryDetail(0);
				detail.setArrivedState(0);
			}else if(r==(routes.size()-1)){
				detail.setFactoryDetail(-1);
				detail.setArrivedState(2);
			}else{
				detail.setFactoryDetail(1);
				detail.setArrivedState(2);
			}
			this.detailDao.addDetail(detail);
		}	
		
		//调用cxf
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		//注册WebService接口     
		factory.setServiceClass(IForwardServices.class);     
		//设置WebService地址     
		factory.setAddress("http://"+thisIP+":8080/aspireMidware/services/forward");          
		IForwardServices forward = (IForwardServices)factory.create();
		System.out.println("invoke  instrut webservice..."); 
		boolean res = false;
		try {
				res = forward.instruct(schedule);
		}catch(Exception e){e.printStackTrace();}		
		if(!res){
			this.detailDao.updateOrderDetail(order.getId(), -1, origin);
		}
		System.out.println("调度结果："+res+"-----生成"+routes+"路径-----");
		if(res == true){
			//return routes;
			result.put(order.getId(), routes);
		}else{
			result.put(-order.getId(), routes);
		}
		return result;
	}
	
	public List<Order_info> findOrdersById(int id){
		return this.orderDao.getOrdersById(id);
	}
	
	public List<Order_info>findOrdersByTime(String start,String end,int id){
		return this.orderDao.getOrdersById(start, end, id);
	}
	
	public List<Order_info>findOrdersByTime(String start,String end){
		return this.orderDao.getOrders(start, end);
	}
	
	public List<Order_info> findOrders(){
		return this.orderDao.getOrders();
	}
	public OrderDao getOrderDao() {
		return orderDao;
	}
	@Resource
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public OrderDetailDao getOrderDetail() {
		return orderDetail;
	}
	@Resource
	public void setOrderDetail(OrderDetailDao orderDetail) {
		this.orderDetail = orderDetail;
	}

	public FactoryDao getFactoryDao() {
		return factoryDao;
	}
	@Resource
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}
	public OrderDetailDao getDetailDao() {
		return detailDao;
	}
	@Resource
	public void setDetailDao(OrderDetailDao detailDao) {
		this.detailDao = detailDao;
	}
}
