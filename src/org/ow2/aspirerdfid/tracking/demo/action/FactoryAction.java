package org.ow2.aspirerdfid.tracking.demo.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.ow2.aspirerdfid.tracking.demo.manage.FactoryManager;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Component
@Scope("prototype")
public class FactoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SubSystem> subs;
	private List<Factory_info> factorys;
	private FactoryManager fm;
	private int level;
	private int id;
	public String list(){
		//level Îª1£¬·µ»Ø²Ö¿â×´Ì¬¼°¿â´æ
		if(level==1){
			subs = this.fm.findSubSystemsState();
		}else{
			factorys = this.fm.findFactorys();
			/*System.out.println("------");
			ActionContext ac = ActionContext.getContext();     
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);     
	        String path = sc.getRealPath("/"); 
			System.out.println(path);*/
			/*String upload_filepath =ServletActionContext.getServletContext().getRealPath("/"); 
			System.out.println(upload_filepath);*/
		}
		return "list";
	}
	
	public String show(){
		//level Îª1£¬·µ»Ø²Ö¿â×´Ì¬¼°¿â´æ
		if(level==1){
			subs = this.fm.findSubSystemsStateById(id);
		}else{
			factorys = this.fm.findFactorysById(id);
		}
		return "show";
	}
	public List<SubSystem> getSubs() {
		return subs;
	}


	public void setSubs(List<SubSystem> subs) {
		this.subs = subs;
	}


	@JSON(serialize=false)
	public FactoryManager getFm() {
		return fm;
	}
	@Resource
	public void setFm(FactoryManager fm) {
		this.fm = fm;
	}

	@JSON(serialize=false)
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public List<Factory_info> getFactorys() {
		return factorys;
	}


	public void setFactorys(List<Factory_info> factorys) {
		this.factorys = factorys;
	}
	@JSON(serialize=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
