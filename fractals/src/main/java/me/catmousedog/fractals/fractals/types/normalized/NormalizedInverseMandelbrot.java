package me.catmousedog.fractals.fractals.types.normalized;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;
import me.catmousedog.fractals.main.Settings;

/**
 * Number = Integer
 */
public final class NormalizedInverseMandelbrot extends Fractal {

	public NormalizedInverseMandelbrot(Settings settings) {
		super(settings);
	}

	private NormalizedInverseMandelbrot(Settings settings, Fractal fractal) {
		super(settings, fractal);
	}

	@Override
	public Number get(double cx, double cy) {
		double kx = cx / (cx * cx + cy * cy);
		double ky = -cy / (cx * cx + cy * cy);

		double x = kx, y = ky;
		double tx;
		double t1, t2;

		double p = 0;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			x = t1 - t2 + kx;
			y = 2 * tx * y + ky;

			p = Math.log(t1 + t2) / 2;

			if (p > bailout) {
				return i + 3 - Math.log(p) / Math.log(2);
			}
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Normalized I-Mandelbrot";
	}

	@Override
	public String fileName() {
		return "NormalizedInverseMandelbrot";
	}

	@Override
	public String getTip() {
		return "<html>The second order mandelbrot with the reciprocal of the coordinate added instead. (<i>z²+1/c<i/>)"
				+ "<br>This allows for deep zooms and smooth pictures but long generating times.</html>";
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new LogPeriodicFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new NormalizedInverseMandelbrot(settings, this);
	}
}
