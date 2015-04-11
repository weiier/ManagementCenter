package org.ow2.aspirerdfid.tracking.demo.action;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.aspirerdfid.tracking.demo.dao.OrderDetailDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;
@Component
@Scope("prototype")
public class TestAction extends ActionSupport {
	private String jsonStr;
	private String message;
	private OrderDetailDao dd;
	public String execute(){
		System.out.println(jsonStr);
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for(int i=0; i<jsonArray.length();i++){
				JSONObject jsonJ = jsonArray.getJSONObject(i);
				System.out.println(jsonJ.getString("origin")+jsonJ.getString("des"));
			}
			/*for(int i=0; i<jsonArray.length();i++){
				System.out.println(jsonArray.getJSONArray(i).toString());
				JSONArray users = jsonArray.getJSONArray(i);
				for(int j = 0; j< users.length(); j++){
					JSONObject jsonJ = jsonArray.getJSONObject(j);
					System.out.println(jsonJ.getString("name"));
				}
			}*/
			message="success";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	public String detail(){
		dd.updateOrderDetail(10, 0, 1);
		return null;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		try {
			this.jsonStr = new String(jsonStr.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@JSON(serialize=false)
	public OrderDetailDao getDd() {
		return dd;
	}
	@Resource
	public void setDd(OrderDetailDao dd) {
		this.dd = dd;
	}
	
}
