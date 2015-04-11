package org.ow2.aspirerdfid.tracking.demo.util;

import java.util.List;
import org.ow2.aspirerdfid.tracking.demo.dao.FactoryDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFolydStatic {
	
	public static void main(String args[]){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		FactoryDao fd = (FactoryDao) context.getBean("factoryDao");
		String[][] map = new String[fd.getFactorys().size()][];
		for(int s=0;s < fd.getFactorys().size();s++){
			 map[s] = fd.getFactorys().get(s).getWeight().split(",");
		}
		ServicesUtil.createRouteByFolyd(map);
		List<Integer> list = ServicesUtil.getRoute(2, 3);
		for(Integer l : list){
			System.out.println(l);
		}
		}
}

