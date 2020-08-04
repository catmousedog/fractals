package me.catmousedog.fractals.fractals;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;

/**
 * Represents a fractal including its fractal function and colour filter.
 * <p>
 * An implementation of this class must define {@link Fractal#items} and any
 * active {@link Data} (listeners) added to it should only call
 * {@link Fractal#saveAndColour()}.
 */
public abstract class Fractal implements Savable {

	/**
	 * Array of all {@link Item}s in order of addition.
	 * <p>
	 * When adding active {@link Data} make sure the event only calls
	 * {@link Fractal#saveAndColour()}.
	 */
	@NotNull
	protected Item[] items;

	/**
	 * The instance of the canvas, used to call {@link Canvas#colourAndPaint()} when
	 * {@link Data} changes.
	 */
	protected Canvas canvas;

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
	 * {@link JPInterface}.
	 * 
	 * @param jp the JPanel to add the {@link Item}s to
	 */
	public void addFilter(JPanel jp) {
		jp.removeAll();
		for (Item i : items)
			jp.add(i.panel());
	}

	/**
	 * Whether or not the listeners in the {@link Fractal#addFilter(JPanel)}
	 * {@link JPanel} are allowed to fire.
	 */
	private boolean allowListener = false;

	/**
	 * Saves all data through {@link Savable#save()} and colours the image through
	 * {@link Canvas#colourAndPaint()}.
	 * <p>
	 * This can only be called if {@link Fractal#allowListener} is true as this
	 * should only be called through a listener triggered by the user. Not the
	 * {@link Savable#update()} method.
	 */
	public void saveAndColour() {
		if (allowListener) {
			save();
			canvas.colourAndPaint();
		}
	}

	/**
	 * Updates the panel using {@link Savable#update()} but doesn't allow the
	 * listeners to trigger. This method should always be used when updating the
	 * data the user can interact with.
	 */
	public void safeUpdate() {
		allowListener = false;
		update();
		allowListener = true;
	}

	/**
	 * Returns the name of this fractal.
	 */
	@Override
	public abstract String toString();

	/**
	 * Return a copy of this {@link Fractal}
	 */
	@Override
	public abstract Fractal clone();

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void setIterations(int i) {
		iterations = i;
	}

	public int getIterations() {
		return iterations;
	}

}
