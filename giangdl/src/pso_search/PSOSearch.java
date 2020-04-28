package pso_search;

import model.Objective;
import model.PbestClass;
import model.Point;
import model.Sensor;
import model.*;
import pso_search.Individual;

import java.util.*;
import java.io.*;
import java.lang.*;

public class PSOSearch {
    // So ca the cua quan the
    public static final int POPNUM = 200;
    // So luong the he
    public static final int PSOINTER = 100;
    // ( can thay doi 3 he so nay )
    // he so quan tinh w
    public static final double W = 0.3;
    // he so van toc theo maxGene
    public static final double C1 = 0.5;
    // he so van toc theo doi tuong lon nhat
    public static final double C2 = 2;

    public static final int SOCATHE = POPNUM;
    // Quan the
    public Individual[] population;

    // cac rang buoc (moi them - dlg)
    public static double speed = 20, limitTime = 100, limitS = speed * limitTime;
    public static int N;
    public static double x1 = 0, x2 = 0, y1 = 0, y2 = 0;

    public Random rand = new Random();
    public Objective ob;
    public Point[] ketqua;
    public double maxEP;


    // khoi tao sensor
    public void init() {
        this.ob = new Objective(Objective.sensors, ob.W, ob.H);
        String filename = "E:\\Documents\\20192\\EvoComputation\\pso_maximal_exposure\\giangdl\\data\\test.txt";
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

        System.out.println("H= " + ob.H);
        String[] firstLine = lines.get(0).split("\\s+");
        N = Integer.parseInt(firstLine[0]);
        for (int i = 0; i < N; i++) {
            String line = lines.get(i + 1);
            String[] parts = line.split("\\s+");
            Sensor sensor1 = new Sensor(
                    Double.parseDouble(parts[0]),
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]));
            Objective.sensors.add(sensor1);
        }

        String line1 = lines.get(N + 1);
        String[] parts1 = line1.split("\\s+");
        x1 = Double.parseDouble(parts1[0]);
        y1 = Double.parseDouble(parts1[1]);
        String line2 = lines.get(N + 2);
        String[] parts2 = line2.split("\\s+");
        x2 = Double.parseDouble(parts2[0]);
        y2 = Double.parseDouble(parts2[1]);

        System.out.println("x1 = " + x1 + ", y1 = " + y1);
        System.out.println("x2 = " + x2 + ", y2 = " + y2);

    }

    // Khoi tao quan the
    private Individual[] InitSolution() {
        Individual[] ps = new Individual[POPNUM];
        int i = 0;
        int k = 0;
        for (k = 0; k < POPNUM; k++) {
            ps[i++] = new Individual(ob);
            System.out.printf("Ca the thu %d duoc khoi tao \n ", i); // **//
        }

        return ps;
    }

    private void searchPSO(int iter) {
//		long start = System.currentTimeMillis();
        init();
        System.out.println("Start init tttttmmmmmmmmm");
        System.out.println("Buoc 1: Khoi tao quan the ");
        population = InitSolution(); // khoi tao quan the

        PbestClass[] Pbest = new PbestClass[SOCATHE];

        for (int i = 0; i < SOCATHE; i++) {
            Pbest[i] = new PbestClass();
        }
        PbestClass Gbest = new PbestClass();
        // Khoi tao Pbest
        for (int i = 0; i < SOCATHE; i++) {
            Pbest[i].point = population[i].Points();
            Pbest[i].Objective = population[i].getObjective();
        }
        // Tim Gbest
        int xacDinhCaTheGbest = 0;
        for (int i = 1; i < SOCATHE; i++) {
            if (Pbest[xacDinhCaTheGbest].Objective < Pbest[i].Objective) {
                xacDinhCaTheGbest = i;
            }
        }

        Gbest = Pbest[xacDinhCaTheGbest];

        int xacDinhPbestToiNhat = 0;
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
                    if (y_t1i > 0 && y_t1i < ob.H)
                        population[k].setGene(j, new Gene(population[k].getGene(j).x, y_t1i, ob));

                    population[k].genes[j].v = v_t1i;

                    if (j == 0) v_t1i = 0;
                    if (j == population[0].getSize() - 1) v_t1i = 0;

                    vanToc.add(v_t1i);
                }

                for (int j = 0; j < population[0].getSize(); j++) {
                    population[k].genes[j].v = vanToc.get(j);
                }

                vanToc.clear();

                if (Pbest[k].Objective < population[k].getObjective()) { //xac dinh Pbest
                    Pbest[k].point = population[k].Points();
                    Pbest[k].Objective = population[k].getObjective();
                }
            }
            // Xac dinh lai ca the Gbest
            xacDinhCaTheGbest = 0;
            for (int a = 1; a < SOCATHE; a++) {
                if (Pbest[xacDinhCaTheGbest].Objective < Pbest[a].Objective && a != POPNUM - 1 && a != POPNUM - 2) {
                    xacDinhCaTheGbest = a;
                }
            }
            System.out.println("The he thu " + theHe + " ca the best la " + Pbest[xacDinhCaTheGbest].Objective);
            Gbest = Pbest[xacDinhCaTheGbest];
        } while (theHe < iter && Gbest.Objective > 0);

        System.out.println("================ DONE ============== ");

        this.ketqua = Gbest.point;
        this.maxEP = Gbest.Objective;
        System.out.println("MaximalExposure (PSOSearch) = " + maxEP);

    }

    public Point[] RunAlgo() {
        searchPSO(PSOINTER);
        return ketqua;
    }

    public static void main(String[] args) throws IOException {
        PSOSearch psoSearch = new PSOSearch();
        psoSearch.RunAlgo();
        BufferedWriter bw = new BufferedWriter(new FileWriter("out1.txt"));
        for (int i = 0; i < psoSearch.ketqua.length; i++) {
            bw.write("(" + psoSearch.ketqua[i].x + "," + psoSearch.ketqua[i].y + "),");
        }
        bw.close();
    }
}
