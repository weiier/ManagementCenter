package org.ow2.aspirerdfid.tracking.demo.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.struts2.json.annotations.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ow2.aspirerdfid.tracking.demo.manage.OrderManager;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
import org.ow2.aspirerdfid.tracking.demo.model.Order_info;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component
@Scope("prototype")
public class OrderAction extends ActionSupport {
	private int origin;
	private int destination;
	private String itemName;
	private int itemQuantity;
	private OrderManager om;
	private String jsonStr;
	private List<Order_info> orders;
	private int id;
	private String start;
	private String end;
	private Map<Integer,List<Integer>> message;
	public String execute(){
		Order_info o = new Order_info();
		Factory_info f1 = new Factory_info();
		Factory_info f2 = new Factory_info();
		o.setItemName(itemName);
		o.setItemQuantity(itemQuantity);
		o.setStartTime(new Date());
		f1.setId(origin);
		f2.setId(destination);
		o.setOrigin(f1);
		o.setDestination(f2);
		om.publish(o,0);
		return null;
	}
	
	public String create(){
		System.out.println("jsonStr"+jsonStr);
		Map<Integer,List<Integer>> publish = new HashMap<Integer,List<Integer>>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for(int i=0; i<jsonArray.length();i++){
				JSONObject jsonJ = jsonArray.getJSONObject(i);
				Order_info o = new Order_info();
				Factory_info origin = new Factory_info();
				Factory_info des = new Factory_info();
				o.setItemName(jsonJ.getString("item"));
				o.setItemQuantity(Integer.parseInt(jsonJ.getString("quantity")));
				o.setStartTime(new Date());
				origin.setId(Integer.parseInt(jsonJ.getString("origin")));
				des.setId(Integer.parseInt(jsonJ.getString("des")));
				o.setOrigin(origin);
				o.setDestination(des);
				publish.putAll(om.publish(o,0));
			}
			message = publish;
		} catch (ParseException e) {
				e.printStackTrace();
		}
			return "create";
	}
	
	public String custom(){
		System.out.println("jsonStr"+jsonStr);
		Map<Integer,List<Integer>> publish = new HashMap<Integer,List<Integer>>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for(int i=0; i<jsonArray.length();i++){
				JSONObject jsonJ = jsonArray.getJSONObject(i);
				Order_info o = new Order_info();
				Factory_info origin = new Factory_info();
				Factory_info des = new Factory_info();
				o.setItemName(jsonJ.getString("item"));
				o.setItemQuantity(Integer.parseInt(jsonJ.getString("quantity")));
				o.setRoute(jsonJ.getString("route"));
				o.setStartTime(new Date());
				origin.setId(Integer.parseInt(jsonJ.getString("origin")));
				des.setId(Integer.parseInt(jsonJ.getString("des")));
				o.setOrigin(origin);
				o.setDestination(des);
				publish.putAll(om.publish(o,1));
			}
			message = publish;
			System.out.println("--------"+message+"-------");
		} catch (ParseException e) {
				e.printStackTrace();
		}
			return "custom";
	
	}
	
	
	public String list(){
		orders = this.om.findOrders();
		return "list";
	}

	public String show(){
		orders = this.om.findOrdersById(id);
		return "show";
	}
	public String search(){
		if(id!=0){
			orders = this.om.findOrdersByTime(start, end, id);
		}else{
			orders = this.om.findOrdersByTime(start, end);
		}
		return "search";
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
	
	@JSON(serialize=false)
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	@JSON(serialize=false)
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	@JSON(serialize=false)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
		System.out.print(itemName);
	}
	@JSON(serialize=false)
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	@JSON(serialize=false)
	public OrderManager getOm() {
		return om;
	}
	@Resource
	public void setOm(OrderManager om) {
		this.om = om;
	}

	public List<Order_info> getOrders() {
		return orders;
	}

	public void setOrders(List<Order_info> orders) {
		this.orders = orders;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@JSON(serialize=false)
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	@JSON(serialize=false)
	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Map<Integer, List<Integer>> getMessage() {
		return message;
	}

	public void setMessage(Map<Integer, List<Integer>> message) {
		this.message = message;
	}
	
}
