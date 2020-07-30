package me.catmousedog.fractals.canvas;

import java.awt.Dimension;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;

/**
 * A class to store the configuration of the canvas, such as the position and
 * the iteration count.
 */
public class Configuration {

	private final LinearTransform transform;

	private final Fractal fractal;

	private double zoomFactor;
	
	private Dimension dimension;

	public Configuration(LinearTransform transform, Fractal fractal, double zoomFactor) {
		this.transform = transform;
		this.fractal = fractal;
		this.zoomFactor = zoomFactor;
	}

	public LinearTransform getTransform() {
		return transform;
	}

	public Fractal getFractal() {
		return fractal;
	}

	public int getIterations() {
		return fractal.getIterations();
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public void setIterations(int iterations) {
		fractal.setIterations(iterations);
	}

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	/**
	 * returns the information needed to recreate this {@link Configuration} as a
	 * String
	 * 
	 * @return String of format: "dx:dy:m:n:rot:iter:zoom"
	 */
	public String getID() {
		return Double.toString(transform.getdx()) + ":" + Double.toString(transform.getdy()) + ":"
				+ Double.toString(transform.getm()) + ":" + Double.toString(transform.getn()) + ":"
				+ Double.toString(transform.getrot()) + ":" + Integer.toString(fractal.getIterations()) + ":"
				+ Double.toString(zoomFactor);
	}

	/**
	 * Changes this {@link Configuration} to match the given String.
	 * <p>
	 * Doing {@code Configuration.fromID(config.getID())} will result in the same
	 * {@link Configuration}.
	 * 
	 * @param id String representing this {@link Configuration} of the form:<br>
	 *           {@code dx:dy:m:n:rot:iter:zoom}
	 *           <p>
	 *           If any of the latter variables are left out, they will not be
	 *           changed.
	 * 
	 * @throws NumberFormatException if any of the values could not be parsed
	 */
	public void fromID(String id) throws NumberFormatException {
		String[] args = id.split(":");

		if (args.length > 1)
			transform.setTranslation(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
		if (args.length > 3)
			transform.setScalar(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
		if (args.length > 4)
			transform.setRot(Double.parseDouble(args[4]));
		if (args.length > 5)
			setIterations(Integer.parseInt(args[5]));
		if (args.length > 6)
			zoomFactor = Double.parseDouble(args[6]);
	}

	/**
	 * @return a copy of this {@link Configuration}
	 */
	@Override
	public Configuration clone() {
		return new Configuration(transform.clone(), fractal, zoomFactor);
	}

}
