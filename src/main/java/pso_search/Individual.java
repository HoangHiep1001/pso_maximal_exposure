package main.java.pso_search;
import main.java.model.Objective;
import main.java.model.Point;

import java.util.Random;

public class Individual {

    protected Objective ob;
    protected Gene[] genes;
    protected double objective;
    private double dx =0.5;
    private double dy =0.5;
    Random random = new Random();

    public Individual(Objective ob, double[] y) {
        this.ob = ob;
        this.genes = new Gene[y.length];
        int j = 0;
        int corX = (int) (PSO_Search.startPoint.x/dx);
        int corY = (int) (PSO_Search.startPoint.y/dy);
        int next = (int) (ob.H*random.nextDouble()/dy);
        if(checkConditionalTime(corX,corY,corX+1,next)){
            this.genes[0] = new Gene(ob.xk[corX], y[corY], ob.xk[corX+1], y[(int) (ob.H*random.nextDouble()/dy)], ob);
        }
        for (j = 0; j < y.length - 1; j++)
            this.genes[j] = new Gene(ob.xk[j], y[j], ob.xk[j + 1], y[j + 1], ob);
        this.genes[j] = new Gene(ob.xk[j], y[j], ob);
        this.objective = getObjective();
    }

    private Individual(Individual ind) {
        this.ob = ind.ob;
        this.objective = ind.objective;
        this.genes = new Gene[ind.genes.length];
        for (int j = 0; j < genes.length; j++)
            this.genes[j] = ind.genes[j];
    }

    public double getObjective() {
        return this.getObjective(0, this.genes.length);
    }

    public double getObjective(int begin, int size) {
        double value = 0;
        for (int i = 0; i < size; i++)
            value += this.genes[i + begin].exposure;
        return value;
    }
    public boolean checkConditionalTime(int x1, int y1,int x2,int y2){
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y1 - y2), 2)) < PSO_Search.limdeltaS;
    }

    public int getSize() {
        return this.genes.length;
    }

    public Point[] Points() {
        return this.genes;
    }

    public Gene getGene(int index) {
        return this.genes[index];
    }
    public double objective() {
        return this.objective;
    }
    public void setGene(int index, Gene value) {
        if (index > 0)
            this.genes[index - 1] = new Gene(this.genes[index - 1].x, this.genes[index - 1].y, value.x, value.y, ob);
        if (index < this.genes.length - 1)
            this.genes[index] = new Gene(value.x, value.y, this.genes[index + 1].x, this.genes[index + 1].y, ob);
        else
            this.genes[index] = value;
    }
    public double tinhHamMucTieuChoCaThe() {
        double value = 0;
        for (int i = 0; i < genes.length; i++)
            value += this.genes[i].exposure;
        return value;
    }
}