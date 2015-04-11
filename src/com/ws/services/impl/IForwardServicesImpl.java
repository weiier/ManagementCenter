package com.ws.services.impl;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ow2.aspirerdfid.tracking.demo.dao.FactoryDao;
import org.ow2.aspirerdfid.tracking.demo.dao.OrderDao;
import org.ow2.aspirerdfid.tracking.demo.dao.OrderDetailDao;
import org.ow2.aspirerdfid.tracking.demo.db.BriefParams;
import org.ow2.aspirerdfid.tracking.demo.db.DetailParams;
import org.ow2.aspirerdfid.tracking.demo.db.ScheduleParams;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.ow2.aspirerdfid.tracking.demo.model.Order_info;
import org.ow2.aspirerdfid.tracking.demo.util.ServicesUtil;
import com.ws.services.IForwardServices;

@WebService(endpointInterface="com.ws.services.IForwardServices")
public class IForwardServicesImpl implements IForwardServices{
	private FactoryDao factoryDao;
	private OrderDao orderDao;
	private OrderDetailDao detailDao;
	//汇报
	@Override
	public boolean report(BriefParams params) {
		System.out.println(params.getRouteID());
		System.out.println(params.getTransactionID());
		System.out.println(params.getWarehouseID());
		List<Order_info> orders;
		
		if(params.getRouteID()!=-2){
			orders = this.orderDao.getOrdersById((int)params.getRouteID());
		}else{
			orders = this.orderDao.getOrdersByTranscationID(params.getTransactionID());
			params.setRouteID(orders.get(0).getId());
		}	
		if(orders.get(0).getTranscationID()==null){
			this.orderDao.updateOrderById((int)params.getRouteID(), params.getTransactionID());
		}
		this.noticeNext(params, orders.get(0));
		return true;
	}
	//再次发布命令，通知下一个仓库，发货事件通知下一仓库准备收货，收货事件也要通知下一仓库，需判断是不是收货仓库
	private void noticeNext(BriefParams b,Order_info o){
		System.out.println(b.getEventType());
		System.out.println(b.getRouteID());
		System.out.println(b.getTransactionID());
		System.out.println(b.getWarehouseID());
		System.out.println(b.getOrderInfo());
		String thisIP = null;
		String nextIP = null;
		String preIP = null;
		int location = -1;
		List<Factory_info> factorys = this.factoryDao.getFactorys();
		List<Integer> routes = ServicesUtil.FormatString(o.getRoute());
		System.out.println("-------输出汇报测试："+routes.toString()+"---------");
		//判断当前仓库在路径中的位置
		for(int l=0;l<routes.size();l++){
			if(ServicesUtil.FormatStringtoID(b.getWarehouseID())==routes.get(l)){
				location = l;
				break;
			}
		}
		//如果是收货,通知下一仓库
		if(b.getEventType()==1){
			//最后一站,需通知第一站订单完成
			if(location==(routes.size()-1)){
				this.orderDao.updateLastOrder((int)b.getRouteID());
				this.detailDao.updateOrderDetail((int)b.getRouteID(), 3, ServicesUtil.FormatStringtoID(b.getWarehouseID()));
				ScheduleParams schedule = new ScheduleParams();
				for(int f = 0;f < factorys.size();++f){
					if(factorys.get(f).getId()==routes.get(0)){
						thisIP = factorys.get(f).getFactoryIP();
					}
				}
				System.out.println(thisIP);
				schedule.setTransactionID(b.getTransactionID());
				schedule.setOrderInfo(b.getOrderInfo());
				//调用cxf
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
				//注册WebService接口     
				factory.setServiceClass(IForwardServices.class);     
				//设置WebService地址     
				factory.setAddress("http://"+thisIP+":8080/aspireMidware/services/forward");          
				IForwardServices forward = (IForwardServices)factory.create();
				System.out.println("invoke  received report..."); 
				boolean res = false;
				try {
					res = forward.instruct(schedule);
					System.out.println("汇报"+res);
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				this.detailDao.updateOrderDetail((int)b.getRouteID(),1, ServicesUtil.FormatStringtoID(b.getWarehouseID()));
				ScheduleParams schedule = new ScheduleParams();
				
				for(int f = 0;f < factorys.size();++f){
					if(factorys.get(f).getId()==routes.get(location)){
						thisIP = factorys.get(f).getFactoryIP();
					}
					if(factorys.get(f).getId()==routes.get(location-1)){
						preIP = factorys.get(f).getFactoryIP();
					}
					if(factorys.get(f).getId()==routes.get(location+1)){
						nextIP = factorys.get(f).getFactoryIP();
					}
				}
				System.out.println(preIP);
				System.out.println(thisIP);
				System.out.println(nextIP);
				// 设置指令
				schedule.setOrderInfo(b.getOrderInfo());
				schedule.setNextIP(nextIP);
				schedule.setNextWarehouse(ServicesUtil.FormatIDtoString(routes.get(location+1)));
				schedule.setPrevIP(preIP);
				schedule.setPrevWarehouse(ServicesUtil.FormatIDtoString(routes.get(location-1)));
				schedule.setRouteID(b.getRouteID());
				schedule.setSendTime(new Date().toString());
				schedule.setTransactionID(b.getTransactionID());
				//调用cxf
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
				//注册WebService接口     
				factory.setServiceClass(IForwardServices.class);     
				//设置WebService地址     
				factory.setAddress("http://"+thisIP+":8080/aspireMidware/services/forward");          
				IForwardServices forward = (IForwardServices)factory.create();
				System.out.println("invoke  received..."); 
				boolean res = false;
				try {
					res = forward.instruct(schedule);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(!res){
					this.detailDao.updateOrderDetail((int)b.getRouteID(), -1, routes.get(location));
				}
				System.out.println("收货调度"+res);
			}
		
			//如果是发货，更新详单，通知下一仓库准备收货
		}else{
			//更新详单
			this.detailDao.updateOrderDetail((int)b.getRouteID(),0, ServicesUtil.FormatStringtoID(b.getWarehouseID()));
			ScheduleParams schedule = new ScheduleParams();
			//通知收货仓库
			if(location==(routes.size()-2)){
				for(int f = 0;f < factorys.size();++f){
					if(factorys.get(f).getId()==routes.get(location+1)){
						thisIP = factorys.get(f).getFactoryIP();
					}
					if(factorys.get(f).getId()==routes.get(location)){
						preIP = factorys.get(f).getFactoryIP();
					}
				}
				System.out.println(preIP);
				System.out.println(thisIP);
				// 设置指令
				schedule.setOrderInfo(b.getOrderInfo());
				schedule.setPrevIP(preIP);
				schedule.setPrevWarehouse(ServicesUtil.FormatIDtoString(routes.get(location)));
				schedule.setRouteID(b.getRouteID());
				schedule.setSendTime(new Date().toString());
				schedule.setTransactionID(b.getTransactionID());
				//通知中转站
			}else{
				for(int f = 0;f < factorys.size();++f){
					if(factorys.get(f).getId()==routes.get(location+1)){
						thisIP = factorys.get(f).getFactoryIP();
					}
					if(factorys.get(f).getId()==routes.get(location)){
						preIP = factorys.get(f).getFactoryIP();
					}
					if(factorys.get(f).getId()==routes.get(location+2)){
						nextIP = factorys.get(f).getFactoryIP();
					}
				}
				System.out.println(preIP);
				System.out.println(thisIP);
				System.out.println(nextIP);
				// 设置指令
				schedule.setOrderInfo(b.getOrderInfo());
				schedule.setNextIP(nextIP);
				schedule.setNextWarehouse(ServicesUtil.FormatIDtoString(routes.get(location+2)));
				schedule.setPrevIP(preIP);
				schedule.setPrevWarehouse(ServicesUtil.FormatIDtoString(routes.get(location)));
				schedule.setRouteID(-2);
				schedule.setSendTime(new Date().toString());
				schedule.setTransactionID(b.getTransactionID());
			}
			//调用cxf
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
			//注册WebService接口     
			factory.setServiceClass(IForwardServices.class);     
			//设置WebService地址     
			factory.setAddress("http://"+thisIP+":8080/aspireMidware/services/forward");          
			IForwardServices forward = (IForwardServices)factory.create();
			System.out.println("invoke  shipping again..."); 
			boolean res = false;
			try {
				res = forward.instruct(schedule);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(!res){
				this.detailDao.updateOrderDetail((int)b.getRouteID(), -1, routes.get(location+1));
			}
			System.out.println("发货调度"+res);
		}
	}

	//注册验证
	private boolean checkFactory(SubSystem subSystem){
		if((subSystem.getFactory_Owner()!=1) && (subSystem.getFactory_Owner()!=0)){
			System.out.println("非法拥有者");
			return false;
		}
		if(subSystem.getFactory_Name()==null){
			System.out.println("非法仓库名");
			return false;
		}
		List<Factory_info> factorys = this.factoryDao.getFactorys();
		for(int i = 0; i < factorys.size();++i){
			if(subSystem.getFactory_IP().equals(factorys.get(i).getFactoryIP())){
				System.out.println("重复的ip");
				return false;
			}
		}
		return true;
	}

	@Override
	//注册仓库
	public String registerApply(SubSystem factory) {
		System.out.println(factory.getFactory_Name());
		System.out.println(factory.getFactory_IP());
		System.out.println(factory.getFactory_Loacation());
		if(!checkFactory(factory)){
			return null;
		}else{
			Factory_info f = new Factory_info();
			f.setCity(factory.getFactory_City());
			f.setDescription(factory.getFactory_Description());
			f.setFactoryIP(factory.getFactory_IP());
			f.setLocation(factory.getFactory_Loacation());
			f.setName(factory.getFactory_Name());
			f.setOwner(factory.getFactory_Owner());
			//权值，以此求出最短路径.暂时手动更新...
			//f.setWeight((int)(Math.random()*10)+1);
			String num = ""+this.factoryDao.addFactory(f);
			while(num.length() < 7){
				num="0"+num;
			}
			return num;
		}
	}
	@Override
	public boolean reportToWareHouse(DetailParams params) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean instruct(ScheduleParams schedul) {
		System.out.println("不要调用我");
		return false;
	}
	@Override
	public String expressInfo(String transactionID) {
		// TODO Auto-generated method stub
		return null;
	}

	public FactoryDao getFactoryDao() {
		return factoryDao;
	}
	@Resource
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}
	public OrderDao getOrderDao() {
		return orderDao;
	}
	@Resource
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public OrderDetailDao getDetailDao() {
		return detailDao;
	}
	@Resource
	public void setDetailDao(OrderDetailDao detailDao) {
		this.detailDao = detailDao;
	}

}
