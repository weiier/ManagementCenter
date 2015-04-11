package org.ow2.aspirerdfid.tracking.demo.util;

public class Folyd {
	public  int[][] getFolyd(int[][] route,int first, int last){
		int d[][] = new int[5][5];
		int path[][] = new int[5][5];
		int num = route.length;
		int max=99;
		int i,j,k;
		//初始化
		for (i=0;i<num;i++){
			for(j=0;j<num;j++){
				if(route[i][j]<max){
					path[i][j]=j;
				}else{
					path[i][j]=-1;
				}
				d[i][j]=route[i][j];
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
		//
		for (i=0;i<num;i++){
			for(j=0;j<num;j++){
				if(j!=num-1){
					System.out.print(path[i][j]);
				}
				else {
					System.out.println(path[i][j]);
				}
			}
		}
		
		for (i=0;i<num;i++){
			for(j=0;j<num;j++){
				if(j!=num-1){
					System.out.print(d[i][j]);
				}
				else {
					System.out.println(d[i][j]);
				}
			}
		}
		//输出最短路径及值
		System.out.println(d[first][last]);
		int point;
		if(d[first][last]==max){
			System.out.println("路径不可达");
		}else{
			System.out.print("路径为：");
			point=first;
			System.out.print(point+"-->");
			while(point!=last && point!=path[point][last]){
				point = path[point][last];
				System.out.print(point+"-->");
			}
		}
		
		return path;
	}
	public static void main(String args[]){
		for(int i=4;i>0;i--){
		System.out.println(Math.random()*20+1);
		}
		System.out.println(Class.class.getClass().getResource("/").getPath());
	}
}
