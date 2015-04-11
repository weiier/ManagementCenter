package org.ow2.aspirerdfid.tracking.demo.dao.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ow2.aspirerdfid.tracking.demo.dao.ItemDao;
import org.ow2.aspirerdfid.tracking.demo.db.Item;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.springframework.stereotype.Component;
import com.ws.services.ISearchServices;
@Component("itemDao")
//注解并没有加名称
public class ItemDaoImpl implements ItemDao {

	@Override
	public SubSystem findMaterials(String keyword, String IP) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		//注册WebService接口     
		factory.setServiceClass(ISearchServices.class);     
		//设置WebService地址     
		factory.setAddress("http://"+IP+":8080/aspireMidware/services/search");          
		ISearchServices search = (ISearchServices)factory.create();
		System.out.println("invoke helloServices webservice..."); 
		SubSystem sb = new SubSystem();
		List<Item> material;
		System.out.println(keyword);
				try {
					material = search.getMaterials(keyword);
					sb.setItems(material);
					sb.setFactory_State(0);
					System.out.println("online");			
				} catch (UnsupportedEncodingException e) {
					sb.setFactory_State(3);
				}catch(Exception e){
					sb.setFactory_State(1);
					System.out.println("offline");
				}
				return sb;	
	}

	@Override
	public SubSystem findMaterialsWithLimit(String key, String ip, int limit) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		//注册WebService接口     
		factory.setServiceClass(ISearchServices.class);     
		//设置WebService地址     
		factory.setAddress("http://"+ip+":8080/aspireMidware/services/search");          
		ISearchServices search = (ISearchServices)factory.create();
		System.out.println("invoke helloServices webservice..."); 
		SubSystem sb = new SubSystem();
		List<Item> material;
		List<Item> withLimit = new ArrayList<Item>();
		System.out.println(key);
				try {
					material = search.getMaterials(key);
					if(material == null||material.isEmpty()){
						sb.setItems(null);
						sb.setFactory_State(0);
						System.out.println("online");			
					}else{
						for(int s=0;s<material.size();s++){
							if(material.get(s).getQuantity() >= limit){
								withLimit.add(material.get(s));
							}
						}
						sb.setItems(withLimit);
						sb.setFactory_State(0);
						System.out.println("online");			
					}
				} catch (UnsupportedEncodingException e) {
					sb.setFactory_State(3);
				}catch(Exception e){
					sb.setFactory_State(1);
					System.out.println("offline");
				}
				return sb;	
	}

}
