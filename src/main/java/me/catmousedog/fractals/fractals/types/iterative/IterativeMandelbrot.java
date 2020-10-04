package me.catmousedog.fractals.fractals.types.iterative;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.main.Settings;

/**
 * Number = Integer
 */
public final class IterativeMandelbrot extends Fractal {

	public IterativeMandelbrot(Settings settings) {
		super(settings);
	}

	private IterativeMandelbrot(Settings settings, Fractal fractal) {
		super(settings, fractal);
	}

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
				return 255 * (iterations - i) / iterations;

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;

		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Iterative Mandelbrot";
	}

	@Override
	public String fileName() {
		return "IterativeMandelbrot";
	}

	@Override
	public String getTip() {
		return "<html>The second order mandelbrot (<i>z²+c<i/>) generated using an escape time algorithm."
				+ "<br>This allows for deep zooms but creates aliasing effects, generally has the shortest generating time.</html>";
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new IterativeLinearFilter(this), new IterativePeriodicFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new IterativeMandelbrot(settings, this);
	}
}
