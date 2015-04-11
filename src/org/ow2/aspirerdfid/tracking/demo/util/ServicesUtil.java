package org.ow2.aspirerdfid.tracking.demo.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;

public class ServicesUtil {
	public static int max=999;
	public static List<Integer> getRoute(List<Factory_info> f,int id_1,int id_2){
		/*	String[][] map = new String[f.size()][];
		for(int s=0;s < f.size();s++){
			 map[s] = f.get(s).getWeight().split(",");
		}*/		
		 return getRoute(id_1-1,id_2-1);
	
		/*List<Integer> l = new ArrayList<Integer>();
		if(id_1==3&&id_2==4){
			l.add(id_1);
			l.add(1);
			l.add(id_2);
		}else{
			l.add(id_1);
			l.add(id_2);
		}
		return l;*/
	}
	public static String FormatList(List<Integer> list){
		 String route = "";
			for (int i =0;i<list.size();i++){
				if(i!=list.size()-1)route+=list.get(i)+",";
				else route+=list.get(i);
			}
			return route;
	}
	public static List<Integer> FormatString(String string){
		List<Integer> list = new ArrayList<Integer>();
		String liststr[] = string.split(",");
		for (String s:liststr){
			list.add(Integer.parseInt(s));			
		}
		return list;
	}
	public static String FormatIDtoString(int id){
		String num = ""+id;
		while(num.length() < 7){
			num="0"+num;
		}
		return num;
	}
	
	public static  int FormatStringtoID(String num){
		int id = 0;
		for(int i = 0; i < 7;i++){
			if(num.charAt(i)!='0'){
				id = Integer.parseInt(num.substring(i, num.length()));
				//System.out.println(id);
				break;
			}
		}
		return id;
	}
	//需传入经过split的factory权值
	public static  void  createRouteByFolyd(String[][] route){
		int d[][] = new int[100][100];
		int path[][] = new int[100][100];
		int num = route.length;
		int i,j,k;
		//初始化
		for (i=0;i<num;i++){
			for(j=0;j<num;j++){
				if(Integer.parseInt(route[i][j])<max){
					path[i][j]=j;
				}else{
					path[i][j]=-1;
				}
				d[i][j]=Integer.parseInt(route[i][j]);
			}
		}
		//计算最短路径及路径长度
		for(k=0;k<num;k++){
			for (i=0;i<num;i++){
				for(j=0;j<num;j++){
					if(d[i][j]>d[i][k]+d[k][j]){
						d[i][j] = d[i][k] + d[k][j];
						path[i][j] = path[i][k];
					}
				}
			}
		}
		//将路径序列化
		
		Path p = new Path();
		p.setDistance(d);
		p.setP(path);
		
		 try {
              FileOutputStream fos = new FileOutputStream("src"+File.separator+"path.txt");
              ObjectOutputStream oos = new ObjectOutputStream(fos);           
              oos.writeObject(p);
              oos.close();                       
		 } catch (Exception e) {  e.printStackTrace();   }
		
		
	}
	
	public static  List<Integer> getRoute(int first, int last){
		List<Integer> list = new ArrayList<Integer>();
		try {
            FileInputStream fis = new FileInputStream("C:\\Workspaces\\aspireRFIDonsTrackingService"+File.separator+"src"+File.separator+"path.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Path p = (Path) ois.readObject();
            ois.close();
            int num = 4;
            //打印测试
			for (int i=0;i<num;i++){
				for(int j=0;j<num;j++){
					if(j!=num-1){
						System.out.print(p.getP()[i][j]);
					}
					else {
						System.out.println(p.getP()[i][j]);
					}
				}
			}
			
			for (int i=0;i<num;i++){
				for(int j=0;j<num;j++){
					if(j!=num-1){
						System.out.print(p.getDistance()[i][j]);
					}
					else {
						System.out.println(p.getDistance()[i][j]);
					}
				}
			}
			
			//输出最短路径及值
			int point;
			if(p.getDistance()[first][last]==max){
				System.out.println("路径不可达");
			}else{
				System.out.print("路径为：");
				point=first;
				list.add(point+1);
				System.out.print(point+"-->");
				while(point!=last && point!=p.getP()[point][last]){
					point = p.getP()[point][last];
					list.add(point+1);
					System.out.println(point+"-->");
				}
			}
    } catch (Exception ex) {
            ex.printStackTrace();
    }
		
		return list;
	}
	
	public static void main(String args[]){
		ServicesUtil.getRoute(3, 2);
	}
}
