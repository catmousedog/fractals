package me.catmousedog.fractals.fractals.concrete.iterative;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Settings;

/**
 * Number = Integer
 */
public final class IterativeShip extends IterativeFractal {

	public IterativeShip(Settings settings) {
		super(settings);
	}

	private IterativeShip(Settings settings, int iterations, LinearTransform transform, Location[] locations, double r,
			double g, double b, boolean inverted) {
		super(settings, iterations, transform, locations, r, g, b, inverted);
	}

	/**
	 * returns 0 - 255
	 */
	@Override
	public Number get(double cx, double cy) {
		double x = 0, y = 0;
		double tx;
		double t1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			if (t1 + t2 > bailout)
				return 255 - (255 * i) / iterations;

			x = t1 - t2 + cx;
			y = Math.abs(2 * tx * y) + cy;

		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Iterative Burning Ship";
	}

	@Override
	public String formalName() {
		return "IterativeShip";
	}

	@Override
	public String getTip() {
		return "<html>The Buring Ship generated using an escape time algorithm. <br>This allows for deep zooms but creates aliasing effects, but has the shortest generating time.</html>";
	}

	@Override
	public Fractal clone() {
		return new IterativeShip(settings, iterations, transform.clone(), locations, r, g, b, inverted);
	}
}
