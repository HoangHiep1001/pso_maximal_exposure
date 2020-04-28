package pso_search;

import java.util.Random;

import model.Objective;
import model.Point;

public class Individual {

	protected Objective ob;
	protected Gene[] genes;
	protected double objective;
	// khoi tao mot ca the
	public Individual(Objective ob) { 
		this.ob = ob;
		this.genes = new Gene[ob.yk.length];
		Random r = new Random();
		double randMax = 1.3;
		ob.yk[0] = PSOSearch.y1;
		int kk = 0, checkk = 0;
		double a, b;
		for (int i = 1; i < ob.yk.length-1; i++) {
			double rex, rey, ress, ress1 = 0;
			do {
				ob.yk[i] = ob.yk[i - 1] + (r.nextDouble() * 2 * randMax - randMax);
				rex = PSOSearch.x2 - ob.xk[i];
				rey = PSOSearch.y2 - ob.yk[i];
				ress = Math.sqrt(rex * rex + rey * rey);
				for(int it = 1; it < i; it ++) {
					rex = ob.xk[it] - ob.xk[it - 1];
					rey = ob.yk[it] - ob.yk[it - 1];
					ress1 += Math.sqrt(rex * rex + rey * rey);
				}
				if( ress1 + ress > PSOSearch.limitS ) {
					kk = i;
					checkk = 1;
					break;
				}
				
				if(checkk == 1) break;
				
			} while (ob.yk[i] < 0 || ob.yk[i] > ob.H);
		}
		
		if(checkk == 1 ) {
			a = (PSOSearch.y2 - ob.yk[kk-1])/(PSOSearch.x2 - ob.xk[kk-1]);
			b = PSOSearch.y2 - PSOSearch.x2 * a;
			
			for(int i = kk; i < ob.yk.length - 1; i++) {
				ob.yk[i] = a*ob.xk[i] + b;
			}
		}
		ob.yk[ob.yk.length - 1] = PSOSearch.y2;
		
		int j=0;

		for (j = 0; j < ob.yk.length-1; j++) {
			this.genes[j] = new Gene(ob.xk[j], ob.yk[j], ob.xk[j + 1], ob.yk[j + 1], ob);
		}
			
		this.genes[j] = new Gene(ob.xk[j], ob.yk[j],ob);
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
		if( resS > PSOSearch.limitS ) return 0;
		double res = 0, tempp = 0, tmax = 0;
		for (int i = 0; i < size; i++) {
			tempp = this.genes[i + begin].ip;
			res += tempp;
			if( tmax < tempp ) {
				tmax = tempp;
			}
		}
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
}
