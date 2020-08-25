package me.catmousedog.fractals.canvas;

import me.catmousedog.fractals.fractals.Fractal;

/**
 * A class to store the configuration of the canvas, such as the position and
 * the iteration count.
 */
public class Configuration {

	/**
	 * The {@link Fractal} containing the fractal function
	 * {@link Fractal#get(double, double)} and the colour filter
	 * {@link Fractal#filter}.
	 */
	private Fractal fractal;

	/**
	 * The zoom factor used when zooming in or out.
	 */
	private double zoomFactor;

	public Configuration(Fractal fractal, double zoomFactor) {
		this.fractal = fractal;
		this.zoomFactor = zoomFactor;
	}

	/**
	 * Change the Fractal belonging to this {@link Configuration}<br>
	 * This method should only be called by {@link Canvas#setFractal(Fractal)}
	 * 
	 * @param fractal
	 */
	void setFractal(Fractal fractal) {
		this.fractal = fractal;
	}

	public Fractal getFractal() {
		return fractal;
	}

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * @return a copy of this {@link Configuration}
	 */
	@Override
	public Configuration clone() {
		return new Configuration(fractal, zoomFactor);
	}

}
