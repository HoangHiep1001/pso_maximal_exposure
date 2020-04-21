package pso_search;

import model.Objective;
import model.PbestClass;
import model.Point;
import model.Sensor;
import model.*;
import data.Data;
import pso_search.Individual;

import java.util.*;
import java.io.*;

//import M_ExposurePath.Sensor;
//import algorithm.Algothirm;

public class PSOSearch {
	// So ca the cua quan the
	public static final int POPNUM = 200;
	public static final int SOLANCHAY = 20;

	// So luong the he
	public static final int PSOINTER = 200;

	// he so quan tinh w
	public static final double W = 0.3;
	// he so van toc theo maxGene
	public static final double C1 = 0.5;
	// he so van toc theo doi tuong lon nhat
	public static final double C2 = 0.5;

	public static final int SOTOADO = 5001;

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
//		ArrayList<Obstace> lobs = new ArrayList<Obstace>();
//		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		String filename = "C:\\Users\\20161\\Desktop\\BTL_TTTH\\src\\M_ExposurePath\\test.txt";
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
			
			System.out.println("Sensor "+ (i+1) + ": " + sensor1.p.x + " " + sensor1.p.y + " " + sensor1.r );
			Objective.sensors.add(sensor1);
			
			System.out.println("Sensor "+ (i+1) + ": " + sensor1.p.x + " " + sensor1.p.y + " " + sensor1.r );
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
			double[] resy = ob.initNormal(ob.H * rand.nextDouble(), 0, ob.H);
			ps[i++] = new Individual(ob, ob.initNormal(ob.H * rand.nextDouble(), 0, ob.H));

			System.out.printf("Ca the thu %d duoc khoi tao \n ", i); // **//
//			System.out.println("Mep = " + ps[k].getObjective());
		}

		return ps;
	}
	
	
	private void searchPSO(int iter) {
		long start = System.currentTimeMillis();
		init();
		System.out.println("Start init tttttmmmmmmmmm");
		System.out.println("Bước 1:  khởi tạo quần thể: ");
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
		// Tim Gbest trong tat ca cac Pbest
		int xacDinhCaTheGbest = 0;
		for (int a = 1; a < SOCATHE; a++) {
			if (Pbest[xacDinhCaTheGbest].Objective > Pbest[a].Objective) {
				xacDinhCaTheGbest = a;
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
		// this.ketqua = population[xacDinhPbestToiNhat].Points();
		this.maxEP = Gbest.Objective;
		//this.timeEslapse = System.currentTimeMillis() - start;
		System.out.println("MaximalExposure (PSOSearch) = " + maxEP);
		//System.out.println("Time: " + timeEslapse);
	}

	// ***//
	//@Override
	public Point[] RunAlgo() {
		searchPSO(PSOINTER);
//		population = InitSolution(); // khoi tao quan the
//		draw(Data.getResultPath("ketqua.png"),population);	
		return ketqua;
	}

	
	public static void main(String[] args) {
//		PSOSearch g = new PSOSearch();
		PSOSearch app = new PSOSearch();
//		app.init();
		app.RunAlgo();
//		FileOutputStream fos = null;
//		PrintStream ps = null;
//		String[] datatype = {"datae","datag","datar"};
//
//		try {
//			fos = new FileOutputStream("ketquaPSO.txt");
//			ps = new PrintStream(fos);
//			ps.println("filename                result      DLC       time ");
//			//Chay bo 10
//			int[] dataNum = Data.dataNum500;
//			for (int i = 0; i < dataNum.length; i++) {
//				for (int j = 0; j < Data.dataArg.length; j++) {
//					for (int k = 0; k < 10; k++) {
//						for (int typedata = 0;typedata<3;typedata++){
//	
//							final int nRun = SOLANCHAY;
//							double[] kqO = new double[nRun];
//							double kqAv = 0;
//							double timeAv = 0;
//							double xichMa = 0;
//	//						String filename = "datae_" + dataNum[i] + "_" + (k + 1) + ".txt";
//							String filename = datatype[typedata]+"/"+ datatype[typedata]+"_" + dataNum[i] + "_" + (k + 1) + ".txt";
//							g.readData(filename);
//							for (int v = 0; v < nRun; v++) {
//								System.out.println("Du lieu " + dataNum[i] + "-" + Data.dataArg[j] + " bo " + (k + 1)
//										+ " chay " + v);
////								String outName = "data" + dataNum[i] + "_" + Data.dataArg[j] + "_" + (k + 1)
////										+ "_ketqua_" + v + "_PSO.txt";
//								String outName = datatype[typedata]+"/"+ datatype[typedata]+"_" + dataNum[i] + "_" + Data.dataArg[j] + "_" + (k + 1)
//										+ "_ketqua_" + v + "_PSO.txt";
//								if (Data.isResultExists(outName))
//									g.readOutput(outName);
//								else {
//									g.RunAlgo();
//									g.output(outName);
//								}
//								kqO[v] = g.maxEP;
//								kqAv += g.maxEP;
//								timeAv += g.timeEslapse;
//							}
//							timeAv /= nRun;
//							kqAv /= nRun;
//							for (int v = 0; v < nRun; v++)
//								xichMa += (kqO[v] - kqAv) * (kqO[v] - kqAv);
//							xichMa /= nRun;
//							ps.println(filename + "      " + kqAv + "      " + xichMa + "      " + timeAv);
//						}
//						ps.println("-------------------------------------------------");
//					}
//				}
//			}
//		} catch (
//
//		Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				ps.close();
//				fos.close();
//			} catch (Exception e) {
//			}
//		}
//		System.out.println("Done");
	}
}
