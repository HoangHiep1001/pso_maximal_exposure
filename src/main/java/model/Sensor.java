package main.java.model;

public class Sensor {
    public Point point;
    private double r;

    public Sensor(Point point, double r) {
        this.point = point;
        this.r = r;
    }
    public Sensor(double x,double y,double r){
        this.point = new Point(x,y);
        this.r = r;
    }
    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
}
