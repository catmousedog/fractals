package me.catmousedog.fractals.fractals.types.potential;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;

/**
 * Number = Integer
 */
public final class PotentialInverseMandelbrot extends Fractal {

	public PotentialInverseMandelbrot() {
		super();
	}

	private PotentialInverseMandelbrot(Fractal fractal) {
		super(fractal);
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
				return p / Math.pow(2, i);
			}
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Potential I-Mandelbrot";
	}

	@Override
	public String fileName() {
		return "PotentialInverseMandelbrot";
	}

	@Override
	public String getTip() {
		return "<html>The second order mandelbrot with the reciprocal of the coordinate added instead. (<i>z²+1/c<i/>)"
				+ "<br>This allows for smooth pictures but long generating times."
				+ "<br>Not great for deep zooms.</html>";
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new LogPeriodicFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new PotentialInverseMandelbrot(this);
	}
}
