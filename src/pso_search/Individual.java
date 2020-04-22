package pso_search;

import model.Objective;
import model.Point;

public class Individual {

	protected Objective ob;
	protected Gene[] genes;
	protected double objective; ///

	// khoi tao mot ca the
	public Individual(Objective ob, double[] y) { 
		this.ob = ob;
		this.genes = new Gene[y.length];
		int j=0;

		for (j = 0; j < y.length-1; j++) {
			this.genes[j] = new Gene(ob.xk[j], y[j], ob.xk[j + 1], y[j + 1], ob);
		}
			
		this.genes[j] = new Gene(ob.xk[j], y[j],ob);
		this.objective = getObjective();
	}

	public double getObjective() {
		return this.getObjective(0, this.genes.length);
	}

	public double getObjective(int begin, int size) {
		double value = 0;
		
		double resS = 0;
		double resX, resY;
		for(int i = 1; i < size; i++) {
			resX = this.genes[i + begin].x - this.genes[i + begin - 1].x;
			resY = this.genes[i + begin].y - this.genes[i + begin - 1].y;
			resS += Math.sqrt(resX * resX + resY * resY);
		}
		
		double t11 = resS/PSOSearch.speed;
		double timeM = PSOSearch.limitTime - t11;
		t11 = Objective.dx/PSOSearch.speed;
		
		if( timeM < 0 ) return 0;
//		System.out.println("timeM = "+ timeM);
		
		double res = 0, tempp = 0, tmax = 0;
		
		for (int i = 0; i < size; i++) {
			tempp = this.genes[i + begin].ip;
			res += tempp;
			if( tmax < tempp ) {
				tmax = tempp;
			}
		}
//		System.out.println(" tong cuong do cam bien tren quang duong la " + timeM);
		
		double IP = res - tmax;
		value = IP * t11 + tmax * timeM;
		return value;
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

	// giong cai tren
	public double tinhHamMucTieuChoCaThe() {
		double value = 0;
//		for (int i = 0; i < genes.length; i++)
//			value += this.genes[i].ip;
		value = this.getObjective();
		return value;
	}
}
