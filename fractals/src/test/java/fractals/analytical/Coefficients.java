package fractals.analytical;

import java.util.ArrayList;

import fractals.rational.Complex;

public class Coefficients extends ArrayList<Complex> {

	private static final long serialVersionUID = -7909929014621202110L;

	public static final int N = 4;

	public final Complex[] r = new Complex[N];

	public void update() {
		for (int i = 0; i < r.length; i++) {
			r[i] = f(new Complex(2 * Math.PI * i / (double) N));
		}
	}

	public boolean add(double x, double y) {
		return super.add(new Complex(x, y));
	}
	
	public Complex omega(Complex q) {
		Complex omega = new Complex(1, 0);
		for (Complex r : r) {
			omega = omega.multiply(q.subtract(r));
		}
		omega = omega.multiply(get(0).power(-r.length));
		omega = omega.add(new Complex(1, 0));
		omega = omega.inverse();
		return omega;
	}

	private Complex f(Complex z) {
		Complex sum = new Complex(0, 0);
		for (int i = 0; i < super.size(); i++) {
			sum = sum.add(z.power(1 - i).multiply(super.get(i)));
		}
		return sum;
	}

}
