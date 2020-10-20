package me.catmousedog.fractals.data;

public class FractalValue {

	public double x, y;

	public int I, i;

	/**
	 * Creates a <code>FractalValue</code> from a complex number <b>x + iy</b> and
	 * an iteration count and a total iterations.
	 * 
	 * @param x
	 * @param y
	 * @param i
	 */
	public FractalValue(double x, double y, int i, int I) {
		this.x = x;
		this.y = y;
		this.i = i;
		this.I = I;
	}

	/**
	 * @return the square of the absolute value of this complex number, the squared
	 *         distance.
	 */
	public double square() {
		return x * x + y * y;
	}

}
