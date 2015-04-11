package org.ow2.aspirerdfid.tracking.demo.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.ow2.aspirerdfid.tracking.demo.dao.FactoryDao;
import org.ow2.aspirerdfid.tracking.demo.dao.LogDao;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_log;
import org.springframework.stereotype.Component;

@Component
public class FactoryManager {
	private FactoryDao factoryDao;
	private LogDao logDao;
	
	//获得仓库详细信息
	public List<SubSystem> findSubSystems(){
		List<SubSystem> subs= new ArrayList<SubSystem>();
		List<Factory_info> factorys = new ArrayList<Factory_info>();
		factorys = this.factoryDao.getFactorys();
		for(int i = 0; i< factorys.size();i++){
			SubSystem sub = new SubSystem();
			SubSystem subDetail = factoryDao.getFactoryDetail(factorys.get(i).getFactoryIP());
			sub.setFactory_City(factorys.get(i).getCity());
			sub.setFactory_Description(factorys.get(i).getDescription());
			sub.setFactory_ID(""+factorys.get(i).getId());
			sub.setFactory_IP(factorys.get(i).getFactoryIP());
			sub.setFactory_Loacation(factorys.get(i).getLocation());
			sub.setFactory_Name(factorys.get(i).getName());
			sub.setFactory_Owner(factorys.get(i).getOwner());
			sub.setFactory_State(subDetail.getFactory_State());
			sub.setItems(subDetail.getItems());
			subs.add(sub);
			
			//加入仓库日志
			Factory_log log = new Factory_log();
			log.setFactory(factorys.get(i));
			log.setFactoryState(subDetail.getFactory_State());
			log.setStateTime(new Date());
			//this.logDao.addLog(log);
		}
			return subs;
	}
	//根据id获取仓库详细信息
	public List<SubSystem>findSubSystemsById(int id){
		List<SubSystem> sb= new ArrayList<SubSystem>();
		List<Factory_info> factorys = new ArrayList<Factory_info>();
		factorys = this.factoryDao.getFactorysById(id);
		for(int i = 0; i< factorys.size();i++){
			SubSystem sub = new SubSystem();
			SubSystem subDetail = factoryDao.getFactoryDetail(factorys.get(i).getFactoryIP());
			sub.setFactory_City(factorys.get(i).getCity());
			sub.setFactory_Description(factorys.get(i).getDescription());
			sub.setFactory_ID(""+factorys.get(i).getId());
			sub.setFactory_IP(factorys.get(i).getFactoryIP());
			sub.setFactory_Loacation(factorys.get(i).getLocation());
			sub.setFactory_Name(factorys.get(i).getName());
			sub.setFactory_Owner(factorys.get(i).getOwner());
			sub.setFactory_State(subDetail.getFactory_State());
			sub.setItems(subDetail.getItems());
			sb.add(sub);
			//加入仓库日志
			Factory_log log = new Factory_log();
			log.setFactory(factorys.get(i));
			log.setFactoryState(subDetail.getFactory_State());
			log.setStateTime(new Date());
			//this.logDao.addLog(log);
		}
			return sb;
	}
	
	//获得仓库状态
	public List<SubSystem> findSubSystemsState(){
		List<SubSystem> subs= new ArrayList<SubSystem>();
		List<Factory_info> factorys = new ArrayList<Factory_info>();
		factorys = this.factoryDao.getFactorys();
		for(int i = 0; i< factorys.size();i++){
			SubSystem sub = new SubSystem();
			SubSystem subState = factoryDao.getFactoryState(factorys.get(i).getFactoryIP());
			sub.setFactory_City(factorys.get(i).getCity());
			sub.setFactory_Description(factorys.get(i).getDescription());
			sub.setFactory_ID(""+factorys.get(i).getId());
			sub.setFactory_IP(factorys.get(i).getFactoryIP());
			sub.setFactory_Loacation(factorys.get(i).getLocation());
			sub.setFactory_Name(factorys.get(i).getName());
			sub.setFactory_Owner(factorys.get(i).getOwner());
			sub.setFactory_State(subState.getFactory_State());
			subs.add(sub);
			
			//加入仓库日志
			Factory_log log = new Factory_log();
			log.setFactory(factorys.get(i));
			log.setFactoryState(subState.getFactory_State());
			log.setStateTime(new Date());
			//this.logDao.addLog(log);
		}
			return subs;
	}
	
	//根据id获取仓库状态
		public List<SubSystem>findSubSystemsStateById(int id){
			List<SubSystem> sb= new ArrayList<SubSystem>();
			List<Factory_info> factorys = new ArrayList<Factory_info>();
			factorys = this.factoryDao.getFactorysById(id);
			for(int i = 0; i< factorys.size();i++){
				SubSystem sub = new SubSystem();
				SubSystem subState = factoryDao.getFactoryState(factorys.get(i).getFactoryIP());
				sub.setFactory_City(factorys.get(i).getCity());
				sub.setFactory_Description(factorys.get(i).getDescription());
				sub.setFactory_ID(""+factorys.get(i).getId());
				sub.setFactory_IP(factorys.get(i).getFactoryIP());
				sub.setFactory_Loacation(factorys.get(i).getLocation());
				sub.setFactory_Name(factorys.get(i).getName());
				sub.setFactory_Owner(factorys.get(i).getOwner());
				sub.setFactory_State(subState.getFactory_State());
				sb.add(sub);
				//加入仓库日志
				Factory_log log = new Factory_log();
				log.setFactory(factorys.get(i));
				log.setFactoryState(subState.getFactory_State());
				log.setStateTime(new Date());
				//this.logDao.addLog(log);
			}
				return sb;
		}
	
	public List<Factory_info> findFactorys(){
		return this.factoryDao.getFactorys();
	}
	
	public List<Factory_info> findFactorysById(int id){
		return this.factoryDao.getFactorysById(id);
	}
	public FactoryDao getFactoryDao() {
		return factoryDao;
	}
	@Resource
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
	}
	public LogDao getLogDao() {
		return logDao;
	}
	@Resource
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}
	
	
}
