package main.java.pso_search;

import main.java.model.Point;

public class Gene extends Point {
    private double exposure;
    private double v;
    public Gene(double x,double y){
        super(x,y);
        this. v = 0;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }
}
