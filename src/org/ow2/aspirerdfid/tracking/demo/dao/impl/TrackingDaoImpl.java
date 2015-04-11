package org.ow2.aspirerdfid.tracking.demo.dao.impl;
import java.util.List;
import javax.servlet.ServletContext;
import ons.client.OnsResolveClient;
import org.apache.struts2.ServletActionContext;
import org.ow2.aspirerdfid.tracking.demo.dao.TrackingDao;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionContext;
import com.zzc.client.TrackingInterfaceClient;
import com.zzc.model.QueryParams;
import com.zzc.model.Result;
import com.zzc.util.DataProcess;
import com.zzc.util.MD5Coder;
@Component("trackingDao")
public class TrackingDaoImpl implements TrackingDao {

	@Override
	public List<Result> getTracking(String epcId, QueryParams params) {
			//解析epc得到dns地址
			String address=null;
			OnsResolveClient onsClient = new OnsResolveClient("http://10.103.240.208:8082/resolveEpcis");
	        List<String> list = onsClient.QueryDsByTag(epcId);
	        for(String http:list){
	        	System.out.println(http);
	        	if(http.endsWith("tracking")){
	        		address=http;
	        	}
	        }
	        //根据DNS地址tracking
	        TrackingInterfaceClient trackingClient = new TrackingInterfaceClient(address);
	    	List<Result> array = trackingClient.queryTag(epcId,params );
	    	System.out.println("end of event service");
	    	/*
	    	 * array是搜索到的加密的结果
	    	 * 根据用户名和密码对搜索到的结果解密
	    	 * */
	    	String key = MD5Coder.getKey(params.getUid(), params.getUpw());
	    	//List<QueryResult> result = new ArrayList<QueryResult>(); 
	   
	    	//result.get(0).getCompany().getLogo();
	    	ServletContext sc = (ServletContext) ActionContext.getContext().get(ServletActionContext.SERVLET_CONTEXT);     
	        String path = sc.getRealPath("/"); 
	    	try {
	    	/*	for(int i=0;i<result.size();i++){
	    			QueryResult r = result.get(i);
	    			String logo="";
	    			String imageData="";
	    			String docData="";
	    			String dataDddress=null;
	    			if(r.getCompany().getId()==null){
	    				dataDddress=""+i;
	    			}else{
	    				dataDddress=r.getCompany().getId();
	    			}
	    			 logo+="logo/un_"+dataDddress+".png;"+"logo/"+dataDddress+".png;";
	    			 imageData+="logo/hide_"+dataDddress+".png;"+"logo/cover_"+dataDddress+".bmp;";
	    			 docData+="logo/hide_"+dataDddress+".pdf;"+"logo/cover_"+dataDddress+".pdf;";
	    			ImageFPE.generatePng(r.getCompany().getLogo(),path+"logo\\un_"+dataDddress+".png", key);
	    			ImageFPE.decryptPng(r.getCompany().getLogo(),path+"logo\\"+dataDddress+".png", key);
	    			r.getCompany().setLogo(logo);
	    			ImageHide.BMPToDataSource(r.getCompany().getImageData(),path+"logo\\hide_"+dataDddress+".png");
					ImageHide.generateCover(r.getCompany().getImageData(),path+"logo\\cover_"+dataDddress+".bmp");
					r.getCompany().setImageData(imageData);
					PdfHide.PDFToDataSource(r.getCompany().getDocData(), path+"logo\\hide_"+dataDddress+".pdf");
					PdfHide.generatePdf(r.getCompany().getDocData(), path+"logo\\cover_"+dataDddress+".pdf");
					r.getCompany().setDocData(docData);
	    		}*/

	    		if(array != null && !array.isEmpty()){
	    			DataProcess dp = new DataProcess("fieldLevel");
	    			for(int i=0;i<array.size();i++){
	    				array.get(i).setQueryResult(dp.decrypt(array.get(i), key,path+"bmp\\",path+"logo\\",path+"pdf\\",i)) ;
	    			}
	    			
	    		}
	    		else {
	    			System.out.println("Data to decrypt is null");
	    		}
			}catch(NullPointerException | IllegalArgumentException | ClassCastException  e){
				e.printStackTrace();
			}
	    	return array;
	}

}
