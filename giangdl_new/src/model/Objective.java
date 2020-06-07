package model;

import java.util.ArrayList;
import java.util.Random;

import pso_search.PSOSearch;

public class Objective {
	// Kich thuoc vung
	public static double W = 100, H = 100;
	public static ArrayList<Sensor> sensors = new ArrayList<Sensor>();

	private static Random r = new Random();
//	public final double dx = 0.1;
	
	// sua dx = dy ( 0.1 -> 0.5 )
	public final static double dx = 0.5;
	public final static double dy = 0.5;
	
	public final double randMax = 1.3;
	public int nbSegmentX;
	public int nbSegmentY;
	public double[] xk;
	public double[] yk;

	public Objective(ArrayList<Sensor> sensors, double width, double height) {
//		this.sensors = new ArrayList<Sensor>();
		this.sensors = sensors;
		this.W = width;
		this.H = height;
		this.nbSegmentX = (int) (width / dx); // so diem chia theo x
		this.nbSegmentY = (int) (height / dy); // so diem chia theo y
		this.xk = new double[nbSegmentX + 1];
		this.yk = new double[nbSegmentY + 1];
		xk[0] = 0;
		yk[0] = 0;
		for (int i = 1; i < yk.length; i++) {
			yk[i] = yk[i - 1] + dy; // vi tri cua gen tren x
		}
	}

	public double Sensor_Point(Sensor s, double x, double y) {
		double dx = x - s.p.x; //
		double dy = y - s.p.y;
		
		double d = Math.sqrt(dx * dx + dy * dy);
//		System.out.println("bankinh la " + Sensor.r)
		return (d > s.r) ? 0 : 1;
	}

	// ham nay tra ve gia tri cuong do cam bien tai 1 diem 
	public double getIP(double x, double y) {
		double value = 0;
		for (int i = 0; i < sensors.size(); i++)
			value = value + Sensor_Point(sensors.get(i), x, y);
		return value;
	}
	
//	public double getIP1() {
//		double value = 0;
//		double x = 44.67;
//		double y = 60.8;
//		for (int i = 0; i < sensors.size(); i++) {
//			value = value + Sensor_Point(sensors.get(i), x, y);
////			System.out.println("ban kinh la " + sensors.get(i).r);
//		}
//			
//		System.out.println("gia tri la : " + value);
//		return value;
//	}
	
}
