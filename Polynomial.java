import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class Polynomial {
	double [] coef;
	int [] exponent;
	
	public Polynomial() {
		coef = null;
		exponent = null;
	}
	
	public Polynomial(File file) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = input.readLine();
		String [] temp = line.split("[+]");
		int countSub = 0;
		int counter = 0;
		int start = 0;
		boolean foundX = false;
		
		for(int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == '-') {
				countSub++;
			}
		}
		
		String [] strArr = new String [temp.length + countSub];
		
		for(int end = 0; end < line.length(); end++) {
			if(line.charAt(end) == '-') {
				strArr[counter] = line.substring(start, end);
				counter++;
				start = end;
			}
			else if(line.charAt(end) == '+') {
				strArr[counter] = line.substring(start, end);
				counter++;
				start = end+1;
			}
			else if(end == line.length() - 1) {
				strArr[counter] = line.substring(start);
			}
		}

		double [] newCoef = new double [strArr.length];
		int [] newExpo = new int [strArr.length];
		for(int i = 0; i < strArr.length; i++) {
			for(int j = 0; j < strArr[i].length(); j++) {
				if(strArr[i].charAt(j) == 'x' && j == strArr[i].length() - 1) {
					newCoef[i] = Double.parseDouble(strArr[i].substring(0, j));
					newExpo[i] = 1;
					foundX = true;
				} else if(strArr[i].charAt(j) == 'x') {
					newCoef[i] = Double.parseDouble(strArr[i].substring(0, j));
					newExpo[i] = Integer.parseInt(strArr[i].substring(j+1));
					foundX = true;
				}
				if(j == strArr[i].length() - 1 && foundX == false) {
					newCoef[i] = Double.parseDouble(strArr[i]);
				}
			}
		}
		coef = newCoef;
		exponent = newExpo;
		input.close();
	}
	
	public Polynomial(double [] p, int [] c) {
		coef = p;
		exponent = c;
	}
	
	public int findMax() {
		int result = 0;
		for(int i = 0; i < exponent.length; i++) {
			if(exponent[i] > result) {
				result = exponent[i];
			}
		}
		return result;
	}
	
	public Polynomial add(Polynomial p) {
		int maxLength = Math.max(p.findMax(), this.findMax()) + 1;
		double [] newCoef = new double [maxLength];
		int [] newExponent = new int [maxLength];
		for(int i = 0; i < maxLength; i++) {
			newExponent[i] = i;
		}
		for(int i = 0; i < p.exponent.length; i++) {
			newCoef[p.exponent[i]] += p.coef[i];
		}
		for(int i = 0; i < this.exponent.length; i++) {
			newCoef[this.exponent[i]] += this.coef[i];
 		}
		Polynomial newPoly = new Polynomial(newCoef, newExponent);
		Polynomial result = trimPoly(newPoly);
		return result;
	}
	
	public Polynomial trimPoly(Polynomial p) {
		int numZeros = 0;
		for(int i = 0; i < p.exponent.length; i++) {
			if(p.coef[i] == 0) {
				numZeros++;
			}
		}
		double [] newCoef = new double [p.exponent.length - numZeros];
		int [] newExponent = new int [p.exponent.length - numZeros];
		int counter = 0;
		for(int i = 0; i < p.exponent.length; i++) {
			if(p.coef[i] != 0) {
				newCoef[counter] = p.coef[i];
				newExponent[counter] = p.exponent[i];
				counter++;
			}
		}
		Polynomial result = new Polynomial(newCoef, newExponent);
		return result;
	}
	
	public Polynomial multiply(Polynomial p) {
		double [] newCoef = new double [p.findMax() + this.findMax()];
		int [] newExponent = new int [p.findMax() + this.findMax()];
		for(int i = 0; i < newExponent.length; i++) {
			newExponent[i] = i;
		}
		Polynomial p0 = new Polynomial(newCoef, newExponent);
		double [] coefTemp = new double [p.exponent.length];
		int [] exponentTemp = new int [p.exponent.length];
		for(int i = 0; i < this.exponent.length; i++) {
			for(int j = 0; j < p.exponent.length; j++) {
				coefTemp[j] = this.coef[i] * p.coef[j];
				exponentTemp[j] = this.exponent[i] + p.exponent[j];
			}
			Polynomial p1 = new Polynomial(coefTemp, exponentTemp);
			p0 = p0.add(p1);
		}
		return p0;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for(int i = 0; i < this.exponent.length; i++) {
			result += this.coef[i] * Math.pow(x, this.exponent[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return(evaluate(x) == 0);
	}
	
	public void saveToFile(String filename) throws IOException {
		FileWriter output = new FileWriter(filename);
		String result = "";
		for(int i = 0; i < this.exponent.length; i++) {
			if(this.exponent[i] == 0) {
				result += this.coef[i];
			} 
			else if(this.coef[i] < 0) {
				result += this.coef[i] + "x" + this.exponent[i];
			}
			else {
				result += "+" + this.coef[i] + "x" + this.exponent[i];
			}
		}
		output.write(result);
		output.close();
	}
}
