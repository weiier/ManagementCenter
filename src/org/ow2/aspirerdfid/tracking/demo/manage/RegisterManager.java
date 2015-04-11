package org.ow2.aspirerdfid.tracking.demo.manage;

import java.util.List;

import javax.annotation.Resource;

import org.ow2.aspirerdfid.tracking.demo.dao.FactoryDao;
import org.ow2.aspirerdfid.tracking.demo.dao.RegisterDao;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.springframework.stereotype.Component;

import com.zzc.db.User;

@Component
public class RegisterManager {
	private RegisterDao registerDao;
	private FactoryDao factoryDao;
	public String addUser(User u){
		String message = "";
		List<Factory_info> factorys = factoryDao.getFactorys();
		for(Factory_info f : factorys){
			message += f.getId()+":"+this.getRegisterDao().addUser(u, f.getFactoryIP())+";";
		}
		System.out.println(message);
		return message;
	}
	
	public RegisterDao getRegisterDao() {
		return registerDao;
	}
	@Resource
	public void setRegisterDao(RegisterDao registerDao) {
		this.registerDao = registerDao;
	}

	public FactoryDao getFactoryDao() {
		return factoryDao;
	}
	@Resource
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}
	
}
