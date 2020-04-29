//package algorithm;
//
//import java.awt.*;
//import java.awt.image.*;
//import java.util.*;
//import java.io.*;
//import javax.imageio.*;
//import javax.imageio.*;
//
//import data.*;
//import model.*;
//import model.Obstace;
//import model.Point;
//import model.Sensor;
//import pso_search.*;
//
//public abstract class Algothirm {
//	public Random rand = new Random();
//	public Objective ob;
//	public Point[] ketqua;
//	public double maxEP;
//	public long timeEslapse;
//	public BufferedImage bitmap;
//	static final int scale = 1;
//
////	@SuppressWarnings("resource")
////	public void readData(String filename) throws Exception {
////		ArrayList<Obstace> lobs = new ArrayList<Obstace>();
////		ArrayList<Sensor> list = new ArrayList<Sensor>();
////		BufferedReader input = new BufferedReader(new FileReader(Data.getDataPath(filename)));
////		String s = input.readLine();
////		String[] s1 = s.split(" ");
////		int nob = Integer.parseInt(s1[0]); // So vat can trong khu vá»±c
////		int nse = Integer.parseInt(s1[1]); // Sá»‘ lÆ°á»£ng sensor
////		Sensor.r = Double.parseDouble(s1[2]); // bÃ¡n kÃ­nh
//////		Sensor.r = 10.0; // bÃ¡n kÃ­nhs
////
////		Sensor.angle = Double.parseDouble(s1[3]); // GÃ³c
////		int W = Integer.parseInt(s1[4]);// chiá»�u dÃ i
////		int H = Integer.parseInt(s1[5]);// chiá»�u rá»™ng
////		// thÃªm váº­t cáº£n vÃ o khu vá»±c
////		for (int i = 0; i < nob; i++) {
////			String temp = input.readLine();
////			String[] t = temp.split(" ");
////			lobs.add(new Obstace(Double.parseDouble(t[0]), Double.parseDouble(t[1]), Double.parseDouble(t[2]),
////					Double.parseDouble(t[3])));
////		}
////		// Sinh ra cÃ¡c sensor
////		for (int i = 0; i < nse; i++) {
////			String temp = input.readLine();
////			String[] t = temp.split(" ");
////			Point p = new Point(Double.parseDouble(t[0]), Double.parseDouble(t[1]));
////			double angle = Double.parseDouble(t[2]);
////			list.add(new Sensor(p, angle));
////		}
////		this.ob = new Objective(list, lobs, W, H);
////		this.bitmap = new BufferedImage(W*scale, H*scale + 20, BufferedImage.TYPE_INT_ARGB);// ??????
////																				// Cá»˜ng
////																				// 20
////																				// lÃ m
////																				// gÃ¬?
////	}
////
////	@SuppressWarnings("resource")
////	public void readOutput(String filename) throws Exception {
////		if (this.ob == null)
////			return;
////		BufferedReader input = new BufferedReader(new FileReader(Data.getResultPath(filename)));
////		String[] line = input.readLine().split(" ");
////		int lent = Integer.parseInt(line[0]); // lent = phan tu thu 1
////		minExp = Double.parseDouble(line[1]); // minExp = phan tu thu 2
////		timeEslapse = Long.parseLong(line[2]); // timeEslapse = phan tu thu 3
////		ketqua = new Point[lent];
////		for (int i = 0; i < lent; i++) {
////			line = input.readLine().split(" ");
////			ketqua[i] = new Point(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
////		}
////		draw(null);
////	}
////
////	public abstract Point[] RunAlgo();
////
////	public void output(String filename) {
////		if (ketqua == null)
////			RunAlgo();
////		FileOutputStream fos = null;
////		PrintStream pw = null;
////		try {
////			fos = new FileOutputStream(Data.getResultPath(filename));
////			pw = new PrintStream(fos);
////			pw.println(ob.H + " " + ob.W + " " +ketqua.length + " " + minExp + " " + timeEslapse);
////			for (int i = 0; i < ketqua.length; i++)
////				pw.println(ketqua[i].x + " " + ketqua[i].y);
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			try {
////				pw.close();
////				fos.close();
////			} catch (Exception e) {
////			}
////		}
////		draw(Data.getResultPath(filename + ".png"));
////	}
////
////	public void draw(String filename) {
////		Graphics g = bitmap.getGraphics();
////		g.setColor(Color.WHITE);
////		g.fillRect(0, 0, ob.W*scale, ob.H*scale + 20);
////		 g.setColor(Color.BLACK);
////		 for (int i = 0; i < this.ob.obstaces.size(); i++) {
////		 Obstace ob = this.ob.obstaces.get(i);
////		 // Dao nguoc toa do. Toa do ve = p(x, h-y)
////		 g.fillRect((int)ob.x*scale, (int)ob.y*scale, (int)ob.width*scale, (int)ob.height*scale);
////		 }
////		g.setColor(Color.BLUE);
////		for (int i = 0; i < ob.sensors.size(); i++) {
////			Sensor s = ob.sensors.get(i);
////			// Dao nguoc toa do. Toa do ve = p(x, h-y)
////			g.fillArc((int) (s.p.x - Sensor.r)*scale, (int) (s.p.y - Sensor.r)*scale, (int) (2 * Sensor.r)*scale, (int) (2 * Sensor.r)*scale,
////					(int) ((-s.Viangle - Sensor.angle) * 180 / Math.PI), (int) (Sensor.angle * 360 / Math.PI));
////		}
////		g.setColor(Color.RED);
////		Point[] var = ketqua;
////		for (int j = 0; j < var.length - 1; j++) {
////			Point p1 = var[j];
////			Point p2 = var[j + 1];
////			g.drawLine((int) p1.x*scale, (int) p1.y*scale, (int) p2.x*scale, (int) p2.y*scale);
////		}
////		
////		g.setColor(Color.BLACK);
////		g.drawString("MinE: " + minExp, 0, ob.H*scale + 5);
////		if (filename != null) {
////			try {
////				ImageIO.write(bitmap, "png", new FileOutputStream(filename));
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
////	}
////	public void draw(String filename, Individual[] population) {
////		Graphics g = bitmap.getGraphics();
////		g.setColor(Color.WHITE);
////		g.fillRect(0, 0, ob.W*scale, ob.H*scale + 20);
////		 g.setColor(Color.BLACK);
////		 for (int i = 0; i < this.ob.obstaces.size(); i++) {
////		 Obstace ob = this.ob.obstaces.get(i);
////		 // Dao nguoc toa do. Toa do ve = p(x, h-y)
////		 g.fillRect((int)ob.x*scale, (int)ob.y*scale, (int)ob.width*scale, (int)ob.height*scale);
////		 }
////		g.setColor(Color.BLUE);
////		for (int i = 0; i < ob.sensors.size(); i++) {
////			Sensor s = ob.sensors.get(i);
////			// Dao nguoc toa do. Toa do ve = p(x, h-y)
////			g.fillArc((int) (s.p.x - Sensor.r)*scale, (int) (s.p.y - Sensor.r)*scale, (int) (2 * Sensor.r)*scale, (int) (2 * Sensor.r)*scale,
////					(int) ((-s.Viangle - Sensor.angle) * 180 / Math.PI), (int) (Sensor.angle * 360 / Math.PI));
////		}
////		g.setColor(Color.RED);
////		for (int m=0;m<population.length;m++){
////			ketqua = population[m].Points();
////			Point[] var = ketqua;
////			System.out.println("Dang ghi ca the "+ m);
////			for (int j = 0; j < var.length - 1; j++) {
////				Point p1 = var[j];
////				Point p2 = var[j + 1];
////				g.drawLine((int) p1.x*scale, (int) p1.y*scale, (int) p2.x*scale, (int) p2.y*scale);
////			}
////			
////			g.setColor(Color.BLACK);
////			g.drawString("MinE: " + minExp, 0, ob.H*scale + 5);
////			if (filename != null) {
////				try {
////					ImageIO.write(bitmap, "png", new FileOutputStream(filename));
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////			}
////		}
////	}
//}