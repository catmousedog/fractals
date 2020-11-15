package me.catmousedog.fractals.data;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;

/**
 * A value returned by the {@link Fractal #get(double, double)} method. This
 * <code>FractalValue</code> is then passed on to the current {@link Function}.
 * <p>
 * If the {@link FractalValue#i} equals the {@link FractalValue#I} the point
 * associated with the <code>FractalValue</code> is convergent.
 */
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
	 * @return true if {@link FractalValue#i} equals {@link FractalValue#I}.
	 */
	public boolean isConvergent() {
		return i == I;
	}

	/**
	 * @return the square of the absolute value of this complex number, the squared
	 *         distance.
	 */
	public double square() {
		return x * x + y * y;
	}

}
