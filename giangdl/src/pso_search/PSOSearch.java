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
	public static final int PSOINTER = 200;
	
	// ( can thay doi 3 he so nay )
	// he so quan tinh w
	public static final double C = 0.3;
	// he so van toc theo maxGene
	public static final double C1 = 0.5;
	// he so van toc theo doi tuong lon nhat
	public static final double C2 = 0.5;

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
	
//	public PSOSearch() {
//		this.Objective.sensors = new ArrayList<Sensor>();
//	}
	
	// khoi tao sensor
	public void init() {		
//		speed = 5.0;
//		limitTime = 100.0;

		this.ob = new Objective(Objective.sensors, ob.W, ob.H);
		String filename = "C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\data\\test.txt";
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
		
		System.out.println("H= "+ob.H);
		String[] firstLine = lines.get(0).split("\\s+");
		N = Integer.parseInt(firstLine[0]); 
		double x11 = 100, y11 = 100, r11 = 0;
		for (int i = 0; i < N; i++) {
			String line = lines.get(i+1);
			String[] parts = line.split("\\s+");
			
			x11 = Double.parseDouble(parts[0]);
			y11 = Double.parseDouble(parts[1]);
			r11 = Double.parseDouble(parts[2]);
			Sensor sensor1 = new Sensor(x11, y11, r11);
			
//			System.out.println("Sensor "+ (i+1) + ": " + sensor1.p.x + " " + sensor1.p.y + " " + sensor1.r );
			Objective.sensors.add(sensor1);
			
//			System.out.println("Sensor "+ (i+1) + ": " + sensor1.p.x + " " + sensor1.p.y + " " + sensor1.r );
		}
		
		String line1 = lines.get(N+1);
		String[] parts1 = line1.split("\\s+");
		x1 = Double.parseDouble(parts1[0]);
		y1 = Double.parseDouble(parts1[1]);
		String line2 = lines.get(N+2);
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
		int k=0;
		for (k = 0; k < POPNUM; k++) { 
//			ps[i++] = new Individual(ob, ob.initNormal(ob.H * rand.nextDouble(), 0, ob.H));
			ps[i++] = new Individual(ob);
//			System.out.printf("Ca the thu %d duoc khoi tao \n ", i); 
//			System.out.println("Mep = " + ps[k].getObjective());
		}
		
		System.out.printf("XONG KHOI TAO CA THE");
		return ps;
	}
	
	private void searchPSO(int iter) {

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
//			System.out.println("Pbest[i].Objective = " + Pbest[i].Objective);
		}
		
		// Tim Gbest
		// Tim Gbest trong tat ca cac Pbest
		int xacDinhCaTheGbest = 0;
		for (int a = 1; a < SOCATHE; a++) {
//			System.out.println(" Pbest[a].Objective = " + Pbest[a].Objective);
			if (Pbest[xacDinhCaTheGbest].Objective < Pbest[a].Objective) {
				xacDinhCaTheGbest = a;
			}
		}
		
		Gbest = Pbest[xacDinhCaTheGbest];
	
//		int xacDinhPbestToiNhat = 0;
		int theHe = 0;
		ArrayList<Double> vanToc = new ArrayList<>();
		do {
			System.out.println("============= Thế hệ thứ " + ++theHe + " ===============");
			System.out.println("Gbest Mep = " + Gbest.Objective);
			for (int k = 0; k < SOCATHE; k++) {

				double r1 = rand.nextDouble();
				double r2 = rand.nextDouble();

				for (int j = 0; j < population[0].getSize(); j++) {
					
					double x_ti = population[k].getGene(j).y;

					double v_t1i = C * population[k].getGene(j).v + C1 * r1 * (Pbest[k].point[j].x - x_ti) + C2 * r2 * (Gbest.point[j].x - x_ti);
					double x_t1i = x_ti + v_t1i;
					
					if(x_t1i >0 && x_t1i<ob.W) {
						population[k].setGene(j, new Gene(x_t1i, population[k].getGene(j).y, ob));
					}
						
					population[k].genes[j].v = v_t1i;
					
					if(j == 0) v_t1i = 0;
					if(j == population[0].getSize() - 1) v_t1i = 0;
					
					vanToc.add(v_t1i);
//					if(j == 0) {
//						System.out.println("van toc la " + v_t1i);
//					}
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
				if (Pbest[xacDinhCaTheGbest].Objective < Pbest[a].Objective && a!=POPNUM-1 && a!= POPNUM-2) {
					xacDinhCaTheGbest = a;
				}
			}
			System.out.println("The he thu "+ theHe + " ca the best la " + Pbest[xacDinhCaTheGbest].Objective);
			Gbest = Pbest[xacDinhCaTheGbest];
		} while (theHe < PSOINTER && Gbest.Objective > 0);

		System.out.println("xxxxxxxxxxxxx XONG GIAI THUAT PSO xxxxxxxxxxxxxxxxxx");

		this.ketqua = Gbest.point;
		this.maxEP = Gbest.Objective;
		System.out.println("MaximalExposure (PSOSearch) = " + maxEP);
		
	}
	
	public Point[] RunAlgo() {
		searchPSO(PSOINTER);
		return ketqua;
	}

//	public void InitRandom() throws IOException {
//		final Random R = new Random();
//		PrintWriter writer1 = null;
//		// double c = 1.2;
//		writer1 = new PrintWriter(new File("C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\output\\output.txt"));
//		for(int i = 0; i < ketqua.length; i++) {
//			
//			
//			
//		}	
//		writer1.write(N + "\n");
//		for (int i = 0; i < N; i++) {
//			double x11 = 200, y11 = 100, r11 = Rmax;
//
//			while (x11 >= wOfField && y11 >= hOfField) {
//				x11 = R.nextDouble() + R.nextInt((int) wOfField);
//				y11 = R.nextDouble() + R.nextInt((int) hOfField);
//				r11 = R.nextDouble() + R.nextInt((int) Rmax);
//			}
//
//			Sensor sensor1 = new Sensor(x11, y11, r11);
//			writer1.write(x11 + " " + y11 + " " + r11 + "\n");
//			
//		}
//
//		double temp1 = wOfField, temp2 = wOfField;
//
//		while (temp1 >= wOfField && temp2 >= wOfField) {
//			temp1 = R.nextDouble() + R.nextInt((int) wOfField);
//			temp2 = R.nextDouble() + R.nextInt((int) wOfField);
//
//		}
//
//		double a_temp = 0, b_temp = a_temp + deltaS;
//		int check1 = 1, check2 = 1;
//		for (int i = 0; i < wOfField; i++) {
//			if (temp1 >= a_temp && temp1 <= b_temp) {
//				x1 = (Math.abs(temp1 - a_temp) >= Math.abs(temp1 - b_temp)) ? b_temp : a_temp;
//				check1 = 0;
//			}
//			if (temp2 >= a_temp && temp2 <= b_temp) {
//				x2 = (Math.abs(temp2 - a_temp) >= Math.abs(temp2 - b_temp)) ? b_temp : a_temp;
//				check2 = 0;
//			}
//			a_temp = b_temp;
//			b_temp = b_temp + deltaS;
//			if (check1 == 0 && check2 == 0)
//				break;
//		}
//
//		double x8 = 0;
//		writer1.write(x1 + " " + x8 + "\n");
//		writer1.write(x2 + " " + hOfField);
//
//		writer1.flush();
//		writer1.close();
//
//	}
//	}
	public static void main(String[] args) {
		PSOSearch app = new PSOSearch();
//		app.init();
		app.RunAlgo();
	}
}
