package org.ow2.aspirerdfid.tracking.demo.manage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.ow2.aspirerdfid.tracking.demo.dao.FactoryDao;
import org.ow2.aspirerdfid.tracking.demo.dao.ItemDao;
import org.ow2.aspirerdfid.tracking.demo.dao.LogDao;
import org.springframework.stereotype.Component;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_log;
import org.ow2.aspirerdfid.tracking.demo.util.Path;
import org.ow2.aspirerdfid.tracking.demo.util.ServicesUtil;
@Component
public class ItemManager {
	private ItemDao itemDao;
	private FactoryDao factoryDao;
	private LogDao logDao;
	//根据关键字获取所有仓库的相关物资
	public List<SubSystem> getMaterialsByKey(String key){
		List<Factory_info> factorys = this.factoryDao.getFactorys();
		List<SubSystem> subs = new ArrayList<SubSystem>();
		for(int i = 0; i< factorys.size();i++){
			SubSystem sub = new SubSystem();
			SubSystem subDetail = this.itemDao.findMaterials(key, factorys.get(i).getFactoryIP());
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
	
	//根据关键字获取所有仓库的相关物资，根据权重排序,返回库存
		public Map<Integer,List<SubSystem>> getMaterialsByKey(List<Integer> ids,String key){
			List<Factory_info> factorys = this.factoryDao.getFactorys();
			List<SubSystem> subs = new ArrayList<SubSystem>();
			for(int i = 0; i< factorys.size();i++){
				SubSystem sub = new SubSystem();	
				SubSystem subDetail = this.itemDao.findMaterials(key, factorys.get(i).getFactoryIP());
				if(subDetail.getItems()!=null&&!subDetail.getItems().isEmpty()&&!ids.contains(factorys.get(i).getId())){
					sub.setFactory_ID(""+factorys.get(i).getId());
					sub.setFactory_IP(factorys.get(i).getFactoryIP());
					sub.setFactory_City(factorys.get(i).getCity());
					sub.setFactory_State(subDetail.getFactory_State());
					sub.setItems(subDetail.getItems());
					subs.add(sub);
				}
			}
			
			Map<Integer,List<SubSystem>> resultMap = new HashMap<Integer,List<SubSystem>>();
			for(int m=0; m<ids.size();m++){
				List<SubSystem> results = new ArrayList<SubSystem>();
				try{
		            FileInputStream fis = new FileInputStream("C:\\Workspaces\\aspireRFIDonsTrackingService"+File.separator+"src"+File.separator+"path.txt");
		            ObjectInputStream ois = new ObjectInputStream(fis);
		            Path p = (Path) ois.readObject();
		            ois.close();
		            //排序
		            List<String> sort = new ArrayList<String>();
		            for(int i=0; i < subs.size(); i++){
						int max = 999;
						SubSystem s  = null;
						int flag = -1;
						for(int j=0; j< subs.size();++j){
							if(!sort.contains(subs.get(j).getFactory_ID())){
								int factory_id = ServicesUtil.FormatStringtoID(subs.get(j).getFactory_ID());
								if(p.getDistance()[ids.get(m)-1][factory_id-1]<max){
									max = p.getDistance()[ids.get(m)-1][factory_id-1];
									s = subs.get(j);
									flag = j;
								}
							}
						}
						sort.add(subs.get(flag).getFactory_ID());
						results.add(s);
					}
				}catch(Exception e){e.printStackTrace();}
				
				resultMap.put(ids.get(m), results);
			}
			return resultMap;
		}
		
	//根据关键字及数量限制获取所有仓库的相关物资
		public List<SubSystem> getMaterialsByLimit(String key,int limit){
			List<Factory_info> factorys = this.factoryDao.getFactorys();
			List<SubSystem> subs = new ArrayList<SubSystem>();
			for(int i = 0; i< factorys.size();i++){
				SubSystem sub = new SubSystem();
				SubSystem subDetail = this.itemDao.findMaterialsWithLimit(key, factorys.get(i).getFactoryIP(),limit);
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
		
	//根据关键字获得某一仓库的物资
	public List<SubSystem> getMaterialsByPramas(String key,int id){
		List<Factory_info> factorys= this.factoryDao.getFactorysById(id);
		List<SubSystem> subs = new ArrayList<SubSystem>();
		for(int i = 0; i< factorys.size();i++){
			SubSystem sub = new SubSystem();
			SubSystem subDetail = this.itemDao.findMaterials(key, factorys.get(i).getFactoryIP());
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
	//根据关键字及数量限制获得某一仓库的物资
	public List<SubSystem> getMaterialsByPramas(String key,int id,int limit){
		List<Factory_info> factorys= this.factoryDao.getFactorysById(id);
		List<SubSystem> subs = new ArrayList<SubSystem>();
		for(int i = 0; i< factorys.size();i++){
			SubSystem sub = new SubSystem();
			SubSystem subDetail = this.itemDao.findMaterialsWithLimit(key, factorys.get(i).getFactoryIP(),limit);
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
	
	public ItemDao getItemDao() {
		return itemDao;
	}
	@Resource
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
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
