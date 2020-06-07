package pso_search;

import model.Point;
import model.Objective;
import java.util.*;

public class Gene extends Point {
	public double ip;
	public double v;
	Random r = new Random();
	
	public Gene(double x, double y, Objective ob) {
		super(x, y);
		this.ip = ob.getIP(x,y);
//		this.ip = 0;
		this.v = 0;
	}

	public Gene(double x, double y, double x1, double y1, Objective ob) {
		super(x, y);
		this.ip = ob.getIP(x, y);
		this.v = 0;
	}
//	public void setV(double v){
//		this.v = v;
//	}
	
}
