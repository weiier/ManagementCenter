package org.ow2.aspirerdfid.tracking.demo.util;
import java.io.Serializable;

public class Path  implements Serializable{
	private int distance[][];
	private int p[][];
	public int[][] getDistance() {
		return distance;
	}
	public void setDistance(int[][] distance) {
		this.distance = distance;
	}
	public int[][] getP() {
		return p;
	}
	public void setP(int[][] p) {
		this.p = p;
	}
	
}
