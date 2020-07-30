package me.catmousedog.fractals.fractals;

/**
 * represents a fractal, including its function for each point in 2D space
 */
public abstract class Fractal {

	protected int iterations = 100;

	public Fractal() {
	}

	public Fractal(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * calculates the fractal function for a given point in space
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the value the fractal function returns
	 */
	public abstract double get(double x, double y);

	public void setIterations(int i) {
		iterations = i;
	}

	public int getIterations() {
		return iterations;
	}
}
