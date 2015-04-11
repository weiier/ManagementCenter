package org.ow2.aspirerdfid.tracking.demo.dao.impl;

import org.ow2.aspirerdfid.tracking.demo.dao.RegisterDao;
import org.springframework.stereotype.Component;

import com.zzc.client.RegisterInterfaceClient;
import com.zzc.db.User;
@Component("registerDao")
public class RegisterDaoImpl implements RegisterDao {

	@Override
	public String addUser(User u,String ip) {
		RegisterInterfaceClient client = new RegisterInterfaceClient("http://"+ip+":8080/epcis_ds/register");
		String message = null;
		try{
			message = client.addUser(u);
		}catch(Exception e){
			message = "connectFailed";
		}
		System.out.println(message);
		return message;
	}
}
