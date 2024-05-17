
public class Polynomial {
	//fields 
	double [] coef;
	
	//constructors
	public Polynomial() {
		coef = new double [0];
	}
	
	public Polynomial(double [] p) {
		coef = p;
	}
	
	//methods
	public int getLength() {
		return coef.length;
	}
	
	public double getCoefficient(int index) {
		return coef[index];
	}
	
	public Polynomial add(Polynomial p) {
		int maxLength = Math.max(p.getLength(), coef.length);
		double [] newArray = new double[maxLength];
		for(int i = 0; i < maxLength; i++) {
			if(i < p.getLength()) {
				newArray[i] += p.getCoefficient(i);
			} 
			if(i < coef.length) {
				newArray[i] += coef[i];
			}
		}
		Polynomial result = new Polynomial(newArray);
		return result;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for(int i = 0; i < coef.length; i++) {
			result += coef[i] * Math.pow(x, i);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return(evaluate(x) == 0);
	}
}
