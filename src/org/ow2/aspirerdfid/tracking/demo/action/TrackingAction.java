package org.ow2.aspirerdfid.tracking.demo.action;

import java.util.List;
import javax.annotation.Resource;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.ow2.aspirerdfid.tracking.demo.manage.TrackingManager;
import org.ow2.aspirerdfid.tracking.demo.util.ServicesUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;
import com.zzc.db.User;
import com.zzc.model.QueryParams;
import com.zzc.model.QueryResult;
import com.zzc.model.Result;
@Component
@Scope("prototype")
public class TrackingAction extends ActionSupport {
	private String epc;
	private String username;
	private TrackingManager tm;
	private String password;
	private int company;
	private List<Result> result;
	public String execute(){
			System.out.println(username);
		 	QueryParams p = new QueryParams();
		 	if(company!=0){
		 		p.setCompany(ServicesUtil.FormatIDtoString(company));
	        }else{
	        	p.setCompany(null);
	        }
		 	System.out.println(p.getCompany());
	        //String ip = ServletActionContext.getRequest().getRemoteAddr();
	        //System.out.println(ip);
	        p.setIp("10.109.22.210");
	        p.setLatitude(39.968608);
	        p.setLongitude(116.364402);
	        p.setUid(username);
	        p.setUpw(password);
	        result = this.tm.queryTracking(epc, p);
		return "success";
	}
	@JSON(serialize=false)
	public TrackingManager getTm() {
		return tm;
	}
	@Resource
	public void setTm(TrackingManager tm) {
		this.tm = tm;
	}
	
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
	}
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	
}
