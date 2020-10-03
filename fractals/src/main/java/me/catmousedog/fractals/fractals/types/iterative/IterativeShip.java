package me.catmousedog.fractals.fractals.types.iterative;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.main.Settings;

/**
 * Number = Integer
 */
public final class IterativeShip extends Fractal {

	public IterativeShip(Settings settings) {
		super(settings);
	}

	private IterativeShip(Settings settings, Fractal fractal) {
		super(settings, fractal);
	}

	/**
	 * returns 0 - 255
	 */
	@Override
	public Number get(double cx, double cy) {
		double x = cx, y = cy;
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
	public String fileName() {
		return "IterativeShip";
	}

	@Override
	public String getTip() {
		return "<html>The Buring Ship generated using an escape time algorithm."
				+ "<br>This allows for deep zooms but creates aliasing effects, generally has the shortest generating time.</html>";
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new IterativeLinearFilter(this), new IterativePeriodicFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new IterativeShip(settings, this);
	}
}
