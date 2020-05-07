package model;

public class Sensor {
	public Point p; 
	public static double r;

	
	public Sensor(Point p, double r) {
		this.p = p;
		this.r = r;
	}
	
	public Sensor(double x, double y, double r) {
		this.p = new Point(x, y);
		this.r = r;
	}

}
