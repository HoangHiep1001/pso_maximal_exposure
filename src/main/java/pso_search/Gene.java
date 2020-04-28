package main.java.pso_search;

import main.java.model.Objective;
import main.java.model.Point;

public class Gene extends Point {
    public double exposure;
    public double v;

    public Gene(double x, double y) {
        super(x, y);
        this.v = 0;
    }
    public Gene(double x, double y,Objective ob) {
        super(x, y);
        this.v = 0;
        this.exposure = ob.getIP(x,y);
    }
    public Gene(double x, double y, double x1, double y1, Objective ob) {
        super(x, y);
        this.exposure = ob.getIP(x, y, x1, y1);
        this.v = 0;
    }
    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }
}
