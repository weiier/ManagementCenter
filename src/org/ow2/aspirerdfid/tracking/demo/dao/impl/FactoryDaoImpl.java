package org.ow2.aspirerdfid.tracking.demo.dao.impl;


import java.util.List;
import javax.annotation.Resource;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ow2.aspirerdfid.tracking.demo.dao.FactoryDao;
import org.ow2.aspirerdfid.tracking.demo.db.Item;
import org.ow2.aspirerdfid.tracking.demo.db.ScheduleParams;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.ws.services.IForwardServices;
import com.ws.services.ISearchServices;
@Component("factoryDao")
public class FactoryDaoImpl implements FactoryDao{
	private HibernateTemplate hibernateTemplate;
	@Override
	public List<Factory_info> getFactorys() {
		return this.hibernateTemplate.find("from Factory_info");
	}
	
	@Override
	public List<Factory_info> getFactorysById(int id) {
	 return hibernateTemplate.find("from Factory_info f where f.id='" + id +"'");
	}

	@Override
	public int addFactory(Factory_info f) {
		this.hibernateTemplate.save(f);
		return f.getId();
	}
	
	@Override
	public SubSystem getFactoryDetail(String ip) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		//ע��WebService�ӿ�     
		factory.setServiceClass(ISearchServices.class);     
		//����WebService��ַ     
		factory.setAddress("http://"+ip+":8080/aspireMidware/services/search");          
		ISearchServices search = (ISearchServices)factory.create();
		System.out.println("invoke factory webservice..."); 
		SubSystem sb = new SubSystem();
		try {
				List<Item> material = search.getMaterials("");
				sb.setItems(material);
				sb.setFactory_State(0);
				System.out.println("����");
				return sb;
			}catch(Exception e){
				System.out.println("����");
				sb.setFactory_State(1);
				return sb;
			}
	}
	
	public SubSystem getFactoryState(String ip){
			ScheduleParams schedule = new ScheduleParams();
			schedule.setRouteID(-3);
			SubSystem sb = new SubSystem();
			//����cxf
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
			//ע��WebService�ӿ�     
			factory.setServiceClass(IForwardServices.class);     
			//����WebService��ַ     
			factory.setAddress("http://"+ip+":8080/aspireMidware/services/forward");     
			IForwardServices forward = (IForwardServices)factory.create();
			System.out.println("invoke  factory service..."); 
			boolean res = false;
			try {
					 res = forward.instruct(schedule);
					 if(res){
						 sb.setFactory_State(0);
					 }else{
						 sb.setFactory_State(1);
					 }
			}catch(Exception e){
					e.printStackTrace();
					sb.setFactory_State(1);
			}
			System.out.println("�ֿ�״̬��"+res);
			return sb;
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
