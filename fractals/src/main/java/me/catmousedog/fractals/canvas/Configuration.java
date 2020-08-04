package me.catmousedog.fractals.canvas;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;

/**
 * A class to store the configuration of the canvas, such as the position and
 * the iteration count.
 */
public class Configuration {

	/**
	 * The linear transform used to get to the current location, including:
	 * translation, scaling and rotation.
	 */
	private final LinearTransform transform;

	/**
	 * The {@link Fractal} containing the fractal function
	 * {@link Fractal#get(double, double)} and the colour filter
	 * {@link Fractal#filter}.
	 */
	private Fractal fractal;

	/**
	 * The iteration count for the fractal, this is also stored in this class for
	 * when {@link Configuration#setFractal(Fractal)} is used.
	 */
	private int iterations;

	/**
	 * The zoom factor used when zooming in or out.
	 */
	private double zoomFactor;

	public Configuration(LinearTransform transform, Fractal fractal, int iterations, double zoomFactor) {
		this.transform = transform;
		setFractal(fractal);
		setIterations(iterations);
		this.zoomFactor = zoomFactor;
	}

	public LinearTransform getTransform() {
		return transform;
	}

	public void setFractal(Fractal fractal) {
		fractal.setIterations(iterations);
		this.fractal = fractal;
	}

	public Fractal getFractal() {
		return fractal;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
		fractal.setIterations(iterations);
	}

	public int getIterations() {
		return iterations;
	}

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public double getZoomFactor() {
		return zoomFactor;
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
	 *           changed. <br>
	 *           But at least 4 must be given.
	 * 
	 * @return True if successful, false otherwise. <br>
	 *         If the String doesn't contain at least 4 items, false is returned.
	 */
	public boolean fromID(String id) {
		String[] args = id.split(":");

		if (args.length < 4)
			return false;

		try {
			transform.setTranslation(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
			transform.setScalar(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
			if (args.length > 4)
				transform.setRot(Double.parseDouble(args[4]));
			if (args.length > 5)
				setIterations(Integer.parseInt(args[5]));
			if (args.length > 6)
				zoomFactor = Double.parseDouble(args[6]);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * @return a copy of this {@link Configuration}
	 */
	@Override
	public Configuration clone() {
		return new Configuration(transform.clone(), fractal, iterations, zoomFactor);
	}

}
