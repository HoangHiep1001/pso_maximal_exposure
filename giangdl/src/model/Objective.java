package model;

import java.util.ArrayList;
import java.util.Random;

import pso_search.PSOSearch;

public class Objective {
	// Kich thuoc vung
	public static double W = 100, H = 100;
	public static ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	private static Random r = new Random();
	public final static double dx = 0.5;

	public final static double dy = 0.5;
	public final double randMax = 4;
	public int nbSegmentX;
	public int nbSegmentY;
	public double[] xk;
	public double[] yk;

	public Objective(ArrayList<Sensor> sensors, double width, double height) {
//		this.sensors = new ArrayList<Sensor>();
//		this.obstaces = obs;
		this.sensors = sensors;
		this.W = width;
		this.H = height;
		this.nbSegmentX = (int) (width / dx); // so diem chia theo x
		this.nbSegmentY = (int) (height / dy); // so diem chia theo y
		this.xk = new double[nbSegmentX + 1];
		this.yk = new double[nbSegmentY + 1];
		xk[0] = 0;
		yk[0] = 0;
		for (int i = 1; i < xk.length; i++) {
			xk[i] = xk[i - 1] + dx; // vi tri cua gen tren x
		}
	
//		for (int i = 1; i < yk.length; i++) {
//			yk[i] = yk[i - 1] + dy; // cáº­p nháº­t vá»‹ trÃ­ cá»§a y
//		yk[0] = PSOSearch.y1;
//		int kk = 0, checkk = 0;
//		double a, b;
//		for (int i = 1; i < yk.length-1; i++) {
//			double rex, rey, ress, ress1;
//			do {
//				yk[i] = yk[i - 1] + (r.nextDouble() * 2 * randMax - randMax);
//				rex = PSOSearch.x2 - xk[i];
//				rey = PSOSearch.y2 - yk[i];
//				ress = rex * rex + rey * rey;
//				rex = PSOSearch.x1 - xk[i];
//				rey = PSOSearch.y1 - yk[i];
//				ress1 = rex * rex + rey * rey;
//				if( ress1 + ress > PSOSearch.limitS ) {
//					kk = i;
//					checkk = 1;
//					break;
//				}
//				
//				if(checkk == 1) break;
//				
//			} while (yk[i] < 0 || yk[i] > this.H);
//		}
//		
//		a = (PSOSearch.y2 - yk[kk-1])/(PSOSearch.x2 - xk[kk-1]);
//		b = PSOSearch.y2 - PSOSearch.x2 * a;
//		
//		for(int i = kk; i < yk.length - 1; i++) {
//			yk[i] = a*xk[i] + b;
//		}
//		
//		yk[yk.length - 1] = PSOSearch.y2;

	}

	public double Sensor_Point(Sensor s, double x, double y) {
		double dx = x - s.p.x; //
		double dy = y - s.p.y;
		
		double d = Math.sqrt(dx * dx + dy * dy);
		return (d > Sensor.r) ? 0 : 1;
	}

	// ham nay tra ve gia tri cuong do cam bien tai 1 diem 
	public double getIP(double x, double y) {
		double value = 0;
		for (int i = 0; i < sensors.size(); i++)
			value = value + Sensor_Point(sensors.get(i), x, y);
		return value;
	}
	
	public double getIP(double x1, double y1, double x2, double y2) {
//		if (y1 < 0 || y1 > H || y2 < 0 || y2 > H)
//			return 0;
		double delta_x = x2 - x1;
		double delta_y = Math.abs(y2 - y1);
		double value = 0;
		double step = y2 < y1 ? -1 : 1;
		
		
		while (delta_y >= 1) {
			value += getIP(x1, y1);
			delta_y--;
			y1 += step;
		}
		
		value = this.getIP(x2, y2);
		
		//value += getIP(x1, y1) * Math.sqrt(delta_x * delta_x + delta_y * delta_y);
		return value;
	}

	public double[] initNormal(double ys0, double min, double max) {		//tao mang ngau nhien
		double[] ys = new double[xk.length];
		ys[0] = ys0;
		for (int i = 1; i < ys.length; i++) {
			do {
				ys[i] = ys[i - 1] + (r.nextDouble() * 2 * randMax - randMax);
			} while (ys[i] < min || ys[i] > max);
		}
		return ys;
	}
	
	public double[] initNormal(double ys0, double ysend, double min, double max) {		//tao mang ngau nhien
		double[] ys = new double[xk.length];
		ys[0] = ys0;
		for (int i = 1; i < ys.length - 1; i++) {
			do {
				ys[i] = ys[i - 1] + (r.nextDouble() * 2 * randMax - randMax);
			} while (ys[i] < min || ys[i] > max);
		}
		
		ys[ys.length - 1] = ysend; 
				
		return ys;
	}
	
	public double[] initNormal(double ys0, double min, double max,double xstart,double xend) {		//tao mang ngau nhien
		double[] ys = new double[xk.length];
		ys[0] = ys0;
		for (int i = 1; i < ys.length; i++) {
			do {
				ys[i] = ys[i - 1] + (r.nextDouble() * 2 * randMax - randMax);
			} while (ys[i] < min || ys[i] > max);
		}
		return ys;
	}
	
    public double[] initHeuristic(double ys0,double start,double end) {		//khoi tao ca the
		double[] ys = new double[xk.length];
		ys[0] = ys0;
//        ys[0] = start;
//		ys[0] = 100;
		for (int i = 1; i < xk.length; i++) {
            double min = Double.MAX_VALUE, min2 = Double.MAX_VALUE;
            int index = 0;
            int minp = (int)((ys[i - 1] - randMax) / dy);
            int maxp = (int)((ys[i - 1] + randMax) / dy);
            if (minp < 0)
                minp = 0;
            if (maxp >= yk.length)
                maxp = yk.length - 1;
            for (int j = minp; j < maxp; j++) {
                double dy = Math.abs(yk[j] - ys[i - 1]);
//                double v = getIP(xk[i], yk[j])*Math.abs(dx*dx+dy*dy);
                double v =  getIP(xk[i], yk[j]);
                if (v < min || (v == min && dy < min2)) {
//                if (v < min) {
                    min = v;
                    min2 = dy;
                    index = j;
                }
            
            }
            ys[i] = yk[index];
        }
			return ys;
	}	
	
	
	
	
	
}
