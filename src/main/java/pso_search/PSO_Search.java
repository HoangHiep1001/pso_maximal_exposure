package main.java.pso_search;
import main.java.model.Objective;
import main.java.model.Pbest;
import main.java.model.Point;
import main.java.model.Sensor;

import java.util.*;
import java.io.*;

public class PSO_Search{
    public static Point startPoint;
    public static Point endPoint;
    public Objective ob;
    public static final int NUMPO = 200;
    public static final int PSOINTER = 200;
    public static final double W = 0.3;
    public static final double C1 = 2;
    public static final double C2 = 1.5;
    public static final int SOCATHE = 200;
    public Individual[] population;
    public static double vmax = 10, limTime = 100, limdeltaS = vmax * limTime;
    private Random rand = new Random();
    private Point[] ketqua;

    public void readData() {
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        this.ob = new Objective(sensors);
        String filename = "./src/main/java/pso_search/test.txt";
        List<String> lines = new ArrayList<String>();

        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line = null;
            while ((line = input.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] firstLine = lines.get(0).split("\\s+");
        int n  = Integer.parseInt(firstLine[0]);
        for (int i = 0; i < n; i++) {
            String line = lines.get(i+1);
            String[] parts = line.split("\\s+");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double r = Double.parseDouble(parts[2]);
            Sensor sensor1 = new Sensor(x,y,r);
            sensors.add(sensor1);
        }
        String[] p1 = lines.get(n+1).split("\\s+");
        this.startPoint= new Point(Double.parseDouble(p1[0]),Double.parseDouble(p1[1]));
        String[] p2 = lines.get(n+2).split("\\s+");
        this.endPoint = new Point(Double.parseDouble(p2[0]),Double.parseDouble(p2[1]));
    }
    // Khoi tao quan the
    public Individual[] InitSolution() {
        Individual[] ps = new Individual[NUMPO];
        int i = 0;
        int k = 0;
        for (k = 0; k < NUMPO; k++) {
            ps[i++] = new Individual(ob, ob.initNormal(ob.H * rand.nextDouble(), 0, ob.H));
            System.out.printf("Ca the thu %d duoc khoi tao \n ", i);
			System.out.println("Mep = " + ps[k].getObjective());
        }
        return ps;
    }
    // pso_search
    private void searchPSO(int iter) {
        System.out.printf("============Bat dau khoi tao quan the===========");
        population = InitSolution();
        System.out.println("===========khoi tao quan the xong!!!===========");
        Pbest[] Pbest = new Pbest[SOCATHE];
        for (int i = 0; i < SOCATHE; i++) {
            Pbest[i] = new Pbest();
        }
        Pbest Gbest = new Pbest();
        // Khoi tao Pbest
        for (int i = 0; i < SOCATHE; i++) {
            Pbest[i].point = population[i].Points();
            Pbest[i].Objective = population[i].getObjective();
        }
        // Tim Gbest
        // Tim Gbest trong tat ca cac Pbest
        int getBestInd = 0;
        for (int a = 1; a < SOCATHE; a++) {
            if (Pbest[getBestInd].Objective < Pbest[a].Objective) {
                getBestInd = a;
            }
        }
        Gbest = Pbest[getBestInd];
        int theHe = 0;
        ArrayList<Double> vanToc = new ArrayList<>();
        do {
            System.out.println("============= Thế hệ thứ " + ++theHe + " ===============");
            System.out.println("Gbest Mep = " + Gbest.Objective);
            for (int k = 0; k < SOCATHE; k++) {
                double r1 = rand.nextDouble();
                double r2 = rand.nextDouble();
                for (int j = 0; j < population[0].getSize(); j++) {
                    double y_ti = population[k].getGene(j).y;
                    double v_t1i = W * population[k].getGene(j).v + C1 * r1 * (Pbest[k].point[j].y - y_ti)
                            + C2 * r2 * (Gbest.point[j].y - y_ti);
                    double y_t1i = y_ti + v_t1i;
                    if(y_t1i >0 && y_t1i<ob.H)
                        population[k].setGene(j, new Gene(population[k].getGene(j).x, y_t1i,ob));
                    population[k].genes[j].v = v_t1i;
                    vanToc.add(v_t1i);
                }
                for (int j = 0; j < population[0].getSize(); j++) {
                    population[k].genes[j].v = vanToc.get(j);
                }
                vanToc.clear();
                if (Pbest[k].Objective > population[k].tinhHamMucTieuChoCaThe()) { //xac dinh Pbest
                    Pbest[k].point = population[k].Points();
                    Pbest[k].Objective = population[k].tinhHamMucTieuChoCaThe();
                }
            }
            getBestInd = 0;
            for (int a = 1; a < SOCATHE; a++) {
                if (Pbest[getBestInd].Objective < Pbest[a].Objective && a!=NUMPO-1 && a!= NUMPO-2) {
                    getBestInd = a;
                }
            }
            System.out.println("The he thu "+ theHe + " ca the best la " + Pbest[getBestInd].Objective);
            Gbest = Pbest[getBestInd];
            for(int i=0;i<vanToc.size();i++){
                System.out.println(vanToc.get(i));
            }
        } while (theHe < iter && Gbest.Objective > 0);
        System.out.println("================ DONE ==================");
        this.ketqua = Gbest.point;
        // this.ketqua = population[xacDinhPbestToiNhat].Points();
        double maxEP = Gbest.Objective;
        System.out.println("MaximalExposure = " + maxEP);
    }
    public Point[] RunAlgo() {
        readData();
        searchPSO(PSOINTER);
        return ketqua;
    }
    public static void main(String[] args) {
        PSO_Search app = new PSO_Search();
        app.RunAlgo();
    }
}
