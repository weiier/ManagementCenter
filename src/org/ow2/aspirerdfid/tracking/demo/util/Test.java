/*package org.ow2.aspirerdfid.tracking.demo.util;
import java.io.File;
import java.io.IOException;
import java.util.List;

import ons.client.OnsResolveClient;

import com.zzc.client.RegisterInterfaceClient;
import com.zzc.client.TrackingInterfaceClient;
import com.zzc.db.User;
import com.zzc.model.*;
import com.zzc.util.DataProcess;
import com.zzc.util.ImageFPE;
import com.zzc.util.MD5Coder;

public class Test {

	    public static void main (String[] args) {
	    	String str=null;
	        OnsResolveClient onsClient = new OnsResolveClient("http://10.103.242.55:8080/resolveEpcis");
	        
	        List<String> list = onsClient.QueryDsByTag("urn:epc:id:sgtin:6901003.000000.140529003");
	        System.out.println(list);
	        for(String http:list){
	        	if(http.endsWith("tracking")){
	        		str=http;
	        		System.out.println(str);
	        	}
	        }
	        QueryParams p = new QueryParams();
	        p.setCompany("1");
//登录客户的ip
	        p.setIp("10.103.241.20");
	        p.setLatitude(38.968608);
	        p.setLongitude(116.364402);
	        p.setUid("zzc");
	        p.setUpw("zzc");
	        TrackingInterfaceClient trackingClient = new TrackingInterfaceClient(str);
	    	List<QueryResult> array = trackingClient.queryTag("urn:epc:id:sgtin:6901003.000000.140529003",p );
	    	for(int i =0;i<array.size();i++){
	    		System.out.println(array.get(i).getEvent().getAction());
	    	}
	    	System.out.println("end of event service...");
	    	System.exit(0);   
	    	
	    	String address=null;
			OnsResolveClient onsClient = new OnsResolveClient("http://10.103.242.55:8080/resolveEpcis");
	        List<String> list = onsClient.QueryDsByTag("urn:epc:id:sgtin:6901003.000000.140623005");
	        for(String http:list){
	        	System.out.println(http);
	        	if(http.endsWith("tracking")){
	        		address=http;
	        		break;
	        	}
	        }
	        
	        QueryParams p = new QueryParams();
	        p.setCompany("1");
//登录客户的ip
	        p.setIp("10.103.241.20");
	        p.setLatitude(38.968608);
	        p.setLongitude(116.364402);
	        p.setUid("zzc");
	        p.setUpw("zzc");
	        
	        //根据DOS地址tracking
	        TrackingInterfaceClient trackingClient = new TrackingInterfaceClient(address);
	    	List<QueryResult> array = trackingClient.queryTag("urn:epc:id:sgtin:6901003.000000.140623005",p);
	    	System.out.println("end of event service");
	    	
	    	 * array是搜索到的加密的结果
	    	 * 根据用户名和密码对搜索到的结果解密
	    	 * 
	    	String key = MD5Coder.getKey("zzc", "zzc");
	    	List<QueryResult> result = DataProcess.decrypt(array, key);
	    	//result.get(0).getCompany().getLogo();
	    	
	    	try {
				ImageFPE.generatePng(result.get(0).getCompany().getLogo(), "C:"+File.separator+"logo\\undecrypt.png", key);
				ImageFPE.decryptPng(result.get(0).getCompany().getLogo(), "C:\\logo\\decrypt.png", key);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassCastException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	RegisterInterfaceClient client = new RegisterInterfaceClient("http://10.103.242.55:8080/epcis_ds/register");
	    	User u = new User();
	    	u.setPassword("admin");
	    	u.setUsername("test");
	    	u.setUlevel(0);
	    	try{
	    	System.out.println(client.addUser(u));
	    	}catch(Exception e){
	    		System.out.println("refued");
	    	}
	    }
	}

	public static void main(String args[]){
	
	TrackingInterfaceClient trackingClient = new TrackingInterfaceClient("http://10.103.241.55:8080/epcis_ds/tracking");

	
	List<QueryResult> array = trackingClient.queryTag("urn:epc:id:gid:1.0.230760449");
	for(int i =0;i<array.size();i++){
		System.out.println(array.get(i).getEvent().getAction());
	}
	System.out.println("end of event service...");
	System.exit(0);   
	}
	
*/