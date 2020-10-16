package me.catmousedog.fractals.fractals;

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
	 * Creates a <code>FractalValue</code> with all values assigned to zero.<br>
	 * This value is used for convergent points.
	 */
	public FractalValue() {
		x = 0;
		y = 0;
		i = 0;
		I = 0;
	}

	/**
	 * @return the square of the absolute value of this complex number, the squared
	 *         distance.
	 */
	public double square() {
		return x * x + y * y;
	}

}
