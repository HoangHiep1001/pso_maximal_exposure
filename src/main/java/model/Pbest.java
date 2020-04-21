package main.java.model;

public class Pbest {
    public static final int NUMPOINT = 5001;
    public Point[] point ;
    public double Objective;

    public Pbest() {
        Objective = 0;
        point = new Point[NUMPOINT];
    }

    public double getObjective() {
        return Objective;
    }
}
