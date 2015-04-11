package org.ow2.aspirerdfid.tracking.demo.action;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.ow2.aspirerdfid.tracking.demo.manage.RegisterManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.zzc.db.User;
@Component
@Scope("prototype")
public class RegisterAction extends ActionSupport{
	private RegisterManager rm;
	private String uname;
	private String upw;
	private String result;
	public String execute(){
		User u = new User();
		u.setPassword(upw);
		u.setUsername(uname);
		u.setUlevel(0);
		this.rm.addUser(u);
		result = "success";
		return "message";
	}
	
	@JSON(serialize=false)
	public RegisterManager getRm() {
		return rm;
	}
	@Resource
	public void setRm(RegisterManager rm) {
		this.rm = rm;
	}
	@JSON(serialize=false)
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	@JSON(serialize=false)
	public String getUpw() {
		return upw;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
