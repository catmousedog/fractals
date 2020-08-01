package me.catmousedog.fractals.fractals;

import javax.swing.JPanel;

import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.ui.Savable;

/**
 * Represents a fractal including its fractal function and colour filter. This
 * class also holds a {@link Fractal#addFilter(JPanel)} which creates a
 * {@link Savable} user interface.
 */
public abstract class Fractal implements Savable {

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
	public abstract Number get(double x, double y);

	/**
	 * Applies the calculated value 'v' to a colour filter and returns an integer
	 * rgb value.
	 * 
	 * @param v value calculated by the fractal
	 * @return the rgb value as an integer
	 */
	public abstract int filter(Number v);

	/**
	 * Adds all the necessary components to a {@link JPanel} on the
	 * {@link JPInterface}. This includes any
	 * 
	 * @param jp
	 */
	public abstract void addFilter(JPanel jp);

	/**
	 * Returns the name of this fractal.
	 */
	@Override
	public abstract String toString();

	public void setIterations(int i) {
		iterations = i;
	}

	public int getIterations() {
		return iterations;
	}

}
