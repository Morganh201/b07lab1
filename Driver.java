import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) throws IOException {
		double [] p = {6, 2, 5};
		int [] c = {0, 1, 3};
		Polynomial poly1 = new Polynomial(p, c);
		double[] p1 = {7, 1, 3};
		int [] c1 = {0, 2, 5};
		Polynomial poly2 = new Polynomial(p1, c1);
		Polynomial sum = poly1.add(poly2);
		
		for(int i = 0; i < sum.coef.length; i++) {
			System.out.println(sum.coef[i] + "," + sum.exponent[i]);
		}
		
		System.out.println(sum.evaluate(-1));
		
		System.out.println(sum.hasRoot(0));
		
		Polynomial multi = poly1.multiply(poly2);
		for(int i = 0; i < multi.coef.length; i++) {
			System.out.println(multi.coef[i] + "," + multi.exponent[i]);
		}
		
		File f = new File("test.txt");
		Polynomial poly3 = new Polynomial(f);
		for(int i = 0; i < poly3.coef.length; i++) {
			System.out.println(poly3.coef[i] + "," + poly3.exponent[i]);
		}
		
		poly3.saveToFile("test1.txt");
		
	}
}
