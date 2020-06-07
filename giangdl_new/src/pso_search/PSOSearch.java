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
	public static final int POPNUM = 5000;

	// So luong the he
	public static final int PSOINTER = 200;
	
	// can thay doi 3 he so nay ( he so quan tinh w - he so van toc theo maxgene, he so van toc theo doi tuong lon nhat)
	
	public static final double C = 0.3;
	public static final double C1 = 0.5;
	public static final double C2 = 2;

	public static final int SOCATHE = POPNUM;
	// Quan the
	public Individual[] population;

	// cac rang buoc (moi them - dlg)
	
	public static double speed = 5, limitTime = 100, limitS = speed * limitTime;
	public static int N;
	public static double x1 = 0, x2 = 0, y1 = 0, y2 = 0;
	
	public Random rand = new Random();
	public Objective ob;
	public Point[] ketqua;
	public static double maxEP;
	public static String filename;
	public static long timeC = 0;
	
//	public PSOSearch() {
//		this.Objective.sensors = new ArrayList<Sensor>();
//	}
	
	// khoi tao sensor
	public void init() {		
//		speed = 5.0;
//		limitTime = 100.0;
		Objective.sensors = new ArrayList<Sensor>();
		this.ob = new Objective(Objective.sensors, ob.W, ob.H);
		
//		String filename = "/home/giang/Documents/MEP_PSO/data/200.txt";
		System.out.println(filename);
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
		
//		System.out.println("H= "+ob.H);
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
		System.out.println("so sensor = " + Objective.sensors.size());
		
	}
	// Khoi tao quan the
	public Individual[] InitSolution() {
		Individual[] ps = new Individual[POPNUM];
		int i = 0;
		int k=0;
		for (k = 0; k < POPNUM; k++) { 
			ps[i++] = new Individual(ob);
//			System.out.println("Mep = " + ps[k].getObjective());
		}
		
		System.out.println("XONG KHOI TAO QUAN THE");
		return ps;
	}
	
	public void searchPSO(int iter) throws IOException {
		long start = System.currentTimeMillis();
		init();
		System.out.println("Khoi tao quan the ");
		population = InitSolution(); 

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
		
//		for (int i = 0; i < SOCATHE; i++) {
////			Pbest[0].point = population[i].Points();
//			System.out.print("("+Pbest[0].point[i].x+", "+Pbest[0].point[i].y+"), ");
//
//		}
		
		// khoi tao file in ket qua trung gian
		PrintWriter writer1 = null;
		
		writer1 = new PrintWriter(new File("/home/giang/Documents/MEP_PSO/output/output_temp_200.txt"));
		int temppp = PSOINTER + 1;
		writer1.write(temppp + "\n");
		writer1.write(SOCATHE + "\n");
		writer1.write(Pbest[0].point.length + "\n");
		
		for (int i = 0; i < SOCATHE; i++) {
			for(int j = 0; j < Pbest[i].point.length; j++) {
//				System.out.print("("+Pbest[i].point[j].x+", "+Pbest[i].point[j].y+"), ");
				writer1.write(Pbest[i].point[j].x + " " + Pbest[i].point[j].y + "\n");
			}	
//			System.out.println();
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
			System.out.println("Thế hệ thứ " + ++theHe + ": ");
			System.out.println("Gbest Mep = " + Gbest.Objective);
			for (int k = 0; k < SOCATHE; k++) {

				double r1 = rand.nextDouble();
				double r2 = rand.nextDouble();

				for (int j = 0; j < population[0].getSize(); j++) {
//					if(j != 0 && j != population[0].getSize() - 1 ) {
//						population[k].getGene(j).v = rand.nextDouble();
//					}
					double x_ti = population[k].getGene(j).x;

					double v_t1i = C * population[k].getGene(j).v + C1 * r1 * (Pbest[k].point[j].x - x_ti) + C2 * r2 * (Gbest.point[j].x - x_ti);
					double x_t1i = x_ti + v_t1i;
					
					if(x_t1i >0 && x_t1i<ob.W) {
						population[k].setGene(j, new Gene(x_t1i, population[k].getGene(j).y, ob));
					}
						
					population[k].genes[j].v = v_t1i;
					
//					if(j == 0) v_t1i = 0;
//					if(j == population[0].getSize() - 1) v_t1i = 0;
					
					vanToc.add(v_t1i);
//					System.out.println("van toc la " + v_t1i);
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
			System.out.println("Ca the best la " + Pbest[xacDinhCaTheGbest].Objective);
			Gbest = Pbest[xacDinhCaTheGbest];
			
			// in ket qua temp sau moi the he
			for (int i = 0; i < SOCATHE; i++) {
				for(int j = 0; j < Pbest[i].point.length; j++) {
//					System.out.print("("+ketqua[i].x+", "+ketqua[i].y+"), ");
					writer1.write(Pbest[i].point[j].x + " " + Pbest[i].point[j].y + "\n");
				}	
			}
		} while (theHe < PSOINTER && Gbest.Objective > 0);
		
		writer1.flush();
		writer1.close();
//		System.out.println("\n");

		this.ketqua = Gbest.point;
		this.maxEP = Gbest.Objective;
		
		System.out.println("MaximalExposure (PSOSearch) = " + maxEP);
		timeC = System.currentTimeMillis() - start;
	}
	
	public Point[] RunAlgo() throws IOException{
		searchPSO(PSOINTER);
		return ketqua;
	}
	
//	public void PrintTemp() throws IOException {
//		PrintWriter writer1 = null;
//		
//		writer1 = new PrintWriter(new File("C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\output\\output_temp.txt"));
//		writer1.write(ketqua.length + "\n");
//		
//		for(int i = 0; i < ketqua.length; i++) {
//			System.out.print("("+ketqua[i].x+", "+ketqua[i].y+"), ");
//			writer1.write(ketqua[i].x + " " + ketqua[i].y);
//			if( i != ketqua.length - 1 ) {
//				writer1.write("\n");
//			}
//		}	
//		
//		writer1.flush();
//		writer1.close();
//	}
	
	public void PrintFile() throws IOException {
		Random R = new Random();
		PrintWriter writer1 = null;
		// double c = 1.2;
		writer1 = new PrintWriter(new File("/home/giang/Documents/MEP_PSO/output/output_200.txt"));
//		writer1.write(x1 + " " + y1 + "\n");
		writer1.write(ketqua.length + "\n");
		for(int i = 0; i < ketqua.length; i++) {
			System.out.print("("+ketqua[i].x+", "+ketqua[i].y+"), ");
			writer1.write(ketqua[i].x + " " + ketqua[i].y);
			if( i != ketqua.length - 1 ) {
				writer1.write("\n");
			}
		}	
		
		writer1.flush();
		writer1.close();
	}
	public static void main(String[] args) throws IOException  {
		PSOSearch app = new PSOSearch();
////		app.init();
		PrintWriter writer2 = null;
		String filekq = "/home/giang/Documents/MEP_PSO/ketqua.txt";
		writer2 = new PrintWriter(new File(filekq));
		writer2.write("filename                  result            time " + "\n");
		String[] datas = { "10", "20", "50", "100", "200" };
		
		for(int i = 0; i < datas.length; i++) {
			for(int j = 1; j <= 10; j++) {
				filename = "/home/giang/Documents/MEP_PSO/data/data_" + datas[i] + "_" + String.valueOf(j) + ".txt";
				
				app.RunAlgo();
//				app.PrintFile();
				writer2.write("data_" + datas[i] + "_" + String.valueOf(j) + ".txt            " + maxEP + "          " + timeC + "\n");
				System.out.println("\n-----------------------------");
//				writer2.flush();
			}
		}
		
		writer2.flush();
		writer2.close();
//		
//		
//		PSOSearch app = new PSOSearch();
//		filename = "/home/giang/Documents/MEP_PSO/data/data_200_6.txt"; //+ datas[i] + "_" + String.valueOf(j) + ".txt";
//		app.RunAlgo();
//		app.PrintFile();
		
	}
}
