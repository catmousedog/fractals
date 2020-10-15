package me.catmousedog.fractals.utils;

public class FractalValue {

	public double x, y;
	
	public int i;

	public FractalValue(double x, double y, int i) {
		this.x = x;
		this.y = y;
		this.i = i;
	}
	
	/**
	 * @return the square of the absolute value of this complex number, the squared distance.
	 */
	public double square() {
		return x*x+y*y;
	}

}
