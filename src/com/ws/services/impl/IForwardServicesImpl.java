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
	//�㱨
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
	//�ٴη������֪ͨ��һ���ֿ⣬�����¼�֪ͨ��һ�ֿ�׼���ջ����ջ��¼�ҲҪ֪ͨ��һ�ֿ⣬���ж��ǲ����ջ��ֿ�
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
		System.out.println("-------����㱨���ԣ�"+routes.toString()+"---------");
		//�жϵ�ǰ�ֿ���·���е�λ��
		for(int l=0;l<routes.size();l++){
			if(ServicesUtil.FormatStringtoID(b.getWarehouseID())==routes.get(l)){
				location = l;
				break;
			}
		}
		//������ջ�,֪ͨ��һ�ֿ�
		if(b.getEventType()==1){
			//���һվ,��֪ͨ��һվ�������
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
				//����cxf
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
				//ע��WebService�ӿ�     
				factory.setServiceClass(IForwardServices.class);     
				//����WebService��ַ     
				factory.setAddress("http://"+thisIP+":8080/aspireMidware/services/forward");          
				IForwardServices forward = (IForwardServices)factory.create();
				System.out.println("invoke  received report..."); 
				boolean res = false;
				try {
					res = forward.instruct(schedule);
					System.out.println("�㱨"+res);
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
				// ����ָ��
				schedule.setOrderInfo(b.getOrderInfo());
				schedule.setNextIP(nextIP);
				schedule.setNextWarehouse(ServicesUtil.FormatIDtoString(routes.get(location+1)));
				schedule.setPrevIP(preIP);
				schedule.setPrevWarehouse(ServicesUtil.FormatIDtoString(routes.get(location-1)));
				schedule.setRouteID(b.getRouteID());
				schedule.setSendTime(new Date().toString());
				schedule.setTransactionID(b.getTransactionID());
				//����cxf
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
				//ע��WebService�ӿ�     
				factory.setServiceClass(IForwardServices.class);     
				//����WebService��ַ     
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
				System.out.println("�ջ�����"+res);
			}
		
			//����Ƿ����������굥��֪ͨ��һ�ֿ�׼���ջ�
		}else{
			//�����굥
			this.detailDao.updateOrderDetail((int)b.getRouteID(),0, ServicesUtil.FormatStringtoID(b.getWarehouseID()));
			ScheduleParams schedule = new ScheduleParams();
			//֪ͨ�ջ��ֿ�
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
				// ����ָ��
				schedule.setOrderInfo(b.getOrderInfo());
				schedule.setPrevIP(preIP);
				schedule.setPrevWarehouse(ServicesUtil.FormatIDtoString(routes.get(location)));
				schedule.setRouteID(b.getRouteID());
				schedule.setSendTime(new Date().toString());
				schedule.setTransactionID(b.getTransactionID());
				//֪ͨ��תվ
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
				// ����ָ��
				schedule.setOrderInfo(b.getOrderInfo());
				schedule.setNextIP(nextIP);
				schedule.setNextWarehouse(ServicesUtil.FormatIDtoString(routes.get(location+2)));
				schedule.setPrevIP(preIP);
				schedule.setPrevWarehouse(ServicesUtil.FormatIDtoString(routes.get(location)));
				schedule.setRouteID(-2);
				schedule.setSendTime(new Date().toString());
				schedule.setTransactionID(b.getTransactionID());
			}
			//����cxf
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
			//ע��WebService�ӿ�     
			factory.setServiceClass(IForwardServices.class);     
			//����WebService��ַ     
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
			System.out.println("��������"+res);
		}
	}

	//ע����֤
	private boolean checkFactory(SubSystem subSystem){
		if((subSystem.getFactory_Owner()!=1) && (subSystem.getFactory_Owner()!=0)){
			System.out.println("�Ƿ�ӵ����");
			return false;
		}
		if(subSystem.getFactory_Name()==null){
			System.out.println("�Ƿ��ֿ���");
			return false;
		}
		List<Factory_info> factorys = this.factoryDao.getFactorys();
		for(int i = 0; i < factorys.size();++i){
			if(subSystem.getFactory_IP().equals(factorys.get(i).getFactoryIP())){
				System.out.println("�ظ���ip");
				return false;
			}
		}
		return true;
	}

	@Override
	//ע��ֿ�
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
			//Ȩֵ���Դ�������·��.��ʱ�ֶ�����...
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
		System.out.println("��Ҫ������");
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
