package org.ow2.aspirerdfid.tracking.demo.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.apache.struts2.json.annotations.JSON;
import org.ow2.aspirerdfid.tracking.demo.db.Item;
import org.ow2.aspirerdfid.tracking.demo.manage.FactoryManager;
import org.ow2.aspirerdfid.tracking.demo.manage.ItemManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component
@Scope("prototype")
public class ItemAction extends ActionSupport {
	private FactoryManager fm;
	private ItemManager im;
	private List<SubSystem> sbs;
	private Map<Integer,List<SubSystem>> maps;
	private String ids;
	private int id;
	private int limit;
	private String keyword;
	//获取所有仓库的物资
	public String list(){
		sbs = this.fm.findSubSystems();
		return "list";
	}
	
	public String schedule(){
		String[] strID = ids.split(",");
		List<Integer> l = new ArrayList<Integer>();
		for(int i=0; i<strID.length;i++){
			l.add(Integer.parseInt(strID[i]));
			System.out.println(strID[i]);
		}
		maps = this.im.getMaterialsByKey(l, keyword);
	/*	int num=0;
		for(int i=0; i<factorys.size(); i++){
			num += factorys.get(i).getItems().get(0).getQuantity();
			if(amount <= num){
				sbs.add(factorys.get(i));
				break;
			}else{
				sbs.add(factorys.get(i));
			}
		}
		if(amount > num){
			sbs.clear();
		}*/

		return "schedule";
	}
	
	//获得某一仓库的所有物资
	public String show(){
		sbs = this.fm.findSubSystemsById(id);
		return "show";
	}
	//根据关键字获取物资,数量限制需>0,id需>0
	public String search(){
		if(id==0&&limit==0){
			sbs = this.im.getMaterialsByKey(keyword);
		}else if(id!=0&&limit==0){
			sbs = this.im.getMaterialsByPramas(keyword, id);
		}else if(id==0&&limit!=0){
			sbs = this.im.getMaterialsByLimit(keyword, limit);
		}else{
			sbs = this.im.getMaterialsByPramas(keyword, id, limit);
		}
		return "search";
	}
	@JSON(serialize=false)
	public ItemManager getIm() {
		return im;
	}
	@Resource
	public void setIm(ItemManager im) {
		this.im = im;
	}
	@JSON(serialize=false)
	public FactoryManager getFm() {
		return fm;
	}
	@Resource
	public void setFm(FactoryManager fm) {
		this.fm = fm;
	}
	public List<SubSystem> getSbs() {
		return sbs;
	}
	public void setSbs(List<SubSystem> sbs) {
		this.sbs = sbs;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		try {
			this.keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, List<SubSystem>> getMaps() {
		return maps;
	}

	public void setMaps(Map<Integer, List<SubSystem>> maps) {
		this.maps = maps;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
